package models;
import java.util.ArrayList;
import java.util.Map.Entry;

import javafx.scene.control.TextField;

import java.util.Properties;

import var.Constants;


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
	private PropertyModel settings_f1search;
	private PropertyModel settings_f2search;
	private PropertyModel settings_f3search;
	private PropertyModel settings_f4search;
	private PropertyModel settings_f1searchShift;
	private PropertyModel settings_f2searchShift;
	private PropertyModel settings_f3searchShift;
	private PropertyModel settings_f4searchShift;
	private PropertyModel settings_f1searchCtrl;
	private PropertyModel settings_f2searchCtrl;
	private PropertyModel settings_f3searchCtrl;
	private PropertyModel settings_f4searchCtrl;
	private PropertyModel settings_stichwortmarkierungGruen;
	private PropertyModel settings_stichwortmarkierungRot;
	private PropertyModel settings_API_link;
	private PropertyModel settings_API_CheckApiAfterImport;	
	private PropertyModel settings_API_RemoveHoldings;
	private PropertyModel settings_API_sigil;	
	private PropertyModel settings_API_isbnSeparator;
	private PropertyModel settings_API_markOA;	
	private PropertyModel settings_API_RemoveOA;
	
	public void updateProperties(String exportFileFolder,String settings_SaveFileFolder,String publisherBlacklist,String RemoveDuplicates,String dDCBlacklist,
			String dDCWhitelist, String settings_BlockBelletristik, String settings_f1search,String settings_f2search,String settings_f3search,String settings_f4search,
			String settings_f1searchShift,String settings_f2searchShift,String settings_f3searchShift,String settings_f4searchShift,String settings_f1searchCtrl,
			String settings_f2searchCtrl,String settings_f3searchCtrl,String settings_f4searchCtrl, String settings_stichwortmarkierungGruen, String settings_stichwortmarkierungRot, 
			String settings_API_link, String settings_API_CheckApiAfterImport, String settings_API_RemoveHoldings, String settings_API_isbnSeparator, String settings_API_sigil, String settings_API_markOA, String settings_API_RemoveOA)
	{
		this.settings_ExportFileFolder.setNewValue(exportFileFolder);
		this.settings_SaveFileFolder.setNewValue(settings_SaveFileFolder);
		this.settings_PublisherBlacklist.setNewValue(publisherBlacklist);
		this.settings_RemoveDuplicates.setNewValue(RemoveDuplicates);
		this.settings_DDCBlacklist.setNewValue(dDCBlacklist);
		this.settings_DDCWhitelist.setNewValue(dDCWhitelist);
		this.settings_BlockBelletristik.setNewValue(settings_BlockBelletristik);
		this.settings_f1search.setNewValue(settings_f1search);
		this.settings_f2search.setNewValue(settings_f2search);
		this.settings_f3search.setNewValue(settings_f3search);
		this.settings_f4search.setNewValue(settings_f4search);
		this.settings_f1searchShift.setNewValue(settings_f1searchShift);
		this.settings_f2searchShift.setNewValue(settings_f2searchShift);
		this.settings_f3searchShift.setNewValue(settings_f3searchShift);
		this.settings_f4searchShift.setNewValue(settings_f4searchShift);
		this.settings_f1searchCtrl.setNewValue(settings_f1searchCtrl);
		this.settings_f2searchCtrl.setNewValue(settings_f2searchCtrl);
		this.settings_f3searchCtrl.setNewValue(settings_f3searchCtrl);
		this.settings_f4searchCtrl.setNewValue(settings_f4searchCtrl);
		this.settings_stichwortmarkierungGruen.setNewValue(settings_stichwortmarkierungGruen);
		this.settings_stichwortmarkierungRot.setNewValue(settings_stichwortmarkierungRot);
		this.settings_API_link.setNewValue(settings_API_link);
		this.settings_API_CheckApiAfterImport.setNewValue(settings_API_CheckApiAfterImport);	
		this.settings_API_RemoveHoldings.setNewValue(settings_API_RemoveHoldings);		
		this.settings_API_isbnSeparator.setNewValue(settings_API_isbnSeparator);
		this.settings_API_sigil.setNewValue(settings_API_sigil);
		this.settings_API_markOA.setNewValue(settings_API_markOA);
		this.settings_API_RemoveOA.setNewValue(settings_API_RemoveOA);

	}
	
	public PropertiesFileModel(String settings_ExportFileFolder,String settings_SaveFileFolder, String settings_PublisherBlacklist, String settings_RemoveDuplicates, String settings_DDCBlacklist, 
			String settings_DDCWhitelist, String settings_BlockBelletristik, String settings_f1search,String settings_f2search,String settings_f3search,String settings_f4search,
			String settings_f1searchShift,String settings_f2searchShift,String settings_f3searchShift,String settings_f4searchShift,String settings_f1searchCtrl,String settings_f2searchCtrl,
			String settings_f3searchCtrl,String settings_f4searchCtrl, String settings_stichwortmarkierungGruen, String settings_stichwortmarkierungRot, String settings_API_link, 
			String settings_API_CheckApiAfterImport, String settings_API_RemoveHoldings, String settings_API_isbnSeparator, String settings_API_sigil, String settings_API_markOA, String settings_API_RemoveOA)	
	{
		this.settings_ExportFileFolder = new PropertyModel(settings_ExportFileFolder,"C:\\","settings_ExportFileFolder");
		this.settings_SaveFileFolder.setNewValue(settings_SaveFileFolder);
		this.settings_PublisherBlacklist = new PropertyModel(settings_PublisherBlacklist,"","settings_PublisherBlacklist");
		this.settings_RemoveDuplicates = new PropertyModel(settings_RemoveDuplicates,"","settings_RemoveDuplicates");
		this.settings_DDCBlacklist = new PropertyModel(settings_DDCBlacklist,"","settings_DDCBlacklist");
		this.settings_DDCWhitelist = new PropertyModel(settings_DDCWhitelist,"","settings_DDCWhitelist");
		this.settings_BlockBelletristik = new PropertyModel(settings_BlockBelletristik, "", "settings_BlockBelletristik");
		this.settings_f1search = new PropertyModel(settings_f1search, Constants.CatalogueUrlTitleSearch, "settings_f1search");
		this.settings_f2search = new PropertyModel(settings_f2search, Constants.CatalogueUrlTitleAuthorSearch, "settings_f2search");
		this.settings_f3search = new PropertyModel(settings_f3search, Constants.CatalogueUrlIsbnfSearch, "settings_f3search");
		this.settings_f4search = new PropertyModel(settings_f4search, Constants.CatalogueUrlpublisherSearch, "settings_f4search");
		this.settings_f1searchShift = new PropertyModel(settings_f1searchShift, "", "settings_f1searchShift");
		this.settings_f2searchShift = new PropertyModel(settings_f2searchShift, "", "settings_f2searchShift");
		this.settings_f3searchShift = new PropertyModel(settings_f3searchShift, "", "settings_f3searchShift");
		this.settings_f4searchShift = new PropertyModel(settings_f4searchShift, "", "settings_f4searchShift");
		this.settings_f1searchCtrl = new PropertyModel(settings_f1searchCtrl, "", "settings_f1searchCtrl");
		this.settings_f2searchCtrl = new PropertyModel(settings_f2searchCtrl, "", "settings_f2searchCtrl");
		this.settings_f3searchCtrl = new PropertyModel(settings_f3searchCtrl, "", "settings_f3searchCtrl");
		this.settings_f4searchCtrl = new PropertyModel(settings_f4searchCtrl, "", "settings_f4searchCtrl");
		this.settings_stichwortmarkierungGruen = new PropertyModel(settings_stichwortmarkierungGruen, "", "settings_stichwortmarkierungGruen");
		this.settings_stichwortmarkierungRot = new PropertyModel(settings_stichwortmarkierungRot, "", "settings_stichwortmarkierungRot");	
		this.settings_API_link = new PropertyModel(settings_API_link, "", "settings_API_link");
		this.settings_API_CheckApiAfterImport = new PropertyModel(settings_API_CheckApiAfterImport, "", "settings_API_CheckApiAfterImport");	
		this.settings_API_RemoveHoldings = new PropertyModel(settings_API_RemoveHoldings, "", "settings_API_RemoveHoldings");		
		this.settings_API_isbnSeparator = new PropertyModel(settings_API_isbnSeparator, "", "settings_API_isbnSeparator");
		this.settings_API_sigil = new PropertyModel(settings_API_sigil, "", "settings_API_sigil");
		this.settings_API_markOA = new PropertyModel(settings_API_markOA, "", "settings_API_markOA");
		this.settings_API_RemoveOA = new PropertyModel(settings_API_RemoveOA, "", "settings_API_RemoveOA");
	
		
		initPropertyList();
	}
	
	public PropertiesFileModel()	
	{
		this.settings_ExportFileFolder = new PropertyModel("C:\\ReiheA\\Export","settings_ExportFileFolder");
		this.settings_SaveFileFolder = new PropertyModel("C:\\ReiheA\\Save","settings_SaveFileFolder");
		this.settings_PublisherBlacklist = new PropertyModel("","settings_PublisherBlacklist");
		this.settings_RemoveDuplicates = new PropertyModel("true","settings_RemoveDuplicates");
		this.settings_DDCBlacklist = new PropertyModel("","settings_DDCBlacklist");
		this.settings_DDCWhitelist = new PropertyModel("","settings_DDCWhitelist");
		this.settings_BlockBelletristik = new PropertyModel("false", "settings_BlockBelletristik");
		this.settings_f1search = new PropertyModel(Constants.CatalogueUrlTitleSearch, "settings_f1search");
		this.settings_f2search = new PropertyModel(Constants.CatalogueUrlTitleAuthorSearch, "settings_f2search");
		this.settings_f3search = new PropertyModel(Constants.CatalogueUrlIsbnfSearch, "settings_f3search");
		this.settings_f4search = new PropertyModel(Constants.CatalogueUrlpublisherSearch, "settings_f4search");
		this.settings_f1searchShift = new PropertyModel(Constants.KVKUrlTitleSearch, "settings_f1searchShift");
		this.settings_f2searchShift = new PropertyModel(Constants.KVKUrlTitleAuthorSearch, "settings_f2searchShift");
		this.settings_f3searchShift = new PropertyModel(Constants.KVKUrlIsbnfSearch, "settings_f3searchShift");
		this.settings_f4searchShift = new PropertyModel(Constants.KVKUrlpublisherSearch , "settings_f4searchShift");
		this.settings_f1searchCtrl = new PropertyModel("", "settings_f1searchCtrl");
		this.settings_f2searchCtrl = new PropertyModel("", "settings_f2searchCtrl");
		this.settings_f3searchCtrl = new PropertyModel("", "settings_f3searchCtrl");
		this.settings_f4searchCtrl = new PropertyModel("", "settings_f4searchCtrl");
		this.settings_stichwortmarkierungGruen = new PropertyModel("", "settings_stichwortmarkierungGruen");
		this.settings_stichwortmarkierungRot = new PropertyModel("", "settings_stichwortmarkierungRot");
		this.settings_API_link = new PropertyModel("http://sru.k10plus.de/gvk!rec=1?version=1.1&operation=searchRetrieve&query=pica.isb%3D[{[isbn]}]&maximumRecords=10&recordSchema=marcxml", "settings_API_link");
		this.settings_API_CheckApiAfterImport = new PropertyModel("false", "settings_API_CheckApiAfterImport");	
		this.settings_API_RemoveHoldings = new PropertyModel("false", "settings_API_RemoveHoldings");		
		this.settings_API_isbnSeparator = new PropertyModel("+or+pica.isb%3D", "", "settings_API_isbnSeparator");
		this.settings_API_sigil = new PropertyModel("DE-Frei129", "", "settings_API_sigil");
		this.settings_API_markOA = new PropertyModel("true", "", "settings_API_markOA");
		this.settings_API_RemoveOA = new PropertyModel("false", "", "settings_API_RemoveOA");
	
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
		this.PropertyModels.add(this.settings_f1search);
		this.PropertyModels.add(this.settings_f2search);
		this.PropertyModels.add(this.settings_f3search);
		this.PropertyModels.add(this.settings_f4search);
		this.PropertyModels.add(this.settings_f1searchShift);
		this.PropertyModels.add(this.settings_f2searchShift);
		this.PropertyModels.add(this.settings_f3searchShift);
		this.PropertyModels.add(this.settings_f4searchShift);
		this.PropertyModels.add(this.settings_f1searchCtrl);
		this.PropertyModels.add(this.settings_f2searchCtrl);
		this.PropertyModels.add(this.settings_f3searchCtrl);
		this.PropertyModels.add(this.settings_f4searchCtrl);
		this.PropertyModels.add(this.settings_stichwortmarkierungGruen);
		this.PropertyModels.add(this.settings_stichwortmarkierungRot);
		this.PropertyModels.add(this.settings_API_link);
		this.PropertyModels.add(this.settings_API_CheckApiAfterImport);
		this.PropertyModels.add(this.settings_API_RemoveHoldings);
		this.PropertyModels.add(this.settings_API_sigil);
		this.PropertyModels.add(this.settings_API_isbnSeparator);
		this.PropertyModels.add(this.settings_API_markOA);
		this.PropertyModels.add(this.settings_API_RemoveOA);
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
	

	public String get_settings_stichwortmarkierungGruen     (){return this.settings_stichwortmarkierungGruen     .getCurrentValue();} 
	public String get_settings_stichwortmarkierungRot     (){return this.settings_stichwortmarkierungRot     .getCurrentValue();} 
	
	public String get_settings_API_link () {return this.settings_API_link.getCurrentValue();}
	
	public String get_settings_f1search     (){return this.settings_f1search     .getCurrentValue();} 
	public String get_settings_f2search     (){return this.settings_f2search     .getCurrentValue();} 
	public String get_settings_f3search     (){return this.settings_f3search     .getCurrentValue();} 
	public String get_settings_f4search     (){return this.settings_f4search     .getCurrentValue();} 
	public String get_settings_f1searchShift(){return this.settings_f1searchShift.getCurrentValue();} 
	public String get_settings_f2searchShift(){return this.settings_f2searchShift.getCurrentValue();} 
	public String get_settings_f3searchShift(){return this.settings_f3searchShift.getCurrentValue();} 
	public String get_settings_f4searchShift(){return this.settings_f4searchShift.getCurrentValue();} 
	public String get_settings_f1searchCtrl (){return this.settings_f1searchCtrl .getCurrentValue();} 
	public String get_settings_f2searchCtrl (){return this.settings_f2searchCtrl .getCurrentValue();} 
	public String get_settings_f3searchCtrl (){return this.settings_f3searchCtrl .getCurrentValue();} 
	public String get_settings_f4searchCtrl (){return this.settings_f4searchCtrl .getCurrentValue();} 
	public String get_settings_API_sigil (){return this.settings_API_sigil .getCurrentValue();} 
	public String get_settings_API_isbnSeparator (){return this.settings_API_isbnSeparator .getCurrentValue();} 
	public String get_settings_API_markOA (){return this.settings_API_markOA .getCurrentValue();} 
	public String get_settings_API_RemoveOA (){return this.settings_API_RemoveOA .getCurrentValue();} 
		
	public String get_settings_API_CheckApiAfterImport (){return this.settings_API_CheckApiAfterImport .getCurrentValue();} 
	public String get_settings_API_RemoveHoldings (){return this.settings_API_RemoveHoldings .getCurrentValue();} 
	
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
	
	public String get_labels_f1search     (){return this.settings_f1search     .getTagNameValue();} 
	public String get_labels_f2search     (){return this.settings_f2search     .getTagNameValue();} 
	public String get_labels_f3search     (){return this.settings_f3search     .getTagNameValue();} 
	public String get_labels_f4search     (){return this.settings_f4search     .getTagNameValue();} 
	public String get_labels_f1searchShift(){return this.settings_f1searchShift.getTagNameValue();} 
	public String get_labels_f2searchShift(){return this.settings_f2searchShift.getTagNameValue();} 
	public String get_labels_f3searchShift(){return this.settings_f3searchShift.getTagNameValue();} 
	public String get_labels_f4searchShift(){return this.settings_f4searchShift.getTagNameValue();} 
	public String get_labels_f1searchCtrl (){return this.settings_f1searchCtrl .getTagNameValue();} 
	public String get_labels_f2searchCtrl (){return this.settings_f2searchCtrl .getTagNameValue();} 
	public String get_labels_f3searchCtrl (){return this.settings_f3searchCtrl .getTagNameValue();} 
	public String get_labels_f4searchCtrl (){return this.settings_f4searchCtrl .getTagNameValue();} 
	public String get_labels_API_sigil (){return this.settings_API_sigil .getTagNameValue();} 
	public String get_labels_API_isbnSeparator (){return this.settings_API_isbnSeparator .getTagNameValue();} 
	public String get_labels_API_markOA (){return this.settings_API_markOA .getTagNameValue();} 
	public String get_labels_API_RemoveOA (){return this.settings_API_RemoveOA .getTagNameValue();} 

	public String get_labels_API_RemoveHoldings (){return this.settings_API_RemoveHoldings .getTagNameValue();} 
	public String get_labels_API_CheckApiAfterImport (){return this.settings_API_CheckApiAfterImport .getTagNameValue();} 	
	
	public String get_labels_stichwortmarkierungGruen (){return this.settings_stichwortmarkierungGruen .getTagNameValue();} 
	public String get_labels_stichwortmarkierungRot (){return this.settings_stichwortmarkierungRot .getTagNameValue();} 

	public String get_Labels_API_link () {return this.settings_API_link.getTagNameValue();}
	
}