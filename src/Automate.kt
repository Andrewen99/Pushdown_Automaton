import Util.badState
import Util.finalState
import kotlin.math.roundToInt

class Automate {
    val rules1: MutableSet<AutRule> = mutableSetOf()
    val rules2: MutableSet<AutRule> = mutableSetOf()
    val States: MutableSet<String> = mutableSetOf()
    val Z: MutableSet<Char> = mutableSetOf('h')
    val P: MutableSet<Char> = mutableSetOf()
    val NotP: MutableSet<Char> = mutableSetOf()
    val rawLines: MutableSet<String> = mutableSetOf()

    fun createRules2() {
        P.forEach {
            rules2.add(AutRule(AutState(inTape = it.toString(), stack = it.toString()), arrayListOf("~")))
        }
        rules2.add(AutRule(AutState(inTape = "~", stack = "h"), arrayListOf("~")))
    }

    fun fillP() {
        Z.forEach {
            if (!NotP.contains(it)) P.add(it)
        }
    }

    fun isDetermened(): Boolean {
        rules1.forEach {
            if (it.output.size > 1) {
                return false
            }
        }
        return true
    }

    fun tryPath(startState: AutState): MutableList<AutState> {
        val states = mutableListOf(startState)
        if (P.contains(startState.stack.last())) {
            states.add(transition2(startState))
        } else {
            states.add(transition1(startState))
        }

        while (!states.last().equals(finalState) && !states.last().equals(badState)) {
            val state = states.last()
            if (P.contains(state.stack.last())) {
                if (state.inTape.first().equals(state.stack.last())) {
                    states.add(transition2(state))
                } else {
                    states.add(badState)
                }
            } else {
                states.add(transition1(state))
            }
        }
        return states
    }

    fun transition1(state: AutState): AutState {
        var rule: AutRule
        var out = ""
        rules1.forEach {
            if (it.input.stack.equals(state.stack[state.stack.lastIndex].toString())) {
                rule = it
                when {
                    rule.output.size > 1 -> out = getRandString(rule.output)
                    else -> out = rule.output.get(0)
                }
            }
        }
        var resultOutput = state.stack.dropLast(1) + out
        var result = AutState(state.state, state.inTape, resultOutput )
        return result;

    }

    fun transition2(state: AutState): AutState {
        var rule: AutRule
        rules2.forEach {
            if (it.input.stack.equals(state.stack[state.stack.lastIndex].toString()) && state.inTape[0].equals(state.stack[state.stack.lastIndex])) {
                rule = it
            }
        }
        var inTape = state.inTape.drop(1)
        if (inTape.isEmpty()) inTape = "~"
        var result = AutState(state.state, inTape, state.stack.dropLast(1))
        return result;
    }

    //TODO - сделать норм метод а не это все
    fun getRandString(list: List<String>): String {
        return list.get((Math.random() * list.lastIndex).roundToInt())
    }
}