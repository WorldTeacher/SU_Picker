package userInterface;

import javafx.scene.control.Tab;
import var.Constants;

public class ExportTab {
	
	public Tab exportTab;
	
	public ExportTab() {
		this.exportTab = new Tab(Constants.ExportTabName);
		this.exportTab.setClosable(false);
	}
	

	
}
