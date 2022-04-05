package models_vivi;


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
	
	private PropertyModel SherpaApiAdress;
	private PropertyModel SherpaKey;
	private PropertyModel MultiApiCallExportConditionsInSeparateColumns;
	private PropertyModel MultiApiCallByTitleIfNoIssnFound;
	private PropertyModel MultiApiCallWhiteListColumn;
	private PropertyModel MultiApiCallWhiteListFilter;
	private PropertyModel MultiApiCallWhiteListCheckbox;
	private PropertyModel SingleApiCallByTitleIfNoIssnFound;
	private PropertyModel SingleApiCallDisplayConditionsIfNoTagContained;
	private PropertyModel importFileTypeISSNColumn; 
	private PropertyModel importFileTypeeISSNColumn;
	private PropertyModel importFileTypeTitleColumn;
	private PropertyModel exportColumnWhiteListFilterColumnTextField;
	private PropertyModel importFileTypeComboBox;
	
	
	
	public PropertiesFileModel(String sherpaApiAdress, String sherpaKey, String multiApiCallExportConditionsInSeparateColumns, 
			String multiApiCallByTitleIfNoIssnFound, String multiApiCallWhiteListColumn, String multiApiCallWhiteListFilter, String multiApiCallWhiteListCheckbox, 
			String singleApiCallByTitleIfNoIssnFound, String singleApiCallDisplayConditionsIfNoTagContained,
			String importFileTypeISSNColumn, String importFileTypeeISSNColumn, String importFileTypeTitleColumn, 
			String exportColumnWhiteListFilterColumnTextField, String importFileTypeComboBox)	
	{
		
		this.SherpaApiAdress = new PropertyModel(sherpaApiAdress, "https://v2.sherpa.ac.uk/cgi.com/", "SherpaApiAdress");
		this.SherpaKey = new PropertyModel(sherpaKey, "", "SherpaKey");
		this.MultiApiCallExportConditionsInSeparateColumns = new PropertyModel(multiApiCallExportConditionsInSeparateColumns, "true", "MultiApiCallExportConditionsInSeparateColumns");
		this.MultiApiCallByTitleIfNoIssnFound = new PropertyModel(multiApiCallByTitleIfNoIssnFound, "true", "MultiApiCallByTitleIfNoIssnFound");
		this.MultiApiCallWhiteListColumn = new PropertyModel(multiApiCallWhiteListColumn, "", "MultiApiCallWhiteListColumn");
		this.MultiApiCallWhiteListFilter = new PropertyModel(multiApiCallWhiteListFilter, "", "MultiApiCallWhiteListFilter");
		this.MultiApiCallWhiteListCheckbox = new PropertyModel(multiApiCallWhiteListCheckbox, "false", "MultiApiCallWhiteListCheckbox");
		this.SingleApiCallByTitleIfNoIssnFound = new PropertyModel(singleApiCallByTitleIfNoIssnFound, "true", "SingleApiCallByTitleIfNoIssnFound");
		this.SingleApiCallDisplayConditionsIfNoTagContained = new PropertyModel(singleApiCallDisplayConditionsIfNoTagContained, "true", "SingleApiCallDisplayConditionsIfNoTagContained");		
		this.importFileTypeISSNColumn = new PropertyModel(importFileTypeISSNColumn, "", "importFileTypeISSNColumn");
		this.importFileTypeeISSNColumn = new PropertyModel(importFileTypeeISSNColumn, "", "importFileTypeeISSNColumn");
		this.importFileTypeTitleColumn = new PropertyModel(importFileTypeTitleColumn, "", "importFileTypeTitleColumn");
		this.exportColumnWhiteListFilterColumnTextField = new PropertyModel(exportColumnWhiteListFilterColumnTextField, "", "exportColumnWhiteListFilterColumnTextField");
		this.importFileTypeComboBox = new PropertyModel(importFileTypeComboBox, "Web of Science Publication Export", "importFileTypeComboBox");
		initPropertyList();
	}
	
	public PropertiesFileModel()	
	{
		
		this.SherpaApiAdress = new PropertyModel("https://v2.sherpa.ac.uk/cgi.com/", "SherpaApiAdress");
		this.SherpaKey = new PropertyModel("", "SherpaKey");
		this.MultiApiCallExportConditionsInSeparateColumns = new PropertyModel("true", "MultiApiCallExportConditionsInSeparateColumns");
		this.MultiApiCallByTitleIfNoIssnFound = new PropertyModel("true", "MultiApiCallByTitleIfNoIssnFound");
		this.MultiApiCallWhiteListColumn = new PropertyModel("", "MultiApiCallWhiteListColumn");
		this.MultiApiCallWhiteListFilter = new PropertyModel("", "MultiApiCallWhiteListFilter");
		this.MultiApiCallWhiteListCheckbox = new PropertyModel("false", "MultiApiCallWhiteListCheckbox");
		this.SingleApiCallByTitleIfNoIssnFound = new PropertyModel("true", "SingleApiCallByTitleIfNoIssnFound");
		this.SingleApiCallDisplayConditionsIfNoTagContained = new PropertyModel("true", "SingleApiCallDisplayConditionsIfNoTagContained");		
		this.importFileTypeISSNColumn = new PropertyModel("", "importFileTypeISSNColumn");
		this.importFileTypeeISSNColumn = new PropertyModel("", "importFileTypeeISSNColumn");
		this.importFileTypeTitleColumn = new PropertyModel("", "importFileTypeTitleColumn");
		this.exportColumnWhiteListFilterColumnTextField = new PropertyModel("", "exportColumnWhiteListFilterColumnTextField");
		this.importFileTypeComboBox = new PropertyModel("Web of Science Publication Export", "importFileTypeComboBox");
		
		initPropertyList();
	}
	
	private void initPropertyList()
	{		
		this.PropertyModels = new ArrayList<PropertyModel>();		
		this.PropertyModels.add(this.SherpaApiAdress);
		this.PropertyModels.add(this.SherpaKey);
		this.PropertyModels.add(this.MultiApiCallExportConditionsInSeparateColumns);
		this.PropertyModels.add(this.MultiApiCallByTitleIfNoIssnFound);
		this.PropertyModels.add(this.MultiApiCallWhiteListColumn);
		this.PropertyModels.add(this.MultiApiCallWhiteListFilter);
		this.PropertyModels.add(this.MultiApiCallWhiteListCheckbox);
		this.PropertyModels.add(this.SingleApiCallByTitleIfNoIssnFound);
		this.PropertyModels.add(this.SingleApiCallDisplayConditionsIfNoTagContained);
		this.PropertyModels.add(this.importFileTypeISSNColumn);
		this.PropertyModels.add(this.importFileTypeeISSNColumn);
		this.PropertyModels.add(this.importFileTypeTitleColumn);
		this.PropertyModels.add(this.exportColumnWhiteListFilterColumnTextField);
		this.PropertyModels.add(this.importFileTypeComboBox);
		
	}
	
	public void setGeneralProperties(String sherpaApiAdress, String sherpaKey)
	{
		this.SherpaApiAdress.setNewValue(sherpaApiAdress);
		this.SherpaKey.setNewValue(sherpaKey);
		
		initPropertyList();
	}
	
	public void setMultiApiCallProperties(String multiApiCallExportConditionsInSeparateColumns, String multiApiCallByTitleIfNoIssnFound, 
			String multiApiCallWhiteListColumn, String multiApiCallWhiteListFilter, String multiApiCallWhiteListCheckbox,
			String importFileTypeISSNColumn, String importFileTypeeISSNColumn, String importFileTypeTitleColumn, String exportColumnWhiteListFilterColumnTextField, String importFileTypeComboBox)
	{
		this.MultiApiCallExportConditionsInSeparateColumns.setNewValue(multiApiCallExportConditionsInSeparateColumns);
		this.MultiApiCallByTitleIfNoIssnFound.setNewValue(multiApiCallByTitleIfNoIssnFound);
		this.MultiApiCallWhiteListColumn.setNewValue(multiApiCallWhiteListColumn);
		this.MultiApiCallWhiteListFilter.setNewValue(multiApiCallWhiteListFilter);
		this.MultiApiCallWhiteListCheckbox.setNewValue(multiApiCallWhiteListCheckbox);
		this.importFileTypeISSNColumn.setNewValue(importFileTypeISSNColumn);
		this.importFileTypeeISSNColumn.setNewValue(importFileTypeeISSNColumn);
		this.importFileTypeTitleColumn.setNewValue(importFileTypeTitleColumn);
		this.exportColumnWhiteListFilterColumnTextField.setNewValue(exportColumnWhiteListFilterColumnTextField);
		this.importFileTypeComboBox.setNewValue(importFileTypeComboBox);
		
		initPropertyList();
	}
	
	public void setSingleApiCallProperties(String singleApiCallByTitleIfNoIssnFound, String singleApiCallDisplayConditionsIfNoTagContained)
	{
		this.SingleApiCallByTitleIfNoIssnFound.setNewValue(singleApiCallByTitleIfNoIssnFound);
		this.SingleApiCallDisplayConditionsIfNoTagContained.setNewValue(singleApiCallDisplayConditionsIfNoTagContained);
		
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
	
	public String getSherpaApiAdress()
	{
		return this.SherpaApiAdress.getCurrentValue();
	}
	public String getSherpaKey()
	{
		return this.SherpaKey.getCurrentValue(); 
	}
	public Boolean getMultiApiCallExportConditionsInSeparateColumns()
	{
		return Boolean.parseBoolean(this.MultiApiCallExportConditionsInSeparateColumns.getCurrentValue());
	}
	public Boolean getMultiApiCallByTitleIfNoIssnFound()
	{
		return Boolean.parseBoolean(this.MultiApiCallByTitleIfNoIssnFound.getCurrentValue());
	}
	public String getMultiApiCallWhiteListColumn()
	{
		return this.MultiApiCallWhiteListColumn.getCurrentValue();
	}
	public String getMultiApiCallWhiteListFilter()
	{
		return this.MultiApiCallWhiteListFilter.getCurrentValue();
	}
	public Boolean getMultiApiCallWhiteListCheckbox()
	{
		return Boolean.parseBoolean(this.MultiApiCallWhiteListCheckbox.getCurrentValue());
	}
	public Boolean getSingleApiCallByTitleIfNoIssnFound()
	{
		return Boolean.parseBoolean(this.SingleApiCallByTitleIfNoIssnFound.getCurrentValue());
	}
	public Boolean getSingleApiCallDisplayConditionsIfNoTagContained()
	{
		return Boolean.parseBoolean(this.SingleApiCallDisplayConditionsIfNoTagContained.getCurrentValue());
	}
	public String getImportFileTypeComboBox()
	{
		return this.importFileTypeComboBox.getCurrentValue();
	}
	public String getExportColumnWhiteListFilterColumnTextField()
	{
		return this.exportColumnWhiteListFilterColumnTextField.getCurrentValue();
	}
	public String getImportFileTypeTitleColumn()
	{
		return this.importFileTypeTitleColumn.getCurrentValue();
	}
	public String getImportFileTypeISSNColumn()
	{
		return this.importFileTypeISSNColumn.getCurrentValue();
	}	
	public String getImportFileTypeeISSNColumn()
	{
		return this.importFileTypeeISSNColumn.getCurrentValue();
	}	
}
