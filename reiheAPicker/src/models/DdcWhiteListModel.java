package models;

public class DdcWhiteListModel {
	
	private int ddcNumber;
	private boolean covers0to100;
	
	public DdcWhiteListModel(int ddcNumber, boolean covers0to100)
	{
		this.ddcNumber = ddcNumber;
		this.covers0to100 = covers0to100;
	}
	
	public int getDdcNumber()
	{
		return this.ddcNumber;
	}
	
	public boolean getCovers0to100()
	{
		return this.covers0to100;
	}
	

}
