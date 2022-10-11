package eu.luminis.workshop.smallsteps.logic.appservice;

import eu.luminis.workshop.smallsteps.logic.domainmodel.valueobjects.LegoStoreId;
import eu.luminis.workshop.smallsteps.logic.domainservice.state.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class LegoStockQueries {
    private final LegoStockRepository repository;

    @Autowired
    public LegoStockQueries(LegoStockRepository repository) {
        this.repository = repository;
    }

    public Map<String, Integer> currentlyMissingPartsReport(LegoStoreId legoStoreId) {
        StockState foundState = repository.find(legoStoreId);

        List<Map<String, Integer>> collect = foundState.getIncompleteStock().stream()
                .map(LegoBox::getMissingParts)
                .collect(Collectors.toList());

        Map<String, Integer> merged = mergeAndSum(collect);

        return toSortedMap(merged);
    }

    public Map<String, Integer> historicallyMostLostParts(LegoStoreId legoStoreId) {
        StockState foundState = repository.find(legoStoreId);

        List<Map<String, Integer>> collect = foundState.getIncompleteReturnHistory().stream()
                .map(IncompleteReturn::getMissingParts)
                .collect(Collectors.toList());

        Map<String, Integer> merged = mergeAndSum(collect);

        return toSortedMap(merged);

    }

    private Map<String, Integer> mergeAndSum(List<Map<String, Integer>> collect) {
        Map<String, Integer> missingParts = new HashMap<>();
        collect.forEach(item -> {
            item.keySet().forEach(key -> {
                missingParts.merge(key, item.get(key), Integer::sum);
            });
        });
        return missingParts;
    }

    private Map<String, Integer> toSortedMap(Map<String, Integer> missingParts) {
        return missingParts.entrySet().stream()
                .sorted(Comparator.comparing(o -> Integer.valueOf(o.getKey())))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (v1, v2) -> {
                            throw new RuntimeException(String.format("Duplicate key for values %s and %s", v1, v2));
                        },
                        LinkedHashMap::new));
    }
}
