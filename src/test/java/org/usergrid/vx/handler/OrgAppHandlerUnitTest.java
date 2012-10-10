package org.usergrid.vx.handler;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.assertEquals;

/**
 * @author zznate
 */
public class OrgAppHandlerUnitTest {

    private Map<String,String> paramMap;

    @Test
    public void verifyParamsOk() {
        paramMap = new HashMap<String, String>();
        paramMap.put("param0","myorg");
        paramMap.put("param1","myapp");
        // call w/ null container for now
        OrgAppHandler oah = new OrgAppHandler(null);
        oah.extract(paramMap);
        assertEquals("myorg", oah.getOrgName());
        assertEquals("myapp", oah.getAppName());
    }

    // separation btwn rigid event model and "doing stuff":
    // - parameter extraction & validation
    // - exceptions - still unclear on exception model
}
