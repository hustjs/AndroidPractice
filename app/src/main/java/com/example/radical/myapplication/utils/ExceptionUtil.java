package com.example.radical.myapplication.utils;

/**
 * Title.
 * <p/>
 * Description.
 *
 * @author Bill Lv {@literal <billcc.lv@hotmail.com>}
 * @version 1.0
 * @since 2016-07-25
 */
public class ExceptionUtil {
    public static String getRootCauseMessage(final Throwable t) {
        Throwable tCursor = t;
        while (tCursor.getCause() != null) {
            tCursor = tCursor.getCause();
        }
        return tCursor.getMessage();
    }
}
