package eu.luminis.workshop.smallsteps.logic.domainservice.auth;

import eu.luminis.workshop.smallsteps.logic.domainmodel.valueobjects.LegoStoreId;

public class LegoStoreAuthentication implements Authentication {
    private final LegoStoreId store;

    public LegoStoreAuthentication(LegoStoreId store) {
        this.store = store;
    }

    public LegoStoreId getStore() {
        return store;
    }
}
