package mob_insurance.main;

import java.util.Iterator;
import java.util.Map;

import mob_insurance.io.ManageConfig;
import mob_insurance.io.TestData;
import mob_insurance.processor.TestResult;
//import mob_insurance.testsuite.AgentSettingTestSuite;
import mob_insurance.testsuite.CreateContractTestSuite;
import mob_insurance.testsuite.ManageInsuranceField;

public class GenerateTestFiles {

	public static void main(String args[]) throws Exception{
		ManageConfig.loadConfigProperties();
		String fileName=ManageConfig.getValue("InsuranceField.Raw.File");
		if(fileName == null || fileName.isEmpty()){
			System.out.println("Info: File name has not specified against InsuranceField.Raw.File to retrieve insurance fields. Henece process has been aborted.");
			 return;
		 }
		 System.out.println("Info: Started extracting insurance fields from specified file.");
		 ManageInsuranceField objManageInsuranceField=new ManageInsuranceField();
		 objManageInsuranceField.generateInsuranceFieldTestDataFile(fileName);
		 System.out.println("Info: completed extracting insurance fields from specified file.");
				
	}
}
