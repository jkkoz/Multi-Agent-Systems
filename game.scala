import scala.util.control.Breaks._

object game {

  def main(args: Array[String]): Unit = {
    breakable {
      while (true) {
        println("Enter number of games: ")
        val nGames: Int = scala.io.StdIn.readInt()
        if (nGames <= 0) break()

        val payoffMatrix1 = Array(
          Array(1, 5),
          Array(5, 1)
        )

        val payoffMatrix2 = Array(
          Array(1, 5),
          Array(5, 1)
        )

        var jointCD: Int = 0
        var jointDC: Int = 0
        var jointDD: Int = 0
        var jointCC: Int = 0

        val agent1 = new Agent(payoffMatrix1)
        val agent2 = new Agent(payoffMatrix2)

        for (i <- 1 to nGames) {
          (agent1.selectedAction, agent2.selectedAction) match {
            case (`c`, `d`) => jointCD += 1
            case (`d`, `c`) => jointDC += 1
            case (`c`, `c`) => jointCC += 1
            case (`d`, `d`) => jointDD += 1
          }

          val reward1 = agent1.getUtility(agent2.selectedAction)
          val reward2 = agent2.getUtility(agent1.selectedAction)

          agent1.reviseQ(reward1)
          agent2.reviseQ(reward2)

          agent1.selectedAction = agent1.decideNextAction()
          agent2.selectedAction = agent2.decideNextAction()
        }

        def perc(act: Int) : Double = act.toDouble / nGames.toDouble * 100

        println(s"Joint action (C, D) occurred = $jointCD times; ${perc(jointCD)}%")
        println(s"Joint action (D, C) occurred = $jointDC times; ${perc(jointDC)}%")
        println(s"Joint action (C, C) occurred = $jointCC times; ${perc(jointCC)}%")
        println(s"Joint action (D, D) occurred = $jointDD times; ${perc(jointDD)}%")

        println(s"Agent1 Q(C) = ${agent1.currentC}, Q(D) = ${agent1.currentD}")
        println(s"Agent2 Q(C) = ${agent2.currentC}, Q(D) = ${agent2.currentD}")
      }
    }
  }
}
