package logic_vivi;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.ByteOrderMark;
import org.apache.commons.io.input.BOMInputStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import models_vivi.ApiMultiCallModel;

//handles imports and exports for multipApiCall: Excel/csv/tsv import and Excel export
public class ImportExportFileHandler {
	
	//global import method, will handle excel and csv/tsv
	//will create and return a list (lines) of a List (columns) 
	//the subsequent mechanism all handle that specific file.
	public static List<List<String>> getFileStringListFromFile(File fileChosen) throws IOException
		{
				List<List<String>> rows = new ArrayList<List<String>>();		
				PropertyFileHandler propertyFilehandler = new PropertyFileHandler();
				InputStream inputStream = new FileInputStream(fileChosen.getAbsolutePath());
								
				if (propertyFilehandler.propertyFileModel.getImportFileTypeComboBox().equals("Tab/Komma getrennte Datei (CSV/TSV)"))					
				{										
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
						 
					
				} else if (propertyFilehandler.propertyFileModel.getImportFileTypeComboBox().equals("Web of Science Publication Export"))					
					{										
						String defaultEncoding = "UTF-8";						
					
						BOMInputStream bOMInputStream = new BOMInputStream(inputStream);
					    ByteOrderMark bom = bOMInputStream.getBOM();
					    String charsetName = bom == null ? defaultEncoding : bom.getCharsetName();
					    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new BufferedInputStream(bOMInputStream), charsetName));
					    String line;
						  while ((line = bufferedReader.readLine()) != null) {	
							  	String separator = "\t";							 						  
								  String[] array = line.split(separator);
								  ArrayList<String> columns = new ArrayList<String>();
								  for (String string : array)
								  {
									  columns.add(string);
								  }
								  rows.add(columns);
						  }
						  	inputStream.close();
						  	bufferedReader.close();	
					
				}else if (propertyFilehandler.propertyFileModel.getImportFileTypeComboBox().contains("Excel-Datei"))
				{				
					
					XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
					XSSFSheet sheet = workbook.getSheetAt(0);
					Iterator<Row> rowIterator = sheet.iterator();
					
					 while (rowIterator.hasNext()) {
				            Row row = rowIterator.next();
				            // Get iterator to all cells of current row
				            Iterator<Cell> cellIterator = row.cellIterator();
				            ArrayList<String> columns = new ArrayList<String>();
				            while (cellIterator.hasNext()) {
				                Cell cell = cellIterator.next();
				            
				         // Change to getCellType() if using POI 4.x
			                CellType cellType = cell.getCellType();
			 
			                switch (cellType) {
			                case _NONE:			                    
			                	columns.add("");
			                case BOOLEAN:
			                	columns.add(Boolean.toString(cell.getBooleanCellValue()));
			                    break;
			                case BLANK:
			                	columns.add("");
			                    break;
			                case FORMULA:
			                    // Formula
			                	columns.add(cell.getCellFormula());   
			                FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
			                    // Print out value evaluated by formula
			                	columns.add(Double.toString(evaluator.evaluate(cell).getNumberValue()));
			                    break;
			                case NUMERIC:
			                	columns.add(Double.toString(cell.getNumericCellValue()));			                 
			                    break;
			                case STRING:
			                	columns.add(cell.getStringCellValue());
			                
			                    break;
			                case ERROR:
			                	columns.add("Error converting Excel column");			            
			                    break;
			                }
				            }
				     rows.add(new ArrayList<String>(columns));	
				     columns.clear();
					}
					 workbook.close();
				}
				return rows;
			}
			
			//just display first to lines so user can make sure the data was imported correctly
			//function takes the list of lines with the lists of columns and turns it into a string to return and display
			public static String getFirstTwoLinesFromFileImportAndAnalyzeImport(List<List<String>> importedFileRowsStrings)
			{	
				PropertyFileHandler propertyFileHandler = new PropertyFileHandler();
				String returnString = new String();				
				
				if (importedFileRowsStrings == null || importedFileRowsStrings.size() < 2 || importedFileRowsStrings.get(0).size() == 0)
				{
					return "File could not be read ore did not contain any values.";
				}
				
				returnString = "Ausgewählter Importmodus: " + propertyFileHandler.propertyFileModel.getImportFileTypeComboBox() + "\n";
				
				if(!propertyFileHandler.propertyFileModel.getImportFileTypeComboBox().equals("Web of Science Publication Export"))
				{
					if (propertyFileHandler.propertyFileModel.getImportFileTypeTitleColumn().isBlank() &&  propertyFileHandler.propertyFileModel.getImportFileTypeISSNColumn().isBlank() && propertyFileHandler.propertyFileModel.getImportFileTypeeISSNColumn().isBlank())
					{
						returnString += "Keine Spaltenbezeichnung angegeben: Die importierten Daten werden nach ISSN durchsucht.\n";
					} else
					{
						returnString += "Spaltenbezeichnung Titel: '"+ propertyFileHandler.propertyFileModel.getImportFileTypeTitleColumn() +"' Spaltenbezeichnung ISSN: '"+ propertyFileHandler.propertyFileModel.getImportFileTypeISSNColumn() +"' Spaltenbezeichnung eISSN: '"+ propertyFileHandler.propertyFileModel.getImportFileTypeeISSNColumn()+"'" + "\n\n";
					}	
				} 	
				
				int lineCount = importedFileRowsStrings.size();
				int columnCount = importedFileRowsStrings.get(0).size();
				
				returnString += "Es wurden " + lineCount + " Zeilen importiert " + "\n";	
				returnString += "Es wurden " + columnCount + " Spalten erkannt " + "\n";
				returnString += "Ausgabe der ersten beiden Zeilen (Überschrift und erste Datenzeile) als Beispiel:" + "\n\n";
				
				for (int counter = 0; counter < importedFileRowsStrings.get(0).size(); counter ++)
				{			
					if (importedFileRowsStrings.get(1).size() > counter)
					{
						returnString += lineFormatter(ConvenienceTools.wos_tagMatcher(importedFileRowsStrings.get(0).get(counter)), importedFileRowsStrings.get(1).get(counter));	
					}							
				}
				return returnString;
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
	
			//put all the data fetched into an excel list and save it		
			public static  void exportExcelList(ArrayList<ApiMultiCallModel> apiMultiCallModels, String filepath) throws FileNotFoundException, IOException
			{
				//create workbook
					XSSFWorkbook workbook = new XSSFWorkbook();
				//create sheet
			        XSSFSheet sheet = workbook.createSheet("Api Results");  	 
			    
			        int rowCount = -1;	
			      
			        PropertyFileHandler propertyFileHandler = new PropertyFileHandler();
					if (propertyFileHandler.propertyFileModel.getMultiApiCallExportConditionsInSeparateColumns())
					{
						for (ApiMultiCallModel apiMultiCallModel : apiMultiCallModels) {
				        	
					        
				            Row row = sheet.createRow(++rowCount);
				             
				            int columnCount = -1;
				            
				            Cell cellIssn = row.createCell(++columnCount);
				            cellIssn.setCellValue((String) apiMultiCallModel.issn);
				            
				            Cell celleIssn = row.createCell(++columnCount);
				            celleIssn.setCellValue((String) apiMultiCallModel.eIssn);
				            
				            Cell cellTitle = row.createCell(++columnCount);
				            cellTitle.setCellValue((String) apiMultiCallModel.title);
				            
				            Cell cellPublisher = row.createCell(++columnCount);
				            cellPublisher.setCellValue((String) apiMultiCallModel.publisher);
				            
				            Cell cellPublisherType = row.createCell(++columnCount);
				            cellPublisherType.setCellValue((String) apiMultiCallModel.publisherType);
				            
				            Cell cellLastUpdated = row.createCell(++columnCount);
				            cellLastUpdated.setCellValue((String) apiMultiCallModel.lastUpdated);
				            
				            Cell cellSubmittedPrerequisites = row.createCell(++columnCount);
				            cellSubmittedPrerequisites.setCellValue((String) apiMultiCallModel.submittedPrerequisites);
				            Cell cellSubmittedCopyRight = row.createCell(++columnCount);
				            cellSubmittedCopyRight.setCellValue((String) apiMultiCallModel.submittedCopyRightOwner);
				            Cell cellSubmittedLicense = row.createCell(++columnCount);
				            cellSubmittedLicense.setCellValue((String) apiMultiCallModel.submittedLicense);
				            Cell cellSubmittedOAFees = row.createCell(++columnCount);				            
				            cellSubmittedOAFees.setCellValue((String) apiMultiCallModel.submittedAdditionalOAFees);
				            Cell cellSubmittedRepositories = row.createCell(++columnCount);
				            cellSubmittedRepositories.setCellValue((String) apiMultiCallModel.submittedRepository);
				            Cell cellSubmittedEmbargo = row.createCell(++columnCount);
				            cellSubmittedEmbargo.setCellValue((String) apiMultiCallModel.submittedEmbargo);
				            Cell cellSubmittedCondition = row.createCell(++columnCount);
				            cellSubmittedCondition.setCellValue((String) apiMultiCallModel.submittedConditions);
				            
				            Cell cellAcceptedPrerequisites = row.createCell(++columnCount);
				            cellAcceptedPrerequisites.setCellValue((String) apiMultiCallModel.acceptedPrerequisites);
				            Cell cellAcceptedCopyRight = row.createCell(++columnCount);
				            cellAcceptedCopyRight.setCellValue((String) apiMultiCallModel.acceptedCopyRightOwner);
				            Cell cellAcceptedLicense = row.createCell(++columnCount);
				            cellAcceptedLicense.setCellValue((String) apiMultiCallModel.acceptedLicense);
				            Cell cellAcceptedOAFees = row.createCell(++columnCount);
				            cellAcceptedOAFees.setCellValue((String) apiMultiCallModel.acceptedAdditionalOAFees);
				            Cell cellAcceptedRepositories = row.createCell(++columnCount);
				            cellAcceptedRepositories.setCellValue((String) apiMultiCallModel.acceptedRepository);
				            Cell cellAcceptedEmbargo = row.createCell(++columnCount);
				            cellAcceptedEmbargo.setCellValue((String) apiMultiCallModel.acceptedEmbargo);
				            Cell cellAcceptedCondition = row.createCell(++columnCount);
				            cellAcceptedCondition.setCellValue((String) apiMultiCallModel.acceptedConditions);
				            
				            Cell CellPublishedPrerequisites = row.createCell(++columnCount);
				            CellPublishedPrerequisites.setCellValue((String) apiMultiCallModel.publishedPrerequisites);			            
				            Cell CellPublishedCopyRight = row.createCell(++columnCount);
				            CellPublishedCopyRight.setCellValue((String) apiMultiCallModel.publishedCopyRightOwner);
				            Cell cellPublishedLicense = row.createCell(++columnCount);
				            cellPublishedLicense.setCellValue((String) apiMultiCallModel.publishedLicense);
				            Cell CellPublishedOAFees = row.createCell(++columnCount);
				            CellPublishedOAFees.setCellValue((String) apiMultiCallModel.publishedAdditionalOAFees);
				            Cell CellPublishedRepositories = row.createCell(++columnCount);
				            CellPublishedRepositories.setCellValue((String) apiMultiCallModel.publishedRepository); 
				            Cell CellPublishedEmbargo = row.createCell(++columnCount);
				            CellPublishedEmbargo.setCellValue((String) apiMultiCallModel.publishedEmbargo); 
				            Cell CellPublishedCondition = row.createCell(++columnCount);
				            CellPublishedCondition.setCellValue((String) apiMultiCallModel.publishedConditions); 
				            
				            Cell Cellurls = row.createCell(++columnCount);
				            Cellurls.setCellValue((String) apiMultiCallModel.urls); 
				            
				            for(String randomInfo: apiMultiCallModel.randomInfo)
				            {
				            	Cell cell = row.createCell(++columnCount);
				  	            cell.setCellValue((String) randomInfo);
				            }  
				        }
					} else
					{
						//loop through all bulk models, put information into excel file
				        for (ApiMultiCallModel apiMultiCallModel : apiMultiCallModels) {
				        	
				        
				            Row row = sheet.createRow(++rowCount);
				             
				            int columnCount = -1;
				            
				            Cell cellIssn = row.createCell(++columnCount);
				            cellIssn.setCellValue((String) apiMultiCallModel.issn);
				            
				            Cell celleIssn = row.createCell(++columnCount);
				            celleIssn.setCellValue((String) apiMultiCallModel.eIssn);
				            
				            Cell cellTitle = row.createCell(++columnCount);
				            cellTitle.setCellValue((String) apiMultiCallModel.title);
				            
				            Cell cellPublisher = row.createCell(++columnCount);
				            cellPublisher.setCellValue((String) apiMultiCallModel.publisher);
				            
				            Cell cellPublisherType = row.createCell(++columnCount);
				            cellPublisherType.setCellValue((String) apiMultiCallModel.publisherType);
				            
				            Cell cellLastUpdated = row.createCell(++columnCount);
				            cellLastUpdated.setCellValue((String) apiMultiCallModel.lastUpdated);
				            
				            Cell cellSubmitted = row.createCell(++columnCount);
				            cellSubmitted.setCellValue((String) apiMultiCallModel.submitted);
				            
				            Cell cellAccepted = row.createCell(++columnCount);
				            cellAccepted.setCellValue((String) apiMultiCallModel.accepted);
				            
				            Cell CellPublished = row.createCell(++columnCount);
				            CellPublished.setCellValue((String) apiMultiCallModel.published);  
				            
				            Cell Cellurls = row.createCell(++columnCount);
				            Cellurls.setCellValue((String) apiMultiCallModel.urls); 
				            
				            for(String randomInfo: apiMultiCallModel.randomInfo)
				            {
				            	Cell cell = row.createCell(++columnCount);
				  	            cell.setCellValue((String) randomInfo);
				            }  
				        }
					}
			        
			        
			        //save file to location
			        	FileOutputStream outputStream = new FileOutputStream(filepath); 
				            workbook.write(outputStream);
				            workbook.close();	 
			}	

}
