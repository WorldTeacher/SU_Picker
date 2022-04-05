package logic_vivi;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//contains all functions that parse json and get the desired values out of the json objects/arrays
public class JsonParser {
	
	JSONObject jsonObject;
	String keyNotFoundMessage = "?";
	JSONArray conditionsJsonArray;
	String conditionsText = "Voraussetzungen: %s" + System.lineSeparator() + "CopyRight Owner: %s" + System.lineSeparator() + "Zusätzliche OA Gebühren: %s" + System.lineSeparator() + "Repositories: %s" + System.lineSeparator() + "Embargo: %s" + System.lineSeparator() + "" + System.lineSeparator() + "Konditionen:" + System.lineSeparator() + "%s";
	
	String prerequisitesLabel = "Voraussetzungen: ";
	String copyrightOwnerLabel = "CopyRight Owner: ";
	String additionalOaFeeLabel = "Zusätzliche OA Gebühren: ";
	String locationsLabel = "Repositories: ";
	String embargoLabel = "Embargo: ";
	String conditionsLabel = "Konditionen: ";	
	String licenseLabel = "Lizenz: ";
	String urlLabel = "Nachweis: ";
	
	public JsonParser(String jsonString)
	{
		this.jsonObject = new JSONObject(jsonString);
	}
	
	//json object will contain one tag "urls", which will contain several urls.
	//these, again, consist of "description" and "url".
	//get all "descriptions: url" pairs and return them as a string
	public String getUrlCollection()
	{
		Object object = loopThroughJsonObjectAndFindKey(jsonObject, "urls");
		String returnString = "";
		if (object instanceof JSONArray)
		{
			for (Object subObject : ((JSONArray)object))
			{
				returnString += turnObjectToString(loopThroughJsonObjectAndFindKey(subObject, "description")) + ": " +
				turnObjectToString(loopThroughJsonObjectAndFindKey(subObject, "url") + System.lineSeparator());			
			}
		}
		return returnString;
	}
	
	
	public String getTitle()
	{		 
		return 	turnObjectToString(loopThroughJsonObjectAndFindKey(jsonObject, "title"));
	}

	
	public String getPublisher()
	{
		// "name" is such a generic tag: Check for JSON tag publishers, then check for name to prevent issues
		return turnObjectToString(loopThroughJsonObjectAndFindKey(loopThroughJsonObjectAndFindKey(jsonObject, "publishers"), "name"));	
		
	}
	
	public String getLink()
	{
		return turnObjectToString(loopThroughJsonObjectAndFindKey(jsonObject, "url"));	
	}
	
	public String getISSN()
	{
		Object object = loopThroughJsonObjectAndFindKey(jsonObject, "issns");
		if (object instanceof JSONArray)
		{
			for (Object subObject : ((JSONArray)object))
			{
				if (turnObjectToString(loopThroughJsonObjectAndFindKey(subObject, "type")).contains("print"))
				{
					return turnObjectToString(loopThroughJsonObjectAndFindKey(subObject, "issn"));
				}
			}
		}
		
		if (object instanceof JSONObject)
		{
			 return turnObjectToString(loopThroughJsonObjectAndFindKey(object, "issn"));
		}
		
		return keyNotFoundMessage;			
	}
	
	public String getISSN_e()
	{
		Object object = loopThroughJsonObjectAndFindKey(jsonObject, "issns");
		if (object instanceof JSONArray)
		{
			for (Object subObject : ((JSONArray)object))
			{
				if (turnObjectToString(loopThroughJsonObjectAndFindKey(subObject, "type")).contains("electronic"))
				{
					return turnObjectToString(loopThroughJsonObjectAndFindKey(subObject, "issn"));
				}
			}
		}
		
		if (object instanceof JSONObject)
		{
			 return turnObjectToString(loopThroughJsonObjectAndFindKey(object, "issn"));
		}
		
		return keyNotFoundMessage;	
	}
	
	public String getMarkerJournalInDOAJ()
	{
		 return turnObjectToString(loopThroughJsonObjectAndFindKey(jsonObject, "listed_in_doaj"));
	}
	
	public String getDOAJLink()
	{
		if (getMarkerJournalInDOAJ().contains("yes"))
		{
			if(!getISSN().contains(keyNotFoundMessage))
			{
				return "https://doaj.org/toc/" + getISSN();
			} else if (!getISSN_e().contains(keyNotFoundMessage))
			{
				return "https://doaj.org/toc/" + getISSN_e();
			}			
		} else
		{
			return "not listed";
		}
		return keyNotFoundMessage;		
	}	
	
	public String getLastUpdated()
	{	
		return turnObjectToString(loopThroughJsonObjectAndFindKey(jsonObject, "date_modified"));				
	}
		
