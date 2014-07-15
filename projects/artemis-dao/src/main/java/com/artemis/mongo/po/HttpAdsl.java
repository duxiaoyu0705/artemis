package com.artemis.mongo.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.apache.commons.lang.StringUtils;

import com.artemis.core.bean.Proxy;
import com.artemis.mongo.persistence.MongoEntity;

@Entity
public class HttpAdsl implements MongoEntity, Proxy {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5080408282852831135L;
	private String id;
	private String url;
	private int port;
	private String contextPath;

	private int freq; // ����Ƶ��
	private int restartFreq; // ����Ƶ��
	private int status; // ״̬ 0-���� 1-���� 2-������
	private int cloudVisite; // �Ƿ�����Ʒ��� 0-������ 1-����
	private int cloudFreq; // �Ʒ���Ƶ��
	private String params; // ����
	private Date visitedDate; // ������ʱ��
	private Date cloudVisitedDate; // ����Ʒ���ʱ��
	private Date restartDate; // �������ʱ��
	private String publicIp; // ����IP

	private Date creationDate;

	@Id
	@GeneratedValue
	@Column(name = ID)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "url")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "port")
	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	@Column(name = "context_path")
	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	@Column(name = "params")
	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	@Column(name = "c_date")
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name = "freq")
	public int getFreq() {
		return freq;
	}

	public void setFreq(int freq) {
		this.freq = freq;
	}

	@Column(name = "status")
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Column(name = "v_date")
	public Date getVisitedDate() {
		return visitedDate;
	}

	public void setVisitedDate(Date visitedDate) {
		this.visitedDate = visitedDate;
	}

	@Column(name = "restart_date")
	public Date getRestartDate() {
		return restartDate;
	}

	public void setRestartDate(Date restartDate) {
		this.restartDate = restartDate;
	}

	@Column(name = "restart_freq")
	public int getRestartFreq() {
		return restartFreq;
	}

	public void setRestartFreq(int restartFreq) {
		this.restartFreq = restartFreq;
	}

	@Column(name = "public_ip")
	public String getPublicIp() {
		return publicIp;
	}

	public void setPublicIp(String publicIp) {
		this.publicIp = publicIp;
	}

	@Column(name = "cloud_visite")
	public int getCloudVisite() {
		return cloudVisite;
	}

	public void setCloudVisite(int cloudVisite) {
		this.cloudVisite = cloudVisite;
	}

	@Column(name = "cloud_freq")
	public int getCloudFreq() {
		return cloudFreq;
	}

	public void setCloudFreq(int cloudFreq) {
		this.cloudFreq = cloudFreq;
	}

	@Column(name = "cv_date")
	public Date getCloudVisitedDate() {
		return cloudVisitedDate;
	}

	public void setCloudVisitedDate(Date cloudVisitedDate) {
		this.cloudVisitedDate = cloudVisitedDate;
	}

	@Override
	public String getSimpleName() {
		return StringUtils.substringAfterLast(this.getUrl(), ".");
	}

	public String getIp() {
		return this.url.replace("http://", "");
	}
}
