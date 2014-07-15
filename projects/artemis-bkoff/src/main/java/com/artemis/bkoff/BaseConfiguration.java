package com.artemis.bkoff;

import java.io.IOException;
import java.net.URISyntaxException;

import org.sothis.core.config.ConfigurationSupport;

public class BaseConfiguration extends ConfigurationSupport {

	protected final String environment;
	protected final boolean disableCache;
	protected final boolean disableLocalCache;

	public BaseConfiguration(String overridePropertiesLocation)
			throws IOException, URISyntaxException {
		super(overridePropertiesLocation);
		this.environment = get("fangjia.environment", "development");
		disableCache = getBoolean("fangjia.disableCache", true);
		disableLocalCache = getBoolean("fangjia.disableLocalCache", true);
	}

	public final String getEnvironment() {
		return environment;
	}

	/**
	 * �Ƿ��ڿ�������������
	 * 
	 * @return
	 */
	public final boolean isDevelopmentEnvironment() {
		return "development".equalsIgnoreCase(getEnvironment());
	}

	/**
	 * �Ƿ��ڲ��Ի���������
	 * 
	 * @return
	 */
	public final boolean isTestEnvironment() {
		return "test".equalsIgnoreCase(getEnvironment());
	}

	/**
	 * �Ƿ�����������������
	 * 
	 * @return
	 */
	public final boolean isProductionEnvironment() {
		return "production".equalsIgnoreCase(getEnvironment());
	}

	/**
	 * �ж��Ƿ����cache��Ĭ��Ϊtrue
	 * 
	 * @return
	 */
	public final boolean isDisableCache() {
		return disableCache;
	}

	/**
	 * �ж��Ƿ���ñ��ػ���cache��Ĭ��Ϊtrue
	 * 
	 * @return
	 */
	public final boolean isDisableLocalCache() {
		return disableLocalCache;
	}

}
