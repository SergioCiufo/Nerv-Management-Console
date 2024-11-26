# Nerv Management Console

La Nerv Management Console è un'applicazione web progettata per la gestione e l'assegnamento di incarichi al personale NERV, con funzionalità di visualizzazione dei membri, assegnamento a missioni e addestramenti, e monitoraggio dello stato delle missioni completate. Il sistema è accessibile solo agli utenti autorizzati tramite un form di login, con supporto per l'autologin tramite cookie di sessione.
Funzionalità principali

    Login e Autenticazione: La pagina di login permette l'accesso solo agli utenti con credenziali valide (username e password). Se l'autenticazione ha successo, l'utente viene rediretto alla home page; in caso contrario, viene mostrato un errore.

    Autologin: Il sistema supporta l'autologin tramite cookie di sessione. Gli utenti che hanno già effettuato l'accesso non devono reinserire le credenziali.

    Home Page: La home page offre le seguenti funzionalità:
        Visualizzazione dei membri operativi.
        Assegnamento dei membri ad addestramento.
        Visualizzazione delle missioni disponibili.
        Assegnamento dei membri alle missioni.
        Monitoraggio dello stato delle missioni.
        Logout.

    Membri: Ogni membro ha le seguenti caratteristiche:
        Livello: Determina la partecipazione a missioni (tra 0 e 100).
        Tasso di Sincronia EVA: Capacità di pilotare un EVA (tra 0 e 100).
        Abilità di Supporto: Capacità di supportare la squadra (tra 0 e 100).
        Abilità Tattica: Capacità di analizzare la situazione sul campo (tra 0 e 100).

    Addestramento: I membri possono essere assegnati a tre tipologie di addestramento, ognuno con impatti differenti sulle loro abilità:
        Simulazione Attacco Angelo: Migliora il pilotaggio dell'EVA e le abilità tattiche, ma riduce l'abilità di supporto.
        Simulazione Missione Infiltrazione: Migliora l'abilità tattica e di supporto, ma riduce il pilotaggio dell'EVA.
        Simulazione Missione Recupero: Migliora il supporto e il pilotaggio, ma riduce l'abilità tattica.

    Missioni: Ogni missione è caratterizzata da:
        Nome univoco.
        Livello minimo richiesto per i membri.
        Parametri di abilità per pilotaggio EVA, supporto e analisi tattica.
        Numero massimo di partecipanti.

    Assegnamento Missioni: I membri vengono assegnati alle missioni in base alle loro abilità e al livello minimo richiesto. La probabilità di successo della missione è calcolata in base alle abilità dei membri.

    Storico Missioni: È disponibile uno storico delle missioni completate, con dettagli sui membri partecipanti, la data e le statistiche al momento della missione.

Tecnologie

    Java (Servlets, JSP)
    Hibernate ORM per la gestione del database (Senza annotazioni).
    Oracle DB: Il database utilizzato per archiviare tutte le informazioni relative agli utenti, membri, missioni e addestramenti.
    DataSource: Utilizzo di un DataSource per la gestione delle connessioni al database in modo efficiente e sicuro.
    CSS/HTML per l'interfaccia utente
    JavaScript/jQuery per interazioni dinamiche

Dettagli del progetto

Questo progetto è stato sviluppato come esercitazione. È stato progettato per simulare un sistema di gestione di membri, missioni e addestramenti in un contesto fittizio.
