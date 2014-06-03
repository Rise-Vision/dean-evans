// Copyright © 2010 - June 2014 Rise Vision Incorporated.
// Use of this software is governed by the GPLv3 license
// (reproduced in the LICENSE file).

package com.risedisplay.ems.web;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import com.google.common.collect.Lists;
import com.google.visualization.datasource.DataSourceServlet;
import com.google.visualization.datasource.base.DataSourceException;
import com.google.visualization.datasource.base.TypeMismatchException;
import com.google.visualization.datasource.datatable.ColumnDescription;
import com.google.visualization.datasource.datatable.DataTable;
import com.google.visualization.datasource.datatable.TableRow;
import com.google.visualization.datasource.datatable.value.Value;
import com.google.visualization.datasource.datatable.value.ValueType;
import com.google.visualization.datasource.query.Query;

public class BookingsServlet extends DataSourceServlet  {

	private static final long serialVersionUID = -8799344624632154332L;
	
	private static final ColumnDescription[] TABLE_COLUMNS = new ColumnDescription[] {
		new ColumnDescription("BookingDate", ValueType.TEXT, "BookingDate"),
		new ColumnDescription("StartBookingDate", ValueType.TEXT, "StartBookingDate"),
		new ColumnDescription("RoomDescription", ValueType.TEXT, "RoomDescription"),
		new ColumnDescription("TimeEventStart", ValueType.TEXT, "TimeEventStart"),
		new ColumnDescription("TimeEventEnd", ValueType.TEXT, "TimeEventEnd"),
		new ColumnDescription("GroupName", ValueType.TEXT, "GroupName"),
		new ColumnDescription("EventName", ValueType.TEXT, "EventName"),
		new ColumnDescription("SetupTypeDescription", ValueType.TEXT, "SetupTypeDescription"),
		new ColumnDescription("SetupCount", ValueType.TEXT, "SetupCount"),
		new ColumnDescription("ReservationID", ValueType.TEXT, "ReservationID"),
		new ColumnDescription("EventCoordinator", ValueType.TEXT, "EventCoordinator"),
		new ColumnDescription("GroupID", ValueType.TEXT, "GroupID"),
		new ColumnDescription("VIP", ValueType.TEXT, "VIP"),
		new ColumnDescription("VIPEvent", ValueType.TEXT, "VIPEvent"),
		new ColumnDescription("ClosedAllDay", ValueType.TEXT, "ClosedAllDay"),
		new ColumnDescription("OpenTime", ValueType.TEXT, "OpenTime"),
		new ColumnDescription("CloseTime", ValueType.TEXT, "CloseTime"),
		new ColumnDescription("GroupTypeDescription", ValueType.TEXT, "GroupTypeDescription"),
		new ColumnDescription("EventTypeDescription", ValueType.TEXT, "EventTypeDescription"),
		new ColumnDescription("Contact", ValueType.TEXT, "Contact"),
		new ColumnDescription("BookingID", ValueType.TEXT, "BookingID"),
		new ColumnDescription("TimeBookingStart", ValueType.TEXT, "TimeBookingStart"),
		new ColumnDescription("TimeBookingEnd", ValueType.TEXT, "TimeBookingEnd"),
		new ColumnDescription("GMTStartTime", ValueType.TEXT, "GMTStartTime"),
		new ColumnDescription("GMTEndTime", ValueType.TEXT, "GMTEndTime"),
		new ColumnDescription("BuildingCode", ValueType.TEXT, "BuildingCode"),
		new ColumnDescription("Building", ValueType.TEXT, "Building"),
		new ColumnDescription("RoomCode", ValueType.TEXT, "RoomCode"),
		new ColumnDescription("Room", ValueType.TEXT, "Room"),
		new ColumnDescription("RoomID", ValueType.TEXT, "RoomID"),
		new ColumnDescription("BuildingID", ValueType.TEXT, "BuildingID"),
		new ColumnDescription("StatusID", ValueType.TEXT, "StatusID"),
		new ColumnDescription("StatusTypeID", ValueType.TEXT, "StatusTypeID"),
		new ColumnDescription("EventTypeID", ValueType.TEXT, "EventTypeID"),
		new ColumnDescription("GroupTypeID", ValueType.TEXT, "GroupTypeID"),
		new ColumnDescription("DateAdded", ValueType.TEXT, "DateAdded"),
		new ColumnDescription("AddedBy", ValueType.TEXT, "AddedBy"),
		new ColumnDescription("DateChanged", ValueType.TEXT, "DateChanged"),
		new ColumnDescription("ChangedBy", ValueType.TEXT, "ChangedBy"),
	};

	@Override
	protected boolean isRestrictedAccessMode() {
		return false; // this avoids cross-domain issues
	}

