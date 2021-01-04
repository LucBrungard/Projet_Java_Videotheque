package produit;

import org.json.JSONObject;

public class BD extends Livre{

	public BD(String nature, String id, String titre, float tarif_journalier, String auteur) {
		super(nature, id, titre, tarif_journalier, auteur);
		this.nat = "BD";
	}
	
	

	public BD(JSONObject src) {
		super(src);
		this.nat = src.get("nature").toString();
	}

	public JSONObject toJson() {
		JSONObject jsonObject = super.toJson();
		return jsonObject;
	}
	
	public String getAuteur() {
		return super.getAuteur();
	}



	@Override
	public String toString() {
		return "BD [auteur=" + auteur + ", id=" + id + ", nat=" + nat + ", titre=" + titre + ", tarifjournalier="
				+ tarifjournalier + "]";
	}
	
	
}
