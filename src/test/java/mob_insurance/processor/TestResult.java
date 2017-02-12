package mob_insurance.processor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TestResult {
	
	private static long startTime;
	private static long endTime;
	private static StringBuffer header = new StringBuffer("");
	private static StringBuffer testCaseResult = new StringBuffer("");
    private static List<String[]> lsTestResult=null;
    
    public static void initializeTestResult(){
    	startTime=System.currentTimeMillis();
        lsTestResult=new ArrayList<String[]>();
        header.append("<HTML><HEAD><Title>Test ran at "+new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date(startTime))+ "</Title><style>table, th, td {border: 1px solid black;border-collapse: collapse;}th, td {padding: 5px;text-align: left;}</style></HEAD><Body><h2 align='center'>Test execution started at <i>"+new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date(startTime))+" and ended at ");	
    }
	
	public TestResult() {
        startTime=System.currentTimeMillis();
        lsTestResult=new ArrayList<String[]>();
        header.append("<HTML><HEAD><Title>Test ran at "+new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date(startTime))+ "</Title><style>table, th, td {border: 1px solid black;border-collapse: collapse;}th, td {padding: 5px;text-align: left;}</style></HEAD><Body><h2 align='center'>Test execution started at <i>"+new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date(startTime))+" and ended at ");        
    }
	
	public static void appendTestResult(){
		testCaseResult.append("<br><br>"); 
		testCaseResult.append("<Table id='AgentSettingTestSuite' border='2' style='width:80%' align='center'> <TR><TD>Test Name: "+(lsTestResult.get(0))[0]+"</TD></TR></TABLE>");
    	testCaseResult.append("<Table id='AgentSettingTestSuite' border='2' style='width:80%' align='center'><TR style='background-color:lightblue'><TH>Test Step</TH><TH>Status</TH><TH>Comment</TH></TR>");
    	
        int stepNumber=1;
                
        for(int i=1;i< lsTestResult.size();i++){
        	String[] testResult=lsTestResult.get(i);
        	testCaseResult.append("<TR>");
        	testCaseResult.append("<TD>Step "+stepNumber+": "+testResult[0]+"</TD>");
        	testCaseResult.append("<TD>"+testResult[1]+"</TD>");
        	testCaseResult.append("<TD>"+testResult[2]+"</TD>");
        	testCaseResult.append("</TR>");
        	stepNumber++;
        }
        
        testCaseResult.append("</Table>");
	}
	
	public void appendTestResult(List<String[]> lsTestResult){
		testCaseResult.append("<br><br>"); 
		testCaseResult.append("<Table id='AgentSettingTestSuite' border='2' style='width:80%' align='center'> <TR><TD>Test Name: "+(lsTestResult.get(0))[0]+"</TD></TR></TABLE>");
    	testCaseResult.append("<Table id='AgentSettingTestSuite' border='2' style='width:80%' align='center'><TR style='background-color:lightblue'><TH>Test Step</TH><TH>Status</TH><TH>Comment</TH></TR>");
    	
        int stepNumber=1;
                
        for(int i=1;i< lsTestResult.size();i++){
        	String[] testResult=lsTestResult.get(i);
        	testCaseResult.append("<TR>");
        	testCaseResult.append("<TD>Step "+stepNumber+": "+testResult[0]+"</TD>");
        	testCaseResult.append("<TD>"+testResult[1]+"</TD>");
        	testCaseResult.append("<TD>"+testResult[2]+"</TD>");
        	testCaseResult.append("</TR>");
        	stepNumber++;
        }
        
        testCaseResult.append("</Table>");
	}
	
	private static void renameFile(String filePath){
    	
    	File eTestResult=new File(filePath+"\\MI_Detailed_Results.html");
    	new File("loc/xyz1.mp3").renameTo(new File("loc/xyz.mp3"));
    	if(eTestResult.exists()){   
    	   String fileName=String.format("MI_Detailed_Results_"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date(System.currentTimeMillis())));
    	   File nFile=new File(filePath+"\\"+fileName);
    	   System.out.println("Renamed Detailed ReportFileName "+fileName);
    	   eTestResult.renameTo(nFile);    	  
    	   nFile=null;
    	}
    	eTestResult=null;
    }
    
    public static void generateTestResult(){
		if(testCaseResult.length() == 0){
			System.out.println("Info: No test result to write in file. Hence skipped generating Test result file.");
			return;
		}
		 String project_path = System.getProperty("user.dir");
		 String fpath = project_path+"\\src\\test\\resources\\Report\\";
		 renameFile(fpath);
		 PrintWriter testResultStream=null;
		 //String fName=String.format("MI_Detailed_Results.html", "Report",new SimpleDateFormat("yyyyMMddHHmmss").format(new Date(System.currentTimeMillis())));
		 //System.out.println("getReportFileName "+fName);
		 try {
			testResultStream=new PrintWriter(new File(fpath+"MI_Detailed_Results.html"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		endTime=System.currentTimeMillis();		
		header.append(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date(endTime))+"</i>.</h2>");	
        
        testCaseResult.append("</BODY></HTML>");
        
        testResultStream.println(header);
    	testResultStream.println(testCaseResult);
    	testResultStream.close();
    	testResultStream=null;
	}
	
	public static void resetListTestResult(){
	     if(!lsTestResult.isEmpty()){
	        lsTestResult=new ArrayList<String[]>();
	     }
	}
	
	public static void addTestResult(String... testResult){
		 String[] result=new String[3];
	     result[0]=testResult[0];
	     result[1]=testResult[1];	     
	     result[2]=testResult.length==3? testResult[2]:"";
	     lsTestResult.add(result);
	}
	
	
}
