package org.usergrid.vx.handler;

import org.vertx.java.core.Handler;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.deploy.Container;

/**
 * @author zznate
 */
public class AuthenticatingHandlerProxy implements Handler<HttpServerRequest> {

    // TODO constructed as singleton based on Context for lookup:
    // private AuthenticatingHandlerProxy(cdiContext context)....
    private final Handler<HttpServerRequest> delegate;

    public AuthenticatingHandlerProxy(Handler<HttpServerRequest> delegate) {
        this.delegate = delegate;
    }

    /**
     * Invoke the delegate or send back an error
     * @param event
     */
    @Override
    public void handle(HttpServerRequest event) {
        if ( credentials(event) ) {
            delegate.handle(event);
        } else {
            AuthenticationFailureHandler.writeError(event);
        }
    }

    public Handler forOrgApp(Container container, HttpServerRequest request) {
        // credentials(request)?
        // if false, return new AuthenticationFailureHandler(); <-- at Interceptor level?
        return credentials(request) ? new OrgAppHandler(container) : null;

    }

    private boolean credentials(HttpServerRequest request) {


        return false;
    }

}
