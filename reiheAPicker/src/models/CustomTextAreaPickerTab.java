package models;

import javafx.scene.control.TextArea;

public class CustomTextAreaPickerTab extends TextArea {
	
	private ListEnum listEnum;
	
	public CustomTextAreaPickerTab(ListEnum listEnum)
	{
		this.listEnum = listEnum;		
	}

	public ListEnum getListEnum() {
		return listEnum;
	}	
		
	
}
