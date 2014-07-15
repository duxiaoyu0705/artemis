package com.artemis.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;

public class DateUtil {
	private static final Logger LOG = Logger.getLogger(DateUtil.class);

	/**
	 * ��ȡȥ��ͬ�ڵ�һ��������
	 * 
	 * @return
	 */
	public static Date getThisWeekInLastYear() {
		Date date = getDateOfLastMonth(new Date(), -12);
		date = getFirstDayOfWeek(date);
		return date;
	}

	public static Date getLastWeekendOfLastMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.SUNDAY);
		calendar.setTime(date);
		int month = calendar.get(Calendar.MONTH);
		calendar.set(Calendar.WEEK_OF_MONTH, 1);
		calendar.set(Calendar.DAY_OF_WEEK, 1);
		while (month != calendar.get(Calendar.MONTH)) {
			calendar.add(Calendar.DATE, 7);
		}
		return getFirstDayOfWeek(calendar.getTime());
	}

	/**
	 * ���������õ���һ��������������� ע�⣺�����calendar����ֵͬ����ֵһ��
	 * 
	 * @param calendar
	 * @return
	 */
	public static Calendar getFirstDayOfWeek(Calendar calendar) {
		int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		if (week == 0) {
			week = 7;
		}
		calendar.add(Calendar.DATE, -week);
		return calendar;
	}

	public static boolean isSunday(Calendar calendar) {
		return calendar.get(Calendar.DAY_OF_WEEK) - 1 == 0;
	}

	public static boolean isSunday(Date date) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		return isSunday(cal);
	}

	public static boolean isLastSundayOfMonth(Date date) {
		return isSunday(date)
				&& between(DateUtils.addMonths(DateUtils.truncate(date, Calendar.MONTH), 1), date, Calendar.DAY_OF_MONTH) <= 7;
	}

	public static Date getFirstWeekendOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.SUNDAY);
		calendar.setTime(date);
		int month = calendar.get(Calendar.MONTH);
		calendar.set(Calendar.WEEK_OF_MONTH, 1);
		calendar.set(Calendar.DAY_OF_WEEK, 1);
		while (month != calendar.get(Calendar.MONTH)) {
			calendar.add(Calendar.DATE, 7);
		}
		return truncateDay(calendar.getTime());
	}

	public static Date getCurrentHour() {
		Calendar cal = Calendar.getInstance();
		Calendar now = Calendar.getInstance();
		cal.set(Calendar.YEAR, now.get(Calendar.YEAR));
		cal.set(Calendar.MONTH, now.get(Calendar.MONTH));
		cal.set(Calendar.DAY_OF_MONTH, now.get(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, now.get(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}

	public static Date getFirstDayOfWeek(Date date) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal = getFirstDayOfWeek(cal);
		return truncateDay(cal.getTime());
	}

	public static Date getFirstDayOfMonth(Date date) {
		return getFirstDayOfMonth(date, 0);
	}

	public static Date getFirstDayOfMonth(Date date, int offsetMonth) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1, 0, 0, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.add(Calendar.MONTH, offsetMonth);
		return calendar.getTime();
	}

	public static Date getLastDayOfMonth(Date date) {
		return getLastDayOfMonth(date, 0);
	}

	public static Date getLastEndMonthDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1, 0, 0, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.add(Calendar.MONTH, -1);
		return truncateDay(getLastDayOfMonth(calendar.getTime()));
	}

	public static Date getLastDayOfMonth(Date date, int offsetMonth) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(getFirstDayOfMonth(date, offsetMonth + 1));
		calendar.add(Calendar.SECOND, -1);
		return calendar.getTime();
	}

	/**
	 * ��date truncate������Ϊֹ,ȥ��ʱ����
	 * 
	 * @param date
	 * @return
	 */
	public static Date truncateDay(Date date) {
		return DateUtils.truncate(date, Calendar.DAY_OF_MONTH);
	}

	/**
	 * �õ���һ��ķ�Χ.��������������2007-12-12 12:36:40,�򷵻ص���2007-12-12 00:00:00��2007-12-13
	 * 0:0:0
	 * 
	 * @param now
	 * @return
	 */
	public static Date[] getOneDayRange(Date date) {
		Date[] dates = new Date[2];
		dates[0] = DateUtils.truncate(date, Calendar.DAY_OF_MONTH);
		dates[1] = DateUtils.addDays(dates[0], 1);
		return dates;
	}

	/**
	 * ת�����ڳ�yyyy-mm-dd��String��ʽ
	 * 
	 * @param date
	 * @return
	 */
	public static String formatToYYYYMMDD(Date date) {
		if (date == null)
			return "";
		String result = "";
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			result = format.format(date);
		} catch (Exception e) {
			return "";
		}
		return result;
	}

	public static String formatByPattern(Date date, String pattern) {
		if (date == null)
			return "";
		String result = "";
		try {
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			result = format.format(date);
		} catch (Exception e) {
			return "";
		}
		return result;
	}

	/**
	 * ת�����ڳ�yyyy-mm-dd hh:mm:ss��String��ʽ
	 * 
	 * @param date
	 * @return
	 */
	public static String formatToYYYYMMDDhhmmss(Date date) {
		if (date == null)
			return "";
		String result = "";
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			result = format.format(date);
		} catch (Exception e) {
			return "";
		}
		return result;
	}

	/**
	 * parse date from yyyyMMddHHmmss fromat
	 * 
	 * @param dateStr
	 * @return
	 */
	public static Date parseDate(String dateStr) {
		return parseDate(dateStr, "yyyyMMddHHmmss");
	}

	/**
	 * parse date from yyyyMMddHHmmss fromat
	 * 
	 * @param dateStr
	 * @return
	 */
	public static Date parseDate(String dateStr, String pattern) {
		Date date = null;
		if (StringUtils.isNotBlank(dateStr)) {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			try {
				date = sdf.parse(dateStr);
			} catch (ParseException e) {
				LOG.error("Error parseDate " + dateStr + " pattern:" + pattern, e);
			}
		}
		return date;
	}

	/**
	 * ת�����ڳ�yyyymmddhhmmss��String��ʽ
	 * 
	 * @param date
	 * @return
	 */
	public static String formatToTightYYYYMMDDhhmmss(Date date) {
		if (date == null)
			return "";
		String result = "";
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			result = format.format(date);
		} catch (Exception e) {
			return "";
		}
		return result;
	}

	/**
	 * ת�����ڳ�yyyymmdd��String��ʽ
	 * 
	 * @param date
	 * @return
	 */
	public static String formatToTightYYYYMMDD(Date date) {
		if (date == null)
			return "";
		String result = "";
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			result = format.format(date);
		} catch (Exception e) {
			return "";
		}
		return result;
	}

	/**
	 * ת������HHmmss��String��ʽ
	 * 
	 * @param date
	 * @return
	 */
	public static String formatToTightHHmmss(Date date) {
		if (date == null)
			return "";
		String result = "";
		try {
			SimpleDateFormat format = new SimpleDateFormat("HHmmss");
			result = format.format(date);
		} catch (Exception e) {
			return "";
		}
		return result;
	}

	/**
	 * ת�����ڳ�yymmdd��String��ʽ
	 * 
	 * @param date
	 * @return
	 */
	public static String formatToTightYYMMDD(Date date) {
		if (date == null)
			return "";
		String result = "";
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyMMdd");
			result = format.format(date);
		} catch (Exception e) {
			return "";
		}
		return result;
	}

	/**
	 * ת�����ڳ�yy-mm-dd��String��ʽ
	 * 
	 * @param date
	 * @return
	 */
	public static String formatToYYMMDD(Date date) {
		if (date == null)
			return "";
		String result = "";
		try {
			SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd");
			result = format.format(date);
		} catch (Exception e) {
			return "";
		}
		return result;
	}

	/***************************************************************************
	 * ����֮ǰ�����»�֮�󼸸��µĽ���
	 * 
	 * @param date
	 * @param num
	 * @return
	 */
	public static Date getDateOfLastMonth(Date date, int num) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(Calendar.MONTH, num);

		return cal.getTime();
	}

	public static Date getAnalyDate(Date date, int dateEnum, int num) {

		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(dateEnum, num);

		return cal.getTime();
	}

	/***************************************************************************
	 * ���ݴ���������Լ����ֻ�õ�ǰ����֮ǰ����֮���·ݵ���ʼ���� �紫��2007-06-21��6���� ����Ϊ2007-12-1��date
	 * �紫��2007-06-21��-6���� ����Ϊ2006-12-1��date
	 * 
	 * @param date
	 * @param num
	 * @return
	 */
	public static Date getBeforeDate(Date date, int num) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(Calendar.MONTH, num);
		cal.set(GregorianCalendar.DAY_OF_MONTH, 1);

		return truncateDay(cal.getTime());
	}

	public static Date getBeforeDay(Date date, int num) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(Calendar.DATE, -num);

		return truncateDay(cal.getTime());
	}

	public static Date getBeforeHour(Date date, int num) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(Calendar.HOUR, -num);

		return cal.getTime();
	}

	public static Date getAfterDay(Date date, int num) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(Calendar.DATE, num);

		return truncateDay(cal.getTime());
	}

	public static Date getAfterDay2second(Date date, int num) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(Calendar.DATE, num);

		return cal.getTime();
	}

	/**
	 * ��ȡ��������date�������num��������б�<br>
	 * <b>ע�⣺��ʱ���������date, ��������õ���������Ϊfalse</b>
	 * 
	 * @param date
	 * @param num
	 * @return
	 */
	public static List<Date> getBeforeDates(Date date, int num) {
		return getBeforeDates(date, num, true);
	}

	/**
	 * ��ȡ��������date�������num��������б�
	 * 
	 * @param date
	 * @param num
	 * @param contains
	 *            �Ƿ����date
	 * @return
	 */
	public static List<Date> getBeforeDates(Date date, int num, boolean contains) {
		List<Date> list = new ArrayList<Date>();

		if (num == 0) {
			list.add(date);
			return list;
		}

		// int n = num;
		while (num != 0) {
			// for(int n = num; n<num;n++){
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(date);
			cal.add(Calendar.DATE, num);
			list.add(truncateDay(cal.getTime()));
			if (num > 0)
				num = num - 1;
			else if (num < 0)
				num = num + 1;
			// }
		}
		if (contains) {
			list.add(date);
		}
		return list;
	}

	public static int getYear(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);

	}

	public static int getHour(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.HOUR_OF_DAY);

	}

	public static int getMinute(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MINUTE);

	}

	public static int getMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH) + 1;

	}

	public static int getDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_MONTH);

	}

	public static Date buildLastDateOfMonth(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, 1, 23, 59, 59);
		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		return calendar.getTime();
	}

	/**
	 * eg: getAfterDays(2007-10-4 12:32:00, 2007-10-3 10:00:00) == 1
	 * getAfterDays(2007-10-4 12:32:00, 2007-10-3 14:00:00) == 0
	 * getAfterDays(2007-10-1 12:32:00, 2007-10-3 14:00:00) == -2
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int getAfterDays(Date date1, Date date2) {
		long times = truncateDay(date1).getTime() - truncateDay(date2).getTime();
		return (int) (times / 1000 / 3600 / 24);
	}

	public static int compareIgnoreDay(Date date1, Date date2) {
		Calendar calendar1 = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		calendar1.setTime(date1);
		calendar2.setTime(date2);
		calendar1.set(2000, 1, 1);
		calendar2.set(2000, 1, 1);
		return calendar1.compareTo(calendar2);
	}

	public static int compare(Date date1, Date date2, int calendar) {
		date1 = DateUtils.truncate(date1, calendar);
		date2 = DateUtils.truncate(date2, calendar);
		return date1.compareTo(date2);
	}

	public static int compare(Date date1, Date date2, int calendar, int period) {
		date1 = DateUtils.truncate(date1, calendar);
		date2 = DateUtils.truncate(date2, calendar);
		return date1.compareTo(date2);
	}

	public static Date formatDateFromTime(String time, Date date) throws Exception {
		Date ret = new SimpleDateFormat("hh:mm:ss").parse(time);

		Calendar calendar1 = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		calendar1.setTime(ret);
		calendar2.setTime(date);
		calendar1.set(Calendar.YEAR, calendar2.get(Calendar.YEAR));
		calendar1.set(Calendar.MONTH, calendar2.get(Calendar.MONTH));
		calendar1.set(Calendar.DAY_OF_MONTH, calendar2.get(Calendar.DAY_OF_MONTH));
		return calendar1.getTime();
	}

	public static int compareDateBetween(Date date, Date startDate, Date endDate) {
		if (date.before(startDate)) {
			return -1;
		} else if (date.after(endDate)) {
			return 1;
		} else {
			return 0;
		}
	}

	/**
	 * ��ȡ��dete֮ǰ���ĵ�num������
	 */
	public static Date getHour(Date date, int num) {
		date = DateUtils.addHours(date, num);
		date = DateUtils.truncate(date, Calendar.HOUR_OF_DAY);
		return date;
	}

	/**
	 * 
	 * @param date
	 * @return
	 */
	public static Date formatStartDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}

	public static Date formatEndDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return calendar.getTime();
	}

	/**
	 * @param endDate
	 * @param count
	 * @return ��endDateʼ����count��weekend���б�
	 */
	public static List<Date> getWeekendList(Date endDate, int count) {
		if (count < 1)
			throw new IllegalArgumentException("the count should be an integer which larger than 0");
		List<Date> ret = new ArrayList<Date>(count);
		Date[] dates = new Date[count];
		Date now = new Date();
		int i = 0, j = 0;
		while (count > 0) {
			Date lastDayOfMonth = getLastDayOfMonth(endDate, i);
			--i;
			if (!lastDayOfMonth.after(now)) {
				count--;
				dates[count] = getFirstDayOfWeek(lastDayOfMonth);
			}
		}
		for (j = 0; j < dates.length; j++) {
			ret.add(dates[j]);
		}
		return ret;
	}

	/**
	 * 
	 * @param endMonth
	 * @param count
	 * @param pattern
	 * @return ��endMonthΪֹ����count���£���pattern���и�ʽ��
	 */
	public static List<String> formatDates(List<Date> dates, String pattern) {
		List<String> ret = new ArrayList<String>(dates.size());
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		for (Date date : dates) {
			ret.add(format.format(date));
		}
		return ret;
	}

	/**
	 * 
	 * @param endMonth
	 * @param count
	 * @param pattern
	 * @return ��endMonthΪֹ����count����
	 */
	public static List<Date> getMonthList(Date endMonth, int count) {
		if (count < 1)
			throw new IllegalArgumentException("the count should be an integer which larger than 0");
		List<Date> ret = new ArrayList<Date>(count);
		for (int i = count - 1; i >= 0; i--) {
			ret.add(DateUtils.addMonths(endMonth, -i));
		}
		return ret;
	}

	@SuppressWarnings("deprecation")
	public static List<Date> getSundayListOfWeek(Date date, int count) {
		if (count < 1)
			throw new IllegalArgumentException("the count should be an integer which larger than 0");

		Date endWeek = getFirstDayOfWeek(date);
		List<Date> ret = new ArrayList<Date>(count);
		for (int i = count - 1; i >= 0; i--) {
			ret.add(DateUtils.addWeeks(endWeek, -i));
		}
		if (truncateDay(date).getDay() == 0) {
			ret.add(date);
		}

		return ret;
	}

	public static void main(String[] args) {
		Date date = parseDate("2012-06-24", "yyyy-MM-dd");
		List<Date> dates = getSundayListOfWeek(date, 53);
		for (Date d : dates) {
			System.out.println(d);
		}
	}

	public static List<Date> getMonthListAfterDate(Date endMonth, int count) {
		if (count < 1)
			throw new IllegalArgumentException("the count should be an integer which larger than 0");
		List<Date> ret = new ArrayList<Date>(count);
		for (int i = count - 1; i >= 0; i--) {
			ret.add(DateUtils.addMonths(endMonth, i));
		}
		return ret;
	}

	public static List<Date> getWeekendListAfterDate(Date startDate, int count) {
		List<Date> dateList = new ArrayList<Date>();
		if (count < 1) {
			throw new IllegalArgumentException("the count should be an integer which larger than 0");
		}
		for (int i = count; i > 0; i--) {
			dateList.add(DateUtils.addWeeks(startDate, i));
		}
		return dateList;
	}

	/**
	 * 
	 * @param endMonth
	 * @param count
	 * @return ��endMonth��count���µ�weekend�б�
	 */
	public static List<Date> getWeekendListByMonth(Date endMonth, int count) {
		if (count < 1)
			throw new IllegalArgumentException("the count should be an integer which larger than 0");
		Date beginMonth = DateUtils.addMonths(endMonth, -count + 1);
		Date weekend = getFirstWeekendOfMonth(beginMonth);
		List<Date> ret = new ArrayList<Date>(count * 5);
		while (weekend.compareTo(endMonth) <= 0) {
			ret.add(weekend);
			weekend = DateUtils.addDays(weekend, 7);
		}
		return ret;
	}

	public static List<Date> getWeekendListFromStartDateToEndDate(Date startDate, Date endDate) {
		List<Date> dateList = new ArrayList<Date>();
		startDate = getFirstDayOfWeek(startDate);
		endDate = getFirstDayOfWeek(endDate);
		while (startDate.compareTo(endDate) >= 0) {
			dateList.add(startDate);
			startDate = DateUtils.addWeeks(startDate, -1);
		}
		return dateList;
	}

	public static List<String> getDateListFromStartDateToEndDate(String startDateStr, String endDateStr, String pattern) {
		List<String> dateList = new ArrayList<String>();
		Date startDate = truncateDay(parseDate(startDateStr, "yyyy-MM-dd"));
		Date endDate = truncateDay(parseDate(endDateStr, "yyyy-MM-dd"));
		while (endDate.compareTo(startDate) >= 0) {
			dateList.add(formatByPattern(startDate, pattern));
			startDate = DateUtils.addDays(startDate, 1);
		}
		return dateList;
	}

	public static List<Date> getWeekendListBeforeDate(Date startDate, int count) {
		List<Date> dateList = new ArrayList<Date>();
		if (count < 1) {
			throw new IllegalArgumentException("the count should be an integer which larger than 0");
		}
		startDate = getFirstDayOfWeek(startDate);
		for (int i = count; i > 0; i--) {
			dateList.add(startDate);
			startDate = DateUtils.addWeeks(startDate, -1);
		}
		return dateList;
	}

	public static boolean isTimeOff(Date publishDate) {
		Date currDate = DateUtils.addDays(new Date(), -7);
		int a = publishDate.compareTo(currDate);
		if (a > 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * �õ��������ڵĲ�ֵ<br>
	 * between('2009-11-10', '2008-12-12', Calendar.YEAR) == 1<br>
	 * between('2009-11-10', '2008-12-12', Calendar.MONTH) == 11<br>
	 * between('2009-11-10', '2008-12-12', Calendar.WEEK_OF_YEAR) == 47<br>
	 * between('2009-11-10', '2008-12-12', Calendar.DATE) == 333<br>
	 * between('2009-11-10', '2008-12-12', Calendar.HOUR) == 7992<br>
	 * 
	 * @param date1
	 * @param date2
	 * @param field
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static long between(Date date1, Date date2, int field) {
		long ret = 0;
		long msBetween = date1.getTime() - date2.getTime();
		switch (field) {
		case Calendar.YEAR:
			ret = date1.getYear() - date2.getYear();
			break;
		case Calendar.MONTH:
			ret = (date1.getYear() - date2.getYear()) * 12 + (date1.getMonth() - date2.getMonth());
			break;
		case Calendar.WEEK_OF_MONTH:
		case Calendar.WEEK_OF_YEAR:
			ret = (long) (msBetween / org.apache.commons.lang.time.DateUtils.MILLIS_PER_DAY / 7);
			break;
		case Calendar.DATE:
		case Calendar.DAY_OF_WEEK:
		case Calendar.DAY_OF_WEEK_IN_MONTH:
		case Calendar.DAY_OF_YEAR:
			ret = (long) (msBetween / org.apache.commons.lang.time.DateUtils.MILLIS_PER_DAY);
			break;
		case Calendar.HOUR:
		case Calendar.HOUR_OF_DAY:
			ret = (long) (msBetween / org.apache.commons.lang.time.DateUtils.MILLIS_PER_HOUR);
			break;
		case Calendar.MINUTE:
			ret = (long) (msBetween / org.apache.commons.lang.time.DateUtils.MILLIS_PER_MINUTE);
			break;
		case Calendar.SECOND:
			ret = (long) (msBetween / org.apache.commons.lang.time.DateUtils.MILLIS_PER_SECOND);
			break;
		default:
			ret = msBetween;
		}
		return ret;
	}

	public static Date getDateByIntervalAndOffset(int interval, int offset) {
		if (interval == 0) {
			throw new IllegalArgumentException("interval is  0");
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(DateUtils.truncate(calendar.getTime(), Calendar.HOUR_OF_DAY));
		int minute = calendar.get(Calendar.MINUTE);
		calendar.set(Calendar.MINUTE, -((minute / interval) * interval - (offset * interval)));
		return calendar.getTime();
	}

	@SuppressWarnings("deprecation")
	public static List<String> getDateListByInterval(Date startDate, Date endDate, int interval, String format) {
		List<String> dateList = new ArrayList<String>();
		Date _startDate = (Date) startDate.clone();
		while (endDate.compareTo(_startDate) >= 0) {
			if (_startDate.getHours() > 7 || _startDate.getHours() == 0) {
				dateList.add(formatByPattern(_startDate, format));
			}
			_startDate = DateUtils.add(_startDate, Calendar.MINUTE, interval);
		}
		return dateList;
	}

	public static List<String> getDateListByOffset(int limit, int offset, String format, int field) {
		List<String> dateList = new ArrayList<String>();
		Calendar calendar = Calendar.getInstance();
		calendar.add(field, offset);
		for (int i = 0; i < limit; i++) {
			dateList.add(formatByPattern(calendar.getTime(), format));
			calendar.add(field, 1);
		}
		return dateList;
	}

	public static Date getDateByOffsetAndField(int offset, int field) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(field, offset);
		return calendar.getTime();
	}


	private static int MINU_SEC = 60;
	private static int HOUR_SEC = 3600;
	private static long DAY_SEC = HOUR_SEC * 24;
	private static long MONTH_SEC = DAY_SEC * 30;
	private static long YEAR_SEC = MONTH_SEC * 12;

	/**
	 * ��ȡʱ��������ʱ�����������
	 * 
	 * @param date
	 * @return
	 */
	public static String getShortDateText(Date date) {
		Calendar systemCalendar = Calendar.getInstance();
		Calendar inputCalendar = Calendar.getInstance();
		inputCalendar.setTime(date);
		long msNow = systemCalendar.getTimeInMillis();
		long msInput = inputCalendar.getTimeInMillis();
		if (msInput <= msNow) {
			long diffSec = (msNow - msInput) / 1000;
			if (diffSec < DAY_SEC) {
				if (diffSec >= HOUR_SEC) {
					return (diffSec / HOUR_SEC) + "Сʱǰ";
				} else if (diffSec >= MINU_SEC) {
					return (diffSec / MINU_SEC) + "����ǰ";
				} else {
					return "�ո�";
				}
			} else if (diffSec < MONTH_SEC) {
				return (diffSec / DAY_SEC) + "��ǰ";
			} else if (diffSec < YEAR_SEC) {
				return ((int) (diffSec / MONTH_SEC)) + "��ǰ";
			} else {
				int yearInput = inputCalendar.get(Calendar.YEAR);
				int yearNow = systemCalendar.get(Calendar.YEAR);
				return (yearNow - yearInput) + "��ǰ";
			}
		} else {
			return "�ո�";// ����ʱ����� ϵͳʱ�� ���� "�ո�"
		}
	}

	/**
	 * ��ȡ����ϵͳʱ���������
	 * FIXME:�ô�����bug��(����:2012��1��1��-2011��12��30�շ��ص���1��ǰ),�Ƽ�ʹ��getShortDateText(Date
	 * date)
	 */
	public static String getDaysBeforeNow(Date date) {
		long sysTime = Long.parseLong(formatToYYYYMMDDhhmmss(new Date()));
		long ymdhms = Long.parseLong(formatToYYYYMMDDhhmmss(date));
		String strYear = "��ǰ";
		String strMonth = "��ǰ";
		String strDay = "��ǰ";
		String strHour = "Сʱǰ";
		String strMinute = "����ǰ";
		try {
			if (ymdhms == 0) {
				return "";
			}
			long between = (sysTime / 10000000000L) - (ymdhms / 10000000000L);
			if (between > 0) {
				return between + strYear;
			}
			between = (sysTime / 100000000L) - (ymdhms / 100000000L);
			if (between > 0) {
				return between + strMonth;
			}
			between = (sysTime / 1000000L) - (ymdhms / 1000000L);
			if (between > 0) {
				return between + strDay;
			}
			between = (sysTime / 10000) - (ymdhms / 10000);
			if (between > 0) {
				return between + strHour;
			}
			between = (sysTime / 100) - (ymdhms / 100);
			if (between > 0) {
				return between + strMinute;
			}
			return "1" + strMinute;
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * ��ȡ���Сʱ��
	 * 
	 * @param publishDate
	 * @param strFormatter
	 * @return
	 */
	public static String getHourDateRangeStr(Date publishDate, String strFormatter) {
		Date[] range = DateUtil.getOneDayRange(new Date());
		if (null != publishDate) {
			if (publishDate.after(range[0]) && publishDate.before(range[1])) {
				int dateDiff = DateUtil.getHour(new Date()) - DateUtil.getHour(publishDate);
				return dateDiff > 0 ? dateDiff + "Сʱǰ" : "1Сʱ��";
			} else {
				return (DateUtil.formatByPattern(publishDate, strFormatter));
			}
		} else {
			return null;
		}
	}
}
