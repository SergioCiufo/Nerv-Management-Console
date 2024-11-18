<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%@page
	import="com.company.nervManagementConsole.model.MissionArchive.MissionResult"%>
<%@page import="com.company.nervManagementConsole.model.MissionArchive"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.sql.Blob"%>
<%@page
	import="com.company.nervManagementConsole.model.MissionParticipants"%>
<%@page import="com.company.nervManagementConsole.model.Mission"%>
<%@page import="java.time.Duration"%>
<%@page import="java.time.LocalDateTime"%>
<%@page import="java.util.List"%>
<%@page
	import="com.company.nervManagementConsole.model.SimulationParticipant"%>
<%@page import="com.company.nervManagementConsole.model.Simulation"%>
<%@page
	import="com.company.nervManagementConsole.model.UserMembersStats"%>
<%@page import="com.company.nervManagementConsole.model.Member"%>
<%@page import="com.company.nervManagementConsole.utils.Costants"%>
<%@page import="com.company.nervManagementConsole.model.User"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Home</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="<%= "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/css/Home.css"%>" />
</head>
<style>
@font-face {
	font-family: "evangelionFont";
	src:
		url('<%= "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/font/NimbusRomNo9L-Reg.otf" %>')
		format("opentype");
}

body {
	background-image:
		url('<%= "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/media/background.svg" %>');
	font-family: "evangelionFont", sans-serif;
}
</style>

<% 
    User user=(User)request.getSession().getAttribute(Costants.KEY_SESSION_USER);
	Member member;	
	Simulation simulation;
	UserMembersStats memberStats;
	Mission missions;
	Blob imageBlob;
	MissionParticipants missionParticipants;
	MissionArchive missionArchive;
	String status;
	
	List<SimulationParticipant> participants = user.getParticipants(); 
	List<Member> members = user.getMembers();
	Map<String, List<MissionArchive>> missionArchiveMap = user.getMissionArchiveResult();
%>

