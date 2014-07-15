package com.artemis.bkoff.controllers;

import java.util.Date;

import com.artemis.bkoff.model.ProxyModel;
import com.artemis.mongo.dao.GrandCloudDao;
import com.artemis.mongo.dao.HttpAdslDao;
import com.artemis.mongo.po.GrandCloud;
import com.artemis.mongo.po.HttpAdsl;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class ProxyController {
	private HttpAdslDao httpAdslDao = HttpAdslDao.getInstance();
	private GrandCloudDao grandCloudDao = GrandCloudDao.getInstance();

	public Object listHttpAdslAction(ProxyModel model) {
		model.setHttpAdslList(httpAdslDao.findAll());
		model.setGrandCloudList(grandCloudDao.findAll());
		return model;
	}

	public Object listNginxAdslAction(ProxyModel model) {
		model.setHttpAdslList(httpAdslDao.findAll());
		model.setGrandCloudList(grandCloudDao.findAll());
		return model;
	}

	public Object listGrandCloudAction(ProxyModel model) {
		model.setHttpAdslList(httpAdslDao.findAll());
		model.setGrandCloudList(grandCloudDao.findAll());
		return model;
	}

	public Object createHttpAdslAction(ProxyModel model) {
		if (model.getId() != null) {
			HttpAdsl httpAdsl = this.httpAdslDao.findById(model.getId());
			model.setHttpAdsl(httpAdsl);
		}
		return model;
	}

	public Object createNginxAdslAction(ProxyModel model) {
		return model;
	}

	public Object createGrandCloudAction(ProxyModel model) {
		return model;
	}

	public Object deleteHttpAdslAction(ProxyModel model) {
		if (model.getId() != null) {
			this.httpAdslDao.deleteById(model.getId());
		}
		return model.redirect("/proxy/listHttpAdsl");
	}

	public Object deleteGrandCloudAction(ProxyModel model) {
		if (model.getId() != null) {
			this.grandCloudDao.deleteById(model.getId());
		}
		return model.redirect("/proxy/listGrandCloud");
	}

	public Object updateHttpAdslAction(ProxyModel model) {
		DBObject data = new BasicDBObject();
		data.put("status", model.getStatus());
		this.httpAdslDao.updateById(data, model.getId());
		return model.redirect("/proxy/listHttpAdsl");
	}

	public Object updateGrandCloudAction(ProxyModel model) {
		DBObject data = new BasicDBObject();
		data.put("status", model.getStatus());
		this.grandCloudDao.updateById(data, model.getId());
		return model.redirect("/proxy/listGrandCloud");
	}

	public Object saveHttpAdslAction(ProxyModel model) {
		HttpAdsl entity = model.getHttpAdsl();
		if (entity != null) {
			if (entity.getId() != null) {
				DBObject data = new BasicDBObject();
				data.put("url", entity.getUrl());
				data.put("port", entity.getPort());
				data.put("context_path", entity.getContextPath());
				data.put("freq", entity.getFreq());
				data.put("restart_freq", entity.getRestartFreq());
				data.put("params", entity.getParams());
				data.put("cloud_visite", entity.getCloudVisite());
				data.put("cloud_freq", entity.getCloudFreq());

				this.httpAdslDao.updateById(data, entity.getId());
			} else {
				if (entity.getUrl() != null) {
					if (!entity.getUrl().startsWith("http://")) {
						entity.setUrl("http://" + entity.getUrl());
					}
					entity.setCreationDate(new Date());
					this.httpAdslDao.save(entity);
				}
			}
		}
		return model.redirect("/proxy/listHttpAdsl");
	}

	public Object saveGrandCloudAction(ProxyModel model) {
		GrandCloud entity = model.getGrandCloud();
		if (entity != null) {
			if (entity.getUrl() != null) {
				if (!entity.getUrl().startsWith("http://")) {
					entity.setUrl("http://" + entity.getUrl());
				}
				entity.setCreationDate(new Date());
				this.grandCloudDao.save(entity);
			}
		}
		return model.redirect("/proxy/listGrandCloud");
	}

}