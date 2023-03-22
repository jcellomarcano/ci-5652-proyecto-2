
import kotlin.random.Random
import kotlin.system.measureTimeMillis

fun main() {
//    val data = listOf(
//        Item(value = 60, weight = 10),
//        Item(value = 100, weight = 20),
//        Item(value = 120, weight = 30),
//    )
//    val antsAmount = 10
//    val pesoLimiteMochila = 50
//    val iterations = 1000
//    val evaporationRate = 0.1
//    val minItemValue = 1
//
//    val result = runAnts(data, antsAmount, pesoLimiteMochila, iterations, evaporationRate, minItemValue)
//    println("Optimal solution: $result")

//    val numberOfItems = 500
//    val maxValue = 100
//    val maxWeight = 100
//    val bagCapacity = 1000
//
//    val data = List(numberOfItems) {
//        Item(value = Random.nextInt(1, maxValue), weight = Random.nextInt(1, maxWeight))
//    }
//
//    val antsAmount = 50
//    val iterations = 1000
//    val evaporationRate = 0.1
//    val minItemValue = 1
//
//    val executionTime = measureTimeMillis {
//        val result = runAnts(data, antsAmount, bagCapacity, iterations, evaporationRate, minItemValue)
//        println("Solution: $result")
//    }
//
//    println("Execution time: $executionTime ms")

    val initialData = listOf(
        Item(value = 120, weight = 30),
        Item(value = 75, weight = 40),
        Item(value = 50, weight = 50),
        Item(value = 80, weight = 60),
        Item(value = 20, weight = 70),
        Item(value = 10, weight = 80),
        Item(value = 90, weight = 90),
        Item(value = 110, weight = 100),
        Item(value = 60, weight = 10),
        Item(value = 100, weight = 20),
    )

    val additionalItems = List(10000) {
        Item(value = Random.nextInt(1, 100), weight = Random.nextInt(51, 100))
    }

    val data = additionalItems + initialData

    val bagCapacity = 50
    val antsAmount = 50
    val iterations = 1000
    val evaporationRate = 0.1
    val minItemValue = 1

    val executionTime = measureTimeMillis {
        val result = runAnts(data, antsAmount, bagCapacity, iterations, evaporationRate, minItemValue)
        println("Solution: $result")
    }

    println("Execution time: $executionTime ms")

//    val solution = runAnts(data, antsAmount, bagCapacity, iterations, evaporationRate, minItemValue)
//    val totalWeight = solution.indices.filter { solution[it] == 1 }.sumBy { data[it].weight }
//    val totalValue = solution.indices.filter { solution[it] == 1 }.sumBy { data[it].value }
//    val isSolutionValid = totalWeight <= bagCapacity
//
//    println("Total weight: $totalWeight")
//    println("Total value: $totalValue")
//    println("Solution is valid: $isSolutionValid")
}

fun runAnts(
    data: List<Item>,
    antsAmount: Int,
    pesoLimiteMochila: Int,
    iterations: Int,
    evaporationRate: Double,
    minItemValue: Int,
): Item {
    val solution = antColonyOptimization(data, pesoLimiteMochila, antsAmount, iterations, evaporationRate, minItemValue)
    val (valor, peso) = solution.indices.filter { solution[it] == 1 }.fold(Pair(0, 0)) { acc, i ->
        Pair(acc.first + data[i].value, acc.second + data[i].weight)
    }
    return Item(value = valor, weight = peso)
}

fun antColonyOptimization(
    items: List<Item>,
    capacity: Int,
    antsCount: Int,
    iterations: Int,
    evaporationRate: Double,
    minItemValue: Int,
): List<Int> {
    val ants = List(antsCount) { Ant(items, capacity, minItemValue) }
    pheromones = MutableList(items.size) { 0.1 }

    var bestSolution = emptyList<Int>()
    var bestValue = 0

    repeat(iterations) {
        val solutions = ants.map { it.findSolution(antsCount) }
        for (solution in solutions) {
            val value = solution.indices.filter { solution[it] == 1 }.sumBy { items[it].value }

            if (value > bestValue) {
                bestSolution = solution
                bestValue = value
                solution.indices.filter { solution[it] == 1 }.forEach { pheromones[it] += value / bestValue.toDouble() }
            }
        }
    }

    return bestSolution
}
