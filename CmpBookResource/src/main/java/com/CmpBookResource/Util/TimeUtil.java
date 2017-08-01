package com.CmpBookResource.Util;

import java.util.TimeZone;

/**
 * 时间工具类，用于判断两个时间是否位于同一天
 * 
 * @author Shane
 * @version 1.0
 */
public class TimeUtil {
	public static final int SECONDS_IN_DAY = 60 * 60 * 24;
	public static final long MILLIS_IN_DAY = 1000L * SECONDS_IN_DAY;

	/**
	 * 判断两个时间戳是否为同一天
	 * 
	 * @param ms1
	 *            第一个时间戳
	 * @param ms2
	 *            第二个时间戳
	 * @return 如果为同一天，返回true，否则返回false
	 */
	public static boolean isSameDayOfMillis(final long ms1, final long ms2) {
		final long interval = ms1 - ms2;
		return interval < MILLIS_IN_DAY && interval > -1L * MILLIS_IN_DAY && toDay(ms1) == toDay(ms2);
	}

	/**
	 * 将毫秒级时间戳转化为天
	 * 
	 * @param millis
	 *            待转化的时间戳
	 * @return 转化后的天
	 */
	private static long toDay(long millis) {
		return (millis + TimeZone.getDefault().getOffset(millis)) / MILLIS_IN_DAY;
	}
}
