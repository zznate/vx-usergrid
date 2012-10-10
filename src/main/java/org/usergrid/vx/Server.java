package org.usergrid.vx;

import org.usergrid.vx.handler.AuthenticatingHandlerProxy;
import org.usergrid.vx.handler.ExcepGenerator;
import org.usergrid.vx.handler.MgmtTokenHandler;
import org.usergrid.vx.handler.OrgAppHandler;
import org.vertx.java.core.Handler;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.http.RouteMatcher;
import org.vertx.java.core.logging.Logger;
import org.vertx.java.deploy.Verticle;

/**
 * A Verticle impl. Shows how we could execute some of the routing logic currently in use
 * in the REST tier.
 *
 * The initial inclination may be to put allthethings in a single Verticle, but that would
 * be against the inherent design goal of service decoupling and event-driven programming.
 * This approach has a number of parallels from what I can tell with Erlang's concept of
 * actors.
 *
 * @author zznate
 */
public class Server extends Verticle {

    private Logger logger;

    private static String REGEX_UUID =
            "([A-Fa-f0-9]{8}-[A-Fa-f0-9]{4}-[A-Fa-f0-9]{4}-[A-Fa-f0-9]{4}-[A-Fa-f0-9]{12})";
    private static String REGEX_ANY_TO_SLASH =
            "([^\\/]+)";
    private static String REGEX_ANY = "\\/?([A-Za-z][A-Za-z0-9]*\\/?)*";
            //"\\/?([A-Za-z][A-Za-z0-9]*\\/?)*";

    @Override
    public void start() throws Exception {
        // NOTE: must acquire the logger from the container directly,
        // - a bit of wheel re-invention from slf4j facade-ing (same pattern)
        // - vertx classloader throws exception when invoking LoggerFactory directly (even vertx one)
        // - logging impl is pluggable: see http://vertx.io/manual.html#logging
        logger = container.getLogger();
        // RouteMatcher acts as the dispatcher to Handler impls
        // NOTE: most of this could probably be accomplished with configuration
        // - regex to factory class mappings?
        // TODO: a "main" Verticle for starting everything else might be better written in JavaScript
        // - json support would make this concise and easily parse-able
        RouteMatcher rm = new RouteMatcher();
        // TODO: handlers should come from factory so they can be injected w/ resources
        rm.get("/exception", new ExcepGenerator());
        // TODO: management should probably go in a different Verticle
        rm.get("/management/token", new MgmtTokenHandler());
        // NOTE: order matters with regex stacking
        // NOTE: there are also [get|put|post|etc..]WithRegEx
        // org/app match with UUID regex
        rm.get("/auth/fail", new AuthenticatingHandlerProxy(new OrgAppHandler(container)));
        rm.allWithRegEx("\\/" + REGEX_UUID + "\\/" + REGEX_UUID + REGEX_ANY,
                new OrgAppHandler(container));
        // UUID/appname
        rm.allWithRegEx("\\/"+REGEX_UUID+"\\/"+ REGEX_ANY_TO_SLASH + REGEX_ANY,
                new OrgAppHandler(container));
        // orgName/uuid
        rm.allWithRegEx("\\/"+ REGEX_ANY_TO_SLASH + "\\/"+REGEX_UUID + REGEX_ANY,
                new OrgAppHandler(container));
        // org/app match with name regex simple case
        rm.allWithRegEx("\\/" + REGEX_ANY_TO_SLASH + "\\/" + REGEX_ANY_TO_SLASH + REGEX_ANY,
                new OrgAppHandler(container));
        // no match default
        rm.noMatch(new Handler<HttpServerRequest>() {
            public void handle(HttpServerRequest req) {
                req.response.end("Nothing matched");
            }
        });

        // configure the server for this verticle
        vertx.createHttpServer().requestHandler(rm).listen(8080);
    }



}
