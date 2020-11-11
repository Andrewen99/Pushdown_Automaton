import Util.badState
import Util.finalState

class MultithreadedAutomate: Thread{
    constructor(automate: Automate, startStates: MutableList<AutState>) {
        this.aut = automate;
        this.startStates = startStates
    }

    val aut: Automate
    val startStates: MutableList<AutState>


    fun tryPath(): MutableList<AutState> {
        val states = startStates

        while (!states.last().equals(finalState) && !states.last().equals(badState)) {
            val state = states.last()
            if (aut.P.contains(state.stack.last())) {
                if (state.inTape.first().equals(state.stack.last())) {
                    states.add(transition2(state))
                } else {
                    states.add(badState)
                }
            } else {
                states.add(transition1(state, states))
            }
        }
        println("I am thread " + name)
        println("Passing this list: \n" + states + "\n")
        if (states.last().equals(Util.finalState)) {
            println("\n\n+++++++POBEEEEDAAAA+++++++++\n\n")
            SynchList.addToFinishList(states)
            println("+++ synchlist " + states)
        }
        return states
    }

    fun transition1(state: AutState, states: MutableList<AutState>): AutState {
        var rule: AutRule
        var out = ""
        aut.rules1.forEach {
            if (it.input.stack.equals(state.stack[state.stack.lastIndex].toString())) {
                rule = it
                when {
                    rule.output.size > 1 -> out = getMultithreaded(rule.output, states)
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
        aut.rules2.forEach {
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
    fun getMultithreaded(list: List<String>, states: MutableList<AutState>): String {

        var threadStates = mutableListOf<AutState>()
        states.forEach { threadStates.add(it) }
        var state = states.last();
        var resultOutput = state.stack.dropLast(1) + list[1]
        var result = AutState(state.state, state.inTape, resultOutput )
        threadStates.add(result)
        MultithreadedAutomate(aut, threadStates).start()
        return list.get(0)
    }

    override fun run() {
        SynchList.addToList(tryPath())
    }

}