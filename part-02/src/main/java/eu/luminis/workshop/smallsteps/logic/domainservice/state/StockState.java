package eu.luminis.workshop.smallsteps.logic.domainservice.state;

import eu.luminis.workshop.smallsteps.logic.domainmodel.valueobjects.LegoSetNumber;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Collections.*;

public class StockState {
    private final Map<LegoSetNumber, Integer> availableStock;
    private final Set<LegoSetForBuilder> reserved;
    private final List<LegoSetForBuilder> atBuilder;
    private final List<LegoBox> incompleteStock;
    private final List<IncompleteReturn> incompleteReturnHistory;

    public StockState() {
        this(null,null,null,null,null);
    }

    public StockState(Map<LegoSetNumber, Integer> availableStock,
                       Set<LegoSetForBuilder> reserved,
                       List<LegoSetForBuilder> atBuilder,
                       List<LegoBox> incompleteStock,
                       List<IncompleteReturn> incompleteReturnHistory) {
        this.availableStock = availableStock != null? availableStock: emptyMap();
        this.reserved = reserved != null ? reserved: emptySet();
        this.atBuilder = atBuilder != null ? atBuilder: emptyList();
        this.incompleteStock = incompleteStock != null ? incompleteStock : emptyList();
        this.incompleteReturnHistory = incompleteReturnHistory != null ? incompleteReturnHistory : emptyList();
    }

    public Map<LegoSetNumber, Integer> getAvailableStock() {
        return availableStock;
    }

    public Set<LegoSetForBuilder> getReserved() {
        return reserved;
    }

    public List<LegoSetForBuilder> getAtBuilder() {
        return atBuilder;
    }

    public List<LegoBox> getIncompleteStock() {
        return incompleteStock;
    }

    public List<IncompleteReturn> getIncompleteReturnHistory() {
        return incompleteReturnHistory;
    }

    public StockState copy(Map<LegoSetNumber, Integer> availableStock,
                           Set<LegoSetForBuilder> reserved,
                           List<LegoSetForBuilder> atBuilder,
                           List<LegoBox> incompleteStock,
                           List<IncompleteReturn> incompleteReturnHistory) {
        return new StockState(
                availableStock != null ? availableStock : Map.copyOf(this.availableStock),
                reserved != null ? reserved : Set.copyOf(this.reserved),
                atBuilder != null ? atBuilder : List.copyOf(this.atBuilder),
                incompleteStock != null ? incompleteStock : List.copyOf(this.incompleteStock),
                incompleteReturnHistory != null ? incompleteReturnHistory : List.copyOf(this.incompleteReturnHistory)
        );
    }
}
