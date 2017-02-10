package mob_insurance.io;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.support.ui.Duration;
import org.openqa.selenium.support.ui.Sleeper;

import mob_insurance.common.teststep.InsuranceField;


public class TestData {
   

	Map<Integer,Map<String,String>> mapTestData=null;
	
	public Map<Integer,Map<String,String>> readTestData(String fileName){
		File objXLSObjectDD=null;
		FileInputStream fObjDB =null;
		mapTestData=new TreeMap<Integer,Map<String,String>>();
		Workbook wBook=null;
		try{
			objXLSObjectDD=new File("src//test//resources//TestCases//"+fileName);
			fObjDB = new FileInputStream(objXLSObjectDD);
			
			wBook = WorkbookFactory.create(fObjDB);
			Sheet sheet=wBook.getSheetAt(0);
			
			Row hRow=sheet.getRow(0);
			Row cRow=null;
			for(int r=1;r<=sheet.getLastRowNum();r++){
				Map<String,String> mapTestDataRow=new TreeMap<String,String>(String.CASE_INSENSITIVE_ORDER);
				cRow=sheet.getRow(r);
				if(cRow==null){continue;}
				for(int i=0;i<hRow.getLastCellNum();i++){
					String headerName=hRow.getCell(i).getStringCellValue();
					String data=cRow.getCell(i).getStringCellValue();
					mapTestDataRow.put(headerName, data);
				}				
				mapTestData.put(r, mapTestDataRow);
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
		
		return mapTestData;
		
	}

    public Map<String,InsuranceField> readInsuranceTypeTestData(String testDataReference, String insType){
        TestDataReference objTDRef=getTestDataReference(testDataReference);
    	
    	File objXLSObjectDD=null;
		FileInputStream fObjDB =null;
		Workbook wBook=null;
		Map<String,InsuranceField> mapTestDataRow=new TreeMap<String,InsuranceField>(String.CASE_INSENSITIVE_ORDER);
		try{
			objXLSObjectDD=new File("src//test//resources//TestCases//"+objTDRef.fileName);
			fObjDB = new FileInputStream(objXLSObjectDD);
			
			wBook = WorkbookFactory.create(fObjDB);
			Sheet sheet=wBook.getSheet(objTDRef.sheetName);
			
			Row cRow=null;
			
			for(int r=1;r<=sheet.getLastRowNum();r++){	
				CellReference cellRef=new CellReference(objTDRef.columnLabel.toUpperCase()+r);
				cRow=sheet.getRow(cellRef.getRow());
				if(cRow==null){continue;}
				String insuranceType=cRow.getCell(0).getStringCellValue();
				if(!insuranceType.trim().equalsIgnoreCase(insType) || cRow.getCell(cellRef.getCol()) == null || cRow.getCell(cellRef.getCol()).getCellType() == Cell.CELL_TYPE_BLANK){
					continue;
				}
				Cell tabCell=cRow.getCell(1);
				if(tabCell != null && tabCell.getStringCellValue().length() > 0){
					System.out.println("Info: "+cRow.getCell(2).getStringCellValue()+" field ignored while reading it from Test Data file.");
					continue;
				}
				
				InsuranceField objInsuranceField=new InsuranceField();
				
				objInsuranceField.insuranceType=cRow.getCell(0).getStringCellValue();
				objInsuranceField.fieldName=cRow.getCell(2).getStringCellValue();
				String insuranceField=cRow.getCell(2).getStringCellValue();	
				String fieldValue="";
				if(cRow.getCell(cellRef.getCol()).getCellType() == Cell.CELL_TYPE_STRING){
					fieldValue= cRow.getCell(cellRef.getCol()).getStringCellValue();
				}else if(cRow.getCell(cellRef.getCol()).getCellType() == Cell.CELL_TYPE_NUMERIC && DateUtil.isCellDateFormatted(cRow.getCell(cellRef.getCol()))){
					SimpleDateFormat df = new SimpleDateFormat("MM/dd/yy");
					fieldValue=df.format(cRow.getCell(cellRef.getCol()).getDateCellValue());
				}else if(cRow.getCell(cellRef.getCol()).getCellType() == Cell.CELL_TYPE_NUMERIC){
					fieldValue=String.valueOf(cRow.getCell(cellRef.getCol()).getNumericCellValue());
				}else if(cRow.getCell(cellRef.getCol()).getCellType() == Cell.CELL_TYPE_BOOLEAN){
					fieldValue=String.valueOf(cRow.getCell(cellRef.getCol()).getBooleanCellValue());
				}
				objInsuranceField.testDataValue=fieldValue;
				objInsuranceField.fieldTitle=cRow.getCell(3).getStringCellValue();
				objInsuranceField.fieldType=cRow.getCell(4).getStringCellValue();
				
				//Set dependOn field
				if(objInsuranceField.fieldTitle.contains("->")){
					objInsuranceField.dependOn=objInsuranceField.fieldTitle.substring(0,objInsuranceField.fieldTitle.indexOf("->"));
					objInsuranceField.fieldTitle=objInsuranceField.fieldTitle.substring(objInsuranceField.fieldTitle.indexOf("->")+2);
					
				}
				
				mapTestDataRow.put(insuranceField, objInsuranceField);
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
		return mapTestDataRow;
    }
    
    private TestDataReference getTestDataReference(String testDataReference){
    	TestDataReference objTDRef=new TestDataReference();
		Pattern pattern = Pattern.compile("\\[(.*?)\\]");
		Matcher match=pattern.matcher(testDataReference);
		while(match.find() && objTDRef.fileName.length() == 0){
			objTDRef.fileName=match.group(1).trim();	
		}
		
		objTDRef.columnLabel=testDataReference.substring(testDataReference.lastIndexOf(".")+1);
		
		if(objTDRef.fileName.length() == 0){
			objTDRef.fileName=ManageConfig.getTestDataFileName("TestData.Contract.Create").trim();
			objTDRef.sheetName=testDataReference.substring(1, testDataReference.lastIndexOf(".")).trim();
			if(objTDRef.sheetName.startsWith("'") && objTDRef.sheetName.endsWith("'")){
				objTDRef.sheetName=objTDRef.sheetName.replaceFirst("'", "");
				objTDRef.sheetName=objTDRef.sheetName.substring(0,objTDRef.sheetName.lastIndexOf("'"));
			}
		}else{			
			objTDRef.sheetName=testDataReference.substring(testDataReference.indexOf("]")+1, testDataReference.lastIndexOf(".")).trim();
			if(objTDRef.sheetName.endsWith("'")){
				objTDRef.sheetName=objTDRef.sheetName.substring(0, objTDRef.sheetName.length()-1);
			}
		}
    	
		return objTDRef;
    }
    
    private class TestDataReference{
    	public String fileName="";
    	public String sheetName="";
    	public String columnLabel="";
    }
	
}
