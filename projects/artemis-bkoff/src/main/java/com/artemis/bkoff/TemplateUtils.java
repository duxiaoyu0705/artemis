package com.artemis.bkoff;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.artemis.core.util.DataUtil;
import com.artemis.core.util.DateUtil;

public final class TemplateUtils {
	/**
	 * �����ض���ĳ���ַ���
	 * 
	 * @author
	 */
	public static String cleanSpecString(String str, String spec) {
		StringBuffer result = new StringBuffer();
		String[] array = str.split(spec);
		for (int i = 0; i < array.length; i++) {
			result.append(array[i]);
		}
		return result.toString();
	}

	/**
	 * ��ȡ�ַ���
	 * 
	 * @param str
	 * @param beginIndex
	 * @param endIndex
	 * @return
	 */
	public static String cutString(String str, int beginIndex, int endIndex) {
		return str.substring(beginIndex, endIndex);
	}

	/**
	 * ��ȡ�ַ����ַ����е�index
	 * 
	 * @param str
	 * @param strChar
	 * @return
	 */
	public static int indexOf(String str, String strChar) {
		return str.indexOf(strChar);
	}

	/**
	 * ����ͼƬurl
	 * 
	 * @param u
	 * @param s
	 * @param t
	 * @return
	 */
	public static String encodePicUrl(String u, String s, String t) {
		/*
		 * if (StringUtils.isBlank(u)) { return StringUtils.EMPTY; } try {
		 * return SecurityUtil.encode("u=" + URLEncoder.encode(u, "UTF-8") +
		 * "&s=" + s + "&t=" + t); } catch (UnsupportedEncodingException e) {
		 * return SecurityUtil.encode("u=" + u + "&s=" + s + "&t=" + t); }
		 */
		return DataUtil.encodePicUrl(u, s, t);
	}

	/**
	 * ��ʽ������
	 * 
	 * ����λ��ʮλֱ�ӱ�Ϊ0
	 * 
	 * @param avgPirce
	 * @return
	 */
	public static Integer formatAvgPrice(Integer avgPirce) {
		return DataUtil.formatAvgPrice(avgPirce);
	}

	/**
	 * ȥ��str�е�html���
	 * 
	 * @param str
	 * @return
	 */
	public static String removeHtml(String str) {
		return DataUtil.removeHtml(str);
	}

	/**
	 * �Ƴ�HTML�е�<a>���
	 * 
	 * @param htmlDes
	 * @return
	 */
	public static String removeHref(String htmlDes) {
		return DataUtil.removeHref(htmlDes);
	}

	/**
	 * ȫ��ת���
	 * 
	 * @param input
	 * @return
	 */
	public static String toDBCcase(String input) {
		return DataUtil.toDBCcase(input);
	}

	/**
	 * ת��ʱ��
	 * 
	 * @param input
	 * @return
	 */
	public static String getDaysBeforeNow(Date date) {
		return DateUtil.getDaysBeforeNow(date);
	}

	/**
	 * ת��ʱ��
	 * 
	 * @param input
	 * @return _tyler
	 */
	public static String getShortDateText(Date date) {
		return DateUtil.getShortDateText(date);
	}

	/**
	 * ȥ����֧�ֵı����ַ�
	 * 
	 * @param input
	 * @return
	 */
	public static String cutUnvalidCode(String input) {
		return input.replaceAll("[^\\x00-\\x80\uFE30-\uFFA0\u4E00-\u9FA5]+", "");
	}

	/**
	 * ����
	 * 
	 * @param s
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String encode(String s) throws UnsupportedEncodingException {
		return URLEncoder.encode(s, "UTF-8");
	}

	/**
	 * ��ʽ������
	 * 
	 */
	public static String dateFormat(Date date, String pattern) {
		if (null == date || null == pattern)
			return "";

		return DateUtil.formatByPattern(date, pattern);
	}

	/**
	 * ��ȡdateǰdays�������
	 * 
	 * @param date
	 * @param days
	 * @return
	 */
	public static Date getBeforeDay(Date date, int days) {
		if (null == date) {
			date = new Date();
		}
		return DateUtil.getBeforeDay(date, days);
	}

	public static String replace(String text, String searchString, String replacement) {
		return StringUtils.replace(text, searchString, replacement);
	}

