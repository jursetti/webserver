package com.antm.fdsm.web.webserver;

import io.vertx.core.AbstractVerticle;
//import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.net.JksOptions;
//import io.vertx.core.net.NetServerOptions;

public class Start extends AbstractVerticle {

	public void main() {
		try {
			start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    public void start() throws Exception {

        Vertx vertx = Vertx.vertx();
        
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
        
    }
        
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

