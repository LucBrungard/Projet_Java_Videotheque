package projetJava;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
