package userInterface_vivi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import logic_vivi.ConvenienceTools;
import logic_vivi.HelpTextFileHandler;
import logic_vivi.ImportExportFileHandler;
import logic_vivi.MultiApiCallTabRunnableThread;
import logic_vivi.PropertyFileHandler;
import models_vivi.ApiMultiCallModel;


/*
 * Functions related to the "scene" for multi calls
 * Name results from intention to enable users to upload files;
 */
public class MultiApiCallTab {
	
	//manipulate this if you want to make the fields wider
	//rest will align automatically
	int TextFieldMinLength = 120;	
	
	public Pane multiApiCallpane;
	
	
	int DefaultTextFieldLength = 149;		
	int TextAreaWidth = ((TextFieldMinLength + DefaultTextFieldLength) * 3) + 5 * 20;
	int TextAreaHeight = 90;
	int TextAreaParsedHeight_conditions = 160;
	int TextAreaParsedHeight_archive = 40;
	int TextAreaParsedHeight_info = 30;	
	int amountLinesInTextbox = 0;
	HBox hbox;
	HBox hbox_fileImport;
	HBox hbox_fileSave;
	VBox vbox;
	TextArea inputTextArea;
	Button button_submit;
	Button button_import;
	Button button_save;
	ProgressIndicator progressIndicator;
	List<List<String>> importedFileRowsStrings;
	
	Thread newThread;
	
	javafx.stage.FileChooser fileChooser;
	
	//text Area contains initial tips how to use it; will later be used for actual api response!
	TextArea romeoSherpaText = new TextArea("Bitte wählen Sie eine Datei aus. Importoptionen finden Sie im Optionsmenü.");		
	Label labelISSN;
	Label labelFileImport;
	
	MultiApiCallTabRunnableThread multiApiCallTabRunnableThread;	
	
	//HelpTexts
	String HelpImagePath = "res/info-small.png";
	Button MultiApiCallChooseFileHelpButton;	
	
