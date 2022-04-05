package userInterface_reiheAPicker;

import javafx.scene.control.Tab;
import var_reiheAPicker.copy.Constants;

public class HistoryTab {
	
	public Tab historyTab;
	
	public HistoryTab() {
		this.historyTab = new Tab(Constants.HistoryTabName);
		this.historyTab.setClosable(false);
	}
	

	
}
