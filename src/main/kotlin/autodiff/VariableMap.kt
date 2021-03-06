package autodiff

import kotlin.math.round
import kotlin.math.sqrt

class VariableMap {
    var map = mutableMapOf<Variable, Double>()

    operator fun set(variable: Variable, value: Double) {
        this.map[variable] = value
    }

    operator fun get(key: Variable): Double {
        return this.map[key]!!
    }

    operator fun plus(addend: VariableMap): VariableMap {
        val newMap = VariableMap()
        for ((variable, value) in this.map) {
            newMap[variable] = value + addend[variable]
        }
        return newMap
    }

    operator fun times(scale: Double): VariableMap {
        val newMap = VariableMap()
        for ((variable, value) in this.map) {
            newMap[variable] = value * scale
        }
        return newMap
    }

    fun norm(): Double {
        var norm = 0.0
        for (item in this.map.values) {
            norm += item!! * item!!
        }
        return sqrt(norm)
    }

    fun dot(other: VariableMap): Double {
        var dotProduct = 0.0
        this.map.keys.map {dotProduct += this[it] * other[it] }
        return dotProduct
    }

    fun copy(): VariableMap {
        val newMap = VariableMap()
        newMap.map = HashMap<Variable, Double>(this.map)
        return newMap
    }

    override fun toString(): String {
        var str = "{"
        for (item in map) {
            str += "${item.key} = ${(item.value*1E4).toInt()/1E4}, "
        }
        return "${str.substring(0, str.length - 2)}}"
    }
}