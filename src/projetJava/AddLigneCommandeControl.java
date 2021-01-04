package projetJava;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import org.json.JSONArray;
import org.json.JSONObject;

import produit.Produit;
import projetJava.PageProduitControl.BD;
import projetJava.PageProduitControl.CD;
import projetJava.PageProduitControl.DVD;
import projetJava.PageProduitControl.Dictionnaire;
import projetJava.PageProduitControl.Livre;
import projetJava.PageProduitControl.ManuelScolaire;
import projetJava.PageProduitControl.Roman;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import videotheque.Client;
import videotheque.Commande;
import videotheque.LigneCommande;
import videotheque.Videotheque;

public class AddLigneCommandeControl implements Initializable {
	@FXML private ComboBox<String> commandecombobox;
	@FXML private ComboBox<String> clientcombobox;
	@FXML private ComboBox<String> produitcombobox;
	@FXML private DatePicker textdatedebutcommande;
	@FXML private DatePicker textdatefincommande;
	@FXML private Button btnvalidercommande;
	@FXML private Button btnannulercommande;
	@FXML private Button btnmodifiercommande;
	@FXML private DatePicker txtdatefin;
	
	ObservableList<String> produit = FXCollections.observableArrayList();
	ObservableList<String> commande = FXCollections.observableArrayList();
	
	Videotheque nouvelle = new Videotheque();
	DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	
	@Override
	public void initialize(URL location, ResourceBundle ProjetJava) {						
		loadDate();	
	}
	
	public void initialize() {	
		produitcombobox.setItems(produit);
		commandecombobox.setItems(commande);
	}
	
	@FXML protected void Annuler(ActionEvent e) throws IOException {
		Parent test = FXMLLoader.load(getClass().getResource("/projetJava/fxml/PageCommande.fxml"));
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
		  for (produit.Produit prod : nouvelle.TabProduit) {  
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
	  
    private void loadDate() {
		for (produit.Produit pr : nouvelle.TabProduit) {	
			String idprod = pr.getId();
			String titre = pr.getTitre();
			float tarifj = pr.getTarif_journalier();
			String nature = pr.getNat();
			
			produit.add(idprod + " | " + nature + " | " + titre + " | " + tarifj);
		}
		
		for (videotheque.Commande com : nouvelle.TabCommande) {	
			String idCom = com.getIdCom();		
			commande.add(idCom);
		}		
	}

    
    @FXML private void ajouterlignecommande(ActionEvent e) throws IOException {
    	   	
    	if(commandecombobox.getValue() == null || produitcombobox.getValue() == null || textdatefincommande.getValue() == null)
    	{
    	}
    	else
    	{        	
        	ZoneId defaultZoneId = ZoneId.systemDefault();
        	
        	String idCom = commandecombobox.getValue();
        	String idProd = produitcombobox.getValue();
        	LocalDate datecrea = textdatedebutcommande.getValue();
        	LocalDate datefin = textdatefincommande.getValue();
        	Date dateCrea = Date.from(datecrea.atStartOfDay(defaultZoneId).toInstant());
        	Date dateFin = Date.from(datefin.atStartOfDay(defaultZoneId).toInstant());
           	
        	double reduc =0;
        	
    	for (Commande com : nouvelle.TabCommande) {
    		reduc = 0;
    		
    		
    		if (idCom.equals(com.getIdCom())) {
    			com.getTabLigneCommande().add(new LigneCommande(dateCrea, dateFin, idProd));
    		}
    		
    		for (videotheque.Client cl : nouvelle.TabClient) {
				if (cl.getIdClient().equals(com.getIdClient())) {
					if (com.getTabLigneCommande().size() >= 5) {
						reduc = (1.0 - (1.0-cl.getReduc()) * ( 1.0 - (10.0/100.0) ) )*100.0;
					}
					else {
						reduc = (1 - ( 1-cl.getReduc()) ) *100;
					}
					com.setReduc((float)reduc);
				}					
			}  		
    	}     	
    	write();
    	Annuler(e);
    }
    }
	

	@FXML private void ChoixCommande() {
		String idCom = commandecombobox.getValue();
		for (Commande com: nouvelle.TabCommande) {
			if (com.getIdCom().equals(idCom)) {
				clientcombobox.setValue(com.getIdClient());
				
				Date date = com.getDatecrea();
				LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				
				textdatedebutcommande.setValue(localDate);
			}
		}
	}
}
