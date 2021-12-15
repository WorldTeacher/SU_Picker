package userInterface;

import java.io.IOException;
import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import logic.QueueManager;
import var.Constants;

public class PickerTab {
	
	public Tab pickerTab;
	
	public GridPane gridpane;	
	
	public HBox currentsHBox;
	public HBox nextsHBox;
	public HBox formersHBox;
	
	public VBox currentsleftVBox;
	public VBox currentsmiddleVBox;
	public VBox currentsrightVBox;
	public VBox formersleftVBox;
	public VBox formersmiddleVBox;
	public VBox formersrightVBox;
	public VBox nextsleftVBox;
	public VBox nextsmiddleVBox;
	public VBox nextsrightVBox;
	
	public TextArea currentspicks;
	public TextArea currentsqueue;
	public TextArea currentsnopes;
	public TextArea formerspicks;
	public TextArea formersqueue;
	public TextArea formersnopes;
	public TextArea nextspicks;
	public TextArea nextsqueue;
	public TextArea nextsnopes;
	
	/* last action 0: none
	 * last action 1: swipeLeft
	 * last action 2: swipeRight
	 */
	private int lastAction = 0;

	public PickerTab()
	{
		this.pickerTab = new Tab(Constants.ChooserTabName);
		this.pickerTab.setClosable(false);
	}	
	
