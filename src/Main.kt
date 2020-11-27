fun main() {
    val automate = FileParser.parseFileToAutomate("files/test.txt")

    val multithreadedAutomate = MultithreadedAutomate(automate, mutableListOf(AutState(inTape = "a+a*a", stack = "hE")))
    //val multithreadedAutomate = MultithreadedAutomate(automate, mutableListOf(AutState(inTape = "ab", stack = "hE")))
    multithreadedAutomate.start()
    var time = 0;
    while (!SynchList.foundFinish() && time < 100) {
        Thread.sleep(100)
        time++
    }
    if (SynchList.foundFinish()) {
        println("Финальный путь найден: ")
        SynchList.finishList.forEach {
            println("\n Путь: \n------------")
            it.forEach {
                println(it)
            }
            println("--------")
        }
    }


    println("Все пути")
    SynchList.list.forEach {
        println(it)
    }

}