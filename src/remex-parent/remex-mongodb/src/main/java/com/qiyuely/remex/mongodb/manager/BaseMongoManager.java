package com.qiyuely.remex.mongodb.manager;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.mongodb.DBCollection;
import com.qiyuely.remex.utils.ValidateUtils;

/**
 * mongodb基础管理类
 * 
 * @author Qiaoxin.Hong
 *
 * @param <T> 数据实体Class
 */
public abstract class BaseMongoManager<T> {
	
	@Autowired
	protected MongoTemplate mongoTemplate;

	/**
	 * 数据实体Class
	 */
	protected Class<T> entityClass;
	
	@SuppressWarnings("unchecked")
	public BaseMongoManager() {
		//生成各泛型的class
		Type genType = getClass().getGenericSuperclass();   
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments(); 
		entityClass = (Class<T>) params[0];
	}
	
	/**
	 * 取得数据集名称
	 * @return
	 */
	public String getCollectionName() {
		return entityClass.getSimpleName();
	}
	
	/**
	 * 查询列表
	 * @param query
	 * @return
	 */
	public List<T> find(Query query) {
		return mongoTemplate.find(query, entityClass, getCollectionName());
	}
	
	/**
	 * 查询单条数据
	 * @param query
	 * @return
	 */
	public T findOne(Query query) {
		return mongoTemplate.findOne(query, entityClass, getCollectionName());
	}
	
	/**
	 * 根据主键编号查询数据
	 * @param id
	 * @return
	 */
	public T findById(Object id) {
		return mongoTemplate.findById(id, entityClass, getCollectionName());
	}
	
	/**
	 * 查询总数量
	 * @param query
	 * @return
	 */
	public long count(Query query) {
		return mongoTemplate.count(query, entityClass, getCollectionName());
	}
	
	/**
	 * 查询列表
	 * @param andCrArr 条件
	 * @return
	 */
	public List<T> find(Criteria...andCrArr) {
		return find(null, andCrArr);
	}
	
	/**
	 * 查询列表
	 * @param count 查询数量
	 * @param andCrArr 条件
	 * @return
	 */
	public List<T> find(Integer count, Criteria...andCrArr) {
		return find(count, null, andCrArr);
	}
	
	/**
	 * 查询列表
	 * @param count 查询数量
	 * @param sort 排序
	 * @param andCrArr 条件
	 * @return
	 */
	public List<T> find(Integer count, Sort sort, Criteria...andCrArr) {
		Query query = new Query();
		
		packQuery(query, count, sort, andCrArr);
		
		return find(query);
	}
	
	/**
	 * 封装Query对象
	 * @param query
	 * @param andCrArr
	 */
	protected void packQuery(Query query, Criteria...andCrArr) {
		packQuery(query, null, null, andCrArr);
	}
	
	/**
	 * 封装Query对象
	 * @param query query对象
	 * @param count 查询数量
	 * @param andCrArr 条件
	 */
	protected void packQuery(Query query, Integer count, Criteria...andCrArr) {
		packQuery(query, count, null, andCrArr);
	}
	
	/**
	 * 封装Query对象
	 * @param query query对象
	 * @param count 查询数量
	 * @param sort 排序
	 * @param andCrArr 条件
	 */
	protected void packQuery(Query query, Integer count, Sort sort, Criteria...andCrArr) {
		if (query != null) {
			
			//条件
			if (ValidateUtils.isNotEmpty(andCrArr)) {
				Criteria criteria = packAndCriteria(andCrArr);
				query.addCriteria(criteria);
			}
			
			//排序
			if (sort != null) {
				query.with(sort);
			}
			
			//数量
			if (count != null && count >= 0) {
				query.limit(count);
			}
		}
	}
	
	/**
	 * 封装并且关系的Criteria对象
	 * @param crArr
	 * @return
	 */
	protected Criteria packAndCriteria(Criteria...crArr) {
		Criteria criteria = new Criteria();
		
		if (ValidateUtils.isNotEmpty(crArr)) {
			criteria.andOperator(crArr);
		}
		return criteria;
	}
	
	/**
	 * 封装或者关系的Criteria对象
	 * @param crArr
	 * @return
	 */
	protected Criteria packOrCriteria(Criteria...crArr) {
		Criteria criteria = new Criteria();
		
		if (ValidateUtils.isNotEmpty(crArr)) {
			criteria.orOperator(crArr);
		}
		return criteria;
	}
	
	/**
	 * 清空数据集
	 */
	public void drop() {
		mongoTemplate.dropCollection(getCollectionName());
	}
	
	/**
	 * 保存数据
	 * @param entity
	 */
	public void save(T entity) {
		mongoTemplate.save(entity, getCollectionName());
	}
	
	/**
	 * 自动创建数据集（如果数据集不存在）
	 */
	protected void autoCreateCollection() {
		boolean exists = mongoTemplate.collectionExists(getCollectionName());
		//不存在则创建数据集
		if (!exists) {
			mongoTemplate.createCollection(getCollectionName());
			DBCollection collection = mongoTemplate.getCollection(getCollectionName());
			
			//创建数据集的后续处理
			afterCreateCollection(collection);
		}
	}
	
	/**
	 * 创建数据集的后续处理，可设，由{@link #autoCreateCollection()}发起
	 * @param collection
	 */
	protected void afterCreateCollection(DBCollection collection) {
		
	}
}

