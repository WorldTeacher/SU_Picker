package userInterface_reiheAPicker;

import javafx.scene.control.Tab;
import var_reiheAPicker.copy.Constants;

public class ExportTab {
	
	public Tab exportTab;
	
	public ExportTab() {
		this.exportTab = new Tab(Constants.ExportTabName);
		this.exportTab.setClosable(false);
	}
	

	
}