	public String getSherpaLink()
	{
		return turnObjectToString(loopThroughJsonObjectAndFindKey(jsonObject, "uri"));	
	}
	
	//gets whole JSONArray containing all conditions; saves it in property so we only have to do it once
	public boolean getConditions()
	{		
		Object resultFromLoop = loopThroughJsonObjectAndFindKey(jsonObject, "permitted_oa");
		this.conditionsJsonArray = new JSONArray();
		if (resultFromLoop instanceof JSONObject)
		{
			//shouldnt happen
			return false;
		} else if (resultFromLoop instanceof JSONArray)
		{
			this.conditionsJsonArray = (JSONArray)resultFromLoop;
		}				
		return true;		
	}	
	
	
	private String extractConditionsByArticleVersion(String articleVersion)
	{
		String prerequisites;
		String copyrightOwner;
		String license;
		String additionalOaFee;
		String locations;
		String embargo;
		String conditions;	
		String finalText = "";
		
		PropertyFileHandler propertyFileHandler = new PropertyFileHandler();
		
		if (conditionsJsonArray == null)
		{
			if (getConditions() == false)
			{
				return "Not able to resolve Conditions";
			}
		}
		
		for (Object subObject : ((JSONArray)conditionsJsonArray))
		{
			if (loopThroughJsonObjectAndFindKey(subObject, "article_version").toString().contains(articleVersion))
			{
				prerequisites = turnObjectToString(loopThroughJsonObjectAndFindKey(subObject, "prerequisites"));
				copyrightOwner = turnObjectToString(loopThroughJsonObjectAndFindKey(subObject, "copyright_owner"));
				license = turnObjectToString(loopThroughJsonObjectAndFindKey(subObject, "license"));
				additionalOaFee = turnObjectToString(loopThroughJsonObjectAndFindKey(subObject, "additional_oa_fee"));
				locations = turnObjectToString(loopThroughJsonObjectAndFindKey(subObject, "location_phrases"));
				String number = turnObjectToString(loopThroughJsonObjectAndFindKey(subObject, "amount"));
				String timeframe = turnObjectToString(loopThroughJsonObjectAndFindKey(subObject, "units"));
				timeframe = timeframe.equals(keyNotFoundMessage) ? "" : timeframe;	
				embargo = number + timeframe;
				
				conditions  = turnObjectToString(loopThroughJsonObjectAndFindKey(subObject, "conditions"));
				if (finalText != "")
				{
					finalText += "" + System.lineSeparator() + "- - - - - - - Additional Section - - - - - - -" + System.lineSeparator() + "";
				}
				
				if (propertyFileHandler.propertyFileModel.getSingleApiCallDisplayConditionsIfNoTagContained())
				{
					finalText += String.format(conditionsText, prerequisites, copyrightOwner, additionalOaFee, locations, embargo, conditions);						
				} else
				{
					if (prerequisites != keyNotFoundMessage)
					{
						finalText += prerequisitesLabel + prerequisites + System.lineSeparator();
					}
					if (copyrightOwner != keyNotFoundMessage)
					{
						finalText += copyrightOwnerLabel + copyrightOwner + System.lineSeparator();
					}
					if (license != keyNotFoundMessage)
					{
						finalText += licenseLabel + license + System.lineSeparator();
					}						
					if (additionalOaFee != keyNotFoundMessage)
					{
						finalText += additionalOaFeeLabel + additionalOaFee + System.lineSeparator();
					}
					if (locations != keyNotFoundMessage)
					{
						finalText += locationsLabel + locations + System.lineSeparator();
					}
					if (embargo != keyNotFoundMessage)
					{
						finalText += embargoLabel + embargo + System.lineSeparator();
					}
					if (conditions != keyNotFoundMessage)
					{
						finalText += System.lineSeparator() + conditionsLabel + System.lineSeparator() + conditions + System.lineSeparator();
					}
				}
				
			}
		}
		
		if (finalText == "")
			{
			return "Tag '" + articleVersion + "' not found or JSON invalid";
			} else
			{
			return finalText;
			}
	}
	
	public String getConditionsPublishedVersion()	
	{
		return(extractConditionsByArticleVersion("published"));
	}		
	public String getConditionsAcceptedVersion()	
	{
		return(extractConditionsByArticleVersion("accepted"));
	}
	public String getConditionsSubmittedVersion()
	{
		return(extractConditionsByArticleVersion("submitted"));
	}
	public String getPublisherType()
	{
		return turnObjectToString(loopThroughJsonObjectAndFindKey(jsonObject, "relationship_type"));	
	}
	
	//addition: 04.09.20; Functions for indvidual condition fields (prerequ, copyright, oa fees, repositoriy, embargo, conditions)
	//addition 21.09: sometimes, more than one phrase contained.
	
