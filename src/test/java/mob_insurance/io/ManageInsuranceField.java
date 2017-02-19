package mob_insurance.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;


public class ManageInsuranceField {


	String fileName="";
	String columnInsuranceType,columnFieldLabel,columnElementType,columnOptions,columnCondition,columnElementName,columnTabName,columnSequence;
	Properties prInsuranceFieldLocator=null;
	
	public ManageInsuranceField(){
		try{
		    columnInsuranceType=ManageConfig.getValue("InsuranceField.HeaderName.insuranceType");
			columnFieldLabel=ManageConfig.getValue("InsuranceField.HeaderName.fieldLabel");
			columnElementType=ManageConfig.getValue("InsuranceField.HeaderName.elementType");
			columnOptions=ManageConfig.getValue("InsuranceField.HeaderName.comboboxOption");
			columnCondition=ManageConfig.getValue("InsuranceField.HeaderName.condition");
			columnElementName=ManageConfig.getValue("InsuranceField.HeaderName.elementName");
			columnTabName=ManageConfig.getValue("InsuranceField.HeaderName.tab");
			columnSequence=ManageConfig.getValue("InsuranceField.HeaderName.FieldSequenceNumber");
			prInsuranceFieldLocator=new Properties();
			
		}catch(Exception e){
			throw e;
		}
	}
	
	public void generateInsuranceFieldTestDataFile(String filePath){
		Map<String,Map<String,FieldInfo>> mapInsuranceFieldInfo=readInsuranceFieldFile(filePath);
		generateInsuranceFieldInfo(mapInsuranceFieldInfo);
	} 
	
	private Map<String,Map<String,FieldInfo>> readInsuranceFieldFile(String filePath){
		File objXLSObjectDD=null;
		FileInputStream fObjDB =null;
		Map<String,Map<String,FieldInfo>> mapInsuranceFieldInfo=new TreeMap<String,Map<String,FieldInfo>>(String.CASE_INSENSITIVE_ORDER);
		Map<String,Integer> mapHeaderInfo=new TreeMap<String,Integer>(String.CASE_INSENSITIVE_ORDER);
		Map<String,FieldInfo> mapFieldInfo=null;
		Workbook wBook=null;
		try{
			objXLSObjectDD=new File(filePath);
			fObjDB = new FileInputStream(objXLSObjectDD);
			
			wBook = WorkbookFactory.create(fObjDB);
			Sheet sheet=wBook.getSheetAt(0);
			
			Row hRow=sheet.getRow(0);
			for(int r=0;r<hRow.getLastCellNum();r++){
				Cell cCell=hRow.getCell(r);
				if(cCell==null || cCell.getCellType() == Cell.CELL_TYPE_BLANK){
					continue;
				}
				mapHeaderInfo.put(cCell.getStringCellValue(),Integer.valueOf(r));				
			}			
			Row cRow=null;
			for(int r=1;r<=sheet.getLastRowNum();r++){
				cRow=sheet.getRow(r);
				if(cRow==null){continue;}
				String insuranceType=getCellValue(cRow,mapHeaderInfo.get(columnInsuranceType));
				String fieldLabel=getCellValue(cRow,mapHeaderInfo.get(columnFieldLabel));
				String elementType=getCellValue(cRow,mapHeaderInfo.get(columnElementType));
				String options=getCellValue(cRow,mapHeaderInfo.get(columnOptions));
				String condition=getCellValue(cRow,mapHeaderInfo.get(columnCondition));
				String elementName=getCellValue(cRow,mapHeaderInfo.get(columnElementName));
				String tabName=getCellValue(cRow,mapHeaderInfo.get(columnTabName));
				String sequence=getCellValue(cRow,mapHeaderInfo.get(columnSequence));
				
				//Skip processing current row if Insurance Type or Element Name / Type / Label is blank				
				if(insuranceType.isEmpty() || elementName.isEmpty() || elementType.isEmpty() || fieldLabel.isEmpty() ){
					System.out.println("Insurance Type or Element Name or Type Or Field Label is blank for "+r+" row. Hence skipped this row.");
					continue;
				}

				FieldInfo objFieldInfo=new FieldInfo();
				objFieldInfo.fieldName=elementName;
				objFieldInfo.fieldType=elementType;
				objFieldInfo.fieldTitle=fieldLabel;
				objFieldInfo.fieldCheck=condition;
				objFieldInfo.comboBox_Value=options;
				if(tabName.toUpperCase().endsWith("_CLIENT") ||tabName.toUpperCase().endsWith("_ORDER") || tabName.toUpperCase().endsWith("ORDER_REQUEST") || tabName.toUpperCase().endsWith("ORDER_PROT") || tabName.toUpperCase().endsWith("_PRODUCT")){
					objFieldInfo.tab=tabName;	
				}
				objFieldInfo.sequenceNumber=Double.valueOf(sequence).intValue();
				
				if(mapInsuranceFieldInfo.get(insuranceType) == null){
				   mapFieldInfo=new TreeMap<String,FieldInfo>(String.CASE_INSENSITIVE_ORDER);
				}else{
				   mapFieldInfo=mapInsuranceFieldInfo.get(insuranceType);
				}
				mapFieldInfo.put(tabName+"."+elementName,objFieldInfo);
				mapInsuranceFieldInfo.put(insuranceType, mapFieldInfo);
				
			}
			
		}catch(Exception e){
			System.out.println("Error: Exception occured while reading TestData file. Values should be specified in all test data column of excel. Below is Stacktrace: "+e);
		}finally{
			try{
				if(wBook != null){
					wBook.close();
					wBook=null;
				}
			}catch(Exception e){
				System.out.println("Error: Exception occured while closing TestData file. Below is Stacktrace: "+e);
			}
		}
		return mapInsuranceFieldInfo;
		
		
	}
	
