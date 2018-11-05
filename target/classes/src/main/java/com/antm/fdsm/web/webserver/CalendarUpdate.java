package com.antm.fdsm.web.webserver;

import org.pmw.tinylog.Logger;

import com.antm.fdsm.orcl.utils.Singleton;

import io.vertx.core.http.HttpServerRequest;

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
		
	}
}
