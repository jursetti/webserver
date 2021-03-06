package com.antm.fdsm.web.webserver;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.pmw.tinylog.Logger;

import com.antm.fdsm.orcl.odc.DatabaseService;
import com.antm.fdsm.orcl.utils.Singleton;

import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class CalendarUpdate {

	private final static Singleton dbCdbdfmService = Singleton.CDBDFM.setDirs(Def.DIRS).setSlackApp(Def.SLACK_WEBHOOK_APP);
	
	public static void main(String[] args ) {
		Logger.info("Updating calendar");
		
	}
	
	public void calendarUpdate ( HttpServerRequest request) {
		Logger.info("Updating calendar");
		String event     = request.getParam("event");
		String startDate = request.getParam("startDate");
		String endDate   = request.getParam("endDate");
		String holiday   = request.getParam("holiday");
		String url       = request.getParam("url");
		
		Logger.info("Event: {}", event);
		Logger.info("Start Date: {}", startDate);
		Logger.info("End Date: {}", endDate);
		Logger.info("Holiday: {}", holiday);
		Logger.info("URL: {}", url);
		
		// Insert new event into the calendar table
		// Sample date: 11/06/2018 9:57 AM
		
		// Get max id
		DatabaseService cdbdfm = new DatabaseService(dbCdbdfmService);
		String sqlSelect =  
				"select\n" +
				"  max(ID)\n" +
				"from calendar";
		int lastId = 0;
		try {
			Logger.info("Run sql query to get max ID");
			List<JsonArray> maxId;
			maxId = cdbdfm.query(sqlSelect).get();
			for (int i = 0 ; i < maxId.size(); i++ ) {
				//JsonObject event = new JsonObject();
				lastId = Integer.parseInt(maxId.get(i).getString(0));
			}
			Logger.info("Max ID: [{}]",lastId);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		int nextId = lastId + 1;
		
		// Date format string
		String dateFormat = "MM/DD/YYYY HH:MI AM";
		String startDateFmt = "TO_DATE('" + startDate + "','" + dateFormat + "')";
		
		String endDateFmt = "TO_DATE('" + endDate + "','" + dateFormat + "')";
		if ( isNullOrEmpty(endDate) ) {
			endDateFmt = "''";	
		}
			
		String allDay = "Y";
		if ( ! startDate.contains("12:00 AM") ) {
			allDay = "N";
		}
		
		// Is the holiday checkbox is checked the value is on. If not checked the value is null.
		String isHoliday = "Y";
		if ( isNullOrEmpty(holiday) ) {
			isHoliday = "N";
		}
		
		Logger.info("Insert data: {},{},{},{},{},{},{}",nextId,event,startDateFmt,endDateFmt,allDay,isHoliday,url);
		
		String sqlInsert =  
		  "INSERT INTO FDSMOPS.CALENDAR ( ID, TITLE, ALLDAY, STARTDT, ENDDT, URL, HOLIDAY )\n" +
		  "VALUES ( " + nextId + ",'" + event + "','"+ allDay + "'," + startDateFmt + "," + endDateFmt + ",'" + url + "','" + isHoliday + "')";
		
		Logger.info("Insert SQL: {}",sqlInsert);
		
		cdbdfm.executeUpdate(sqlInsert);
		
		return;

	}
	
    public static boolean isNullOrEmpty(String str) {
        if(str != null && !str.isEmpty())
            return false;
        return true;
    }
}
