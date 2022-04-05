package logic_reiheAPicker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import models_reiheAPicker.PropertiesFileModel;

public class PropertyFileHandler {
	
		private static final PropertyFileHandler propertyFileHandler = new PropertyFileHandler();
		//propertiesFile Location
		private String propertiesFileString = "./reiheAconfig.properties";		
		//addition 7.9.20;
		public PropertiesFileModel propertyFileModel;
	 
	PropertyFileHandler() 
	{				
		try {					
			initConfigFile();			
				
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static PropertyFileHandler getInstance() {
			return propertyFileHandler;
	}		
	
	//Opens config file and loads config details
	//If config file cannot be found: Create default Config file
	private void initConfigFile() throws IOException
	{		
		File configFile = new File(this.propertiesFileString);		
		
		if (!configFile.exists())
		{	
			configFile.createNewFile();	
			this.propertyFileModel = new PropertiesFileModel();	
			savePropertiesFile(propertyFileModel.getProperties(), this.propertiesFileString);						
			
		} else if (this.propertyFileModel == null)
		{			
			this.propertyFileModel = new PropertiesFileModel();	
			InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(configFile), StandardCharsets.UTF_8);	
			Properties properties = new Properties();			
			properties.load(inputStreamReader);			
			this.propertyFileModel.setProperties(properties);	
			
		} else
		{
			InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(configFile), StandardCharsets.UTF_8);	
			Properties properties = new Properties();			
			properties.load(inputStreamReader);			
			this.propertyFileModel.setProperties(properties);				
		}		
	}
	
	
	//Saves Changes in "Options" Tab to config file
		public void setConfigDetailGeneral(String settings_ExportFileFolder) throws IOException
		{			
		this.propertyFileModel.setGeneralProperties(settings_ExportFileFolder);	
		savePropertiesFile(this.propertyFileModel.getProperties(), this.propertiesFileString);
		return;	
		}
	
		public void setConfigDetail() throws IOException
		{			
		savePropertiesFile(this.propertyFileModel.getProperties(), this.propertiesFileString);
		return;	
		}
		
		
		//Save Properties File
		private static void savePropertiesFile(Properties properties, String location) throws IOException
		{
					File helpTextFile = new File(location);			
							
					OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(helpTextFile), StandardCharsets.UTF_8);								
					properties.store(outputStreamWriter, "Properties");
					outputStreamWriter.close();
		}

}