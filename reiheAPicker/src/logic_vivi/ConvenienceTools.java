package logic_vivi;

import java.io.FileInputStream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;


/*
 * functions that are usfule for several things or dont quite fit anywere else
 */
public class ConvenienceTools {	
	
	static String DialogImageString = "res/chat.png";
	static String DialogGraphicString = "res/lupe.png";
	
	//receives a String, turns it into proper column name and returns that
	//information taken from Web of Science
	public  static String wos_tagMatcher(String tag)
	{
		String match = tag;
		
		switch (tag) {
		
		case "FN":
			match = "File Name";
			break;
			case "VR":
			match = "Version Number";
			break;
			case "PT":
			match = "Publication Type";
			break;
			case "AU":
			match = "Authors";
			break;
			case "AF":
			match = "Author Full Name";
			break;
			case "BA":
			match = "Book Authors";
			break;
			case "BF":
			match = "Book Authors Full Name";
			break;
			case "CA":
			match = "Group Authors";
			break;
			case "GP":
			match = "Book Group Authors";
			break;
			case "BE":
			match = "Editors";
			break;
			case "TI":
			match = "Document Title";
			break;
			case "SO":
			match = "Publication Name";
			break;
			case "SE":
			match = "Book Series Title";
			break;
			case "BS":
			match = "Book Series Subtitle";
			break;
			case "LA":
			match = "Language";
			break;
			case "DT":
			match = "Document Type";
			break;
			case "CT":
			match = "Conference Title";
			break;
			case "CY":
			match = "Conference Date";
			break;
			case "CL":
			match = "Conference Location";
			break;
			case "SP":
			match = "Conference Sponsors";
			break;
			case "HO":
			match = "Conference Host";
			break;
			case "DE":
			match = "Author Keywords";
			break;
			case "ID":
			match = "Keywords Plus®";
			break;
			case "AB":
			match = "Abstract";
			break;
			case "C1":
			match = "Author Address";
			break;
			case "RP":
			match = "Reprint Address";
			break;
			case "EM":
			match = "E-mail Address";
			break;
			case "RI":
			match = "ResearcherID Number";
			break;
			case "OI":
			match = "ORCID Identifier";
			break;
			case "FU":
			match = "Funding Agency and Grant Number";
			break;
			case "FX":
			match = "Funding Text";
			break;
			case "CR":
			match = "Cited References";
			break;
			case "NR":
			match = "Cited Reference Count";
			break;
			case "TC":
			match = "Web of Science Core Collection Times Cited Count";
			break;
			case "Z9":
			match = "Total Times Cited Count";
			break;
			case "U1":
			match = "Usage Count (Last 180 Days)";
			break;
			case "U2":
			match = "Usage Count (Since 2013)";
			break;
			case "PU":
			match = "Publisher";
			break;
			case "PI":
			match = "Publisher City";
			break;
			case "PA":
			match = "Publisher Address";
			break;
			case "SN":
			match = "ISSN";
			break;
			case "EI":
			match = "eISSN";
			break;
			case "BN":
			match = "ISBN";
			break;
			case "J9":
			match = "29-Character Source Abbreviation";
			break;
			case "JI":
			match = "ISO Source Abbreviation";
			break;
			case "PD":
			match = "Publication Date";
			break;
			case "PY":
			match = "Year Published";
			break;
			case "VL":
			match = "Volume";
			break;
			case "IS":
			match = "Issue";
			break;
			case "SI":
			match = "Special Issue";
			break;
			case "PN":
			match = "Part Number";
			break;
			case "SU":
			match = "Supplement";
			break;
			case "MA":
			match = "Meeting Abstract";
			break;
			case "BP":
			match = "Beginning Page";
			break;
			case "EP":
			match = "Ending Page";
			break;
			case "AR":
			match = "Article Number";
			break;
			case "DI":
			match = "DOI";
			break;
			case "D2":
			match = "Book DOI";
			break;
			case "EA":
			match = "Early access date";
			break;
			case "EY":
			match = "Early access year";
			break;
			case "PG":
			match = "Page Count";
			break;
			case "P2":
			match = "Chapter Count (Book Citation Index)";
			break;
			case "WC":
			match = "Web of Science Categories";
			break;
			case "SC":
			match = "Research Areas";
			break;
			case "GA":
			match = "Document Delivery Number";
			break;
			case "PM":
			match = "PubMed ID";
			break;
			case "UT":
			match = "Accession Number";
			break;
			case "OA":
			match = "Open Access Indicator";
			break;
			case "HP":
			match = "ESI Hot Paper";
			break;
			case "HC":
			match = "ESI Highly Cited Paper";
			break;
			case "DA":
			match = "Date this report was generated.";
			break;
			case "ER":
			match = "End of Record";
			break;
			case "EF":
			match = "End of File";
			break;		
		}		
		
		return match;
	}
	
