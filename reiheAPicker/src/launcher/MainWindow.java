package launcher;

import java.io.IOException;
import java.util.Optional;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
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
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import logic_reiheAPicker.GetButtonPics;
import logic_vivi.ConvenienceTools;
import userInterface_reiheAPicker.ISBNCheckerTab;
import userInterface_reiheAPicker.ImportSettingsTab;
import userInterface_reiheAPicker.PickerTab;
import userInterface_vivi.ImpressumTab;
import userInterface_vivi.MultiApiCallTab;
import userInterface_vivi.PropertiesTab;
import userInterface_vivi.SingleApiCallTab;

public class MainWindow extends Application{
	
	private Scene reiheAPickerScene = null;
	private Scene isbnCheckerScene = null;
	private Scene viviScene = null;
	private Scene toolChooserScene = null;
	
		public static void startWindow(String[] args)
		{
			launch(args);
		}
	
	   @Override
	    public void start(Stage primaryStage) throws IOException {
	        primaryStage.setTitle(var_reiheAPicker.copy.Constants.ProgramName);
	        	       
	        Image tooltipIcon = GetButtonPics.getButtonImage_TooltipIcon();
	        if (tooltipIcon != null)
	        {  	
	        	primaryStage.getIcons().add(tooltipIcon);
	        }		        
	        
	       
	        initiateToolChooser(primaryStage);
	        
	    }  
	   
