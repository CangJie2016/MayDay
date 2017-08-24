package com.cangjie.mayday;

import com.cangjie.mayday.utils.MD5;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        double d = 3.2;
        double d2 = Math.floor(d);
        int i = (int)d;
        int i2 = (int)d2;
        assertEquals(i, i2);
    }

    @Test
    public void chartMonth() throws Exception {

        assertEquals(4, 2 + 2);
    }    @Test
    public void chartMonth2() throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);
        String date = format.format(new Date());
        int i = Integer.valueOf(date) * 1024;
        String s = MD5.MD5Encode(String.valueOf(i));
        assertEquals(s, date);
    }
}