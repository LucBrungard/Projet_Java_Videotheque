package projetJava;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.json.JSONArray;
import org.json.JSONObject;

import produit.Produit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import videotheque.Client;
import videotheque.Commande;
import videotheque.Videotheque;

public class AddClientControl implements Initializable {
	
	@FXML private TextField txtclientid;
	@FXML private TextField txtclientnom;
	@FXML private TextField txtclientprenom;
	@FXML private TextField txtclientreduc;
	@FXML private Button validerbtnclient;
	@FXML private Button btnclientannuler;

	Videotheque nouvelle = new Videotheque();
	
	@Override
	public void initialize(URL location, ResourceBundle ProjetJava) {		
	}

	@FXML protected void Annuler(ActionEvent e) throws IOException {
		Parent test = FXMLLoader.load(getClass().getResource("/projetJava/fxml/PageClient.fxml"));
		Scene scene = new Scene(test);
		Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.centerOnScreen();
		window.show();
	}
	
	@FXML public void addclient(ActionEvent e) throws IOException{
		
		if(txtclientnom.getText().isEmpty() || txtclientprenom.getText().isEmpty())
		{
			
		}
		else
		{
			String idclient = "cl"+creationId();
			String nomclient = txtclientnom.getText();
			String prenomclient = txtclientprenom.getText();
			
			nouvelle.TabClient.add(new Client(idclient, nomclient, prenomclient, 0));
		
			   write();
		
			   Annuler(e);
	}
	}
	
	public String creationId() {
		int i = 0;
		if (nouvelle.TabClient.size() != 0) {
			String idcom = nouvelle.TabClient.get(nouvelle.TabClient.size()-1).getIdClient(); 
			i = Integer.parseInt(idcom.substring(2, 5));
			i+=1;
		} 		
		return String.format("%03d", i);				
	}
	
	public JSONObject toJson() {
		  JSONObject jsonObject = new JSONObject();

		  JSONArray dtalcclient = new JSONArray();
		  for (Client cl : nouvelle.TabClient) {
			  dtalcclient.put(cl.toJson());
		  }
		  jsonObject.put("Clients", dtalcclient);
		
		
		  JSONArray dtalccom = new JSONArray(); 
		  for (Commande com : nouvelle.TabCommande) { 
			  dtalccom.put(com.toJson());
		  } 
		  jsonObject.put("Commandes", dtalccom);
		  
		  JSONArray dtalcprod = new JSONArray(); 
		  for (Produit prod : nouvelle.TabProduit) {  
			  dtalcprod.put(prod.toJson());
		  } 
		  jsonObject.put("Produits", dtalcprod);
		 	
		  return jsonObject;
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
	
		
}
