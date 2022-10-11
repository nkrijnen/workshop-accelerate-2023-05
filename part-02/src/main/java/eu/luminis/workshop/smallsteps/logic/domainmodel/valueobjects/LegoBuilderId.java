package eu.luminis.workshop.smallsteps.logic.domainmodel.valueobjects;

import java.util.Objects;
import java.util.UUID;

public class LegoBuilderId {
    public static final String PREFIX = "bldr_";
    private final UUID builderId;

    public LegoBuilderId() {
        this(UUID.randomUUID());
    }

    public LegoBuilderId(String builderId) {
        this(UUID.fromString(builderId.replaceFirst("^" + PREFIX, "")));
    }

    public LegoBuilderId(UUID builderId) {
        this.builderId = builderId;
    }

    @Override
    public String toString() {
        return PREFIX + this.builderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LegoBuilderId that = (LegoBuilderId) o;
        return Objects.equals(builderId, that.builderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(builderId);
    }
}
