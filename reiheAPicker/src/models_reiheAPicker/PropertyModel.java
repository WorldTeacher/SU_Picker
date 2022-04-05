package models_reiheAPicker;

//only used in model: PropertyFileModel
//Basic propertyEntity
//InitialValue not needed as of now
public class PropertyModel {
	
	public final String TagNameValue;
	private String CurrentValue;
	private String InitialValue;
	private String DefaultValue;
	
	public PropertyModel(String currentValue, String defaultValue, String tagNameValue) {
		this.CurrentValue = currentValue;
		this.InitialValue = currentValue;
		this.DefaultValue = defaultValue;	
		this.TagNameValue = tagNameValue;
	}
	
	public PropertyModel(String defaultValue, String tagNameValue) {
		this.CurrentValue = defaultValue;
		this.InitialValue = defaultValue;
		this.DefaultValue = defaultValue;	
		this.TagNameValue = tagNameValue;
	}
	
	public String getCurrentValue()
	{
		return this.CurrentValue;
	}
	
	public String getInitialValue()
	{
		return this.InitialValue;
	}
	
	public String getDefaultValue()
	{
		return this.DefaultValue;
	}
	
	public String getTagNameValue()
	{
		return this.TagNameValue;
	}
	
	public void setNewValue(String newValue)
	{
		this.CurrentValue = newValue;
		this.InitialValue = newValue;
	}
}