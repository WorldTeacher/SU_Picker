package logic;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.stage.Stage;
import javafx.util.Callback;
import models.DdcWhiteListModel;
import models.ListEnum;
import models.PropertyModel;
import var.Constants;

public class QueueManager {
	
	PropertyFileHandler propertyFileHandler = PropertyFileHandler.getInstance();
	
	private List<List<String>> queueTitles = null;
	private List<List<String>> nopeTitles = new ArrayList<List<String>>();
	private List<List<String>> pickTitles = new ArrayList<List<String>>();
	
	private List<DdcWhiteListModel> ddcWhitelistModels;
	private String[] publisherBlacklist;
	
	public int pickTitlescurrentIteration;
	public int nopeTitlescurrentIteration;
	public int queueTitlescurrentIteration;
	
	private int contentsTextIndex = -1;
	private int contentsIndex = -1;
	private int ddcIndex = -1;	
	private int isbnIndex = -1;
	private int publisherIndex = -1;
	private int authorIndex = -1;
	
	private ApiCallerClass apiCaller;
	
	public List<String> ColumnNames;
	public double initialSizeQueueTitles = -1;
	
	
	public QueueManager(List<List<String>> titles, ProgressIndicator progressIndicator)
	{
		this.apiCaller = new ApiCallerClass();
		
		titles.get(0).add(1, "HINWEIS");
		for (int counter = 1; counter <= titles.size()-1; counter ++)
		{
			titles.get(counter).add(1, "-");
		}	
		titles.get(0).add(2, Constants.bestandLabel);
		for (int counter = 1; counter <= titles.size()-1; counter ++)
		{
			titles.get(counter).add(2, "-");
		}	
		
		if (!this.propertyFileHandler.propertyFileModel.get_settings_API_link().isBlank() &&
				this.propertyFileHandler.propertyFileModel.get_settings_API_CheckApiAfterImport().equals("true"))
		{
			setIsbnIndex(titles);				
			progressIndicator.setVisible(true);	
			
			UpdateDataApiRunable updateDataApiRunable = new UpdateDataApiRunable(titles, this.isbnIndex, progressIndicator);
			Thread newThread = new Thread(updateDataApiRunable);				
			newThread.start();			
						
			progressIndicator.progressProperty().addListener((observable, oldValue, newValue) -> {
				if ((double) newValue >= (double) 1)
				{
					updateLists(titles);
				}			
			});
	
		}	else
		{
			updateLists(titles);
		}
	}
	
	public boolean listNullOrEmpty(ListEnum listEnum)
	{
		List<List<String>> list = getList(listEnum);
		if (list == null || list.size() == 0 || list.isEmpty())
		{
			return true;
		}
		return false;
	}
	
	public boolean titleIsOwnedOrOpenAccess(ListEnum listEnum)
	{
		List<String> title = getCurrentTitle(listEnum);
		if (title != null)
		{
			if (title.get(Constants.holdingsIndex).contains(Constants.bestandvorhanden) || title.get(Constants.holdingsIndex).contains(Constants.oaMarker))
			{
				return true;
			}			
		}
		return false;
	}
	
	public boolean titleApiHasNotYetRun(ListEnum listEnum)
	{
		List<String> title = getCurrentTitle(listEnum);
		if (title != null)
		{
			if (title.get(Constants.holdingsIndex).contains(Constants.emptyField))
			{
				return true;
			}			
		}
		return false;
	}
	
	public boolean titleApiCouldNotBeAccessed(ListEnum listEnum)
	{
		List<String> title = getCurrentTitle(listEnum);
		if (title != null)
		{
			if (title.get(Constants.holdingsIndex).contains(Constants.apiCouldNotBeAccessed))
			{
				return true;
			}			
		}
		return false;
	}
	
	public List<String> getCurrentTitle(ListEnum listEnum)
	{
		switch (listEnum)
		{
		case NOPES:
		{
			
			if (this.nopeTitles.size()>0 && this.nopeTitles.size()-1 >= this.nopeTitlescurrentIteration)
			{
			return this.nopeTitles.get(this.nopeTitlescurrentIteration);
			}
		return null;
		}
		case QUEUE:
		{
			if (this.queueTitles.size()>0 && this.queueTitles.size()-1 >= this.queueTitlescurrentIteration)
			{
				return this.queueTitles.get(this.queueTitlescurrentIteration);	
			}
			return null;
			
		}
		case PICKS:
		{
			if (this.pickTitles.size()>0 && this.pickTitles.size()-1 >= this.pickTitlescurrentIteration)
			{
			return this.pickTitles.get(this.pickTitlescurrentIteration);
			}
			return null;
			}
		}
		return null;
	}
	
