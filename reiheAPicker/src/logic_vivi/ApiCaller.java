package logic_vivi;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/*
 * Class containing all functions related to API-Interaction
 */
public class ApiCaller {
	
	//registry key you'll get after registering for free; necessary to prevent limited amount of calls per day	
	public String romeoSherpaApiResponseString;	
	private Boolean fetchByTitleIfIssnMissingSingleCall;
	Boolean fetchByTitleIfIssnMissingMultiCall;
	private PropertyFileHandler propertyFilehandler;
	
	//Sherpa Romeo url parts from config file
	private String sherpaRomeoKeyString;
	private String sherpaRomeoApiString;
	
	//Sherpa Romeo url parts to be assembled
	private String apiKeyRetrieveByAndItemTypeIndicator = "retrieve_by_id?item-type=publication";
	private String apiKeyIndicatorString = "&api-key=";
	private String apiReturnFormatIndicatorString = "&format=Json";
	private String apiIdentifierFormatIndicatorString = "&identifier=";
	
	
	public ApiCaller()
	{
		this.propertyFilehandler = PropertyFileHandler.getInstance();		
		this.sherpaRomeoKeyString = this.propertyFilehandler.propertyFileModel.getSherpaKey();
		this.sherpaRomeoApiString = this.propertyFilehandler.propertyFileModel.getSherpaApiAdress();
		this.fetchByTitleIfIssnMissingSingleCall = this.propertyFilehandler.propertyFileModel.getSingleApiCallByTitleIfNoIssnFound();
		this.fetchByTitleIfIssnMissingMultiCall = this.propertyFilehandler.propertyFileModel.getMultiApiCallByTitleIfNoIssnFound();
	}
	
	public void initWithISSnOrTitle(String textFieldContent, Boolean SingleCall)
	{
		//if content is valid issn, call api by issn; Else: call api by title		
		if (ConvenienceTools.isValidISSN(textFieldContent))
		{
			initWithISSN(textFieldContent);
		} else			
		{
			if ((SingleCall && fetchByTitleIfIssnMissingSingleCall) || (!SingleCall && fetchByTitleIfIssnMissingMultiCall))
			{
				initWithTitleNotISSN(textFieldContent.replace(" ", "%20"));
			} else
			{
				this.romeoSherpaApiResponseString = "Keine gültige ISSN erkannt. Der Abruf über den Titel wurde deaktiviert!\nDiese Einstellung können Sie im Optionsmenü ändern!";
			}
		}	
	}
		
		
	//if issn is available; use this! ISSN are unique, therefore the data returned will be reliable
	public void initWithISSN(String textFieldContent)
	{	
		this.romeoSherpaApiResponseString = "";
		if (textFieldContent.isEmpty())
		{
			this.romeoSherpaApiResponseString = "";
			return;
		}
	
		try {
			this.romeoSherpaApiResponseString = executePost(createUrl(textFieldContent));
		} catch (URISyntaxException | InterruptedException e) {			
			e.printStackTrace();
		}	
		return;
	}
	
	//if issn is not available; use this! careful: Journal names might return bad results
	public void initWithTitleNotISSN(String textFieldContent)
	{	
		this.romeoSherpaApiResponseString = "";
		if (textFieldContent.isEmpty())
		{
			this.romeoSherpaApiResponseString = "";
			return;
		}		
			
		try {
			this.romeoSherpaApiResponseString = executePost(createUrl(textFieldContent));
		} catch (URISyntaxException | InterruptedException e) {		
			e.printStackTrace();
		}		
		return;
	}
	
	//create URL from its individual strings
	private URL createUrl(String itemToSearch) throws URISyntaxException
	{
		URL sherpaAssembledUrl = null;
	
		try {
			sherpaAssembledUrl = new URL(sherpaRomeoApiString + apiKeyRetrieveByAndItemTypeIndicator + apiKeyIndicatorString + sherpaRomeoKeyString + apiReturnFormatIndicatorString + apiIdentifierFormatIndicatorString + itemToSearch);		
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return sherpaAssembledUrl;
	}
	
	//execute Post
	private String executePost(URL targetURL) throws InterruptedException {
		
		HttpURLConnection connection = null;
		Thread.sleep(500);			
			//actually calling the api, fetching data
		  try {
		    //Create connection		   
		  
		    CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));		 
		    	
		    connection = (HttpURLConnection) targetURL.openConnection();
		    connection.setRequestMethod("GET");
		    connection.setRequestProperty("Accept-Charset", StandardCharsets.UTF_8.name());	

		    InputStream is = connection.getInputStream();
		    
		    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		    StringBuilder response = new StringBuilder();
		    String line;
		    while ((line = rd.readLine()) != null) {
		      response.append(line);
		      response.append(System.lineSeparator());
		    }
		    rd.close();
		    return response.toString();
		  } catch (Exception e) {
		    e.printStackTrace();
		    return null;
		  } finally {
		    if (connection != null) {
		      //connection.disconnect();
		    }
		  }
		}
}
