package com.antm.fdsm.web.webserver;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
//import io.vertx.core.Future;
//import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.net.JksOptions;
//import io.vertx.core.net.NetServerOptions;

public class Start extends AbstractVerticle {

	private static final int PORT = 8443;
	
    private HttpServer httpServer = null;

    @Override
    public void start() throws Exception {
    	
		System.out.println("Get jks file");
        JksOptions jks = new JksOptions();
        jks.setPath("/home/opc/certs/keystore.jks");
        jks.setPassword("Antm2017#");
        
        System.out.println("Create HTTP Server");
        httpServer = vertx.createHttpServer(new HttpServerOptions().setSsl(true).setKeyStoreOptions(jks).setLogActivity(true));

        System.out.println("Create request handler");
        httpServer.requestHandler(new Handler<HttpServerRequest>() {
            public void handle(HttpServerRequest request) {
                System.out.println("HTTP Server is running");
            }
        });

        System.out.println("Start HTTP server on port " + PORT);
        httpServer.listen(PORT, res -> {
            if (res.succeeded()) {
                System.out.println("HTTP server is ready!");
              } else {
                System.out.println("HTTP server failed!");
              }
            });
    }
    
	/*
	public void main() {
		try {
			start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	*/
	
    /*
    public void start() throws Exception {

        Vertx vertx = Vertx.vertx();
        
		System.out.println("Get jks file");
        JksOptions jks = new JksOptions();
        jks.setPath("/home/opc/certs/fdsm-compute-dev-adm-web.anthem.com.jks");
        jks.setPassword("Antm2017#");

        System.out.println("Create HTTP Server");
        vertx.createHttpServer(new HttpServerOptions().setSsl(true).setKeyStoreOptions(jks))
        	.requestHandler(r -> {
    	  		r.response().end("<h1>Hello from my first Vert.x application</h1>");
        	})
        	.listen(PORT);
        System.out.println("HTTP Server running on port " + PORT);
    }
    */
        
	/*
	public void start(Future<Void> fut) {
		
		System.out.println("Get jks file");
        JksOptions jks = new JksOptions();
        jks.setPath("/home/opc/fdsm-compute-dev-adm-web.anthem.com.jks");
        jks.setPassword("Antm2017#");

        System.out.println("Create HTTP Server");
        vertx.createHttpServer(new HttpServerOptions().setSsl(true).setKeyStoreOptions(jks))
        	.requestHandler(r -> {
    	  		r.response().end("<h1>Hello from my first Vert.x application</h1>");
        	})
        	.listen(443);
      */  /*
    	  	.listen(443, result -> {
    	  		if ( result.succeeded() ) {
    	  			System.out.println("Success!");
    	  			fut.complete();
    	  		}
    	  		else {
    	  			System.out.println("Failure!");
    	  			fut.fail(result.cause());
    	  		}
    	  	});
    	  	*/
        /*
	  vertx
	  	.createHttpServer().requestHandler(r -> {
	  		r.response().end("<h1>Hello from my first Vert.x application</h1>");
	  	})
	  	.listen(443, result -> {
	  		if ( result.succeeded() ) {
	  			fut.complete();
	  		}
	  		else {
	  			fut.fail(result.cause());
	  		}
	  	});
	  	*/

  //}
}

