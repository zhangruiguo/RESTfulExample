package com.mkyong.customer.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.alibaba.fastjson.JSON;
import com.manager.Serialize;
import com.mkyong.customer.CustomerBo;


public class CustomerBoImpl implements CustomerBo {

	public String getMsg() {

		return "RESTEasy + Spring example11222211";
		
	}
	static com.db.DBHelper db1 = null;
	static ResultSet rs = null;
	static java.sql.ResultSetMetaData data = null;
	public String getData() {
		String str=null;
		db1 = new com.db.DBHelper("show tables;");// 创建DBHelper对象
		try {
			rs = db1.pst.executeQuery();// 执行语句，得到结果集
			data = rs.getMetaData();
			str = Serialize.SerializeWithSchema(rs, data, ",");
			str= JSON.toJSONString(str);
			
			rs.last();
			db1.close();// 关闭连接
		} catch (SQLException e) {
			str = e.getMessage();
			str= JSON.toJSONString(str);
			e.printStackTrace();
		}
		return str;
		
	}
}