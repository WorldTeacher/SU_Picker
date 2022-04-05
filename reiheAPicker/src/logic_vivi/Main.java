package logic_vivi;
import userInterface_vivi.Gui;
import javafx.application.Application;
import javafx.stage.Stage;

//Engage!
public class Main extends Application {
	@Override
	public void start(Stage primaryStage)
	{		
		new Gui(primaryStage);		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
