// Copyright © 2010 - June 2014 Rise Vision Incorporated.
// Use of this software is governed by the GPLv3 license
// (reproduced in the LICENSE file).

package com.risedisplay.ems.web;

import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;
import com.google.visualization.datasource.datatable.ColumnDescription;
import com.google.visualization.datasource.datatable.value.DateTimeValue;
import com.google.visualization.datasource.datatable.value.DateValue;
import com.google.visualization.datasource.datatable.value.TimeOfDayValue;
import com.google.visualization.datasource.datatable.value.Value;
import com.google.visualization.datasource.datatable.value.ValueType;
import com.google.visualization.datasource.query.AbstractColumn;
import com.google.visualization.datasource.query.Query;
import com.ibm.icu.util.GregorianCalendar;
import com.ibm.icu.util.TimeZone;

public class GVUtils {
	
	static public Value DateToDateTimeValue(Date date) {
		
		if (date == null)
			return Value.getNullValueFromValueType(ValueType.DATETIME);
		
		GregorianCalendar result = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
		result.setTime(date);
		return new DateTimeValue(result);
	}
	
	static public Value DateToDateValue(Date date) {
		
		if (date == null)
			return Value.getNullValueFromValueType(ValueType.DATE);
		
		GregorianCalendar result = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
		result.setTime(date);
		return new DateValue(result);
	}
	
	static public Value DateToTimeValue(Date date) {
		
		if (date == null)
			return Value.getNullValueFromValueType(ValueType.TIMEOFDAY);
		
		GregorianCalendar result = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
		result.setTime(date);
		return new TimeOfDayValue(result);
	}
	

	static public boolean isColumnRequested(Query query, String columnName) {

		// If the query is empty return true.
		if (query == null || query.isEmpty()) {
			return true;
		}

		List<AbstractColumn> columns = query.getSelection().getColumns();
		for (AbstractColumn column : columns) {
			if (column.getId().equalsIgnoreCase(columnName)) {
				return true;
			}
		}
		
		return false;
	}

	static public List<ColumnDescription> getRequiredColumns(Query query, ColumnDescription[] availableColumns) {
		
		List<ColumnDescription> requiredColumns = Lists.newArrayList();
		
		for (ColumnDescription column : availableColumns) {
			if (isColumnRequested(query, column.getId())) {
				requiredColumns.add(column);
			}
		}
		
		return requiredColumns;
	}
	
}
