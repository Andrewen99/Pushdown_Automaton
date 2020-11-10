class Automate {
    val rules1: MutableSet<AutRule> = mutableSetOf()
    val rules2: MutableSet<AutRule> = mutableSetOf()
    val States: MutableSet<String> = mutableSetOf()
    val Z: MutableSet<Char> = mutableSetOf('h')
    val P: MutableSet<Char> = mutableSetOf()
    val NotP: MutableSet<Char> = mutableSetOf('h')
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
        if (rules1.flatMap { it.input.stack }.contains(startState.stack.get(startState.stack.lastIndex))) {

        }
        return mutableListOf()
    }

    fun transition1() {

    }

    fun transition2() {

    }
}