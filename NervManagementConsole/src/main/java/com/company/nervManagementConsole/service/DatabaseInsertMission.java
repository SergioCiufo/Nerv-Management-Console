package com.company.nervManagementConsole.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.company.nervManagementConsole.dao.MissionDao;
import com.company.nervManagementConsole.model.Mission;

public class DatabaseInsertMission {
	private MissionDao missionDao;

	public DatabaseInsertMission(MissionDao missionDao) {
		super();
		this.missionDao = missionDao;
	}
	
	public void createMission(Connection connection, String name, String description, Integer participantsMax, Integer levelMin,
			Integer synchRate, Integer tactAbility, Integer suppAbility, Integer exp, Integer durationTime) throws SQLException {
		
		Mission mission = new Mission(name, description, participantsMax, levelMin, synchRate, 
				tactAbility, suppAbility, exp, durationTime);
		try {
			missionDao.create(mission, connection);
			connection.commit();
		} catch (Exception e) {
			connection.rollback();
			e.printStackTrace();
		}
	}
	
	public void createMissions(Connection connection) throws SQLException {
	    createMission(connection, "Angel Attack",
	                  "A giant angel, Sachiel, emerges from the seas and heads towards Neo Tokyo-3, destroying everything in its path. "
	                  + "The city is in a state of emergency, with conventional defenses unable to stop it. "
	                  + "The central command of NERV orders the use of the Evangelion, but the enemy shows surprising resistance and a disturbing regenerative ability.",
	                  1, 2, 40, 35, 25, 100, 5);
	    
	    createMission(connection, "A Transfer",
	                  "A new angel, Shamshel, attacks the city, using long energy whips that can devastate anything they touch. "
	                  + "The enemy proves to be agile and deadly, forcing the Evangelion to fight in close quarters. "
	                  + "The mission requires cool-headedness to stop it before it causes irreparable damage.",
	                  1, 5, 50, 30, 35, 200, 6);

	    createMission(connection, "Showdown in Tokyo-3",
	                  "Ramiel, an angel with a perfect geometric shape, manifests above Neo Tokyo-3. "
	                  + "Every approach is repelled by a devastating energy beam, making direct confrontation impossible. "
	                  + "The only solution is to develop a long-range attack strategy, but any mistake could be fatal.",
	                  2, 15, 60, 45, 40, 300, 7);

	    createMission(connection, "Asuka Strikes!",
	                  "An angel emerges from the depths of the ocean, attacking a military fleet with incredible strength and speed. "
	                  + "Facing Gaghiel in an underwater environment poses new challenges, as the Evangelion must adapt to the marine conditions "
	                  + "while trying to defend the valuable cargo transported by the fleet.",
	                  2, 20, 70, 55, 60, 300, 8);
	    
	    createMission(connection, "Both of You, Dance Like You Want to Win!",
	                  "Israfel, a humanoid angel, splits into two perfectly synchronized entities, "
	                  + "making every attack ineffective. The mission requires a strategy based on perfect cooperation to strike "
	                  + "both targets simultaneously.",
	                  2, 25, 75, 65, 65, 350, 9);
	    
	    createMission(connection, "Magma Diver",
	                  "A dormant angel is located inside an active volcano. The mission is to capture it alive, "
	                  + "but the sudden awakening of Sandalphon turns the operation into a desperate struggle. "
	                  + "The hostile environment and volcanic instability test every move.",
	                  1, 30, 60, 70, 55, 350, 10);
	    
	    createMission(connection, "The Day Tokyo-3 Stood Still",
	                  "During a sudden blackout, an angel in the form of a colossal spider attacks Neo Tokyo-3, "
	                  + "using a powerful acid to pierce the shelters. In the absence of technological support, the mission takes place "
	                  + "under precarious conditions, requiring improvised solutions to stop Matarael before it reaches the civilians.",
	                  2, 35, 50, 60, 45, 400, 10);
	    
	    createMission(connection, "She Said, Don't Make Others Suffer",
	                  "Sahaquiel, a giant angel, manifests in the skies and attempts to crash into the city like a massive bomb. "
	                  + "The only hope is to intercept it before impact, but its ability to attack from a distance and its size "
	                  + "pose a unique challenge. Every second is crucial to save Neo Tokyo-3.",
	                  2, 40, 65, 80, 75, 900, 11);
	    
	    createMission(connection, "Lilliputian Hitcher",
	                  "A microscopic angel invades NERV, infecting the computer systems and putting the entire base at risk. "
	                  + "The mission requires not just strength, but intelligence and strategy to block the enemy, which evolves rapidly "
	                  + "to adapt to every countermeasure.",
	                  1, 50, 55, 60, 80, 950, 12);
	    
	    createMission(connection, "Oral Stage",
	                  "An Evangelion in transit is infected by a parasitic angel. Bardiel uses the unit to attack, "
	                  + "fully exploiting its power. Stopping it means fighting against one of your own mechs, without destroying "
	                  + "the infected unit.",
	                  3, 60, 70, 50, 60, 500, 13);
	    
	    createMission(connection, "Splitting of the Breast",
	                  "Leliel appears above Neo Tokyo-3 as a huge black sphere, but the real enemy is a two-dimensional shadow that distorts reality. "
	                  + "Entering the dimension created by the angel is the only chance to neutralize it, but the risk of getting trapped "
	                  + "forever is extremely high.",
	                  2, 65, 80, 70, 65, 450, 14);
	    
	    createMission(connection, "Ambivalence",
	                  "Zeruel, an angel with unprecedented destructive power, directly attacks NERV, smashing all defenses with its "
	                  + "energy attacks and powerful blades. Stopping it requires a direct confrontation, but any mistake could lead to the destruction "
	                  + "of the headquarters and the city.",
	                  3, 70, 85, 90, 85, 950, 15);
	    
	    createMission(connection, "Introjection",
	                  "The angel Zeruel continues its devastating attack on NERV, pushing the base's defenses to the limit. Pilots must face "
	                  + "a desperate battle to stop its advance. The enemy proves to be incredibly resistant and powerful, forcing them to risk "
	                  + "everything in a battle with no holds barred.",
	                  3, 80, 90, 80, 90, 1000, 16);
	    
	    createMission(connection, "He Was Aware That He Was Still a Child",
	                  "The angel Arael appears in Earth’s orbit, sending a powerful psychic attack towards the pilots. The mission requires an immediate "
	                  + "response to stop it, but every attempt to approach exposes them to grave mental risks. Past traumas resurface, "
	                  + "turning the battle into both a physical and emotional test.",
	                  2, 85, 65, 55, 70, 1500, 17);
	    
	    createMission(connection, "Don't Be",
	                  "An enigmatic angel, Armisael, manifests as a fluid entity attempting to make contact with the Evangelion units. "
	                  + "The mission is to stop it before it breaches the defenses, but every move brings the pilots closer to a dangerous connection "
	                  + "with the angel, threatening to merge their minds and bodies.",
	                  2, 90, 90, 75, 80, 2000, 18);
	    
	    createMission(connection, "Kaworu Nagisa",
	                  "Tabris, an angel in disguise, makes direct contact with NERV, blurring the lines between enemy and ally. "
	                  + "The mission is to uncover its true nature and prevent it from accessing the Terminal Dogma, where a terrible secret is hidden. "
	                  + "The battle takes place on both personal and strategic levels, culminating in a difficult choice for the pilots.",
	                  3, 100, 100, 90, 85, 3000, 20);
	    
	    createMission(connection, "Take Care of Yourself",
	                  "The final challenge is not against an angel, but against oneself. The last mission is a deep reflection on sacrifice and inner strength, "
	                  + "where each pilot must face their own demons to overcome their fears and doubts, succeeding in a race against time and their limitations.",
	                  3, 100, 100, 100, 100, 5000, 25);
	}
}
