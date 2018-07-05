package com.thetrainline.basetests;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.testng.IHookCallBack;
import org.testng.IHookable;
import org.testng.ITestResult;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.thetrainline.framework.listeners.CustomListener;
import com.thetrainline.framework.selenium.Browser;
import com.thetrainline.framework.utils.Configuration;
import com.thetrainline.misc.TrainlineLogger;
import com.thetrainline.utils.DirectoryUtils;
import com.thetrainline.utils.FileUtils;

import ru.yandex.qatools.allure.Allure;
import ru.yandex.qatools.allure.annotations.Attachment;
import ru.yandex.qatools.allure.events.StepFailureEvent;

@Listeners(CustomListener.class)
public class BaseTest implements IHookable {
	
	
	protected Browser browser;
	protected Configuration config ;
	
	static Logger log = Logger.getLogger(BaseTest.class);
	
	/**
	 * SetUp Environment,Browser,TimeOut and User details.
	 * @throws Exception 
	 */
	@BeforeSuite(alwaysRun=true)
	@Parameters({"browser"})
	public void beforeSuite(@Optional("chrome")String browserString){
		TrainlineLogger.initializeLogger();
		Configuration.getInstance().setBrowser(browserString);
		config = Configuration.getInstance();
	}
	
	/**
	 * Log Out of the application and close the browser.
	 * @throws Exception 
	 */
	@AfterSuite(alwaysRun = true)
	public void tearDown() throws Exception {
		File file = new File(DirectoryUtils.getAllureReportDir()+ File.separator +  "environment.properties");
		file.getParentFile().mkdirs();
		FileUtils.writePropertiesFile(config.getEnvProperties(), file.getAbsolutePath());
	}

	public void run(IHookCallBack callBack, ITestResult testResult) {
		callBack.runTestMethod(testResult);
		if (testResult.getThrowable() != null) {
			log.info("Test Case : " + testResult.getMethod().getMethodName() + "Failed, Reason : "
					+ testResult.getThrowable().getCause().getMessage());
			takeScreenShot(testResult.getMethod().getMethodName(), testResult.getThrowable().getCause().getMessage());
			Allure.LIFECYCLE.fire(new StepFailureEvent().withThrowable(new AssertionError()));
		}
	}

	/**
	 * Attach image when test fails.
	 * 
	 * @param methodName
	 * @param description
	 * @return
	 * @throws IOException
	 */
	@Attachment(value = "Failure in method {0}, Reason - {1} ", type = "image/png")
	public byte[] takeScreenShot(String methodName, String description) {
		return browser.takeScreenShot();
	}

	/**
	 * Attach image when test fails.
	 * 
	 * @param description
	 * @return
	 * @throws IOException
	 */
	@Attachment(value = "Reason - {0} ", type = "image/png")
	public byte[] takeScreenShot(String description) {
		return browser.takeScreenShot();
	}
}
