package launcher;

import logic_reiheAPicker.PropertyFileHandler;

public class Startup {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PropertyFileHandler propertyFilehandler = PropertyFileHandler.getInstance();
		MainWindow.startWindow(args);		
	}
	
 

}
