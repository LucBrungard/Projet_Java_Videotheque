package produit;

import org.json.JSONObject;

public abstract class SupportNum�rique extends Produit{

	public SupportNum�rique(String nature, String id, String titre, float tarif_journalier) {
		super(nature, id, titre, tarif_journalier);
		// TODO Auto-generated constructor stub
	}
	
	public SupportNum�rique(JSONObject src) {
		super(src);
	}
	
	public JSONObject toJson() {
		JSONObject jsonObject = super.toJson();
		return jsonObject;
	}

}
