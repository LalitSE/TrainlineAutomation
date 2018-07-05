package com.thetrainline.utils;

import java.io.File;

import ru.yandex.qatools.allure.config.AllureConfig;

public class DirectoryUtils {

	public static String getUserDir(){
		return System.getProperty("user.dir");
	}
	
	public static String getAllureReportDir() {
	return 	getUserDir() + File.separator + AllureConfig.getDefaultResultsDirectory();
	}

	public static String getTargetDir(){
		return getUserDir() + File.separator + "target";
	}
}
