package logic_vivi;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import models_vivi.ApiMultiCallModel;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;


//runnable; Used when calling the api for publication lists
//runnable, so gui can be updated with current progress.
public class MultiApiCallTabRunnableThread extends Thread{	
	List<List<String>> importedFileRowsStrings;
	
	List<ApiMultiCallModel> apiMultiCallModels;
	Button button_save;
	public float currentProgress = 0;
	ProgressIndicator progressIndicator;
	ObservableValue<? extends Number> progressProperty;
	TextArea romeoSherpaText;
	ApiMultiCallModel issnAlreadyCalledSkipCopyThisApiMultiCallModel;
	
	public MultiApiCallTabRunnableThread(List<List<String>> importedFileRowsStrings, ProgressIndicator progressIndicator, Button button_save, TextArea romeoSherpaText)
	{	
		this.progressIndicator = progressIndicator;
		this.importedFileRowsStrings = importedFileRowsStrings;		
		this.button_save = button_save;
		this.romeoSherpaText = romeoSherpaText;
	}	
	
	public List<ApiMultiCallModel> getFinalResultListApiMultiCallModel()
	{
		return this.apiMultiCallModels;
	}
	
	public void setCurrentProgress(int max, int current)
	{			
		currentProgress = (float) ((float)current / (float) max);		
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				progressIndicator.setVisible(true);
				progressIndicator.setProgress(currentProgress);
				if (currentProgress == 1)
				{
					button_save.setVisible(true);
				}
			}
		});
		System.out.println(current + " of " + max + "; " + currentProgress * 100 + "%");				
	}
	
	public void run() 
	{			
		try {
			getTextandCallApiSubsequently();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void getTextandCallApiSubsequently() throws InterruptedException, IOException
	{	
		
		if (importedFileRowsStrings == null || importedFileRowsStrings.size() < 2)
		{
			apiMultiCallModels = null;
			return;
		}		
		
		
		int[] issnTitlePositions = findIssnAndTitlePosition(importedFileRowsStrings.get(0));			
		initiateBulkModelCallApiAndFillInData(this.importedFileRowsStrings, issnTitlePositions[0], issnTitlePositions[1], issnTitlePositions[2]);
	}
	
	//iterates through all models which have been enriched and saved already. If issn is contained in there already,
	//take these values instead of calling the api a second time 
	private Boolean checkIfBulkModelContainsJournalAlready(String issn)
	{
		for(ApiMultiCallModel apiMultiCallModel: this.apiMultiCallModels)
		{
			if (apiMultiCallModel.issn.equals(issn) || apiMultiCallModel.eIssn.equals(issn))
			{
				this.issnAlreadyCalledSkipCopyThisApiMultiCallModel = apiMultiCallModel;
				return true;
			}
		}
		return false;
	}
	
	
	private void initiateBulkModelCallApiAndFillInData(List<List<String>> importedFileRowsStrings , int issnPosition, int eissnPosition, int titlePosition) throws IOException
	{				
		this.apiMultiCallModels = new ArrayList<ApiMultiCallModel>();
		//first line contains column names; needs to be treated differently!
		boolean isHeader = true;	
		ApiCaller apiCaller = new ApiCaller(); 
		
		//Custom filter set in options menu; Must check position of column later --> saved in this variable
		int customColumnFilterPosition = -1;
		PropertyFileHandler propertyFileHandler = new PropertyFileHandler();
		int counter = 0;
		
		for (List<String> arrayColumnList : importedFileRowsStrings)
		{			
			//additional thing, if current line is header:
			if (isHeader)
			{
				//add first columns to the current BulkModel, THEN add the Web of Science tags which we altered earlier;
				//these are still the column names of the final file! we want the fetched information to start with, so they'll come first
				//addition: choose between extended or simple model; (individual columns for conditions or one column for collective conditions)
				
				List<String> headerList = new ArrayList<String>(arrayColumnList);
				for (int iterator = 0; iterator < headerList.size(); iterator ++)
				{
					headerList.set(iterator, ConvenienceTools.wos_tagMatcher(headerList.get(iterator)));
				}				
				
					this.apiMultiCallModels.add(new ApiMultiCallModel("ISSN für API genutzt?",
							"eIssn für API genutzt?",
							"Titel für API genutzt?",
							"Submitted", 
							"Accepted", 
							"Published", 
							"Voraussetzungen (Submitted)", 
							"CopyRight Owner (Submitted)", 							
							"Zusätzliche OA Gebühren (Submitted)", 
							"Repositories (Submitted)", 
							"Embargo (Submitted)", 
							"Konditionen (Submitted)", 
							"Voraussetzungen (Accepted)", 
							"CopyRight Owner (Accepted)",							
							"Zusätzliche OA Gebühren (Accepted)", 
							"Repositories (Accepted)", 
							"Embargo (Accepted)", 
							"Konditionen (Accepted)", 
							"Voraussetzungen (Published)", 
							"CopyRight Owner (Published)",							
							"Zusätzliche OA Gebühren (Published)", 
							"Repositories (Published)", 
							"Embargo (Published)", 
							"Konditionen (Published)",
							"Last Updated",
							"Publisher Type",
							"Publisher",
							"Lizenz (Submitted)",
							"Lizenz (Accepted)",
							"Lizenz (Published)",
							"Nachweise",
							
							headerList));
					
					//if Filter is set, get the position right here:
					if (!propertyFileHandler.propertyFileModel.getMultiApiCallWhiteListColumn().isBlank() && !propertyFileHandler.propertyFileModel.getMultiApiCallWhiteListFilter().isBlank())
					{
						for(int j = 0; j < arrayColumnList.size(); j ++)
						{
							if (arrayColumnList.get(j).equals(propertyFileHandler.propertyFileModel.getMultiApiCallWhiteListColumn().substring(propertyFileHandler.propertyFileModel.getMultiApiCallWhiteListColumn().indexOf("(") + 1, propertyFileHandler.propertyFileModel.getMultiApiCallWhiteListColumn().indexOf(")")))
									|| arrayColumnList.get(j).equals(propertyFileHandler.propertyFileModel.getMultiApiCallWhiteListColumn()))
							{									
								customColumnFilterPosition = j;
							}
						}
					}
				
				isHeader = false;
			} else
			{					
				//if its not the header, but a file with actual data:
				//Check if we are out of bounds, also check if the ISSN in this line is actually an issn!
				//if so, clear all data in current apiCaller instance, fetch data
				
				// --> if start
				if (	propertyFileHandler.propertyFileModel.getMultiApiCallWhiteListCheckbox() //checkbox for whitelisting set!
						&& !propertyFileHandler.propertyFileModel.getMultiApiCallWhiteListColumn().isBlank() //Column not blank
						&& !propertyFileHandler.propertyFileModel.getMultiApiCallWhiteListFilter().isBlank() //filter not blank -> both need to be set!
						&& customColumnFilterPosition != -1 //iterator for array, not negative! Otherwise it will crash
						&& !StringUtils.containsIgnoreCase(arrayColumnList.get(customColumnFilterPosition), propertyFileHandler.propertyFileModel.getMultiApiCallWhiteListFilter()) //filter NOT contained in column (then line will be deleted)
					)	// <-- if end
				{
					//Both, column and filter properties for Whitelisting contain values: Filter items! Also filterPosition set successfully					
						System.out.println("Line removed due to filter: Column '" + propertyFileHandler.propertyFileModel.getMultiApiCallWhiteListColumn() + "' does not contain value: '" + propertyFileHandler.propertyFileModel.getMultiApiCallWhiteListFilter() + "' (" + arrayColumnList.get(customColumnFilterPosition) + ")");				
				
				}else if ((arrayColumnList.size() > issnPosition && issnPosition != -1 && ConvenienceTools.isValidISSN(arrayColumnList.get(issnPosition)) && checkIfBulkModelContainsJournalAlready(arrayColumnList.get(issnPosition)) && this.issnAlreadyCalledSkipCopyThisApiMultiCallModel != null) || 
						(arrayColumnList.size() > eissnPosition && eissnPosition != -1 && ConvenienceTools.isValidISSN(arrayColumnList.get(eissnPosition)) && checkIfBulkModelContainsJournalAlready(arrayColumnList.get(eissnPosition)) && this.issnAlreadyCalledSkipCopyThisApiMultiCallModel != null))
				{
					//if issn or eissn are already contained in BulkModels,
					//copy bulkModel (which is saved to class property issnAlreadyCalledSkipCopyThisApiMultiCallModel
					//while function checkIfBulkModelContainsJournalAlready is executed)
					
					this.apiMultiCallModels.add(new ApiMultiCallModel(issnAlreadyCalledSkipCopyThisApiMultiCallModel.issn, 
							issnAlreadyCalledSkipCopyThisApiMultiCallModel.eIssn,	
							issnAlreadyCalledSkipCopyThisApiMultiCallModel.title, 
							issnAlreadyCalledSkipCopyThisApiMultiCallModel.submitted,
							issnAlreadyCalledSkipCopyThisApiMultiCallModel.accepted,
							issnAlreadyCalledSkipCopyThisApiMultiCallModel.published,
														
							issnAlreadyCalledSkipCopyThisApiMultiCallModel.submittedPrerequisites,
							issnAlreadyCalledSkipCopyThisApiMultiCallModel.submittedCopyRightOwner,
							issnAlreadyCalledSkipCopyThisApiMultiCallModel.submittedAdditionalOAFees,
							issnAlreadyCalledSkipCopyThisApiMultiCallModel.submittedRepository,
							issnAlreadyCalledSkipCopyThisApiMultiCallModel.submittedEmbargo,
							issnAlreadyCalledSkipCopyThisApiMultiCallModel.submittedConditions,
							
							issnAlreadyCalledSkipCopyThisApiMultiCallModel.acceptedPrerequisites,
							issnAlreadyCalledSkipCopyThisApiMultiCallModel.acceptedCopyRightOwner,
							issnAlreadyCalledSkipCopyThisApiMultiCallModel.acceptedAdditionalOAFees,
							issnAlreadyCalledSkipCopyThisApiMultiCallModel.acceptedRepository,
							issnAlreadyCalledSkipCopyThisApiMultiCallModel.acceptedEmbargo,
							issnAlreadyCalledSkipCopyThisApiMultiCallModel.acceptedConditions,
														
							issnAlreadyCalledSkipCopyThisApiMultiCallModel.publishedPrerequisites,	
							issnAlreadyCalledSkipCopyThisApiMultiCallModel.publishedCopyRightOwner,	
							issnAlreadyCalledSkipCopyThisApiMultiCallModel.publishedAdditionalOAFees,							
							issnAlreadyCalledSkipCopyThisApiMultiCallModel.publishedRepository,
							issnAlreadyCalledSkipCopyThisApiMultiCallModel.publishedEmbargo,
							issnAlreadyCalledSkipCopyThisApiMultiCallModel.publishedConditions,
							
							issnAlreadyCalledSkipCopyThisApiMultiCallModel.lastUpdated,
							issnAlreadyCalledSkipCopyThisApiMultiCallModel.publisherType,
							issnAlreadyCalledSkipCopyThisApiMultiCallModel.publisher,
							
							issnAlreadyCalledSkipCopyThisApiMultiCallModel.submittedLicense,
							issnAlreadyCalledSkipCopyThisApiMultiCallModel.acceptedLicense,
							issnAlreadyCalledSkipCopyThisApiMultiCallModel.publishedLicense,
							
							issnAlreadyCalledSkipCopyThisApiMultiCallModel.urls,
							
							arrayColumnList));
					
					this.issnAlreadyCalledSkipCopyThisApiMultiCallModel = null;
					
				}else if (arrayColumnList.size() > issnPosition  && issnPosition != -1 &&  ConvenienceTools.isValidISSN(arrayColumnList.get(issnPosition)))
				{
				//init with issn
				apiCaller.initWithISSN(arrayColumnList.get(issnPosition));
				JsonParser jsonParser = new JsonParser(apiCaller.romeoSherpaApiResponseString);
				//add fetched data to BulkModel
				
				
					this.apiMultiCallModels.add(new ApiMultiCallModel(arrayColumnList.get(issnPosition), 
							"",	
							"", 
							jsonParser.getConditionsSubmittedVersion(),
							jsonParser.getConditionsAcceptedVersion(),
							jsonParser.getConditionsPublishedVersion(),
														
							jsonParser.getPrerequisitesSubmittedVersion(),
							jsonParser.getCopyRightOwnerSubmittedVersion(),
							jsonParser.getOAFeesSubmittedVersion(),
							jsonParser.getRepositorySubmittedVersion(),
							jsonParser.getEmbargoSubmittedVersion(),
							jsonParser.getConditionSubmittedVersion(),
							
							jsonParser.getPrerequisitesAcceptedVersion(),
							jsonParser.getCopyRightOwnerAcceptedVersion(),
							jsonParser.getOAFeesAcceptedVersion(),
							jsonParser.getRepositoryAcceptedVersion(),
							jsonParser.getEmbargoAcceptedVersion(),
							jsonParser.getConditionAcceptedVersion(),
														
							jsonParser.getPrerequisitesPublishedVersion(),	
							jsonParser.getCopyRightOwnerPublishedVersion(),	
							jsonParser.getOAFeesPublishedVersion(),							
							jsonParser.getRepositoryPublishedVersion(),
							jsonParser.getEmbargoPublishedVersion(),
							jsonParser.getConditionPublishedVersion(),
							
							jsonParser.getLastUpdated(),
							jsonParser.getPublisherType(),
							jsonParser.getPublisher(),
							
							jsonParser.getLicenseSubmittedVersion(),
							jsonParser.getLicenseAcceptedVersion(),
							jsonParser.getLicensePublishedVersion(),
							
							jsonParser.getUrlCollection(),
							
							arrayColumnList));	
				
				} else if (arrayColumnList.size() > eissnPosition  && eissnPosition != -1 &&  ConvenienceTools.isValidISSN(arrayColumnList.get(eissnPosition)))
				{				
					
				//init with eissn
				apiCaller.initWithISSN(arrayColumnList.get(eissnPosition));
				JsonParser jsonParser = new JsonParser(apiCaller.romeoSherpaApiResponseString);
				//add fetched data to BulkModel				
				
					this.apiMultiCallModels.add(new ApiMultiCallModel("", 
							arrayColumnList.get(eissnPosition),	
							"", 
							jsonParser.getConditionsSubmittedVersion(),
							jsonParser.getConditionsAcceptedVersion(),
							jsonParser.getConditionsPublishedVersion(),
														
							jsonParser.getPrerequisitesSubmittedVersion(),
							jsonParser.getCopyRightOwnerSubmittedVersion(),
							jsonParser.getOAFeesSubmittedVersion(),
							jsonParser.getRepositorySubmittedVersion(),
							jsonParser.getEmbargoSubmittedVersion(),
							jsonParser.getConditionSubmittedVersion(),
							
							jsonParser.getPrerequisitesAcceptedVersion(),
							jsonParser.getCopyRightOwnerAcceptedVersion(),
							jsonParser.getOAFeesAcceptedVersion(),
							jsonParser.getRepositoryAcceptedVersion(),
							jsonParser.getEmbargoAcceptedVersion(),
							jsonParser.getConditionAcceptedVersion(),
														
							jsonParser.getPrerequisitesPublishedVersion(),	
							jsonParser.getCopyRightOwnerPublishedVersion(),	
							jsonParser.getOAFeesPublishedVersion(),							
							jsonParser.getRepositoryPublishedVersion(),
							jsonParser.getEmbargoPublishedVersion(),
							jsonParser.getConditionPublishedVersion(),
							
							jsonParser.getLastUpdated(),
							jsonParser.getPublisherType(),
							jsonParser.getPublisher(),
							
							jsonParser.getLicenseSubmittedVersion(),
							jsonParser.getLicenseAcceptedVersion(),
							jsonParser.getLicensePublishedVersion(),
							
							jsonParser.getUrlCollection(),
							
							arrayColumnList));			
						
				
				
				
				}else if (arrayColumnList.size() > titlePosition  && titlePosition != -1 &&  apiCaller.fetchByTitleIfIssnMissingMultiCall)
				{
				//init with Title (unreliable results presumably)
					apiCaller.initWithTitleNotISSN(arrayColumnList.get(titlePosition).replace(" ", "%20"));
					JsonParser jsonParser = new JsonParser(apiCaller.romeoSherpaApiResponseString);
					
					//add fetched data to BulkModel				
					this.apiMultiCallModels.add(new ApiMultiCallModel("", 
							"",	
							arrayColumnList.get(titlePosition), 
							jsonParser.getConditionsSubmittedVersion(),
							jsonParser.getConditionsAcceptedVersion(),
							jsonParser.getConditionsPublishedVersion(),
														
							jsonParser.getPrerequisitesSubmittedVersion(),
							jsonParser.getCopyRightOwnerSubmittedVersion(),
							jsonParser.getOAFeesSubmittedVersion(),
							jsonParser.getRepositorySubmittedVersion(),
							jsonParser.getEmbargoSubmittedVersion(),
							jsonParser.getConditionSubmittedVersion(),
							
							jsonParser.getPrerequisitesAcceptedVersion(),
							jsonParser.getCopyRightOwnerAcceptedVersion(),
							jsonParser.getOAFeesAcceptedVersion(),
							jsonParser.getRepositoryAcceptedVersion(),
							jsonParser.getEmbargoAcceptedVersion(),
							jsonParser.getConditionAcceptedVersion(),
														
							jsonParser.getPrerequisitesPublishedVersion(),	
							jsonParser.getCopyRightOwnerPublishedVersion(),	
							jsonParser.getOAFeesPublishedVersion(),							
							jsonParser.getRepositoryPublishedVersion(),
							jsonParser.getEmbargoPublishedVersion(),
							jsonParser.getConditionPublishedVersion(),
							
							jsonParser.getLastUpdated(),
							jsonParser.getPublisherType(),
							jsonParser.getPublisher(),
							
							jsonParser.getLicenseSubmittedVersion(),
							jsonParser.getLicenseAcceptedVersion(),
							jsonParser.getLicensePublishedVersion(),
							
							jsonParser.getUrlCollection(),
							
							arrayColumnList));
			
				}else if (issnPosition == -1 && eissnPosition == -1 && titlePosition == -1)
				{
					Boolean issnFound = false;
					//search for issn in each column
					for (String column : arrayColumnList)
					{
						if (!issnFound && ConvenienceTools.isValidISSN(column))
						{
							issnFound = true;
							//init with issn
							apiCaller.initWithISSN(column);
							JsonParser jsonParser = new JsonParser(apiCaller.romeoSherpaApiResponseString);
							//add fetched data to BulkModel
							
							
								this.apiMultiCallModels.add(new ApiMultiCallModel(column, 
										"",	
										"", 
										jsonParser.getConditionsSubmittedVersion(),
										jsonParser.getConditionsAcceptedVersion(),
										jsonParser.getConditionsPublishedVersion(),
																	
										jsonParser.getPrerequisitesSubmittedVersion(),
										jsonParser.getCopyRightOwnerSubmittedVersion(),
										jsonParser.getOAFeesSubmittedVersion(),
										jsonParser.getRepositorySubmittedVersion(),
										jsonParser.getEmbargoSubmittedVersion(),
										jsonParser.getConditionSubmittedVersion(),
										
										jsonParser.getPrerequisitesAcceptedVersion(),
										jsonParser.getCopyRightOwnerAcceptedVersion(),
										jsonParser.getOAFeesAcceptedVersion(),
										jsonParser.getRepositoryAcceptedVersion(),
										jsonParser.getEmbargoAcceptedVersion(),
										jsonParser.getConditionAcceptedVersion(),
																	
										jsonParser.getPrerequisitesPublishedVersion(),	
										jsonParser.getCopyRightOwnerPublishedVersion(),	
										jsonParser.getOAFeesPublishedVersion(),							
										jsonParser.getRepositoryPublishedVersion(),
										jsonParser.getEmbargoPublishedVersion(),
										jsonParser.getConditionPublishedVersion(),
										
										jsonParser.getLastUpdated(),
										jsonParser.getPublisherType(),
										jsonParser.getPublisher(),
										
										jsonParser.getLicenseSubmittedVersion(),
										jsonParser.getLicenseAcceptedVersion(),
										jsonParser.getLicensePublishedVersion(),
										
										jsonParser.getUrlCollection(),
										
										arrayColumnList));	
						}
					}
					
					if (!issnFound)
					{
						this.apiMultiCallModels.add(new ApiMultiCallModel("No ISSN found",
								"",
								arrayColumnList));	
					}
										
				} else
				{
					this.apiMultiCallModels.add(new ApiMultiCallModel("No ISSN found, search by title deactivated",
							"",
							arrayColumnList));	
				}
			}				
			counter ++;
			setCurrentProgress(importedFileRowsStrings.size(), counter+1);	
		}
		
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				displayApiMultiCallModels(apiMultiCallModels);
				button_save.setVisible(true);
			}
		});
		
		try {
			Thread.currentThread().join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//find issn and title Positions
	private int[] findIssnAndTitlePosition(List<String> list)
	{
		int issnPosition = -1;
		int eissnPosition = -1;
		int journalPosition = -1;
		
		String issnHeader;
		String eissnHeader;
		String titleHeader;
		
		PropertyFileHandler propertyFileHandler = new PropertyFileHandler();
		if (propertyFileHandler.propertyFileModel.getImportFileTypeComboBox().equals("Web of Science Publication Export"))
		{
			issnHeader = "sn";
			eissnHeader = "ei";
			titleHeader = "so";
		} else
		{		
			if (propertyFileHandler.propertyFileModel.getImportFileTypeTitleColumn().isEmpty() && propertyFileHandler.propertyFileModel.getImportFileTypeeISSNColumn().isEmpty() && propertyFileHandler.propertyFileModel.getImportFileTypeISSNColumn().isEmpty())
			{
				//if no columns are set, we are going to search for a issn in each line. No positions needed
				return new int[]{issnPosition, eissnPosition, journalPosition};
			} else
			{							
				issnHeader = propertyFileHandler.propertyFileModel.getImportFileTypeISSNColumn();
				eissnHeader = propertyFileHandler.propertyFileModel.getImportFileTypeeISSNColumn();
				titleHeader = propertyFileHandler.propertyFileModel.getImportFileTypeTitleColumn();
			}			
		}		
		
		//loop through each column: check if column is issn (SN); eissn (EI); or journal title (SO)
		for(int i = 0; i < list.size(); i++)
		{			
			if (issnPosition == -1 && !issnHeader.isEmpty() && Pattern.compile(Pattern.quote(issnHeader), Pattern.CASE_INSENSITIVE).matcher(list.get(i)).find())
			{
				issnPosition = i;
			}
			if (eissnPosition == -1 && !eissnHeader.isEmpty() &&  Pattern.compile(Pattern.quote(eissnHeader), Pattern.CASE_INSENSITIVE).matcher(list.get(i)).find())
			{
				eissnPosition = i;
			}
			if (journalPosition == -1 && !titleHeader.isEmpty() &&  Pattern.compile(Pattern.quote(titleHeader), Pattern.CASE_INSENSITIVE).matcher(list.get(i)).find())
			{
				journalPosition = i;
			}			
		}	
		
		return new int[]{issnPosition, eissnPosition, journalPosition};		
				
	}	
	
	
	//line formatter for a better monospace-font display
	//i.e:
	//column A: 	value A
	//col. B:		value B
	public static String lineFormatter(String heading, String data)
	{				
		if (heading.toCharArray().length > 25)
		{
			heading = heading.substring(0,20);
			heading += " (..):   ";
		} else
		{
			heading += ":";
			int counter = 29 - heading.toCharArray().length;
			for (;counter > 0; counter --)
			{
				heading += " ";
			}
			
		}
		return heading + data + "\n";
		
	}

	
	
		//display BulkModel in UploadScene; loop through each element and display accordingly
		private void displayApiMultiCallModels(List<ApiMultiCallModel> apiMultiCallModels)
		{
			this.romeoSherpaText.clear();
			
			PropertyFileHandler propertyFileHandler = new PropertyFileHandler();
			if (propertyFileHandler.propertyFileModel.getMultiApiCallExportConditionsInSeparateColumns())
			{
				this.romeoSherpaText.appendText("Sie können die generierten Daten nun exportieren!" + System.lineSeparator() + System.lineSeparator() + "Ausgabe der ersten beiden Zeilen (Überschrift und erste Datenzeile) als Beispiel:\n\n" + 
						lineFormatter(apiMultiCallModels.get(0).issn, apiMultiCallModels.get(1).issn) +
						lineFormatter(apiMultiCallModels.get(0).eIssn, apiMultiCallModels.get(1).eIssn) +
						lineFormatter(apiMultiCallModels.get(0).title, apiMultiCallModels.get(1).title) +
						lineFormatter(apiMultiCallModels.get(0).publisherType, apiMultiCallModels.get(1).publisherType) +
						lineFormatter(apiMultiCallModels.get(0).submittedPrerequisites, apiMultiCallModels.get(1).submittedPrerequisites) +
						lineFormatter(apiMultiCallModels.get(0).submittedCopyRightOwner, apiMultiCallModels.get(1).submittedCopyRightOwner) +
						lineFormatter(apiMultiCallModels.get(0).submittedAdditionalOAFees, apiMultiCallModels.get(1).submittedAdditionalOAFees) +
						lineFormatter(apiMultiCallModels.get(0).submittedRepository, apiMultiCallModels.get(1).submittedRepository) +
						lineFormatter(apiMultiCallModels.get(0).submittedEmbargo, apiMultiCallModels.get(1).submittedEmbargo) +
						lineFormatter(apiMultiCallModels.get(0).submittedConditions, apiMultiCallModels.get(1).submittedConditions) +
						lineFormatter(apiMultiCallModels.get(0).acceptedPrerequisites, apiMultiCallModels.get(1).acceptedPrerequisites) +
						lineFormatter(apiMultiCallModels.get(0).acceptedCopyRightOwner, apiMultiCallModels.get(1).acceptedCopyRightOwner) +
						lineFormatter(apiMultiCallModels.get(0).acceptedAdditionalOAFees, apiMultiCallModels.get(1).acceptedAdditionalOAFees) +
						lineFormatter(apiMultiCallModels.get(0).acceptedRepository, apiMultiCallModels.get(1).acceptedRepository) +
						lineFormatter(apiMultiCallModels.get(0).acceptedEmbargo, apiMultiCallModels.get(1).acceptedEmbargo) +
						lineFormatter(apiMultiCallModels.get(0).acceptedConditions, apiMultiCallModels.get(1).acceptedConditions) +
						lineFormatter(apiMultiCallModels.get(0).publishedPrerequisites, apiMultiCallModels.get(1).publishedPrerequisites) +
						lineFormatter(apiMultiCallModels.get(0).publishedCopyRightOwner, apiMultiCallModels.get(1).publishedCopyRightOwner) +
						lineFormatter(apiMultiCallModels.get(0).acceptedRepository, apiMultiCallModels.get(1).acceptedRepository) +
						lineFormatter(apiMultiCallModels.get(0).publishedAdditionalOAFees, apiMultiCallModels.get(1).publishedAdditionalOAFees) +
						lineFormatter(apiMultiCallModels.get(0).publishedEmbargo, apiMultiCallModels.get(1).publishedEmbargo) +
						lineFormatter(apiMultiCallModels.get(0).publishedConditions, apiMultiCallModels.get(1).publishedConditions)
						);						
						
			} else
			{
				
				this.romeoSherpaText.appendText("Sie können die generierten Daten nun exportieren!" + "\n\n" + "Ausgabe der ersten beiden Zeilen (Überschrift und erste Datenzeile) als Beispiel:\n\n" + 
						lineFormatter(apiMultiCallModels.get(0).issn, apiMultiCallModels.get(1).issn) +
						lineFormatter(apiMultiCallModels.get(0).title, apiMultiCallModels.get(1).title) +
						lineFormatter(apiMultiCallModels.get(0).publisherType, apiMultiCallModels.get(1).publisherType) +
						lineFormatter(apiMultiCallModels.get(0).publisher, apiMultiCallModels.get(1).publisher) +
						lineFormatter(apiMultiCallModels.get(0).submitted, apiMultiCallModels.get(1).submitted) +
						lineFormatter(apiMultiCallModels.get(0).accepted, apiMultiCallModels.get(1).accepted) +
						lineFormatter(apiMultiCallModels.get(0).published, apiMultiCallModels.get(1).published));
				
			}
			
			
		}
	
}
