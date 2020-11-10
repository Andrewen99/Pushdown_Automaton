data class AutRule(val input: AutState, val output: List<String>) {
    constructor(stack: String, output: List<String>) : this(AutState(stack = stack), output)
}