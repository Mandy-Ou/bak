package com.cmw.service.impl.fininter.external;

import java.io.Serializable;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;

import javax.management.ObjectName;

import org.apache.log4j.Logger;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.stat.DruidDataSourceStatManager;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.util.SpringContextUtil;
import com.cmw.entity.fininter.FinSysCfgEntity;
import com.cmw.service.inter.fininter.FinSysCfgService;
/**
 * 
 * @author chengmingwei
 *
 */
public class DbPool implements Serializable{
	protected static Logger log = Logger.getLogger("com.cmw.service.impl.fininter.external.DbPool");
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Map<String,Boolean> flagMap = new HashMap<String, Boolean>();/*用来标识哪些数据源需要重新加载*/

	
	private DbPool() {
	}
	
	FinSysCfgService finSysCfgService;
	FinSysCfgEntity entity;
	DruidDataSource dataSource = null;
	public synchronized DruidDataSource getDataSource(String finSysCode) throws ServiceException{
//		if(isReloadDs(finSysCode) && null != this.dataSource){
//			removeDataSouce();
//			init(finSysCode);
//			setProps();
//		}else{
//			init(finSysCode);
//			setProps();
//		}
		init(finSysCode);
		setProps();
		return dataSource;
	}
	
	public void removeDataSouce(){
		IdentityHashMap<Object, ObjectName> map = DruidDataSourceStatManager.getInstances();
		if(null == map || map.size() == 0) return;
		boolean isExist = map.containsKey(this.dataSource);
		if(!isExist) return;
		
		this.dataSource.close();
		DruidDataSourceStatManager.removeDataSource(this.dataSource);
		this.dataSource = null;
	}
	
	public void removeDataSouce(DruidDataSource dataSource){
		IdentityHashMap<Object, ObjectName> map = DruidDataSourceStatManager.getInstances();
		if(null == map || map.size() == 0) return;
		//String dataSourceName = this.dataSource.getName();
		boolean isRemove = false;
		Set<Object> dses = map.keySet();
		for(Object ds : dses){
			DruidDataSource dds = (DruidDataSource)ds;
			log.info("ds.id="+dds.getID()+",dataSource.id="+dataSource.getID());
			if(dds.getID() == dataSource.getID()){
				isRemove = true;
				break;
			}
		}
		if(!isRemove) return;
		String dataSourceName = this.dataSource.getName();
		DruidDataSourceStatManager.removeDataSource(dataSource);
		dataSource = null;
		if(this.dataSource.getName().equals(dataSourceName)) this.dataSource = null;
	}
	
	public synchronized DruidDataSource getDataSource(FinSysCfgEntity entity) throws ServiceException{
		this.entity = entity;
		setProps();
		return dataSource;
	}
	
	private void init(String finSysCode) throws ServiceException{
		initService();
		entity = finSysCfgService.getCfgByCode(finSysCode);
		log.info("port:"+entity.getPort());
		if(null == entity) throw new ServiceException("编号为"+finSysCode+"的财务系统在财务系统配置功能中未配置或是禁用状态!");
	}
	
	private void initService(){
		finSysCfgService = (FinSysCfgService)SpringContextUtil.getBean("finSysCfgService");
	}
	
	
	private void setProps(){
		String code = entity.getCode();
		flagMap.put(code, true);/*表示已加载过*/
		Integer maxSize = entity.getMaxSize();
		if(null == maxSize) maxSize = 10;
		dataSource = new DruidDataSource();
		dataSource.setName(code);
	    dataSource.setMaxActive(maxSize);
	    dataSource.setMinIdle(1);
	    dataSource.setInitialSize(1);
	    dataSource.setPoolPreparedStatements(true);
	    dataSource.setTestOnBorrow(false);
	    dataSource.setTestOnReturn(false);
	    dataSource.setMinEvictableIdleTimeMillis(30);
	    dataSource.setMaxWaitThreadCount(1000);
	    setCustProps();
	}

	private void setCustProps() {
		Map<String,String> cfgMap = getProp();
	    dataSource.setDriverClassName(cfgMap.get("driver"));
	    dataSource.setUrl(cfgMap.get("url"));
	    dataSource.setUsername(cfgMap.get("username"));
	    dataSource.setPassword(cfgMap.get("password"));
	}
	
	/**
	 * 标记要重新加载指定编号的财务系统接口数据源
	 * @param finsysCode 要重新加载数据源的财务系统编号
	 */
	public static void markChanged(String finsysCode){
		flagMap.put(finsysCode, false);
	}
	
	/**
	 * 判断指定的数据源是否需要重新加载
	 * @param finsysCode 要重新加载数据源的财务系统编号
	 * @return 	返回 Boolean 值, true : 要重新加载,false : 不重新加载
	 */
	public static boolean isReloadDs(String finsysCode){
		if(null == flagMap || flagMap.size() == 0) return true;
		boolean flag = flagMap.get(finsysCode);
		return !flag;
	}
	
	private Map<String,String> getProp(){
		String driver = null;
		String url = null;
		String server = entity.getServer();
		int port = entity.getPort();
		String dbName = entity.getDbName();
		String username = entity.getLoginName();
		String password = entity.getPassWord();
		Byte dbType = entity.getDbType();
		switch (dbType.intValue()) {
			case FinSysCfgEntity.DBTYPE_1:{
				driver = "net.sourceforge.jtds.jdbc.Driver";
				url = "jdbc:jtds:sqlserver://"+server+":"+port+"/"+dbName;
				break;
			}case FinSysCfgEntity.DBTYPE_2:{
				driver = "com.mysql.jdbc.Driver";
				url = "jdbc:mysql://"+server+":"+port+"/"+dbName;
				break;
			}case FinSysCfgEntity.DBTYPE_3:{
				driver = "oracle.jdbc.driver.OracleDriver";
				url = "jdbc:oracle:thin:@"+server+":"+port+":"+dbName;
				break;
			}
			default:
				break;
		}
		Map<String,String> map = new HashMap<String, String>();
		map.put("driver", driver);
		map.put("url", url);
		map.put("username", username);
		map.put("password", password);
		return map;
	}
	
	
	public FinSysCfgEntity getEntity() {
		return entity;
	}

	public void setEntity(FinSysCfgEntity entity) {
		this.entity = entity;
	}

	/**
	 * 获取工厂实例
	 * @return DbPool 对象
	 */
	public static DbPool getInstance(){
		return LazyHolder.INSTANCE;
	}
	
	private static final class LazyHolder{
		private static final DbPool INSTANCE = new DbPool();
	}
}
