package autodiff

import autodiff.operator.Difference
import autodiff.operator.Product
import autodiff.operator.Quotient
import autodiff.operator.Sum

abstract class Expression {
    abstract var value: Double

    abstract fun getVariables(): Set<Variable>

    operator fun plus(expression : Expression) : Expression {
        return Sum(this, expression)
    }

    operator fun minus(expression : Expression) : Expression {
        return Difference(this, expression)
    }

    operator fun times(expression : Expression) : Expression {
        return Product(this, expression)
    }

    operator fun div(expression : Expression) : Expression {
        return Quotient(this, expression)
    }

    abstract fun evaluate(variables: VariableMap): Double

    abstract fun solveGradient(variables: VariableMap, gradient: VariableMap, path: Double)

    abstract fun forwardAutoDiff(variable: Variable, value: VariableMap, degree: Int): Vector

//    abstract fun forwardAutoDiff(variable: Variable, value: VariableMap, degree: Int, multiplyByNFactorial: Boolean): Vector

    fun solveGradient(variables: VariableMap): VariableMap {
        var gradient = VariableMap()
        getVariables().map {gradient.put(it, 0.0)}
        evaluate(variables)
        solveGradient(variables, gradient, 1.0)
        return gradient
    }
}