	//initializes multi-call scene (=upload scene)
	public MultiApiCallTab(Stage primaryStage)
	{	
		//for help Texts
		//helpTexts and buttons
		HelpTextFileHandler helpTextFileHandler = HelpTextFileHandler.getInstance();		
		
		Image helpImage = ConvenienceTools.getPictureFromResources(HelpImagePath);	
		this.MultiApiCallChooseFileHelpButton = new Button();			
		this.MultiApiCallChooseFileHelpButton.setGraphic(ConvenienceTools.getImageViewForHelpTexts(helpImage));
		ConvenienceTools.getHelpButtonEvent(this.MultiApiCallChooseFileHelpButton, helpTextFileHandler.helpTextModel.getMultiApiCallChooseFile());
		
		romeoSherpaText.setEditable(false);
		this.inputTextArea = new TextArea();
		this.inputTextArea.setDisable(true);
		this.inputTextArea.setEditable(false);
		this.inputTextArea.setMinWidth(TextAreaWidth - (2 * DefaultTextFieldLength));
		this.button_submit = new Button();
		this.button_submit.setMinWidth(30);
		this.button_submit.setText("Absenden");		
		
		this.button_import = new Button();
		this.button_import.setMinWidth(30);
		this.button_import.setText("Datei auswählen");
		
		this.button_save = new Button();
		this.button_save.setMinWidth(30);
		this.button_save.setText("Ausführliche Excel Datei speichern");
		
		this.labelISSN = new Label("Importinformationen:");		
		this.labelFileImport = new Label("Datei auswählen:");
		this.labelISSN.setMinWidth(120);
		this.labelFileImport.setMinWidth(120);
		this.progressIndicator = new ProgressIndicator();
		this.progressIndicator.setVisible(false);
	    
		this.romeoSherpaText.setMaxHeight(TextAreaParsedHeight_conditions);
		this.romeoSherpaText.setMinWidth(TextAreaWidth);
	
		this.getSubmitButtonEvent();
		this.getButton_import_Event(primaryStage);		
		this.getSaveButtonEvent(primaryStage);
		
		this.hbox_fileSave = new HBox(20, button_save);	
		this.hbox_fileSave.setMinWidth(TextAreaWidth);
		this.hbox_fileSave.setAlignment(Pos.CENTER_RIGHT);
		this.button_save.setVisible(false);
		this.hbox_fileImport = new HBox(20, labelFileImport, button_import,	MultiApiCallChooseFileHelpButton);
		
		this.hbox = new HBox(20, labelISSN, inputTextArea, button_submit, progressIndicator);		
		this.vbox = new VBox(20, romeoSherpaText);	

		this.hbox.setLayoutY(50);	
		this.hbox_fileImport.setLayoutY(20);
		this.hbox_fileSave.setLayoutY(440);			
		this.vbox.setLayoutY(270);
		this.hbox.setLayoutX(20);
		this.vbox.setLayoutX(20);	
		this.hbox_fileImport.setLayoutX(20);
		this.hbox_fileSave.setLayoutX(20);
		//this.vbox = new VBox(labelRomeoSherpa, romeoSherpaText, labelOaDoi, oaDoiText, labelDoiOrg, doajText);
		this.multiApiCallpane = new Pane();
		this.multiApiCallpane.getChildren().addAll(this.hbox, this.hbox_fileImport, this.hbox_fileSave, this.vbox);
		this.inputTextArea.setFont(Font.font("Monospaced", 12));
		this.romeoSherpaText.setFont(Font.font("Monospaced", 12));
	
		
		//Call API if "Enter" is being used
		this.inputTextArea.setOnKeyReleased(new EventHandler<KeyEvent>() {			        
			        @Override
			        public void handle(KeyEvent event) {
			            if(event.getCode().equals(KeyCode.ENTER)) {			            
			            		getSubmitButtonEvent();
								}
			        }
			    });
	
	}
	
	
	//starts api-process
	private void getSubmitButtonEvent()
	{		
		
		this.button_submit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {		
				//text: User input; todo: Function to upload file directly instead of copying contents	
					
					progressIndicator.setVisible(false);
					progressIndicator.setProgress(0);
					button_save.setVisible(false);
					
					if (importedFileRowsStrings == null || importedFileRowsStrings.size() < 2)
					{
						inputTextArea.setText("No Input detected!");
					}
					romeoSherpaText.setText("Prozess gestartet. Bitte beachten Sie die Fortschrittsanzeige!");
				  	
					
					multiApiCallTabRunnableThread = new MultiApiCallTabRunnableThread(importedFileRowsStrings, progressIndicator, button_save, romeoSherpaText);					
					newThread = new Thread(multiApiCallTabRunnableThread);				
					newThread.start();			
		}});		
	}
	
	
	//Definition of Event: Take BulkModel and Save it in an Excel File
	private void getSaveButtonEvent(Stage primaryStage)
	{				
		this.button_save.setOnAction(new EventHandler<ActionEvent>() {			
			@Override
			public void handle(ActionEvent event) {				
				progressIndicator.setVisible(true);
				romeoSherpaText.clear();
				//text: User input; todo: Function to upload file directly instead of copying contents	
				javafx.stage.FileChooser fileChooser = new javafx.stage.FileChooser();
				fileChooser.setTitle("Excel Datei speichern");	
				 FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Files", ".xlsx");
				fileChooser.getExtensionFilters().add(extFilter);
				File fileChosen = fileChooser.showSaveDialog(primaryStage);
				if (fileChosen != null)
				{					
					try {
						ImportExportFileHandler.exportExcelList((ArrayList<ApiMultiCallModel>) multiApiCallTabRunnableThread.getFinalResultListApiMultiCallModel(), fileChosen.getAbsolutePath());
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else 
				{					
					inputTextArea.setText("Datei konnte nicht gespeichert werden. Bitte versuchen Sie es erneut!");
				}
			}
		});			 
	}
	
	
	//starts file-chooser
	private void getButton_import_Event(Stage primaryStage)
	{		
		
		this.button_import.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				
				inputTextArea.clear();
				progressIndicator.setVisible(false);
				amountLinesInTextbox = 0;
				//text: User input; 	
				javafx.stage.FileChooser fileChooser = new javafx.stage.FileChooser();
				PropertyFileHandler propertyFileHandler = PropertyFileHandler.getInstance();
				fileChooser.setTitle(propertyFileHandler.propertyFileModel.getImportFileTypeComboBox());	
				fileChooser.getExtensionFilters().addAll(new ExtensionFilter("All Files", "*.*"));
				File fileChosen = fileChooser.showOpenDialog(primaryStage);
				if (fileChosen != null)
				{	
					
				  try {
					  	importedFileRowsStrings = ImportExportFileHandler.getFileStringListFromFile(fileChosen);
					  	if (importedFileRowsStrings == null)
					  	{
					  		romeoSherpaText.setText("Import gescheitert. Bitte prüfen Sie daten und Einstellungen!");
					  	} else
					  	{
					  		
					  		inputTextArea.setText(ImportExportFileHandler.getFirstTwoLinesFromFileImportAndAnalyzeImport(importedFileRowsStrings));
							romeoSherpaText.setText("Sie können den Prozess nun starten!");
					  	}
					  	inputTextArea.setDisable(false);					  
						
					  } catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}else 
				{
					inputTextArea.setText("Vorgang abgebrochen oder Datei nicht gefunden.");
				}
			}
		});		
	}
	
}


