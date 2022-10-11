package eu.luminis.workshop.smallsteps.api;

import eu.luminis.workshop.smallsteps.logic.appservice.LegoStockMutation;
import eu.luminis.workshop.smallsteps.logic.domainmodel.valueobjects.LegoBuilderId;
import eu.luminis.workshop.smallsteps.logic.domainmodel.valueobjects.LegoSetNumber;
import eu.luminis.workshop.smallsteps.logic.domainmodel.valueobjects.LegoStoreId;
import eu.luminis.workshop.smallsteps.logic.domainservice.auth.AuthProvider;
import org.jboss.resteasy.reactive.RestHeader;
import org.jboss.resteasy.reactive.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import static eu.luminis.workshop.smallsteps.api.support.HeaderReader.authoriseAndHandleStockMutation;

@RestController
public class LegoRentingController {
    @Autowired
    private LegoStockMutation stockMutationService;

    @PostMapping(path = "/renting/legoset/{legoSetNumber}/reserve")
    public RestResponse<String> reserveLegoSet(
            @PathVariable("legoSetNumber") Integer legoSetNumberParam,
            @RestHeader("X-SELECTED-LEGO-STORE") String legoStoreIdParam,
            @RestHeader("X-API-GATEWAY-AUTH") String legoAuthParam) {

        AuthProvider authProvider = authoriseAndHandleStockMutation(legoAuthParam);

        stockMutationService.handleMutation(
                authProvider,
                new LegoStoreId(legoStoreIdParam),
                stock -> stock.reserve(new LegoSetNumber(legoSetNumberParam))
        );

        return RestResponse.accepted("Lego set has been reserved");
    }

    @PostMapping(path = "/renting/legoset/{legoSetNumber}/ship/{builderId}")
    public RestResponse<String> shipLegoSet(
            @PathVariable("legoSetNumber") Integer legoSetNumberParam,
            @PathVariable("builderId") String builderId,
            @RestHeader("X-SELECTED-LEGO-STORE") String legoStoreIdParam,
            @RestHeader("X-API-GATEWAY-AUTH") String legoAuthParam) {

        AuthProvider authProvider = authoriseAndHandleStockMutation(legoAuthParam);

        stockMutationService.handleMutation(
                authProvider,
                new LegoStoreId(legoStoreIdParam),
                stock -> stock.ship(new LegoSetNumber(legoSetNumberParam), new LegoBuilderId(builderId))
        );

        return RestResponse.accepted("Shipment registered");
    }
}
