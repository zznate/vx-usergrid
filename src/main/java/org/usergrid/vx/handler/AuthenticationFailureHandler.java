package org.usergrid.vx.handler;

import org.jboss.netty.handler.codec.http.HttpResponseStatus;
import org.vertx.java.core.Handler;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.core.json.impl.Json;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zznate
 */
public class AuthenticationFailureHandler implements Handler<HttpServerRequest> {

    @Override
    public void handle(HttpServerRequest event) {
        writeError(event);
    }

    /**
     * Short cut to avoid invocation when we are acting on a stream
     *
     * @param event
     */
    public static void writeError(HttpServerRequest event) {
        event.response.statusCode = HttpResponseStatus.FORBIDDEN.getCode();
        event.response.headers().put("Content-Type", "application/json; charset=UTF-8");
        Map map = new HashMap(data);
        map.put("details","Username or password did not match");
        event.response.end(Json.encodePrettily(map));
    }

    private static final Map<String,String> data = new HashMap<String,String>(2);
    static {
        data.put("error","Authentication failure");
    }
}
