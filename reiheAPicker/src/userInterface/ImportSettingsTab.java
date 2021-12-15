package userInterface;


import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBox;
import logic.ImportExportFileHandler;
import logic.PropertyFileHandler;
import models.PropertyModel;
import var.Constants;

public class ImportSettingsTab {
	
	public Tab importSettingsTab;
	public List<List<String>> titles = null;
	PropertyFileHandler propertyFilehandler = PropertyFileHandler.getInstance();
	
	public GridPane gridpane;	
	
	public VBox globalVBox;
	
	public Label settingsLabel1;
	public Label settingsLabel2;
	public Label settingsLabel3;
	public Label settingsLabel4;
	public Label settingsLabel5;
	public Label settingsLabel6;
	public Label settingsLabel7;
	
	public TextField settingsTextField1;
	public TextField settingsTextField2;
	public TextField settingsTextField3;
	public TextField settingsTextField4;
	public TextField settingsTextField5;
	public TextField settingsTextField6;
	public TextField settingsTextField7;
	public Button saveSettingsButton;
	
	VBox topLineHbox = new VBox();
		
	public ImportSettingsTab() throws IOException {
		initiateElements();
		configureElements();
	}	
	
	private void initiateElements()
	{
		this.importSettingsTab = new Tab(Constants.ImportSettingsTabName);
		this.gridpane = new GridPane();
		
		this.globalVBox = new VBox();
		
		this.settingsLabel1 = new Label(propertyFilehandler.propertyFileModel.get_labels_ExportFileFolder());
		this.settingsLabel2 = new Label(propertyFilehandler.propertyFileModel.get_labels_DDCBlacklist());
		this.settingsLabel3 = new Label(propertyFilehandler.propertyFileModel.get_labels_DDCWhitelist());		
		this.settingsLabel4 = new Label(propertyFilehandler.propertyFileModel.get_labels_PublisherBlacklist());
		this.settingsLabel5 = new Label(propertyFilehandler.propertyFileModel.get_labels_RemoveDuplicates());
		this.settingsLabel6 = new Label(propertyFilehandler.propertyFileModel.get_labels_BlockBelletristik());
		this.settingsLabel7 = new Label(propertyFilehandler.propertyFileModel.get_labels_SaveFileFolder());
		
		this.settingsTextField1 = new TextField(propertyFilehandler.propertyFileModel.get_settings_ExportFileFolder());
		this.settingsTextField2 = new TextField(propertyFilehandler.propertyFileModel.get_settings_DDCBlacklist());
		this.settingsTextField3 = new TextField(propertyFilehandler.propertyFileModel.get_settings_DDCWhitelist());		
		this.settingsTextField4 = new TextField(propertyFilehandler.propertyFileModel.get_settings_PublisherBlacklist());
		this.settingsTextField5 = new TextField(propertyFilehandler.propertyFileModel.get_settings_RemoveDuplicates());
		this.settingsTextField6 = new TextField(propertyFilehandler.propertyFileModel.get_settings_BlockBelletristik());
		this.settingsTextField7 = new TextField(propertyFilehandler.propertyFileModel.get_settings_SaveFileFolder());
		this.saveSettingsButton = new Button("Einstellungen übernehmen und speichern!");

		
	}
	
	private void configureElements()
	{
		this.importSettingsTab.setClosable(false);
				
		this.gridpane.add(this.settingsLabel1, 0, 0); 	this.gridpane.add(this.settingsTextField1, 1, 0);
		this.gridpane.add(this.settingsLabel7, 0, 1);	this.gridpane.add(this.settingsTextField7, 1, 1);
		this.gridpane.add(this.settingsLabel2, 0, 2); 	this.gridpane.add(this.settingsTextField2, 1, 2);
		this.gridpane.add(this.settingsLabel3, 0, 3);	this.gridpane.add(this.settingsTextField3, 1, 3);
		this.gridpane.add(this.settingsLabel4, 0, 4);	this.gridpane.add(this.settingsTextField4, 1, 4);
		this.gridpane.add(this.settingsLabel5, 0, 5);	this.gridpane.add(this.settingsTextField5, 1, 5);
		this.gridpane.add(this.settingsLabel6, 0, 6);	this.gridpane.add(this.settingsTextField6, 1, 6);
																											this.gridpane.add(this.saveSettingsButton, 2, 6);
				
		this.gridpane.setHgap(10);
		this.gridpane.setVgap(10);
		this.gridpane.setPadding(new Insets(10,10,10,10));
		
		saveSettingsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	
            	try {
					updateAndSaveProperties();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
 
            }
        });
		
		
		this.globalVBox.getChildren().addAll(this.topLineHbox, this.gridpane);
		
		this.importSettingsTab.setContent(this.globalVBox);
		
	}	
	
	private void updateAndSaveProperties() throws IOException
	{
		this.propertyFilehandler.propertyFileModel.updateProperties(this.settingsTextField1.getText(),this.settingsTextField7.getText(), this.settingsTextField4.getText(),this.settingsTextField5.getText(),this.settingsTextField2.getText(),this.settingsTextField3.getText(), this.settingsTextField6.getText());
		this.propertyFilehandler.setConfigDetail();
		
	}
	
	void openFileAndLoad() throws IOException
	{
		this.titles = ImportExportFileHandler.importCsv();
	}


	

				
}