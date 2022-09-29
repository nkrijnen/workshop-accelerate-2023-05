package eu.luminis.workshop.smallsteps.logic.domainmodel;

import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class LegoPartsTest {

    @Test
    public void test_hasParts() {
        assertTrue(new LegoParts(Map.of("3306", 2)).hasParts());
        assertFalse(new LegoParts(Map.of()).hasParts());
    }

    @Test
    public void test_allPartsPresentInOtherLegoParts() {
        LegoParts other = new LegoParts(Map.of("3306", 2, "101708", 3, "783101", 4));

        assertTrue(new LegoParts(Map.of("3306", 2, "101708", 2)).allPartsPresentIn(other));
        assertTrue(new LegoParts(Map.of("101708", 3, "3306", 2)).allPartsPresentIn(other));
        assertFalse(new LegoParts(Map.of("3306", 2, "999999", 2)).allPartsPresentIn(other));
        assertTrue(new LegoParts(Map.of()).allPartsPresentIn(other));
        assertFalse(new LegoParts(Map.of("3306", 2, "999999", 2))
                .allPartsPresentIn(new LegoParts(Map.of())));
    }

    @Test
    public void test_hasNonePartWithZeroNumber() {
        assertTrue(new LegoParts(Map.of("3306", 2, "101708", 3)).nonePartsWithZero());
        assertFalse(new LegoParts(Map.of("3306", 0, "101708", 3)).nonePartsWithZero());
    }

    @Test
    public void test_allPartsAreAvailableInRequestedNumber() {
        LegoParts other = new LegoParts(Map.of("3306", 2, "101708", 3));

        assertTrue(other.allPartsInRequestedNumberAvailable(
                new LegoParts(Map.of("3306", 2, "101708", 3))));
        assertFalse(other.allPartsInRequestedNumberAvailable(
                new LegoParts(Map.of("3306", 2, "101708", 4))));
    }

    @Test
    public void test_add_two_LegoParts() {
        LegoParts one = new LegoParts(Map.of("3306", 2, "101708", 3));
        LegoParts two = new LegoParts(Map.of("3306", 2, "101716", 3));
        assertThat(one.plus(two).listParts()).containsExactlyInAnyOrderEntriesOf(Map.of(
                "3306", 4,
                "101708", 3,
                "101716", 3
        ));
    }

    @Test
    public void test_list_parts_sorted() {
        LegoParts one = new LegoParts(Map.of(
                "3306", 2,
                "101708", 3,
                "7800", 1,
                "1100", 9
                ));

        assertThat(one.listPartsSorted()).containsExactlyEntriesOf(
                new LinkedHashMap<>() {{
                    put("1100", 9);
                    put("3306", 2);
                    put("7800", 1);
                    put("101708", 3);
                }});
    }

    @Test
    public void test_equal() {
        assertEquals(
                new LegoParts(Map.of("3306", 2, "101708", 3)),
                new LegoParts(Map.of("3306", 2, "101708", 3))
        );
    }
}
