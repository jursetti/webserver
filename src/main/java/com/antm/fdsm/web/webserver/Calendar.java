package com.antm.fdsm.web.webserver;

import java.text.NumberFormat;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.pmw.tinylog.Logger;

import com.antm.fdsm.orcl.odc.DatabaseService;
import com.antm.fdsm.orcl.utils.Singleton;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class Calendar {

	private final static Singleton dbHypfinusrdService = Singleton.HYPFINUSRD.setDirs(Def.DIRS).setSlackApp(Def.SLACK_WEBHOOK_APP);
	
	public void main (String[] args ) {
		
		Logger.info("Create calendar");
		JsonArray events = new JsonArray();
		events = getEvents();
		System.out.println(events);
		
	}

	private JsonArray getEvents() {

		Logger.info("Get calendar events");
		JsonObject event = new JsonObject();
		JsonArray events = new JsonArray();
		DatabaseService hypfinusrd = new DatabaseService(dbHypfinusrdService);
		String sqlSelect =  
				"select\n" +
				"  ID,\n" +
				"  TITLE,\n" +
				"  ALLDAY,\n" +
				"  to_char(STARTDT,'YYYY-MM-DD') || 'T' || to_char(STARTDT,'HH24:MI:SS'),\n" +
				"  to_char(ENDDT,'YYYY-MM-DD') || 'T' || to_char(ENDDT,'HH24:MI:SS'),\n" +
				"  URL\n" +
				"from calendar";
		List<JsonArray> eventData;
		try {
			eventData = hypfinusrd.query(sqlSelect).get();
			
			if ( eventData.size() == 0 ) {
				System.out.println("No records!");
			}
			else {
				for (int i = 0 ; i < eventData.size(); i++ ) {
					int id = eventData.get(i).getInteger(0);
					String title = eventData.get(i).getString(1);
					String allDay = eventData.get(i).getString(2);
					String start = eventData.get(i).getString(3);
					String end = eventData.get(i).getString(4);
					String url= eventData.get(i).getString(5);
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
				}
			}
					
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		return events;
	}
}
