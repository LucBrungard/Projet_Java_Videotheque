package produit;

import org.json.JSONObject;

public class CD extends SupportNumérique{
	String annee;
	
	public CD(String nature, String id, String titre, float tarif_journalier, String annee) {
		super(nature, id, titre, tarif_journalier);
		this.annee = annee;
		// TODO Auto-generated constructor stub
	}

	public CD(JSONObject src) {
		super(src);
		String nat = src.get("nature").toString();
		if (nat == "CD") {
			this.annee = src.get("annee").toString();
			this.nat = nat;
		}
	}
	
	public JSONObject toJson() {
		JSONObject jsonObject = super.toJson();
		jsonObject.put("annee", this.annee);
		return jsonObject;
	}

	public String getAnnee() {
		// TODO Auto-generated method stub
		return this.annee;
	}

	@Override
	public String toString() {
		return "CD [annee=" + annee + ", id=" + id + ", nat=" + nat + ", titre=" + titre + ", tarifjournalier="
				+ tarifjournalier + "]";
	}

	
}
