package userInterface;

import java.util.List;

import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import logic.TextFormatter;
import var.Constants;

public class PickerTab {
	
	public Tab pickerTab;
	
	public HBox hBoxVBoxContainer;
	public VBox leftVBox;
	public VBox middleVBox;
	public VBox rightVBox;
	
	public TextArea picks;
	public TextArea queue;
	public TextArea nopes;

	
	
	public PickerTab(List<List<String>> titles) {
		

		initiateElements();
		configureElements();
		
		TextFormatter textFormatter = new TextFormatter(titles);
		queue.setText(textFormatter.nextTitle());		
		
		
		 this.middleVBox.addEventFilter(KeyEvent.KEY_RELEASED, keyEvent -> {
			 	if (keyEvent.getCode() == KeyCode.UP)
			 	{
			 		this.queue.setText(textFormatter.formerTitle());
			 	}
			 	if (keyEvent.getCode() == KeyCode.DOWN)
			 	{
			 		this.queue.setText(textFormatter.nextTitle());	
			 	}
			 	if (keyEvent.getCode() == KeyCode.RIGHT)
			 	{		
			 		this.queue.setText(textFormatter.pickCurrentTitle());
			 		this.picks.setText(textFormatter.fetchTitlesPicks());
			 		
			 	}
			 	if (keyEvent.getCode() == KeyCode.LEFT)
			 	{	
			 		this.queue.setText(textFormatter.denyCurrentTitle());
			 		this.nopes.setText(textFormatter.fetchTitlesNopes());
			 		
			 	}
	            
	        });		
		 
		 
	}	
	
	
	
	private void initiateElements()
	{
		this.pickerTab = new Tab(Constants.ChooserTabName);
		
		this.queue = new TextArea();
		this.picks = new TextArea();
		this.nopes = new TextArea();
		
		this.hBoxVBoxContainer = new HBox();
		this.leftVBox = new VBox(200, nopes);
		this.middleVBox = new VBox(200, queue);
		this.rightVBox = new VBox(200, picks);
		
		
		
	}
	
	private void configureElements()
	{
		
		
		this.pickerTab.setClosable(false);		
		this.queue.setEditable(false);
		this.queue.setPrefSize(400, 400);
		this.picks.setEditable(false);
		this.nopes.setEditable(false);
		this.picks.setPrefSize(400, 400);
		this.nopes.setPrefSize(400, 400);

		this.hBoxVBoxContainer.getChildren().add(this.leftVBox);
		this.hBoxVBoxContainer.getChildren().add(this.middleVBox);
		this.hBoxVBoxContainer.getChildren().add(this.rightVBox);
		
		this.pickerTab.setContent(this.hBoxVBoxContainer);
	}
	
}

