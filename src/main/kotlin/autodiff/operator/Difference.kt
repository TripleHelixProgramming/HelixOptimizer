package autodiff.operator

import autodiff.Expression
import autodiff.Variable
import autodiff.VariableMap
import autodiff.Vector
import kotlin.math.cos

class Difference(val left: Expression, val right: Expression) : Expression() {
    private var containedVariables: Set<Variable> = left.getVariables() + right.getVariables()
    override var value = 0.0

    override fun getVariables(): Set<Variable> {
        return containedVariables
    }

    override fun evaluate(variables: VariableMap): Double {
        value = left.evaluate(variables) - right.evaluate(variables)
        return value
    }

    override fun solveGradient(variables: VariableMap, gradient: VariableMap, path: Double) {
        left.solveGradient(variables, gradient, path)
        right.solveGradient(variables, gradient, path * -1)
    }

    override fun forwardAutoDiff(variable: Variable, value: VariableMap, degree: Int): Vector {
        val p1 = left.forwardAutoDiff(variable, value, degree)
        val p2 = right.forwardAutoDiff(variable, value, degree)
        return p1.minus(p2)
    }
    override fun toString(): String {
        return "(${left} - ${right})"
    }
}