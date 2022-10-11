package eu.luminis.workshop.smallsteps.logic.domainmodel.valueobjects;

import java.util.UUID;

public class LegoStoreId {
    public static final String PREFIX = "stor_";
    private final UUID storeId;

    public LegoStoreId() {
        this(UUID.randomUUID());
    }

    public LegoStoreId(String storeId) {
        this(UUID.fromString(storeId.replaceFirst("^" + PREFIX, "")));
    }

    public LegoStoreId(UUID storeId) {
        this.storeId = storeId;
    }

    @Override
    public String toString() {
        return PREFIX + this.storeId;
    }
}