	public void rebuild(List<List<String>> titles) {

		initiateElements();
		configureElements();
		
		QueueManager queueManager = new QueueManager(titles);
		
		this.currentsqueue.setText(queueManager.fetchFormattedTitleQueue());
 		this.nextsqueue.setText(queueManager.fetchNextTitleListQueue());
		this.currentsnopes.setText(queueManager.fetchFormattedTitleNope());
 		this.nextsnopes.setText(queueManager.fetchNextTitleListNope());
		
		
		 this.currentsqueue.addEventFilter(KeyEvent.KEY_RELEASED, keyEvent -> {
			 	if (keyEvent.getCode() == KeyCode.ENTER)
			 	{
			 		if (keyEvent.isShiftDown())
			 		{
			 			queueManager.saveProgress();
			 		} else
			 		{
				 		queueManager.exportPicks();
			 		}
			 			
			 	}	
			 	if (keyEvent.getCode() == KeyCode.SPACE)
			 	{
			 		try {
						queueManager.openQueueCurrentLink();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}			 		
			 	}			 
			 	if (keyEvent.getCode() == KeyCode.UP)
			 	{
			 		queueManager.formerTitleQueue();
			 		
			 		this.currentsqueue.setText(queueManager.fetchFormattedTitleQueue());
			 		this.nextsqueue.setText(queueManager.fetchNextTitleListQueue());
			 		this.formersqueue.setText(queueManager.fetchFormerTitleListQueue());
			 		
			 	}
			 	if (keyEvent.getCode() == KeyCode.DOWN)
			 	{
			 		queueManager.nextTitleQueue();
			 		
			 		this.currentsqueue.setText(queueManager.fetchFormattedTitleQueue());
			 		this.nextsqueue.setText(queueManager.fetchNextTitleListQueue());
			 		this.formersqueue.setText(queueManager.fetchFormerTitleListQueue());			 	
			 	}
			 	if (keyEvent.getCode() == KeyCode.RIGHT)
			 	{		
			 		if (keyEvent.isControlDown())
			 		{
			 			queueManager.setZAFlag();
			 		}
			 		if (keyEvent.isShiftDown())
			 		{
			 			queueManager.setSwitchFlag();
			 		}
			 		queueManager.pickCurrentTitle();
			 		this.lastAction = 2;
			 		
					this.currentsqueue.setText(queueManager.fetchFormattedTitleQueue());
			 		this.nextsqueue.setText(queueManager.fetchNextTitleListQueue());
			 		this.formersqueue.setText(queueManager.fetchFormerTitleListQueue());
			 		
					this.currentsnopes.setText(queueManager.fetchFormattedTitleNope());
			 		this.nextsnopes.setText(queueManager.fetchNextTitleListNope());
			 		this.formersnopes.setText(queueManager.fetchFormerTitleListNope());
			 		
					this.currentspicks.setText(queueManager.fetchFormattedTitlePick());
			 		this.nextspicks.setText(queueManager.fetchNextTitleListPick());
			 		this.formerspicks.setText(queueManager.fetchFormerTitleListPick());
			 		
			 		
			 	}
			 	if (keyEvent.getCode() == KeyCode.LEFT)
			 	{	
			 		queueManager.denyCurrentTitle();
			 		this.lastAction = 1;
			 		
					this.currentsqueue.setText(queueManager.fetchFormattedTitleQueue());
			 		this.nextsqueue.setText(queueManager.fetchNextTitleListQueue());
			 		this.formersqueue.setText(queueManager.fetchFormerTitleListQueue());
			 		
					this.currentsnopes.setText(queueManager.fetchFormattedTitleNope());
			 		this.nextsnopes.setText(queueManager.fetchNextTitleListNope());
			 		this.formersnopes.setText(queueManager.fetchFormerTitleListNope());
			 		
					this.currentspicks.setText(queueManager.fetchFormattedTitlePick());
			 		this.nextspicks.setText(queueManager.fetchNextTitleListPick());
			 		this.formerspicks.setText(queueManager.fetchFormerTitleListPick());			
			 		
			 	}
			 	if (keyEvent.getCode() == KeyCode.BACK_SPACE)
			 	{				 			
			 		if (this.lastAction == 1)
			 		{
				 		queueManager.undoChoiceNopes();
				 		
						this.currentsqueue.setText(queueManager.fetchFormattedTitleQueue());
				 		this.nextsqueue.setText(queueManager.fetchNextTitleListQueue());
				 		this.formersqueue.setText(queueManager.fetchFormerTitleListQueue());
				 		
						this.currentsnopes.setText(queueManager.fetchFormattedTitleNope());
				 		this.nextsnopes.setText(queueManager.fetchNextTitleListNope());
				 		this.formersnopes.setText(queueManager.fetchFormerTitleListNope());			 			
			 		}
			 		else if  (this.lastAction == 2)
			 			{			 	
			 			queueManager.undoChoicePicks();
			 			queueManager.resetFlag();
			 		
			 			this.currentsqueue.setText(queueManager.fetchFormattedTitleQueue());
			 			this.nextsqueue.setText(queueManager.fetchNextTitleListQueue());
			 			this.formersqueue.setText(queueManager.fetchFormerTitleListQueue());
			 		
			 			this.currentspicks.setText(queueManager.fetchFormattedTitlePick());
			 			this.nextspicks.setText(queueManager.fetchNextTitleListPick());
			 			this.formerspicks.setText(queueManager.fetchFormerTitleListPick());	
			 			
			 		}
			 		this.lastAction = 0;
			 	}
			 	
			 	
	            
	        });		
		 
		 this.currentsnopes.addEventFilter(KeyEvent.KEY_RELEASED, keyEvent -> {			 
			 if (keyEvent.getCode() == KeyCode.ENTER)
			 	{
			 		queueManager.exportPicks();
			 	}	
			 	if (keyEvent.getCode() == KeyCode.SPACE)
			 	{
			 		try {
						queueManager.openNopeCurrentLink();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}			 		
			 	}
			 	if (keyEvent.getCode() == KeyCode.UP)
			 	{
			 		queueManager.formerTitleNopes();
			 		
			 		this.currentsnopes.setText(queueManager.fetchFormattedTitleNope());
			 		this.nextsnopes.setText(queueManager.fetchNextTitleListNope());
			 		this.formersnopes.setText(queueManager.fetchFormerTitleListNope());
			 		
			 	}
			 	if (keyEvent.getCode() == KeyCode.DOWN)
			 	{
			 		queueManager.nextTitleNopes();
			 		
			 		this.currentsnopes.setText(queueManager.fetchFormattedTitleNope());
			 		this.nextsnopes.setText(queueManager.fetchNextTitleListNope());
			 		this.formersnopes.setText(queueManager.fetchFormerTitleListNope());			 	
			 	}
			 	if (keyEvent.getCode() == KeyCode.RIGHT)
			 	{	
			 		
			 		queueManager.undoChoiceNopes();
			 		
					this.currentsqueue.setText(queueManager.fetchFormattedTitleQueue());
			 		this.nextsqueue.setText(queueManager.fetchNextTitleListQueue());
			 		this.formersqueue.setText(queueManager.fetchFormerTitleListQueue());
			 		
					this.currentsnopes.setText(queueManager.fetchFormattedTitleNope());
			 		this.nextsnopes.setText(queueManager.fetchNextTitleListNope());
			 		this.formersnopes.setText(queueManager.fetchFormerTitleListNope());
			 		
			 		
			 	}
			 	if (keyEvent.getCode() == KeyCode.LEFT)
			 	{	
			 		//
			 	}
	            
	        });	
		 
		 this.currentspicks.addEventFilter(KeyEvent.KEY_RELEASED, keyEvent -> {
			 
			 if (keyEvent.getCode() == KeyCode.ENTER)
			 	{
			 		queueManager.exportPicks();
			 	}	
			 	if (keyEvent.getCode() == KeyCode.SPACE)
			 	{
			 		try {
						queueManager.openPickCurrentLink();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}			 		
			 	}
			 if (keyEvent.getCode() == KeyCode.UP)
			 	{
			 		queueManager.formerTitlePicks();
			 		
			 		this.currentspicks.setText(queueManager.fetchFormattedTitlePick());
			 		this.nextspicks.setText(queueManager.fetchNextTitleListPick());
			 		this.formerspicks.setText(queueManager.fetchFormerTitleListPick());
			 		
			 	}
			 	if (keyEvent.getCode() == KeyCode.DOWN)
			 	{
			 		queueManager.nextTitlePicks();
			 		
			 		this.currentspicks.setText(queueManager.fetchFormattedTitlePick());
			 		this.nextspicks.setText(queueManager.fetchNextTitleListPick());
			 		this.formerspicks.setText(queueManager.fetchFormerTitleListPick());			 	
			 	}
			 	if (keyEvent.getCode() == KeyCode.RIGHT)
			 	{	
			 		//
			 		
			 	}
			 	if (keyEvent.getCode() == KeyCode.LEFT)
			 	{				 		
			 		queueManager.undoChoicePicks();
			 		queueManager.resetFlag();
			 		
					this.currentsqueue.setText(queueManager.fetchFormattedTitleQueue());
			 		this.nextsqueue.setText(queueManager.fetchNextTitleListQueue());
			 		this.formersqueue.setText(queueManager.fetchFormerTitleListQueue());
			 		
					this.currentspicks.setText(queueManager.fetchFormattedTitlePick());
			 		this.nextspicks.setText(queueManager.fetchNextTitleListPick());
			 		this.formerspicks.setText(queueManager.fetchFormerTitleListPick());			
			 		
			 	}
	            
	        });	
		 
		 
	}	
	
