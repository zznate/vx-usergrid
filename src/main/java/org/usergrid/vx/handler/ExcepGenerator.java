package org.usergrid.vx.handler;

import org.vertx.java.core.Handler;
import org.vertx.java.core.http.HttpServerRequest;

/**
 * Example of what happens when an exception is thrown within main event loop
 *
 * @author zznate
 */
public class ExcepGenerator implements Handler<HttpServerRequest> {
    @Override
    public void handle(HttpServerRequest event) {
        // because we deal w/ streams, should focus on "error interpretation"
        throw new IllegalArgumentException("what happens w/ me?");
        // NOTE: this hangs the stream for the client...
        // - is there a 'shortcircuit' upstream to prevent dangling like this?
    }
}
