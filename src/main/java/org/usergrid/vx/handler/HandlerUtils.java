package org.usergrid.vx.handler;

import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.json.impl.Json;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zznate
 */
public class HandlerUtils {

    public static Buffer dumpRequest(HttpServerRequest req) {
        Map headers = req.headers();
        Map params = req.params();
        Map dump = new HashMap();
        dump.put("headers", headers);
        dump.put("parameters",params);
        return new Buffer(Json.encodePrettily(dump));
    }
}
