package produit;

import org.json.JSONObject;

public class DVD extends SupportNumérique{
	public DVD(String nature, String id, String titre, float tarif_journalier, String realisateur) {
		super(nature, id, titre, tarif_journalier);
		this.realisateur = realisateur;
		// TODO Auto-generated constructor stub
	}

	public DVD(JSONObject src) {
		super(src);
		String nat = src.get("nature").toString();
		if (nat == "DVD") {
			this.realisateur = src.get("annee").toString();
			this.nat = nat;
		}
	}
	
	public JSONObject toJson() {
		JSONObject jsonObject = super.toJson();
		jsonObject.put("realisateur", this.realisateur);
		return jsonObject;
	}

	String realisateur;

	public String getRealisateur() {
		return realisateur;
	}

	@Override
	public String toString() {
		return "DVD [realisateur=" + realisateur + ", id=" + id + ", nat=" + nat + ", titre=" + titre
				+ ", tarifjournalier=" + tarifjournalier + "]";
	}
	
	
}
