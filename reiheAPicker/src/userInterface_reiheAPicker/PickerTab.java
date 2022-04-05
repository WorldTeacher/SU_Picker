package userInterface_reiheAPicker;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import logic_reiheAPicker.GetButtonPics;
import logic_reiheAPicker.ImportExportFileHandler;
import logic_reiheAPicker.PropertyFileHandler;
import logic_reiheAPicker.QueueManager;
import models_reiheAPicker.CustomTextAreaPickerTab;
import models_reiheAPicker.ListEnum;
import var_reiheAPicker.copy.Constants;

public class PickerTab {
	
	public Tab pickerTab;
	
	public GridPane gridpaneLower;	
	public PropertyFileHandler propertyFileHandler = PropertyFileHandler.getInstance();
	
	public HBox currentsHBox;
	public HBox nextsHBox;
	public HBox formersHBox;
	public HBox shortCutHBox;
	
	public Text progressText;
	
	public VBox currentsleftVBox;
	public VBox currentsmiddleVBox;
	public VBox currentsrightVBox;
	public VBox formersleftVBox;
	public VBox formersmiddleVBox;
	public VBox formersrightVBox;
	public VBox nextsleftVBox;
	public VBox nextsmiddleVBox;
	public VBox nextsrightVBox;
	
	public CustomTextAreaPickerTab currentspicks;
	public CustomTextAreaPickerTab currentsqueue;
	public CustomTextAreaPickerTab currentsnopes;	
	
	public Label textA1;
	public Label textA2;
	public Label textA3;
	public Label textA4;
	public Label textA5;
	public Label textA6;
	public Label textA7;
	public Label textA8;
	public Label textA9;
	public Label textA10;

	public Label textB1;
	public Label textB2;
	public Label textB3;
	public Label textB4;
	public Label textB5;
	public Label textB6;
	public Label textB7;
	public Label textB8;
	public Label textB9;
	public Label textB10;

	public Label textC1;
	public Label textC2;
	public Label textC3;
	public Label textC4;
	public Label textC5;
	public Label textC6;
	public Label textC7;
	public Label textC8;
	public Label textC9;
	public Label textC10;

	public Label textD1;
	public Label textD2;
	public Label textD3;
	public Label textD4;
	public Label textD5;
	public Label textD6;
	public Label textD7;
	public Label textD8;
	public Label textD9;
	public Label textD10;

	public Label textE1;
	public Label textE2;
	public Label textE3;
	public Label textE4;
	public Label textE5;
	public Label textE6;
	public Label textE7;
	public Label textE8;
	public Label textE9;
	public Label textE10;

	public Label textF1;
	public Label textF2;
	public Label textF3;
	public Label textF4;
	public Label textF5;
	public Label textF6;
	public Label textF7;
	public Label textF8;
	public Label textF9;
	public Label textF10;
	
	public HBox markedKeyWordsHBoxNopes;
	public HBox markedKeyWordsHBoxQueue;
	public HBox markedKeyWordsHBoxPicks;
	public Label markedKeyWordsNopesGreen;
	public Label markedKeyWordsNopesRed;
	public Label markedKeyWordsQueueGreen;
	public Label markedKeyWordsQueueRed;
	public Label markedKeyWordsPicksGreen;
	public Label markedKeyWordsPicksRed;
	public Label markedKeyWordsNopesOA;
	public Label markedKeyWordsQueueOA;
	public Label markedKeyWordsPicksOA;
	
    Image ButtonImage_ctrl;
    Image ButtonImage_space;
    Image ButtonImage_shift;
    Image ButtonImage_backspace;
    Image ButtonImage_F1;
    Image ButtonImage_F2;
    Image ButtonImage_F3;
    Image ButtonImage_F4;
    Image ButtonImage_enter;
    Image ButtonImage_arrowRight;
    Image ButtonImage_verticalLine;
    Image ButtonImage_closedEye;	
    Image QuestionMarkImage;
    Image ButtonImage_alt;
    
    Button helpButton;  
    
    Stage primaryStage;
    public QueueManager queuemanager;
    public int buildStage = -1;
    
	public ProgressBar progressBar;
	
	/* last action 0: none
	 * last action 1: swipeLeft
	 * last action 2: swipeRight
	 */
	private int lastAction = 0;

	public PickerTab(Stage primaryStage)
	{
		this.primaryStage = primaryStage;
		this.pickerTab = new Tab(Constants.ChooserTabName);
		this.pickerTab.setClosable(false);
		this.buildStage = 0;
	}	
	
	private void updateQueueProgress(double number)
	{
		this.progressBar.setProgress(number);	
	}
	

	
	public void rebuild(List<List<String>> titles, ProgressIndicator progressIndicator) throws InterruptedException {

		initiateElements();
		configureElements();
		this.buildStage = 1;		
		
		this.queuemanager = new QueueManager(titles, progressIndicator);		
		
		if (!propertyFileHandler.propertyFileModel.get_settings_API_link().isBlank() &&
				propertyFileHandler.propertyFileModel.get_settings_API_CheckApiAfterImport().equals("true"))
		{
			
		progressIndicator.progressProperty().addListener((observable, oldValue, newValue) -> {
			if ((double) newValue >= (double) 1)
			{
				updateLists();
				progressIndicator.setVisible(false);	
			}			
		});

		}	else
		{
		updateLists();
		}		
		configureEventsGeneral();
		configureEventsQueue();
		configureEventsPicks();
		configureEventsNopes();
	}	
	
	public void updateFormattedTitles(boolean updatenopes, boolean updatequeue, boolean updatepicks, boolean updateApi)
	{
		if (updatenopes)
		{
			this.currentsnopes.setText(queuemanager.fetchFormattedTitle(ListEnum.NOPES, updateApi));	
		}
		if (updatequeue)
		{
			this.currentsqueue.setText(queuemanager.fetchFormattedTitle(ListEnum.QUEUE, updateApi));
		}
		if (updatepicks)
		{
			this.currentspicks.setText(queuemanager.fetchFormattedTitle(ListEnum.PICKS, updateApi));	
		}
	}
	
	private void updateLists()
	{
		
		this.currentsqueue.setVisible(true);		
		this.currentspicks.setVisible(true);
		this.currentsnopes.setVisible(true);
		
		updateQueueProgress(queuemanager.getProgress());
		updateProgressText(queuemanager.getProgressText());
		
		updateQueues(true, true, true);
		updateFormattedTitles(true, true, false, true);		
		 
		updateFormattedTitle(ListEnum.NOPES);
		updateFormattedTitle(ListEnum.QUEUE);
	}
	
	public void resetformersleft()
	{
		this.textA1.setText(" ");
		this.textA2.setText(" ");
		this.textA3.setText(" ");
		this.textA4.setText(" ");
		this.textA5.setText(" ");
		this.textA6.setText(" ");
		this.textA7.setText(" ");
		this.textA8.setText(" ");
		this.textA9.setText(" ");
		this.textA10.setText(" ");
	}
	
	public void resetformersmiddle()
	{
		this.textB1.setText(" ");
		this.textB2.setText(" ");
		this.textB3.setText(" ");
		this.textB4.setText(" ");
		this.textB5.setText(" ");
		this.textB6.setText(" ");
		this.textB7.setText(" ");
		this.textB8.setText(" ");
		this.textB9.setText(" ");
		this.textB10.setText(" ");
	}
	
	public void resetformersright()
	{
		this.textC1.setText(" ");
		this.textC2.setText(" ");
		this.textC3.setText(" ");
		this.textC4.setText(" ");
		this.textC5.setText(" ");
		this.textC6.setText(" ");
		this.textC7.setText(" ");
		this.textC8.setText(" ");
		this.textC9.setText(" ");
		this.textC10.setText(" ");
	}
	
	public void resetnextsleft()
	{
		this.textD1.setText(" ");
		this.textD2.setText(" ");
		this.textD3.setText(" ");
		this.textD4.setText(" ");
		this.textD5.setText(" ");
		this.textD6.setText(" ");
		this.textD7.setText(" ");
		this.textD8.setText(" ");
		this.textD9.setText(" ");
		this.textD10.setText(" ");
	}
	
	public void resetnextsmiddle()
	{
		this.textE1.setText(" ");
		this.textE2.setText(" ");
		this.textE3.setText(" ");
		this.textE4.setText(" ");
		this.textE5.setText(" ");
		this.textE6.setText(" ");
		this.textE7.setText(" ");
		this.textE8.setText(" ");
		this.textE9.setText(" ");
		this.textE10.setText(" ");
	}
	
	public void resetnextsrightt()
	{
		this.textF1.setText(" ");
		this.textF2.setText(" ");
		this.textF3.setText(" ");
		this.textF4.setText(" ");
		this.textF5.setText(" ");
		this.textF6.setText(" ");
		this.textF7.setText(" ");
		this.textF8.setText(" ");
		this.textF9.setText(" ");
		this.textF10.setText(" ");
	}
	
	public String shortenShortTitleString(String title)
	{
		
		
		String titleShort = title.split(" : ")[0];
		if (titleShort.length() > 60)
		{
			titleShort = titleShort.substring(0, 59);
		}
		
		//return titleShort;
		return title;
	}	
	
