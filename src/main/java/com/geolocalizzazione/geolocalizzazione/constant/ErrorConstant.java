package com.geolocalizzazione.geolocalizzazione.constant;

public class ErrorConstant {


    public final static String BAD_REQUEST="Errore nei valori inviati in input";

    //AUTOMEZZO
    //AUTISTA
    public final static String AUTISTA_DUPLICATE="Autista con nome e cognome già esistente";

    //PATENTE
    public final static String PATENTE_NOT_FOUND="Patente non trovata nel sistema";

    //ASSICURAZIONE
    public final static String ASSICURAZIONE_BAD_REQUEST="Errore nella valorizzazione di una nuova assicurazione";
    public final static String NUMERO_POLIZZA_EXISTIS="Il numero di polizza esiste già";

    //DOCUMENTI
    public final static String DOCUMENT_NOT_FOUND="Nessun documento trovato";

    //PERCORSO
    public final static String PERCORSO_NOT_FOUND= "Percorso non trovato";
    public final static String PERCORSO_TERMINATO= "Percorso non modificabile in quanto risulta terminato";
    public final static String PERCORSO_NOT_ARCHIVIATO= "Percorso non archiviabile in quanto non risulta essere terminato";
    public final static String PERCORSO_NOT_CLOSE= "Percorso non terminabile in quanto esistono delle consegne non gestite";


}
