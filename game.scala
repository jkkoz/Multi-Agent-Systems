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
          val action1 = agent1.decideNextAction()
          val action2 = agent2.decideNextAction()

          println(s"Agent 1 chose: $action1     Agent 2 chose: $action2")

          agent1.selectedAction = action1
          agent2.selectedAction = action2

          (action1, action2) match {
            case (`c`, `d`) => jointCD += 1
            case (`d`, `c`) => jointDC += 1
            case (`c`, `c`) => jointCC += 1
            case (`d`, `d`) => jointDD += 1
          }

          val reward1 = agent1.getUtility(action2)
          val reward2 = agent2.getUtility(action1)

          agent1.reviseQ(reward1)
          agent2.reviseQ(reward2)

        }
        println(s"Joint action (C, D) occurred = $jointCD times")
        println(s"Joint action (D, C) occurred = $jointDC times")
        println(s"Joint action (C, C) occurred = $jointCC times")
        println(s"Joint action (D, D) occurred = $jointDD times")
        println(s"Agent1 Q(C) = ${agent1.currentC}, Q(D) = ${agent1.currentD}")
        println(s"Agent2 Q(C) = ${agent2.currentC}, Q(D) = ${agent2.currentD}")
      }
    }
  }
}
