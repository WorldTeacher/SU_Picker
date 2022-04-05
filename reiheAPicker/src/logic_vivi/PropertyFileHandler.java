package logic_vivi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import models_vivi.PropertiesFileModel;

public class PropertyFileHandler {
	
		private static final PropertyFileHandler propertyFileHandler = new PropertyFileHandler();
		//propertiesFile Location
		private String propertiesFileString = "./viviconfig.properties";		
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
	public void setConfigDetailGeneral(String sherpaUrlString, String sherpaApiKeyString) throws IOException
	{			
		this.propertyFileModel.setGeneralProperties(sherpaUrlString, sherpaApiKeyString);	
		savePropertiesFile(this.propertyFileModel.getProperties(), this.propertiesFileString);
		return;	
	}
	
	//Saves Changes in "Options" Tab to config file
		public void setConfigDetailSingleCall(Boolean apiSingleCallByTitleIfNoIssnFoundCheckbox, Boolean displayConditionsIfNoTagContainedCheckbox) throws IOException
		{			
			this.propertyFileModel.setSingleApiCallProperties(apiSingleCallByTitleIfNoIssnFoundCheckbox.toString(), displayConditionsIfNoTagContainedCheckbox.toString());
			savePropertiesFile(this.propertyFileModel.getProperties(), this.propertiesFileString);
			return;	
		}
		
		//Saves Changes in "Options" Tab to config file
		public void setConfigDetailMultiCall(Boolean apiMultiCallByTitleIfNoIssnFoundCheckbox, Boolean exportConditionsInSeparateColumnsCheckbox, String exportColumnWhiteListColumnTextField, String exportColumnWhiteListFilterTextField, Boolean exportWhiteListCheckboxBoolean, 
				String importFileTypeISSNColumn, String importFileTypeeISSNColumn, String importFileTypeTitleColumn, 
				String exportColumnWhiteListFilterColumnTextField, String importFileTypeComboBox) throws IOException
		{			
			this.propertyFileModel.setMultiApiCallProperties(exportConditionsInSeparateColumnsCheckbox.toString(), 
					apiMultiCallByTitleIfNoIssnFoundCheckbox.toString(), 
					exportColumnWhiteListColumnTextField, 
					exportColumnWhiteListFilterTextField, 
					exportWhiteListCheckboxBoolean.toString(),
					importFileTypeISSNColumn,
					importFileTypeeISSNColumn,
					importFileTypeTitleColumn,
					exportColumnWhiteListFilterColumnTextField,
					importFileTypeComboBox
					);
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
