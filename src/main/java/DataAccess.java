package com.xx.fx.deals;

import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DataAccess {

	@Autowired
	private JdbcTemplate jdbc;

	private Logger logger = LoggerFactory.getLogger(DataAccess.class);

	////////////////////////////////////////////////////////////////////////////////
	public void insertRequest(String tableName, Map<String, Object> keysAndValues) {
		try {
			logger.debug("insertRequest({},{})", tableName, keysAndValues);
			Object[] values = keysAndValues.values().toArray();
			final String columns = keysAndValues.keySet().stream().collect(Collectors.joining(", "));
			String fields = populateParameterMarkers(values.length);
			String query = "INSERT INTO " + tableName + " ( " + columns + " ) VALUES ( " + fields + " )";
			jdbc.update(query, values);
		} finally {
			logger.debug("/insertRequest({},{})", tableName, keysAndValues);
		}
	}

	////////////////////////////////////////////////////////////////////////////////
	protected String populateParameterMarkers(int length) {
		StringBuilder marker = new StringBuilder();
		for (int i = 0; i < length; i++) {
			marker.append("?");
			if (i < length - 1) {
				marker.append(", ");
			}
		}
		return marker.toString();
	}

}