	private String getCellValue(Row row,int cellNumber){
		Cell objCell=row.getCell(cellNumber);
		String cellValue="";
		try{
			if(objCell.getCellType() == Cell.CELL_TYPE_STRING)
			  cellValue=objCell.getStringCellValue();
			else if(objCell.getCellType() == Cell.CELL_TYPE_NUMERIC)
			  cellValue=String.valueOf(objCell.getNumericCellValue());
		}catch(Exception e){
			cellValue="";
		}
		
		return cellValue;
	}
	
	public void generateInsuranceFieldInfo(Map<String,Map<String,FieldInfo>> mapInsuranceFieldInfo){
		//Method includes logic to generate excel sheet containing all insurance fields
		Iterator<String> itrInsurnaceType=mapInsuranceFieldInfo.keySet().iterator();
		
		Workbook wBook=new HSSFWorkbook();
		Sheet sheet=wBook.createSheet(); 
		wBook.setSheetName(wBook.getSheetIndex(sheet),"Insurance Fields");
		//Create header row
		Row hRow=sheet.createRow(0);
		hRow.createCell(0).setCellValue("Sequence");
		hRow.createCell(1).setCellValue("Insurance Type");
		hRow.createCell(2).setCellValue("Tab");
		hRow.createCell(3).setCellValue("Element Name");
		hRow.createCell(4).setCellValue("Field Title");
		hRow.createCell(5).setCellValue("Field Type");
		hRow.createCell(6).setCellValue("TestData-1");
		
		int rowIndex=1;
		while(itrInsurnaceType.hasNext()){
			String keyInsuranceType=itrInsurnaceType.next();
			Map<String,FieldInfo> mapFieldInfo=mapInsuranceFieldInfo.get(keyInsuranceType);
			Iterator<String> itrElementName=mapFieldInfo.keySet().iterator();
			while(itrElementName.hasNext()){
				Row row=sheet.createRow(rowIndex);
				String keyTabElementName=itrElementName.next();
    			FieldInfo objFieldInfo=mapFieldInfo.get(keyTabElementName);
				String fieldName=getFieldName(mapFieldInfo,objFieldInfo);
				//Needs to write: Logic to prepare row includes values for Insurance Type / Field Name / Element Type
				row.createCell(0).setCellValue(objFieldInfo.sequenceNumber);
				row.createCell(1).setCellValue(keyInsuranceType);
				row.createCell(2).setCellValue(objFieldInfo.tab);
				row.createCell(3).setCellValue(objFieldInfo.fieldName);
				row.createCell(4).setCellValue(fieldName);
				row.createCell(5).setCellValue(objFieldInfo.fieldType);
				
				appendInsuranceFieldLocatorInProperty(keyInsuranceType+"."+objFieldInfo.fieldName,objFieldInfo.fieldType,objFieldInfo.tab,fieldName,objFieldInfo.fieldName);
				rowIndex++;
			}
			
		}
		
		try{
			
			System.out.println("Info: Writing insurnace fields in src\\test\\resources\\Outputfiles\\ContractCategoryFieldInfo.xls");
//			Write Insurance fields in Excel sheet
			FileOutputStream fileOutputStream=new FileOutputStream(new File("src\\test\\resources\\Outputfiles\\ContractCategoryFieldInfo.xls"));
			wBook.write(fileOutputStream);
			fileOutputStream.close();
			wBook.close();
			
//			Write property file
			System.out.println("Info: Writing insurnace fields locator in src\\test\\resources\\Outputfiles\\InsuranceFieldLocator.properties");
			fileOutputStream=new FileOutputStream(new File("src\\test\\resources\\Outputfiles\\InsuranceFieldLocator.properties"));
			prInsuranceFieldLocator.store(fileOutputStream, "Place all these properties between Start/End: Insurance Field tag inside locator_en/_dh.properties files.");
			fileOutputStream.close();
			
		}catch(Exception e){
			
		}
		
	} 
	
