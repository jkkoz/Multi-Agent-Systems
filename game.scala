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

    println("Game started")
  }
}
