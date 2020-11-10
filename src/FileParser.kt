import java.io.File

object FileParser {
    fun parseFileToAutomate(path: String): Automate {
        val automate: Automate = Automate()
        File(path).forEachLine {
            val line = it.replace(" ", "")
            automate.rawLines.add(line)
            createRule1(line, automate)
        }
        println("rules " + automate.rules1)
        println("raw lines " + automate.rawLines)
        automate.fillP()
        automate.createRules2()
        return automate
    }

    fun createRule1(line: String, automate: Automate) {
        val input = line[0]
        automate.Z.add(input)
        automate.NotP.add(input)
        var output = line.removeRange(0, 2).split("|");
        output.forEach {
            it.forEach {
                automate.Z.add(it)
            }
        }
        automate.rules1.add(AutRule(input.toString(), output))
    }
}