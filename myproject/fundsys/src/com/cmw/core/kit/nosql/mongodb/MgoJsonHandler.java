package com.cmw.core.kit.nosql.mongodb;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import com.cmw.core.kit.file.FileUtil;
import com.cmw.core.kit.nosql.exception.NoSqlException;
import com.cmw.core.util.FastJsonUtil;
import com.cmw.core.util.StringHandler;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.WriteResult;

/**
 * Mongo DB 数据处理类
 * @author chengmingwei
 *
 */
public class MgoJsonHandler {
	//Mongo 服务器地址
	private String server = "localhost";
	//Mongo 端口
	private int port = 27017;
	//数据库名
	private String dbName;
	//用户名
	private String userName;
	//密码
	private String passWord;
	//是否已从资源文件加载过了Mongo DB 数据库连接资源
	private static String[] dbCfgs = null;
	//Mongo 对象
	private Mongo mongo = null;
	//数据库对象
	private DB db = null;
	
	public MgoJsonHandler() {
		if(null == dbCfgs){
			initCfgFromRecourse();
		}
		this.server = dbCfgs[0];
		this.port = Integer.parseInt(dbCfgs[1]);
		this.dbName = dbCfgs[2];
		if(StringHandler.isValidStr(dbCfgs[3])) this.userName = dbCfgs[3];
		if(StringHandler.isValidStr(dbCfgs[4])) this.passWord = dbCfgs[4];
		init();
	}
	
	/**
	 * Mongo DB 数据处理类构造函数 [当不需要用户名和密码时，使用此构造函数]
	 * @param server Mongo 服务器地址	默认“localhost”
	 * @param port	Mongo 端口 号 。默认“27017”
	 * @param dbName 数据库名
	 */
	public MgoJsonHandler(String server, int port, String dbName) {
		this.server = server;
		this.port = port;
		this.dbName = dbName;
		init();
	}

	/**
	 * Mongo DB 数据处理类构造函数 [当需要用户名和密码时，使用此构造函数]
	 * @param server Mongo 服务器地址	默认“localhost”
	 * @param port	Mongo 端口 号 。默认“27017”
	 * @param dbName 数据库名
	 * @param userName 用户名
	 * @param passWord 密码
	 */
	public MgoJsonHandler(String server, int port, String dbName,
			String userName, String passWord) {
		super();
		this.server = server;
		this.port = port;
		this.dbName = dbName;
		this.userName = userName;
		this.passWord = passWord;
	}