	private String extractSingleConditionByArticleVersionAndConditionString(String articleVersion, String condition)
	{	
		if (conditionsJsonArray == null)
		{
			if (getConditions() == false)
			{
				return "Not able to resolve Conditions";
			}
		}
		
		String returnString = "";
		String compoundString = "";
		int counter = 0;
		
		for (Object subObject : ((JSONArray)conditionsJsonArray))
		{
			
			if (loopThroughJsonObjectAndFindKey(subObject, "article_version").toString().contains(articleVersion))
			{
				
				returnString += "["+counter+"]";
				counter ++;
				
				compoundString += turnObjectToString(loopThroughJsonObjectAndFindKey(subObject, condition));
			
				returnString += compoundString + " ";
				compoundString = "";
			}
			
		}
		if (!returnString.isEmpty())
		{
			return returnString;
		} else
		{
			return keyNotFoundMessage;
		}
		
	}
	
	//compound condition: as of now, only embargo, amount and units need to be added up, if multiplay array elements are there
	//function above would deliver ?/12 ?/months instead of ? / 12 months
	private String extractCompoundConditionByArticleVersionAndConditionString(String articleVersion, String condition1, String condition2)
	{	
		if (conditionsJsonArray == null)
		{
			if (getConditions() == false)
			{
				return "Not able to resolve Conditions";
			}
		}
		
		String returnString = "";
		String compoundString = "";
		int counter = 0;
		
		for (Object subObject : ((JSONArray)conditionsJsonArray))
		{
			
			if (loopThroughJsonObjectAndFindKey(subObject, "article_version").toString().contains(articleVersion))
			{
				
				returnString += "["+counter+"]";
				counter ++;
				
				compoundString += turnObjectToString(loopThroughJsonObjectAndFindKey(subObject, condition1)) + " " +
						turnObjectToString(loopThroughJsonObjectAndFindKey(subObject, condition2));		
				if (compoundString.contains(keyNotFoundMessage))
				{
					//example: embargo, consisting of two values (number and unit), we still only want to return ? instead of ? ? 
					compoundString = keyNotFoundMessage;
				}
				returnString += compoundString + " ";
				compoundString = "";
			}
			
		}
		if (!returnString.isEmpty())
		{
			return returnString;
		} else
		{
			return keyNotFoundMessage;
		}
		
	}
	
	public String getLicenseSubmittedVersion()
	{	
		return extractSingleConditionByArticleVersionAndConditionString("submitted", "license");			
	}
	
	public String getLicenseAcceptedVersion()
	{	
		return extractSingleConditionByArticleVersionAndConditionString("accepted", "license");			
	}
	
	public String getLicensePublishedVersion()
	{	
		return extractSingleConditionByArticleVersionAndConditionString("published", "license");			
	}
	
