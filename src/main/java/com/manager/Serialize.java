package com.manager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Serialize {
	// ��ȡsql�쳣��Ϣ
		public static HashMap<String, Object> ReturnErr(String err) {
			HashMap<String, Object> mp = new HashMap<String, Object>();
			HashMap<String, Object> map_col = new HashMap<String, Object>();
			map_col.put("field", "error");
			map_col.put("width", 120);
			map_col.put("type", "String");
			HashMap<String, Object> map_val = new HashMap<String, Object>();
			map_val.put("error", err);
			List<HashMap<String, Object>> list_col = new ArrayList<HashMap<String, Object>>();
			List<HashMap<String, Object>> list_val = new ArrayList<HashMap<String, Object>>();
			list_col.add(map_col);
			list_val.add(map_val);
			mp.put("columns", list_col);
			mp.put("rows", list_val);
			return mp;
		}

		// ���ݽ��������Map��
		public static HashMap<String, Object> SerializeWithRequest(ResultSet rs, java.sql.ResultSetMetaData data, String seperate)
				throws SQLException {
			HashMap<String, Object> m = new HashMap<String, Object>();
			List<HashMap<String, Object>> list_col = new ArrayList<HashMap<String, Object>>();
			List<HashMap<String, Object>> list_val = new ArrayList<HashMap<String, Object>>();

			for (int i = 1; i <= data.getColumnCount(); i++) {
				// ���ָ���е�����
				String columnName = data.getColumnName(i);
				// ���ָ���е���������
				int columnType = data.getColumnType(i);
				HashMap<String, Object> map_col = new HashMap<String, Object>();
				map_col.put("field", columnName);
				map_col.put("width", 120);
				map_col.put("type", "String");
				list_col.add(map_col);
			}
			while (rs.next()) {
				HashMap<String, Object> map_val = new HashMap<String, Object>();
				for (int i = 1; i <= data.getColumnCount(); i++) {
					// ���ָ���е�����
					String columnName = data.getColumnName(i);
					// ���ָ���е���ֵ
					Object columnValue = rs.getString(i);
					map_val.put(columnName, columnValue);
				}
				list_val.add(map_val);
			} // ��ʾ����

			m.put("columns", list_col);
			m.put("rows", list_val);
			return m;
		}

		// ���ݽ��������json��
		public static String SerializeWithSchema(ResultSet rs, java.sql.ResultSetMetaData data, String seperate)
				throws SQLException {
			StringBuilder sb = new StringBuilder();
			int resultFieldCnt = 0;
			int rowCount = 0;
			sb.append("{\"columns\":");
			// sb.append("{columns:");
			sb.append("[");
			for (int i = 1; i <= data.getColumnCount(); i++) {
				// ���ָ���е�����
				String columnName = data.getColumnName(i);
				// ���ָ���е���������
				int columnType = data.getColumnType(i);

				if (i == data.getColumnCount()) {
					sb.append("{\"field\":\"").append(columnName).append("\",\"width\":120,\"type\":\"" + "string" + "\"}");
					// sb.append("{field:").append(columnName).append(",width:120,type:"
					// + "string" + "}");
				} else {
					sb.append("{\"field\":\"").append(columnName)
							.append("\",\"width\":120,\"type\":\"" + "string" + "\"},");
					// sb.append("{field:").append(columnName).append(",width:120,type:"
					// + "string" + "},");
				}
			}
			// StripComma(sb, 1);
			// sb.append("]");
			sb.append(",\"rows\":[");
			sb.append(",rows:[");

			rs.last(); // �Ƶ����һ��
			int allCount = rs.getRow(); // �õ���ǰ�кţ�Ҳ���Ǽ�¼��
			rs.beforeFirst(); // �����Ҫ�ý�������Ͱ�ָ�����Ƶ���ʼ����λ��
			while (rs.next()) {
				sb.append("{");
				for (int i = 1; i <= data.getColumnCount(); i++) {
					// ���ָ���е�����
					String columnName = data.getColumnName(i);
					sb.append("\"" + columnName + "\":");
					// sb.append(columnName + ":");
					// ���ָ���е���ֵ
					Object columnValue = rs.getString(i);
					sb.append("\"" + (String) columnValue + "\"");
					// sb.append(columnValue);
					if (i < data.getColumnCount()) {
						sb.append(",");
					}
					resultFieldCnt++;
				}

				sb.append("}");
				if ((rowCount + 1) < allCount) {
					sb.append(seperate);
				}
				rowCount++;
			} // ��ʾ����

			sb.append("]}");
			return sb.toString();
		}

		/// Strips a comma off a string builder. In Format mode
		private static void StripComma(StringBuilder sb, int k) {
			sb.delete(sb.length() - k, k); // ,\r\n
		}
}