	//return predefined Columns from web of science to display in properties tab for the whitelisting option
	public static  ObservableList<String> getWoSColumnList()
	{
		return	FXCollections.observableArrayList(
				"Publication Type (PT)",   
				 "Author Full Name (AF)",      
				   "Editors (BE)",   "Document Title (TI)",   "Publication Name (SO)", 
				  "Language (LA)",   "Document Type (DT)", 
				  "Conference Title (CT)",   "Author Keywords (DE)",   "Keywords Plus® (ID)", 
				  "Abstract (AB)",   "Reprint Address (RP)",   "E-mail Address (EM)", 
				  "ORCID Identifier (OI)",  
				 "Publisher (PU)",    
				"ISSN (SN)",   "eISSN (EI)", 
				  "Web of Science Categories (WC)",   
				"Research Areas (SC)",  "Open Access Indicator (OA)");
	}
	
	
	//check if issn looks like an actual issn: needs to be at least 9 chars in length
	//may contain -, X or x
	//otherwise only contains digits
	public static  boolean isValidISSN(String issn)
	{
		if (issn.length() < 9)
		{
			return false;
		}
		
		for (int i = 0; i < issn.length(); i++){
		    char c = issn.charAt(i);        
		    if (!(Character.isDigit(c) || c == '-' || c == 'X' || c == 'x'))
		    {
		    	return false;
		    }
		}
		return true;
	}
	
		
		
		//creates a new window with info text
		public static void getHelpButtonEvent(Button button, String helpText)
		{		
			button.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {	
					ConvenienceTools.createInfoAlert(helpText);
				}
			});		
		}	
		
		//get picture from resources, icon, logos etc
		public static Image getPictureFromResources(String picturePath)
		{
			FileInputStream inputStream;
			Image image = null;		
			try
			{
				inputStream = new FileInputStream(picturePath);
				image = new Image(inputStream);			
			}
			catch (Exception e)
			{
				return null;	
			}
			
			return image;		
		}			
		
		
		//info alerts are only used for help/info buttons as of now
		public static  void createInfoAlert(String text)
		{
		
			//define basic alert:
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setGraphic(null);
			alert.setTitle(null);		
			alert.getDialogPane().setPrefWidth(600);
			
			//set icon
			// Get the Stage.
			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();

			// Add a custom icon.
			Image dialogImage = getPictureFromResources(DialogImageString);
			if (dialogImage != null)
			{
				stage.getIcons().add(dialogImage);
			}
			
			//get textParts (images and texts) and add them into a vbox		
			//images are designated as <img>path</img>
			//<br> need to be replaced, as well...
			VBox vBox = new VBox();	
			TextFlow textflow;
			ImageView imageView;
			Document doc=Jsoup.parse(text);
			Elements divs = doc.select("*");
			for(Element div : divs){
				if(div.tag().getName().toString() == "h1")
				{
					alert.setTitle(div.text());				
					
				}else if(div.tag().getName().toString() == "h2")
				{
					textflow = new TextFlow(new Text(System.lineSeparator() + div.text()));
					textflow.setStyle("-fx-font-weight: bold;");
					textflow.setStyle("-fx-font: 24 arial;");	
					
					vBox.getChildren().add(textflow);
				}else if(div.tag().getName().toString() == "h3")
				{
					textflow = new TextFlow(new Text(System.lineSeparator() + div.text()));
					
					textflow.setStyle("-fx-font-weight: bold;");
					textflow.setStyle("-fx-font: 22 arial;");
					
					vBox.getChildren().add(textflow);
				}else if(div.tag().getName().toString() == "h4")
				{
					textflow = new TextFlow(new Text(System.lineSeparator() + div.text()));
					textflow.setStyle("-fx-font-weight: bold;");
					textflow.setStyle("-fx-font: 20 arial;");
					
					vBox.getChildren().add(textflow);
				}else if(div.tag().getName().toString() == "h5")
				{
					textflow = new TextFlow(new Text(System.lineSeparator() + div.text()));
					textflow.setStyle("-fx-font-weight: bold;");
					textflow.setStyle("-fx-font: 18 arial;");
					
					vBox.getChildren().add(textflow);
				}else if(div.tag().getName().toString() == "h6")
				{
					textflow = new TextFlow(new Text(System.lineSeparator() + div.text()));
					textflow.setStyle("-fx-font-weight: bold;");
					textflow.setStyle("-fx-font: 16 arial;");
					
					vBox.getChildren().add(textflow);
				} else if (div.tag().getName().toString() == "p")
				{
					textflow = new TextFlow(new Text(div.text()));
					textflow.setStyle("-fx-font: 14 arial;");
					textflow.setTextAlignment(TextAlignment.JUSTIFY);
					vBox.getChildren().add(textflow);
				} else if (div.tag().getName().toString() == "img")
				{					
					imageView = new ImageView(getPictureFromResources(div.attr("src")));				
					vBox.getChildren().add(imageView);
				}
		        
		    }
			alert.getDialogPane().setContent(vBox);
			alert.showAndWait();
		}
		
		//turns a regular image into an imageview, which can then be displayed by javafx
		public static ImageView getImageViewForHelpTexts(Image helpImage)
		{			
			ImageView helpTextimgView = null;
			if (helpImage != null)
			{				
				helpTextimgView = new ImageView(helpImage);
				helpTextimgView.setFitWidth(15);
				helpTextimgView.setPreserveRatio(true);
				
			} 
			return helpTextimgView;
		}	
		
}
