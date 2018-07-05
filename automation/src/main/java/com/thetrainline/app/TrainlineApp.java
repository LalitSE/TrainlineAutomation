package com.thetrainline.app;

import com.thetrainline.framework.utils.Configuration;

public class TrainlineApp extends AbstractApp{

	public TrainlineApp(){
		super();
	}

	@Override
	public String getURL() {
		return Configuration.getInstance().getBaseURL();
	}
}
