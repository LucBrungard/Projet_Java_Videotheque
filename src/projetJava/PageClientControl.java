package projetJava;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import org.json.JSONArray;
import org.json.JSONObject;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import videotheque.Videotheque;

public class PageClientControl implements Initializable {
	
	@FXML private Button BtnAjouterClient;
	@FXML private Button BtnSupprimerClient;
	@FXML private Button BtnAcceuil;	
	
	@FXML private TableView<Client> TableViewClient;
	@FXML private TableColumn<Client, String> TableViewClientId;
	@FXML private TableColumn<Client, String> TableViewClientNom;
	@FXML private TableColumn<Client, String> TableViewClientPrenom;
	@FXML private TableColumn<Client, Float> TableViewClientReduc;
	
	Videotheque nouvelle = new Videotheque();

	ObservableList<Client> list = FXCollections.observableArrayList();
	
	@Override
	public void initialize(URL location, ResourceBundle ProjetJava) {
		initCol();
		loadDate();
	}
	
	

	@FXML protected void RetourMain(ActionEvent e) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/projetJava/fxml/Main.fxml"));
		Scene scene = new Scene(root);
		Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.centerOnScreen();
		window.show();
	}

	@FXML protected void ClickOnSupprimer(ActionEvent e) throws IOException {
		
		
        ObservableList<Client> Allpeople, Ligneselect;
        Allpeople = TableViewClient.getItems();
        Ligneselect = TableViewClient.getSelectionModel().getSelectedItems();
        
        for(Client client : Ligneselect)
        {
            Allpeople.remove(client);
            System.out.println(client.getId());
            Videotheque.removeClient(client.getId());
        }     
        
}
        
      
        
        
    

	@FXML protected void ClickOnAjouter(ActionEvent e) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/projetJava/fxml/AddClient.fxml"));
		Scene scene = new Scene(root);
		Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.centerOnScreen();
		window.show();
	}

	private void loadDate() {
		
		for (videotheque.Client cl : nouvelle.TabClient) {
			String id = cl.getIdClient();
			String nom = cl.getNom();
			String prenom = cl.getPrenom();
			int compteurcommande = 0;
			float reduc = 0;
			
			for (videotheque.Commande com : nouvelle.TabCommande) {
				if (id.equals(com.getIdClient())) {
					compteurcommande += 1;
				}
			}
			
			if (compteurcommande >= 5) {
				reduc = 10;
			}
			else {
				reduc = 0;
			}
			cl.setReduc(reduc);
			write();
			
			list.add(new Client(id, nom, prenom, reduc));
		}
				
		TableViewClient.getItems().setAll(list);
	}


	private void initCol() {
		TableViewClientId.setCellValueFactory(new PropertyValueFactory<>("id"));
		TableViewClientNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
		TableViewClientPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
		TableViewClientReduc.setCellValueFactory(new PropertyValueFactory<>("reduc"));
	}
	
	  public class Client { 
		  private SimpleStringProperty id;
		  private SimpleStringProperty nom; 
		  private SimpleStringProperty prenom;
		  private SimpleFloatProperty reduc;
	  
	  Client(String id, String nom, String prenom, float reduc) { 
		  this.id = new SimpleStringProperty(id); 
		  this.nom = new SimpleStringProperty(nom);
		  this.prenom = new SimpleStringProperty(prenom);
		  this.reduc = new SimpleFloatProperty(reduc);
	  }
	    
	  public String getId() {
		  return this.id.get();
		  }
	  
	  public String getNom() {
		  return this.nom.get();
		  }
	  
	  public String getPrenom() {
		  return this.prenom.get();
		  }
	  
	  public Float getReduc() {
		  return this.reduc.get();
		  }
	 
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
