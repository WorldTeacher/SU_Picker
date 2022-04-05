package logic_vivi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import models_vivi.HelpTextModel;

//File help.properties includes texts and images, which will be displayed by clicking info-buttons.
//notice: <h1>title</h1> in these texts --> Title for the window that will open after clicking the button
//<h2> - <h6> various headings in different sizes (h2>h3 etc)
//<img src="Resources/image.png"> will display an image
// <p>text</p> will display text in a paragraph with justified text
public class HelpTextFileHandler {
	
	private static final HelpTextFileHandler helpTextFileHandler = new HelpTextFileHandler();
	private String helpTextsFileString = "./help.properties";	
	public HelpTextModel helpTextModel;	
	
	private HelpTextFileHandler() 
	{				
		try {					
			initHelpTextFile();	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static HelpTextFileHandler getInstance() {
		return helpTextFileHandler;
	}	
	
		//Opens file with information/help snippets and loads details
		//If file file cannot be found, set class variable to false, so buttons wont be displayed
		private void initHelpTextFile() throws IOException
		{		
			File helpTextFile = new File(this.helpTextsFileString);			
			
			if (!helpTextFile.exists() || helpTextFile == null)
			{					
				this.helpTextModel = new HelpTextModel();
				savePropertiesFile(this.helpTextModel.getProperties(), helpTextsFileString);
				
			} else
			{				
				this.helpTextModel = new HelpTextModel();
				InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(helpTextFile), StandardCharsets.UTF_8);	
				Properties properties = new Properties();			
				properties.load(inputStreamReader);						
				this.helpTextModel.setProperties(properties);
			} 	
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
