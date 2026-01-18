import scala.util.Random

class Agent( val matrix: Array[Array[Int]] = Array.ofDim[Int](2,2),
             val alpha: Double = 0.05,
             val epsilon: Double = 0.05)
{
  var selectedAction: Action = d
  var currentC: Double = 1
  var currentD: Double = 1

  //x is the action of adversary agents. Returns the payoff values of joint action of local and adversary agent’s action.
  def getUtility(adversaryAction : Action) : Int =
  {
    val row = if (selectedAction == c) 0 else 1
    val col = if (adversaryAction == c) 0 else 1
    matrix(row)(col)
  }


  //Revise the value of Q(c) or Q(d) after a game.
  def reviseQ(v : Int) : Unit =
  {
    selectedAction match {
      case c => currentC = alpha*v + (1 - alpha)* currentC
      case d => currentD = alpha*v + (1 - alpha)* currentD
    }
  }

  //Decide next action based on Q values and ε-greedy strategy.
  def decideNextAction() : Action =
  {
    if (Random.nextDouble() < epsilon)
    {
      if (Random.nextBoolean()) c else d
    }
    else
    {
      if (currentC > currentD) c
      else if (currentD > currentC) d
      else if (Random.nextBoolean()) c else d
    }
  }

}