	public List<List<String>> getList(ListEnum listEnum)
	{
		switch (listEnum)
		{
		case NOPES:
		{
			return this.nopeTitles;
		}
		case QUEUE:
		{
			return this.queueTitles;
		}
		case PICKS:
		{
			return this.pickTitles;
		}
		}
		return null;
	}
	
	public int getIterator(ListEnum listEnum)
	{
		switch (listEnum)
		{
		case NOPES:
		{
			return this.nopeTitlescurrentIteration;
		}
		case QUEUE:
		{
			return this.queueTitlescurrentIteration;
		}
		case PICKS:
		{
			return this.pickTitlescurrentIteration;
		}
		}
		return -99;
	}
	
	public void updateLists(List<List<String>> titles)
	{
		this.queueTitles = titles.subList(1, titles.size());
		this.ColumnNames = titles.get(0);
		this.queueTitlescurrentIteration = 0;			
		
		if (this.initialSizeQueueTitles == -1)
		{
			this.initialSizeQueueTitles = this.queueTitles.size();
		}
		
		if (this.propertyFileHandler.propertyFileModel.get_settings_API_CheckApiAfterImport().equals("true") && this.propertyFileHandler.propertyFileModel.get_settings_API_RemoveHoldings().equals("true") && !this.propertyFileHandler.propertyFileModel.get_settings_API_link().isBlank())
		{
			removeHoldings();
		}
		
		if (this.propertyFileHandler.propertyFileModel.get_settings_API_CheckApiAfterImport().equals("true") && this.propertyFileHandler.propertyFileModel.get_settings_API_RemoveOA().equals("true") && !this.propertyFileHandler.propertyFileModel.get_settings_API_link().isBlank())
		{
			removeOA();
		}		
		
		if (!this.propertyFileHandler.propertyFileModel.get_settings_DDCWhitelist().isBlank() && !this.propertyFileHandler.propertyFileModel.get_settings_DDCWhitelist().isEmpty())
		{
			initiateDdcWhitelist();
			removeUnwantedDDCs();				
		}		
		
		if (this.propertyFileHandler.propertyFileModel.get_settings_RemoveDuplicates().equals("true"))
		{
			removeDuplicatesInQueue();
		}	
		if (!this.propertyFileHandler.propertyFileModel.get_settings_PublisherBlacklist().isBlank() && !this.propertyFileHandler.propertyFileModel.get_settings_PublisherBlacklist().isEmpty())
		{
			initiatePublisherBlacklist();
			removeBadPublishers();			
		}
		if (this.propertyFileHandler.propertyFileModel.get_settings_BlockBelletristik().equals("true"))
		{
			removeBelletristik();
		}			
	}
	
	public List<List<String>> get10Formers(ListEnum listEnum)
	{
		List<List<String>> titles = getList(listEnum);
		int iteration = getIterator(listEnum);
		
		if (iteration >= 10)
		{
			return titles.subList(iteration - 10, iteration);
		} else if (iteration != 0 && !titles.isEmpty())
		{
			return titles.subList(0, iteration);
		}
		return null;
	}
	
	public List<List<String>> get10Nexts(ListEnum listEnum)
	{
		List<List<String>> titles = getList(listEnum);
		int iteration = getIterator(listEnum);
		
		if (iteration <= titles.size()-11)
		{
			return titles.subList(iteration + 1, iteration + 10);
		} else if (iteration <= titles.size()-1 && !titles.isEmpty())
		{
			return titles.subList(iteration + 1, titles.size());
		}
		return null;		
	}	
	
	public String getProgressText()
	{
		return this.queueTitles.size() + "/" + (int) this.initialSizeQueueTitles; 
	}
	
	public boolean isExportFolderValid()
	{
		if (this.propertyFileHandler.propertyFileModel.get_settings_ExportFileFolder().isBlank())
		{
			return false;
		}
		if (this.propertyFileHandler.propertyFileModel.get_settings_ExportFileFolder().isEmpty())
		{
			return false;
		}
		if (this.propertyFileHandler.propertyFileModel.get_settings_ExportFileFolder() == null)
		{
			return false;
		}
		return true;
	}
	
	
	public double getProgress()
	{
		double initialSize = this.initialSizeQueueTitles;
		double queueSize = this.queueTitles.size()-1;
		double currentProgress = (initialSize - queueSize) / initialSize;
		
		return  (currentProgress);
	}
	
