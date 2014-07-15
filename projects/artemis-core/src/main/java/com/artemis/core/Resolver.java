package com.artemis.core;

import java.util.Map;

import com.artemis.core.result.Result;

/**
 * ���ܷ����ӿڶ���
 * 
 * @author xiaoyu
 * 
 */
public interface Resolver {
	public static final String URL = "$url";
	public static final String LINKS = "$links";
	public static final String CONENT = "$content";
	public static final String REFERER = "$referer";
	public static final String SELECTOR = "$selector";
	public static final String PARAM_NAME = "$name";
	public static final String ATTR_NAME = "$attr";

	/**
	 * ������
	 * 
	 * @return
	 */
	public Result invoke(Map<String, Object> params);

	// /**
	// * ��������
	// *
	// * @return
	// */
	// public String getName();
	//
	// /**
	// * ��ҳ����
	// *
	// * @return
	// */
	// public String getContent();
}
