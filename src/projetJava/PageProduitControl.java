package projetJava;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import produit.BD;
import produit.CD;
import produit.DVD;
import produit.Dictionnaire;
import produit.Livre;
import produit.ManuelScolaire;
import produit.Produit;
import produit.Roman;
import projetJava.PageClientControl.Client;
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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import videotheque.Videotheque;

public class PageProduitControl implements Initializable {
	
	@FXML private Button BtnAjouterProduit;
	@FXML private Button BtnSupprimerProduit;
	@FXML private Button BtnAcceuil;

	@FXML private TableView<Produit> TableViewProduit;
	@FXML private TableColumn<Produit, String> TableViewProduitNature;
	@FXML private TableColumn<Produit, String> TableViewProduitId;
	@FXML private TableColumn<Produit, String> TableViewProduitTitre;
	@FXML private TableColumn<Produit, Float> TableViewProduitTarifj;

	ObservableList<Produit> list = FXCollections.observableArrayList();
	
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
		ObservableList<Produit> Allpeople, Ligneselect;
        Allpeople = TableViewProduit.getItems();
        Ligneselect = TableViewProduit.getSelectionModel().getSelectedItems();
        
        for(Produit prod : Ligneselect)
        {
            Allpeople.remove(prod);
            Videotheque.removeProduit(prod.getId());
        }     
        
	}

	@FXML protected void ClickOnAjouter(ActionEvent e) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/projetJava/fxml/AddProduit.fxml"));
		Scene scene = new Scene(root);
		Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.centerOnScreen();
		window.show();
	}

	private void loadDate() {
		Videotheque nouvelle = new Videotheque();
		
		
		for (produit.Produit pr : nouvelle.TabProduit) {
			
			String id = pr.getId();
			String titre = pr.getTitre();
			float tarifj = pr.getTarif_journalier();
			String nature = pr.getNat();
			
			if (pr instanceof produit.Livre) {
				
				if (pr instanceof produit.BD) {					
					String auteur = ((produit.BD) pr).getAuteur();
					list.add(new BD(nature, id, titre, tarifj, auteur));
				}
				else if (pr instanceof produit.ManuelScolaire) {
					String auteur = ((produit.ManuelScolaire) pr).getAuteur();
					list.add(new ManuelScolaire(nature, id, titre, tarifj, auteur));
				}
				else if (pr instanceof produit.Roman) {
					String auteur = ((produit.Roman) pr).getAuteur();
					list.add(new Roman(nature, id, titre, tarifj, auteur));
				}
				else {							
					String auteur = ((produit.Livre) pr).getAuteur();
					list.add(new Livre(nature, id, titre, tarifj, auteur));			
				}
			}
			
			
			if (pr instanceof produit.Dictionnaire) {
				String langue = ((produit.Dictionnaire) pr).getLangue();
				list.add(new Dictionnaire(nature, id, titre, tarifj, langue));
			}
			if (pr instanceof produit.CD){
				String annee = ((produit.CD) pr).getAnnee();
				list.add(new CD(nature, id, titre, tarifj, annee));
			}
			if (pr instanceof produit.DVD) {
				String realisateur = ((produit.DVD) pr).getRealisateur();
				list.add(new DVD(nature, id, titre, tarifj, realisateur));
			}
			
			
		}
		
		TableViewProduit.getItems().setAll(list);
	}
	
	private void initCol() {
		TableViewProduitNature.setCellValueFactory(new PropertyValueFactory<>("nature"));
		TableViewProduitId.setCellValueFactory(new PropertyValueFactory<>("id"));
		TableViewProduitTitre.setCellValueFactory(new PropertyValueFactory<>("titre"));
		TableViewProduitTarifj.setCellValueFactory(new PropertyValueFactory<>("tarifjournalier"));
	}
	
	
	public class Produit { 
		  public SimpleStringProperty nature;
		  public SimpleStringProperty id;
		  public SimpleStringProperty titre; 
		  public SimpleFloatProperty tarifjournalier;
	  
		  Produit(String nature, String id, String titre, float tarifj) { 
			  this.nature = new SimpleStringProperty(nature);
			  this.id = new SimpleStringProperty(id); 
			  this.titre = new SimpleStringProperty(titre);
			  this.tarifjournalier = new SimpleFloatProperty(tarifj);
		    }
		  
		  public String getNature() {
			  return this.nature.get();
		  }
	  
		  public String getId() {
			  return this.id.get();
			}
		  
		  public String getTitre() {
			  return this.titre.get();
			}
		  
		  public Float getTarifjournalier() {
			  return this.tarifjournalier.get();
		  	}   
	}
	
		public class Livre extends Produit{ 
			  private final SimpleStringProperty auteur;
		  
		  Livre(String nature, String id, String titre, float Tarifjournalier, String auteur) { 
			  super(nature, id, titre, Tarifjournalier);
			  this.auteur = new SimpleStringProperty(auteur);
		  }
		 		    
		}
		  
		public class BD extends Produit{ 
			  private final SimpleStringProperty auteur;
		  
		  BD(String nature, String id, String titre, float Tarifjournalier, String auteur) { 
			  super(nature, id, titre, Tarifjournalier);
			  this.auteur = new SimpleStringProperty(auteur);
		  }
		  
		}
		  
		public class ManuelScolaire extends Produit{ 
			  private final SimpleStringProperty auteur;
		  
		  ManuelScolaire(String nature, String id, String titre, float Tarifjournalier, String auteur) { 
			  super(nature, id, titre, Tarifjournalier);
			  this.auteur = new SimpleStringProperty(auteur);
		  }

		}
				  
		public class Roman extends Produit{ 
			  private final SimpleStringProperty auteur;
		  
		  Roman(String nature, String id, String titre, float Tarifjournalier, String auteur) { 
			  super(nature, id, titre, Tarifjournalier);
			  this.auteur = new SimpleStringProperty(auteur);
		  }
		}
				  
      public class Dictionnaire extends Produit{ 
	      private final SimpleStringProperty langue;
	      
	      Dictionnaire(String nature, String id, String titre, float Tarifjournalier, String langue) { 
	    	  super(nature, id, titre, Tarifjournalier);
			  this.langue = new SimpleStringProperty(langue);
		  }
	      
	}
						  
      public class CD extends Produit{ 
	      private final SimpleStringProperty annee;
	      
	      CD(String nature, String id, String titre, float Tarifjournalier, String annee) { 
	    	  super(nature, id, titre, Tarifjournalier);
			  this.annee = new SimpleStringProperty(annee);
		  }
	      
	}
							  
      public class DVD extends Produit{ 
	      private final SimpleStringProperty realisateur;
	      
	      DVD(String nature, String id, String titre, float Tarifjournalier, String realisateur) { 
	    	  super(nature, id, titre, Tarifjournalier);
			  this.realisateur = new SimpleStringProperty(realisateur);
		  }
	      
	}
	  
	  
}
