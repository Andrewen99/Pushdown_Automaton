fun main() {
    val automate = FileParser.parseFileToAutomate("/Users/andrewshakirov/Documents/шкалка/4 курс/ТАЯК/Laba3/test.txt")

    for ( i in 0..1000000) {
        val result = automate.tryPath(AutState(inTape = "a+a*a", stack = "hE"))
        println(result)
        println("-----")
        if (result.last().equals(Util.finalState)) {
            println("POOOOBEEEEDAAAA")
            println(result)
            break
        }
    }
}