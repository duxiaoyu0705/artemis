package com.artemis.core.result;

/**
 * ����������HTMLƬ�ν��
 * 
 * @author xiaoyu
 * 
 */
public class HtmlResult implements SingleResult {
	private String name;
	private String value;

	public HtmlResult(String name, String value) {
		this.name = name;
		this.value = value;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getValue() {
		return value;
	}

}
