package com.antm.fdsm.web.webserver;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;

public class Start extends AbstractVerticle {

	public void main() {
		try {
			start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void start(Future<Void> fut) {
	  
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

  }
}

