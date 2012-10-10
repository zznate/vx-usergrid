package org.usergrid.vx.handler;

import org.vertx.java.core.Handler;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.json.impl.Json;
import org.vertx.java.deploy.Container;

import java.util.Map;

/**
 * A sample Handler implementation that extracts the parameters which are
 * supposed to be present for an org/app combination.
 *
 * How would Shiro authentication fit in this model without producing a lot
 * of duplicated code? Handler's would have to be heavily annotated and
 * instance acquisition driven from a Factory access to the request context.
 */
public class OrgAppHandler implements Handler<HttpServerRequest> {

    private String appName;
    private String orgName;
    private final Container container;

    public OrgAppHandler(Container container) {
        this.container = container;
    }

    public String getOrgName() {
        return orgName;
    }

    public String getAppName() {
        return appName;
    }

    public void handle(HttpServerRequest req) {
        // TODO cache immutable data in the concurrent map impl
        // - immutable data would be OrgInfo and AppInfo
        // - see: http://vertx.io/core_manual_java.html#shared-data

        // TODO more dynamic 'bind(JsonObj)' and add strong type checking
        extract(req.params());
        // --------------------------------
        // service invocation goes here
        // --------------------------------
        // TODO encapsulate response writing based on type
        container.getLogger().info("OrgAppHandler handling request...");
        req.response.headers().put("Content-Type", "application/json; charset=UTF-8");
        container.getLogger().info(HandlerUtils.dumpRequest(req));
        req.response.end(new Buffer(Json.encodePrettily(this), "UTF8"));
    }

    // TODO move this to Guava's exposeForTest annotation
    void extract(Map<String,String> params) {
        orgName = params.get("param0");
        appName = params.get("param1");
    }
}