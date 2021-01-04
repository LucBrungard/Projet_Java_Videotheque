package projetJava;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import videotheque.Videotheque;

public class Main extends Application {
	
	@Override
	public void start(Stage PremiereFenetre) throws Exception {
		
		Parent root = FXMLLoader.load(getClass().getResource("/projetJava/fxml/Main.fxml"));
		Scene scene = new Scene(root);
		PremiereFenetre.setScene(scene);
		PremiereFenetre.setResizable(false);
		PremiereFenetre.centerOnScreen();
		PremiereFenetre.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
