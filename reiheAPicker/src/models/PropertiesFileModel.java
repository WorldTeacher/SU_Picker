package models;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.Properties;


//new PropertiesFileModel
//created to simplifiy the task of creating new Properties in the config file
//also makes creating new fields in properties guy much easier
//in order to add a new property:
//(1) Create Field in this Model
//For example: private PropertyModel RandomCheckbox;
//(2) Add Field to Constructors
// (two separate ones, one with values, second one only created model with default values (in case no properties file detected))
// you have to add the new Property to both constructors
// One constructor takes the actual value (from the file), the default value, and the tagName for the file
// the second constructor 
//(3) Add new Field to function "InitPropertyList"
//Function gathers all Properties in a list, so other Functions can be generic and iterate through that very list
//(4)Add new Field to function that corresponds to its tab (setGeneralProperties, setSingleCallProperties, setMultiCallProperties
//These functions are called when the user clicks the save button on each tab
//(5) create a getter Function

public class PropertiesFileModel {	
	
	private ArrayList<PropertyModel> PropertyModels;
	
	private PropertyModel settings_ExportFileFolder;
	private PropertyModel settings_PublisherBlacklist;
	private PropertyModel settings_RemoveDuplicates;
	private PropertyModel settings_DDCBlacklist;
	private PropertyModel settings_DDCWhitelist;
	private PropertyModel settings_BlockBelletristik;
	private PropertyModel settings_SaveFileFolder;
	
	public void updateProperties(String exportFileFolder,String settings_SaveFileFolder,String publisherBlacklist,String RemoveDuplicates,String dDCBlacklist,String dDCWhitelist, String settings_BlockBelletristik)
	{
		this.settings_ExportFileFolder.setNewValue(exportFileFolder);
		this.settings_SaveFileFolder.setNewValue(settings_SaveFileFolder);
		this.settings_PublisherBlacklist.setNewValue(publisherBlacklist);
		this.settings_RemoveDuplicates.setNewValue(RemoveDuplicates);
		this.settings_DDCBlacklist.setNewValue(dDCBlacklist);
		this.settings_DDCWhitelist.setNewValue(dDCWhitelist);
		this.settings_BlockBelletristik.setNewValue(settings_BlockBelletristik);
	}
	
	public PropertiesFileModel(String settings_ExportFileFolder,String settings_SaveFileFolder, String settings_PublisherBlacklist, String settings_RemoveDuplicates, String settings_DDCBlacklist, String settings_DDCWhitelist, String settings_BlockBelletristik)	
	{
		this.settings_ExportFileFolder = new PropertyModel(settings_ExportFileFolder,"C:\\","settings_ExportFileFolder");
		this.settings_SaveFileFolder.setNewValue(settings_SaveFileFolder);
		this.settings_PublisherBlacklist = new PropertyModel(settings_PublisherBlacklist,"","settings_PublisherBlacklist");
		this.settings_RemoveDuplicates = new PropertyModel(settings_RemoveDuplicates,"","settings_RemoveDuplicates");
		this.settings_DDCBlacklist = new PropertyModel(settings_DDCBlacklist,"","settings_DDCBlacklist");
		this.settings_DDCWhitelist = new PropertyModel(settings_DDCWhitelist,"","settings_DDCWhitelist");
		this.settings_BlockBelletristik = new PropertyModel(settings_BlockBelletristik, "", "settings_BlockBelletristik");
		initPropertyList();
	}
	
	public PropertiesFileModel()	
	{
		this.settings_ExportFileFolder = new PropertyModel("C:\\ReiheA\\Export","settings_ExportFileFolder");
		this.settings_SaveFileFolder = new PropertyModel("C:\\ReiheA\\Save","settings_SaveFileFolder");
		this.settings_PublisherBlacklist = new PropertyModel("","settings_PublisherBlacklist");
		this.settings_RemoveDuplicates = new PropertyModel("","settings_RemoveDuplicates");
		this.settings_DDCBlacklist = new PropertyModel("","settings_DDCBlacklist");
		this.settings_DDCWhitelist = new PropertyModel("","settings_DDCWhitelist");
		this.settings_BlockBelletristik = new PropertyModel("", "settings_BlockBelletristik");
		
		initPropertyList();
	}
	
	private void initPropertyList()
	{		
		this.PropertyModels = new ArrayList<PropertyModel>();
		
		this.PropertyModels.add(this.settings_ExportFileFolder);
		this.PropertyModels.add(this.settings_SaveFileFolder);
		this.PropertyModels.add(this.settings_PublisherBlacklist);
		this.PropertyModels.add(this.settings_RemoveDuplicates);
		this.PropertyModels.add(this.settings_DDCBlacklist);
		this.PropertyModels.add(this.settings_DDCWhitelist);
		this.PropertyModels.add(this.settings_BlockBelletristik);
		
	}
	
	public void setGeneralProperties(String settings_ExportFileFolder)
	{
		this.settings_ExportFileFolder.setNewValue(settings_ExportFileFolder);	
		initPropertyList();
	}	

	public Properties getProperties()
	{
		Properties properties = new Properties();
		for(PropertyModel propertyModel: PropertyModels)
		{
			properties.setProperty(propertyModel.TagNameValue, propertyModel.getCurrentValue());
		}
		
		return properties;		
	}
	

	
	public void setProperties(Properties propertiesFile)
	{
		for (Entry<Object, Object> propertyFileItem : propertiesFile.entrySet())
		{
			for (PropertyModel propertyModel : PropertyModels)
			{
				if (propertyFileItem.getKey().equals(propertyModel.TagNameValue))
				{
					propertyModel.setNewValue(propertyFileItem.getValue().toString());
				}
			}
		}		
		initPropertyList();
		return;
	}
		
	public String get_settings_ExportFileFolder()
	{
		return this.settings_ExportFileFolder.getCurrentValue();
	}
	public String get_settings_PublisherBlacklist()
	{
		return this.settings_PublisherBlacklist.getCurrentValue();
	}
	public String get_settings_RemoveDuplicates()
	{
		return this.settings_RemoveDuplicates.getCurrentValue();
	}
	public String get_settings_DDCBlacklist()
	{
		return this.settings_DDCBlacklist.getCurrentValue();
	}
	public String get_settings_DDCWhitelist()
	{
		return this.settings_DDCWhitelist.getCurrentValue();
	}
	public String get_settings_BlockBelletristik()
	{
		return this.settings_BlockBelletristik.getCurrentValue();
	}
	public String get_settings_SaveFileFolder()
	{
		return this.settings_SaveFileFolder.getCurrentValue();
	}
	
	public String get_labels_ExportFileFolder()
	{
		return this.settings_ExportFileFolder.getTagNameValue();
	}
	public String get_labels_PublisherBlacklist()
	{
		return this.settings_PublisherBlacklist.getTagNameValue();
	}
	public String get_labels_RemoveDuplicates()
	{
		return this.settings_RemoveDuplicates.getTagNameValue();
	}
	public String get_labels_DDCBlacklist()
	{
		return this.settings_DDCBlacklist.getTagNameValue();
	}
	public String get_labels_DDCWhitelist()
	{
		return this.settings_DDCWhitelist.getTagNameValue();
	}
	public String get_labels_BlockBelletristik()
	{
		return this.settings_BlockBelletristik.getTagNameValue();
	}
	public String get_labels_SaveFileFolder()
	{
		return this.settings_SaveFileFolder.getTagNameValue();
	}
	
}