	private void removeBelletristik()
	{
		setDdcIndex();
		
		for (int counter = 0; counter <= this.queueTitles.size()-1; counter ++)
		{		
				if (!this.queueTitles.get(counter).get(this.ddcIndex).equals("-") && !this.queueTitles.get(counter).get(this.ddcIndex).isEmpty())
				{
					if (this.queueTitles.get(counter).get(this.ddcIndex).contains("B"))
					{
						System.out.println("removed belletristic book: " + this.queueTitles.get(counter).get(0));
						this.queueTitles.get(counter).set(1, "Als belletristisch aussortiert!");
						this.nopeTitles.add(this.queueTitles.get(counter));
						this.queueTitles.remove(this.queueTitles.get(counter));
						counter --;
					}
				}							
		}		
	}
	
	private void removeBadPublishers()
	{		
		setPublisherIndex();
		
		for (int counter = 0; counter <= this.queueTitles.size()-1; counter ++)
		{		
				if (!this.queueTitles.get(counter).get(this.publisherIndex).equals("-") && !this.queueTitles.get(counter).get(this.publisherIndex).isEmpty())
				{
					for (String publisher : this.publisherBlacklist)
					{
						if (this.queueTitles.get(counter).get(this.publisherIndex).contains(publisher))
						{
							System.out.println("removed publisher: " + publisher + " in: " + this.queueTitles.get(counter).get(0));
							this.queueTitles.get(counter).set(1, "Publisher unerwünscht!");
							this.nopeTitles.add(this.queueTitles.get(counter));
							this.queueTitles.remove(this.queueTitles.get(counter));
							counter --;
						}
					}
				}							
		}
	}
	
	private void initiatePublisherBlacklist() 
	{
		setPublisherIndex();	
		this.publisherBlacklist = this.propertyFileHandler.propertyFileModel.get_settings_PublisherBlacklist().split(";");		
	}	
	
	
	private void removeDuplicatesInQueue()
	{
		setIsbnIndex();
		
		for (int outerCounter = 0; outerCounter <= this.queueTitles.size()-1; outerCounter ++)
		{
			for (int innerCounter = 0; innerCounter <= this.queueTitles.size()-1; innerCounter ++)
			{
				if (!this.queueTitles.get(innerCounter).get(this.isbnIndex).equals("-") && !this.queueTitles.get(innerCounter).get(this.isbnIndex).isEmpty())
				{
					if (innerCounter != outerCounter && this.queueTitles.get(outerCounter).get(this.isbnIndex).equals(this.queueTitles.get(innerCounter).get(isbnIndex)))
					{
						System.out.println("removed duplicate: " + this.queueTitles.get(innerCounter).get(0) + " <---|---> " + this.queueTitles.get(outerCounter).get(0));
						this.queueTitles.get(innerCounter).set(1, "Als Duplikat aussortiert!");
						this.nopeTitles.add(this.queueTitles.get(innerCounter));
						this.queueTitles.remove(this.queueTitles.get(innerCounter));
						if (innerCounter > 0)
						innerCounter --;
						if (outerCounter > 0)
						outerCounter --;
					}
				}									
			}				
		}
	}
	
	private void removeUnwantedDDCs()
	{
		
		setDdcIndex();	
		
		if (this.ddcIndex != -1)
		{
			//iterate through all queue titels
			for (int counter = 0; counter <= this.queueTitles.size()-1; counter ++)
			{
				String[] ddcListTitle = this.queueTitles.get(counter).get(this.ddcIndex).replace(" ", "").split(";");
				boolean discard = true;
				
				// and for each queue title, iterate through all ddcs attached
				for (String currentddc: ddcListTitle)
				{
					// and for all ddcs attached, iterate through all ddc whiteListModels
					for (DdcWhiteListModel ddcWhitelistModel: this.ddcWhitelistModels)
					{
						//tryparse, skip if it cant.
					
						//if current ddc whitelist matches
						if (ddcWhitelistModel.getCovers0to100()  && discard)
						{							
							if (tryParseInt(currentddc) >= ddcWhitelistModel.getDdcNumber() && tryParseInt(currentddc) < ddcWhitelistModel.getDdcNumber() +100)
							{								
								discard = false;
							}
						} else	if (tryParseInt(currentddc) == ddcWhitelistModel.getDdcNumber()  && discard)
							{								
								discard = false;
							}						
					}
				}	
				if (discard)
				{
					System.out.println("removed title due to DDC: " + this.queueTitles.get(counter).get(this.ddcIndex));
					this.queueTitles.get(counter).set(1, "Anhand DDC aussortiert!");
					this.nopeTitles.add(this.queueTitles.get(counter));
					this.queueTitles.remove(this.queueTitles.get(counter));
					counter --;
				}
			}
			
		}
	}
	
