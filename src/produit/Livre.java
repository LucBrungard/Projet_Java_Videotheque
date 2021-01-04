package produit;

import org.json.JSONObject;

public class Livre extends Document{
	
	String auteur;
	
	public Livre(String nature, String id, String titre, float tarif_journalier, String auteur) {	
		super(nature, id, titre, tarif_journalier);
		this.auteur = auteur;
		this.nat = "Livre";
		
	}
	
	public Livre(JSONObject src) {
		super(src);
		String nat = src.get("nature").toString();
		if (nat == "Livre") {
			this.auteur = src.get("auteur").toString();
			this.nat = nat;
		}
	}

	public JSONObject toJson() {
		JSONObject jsonObject = super.toJson();
		jsonObject.put("auteur", this.auteur);
		return jsonObject;
	}

	public String getAuteur() {
		return auteur;
	}

	@Override
	public String toString() {
		return "Livre [auteur=" + auteur + ", id=" + id + ", nat=" + nat + ", titre=" + titre + ", tarifjournalier="
				+ tarifjournalier + "]";
	}
	
	

}
