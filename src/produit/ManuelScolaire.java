package produit;

import org.json.JSONObject;

public class ManuelScolaire extends Livre{

	public ManuelScolaire(String nature, String id, String titre, float tarif_journalier, String auteur) {
		super(nature, id, titre, tarif_journalier, auteur);
		// TODO Auto-generated constructor stub
	}

	public ManuelScolaire(JSONObject src) {
		super(src);
		String nat = src.get("nature").toString();
		if (nat == "ManuelScolaire") {
			this.auteur = src.get("auteur").toString();
			this.nat = nat;
		}
	}
	
	public JSONObject toJson() {
		JSONObject jsonObject = super.toJson();
		return jsonObject;
	}

	@Override
	public String toString() {
		return "ManuelScolaire [auteur=" + auteur + ", id=" + id + ", nat=" + nat + ", titre=" + titre
				+ ", tarifjournalier=" + tarifjournalier + "]";
	}
	
	

}
