package com.cmw.core.kit.nosql.mongodb;

import java.io.Serializable;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.ResourceBundle;

import org.bson.types.ObjectId;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cmw.core.kit.file.FileUtil;
import com.cmw.core.util.StringHandler;
import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.WriteResult;
import com.mongodb.util.JSON;

public class MongoDbManager implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * tab
	 */
	public static final String TAB_KEY = "tab";
	/**
	 * datas
	 */
	public static final String DATAS_KEY = "datas";
	/**
	 * totalcount
	 */
	public static final String TOTALCOUNT_KEY = "totalcount";
	/**
	 * _id
	 */
	public static final String PRIMARY_ID_KEY = "_id";
	/**
	 * success
	 */
	public static final String SUCCESS_KEY = "success";
	/**
	 * msg
	 */
	public static final String MSG_KEY = "msg";
	private Mongo mongo = null;
	private DB db = null;
	public MongoDbManager(){
		init();
	}
	
	private void init(){
		if(null == mongo){
			try {
				ResourceBundle resource = FileUtil.getResourceObj("nosql_jdbc.properties");
				String mongo_server =  resource.getString("nosql_server");
				String mongo_port =  resource.getString("nosql_port");
				String mongo_db =  resource.getString("nosql_dbname");
				if(!StringHandler.isValidStr(mongo_server)) throw new MongoException("检查到在 nosql_jdbc.properties 文件中没有配置  nosql_server!");
				if(!StringHandler.isValidStr(mongo_port)) throw new MongoException("检查到在 nosql_jdbc.properties 文件中没有配置  nosql_port!");
				if(!StringHandler.isValidStr(mongo_db)) throw new MongoException("检查到在 nosql_jdbc.properties 文件中没有配置  nosql_dbname!");
				mongo = new Mongo(mongo_server,Integer.parseInt(mongo_port));
				db = mongo.getDB(mongo_db);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}catch (MongoException e) {
				e.printStackTrace();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 插入数据 格式： "{tab:'auditproject_user',datas:{name:'程明强',age:26}}";
	 * @param params  Map 对象 key : tab ,datas
	 * @throws MongoException
	 * @return 返回插入成功的 objectId
	 */
	public JSONObject insert(Map<String, Object> params) throws MongoException{
		String table = (String)params.get(TAB_KEY);
		String datas = (String)params.get(DATAS_KEY);
		BasicDBObject jsonObject = (BasicDBObject)JSON.parse(datas);
		insert(jsonObject, table);
		JSONObject dataSource =  returnJson(table, 1);
		ObjectId objectId = (ObjectId)jsonObject.getObjectId("_id");
		dataSource.put(PRIMARY_ID_KEY, objectId.toString());
		return dataSource;
		//return dataSource.toJSONString();
	}
	/**
	 * 插入数据 格式： "{tab:'auditproject_user',datas:{name:'程明强',age:26}}";
	 * @param jsonData  要插入的 json 字符串数据
	 * @throws MongoException
	 * @return 返回插入成功的 {tab:'xxx',_id:'eqewqqq12333',totalcount:1,success:'true'}
	 */
	public String insert(String jsonData) throws MongoException{
		BasicDBObject jsonObject = (BasicDBObject)JSON.parse(jsonData);
		String table = (String)jsonObject.get(TAB_KEY);
		jsonObject = (BasicDBObject)jsonObject.get(DATAS_KEY);
		insert(jsonObject, table);
		ObjectId objectId = (ObjectId)jsonObject.getObjectId("_id");
		JSONObject dataSource =  returnJson(table, 1);
		dataSource.put("objectId", objectId);
		dataSource.put(PRIMARY_ID_KEY, objectId.toString());
		return dataSource.toJSONString();
	}

	private boolean insert(BasicDBObject jsonObject, String table) throws MongoException{
		if(!StringHandler.isValidStr(table)) throw new MongoException("在调用 MongoDbManager 对象的 insert 方法时，所提供的数据没有指定 \"tab\" 的值！");
		db.requestStart();
		DBCollection coll = getTable(table);
		WriteResult result = coll.insert(jsonObject);//coll.insert(jsonObject,WriteConcern.SAFE);
		db.requestDone();
		System.out.println("insert error : "+result.getError());
		CommandResult cmdResult = result.getLastError();
		boolean isSuccess = false;
		if(null == cmdResult){
			isSuccess = true;
			return isSuccess;
		}else{
			MongoException insetErrException = cmdResult.getException();
			if(null != insetErrException){
				throw insetErrException;
			}else{
				return true;
			}
		}
	}
	
	/**
	 * 更新数据 格式： "{tab:'auditproject_user',datas:{name:'程明强',age:26}}";
	 * @param params 更新的参数  key : tab ,datas
	 * @throws MongoException
	 * @return 返回插入成功的 objectId
	 */
	public String update(Map<String, Object> params) throws MongoException{
		String table = (String)params.get(TAB_KEY);
		String datas = (String)params.get(DATAS_KEY);
		BasicDBObject jsonObject = (BasicDBObject)JSON.parse(datas);
		String json = update(jsonObject, table);
		return json;
	}
	
	/**
	 * 更新数据 格式： "{tab:'auditproject_user',datas:{name:'程明强',age:26}}";
	 * @param jsonData  要插入的 json 字符串数据
	 * @throws MongoException
	 * @return 返回插入成功的 objectId
	 */
	public String update(String jsonData) throws MongoException{
		BasicDBObject jsonObject = (BasicDBObject)JSON.parse(jsonData);
		String table = (String)jsonObject.get(TAB_KEY);
		jsonObject = (BasicDBObject)jsonObject.get(DATAS_KEY);
		String json = update(jsonObject, table);
		return json;
	}

	private String update(BasicDBObject jsonObject, String table) {
		if(!StringHandler.isValidStr(table)) throw new MongoException("在调用 MongoDbManager 对象的 update 方法时，所提供的数据没有指定 \"tab\" 的值！");
		DBCollection coll = getTable(table);
		String objectIdStr = jsonObject.getString(PRIMARY_ID_KEY);
		if(!StringHandler.isValidStr(objectIdStr)) throw new MongoException("在调用 MongoDbManager 对象的 update 方法时，表'"+table+"' 的主键 objectId 为 null！");
		ObjectId objectId = new ObjectId(objectIdStr);
		jsonObject.remove(PRIMARY_ID_KEY);
		int count = coll.update(new BasicDBObject(PRIMARY_ID_KEY, objectId),jsonObject,false,false).getN();
		JSONObject dataSource = returnJson(table, count);
		if(count == 0){
			insert(jsonObject,table);
			objectId = (ObjectId)jsonObject.getObjectId("_id");
			if(null != objectId) objectIdStr = objectId.toString();
		}
		dataSource.put(PRIMARY_ID_KEY, objectIdStr);
		return dataSource.toJSONString();
	}

	private JSONObject returnJson(String table, int count) {
		JSONObject dataSource = new JSONObject();
		dataSource.put(TAB_KEY, table);
		dataSource.put(TOTALCOUNT_KEY, count);
		dataSource.put(SUCCESS_KEY, true);
		dataSource.put(MSG_KEY, "ok");
		return dataSource;
	}
	
	/**
	 * 查找数据 
	 *  格式： params.put("tab","auditproject_user");
	 *  	  params.put("_id","504b43397e5d00c73e6d6409");
	 *   	  params.put("name","程明强") 
	 * @param params 查询参数
	 * @return	返回 JSON 字符串数据	
	 */
	public String find(Map<String,Object> params){
		String json = null;
		JSONObject dataSource = findData(params);
		json = dataSource.toJSONString();
		return json;
	}
	
	/**
	 * 查找指定的表中是否存在指定的记录
	 * @param params 表和查询参数
	 * @return true : 有符合条件的记录， false : 找不到记录
	 */
	public boolean existRecord(Map<String, Object> params) {
		String table = (String)params.get(TAB_KEY);
		DBCollection coll = getTable(table);
		params.remove(TAB_KEY);
		BasicDBObject ref = null;
		String datas = (String)params.get(DATAS_KEY);
		if(StringHandler.isValidStr(datas)){
			ref = (BasicDBObject)JSON.parse(datas);
		}
		DBCursor cursor = (null == ref) ? coll.find() : coll.find(ref);
		if(null == cursor) return false;
		boolean isExist = false;
		while(cursor.hasNext()){
			isExist = true;
			break;
		}
		return isExist;
	}
	
	/**
	 * 查找数据并以 JSONObject 对象返回
	 * @param params	tabCfgsStr
	 * @return
	 */
	public JSONObject findData(Map<String, Object> params) {
		String table = (String)params.get(TAB_KEY);
		DBCollection coll = getTable(table);
		params.remove(TAB_KEY);
		BasicDBObject ref = null;
		String datas = (String)params.get(DATAS_KEY);
		if(StringHandler.isValidStr(datas)){
			ref = (BasicDBObject)JSON.parse(datas);
		}
		DBCursor cursor = (null == ref) ? coll.find() : coll.find(ref);
		JSONArray array = new JSONArray();
		while(cursor.hasNext()){
			BasicDBObject row = (BasicDBObject)cursor.next();
			ObjectId objectId = row.getObjectId(PRIMARY_ID_KEY);
			String objectIdStr = objectId.toString();
			row.removeField(PRIMARY_ID_KEY);
			row.put(PRIMARY_ID_KEY, objectIdStr);
			//System.out.println(JSON.serialize(row));
			array.add(row);
		}
		
		JSONObject dataSource = returnJson(table,array.size());
		dataSource.put(DATAS_KEY, array.toJSONString());
		return dataSource;
	}
	
	/**
	 * 删除数据
	 * @param params 过滤参数
	 *   格式： params.put("tab","auditproject_user");
	 *  	  params.put("_id","504b43397e5d00c73e6d6409");
	 *   	  params.put("name","程明强") 
	 * @return 返回JSON字符串 格式： {tab:'auditproject_user',totalcount:2} 返回指定表中删除的记录数
	 */
	public String delete(Map<String,Object> params){
		String table = (String)params.get(TAB_KEY);
		DBCollection coll = getTable(table);
		params.remove(TAB_KEY);
		BasicDBObject ref = new BasicDBObject();
		ref.putAll(params);
		WriteResult result = coll.remove(ref);
		int count = result.getN();
		String json = returnJson(table,count).toJSONString();
		return json;
	}
	
	public DBCollection getTable(String table){
		DBCollection coll = db.getCollection(table);
		return coll;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		 MongoDbManager mongoMgr = MongoDbManager.getInstance();
		 String jsonData = "{tab:'auditproject_user',datas:{name:'程明强',age:26}}";
		 mongoMgr.insert(jsonData);
		 DBCollection coll =mongoMgr.getTable("auditproject_user");
		 DBCursor cursor = coll.find();
		 while(cursor.hasNext()){
				DBObject row = cursor.next();
				System.out.println(row);
		}
//		 String jsonStr = null;
//		 //---> 更新 <---//
//		 jsonStr = "{tab:'auditproject_user',datas:{_id:'504b4b844333a138865c016f',name:'程明强能干',age:28}}";
//		 jsonStr = mongoMgr.update(jsonStr);
//		 Map<String,Object> params = new HashMap<String,Object>();
//		 params.put(TAB_KEY, "auditproject_user");
//		 params.put("name", "程明强");
//		 mongoMgr.delete(params);
//		 //---> 查询 <---//
//		
//		 //params.put("name", "程明卫");
//		 params.put(TAB_KEY, "auditproject_user");
//		 params.remove("name");
//		 jsonStr = mongoMgr.find(params);
//		 System.out.println(jsonStr);
		 
		
	}
	
	
	/**
	 * 获取实例
	 * @return
	 */
	public static MongoDbManager getInstance(){
		return LazyCls.singletonInstance;
	}
	
	private static class LazyCls{
		private static final MongoDbManager singletonInstance = new MongoDbManager();
	}

	private Object readResolve(){
		return getInstance();
	}
	
}
