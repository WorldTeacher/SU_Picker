package models_vivi;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

//HelpTextModel contains all values and property names in file: Help.properties
//needed to display generic info-texts
public class HelpTextModel {
	
	private Map<String,String> map;
	public Boolean valuesInitiated;
	
	public HelpTextModel()
	{
		this.map = new HashMap<String, String>();
		//Need to add more "Help Texts"?
		//just add them here and use them wherever you need in the code
		this.valuesInitiated = false;		
		this.map.put("PropertiesApiKey", "");
		this.map.put("PropertiesCallApiViaTitleIfIssnMissing", "");
		this.map.put("PropertiesShowDetailsIfJsonTagMissing", "");
		this.map.put("PropertiesExportDataInSeparateColumns", "");
		this.map.put("PropertiesWhitelistExport", "");
		this.map.put("MultiApiCallChooseFile", "");
		this.map.put("PropertiesFileImportFileTypDefintion", "");
		this.map.put("PropertiesFileImportColumnDefintion", "");
		
	}	
	
	public String getPropertiesApiKey()
	{
		return this.map.get("PropertiesApiKey");
	}
	
	public String getPropertiesCallApiViaTitleIfIssnMissing()
	{
		return this.map.get("PropertiesCallApiViaTitleIfIssnMissing");
	}
	
	public String getPropertiesShowDetailsIfJsonTagMissing()
	{
		return this.map.get("PropertiesShowDetailsIfJsonTagMissing");
	}
	
	public String getPropertiesExportDataInSeparateColumns()
	{
		return this.map.get("PropertiesExportDataInSeparateColumns");
	}
	
	public String getPropertiesWhitelistExport()
	{
		return this.map.get("PropertiesWhitelistExport");
	}
	
	public String getMultiApiCallChooseFile()
	{
		return this.map.get("MultiApiCallChooseFile");
	}	
	
	public String getPropertiesFileImportFileTypDefintion()
	{
		return this.map.get("PropertiesFileImportFileTypDefintion");
	}
	
	public String getPropertiesFileImportColumnDefintion()
	{
		return this.map.get("PropertiesFileImportColumnDefintion");
	}
	
	public void setProperties(Properties properties)
	{		
 		for (Entry<Object, Object> propertyFileItem : properties.entrySet())
		{
			Iterator<Entry<String, String>> iterator = this.map.entrySet().iterator();
			while(iterator.hasNext())
			{
				 Map.Entry<String,String> pair = (Map.Entry<String,String>)iterator.next();
				 if (propertyFileItem.getKey().toString().equals(pair.getKey().toString()))
				 {
					 if (!propertyFileItem.getValue().toString().isEmpty())
					 {
						 this.valuesInitiated = true;	
						 pair.setValue(propertyFileItem.getValue().toString());
					 }					 
				 }		        
			}
		}		
	}
	
	public void addMap(String key, String value)
	{
		this.map.put(key, value);
	}
	
	public Map<String, String> getMap()
	{
		return this.map;
	}
	
	public Properties getProperties()
	{
		Properties properties = new Properties();
		Iterator<Entry<String, String>> iterator = this.map.entrySet().iterator();
		while(iterator.hasNext())
		{
			 Map.Entry<String,String> pair = (Map.Entry<String,String>)iterator.next();		
			 properties.setProperty(pair.getKey(), pair.getValue());
				        
		}		
		return properties;	
	}

}
