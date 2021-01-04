package projetJava;


import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import org.json.JSONArray;
import org.json.JSONObject;

import projetJava.PageClientControl.Client;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import videotheque.Commande;
import videotheque.LigneCommande;
import videotheque.Videotheque;

public class PageCommandeControl implements Initializable {
	
	@FXML private Button BtnAjouterCommande;
	@FXML private Button BtnSupprimerCommande;
	@FXML private Button BtnAcceuil;
	@FXML private Button BtnAjouterligneCommande;
	
	@FXML public TableView<Commande> TableViewCommande;
	@FXML public TableColumn<Commande, String> TableViewCommandeIdClient;
	@FXML public TableColumn<Commande, String> TableViewCommandeIdCom;
	@FXML public TableColumn<Commande, Date> TableViewCommandeDateCrea;
	@FXML public TableColumn<Commande, Date> TableViewCommandeDateFin;
	@FXML public TableColumn<Commande, Float> TableViewCommandeReduc;
	@FXML public TableColumn<Commande, String> TableViewCommandeProduit;
	
	DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	
	Videotheque nouvelle = new Videotheque();
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle ProjetJava) {
		initCol();
		loadDate();		
	}
	
	public class Commande {
		private SimpleStringProperty idclient;
		private SimpleStringProperty idcom; 
		private SimpleStringProperty datecrea;
		private SimpleStringProperty datefin;
		private SimpleFloatProperty reduc;	
		private SimpleStringProperty produit;
		
		Commande(String idclient, String idcom, String datecrea, String datefin, float reduc, String produit) {
			this.idclient = new SimpleStringProperty(idclient);
			this.idcom = new SimpleStringProperty(idcom);
			this.datecrea = new SimpleStringProperty(datecrea);
			this.datefin = new SimpleStringProperty(datefin);
			this.reduc = new SimpleFloatProperty(reduc);
			this.produit = new SimpleStringProperty(produit);
		}

		public String getIdclient() {
			return this.idclient.get();
		}

		public String getIdcom() {
			return this.idcom.get();
		}

		public Float getReduc() {
			return this.reduc.get();
		}

		public String getDatecrea() {
			return this.datecrea.get();
		}
		
		public String getDatefin() {
			return this.datefin.get();
		}
		
		public String getProduit() {
			return this.produit.get();
		}

	}

	private void initCol() {
		TableViewCommandeIdClient.setCellValueFactory(new PropertyValueFactory<>("idclient"));
		TableViewCommandeIdCom.setCellValueFactory(new PropertyValueFactory<>("idcom"));
		TableViewCommandeDateCrea.setCellValueFactory(new PropertyValueFactory<>("datecrea"));
		TableViewCommandeDateFin.setCellValueFactory(new PropertyValueFactory<>("datefin"));
		TableViewCommandeReduc.setCellValueFactory(new PropertyValueFactory<>("reduc"));
		TableViewCommandeProduit.setCellValueFactory(new PropertyValueFactory<>("produit"));
		
	}

	@FXML protected void RetourMain(ActionEvent e) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("fxml/Main.fxml"));
		Scene scene = new Scene(root);
		Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.centerOnScreen();
		window.show();
	}

	@FXML protected void ClickOnSupprimerCmd(ActionEvent e) throws IOException {
		ObservableList<Commande> Allpeople, Ligneselect;
        Allpeople = TableViewCommande.getItems();
        Ligneselect = TableViewCommande.getSelectionModel().getSelectedItems();
        
        for(Commande commande : Ligneselect)
        {
            Allpeople.remove(commande);
            
            try {
				Videotheque.removeLigneCommande(commande.getIdcom(), dateFormat.parse(commande.getDatefin()), commande.getProduit());
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }
        
        write();
        loadDate();
}
	

	@FXML protected void ClickOnAjouterCmd(ActionEvent e) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("fxml/AddCommande.fxml"));
		Scene scene = new Scene(root);
		Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.centerOnScreen();
		window.show();
	}

	private void loadDate() {
		ObservableList<Commande> list = FXCollections.observableArrayList();
		
		//Actualisation des réductions des clients en fonctions des commandes effectuées
		for (videotheque.Client cl : nouvelle.TabClient) {
			int cpcom = 0;
			for (videotheque.Commande com : nouvelle.TabCommande) {
				if (cl.getIdClient().equals(com.getIdClient())) {
					cpcom += 1;
				}
			}
			if (cpcom >= 5) {
				cl.setReduc(10);
			}
			else {
				cl.setReduc(0);
			}
		}
		write();
		
		for (videotheque.Commande com : nouvelle.TabCommande) {
			String idClient = com.getIdClient();
			String idCom = com.getIdCom();
			
			Date dateCrea = com.getDatecrea();
			String datedeb = dateFormat.format(dateCrea);
			
			double reduc = 0;
			
			//une fois que les réductions des clients sont à jour, il faut mettre à jour les réductions des commandes
			for (videotheque.Client cl : nouvelle.TabClient) {
				if (cl.getIdClient().equals(idClient)) {
					if (com.getTabLigneCommande().size() >= 5) {
						reduc = (1.0 - (1.0- cl.getReduc()/100.0 ) * ( 1.0 - (10.0/100.0) ) )*100.0;
					}
					else {
						reduc = (1.0 - (1.0- cl.getReduc()/100.0 ) ) *100.0;
					}
				}					
			}			
			com.setReduc((float)reduc);		
			write();
			
			ArrayList<videotheque.LigneCommande> TabLigneCommande = com.getTabLigneCommande();
			
			for (videotheque.LigneCommande lc : TabLigneCommande) {
				String idprod = lc.getProduit();
				Date datearrive = lc.getDate_fin();
				String datefin = dateFormat.format(datearrive);
				
				list.add(new Commande(idClient, idCom, datedeb, datefin, (float)reduc, idprod));
			}
		}
		
		TableViewCommande.getItems().setAll(list);
	}
	  
    @FXML protected void ClickOnAjouterLigneCmd(ActionEvent e) throws IOException {
    		Parent root = FXMLLoader.load(getClass().getResource("fxml/AddLigneCommande.fxml"));
    		Scene scene = new Scene(root);
    		Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
    		window.setScene(scene);
    		window.centerOnScreen();
    		window.show(); 		
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
    
    public JSONObject toJson() {
		  JSONObject jsonObject = new JSONObject();

		  JSONArray dtalcclient = new JSONArray();
		  for (videotheque.Client cl : nouvelle.TabClient) {
			  dtalcclient.put(cl.toJson());
		  }
		  jsonObject.put("Clients", dtalcclient);
		
		
		  JSONArray dtalccom = new JSONArray(); 
		  for (videotheque.Commande com : nouvelle.TabCommande) { 
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
}
