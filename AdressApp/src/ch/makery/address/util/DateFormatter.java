package ch.makery.address.util;

/**
 * Classe auxiliar para formatação de datas
 * 
 * @author Diego Demétrio
 */
public class DateFormatter {
    
    public static String dateDMY() {
        return "dd/MM/yyyy";
    }

    public static String dateMYD() {
        return "MM/yyyy/dd";
    }

    public static String dateDYM() {
        return "dd/yyyy/MM";
    }

    public static String dateMDY() {
        return "MM/dd/yyyy";
    }

    public static String dateYDM() {
        return "yyyy/dd/MM";
    }

    public static String dateYMD() {
        return "yyyy/MM/dd";
    }  
}