	   private void initiateToolChooser(Stage primaryStage)
	   {
		   if (this.toolChooserScene == null)
		   {
			   Screen screen = Screen.getPrimary();
			   Rectangle2D bounds = screen.getVisualBounds();

			   primaryStage.setMaximized(true);
			   primaryStage.setX(bounds.getMinX());
			   primaryStage.setY(bounds.getMinY());
			   primaryStage.setWidth(bounds.getWidth());
			   primaryStage.setHeight(bounds.getHeight());
			   
			   
			   
			   GridPane gridpane = new GridPane();
			   Button reiheAButton = new Button("Reihe-A Picker (DNB-Neuerscheinungen als CSV/TSV Datei einlesen und bearbeiten)");
			   Button isbnCheckerButton = new Button("ISBN-Bestandsprüfer (ISBNs aus beliebigen Texten filtern und Bestandsprüfung durchführen)");
			   Button viviButton = new Button("Vivi (ISSNs über Sherpa/Romeo um lizenzrechtliche Informationen prüfen und Publikationslisten anreichern)");
			   Button snakeButton = new Button("Snake (Reduziert Ladezeiten der anderen Module; SRC: https://github.com/04fara/snake/blob/master/src/Snake.java [MIT License]) ");
			   
			   
			  
			   
			   reiheAButton.setPadding(new Insets(15,15,15,15));
			   isbnCheckerButton.setPadding(new Insets(15,15,15,15));
			   viviButton.setPadding(new Insets(15,15,15,15));
			   snakeButton.setPadding(new Insets(15,15,15,15));
			  
			   reiheAButton.setAlignment(Pos.BASELINE_LEFT);
			   isbnCheckerButton.setAlignment(Pos.BASELINE_LEFT);
			   viviButton.setAlignment(Pos.BASELINE_LEFT);		 
			   snakeButton.setAlignment(Pos.BASELINE_LEFT);		 
			   primaryStage.widthProperty().addListener((observable, oldValue, newValue) -> {
					if (newValue != oldValue)
					{
						double maxButtonSize = 0;
						maxButtonSize = reiheAButton.getWidth();
						maxButtonSize = isbnCheckerButton.getWidth() > maxButtonSize ? isbnCheckerButton.getWidth() : maxButtonSize;
						maxButtonSize = viviButton.getWidth() > maxButtonSize ? viviButton.getWidth() : maxButtonSize;
						maxButtonSize = snakeButton.getWidth() > maxButtonSize ? snakeButton.getWidth() : maxButtonSize;
						reiheAButton.setMinWidth(maxButtonSize);
						isbnCheckerButton.setMinWidth(maxButtonSize);
						viviButton.setMinWidth(maxButtonSize);
						snakeButton.setMinWidth(maxButtonSize);
					}	
					
				});
			   
			   Image buttonImage = GetButtonPics.getButtonImage_reiheAPicker();
		        if (buttonImage != null)
		        {  	
		        	reiheAButton.setGraphic(GetButtonPics.turnPicIntoImageView(buttonImage));
		        }	
		        buttonImage = GetButtonPics.getButtonImage_snakeButton();
		        if (buttonImage != null)
		        {  	
		        	snakeButton.setGraphic(GetButtonPics.turnPicIntoImageView(buttonImage));
		        }
		       buttonImage = GetButtonPics.getButtonImage_isbnChecker();
		        if (buttonImage != null)
		        {  	
		        	isbnCheckerButton.setGraphic(GetButtonPics.turnPicIntoImageView(buttonImage));
		        }	
		        buttonImage = GetButtonPics.getButtonImage_viviButton();
		        if (buttonImage != null)
		        {  	
		        	viviButton.setGraphic(GetButtonPics.turnPicIntoImageView(buttonImage));
		        }
		        
		  
		       
		       HBox hBox1 = new HBox();
		       HBox hBox2 = new HBox();
		       HBox hBox3 = new HBox();
		       
		       hBox1.setAlignment(Pos.BASELINE_CENTER);
		       hBox2.setAlignment(Pos.BASELINE_CENTER);
		       hBox3.setAlignment(Pos.BASELINE_CENTER);
		  
		      
		       /*
		       hBox1 .getChildren().addAll(region1, reiheAButton, region2);
		       hBox2 .getChildren().addAll(region3, isbnCheckerButton, region4);
		       hBox3.getChildren().addAll(region5, viviButton, region6);
		       */   
		       		       
		       hBox1 .getChildren().addAll( reiheAButton );
		       hBox2 .getChildren().addAll( isbnCheckerButton);
		       hBox3.getChildren().addAll( viviButton );
		      
		        
		       gridpane.add(hBox1,  0, 0);
		       gridpane.add(hBox2,  0, 1);
		       gridpane.add(hBox3,  0, 2);
		       gridpane.setPadding(new Insets(15,15,15,15));
		       GridPane.setMargin(hBox1, new Insets(15, 15, 15, 15));
		       GridPane.setMargin(hBox2, new Insets(15, 15, 15, 15));
		       GridPane.setMargin(hBox3, new Insets(15, 15, 15, 15));
		       
		       viviButton.setOnAction(new EventHandler<ActionEvent>() {
		            @Override public void handle(ActionEvent e) {
		            	 try {
							initiateVivi(primaryStage);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
		            }
		        });  
		        
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
		        
		      	        
		        
		        primaryStage.addEventFilter(KeyEvent.KEY_RELEASED, keyEvent -> {		        	
		        	
		        	if(keyEvent.getCode() == KeyCode.ESCAPE)
		        	{
		        		initiateToolChooser(primaryStage);
		        	}		
		        		        	
				 	
		        });	
		        
		   
		        	
		        	
		
		       
		       this.toolChooserScene = new Scene(new VBox(gridpane));
		      
		   } 
		 primaryStage.setScene(this.toolChooserScene);   
	       
	        primaryStage.show();	        
		   
	   }
	   
	   
	   private void initiateVivi(Stage primaryStage) throws IOException
	   {
		   	
		 if (this.viviScene == null)
		 {
		
			TabPane tabPaneMenu;
			TabPane tabPaneApiCalls;
			SingleApiCallTab singleApiCallTab;	
			MultiApiCallTab multiApiCallTab;
			PropertiesTab propertiesTab;
			ImpressumTab impressumTab;
			Tab tabSingleApiCallTab;
			Tab tabMultiApiCallTab;
			Tab tabPropertiesTab;
			Tab tabImpressumTab;
			Tab tabApiCallsTab;	
			
			
			String path_question_icon = "res/question.png";
			String path_options_icon = "res/admin_variante.png";
			String path_magnif_glass_icon = "res/lupe.png";
					
					tabPaneMenu = new TabPane();
					tabPaneApiCalls = new TabPane();
							
					//create Tabs
					tabSingleApiCallTab = new Tab("ISSN/Titel abfragen");
					tabMultiApiCallTab = new Tab("Publikationsliste anreichern");
					tabPropertiesTab = new Tab("Optionen");
					tabImpressumTab = new Tab("?");
					tabApiCallsTab = new Tab("Tools");
					tabSingleApiCallTab.setClosable(false);
					tabMultiApiCallTab.setClosable(false);
					tabPropertiesTab.setClosable(false);
					tabImpressumTab.setClosable(false);
					tabApiCallsTab.setClosable(false);
				
					
					//put Tabs into TabPane
					tabPaneMenu.getTabs().add(tabApiCallsTab);
					tabPaneMenu.getTabs().add(tabPropertiesTab);
					tabPaneMenu.getTabs().add(tabImpressumTab);			
					tabPaneApiCalls.getTabs().add(tabSingleApiCallTab);
					tabPaneApiCalls.getTabs().add(tabMultiApiCallTab);
					
					//create VBox for scene, contains tabPane
					VBox vBoxMenu = new VBox(tabPaneMenu);
					VBox vBoxApiCalls = new VBox(tabPaneApiCalls);
					primaryStage.setResizable(false);	
					this.viviScene = new Scene(vBoxMenu,950,650);
					
				
						
					
					//set minilogo	
					//get pictures for menu	
					Image imageQuestionmark = ConvenienceTools.getPictureFromResources(path_question_icon);
					Image imageOptions = ConvenienceTools.getPictureFromResources(path_options_icon);
					Image imageMagnif_glass = ConvenienceTools.getPictureFromResources(path_magnif_glass_icon);
					
					if (imageQuestionmark != null && imageOptions != null && imageMagnif_glass != null)
					{
						tabPaneMenu.tabMinHeightProperty().set(35);
						tabPaneMenu.tabMinWidthProperty().set(35);
						
						
						//tabPropertiesTab.setGraphic();;
						tabImpressumTab.setGraphic(new ImageView(imageQuestionmark));
						tabImpressumTab.setText("");
						tabPropertiesTab.setGraphic(new ImageView(imageOptions));
						tabPropertiesTab.setText("");
						tabApiCallsTab.setGraphic(new ImageView(imageMagnif_glass));
						tabApiCallsTab.setText("");
						
						//tabApiCallsTab.
					}
					
					
					
					//Put different tabs into tab "tools"			
					
					
					
					//Create different Tabs: A bit confusing - the following classes are no tabs technically speaking,
					//however, they do contain all the elements desired.
					//their "Pane"-properties will be added into the tabs later;
					singleApiCallTab = new SingleApiCallTab();			
					multiApiCallTab = new MultiApiCallTab(primaryStage);
					propertiesTab = new PropertiesTab();
					impressumTab = new ImpressumTab();		
					
					//Add Panes to Tabs: 
					tabApiCallsTab.setContent(vBoxApiCalls);
					tabSingleApiCallTab.setContent(singleApiCallTab.singleApiCallpane);
					tabMultiApiCallTab.setContent(multiApiCallTab.multiApiCallpane);
					tabPropertiesTab.setContent(propertiesTab.propertiesPane);		
					tabImpressumTab.setContent(impressumTab.impressumPane);		
					
		 }
		
		primaryStage.setScene(this.viviScene);
			
	   }
	   
	   private void initiateISBNChecker(Stage primaryStage)
	   {
		   if (this.isbnCheckerScene == null)
		   {
	        ISBNCheckerTab isbnChecker = new ISBNCheckerTab();	        
	        TabPane mainTabPane = new TabPane();	         
	        mainTabPane.getTabs().add(isbnChecker.isbnCheckerTab);

	        
	        VBox vBoxMenu = new VBox(mainTabPane);
	        this.isbnCheckerScene = new Scene(vBoxMenu);
	        
		   } 
		   primaryStage.setScene(this.isbnCheckerScene);
	        	        
	        
	   }
	   
	   private void initiateReiheAPicker(Stage primaryStage) throws IOException
	   {
		  if (this.reiheAPickerScene == null)
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
		       this.reiheAPickerScene = new Scene(vBoxMenu);
		        
		  }
		  primaryStage.setScene(this.reiheAPickerScene);
	
	   }
	
	   
	   public void setPickerTabActive(TabPane mainTabPane, PickerTab pickerTab)
	   {
		   mainTabPane.getSelectionModel().select(mainTabPane.getTabs().indexOf(pickerTab.pickerTab));
	   }
	
}