	public void updateQueues(boolean updateNopes, boolean updateQueue, boolean updatePicks)
	{			
		
		List<List<String>> formersleft;
		List<List<String>> formersright;
		List<List<String>> formersmiddle;
		List<List<String>> nextsleft;
		List<List<String>> nextsright;
		List<List<String>> nextsmiddle;
		
		if (updateNopes)
		{
			formersleft = queuemanager.get10Formers(ListEnum.NOPES);
			nextsleft = queuemanager.get10Nexts(ListEnum.NOPES);
			if (formersleft != null)
			{
				for (int counter = 0; counter <= 9; counter ++) 
				{
					switch (counter)
					{
					case 0:
					{
						if (counter > formersleft.size()-1)
						{
							this.textA10.setText(" ");
						} else
						{
						this.textA10.setText(queuemanager.nopeTitlescurrentIteration - counter + " - " + shortenShortTitleString(formersleft.get(formersleft.size()-1-counter).get(0)));
						break;
						}
					}
					case 1:
					{
						if (counter > formersleft.size()-1)
						{
							this.textA9.setText(" ");
						} else
						{
						this.textA9.setText(queuemanager.nopeTitlescurrentIteration - counter + " - " + shortenShortTitleString(formersleft.get(formersleft.size()-1-counter).get(0)));
						break;
						}
					}
					case 2:
					{
						if (counter > formersleft.size()-1)
						{
							this.textA8.setText(" ");
						} else
						{
						this.textA8.setText(queuemanager.nopeTitlescurrentIteration - counter + " - " + shortenShortTitleString(formersleft.get(formersleft.size()-1-counter).get(0)));
						break;
					}
				}
					case 3:
					{
						if (counter > formersleft.size()-1)
					{
						this.textA7.setText(" ");
					} else
					{
						this.textA7.setText(queuemanager.nopeTitlescurrentIteration - counter + " - " + shortenShortTitleString(formersleft.get(formersleft.size()-1-counter).get(0)));
						break;
					}
				}
					case 4:
					{
						if (counter > formersleft.size()-1)
					{
						this.textA6.setText(" ");
					} else
					{
						this.textA6.setText(queuemanager.nopeTitlescurrentIteration - counter + " - " + shortenShortTitleString(formersleft.get(formersleft.size()-1-counter).get(0)));
						break;
					}
				}
					case 5:
					{
						if (counter > formersleft.size()-1)
					{
						this.textA5.setText(" ");
					} else
					{
						this.textA5.setText(queuemanager.nopeTitlescurrentIteration - counter + " - " + shortenShortTitleString(formersleft.get(formersleft.size()-1-counter).get(0)));
						break;
					}
				}
					case 6:
					{
						if (counter > formersleft.size()-1)
					{
						this.textA4.setText(" ");
					} else
					{
						this.textA4.setText(queuemanager.nopeTitlescurrentIteration - counter + " - " + shortenShortTitleString(formersleft.get(formersleft.size()-1-counter).get(0)));
						break;
					}
				}
					case 7:
					{
						if (counter > formersleft.size()-1)
					{
						this.textA3.setText(" ");
					} else
					{
						this.textA3.setText(queuemanager.nopeTitlescurrentIteration - counter + " - " + shortenShortTitleString(formersleft.get(formersleft.size()-1-counter).get(0)));
						break;
					}
				}
					case 8:
					{
						if (counter > formersleft.size()-1)
					{
						this.textA2.setText(" ");
						break;
					} else
					{
						this.textA2.setText(queuemanager.nopeTitlescurrentIteration - counter + " - " + shortenShortTitleString(formersleft.get(formersleft.size()-1-counter).get(0)));
						break;				
					}				
					}
					case 9:
					{
						if (counter > formersleft.size()-1)
					{
						this.textA1.setText(" ");
						break;
					} else
					{
						this.textA1.setText(queuemanager.nopeTitlescurrentIteration - counter + " - " + shortenShortTitleString(formersleft.get(formersleft.size()-1-counter).get(0)));
						break;				
					}
					}
					}
				}
			} else resetformersleft();
			
			if (nextsleft != null)
			{
				for (int counter = 0; counter <= 9; counter ++) 
				{
					switch (counter)
					{
					case 0:
					{
						if (counter > nextsleft.size()-1)
						{
							this.textD1.setText(" ");
						} else
						{
						this.textD1.setText(queuemanager.nopeTitlescurrentIteration + 2 + counter + " - " + shortenShortTitleString(nextsleft.get(counter).get(0)));
						break;
						}
					}
					case 1:
					{
						if (counter > nextsleft.size()-1)
						{
							this.textD2.setText(" ");
						} else
						{
						this.textD2.setText(queuemanager.nopeTitlescurrentIteration + 2 + counter + " - " + shortenShortTitleString(nextsleft.get(counter).get(0)));
						break;
						}
					}
					case 2:
					{
						if (counter > nextsleft.size()-1)
						{
							this.textD3.setText(" ");
						} else
						{
						this.textD3.setText(queuemanager.nopeTitlescurrentIteration + 2 + counter + " - " + shortenShortTitleString(nextsleft.get(counter).get(0)));
						break;
					}
				}
					case 3:
					{
						if (counter > nextsleft.size()-1)
					{
						this.textD4.setText(" ");
					} else
					{
						this.textD4.setText(queuemanager.nopeTitlescurrentIteration + 2 + counter + " - " + shortenShortTitleString(nextsleft.get(counter).get(0)));
						break;
					}
				}
					case 4:
					{
						if (counter > nextsleft.size()-1)
					{
						this.textD5.setText(" ");
					} else
					{
						this.textD5.setText(queuemanager.nopeTitlescurrentIteration + 2 + counter + " - " + shortenShortTitleString(nextsleft.get(counter).get(0)));
						break;
					}
				}
					case 5:
					{
						if (counter > nextsleft.size()-1)
					{
						this.textD6.setText(" ");
					} else
					{
						this.textD6.setText(queuemanager.nopeTitlescurrentIteration + 2 + counter + " - " + shortenShortTitleString(nextsleft.get(counter).get(0)));
						break;
					}
				}
					case 6:
					{
						if (counter > nextsleft.size()-1)
					{
						this.textD7.setText(" ");
					} else
					{
						this.textD7.setText(queuemanager.nopeTitlescurrentIteration + 2 + counter + " - " + shortenShortTitleString(nextsleft.get(counter).get(0)));
						break;
					}
				}
					case 7:
					{
						if (counter > nextsleft.size()-1)
					{
						this.textD8.setText(" ");
					} else
					{
						this.textD8.setText(queuemanager.nopeTitlescurrentIteration + 2 + counter + " - " + shortenShortTitleString(nextsleft.get(counter).get(0)));
						break;
					}
				}
					case 8:
					{
						if (counter > nextsleft.size()-1)
					{
						this.textD9.setText(" ");
						break;
					} else
					{
						this.textD9.setText(queuemanager.nopeTitlescurrentIteration + 2 + counter + " - " + shortenShortTitleString(nextsleft.get(counter).get(0)));
						break;				
					}				
					}
					case 9:
					{
						if (counter > nextsleft.size()-1)
					{
						this.textD10.setText(" ");
						break;
					} else
					{
						this.textD10.setText(queuemanager.nopeTitlescurrentIteration + 2 + counter + " - " + shortenShortTitleString(nextsleft.get(counter).get(0)));
						break;				
					}
					}
					}
				}
			} else resetnextsleft();
		}
		
		if (updateQueue)
		{
			formersmiddle = queuemanager.get10Formers(ListEnum.QUEUE); 
	 		nextsmiddle = queuemanager.get10Nexts(ListEnum.QUEUE); 
			if (formersmiddle != null)
			{
				for (int counter = 0; counter <= 9; counter ++) 
				{
					switch (counter)
					{
					case 0:
					{
						if (counter > formersmiddle.size()-1)
						{
							this.textB10.setText(" ");
						} else
						{
						this.textB10.setText(queuemanager.queueTitlescurrentIteration - counter + " - " + shortenShortTitleString(formersmiddle.get(formersmiddle.size()-1-counter).get(0)));
						break;
						}
					}
					case 1:
					{
						if (counter > formersmiddle.size()-1)
						{
							this.textB9.setText(" ");
						} else
						{
						this.textB9.setText(queuemanager.queueTitlescurrentIteration - counter + " - " + shortenShortTitleString(formersmiddle.get(formersmiddle.size()-1-counter).get(0)));
						break;
						}
					}
					case 2:
					{
						if (counter > formersmiddle.size()-1)
						{
							this.textB8.setText(" ");
						} else
						{
						this.textB8.setText(queuemanager.queueTitlescurrentIteration - counter + " - " + shortenShortTitleString(formersmiddle.get(formersmiddle.size()-1-counter).get(0)));
						break;
					}
				}
					case 3:
					{
						if (counter > formersmiddle.size()-1)
					{
						this.textB7.setText(" ");
					} else
					{
						this.textB7.setText(queuemanager.queueTitlescurrentIteration - counter + " - " + shortenShortTitleString(formersmiddle.get(formersmiddle.size()-1-counter).get(0)));
						break;
					}
				}
					case 4:
					{
						if (counter > formersmiddle.size()-1)
					{
						this.textB6.setText(" ");
					} else
					{
						this.textB6.setText(queuemanager.queueTitlescurrentIteration - counter + " - " + shortenShortTitleString(formersmiddle.get(formersmiddle.size()-1-counter).get(0)));
						break;
					}
				}
					case 5:
					{
						if (counter > formersmiddle.size()-1)
					{
						this.textB5.setText(" ");
					} else
					{
						this.textB5.setText(queuemanager.queueTitlescurrentIteration - counter + " - " + shortenShortTitleString(formersmiddle.get(formersmiddle.size()-1-counter).get(0)));
						break;
					}
				}
					case 6:
					{
						if (counter > formersmiddle.size()-1)
					{
						this.textB4.setText(" ");
					} else
					{
						this.textB4.setText(queuemanager.queueTitlescurrentIteration - counter + " - " + shortenShortTitleString(formersmiddle.get(formersmiddle.size()-1-counter).get(0)));
						break;
					}
				}
					case 7:
					{
						if (counter > formersmiddle.size()-1)
					{
						this.textB3.setText(" ");
					} else
					{
						this.textB3.setText(queuemanager.queueTitlescurrentIteration - counter + " - " + shortenShortTitleString(formersmiddle.get(formersmiddle.size()-1-counter).get(0)));
						break;
					}
				}
					case 8:
					{
						if (counter > formersmiddle.size()-1)
					{
						this.textB2.setText(" ");
						break;
					} else
					{
						this.textB2.setText(queuemanager.queueTitlescurrentIteration - counter + " - " + shortenShortTitleString(formersmiddle.get(formersmiddle.size()-1-counter).get(0)));
						break;				
					}				
					}
					case 9:
					{
						if (counter > formersmiddle.size()-1)
					{
						this.textB1.setText(" ");
						break;
					} else
					{
						this.textB1.setText(queuemanager.queueTitlescurrentIteration - counter + " - " + shortenShortTitleString(formersmiddle.get(formersmiddle.size()-1-counter).get(0)));
						break;				
					}
					}
					}
				}
			} else resetformersmiddle();
			
			if (nextsmiddle != null)
			{
				for (int counter = 0; counter <= 9; counter ++) 
				{
					switch (counter)
					{
					case 0:
					{
						if (counter > nextsmiddle.size()-1)
						{
							this.textE1.setText(" ");
						} else
						{
						this.textE1.setText(queuemanager.queueTitlescurrentIteration + 2 + counter + " - " + shortenShortTitleString(nextsmiddle.get(counter).get(0)));
						break;
						}
					}
					case 1:
					{
						if (counter > nextsmiddle.size()-1)
						{
							this.textE2.setText(" ");
						} else
						{
						this.textE2.setText(queuemanager.queueTitlescurrentIteration + 2 + counter + " - " + shortenShortTitleString(nextsmiddle.get(counter).get(0)));
						break;
						}
					}
					case 2:
					{
						if (counter > nextsmiddle.size()-1)
						{
							this.textE3.setText(" ");
						} else
						{
						this.textE3.setText(queuemanager.queueTitlescurrentIteration + 2 + counter + " - " + shortenShortTitleString(nextsmiddle.get(counter).get(0)));
						break;
					}
				}
					case 3:
					{
						if (counter > nextsmiddle.size()-1)
					{
						this.textE4.setText(" ");
					} else
					{
						this.textE4.setText(queuemanager.queueTitlescurrentIteration + 2 + counter + " - " + shortenShortTitleString(nextsmiddle.get(counter).get(0)));
						break;
					}
				}
					case 4:
					{
						if (counter > nextsmiddle.size()-1)
					{
						this.textE5.setText(" ");
					} else
					{
						this.textE5.setText(queuemanager.queueTitlescurrentIteration + 2 + counter + " - " + shortenShortTitleString(nextsmiddle.get(counter).get(0)));
						break;
					}
				}
					case 5:
					{
						if (counter > nextsmiddle.size()-1)
					{
						this.textE6.setText(" ");
					} else
					{
						this.textE6.setText(queuemanager.queueTitlescurrentIteration + 2 + counter + " - " + shortenShortTitleString(nextsmiddle.get(counter).get(0)));
						break;
					}
				}
					case 6:
					{
						if (counter > nextsmiddle.size()-1)
					{
						this.textE7.setText(" ");
					} else
					{
						this.textE7.setText(queuemanager.queueTitlescurrentIteration + 2 + counter + " - " + shortenShortTitleString(nextsmiddle.get(counter).get(0)));
						break;
					}
				}
					case 7:
					{
						if (counter > nextsmiddle.size()-1)
					{
						this.textE8.setText(" ");
					} else
					{
						this.textE8.setText(queuemanager.queueTitlescurrentIteration + 2 + counter + " - " + shortenShortTitleString(nextsmiddle.get(counter).get(0)));
						break;
					}
				}
					case 8:
					{
						if (counter > nextsmiddle.size()-1)
					{
						this.textE9.setText(" ");
						break;
					} else
					{
						this.textE9.setText(queuemanager.queueTitlescurrentIteration + 2 + counter + " - " + shortenShortTitleString(nextsmiddle.get(counter).get(0)));
						break;				
					}				
					}
					case 9:
					{
						if (counter > nextsmiddle.size()-1)
					{
						this.textE10.setText(" ");
						break;
					} else
					{
						this.textE10.setText(queuemanager.queueTitlescurrentIteration + 2 + counter + " - " + shortenShortTitleString(nextsmiddle.get(counter).get(0)));
						break;				
					}
					}
					}
				}
			} else resetnextsmiddle();
		}
		
		if (updatePicks)
		{			//formers
			formersright = queuemanager.get10Formers(ListEnum.PICKS); 
	 		nextsright = queuemanager.get10Nexts(ListEnum.PICKS);			
			
			if (formersright != null)
			{
				for (int counter = 0; counter <= 9; counter ++) 
				{
					switch (counter)
					{
					case 0:
					{
						if (counter > formersright.size()-1)
						{
							this.textC10.setText(" ");
						} else
						{
						this.textC10.setText(queuemanager.pickTitlescurrentIteration - counter + " - " + shortenShortTitleString(formersright.get(formersright.size()-1-counter).get(0)));
						break;
						}
					}
					case 1:
					{
						if (counter > formersright.size()-1)
						{
							this.textC9.setText(" ");
						} else
						{
						this.textC9.setText(queuemanager.pickTitlescurrentIteration - counter + " - " + shortenShortTitleString(formersright.get(formersright.size()-1-counter).get(0)));
						break;
						}
					}
					case 2:
					{
						if (counter > formersright.size()-1)
						{
							this.textC8.setText(" ");
						} else
						{
						this.textC8.setText(queuemanager.pickTitlescurrentIteration - counter + " - " + shortenShortTitleString(formersright.get(formersright.size()-1-counter).get(0)));
						break;
					}
				}
					case 3:
					{
						if (counter > formersright.size()-1)
					{
						this.textC7.setText(" ");
					} else
					{
						this.textC7.setText(queuemanager.pickTitlescurrentIteration - counter + " - " + shortenShortTitleString(formersright.get(formersright.size()-1-counter).get(0)));
						break;
					}
				}
					case 4:
					{
						if (counter > formersright.size()-1)
					{
						this.textC6.setText(" ");
					} else
					{
						this.textC6.setText(queuemanager.pickTitlescurrentIteration - counter + " - " + shortenShortTitleString(formersright.get(formersright.size()-1-counter).get(0)));
						break;
					}
				}
					case 5:
					{
						if (counter > formersright.size()-1)
					{
						this.textC5.setText(" ");
					} else
					{
						this.textC5.setText(queuemanager.pickTitlescurrentIteration - counter + " - " + shortenShortTitleString(formersright.get(formersright.size()-1-counter).get(0)));
						break;
					}
				}
					case 6:
					{
						if (counter > formersright.size()-1)
					{
						this.textC4.setText(" ");
					} else
					{
						this.textC4.setText(queuemanager.pickTitlescurrentIteration - counter + " - " + shortenShortTitleString(formersright.get(formersright.size()-1-counter).get(0)));
						break;
					}
				}
					case 7:
					{
						if (counter > formersright.size()-1)
					{
						this.textC3.setText(" ");
					} else
					{
						this.textC3.setText(queuemanager.pickTitlescurrentIteration - counter + " - " + shortenShortTitleString(formersright.get(formersright.size()-1-counter).get(0)));
						break;
					}
				}
					case 8:
					{
						if (counter > formersright.size()-1)
					{
						this.textC2.setText(" ");
						break;
					} else
					{
						this.textC2.setText(queuemanager.pickTitlescurrentIteration - counter + " - " + shortenShortTitleString(formersright.get(formersright.size()-1-counter).get(0)));
						break;				
					}				
					}
					case 9:
					{
						if (counter > formersright.size()-1)
					{
						this.textC1.setText(" ");
						break;
					} else
					{
						this.textC1.setText(queuemanager.pickTitlescurrentIteration - counter + " - " + shortenShortTitleString(formersright.get(formersright.size()-1-counter).get(0)));
						break;				
					}
					}
					}
				}
			} else resetformersright();
			
			//nexts		
			if (nextsright != null)
			{
				for (int counter = 0; counter <= 9; counter ++) 
				{
					switch (counter)
					{
					case 0:
					{
						if (counter > nextsright.size()-1)
						{
							this.textF1.setText(" ");
						} else
						{
						this.textF1.setText(queuemanager.pickTitlescurrentIteration + 2 + counter + " - " + shortenShortTitleString(nextsright.get(counter).get(0)));
						break;
						}
					}
					case 1:
					{
						if (counter > nextsright.size()-1)
						{
							this.textF2.setText(" ");
						} else
						{
						this.textF2.setText(queuemanager.pickTitlescurrentIteration + 2 + counter + " - " + shortenShortTitleString(nextsright.get(counter).get(0)));
						break;
						}
					}
					case 2:
					{
						if (counter > nextsright.size()-1)
						{
							this.textF3.setText(" ");
						} else
						{
						this.textF3.setText(queuemanager.pickTitlescurrentIteration + 2 + counter + " - " + shortenShortTitleString(nextsright.get(counter).get(0)));
						break;
					}
				}
					case 3:
					{
						if (counter > nextsright.size()-1)
					{
						this.textF4.setText(" ");
					} else
					{
						this.textF4.setText(queuemanager.pickTitlescurrentIteration + 2 + counter + " - " + shortenShortTitleString(nextsright.get(counter).get(0)));
						break;
					}
				}
					case 4:
					{
						if (counter > nextsright.size()-1)
					{
						this.textF5.setText(" ");
					} else
					{
						this.textF5.setText(queuemanager.pickTitlescurrentIteration + 2 + counter + " - " + shortenShortTitleString(nextsright.get(counter).get(0)));
						break;
					}
				}
					case 5:
					{
						if (counter > nextsright.size()-1)
					{
						this.textF6.setText(" ");
					} else
					{
						this.textF6.setText(queuemanager.pickTitlescurrentIteration + 2 + counter + " - " + shortenShortTitleString(nextsright.get(counter).get(0)));
						break;
					}
				}
					case 6:
					{
						if (counter > nextsright.size()-1)
					{
						this.textF7.setText(" ");
					} else
					{
						this.textF7.setText(queuemanager.pickTitlescurrentIteration + 2 + counter + " - " + shortenShortTitleString(nextsright.get(counter).get(0)));
						break;
					}
				}
					case 7:
					{
						if (counter > nextsright.size()-1)
					{
						this.textF8.setText(" ");
					} else
					{
						this.textF8.setText(queuemanager.pickTitlescurrentIteration + 2 + counter + " - " + shortenShortTitleString(nextsright.get(counter).get(0)));
						break;
					}
				}
					case 8:
					{
						if (counter > nextsright.size()-1)
					{
						this.textF9.setText(" ");
						break;
					} else
					{
						this.textF9.setText(queuemanager.pickTitlescurrentIteration + 2 + counter + " - " + shortenShortTitleString(nextsright.get(counter).get(0)));
						break;				
					}				
					}
					case 9:
					{
						if (counter > nextsright.size()-1)
					{
						this.textF10.setText(" ");
						break;
					} else
					{
						this.textF10.setText(queuemanager.pickTitlescurrentIteration + 2 + counter + " - " + shortenShortTitleString(nextsright.get(counter).get(0)));
						break;				
					}
					}
					}
				}
			} else resetnextsrightt();
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}		
	
	private void exportQueue(QueueManager queuemanager)
	{
		if (queuemanager.isExportFolderValid())
			{
				ImportExportFileHandler importExportFileHandler = new ImportExportFileHandler();
	 		queuemanager.exportPicks();				 		
	 		try {
				importExportFileHandler.openExportFileFolder();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			} else
			{
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Warnung");
				alert.setHeaderText("Kein gültiger Exportpfad angegeben. Ihre Daten wurden noch nicht gespeichert.");
				alert.setContentText("Bitte gehen Sie zu den Importeinstellungen und überprüfen Sie Ihre Eingaben!");

				alert.showAndWait();
			}
	}
	
	private void initiateElements()
	{
		this.pickerTab = new Tab(Constants.ChooserTabName);
		
		this.currentsqueue = new CustomTextAreaPickerTab(ListEnum.QUEUE);
		this.currentspicks = new CustomTextAreaPickerTab(ListEnum.PICKS);
		this.currentsnopes = new CustomTextAreaPickerTab(ListEnum.NOPES);
		
		this.progressBar = new ProgressBar(0.1);
		
		this.gridpaneLower = new GridPane();
		this.progressText = new Text();
		
		this.currentsHBox = new HBox();
		this.nextsHBox = new HBox();
		this.formersHBox = new HBox();
		this.shortCutHBox = new HBox();
		
		this.currentsleftVBox = new VBox(200, this.currentsnopes);
		this.currentsmiddleVBox = new VBox(200, this.currentsqueue);
		this.currentsrightVBox = new VBox(200, this.currentspicks);
				
		this.helpButton = new Button();
		
		this.textA1 = new Label("");
		this.textA2 = new Label("");
		this.textA3 = new Label("");
		this.textA4 = new Label("");
		this.textA5 = new Label("");
		this.textA6 = new Label("");
		this.textA7 = new Label("");
		this.textA8 = new Label("");
		this.textA9 = new Label("");
		this.textA10 = new Label("");
		this.formersleftVBox = new VBox(5, this.textA1, this.textA2, this.textA3, this.textA4, this.textA5, this.textA6, this.textA7, this.textA8, this.textA9, this.textA10);
			
		this.textB1 = new Label(" ");
		this.textB2 = new Label(" ");
		this.textB3 = new Label(" ");
		this.textB4 = new Label(" ");
		this.textB5 = new Label(" ");
		this.textB6 = new Label(" ");
		this.textB7 = new Label(" ");
		this.textB8 = new Label(" ");
		this.textB9 = new Label(" ");
		this.textB10 = new Label(" ");
		this.formersmiddleVBox = new VBox(5, this.textB1, this.textB2, this.textB3, this.textB4, this.textB5, this.textB6, this.textB7, this.textB8, this.textB9, this.textB10);
		
		this.textC1 = new Label(" ");
		this.textC2 = new Label(" ");
		this.textC3 = new Label(" ");
		this.textC4 = new Label(" ");
		this.textC5 = new Label(" ");
		this.textC6 = new Label(" ");
		this.textC7 = new Label(" ");
		this.textC8 = new Label(" ");
		this.textC9 = new Label(" ");
		this.textC10 = new Label(" ");
		this.formersrightVBox = new VBox(5, this.textC1, this.textC2, this.textC3, this.textC4, this.textC5, this.textC6, this.textC7, this.textC8, this.textC9, this.textC10);
		
		this.textD1 = new Label(" ");
		this.textD2 = new Label(" ");
		this.textD3 = new Label(" ");
		this.textD4 = new Label(" ");
		this.textD5 = new Label(" ");
		this.textD6 = new Label(" ");
		this.textD7 = new Label(" ");
		this.textD8 = new Label(" ");
		this.textD9 = new Label(" ");
		this.textD10 = new Label(" ");
		this.nextsleftVBox = new VBox(5, this.textD1, this.textD2, this.textD3, this.textD4, this.textD5, this.textD6, this.textD7, this.textD8, this.textD9, this.textD10);
		
		this.textE1 = new Label(" ");
		this.textE2 = new Label(" ");
		this.textE3 = new Label(" ");
		this.textE4 = new Label(" ");
		this.textE5 = new Label(" ");
		this.textE6 = new Label(" ");
		this.textE7 = new Label(" ");
		this.textE8 = new Label(" ");
		this.textE9 = new Label(" ");
		this.textE10 = new Label(" ");
		this.nextsmiddleVBox = new VBox(5, this.textE1, this.textE2, this.textE3, this.textE4, this.textE5, this.textE6, this.textE7, this.textE8, this.textE9, this.textE10);
		
		this.textF1 = new Label(" ");
		this.textF2 = new Label(" ");
		this.textF3 = new Label(" ");
		this.textF4 = new Label(" ");
		this.textF5 = new Label(" ");
		this.textF6 = new Label(" ");
		this.textF7 = new Label(" ");
		this.textF8 = new Label(" ");
		this.textF9 = new Label(" ");
		this.textF10 = new Label(" ");
		this.nextsrightVBox = new VBox(5, this.textF1, this.textF2, this.textF3, this.textF4, this.textF5, this.textF6, this.textF7, this.textF8, this.textF9, this.textF10);
		
		this.markedKeyWordsHBoxNopes = new HBox();
		this. markedKeyWordsHBoxQueue = new HBox();
		this.markedKeyWordsHBoxPicks = new HBox();
		this.markedKeyWordsNopesGreen = new Label();
		this.markedKeyWordsNopesRed = new Label();
		this.markedKeyWordsQueueGreen = new Label();
		this.markedKeyWordsQueueRed = new Label();
		this.markedKeyWordsPicksGreen = new Label();
		this.markedKeyWordsPicksRed = new Label();
		this.markedKeyWordsNopesOA = new Label();
		this.markedKeyWordsQueueOA = new Label();
		this.markedKeyWordsPicksOA = new Label();
		
	}
			public void updateKeyWords(ListEnum listEnum) throws MalformedURLException, InterruptedException
			{
				switch (listEnum)
				{
				case NOPES:
				{
					 this.markedKeyWordsNopesGreen.setText(this.queuemanager.getCurrentKeyWords(ListEnum.NOPES, true));				 
					 this.markedKeyWordsNopesRed.setText(this.queuemanager.getCurrentKeyWords(ListEnum.NOPES, false));					
					 this.markedKeyWordsNopesOA.setText(this.queuemanager.getCurrentOAKeyWord(ListEnum.NOPES, Constants.oaMarker, Constants.oaKeyWordForDisplay));
					return;
				}
				case QUEUE:
				{
					 this.markedKeyWordsQueueGreen.setText(this.queuemanager.getCurrentKeyWords(ListEnum.QUEUE, true));				 
					 this.markedKeyWordsQueueRed.setText(this.queuemanager.getCurrentKeyWords(ListEnum.QUEUE, false));					
					 this.markedKeyWordsQueueOA.setText(this.queuemanager.getCurrentOAKeyWord(ListEnum.QUEUE, Constants.oaMarker, Constants.oaKeyWordForDisplay));
					return;
				}
				case PICKS:
				{
					 this.markedKeyWordsPicksGreen.setText(this.queuemanager.getCurrentKeyWords(ListEnum.PICKS, true));				 
					 this.markedKeyWordsPicksRed.setText(this.queuemanager.getCurrentKeyWords(ListEnum.PICKS, false));					
					 this.markedKeyWordsPicksOA.setText(this.queuemanager.getCurrentOAKeyWord(ListEnum.PICKS, Constants.oaMarker, Constants.oaKeyWordForDisplay));
					return;
				}					
				}
				return;				
			}
	
	private void configureElements()
	{			
				
		this.markedKeyWordsHBoxNopes.prefWidthProperty().bind(primaryStage.widthProperty().multiply(0.322));
		this. markedKeyWordsHBoxQueue.prefWidthProperty().bind(primaryStage.widthProperty().multiply(0.322));
		this.markedKeyWordsHBoxPicks.prefWidthProperty().bind(primaryStage.widthProperty().multiply(0.322));
		this.markedKeyWordsNopesGreen.setFont(new Font(Constants.DefaultFont, Constants.DefaultFontSize)); 
		this.markedKeyWordsNopesGreen.setTextFill(Color.rgb(46,139,87));
		this.markedKeyWordsNopesRed.setFont(new Font(Constants.DefaultFont, Constants.DefaultFontSize)); 
		this.markedKeyWordsNopesRed.setTextFill(Color.rgb(139,0,0));
		this.markedKeyWordsQueueGreen.setFont(new Font(Constants.DefaultFont, Constants.DefaultFontSize)); 
		this.markedKeyWordsQueueGreen.setTextFill(Color.rgb(46,139,87));
		this.markedKeyWordsQueueRed.setFont(new Font(Constants.DefaultFont, Constants.DefaultFontSize)); 
		this.markedKeyWordsQueueRed.setTextFill(Color.rgb(139,0,0));
		this.markedKeyWordsPicksGreen.setFont(new Font(Constants.DefaultFont, Constants.DefaultFontSize)); 
		this.markedKeyWordsPicksGreen.setTextFill(Color.rgb(46,139,87));
		this.markedKeyWordsPicksRed.setFont(new Font(Constants.DefaultFont, Constants.DefaultFontSize)); 
		this.markedKeyWordsPicksRed.setTextFill(Color.rgb(139,0,0));
		this.markedKeyWordsNopesOA.setFont(new Font(Constants.DefaultFont, Constants.DefaultFontSize)); 
		this.markedKeyWordsQueueOA.setFont(new Font(Constants.DefaultFont, Constants.DefaultFontSize)); 
		this.markedKeyWordsPicksOA.setFont(new Font(Constants.DefaultFont, Constants.DefaultFontSize)); 
		this.markedKeyWordsNopesOA.setTextFill(Color.rgb(160,32,240));
		this.markedKeyWordsQueueOA.setTextFill(Color.rgb(160,32,240));
		this.markedKeyWordsPicksOA.setTextFill(Color.rgb(160,32,240));
		

		this.markedKeyWordsNopesGreen.setStyle("-fx-font-weight: bold"); 
		this.markedKeyWordsNopesRed.setStyle("-fx-font-weight: bold"); 
		this.markedKeyWordsQueueGreen.setStyle("-fx-font-weight: bold"); 
		this.markedKeyWordsQueueRed.setStyle("-fx-font-weight: bold"); 
		this.markedKeyWordsPicksGreen.setStyle("-fx-font-weight: bold"); 
		this.markedKeyWordsPicksRed.setStyle("-fx-font-weight: bold"); 
		this.markedKeyWordsNopesOA.setStyle("-fx-font-weight: bold");
		this.markedKeyWordsQueueOA.setStyle("-fx-font-weight: bold");
		this.markedKeyWordsPicksOA.setStyle("-fx-font-weight: bold");
		
	
		Region regionKeyWordsNopes = new Region();
		Region regionKeyWordsQueue = new Region();
		Region regionKeyWordsPicks = new Region();
		
		Region regionKeyWordsNopes2 = new Region();
		Region regionKeyWordsQueue2 = new Region();
		Region regionKeyWordsPicks2 = new Region();
		
		HBox.setHgrow(regionKeyWordsNopes, Priority.ALWAYS);
		HBox.setHgrow(regionKeyWordsQueue, Priority.ALWAYS);
		HBox.setHgrow(regionKeyWordsPicks, Priority.ALWAYS);
		HBox.setHgrow(regionKeyWordsNopes2, Priority.ALWAYS);
		HBox.setHgrow(regionKeyWordsQueue2, Priority.ALWAYS);
		HBox.setHgrow(regionKeyWordsPicks2, Priority.ALWAYS);
		
		this.markedKeyWordsHBoxNopes.getChildren().addAll(this.markedKeyWordsNopesGreen, regionKeyWordsNopes, markedKeyWordsNopesOA, regionKeyWordsNopes2, this.markedKeyWordsNopesRed);
		this.markedKeyWordsHBoxQueue.getChildren().addAll(this.markedKeyWordsQueueGreen, regionKeyWordsQueue, markedKeyWordsQueueOA, regionKeyWordsQueue2, this.markedKeyWordsQueueRed);
		this.markedKeyWordsHBoxPicks.getChildren().addAll(this.markedKeyWordsPicksGreen, regionKeyWordsPicks, markedKeyWordsPicksOA, regionKeyWordsPicks2, this.markedKeyWordsPicksRed);
		
		
		
		
		this.pickerTab.setClosable(false);		
		this.currentsqueue.setEditable(false);		
		this.currentspicks.setEditable(false);
		this.currentsnopes.setEditable(false);	
		
		this.currentsqueue.setVisible(false);		
		this.currentspicks.setVisible(false);
		this.currentsnopes.setVisible(false);	
		
		this.currentsqueue.setWrapText(true);
		this.currentspicks.setWrapText(true);
		this.currentsnopes.setWrapText(true);
		
		this.currentsqueue.prefWidthProperty().bind(primaryStage.widthProperty().multiply(0.323));
		this.currentspicks.prefWidthProperty().bind(primaryStage.widthProperty().multiply(0.323));
		this.currentsnopes.prefWidthProperty().bind(primaryStage.widthProperty().multiply(0.323));
		this.currentsqueue.prefHeightProperty().bind(primaryStage.heightProperty().multiply(0.321));
		this.currentspicks.prefHeightProperty().bind(primaryStage.heightProperty().multiply(0.321));
		this.currentsnopes.prefHeightProperty().bind(primaryStage.heightProperty().multiply(0.321));
		
		this.currentsqueue.setMinHeight(200);
		this.currentspicks.setMinHeight(200);
		this.currentsnopes.setMinHeight(200);
		
		
		this.currentspicks.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!oldValue.equals(newValue))
			{
				updateFormattedTitle(ListEnum.PICKS);
			}			
		});
		
