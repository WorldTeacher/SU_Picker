package userInterface;

import java.io.IOException;
import java.util.Optional;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import logic.GetButtonPics;

public class MainWindow extends Application{
	
	public int clickIntoBoxHintGiven = -1;
		
		public static void startWindow(String[] args)
		{
			launch(args);
		}
	
	   @Override
	    public void start(Stage primaryStage) throws IOException {
	        primaryStage.setTitle(var.Constants.ProgramName);
	        	       
	        Image tooltipIcon = GetButtonPics.getButtonImage_TooltipIcon();
	        if (tooltipIcon != null)
	        {  	
	        	primaryStage.getIcons().add(tooltipIcon);
	        }	
	        
	        
	       
	        initiateToolChooser(primaryStage);
	        
	    }  
	   
	   private void initiateToolChooser(Stage primaryStage)
	   {
		   
		   Screen screen = Screen.getPrimary();
		   Rectangle2D bounds = screen.getVisualBounds();

		   primaryStage.setMaximized(true);
		   primaryStage.setX(bounds.getMinX());
		   primaryStage.setY(bounds.getMinY());
		   primaryStage.setWidth(bounds.getWidth());
		   primaryStage.setHeight(bounds.getHeight());
		   
		   
		   GridPane gridpane = new GridPane();
		   Button reiheAButton = new Button("Reihe-A Picker");
		   Button isbnCheckerButton = new Button("ISBN-Checker");
		   reiheAButton.setPadding(new Insets(15,15,15,15));
		   isbnCheckerButton.setPadding(new Insets(15,15,15,15));
		  
		  
		  
		   
		   
		   Image buttonImage = GetButtonPics.getButtonImage_reiheAPicker();
	        if (buttonImage != null)
	        {  	
	        	reiheAButton.setGraphic(GetButtonPics.turnPicIntoImageView(buttonImage));
	        }	
	       buttonImage = GetButtonPics.getButtonImage_isbnChecker();
	        if (buttonImage != null)
	        {  	
	        	isbnCheckerButton.setGraphic(GetButtonPics.turnPicIntoImageView(buttonImage));
	        }	
	       gridpane.add(reiheAButton, 0, 0);
	       gridpane.add(isbnCheckerButton, 0, 1);
	       gridpane.setPadding(new Insets(15,15,15,15));
	       GridPane.setMargin(isbnCheckerButton, new Insets(15, 15, 15, 15));
	       GridPane.setMargin(reiheAButton, new Insets(15, 15, 15, 15));
	       
	       primaryStage.setScene(new Scene(new VBox(gridpane)));
	       
	        primaryStage.show();	
	        
	       
	        
	        
	        reiheAButton.setOnAction(new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {
	            	 try {
						initiateReiheAPicker(primaryStage);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	            }
	        });  
	        
	        isbnCheckerButton.setOnAction(new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {
	            	initiateISBNChecker(primaryStage);
	            }
	        });  
		   
	   }
	   
	   private void initiateISBNChecker(Stage primaryStage)
	   {
		 
	        ISBNCheckerTab isbnChecker = new ISBNCheckerTab();	        
	        TabPane mainTabPane = new TabPane();	         
	        mainTabPane.getTabs().add(isbnChecker.isbnCheckerTab);

	        
	        VBox vBoxMenu = new VBox(mainTabPane);
	        Scene mainScene = new Scene(vBoxMenu);
	        	        
	        primaryStage.setScene(mainScene);
	   }
	   
	   private void initiateReiheAPicker(Stage primaryStage) throws IOException
	   {
		  
		   Button importButton = new Button();
	        ProgressIndicator progressIndicator = new ProgressIndicator(0);
	        //progressIndicator.setVisible(false);
	        //importButton.setText("Import");
	        //importButton.setPrefSize(80, 80);
	        
	        Image importButtonImage = GetButtonPics.getButtonImage_importButton();
	        if (importButtonImage != null)
	        {  	
		    importButton.setGraphic(GetButtonPics.turnPicIntoImageView(importButtonImage));
	        }	 	        
	       
	        //Button tutorialButton = new Button();
	        //tutorialButton.setText("Tutorial"); 
	        //Button exportButton = new Button();
	        //exportButton.setText("Export"); 	   
	        
	        Region region = new Region();
			
			HBox.setHgrow(region, Priority.ALWAYS);
	        
	        HBox controlsHBox = new HBox(importButton, region, progressIndicator /*, exportButton, tutorialButton*/);	 
	        controlsHBox.setMinHeight(35);
	        HBox.setMargin(importButton, new Insets(5,5,5,5));
	        controlsHBox.setAlignment(Pos.CENTER_LEFT);
	        
	         
	        
	        PickerTab pickerTab = new PickerTab(primaryStage);
	        
	        TabPane mainTabPane = new TabPane();	        
	        ImportSettingsTab importSettingsTab = new ImportSettingsTab();	 
	        progressIndicator.setVisible(false);
	        
	        
	        
	        importButton.setOnAction(new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {
	                try {
	                	
	                	
	                	if (mainTabPane.getTabs().contains(pickerTab.pickerTab))
	                	{
	                		Alert alert = 
	                		        new Alert(AlertType.WARNING, 
	                		            "Daten erneut importieren? Aktueller Fortschritt geht verloren!",
	                		             ButtonType.OK, 
	                		             ButtonType.CANCEL);
	                		alert.setTitle("Aktuellen Fortschritt überschreiben?");
	                		Optional<ButtonType> result = alert.showAndWait();

	                		if (result.get() == ButtonType.OK) {
	                			mainTabPane.getTabs().remove(pickerTab.pickerTab);
	                			importSettingsTab.openFileAndLoad();
	    						pickerTab.rebuild(importSettingsTab.titles, progressIndicator);
	    						mainTabPane.getTabs().add(pickerTab.pickerTab);
	    						mainTabPane.getSelectionModel().select(mainTabPane.getTabs().indexOf(pickerTab.pickerTab));
	                		}
	                	}   
	                	else
	                	{
	                		importSettingsTab.openFileAndLoad();
							pickerTab.rebuild(importSettingsTab.titles, progressIndicator);
							mainTabPane.getTabs().add(pickerTab.pickerTab);	
							 setPickerTabActive(mainTabPane, pickerTab);							
	                	}
						
						
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	            }
	        });  	        
	        
	        mainTabPane.getTabs().add(importSettingsTab.importSettingsTab);
	        
	        VBox vBoxMenu = new VBox(controlsHBox, mainTabPane);    
	      
	        
	        
	        Scene mainScene = new Scene(vBoxMenu);
	        
	        
	        
	        primaryStage.setScene(mainScene);
	      
	        	        
	      
		   
		   primaryStage.addEventFilter(KeyEvent.KEY_RELEASED, keyEvent -> {
			   if (this.clickIntoBoxHintGiven == -1 &&				
					   pickerTab.buildStage == 1 &&
					   !pickerTab.currentspicks.isFocused() &&
					   !pickerTab.currentsqueue.isFocused() &&
					   !pickerTab.currentsnopes.isFocused() 
					   )
			   {				   
				   
				   if (keyEvent.getCode() == KeyCode.LEFT
						   || keyEvent.getCode() == KeyCode.RIGHT
						   || keyEvent.getCode() == KeyCode.UP
						   || keyEvent.getCode() == KeyCode.DOWN
						   || keyEvent.getCode() == KeyCode.RIGHT
						   || keyEvent.getCode() == KeyCode.SPACE
					)

				 	{			
					    
					   setPickerTabActive(mainTabPane, pickerTab);
					   giveClickHint();	
				 	}	
			   }
		   });
	   }
	   
	   private void giveClickHint()
	   {
		   Alert alert = new Alert(AlertType.INFORMATION);
		   alert.initStyle(StageStyle.UTILITY);
		   alert.setTitle(null);
		   alert.setHeaderText(null);
		   alert.setContentText("Bitte klicken Sie in eines der Textfelder in der Titelauswahl um es zu aktivieren, ansonsten können Befehle nicht zugeordnet werden.\r\n"
		   		+ "Das derzeit aktive Textfeld erkennen Sie durch einen blauen Rahmen.");

		   alert.showAndWait();
		   this.clickIntoBoxHintGiven = 1;	
		   
	   }
	   
	   public void setPickerTabActive(TabPane mainTabPane, PickerTab pickerTab)
	   {
		   mainTabPane.getSelectionModel().select(mainTabPane.getTabs().indexOf(pickerTab.pickerTab));
	   }
	
}
