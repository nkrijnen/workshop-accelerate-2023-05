package eu.luminis.workshop.smallsteps.logic.domainservice.auth;

import eu.luminis.workshop.smallsteps.logic.domainmodel.valueobjects.LegoBuilderId;

public class LegoBuilderAuthentication implements Authentication {
    private final LegoBuilderId builder;

    public LegoBuilderAuthentication(LegoBuilderId builder) {
        this.builder = builder;
    }

    public LegoBuilderId getBuilder() {
        return builder;
    }
}
