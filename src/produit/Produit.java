package produit;

import org.json.JSONObject;

public abstract class Produit {
	String id;
	protected String nat;
	protected String titre;
	protected float tarifjournalier = 0;

	public Produit(JSONObject src) {
		this.id = src.get("id").toString();
		this.nat = src.get("nature").toString();
		this.titre = src.get("titre").toString();
		this.tarifjournalier = Float.valueOf(src.get("tarifJournalier").toString());		
	}
	
	public JSONObject toJson() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", this.id);
		jsonObject.put("nature", this.nat);
		jsonObject.put("titre", this.titre);
		jsonObject.put("tarifJournalier", this.tarifjournalier);
		return jsonObject;
	}
	
	public String getId() {
		return id;
	}

	public String getTitre() {
		return titre;
	}

	public float getTarif_journalier() {
		return tarifjournalier;
	}
		
	public Produit(String nature, String id, String titre, float tarifjournalier) {
		this.nat = nature;
		this.id = id;
		this.titre = titre;
		this.tarifjournalier = tarifjournalier;
	}

	public String getNat() {
		return nat;
	}
	
	
}
