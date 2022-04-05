package userInterface_vivi;
import java.io.IOException;

import logic_vivi.ApiCaller;
import logic_vivi.ConvenienceTools;
import logic_vivi.JsonParser;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * First Tab: Provides a simple interface to get information for a single ISSN
 */
public class SingleApiCallTab {
	
	//manipulate this if you want to make the fields wider
	//rest will align dynamically
	int TextFieldMinLength = 120;
	
	int TextAreaHeightUrlCollection = 90;
	int TextAreaHeightSherpaResponse = 60;
	
	int TextAreaParsedHeight_conditions = 160;
	int TextAreaParsedHeight_archive = 40;
	int TextAreaParsedHeight_info = 30;	
	int DefaultTextFieldLength = 149;		
	int TextAreaWidth = ((TextFieldMinLength + DefaultTextFieldLength) * 3) + 5 * 20;
	
	public Pane singleApiCallpane;
	
	HBox hbox_input;
	HBox hbox2_responseLine1;
	HBox hbox3_responseLine2;
	HBox hbox4_responseLine3;
	HBox hbox5_labelsConditions;
	HBox hbox6_conditions;
	VBox vbox_SherpaResponse;	
	TextField issnOrTitleTextfield;
	Button button_submit;
	TextArea romeoSherpaText = new TextArea();	
	TextArea urlCollectionTextArea = new TextArea();	
		
	String textFieldContent;
	Label labelRomeoSherpa;
	Label labelUrlCollection;
	Label labelOaDoi;
	Label labelDoiOrg;
	Label labelISSN;
	Label journalInfoTitle;	
	Label journalInfoLink;	
	TextField journalInfoTitle_textfield;
	TextField journalInfoLink_textfield;	
	
	//Additions required by Sherpa Migration (July 2020)
	
	Label publisherType;
	TextField publisherType_textfield;
	Label publisherName;
	TextField publisherName_textfield;
	Label lastUpdated;
	TextField lastUpdated_textfield;
	Label sherpaLink;
	TextField sherpaLink_textfield;
	Label doajLink;
	TextField doajLink_textfield;
	Label issn;
	TextField issn_textfield;
	Label issn_e;
	TextField issn_textfield_e;
	
