package eu.luminis.workshop.smallsteps.logic.domainModel

class LegoParts(private val partCounts: Map<String, Int> = mapOf()) {

    fun isNotEmpty(): Boolean = partCounts.isNotEmpty()

    fun hasNoneWithCountZero(): Boolean = partCounts.all { it.value > 0 }

    fun partsPresentIn(other: LegoParts): Boolean = this.partCounts.all { other.partCounts.containsKey(it.key) }

    fun hasNoMoreThan(other: LegoParts): Boolean =
        this.partCounts.all { other.partCounts.getOrDefault(it.key, 0) >= it.value }

    operator fun plus(other: LegoParts): LegoParts = (this.partCounts.asSequence() + other.partCounts.asSequence())
        .groupBy({ it.key }, { it.value })
        .mapValues { (_, values) -> values.sum() }
        .let(::LegoParts)

    fun toListOfPartCounts() = partCounts.toList()

    // Temporary method to ease codebase migration from Map<String, Int>
    // TODO delete when all code is migrated to LegoParts
    fun toPartCounts() = partCounts

    override fun equals(other: Any?) = partCounts == (other as? LegoParts)?.partCounts

    override fun hashCode() = partCounts.hashCode()

}

// Temporary method to ease codebase migration from Map<String, Int>
// TODO delete when all code is migrated to LegoParts
fun Map<String, Int>.toLegoParts() = LegoParts(this)
fun LegoParts.toLegoParts() = this