package userInterface_reiheAPicker;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import logic_reiheAPicker.ApiCallerClass;
import logic_reiheAPicker.PropertyFileHandler;
import var_reiheAPicker.copy.Constants;

public class ISBNCheckerTab {
	
	public Tab isbnCheckerTab;
	private Label inputLabel;
	private Label outputLabel;
	private TextArea isbnInput;
	private Button isbnSendButton;
	private TextArea isbnOutput;
	private GridPane gridpane;
	private ApiCallerClass apiCaller;
	private PropertyFileHandler propertyFileHandler;
	private VBox pageVBox;
	private int isbnAmount;
	private CheckBox issnInstead;
	
	
	public ISBNCheckerTab()
	{
		initiateElements();		
		configureElements();
	}
	
	private void initiateElements()
	{
		this.isbnCheckerTab = new Tab(Constants.isbnCheckerTabName);
		this.inputLabel = new Label("Input");
		this.outputLabel = new Label("Output");
		this.isbnInput = new TextArea();
		this.isbnSendButton = new Button("Anfrage starten");
		this.isbnOutput = new TextArea();
		this.gridpane = new GridPane();
		this.apiCaller = new ApiCallerClass();
		this.propertyFileHandler = PropertyFileHandler.getInstance();
		this.pageVBox = new VBox();	
		this.isbnAmount = 0;
	}
	private void configureElements()
	{	
		this.isbnCheckerTab.setClosable(false);
		this.issnInstead = new CheckBox();
		this.issnInstead.setText("Titel sind Zeitschriften (ISSN statt ISBN)");		
		
		this.gridpane.add(this.inputLabel, 0, 0); this.gridpane.add(this.isbnInput, 1, 0);this.gridpane.add(this.issnInstead, 2, 0);	this.gridpane.add(this.isbnSendButton, 3, 0);	
		this.gridpane.add(this.outputLabel, 0, 1); this.gridpane.add(this.isbnOutput, 1, 1);
		this.gridpane.setHgap(10);
		this.gridpane.setVgap(10);
		this.gridpane.setPadding(new Insets(10,10,10,10));
		this.isbnInput.setMinWidth(600);
		this.isbnInput.setMinHeight(300);
		this.isbnOutput.setMinWidth(600);
		this.isbnOutput.setMinHeight(300);
		this.isbnInput.setMaxWidth(600);
		this.isbnInput.setMaxHeight(300);
		this.isbnOutput.setMaxWidth(600);
		this.isbnOutput.setMaxHeight(300);
		this.isbnOutput.setEditable(false);
		this.pageVBox.getChildren().addAll(this.gridpane);
		
		isbnSendButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {            	
            	
					try {
						getAndCallIsbnsAndSetOutput();
					
						
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
            
            }
        });
		
		this.isbnInput.textProperty().addListener((observable, oldValue, newValue) -> {
							countISBNsFirst();						
		});
		 