<body class="container-fluid">
	<nav class="row align-items-center">
		<div class="col text-end">
			<form action="<%=request.getContextPath() + "/logout"%>"
				method="post" class="d-inline">
				<button id="logoutBtn" type="submit">
					<h3>LOGOUT</h3>
				</button>
			</form>
		</div>
	</nav>
	<main class="row">
		<div class="col-3">
			<div class="row d-flex nameUser">
				<h1><%= user.getName() %>
					<%=user.getSurname() %></h1>
			</div>
			<div class="row imageUserDiv">
				<img id="imageUser"
					src='<%="http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/media/misatoProfilePic.jpg"%>'
					alt="user profile pic">
			</div>
			<div class="row d-flex box">
				<button id="buttonBoxMember" class="buttonBox"
					onclick="toggleMemberPage()">
					<h1>MEMBERS</h1>
				</button>
			</div>
			<div class="row d-flex box">
				<button id="buttonBoxSimulations" class="buttonBox ">
					<h1>SIMULATION</h1>
				</button>
			</div>
			<div class="row d-flex box">
				<button id="buttonBoxMissions" class="buttonBox ">
					<h1>MISSIONS</h1>
				</button>
			</div>
		</div>

		<!-- MEMBERS PART -->
		<% 
    if (user.getMembers() == null || user.getMembers().isEmpty()) {
        out.print("NO MEMBERS");
    } else {
%>
		<div id="memberPage" class="col-4 overflow-auto"
			style="max-height: 100%; display: none;">
			<div class="row membersBox justify-content-center align-items-center">
				<% 
                    for (int i = 0; i < user.getMembers().size(); i++) {
                        member = user.getMembers().get(i);
                        memberStats = member.getMemberStats();
                        if (memberStats != null) {
                            status = memberStats.getStatus() ? "FREE" : "BUSY";
                            
                            for (SimulationParticipant participant : participants) {
                                if (participant.getMemberId().equals(member.getIdMember())) {
                                    status = "TRAINING";
                                    break;
                                }
                            }
                            
                            for (MissionParticipants missPartecipant : user.getMissionParticipants()){
                            	if(missPartecipant.getMemberId().equals(member.getIdMember())){
                            		status = "MISSION";
                            		break;
                            	}
                            }
                            
                            String buttonClass = "buttonMemberInfo";
                            if (status.equals("TRAINING")) {
                                buttonClass = "buttonMemberInfoTraining";  // Classe CSS per il training
                            } else if (status.equals("MISSION")) {
                                buttonClass = "buttonMemberInfoMission";  // Classe CSS per il training
                            }
                            
                            
                %>
				<button class="buttonMemberInfo <%= buttonClass %>"
					id="member<%= i %>" onclick="showMemberInfo(<%= i %>)">
					<div class="row memberName text-center align-items-end">
						<h1><%= member.getName() %>
							<%= member.getSurname() %></h1>
					</div>
					<div class="row memberStatus text-center align-items-top">
						<h1>
							STATUS:
							<%= status %></h1>
					</div>
				</button>
				<% 
                        }
                    } 
                %>
			</div>
		</div>
		<% } %>
		<!---->
		<!-- hide by member select INFOBOX DEVE INCREMENTARE DI 1 SEGUITO DAL BOTTONE CHE LO RICHIAMA-->
		<% 
    if (user.getMembers() != null && !user.getMembers().isEmpty()) {
        for (int i = 0; i < user.getMembers().size(); i++) {
            member = user.getMembers().get(i);
            memberStats = member.getMemberStats();
            if (memberStats != null) {
            	status = memberStats.getStatus() ? "FREE" : "BUSY";
            	String simInfo = null;
            	String missInfo = null;

            	
            	for (SimulationParticipant participant : participants) {
                    if (participant.getMemberId().equals(member.getIdMember())) {
                        status = "TRAINING";
                        simInfo = participant.getSimulation().getName();

                        break;
                    }
                }
            	
            	 for (MissionParticipants missPartecipant : user.getMissionParticipants()){
                 	if(missPartecipant.getMemberId().equals(member.getIdMember())){
                 		status = "MISSION";
                 		missInfo = missPartecipant.getMission().getName();
                 		break;
                 	}
                 }
            	
            	 String buttonClass = "buttonStatusInfo";
                 if (status.equals("TRAINING")) {
                     buttonClass = "buttonStatusInfoTraining";  // Classe CSS per il training
                 }else if (status.equals("MISSION")) {
                     buttonClass = "buttonMemberInfoMission";  // Classe CSS per il training
                 }
%>
		<div class="col-4 infoBoxHide" id="infoBox<%= i %>"
			style="display: none;">
			<div class="row infoBox justify-content-center">
				<div
					class="row memberSpecImg justify-content-center align-items-center">
					<img class="imgSimMember" alt="member image"
						src=" <%= "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/media/memberImage" + i + ".png"%>">
				</div>
				<div
					class="row memberSpec justify-content-center align-items-center">
					<p style="font-weight: bold; font-size: larger;">
						LEVEL:
						<%= memberStats.getLevel() %></p>
					<p style="font-weight: bold; font-size: larger;">
						EXP:
						<%= memberStats.getExp() %></p>
					<p style="font-weight: bold; font-size: larger;">
						SYNCHRONIZATION RATE:
						<%= memberStats.getSynchronizationRate() %>%
					</p>
					<p style="font-weight: bold; font-size: larger;">
						TACTICAL ABILITY:
						<%= memberStats.getTacticalAbility() %>%
					</p>
					<p style="font-weight: bold; font-size: larger;">
						SUPPORT ABILITY:
						<%= memberStats.getSupportAbility() %>%
					</p>
				</div>

				<div
					class="row memberSpec justify-content-center align-items-center">
					<button class="<%= buttonClass %>">

						<div class="row memberStatus text-center align-items-top">
							<h4>
								STATUS:
								<%= status %></h4>
							<% if(simInfo != null){   	
                            %>
							<h4>
								NAME:
								<%= simInfo %></h4>
							<%
                            }else if(missInfo != null){
                            %>
							<h4>
								NAME:
								<%= missInfo %></h4>
							<%
                            }
                           	%>


						</div>
					</button>
				</div>
			</div>
		</div>
		<%
            }
        }
    }
