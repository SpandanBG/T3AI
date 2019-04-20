package xyz.sudcoding.t3ai;

import java.util.Random;

/**
 * T3AI Class To Implement Smart AI to play a game of Tic Tac Toe
 * @author: Spandan Buragohain 
 *    j -->
 * i  o | x | o
 * |  -- --- --
 * v  x | o | x
 *    -- --- --
 *    0 | x | o
 * */
public class T3AI {
	// Game Variables
	private char[][] gameBoard = new char[9][9];    // To keep track of the game state
	private int turn;								// To keep track of the number of turns
	private char cpu, player, victor;				// CPU, Player and Victor's chars
	private boolean cpuTurn;						// Keep track if the current game is to be played by the CPU
	private Random rand = new Random();				// Generates a random number 
	
	/*-------------------------------PUBLIC METHODS-------------------------------*/
	
	/**
	 * The Constructor creates a new instance of the game 
	 * It can also be used to reset the game
	 * @param playerGoFirst -> False if the first move to be made is by CPU
	 * @param playerChar -> The char chosen by the player: either 'x' or 'o' 
	 * */
	public T3AI(boolean playerGoFirst, char playerChar) throws T3AIException {
		if(playerChar!='x' && playerChar!='o'){						// If neither 'x' nor 'o' -> Throw T3AIException
			throw new T3AIException("Invalid Character Input: Enter either 'x' OR 'o'");
		}
		for(int i=0; i<3; i++){
			for(int j=0; j<3; j++){
				gameBoard[i][j]=' ';
			}
		}
		turn=0;
		player = playerChar;
		cpu = (playerChar=='x')?'o':'x';
		victor=' ';
		cpuTurn = !playerGoFirst;
	}
	
	/**
	 * This method checks if there are any more moves left
	 * Or the game have not ended with a defeat or victory
	 * And returns True
	 * Can be used to keep the game alive by using it as a
	 * Condition inside the while loop
	 * @return boolean -> If current game state has more moves left
	 * */
	boolean playing(){
		return hasMoves() && matched()==' ';
	}

	/**
	 * This method inputs the player's move into the
	 * Game state and returns successfully if
	 * Marked the move, otherwise throws T2AIException
	 * @param i -> the y coordinate of the game state
	 * @param j -> the x coordinate of the game state
	 * @param isPlayer -> True if the player is giving it's move
	 * @throws T3AIException
	 * */
	void markMove(int i, int j, boolean isPlayer) throws T3AIException{
		
		if((cpuTurn && isPlayer) || (!cpuTurn && !isPlayer)){
			throw new T3AIException("Invalid Player");
		}
		if((i*3+j)<0 || (i*3+j)>8){
			throw new T3AIException("Invalid Location Coordinate");
		}
		if(gameBoard[i][j]!=' '){
			throw new T3AIException("Preoccupied Location Coordinate");
		}
		if(!hasMoves()){
			throw new T3AIException("No Moves Left");
		}
		gameBoard[i][j] = (isPlayer)?player:cpu;
		cpuTurn = !cpuTurn;		// Flip Turn
		turn++;					// Increment Turn
	}

	/**
	 * This method is responsible to send the victor's char
	 * It sends a ' ', if it was draw
	 * @return victor -> char of the winner
	 * */
	char getVictor(){
		return victor=matched();
	}
	
	/**
	 * This method returns the Game State
	 * As an 2D array of char
	 * @return gameBoard -> The state of the game as a char[][]
	 * */
	char[][] getGameState(){
		return gameBoard;
	}

	/**
	 * This method calculates the CPU's move and returns the 
	 * Coordinates of the location it has marked it has moved
	 * @return move[] -> i, j coordinates of the move
	 * @throws T3AIException if no moves can be given (Err ID: 5)
	 * */
	int[] animateCPU() throws T3AIException {
		if(hasMoves() && getVictor()==' '){					// Checks if any more moves are left
			int[] moveSet = new int[2];
			if(turn==0){		// Generates a random move in the beginning
				int i = rand.nextInt(8);
				moveSet[0]=i/3; moveSet[1]=i-moveSet[0]*3;
				try{
					markMove(moveSet[0],moveSet[1],false);
				} catch(T3AIException ignored){}
			} else {			// Compute the best move
				moveSet = findBestMove();
				try{
					markMove(moveSet[0],moveSet[1],false);
				} catch(T3AIException ignored){}
			}
			return moveSet;
		} else {
			throw new T3AIException("No Moves Left");
		}
	}
	
	/**
	 * This method returns true if the current round is for the player to play
	 * @returns boolean -> true if it's player's turn to play
	 * */
	boolean isPlayerTurn(){
		return !cpuTurn;
	}
	
