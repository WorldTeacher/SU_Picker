package userInterface;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import logic.ImportExportFileHandler;
import logic.PropertyFileHandler;

public class MainWindow extends Application{	
	
		public static void startWindow(String[] args)
		{
			launch(args);
		}
	
	   @Override
	    public void start(Stage primaryStage) throws IOException {
	        primaryStage.setTitle(var.Constants.ProgramName);
	       
	     
	        
	              
	        Button importButton = new Button();
	        importButton.setText("Import");
	        Button tutorialButton = new Button();
	        tutorialButton.setText("Tutorial"); 
	        Button exportButton = new Button();
	        exportButton.setText("Export"); 	        
	        HBox controlsHBox = new HBox(importButton, exportButton, tutorialButton);	 
	        
	        
	        PickerTab pickerTab = new PickerTab();
	        
	        TabPane mainTabPane = new TabPane();	        
	        ImportSettingsTab importSettingsTab = new ImportSettingsTab();
	        
	        importButton.setOnAction(new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {
	                try {
	                	
						importSettingsTab.openFileAndLoad();
						pickerTab.rebuild(importSettingsTab.titles);
						mainTabPane.getTabs().add(pickerTab.pickerTab);	
						
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	            }
	        });       

	        
	       
	        HistoryTab historyTab = new HistoryTab();
	        ExportTab exportTab = new ExportTab();
	        
	        
	        mainTabPane.getTabs().add(importSettingsTab.importSettingsTab);
	        //mainTabPane.getTabs().add(pickerTab.pickerTab);	
	        //mainTabPane.getTabs().add(historyTab.historyTab);	
	        //mainTabPane.getTabs().add(exportTab.exportTab);
	        
	        VBox vBoxMenu = new VBox(controlsHBox, mainTabPane);    
	      
	        
	        
	        Scene mainScene = new Scene(vBoxMenu, 800, 600);
	        
	        
	        
	        primaryStage.setScene(mainScene);
	        primaryStage.setMaximized(true);
	        primaryStage.show();
	        
	        primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> {
			     // Do whatever you want
			});

		   primaryStage.heightProperty().addListener((obs, oldVal, newVal) -> {
			     // Do whatever you want
			});
	        
	    }   
	
}
