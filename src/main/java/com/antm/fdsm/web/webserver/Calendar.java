package com.antm.fdsm.web.webserver;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.pmw.tinylog.Logger;

import com.antm.fdsm.orcl.odc.DatabaseService;
import com.antm.fdsm.orcl.utils.Singleton;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class Calendar {

	private final static Singleton dbCdbdfmService = Singleton.CDBDFM.setDirs(Def.DIRS).setSlackApp(Def.SLACK_WEBHOOK_APP);
	
	public static void main(String[] args ) {
		
		Logger.info("Create calendar");
		JsonArray events = new JsonArray();
		events = getEvents();
		String eventsPretty = events.encodePrettily(); 
		System.out.println(eventsPretty);
		
		String html = createHtml(events);
		System.out.println(html);
		
	}

	public String createCalendar() {
		
		Logger.info("Create calendar");
		JsonArray events = new JsonArray();
		events = getEvents();
		String eventsPretty = events.encodePrettily(); 
		System.out.println(eventsPretty);
		
		String calendarHtml = createHtml(events);
		return calendarHtml;
		
	}
	
	private static JsonArray getEvents() {

		Logger.info("Get calendar events");
		List<JsonArray> eventData;
		JsonArray events = new JsonArray();
		DatabaseService cdbdfm = new DatabaseService(dbCdbdfmService);
		String sqlSelect =  
				"select\n" +
				"  ID,\n" +
				"  TITLE,\n" +
				"  ALLDAY,\n" +
				"  to_char(STARTDT,'YYYY-MM-DD') || 'T' || to_char(STARTDT,'HH24:MI:SS'),\n" +
				"  to_char(ENDDT,'YYYY-MM-DD') || 'T' || to_char(ENDDT,'HH24:MI:SS'),\n" +
				"  URL,\n" +
				"  HOLIDAY\n" +
				"from calendar";
		try {
			Logger.info("Run sql query");
			eventData = cdbdfm.query(sqlSelect).get();
			Logger.info("Retrieved [{}] records",eventData.size());
			
			if ( eventData.size() == 0 ) {
				System.out.println("No records!");
			}
			else {
				for (int i = 0 ; i < eventData.size(); i++ ) {
					JsonObject event = new JsonObject();
					String id      = eventData.get(i).getString(0);
					String title   = eventData.get(i).getString(1);
					String allDay  = eventData.get(i).getString(2);
					String start   = eventData.get(i).getString(3);
					String end     = eventData.get(i).getString(4);
					String url     = eventData.get(i).getString(5);
					String holiday = eventData.get(i).getString(6);

					event.put("id", id);
					event.put("title", title);
					if ( allDay.matches("Y") ) {
						event.put("allDay", true);
					}
					event.put("start", start);
					if ( ! end.isEmpty()) {
						event.put("end", end);
					}
					if ( ! url.isEmpty()) {
						event.put("url", url);
					}
					events.add(event);
					if ( holiday.matches("Y") ) {
						event.put("color", "#D6EAF8");
					}

				}
			}
					
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		return events;
	}


	private static String createHtml(JsonArray events) {
	
		String html = "<!DOCTYPE html>\r\n" + 
				"<html>\r\n" + 
				"<head>\r\n" + 
				"<meta charset='utf-8' />\r\n" + 
				"<link href='../fullcalendar.min.css' rel='stylesheet' />\r\n" + 
				"<link href='../fullcalendar.print.min.css' rel='stylesheet' media='print' />\r\n" + 
				"<script src='../lib/moment.min.js'></script>\r\n" + 
				"<script src='../lib/jquery.min.js'></script>\r\n" + 
				"<script src='../fullcalendar.min.js'></script>\r\n" + 
				"<script>\r\n" + 
				"\r\n" + 
				"  $(document).ready(function() {\r\n" + 
				"\r\n" + 
				"    $('#calendar').fullCalendar({\r\n" + 
				"      editable: true,\r\n" + 
				"      eventLimit: true, // allow \"more\" link when too many events\r\n" + 
				"      events: \r\n" +
				events +
				"    });\r\n" + 
				"\r\n" + 
				"  });\r\n" + 
				"\r\n" + 
				"</script>\r\n" + 
				"<style>\r\n" + 
				"\r\n" + 
				"  body {\r\n" + 
				"    margin: 40px 10px;\r\n" + 
				"    padding: 0;\r\n" + 
				"    font-family: \"Lucida Grande\",Helvetica,Arial,Verdana,sans-serif;\r\n" + 
				"    font-size: 14px;\r\n" + 
				"  }\r\n" + 
				"\r\n" + 
				"  #calendar {\r\n" + 
				"    max-width: 900px;\r\n" + 
				"    margin: 0 auto;\r\n" + 
				"  }\r\n" + 
				"\r\n" + 
				"</style>\r\n" + 
				"</head>\r\n" + 
				"<body>\r\n" + 
				"\r\n" + 
				"  <div id='calendar'></div>\r\n" + 
				"\r\n" + 
				"</body>\r\n" + 
				"</html>";
		return html;
	}
	
}
