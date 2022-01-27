package logic;

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
import java.time.Duration;
import java.time.LocalDateTime;

/*
 * Class containing all functions related to API-Interaction
 */
public class ApiCallerClass {
	
	//execute Post
	private LocalDateTime timeStamp;
	
	public ApiCallerClass()
	{
		this.timeStamp = LocalDateTime.now();
	}
	
	public String callUrl(URL targetURL) throws InterruptedException {
				
		HttpURLConnection connection = null;
				
		if (timeStamp != null && Duration.between(timeStamp, LocalDateTime.now()).getSeconds() < .5)
		{			
			Thread.sleep(500);	
		}
		this.timeStamp = LocalDateTime.now();
		
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