	/*-------------------------------PRIVATE METHODS-------------------------------*/
	
	/**
	 * This method is responsible to check if any more moves are remaining
	 * Can be simply implemented with `return turn<9` but it is not useful
	 * in methods which do not keep track of turns played
	 * @return boolean -> based on if any more moves can be given in a given game state
	 * */
	private boolean hasMoves(){
		for(int i=0; i<3; i++){
			for(int j=0; j<3; j++){
				if(gameBoard[i][j]==' '){
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * This method is responsible to check if there are any victory occurred for 
	 * any of the player, i.e. CPU or Player
	 * If any victor exits, it returns the victor's char
	 * Otherwise returns ' '
	 * @return char -> char of the player that has won the match
	 * */
	private char matched(){
		for(int i=0; i<3; i++){
			// Checking horizontally
			if(gameBoard[i][0]==gameBoard[i][1] && gameBoard[i][1]==gameBoard[i][2] && gameBoard[i][0]!=' '){
				return gameBoard[i][0];
			}
			// Checking vertically
			if(gameBoard[0][i]==gameBoard[1][i] && gameBoard[1][i]==gameBoard[2][i] && gameBoard[2][i]!=' '){
				return gameBoard[0][i];
			}
		}
		// Checking diagonally
		if(gameBoard[0][0]==gameBoard[1][1] && gameBoard[1][1]==gameBoard[2][2] && gameBoard[1][1]!=' '){
			return gameBoard[1][1];
		} else if (gameBoard[0][2]==gameBoard[1][1] && gameBoard[1][1]==gameBoard[2][0] && gameBoard[1][1]!=' '){
			return gameBoard[1][1];
		}
		// Default return
		return ' ';
	}
	
	/**
	 * This method evaluates the best move for a given state
	 * And returns the integer array of the move coordinates
	 * If any failure occurs, it returns -1, -1
	 * @return moveSet -> i,j coordinates in an array of the move to be played by the CPU
	 * */
	private int[] findBestMove(){
		int[] ms_pov = new int[2];        // Arrays to store the coordinates of highestPOV and leastPOL, respectively
		int[] ms_pol = new int[2];
		int[] obtain = new int[3];				// Array to store the NOL, NOG and NOV data given by cpuBrain(), respectively;
		float leastPOL=999999;					// Least probability of defeat
		float highestPOV=-999999;				// Highest probability of victory
		float POLcV=-999999, POVcL=999999;		// The POV and POL counter parts of leastPOL and highestPOV, respectively
		float gotPOL, gotPOV;					// Obtained probability of defeat, victory and defeat from new set of data
		ms_pov[0]=ms_pov[1]=ms_pol[0]=ms_pol[1]=1;

		int[] counterMove = performCounter(cpu, player);	// Check for possible victory
		if(counterMove[2]!=0){								// Non zero victory moves
			ms_pov[0]=counterMove[0]; ms_pov[1]=counterMove[1];
			return ms_pov;
		}
		
		counterMove = performCounter(player,cpu);	// Check for possible defeat
		if(counterMove[2]>0){							// Non zero counter-able move 
			ms_pol[0]=counterMove[0]; ms_pol[1]=counterMove[1];
			return ms_pol;
		}
		
		// Checking for possible forking move for CPU
		int[] place = forkCheck(cpu, player);
		if(place[2]==1){
			ms_pov[0]=place[0];ms_pov[1]=place[1];
			return ms_pov;
		}

		for(int i=0; i<3; i++){
			for(int j=0; j<3; j++){
				if(gameBoard[i][j]==' '){
					gameBoard[i][j]=cpu;
					place = forkCheck(player,cpu);
					if(place[2]==0){						// Checking for possible Player's ability to fork
						obtain = cpuBrain(false);			// Getting the data-set to analyze for the best move
						gotPOL = (float)(obtain[0])/(float)(obtain[1]);		// NOL/NOG (Number of lose/Number of games)
						gotPOV = (float)(obtain[2])/(float)(obtain[1]);		// NOV/NOG (Number of victory/Number of games)
						
						// Getting the highest possible POV
						if(highestPOV<gotPOV){
							highestPOV=gotPOV;
							POVcL=gotPOL;
							ms_pov[0]=i; ms_pov[1]=j;
						} else if (highestPOV==gotPOV && gotPOL<POVcL){
							highestPOV=gotPOV;
							POVcL=gotPOL;
							ms_pov[0]=i; ms_pov[1]=j;
						}
						// Getting the least possible POL
						if(leastPOL>gotPOL){
							leastPOL=gotPOL;
							POLcV=gotPOV;
							ms_pol[0]=i; ms_pol[1]=j;
						} else if (leastPOL==gotPOL && gotPOV>POLcV){
							leastPOL=gotPOL;
							POLcV=gotPOV;
							ms_pol[0]=i; ms_pol[1]=j;
						}
					}
					gameBoard[i][j]=' ';
				}
			}
		}
		
		// Finding the better probability
		leastPOL=1-leastPOL;	// ~POL = 1-POL
		if(leastPOL>highestPOV){
			return ms_pol;
		} else{
			return ms_pov;
		}
	}
	
	/**
	 * This is the brain of the CPU Player
	 * It analyzes the best move using back tracking algorithm
	 * And playing the optimal move for each set of game state
	 * @param isCPU -> boolean to mark whose game is being test
	 * @return obtain[] -> an integer array containing the NOL and NOG 
	 * */
	private int[] cpuBrain(boolean isCPU){
        int[] cost = new int[3];		// NOL, NOG and NOV respectively
		cost[0]=cost[1]=cost[2]=0;

		// Check for possible victory
		victor = matched();
		if(victor==player){				// If the player wins in the given game state, it's marked as defeat 
			cost[0]=1;
			cost[1]=1;
			cost[2]=0;
			victor=' ';
			return cost;
		} else if (victor==cpu){		// This is marked as victory
			cost[0]=0;
			cost[1]=1;
			cost[2]=1;
			victor=' ';
			return cost;
		}
		
		// Checking if all moves are done
		if(!hasMoves()){				// This is regarded as a draw
			cost[0]=0;
			cost[1]=1;
			cost[2]=0;
			return cost;
		}
		
		if(isCPU){		// Plays the move for itself (MAXIMIZER)
			return virtualPlayer(cpu,player,false);
		}
		
		if(!isCPU){		// Plays the move for the player (MINIMIZER)
			return virtualPlayer(player,cpu,true);
		}
		
		return cost;
	}
	
	/**
	 * This method acts like a CPU or Player and goes through all the ' '
	 * In the game state OR performs counter at the given state.
	 * This method works with coordinated to cpuBrain()
	 * @param forC -> The char of the current playing character
	 * @param againstC -> The char of the player being played against
	 * @param flag -> To set isCPU for the next iteration of cpuBrain() Has to be false form CPU and True form Player
	 * @returns cost[] -> Total cost obtained
	 * */
	private int[] virtualPlayer(char forC, char againstC, boolean flag){
        int[] cost = new int[3];        // i, j coordinates and, number of counters, respectively
        int[] obtain = new int[3];
        int[] res = new int[3];
        int posi, posj;
		cost[0]=0; cost[1]=0; cost[2]=0;
		
		// Checking for possible victory moves
		res = performCounter(forC,againstC);
		if(res[2]!=0){					// If found, add it to the cost
			if(forC==cpu){			// CPU wins
				cost[0] = 0;
				cost[2] = res[2];
			} else {				// Player wins
				cost[0] = res[2];
				cost[2] = 0;
			}
			cost[1] = res[2];
			return cost;
		}
		
		// Checking for possible counter moves
		res = performCounter(againstC,forC);
		if(res[2]==1){					// If single counter-able position found, take it
			posi=res[0]; posj=res[1];
			gameBoard[posi][posj]=forC;
			obtain = cpuBrain(flag);
			cost[0] += obtain[0];
			cost[1] += obtain[1];
			cost[2] += obtain[2];
			gameBoard[posi][posj]=' ';
			return cost;
		} else if(res[2]>1){			// If multiple counter-able position found, assume that many defeats and that many games
			if(againstC==cpu){		// CPU wins
				cost[0] = 0;
				cost[2] = 2;
			} else {				// Player wins
				cost[0] = 2;
				cost[2] = 0;
			}
			cost[1] = 2;
			return cost;
		}
		
		// If above check fails, fall back to conventional checking all possible moves
		for(int i=0; i<3; i++){
			for(int j=0; j<3; j++){
				if(gameBoard[i][j]==' '){
					gameBoard[i][j] = forC;
					obtain = cpuBrain(flag);
					cost[0] += obtain[0];
					cost[1] += obtain[1];
					cost[2] += obtain[2];
					gameBoard[i][j] = ' ';
				}
			}
		}
		return cost;
	}
	
	/**
	 * This method checks for two continuous sequence of char
	 * And a ' '. If found, it notes the coordinates and the 
	 * The number of occurrence of such sequence and sends it to
	 * The caller.
	 * @param checkFor -> The character that needs to be checked
	 * @param checkAgainst -> The character that shouldn't be in the sequence
	 * @return result[3] -> [0],[1] >> i,j and [2] >> 0-no match, 1-one match, ...
	 * */
	public int[] performCounter(char checkFor, char checkAgainst){
		int result[] = new int[3];			// i, j coordinates and number of counter places found, respectively
		int val,posi=-1,posj=-1;

		// Checking horizontally
		for(int i=0; i<3; i++){
			val=0;
			for(int j=0; j<3; j++){
				val+=(gameBoard[i][j]==checkFor)?1:0;
				val+=(gameBoard[i][j]==checkAgainst)?-1:0;
				if(gameBoard[i][j]==' '){
					posi=i; posj=j;
				}
			}
			if(val==2){
				result[0]=posi; result[1]=posj; result[2]++;
			}
		}

		// Checking vertically
		for(int j=0; j<3; j++){
			val=0;
			for(int i=0; i<3; i++){
				val+=(gameBoard[i][j]==checkFor)?1:0;
				val+=(gameBoard[i][j]==checkAgainst)?-1:0;
				if(gameBoard[i][j]==' '){
					posi=i; posj=j;
				}
			}
			if(val==2){
				result[0]=posi; result[1]=posj; result[2]++;
			}
		}
		
		// Checking diagonally
		for(int i=0; i<3; i+=2){
			val=0;
			for(int j=0; j<3; j++){
				val+=(gameBoard[j][Math.abs(i-j)]==checkFor)?1:0;
				val+=(gameBoard[j][Math.abs(i-j)]==checkAgainst)?-1:0;
				if(gameBoard[j][Math.abs(i-j)]==' '){
					posi=j; posj=Math.abs(i-j);
				}
			}
			if(val==2){
				result[0]=posi; result[1]=posj; result[2]++;
			}
		}
		
		return result;
	}
	
	/**
	 * This method is responsible to test for possible forking in a given
	 * game state. The idea is simple, For every blank space put the forC
	 * and check if any counter is occurring. If Yes, counter put the againstC into
	 * the counter position. Check if againstC will win. If Not, then check if 
	 * forC can be put anywhere that would cause double counter. If Yes, the 
	 * first position of forC is the fork position.
	 * @param forC -> the fork creator
	 * @param againstC -> the fork victim
	 * @return place[] -> 0,1->i,j and 2->"if fork is possible"=1, "else"=0
	 * */
	private int[] forkCheck(char forC, char againstC){
        int[] place = new int[3];
		place[0]=place[1]=place[2]=0;
		for(int i=0; i<3; i++){
			for(int j=0; j<3; j++){
				if(gameBoard[i][j]==' '){
					gameBoard[i][j]=forC;
					int res[] = performCounter(forC,againstC);
					victor = matched();
					if(res[1]==1 && victor==' '){
						gameBoard[res[0]][res[1]]=againstC;
						int res0[] = performCounter(againstC,forC);
						if(res0[2]==0){
							for(int m=0; m<3; m++){
								for(int n=0; n<3; n++){
									if(gameBoard[m][n]==' '){
										gameBoard[m][n]=forC;
										int res2[] = performCounter(forC,againstC);
										if(res2[2]==2){
											gameBoard[m][n]=' ';
											gameBoard[res[0]][res[1]]=' ';
											gameBoard[i][j]=' ';
											place[0]=i;place[1]=j;place[2]=1;
											return place;
										}
										gameBoard[m][n]=' ';
									}
								}
							}
						}
						gameBoard[res[0]][res[1]]=' ';
					}
					gameBoard[i][j]=' ';
					victor=' ';
				}
			}
		}
		return place;
	}
}

/**
 * This is an Exception class to handle special Exceptions of the T3AI
 * */
class T3AIException extends Exception{
	private static final long serialVersionUID = 1L;
	private int errTag;
	T3AIException(String msg){
		super(msg);
		switch(msg){
		case "Invalid Character Input: Enter either 'x' OR 'o'": 
			errTag=1; break;
		case "Invalid Player": 
			errTag=2; break;
		case "Preoccupied Location Coordinate": 
			errTag=3; break;
		case "Invalid Location Coordinate": 
			errTag=4; break;
		case "No Moves Left": 
			errTag=5; break;
		default: 
			errTag=0; break;
		}
	}
	/**
	 * This method returns the error tag
	 * List of error tags:
	 * @see 1 -> Invalid Character Input: Enter either 'x' OR 'o'
	 * @see 2 -> Invalid Player
	 * @see 3 -> Preoccupied Location Coordinate
	 * @see 4 -> Invalid Location Coordinate
	 * */
    int getErrorTag(){
		return errTag;
	}
}
