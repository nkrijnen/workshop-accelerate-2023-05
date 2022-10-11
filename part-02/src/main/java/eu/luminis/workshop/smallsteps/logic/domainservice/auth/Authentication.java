package eu.luminis.workshop.smallsteps.logic.domainservice.auth;

import eu.luminis.workshop.smallsteps.logic.domainmodel.valueobjects.LegoBuilderId;

public interface Authentication {
    default LegoBuilderId requiredBuilderId() {
        if (this instanceof LegoBuilderAuthentication) {
            return ((LegoBuilderAuthentication)this).getBuilder();
        } else {
            throw new AccessDeniedException();
        }
    }

    default void mustBeStore() {
        if (!(this instanceof LegoStoreAuthentication)) throw new AccessDeniedException();
    }
}
