package userInterface_vivi;


import java.io.IOException;

import logic_vivi.ConvenienceTools;
import logic_vivi.HelpTextFileHandler;
import logic_vivi.PropertyFileHandler;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class PropertiesTab {
	//Addition: 03.09.20: Instead of one properties pane, build a tab pane: Divide Properties in different tabs	
	public Pane propertiesPane;	
	TabPane tabpane;
	VBox tabPaneVBox;
	
	//tabGeneral	
	Tab tabGeneralProperties;
	Pane paneGeneralProperties;
	Button saveButtonGeneral;
	Label sherpaUrlLabel;
	TextField sherpaUrlString;
	Label sherpaApiKeyLabel;
	TextField sherpaApiKeyString;
	HBox sherpaUrlHBox;
	HBox sherpaApiKeyHbox;
	HBox saveButtonHBoxGeneral;
	
	//tabSingleCall
	Pane paneSingleCallProperties;
	Tab tabSherpaSingleApiCall;
	Button saveButtonSingleCall;
	Label singleApiCallByTitleIfNoIssnFoundLabel;
	CheckBox singleApiCallByTitleIfNoIssnFoundCheckbox;
	Label displayConditionsIfNoTagContainedLabel;
	CheckBox displayConditionsIfNoTagContainedCheckbox;
	HBox singleApiCallByTitleIfNoIssnFoundHbox;
	HBox saveButtonHBoxSingleCall;
	HBox displayConditionsIfNoTagContainedHBox;
	
	//tabMultiCall
	Pane paneMutliCallProperties;
	Tab tabSherpaMultiApiCall;
	Button saveButtonMutliCall;
	Label multiApiCallByTitleIfNoIssnFoundLabel;
	CheckBox multiApiCallByTitleIfNoIssnFoundCheckbox;
	Label exportConditionsInSeparateColumnsLabel;
	CheckBox exportConditionsInSeparateColumnsCheckbox;
	Label exportColumnWhiteListFilterLabel;
	Label exportColumnWhiteListFilterLabelColumn;
	Label exportColumnWhiteListFilterLabelFilter;
	ComboBox<String> exportColumnWhiteListFilterColumnComboBox;
	TextField exportColumnWhiteListFilterFilterTextField;
	CheckBox exportColumnWhiteListCheckbox;
	HBox multiApiCallByTitleIfNoIssnFoundHbox;
	HBox saveButtonHBoxMutliCall;
	HBox exportConditionsInSeparateColumnsHBox;
	HBox exportColumnWhiteListFilterHbox;
	PropertyFileHandler propertyFileHandler;
	
	//helpTexts
	Button propertiesApiKeyButtonHelpText;
	Button propertiesMultiCallCallApiViaTitleIfIssnMissingButtonHelpText;
	Button propertiesSingleCallCallApiViaTitleIfIssnMissingButtonHelpText;
	Button propertiesShowDetailsIfJsonTagMissingButtonHelpText;
	Button propertiesExportDataInSeparateColumnsButtonHelpText;
	Button propertiesWhitelistExportButtonHelpText;
	Button propertiesFileImportColumnDefinitionButtonHelpText;
	Button propertiesFileImportFileTypDefintionButtonHelpText;
	
	//HelpTexts	
	String HelpImagePath = "res/info-small.png";
	
	//addition 15.09.2020: Option to choose other file Imports other then wos, if so: also turn column field into free textfield
	ComboBox<String> importFileTypeComboBox;
	Label importFileTypeLabel;
	Label importFileISSNColumnLabel;
	Label importFileeISSNColumnLabel;
	Label importFileTitleColumnLabel;
	Label importFileTitleColumnDefinitionLabel;
	TextField importFileTypeISSNColumn;
	TextField importFileTypeeISSNColumn;
	TextField importFileTypeTitleColumn;
	TextField exportColumnWhiteListFilterColumnTextField;
	HBox importFileTypeHBox;
	HBox importFileTypeColumnTitleHBox;
	
	
	//Constructor
	public PropertiesTab() throws IOException
	{		
		HelpTextFileHandler helpTextFileHandler = HelpTextFileHandler.getInstance();
		
		//helpTexts and buttons
			Image helpImage = ConvenienceTools.getPictureFromResources(HelpImagePath);		
		
			this.propertiesApiKeyButtonHelpText = new Button();			
			this.propertiesApiKeyButtonHelpText.setGraphic(ConvenienceTools.getImageViewForHelpTexts(helpImage));
			ConvenienceTools.getHelpButtonEvent(this.propertiesApiKeyButtonHelpText, helpTextFileHandler.helpTextModel.getPropertiesApiKey());			
		
			this.propertiesSingleCallCallApiViaTitleIfIssnMissingButtonHelpText = new Button();
			this.propertiesSingleCallCallApiViaTitleIfIssnMissingButtonHelpText.setGraphic(ConvenienceTools.getImageViewForHelpTexts(helpImage));
			ConvenienceTools.getHelpButtonEvent(this.propertiesSingleCallCallApiViaTitleIfIssnMissingButtonHelpText, helpTextFileHandler.helpTextModel.getPropertiesCallApiViaTitleIfIssnMissing());

			this.propertiesMultiCallCallApiViaTitleIfIssnMissingButtonHelpText = new Button();
			this.propertiesMultiCallCallApiViaTitleIfIssnMissingButtonHelpText.setGraphic(ConvenienceTools.getImageViewForHelpTexts(helpImage));
			ConvenienceTools.getHelpButtonEvent(this.propertiesMultiCallCallApiViaTitleIfIssnMissingButtonHelpText, helpTextFileHandler.helpTextModel.getPropertiesCallApiViaTitleIfIssnMissing());
		
			this.propertiesShowDetailsIfJsonTagMissingButtonHelpText = new Button();
			this.propertiesShowDetailsIfJsonTagMissingButtonHelpText.setGraphic(ConvenienceTools.getImageViewForHelpTexts(helpImage));
			ConvenienceTools.getHelpButtonEvent(this.propertiesShowDetailsIfJsonTagMissingButtonHelpText, helpTextFileHandler.helpTextModel.getPropertiesShowDetailsIfJsonTagMissing());
		
			this.propertiesExportDataInSeparateColumnsButtonHelpText = new Button();
			this.propertiesExportDataInSeparateColumnsButtonHelpText.setGraphic(ConvenienceTools.getImageViewForHelpTexts(helpImage));
			ConvenienceTools.getHelpButtonEvent(this.propertiesExportDataInSeparateColumnsButtonHelpText, helpTextFileHandler.helpTextModel.getPropertiesExportDataInSeparateColumns());
		
			this.propertiesWhitelistExportButtonHelpText = new Button();
			this.propertiesWhitelistExportButtonHelpText.setGraphic(ConvenienceTools.getImageViewForHelpTexts(helpImage));
			ConvenienceTools.getHelpButtonEvent(this.propertiesWhitelistExportButtonHelpText, helpTextFileHandler.helpTextModel.getPropertiesWhitelistExport());	
			
			this.propertiesFileImportColumnDefinitionButtonHelpText = new Button();
			this.propertiesFileImportColumnDefinitionButtonHelpText.setGraphic(ConvenienceTools.getImageViewForHelpTexts(helpImage));
			ConvenienceTools.getHelpButtonEvent(this.propertiesFileImportColumnDefinitionButtonHelpText, helpTextFileHandler.helpTextModel.getPropertiesFileImportColumnDefintion());
			
			this.propertiesFileImportFileTypDefintionButtonHelpText = new Button();
			this.propertiesFileImportFileTypDefintionButtonHelpText.setGraphic(ConvenienceTools.getImageViewForHelpTexts(helpImage));
			ConvenienceTools.getHelpButtonEvent(this.propertiesFileImportFileTypDefintionButtonHelpText, helpTextFileHandler.helpTextModel.getPropertiesFileImportFileTypDefintion());
			
		
		this.propertiesPane = new Pane();	
		this.propertyFileHandler = PropertyFileHandler.getInstance();
		this.tabpane = new TabPane();
		this.tabpane.setMinWidth(950);
		
		
		//Tab General
		this.tabGeneralProperties = new Tab("Allgemeines");		
		this.tabGeneralProperties.setClosable(false);
		this.tabpane.getTabs().add(this.tabGeneralProperties); 
		this.paneGeneralProperties = new Pane();
		this.tabGeneralProperties.setContent(this.paneGeneralProperties);
		this.saveButtonGeneral = new Button();
		
			//labels and textfields
			this.sherpaUrlLabel = new Label();
			this.sherpaUrlString = new TextField();
			this.sherpaApiKeyLabel = new Label();
			this.sherpaApiKeyString = new TextField();	
			
			this.saveButtonGeneral.setMinWidth(150);	
			this.sherpaUrlString.setText(propertyFileHandler.propertyFileModel.getSherpaApiAdress());
			this.sherpaApiKeyString.setText(propertyFileHandler.propertyFileModel.getSherpaKey());	
			this.sherpaUrlLabel.setMinWidth(300);
			this.sherpaUrlString.setMinWidth(300);
			this.sherpaApiKeyLabel.setMinWidth(300);
			this.sherpaApiKeyString.setMinWidth(300);	
			this.saveButtonGeneral.setText("Einstellungen speichern");			
			this.sherpaUrlLabel.setText("Sherpa URL");
			this.sherpaApiKeyLabel.setText("API-Key");			
			this.sherpaUrlHBox = new HBox(20, sherpaUrlLabel, sherpaUrlString);
			this.sherpaApiKeyHbox = new HBox(20, sherpaApiKeyLabel, sherpaApiKeyString, this.propertiesApiKeyButtonHelpText);
			this.saveButtonHBoxGeneral = new HBox(20, saveButtonGeneral);
			this.sherpaUrlHBox.setLayoutX(20);
			this.sherpaApiKeyHbox.setLayoutX(20);
			this.saveButtonHBoxGeneral.setLayoutX(20);
			this.sherpaUrlHBox.setLayoutY(50);
			this.sherpaApiKeyHbox.setLayoutY(80);
			this.saveButtonHBoxGeneral.setLayoutY(150);
			
		//Tab singleApiCall
		this.tabSherpaSingleApiCall = new Tab("ISSN/Titel abfragen");
		this.tabSherpaSingleApiCall.setClosable(false);
		this.tabpane.getTabs().add(this.tabSherpaSingleApiCall); 
		this.paneSingleCallProperties = new Pane();
		this.tabSherpaSingleApiCall.setContent(this.paneSingleCallProperties);
		this.saveButtonSingleCall = new Button();
		
			//labels and textfields
			this.singleApiCallByTitleIfNoIssnFoundLabel = new Label();
			this.singleApiCallByTitleIfNoIssnFoundCheckbox = new CheckBox();	
			this.singleApiCallByTitleIfNoIssnFoundCheckbox.setIndeterminate(false);
			
			this.singleApiCallByTitleIfNoIssnFoundCheckbox.setSelected(propertyFileHandler.propertyFileModel.getSingleApiCallByTitleIfNoIssnFound());	
			this.saveButtonSingleCall.setMinWidth(150);
			this.singleApiCallByTitleIfNoIssnFoundLabel.setMinWidth(300);
			this.saveButtonSingleCall.setText("Einstellungen speichern");
			this.singleApiCallByTitleIfNoIssnFoundLabel.setText("Daten alternativ über Titel abrufen?");
			this.singleApiCallByTitleIfNoIssnFoundHbox = new HBox(20, singleApiCallByTitleIfNoIssnFoundLabel, singleApiCallByTitleIfNoIssnFoundCheckbox, propertiesSingleCallCallApiViaTitleIfIssnMissingButtonHelpText);
			this.saveButtonHBoxSingleCall = new HBox(20, saveButtonSingleCall);
			this.singleApiCallByTitleIfNoIssnFoundHbox.setLayoutX(20);
			this.saveButtonHBoxSingleCall.setLayoutX(20);
			this.singleApiCallByTitleIfNoIssnFoundHbox.setLayoutY(50);	
			this.saveButtonHBoxSingleCall.setLayoutY(150);
			
			//addition: 04.09.20
			this.displayConditionsIfNoTagContainedLabel = new Label("Details anzeigen, falls Felder unbelegt?");
			this.displayConditionsIfNoTagContainedLabel.setMinWidth(300);
			this.displayConditionsIfNoTagContainedCheckbox = new CheckBox();
			this.displayConditionsIfNoTagContainedCheckbox.setSelected(propertyFileHandler.propertyFileModel.getSingleApiCallDisplayConditionsIfNoTagContained());	
			this.displayConditionsIfNoTagContainedHBox = new HBox(20, displayConditionsIfNoTagContainedLabel, displayConditionsIfNoTagContainedCheckbox, propertiesShowDetailsIfJsonTagMissingButtonHelpText);
			this.displayConditionsIfNoTagContainedHBox.setLayoutX(20);
			this.displayConditionsIfNoTagContainedHBox.setLayoutY(80);
			
		//Tab MultiApiCall
		this.tabSherpaMultiApiCall = new Tab("Publikationsliste anreichern");
		this.tabSherpaMultiApiCall.setClosable(false);
		this.tabpane.getTabs().add(this.tabSherpaMultiApiCall);
		this.paneMutliCallProperties = new Pane();
		this.tabSherpaMultiApiCall.setContent(this.paneMutliCallProperties);	
		this.saveButtonMutliCall = new Button();
		
		
			//labels and textfields
			this.multiApiCallByTitleIfNoIssnFoundLabel = new Label();
			this.multiApiCallByTitleIfNoIssnFoundCheckbox = new CheckBox();	
			this.multiApiCallByTitleIfNoIssnFoundCheckbox.setIndeterminate(false);			
			this.multiApiCallByTitleIfNoIssnFoundCheckbox.setOnAction(callApiByTitleDisableEnableTitleColumnForColumnDefinition());
			this.multiApiCallByTitleIfNoIssnFoundCheckbox.setSelected(propertyFileHandler.propertyFileModel.getMultiApiCallByTitleIfNoIssnFound());
			
			this.saveButtonMutliCall.setMinWidth(150);	
			this.multiApiCallByTitleIfNoIssnFoundLabel.setMinWidth(300);			
			this.saveButtonMutliCall.setText("Einstellungen speichern");
			this.multiApiCallByTitleIfNoIssnFoundLabel.setText("Daten alternativ über Titel abrufen?");
			this.multiApiCallByTitleIfNoIssnFoundHbox = new HBox(20, multiApiCallByTitleIfNoIssnFoundLabel, multiApiCallByTitleIfNoIssnFoundCheckbox, propertiesMultiCallCallApiViaTitleIfIssnMissingButtonHelpText);
			this.saveButtonHBoxMutliCall = new HBox(20, saveButtonMutliCall);
			this.multiApiCallByTitleIfNoIssnFoundHbox.setLayoutX(20); 
			this.saveButtonHBoxMutliCall.setLayoutX(20);
			this.multiApiCallByTitleIfNoIssnFoundHbox.setLayoutY(50);
			
			//addition: 04.09.20
			this.exportConditionsInSeparateColumnsLabel = new Label("Details in separate Spalten exportieren?");
			this.exportConditionsInSeparateColumnsLabel.setMinWidth(300);
			this.exportConditionsInSeparateColumnsCheckbox = new CheckBox();
			this.exportConditionsInSeparateColumnsCheckbox.setSelected(propertyFileHandler.propertyFileModel.getMultiApiCallExportConditionsInSeparateColumns());	
			this.exportConditionsInSeparateColumnsHBox = new HBox(20, exportConditionsInSeparateColumnsLabel, exportConditionsInSeparateColumnsCheckbox, propertiesExportDataInSeparateColumnsButtonHelpText);
			this.exportConditionsInSeparateColumnsHBox.setLayoutX(20);
			this.exportConditionsInSeparateColumnsHBox.setLayoutY(80);
			this.exportColumnWhiteListFilterLabelColumn = new Label("Spalte:");
			this.exportColumnWhiteListFilterLabelFilter = new Label("Enthält Wert:");
			this.exportColumnWhiteListCheckbox = new CheckBox();
			this.exportColumnWhiteListCheckbox.setSelected(propertyFileHandler.propertyFileModel.getMultiApiCallWhiteListCheckbox());
		
					
			this.exportColumnWhiteListFilterLabel = new Label("Whitelist:");
			this.exportColumnWhiteListFilterLabel.setMinWidth(300);
			this.exportColumnWhiteListFilterColumnComboBox = new ComboBox<String>(ConvenienceTools.getWoSColumnList());
			this.exportColumnWhiteListFilterColumnComboBox.getSelectionModel().select(propertyFileHandler.propertyFileModel.getMultiApiCallWhiteListColumn());
			this.exportColumnWhiteListFilterColumnComboBox.setMaxWidth(190);
			this.exportColumnWhiteListFilterFilterTextField = new TextField(propertyFileHandler.propertyFileModel.getMultiApiCallWhiteListFilter());
			this.saveButtonHBoxMutliCall.setLayoutY(210);				
		
			//addition 15.09.20
			this.importFileTypeComboBox = new ComboBox<String>(FXCollections.observableArrayList(
					"Web of Science Publication Export",
					"Tab/Komma getrennte Datei (CSV/TSV)", 
					"Excel-Datei"));
			this.importFileTypeComboBox.getSelectionModel().select(propertyFileHandler.propertyFileModel.getImportFileTypeComboBox());
			
			this.importFileTypeLabel = new Label("Import Dateityp:");
			this.importFileTitleColumnDefinitionLabel = new Label("Import Spaltendefintion:");		
			this.importFileISSNColumnLabel = new Label("ISSN:");
			this.importFileISSNColumnLabel.setMaxWidth(50);
			this.importFileeISSNColumnLabel = new Label("eISSN:");
			this.importFileeISSNColumnLabel.setMaxWidth(50);
			this.importFileTitleColumnLabel = new Label("Titel:");
			this.importFileTitleColumnLabel.setMaxWidth(50);
			this.importFileTitleColumnDefinitionLabel.setMinWidth(300);	
			this.importFileTypeLabel.setMinWidth(300);
			
			
			this.importFileTypeISSNColumn = new TextField();
			this.importFileTypeISSNColumn.setMaxWidth(100);
			this.importFileTypeeISSNColumn = new TextField();
			this.importFileTypeeISSNColumn.setMaxWidth(100);
			this.importFileTypeTitleColumn = new TextField();
			this.importFileTypeTitleColumn.setMaxWidth(100);
			this.exportColumnWhiteListFilterColumnTextField = new TextField();	
			this.exportColumnWhiteListFilterColumnTextField.setMinWidth(140);
			this.exportColumnWhiteListFilterColumnTextField.setText(propertyFileHandler.propertyFileModel.getExportColumnWhiteListFilterColumnTextField());
			
			this.importFileTypeHBox = new HBox(20, importFileTypeLabel, importFileTypeComboBox, propertiesFileImportFileTypDefintionButtonHelpText);
			this.importFileTypeColumnTitleHBox = new HBox(20, importFileTitleColumnDefinitionLabel, importFileISSNColumnLabel, importFileTypeISSNColumn, importFileeISSNColumnLabel, importFileTypeeISSNColumn, importFileTitleColumnLabel, importFileTypeTitleColumn, propertiesFileImportColumnDefinitionButtonHelpText);
			this.importFileTypeHBox.setLayoutY(110);
			this.importFileTypeHBox.setLayoutX(20);
			this.importFileTypeColumnTitleHBox.setLayoutY(140);
			this.importFileTypeColumnTitleHBox.setLayoutX(20);
						
			
			this.exportColumnWhiteListFilterHbox = new HBox(20, exportColumnWhiteListFilterLabel, exportColumnWhiteListCheckbox, exportColumnWhiteListFilterLabelColumn);
			whitelistAndImportTypeCheckBoxTextFieldHandler();
			this.exportColumnWhiteListFilterHbox.setLayoutY(170);
			this.exportColumnWhiteListFilterHbox.setLayoutX(20);
			
            this.exportColumnWhiteListCheckbox.setOnAction(getWhitelistChecklistEventHandler());	
            this.importFileTypeComboBox.setOnAction(replaceComboBoxOrTextfieldInWhitlistHboxEventHandler());			
		
		this.tabPaneVBox = new VBox(this.tabpane);			
		
		this.paneGeneralProperties.getChildren().addAll(this.sherpaUrlHBox, this.sherpaApiKeyHbox, this.saveButtonHBoxGeneral);
		this.paneSingleCallProperties.getChildren().addAll(this.singleApiCallByTitleIfNoIssnFoundHbox, this.displayConditionsIfNoTagContainedHBox, this.saveButtonHBoxSingleCall);
		this.paneMutliCallProperties.getChildren().addAll(this.multiApiCallByTitleIfNoIssnFoundHbox, this.exportConditionsInSeparateColumnsHBox, this.saveButtonHBoxMutliCall, this.exportColumnWhiteListFilterHbox, importFileTypeHBox, importFileTypeColumnTitleHBox);		
		
		this.propertiesPane.getChildren().addAll(tabPaneVBox);		
		this.getSaveButtonEventGeneral();
		this.getSaveButtonEventSingleCall();
		this.getSaveButtonEventMultiCall();
		callApiByTitleDisableEnableTitleColumnForColumnDefinitionFunction();
		
	}
	
	
	private void getSaveButtonEventGeneral()
	{		
		this.saveButtonGeneral.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				
				//text: User input; todo: Function to upload file directly instead of copying contents		
							
				try {
					propertyFileHandler.setConfigDetailGeneral(sherpaUrlString.getText(), sherpaApiKeyString.getText());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});		
	}
	
	private void getSaveButtonEventSingleCall()
	{		
		this.saveButtonSingleCall.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				
				//text: User input; todo: Function to upload file directly instead of copying contents		
							
				try {
					propertyFileHandler.setConfigDetailSingleCall(singleApiCallByTitleIfNoIssnFoundCheckbox.isSelected(), displayConditionsIfNoTagContainedCheckbox.isSelected());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});		
	}
	
	private void getSaveButtonEventMultiCall()
	{		
		this.saveButtonMutliCall.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String tempImportFileTypeISSNColumn = new String();
				String tempImportFileTypeeISSNColumn = new String();
				String tempImportFileTypeTitleColumn = new String();
				if (importFileTypeComboBox.getSelectionModel().getSelectedItem() == "Web of Science Publication Export")
				{
					tempImportFileTypeISSNColumn = propertyFileHandler.propertyFileModel.getImportFileTypeISSNColumn();
					tempImportFileTypeTitleColumn = propertyFileHandler.propertyFileModel.getImportFileTypeTitleColumn();
				} else
				{
					tempImportFileTypeeISSNColumn = importFileTypeeISSNColumn.getText();
					tempImportFileTypeISSNColumn = importFileTypeISSNColumn.getText();
					tempImportFileTypeTitleColumn = importFileTypeTitleColumn.getText();
				}
		
				try {
					propertyFileHandler.setConfigDetailMultiCall(multiApiCallByTitleIfNoIssnFoundCheckbox.isSelected(), exportConditionsInSeparateColumnsCheckbox.isSelected(), exportColumnWhiteListFilterColumnComboBox.getValue(), exportColumnWhiteListFilterFilterTextField.getText(), exportColumnWhiteListCheckbox.isSelected(), 
							tempImportFileTypeISSNColumn, tempImportFileTypeeISSNColumn, tempImportFileTypeTitleColumn, exportColumnWhiteListFilterColumnTextField.getText(), importFileTypeComboBox.getValue());
				
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});		
	}
	
	private EventHandler<ActionEvent> getWhitelistChecklistEventHandler()
	{
		return  new EventHandler<ActionEvent>() {				  
            public void handle(ActionEvent e) 
            {  	
            
            		exportColumnWhiteListFilterLabelFilter.setDisable(!exportColumnWhiteListCheckbox.isSelected());
        			exportColumnWhiteListFilterLabelColumn.setDisable(!exportColumnWhiteListCheckbox.isSelected());        			
        			exportColumnWhiteListFilterFilterTextField.setDisable(!exportColumnWhiteListCheckbox.isSelected());
        			exportColumnWhiteListFilterColumnComboBox.setDisable(!exportColumnWhiteListCheckbox.isSelected());
        			exportColumnWhiteListFilterColumnTextField.setDisable(!exportColumnWhiteListCheckbox.isSelected());
        		
             }   
        }; 
	}
	
	private EventHandler<ActionEvent> replaceComboBoxOrTextfieldInWhitlistHboxEventHandler()
	{
		return  new EventHandler<ActionEvent>() {	
			public void handle(ActionEvent e) 
            { 				
				whitelistAndImportTypeCheckBoxTextFieldHandler();
				callApiByTitleDisableEnableTitleColumnForColumnDefinitionFunction();
            }
		};
	}
	
	private EventHandler<ActionEvent> callApiByTitleDisableEnableTitleColumnForColumnDefinition()
	{
		return  new EventHandler<ActionEvent>() {	
			public void handle(ActionEvent e) 
	        {  
			callApiByTitleDisableEnableTitleColumnForColumnDefinitionFunction();
	        }
		};
	}
	
	private void callApiByTitleDisableEnableTitleColumnForColumnDefinitionFunction()
	{
		
			if (multiApiCallByTitleIfNoIssnFoundCheckbox.isSelected() && importFileTypeComboBox.getSelectionModel().getSelectedItem() != "Web of Science Publication Export")
			{			
				importFileTitleColumnLabel.setDisable(false);
				importFileTypeTitleColumn.setDisable(false);
			} else if (multiApiCallByTitleIfNoIssnFoundCheckbox.isSelected() && importFileTypeComboBox.getSelectionModel().getSelectedItem() == "Web of Science Publication Export")
			{				
				importFileTitleColumnLabel.setDisable(true);
				importFileTypeTitleColumn.setDisable(true);
			} else if (!multiApiCallByTitleIfNoIssnFoundCheckbox.isSelected())
			{				
				importFileTitleColumnLabel.setDisable(true);
				importFileTypeTitleColumn.setDisable(true);
			}
        
	}
	
	//enables/disables fields depending on which import Type is active (also whitelist filter is being considered)
	private void whitelistAndImportTypeCheckBoxTextFieldHandler()
	{
		if(exportColumnWhiteListFilterHbox.getChildren().contains(propertiesWhitelistExportButtonHelpText))
		{
		exportColumnWhiteListFilterHbox.getChildren().remove(propertiesWhitelistExportButtonHelpText);
		}
		if(exportColumnWhiteListFilterHbox.getChildren().contains(exportColumnWhiteListFilterLabelFilter))
		{
		exportColumnWhiteListFilterHbox.getChildren().remove(exportColumnWhiteListFilterLabelFilter);
		}
		if(exportColumnWhiteListFilterHbox.getChildren().contains(exportColumnWhiteListFilterFilterTextField))
		{
		exportColumnWhiteListFilterHbox.getChildren().remove(exportColumnWhiteListFilterFilterTextField);	
		}
		if (importFileTypeComboBox.getSelectionModel().getSelectedItem() == "Web of Science Publication Export")
		{
			
			
			importFileTypeeISSNColumn.setEditable(false);
			importFileTypeISSNColumn.setEditable(false);
			importFileTypeTitleColumn.setEditable(false);
			importFileTypeISSNColumn.setDisable(true);
			importFileTypeTitleColumn.setDisable(true);
			importFileTypeISSNColumn.setText("SN");
			importFileTypeeISSNColumn.setText("EI");
			importFileTypeTitleColumn.setText("SO"); 
			importFileISSNColumnLabel.setDisable(true);
			importFileTitleColumnLabel.setDisable(true);
			importFileTypeeISSNColumn.setDisable(true);
			importFileeISSNColumnLabel.setDisable(true);
			if(exportColumnWhiteListFilterHbox.getChildren().contains(exportColumnWhiteListFilterColumnTextField))
			{
				exportColumnWhiteListFilterHbox.getChildren().remove(exportColumnWhiteListFilterColumnTextField);						
			}
			if(!exportColumnWhiteListFilterHbox.getChildren().contains(exportColumnWhiteListFilterColumnComboBox))
			{
				exportColumnWhiteListFilterHbox.getChildren().add(exportColumnWhiteListFilterColumnComboBox);
			}
						
		} else
		{
			
			importFileTypeeISSNColumn.setEditable(true);
			importFileTypeISSNColumn.setEditable(true);
			importFileTypeTitleColumn.setEditable(true);
			importFileTypeISSNColumn.setDisable(false);
			importFileTypeTitleColumn.setDisable(false);
			importFileISSNColumnLabel.setDisable(false);
			importFileTitleColumnLabel.setDisable(false);
			importFileTypeeISSNColumn.setDisable(false);
			importFileeISSNColumnLabel.setDisable(false);
			importFileTypeISSNColumn.setText(this.propertyFileHandler.propertyFileModel.getImportFileTypeISSNColumn());
			importFileTypeeISSNColumn.setText(this.propertyFileHandler.propertyFileModel.getImportFileTypeeISSNColumn());
			importFileTypeTitleColumn.setText(this.propertyFileHandler.propertyFileModel.getImportFileTypeTitleColumn()); 
			if(exportColumnWhiteListFilterHbox.getChildren().contains(exportColumnWhiteListFilterColumnComboBox))
			{
				exportColumnWhiteListFilterHbox.getChildren().remove(exportColumnWhiteListFilterColumnComboBox);
			}
			if(!exportColumnWhiteListFilterHbox.getChildren().contains(exportColumnWhiteListFilterColumnTextField))
			{
			exportColumnWhiteListFilterHbox.getChildren().add(exportColumnWhiteListFilterColumnTextField);
			}
		}
		if(!exportColumnWhiteListFilterHbox.getChildren().contains(exportColumnWhiteListFilterLabelFilter))
		{
		exportColumnWhiteListFilterHbox.getChildren().add(exportColumnWhiteListFilterLabelFilter);
		}
		if(!exportColumnWhiteListFilterHbox.getChildren().contains(exportColumnWhiteListFilterFilterTextField))
		{
		exportColumnWhiteListFilterHbox.getChildren().add(exportColumnWhiteListFilterFilterTextField);
		}
		
		exportColumnWhiteListFilterHbox.getChildren().add(propertiesWhitelistExportButtonHelpText);
		
		exportColumnWhiteListFilterLabelFilter.setDisable(!exportColumnWhiteListCheckbox.isSelected());
		exportColumnWhiteListFilterLabelColumn.setDisable(!exportColumnWhiteListCheckbox.isSelected());        			
		exportColumnWhiteListFilterFilterTextField.setDisable(!exportColumnWhiteListCheckbox.isSelected());
		exportColumnWhiteListFilterColumnComboBox.setDisable(!exportColumnWhiteListCheckbox.isSelected());
		exportColumnWhiteListFilterColumnTextField.setDisable(!exportColumnWhiteListCheckbox.isSelected());
		
	}
	
}
