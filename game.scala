import scala.util.control.Breaks._
import java.io._

object game {

  def actToInt(a: Action): Int =
    if (a == `c`) 0 else 1

  def main(args: Array[String]): Unit = {
    breakable {
      while (true) {
        println("Enter number of games: ")
        val nGames: Int = scala.io.StdIn.readInt()
        if (nGames <= 0) break()

        val payoffMatrix1 = Array(
          Array(1, 0),
          Array(5, 2)
        )

        val payoffMatrix2 = Array(
          Array(2, 0),
          Array(5, 1)
        )

        var jointCD: Int = 0
        var jointDC: Int = 0
        var jointDD: Int = 0
        var jointCC: Int = 0

        val agent1 = new Agent(payoffMatrix1)
        val agent2 = new Agent(payoffMatrix2)

        val writer = new PrintWriter(new File("log4.csv"))
        writer.println("iter,a1,a2,q1C,q1D,q2C,q2D")

        for (i <- 1 to nGames) {
          agent1.selectedAction = agent1.decideNextAction()
          agent2.selectedAction = agent2.decideNextAction()

          val reward1 = agent1.getUtility(agent2.selectedAction)
          val reward2 = agent2.getUtility(agent1.selectedAction)

          agent1.reviseQ(reward1)
          agent2.reviseQ(reward2)

          (agent1.selectedAction, agent2.selectedAction) match {
            case (`c`, `d`) =>
              jointCD += 1
              if (jointCD == 1 && jointDC == 0) println(s"CD selected for the first time, iteration: $i")
            case (`d`, `c`) =>
              jointDC += 1
              if (jointDC == 1 && jointCD == 0) println(s"DC selected for the first time, iteration: $i")
            case (`c`, `c`) => jointCC += 1
            case (`d`, `d`) => jointDD += 1
          }

          writer.println(
            s"$i," +
              s"${actToInt(agent1.selectedAction)}," +
              s"${actToInt(agent2.selectedAction)}," +
              s"${agent1.currentC}," +
              s"${agent1.currentD}," +
              s"${agent2.currentC}," +
              s"${agent2.currentD}"
          )
        }
        writer.close()

        def perc(act: Int) : Double = act.toDouble / nGames.toDouble * 100

        println(s"Action (C, D) occurred ${perc(jointCD)}% = $jointCD times;")
        println(s"Action (D, C) occurred ${perc(jointDC)}% = $jointDC times;")
        println(s"Action (C, C) occurred ${perc(jointCC)}% = $jointCC times;")
        println(s"Action (D, D) occurred ${perc(jointDD)}% = $jointDD times;")

        println(s"Agent1 Q(C) = ${agent1.currentC}, Q(D) = ${agent1.currentD}")
        println(s"Agent2 Q(C) = ${agent2.currentC}, Q(D) = ${agent2.currentD}")
      }
    }
  }
}
