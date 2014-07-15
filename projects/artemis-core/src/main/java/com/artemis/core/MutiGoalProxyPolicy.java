package com.artemis.core;

import java.io.IOException;
import java.util.List;

import com.artemis.core.bean.Goal;
import com.artemis.core.bean.Harvest;

/**
 * ץȡʵ�ֲ���
 * 
 * @author duxiaoyu
 * 
 */
public interface MutiGoalProxyPolicy extends ProxyPolicy {
	public List<Harvest> apply(List<Goal> goals) throws IOException;
}
