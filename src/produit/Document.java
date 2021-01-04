package produit;

import org.json.JSONObject;

public abstract class Document extends Produit{

	public Document(String nature, String id, String titre, float tarif_journalier) {
		super(nature, id, titre, tarif_journalier);
		// TODO Auto-generated constructor stub
	}

	public Document(JSONObject src) {
		super(src);
	}
	
	public JSONObject toJson() {
		JSONObject jsonObject = super.toJson();
		return jsonObject;
	}
}
