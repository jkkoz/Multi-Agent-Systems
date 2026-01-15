object game {
  def main(args: Array[String]): Unit = {
    val payoffMatrix1 = Array(
      Array(1, 5),
      Array(5, 1)
    )

    val payoffMatrix2 = Array(
      Array(1, 5),
      Array(5, 1)
    )

    val agent1 = new Agent(payoffMatrix1)
    val agent2 = new Agent(payoffMatrix2)

    for (i <- 1 to 100)
    {
      val action1 = agent1.decideNextAction()
      val action2 = agent2.decideNextAction()

      agent1.selectedAction = action1
      agent2.selectedAction = action2

      val reward1 = agent1.getUtility(action2)
      val reward2 = agent2.getUtility(action1)

      agent1.reviseQ(reward1)
      agent2.reviseQ(reward2)
    }

    println(s"Agent1 Q(C) = ${agent1.currentC}, Q(D) = ${agent1.currentD}")
    println(s"Agent2 Q(C) = ${agent2.currentC}, Q(D) = ${agent2.currentD}")
  }
}