	Label publishedVersion;
	TextArea publishedVersion_textArea;	
	Label acceptedVersion;
	TextArea acceptedVersion_textArea;	
	Label submittedVersion;
	TextArea submittedVersion_textArea;
	
	
	//constructor; Defines all the elements for the Single ApiCallTab;
	public SingleApiCallTab()
	{			
		this.issnOrTitleTextfield = new TextField();			
		this.button_submit = new Button();
		this.button_submit.setMinWidth(30);
		this.button_submit.setText("Absenden");
		this.labelISSN = new Label("ISSN/Titel eingeben:");
		this.journalInfoTitle = new Label("Titel");
		this.publisherName = new Label("Publisher name");
		this.journalInfoLink = new Label("Link (Journal)");		
		this.publisherType = new Label("Typ");	
		this.publisherName = new Label("Verlag");
		this.lastUpdated = new Label("Letztes Update");
		this.publishedVersion = new Label("Published Version");
		this.acceptedVersion = new Label("Accepted Version");
		this.submittedVersion = new Label("Submitted Version");
		this.sherpaLink = new Label("Link (Sherpa)");
		this.labelRomeoSherpa = new Label("Sherpa/Romeo API Response (JSON)");	
		this.labelUrlCollection = new Label("Nachweise:");
		this.issn = new Label("ISSN");		
		this.issn_e = new Label("eISSN");	
		this.doajLink = new Label("Link (DOAJ)");
		this.doajLink_textfield = new TextField();
		this.lastUpdated_textfield = new TextField("");	
		this.issn_textfield = new TextField("");
		this.issn_textfield_e = new TextField("");
		
		this.publisherType_textfield = new TextField("");
		this.publisherName_textfield = new TextField("");
		this.lastUpdated_textfield = new TextField("");
		this.sherpaLink_textfield = new TextField("");	
		
		this.publishedVersion_textArea = new TextArea("");			
		this.acceptedVersion_textArea = new TextArea("");			
		this.submittedVersion_textArea = new TextArea("");		
		
		this.journalInfoTitle_textfield = new TextField("");
		this.publisherName_textfield = new TextField("");
		this.journalInfoLink_textfield = new TextField("");
			
		this.labelISSN.setMinWidth(TextFieldMinLength);
		this.issnOrTitleTextfield.setMinWidth(TextFieldMinLength);
		this.journalInfoTitle.setMinWidth(TextFieldMinLength);
		this.publisherName.setMinWidth(TextFieldMinLength);
		this.journalInfoLink.setMinWidth(TextFieldMinLength);
		this.publisherType.setMinWidth(TextFieldMinLength);
		this.publisherName.setMinWidth(TextFieldMinLength);
		this.lastUpdated.setMinWidth(TextFieldMinLength);
		this.publishedVersion.setMinWidth(TextFieldMinLength);
		this.acceptedVersion.setMinWidth(TextFieldMinLength);
		this.submittedVersion.setMinWidth(TextFieldMinLength);
		this.sherpaLink.setMinWidth(TextFieldMinLength);
		this.doajLink.setMinWidth(TextFieldMinLength);
		this.issn.setMinWidth(TextFieldMinLength);
		this.issn_e.setMinWidth(TextFieldMinLength);
		
		//TextAreaWidth * 0.32
		
		this.publishedVersion.setMinWidth(TextFieldMinLength + DefaultTextFieldLength + 20);
		this.acceptedVersion.setMinWidth(TextFieldMinLength + DefaultTextFieldLength + 20);
		this.submittedVersion.setMinWidth(TextFieldMinLength + DefaultTextFieldLength + 20);
		this.publishedVersion.setMaxWidth(TextFieldMinLength + DefaultTextFieldLength + 20);
		this.acceptedVersion.setMaxWidth(TextFieldMinLength + DefaultTextFieldLength + 20);
		this.submittedVersion.setMaxWidth(TextFieldMinLength + DefaultTextFieldLength + 20);
		
		this.romeoSherpaText.setMaxHeight(TextAreaHeightSherpaResponse);
		this.urlCollectionTextArea.setMaxHeight(TextAreaHeightUrlCollection);
		this.romeoSherpaText.setMinWidth(TextAreaWidth);
		this.publishedVersion_textArea.setMinWidth(TextFieldMinLength + DefaultTextFieldLength + 20);
		this.acceptedVersion_textArea.setMinWidth(TextFieldMinLength + DefaultTextFieldLength + 20);
		this.submittedVersion_textArea.setMinWidth(TextFieldMinLength + DefaultTextFieldLength + 20);
		this.publishedVersion_textArea.setMaxWidth(TextFieldMinLength + DefaultTextFieldLength + 20);
		this.acceptedVersion_textArea.setMaxWidth(TextFieldMinLength + DefaultTextFieldLength + 20);
		this.submittedVersion_textArea.setMaxWidth(TextFieldMinLength + DefaultTextFieldLength + 20);
		
		// set readonly properties
		this.romeoSherpaText.setEditable(false);
		this.urlCollectionTextArea.setEditable(false);
		this.publishedVersion_textArea.setEditable(false);
		this.submittedVersion_textArea.setEditable(false);
		this.acceptedVersion_textArea.setEditable(false);
		this.journalInfoTitle_textfield.setEditable(false);
		this.publisherName_textfield.setEditable(false);
		this.publisherType_textfield.setEditable(false);
		this.lastUpdated_textfield.setEditable(false);
		this.issn_textfield.setEditable(false);
		this.issn_textfield_e.setEditable(false);	
		
		this.getSubmitButtonEvent();
		this.hbox_input = new HBox(20, labelISSN, issnOrTitleTextfield, button_submit);	
		this.hbox2_responseLine1 = new HBox(20, journalInfoTitle, journalInfoTitle_textfield, issn, issn_textfield, issn_e, issn_textfield_e );
		this.hbox3_responseLine2 = new HBox(20, publisherName, publisherName_textfield, publisherType, publisherType_textfield, lastUpdated, lastUpdated_textfield);
		this.hbox4_responseLine3 = new HBox(20, sherpaLink, sherpaLink_textfield, journalInfoLink, journalInfoLink_textfield, doajLink, doajLink_textfield);
		this.hbox5_labelsConditions = new HBox(20, submittedVersion, acceptedVersion, publishedVersion);
		this.hbox6_conditions = new HBox(20, submittedVersion_textArea, acceptedVersion_textArea, publishedVersion_textArea);
		this.vbox_SherpaResponse = new VBox(labelUrlCollection, urlCollectionTextArea, labelRomeoSherpa, romeoSherpaText);
		//this.vbox_SherpaResponse = new vbox_SherpaResponse(labelRomeoSherpa, romeoSherpaText, labelOaDoi, oaDoiText, labelDoiOrg, doajText);
		this.hbox_input.setLayoutY(20);			
		this.hbox2_responseLine1.setLayoutY(65);
		this.hbox3_responseLine2.setLayoutY(95);	
		this.hbox4_responseLine3.setLayoutY(125);			
		this.hbox5_labelsConditions.setLayoutY(160);
		this.hbox6_conditions.setLayoutY(180);
		this.vbox_SherpaResponse.setLayoutY(370);
		this.hbox_input.setLayoutX(20);
		this.vbox_SherpaResponse.setLayoutX(20);
		this.hbox2_responseLine1.setLayoutX(20);
		this.hbox3_responseLine2.setLayoutX(20);
		this.hbox4_responseLine3.setLayoutX(20);		
		this.hbox5_labelsConditions.setLayoutX(20);
		this.hbox6_conditions.setLayoutX(20);
		this.singleApiCallpane = new Pane();
		this.singleApiCallpane.getChildren().addAll(this.hbox_input, this.hbox2_responseLine1, this.hbox3_responseLine2, this.hbox4_responseLine3, this.hbox5_labelsConditions, this.hbox6_conditions, this.vbox_SherpaResponse);
		
		//listeners/observables/actions etc.
		
		//get propertyFilecontent:
		
		
		//Check if new value in issn/titel field ist valid, if so: Call api straight away
		this.issnOrTitleTextfield.textProperty().addListener((observable, oldValue, newValue) ->
		{		
			if(ConvenienceTools.isValidISSN(newValue))
			{
				try {
					getTextAndCallApi();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		//Call API if "Enter" is being used
		this.issnOrTitleTextfield.setOnKeyReleased(new EventHandler<KeyEvent>() {
	        
	        @Override
	        public void handle(KeyEvent event) {
	            if(event.getCode().equals(KeyCode.ENTER)) {
	            	try {
						getTextAndCallApi();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            }
	        }
	    });

		
	}
	
	//defines what to do if Submit button is clicked
	private void getSubmitButtonEvent()
	{		
		this.button_submit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
			
				try {
					getTextAndCallApi();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});			
	}
	
	//get issn (text) from text field. Calls api, extracts information from response, puts it into text fields
	private void getTextAndCallApi() throws IOException
	{
		//clear former output
		clearOutputFields();		
		//API-Caller class
		ApiCaller apiCaller = new ApiCaller();
		
		//Get ISSN or Title
		this.textFieldContent = this.issnOrTitleTextfield.getText();		
		apiCaller.initWithISSnOrTitle(this.textFieldContent, true);
		putApiCallDetailsIntoTextFieldsAfterCall(apiCaller);
			
	}
	
	//clear contents, just in case its not the first call
	private void clearOutputFields()
	{		
				
				this.journalInfoTitle_textfield.clear();
				this.publisherName_textfield.clear();
				this.journalInfoLink_textfield.clear();	
				this.romeoSherpaText.clear();				
				this.publisherType_textfield.clear();		
				this.publisherName_textfield.clear();			
				this.lastUpdated_textfield.clear();				
				this.sherpaLink_textfield.clear();	
				this.publishedVersion_textArea.clear();				
				this.acceptedVersion_textArea.clear();					
				this.submittedVersion_textArea.clear();
				this.doajLink_textfield.clear(); 
				this.issn_textfield.clear();
				this.issn_textfield_e.clear();
				this.urlCollectionTextArea.clear();
	}
	
	//get information, put it into designated fields to display on the gui
	private void putApiCallDetailsIntoTextFieldsAfterCall(ApiCaller apiCaller)
	{		
				this.romeoSherpaText.appendText(apiCaller.romeoSherpaApiResponseString);
				JsonParser jsonParser = new JsonParser(apiCaller.romeoSherpaApiResponseString);					
			
					this.lastUpdated_textfield.setText(jsonParser.getLastUpdated());
					this.sherpaLink_textfield.setText(jsonParser.getSherpaLink());
					this.publishedVersion_textArea.setText(jsonParser.getConditionsPublishedVersion());			
					this.acceptedVersion_textArea.setText(jsonParser.getConditionsAcceptedVersion());		
					this.submittedVersion_textArea.setText(jsonParser.getConditionsSubmittedVersion());
					this.publisherType_textfield.setText(jsonParser.getPublisherType());
					this.journalInfoTitle_textfield.setText(jsonParser.getTitle());	
					this.journalInfoLink_textfield.setText(jsonParser.getLink());	
					this.publisherName_textfield.setText(jsonParser.getPublisher());	
					this.issn_textfield.setText(jsonParser.getISSN());
					this.issn_textfield_e.setText(jsonParser.getISSN_e());
					this.doajLink_textfield.setText(jsonParser.getDOAJLink());
					this.urlCollectionTextArea.setText(jsonParser.getUrlCollection());
			
				
							
	}
}