%>

		<!--INIZIO CONTENITORE SIMULATION-->

		<div id="simulation" class="col-4 overflow-auto"
			style="max-height: 100%; display: none;">
			<div
				class="row simulationBox justify-content-center align-items-center">
				<!-- inizio for bottone SIM 1 DEVE INCREMENTARE PER IL BOTTONE SUCC-->
				<%
    if (user.getSimulations() != null && !user.getSimulations().isEmpty()) {
        for (Simulation sim : user.getSimulations()) {

            boolean hasParticipant = false;
            String membNameSurname = null;

            for (SimulationParticipant participant : participants) {
                if (participant.getSimulation().getSimulationId().equals(sim.getSimulationId())) {
                    hasParticipant = true;
                    for (Member memb : members) {
                        if (memb.getIdMember().equals(participant.getMemberId())) {
                            membNameSurname = memb.getName() + " " + memb.getSurname();
                            break;
                        }
                    }
%>
				<%
    LocalDateTime curentTime = LocalDateTime.now();
    LocalDateTime endTime = participant.getEndTime();
    long durationSeconds = 0;

    Duration duration = Duration.between(curentTime, endTime);
    if (curentTime != null && endTime != null) {   
        durationSeconds = duration.getSeconds();
    }
%>
				<%
	if(duration.isZero() || duration.isNegative()){
	%>

				<form class="formSimComp"
					action="<%=request.getContextPath() + "/simulationCompleted"%>"
					method="post">
					<input type="hidden" name="simulationId"
						value="<%= sim.getSimulationId() %>"> <input type="hidden"
						name="memberSelect" value="<%= participant.getMemberId() %>">
					<button type="submit" class="buttonSimCompleted">
						<span class="SimDescpBtn">
							<h3><%= sim.getName() %></h3>
							<h3>COMPLETED!</h3>
						</span> <span class="startSimBtn">
							<h3>REDEEM</h3>
						</span>
					</button>
				</form>
				<%	
	}else{
	%>
				<button class="buttonSimInfo" id="sim1">
					<div class="row simName text-center align-items-end">
						<h3><%= sim.getName()  %></h3>
						<h4 class="simStatusTrue">ON EXECUTION</h4>
						<h4>
							MEMBER:
							<%= membNameSurname.toUpperCase() %></h4>
						<h4 class="duration">
							DURATION: <span class="timeLeft"><%= durationSeconds %></span>
							MINUTES
						</h4>
					</div>
				</button>
				<%	
	}
%>

				<%
                    break;
                }
            }

            // Se non ci sono partecipanti, aggiungi il pulsante di avvio
            if (!hasParticipant) {
%>
				<button data-simId="<%=sim.getSimulationId() %>"
					class="buttonSimToStart" id="sim2">
					<div class="row simName text-center">
						<div class="col-10 SimDescpBtn">
							<h4><%= sim.getName() %></h4>
							<h6 class="infoSimulation">
								SUPPORT ABILITY:
								<%=sim.getSupportAbility() %>
								MAX
							</h6>
							<h6 class="infoSimulation">
								SYNCHRONIZATION RATE:
								<%=sim.getSynchronizationRate() %>
								MAX
							</h6>
							<h6 class="infoSimulation">
								TACTICAL ABILITY:
								<%=sim.getTacticalAbility() %>
								MAX
							</h6>
							<h6 class="infoSimulation">
								DURATION: <%=sim.getDurationTime() %>:00 MINUTES
							</h6>
						</div>
						<div class="col-2 startSimBtn" style="writing-mode: sideways-lr;">
							<h3>START</h3>
						</div>

					</div>

				</button>
				<!-- fine for bottone-->
				<%
            }
        }
    } else {
%>
				<h1>Nessuna simulazione disponibile.</h1>
				<%
    }