	public String getPrerequisitesSubmittedVersion(){
		return extractSingleConditionByArticleVersionAndConditionString("submitted", "prerequisites");
	}
	public String getPrerequisitesAcceptedVersion(){
		return extractSingleConditionByArticleVersionAndConditionString("accepted", "prerequisites");
	}
	public String getPrerequisitesPublishedVersion(){
		return extractSingleConditionByArticleVersionAndConditionString("published", "prerequisites");
	}
	public String getCopyRightOwnerSubmittedVersion(){
		return extractSingleConditionByArticleVersionAndConditionString("submitted", "copyright_owner");
	}
	public String getCopyRightOwnerAcceptedVersion(){
		return extractSingleConditionByArticleVersionAndConditionString("accepted", "copyright_owner");
	}
	public String getCopyRightOwnerPublishedVersion(){
		return extractSingleConditionByArticleVersionAndConditionString("published", "copyright_owner");
	}
	public String getOAFeesSubmittedVersion(){
		return extractSingleConditionByArticleVersionAndConditionString("submitted", "additional_oa_fee");
	}
	public String getOAFeesAcceptedVersion(){
		return extractSingleConditionByArticleVersionAndConditionString("accepted", "additional_oa_fee");
	}
	public String getOAFeesPublishedVersion(){
		return extractSingleConditionByArticleVersionAndConditionString("published", "additional_oa_fee");
	}
	public String getRepositorySubmittedVersion(){
		return extractSingleConditionByArticleVersionAndConditionString("submitted", "location_phrases");
	}
	public String getRepositoryAcceptedVersion(){
		return extractSingleConditionByArticleVersionAndConditionString("accepted", "location_phrases");
	}
	public String getRepositoryPublishedVersion(){
		return extractSingleConditionByArticleVersionAndConditionString("published", "location_phrases");
	}
	public String getEmbargoSubmittedVersion(){
		
		return extractCompoundConditionByArticleVersionAndConditionString("submitted", "amount", "units");		
	}
	public String getEmbargoAcceptedVersion(){
		return extractCompoundConditionByArticleVersionAndConditionString("accepted", "amount", "units");
	}
	public String getEmbargoPublishedVersion(){
		return extractCompoundConditionByArticleVersionAndConditionString("published", "amount", "units");
	}
	public String getConditionSubmittedVersion(){
		return extractSingleConditionByArticleVersionAndConditionString("submitted", "conditions");
	}
	public String getConditionAcceptedVersion(){
		return extractSingleConditionByArticleVersionAndConditionString("accepted", "conditions");
	}
	public String getConditionPublishedVersion(){
		return extractSingleConditionByArticleVersionAndConditionString("published", "conditions");
	}
	
	
	
	
	
	
	//recursive function: Takes a JSON Object (JSONArray or JSONObject) and a Key;
	//will loop through whole Object and return the value on the lowest layer
	private Object loopThroughJsonObjectAndFindKey(Object object, String key)
	{	
		//first case: current Object is JSONObject and contains key		
		if (object instanceof JSONObject && ((JSONObject)object).has(key))
		{			
			if (((JSONObject)object).optJSONObject(key) != null)
			{
				return ((JSONObject)object).getJSONObject(key);
			} else if (((JSONObject)object).optJSONArray(key) != null)
			{				
				return ((JSONObject)object).getJSONArray(key);
			} else 
			{
				return ((JSONObject)object).get(key);
			}
		//second case: current Object is JSONObject and does not contain key	
		} else if (object instanceof JSONObject && !((JSONObject)object).has(key))
		{
			Iterator<String> jsonObjectKeys = ((JSONObject)object).keys();
			while(jsonObjectKeys.hasNext())
			{
				//iterate through all keys
				//we need to check if they key we are looking for is hiding deeper down
				//if we find it: return the object where we found the key
				//else: Keep looping
				String currentLoopKey = jsonObjectKeys.next();	
				Object returnedObject = loopThroughJsonObjectAndFindKey(((JSONObject)object).get(currentLoopKey), key);
				if (returnedObject != null)
				{
					return returnedObject;
				}
			}
		// third case: current Object is JSONArray
		// loop through all elements, do the recursive thing we all hate so much!
		}else if (object instanceof JSONArray)
		{			
			String returnString = "";
			for (Object subObject : ((JSONArray)object))
			{						
				Object firstLayerObject = loopThroughJsonObjectAndFindKey(subObject, key);
				if (firstLayerObject != null)
				{							
					//sometimes keys repeat themselves (like key title: has several keys, including title)
					Object secondLayerObject = loopThroughJsonObjectAndFindKey(firstLayerObject, key);
					
					if (secondLayerObject != null)
					{	
							return secondLayerObject;
					} else
					{						
						
					//if remaining value is validJson, we need to go through the function again. Else, check if there are multiple values in final array, return them as a string
					if (isJSONValid(firstLayerObject.toString()))
					{
						return firstLayerObject;
					} else
					{
					if (!returnString.isEmpty())
					{
						returnString = returnString + "/";
					}
						returnString = returnString + firstLayerObject.toString();
					}
					}					
				} 
						
			}
			if (!returnString.isEmpty())
			{
				return returnString;	
			}		
		}
		return null;
	}
	
	public boolean isJSONValid(String test) {
	    try {
	        new JSONObject(test);
	    } catch (JSONException ex) {
	        // edited, to include @Arthur's comment
	        // e.g. in case JSONArray is valid as well...
	        try {
	            new JSONArray(test);
	        } catch (JSONException ex1) {
	            return false;
	        }
	    }
	    return true;
	}
	
	//special case: If Key "Phrase" is found: This means we are in the last layer of the json Object
	//return value for key "phrase"; else turn Object into string and return;
	//Function excluded from main recursive function for "safety reasons".
	//Because of the recursive nature, we cant simply check whether an object contains a key, and whether that key/value pair contains a phrase
	//(we could, but it would make the whole thing more difficult to understand)
	//So instead, we check the final item, and if we want to "clean it up", we check if there is still
	//a "phrase" key somewhere in there. //if object = null; return "/"
	//if object still qualifies as a JSONArray, clean that up, too!
	private String turnObjectToString(Object object)
	{
		if (object == null)
		{
			return keyNotFoundMessage;			
		} else 
		{
			Object phraseObject = loopThroughJsonObjectAndFindKey(object, "phrase");
			if (phraseObject != null)
			{
				return loopThroughJsonObjectAndFindKey(object, "phrase").toString();
			}
		}
		if (object instanceof JSONArray)
		{
			String returnString = "\t";
			for (int counter = 0; counter < ((JSONArray)object).length(); counter ++)
			{	
				returnString += ((JSONArray)object).getString(counter).toString();
				returnString += System.lineSeparator() + "\t";
				
			}
			return returnString;
		}
		return object.toString();
	}

	

}
