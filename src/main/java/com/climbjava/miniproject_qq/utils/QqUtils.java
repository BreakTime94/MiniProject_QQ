package com.climbjava.miniproject_qq.utils;

import java.text.SimpleDateFormat;
import java.util.Scanner;

public class QqUtils {
	private static final Scanner scanner = new Scanner(System.in);

	public static final SimpleDateFormat DATE_FORMAT_MONTH = new SimpleDateFormat("yyyy-MM");
	public static final SimpleDateFormat DATE_FORMAT_DATE = new SimpleDateFormat("yyyy-MM-dd");

	public static String nextLine(String msg) {
		System.out.print(msg);
		return scanner.nextLine();
	}

	public static int nextInt(String msg) {
		return Integer.parseInt(nextLine(msg));
	}

	public static boolean nextConfirm(String msg) {
		String s = nextLine(msg);
		return s.equalsIgnoreCase("y") || s.equalsIgnoreCase("yes");
	}
}
