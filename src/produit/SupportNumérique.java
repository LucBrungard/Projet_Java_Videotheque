package produit;

import org.json.JSONObject;

public abstract class SupportNumérique extends Produit{

	public SupportNumérique(String nature, String id, String titre, float tarif_journalier) {
		super(nature, id, titre, tarif_journalier);
		// TODO Auto-generated constructor stub
	}
	
	public SupportNumérique(JSONObject src) {
		super(src);
	}
	
	public JSONObject toJson() {
		JSONObject jsonObject = super.toJson();
		return jsonObject;
	}

}
