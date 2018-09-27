package com.antm.fdsm.web.webserver;

import com.antm.fdsm.web.webserver.Def;
import com.antm.fdsm.web.webserver.Calendar;
import org.pmw.tinylog.Logger;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.net.JksOptions;

public class Start extends AbstractVerticle {

	private static final int PORT = 8443;
	private static final String WEBROOT = "content/";
	
    private HttpServer httpServer = null;

    @Override
    public void start() throws Exception {
    	
    	System.out.println(Def.SLINE);
		Logger.info("Get jks file");
        JksOptions jks = new JksOptions();
        jks.setPath("/home/opc/certs/keystore.jks");
        jks.setPassword("Antm2017#");
        
        Logger.info("Create HTTP Server");
        httpServer = vertx.createHttpServer(new HttpServerOptions().setSsl(true).setKeyStoreOptions(jks).setLogActivity(true));

        Logger.info("Create request handler");
        httpServer.requestHandler(new Handler<HttpServerRequest>() {
            public void handle(HttpServerRequest request) {
            	Logger.info("Launch page " + request.path());
                if (request.path().equals("/")) {
                	request.response().sendFile(WEBROOT + "index.html");
                }
                else if  (request.path().equals("/index.html")) {
                	request.response().sendFile(WEBROOT + "index.html");
                }
                else if  (request.path().equals("/calendar")) {
                	Logger.info("Create calendar object");
                	Calendar calendar = new Calendar();
                	Logger.info("Create calendarHtml");
                	String calendarHtml = calendar.createCalendar();
            	  	System.out.println(calendarHtml);
                	request.response().end(calendarHtml);
                }
                else {
                	request.response().sendFile(WEBROOT + request.path());
                }
            }
        });

        Logger.info("Start HTTP server on port " + PORT);
        httpServer.listen(PORT, res -> {
            if (res.succeeded()) {
            	Logger.info("HTTP server is ready!");
              } else {
            	  Logger.info("HTTP server failed!");
              }
            });
    }
    
}

