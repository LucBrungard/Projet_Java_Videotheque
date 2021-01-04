package projetJava;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

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
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class MainControl implements Initializable {

	@Override
	public void initialize(URL location, ResourceBundle ProjetJava) {		
	}

	@FXML private Button BtnClient;
	@FXML private Button BtnCommande;
	@FXML private Button BtnStock;
	@FXML private Button BtnProduit;

	@FXML protected void ClickOnClient(ActionEvent e) throws IOException {
		Parent Client = FXMLLoader.load(getClass().getResource("fxml/PageClient.fxml"));
		Scene Clientscene = new Scene(Client);
		Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
		window.setScene(Clientscene);
		window.centerOnScreen();
		window.setTitle("Client");
		window.show();
	}

	@FXML protected void ClickOnCommande(ActionEvent e) throws IOException{
		Parent Commande = FXMLLoader.load(getClass().getResource("fxml/PageCommande.fxml"));
		Scene Commandescene = new Scene(Commande);
		Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
		window.setScene(Commandescene);
		window.centerOnScreen();
		window.setTitle("Commande");
		window.show();
	}
	
	@FXML protected void ClickOnProduit(ActionEvent e) throws IOException {
		Parent Produit = FXMLLoader.load(getClass().getResource("fxml/PageProduit.fxml"));
		Scene Produitscene = new Scene(Produit);
		Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
		window.setScene(Produitscene);
		window.centerOnScreen();
		window.setTitle("Produit");
		window.show();
	}

	@FXML protected void ClickOnStock(ActionEvent e) throws IOException{
		Parent Stock = FXMLLoader.load(getClass().getResource("fxml/PageStock.fxml"));
		Scene Stockscene = new Scene(Stock);
		Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
		window.setScene(Stockscene);
		window.centerOnScreen();
		window.setTitle("Stock");
		window.show();
	}
}