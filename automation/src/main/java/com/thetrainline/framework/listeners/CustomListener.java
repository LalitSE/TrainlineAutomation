package com.thetrainline.framework.listeners;

import org.apache.log4j.Logger;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

import ru.yandex.qatools.allure.annotations.Title;

public class CustomListener implements IInvokedMethodListener {

	static Logger log = Logger.getLogger(CustomListener.class);

	@Override
	public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
		Title scenarioTitle = method.getTestMethod().getConstructorOrMethod().getMethod().getAnnotation(Title.class);
		if (scenarioTitle != null)
			log.info("Scenario Started : " + scenarioTitle.value());
	}

	@Override
	public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
		log.info("After " + method.getTestMethod().getMethodName());
	}

}
