package eu.luminis.workshop.smallsteps.api.support;

import eu.luminis.workshop.smallsteps.logic.domainmodel.valueobjects.LegoBuilderId;
import eu.luminis.workshop.smallsteps.logic.domainmodel.valueobjects.LegoStoreId;
import eu.luminis.workshop.smallsteps.logic.domainservice.auth.AccessDeniedException;
import eu.luminis.workshop.smallsteps.logic.domainservice.auth.AuthProvider;
import eu.luminis.workshop.smallsteps.logic.domainservice.auth.LegoBuilderAuthentication;
import eu.luminis.workshop.smallsteps.logic.domainservice.auth.LegoStoreAuthentication;
import org.jboss.resteasy.reactive.RestHeader;

public class HeaderReader {
    public static AuthProvider authoriseAndHandleStockMutation(@RestHeader("X-API-GATEWAY-AUTH") String legoAuthParam) {
        AuthProvider authProvider;
        if (legoAuthParam.startsWith(LegoStoreId.PREFIX)) {
            authProvider = () -> new LegoStoreAuthentication(new LegoStoreId(legoAuthParam));
        } else if (legoAuthParam.startsWith(LegoBuilderId.PREFIX)) {
            authProvider = () -> new LegoBuilderAuthentication(new LegoBuilderId(legoAuthParam));
        } else {
            throw new AccessDeniedException();
        }
        return authProvider;
    }

}
