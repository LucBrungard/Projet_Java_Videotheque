package videotheque;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;

import produit.Produit;

public class LigneCommande {
	Date date_deb;
	Date date_fin; 	
	String idproduit;
	DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	
	public LigneCommande(Date DateCrea, Date DateFin, String idproduit) {
		this.idproduit = idproduit;
		this.date_deb = DateCrea;
		this.date_fin = DateFin;		
	}
	
	public LigneCommande(JSONObject src) {		
		try {
			this.date_deb = dateFormat.parse(src.getJSONObject("dateDeb").toString());	
			this.date_fin = dateFormat.parse(src.getJSONObject("dateFin").toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		this.idproduit = src.get("produit").toString();
	}
	
	public JSONObject toJson() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("dateDeb", dateFormat.format(this.date_deb));
		jsonObject.put("dateFin", dateFormat.format(this.date_fin));
		jsonObject.put("produit", this.idproduit);
		
		return jsonObject;
	}

	@Override
	public String toString() {
		return "LigneCommande [date_deb=" + dateFormat.format(date_deb) + ", date_fin=" + dateFormat.format(date_fin) + "]";
	}

	public Date getDate_deb() {
		return date_deb;
	}

	public Date getDate_fin() {
		return date_fin;
	}

	public String getProduit() {
		return idproduit;
	}
	
	
	
}
