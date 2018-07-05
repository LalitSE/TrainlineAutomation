package com.thetrainline.framework.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

public class Configuration {

	static Logger log = Logger.getLogger(Configuration.class);
	private static Configuration config;
	private Properties prop;
	private File configFile;
	public static final String BROWSERKEY = "browser";
	public static final String BASEURLKEY = "base.url";
	public static final String PAGELOADTIMEOUTKEY = "pageLoadTimeOut";
	public static final String IMPLICITWAITKEY = "implicitWait";
	public static final String EXPLICITWAITKEY = "explicitWait";

	public static Configuration getInstance() {
		if (config == null) {
			config = new Configuration();
		}
		return config;
	}

	private Configuration() {
		this(new File("config.properties"));
	}

	private Configuration(File configFile) {
		this.configFile = configFile;
		initalizeProperty();
	}

	private void initalizeProperty() {
		log.info("Initalizing Configuration Property : " + configFile.getAbsolutePath());
		prop = new Properties();
		FileInputStream input = null;
		try {
			input = new FileInputStream(configFile);
		} catch (FileNotFoundException e) {
			log.fatal("Configuration File Not Found.");
			e.printStackTrace();
			log.fatal(e);
		}
		try {
			prop.load(input);
		} catch (IOException e) {
			log.fatal(e);
			log.fatal("Failed Load Configuration File,Check File Syntax.");
			e.printStackTrace();
		}

	}

	public String getBrowser() {
		return getProperty(BROWSERKEY);
	}

	public void setBrowser(String browser) {
		prop.setProperty(BROWSERKEY, browser);
	}

	public String getBaseURL() {
		return getProperty(BASEURLKEY);
	}

	public int getPageLoadTimeOut() {
		try {
			return Integer.valueOf(getProperty(PAGELOADTIMEOUTKEY));
		} catch (Exception e) {
			log.fatal("Exception Occured while reading pageLoadTimeOut Value.Setting default value : 60 Seconds");
			log.fatal(e);
			return 60;
		}
	}

	public int getImplicitWait() {
		try {
			return Integer.valueOf(getProperty(IMPLICITWAITKEY));
		} catch (Exception e) {
			log.fatal("Exception Occured while reading implicitWait Value.Setting default value : 10 Seconds");
			log.fatal(e);
			return 10;
		}
	}

	public int getExplicitWait() {
		try {
			return Integer.valueOf(getProperty(EXPLICITWAITKEY));
		} catch (Exception e) {
			log.fatal("Exception Occured while reading explicitWait Value. Setting default value : 60 Seconds");
			log.fatal(e);
			return 60;
		}
	}

	private String getProperty(String key) {
		String value = System.getProperty(key);
		if (value == null) {
			value = prop.getProperty(key);
		}
		return value.trim();
	}

	public Properties getProp() {
		return prop;
	}

	public Properties getEnvProperties() {
		Properties prop = new Properties();
		prop.setProperty(BASEURLKEY, getBaseURL());
		prop.setProperty("pageLoadTimeOut", String.valueOf(getPageLoadTimeOut()) + TimeUnit.SECONDS);
		prop.setProperty("implicitWait", String.valueOf(getImplicitWait()) + TimeUnit.SECONDS);
		prop.setProperty("explicitWait", String.valueOf(getExplicitWait()) + TimeUnit.SECONDS);
		return prop;
	}

}
