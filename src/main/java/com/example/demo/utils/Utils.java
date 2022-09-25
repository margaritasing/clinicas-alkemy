package com.example.demo.utils;

import java.util.Calendar;
import java.util.Date;

public class Utils {

	public static boolean sonElMismoDia(Date fechaUno, Date fechaDos) {
		
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(fechaUno);
		cal2.setTime(fechaDos);
		boolean mismoDia = cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
		return mismoDia;
	}
}
