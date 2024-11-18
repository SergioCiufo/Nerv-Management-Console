package com.company.nervManagementConsole.utils;

import java.util.List;

import com.company.nervManagementConsole.model.MissionArchive;

public class MissionCodeGeneratorUtils {
	public static String missionCodeGenerator(List<MissionArchive> missionArchive, Integer idMission){
		Integer missionTry=0;
	    Integer missionTryParse=null;
	    String lastMissionTryString;
	    try {
	    	if(missionArchive == null || missionArchive.isEmpty()) {
		    	missionTry=1;
		    }else {
		    	for(MissionArchive mA : missionArchive) {
		    		lastMissionTryString =mA.getMissionCode();
		    		lastMissionTryString = lastMissionTryString.substring(lastMissionTryString.length() - 3);
		    		missionTryParse = Integer.parseInt(lastMissionTryString)+1;
		    		if(missionTryParse>=missionTry) {
		    			missionTry=missionTryParse;
		    		}
		    	}
		    }
		    String missionTryString = String.format("%03d", missionTry);
		    String missionCode=("M"+idMission+"-"+missionTryString);
		    return missionCode;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
