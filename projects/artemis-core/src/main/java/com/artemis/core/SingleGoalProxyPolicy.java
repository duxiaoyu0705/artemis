package com.artemis.core;

import java.io.IOException;

import com.artemis.core.bean.Goal;
import com.artemis.core.bean.Harvest;

/**
 * ץȡʵ�ֲ���
 * 
 * @author duxiaoyu
 * 
 */
public interface SingleGoalProxyPolicy extends ProxyPolicy {
	
	public Harvest apply(Goal goal) throws IOException;
}
