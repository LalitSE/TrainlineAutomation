package com.thetrainline.framework.utils;

import java.util.List;
import java.util.Random;

public class RandomUtils {

	public static <T> T getRandomFromList(List<T> list){
		Random random = new Random();
		int value = random.nextInt(list.size());
		return list.get(value);
	}
	
}
