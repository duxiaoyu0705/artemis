package com.artemis.spiders.crawl;

import java.util.Date;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.artemis.core.log.ALogger;
import com.artemis.service.UrlsService;
import com.artemis.spiders.SpidersConfig;

/**
 * ����������
 * 
 * @author duxiaoyu
 * 
 */
public class MasterController extends HttpServlet {

	private static final long serialVersionUID = 6618334210198455254L;
	private UrlsService urlsService = UrlsService.getInstance();

	public static final ALogger LOG = new ALogger(MasterController.class);
	
	public void init(ServletConfig config) throws ServletException {
		LOG.print("--------------------------------###--------------------------------------------------");
		LOG.print("spiders starting " + new Date());
		Map<String, String> propertiesMap = SpidersConfig.getInstance().getPropertiesAsMap();
		for (String key : propertiesMap.keySet()) {
			LOG.print("\t" + key + " : " + propertiesMap.get(key));
		}
		LOG.print("spiders started " + new Date());
		LOG.print("--------------------------------###--------------------------------------------------");
		platformCleaner();
		initSpidersThreads();
	}

	private void platformCleaner() {
		urlsService.updateUrlsCrawlQueToInit();
	}

	private void initSpidersThreads() {
		// �ռ�Ŀ��
		GoalCollector goaslCollector = new GoalCollector();
		goaslCollector.start();

		// �ռ��ɹ�
		HarvestCollector harvestThread = new HarvestCollector();
		harvestThread.start();

		// ֩��ץȡ
		MasterSpider spiders = new MasterSpider();
		spiders.start();

		// ����֪ͨ
		AdvisorThread advisor = new AdvisorThread();
		advisor.start();

		// ץȡ����
		DoctorThread doctor = new DoctorThread();
		doctor.start();

		// ��ʱ��
		TimerThread timer = new TimerThread();
		timer.start();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		LOG.print("[service OK]");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		doGet(request, response);
	}

	public void destroy() {

	}
}
