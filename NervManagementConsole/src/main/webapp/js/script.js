$(document).ready(function () {
    $("#buttonBoxMember").click(function () {
        // Nascondi tutte i div se sono visibili
        $(".infoBoxHide:visible").hide();
        $(".simulationBoxHide:visible").hide();
        $("#simulation").hide();
        $("#simMembBox").hide();
        $("#mission").hide();
        $("#missMembBox").hide();

        $("#memberPage").fadeToggle();
    });

    $(".buttonMemberInfo").click(function () {
        $(".infoBoxHide").hide();

        var memberId = $(this).attr('id');
        var memberIndex = memberId.replace("member", ""); 
        var infoBoxId = "infoBox" + memberIndex;

        $("#" + infoBoxId).fadeToggle();
    });

    $("#buttonBoxSimulations").click(function () {
        $(".infoBoxHide:visible").hide();
        $("#memberPage").hide();
        $("#mission").hide();
        $("#missMembBox").hide();
        $(".simulationBoxHide:visible").hide();
        $("#simMembBox").hide();

        $("#simulation").fadeToggle();
    });

    $(".buttonSimToStart").click(function () {
        $(".infoBoxHide:visible").hide();
        $("#memberPage").hide();
        $("#mission").hide();
        $("#missMembBox").hide();
        $("#simMembBox").fadeToggle();
    });

    $("#buttonBoxMissions").click(function () {
        $("#memberPage").hide();
        $(".infoBoxHide:visible").hide();
        $(".simulationBoxHide:visible").hide();
        $("#simulation").hide();
        $("#simMembBox").hide();
        $("#missMembBox").hide();
        $("#mission").fadeToggle();
        $("#memberPage").hide();
    });


    //ci aiuta nella simulationServlet
    $("button.buttonSimToStart").click(function () {
        var simulationId = $(this).attr('data-simId');
        $('#simIdInput').val(simulationId);
    });

    function handleMissionButtonClick() {
        var missionId = $(this).attr('data-missId');
        $('#missIdInput').val(missionId);
        console.log("Mission ID: " + missionId);
        let missionMemberMax = parseInt($(this).attr('data-missMemberMax'));
        console.log("MissionMemberMax: " + missionMemberMax);
        resetCheckboxes();
        setupCheckboxChangeListener(missionMemberMax);
        $('.winProbabilityCell').text(0 + " %");
        retrieveMissionData($(this));
    }

    function resetCheckboxes() {
        $('.member-checkbox').prop('checked', false);
        $('.member-checkbox').off('change');
        suppAbilityArray = [];
        synchRateArray = [];
        tactAbilityArray = [];
        winPoss = 0;
    }

    function setupCheckboxChangeListener(missionMemberMax) {
        $('.member-checkbox').change(function () {
            let selectedCheckboxes = $('.member-checkbox:checked');
            if (selectedCheckboxes.length > missionMemberMax) {
                $(this).prop('checked', false);
                console.log("Alert Max Partecipanti si è attivato");
                alert(`Puoi selezionare al massimo ${missionMemberMax} membri.`);

                const membrSupportAbility = parseInt($(this).attr("data-membSuppAb"));
                const membrSynchRate = parseInt($(this).attr("data-membSynchRate"));
                const membrTactAbility = parseInt($(this).attr("data-membTactAb"));

                const suppIndex = suppAbilityArray.indexOf(membrSupportAbility);
                const synchIndex = synchRateArray.indexOf(membrSynchRate);
                const tactIndex = tactAbilityArray.indexOf(membrTactAbility);

                if (suppIndex > -1) suppAbilityArray.splice(suppIndex, 1);
                if (synchIndex > -1) synchRateArray.splice(synchIndex, 1);
                if (tactIndex > -1) tactAbilityArray.splice(tactIndex, 1);
            }
        });
    }

    //ArrayGlobali per fare poi la media ab membri
    let suppAbilityArray = [];
    let synchRateArray = [];
    let tactAbilityArray = [];
    
    // Funzione di test
    function handleMissionMemberButtonClick() {
        var membrSupportAbility = parseInt($(this).attr("data-membSuppAb"));
        var membrSynchRate = parseInt($(this).attr("data-membSynchRate"));
        var membrTactAbility = parseInt($(this).attr("data-membTactAb"));

        if ($(this).is(":checked")) {
            suppAbilityArray.push(membrSupportAbility);
            synchRateArray.push(membrSynchRate);
            tactAbilityArray.push(membrTactAbility);
        } else {
            const suppIndex = suppAbilityArray.indexOf(membrSupportAbility);
            const synchIndex = synchRateArray.indexOf(membrSynchRate);
            const tactIndex = tactAbilityArray.indexOf(membrTactAbility);

            if (suppIndex > -1) suppAbilityArray.splice(suppIndex, 1);
            if (synchIndex > -1) synchRateArray.splice(synchIndex, 1);
            if (tactIndex > -1) tactAbilityArray.splice(tactIndex, 1);
        }

        console.log("SUPPORT ABILITY ARRAY:", suppAbilityArray);
        console.log("SYNCH RATE ARRAY:", synchRateArray);
        console.log("TACT ABILITY ARRAY:", tactAbilityArray);

        let suppAverage = calculateAverage(suppAbilityArray);
        let synchAverage = calculateAverage(synchRateArray);
        let tactAverage = calculateAverage(tactAbilityArray);

        console.log("Media Support Ability:", suppAverage);
        console.log("Media Synchronization Rate:", synchAverage);
        console.log("Media Tactical Ability:", tactAverage);

        winPossibility(suppAverage, synchAverage, tactAverage);
    }

    function calculateAverage(array) {
        if (array.length === 0) return 0;
        const sum = array.reduce((acc, val) => acc + val, 0);
        return sum / array.length;
    }

    ///////////////////////////
    let missionSuppAbility = 0;
    let missionSynchRate = 0;
    let missionTactAbility = 0;

    // Funzione per recuperare i dati della missione
    function retrieveMissionData(button) {
        missionSuppAbility = parseInt(button.attr('data-missSuppAb'));
        missionSynchRate = parseInt(button.attr('data-missSynchRate'));
        missionTactAbility = parseInt(button.attr('data-missTactAb'));

        console.log("LOG MISSION SA " + missionSuppAbility);
        console.log("LOG MISSION SR " + missionSynchRate);
        console.log("LOG MISSION TA " + missionTactAbility);
    }

    let winPoss = 0;
    function winPossibility(suppAverage, synchAverage, tactAverage) {
        winPoss = 0;
        suppAverage
        synchAverage
        tactAverage
        missionSuppAbility
        missionSynchRate
        missionTactAbility
        if (suppAverage == 0 && synchAverage == 0 && tactAverage == 0) {
            winPoss = 0;
        } else {
            const losePossibility = Math.max(0, missionSuppAbility - suppAverage) + Math.max(0, missionSynchRate - synchAverage) + Math.max(0, missionTactAbility - tactAverage) + 25;
            console.log("LOSE POSSIBILTY" + losePossibility + " %");
            winPoss = Math.floor(100 - losePossibility);
            if (winPoss < 0) {
                winPoss = 0;
            }
        }
        console.log("WIN POSSIBILTY" + winPoss + "%");
        $('.winProbabilityCell').text(winPoss + " %");
    }

    $(document).on('click', 'button.buttonIdMissionToStart', handleMissionButtonClick);
    $("input.member-checkbox").click(handleMissionMemberButtonClick);


    // Funzione per aggiornare il timer
    $(".timeLeft").each(function () {
        let $timeLeftElement = $(this);
        let timeLeft = parseInt($timeLeftElement.text());


        function updateTimer() {
            if (timeLeft > 0) {
                timeLeft--;
                let minutes = Math.floor(timeLeft / 60);
                let seconds = timeLeft % 60;

                minutes = minutes < 10 ? "0" + minutes : minutes;
                seconds = seconds < 10 ? "0" + seconds : seconds;

                $timeLeftElement.html(`${minutes}:${seconds}`);
            } else {
                clearInterval(timerInterval);
                $timeLeftElement.html("00:00");
            }
        }

        let timerInterval = setInterval(updateTimer, 1000);

        if (timeLeft <= 0) {
            clearInterval(timerInterval);
            $timeLeftElement.html("00:00");
        }
    });


    $("#goToMissionArchive").click(function () {
        $("#missionArchive").show();
    });

    $("#closeMissionArchive").click(function () {
        $("#missionArchive").hide();
    });


    $('button[id^="btnIdMission"]').on('click', function () {
        const missionId = $(this).attr('id').replace('btnIdMission', '');

        const missTarget = $(`#missMembBox${missionId}`);

        $(".infoBoxHide:visible").hide();
        $("#memberPage").hide();
        missTarget.fadeToggle();
    });

    $(".closeMission").click(function () {
        $(".missMembBox").hide();
    });

});
