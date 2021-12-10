package userInterface;


import java.io.IOException;
import java.util.List;


import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import logic.ImportExportFileHandler;
import var.Constants;

public class ImportSettingsTab {
	
	public Tab importSettingsTab;
	public List<List<String>> titles = null;

	
	HBox topLineHbox = new HBox();
	
	Button button_import = new Button();
	
	public ImportSettingsTab() throws IOException {
		initiateElements();
		configureElements();	
		this.titles = ImportExportFileHandler.importCsv();

	}
	
	private void initiateElements()
	{
		this.importSettingsTab = new Tab(Constants.ImportSettingsTabName);
		this.button_import = new Button(Constants.ImportButtonName);
		this.topLineHbox = new HBox(30, button_import);
	}
	
	private void configureElements()
	{
		this.importSettingsTab.setClosable(false);
		this.importSettingsTab.setContent(topLineHbox);
	}
	



	

				
}