package produit;

import org.json.JSONObject;

public class Roman extends Livre{	

	public Roman(String nature, String id, String titre, float tarif_journalier, String auteur) {
		super(nature, id, titre, tarif_journalier, auteur);
		// TODO Auto-generated constructor stub
	}

	public Roman(JSONObject src) {
		super(src);
		String nat = src.get("nature").toString();
		if (nat.equals("Roman")) {
			this.auteur = src.get("auteur").toString();
			this.nat = nat;
		}// TODO Auto-generated constructor stub
	}
	
	public JSONObject toJson() {
		JSONObject jsonObject = super.toJson();
		return jsonObject;
	}

	@Override
	public String toString() {
		return "Roman [auteur=" + auteur + ", id=" + id + ", nat=" + nat + ", titre=" + titre + ", tarifjournalier="
				+ tarifjournalier + "]";
	}

	

}
