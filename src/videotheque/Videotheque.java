package videotheque;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.nio.file.Files;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import org.json.JSONArray;
import org.json.JSONObject;

import produit.*;

public class Videotheque {
	public static ArrayList<Client> TabClient;
	public static ArrayList<Produit> TabProduit;
	public static ArrayList<Commande> TabCommande;

	public Videotheque() {
		TabClient = new ArrayList<Client>();
		TabProduit = new ArrayList<Produit>();
		TabCommande = new ArrayList<Commande>();
		JSONObject src = null;
		
		try {
			String contenu = new String(Files.readAllBytes(Paths.get("MaVideotheque.json")));
			src = new JSONObject(contenu);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		JSONArray dtalcclient = src.getJSONArray("Clients");
		for (int i=0; i<dtalcclient.length(); i++) {
			
			Client lc = new Client(dtalcclient.getJSONObject(i));
			TabClient.add(lc);
		}
				
		JSONArray dtalccom = src.getJSONArray("Commandes");
		for (int i =0; i<dtalccom.length(); i++) {
			Commande com = new Commande(dtalccom.getJSONObject(i));
			TabCommande.add(com);
		}

		JSONArray dtalcprod = src.getJSONArray("Produits");
		for (int i =0; i<dtalcprod.length(); i++) {
			JSONObject obj = dtalcprod.getJSONObject(i);
			String nature = obj.getString("nature");
			switch (nature) {
			case "BD" :
				TabProduit.add(new BD(obj));
				break;
			case "Livre":
				TabProduit.add(new Livre(obj));
				break;
			case "Dictionnaire" :
				TabProduit.add(new Dictionnaire(obj));
				break;
			case "ManuelScolaire" :
				TabProduit.add(new ManuelScolaire(obj));
				break;
			case "Roman" :
				TabProduit.add(new Roman(obj));
				break;
			case "CD" :
				TabProduit.add(new CD(obj));
				break;
			case "DVD":
				TabProduit.add(new DVD(obj));
				break;
			}
			
		}
	}
	
	public static JSONObject toJson() {
		JSONObject jsonObject = new JSONObject();

		JSONArray dtalcclient = new JSONArray();
		for (Client cl : TabClient) {
			dtalcclient.put(cl.toJson());
		}
		jsonObject.put("Clients", dtalcclient);
		
		JSONArray dtalcprod = new JSONArray();
		for (Produit prod : TabProduit) {
			dtalcprod.put(prod.toJson());
		}
		jsonObject.put("Produits", dtalcprod);
		
		JSONArray dtalccom = new JSONArray();
		for (Commande com : TabCommande) {
			dtalccom.put(com.toJson());
		}
		jsonObject.put("Commandes", dtalccom);
		
		
		
		return jsonObject;
	}
	
	public static void write() {
		try (FileWriter file = new FileWriter("MaVideotheque.json")) {
			JSONObject obj = toJson();
			file.write(obj.toString());
			file.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static ArrayList<Client> getTabClient () {
		return TabClient;
	}
	
	public static ArrayList<Produit> getTabProduit () {
		return TabProduit;
	}
	
	public static ArrayList<Commande> getTabCommande () {
		return TabCommande;
	}
	
	public static void removeClient(String idcl) {
		Videotheque nouvelle = new Videotheque();
		
		Iterator<Client> itr = nouvelle.TabClient.iterator();
		
		while (itr.hasNext()) {
			Client cl = (Client) itr.next();
			if (cl.getIdClient().equals(idcl)) {
				itr.remove();
				break;
			}
		}
		write();
		
		Iterator<Commande> itr1 = nouvelle.TabCommande.iterator();
		
		while (itr1.hasNext()) {
			Commande com = (Commande) itr1.next();
			if (com.getIdClient().equals(idcl)) {
				itr1.remove();
			}
		}
		write();
	}
	
	public static void removeProduit(String idprod) {
		Videotheque nouvelle = new Videotheque();
		
		Iterator<Produit> itr = nouvelle.TabProduit.iterator();
		
		while (itr.hasNext()) {
			Produit prod = (Produit) itr.next();
			if (prod.getId().equals(idprod)) {
				itr.remove();
				break;
			}
		}
		write();
		
		Iterator<Commande> itr1 = nouvelle.TabCommande.iterator();
		
		while (itr1.hasNext()) {
			Commande com = (Commande) itr1.next();
			Iterator<LigneCommande> itr2 = com.getTabLigneCommande().iterator();
			while (itr2.hasNext()) {
				LigneCommande lc = (LigneCommande) itr2.next();
				System.out.println(lc.getProduit());
				if (lc.getProduit().substring(0, 5).equals(idprod)) {
					itr2.remove();
					break;
				}
			}
			if (com.getTabLigneCommande().size() == 0) {
				itr1.remove();
			}
		}
		write();
		
	}
	
	public static void removeCommande(String idcom) {
		Videotheque nouvelle = new Videotheque();
		Iterator<Commande> itr1 = nouvelle.TabCommande.iterator();
		
		while (itr1.hasNext()) {
			Commande com = (Commande) itr1.next();
			if (com.getIdCom().equals(idcom)) {
				itr1.remove();
			}
		}
		write();
	}
	
	public static void removeLigneCommande(String idcom, Date datefin, String idprod) {
		Videotheque nouvelle = new Videotheque();
		Iterator<Commande> itr1 = nouvelle.TabCommande.iterator();
		
		while (itr1.hasNext()) {
			Commande com = (Commande) itr1.next();
			
			if (com.getIdCom().equals(idcom)) {
				Iterator<LigneCommande> itr2 = com.getTabLigneCommande().iterator();
				while (itr2.hasNext()) {
					LigneCommande lc = (LigneCommande) itr2.next();
					System.out.println(idprod);
					System.out.println(datefin);
					System.out.println(lc.getProduit().substring(0, 5));
					System.out.println(lc.getDate_fin().compareTo(datefin));
					if (lc.getProduit().substring(0, 5).equals(idprod.substring(0, 5)) && (lc.getDate_fin().compareTo(datefin) == 0)) {
						itr2.remove();
						break;
					}
				}
				if (com.getTabLigneCommande().size() == 0) {
					itr1.remove();
				}
			}	
		}
		write();		 	
	}
}
