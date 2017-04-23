# T3AI
This is a java implementation of Tic-Tac-Toe AI with Probabilistic Reasoning. 
The T3AI can be easily implemented in any java application. The GameHandler.java is a demo
program to give an idea on how it works. Check out the Output.txt file to know the input formats.

To know more about this java application, you can check out :
http://www.sudocoding.xyz/tic-tac-toe-ai-with-probabilistic-reasoning

Avaliable public methods:
<ul>
  <li><strong>T3AI(boolean playerGoFirst, char playerChar)</strong></br>
  This is the constructor that takes in two parameters: 
  boolean playerGoFirst: This is to be true, if the player gets to play the first move.
  char playerChar: This contains the char that the player will use. Either 'o' or 'x'
  </li>
  <li><strong>boolean playing()</strong></br>
  Returns true, if the game can continue, i.e. more moves can be given and has not reached a victory, loss or draw
  </li>
  <li><strong>void markMove(int i, int j, boolean isPlayer) throws T3AIException</strong></br>
  This takes three parameters. int i,j marks the row-column index of the char[][] gameBoard which stores the game state.
  The boolean isPlayer denotes who has played the move. If the Player has played, it will be true.
  </li>
  <li><strong>char getVictor()</strong></br>
  This method returns the char of the victor of the game. The return value is ' ' if there was no victor.
  </li>
  <li><strong>char[][] getGameState()</strong></br>
  This method returns the gameBoard char 2d-array containing the location of each played move and the played chars.
  </li>
  <li><strong>int[] animateCPU() throws T3AIException</strong></br>
  This method returns the move played by the CPU after animating the CPU's move
  </li>
  <li><strong>boolean isPlayerTurn()</strong></br>
  This method returns true if it's the turn of the player to move.
  </li>
</ul>

The <strong>T2AIException</strong> is a implementation of Exception class written within the T3AI java file. It handles the custom
errors of the T3AI. 
