package com.company.nervManagementConsole.utils;

import java.util.List;
import java.util.Random;

public class CalculateWinLoseUtils {

	    public static Integer calculateAverage(List<Integer> list) {
	        if (list.isEmpty()) {
	            return 0;
	        }
	        int sum = 0;
	        for (Integer value : list) {
	            sum += value;
	        }
	        return sum / list.size();
	    }

	    public static boolean calculateWinLoseProbability(Integer missionSr, Integer missionSa, Integer missionTa, List<Integer> syncRateToAvg, List<Integer> tactAbilityToAvg, List<Integer> suppAbilityToAvg) {
	        Integer avgSyncRate = calculateAverage(syncRateToAvg);
	        Integer avgTactAbility = calculateAverage(tactAbilityToAvg);
	        Integer avgSuppAbility = calculateAverage(suppAbilityToAvg);

	        Integer defeatProbability = Math.max(0, missionSr - avgSyncRate)
	                                    + Math.max(0, missionSa - avgSuppAbility)
	                                    + Math.max(0, missionTa - avgTactAbility)
	                                    + 25;

	        Random random = new Random();
	        int winPossibility = random.nextInt(101);

	        boolean result = winPossibility >= defeatProbability;

	        if (result) {
	            System.out.println("Winner Winner Chicken Dinner!");
	        } else {
	            System.out.println("You Lose");
	        }

	        return result;
	    }
}