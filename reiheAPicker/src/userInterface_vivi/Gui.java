package userInterface_vivi;
import javafx.stage.Stage;
import logic_vivi.ConvenienceTools;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/*
 */
public class Gui {
	
	Stage primaryStage;
	TabPane tabPaneMenu;
	TabPane tabPaneApiCalls;
	Scene scene;
	SingleApiCallTab singleApiCallTab;	
	MultiApiCallTab multiApiCallTab;
	PropertiesTab propertiesTab;
	ImpressumTab impressumTab;
	Tab tabSingleApiCallTab;
	Tab tabMultiApiCallTab;
	Tab tabPropertiesTab;
	Tab tabImpressumTab;
	Tab tabApiCallsTab;	
	
	String path_LogoSquarePath_icon = "res/logo_square.png";
	String path_question_icon = "res/question.png";
	String path_options_icon = "res/admin_variante.png";
	String path_magnif_glass_icon = "res/lupe.png";
	
	
	//creates initial pane and different scenes and everything, only most basic layout
	public Gui(Stage primaryStage)
	{		
		try {
			//create Tabpane: Contains different tabs
			
			this.tabPaneMenu = new TabPane();
			this.tabPaneApiCalls = new TabPane();
					
			//create Tabs
			this.tabSingleApiCallTab = new Tab("ISSN/Titel abfragen");
			this.tabMultiApiCallTab = new Tab("Publikationsliste anreichern");
			this.tabPropertiesTab = new Tab("Optionen");
			this.tabImpressumTab = new Tab("?");
			this.tabApiCallsTab = new Tab("Tools");
			this.tabSingleApiCallTab.setClosable(false);
			this.tabMultiApiCallTab.setClosable(false);
			this.tabPropertiesTab.setClosable(false);
			this.tabImpressumTab.setClosable(false);
			this.tabApiCallsTab.setClosable(false);
		
			
			//put Tabs into TabPane
			this.tabPaneMenu.getTabs().add(tabApiCallsTab);
			this.tabPaneMenu.getTabs().add(tabPropertiesTab);
			this.tabPaneMenu.getTabs().add(tabImpressumTab);			
			this.tabPaneApiCalls.getTabs().add(tabSingleApiCallTab);
			this.tabPaneApiCalls.getTabs().add(tabMultiApiCallTab);
			
			
			
			
			
			
			//create VBox for scene, contains tabPane
			VBox vBoxMenu = new VBox(this.tabPaneMenu);
			VBox vBoxApiCalls = new VBox(this.tabPaneApiCalls);
			primaryStage.setResizable(false);	
			this.scene = new Scene(vBoxMenu,950,650);
			
			this.primaryStage = primaryStage;
			this.primaryStage.setTitle("ViVi");	
			
			//set minilogo	
			//get pictures for menu	
			Image imageQuestionmark = ConvenienceTools.getPictureFromResources(path_question_icon);
			Image imageLogoSquare = ConvenienceTools.getPictureFromResources(path_LogoSquarePath_icon);
			Image imageOptions = ConvenienceTools.getPictureFromResources(path_options_icon);
			Image imageMagnif_glass = ConvenienceTools.getPictureFromResources(path_magnif_glass_icon);
			
			if (imageQuestionmark != null && imageOptions != null && imageMagnif_glass != null)
			{
				this.tabPaneMenu.tabMinHeightProperty().set(35);
				this.tabPaneMenu.tabMinWidthProperty().set(35);
				
				
				//this.tabPropertiesTab.setGraphic();;
				this.tabImpressumTab.setGraphic(new ImageView(imageQuestionmark));
				this.tabImpressumTab.setText("");
				this.tabPropertiesTab.setGraphic(new ImageView(imageOptions));
				this.tabPropertiesTab.setText("");
				this.tabApiCallsTab.setGraphic(new ImageView(imageMagnif_glass));
				this.tabApiCallsTab.setText("");
				
				//this.tabApiCallsTab.
			}
			
			if (imageLogoSquare != null)
			{				
				this.primaryStage.getIcons().add(imageLogoSquare);				
			} 
			
			//Put different tabs into tab "tools"			
			
			
			
			//Create different Tabs: A bit confusing - the following classes are no tabs technically speaking,
			//however, they do contain all the elements desired.
			//their "Pane"-properties will be added into the tabs later;
			this.singleApiCallTab = new SingleApiCallTab();			
			this.multiApiCallTab = new MultiApiCallTab(primaryStage);
			this.propertiesTab = new PropertiesTab();
			this.impressumTab = new ImpressumTab();		
			
			//Add Panes to Tabs: 
			this.tabApiCallsTab.setContent(vBoxApiCalls);
			this.tabSingleApiCallTab.setContent(singleApiCallTab.singleApiCallpane);
			this.tabMultiApiCallTab.setContent(multiApiCallTab.multiApiCallpane);
			this.tabPropertiesTab.setContent(propertiesTab.propertiesPane);		
			this.tabImpressumTab.setContent(impressumTab.impressumPane);		
			
			//do the magic!
			this.primaryStage.setScene(this.scene);
			this.primaryStage.show();
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}	
		
	}

}


