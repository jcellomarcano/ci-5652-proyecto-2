import java.lang.Math.random
import kotlin.random.Random

class Ant(private val items: List<Item>, private var capacity: Int, private val minItemValue: Int) {
    fun findSolution(antsCount: Int): List<Int> {
        val pickedItems = MutableList(items.size) { 0 }
        val maxTries = items.size * antsCount // You can adjust this value as needed
        var tries = 0

        while (capacity >= minItemValue && tries < maxTries) {
            val randomIndex = Random.nextInt(items.size)
            val item = items[randomIndex]
            if (random() < pheromones[randomIndex] * (item.value.toDouble() / item.weight) && pickedItems[randomIndex] == 0) {
                if (capacity - item.weight >= 0) {
                    capacity -= item.weight
                    pickedItems[randomIndex] = 1
                }
            }
            tries++
        }
        return pickedItems
    }

}

var pheromones = mutableListOf<Double>()
