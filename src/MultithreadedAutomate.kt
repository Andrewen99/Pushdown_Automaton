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
        while (!states.last().equals(finalState) && !states.last().equals(badState) && !states.last().stack.equals("~~") && !states.last().inTape.equals("~~")) {
            val state = states.last()
            if (aut.P.contains(state.stack.last())) {
                if (state.inTape.first().equals(state.stack.last())) {
                    states.add(transition2(state))
                    if (states.last().stack.equals("h") && states.last().inTape.equals("~~")) {

                        println("eq " + states.last().equals(Util.finalState))
                    }
                } else {
                    states.add(badState)
                }
            } else {
                states.add(transition1(state, states))
            }
        }
        //println(" Thread " + this.name + " Passing this list: \n" + states + "\n")
        if (states.last().equals(Util.finalState)) {
            SynchList.addToFinishList(states)
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

    /**
     * Функцие выбора путей при недетрменированном правиле.
     * При недетерменированном правиле для всех вариантов перехода (начиная со 2) создается новый отдельный поток,
     * который ищет путь после выбора данного варианта перехода.
     * Функция возвращает 1 вариант перехода, для поиска пути в данном потоке
     *
     * @param states - путь состояний автомата (в котором последнее состояние - состояние в котором правило недетерменировано)
     * @param list - все варианты переходов
     */
    fun getMultithreaded(list: List<String>, states: MutableList<AutState>): String {
        for (i in 1..list.lastIndex) {
            var threadStates = mutableListOf<AutState>()
            states.forEach { threadStates.add(it) }
            var state = states.last()
            var resultOutput = state.stack.dropLast(1) + list[i]
            var result = AutState(state.state, state.inTape, resultOutput )
            threadStates.add(result)
            MultithreadedAutomate(aut, threadStates).start()
        }
        return list.get(0)
    }

    override fun run() {
        SynchList.addToList(tryPath())
    }

}