package com.aws.codestar.comein.utils;

import java.util.Calendar;
import java.util.Locale;

public class DateUtils {
    public static java.util.Date nowDate() {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        return cal.getTime();
    }
}