%>
			</div>
		</div>

		<!--FINE CONTENITORE SIMULATION--->
		<!-- INIZIO SEND TO SIMULATION -->


		<%
	if (user.getSimulations() != null && !user.getSimulations().isEmpty()) {
		for (Simulation sim : user.getSimulations()) {

				
					
%>
		<div class="col-4 simMembHide" id="simMembBox" style="display: none;">

			<form action="<%=request.getContextPath() + "/simulation"%>"
				method="post" class="row infoSimBox justify-content-center">

				<% 
                for(int i = 0; i < user.getMembers().size(); i++){
					member = user.getMembers().get(i);
					memberStats = member.getMemberStats();
						if(memberStats !=null && memberStats.getStatus() == true){
                %>

				<div
					class="row memberSpecImg  justify-content-center align-items-center">
					<div class="col-8 simMembCol11">
						<label class="radioSimMembLabel"> <input type="hidden"
							id="simIdInput" name="simulationId" value="">
							<button name="memberSelect" value="<%= member.getIdMember() %>"
								class="memberStartBtn">
								<img class="imgSimMember" alt="member image"
									src=" <%= "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/media/memberImage" + i + ".png"%>">
							</button>

						</label>
					</div>
					<div class="col-3">
						<p style="font-weight: bold;">
							LEVEL:
							<%=member.getMemberStats().getLevel() %>
						</p>
						<p style="font-weight: bold;">
							SR:
							<%=member.getMemberStats().getSynchronizationRate() %>%
						</p>
						<p style="font-weight: bold;">
							TA:
							<%=member.getMemberStats().getTacticalAbility() %>%
						</p>
						<p style="font-weight: bold;">
							SA:
							<%=member.getMemberStats().getSupportAbility() %>%
						</p>
					</div>
				</div>

				<%
						}
                }
				%>
			</form>
		</div>
		<%			 
			} 
		} else {
%>
		<h1>Nessuna simulazione disponibile.</h1>
		<%  
    }
