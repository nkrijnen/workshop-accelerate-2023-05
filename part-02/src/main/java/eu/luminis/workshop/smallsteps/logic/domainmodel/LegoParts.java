package eu.luminis.workshop.smallsteps.logic.domainmodel;

import java.util.*;
import java.util.stream.Collectors;

public class LegoParts {
    private final Map<String, Integer> partsCount;

    public LegoParts(Map<String, Integer> partsCount) {
        this.partsCount = Map.copyOf(partsCount);
    }

    public boolean hasParts() {
        return !partsCount.isEmpty();
    }

    public boolean allPartsPresentIn(LegoParts other) {
        return other.partsCount.keySet().containsAll(this.partsCount.keySet());
    }

    public boolean nonePartsWithZero() {
        return !this.partsCount.containsValue(0);
    }

    public boolean allPartsInRequestedNumberAvailable(LegoParts other) {
        if (!other.allPartsPresentIn(this)) {
            return false;
        }
        return other.partsCount.entrySet().stream()
                .noneMatch(part -> part.getValue() > this.partsCount.get(part.getKey()));
    }

    public LegoParts plus(LegoParts other) {
        Map<String, Integer> missingParts = new HashMap<>(this.listParts());
        Map<String, Integer> otherParts = other.listParts();
        otherParts.keySet().forEach(key -> {
                missingParts.merge(key, otherParts.get(key), Integer::sum);
        });

        return new LegoParts(missingParts);
    }

    public Map<String, Integer> listParts() {
        return Map.copyOf(this.partsCount);
    }

    public Map<String, Integer> listPartsSorted() {
        return this.partsCount.entrySet().stream()
                .sorted(Comparator.comparing(o -> Integer.valueOf(o.getKey())))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (v1, v2) -> {
                            throw new RuntimeException(String.format("Duplicate key for values %s and %s", v1, v2));
                        },
                        LinkedHashMap::new));

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LegoParts legoParts = (LegoParts) o;
        return partsCount.equals(legoParts.partsCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(partsCount);
    }
}