	private static final String[] STRONG_KEYS = new String[] { "����" };
	private static final String[] STRONG_VALUES = new String[] { "<strong>����</strong>" };
	private static final String[] STRONG_CLASS_VALUES = new String[] { "<strong class='yhua'>����</strong>" };

	/**
	 * �ؼ��ּ�strong��ʽ
	 * 
	 * @param text
	 * @param isNeedClass
	 *            true:����ʽyhua;false:����class
	 * @return
	 */
	public static String strong(String text, boolean isNeedClass) {
		if (StringUtils.isBlank(text)) {
			return text;
		}

		if (isNeedClass) {
			return StringUtils.replaceEach(text, STRONG_KEYS, STRONG_CLASS_VALUES);
		} else {
			return StringUtils.replaceEach(text, STRONG_KEYS, STRONG_VALUES);
		}
	}

	public static String strong(String text) {
		return strong(text, true);
	}

	/**
	 * ��ȡ1~size�������
	 * 
	 * @param size
	 * @return
	 */
	public static int getRandomIndex(int size) {
		return (int) (Math.random() * size) + 1;
	}

	public static String publishDateStr(Date publishDate) {
		if (null != publishDate) {
			Date[] range = DateUtil.getOneDayRange(new Date());
			if (publishDate.after(range[0]) && publishDate.before(range[1])) {
				int diffHour = DateUtil.getHour(new Date()) - DateUtil.getHour(publishDate);
				if (diffHour > 0) {
					return diffHour + "Сʱǰ";
				} else {
					int diffMinute = DateUtil.getMinute(new Date()) - DateUtil.getMinute(publishDate);
					if (diffMinute <= 0) {
						diffMinute = 1;
					}
					return diffMinute + "����ǰ";
				}

			} else {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				return sdf.format(publishDate);
			}
		}
		return "";
	}

	public static String formatDate(Date publishDate) {
		return formatDate(publishDate, "yyyy-MM-dd");
	}

	public static String formatDate(Date publishDate, String pattern) {
		if (publishDate == null) {
			return "";
		}
		return DateUtil.formatByPattern(publishDate, pattern);
	}

	public static String formatCurrentDate(String pattern) {
		return formatDate(new Date(), pattern);
	}

	public static String upper(String s) {
		if (s == null) {
			return "";
		}
		return s.toUpperCase();
	}

	public static String lower(String s) {
		if (s == null) {
			return "";
		}
		return s.toLowerCase();
	}

	public static boolean contains(String source, String target) {
		if (StringUtils.isBlank(source)) {
			return false;
		}

		if (target == null) {
			return false;
		}

		if ("".equals(target)) {
			return true;
		}

		return source.contains(target);
	}

	public static String mapToString(Map<String, Object> map, String split) {
		return DataUtil.mapToString(map, split);
	}

	public static String metadataToString(Map<String, Object> map, String split) {
		StringBuilder builder = new StringBuilder();
		for (String key : map.keySet()) {
			if (StringUtils.isNotBlank(key)) {
				if ("content".equals(key)) {
					builder.append(key).append(":").append("<textarea style='width:98%' rows=\"3\" cols=\"40\">" + map.get(key) + "</textarea>").append(split);
				} else {
					builder.append(key).append(":").append(map.get(key)).append(split);
				}
			}
		}

		String s = builder.toString();
		if (s.endsWith(",")) {
			s = s.substring(0, s.length() - 1);
		}

		return s;
	}

	public static String timesString(long seconds) {
		if (seconds >= 60 && seconds < 60 * 60) {
			String s = seconds / 60 + "��";
			if (seconds % 60 > 0) {
				s = s + seconds % 60 + "��";
			}
			return s;
		} else if (seconds >= (60 * 60) && seconds < (60 * 60 * 24)) {
			String s = seconds / 3600 + "ʱ";
			if (seconds % 3600 > 0) {
				s = s + timesString(seconds % 3600);
			}
			return s;
		} else if (seconds >= (60 * 60 * 24)) {
			String s = seconds / (60 * 60 * 24) + "��";
			if (seconds % (60 * 60 * 24) > 0) {
				s = s + timesString(seconds % (60 * 60 * 24));
			}
			return s;
		} else {
			return seconds + "��";
		}
	}

	public static String join(List<String> list, String separator) {
		String s = StringUtils.join(list, separator);
		return s;
	}
}