	/**
	 * 初始化 
	 */
	private void init(){
		try {
			this.mongo = new Mongo(server, port);
			this.db = mongo.getDB(dbName);
			if(StringHandler.isValidStr(userName) && 
					StringHandler.isValidStr(passWord)){
				boolean auth = this.db.authenticate(this.userName, this.passWord.toCharArray());
				if(!auth) throw new NoSqlException("通过用户名："+this.userName+" 和 密码："+this.passWord+" 无法访问数据库："+this.dbName+"!");
			}
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (MongoException e) {
			e.printStackTrace();
		}catch (NoSqlException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除数据库
	 */
	public void dropDataBase(){
		db.dropDatabase();
	}
	
	/**
	 * 插入数据 
	 * @param tabName 集合名
	 * @param data	要插入的数据
	 * @return
	 */
	public boolean insert(String tabName , Map<String,Object> data){
		DBCollection dbcoll = getCollection(tabName);
		DBObject dbObj = new BasicDBObject();
		dbObj.putAll(data);
		WriteResult result = dbcoll.insert(dbObj);
		return result.getN()>0;
	}
	
	/**
	 * 获取指定页数大小的数据 并以 JSON 字符串形式返回
	 * @param mmap MgoMap 对象
	 * @return	返回 JSON 字符串
	 * @throws NoSqlException
	 */
	public String findByPageToJson(MgoMap mmap) throws NoSqlException{
		List<Map<String,Object>> datas = findByPage(mmap);
		Map<String,Object> appendParams = new HashMap<String, Object>();
		appendParams.put("totalSize", datas.size()+"");
		appendParams.put("success", "true");
		String jsonData = FastJsonUtil.convertJsonToStr(datas);
		return jsonData;
	}
	
	/**
	 * 获取指定页数大小的数据
	 * @param mmap	MgoMap 对象
	 * @return 返回 List<Map<String,Object>> 对象
	 * @throws NoSqlException 当没有提供分页参数时，抛出此异常
	 */
	public  List<Map<String,Object>> findByPage(MgoMap mmap) throws NoSqlException{
		 Integer pageSize = mmap.getPageSize();
		if(null == pageSize || pageSize == 0) throw new NoSqlException("没有提供分页大小，请通过  MgoMap 对象的  setPageSize 方法设置! ");
		 Integer offSet = mmap.getOffset();
		if(null == offSet)  throw new NoSqlException("没有提供分页时的偏移值，请通过  MgoMap 对象的  setOffset 方法设置! ");
		return find(mmap);
	}
	
	
	/**
	 * 获取指定的数据 并以 JSON 字符串形式返回
	 * @param mmap MgoMap 对象
	 * @return	返回 JSON 字符串
	 * @throws NoSqlException
	 */
	public String findToJson(MgoMap mmap) throws NoSqlException{
		List<Map<String,Object>> datas = find(mmap);
		Map<String,Object> appendParams = new HashMap<String, Object>();
		appendParams.put("totalSize", datas.size()+"");
		appendParams.put("success", "true");
		String jsonData = FastJsonUtil.convertJsonToStr(datas);
		return jsonData;
	}
	
	/**
	 * 查询指定的数据
	 * @param mmap	 MgoMap 对象
	 * @return 返回 List<Map<String,Object>> 对象
	 */
	@SuppressWarnings( "unchecked" )
	public List<Map<String,Object>> find(MgoMap mmap){
		String tabName = mmap.getTabName();
		 DBObject query = mmap.getQueryCmns();
		 DBObject fields = mmap.getShowCmns();
		 DBCollection dbcoll = getCollection(tabName);
		 DBCursor cursor = null;
		if(null == fields){
			cursor = dbcoll.find(query);
		}else{
			cursor = dbcoll.find(query,fields);
		}
		 Integer pageSize = mmap.getPageSize();
		if(null != pageSize && pageSize > 0) cursor.batchSize(pageSize);
		 Integer offSet = mmap.getOffset();
		if(null != offSet) cursor.skip(offSet);
		 DBObject orderBy = mmap.getOrderBy();
		cursor.sort(orderBy);
		
		//---> load Data From cursor
		int count = cursor.count();
		if(null == cursor || 0 == count) return null;
		List<Map<String,Object>> datas = new ArrayList<Map<String,Object>>(count);
		while(cursor.hasNext()){
			datas.add(cursor.next().toMap());
		}
		return datas;
	}
	/**
	 * 根据指定的参数获取一条数据信息
	 * @param tabName	集合名
	 * @param params	过滤参数
	 * @return
	 */
	public Map<String,Object> get(String tabName , Map<String,Object> params){
		 DBObject dbObj = new BasicDBObject(params);
		 DBCollection dbcoll = getCollection(tabName);
		 dbObj = dbcoll.findOne(dbObj);
		 dbObj.toString();
		 @SuppressWarnings("unchecked")
		 Map<String,Object> data = dbObj.toMap();
		 return data;
	}
	
	/**
	 * 根据指定的参数获取一条数据信息
	 * @param tabName	集合名
	 * @param params	过滤参数
	 * @param cmns 要查询的列名
	 * @return
	 */
	public Map<String,Object> get(String tabName , Map<String,Object> params,String ... cmns){
		 DBObject dbObj = new BasicDBObject(params);
		 DBCollection dbcoll = getCollection(tabName);
		
		 DBObject cmnsObj = getShowCmns(cmns);
		 dbObj = dbcoll.findOne(dbObj, cmnsObj);
		 @SuppressWarnings("unchecked")
		 Map<String,Object> data = dbObj.toMap();
		 return data;
	}
	
	/**
	 * 获取要显示的列 对象
	 * @param cmns	列名数组
	 * @return 返回  DBObject 对象
	 */
	private DBObject getShowCmns(String ... cmns){
		 DBObject cmnsObj = new BasicDBObject();
		 for(String cmn : cmns){
			 cmnsObj.put(cmn, 1);
		 }
		 return cmnsObj;
	}
	
	/**
	 * 根据指定的参数获取一条数据信息并以JSON 字符串返回
	 * @param tabName	集合名
	 * @param params	过滤参数
	 * @return
	 */
	public String getToJson(String tabName , Map<String,Object> params,String ... cmns){
		 DBObject dbObj = new BasicDBObject(params);
		 DBCollection dbcoll = getCollection(tabName);
		 DBObject cmnsObj = getShowCmns(cmns);
		 dbObj = dbcoll.findOne(dbObj, cmnsObj);
		 String jsonStr = dbObj.toString();
		 return jsonStr;
	}
	
	/**
	 * 根据指定的参数获取一条数据信息并以JSON 字符串返回
	 * @param tabName	集合名
	 * @param params	过滤参数
	 * @return
	 */
	public String getToJson(String tabName , Map<String,Object> params){
		 DBObject dbObj = new BasicDBObject(params);
		 DBCollection dbcoll = getCollection(tabName);
		 dbObj = dbcoll.findOne(dbObj);
		 String jsonStr = dbObj.toString();
		 return jsonStr;
	}
	
	/**
	 * 插入数据 
	 * @param tabName 集合名
	 * @param data	要插入的数据
	 * @return 返回插入成功的条数
	 */
	public int batchInsert(String tabName , List<Map<String,Object>> datas){
		if(null == datas || datas.size() == 0) return 0;

		List<DBObject> list = new ArrayList<DBObject>(datas.size());
		for(Map<String,Object> data : datas){
			list.add(new BasicDBObject(data));
		}
		
		DBCollection dbcoll = getCollection(tabName);
		WriteResult result = dbcoll.insert(list);
		return result.getN();
	}
	
	/**
	 * 根据集合名获取 集合对象
	 * @param collName	集合名
	 * @return	返回 DBCollection 对象
	 */
	public DBCollection getCollection(String collName){
		DBCollection dbcoll = db.getCollection(collName);
		return dbcoll;
	}

	/**
	 * 获取所有的 DBCollection 对象，并以 Map 形式返回
	 * @return 用 Map 对象返回当前数据库中的所有 DBCollection 对象
	 */
	public Map<String,DBCollection> getAllCollection(){
		Set<String> collNames = db.getCollectionNames();
		Map<String,DBCollection> map = new HashMap<String, DBCollection>();
		DBCollection collection = null;
		for(String collName : collNames){
			collection = db.getCollection(collName);
			map.put(collName, collection);
		}
		return map;
	}
	
	
	private static final String RESOURCE_FILE_NAME = "nosql_jdbc.properties";
	private static final String NOSQL_SERVER_KEY = "nosql_server";
	private static final String NOSQL_PORT_KEY = "nosql_port";
	private static final String NOSQL_DBNAME_KEY = "nosql_dbname";
	private static final String NOSQL_USERNAME_KEY = "nosql_username";
	private static final String NOSQL_PASSWORD_KEY = "nosql_password";
	
	/**
	 * 从资源文件中初始化数据库连接配置信息
	 */
	private static void initCfgFromRecourse(){
		try {
			String[] cfgs = new String[5];
			ResourceBundle recource = FileUtil.getResourceObj(RESOURCE_FILE_NAME);
			cfgs[0] = recource.getString(NOSQL_SERVER_KEY);
			if(null == cfgs[0]) throw new NoSqlException("通过资源键:"+NOSQL_SERVER_KEY+"无法从资源文件"+RESOURCE_FILE_NAME+"中找到相应的  NOSQL 服务地址 配置!");
			
			cfgs[1] = recource.getString(NOSQL_PORT_KEY);
			if(null == cfgs[1]) throw new NoSqlException("通过资源键:"+NOSQL_PORT_KEY+"无法从资源文件"+RESOURCE_FILE_NAME+"中找到相应的  NOSQL 端口 配置!");
			
			cfgs[2] = recource.getString(NOSQL_DBNAME_KEY);
			if(null == cfgs[2]) throw new NoSqlException("通过资源键:"+NOSQL_DBNAME_KEY+"无法从资源文件"+RESOURCE_FILE_NAME+"中找到相应的  NOSQL 数据库名 配置!");
			
			cfgs[3] = recource.getString(NOSQL_USERNAME_KEY);
			cfgs[4] = recource.getString(NOSQL_PASSWORD_KEY);
			dbCfgs = cfgs;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	
	

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public DB getDb() {
		return db;
	}

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
	
}
