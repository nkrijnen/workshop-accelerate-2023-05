package eu.luminis.workshop.smallsteps.logic.domainservice.state;

import eu.luminis.workshop.smallsteps.logic.domainmodel.valueobjects.LegoBuilderId;
import eu.luminis.workshop.smallsteps.logic.domainmodel.valueobjects.LegoSetNumber;

import java.util.Objects;

public class LegoSetForBuilder {
    private LegoSetNumber legoSetNumber;
    private LegoBuilderId legoBuilderId;

    public LegoSetForBuilder(LegoSetNumber legoSetNumber, LegoBuilderId legoBuilderId) {
        this.legoSetNumber = legoSetNumber;
        this.legoBuilderId = legoBuilderId;
    }

    public LegoSetNumber getLegoSetNumber() {
        return legoSetNumber;
    }

    public LegoBuilderId getLegoBuilderId() {
        return legoBuilderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LegoSetForBuilder that = (LegoSetForBuilder) o;
        return Objects.equals(legoSetNumber, that.legoSetNumber) && Objects.equals(legoBuilderId, that.legoBuilderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(legoSetNumber, legoBuilderId);
    }
}
