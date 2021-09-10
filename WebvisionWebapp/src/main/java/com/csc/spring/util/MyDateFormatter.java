package com.csc.spring.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MyDateFormatter {

    /**
     * This allows us to set the Date in a predefined format
     * @param dateFormat
     * @return
     */
    public String setDate(String dateFormat){
        Date tempDate = Calendar.getInstance().getTime();
        DateFormat format = new SimpleDateFormat(dateFormat);
        return format.format(tempDate);

    }
}
