data class AutState(val state: String = "s0", val inTape: ArrayList<String>, val stack: ArrayList<String>) {
    constructor(
        inTape: String = "~",
        stack: String
    ) : this(inTape = arrayListOf(inTape), stack =  arrayListOf(stack))
}