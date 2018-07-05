package com.thetrainline.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class FileUtils {
	/**
	 * Write Environment Properties File, To populate details in Allure Reports
	 * 
	 * @param prop
	 * @param filePath
	 */
	public static void writePropertiesFile(Properties prop, String filePath) {
		OutputStream output = null;
		try {
			output = new FileOutputStream(filePath);
			// save properties to project root folder
			prop.store(output, null);
		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}
}
