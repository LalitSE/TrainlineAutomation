package com.thetrainline.framework.selenium;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import ru.yandex.qatools.allure.annotations.Step;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

/**
 * Class to setup Browser configuration and Actions.
 */
public class Browser {

	static Logger log = Logger.getLogger(Browser.class);

	protected WebDriver driver;
	protected WebDriverWait waitDriver;
	protected Actions actionDriver;
	protected String parentHandle;
	protected Browsers browser;
	protected JavascriptExecutor jsDriver;
	private int dynamicWait = 60;
	private String downloadFolderPath;

	public Browser() {
		this(null);
	}

	public Browser(WebDriver driver) {
		this.driver = driver;
		if (driver != null)
			initialize();
	}

	public void launchBrowser(String browser) {
		launchBrowser(getBrowserFromString(browser));
	}

	@Step("Open Browser : {0}")
	private Browsers getBrowserFromString(String browser) {
		if (browser.equalsIgnoreCase("firefox")) {
			return Browsers.FireFox;
		} else if (browser.equalsIgnoreCase("safari")) {
			return Browsers.Safari;
		} else if (browser.equalsIgnoreCase("ie")) {
			return Browsers.IE;
		} else if (browser.equalsIgnoreCase("edge")) {
			return Browsers.Edge;
		} else if (browser.equalsIgnoreCase("android")) {
			return Browsers.ANDROID;
		} else if (browser.equalsIgnoreCase("ios")) {
			return Browsers.iOS;
		}
		return Browsers.Chrome;
	}

	public Browsers getBrowser() {
		return browser;
	}

	/**
	 * Launch Browser
	 * 
	 * @param browser
	 * @throws MalformedURLException
	 */
	public void launchBrowser(Browsers browser) {
		String osArch = System.getProperty("os.arch");
		log.info("Browser : " + browser);
		log.info("OS Arch : " + osArch);

		if (browser == Browsers.Chrome) {
			System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
			options.addArguments("test-type");
			options.addArguments("start-maximized");
			if (getDownloadFolderPath() != null) {
				log.info("Setting Download Folder Path : " + getDownloadFolderPath());
				HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
				chromePrefs.put("profile.default_content_settings.popups", 0);
				chromePrefs.put("download.default_directory", getDownloadFolderPath());
				options.setExperimentalOption("prefs", chromePrefs);
			}
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			capabilities.setCapability(ChromeOptions.CAPABILITY, options);
			driver = new ChromeDriver(capabilities);

		} else if (browser == Browsers.FireFox) {
			if (osArch.endsWith("64")) {
				System.setProperty("webdriver.gecko.driver", "drivers\\geckodriver_64.exe");
			} else {
				System.setProperty("webdriver.gecko.driver", "drivers\\geckodriver_64.exe");
			}
			DesiredCapabilities capabilities = DesiredCapabilities.firefox();
			capabilities.setCapability("marionette", true);
			driver = new FirefoxDriver(capabilities);

			driver.manage().window().maximize();

		} else if (browser == Browsers.IE) {

			if (osArch.endsWith("64")) {
				System.setProperty("webdriver.ie.driver", "drivers\\IEDriverServer_32.exe");
			} else {
				System.setProperty("webdriver.ie.driver", "drivers\\IEDriverServer_32.exe");
			}
			DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
			capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
			driver = new InternetExplorerDriver(capabilities);
		} else if (browser == Browsers.Edge) {
			// https://developer.microsoft.com/en-us/microsoft-edge/tools/webdriver/
			System.setProperty("webdriver.edge.driver", "drivers\\MicrosoftWebDriver.exe");
			DesiredCapabilities capabilities = DesiredCapabilities.edge();
			driver = new EdgeDriver(capabilities);
		}

		maximize();
		initialize();
	}

	private void initialize() {
		actionDriver = new Actions(driver);
		waitDriver = new WebDriverWait(driver, dynamicWait);// By Default
															// Dynamic Timeout
		// is 60 Seconds
		parentHandle = driver.getWindowHandle();
		jsDriver = (JavascriptExecutor) this.driver;
	}

	/**
	 * Set Implicit Wait
	 * 
	 * @param seconds
	 */
	public void setImpilicitTimeOut(int seconds) {
		log.info("Setting Implicit Wait : " + seconds + " Seconds");
		driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
	}

	/**
	 * Set Explicit wait
	 * 
	 * @param seconds
	 */
	public void setExplicitWait(int seconds) {
		log.info("Setting Explicit Wait : " + seconds + " Seconds");
		dynamicWait = seconds;
		waitDriver = new WebDriverWait(driver, seconds);
	}

	/**
	 * Set Page Load Time Out
	 * 
	 * @param seconds
	 */
	public void setPageLoadTimeOut(int seconds) {
		log.info("Setting Page Load Timeout : " + seconds + " Seconds");
		driver.manage().timeouts().pageLoadTimeout(seconds, TimeUnit.SECONDS);
	}

	@Step("Closing Browser")
	public void closeBrowser() {
		log.info("Closing Browser");
		if (driver != null) {
			driver.quit();
		}
	}

	@Step("Load URL : {0}")
	public void openURL(String URL) {
		log.info("Opening URL : " + URL);
		driver.get(URL);
	}

	/**
	 * Return current driver instance
	 * 
	 * @return
	 */
	public WebDriver getDriver() {
		return driver;
	}

	@Step("Action : Refresh Page")
	public void refresh() {
		log.info("Refreshing Page.");
		driver.navigate().refresh();
	}

	@Step("Action : Navigate To Back")
	public void back() {
		log.info("Navigating To Back");
		driver.navigate().back();
	}

	/**
	 * Attach image whenever method called.
	 * 
	 * @return
	 * @throws IOException
	 */
	public byte[] takeScreenShot() {
		try {
			Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(100))
					.takeScreenshot(getDriver());
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(screenshot.getImage(), "jpg", baos);

			baos.flush();
			byte[] imageInByte = baos.toByteArray();
			baos.close();
			return imageInByte;
		} catch (Exception e) {
			e.printStackTrace();
			return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);
		}
	}

	public Actions getActionDriver() {
		return actionDriver;
	}

	public WebDriverWait getWebDriverWait() {
		return waitDriver;
	}

	public JavascriptExecutor getJSDriver() {
		return jsDriver;
	}

	public void switchToChildWindow() {
		for (String handle : driver.getWindowHandles()) {
			if (!handle.equals(parentHandle)) {
				driver.switchTo().window(handle);
				break;
			}
		}
	}

	public void closeChildWindow() {
		driver.close();
		driver.switchTo().window(parentHandle);
	}

	public void maximize() {
		log.info("Maximize window");
		driver.manage().window().maximize();
	}

	public String getDownloadFolderPath() {
		return downloadFolderPath;
	}

	public void setDownloadFolderPath(String downloadFolderPath) {
		this.downloadFolderPath = downloadFolderPath;
	}

	public int getExplicitWait() {
		return dynamicWait;
	}

}
