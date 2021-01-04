package videotheque;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

public class Commande {
	String idClient;
	String idCom;
	Date date_crea; 
	DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	float reduc;
	ArrayList<LigneCommande> TabLigneCommande = new ArrayList<LigneCommande>();
	
	public Commande(JSONObject src) {
		Date datecrea = null;
		Date datefin = null;
		
		this.idClient = src.get("idClient").toString();
		this.idCom = src.get("idCom").toString();
		try {
			this.date_crea = dateFormat.parse(src.get("dateCrea").toString());	
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		this.reduc = Float.valueOf(src.get("reduc").toString());	
		
		JSONArray dtalc = src.getJSONArray("ligneCommande");
			
		for (int i=0; i<dtalc.length(); i++) {
			JSONObject lignecommande = dtalc.getJSONObject(i);
			try {
				datecrea = dateFormat.parse(lignecommande.get("dateDeb").toString());
				datefin = dateFormat.parse(lignecommande.get("dateFin").toString());
			} catch (ParseException e) {
				e.printStackTrace();
			}			
			String produit = lignecommande.get("produit").toString();
			
			LigneCommande lc = new LigneCommande(datecrea, datefin, produit);
			this.TabLigneCommande.add(lc);
		}
		
	}
	
	public JSONObject toJson() {
		JSONObject jsonObject = new JSONObject();
		
		jsonObject.put("idClient", this.idClient);
		jsonObject.put("idCom", this.idCom);
		jsonObject.put("dateCrea", dateFormat.format(this.date_crea));
		jsonObject.put("reduc", this.reduc);
		
		JSONArray dtalc = new JSONArray();
		for (LigneCommande lc : TabLigneCommande) {
			dtalc.put(lc.toJson());
		}
		jsonObject.put("ligneCommande", dtalc);
		
		return jsonObject;
	}
	
	public Commande(String idclient, String idCom, Date date_crea, float reduc, ArrayList<LigneCommande> TabLigneCommande) {
		this.idClient = idclient;
		this.idCom = idCom;
		this.date_crea = date_crea;
		this.reduc = reduc;
		this.TabLigneCommande = TabLigneCommande;
	}

	@Override
	public String toString() {
		return "Commande [idClient=" + idClient + ", idCom=" + idCom + ", date_crea=" + dateFormat.format(date_crea) + ", reduc=" + reduc
				+ ", TabLigneCommande=" + TabLigneCommande.toString() + "]";
	}

	public String getIdClient() {
		return idClient;
	}

	public String getIdCom() {
		return idCom;
	}

	public Date getDatecrea() {
		return date_crea;
	}

	public float getReduc() {
		return reduc;
	}

	public ArrayList<LigneCommande> getTabLigneCommande() {
		return TabLigneCommande;
	}

	public void setReduc(float reduc) {
		this.reduc = reduc;
	}

	

	
	
	

	
}