%>
		<!-- END SEND SIMULATION -->
		<!-- END MEMBERS PART -->


		<!--INIZIO CONTENITORE MISSION-->
		<div id="mission" class="col-4 overflow-auto"
			style="max-height: 100%; display: none;">
			<div
				class="row simulationBox justify-content-center align-items-center">
				<button class="buttonMissionArchive" id="goToMissionArchive">
					<div class="row missionArchiveDiv text-center align-items-end">
						<h3>MISSION ARCHIVE</h3>
					</div>
				</button>

				<%
               
                
                
                ///----------------
                //se ci sono missioni allora ciclale
                for (Map.Entry<String, List<MissionArchive>> entry : missionArchiveMap.entrySet()) { 
                    List<MissionArchive> missionArchives = entry.getValue(); 
                    MissionArchive lastMission = missionArchives.get(missionArchives.size() - 1);  // Ottieni l'ultimo elemento

	                if(lastMission.getResult() == MissionResult.LOSE){
	                	for(Mission miss : user.getMissions()){
	                		if(lastMission.getMission().getMissionId().equals(miss.getMissionId())){
	                	%>

				<button id=btnIdMission<%=miss.getMissionId()%>
					data-missId="<%=miss.getMissionId()%>"
					data-missMemberMax="<%=miss.getParticipantsMax() %>"
					data-missSuppAb="<%=miss.getSupportAbility() %>"
					data-missSynchRate="<%=miss.getSynchronizationRate() %>"
					data-missTactAb="<%=miss.getTacticalAbility() %>"
					data-missLevelMin="<%=miss.getLevel() %>"
					class="buttonIdMissionToStart" style="background-color: #2b2a33;">
					<div class="row mt-2 missDescpBtn">
						<img
							src="../../media/mission/<%=miss.getName()%>.jpg"
							style="max-width: 100%;">
						<div class="overlay"></div>
						<div class="infoMission">
							<h5>
								SUPPORT ABILITY:
								<%=miss.getSupportAbility() %></h5>
							<h5>
								SYNCHRONIZATION RATE:
								<%=miss.getSynchronizationRate() %></h5>
							<h5>
								TACTICAL ABILITY:
								<%=miss.getTacticalAbility() %></h5>
							<H5>
								LEVEL MIN:
								<%=miss.getLevel() %></H5>
							<h5>
								PARTICIPANT:
								<%=miss.getParticipantsMax() %>
								MAX
							</h5>
							<h5>
								DURATION: <%=miss.getDurationTime() %>:00 MINUTES
							</h5>
						</div>
					</div>
					<div class="row text-center">
						<h4>
							<%= miss.getName() %></h4>
					</div>
				</button>

				<%
	                		}
	                		
	                		
	                		
	                		
	                		
	                		
	                		%>
				<div id="missMembBox<%=miss.getMissionId() %>" class="missMembBox"
					style="position: absolute; z-index: 2; top: 0; left: 0; width: 100%; height: 87.4%; margin-top: 3%; overflow: auto; display: none;">
					<div class="row justify-content-center"
						style="height: 100%; position: relative;">
						<div class="col-4"
							style="background-color: whitesmoke; font-size: 20px; text-align: center; padding: 10px; height: 100%; overflow: auto;">


							<div style="font-weight: bold;"><%=miss.getName() %></div>


							<div
								style="height: 50%; max-height: 50%; background-color: #2b2a33; margin-top: 10px; overflow: hidden;">
								<img
									src="../../media/mission/<%=miss.getName()%>.jpg"
									alt="immagine"
									style="width: 100%; height: 100%; object-fit: contain;">
							</div>

							<div
								style="margin-top: 10px; text-align: justify; font-size: 16px;">
								<%=miss.getDescription() %>
							</div>


							<div style="margin-top: 10px;">
								<div style="font-weight: bold;">REQUIREMENTS</div>

								<table class="table table-bordered"
									style="font-size: 14px; margin-top: 5px;">
									<thead>
										<tr>
											<th>LEVEL</th>
											<th>SR</th>
											<th>SA</th>
											<th>TA</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td><%=miss.getLevel() %></td>
											<td><%=miss.getSynchronizationRate() %></td>
											<td><%=miss.getSupportAbility() %></td>
											<td><%=miss.getTacticalAbility() %></td>
										</tr>
									</tbody>
								</table>
							</div>

						</div>

						<div class="col-6"
							style="background-color: whiteSmoke; font-size: 20px; text-align: center; padding: 10px; height: 100%; overflow: auto;">
							<form class="mt-5"
								action="<%=request.getContextPath() + "/mission"%>"
								method="post">
								<input type="hidden" name="missionId"
									value="<%= miss.getMissionId() %>">
								<% 

	                		    for(int i = 0; i < user.getMembers().size(); i++) {
	                		        member = user.getMembers().get(i);
	                		        memberStats = member.getMemberStats();


	                		        if(memberStats != null && memberStats.getStatus() == true && memberStats.getLevel() >= miss.getLevel()){
	                		    %>


								<div style="display: inline-block; width: 30%; margin-bottom: 20px; background-color: whitesmoke;">

									<div style="height: 60%; width: 100%; margin-bottom: 10px; overflow: hidden;">

										<label
											for="checkbox-M<%=miss.getMissionId() %>-Mem<%= member.getIdMember() %>">
											<img class="imgSimMember" alt="member image"
											src="<%= "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/media/mission_memberImage" + i + ".png" %>"
											alt="immagine"
											style="width: 100%; height: 100%; object-fit: contain;">
										</label> <input type="checkbox" name="memberSelect"
											value="<%= member.getIdMember() %>"
											class="form-check-input member-checkbox"
											id="checkbox-M<%=miss.getMissionId() %>-Mem<%= member.getIdMember() %>"
											data-membSuppAb="<%= member.getMemberStats().getSupportAbility() %>"
											data-membSynchRate="<%= member.getMemberStats().getSynchronizationRate() %>"
											data-membTactAb="<%= member.getMemberStats().getTacticalAbility() %>">
									</div>

									<div
										style="font-weight: bold; font-size: 24px; margin-bottom: 10px;"><%=member.getName() %>
										<%=member.getSurname() %></div>

									<table class="table table-bordered"
										style="font-size: 14px; margin-top: 10px;">
										<thead>
											<tr>
												<th>Livello</th>
												<th>SR</th>
												<th>SA</th>
												<th>TA</th>
											</tr>
										</thead>
										<tbody>
											<tr>
												<td><%=memberStats.getLevel() %></td>
												<td><%=memberStats.getSynchronizationRate() %></td>
												<td><%=memberStats.getSupportAbility() %></td>
												<td><%=memberStats.getTacticalAbility() %></td>
											</tr>
										</tbody>
									</table>

								</div>

								<% 
	                		        }
	                		    }
	                		    // Fine del ciclo per i membri
	                		    %>

								<!-- Sezione probabilità di vittoria -->
								<div
									style="background-color: #f7f7f7; padding: 15px; border-radius: 10px;">
									<table class="table table-bordered"
										style="margin: 0 auto; width: 50%; font-size: 22px; font-weight: bold; color: #333;">
										<tr>
											<th>WIN POSSIBILITY:</th>
											<td class="winProbabilityCell" style="color: green;"></td>
										</tr>
									</table>
								</div>

								<div>
									<button type="submit" class="btn btn-primary btn-lg"
										style="padding: 10px 20px; border-radius: 10px;">
										START</button>
									<button type="button"
										class="btn btn-danger btn-lg closeMission"
										style="padding: 10px 20px; border-radius: 10px;">
										CLOSE</button>
								</div>
							</form>
						</div>
					</div>
				</div>

				<%

	                	}
	                }else if(lastMission.getResult() == MissionResult.PROGRESS){
	                	for(Mission miss : user.getMissions()){
	                		if(lastMission.getMission().getMissionId().equals(miss.getMissionId())){
	                			
	                			LocalDateTime currentMissionTime = null;
	                			LocalDateTime endMissionTime = null;
	                			long durationSeconds = 0;
	                			Duration duration = null;
	                			for(MissionArchive mArc : user.getMissionArchive()){
	                				if(mArc.getMissionCode().equals(lastMission.getMissionCode())){
	                					currentMissionTime = LocalDateTime.now();
			                            endMissionTime = mArc.getEndTime();
			                            Duration.between(currentMissionTime, endMissionTime);	
			                            
			                            if (currentMissionTime != null && endMissionTime != null) {
			                                duration = Duration.between(currentMissionTime, endMissionTime);
			                            }
	                				}
	                				
	                			}   
	                			if (duration != null) {
	                		        durationSeconds = duration.getSeconds();
	                		    }
	                			if(duration != null && (duration.isZero() || duration.isNegative())){
		                            	
		                            	 %>

				<form class="formMissionComp"
					action="<%=request.getContextPath() + "/missionCompleted"%>"
					method="post">
					<input type="hidden" name="missionId"
						value="<%=miss.getMissionId() %>">
					<button type="submit" class="buttonSimCompleted">
						<span class="SimDescpBtn">
							<h3><%=miss.getName() %></h3>
							<h3>COMPLETED!</h3>
						</span> <span class="startSimBtn">
							<h3>REDEEM</h3>
						</span>
					</button>
				</form>
				<%
		                            	
		                            }else{
		                            	
		                            	%>

				<button class=buttonMissionInfo id="sim1">
					<div class="row simName text-center align-items-end">
						<h3><%=miss.getName() %></h3>
						<h4 class="missStatusTrue">ON EXECUTION</h4>
						<%
		    			                        for(MissionParticipants missPart : user.getMissionParticipants()){

		    			                        	for(Member memb : members){
		    			                        	
		    			                        		if(memb.getIdMember().equals(missPart.getMemberId())){
		    				                        		%>

						<h4>
							MEMBER:
							<%=memb.getName().toUpperCase() %>
							<%=memb.getSurname().toUpperCase() %></h4>

						<%
		    				                        	}
		    				                        }
		    			                        }
		    			                        
		             							%>
						<h4 class="duration">
							DURATION: <span class="timeLeft"><%= durationSeconds %></span>
							MINUTES
						</h4>
					</div>
				</button>

				<%
		                            	
		                            }
		                            break;
	                			
	                		}
	                	}
	                }
                }
                
                
              ///se l'archivio è vuoto o se la missione esiste ma non c'è nell'archivio
                for(Mission miss : user.getMissions()){
                	boolean found = false;
                    	for(MissionArchive mArc : user.getMissionArchive()){
                    		if(mArc.getMission().getMissionId().equals(miss.getMissionId())){
                    			 found = true;
                    	         break;
                    		}
                    	}
                    if(missionArchiveMap.isEmpty()){
                    	found= false;
                	}

                	 if (!found) {
                		 %>
				<button id=btnIdMission<%=miss.getMissionId() %>
					data-missId="<%=miss.getMissionId()%>"
					data-missMemberMax="<%=miss.getParticipantsMax() %>"
					data-missSuppAb="<%=miss.getSupportAbility() %>"
					data-missSynchRate="<%=miss.getSynchronizationRate() %>"
					data-missTactAb="<%=miss.getTacticalAbility() %>"
					data-missLevelMin="<%=miss.getLevel() %>"
					class="buttonIdMissionToStart" style="background-color: #2b2a33;">
					<div class="row mt-2 missDescpBtn">
						<img
							src="../../media/mission/<%=miss.getName()%>.jpg"
							style="max-width: 100%;">
						<div class="overlay"></div>
						<div class="infoMission">
							<h5>
								SUPPORT ABILITY:
								<%=miss.getSupportAbility() %>%
							</h5>
							<h5>
								SYNCHRONIZATION RATE:
								<%=miss.getSynchronizationRate() %>%
							</h5>
							<h5>
								TACTICAL ABILITY:
								<%=miss.getTacticalAbility() %>%
							</h5>
							<h5>
								LEVEL MIN:
								<%=miss.getLevel() %></h5>
							<h5>
								PARTICIPANT:
								<%=miss.getParticipantsMax() %>
								MAX
							</h5>
							<h5>
								DURATION: <%=miss.getDurationTime() %>:00 MINUTES
							</h5>
						</div>
					</div>
					<div class="row text-center">
						<h4>
							<%= miss.getName() %></h4>
					</div>
				</button>
				<%	
             			%>



				<div id="missMembBox<%=miss.getMissionId() %>" class="missMembBox"
					style="position: absolute; z-index: 2; top: 0; left: 0; width: 100%; height: 87.4%; margin-top: 3%; overflow: auto; display: none;">
					<div class="row justify-content-center"
						style="height: 100%; position: relative;">
						<div class="col-4"
							style="background-color: whitesmoke; font-size: 20px; text-align: center; padding: 10px; height: 100%; overflow: auto;">

							<div style="font-weight: bold;"><%=miss.getName() %></div>

							<div
								style="height: 50%; max-height: 50%; background-color: #2b2a33; margin-top: 10px; overflow: hidden;">
								<img
									src="../../media/mission/<%=miss.getName()%>.jpg"
									alt="immagine"
									style="width: 100%; height: 100%; object-fit: contain;">
							</div>

							<div
								style="margin-top: 10px; text-align: justify; font-size: 16px;">
								<%=miss.getDescription() %>
							</div>

							<div style="margin-top: 10px;">
								<div style="font-weight: bold;">REQUIREMENTS</div>

								<table class="table table-bordered"
									style="font-size: 14px; margin-top: 5px;">
									<thead>
										<tr>
											<th>LEVEL</th>
											<th>SR</th>
											<th>SA</th>
											<th>TA</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td><%=miss.getLevel() %></td>
											<td><%=miss.getSynchronizationRate() %></td>
											<td><%=miss.getSupportAbility() %></td>
											<td><%=miss.getTacticalAbility() %></td>
										</tr>
									</tbody>
								</table>
							</div>

						</div>

						<div class="col-6"
							style="background-color: whiteSmoke; font-size: 20px; text-align: center; padding: 10px; height: 100%; overflow: auto;">
							<form class="mt-5"
								action="<%=request.getContextPath() + "/mission"%>"
								method="post">
								<input type="hidden" name="missionId"
									value="<%= miss.getMissionId() %>">
								<% 

			    for(int i = 0; i < user.getMembers().size(); i++) {
			        member = user.getMembers().get(i);
			        memberStats = member.getMemberStats();
			
			        if(memberStats != null && memberStats.getStatus() == true && memberStats.getLevel() >= miss.getLevel()){
			    %>

								<div
									style="display: inline-block; width: 30%; margin-bottom: 20px; background-color: whitesmoke;">
									<div
										style="height: 60%; width: 100%; margin-bottom: 10px; overflow: hidden;">

										<label
											for="checkbox-M<%=miss.getMissionId() %>-Mem<%= member.getIdMember() %>">
											<img class="imgSimMember" alt="member image"
											src="<%= "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/media/mission_memberImage" + i + ".png" %>"
											alt="immagine"
											style="width: 100%; height: 100%; object-fit: contain;">
										</label> <input type="checkbox" name="memberSelect"
											value="<%= member.getIdMember() %>"
											class="form-check-input member-checkbox"
											id="checkbox-M<%=miss.getMissionId() %>-Mem<%= member.getIdMember() %>"
											data-membSuppAb="<%= member.getMemberStats().getSupportAbility() %>"
											data-membSynchRate="<%= member.getMemberStats().getSynchronizationRate() %>"
											data-membTactAb="<%= member.getMemberStats().getTacticalAbility() %>">
									</div>

									<div
										style="font-weight: bold; font-size: 24px; margin-bottom: 10px;"><%=member.getName() %>
										<%=member.getSurname() %></div>

									<table class="table table-bordered"
										style="font-size: 14px; margin-top: 10px;">
										<thead>
											<tr>
												<th>Livello</th>
												<th>SR</th>
												<th>SA</th>
												<th>TA</th>
											</tr>
										</thead>
										<tbody>
											<tr>
												<td><%=memberStats.getLevel() %></td>
												<td><%=memberStats.getSynchronizationRate() %></td>
												<td><%=memberStats.getSupportAbility() %></td>
												<td><%=memberStats.getTacticalAbility() %></td>
											</tr>
										</tbody>
									</table>

								</div>
								<% 
			        }
			    }
			    // Fine del ciclo per i membri
			    %>

								<!-- Sezione probabilità di vittoria -->
								<div
									style="background-color: #f7f7f7; padding: 15px; border-radius: 10px;">
									<table class="table table-bordered"
										style="margin: 0 auto; width: 50%; font-size: 22px; font-weight: bold; color: #333;">
										<tr>
											<th>WIN POSSIBILITY:</th>
											<td class="winProbabilityCell" style="color: green;"></td>
										</tr>
									</table>
								</div>

								<div>
									<button type="submit" class="btn btn-primary btn-lg"
										style="padding: 10px 20px; border-radius: 10px;">
										START</button>
									<button type="button"
										class="btn btn-danger btn-lg closeMission"
										style="padding: 10px 20px; border-radius: 10px;">
										CLOSE</button>
								</div>
							</form>
						</div>
					</div>
				</div>

				<%
                	 }
                }               
                %>
			</div>
		</div>

		<!--FINE CONTENITORE MISSION--->

		<!--INIZIO CONTENITORE MISSION ARCHIVE-->
		<div id="missionArchive" class="col-8 overflow-auto"
			style="position: absolute; z-index: 1; top: 50%; left: 50%; transform: translate(-50%, -50%); height: 60%; max-height: 100%; display: none;">
			<button id="closeMissionArchive" class="btn-close"
				style="position: absolute; top: 10px; right: 10px; border: none; color: black; cursor: pointer;"></button>
			<div class="missionArchiveBox">
				<%



    // Raggruppa le missioni per codice
    Map<String, List<MissionArchive>> groupedMissionArchive = new HashMap<>();
    for (MissionArchive ma : user.getMissionArchive()) {
        String missionCode = ma.getMissionCode();
        if (!groupedMissionArchive.containsKey(missionCode)) {
            groupedMissionArchive.put(missionCode, new ArrayList<>());
        }
        groupedMissionArchive.get(missionCode).add(ma);
    }

    // Itera per visualizzare le missioni raggruppate
    for (Map.Entry<String, List<MissionArchive>> entry : groupedMissionArchive.entrySet()) {
        String missionCode = entry.getKey();
        List<MissionArchive> missionList = entry.getValue();
        MissionArchive firstMission = missionList.get(0); 
%>
				<table class="table table-striped table-bordered"
					style="width: 100%; text-align: center; border-collapse: collapse;">
					<thead>
						<tr>
							<th>MISSION: <%= firstMission.getMission().getName() %></th>
							<th>Member</th>
							<th>TA</th>
							<th>SR</th>
							<th>SA</th>
							<th>Date</th>
							<th>Result</th>
						</tr>
					</thead>
					<tbody>
						<% 
                   
                    boolean isFirstRow = true;
                    for (MissionArchive ma : missionList) {
                        for (Member memb : members) {
                            if (memb.getIdMember().equals(ma.getMemberId())) {
                                if (isFirstRow) {
                %>
						<tr>
							<td rowspan="<%= missionList.size() %>">CODE <br> <%= missionCode %></td>
							<% 
                                        isFirstRow = false;
                                    }
                %>
							<td><%= memb.getName() %> <%= memb.getSurname() %></td>
							<td><%= ma.getTacticalAbility() %></td>
							<td><%= ma.getSynchRate() %></td>
							<td><%= ma.getSupportAbility() %></td>
							<td><%= ma.getStartTime().toLocalDate() %></td>
							<td><%= ma.getResult() %></td>
						</tr>
						<% 
                            }
                        }
                    }
                %>
					</tbody>
				</table>
				<%
    }
%>
			</div>
		</div>

		<!--FINE CONTENITORE MISSION ARCHIVE--->


		<!-- IN PROVA, TUTTO DA SPOSTARE -->
	</main>
	<footer class="row">
		<h3>Nerv Management Console Project</h3>
	</footer>
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<script
		src="<%="http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/js/script.js"%>"></script>
</body>
</html>