	private void appendInsuranceFieldLocatorInProperty(String key,String type,String tabName, String... value){
	
//	  Exclude field if tab name is blank	
//	  if(!tabName.equals("")){
//		  return;
//	  }
		
	  // value[0]: fieldTitle || value[1]: fieldName	
	  String prValue="";
	  String[] locator;
	  switch(type.toLowerCase()){
	  	  case "combobox":
	  		              
	  		              locator=ManageConfig.getLocator("Insurancetype.Field.Base.Locator.Dropdown");
	  		              
	  		              locator[1]=locator[1].replaceAll("@fieldLabel", getFieldLabel(value[0])).replaceAll("@elementName", value[1]);
	  		              prValue=locator[0]+"("+locator[1]+")";
	  		              break;
	  		
	  	  case "textarea":
	  	  case "datefield":
	  	  case "textfield":
	  		              locator=ManageConfig.getLocator("Insurancetype.Field.Base.Locator.Input");
	  		              locator[1]=locator[1].replaceAll("@fieldLabel", getFieldLabel(value[0])).replaceAll("@elementName", value[1]);
	  		              prValue=locator[0]+"("+locator[1]+")";
		    		      break;
	  	  case "checkbox":
	  		              locator=ManageConfig.getLocator("Insurancetype.Field.Base.Locator.CheckBox");
	  		              locator[1]=locator[1].replaceAll("@fieldLabel", getFieldLabel(value[0]));
	  		              prValue=locator[0]+"("+locator[1]+")";
		    		      break;	  		              
	  	  case "radiogroup":
	  		              locator=ManageConfig.getLocator("Insurancetype.Field.Base.Locator.RadioButton");
	  		              locator[1]=locator[1].replaceAll("@fieldLabel",getFieldLabel(value[0]));	  		              
	  		              prValue=locator[0]+"("+locator[1]+")";
		    		      break;
	  	  default:
				          System.out.println("Error: Method supports only combobox/textarea/datefield/textfield/checkbox/radiogroup type insurance field.");
	  		              return;
  	  }
	  
	  prInsuranceFieldLocator.put(key.toUpperCase(), prValue);
	  prInsuranceFieldLocator.put(key.toUpperCase()+"_DE", prValue);
		
	} 

	private String getFieldLabel(String fieldName){
		if(fieldName.contains("->")){
			return fieldName.substring(fieldName.indexOf("->")+2).trim(); 
        }
	
	    return fieldName;	
	} 
	
	private String getFieldName(Map<String,FieldInfo> mapFieldInfo,FieldInfo objFieldInfo){
		String fieldName="";
		if(objFieldInfo.fieldCheck.isEmpty())
		   return objFieldInfo.fieldTitle;
		
		Pattern pattern = Pattern.compile("\"Show\":\\{\"field\":\"(.*?)\",");
		Matcher matcher = pattern.matcher(objFieldInfo.fieldCheck); 
		String dependentFieldName="";
		if(matcher.find()) 
			dependentFieldName=matcher.group(1);

		if(dependentFieldName == null || dependentFieldName.isEmpty())
			return objFieldInfo.fieldTitle;

//		if(mapFieldInfo.containsKey(dependentFieldName)){
		 Iterator<String> itrFieldMap=mapFieldInfo.keySet().iterator();
		 String fieldTitle="";
		 while(itrFieldMap.hasNext()){
		   String key=itrFieldMap.next();
		   FieldInfo tmpObjFieldInfo=mapFieldInfo.get(key);
		   if(tmpObjFieldInfo.fieldName.equalsIgnoreCase(dependentFieldName)){
			  fieldTitle= tmpObjFieldInfo.fieldTitle;
			  break;
		   }
		 }
		if(fieldTitle.isEmpty()){
		   fieldName=objFieldInfo.fieldTitle;
           
		}else{
		   fieldName=fieldTitle+"->"+objFieldInfo.fieldTitle;		
		}
        
		return fieldName;
		
	}
	
	public class FieldInfo{
		String fieldName="";
		String fieldTitle="";
		String fieldType="";
		String comboBox_Value="";
		String fieldCheck="";
		String tab="";
		int sequenceNumber=-1;
	}	
	
	
}
