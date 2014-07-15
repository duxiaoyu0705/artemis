package com.artemis.spiders.crawl;

import java.util.Date;
import java.util.List;

import com.artemis.core.log.ALogger;
import com.artemis.core.tools.AsyncThread;
import com.artemis.core.tools.AsyncThread.Call;
import com.artemis.mongo.po.Job;
import com.artemis.mongo.po.Urls;
import com.artemis.service.JobService;
import com.artemis.service.UrlsService;

public class DoctorThread extends Thread {
	private JobService jobService = JobService.getInstance();
	private UrlsService urlsService = UrlsService.getInstance();

	public static final ALogger LOG = new ALogger(DoctorThread.class);

	@Override
	public void run() {
		// ��������
		new AsyncThread(3 * 1000, new Call() {
			@Override
			public void invoke() {
				retryErrors();
			}
		});

		// ��������Ƿ��Ѿ����
		new AsyncThread(10 * 1000, new Call() {
			@Override
			public void invoke() {
				List<Job> jobs = jobService.findAllRunningJobs();
				for (Job job : jobs) {
					// �Ƿ��ǻ�Ծ����
					if (!jobService.isJobActive(job.getId())) {
						jobService.doneJob(job.getId());
						continue;
					}

					// �Ƿ񳬳��趨����ʱ��
					if (isWorktime(job)) {
						LOG.print("[job-stop] {}-{} out of date. so stopped it", new Object[] { job.getId(), job.getName() });
						jobService.doneJob(job.getId());
						continue;
					}
				}
			}
		});

		// ����δ���е�����
		new AsyncThread(60 * 1000, new Call() {
			@Override
			public void invoke() {
				List<Job> jobs = jobService.findAllJobs();
				for (Job job : jobs) {
					if (!jobService.isJobRunning(job.getId())) {
						jobService.cleanDeadJob(job.getId());
					}
				}
			}
		});

	}

	/**
	 * �Ƿ��Ѿ��ﵽ�趨ʱ������
	 * 
	 * @param job
	 * @return
	 */
	private boolean isWorktime(Job job) {
		if (job.getWorktime() <= 0) {
			return false;
		}

		if (new Date().getTime() - job.getStartDate().getTime() > job.getWorktime() * 1000) {
			return true;
		}

		return false;
	}

	/**
	 * ��������
	 */
	private void retryErrors() {
		List<Urls> urlsList = urlsService.findUrlsCrawlErr(1000);
		if (urlsList.size() > 0) {
			for (Urls urls : urlsList) {
				if (urls.getErrors() > 10) {
					urlsService.urlsToErrs(urls);
				} else {
					urlsService.updateUrlsCrawlErrToInit(urls.getId(), urls.getErrors());
				}
			}
		}
	}
}
