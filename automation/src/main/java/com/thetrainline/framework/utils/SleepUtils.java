package com.thetrainline.framework.utils;

import org.apache.log4j.Logger;

public class SleepUtils {

	static Logger log = Logger.getLogger(SleepUtils.class);
	
	/**
	 * Hard Sleep, DO NOT Use unless it is necessary
	 * @param seconds
	 */
	public static void sleep(double seconds) {
		log.info(String.format("Sleeping For %s Seconds", seconds));
		try {
			Thread.sleep(Double.valueOf(seconds * 1000).intValue());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
