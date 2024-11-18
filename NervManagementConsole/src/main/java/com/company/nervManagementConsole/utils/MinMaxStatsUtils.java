package com.company.nervManagementConsole.utils;

public class MinMaxStatsUtils {
	public static Integer MinMaxStat (Integer stat) {
		 if (stat < 0) {
		        return 0;
		    } else if (stat > 100) {
		        return 100;
		    }
		    return stat;
	}
}