		this.currentsqueue.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!oldValue.equals(newValue))
			{
				updateFormattedTitle(ListEnum.QUEUE);
			}			
		});
		
		this.currentsnopes.textProperty().addListener((observable, oldValue, newValue) -> {			
			if (!oldValue.equals(newValue))
			{
				updateFormattedTitle(ListEnum.NOPES);
			}			
		});
		
		
		this.textA1.minWidthProperty().bind(primaryStage.widthProperty().multiply(0.323));
		this.textA2.minWidthProperty().bind(primaryStage.widthProperty().multiply(0.323));
		this.textA3.minWidthProperty().bind(primaryStage.widthProperty().multiply(0.323));
		this.textA4.minWidthProperty().bind(primaryStage.widthProperty().multiply(0.323));
		this.textA5.minWidthProperty().bind(primaryStage.widthProperty().multiply(0.323));
		this.textA6.minWidthProperty().bind(primaryStage.widthProperty().multiply(0.323));
		this.textA7.minWidthProperty().bind(primaryStage.widthProperty().multiply(0.323));
		this.textA8.minWidthProperty().bind(primaryStage.widthProperty().multiply(0.323));
		this.textA9.minWidthProperty().bind(primaryStage.widthProperty().multiply(0.323));
		this.textA10.minWidthProperty().bind(primaryStage.widthProperty().multiply(0.323));
		this.textB1.minWidthProperty().bind(primaryStage.widthProperty().multiply(0.323));
		this.textB2.minWidthProperty().bind(primaryStage.widthProperty().multiply(0.323));
		this.textB3.minWidthProperty().bind(primaryStage.widthProperty().multiply(0.323));
		this.textB4.minWidthProperty().bind(primaryStage.widthProperty().multiply(0.323));
		this.textB5.minWidthProperty().bind(primaryStage.widthProperty().multiply(0.323));
		this.textB6.minWidthProperty().bind(primaryStage.widthProperty().multiply(0.323));
		this.textB7.minWidthProperty().bind(primaryStage.widthProperty().multiply(0.323));
		this.textB8.minWidthProperty().bind(primaryStage.widthProperty().multiply(0.323));
		this.textB9.minWidthProperty().bind(primaryStage.widthProperty().multiply(0.323));
		this.textB10.minWidthProperty().bind(primaryStage.widthProperty().multiply(0.323));
		this.textC1.minWidthProperty().bind(primaryStage.widthProperty().multiply(0.323));
		this.textC2.minWidthProperty().bind(primaryStage.widthProperty().multiply(0.323));
		this.textC3.minWidthProperty().bind(primaryStage.widthProperty().multiply(0.323));
		this.textC4.minWidthProperty().bind(primaryStage.widthProperty().multiply(0.323));
		this.textC5.minWidthProperty().bind(primaryStage.widthProperty().multiply(0.323));
		this.textC6.minWidthProperty().bind(primaryStage.widthProperty().multiply(0.323));
		this.textC7.minWidthProperty().bind(primaryStage.widthProperty().multiply(0.323));
		this.textC8.minWidthProperty().bind(primaryStage.widthProperty().multiply(0.323));
		this.textC9.minWidthProperty().bind(primaryStage.widthProperty().multiply(0.323));
		this.textC10.minWidthProperty().bind(primaryStage.widthProperty().multiply(0.323));
		this.textD1.minWidthProperty().bind(primaryStage.widthProperty().multiply(0.323));
		this.textD2.minWidthProperty().bind(primaryStage.widthProperty().multiply(0.323));
		this.textD3.minWidthProperty().bind(primaryStage.widthProperty().multiply(0.323));
		this.textD4.minWidthProperty().bind(primaryStage.widthProperty().multiply(0.323));
		this.textD5.minWidthProperty().bind(primaryStage.widthProperty().multiply(0.323));
		this.textD6.minWidthProperty().bind(primaryStage.widthProperty().multiply(0.323));
		this.textD7.minWidthProperty().bind(primaryStage.widthProperty().multiply(0.323));
		this.textD8.minWidthProperty().bind(primaryStage.widthProperty().multiply(0.323));
		this.textD9.minWidthProperty().bind(primaryStage.widthProperty().multiply(0.323));
		this.textD10.minWidthProperty().bind(primaryStage.widthProperty().multiply(0.323));
		this.textE1.minWidthProperty().bind(primaryStage.widthProperty().multiply(0.323));
		this.textE2.minWidthProperty().bind(primaryStage.widthProperty().multiply(0.323));
		this.textE3.minWidthProperty().bind(primaryStage.widthProperty().multiply(0.323));
		this.textE4.minWidthProperty().bind(primaryStage.widthProperty().multiply(0.323));
		this.textE5.minWidthProperty().bind(primaryStage.widthProperty().multiply(0.323));
		this.textE6.minWidthProperty().bind(primaryStage.widthProperty().multiply(0.323));
		this.textE7.minWidthProperty().bind(primaryStage.widthProperty().multiply(0.323));
		this.textE8.minWidthProperty().bind(primaryStage.widthProperty().multiply(0.323));
		this.textE9.minWidthProperty().bind(primaryStage.widthProperty().multiply(0.323));
		this.textE10.minWidthProperty().bind(primaryStage.widthProperty().multiply(0.323));
		this.textF1.minWidthProperty().bind(primaryStage.widthProperty().multiply(0.323));
		this.textF2.minWidthProperty().bind(primaryStage.widthProperty().multiply(0.323));
		this.textF3.minWidthProperty().bind(primaryStage.widthProperty().multiply(0.323));
		this.textF4.minWidthProperty().bind(primaryStage.widthProperty().multiply(0.323));
		this.textF5.minWidthProperty().bind(primaryStage.widthProperty().multiply(0.323));
		this.textF6.minWidthProperty().bind(primaryStage.widthProperty().multiply(0.323));
		this.textF7.minWidthProperty().bind(primaryStage.widthProperty().multiply(0.323));
		this.textF8.minWidthProperty().bind(primaryStage.widthProperty().multiply(0.323));
		this.textF9.minWidthProperty().bind(primaryStage.widthProperty().multiply(0.323));
		this.textF10.minWidthProperty().bind(primaryStage.widthProperty().multiply(0.323));
	
		this.progressBar.setMinHeight(15);
		this.progressBar.setMinWidth(100);
		
        this.ButtonImage_ctrl = GetButtonPics.getButtonImage_ctrl();
        this.ButtonImage_space = GetButtonPics.getButtonImage_space();
        this.ButtonImage_shift = GetButtonPics.getButtonImage_shift();
        this.ButtonImage_F1 = GetButtonPics.getButtonImage_F1();
        this.ButtonImage_F2 = GetButtonPics.getButtonImage_F2();
        this.ButtonImage_F3 = GetButtonPics.getButtonImage_F3();
        this.ButtonImage_F4 = GetButtonPics.getButtonImage_F4();
        this.ButtonImage_enter = GetButtonPics.getButtonImage_enter();
        this.ButtonImage_arrowRight = GetButtonPics.getButtonImage_arrowRight();
        this.ButtonImage_verticalLine = GetButtonPics.getButtonImage_verticalLine();
        this.ButtonImage_closedEye = GetButtonPics.getButtonImage_closedEye();
        this.ButtonImage_backspace = GetButtonPics.getButtonImage_backspace();  
        this.QuestionMarkImage = GetButtonPics.getButtonImage_QuestionMark();
        this.ButtonImage_alt = GetButtonPics.getButtonImage_Alt();
        
        
        this.textA1.setFont(new Font(Constants.DefaultFont, Constants.DefaultFontSize)); 
        this.textA2.setFont(new Font(Constants.DefaultFont,  Constants.DefaultFontSize)); 
        this.textA3.setFont(new Font(Constants.DefaultFont,  Constants.DefaultFontSize)); 
        this.textA4.setFont(new Font(Constants.DefaultFont,  Constants.DefaultFontSize)); 
        this.textA5.setFont(new Font(Constants.DefaultFont,  Constants.DefaultFontSize)); 
        this.textA6.setFont(new Font(Constants.DefaultFont,  Constants.DefaultFontSize)); 
        this.textA7.setFont(new Font(Constants.DefaultFont,  Constants.DefaultFontSize)); 
        this.textA8.setFont(new Font(Constants.DefaultFont,  Constants.DefaultFontSize)); 
        this.textA9.setFont(new Font(Constants.DefaultFont,  Constants.DefaultFontSize)); 
        this.textA10.setFont(new Font(Constants.DefaultFont,  Constants.DefaultFontSize)); 
        this.textB1.setFont(new Font(Constants.DefaultFont,  Constants.DefaultFontSize)); 
        this.textB2.setFont(new Font(Constants.DefaultFont,  Constants.DefaultFontSize)); 
        this.textB3.setFont(new Font(Constants.DefaultFont,  Constants.DefaultFontSize)); 
        this.textB4.setFont(new Font(Constants.DefaultFont,  Constants.DefaultFontSize)); 
        this.textB5.setFont(new Font(Constants.DefaultFont,  Constants.DefaultFontSize)); 
        this.textB6.setFont(new Font(Constants.DefaultFont,  Constants.DefaultFontSize)); 
        this.textB7.setFont(new Font(Constants.DefaultFont,  Constants.DefaultFontSize)); 
        this.textB8.setFont(new Font(Constants.DefaultFont,  Constants.DefaultFontSize)); 
        this.textB9.setFont(new Font(Constants.DefaultFont,  Constants.DefaultFontSize)); 
        this.textB10.setFont(new Font(Constants.DefaultFont,  Constants.DefaultFontSize)); 
        this.textC1.setFont(new Font(Constants.DefaultFont,  Constants.DefaultFontSize)); 
        this.textC2.setFont(new Font(Constants.DefaultFont,  Constants.DefaultFontSize)); 
        this.textC3.setFont(new Font(Constants.DefaultFont,  Constants.DefaultFontSize)); 
        this.textC4.setFont(new Font(Constants.DefaultFont,  Constants.DefaultFontSize)); 
        this.textC5.setFont(new Font(Constants.DefaultFont,  Constants.DefaultFontSize)); 
        this.textC6.setFont(new Font(Constants.DefaultFont,  Constants.DefaultFontSize)); 
        this.textC7.setFont(new Font(Constants.DefaultFont,  Constants.DefaultFontSize)); 
        this.textC8.setFont(new Font(Constants.DefaultFont,  Constants.DefaultFontSize)); 
        this.textC9.setFont(new Font(Constants.DefaultFont,  Constants.DefaultFontSize)); 
        this.textC10.setFont(new Font(Constants.DefaultFont,  Constants.DefaultFontSize)); 
        this.textD1.setFont(new Font(Constants.DefaultFont,  Constants.DefaultFontSize)); 
        this.textD2.setFont(new Font(Constants.DefaultFont,  Constants.DefaultFontSize)); 
        this.textD3.setFont(new Font(Constants.DefaultFont,  Constants.DefaultFontSize)); 
        this.textD4.setFont(new Font(Constants.DefaultFont,  Constants.DefaultFontSize)); 
        this.textD5.setFont(new Font(Constants.DefaultFont,  Constants.DefaultFontSize)); 
        this.textD6.setFont(new Font(Constants.DefaultFont,  Constants.DefaultFontSize)); 
        this.textD7.setFont(new Font(Constants.DefaultFont,  Constants.DefaultFontSize)); 
        this.textD8.setFont(new Font(Constants.DefaultFont,  Constants.DefaultFontSize)); 
        this.textD9.setFont(new Font(Constants.DefaultFont,  Constants.DefaultFontSize)); 
        this.textD10.setFont(new Font(Constants.DefaultFont,  Constants.DefaultFontSize)); 
        this.textE1.setFont(new Font(Constants.DefaultFont,  Constants.DefaultFontSize)); 
        this.textE2.setFont(new Font(Constants.DefaultFont,  Constants.DefaultFontSize)); 
        this.textE3.setFont(new Font(Constants.DefaultFont,  Constants.DefaultFontSize)); 
        this.textE4.setFont(new Font(Constants.DefaultFont,  Constants.DefaultFontSize)); 
        this.textE5.setFont(new Font(Constants.DefaultFont,  Constants.DefaultFontSize)); 
        this.textE6.setFont(new Font(Constants.DefaultFont,  Constants.DefaultFontSize)); 
        this.textE7.setFont(new Font(Constants.DefaultFont,  Constants.DefaultFontSize)); 
        this.textE8.setFont(new Font(Constants.DefaultFont,  Constants.DefaultFontSize)); 
        this.textE9.setFont(new Font(Constants.DefaultFont,  Constants.DefaultFontSize)); 
        this.textE10.setFont(new Font(Constants.DefaultFont,  Constants.DefaultFontSize)); 
        this.textF1.setFont(new Font(Constants.DefaultFont,  Constants.DefaultFontSize)); 
        this.textF2.setFont(new Font(Constants.DefaultFont,  Constants.DefaultFontSize)); 
        this.textF3.setFont(new Font(Constants.DefaultFont,  Constants.DefaultFontSize)); 
        this.textF4.setFont(new Font(Constants.DefaultFont,  Constants.DefaultFontSize)); 
        this.textF5.setFont(new Font(Constants.DefaultFont,  Constants.DefaultFontSize)); 
        this.textF6.setFont(new Font(Constants.DefaultFont,  Constants.DefaultFontSize)); 
        this.textF7.setFont(new Font(Constants.DefaultFont,  Constants.DefaultFontSize)); 
        this.textF8.setFont(new Font(Constants.DefaultFont,  Constants.DefaultFontSize)); 
        this.textF9.setFont(new Font(Constants.DefaultFont,  Constants.DefaultFontSize)); 
        this.textF10.setFont(new Font(Constants.DefaultFont,  Constants.DefaultFontSize)); 
        this.currentsnopes.setFont(new Font(Constants.DefaultFont,  Constants.DefaultFontSize));
        this.currentsqueue.setFont(new Font(Constants.DefaultFont,  Constants.DefaultFontSize));
        this.currentspicks.setFont(new Font(Constants.DefaultFont,  Constants.DefaultFontSize));                

        
        //this.textA1.setTextOverrun(OverrunStyle.CLIP);
        this.textA2.setTextOverrun(OverrunStyle.CLIP);
        this.textA3.setTextOverrun(OverrunStyle.CLIP);
        this.textA4.setTextOverrun(OverrunStyle.CLIP);
        this.textA5.setTextOverrun(OverrunStyle.CLIP);
        this.textA6.setTextOverrun(OverrunStyle.CLIP);
        this.textA7.setTextOverrun(OverrunStyle.CLIP);
        this.textA8.setTextOverrun(OverrunStyle.CLIP);
        this.textA9.setTextOverrun(OverrunStyle.CLIP);
        this.textA10.setTextOverrun(OverrunStyle.CLIP);
        this.textB1.setTextOverrun(OverrunStyle.CLIP);
        this.textB2.setTextOverrun(OverrunStyle.CLIP);
        this.textB3.setTextOverrun(OverrunStyle.CLIP);
        this.textB4.setTextOverrun(OverrunStyle.CLIP);
        this.textB5.setTextOverrun(OverrunStyle.CLIP);
        this.textB6.setTextOverrun(OverrunStyle.CLIP);
        this.textB7.setTextOverrun(OverrunStyle.CLIP);
        this.textB8.setTextOverrun(OverrunStyle.CLIP);
        this.textB9.setTextOverrun(OverrunStyle.CLIP);
        this.textB10.setTextOverrun(OverrunStyle.CLIP);
        this.textC1.setTextOverrun(OverrunStyle.CLIP);
        this.textC2.setTextOverrun(OverrunStyle.CLIP);
        this.textC3.setTextOverrun(OverrunStyle.CLIP);
        this.textC4.setTextOverrun(OverrunStyle.CLIP);
        this.textC5.setTextOverrun(OverrunStyle.CLIP);
        this.textC6.setTextOverrun(OverrunStyle.CLIP);
        this.textC7.setTextOverrun(OverrunStyle.CLIP);
        this.textC8.setTextOverrun(OverrunStyle.CLIP);
        this.textC9.setTextOverrun(OverrunStyle.CLIP);
        this.textC10.setTextOverrun(OverrunStyle.CLIP);
        this.textD1.setTextOverrun(OverrunStyle.CLIP);
        this.textD2.setTextOverrun(OverrunStyle.CLIP);
        this.textD3.setTextOverrun(OverrunStyle.CLIP);
        this.textD4.setTextOverrun(OverrunStyle.CLIP);
        this.textD5.setTextOverrun(OverrunStyle.CLIP);
        this.textD6.setTextOverrun(OverrunStyle.CLIP);
        this.textD7.setTextOverrun(OverrunStyle.CLIP);
        this.textD8.setTextOverrun(OverrunStyle.CLIP);
        this.textD9.setTextOverrun(OverrunStyle.CLIP);
        this.textD10.setTextOverrun(OverrunStyle.CLIP);
        this.textE1.setTextOverrun(OverrunStyle.CLIP);
        this.textE2.setTextOverrun(OverrunStyle.CLIP);
        this.textE3.setTextOverrun(OverrunStyle.CLIP);
        this.textE4.setTextOverrun(OverrunStyle.CLIP);
        this.textE5.setTextOverrun(OverrunStyle.CLIP);
        this.textE6.setTextOverrun(OverrunStyle.CLIP);
        this.textE7.setTextOverrun(OverrunStyle.CLIP);
        this.textE8.setTextOverrun(OverrunStyle.CLIP);
        this.textE9.setTextOverrun(OverrunStyle.CLIP);
        this.textE10.setTextOverrun(OverrunStyle.CLIP);
        this.textF1.setTextOverrun(OverrunStyle.CLIP);
        this.textF2.setTextOverrun(OverrunStyle.CLIP);
        this.textF3.setTextOverrun(OverrunStyle.CLIP);
        this.textF4.setTextOverrun(OverrunStyle.CLIP);
        this.textF5.setTextOverrun(OverrunStyle.CLIP);
        this.textF6.setTextOverrun(OverrunStyle.CLIP);
        this.textF7.setTextOverrun(OverrunStyle.CLIP);
        this.textF8.setTextOverrun(OverrunStyle.CLIP);
        this.textF9.setTextOverrun(OverrunStyle.CLIP);
        this.textF10.setTextOverrun(OverrunStyle.CLIP);      
 
       
               
        this.progressText = new Text("");	
		if (!(this.ButtonImage_ctrl == null ||
				this.ButtonImage_space == null || 
				this.ButtonImage_shift == null || 
				this.ButtonImage_F1 == null || 
				this.ButtonImage_F2 == null || 
				this.ButtonImage_F3 == null || 
				this.ButtonImage_F4 == null || 
				this.ButtonImage_enter == null || 
				this.ButtonImage_arrowRight == null || 
				this.ButtonImage_verticalLine == null ||
				this.ButtonImage_closedEye == null ||
				this.ButtonImage_backspace == null) ||
				this.QuestionMarkImage == null ||
				this.ButtonImage_alt == null)
		{		
			buildShortcutHBox();			
		} 		
		
		
		
		shortCutHBox.setAlignment(Pos.CENTER_LEFT);
		HBox.setMargin(this.progressBar, new Insets(0,10,0,0));
	

		this.formersleftVBox.setMaxWidth(200);
		this.formersmiddleVBox.setMaxWidth(200);
		this.formersrightVBox.setMaxWidth(200);
		this.nextsleftVBox.setMaxWidth(200);
		this.nextsmiddleVBox.setMaxWidth(200);
		this.nextsrightVBox.setMaxWidth(200);
		
		//labels instead of textareas:
		this.gridpaneLower.setHgap(10);
		this.gridpaneLower.setVgap(10);
		this.gridpaneLower.setPadding(new Insets(0,10,10,10));
		this.gridpaneLower.add(this.formersleftVBox, 0, 1);		
		this.gridpaneLower.add(this.formersmiddleVBox, 1, 1);
		this.gridpaneLower.add(this.formersrightVBox, 2, 1);
		this.gridpaneLower.add(this.markedKeyWordsHBoxNopes, 0, 2);
		this.gridpaneLower.add(this.markedKeyWordsHBoxQueue, 1, 2);
		this.gridpaneLower.add(this.markedKeyWordsHBoxPicks , 2, 2);
		this.gridpaneLower.add(this.currentsnopes, 0, 3);
		this.gridpaneLower.add(this.currentsqueue, 1, 3);
		this.gridpaneLower.add(this.currentspicks, 2, 3);
		this.gridpaneLower.add(this.nextsleftVBox, 0, 4);
		this.gridpaneLower.add(this.nextsmiddleVBox, 1, 4);
		this.gridpaneLower.add(this.nextsrightVBox, 2, 4);
		
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setFitToHeight(true);
		scrollPane.setFitToWidth(true);
		ScrollPane scrollPane2 = new ScrollPane();
		scrollPane.setContent(this.shortCutHBox);
		scrollPane2.setContent(this.gridpaneLower);
		this.pickerTab.setContent(new VBox(scrollPane, scrollPane2));		
		HBox.setHgrow(gridpaneLower, Priority.ALWAYS);
		
	}
	
	private void buildShortcutHBox()
	{
		Region regionMiddle = new Region();
		HBox.setHgrow(regionMiddle, Priority.ALWAYS);
	
		this.helpButton.setGraphic(GetButtonPics.turnPicIntoImageView(QuestionMarkImage));	
		
		this.helpButton.setOnAction(new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {
	            	
	            	Alert alert = new Alert(AlertType.INFORMATION);
	            	alert.setTitle("Kurzeinführung:");
	            	alert.setHeaderText(null);
	            	alert.setContentText(Constants.Kurzeinfuehrung);
	            	alert.initStyle(StageStyle.UTILITY);

	            	alert.showAndWait();
	            }
	        });       
					
		this.shortCutHBox.getChildren().addAll(
				new Text("   "), GetButtonPics.turnPicIntoImageView(this.ButtonImage_ctrl), GetButtonPics.turnPicIntoImageView(this.ButtonImage_arrowRight), Constants.ShortCutDescription_zurAnsicht,new Text(" "), GetButtonPics.turnPicIntoImageView(this.ButtonImage_verticalLine, 35), new Text("   "), 
				GetButtonPics.turnPicIntoImageView(this.ButtonImage_shift), GetButtonPics.turnPicIntoImageView(this.ButtonImage_arrowRight), Constants.ShortCutDescription_auflagentausch,new Text(" "), GetButtonPics.turnPicIntoImageView(this.ButtonImage_verticalLine, 35), new Text("   "),
				GetButtonPics.turnPicIntoImageView(this.ButtonImage_alt), GetButtonPics.turnPicIntoImageView(this.ButtonImage_arrowRight), Constants.ShortCutDescription_sonderHinweis,new Text(" "), GetButtonPics.turnPicIntoImageView(this.ButtonImage_verticalLine, 35), new Text("   "),
				GetButtonPics.turnPicIntoImageView(this.ButtonImage_space), Constants.ShortCutDescription_inhaltsverzeichnis,new Text(" "), GetButtonPics.turnPicIntoImageView(this.ButtonImage_verticalLine, 35), new Text("   "),
				GetButtonPics.turnPicIntoImageView(this.ButtonImage_shift), GetButtonPics.turnPicIntoImageView(this.ButtonImage_space), Constants.ShortCutDescription_inhaltstext,new Text("   "), GetButtonPics.turnPicIntoImageView(this.ButtonImage_verticalLine, 35), new Text("   "),
				GetButtonPics.turnPicIntoImageView(this.ButtonImage_F1),new Text(" bis "), GetButtonPics.turnPicIntoImageView(this.ButtonImage_F4), Constants.ShortCutDescription_titelsuche, GetButtonPics.turnPicIntoImageView(this.ButtonImage_verticalLine, 35), new Text("   "),
				GetButtonPics.turnPicIntoImageView(this.ButtonImage_backspace), Constants.ShortCutDescription_rueckgaengig, GetButtonPics.turnPicIntoImageView(this.ButtonImage_verticalLine, 35), new Text("   "),
				GetButtonPics.turnPicIntoImageView(this.ButtonImage_enter), Constants.ShortCutDescription_export, new Text(" "),
				regionMiddle, this.progressText, new Text(" "), this.progressBar, new Text(" "), this.helpButton, new Text(" ")
				);		
		this.shortCutHBox.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
		
	
	   		
	}
	
	private void updateProgressText(String text)
	{
		this.progressText.setText(text);
	}
	
	private Region getRegionForFormattedTitle(ListEnum listEnum)
	{
		switch (listEnum)
		{
		case NOPES:
		{
			return ( Region )this.currentsnopes.lookup(".content");
		}
		case QUEUE:
		{
			return ( Region )this.currentsqueue.lookup(".content");
		}
		case PICKS:
		{
			return ( Region )this.currentspicks.lookup(".content");
		}
		}
		return null;
	}
	
	private void updateFormattedTitle(ListEnum listEnum)
	{
		Region region = getRegionForFormattedTitle(listEnum);
		if (region != null)
		region.setBackground(null);
		this.markedKeyWordsPicksGreen.setText("");
		this.markedKeyWordsPicksRed.setText("");
		this.markedKeyWordsPicksOA.setText("");
		try {
			updateKeyWords(listEnum);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		if (region != null)
		{
			if (queuemanager.listNullOrEmpty(listEnum) || queuemanager.titleApiHasNotYetRun(listEnum))
			{
				region.setBackground(null);
			} else if(queuemanager.titleIsOwnedOrOpenAccess(listEnum))
			{				
				region.setBackground(new Background(new BackgroundFill(Color.rgb(255, 220, 220), new CornerRadii(5), Insets.EMPTY)));					
			} else if(!queuemanager.titleIsOwnedOrOpenAccess(listEnum) && !queuemanager.titleApiCouldNotBeAccessed(listEnum))
			{
				region.setBackground(new Background(new BackgroundFill(Color.rgb(220, 255, 220), new CornerRadii(5), Insets.EMPTY)));					
			} else
			{
				region.setBackground(null);
			}
		}	
	}
	
	
	private void configureEventsGeneral() {
		
		this.currentsqueue.setOnScroll(new EventHandler<ScrollEvent>() {
	        @Override public void handle(ScrollEvent event) {
	        	if (event.isShiftDown() && event.getTextDeltaX() > 0)
	        	{
	        		queuemanager.formerTitle(ListEnum.QUEUE);
			 		updateQueues(false, true, false);
			 		updateFormattedTitles(false, true, false, false);		        		
	        	} else if (event.isShiftDown() && event.getTextDeltaX() < 0)
	        	{
	        		queuemanager.nextTitle(ListEnum.QUEUE);
			 		updateQueues(false, true, false);
			 		updateFormattedTitles(false, true, false, false);
	        	}		        	
	        }
	    });
	
	this.currentsnopes.setOnScroll(new EventHandler<ScrollEvent>() {
        @Override public void handle(ScrollEvent event) {
        	if (event.isShiftDown() && event.getTextDeltaX() > 0)
        	{
        		queuemanager.formerTitle(ListEnum.NOPES);
		 		updateQueues(true, false, false);
		 		updateFormattedTitles(true, false, false, false);		        		
        	} else if (event.isShiftDown() && event.getTextDeltaX() < 0)
        	{
        		queuemanager.nextTitle(ListEnum.NOPES);
		 		updateQueues(true, false, false);
		 		updateFormattedTitles(true, false, false, false);
        	}		        	
        }
    });
	
	this.currentspicks.setOnScroll(new EventHandler<ScrollEvent>() {
        @Override public void handle(ScrollEvent event) {
        	if (event.isShiftDown() && event.getTextDeltaX() > 0)
        	{
        		queuemanager.formerTitle(ListEnum.PICKS);
		 		updateQueues(false, false, true);
		 		updateFormattedTitles(false, false, true, false);		        		
        	} else if (event.isShiftDown() && event.getTextDeltaX() < 0)
        	{
        		queuemanager.nextTitle(ListEnum.PICKS);
		 		updateQueues(false, false, true);
		 		updateFormattedTitles(false, false, true, false);
        	}		        	
        }
    });	
	}
	
	
	private void configureEventsQueue() {
		 this.currentsqueue.addEventFilter(KeyEvent.KEY_RELEASED, keyEvent -> {					 
			 
			 	if (keyEvent.getCode() == KeyCode.ENTER && !keyEvent.isAltDown())
			 	{
			 		
			 		if (keyEvent.isShiftDown())
			 		{			 			
			 			//queuemanager.saveProgress();
			 			
			 		} else
			 		{
			 			exportQueue(queuemanager);				 			 			
			 		}			 			
			 	}	
			 	
			 	if (keyEvent.getCode() == KeyCode.F1)
			 	{
			 		try {
						queuemanager.openLink(1, keyEvent.isShiftDown(), keyEvent.isControlDown(), ListEnum.QUEUE);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			 	}
			 	
			 	if (keyEvent.getCode() == KeyCode.F2)
			 	{
			 		try {
			 			queuemanager.openLink(2, keyEvent.isShiftDown(), keyEvent.isControlDown(), ListEnum.QUEUE);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			 	}
			 	
			 	if (keyEvent.getCode() == KeyCode.F3)
			 	{
			 		try {
			 			queuemanager.openLink(3, keyEvent.isShiftDown(), keyEvent.isControlDown(), ListEnum.QUEUE);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			 	}
			 	
			 	if (keyEvent.getCode() == KeyCode.F4)
			 	{
			 		try {
			 			queuemanager.openLink(4, keyEvent.isShiftDown(), keyEvent.isControlDown(), ListEnum.QUEUE);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			 	}			 	
			 	
			 	
			 	
			 	if (keyEvent.getCode() == KeyCode.SPACE)
			 	{		
			 		
			 		if (keyEvent.isShiftDown())
			 		{
			 			try {
							queuemanager.openLinkContentText(ListEnum.QUEUE);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}	
			 		} else
			 		{
			 			try {
							queuemanager.openLinkContentList(ListEnum.QUEUE);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}	
			 		}
			 					 		
			 					 		
			 	}			 
			 	if (keyEvent.getCode() == KeyCode.UP)
			 	{
			 		queuemanager.formerTitle(ListEnum.QUEUE);
			 		updateQueues(false, true, false);
			 		updateFormattedTitles(false, true, false, true);
			 	}
			 	if (keyEvent.getCode() == KeyCode.DOWN)
			 	{
			 		queuemanager.nextTitle(ListEnum.QUEUE);
			 		updateQueues(false, true, false);
			 		updateFormattedTitles(false, true, false, true);
				}		 	
			 	
			 	
			 	if (keyEvent.getCode() == KeyCode.RIGHT)
			 	{					 		
			 		if (keyEvent.isControlDown())
			 		{
			 			queuemanager.setZAFlag();
			 		}
			 		if (keyEvent.isShiftDown())
			 		{
			 			queuemanager.setSwitchFlag();
			 		}
			 		if (keyEvent.isAltDown())
			 		{
			 			TextInputDialog dialog = new TextInputDialog("");
			 			dialog.setTitle(null);
			 			dialog.setHeaderText(null);
			 			dialog.setContentText("Hinweis: ");
			 			dialog.initStyle(StageStyle.UTILITY);
			 			Optional<String> result = dialog.showAndWait();
			 			result.ifPresent(answer -> queuemanager.setAltFlag(answer));			 			
			 		}
			 		
			 		queuemanager.moveTitle(ListEnum.QUEUE, false, ListEnum.PICKS, true, false);
			 		this.lastAction = 2;
			 		
			 		updateQueues(false, true, true);
			 		updateFormattedTitles(false, true, true, true);
			 		
			 		
			 	}
			 	if (keyEvent.getCode() == KeyCode.LEFT)
			 	{	
			 		queuemanager.moveTitle(ListEnum.QUEUE, false, ListEnum.NOPES, true, false);
			 		this.lastAction = 1;
			 		
			 		updateQueues(true, true, false);
			 		updateFormattedTitles(true, true, false, true);
			 		
			 	}
			 	if (keyEvent.getCode() == KeyCode.BACK_SPACE)
			 	{				 			
			 		if (this.lastAction == 1)
			 		{
			 			queuemanager.moveTitle(ListEnum.NOPES, false, ListEnum.QUEUE, false, true);
				 		
				 		updateQueues(true, true, true);
				 		updateFormattedTitles(true, true, true, true);	 			
			 		}
			 		else if  (this.lastAction == 2)
			 			{			 	
			 			queuemanager.moveTitle(ListEnum.PICKS, false, ListEnum.QUEUE, false, true);
			 			queuemanager.resetFlag();
			 		
				 		updateQueues(true, true, true);
				 		updateFormattedTitles(true, true, true, true);
			 			
			 		}
			 		this.lastAction = 0;
			 	}		 	
			 	updateQueueProgress(queuemanager.getProgress());
				updateProgressText(queuemanager.getProgressText());
	        });		
	}
	
	private void configureEventsPicks() {
		 this.currentspicks.addEventFilter(KeyEvent.KEY_RELEASED, keyEvent -> {
			 
			if (keyEvent.getCode() == KeyCode.SPACE)
			{
				if (keyEvent.isShiftDown())
		 		{
		 			try {
						queuemanager.openLinkContentText(ListEnum.PICKS);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
		 		} else
		 		{
		 			try {
						queuemanager.openLinkContentList(ListEnum.PICKS);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
		 		}				
			}			 
			 
			 if (keyEvent.getCode() == KeyCode.ENTER)
			 	{
			 		
			 		if (keyEvent.isShiftDown())
			 		{			 			
			 			//queuemanager.saveProgress();
			 			
			 		} else
			 		{
			 			exportQueue(queuemanager);				 			 			
			 		}			 			
			 	}					
			 if (keyEvent.getCode() == KeyCode.UP)
			 	{
			 		queuemanager.formerTitle(ListEnum.PICKS);
			 		updateQueues(false, false, true);
			 		updateFormattedTitles(false, false, true, true);
			 		
			 	}
			 	if (keyEvent.getCode() == KeyCode.DOWN)
			 	{
			 		queuemanager.nextTitle(ListEnum.PICKS);
			 		updateQueues(false, false, true);
			 		updateFormattedTitles(false, false, true, true);		 	
			 	}
			 	if (keyEvent.getCode() == KeyCode.RIGHT)
			 	{	
			 		//
			 		
			 	}
			 	if (keyEvent.getCode() == KeyCode.LEFT)
			 	{				 		
			 		queuemanager.moveTitle(ListEnum.PICKS, false, ListEnum.QUEUE, false, true);
			 		queuemanager.resetFlag();
			 		updateQueues(false, true, true);
			 		updateFormattedTitles(false, true, true, true);					 		
			 	}
			 	if (keyEvent.getCode() == KeyCode.RIGHT)
			 	{				 		
			 		queuemanager.moveTitle(ListEnum.PICKS, false, ListEnum.NOPES, true, false);
			 		queuemanager.resetFlag();
			 		updateQueues(true, false, true);
			 		updateFormattedTitles(true, false, true, true);					 		
			 	}
			 	if (keyEvent.getCode() == KeyCode.F1)
			 	{
			 		try {
			 			queuemanager.openLink(1, keyEvent.isShiftDown(), keyEvent.isControlDown(), ListEnum.PICKS);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			 	}
			 	
			 	if (keyEvent.getCode() == KeyCode.F2)
			 	{
			 		try {
			 			queuemanager.openLink(2, keyEvent.isShiftDown(), keyEvent.isControlDown(), ListEnum.PICKS);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			 	}
			 	
			 	if (keyEvent.getCode() == KeyCode.F3)
			 	{
			 		try {
			 			queuemanager.openLink(3, keyEvent.isShiftDown(), keyEvent.isControlDown(), ListEnum.PICKS);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			 	}
			 	
			 	if (keyEvent.getCode() == KeyCode.F4)
			 	{
			 		try {
			 			queuemanager.openLink(4, keyEvent.isShiftDown(), keyEvent.isControlDown(), ListEnum.PICKS);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			 	}
			 	
			 	updateQueueProgress(queuemanager.getProgress());
				updateProgressText(queuemanager.getProgressText());

	        });	
	}
	
	private void configureEventsNopes() {
		 this.currentsnopes.addEventFilter(KeyEvent.KEY_RELEASED, keyEvent -> {			 
			 if (keyEvent.getCode() == KeyCode.ENTER)
			 	{
			 		
			 		if (keyEvent.isShiftDown())
			 		{			 			
			 			//queuemanager.saveProgress();
			 			
			 		} else
			 		{
			 			exportQueue(queuemanager);				 			 			
			 		}			 			
			 	}
			 
			 if (keyEvent.getCode() == KeyCode.SPACE)
				{
					if (keyEvent.isShiftDown())
		 		{
		 			try {
						queuemanager.openLinkContentText(ListEnum.NOPES);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
		 		} else
		 		{
		 			try {
						queuemanager.openLinkContentList(ListEnum.NOPES);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
		 			}
				}
				
			 	if (keyEvent.getCode() == KeyCode.UP)
			 	{
			 		queuemanager.formerTitle(ListEnum.NOPES);			 		
			 		updateQueues(true, false, false);
			 		updateFormattedTitles(true, false, false, true);
			 		
			 	}
			 	if (keyEvent.getCode() == KeyCode.DOWN)
			 	{
			 		queuemanager.nextTitle(ListEnum.NOPES);			 		
			 		updateQueues(true, false, false);
			 		updateFormattedTitles(true, false, false, true);	 	
			 	}
			 	if (keyEvent.getCode() == KeyCode.RIGHT)
			 	{	
			 		
			 		queuemanager.moveTitle(ListEnum.NOPES, false, ListEnum.QUEUE, false, true);			 
			 		updateQueues(true, true, false);
			 		updateFormattedTitles(true, true, false, true);			 		
			 	}
			 	if (keyEvent.getCode() == KeyCode.LEFT)
			 	{	
			 		
			 		queuemanager.moveTitle(ListEnum.NOPES, false, ListEnum.PICKS, true, false);			 
			 		updateQueues(true, false, true);
			 		updateFormattedTitles(true, false, true, true);		 		
			 	}
			 	if (keyEvent.getCode() == KeyCode.LEFT)
			 	{	
			 		//
			 	}
			 	
				if (keyEvent.getCode() == KeyCode.F1)
			 	{
			 		try {
			 			queuemanager.openLink(1, keyEvent.isShiftDown(), keyEvent.isControlDown(), ListEnum.NOPES);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			 	}
			 	
			 	if (keyEvent.getCode() == KeyCode.F2)
			 	{
			 		try {
			 			queuemanager.openLink(2, keyEvent.isShiftDown(), keyEvent.isControlDown(), ListEnum.NOPES);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			 	}
			 	
			 	if (keyEvent.getCode() == KeyCode.F3)
			 	{
			 		try {
			 			queuemanager.openLink(3, keyEvent.isShiftDown(), keyEvent.isControlDown(), ListEnum.NOPES);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			 	}
			 	
			 	if (keyEvent.getCode() == KeyCode.F4)
			 	{
			 		try {
			 			queuemanager.openLink(4, keyEvent.isShiftDown(), keyEvent.isControlDown(), ListEnum.NOPES);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			 	}			 	
			 			 	
			 	updateQueueProgress(queuemanager.getProgress());
				updateProgressText(queuemanager.getProgressText());
	            
	        });	
	}	
}

