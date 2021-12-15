package logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import models.DdcWhiteListModel;
import models.PropertyModel;

public class QueueManager {
	
	PropertyFileHandler propertyFileHandler = PropertyFileHandler.getInstance();
	
	private List<List<String>> queueTitles = null;
	private List<List<String>> nopeTitles = new ArrayList<List<String>>();
	private List<List<String>> pickTitles = new ArrayList<List<String>>();
	
	private List<DdcWhiteListModel> ddcWhitelistModels;
	private String[] publisherBlacklist;
	
	private int pickTitlescurrentIteration;
	private int nopeTitlescurrentIteration;
	private int queueTitlescurrentIteration;
	
	private int contentsIndex = -1;
	private int ddcIndex = -1;	
	private int isbnIndex = -1;
	private int publisherIndex = -1;
	
	public List<String> ColumnNames;
	
	
	public QueueManager(List<List<String>> titles)
	{
		titles.get(0).add(1, "HINWEIS");
		for (int counter = 1; counter <= titles.size()-1; counter ++)
		{
			titles.get(counter).add(1, "-");
		}		
		
		this.queueTitles = titles.subList(1, titles.size());
		this.ColumnNames = titles.get(0);
		this.queueTitlescurrentIteration = 0;		
		
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
	
	public void saveProgress()
	{		
		String saveFileString = "[queueTitles]" + this.queueTitles.toString() + "[nopeTitles]" + this.nopeTitles.toString() + "[pickTitles]" + this.pickTitles.toString();
		ImportExportFileHandler.exportFile(saveFileString, this.propertyFileHandler.propertyFileModel.get_settings_SaveFileFolder());
	}
	
	private void removeBelletristik()
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
		for (int counter = 0; counter < this.ColumnNames.size()-1; counter ++)
		{			
			if (this.ColumnNames.get(counter).equals("Verlag"))
			{
				this.publisherIndex = counter;			
			}
		}			
		this.publisherBlacklist = this.propertyFileHandler.propertyFileModel.get_settings_PublisherBlacklist().split(";");		
	}	
	
	
	private void removeDuplicatesInQueue()
	{
		for (int counter = 0; counter < this.ColumnNames.size()-1; counter ++)
		{			
			if (this.ColumnNames.get(counter).equals("ISBN"))
			{
				this.isbnIndex = counter;			
			}
		}	
		
		for (int outerCounter = 0; outerCounter <= this.queueTitles.size()-1; outerCounter ++)
		{
			for (int innerCounter = 0; innerCounter <= this.queueTitles.size()-1; innerCounter ++)
			{
				if (!this.queueTitles.get(innerCounter).get(isbnIndex).equals("-") && !this.queueTitles.get(innerCounter).get(isbnIndex).isEmpty())
				{
					if (innerCounter != outerCounter && this.queueTitles.get(outerCounter).get(isbnIndex).equals(queueTitles.get(innerCounter).get(isbnIndex)))
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
		
		for (int counter = 0; counter <= this.ColumnNames.size()-1; counter ++)
		{			
			if (this.ColumnNames.get(counter).equals("DDC-Sachgruppe"))
			{
				this.ddcIndex = counter;			
			}
		}		
		
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
	
	public void nextTitleQueue()
	{
		if (this.queueTitlescurrentIteration < this.queueTitles.size()-1)
		{
			this.queueTitlescurrentIteration ++;
		}
	}
	
	public void nextTitleNopes()
	{
		if (this.nopeTitlescurrentIteration < this.nopeTitles.size()-1)
		{
			this.nopeTitlescurrentIteration ++;
		}
	}
	
	public void nextTitlePicks()
	{
		if (this.pickTitlescurrentIteration < this.pickTitles.size()-1)
		{
			this.pickTitlescurrentIteration ++;
		}
	}
	
	
	public void formerTitleQueue()
	{
		if (this.queueTitlescurrentIteration >= 1)
		{
			this.queueTitlescurrentIteration --;
		}
	}
	
	public void formerTitlePicks()
	{
		if (this.pickTitlescurrentIteration >= 1)
		{
			this.pickTitlescurrentIteration --;
		}
	}
	
	public void formerTitleNopes()
	{
		if (this.nopeTitlescurrentIteration >= 1)
		{
			this.nopeTitlescurrentIteration --;
		}
	}
	
	public void pickCurrentTitle()
	{
		this.pickTitles.add(this.queueTitles.get(queueTitlescurrentIteration));		
		this.queueTitles.remove(queueTitlescurrentIteration);
		this.pickTitlescurrentIteration = this.pickTitles.size()-1;
		
	}
	
	public void denyCurrentTitle()
	{
		this.nopeTitles.add(this.queueTitles.get(queueTitlescurrentIteration));		
		this.queueTitles.remove(queueTitlescurrentIteration);
		this.nopeTitlescurrentIteration = this.nopeTitles.size()-1;
	}	
	
	public String fetchTitlesNopes()
	{
		
		String returnString = new String();
		
		for (int counter = this.nopeTitles.size() -1; counter >= 0; counter--)
		{
			returnString = returnString + "\r\n" + this.nopeTitles.get(counter).get(0);
		}
		return returnString;
	}

	public String fetchFormattedTitleQueue()
	{
		if (!this.queueTitles.isEmpty())
		{
		return fetchFormattedTitle(this.queueTitles.get(queueTitlescurrentIteration));
		}	
		else return "";
	}
	
	public String fetchFormattedTitleNope()
	{
		if (!this.nopeTitles.isEmpty())
		{
		return fetchFormattedTitle(this.nopeTitles.get(nopeTitlescurrentIteration));
		}	
		else return "";
	}
	
	public String fetchFormattedTitlePick()
	{
		if (!this.pickTitles.isEmpty())
		{
			return fetchFormattedTitle(this.pickTitles.get(pickTitlescurrentIteration));
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
				returnString = returnString + "\r\n" + this.ColumnNames.get(counter) + ": " + currentTitle.get(counter);				
			}
		}		
		return returnString;		
	}	
	
	public String fetchNextTitleListQueue()
	{
		return getNextTitles(this.queueTitles, this.queueTitlescurrentIteration);
	}
	
	public String fetchNextTitleListNope()
	{
		return getNextTitles(this.nopeTitles, this.nopeTitlescurrentIteration);
	}
	
	public String fetchNextTitleListPick()
	{
		return getNextTitles(this.pickTitles, this.pickTitlescurrentIteration);
	}	
	
	public String fetchFormerTitleListQueue()
	{
		return getFormerTitles(this.queueTitles, this.queueTitlescurrentIteration);
	}
	
	public String fetchFormerTitleListNope()
	{
		return getFormerTitles(this.nopeTitles, this.nopeTitlescurrentIteration);
	}
	
	public String fetchFormerTitleListPick()
	{
		return getFormerTitles(this.pickTitles, this.pickTitlescurrentIteration);
	}	
	
	
	private String getFormerTitles(List<List<String>> list, int currentIteration)
	{
		currentIteration --;
		if (!list.isEmpty() && currentIteration >= 0)
		{
			return fetchTitleListFormer(list.subList(0, currentIteration +1));
		}
		return "";
	}
	
	private String getNextTitles(List<List<String>> list, int currentIteration)
	{	
		currentIteration ++;
		if (!list.isEmpty() && currentIteration >= 0 && (currentIteration <= list.size()-1))
		{
		return fetchTitleListNext(list.subList(currentIteration, list.size()));
		}
		return "";
	}
	
	private String fetchTitleListFormer(List<List<String>> list)
	{		
		String returnString = new String();
		
		for (int counter = list.size() -1; counter >= 0; counter--)
		{
			returnString = returnString + "\r\n" + list.get(counter).get(0);
		}
		return returnString;
	}
	
	private String fetchTitleListNext(List<List<String>> list)
	{	
		String returnString = new String();
		
		for (int counter = 0; counter <= list.size()-1; counter++)
		{
			returnString = returnString + "\r\n" + list.get(counter).get(0);
		}
		return returnString;	
	}
	
	public void openQueueCurrentLink() throws IOException
	{		
		setContentsIndex();		
		if (this.contentsIndex != -1 && this.queueTitles.get(this.queueTitlescurrentIteration).size()-1 >= this.contentsIndex)
		{
			openUrlContents(this.queueTitles.get(this.queueTitlescurrentIteration).get(this.contentsIndex));	
		}			
	}
	
	public void openPickCurrentLink() throws IOException
	{		
		setContentsIndex();		
		if (this.contentsIndex != -1 && this.pickTitles.get(this.pickTitlescurrentIteration).size()-1 >= this.contentsIndex)
		{
			openUrlContents(this.pickTitles.get(this.pickTitlescurrentIteration).get(this.contentsIndex));	
		}			
	}
	
	public void openNopeCurrentLink() throws IOException
	{		
		setContentsIndex();		
			if (this.contentsIndex != -1 && this.nopeTitles.get(this.nopeTitlescurrentIteration).size()-1 >= this.contentsIndex)
			{
				openUrlContents(this.nopeTitles.get(this.nopeTitlescurrentIteration).get(this.contentsIndex));	
			}			
	}
	
	private void setContentsIndex()
	{
		if (this.contentsIndex == -1)
		{		
			{
				for(int counter = 0; counter < this.ColumnNames.size()-1; counter ++)
				{
					if (this.ColumnNames.get(counter).equals("Inhaltsverzeichnis"))
					{
						this.contentsIndex = counter;
					}			
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
	
	public void undoChoiceNopes()
	{		
		undoChoice(this.nopeTitles, this.nopeTitlescurrentIteration);
		this.nopeTitlescurrentIteration --;
	}
	
	public void undoChoicePicks()
	{
		undoChoice(this.pickTitles, this.pickTitlescurrentIteration);
		this.pickTitlescurrentIteration --;
	}
	
	private void undoChoice(List<List<String>> list, int currentIteration)
	{
		if (list.size() > 0)
		{
			this.queueTitles.add(0, list.get(currentIteration));
			list.remove(currentIteration);			
		}		
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
	
	public void resetFlag()
	{
		this.queueTitles.get(this.queueTitlescurrentIteration).set(1, "-");
	}

}