	public int tryParseInt(String value) {
	    try {
	    	
	    	if (value.contains("."))
	    	{
	    		return Integer.parseInt(value.substring(0, value.indexOf('.')));
	    	} else
	    	{
	    		return Integer.parseInt(value);
	    	}
	        
	    } catch (NumberFormatException e) {
	    	if (!value.equals("B") && !value.equals("K") && !value.equals("S"))
	    	{
		    	System.out.println("cannot parse DDC: " + value);	    		
	    	}
	        return -1;
	    }
	}
	
	private void removeHoldings()
	{		
		for (int counter = 0; counter <= this.queueTitles.size()-1; counter ++)
		{			
			if (this.queueTitles.get(counter).get(2).contains(Constants.bestandvorhanden))
			{
				System.out.println("removed book that we already own: " + this.queueTitles.get(counter).get(0));
				this.queueTitles.get(counter).set(1, "Aufgrund Bestandsnachweis aussortiert!");
				this.nopeTitles.add(this.queueTitles.get(counter));
				this.queueTitles.remove(this.queueTitles.get(counter));		
				counter--;
				
				
			}
			
		}
	}
	
	private void removeOA()
	{		
		for (int counter = 0; counter <= this.queueTitles.size()-1; counter ++)
		{			
			if (this.queueTitles.get(counter).get(2).contains(Constants.oaMarker))
			{
				System.out.println("removed OA book: " + this.queueTitles.get(counter).get(0));
				this.queueTitles.get(counter).set(1, "Aufgrund OA aussortiert!");
				this.nopeTitles.add(this.queueTitles.get(counter));
				this.queueTitles.remove(this.queueTitles.get(counter));			
				counter--;
				
				
			}
			
		}
	}
	
	private void initiateDdcWhitelist()
	{		
					
		String[] ddcWhitelistSettings = this.propertyFileHandler.propertyFileModel.get_settings_DDCWhitelist().split(";");
		
		this.ddcWhitelistModels = new ArrayList<DdcWhiteListModel>();
		
		for (String ddcWhitelistSetting: ddcWhitelistSettings)
		{				
			if (ddcWhitelistSetting.contains("+"))
			{
				ddcWhitelistSetting = ddcWhitelistSetting.replace("+", "");
				this.ddcWhitelistModels.add(new DdcWhiteListModel(Integer.parseInt(ddcWhitelistSetting), true));
			}
			else
			{
				this.ddcWhitelistModels.add(new DdcWhiteListModel(Integer.parseInt(ddcWhitelistSetting), false));
			}	
		}
	}
	
