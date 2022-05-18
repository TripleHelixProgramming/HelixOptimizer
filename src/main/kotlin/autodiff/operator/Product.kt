package autodiff.operator

import autodiff.Expression
import autodiff.Variable
import autodiff.Vector
import kotlin.math.cos

class Product(val left: Expression, val right: Expression) : Expression() {
    private var containedVariables: Set<Variable> = left.getVariables() + right.getVariables()
    override var value = 0.0

    override fun getVariables(): Set<Variable> {
        return containedVariables
    }

    override fun evaluate(variables: Vector): Double {
        value = left.evaluate(variables) * right.evaluate(variables)
        return value
    }

    // df/du (u * v) = v
    // df/dv (u * v) = u
    override fun solveGradient(variables: Vector, gradient: Vector, path: Double) {
        left.solveGradient(variables, gradient, path * right.value)
        right.solveGradient(variables, gradient, path * left.value)
    }

    override fun forwardAutoDiff(variable: Variable, value: Vector, degree: Int): Vector {
        var p1 = left.forwardAutoDiff(variable, value, degree)
        var p2 = right.forwardAutoDiff(variable, value, degree)
        var p = Vector()
        for (i in 0..degree) {
            var ck = 0.0
            for (j in 0..i) {
                ck += p1[j] * p2[i - j]
            }
            p.add(ck)
        }
        return p
    }

    override fun toString(): String {
        return "(${left} * ${right})"
    }
}