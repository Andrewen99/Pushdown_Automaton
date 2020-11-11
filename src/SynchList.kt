import java.util.*

object SynchList {
    val list: MutableList<List<AutState>> = mutableListOf()
    val finishList: MutableList<List<AutState>> = mutableListOf()

    @Synchronized
    fun addToList(list: List<AutState>) {
        this.list.add(list)
    }

    fun addToFinishList(list: List<AutState>) {
        finishList.add(list)
    }

    @Synchronized
    fun foundFinish(): Boolean {
        return finishList.size == 1
    }
}