	@SuppressWarnings("deprecation")
	@Override
	public DataTable generateDataTable(Query query, HttpServletRequest request)	throws DataSourceException {
		
		String buildingId = request.getParameter("buildingId");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		
		if (buildingId == null || buildingId.isEmpty() || startDate == null || startDate.isEmpty() || endDate == null || endDate.isEmpty()) {
			
			return getEmptyTable();
		}
		
		Date startTime = new Date();

		DataTable data = null;
		
		Properties configFile = new Properties();
		try {
			
			configFile.load(new FileInputStream(request.getRealPath("/emsservice.conf")));
			List<Map<String, String>> bookings = sendPostRequest(configFile.getProperty("url"), configFile.getProperty("username"), configFile.getProperty("password"), buildingId, startDate, endDate);
			
			data = new DataTable();
			
			List<ColumnDescription> requiredColumns = GVUtils.getRequiredColumns(query, TABLE_COLUMNS);
			data.addColumns(requiredColumns);
			
			if (bookings != null) {
				
				for (Map<String, String> booking : bookings) {

					try {

						TableRow row = new TableRow();

						for (ColumnDescription selectionColumn : requiredColumns) {

							row.addCell(booking.get(selectionColumn.getId()));
						}

						data.addRow(row);

					} catch (TypeMismatchException e) {
						e.printStackTrace();
						data = getEmptyTable(); // empty table
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			data = getEmptyTable(); // empty table
		}

		Date endTime = new Date();
		System.out.println("Table generated in " + Double.toString((endTime.getTime() - startTime.getTime()) / 1000.0) + "sec");

		return data;
	}

	private DataTable getEmptyTable() throws TypeMismatchException {

		DataTable result = new DataTable();

		List<ColumnDescription> outputColumns = Lists.newArrayList();

		outputColumns.add(new ColumnDescription("NODATA", ValueType.TEXT, ""));

		result.addColumns(outputColumns);

		TableRow row = new TableRow();
		row.addCell(Value.getNullValueFromValueType(ValueType.TEXT));

		result.addRow(row);

		return result;
	}
	
	private List<Map<String, String>> sendPostRequest(String apiUrl, String username, String password, String buildingId, String startDate, String endDate) {
		
		StringBuilder payload = new StringBuilder();
		payload.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">");
		payload.append("<soap:Body><GetAllBookings xmlns=\"http://DEA.EMS.API.Web.Service/\">");
		payload.append("<UserName>");
		payload.append(username);
		payload.append("</UserName>");
		payload.append("<Password>");
		payload.append(password);
		payload.append("</Password>");
		payload.append("<StartDate>");
		payload.append(startDate);
		payload.append("</StartDate>");
		payload.append("<EndDate>");
		payload.append(endDate);
		payload.append("</EndDate>");
		payload.append("<BuildingID>");
		payload.append(buildingId);
		payload.append("</BuildingID>");
		payload.append("<ViewComboRoomComponents>false</ViewComboRoomComponents>");
		payload.append("</GetAllBookings></soap:Body></soap:Envelope>");
		
		 try {
		        URL url = new URL(apiUrl);
		        URLConnection conn;
		        conn = url.openConnection();
		        conn.setDoOutput(true);
		        conn.setDoInput(true);
		        conn.addRequestProperty("SOAPAction", "http://DEA.EMS.API.Web.Service/GetAllBookings");
		        conn.addRequestProperty("Content-Type", "text/xml; charset=utf-8");
		        conn.addRequestProperty("Content-Encoding", "UTF-8");
		        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		        //System.out.println(payload.toString());
		        wr.write(payload.toString());
		        wr.flush(); 
		        // Get the response 
		        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream())); 
		        
		        List<Map<String, String>> bookings = new ArrayList<Map<String,String>>();
		        Map<String, String> booking = null;
	
		        String line; 
		        
		        while ((line = rd.readLine()) != null) {
		        	//System.out.println(line);
		        	line = line.trim();
		        	
		        	if (line.startsWith("&lt;") && !line.startsWith("&lt;/Bookings&gt;")) {

		        		if (line.equals("&lt;Data&gt;")) {

		        			booking = new HashMap<String, String>();

		        		} else if (line.equals("&lt;/Data&gt;")) {

		        			if (booking != null) {
		        				bookings.add(booking);
		        			}

		        		} else {

		        			int firstBreak = line.indexOf("&gt;");
		        			int secondBreak = line.lastIndexOf("&lt;/");
		        			String key = line.substring(4, firstBreak);
		        			int spaceBreak = key.indexOf(" ");
		        			String value = secondBreak != -1 ? line.substring(firstBreak + 4, secondBreak) : "";
		        			booking.put(spaceBreak != -1 ? key.substring(0, spaceBreak) : key, value);
		        			//System.out.println((spaceBreak != -1 ? key.substring(0, spaceBreak) : key) + "=" + value );
		        		}
		        	}
		        	
		        	
		        } 
		        wr.close(); 
		        rd.close(); 
		        return bookings;
		        
		    } catch (Exception e) {

		        e.printStackTrace();
		        return null;
		    }		
	}
	
	

}
