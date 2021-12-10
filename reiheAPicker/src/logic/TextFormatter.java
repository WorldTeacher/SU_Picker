package logic;

import java.util.ArrayList;
import java.util.List;

public class TextFormatter {
	
	private List<List<String>> queueTitles = null;
	private List<List<String>> nopeTitles = new ArrayList<List<String>>();
	private List<List<String>> pickTitles = new ArrayList<List<String>>();
	
	private int currentIteration;
	public List<String> ColumnNames;
	
	public TextFormatter(List<List<String>> titles)
	{
		this.queueTitles = titles;
		this.ColumnNames = titles.get(0);
		this.currentIteration = 1;
	}
	
	public String nextTitle()
	{
		if (this.currentIteration < this.queueTitles.size()-1)
		{
			this.currentIteration ++;
			return fetchFormattedTitle();
		}
		return fetchFormattedTitle();
	}
	
	public String formerTitle()
	{
		if (this.currentIteration > 1)
		{
			this.currentIteration --;
			return fetchFormattedTitle();
		}
		return fetchFormattedTitle();
	}
	
	public String pickCurrentTitle()
	{
		this.pickTitles.add(this.queueTitles.get(currentIteration));		
		this.queueTitles.remove(currentIteration);
		return fetchFormattedTitle();
	}
	
	public String denyCurrentTitle()
	{
		this.nopeTitles.add(this.queueTitles.get(currentIteration));		
		this.queueTitles.remove(currentIteration);
		return fetchFormattedTitle();
	}
	
	public String fetchTitlesNopes()
	{
		
		String returnString = new String();
		
		for (int counter = this.nopeTitles.size() -1; counter > 0; counter--)
		{
			returnString = returnString + "\r\n" + this.nopeTitles.get(counter).get(0);
		}
		return returnString;
	}
	
	public String fetchTitlesPicks()
	{
		
		String returnString = new String();
		
		for (int counter = this.pickTitles.size() -1; counter > 0; counter--)
		{
			returnString = returnString + "\r\n" + this.pickTitles.get(counter).get(0);
		}
		return returnString;
	}

	
	public String fetchFormattedTitle()
	{		
		String returnString = new String();
		List<String> currentTitle = queueTitles.get(this.currentIteration);
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

}