	private void initiateElements()
	{
		this.pickerTab = new Tab(Constants.ChooserTabName);
		
		this.currentsqueue = new TextArea();
		this.currentspicks = new TextArea();
		this.currentsnopes = new TextArea();
		this.formersqueue = new TextArea();
		this.formerspicks = new TextArea();
		this.formersnopes = new TextArea();
		this.nextsqueue = new TextArea();
		this.nextspicks = new TextArea();
		this.nextsnopes = new TextArea();
		
		
		
		this.gridpane = new GridPane();
		
		this.currentsHBox = new HBox();
		this.nextsHBox = new HBox();
		this.formersHBox = new HBox();
		
		this.currentsleftVBox = new VBox(200, currentsnopes);
		this.currentsmiddleVBox = new VBox(200, currentsqueue);
		this.currentsrightVBox = new VBox(200, currentspicks);
		this.formersleftVBox = new VBox(200, currentsnopes);
		this.formersmiddleVBox = new VBox(200, currentsqueue);
		this.formersrightVBox = new VBox(200, currentspicks);
		this.nextsleftVBox = new VBox(200, currentsnopes);
		this.nextsmiddleVBox = new VBox(200, currentsqueue);
		this.nextsrightVBox = new VBox(200, currentspicks);
		
		
		
	}
	
	private void configureElements()
	{			
		
		this.pickerTab.setClosable(false);		
		this.currentsqueue.setEditable(false);		
		this.currentspicks.setEditable(false);
		this.currentsnopes.setEditable(false);
		this.formersqueue.setEditable(false);		
		this.formerspicks.setEditable(false);
		this.formersnopes.setEditable(false);
		this.nextsqueue.setEditable(false);		
		this.nextspicks.setEditable(false);
		this.nextsnopes.setEditable(false);
		
		this.currentsqueue.setWrapText(true);
		this.currentspicks.setWrapText(true);
		this.currentsnopes.setWrapText(true);
	
		this.currentsqueue.setPrefSize(1200, 400);
		this.currentspicks.setPrefSize(1200, 400);
		this.currentsnopes.setPrefSize(1200, 400);
		this.formersqueue.setPrefSize(1200, 400);
		this.formerspicks.setPrefSize(1200, 400);
		this.formersnopes.setPrefSize(1200, 400);
		this.nextsqueue.setPrefSize(1200, 400);
		this.nextspicks.setPrefSize(1200, 400);
		this.nextsnopes.setPrefSize(1200, 400);	
	
		this.gridpane.setHgap(10);
		this.gridpane.setVgap(10);
		this.gridpane.setPadding(new Insets(10,10,10,10));
		this.gridpane.add(this.formersnopes, 0, 0);
		this.gridpane.add(this.formersqueue, 1, 0);
		this.gridpane.add(this.formerspicks, 2, 0);
		this.gridpane.add(this.currentsnopes, 0, 1);
		this.gridpane.add(this.currentsqueue, 1, 1);
		this.gridpane.add(this.currentspicks, 2, 1);
		this.gridpane.add(this.nextsnopes, 0, 2);
		this.gridpane.add(this.nextsqueue, 1, 2);
		this.gridpane.add(this.nextspicks, 2, 2);
		
		this.pickerTab.setContent(gridpane);		
		HBox.setHgrow(gridpane, Priority.ALWAYS);
		
/*
		
		this.hBoxVBoxContainer.getChildren().add(this.leftVBox);
		this.hBoxVBoxContainer.getChildren().add(this.middleVBox);
		this.hBoxVBoxContainer.getChildren().add(this.rightVBox);	
		
		this.pickerTab.setContent(this.hBoxVBoxContainer);
		
		*/
		
	}
	
}

