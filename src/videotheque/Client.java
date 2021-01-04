package videotheque;

import java.io.FileWriter;
import java.io.IOException;

import org.json.*;

public class Client {
	String idClient;
	protected String nom;
	protected String prenom;
	protected float reduc = 0;
	
	
	public Client() {
	}
	
	public Client(String identifiant, String nom, String prenom, float reduc) {
		this.idClient = identifiant;
		this.nom = nom;
		this.prenom = prenom;
		this.reduc = reduc;
	}
	
	public Client(JSONObject src) {
		this.idClient = src.get("id").toString();
		this.nom = src.get("nom").toString();
		this.prenom = src.get("prenom").toString();
		this.reduc = Float.valueOf(src.get("reduc").toString());		
	}
	
	public static void AjoutClient(String identifiant, String nom, String prenom) {
		Videotheque.TabClient.add(new Client(identifiant, nom, prenom, 0));
	}

	public String toString() {
		return "|Client| ID = " + idClient + " Nom = " + nom + ", Prenom = " + prenom + ", Reduction = " + reduc + "%";
	}
	
	public JSONObject toJson() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", this.idClient);
		jsonObject.put("nom", this.nom);
		jsonObject.put("prenom", this.prenom);
		jsonObject.put("reduc", this.reduc);
		return jsonObject;
	}

	public String getIdClient() {
		return idClient;
	}

	public String getNom() {
		return nom;
	}

	public String getPrenom() {
		return prenom;
	}
	
	public float getReduc() {
		return reduc;
	}
	
	public void write() {
		try (FileWriter file = new FileWriter("MaVideotheque.json")) {
			JSONObject obj = this.toJson();
			file.write(obj.toString());
			file.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
}

	public void setReduc(float reduc) {
		this.reduc = reduc;
	}

	
}
