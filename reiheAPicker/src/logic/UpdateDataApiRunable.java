package logic;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.stage.Stage;
import var.Constants;

public class UpdateDataApiRunable extends Thread {
	
	List<List<String>> titles;
	int isbnIndex;
	public float currentProgress = 0;
	PropertyFileHandler propertyFileHandler;
	ProgressIndicator progressIndicator;
	Stage progressStage;
	private ApiCallerClass apiCaller;

	
	public UpdateDataApiRunable(List<List<String>> titles, int isbnIndex, ProgressIndicator progressIndicator)
	{		       
		this.apiCaller = new ApiCallerClass();
		this.titles = titles;
		this.isbnIndex = isbnIndex;
		this.progressIndicator = progressIndicator;
		this.propertyFileHandler = PropertyFileHandler.getInstance();			
	}
	
	public void setCurrentProgress(int max, int current)
	{			
		currentProgress = (float) ((float)current / (float) max);		
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				progressIndicator.setProgress(currentProgress);				
			}
		});
		System.out.println(current + " of " + max + "; " + currentProgress * 100 + "%");				
	}
	
	public void run()
	{	
		updateTitles();		
	}
	
	private List<List<String>> updateTitles()
	{
		for(int counter = 1; counter <= titles.size()-1; counter ++)
		{			
			try {
				titles.get(counter).set(2, callApi(titles.get(counter).get(isbnIndex)));			
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			setCurrentProgress(titles.size()-1, counter);
		}
		setCurrentProgress(1,1);
		return titles;
	}

	
	private String callApi(String isbnsField) throws MalformedURLException, InterruptedException
	{
		
		URL apiLink;
		String[] isbns = isbnsField.replace(" ", "").split(";");
		if (!propertyFileHandler.propertyFileModel.get_settings_API_link().isBlank() && isbns.length >= 1)
		{			
			if (isbns != null && isbns.length > 0 && !isbns[0].equals("-"))
			{
				String isbnSearchTerm = "";
				int counter = 0;				
				
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
					apiLink = new URL(propertyFileHandler.propertyFileModel.get_settings_API_link().replace(Constants.isbnWildcard, isbnSearchTerm));
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
		return("Prüfung nicht möglich");
	}
	
}
