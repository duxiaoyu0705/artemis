package com.artemis.core.bean;

import java.util.Date;

public interface Proxy {

	public static final int ENABLE = 0; // ����

	public static final int DISABLE = 1;// ����

	public static final int RESTARTING = 2;// ������
	
	public static final int ERROR = 3;// ����
	
	public static final int USING = 4;// ʹ����
	
	public String getId();

	public String getUrl();

	public int getPort();

	public String getContextPath();

	public String getParams();

	public int getFreq();

	public Date getVisitedDate();

	public String getSimpleName();
}