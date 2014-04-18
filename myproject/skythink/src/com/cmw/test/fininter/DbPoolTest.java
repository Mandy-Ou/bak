package com.cmw.test.fininter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

import com.alibaba.druid.pool.DruidDataSource;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.test.AbstractTestCase;
import com.cmw.service.impl.fininter.external.DbPool;

public class DbPoolTest  extends AbstractTestCase {
	
	@Test
	public void testGetDataSource(){
		DbPool pool = DbPool.getInstance();
		try {
			DruidDataSource dataSource = pool.getDataSource("FS0001");
			try {
				Connection conn = dataSource.getConnection();
				String sql = "select * from t_user";
				PreparedStatement pst = conn.prepareStatement(sql);
				ResultSet rst = pst.executeQuery();
				while(rst.next()){
					String FUserId = rst.getString("FUserID");
					String FName = rst.getString("FName");
					System.out.println("FUserId="+FUserId+",FName="+FName);
					System.out.println("---------------------------------");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
}
