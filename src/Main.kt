fun main() {
    val automate = FileParser.parseFileToAutomate("/Users/andrewshakirov/Documents/шкалка/4 курс/ТАЯК/Laba3/test.txt")

    val multithreadedAutomate = MultithreadedAutomate(automate, mutableListOf(AutState(inTape = "a+a*a", stack = "hE")))
    multithreadedAutomate.start()
    var time = 0;
    while (!SynchList.foundFinish() && time < 100) {
        Thread.sleep(100)
        time++
    }
    if (SynchList.foundFinish()) {
        println("Финальный путь найден: ")
        SynchList.finishList.flatten().forEach {
            println(it)
        }
    }
}