package produit;

import org.json.JSONObject;

public class Dictionnaire extends Document {
	String langue;
	
	public Dictionnaire(String nature, String id, String titre, float tarif_journalier, String langue) {
		super(nature, id, titre, tarif_journalier);
		this.langue = langue;
		// TODO Auto-generated constructor stub
	}

	public Dictionnaire(JSONObject src) {
		super(src);
		String nat = src.get("nature").toString();
		if (nat == "Dictionnaire") {
			this.langue = src.get("langue").toString();
			this.nat = nat;
		}
	}
	
	public JSONObject toJson() {
		JSONObject jsonObject = super.toJson();
		jsonObject.put("langue", this.langue);
		return jsonObject;
	}	

	public String getLangue() {
		return langue;
	}

	@Override
	public String toString() {
		return "Dictionnaire [langue=" + langue + ", id=" + id + ", nat=" + nat + ", titre=" + titre
				+ ", tarifjournalier=" + tarifjournalier + "]";
	}

	
}
