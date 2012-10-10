package org.usergrid.vx.handler;

import org.vertx.java.core.Handler;
import org.vertx.java.core.http.HttpServerRequest;

/**
 * @author zznate
 */
public class MgmtTokenHandler implements Handler<HttpServerRequest> {

    public void handle(HttpServerRequest req) {
        req.response.end("MgmtToken");
    }

}