	public void apiShortcut(ListEnum listEnum)
	{
		try {
			callApiOnCurrentData(listEnum);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void nextTitle(ListEnum listEnum)
	{		
		
		switch (listEnum)
		{
		case NOPES:
		{
			if (this.nopeTitlescurrentIteration < this.nopeTitles.size()-1)
			{
				this.nopeTitlescurrentIteration ++;
			}
			return;
		}
		case QUEUE:
		{			if (this.queueTitlescurrentIteration < this.queueTitles.size()-1)
			{
				this.queueTitlescurrentIteration ++;	
				
			}
			return;
		}
		case PICKS:
		{
			if (this.pickTitlescurrentIteration < this.pickTitles.size()-1)
			{
				this.pickTitlescurrentIteration ++;
			}
			return;	
		}
		}
	}
	
	public void formerTitle(ListEnum listEnum)
	{		
		
		switch (listEnum)
		{
		case NOPES:
		{
			if (this.nopeTitlescurrentIteration >= 1)
			{
				this.nopeTitlescurrentIteration --;
			}
			return;
		}
		case QUEUE:
		{
			if (this.queueTitlescurrentIteration >= 1)
			{
				this.queueTitlescurrentIteration --;
			}
			return;
		}
		case PICKS:
		{
			if (this.pickTitlescurrentIteration >= 1)
			{
				this.pickTitlescurrentIteration --;
			}
			return;	
		}
		}
	}
	
	public void setIterator(ListEnum listEnum, int iterator)
	{
		switch (listEnum)
		{
		case NOPES:
		{
			this.nopeTitlescurrentIteration = iterator;
			return;
		}
		case QUEUE:
		{
			this.queueTitlescurrentIteration = iterator;
			return;
		}
		case PICKS:
		{
			this.pickTitlescurrentIteration = iterator;
			return;	
		}
		}
	}
	
	public void moveTitle(ListEnum from, boolean setFromToLastIndex, ListEnum to, boolean setToToLastIndex, boolean moveToCurrentIndex)
	{
		List<List<String>> fromlist = getList(from);
		List<List<String>> tolist = getList(to);
		int fromIterator = getIterator(from);	
		int toIterator = getIterator(to);
		if (fromlist.size() > 0)
		{
			if (moveToCurrentIndex && toIterator >= 0)
			{
				tolist.add(toIterator, fromlist.get(fromIterator));
			} else if (toIterator >= 0)
			{
				tolist.add(fromlist.get(fromIterator));		
			} else
			{
				tolist.add(fromlist.get(fromIterator));
				this.setIterator(to, tolist.size()-1);
			}
			
			fromlist.remove(fromIterator);	
			setIterator(from, getNextIterator(from, setFromToLastIndex));
			setIterator(to, getNextIterator(to, setToToLastIndex));				
		}		
	}
	
	public int getNextIterator(ListEnum listEnum, boolean setToLastIndex)
	{
		if (setToLastIndex)
		{
			return getList(listEnum).size()-1;
		}
		
		List<List<String>> list = getList(listEnum);
		int iterator = getIterator(listEnum);
				
		if (iterator <= list.size()-1)
		{
			return iterator;
		} 
		return list.size()-1;	
	}
	
	public String fetchFormattedTitle(ListEnum listEnum, boolean updateApi)
	{
		List<String> title = getCurrentTitle(listEnum);
		
		if (title != null)
		{
			if (updateApi)
			{
			apiShortcut(listEnum);
			}
			return fetchFormattedTitle(title);
		}
		else return "";
	}
		
	private String fetchFormattedTitleExport(List<String> currentTitle)
	{
		String returnString = new String();	
		boolean containsHinweis = false;
		if (!currentTitle.get(1).equals("-"))
		{
			containsHinweis = true;
		}
		for (int counter = 0; counter < currentTitle.size(); counter++)
		{			
			if (currentTitle.get(counter).equals("-"))
			{
				//dont show empty columns?
			}
			else 
			{	
				if (containsHinweis && counter == 0)
				{
					returnString = "\r\n" + " - - - " + currentTitle.get(1) + " - - - ";
					returnString = returnString + "\r\n" + this.ColumnNames.get(0) + ": " + currentTitle.get(0);
					counter = 2;
				}
				returnString = returnString + "\r\n" + this.ColumnNames.get(counter) + ": " + currentTitle.get(counter);				
			}
		}		
		return returnString;
	}
	
	private String fetchFormattedTitle(List<String> currentTitle)
	{		
		String returnString = new String();		
		for (int counter = 0; counter < currentTitle.size(); counter++)
		{
			if (currentTitle.get(counter).equals("-"))
			{
				//dont show empty columns?
			}
			else 
			{				
				if (returnString.isBlank())
				{
					returnString = this.ColumnNames.get(counter) + ": " + currentTitle.get(counter);
				} else
				{
					returnString = returnString + "\r\n" + this.ColumnNames.get(counter) + ": " + currentTitle.get(counter);					
				}				
			}
		}		
		return returnString;		
	}	
	
	
	public void openLink(int searchMethod, boolean shift, boolean ctrl, ListEnum listEnum) throws IOException
	{
		if (shift)
		{
			searchMethod += 4;
		} else if (ctrl)
		{
			searchMethod += 8;
		}
		openUrlContents(replaceVariablesInLink(getLink(searchMethod), listEnum));
	}
		
	private String getLink(int searchMethod)
	{
		String link = "";
		
		switch (searchMethod) {
		case 1:
			link = this.propertyFileHandler.propertyFileModel.get_settings_f1search();
			break;
		case 2:
			link = this.propertyFileHandler.propertyFileModel.get_settings_f2search();
			break;
		case 3:
			link = this.propertyFileHandler.propertyFileModel.get_settings_f3search();
			break;
		case 4:
			link = this.propertyFileHandler.propertyFileModel.get_settings_f4search();
			break;
		case 5:
			link = this.propertyFileHandler.propertyFileModel.get_settings_f1searchShift();
			break;
		case 6:
			link = this.propertyFileHandler.propertyFileModel.get_settings_f2searchShift();
			break;
		case 7:
			link = this.propertyFileHandler.propertyFileModel.get_settings_f3searchShift();
			break;
		case 8:
			link = this.propertyFileHandler.propertyFileModel.get_settings_f4searchShift();
			break;
		case 9:
			link = this.propertyFileHandler.propertyFileModel.get_settings_f1searchCtrl();
			break;
		case 10:
			link = this.propertyFileHandler.propertyFileModel.get_settings_f2searchCtrl();
			break;
		case 11:
			link = this.propertyFileHandler.propertyFileModel.get_settings_f3searchCtrl();
			break;
		case 12:
			link = this.propertyFileHandler.propertyFileModel.get_settings_f4searchCtrl();
			break;
		default:
			System.out.println("Keine Suchmethode gewählt");
			break;
		}	
		return link;
	}	
	
	private String replaceVariablesInLink(String link, ListEnum listEnum)
	{
		if (link.contains("[{[title]}]"))		{
			
			link = link.replace("[{[title]}]", getCurrentTitle(listEnum).get(0).split(" : ")[0].replace("\"", ""));
		}
		
		if (link.contains("[{[author]}]"))
		{
			setAuthorIndex();
			
			String authorContentRaw = getCurrentTitle(listEnum).get(this.authorIndex);
			authorContentRaw = authorContentRaw.replace("by", "");
			authorContentRaw = authorContentRaw.replace("edited", "");
			authorContentRaw = authorContentRaw.replace("and", "");
			authorContentRaw = authorContentRaw.replace("(Hrsg.)", "");
			authorContentRaw = authorContentRaw.replace("tranlation", "");
			authorContentRaw = authorContentRaw.replace("(Hg.)", "");		
			authorContentRaw = authorContentRaw.replace(",", "");
			authorContentRaw = authorContentRaw.replace(";", "");
			authorContentRaw = authorContentRaw.replace("erzählt und illustriert von", "");
	 			
			String[] authorContent = authorContentRaw.split(" ");
			String authorSearchTerm = "";
			int maxParts = 2;
			for (int counter = 0; counter < maxParts && counter < authorContent.length; counter ++)
			{			
				if (authorContent[counter].isBlank() || authorContent[counter].isEmpty())
				{
					maxParts ++;
				} else
				{
					if (!authorSearchTerm.isBlank())
					{
						authorSearchTerm += " " + authorContent[counter];
					} else 
					{
						authorSearchTerm = authorContent[counter];
					}	
				}
							
			}
			link = link.replace("[{[author]}]", authorSearchTerm);			
		}
		
		if (link.contains("[{[isbn]}]"))
		{
			setIsbnIndex();			
			link = link.replace("[{[isbn]}]", getCurrentTitle(listEnum).get(this.isbnIndex).split(";")[0]);		
		}
		
		if (link.contains("[{[publisher]}]"))
		{
			
			link = link.replace("[{[publisher]}]", getCurrentTitle(listEnum).get(this.publisherIndex));			
		}		
		return link;
	}
	
	public void openLinkContentList(ListEnum listEnum) throws IOException
	{
		setContentsIndex();		
		List<String> title = getCurrentTitle(listEnum);
		
		if (this.contentsIndex != -1 && title.size()-1 >= this.contentsIndex)
		{
			openUrlContents(title.get(this.contentsIndex));	
		}	
	}
	
	public void openLinkContentText(ListEnum listEnum) throws IOException
	{		
		setContentsIndex();	
		List<String> title = getCurrentTitle(listEnum);
		
		if (this.contentsTextIndex != -1 && title.size()-1 >= this.contentsTextIndex)
		{
			openUrlContents(title.get(this.contentsTextIndex));	
		}			
	}	

	private void setContentsIndex()
	{		
			if (this.contentsTextIndex == -1 || this.contentsIndex == -1)
			{		
				{
					for(int counter = 0; counter < this.ColumnNames.size()-1; counter ++)
					{
						if (this.ColumnNames.get(counter).equals("Inhaltstext"))
						{
							this.contentsTextIndex = counter;
						} else if (this.ColumnNames.get(counter).equals("Inhaltsverzeichnis"))
						{
							this.contentsIndex = counter;
						}				
					}
				}			
			}		
	}	
	
	private void setIsbnIndex()
	{
		setIsbnIndex(null);
	}
	
	private void setIsbnIndex(List<List<String>> titles)
	{
		if (this.isbnIndex == -1)
		{
			if (titles != null)
			{
				for (int counter = 0; counter <= titles.get(0).size()-1; counter ++)
				{						
					if (titles.get(0).get(counter).equals("ISBN"))
					{
					this.isbnIndex = counter;					
					}
				}
			} else 	{
				for (int counter = 0; counter < this.ColumnNames.size()-1; counter ++)
				{		
				
					if (this.ColumnNames.get(counter).equals("ISBN"))
					{
					this.isbnIndex = counter;					
					}
				}
			}
			
		}
	}
	
	private void setDdcIndex()
	{
	if (this.ddcIndex == -1)
		{
			for (int counter = 0; counter <= this.ColumnNames.size()-1; counter ++)
			{			
				if (this.ColumnNames.get(counter).equals("DDC-Sachgruppe"))
				{
					this.ddcIndex = counter;			
				}
		}
		}
	}
	
	private void setPublisherIndex()
	{
	if (this.publisherIndex == -1)
	{
		for (int counter = 0; counter <= this.ColumnNames.size()-1; counter ++)
		{
			if (this.ColumnNames.get(counter).equals("Verlag"))
			{
				this.publisherIndex = counter;		
			}
		}
	}
	}
	
	private void setAuthorIndex()
	{
		if (this.authorIndex == -1)
		{
			for (int counter = 0; counter <= this.ColumnNames.size()-1; counter ++)
			{
				if (this.ColumnNames.get(counter).equals("Verfasser"))
				{
					this.authorIndex = counter;		
				}
			}
		}
	}
	
	
	private void openUrlContents(String link) throws IOException
	{
		Runtime rt = Runtime.getRuntime();
		String url = link;
		rt.exec("rundll32 url.dll,FileProtocolHandler " + url);
	}
	
	public void exportPicks()
	{
		String exportString = new String();	
		
		for(int counter = 0; counter <= this.pickTitles.size()-1; counter++)
		{
			exportString += fetchFormattedTitleExport(this.pickTitles.get(counter)) + "\r\n";
		}
		ImportExportFileHandler.exportFile(exportString, this.propertyFileHandler.propertyFileModel.get_settings_ExportFileFolder());
	}
	
	public void setZAFlag()
	{
		this.queueTitles.get(this.queueTitlescurrentIteration).set(1, "Bitte zur Ansicht");
	}
	
	public void setSwitchFlag()
	{
		this.queueTitles.get(this.queueTitlescurrentIteration).set(1, "Bitte Auflage Tauschen");
	}
	
	public void setAltFlag(String text)
	{
		this.queueTitles.get(this.queueTitlescurrentIteration).set(1, text);
	}
	
	public void resetFlag()
	{
		if (this.queueTitlescurrentIteration >= 0)
		{
			this.queueTitles.get(this.queueTitlescurrentIteration).set(1, "-");			
		}
		else if (this.queueTitlescurrentIteration < 0 && this.queueTitles.size()>0)
		{
			this.queueTitlescurrentIteration = this.queueTitles.size() -1;
			this.queueTitles.get(this.queueTitlescurrentIteration).set(1, "-");			
		}
	}
	
	public String getCurrentOAKeyWord(ListEnum listEnum, String marker, String display)
	{
		if (this.propertyFileHandler.propertyFileModel.get_settings_API_markOA().equals("true"))
		{
			List<String> title = getCurrentTitle(listEnum);
			if (title != null && !title.isEmpty() && title.get(Constants.holdingsIndex).contains(marker))
			{
				return display;
			}					
		}
		return "";	
	}
	
	public String getCurrentKeyWords(ListEnum listEnum, boolean greenTrueRedFalse)
	{
		String[] listOfKeyWords = greenTrueRedFalse ? this.propertyFileHandler.propertyFileModel.get_settings_stichwortmarkierungGruen().split(";") : this.propertyFileHandler.propertyFileModel.get_settings_stichwortmarkierungRot().split(";");
		String currentKeyWords = "";
		
		List<String> title = getCurrentTitle(listEnum);
		
		if (title != null && !title.isEmpty())
		{
		
		for (String keyWord : listOfKeyWords)
		{
			if (keyWord.contains(":") && !title.isEmpty())
			{
				String[] comboKeyWord = keyWord.split(":");
				if (comboKeyWord.length == 2)
				{					
					for (int counter = 0; counter <= this.ColumnNames.size()-1; counter ++)
					{
						if (this.ColumnNames.get(counter).equals(comboKeyWord[0]))
						{							
							if (title.get(counter).toLowerCase().contains(comboKeyWord[1].toLowerCase()))
								{	
								currentKeyWords = currentKeyWords.isBlank() ? comboKeyWord[1] :  currentKeyWords + ", " + comboKeyWord[1];
								}
							}		
						}				
				}
			
			
				} else if (title.toString().toLowerCase().contains(keyWord.toLowerCase()) && !title.isEmpty())
					{
					currentKeyWords = currentKeyWords.isBlank() ? keyWord :  currentKeyWords + ", " + keyWord;
					}
		
			}
		
		}
		return 	currentKeyWords;	
	}	

	
	public void callApiOnCurrentData(ListEnum listEnum) throws MalformedURLException
	{	
		setIsbnIndex();		
		
		switch (listEnum)
		{
		case QUEUE:
		{
			if (this.queueTitles.get(queueTitlescurrentIteration).get(2).equals("-"))
			{
				
				try {
					this.queueTitles.get(this.queueTitlescurrentIteration).set(2, callApi(this.queueTitles.get(this.queueTitlescurrentIteration).get(isbnIndex)));
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		}
			
		case PICKS:
		{
			if (this.pickTitles.get(this.pickTitlescurrentIteration).get(2).equals("-"))
			{
				try {
					this.pickTitles.get(this.pickTitlescurrentIteration).set(2, callApi(this.pickTitles.get(this.pickTitlescurrentIteration).get(isbnIndex)));
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}				
			break;
			}
		case NOPES:
		{
			if (this.nopeTitles.get(this.nopeTitlescurrentIteration).get(2).equals("-"))
			{
				try {
					this.nopeTitles.get(this.nopeTitlescurrentIteration).set(2, callApi(this.nopeTitles.get(this.nopeTitlescurrentIteration).get(isbnIndex)));
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}			
			break;
		}	
	}
		
	}
	
	private String callApi(String isbnsField) throws MalformedURLException, InterruptedException
	{
		URL apiLink;
		String[] isbns = isbnsField.replace(" ", "").split(";");
		
		
		if (!this.propertyFileHandler.propertyFileModel.get_settings_API_link().isBlank() && isbns.length >= 1)
		{			
			if (isbns != null && isbns.length > 0 && !isbns[0].equals("-"))
			{
				String isbnSearchTerm = "";
				
				for (String isbn : isbns)
				{
					if (isbnSearchTerm.isBlank())
					{
						isbnSearchTerm = isbn;					
					} else
					{
						isbnSearchTerm = isbnSearchTerm + this.propertyFileHandler.propertyFileModel.get_settings_API_isbnSeparator() + isbn;				
					}
				}
				
				if (!isbnSearchTerm.isBlank())
				{
					
					apiLink = new URL(this.propertyFileHandler.propertyFileModel.get_settings_API_link().replace(Constants.isbnWildcard, isbnSearchTerm));
					String response = this.apiCaller.callUrl(apiLink);	
					String returnString = "";
					if (!response.isBlank())
					{
						
						if (response.contains(Constants.oaSearchTerm))
						{
							returnString = Constants.oaMarker;
						}
						if (response.contains(propertyFileHandler.propertyFileModel.get_settings_API_sigil()))
						{					
							if (returnString.isBlank())
							{
								returnString = Constants.bestandvorhanden;								
							} else
							{
								returnString = Constants.bestandvorhanden + ", " + returnString;
							}
							
						} else
						{
							if (returnString.isBlank())
							{
								returnString = Constants.bestandnichtvorhanden;								
							} else
							{
								returnString = Constants.bestandnichtvorhanden + ", " + returnString;
							}
						}
						return returnString;
					}
					
				}
			}
		}
		return(Constants.apiCouldNotBeAccessed);
	}
	
	
}
