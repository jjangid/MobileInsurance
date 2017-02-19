package mob_insurance.listeners;

import org.testng.ISuite;
import org.testng.ISuiteListener;

public class TestSuitListener implements ISuiteListener{

	@Override
	public void onFinish(ISuite arg0) {
//	Generate Report after finish of Test Suite execution
	TestResult.generateTestResult();	
		
	}

	@Override
	public void onStart(ISuite arg0) {
//  Initialize TestResult instance at time starting of Test Suite Execution		
	TestResult.initializeTestResult();	
		
	}

	
	
}
