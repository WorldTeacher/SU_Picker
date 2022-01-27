package logic;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.ByteOrderMark;
import org.apache.commons.io.input.BOMInputStream;

import javafx.stage.FileChooser.ExtensionFilter;
import var.Constants;
import javafx.stage.Stage;

//handles imports and exports for multipApiCall: Excel/csv/tsv import and Excel export
public class ImportExportFileHandler {
	
	public static List<List<String>> importCsv() throws IOException
	{		
		return(getFileStringListFromFile(getFile()));
	}	
	
	public static void exportFile(String contentString, String location)
	{
		
		PropertyFileHandler propertyFileHandler = PropertyFileHandler.getInstance();
		  try{			  
			  
			  /*
				javafx.stage.FileChooser fileChooser = new javafx.stage.FileChooser();
				fileChooser.getExtensionFilters().addAll(new ExtensionFilter("All Files", "*.*"));
				File userSelection = fileChooser.showSaveDialog(new Stage());			  
			    */			  
			  
		FileWriter fstream = new FileWriter(location + getTimeStamp() + "_ReiheA.txt");
		
        BufferedWriter out = new BufferedWriter(fstream);
        out.write(contentString);
        //Close the output stream
        out.close();
    	}catch (Exception e){//Catch exception if any
    		System.err.println("Error: " + e.getMessage());
    	}
	}
	
	public void openExportFileFolder() throws IOException
	{
		PropertyFileHandler propertyFileHandler = PropertyFileHandler.getInstance();
		Runtime.getRuntime().exec("explorer " + propertyFileHandler.propertyFileModel.get_settings_ExportFileFolder());
	}
	
	public void openSaveFileFolder() throws IOException
	{
		PropertyFileHandler propertyFileHandler = PropertyFileHandler.getInstance();
		Runtime.getRuntime().exec("explorer " + propertyFileHandler.propertyFileModel.get_settings_SaveFileFolder());
	}
	
	private static String getTimeStamp()
	{
		SimpleDateFormat formatter= new SimpleDateFormat("yyyyMMdd_HHmmss");
		String date = formatter.format(System.currentTimeMillis());
		return date;
	}	
	
	private static File getFile()
	{
		//text: User input; 
		File fileChosen = null;
		
			javafx.stage.FileChooser fileChooser = new javafx.stage.FileChooser();
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter("All Files", "*.*"));
			fileChosen = fileChooser.showOpenDialog(new Stage());
		
		
		if (fileChosen == null)
		{		
			System.out.println("Vorgang abgebrochen oder Datei nicht gefunden.");
			return null;
		}
		
		return fileChosen;
	}
	
	//global import method, will handle csv/tsv
	//will create and return a list (lines) of a List (columns) 
	//the subsequent mechanism all handle that specific file.
	private static List<List<String>> getFileStringListFromFile(File fileChosen) throws IOException
		{
				List<List<String>> rows = new ArrayList<List<String>>();		
				InputStream inputStream = new FileInputStream(fileChosen.getAbsolutePath());
													
					String defaultEncoding = "UTF-8";	
					String separator = "";
				
					BOMInputStream bOMInputStream = new BOMInputStream(inputStream);
				    ByteOrderMark bom = bOMInputStream.getBOM();
				    String charsetName = bom == null ? defaultEncoding : bom.getCharsetName();
				    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new BufferedInputStream(bOMInputStream), charsetName));
				    String line;
					  while ((line = bufferedReader.readLine()) != null) {	
						  
						  int commaCounter = 0;
						  int tabCounter = 0;
						  if (separator.isEmpty())
						  {
							  //count Commas and Tabs in the first line (headings) and use that as a separator
							  //using comma as separator is a bit weird: Authors often use commas as separators for their names
							  //might still be useful if people only want to enrich very reduced file (issn/titel/...)
							  for (char character : line.toCharArray())
							  {
								  if (character == ',')
								  {
									  commaCounter ++; 
								  } else if (character == '\t')
								  {
									  tabCounter ++; 
								  }
							  } 
							  separator = (commaCounter > tabCounter) ? "," : "\t";
						  }
						 
						  if (separator.isEmpty())
						  {
							  //no separator? maybe its a file with only one issn per line...
							  	ArrayList<String> columns = new ArrayList<String>();							 
								 columns.add(line.toString());	
								 rows.add(columns);
						  } else
						  {
							  
							  String[] array = line.split(separator);
							  ArrayList<String> columns = new ArrayList<String>();
							  for (String string : array)
							  {
								  columns.add(string);
							  }
							  rows.add(columns);		
							  }
						  	
						  }
					  	inputStream.close();
					  	bufferedReader.close();						 
					
				
				return rows;
			}
			
			
			//lineformatter: UI uses monospace font.
			//so we need to count the chars in order to display the data properly
			// i.e.:
			//column A:    Value 1
			//col. B:	   Value 2			
			public static String lineFormatter(String heading, String data)
			{				
				if (heading.toCharArray().length > 25)
				{
					heading = heading.substring(0,20);
					heading += " (..):   ";
				} else
				{
					heading += ":";
					int counter = 29 - heading.toCharArray().length;
					for (;counter > 0; counter --)
					{
						heading += " ";
					}
					
				}
				return heading + data + "\n";
				
			}

	        
 
			

}