		this.isbnCheckerTab.setContent(this.pageVBox);		
	}
	
	private Pattern getPatternISBN()
	{
		return Pattern.compile("\\D(\\d-?){13}\\D|\\D(\\d-?){10}\\D");
	}
	
	private Pattern getPatternISSN()
	{
		return Pattern.compile("[0-9]{4}\\-?[0-9]{3}[0-9xX]");
	}
	
	private String getInput()
	{
		return this.isbnInput.getText();
	}
	
	private Matcher getMatcher()
	{
		String input = " " + getInput() + " ";
		input = input.replace(" ", "  ");
		input = input.replace("\r\n", "  ");
		input = input.replace("\r", "  ");
		input = input.replace("\n", "  ");
		input = input.replace("\t", "  ");
		
		if (this.issnInstead.isSelected())
		{
			return getPatternISSN().matcher(input);
		} else
		{
			return getPatternISBN().matcher(input);
		}
	}
	

	
	private void countISBNsFirst()
	{
		Matcher matcher;		
		matcher = getMatcher();
		
		
		int counter = 0;

		while (matcher != null && matcher.find())
		{		
			
			System.out.println(counter);
			counter ++;
		}	
		
		this.isbnAmount = counter;
		this.isbnOutput.setText(counter + " ISBNs gefunden. Voraussichtliche Dauer: " + counter * .5 + " Sekunden.");
	}
	
	/*
	// code was just temporarily important to turn DNB-ddcs into bsz-ddcs for Reihe A
	private void getAndCallIsbnsAndSetOutput() throws MalformedURLException, InterruptedException
	{
		String input = isbnInput.getText();
		String output = "";
		
		String[] lines = input.split("\n");
		
		for (String line : lines)
		{
			for (int counter = 0; counter < line.length(); counter ++)
			{
				if (Character.isDigit(line.charAt(counter)))
				{
					output += line.charAt(counter);				
				} else 	if (line.charAt(counter) == ';')
				{
					output += ";";
				} else if (line.charAt(counter) == '.')
				{
					if (counter + 1 < line.length())
					{
						while (Character.isDigit(line.charAt(counter+1)))
						{
							counter ++;
						}
					}
					
					
				}	else if (!Character.isDigit(line.charAt(counter)) && (line.charAt(counter) == 'K' || line.charAt(counter) == 'S' || line.charAt(counter) == 'B'))
				{
					if (counter + 1 < line.length())
					{
						if (Character.isWhitespace(line.charAt(counter +1)))
						{
							output += line.charAt(counter);
						}						
					} else if (counter + 1 == line.length())
					{
						output += line.charAt(counter);
					}
				}			
			}
			output += System.lineSeparator();
		}
		
		
		this.isbnOutput.setText(output);
		
	}	
	*/
	
	/*
	// code was just temporarily important to turn DNB-content links into bsz-content links for Reihe A
	private void getAndCallIsbnsAndSetOutput() throws MalformedURLException, InterruptedException
	{
		String input = isbnInput.getText();
		List<String[]> contentsArrayList = new ArrayList<String[]>();
		
		String[] lines = input.split("\n");
		
		
		for (String line : lines)
		{
			String[] lineContents = line.split(";");
			{
				String[] tempContents = new String[2];
				for (String content : lineContents)
				{					
					if (content.contains("Inhaltsverzeichnis") && content.contains("[") && content.contains("]"))
					{
						tempContents[0] = content.substring(content.indexOf("[") +1, content.indexOf("]"));
					}
					if (content.contains("Inhaltstext")  && content.contains("[") && content.contains("]"))
					{
						tempContents[1] = content.substring(content.indexOf("[")+1, content.indexOf("]"));
					}
				}
				contentsArrayList.add(tempContents);
			}
		}
		
		String output = "";
		
		for(String[] contents : contentsArrayList)
		{
			output += contents[0] + "\t" + contents[1] + "\n";			
		}
		
		
		this.isbnOutput.setText(output.replace("null", ""));
		
	}	
	*/

	
	private void getAndCallIsbnsAndSetOutput() throws MalformedURLException, InterruptedException
	{
		Matcher matcher;		
		matcher = getMatcher();
		String result = getInput();
		String output = "";
		int counter = 0;

		while (matcher.find())
		{			
			String link = "";
			if (this.issnInstead.isSelected())
			{
				 link = "http://sru.k10plus.de/gvk!rec=1?version=1.1&operation=searchRetrieve&query=pica.iss%3D[{[isbn]}]&maximumRecords=10&recordSchema=marcxml";
			} else
			{
				 link = "http://sru.k10plus.de/gvk!rec=1?version=1.1&operation=searchRetrieve&query=pica.isb%3D[{[isbn]}]&maximumRecords=10&recordSchema=marcxml";
			}
			
			
			//result += matcher.group() + ": " + this.apiCaller.callUrl(new URL(link.replace("[{[isbn]}]", matcher.group()))).contains("DE-Frei129") + "\r\n";
	
			if (this.issnInstead.isSelected())
			{
				link = link.replace("[{[isbn]}]", matcher.group().replaceAll("[^0-9|-|X|x]+", ""));
			} else
			{
				link = link.replace("[{[isbn]}]", matcher.group().replaceAll("[^0-9|-]+", ""));
			}
			
			URL url = new URL(link);
			String bestand = this.apiCaller.callUrl(url).contains("DE-Frei129") ? "ja" : "nein";	
			
			if (this.issnInstead.isSelected())
			{
				output += matcher.group().replaceAll("[^0-9|-|X]+", "") + " " + bestand + System.lineSeparator();
			} else
			{
				result = result.replace(matcher.group().replaceAll("[^0-9|-]+", ""), matcher.group().replaceAll("[^0-9|-| ]+", "") + bestand);
			}			
			
			
			System.out.println(counter + "/" + this.isbnAmount + ": " + matcher.group().replaceAll("[^0-9|-]+", "") + " " + bestand);	
		
			counter ++;
		}		
		
		String x = result.substring(0, 1);
		
		if (!result.isBlank() && result.substring(0, 1).isBlank())
		{
			result = result.substring(1, result.length());
		}
			
			
		if (this.issnInstead.isSelected())
		{
			result = output;
		} 
		
		result = result.replace("\r\n ", "\r\n");
		result = result.replace("\r ", "\r");
		result = result.replace("\n ", "\n");
		result = result.replace(" \r\n", "\r\n");
		result = result.replace(" \r", "\r");
		result = result.replace(" \n", "\n");
		result = result.replace("\r\n\t", "\r\n");
		result = result.replace("\r\t", "\r");
		result = result.replace("\n\t", "\n");
		result = result.replace("\t\r\n", "\r\n");
		result = result.replace("\t\r", "\r");
		result = result.replace("\t\n", "\n");
		result = result.replace(" ", "\t"); 
		
			while (result.contains("\t\t") || result.contains("  "))
			{
				result = result.replace("  ", " ");
				result = result.replace("\t\t","\t");
			}
			

			
		
		this.isbnOutput.setText(result);
	}	
	
	
	

}
