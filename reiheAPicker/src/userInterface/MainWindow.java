package userInterface;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainWindow extends Application{	
	
		public static void startWindow(String[] args)
		{
			launch(args);
		}
	
	   @Override
	    public void start(Stage primaryStage) throws IOException {
	        primaryStage.setTitle(var.Constants.ProgramName);
	       
	        StackPane root = new StackPane();
	        
	        
	        
	        TabPane mainTabPane = new TabPane();	        
	        ImportSettingsTab importSettingsTab = new ImportSettingsTab();
	        PickerTab pickerTab = new PickerTab(importSettingsTab.titles);
	        HistoryTab historyTab = new HistoryTab();
	        ExportTab exportTab = new ExportTab();
	        
	        
	        mainTabPane.getTabs().add(importSettingsTab.importSettingsTab);
	        mainTabPane.getTabs().add(pickerTab.pickerTab);	
	        mainTabPane.getTabs().add(historyTab.historyTab);	
	        mainTabPane.getTabs().add(exportTab.exportTab);
	        
	        VBox vBoxMenu = new VBox(mainTabPane);
	        Scene mainScene = new Scene(vBoxMenu, 800, 600);
	        
	        primaryStage.setScene(mainScene);
	        primaryStage.setMaximized(true);
	        primaryStage.show();
	        
	    }
	   
	  
	   
	 



}
