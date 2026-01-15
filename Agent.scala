class Agent( val matrix: Array[Array[Int]] = Array.ofDim[Int](2,2))
{
  var selectedAction: Action = c
  var current: Int = 0
  //History of local actions (optional); ArrayList<Character> ?
  //History of actions other agents took in past. (if necessary)
  //And so many others .....
  def getUtility(x : Action) : Int = 0//x is the action of adversary agents. Returns the payoff values of joint action of local and adversary agent’s action.
  def reviseQ(q : Int) : Double = 0//Revise the value of Q(c) or Q(d) after a game.
  def decideNextAction() : Action = c//Decide next action based on Q values and ε-greedy strategy.
}
