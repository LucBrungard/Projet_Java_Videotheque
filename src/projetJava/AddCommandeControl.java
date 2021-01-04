package projetJava;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import org.json.JSONArray;
import org.json.JSONObject;

import produit.Produit;
import projetJava.PageProduitControl.BD;
import projetJava.PageProduitControl.CD;
import projetJava.PageProduitControl.DVD;
import projetJava.PageProduitControl.Dictionnaire;
import projetJava.PageProduitControl.Livre;
import projetJava.PageProduitControl.ManuelScolaire;
import projetJava.PageProduitControl.Roman;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import videotheque.Client;
import videotheque.Commande;
import videotheque.LigneCommande;
import videotheque.Videotheque;

public class AddCommandeControl implements Initializable {
	@FXML private ComboBox<String> clientcombobox;
	@FXML private ComboBox<String> produitcombobox;
	@FXML private DatePicker textdatedebutcommande;
	@FXML private DatePicker textdatefincommande;
	@FXML private Button btnvalidercommande;
	@FXML private Button btnannulercommande;
	@FXML private Button btnmodifiercommande;
	
	ObservableList<String> produit = FXCollections.observableArrayList();
	ObservableList<String> client = FXCollections.observableArrayList();
	
	Videotheque nouvelle = new Videotheque();
	DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	
	@Override
	public void initialize(URL location, ResourceBundle ProjetJava) {				
		loadDate();
	}
	
	@FXML public void initialize() {
		produitcombobox.setItems(produit);
		clientcombobox.setItems(client);
	}
	
	

	@FXML protected void Annuler(ActionEvent e) throws IOException {
		Parent test = FXMLLoader.load(getClass().getResource("/projetJava/fxml/PageCommande.fxml"));
		Scene scene = new Scene(test);
		Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.centerOnScreen();
		window.show();
	}
	
	public JSONObject toJson() {
		  JSONObject jsonObject = new JSONObject();

		  JSONArray dtalcclient = new JSONArray();
		  for (Client cl : nouvelle.TabClient) {
			  dtalcclient.put(cl.toJson());
		  }
		  jsonObject.put("Clients", dtalcclient);
		
		
		  JSONArray dtalccom = new JSONArray(); 
		  for (Commande com : nouvelle.TabCommande) { 
			  dtalccom.put(com.toJson());
		  } 
		  jsonObject.put("Commandes", dtalccom);
		  
		  JSONArray dtalcprod = new JSONArray(); 
		  for (produit.Produit prod : nouvelle.TabProduit) {  
			  dtalcprod.put(prod.toJson());
		  } 
		  jsonObject.put("Produits", dtalcprod);
		 	
		  return jsonObject;
	}
	
	public void write() {
		try (FileWriter file = new FileWriter("MaVideotheque.json")) {
			JSONObject obj = this.toJson();
			file.write(obj.toString());
			file.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String creationId() {
		int i = 0;
		if (nouvelle.TabCommande.size() != 0) {
			String idcom = nouvelle.TabCommande.get(nouvelle.TabCommande.size()-1).getIdCom(); 
			i = Integer.parseInt(idcom.substring(2, 5));
			i+=1;
		} 		
		return String.format("%03d", i);				
	}
	  
    private void loadDate() {
		for (produit.Produit pr : nouvelle.TabProduit) {	
			String idprod = pr.getId();
			String titre = pr.getTitre();
			float tarifj = pr.getTarif_journalier();
			String nature = pr.getNat();
			
			produit.add(idprod + " | " + nature + " | " + titre + " | " + tarifj);
		}
		
		for (videotheque.Client cl : nouvelle.TabClient) {
			String nom = cl.getNom();
			String prenom = cl.getPrenom();
			String id = cl.getIdClient();
			client.add(id + " | " + nom + " "+ prenom);
		}
	}
    
    @FXML public void ajoutercommande(ActionEvent e) throws IOException{
    	String idCom = "co"+creationId();
    	String idclient = clientcombobox.getValue().substring(0, 5);
    	String idprod = produitcombobox.getValue();
    	
    	LocalDate datecrea = null;
    	Date dateCrea = null;
    	
    	LocalDate datefin = null;
    	Date dateFin = null;
    	
    	float reduc = 0;
    	ZoneId defaultZoneId = ZoneId.systemDefault();
    	
    	ArrayList<LigneCommande> tablignecommande = new ArrayList<LigneCommande>();
    	
    	//datecrea de type LocalDate
    	datecrea = textdatedebutcommande.getValue();
    	
    	//dateCrea de type Date
    	dateCrea = Date.from(datecrea.atStartOfDay(defaultZoneId).toInstant());
    	
    	//datefin de type LocalDate
    	datefin = textdatefincommande.getValue();
    	
    	//dateFin de type Date
    	dateFin = Date.from(datefin.atStartOfDay(defaultZoneId).toInstant());
    	
    	tablignecommande.add(new LigneCommande(dateCrea, dateFin, idprod));
    	
    	nouvelle.TabCommande.add(new Commande(idclient, idCom, dateCrea, reduc, tablignecommande));
    	
    	write();
    	
    	for (videotheque.Client cl : nouvelle.TabClient) {
    		int compteurcommande = 0;
	    	for (videotheque.Commande com : nouvelle.TabCommande) {
				if (cl.getIdClient().equals(com.getIdClient())) {
					compteurcommande += 1;
				}
			}			
			if (compteurcommande >= 5) {
				reduc = 10;
			}
			else {
				reduc = 0;
			}
			cl.setReduc(reduc);
    	}
    	
    	write(); 
    	
    	for (Client client : nouvelle.TabClient) {
    		if (client.getIdClient().equals(idclient)) {
    			reduc = client.getReduc();
    			for (Commande com : nouvelle.TabCommande) {
        			if (com.getIdCom().equals(idCom)) {
        				com.setReduc(reduc);
        			}
        		}    			
    		}
    	}
    	
    	write();
    	  	
    	Annuler(e);
    }
}
