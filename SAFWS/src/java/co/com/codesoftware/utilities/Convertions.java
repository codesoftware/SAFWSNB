/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.codesoftware.utilities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author root
 */
public class Convertions {

    public String dateToString(Date date, String format) {
        DateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }

    /**
     * Funcion que convierte de String a date
     *
     * @param date
     * @param format
     * @return
     */
    public Date stringToDate(String date, String format) {
        Date result = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            result = formatter.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
