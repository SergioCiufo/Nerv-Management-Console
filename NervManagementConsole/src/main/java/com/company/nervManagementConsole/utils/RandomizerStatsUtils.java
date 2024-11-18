package com.company.nervManagementConsole.utils;

import java.util.Random;

public class RandomizerStatsUtils {
	
	public static int randomizeStats(Integer attrbMax) {
		Random random = new Random ();
		int valueRandom=0;
		if(attrbMax<0) {
			valueRandom= random.nextInt(-attrbMax + 1) + attrbMax;
			return valueRandom;
		}else {
			valueRandom= random.nextInt(attrbMax)+1;
			return valueRandom;
		}
	}
}
