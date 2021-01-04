package projetJava;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.json.JSONArray;
import org.json.JSONObject;

import produit.BD;
import produit.CD;
import produit.DVD;
import produit.Dictionnaire;
import produit.Livre;
import produit.ManuelScolaire;
import produit.Produit;
import produit.Roman;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import videotheque.Client;
import videotheque.Commande;
import videotheque.Videotheque;

public class AddProduitControl implements Initializable {
	
	@FXML private TextField txtproduittitre;
	@FXML private TextField txtproduittarifj;
	@FXML private TextField txtproduitauteur;
	@FXML private TextField txtproduitrealisateur;
	@FXML private TextField txtproduitlangue;
	@FXML private TextField txtproduitannee;
	@FXML private ComboBox<String> comboboxproduit;
	@FXML private Button validerbtnclient;
	@FXML private Button annulerbtnclient;
	
	ObservableList<String> produit = FXCollections.observableArrayList("BD","Roman","Manuel Scolaire","Livre","Dictionnaire","CD","DVD");

	Videotheque nouvelle = new Videotheque();
	
	@Override
	public void initialize(URL location, ResourceBundle ProjetJava) {		
	}

	@FXML public void initialize() {
		comboboxproduit.setItems(produit);
	}
	
	@FXML protected void ClickOnAjouter(ActionEvent e) throws IOException {
		
		String idprod = "pr"+creationId();
		String titreprod = txtproduittitre.getText();
		float tarifj = Float.parseFloat(txtproduittarifj.getText());	
		String nature = comboboxproduit.getValue();
		
		if (!txtproduittitre.getText().isEmpty() 
				&& !txtproduittarifj.getText().isEmpty() 
				&& !txtproduitauteur.getText().isEmpty())
		{			
			if (comboboxproduit.getValue().equals("BD"))
			{
				String auteur = txtproduitauteur.getText();
				BD bd = new BD(nature, idprod, titreprod, tarifj , auteur);
				
				nouvelle.TabProduit.add(bd);
			}
			else if (comboboxproduit.getValue().equals("Roman"))
			{
				String auteur = txtproduitauteur.getText();
				Roman roman = new Roman(nature, idprod, titreprod, tarifj , auteur);
				
				nouvelle.TabProduit.add(roman);
			}
			else if (comboboxproduit.getValue().equals("Manuel Scolaire"))
			{
				String auteur = txtproduitauteur.getText();
				ManuelScolaire manuelscolaire = new ManuelScolaire(nature, idprod, titreprod, tarifj, auteur);
				
				nouvelle.TabProduit.add(manuelscolaire);
			}
			else if (comboboxproduit.getValue().equals("Livre"))
			{
				String auteur = txtproduitauteur.getText();
				Livre livre = new Livre(nature, idprod, titreprod, tarifj, auteur);
				
				nouvelle.TabProduit.add(livre);
			}
			
			write();
			
			Annuler(e);
		
		}
		
		else if (!txtproduittarifj.getText().isEmpty() 
				&& !txtproduitlangue.getText().isEmpty())
		{
			if(comboboxproduit.getValue().equals("Dictionnaire"))
			{
				String langue = txtproduitlangue.getText();
				Dictionnaire dico = new Dictionnaire(nature, idprod, titreprod, tarifj, langue);
			
				nouvelle.TabProduit.add(dico);
			}
			
			write();
			
			Annuler(e);
		
		}
		
		else if (!txtproduittitre.getText().isEmpty() 
				&& !txtproduittarifj.getText().isEmpty() 
				&& !txtproduitannee.getText().isEmpty())
		{
			if(comboboxproduit.getValue().equals("CD"))
			{
				String annee = txtproduitannee.getText();
				CD cd = new CD(nature, idprod, titreprod, tarifj, annee);
			
				nouvelle.TabProduit.add(cd);
			}
			
			write();
			
			Annuler(e);
		
		}
		else if (!txtproduittitre.getText().isEmpty() 
				&& !txtproduittarifj.getText().isEmpty() 
				&& !txtproduitrealisateur.getText().isEmpty())
		{
			if(comboboxproduit.getValue().equals("DVD"))
			{
				String realisateur = txtproduitrealisateur.getText();
				DVD dvd = new DVD(nature, idprod, titreprod, tarifj , realisateur);
			
				nouvelle.TabProduit.add(dvd);
			}
			
			write();
			
			Annuler(e);
						
		}
		
	}
	
	
	public String creationId() {
		int i = 0;
		if (nouvelle.TabProduit.size() != 0) {
			String idprod = nouvelle.TabProduit.get(nouvelle.TabProduit.size()-1).getId(); 
			i = Integer.parseInt(idprod.substring(2, 5));
			i+=1;
		} 
		
		return String.format("%03d", i);				
	}

	@FXML protected void Annuler(ActionEvent e) throws IOException {
		Parent test = FXMLLoader.load(getClass().getResource("/projetJava/fxml/PageProduit.fxml"));
		Scene scene = new Scene(test);
		Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.centerOnScreen();
		window.show();
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


	@FXML private void ChoixNature(ActionEvent e) throws IOException {
		if (comboboxproduit.getValue().equals("BD") 
				|| comboboxproduit.getValue().equals("Roman") 
				|| comboboxproduit.getValue().equals("Manuel Scolaire") 
				|| comboboxproduit.getValue().equals("Livre"))
				{
			txtproduitauteur.setVisible(true);
			txtproduitlangue.setVisible(false);
			txtproduitannee.setVisible(false);
			txtproduitrealisateur.setVisible(false);
				}
		else if(comboboxproduit.getValue().equals("Dictionnaire"))
		{
			txtproduitlangue.setVisible(true);
			txtproduitauteur.setVisible(false);
			txtproduitannee.setVisible(false);
			txtproduitrealisateur.setVisible(false);
		}
		else if(comboboxproduit.getValue().equals("CD"))
		{
			txtproduitannee.setVisible(true);
			txtproduitauteur.setVisible(false);
			txtproduitlangue.setVisible(false);
			txtproduitrealisateur.setVisible(false);
		}
		else if(comboboxproduit.getValue().equals("DVD"))
		{
			txtproduitrealisateur.setVisible(true);
			txtproduitauteur.setVisible(false);
			txtproduitlangue.setVisible(false);
			txtproduitannee.setVisible(false);
		}
}
}
