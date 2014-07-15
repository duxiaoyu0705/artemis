package com.artemis.mongo.persistence;

import java.io.Serializable;
import java.util.List;

import com.mongodb.DBObject;

/**
 * ��ѯ�ӿ�
 * 
 * @author xiaoyu
 * 
 * @param <E>
 * @param <K>
 */
public interface EntityDao<E extends Entity, K extends Serializable> {

	/**
	 * ����id���Ҷ���
	 * 
	 * @param id
	 * @return
	 */
	E findById(K id);

	E findById(K id, String[] fields);

	/**
	 * �������ж���
	 * 
	 * @return
	 */
	List<E> findAll();

	/**
	 * ����id�б�������ж��󣬷��ص��б���ݴ����idList����
	 * 
	 * @param idList
	 * @return
	 */
	List<E> findAllByIds(List<K> idList);

	/**
	 * �����ݿ����һ������
	 * 
	 * @param entity
	 */
	void save(E entity);

	/**
	 * �����ݿ����һ������
	 * 
	 * @param entities
	 */
	void saveList(List<E> entities);

	/**
	 * ����idɾ��һ������
	 * 
	 * @param id
	 */
	void deleteById(K id);

	/**
	 * ����id�б�ɾ��һ������
	 * 
	 * @param idList
	 */
	void deleteByIds(List<K> idList);

	/**
	 * �õ���Ӧ��ʵ����
	 * 
	 * @return
	 */
	Class<E> getEntityClass();

	List<E> findAll(DBObject q);

	List<E> findAll(DBObject q, String[] fields);

	List<E> findAll(DBObject q, int maxResult);

	List<E> findAll(DBObject q, int maxResult, String[] fields);

	List<E> findAll(DBObject q, int firstResult, int maxResult);

	List<E> findAll(DBObject q, int firstResult, int maxResult, String[] fields);

	List<E> findAll(DBObject q, DBObject sq);

	List<E> findAll(DBObject q, DBObject sq, String[] fields);

	List<E> findAll(DBObject q, DBObject sq, int maxResult);

	List<E> findAll(DBObject q, DBObject sq, int maxResult, String[] fields);

	List<E> findAll(DBObject q, DBObject sq, int firstResult, int maxResult);

	List<E> findAll(DBObject q, DBObject sq, int firstResult, int maxResult, String[] fields);

	E findOne(DBObject q);

	E findOne(DBObject q, String[] fields);

	long findCount();

	long findCount(DBObject q);

	void updateById(DBObject data, K id);

	void update(DBObject data, DBObject q);

	void delete(DBObject q);
}
