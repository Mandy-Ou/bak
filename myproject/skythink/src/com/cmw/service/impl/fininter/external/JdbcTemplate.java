package com.cmw.service.impl.fininter.external;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.alibaba.druid.pool.DruidDataSource;
import com.cmw.core.base.exception.ServiceException;

public abstract class JdbcTemplate {
	protected static Logger log = Logger.getLogger("com.cmw.service.impl.fininter.external.JdbcTemplate");
	/**
	 * 获取数据源
	 * @param entity
	 * @return
	 */
	protected abstract DruidDataSource getDataSource()throws ServiceException;
	/**
	 * 获取数据集列表
	 * @param sql 要查询的 SQL 语句
	 * @param handler	结果集处理对象
	 * @return 返回实体 列表
	 * @throws ServiceException
	 */
	public <T> List<T> getList(String sql,ResultSetHandler<T> handler) throws ServiceException {
		Connection conn = getConn();
		PreparedStatement pst = null;
		ResultSet rst = null;
		List<T> list = null;
		try {
			pst = conn.prepareStatement(sql);
			rst = pst.executeQuery();
			list = new ArrayList<T>();
			while(rst.next()){
				T obj = handler.execute(rst);
				list.add(obj);
			}
			return  list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}finally{
			try {
				if(null != rst) rst.close();
				if(null != pst) pst.close();
				if(null != conn) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				log.info("close rst or pst or conn failure ... ");
			}
			
		}
	}
	
	
	
	protected Connection getConn() throws ServiceException{
			Connection conn = null;
		try {
			log.info("invoke getConn ---> start getDataSource ... ");
			DruidDataSource dataSource = getDataSource();
			conn = dataSource.getConnection();
			log.info("invoke getConn ---> success ... ");
			return conn;
		} catch (SQLException e) {
			e.printStackTrace();
//			String errMsg = e.getMessage();
			log.info("CMW.ERROR:"+e.getMessage());
//			if(StringHandler.isValidStr(errMsg) && errMsg.indexOf("Connection timed out: connect") != -1){
//				throw new ServiceException(ServiceException.NETWORK_CONN_ERROR);
//			}else{
				throw new ServiceException(e);
//			}
		}
	}
}
