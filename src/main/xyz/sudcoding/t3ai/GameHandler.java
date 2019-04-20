package xyz.sudcoding.t3ai;

import static java.lang.System.out;
import java.util.Scanner;

public class GameHandler {
	private static Scanner in = new Scanner(System.in);

	public static void main(String...args){
		out.println("Welcome to T3AI Tic Tac Toe Game");
		int choice=1;
		do{
			out.print("Would you like to go first(y/n)? : ");
			char playerF = in.next().charAt(0);
			out.print("Select your char(o/x)? : ");
			char playerC = in.next().charAt(0);

			gameFunc(playerF=='y',playerC);

			out.print("Play again (1/0)? : ");
			choice = in.nextInt();
		}while(choice!=0);
	}

	private static void gameFunc(boolean playerFirst, char playerChar){
		int move[] = new int[2];
		try{
			T3AI myGame = new T3AI(playerFirst,playerChar);
			while(myGame.playing()){
				while(myGame.isPlayerTurn()){
					try{
						out.print("Player's move: ");
						move[0] = in.nextInt();
						move[1] = in.nextInt();
						myGame.markMove(move[0], move[1], true);
						display(myGame.getGameState());
					} catch (T3AIException e){
						if(e.getErrorTag()==5){
							break;
						} else {
							out.println(e.toString());
						}
					}
				}
				while(!myGame.isPlayerTurn()){
					try{
						move = myGame.animateCPU();
						out.println("CPU moved: "+move[0]+", "+move[1]);
						display(myGame.getGameState());
					} catch(T3AIException e){
						if(e.getErrorTag()==5){
							break;
						} else {
							out.println(e.toString());
						}
					}
				}
			}
			char v = myGame.getVictor();
			out.println((v==' ')?"Tie":"Winner "+v);
		} catch(T3AIException e){
			out.println(e.toString());
		}
	}

	private static void display(char gameBoard[][]){
		out.println(gameBoard[0][0] + " | " + gameBoard[0][1] + " | " + gameBoard[0][2]);
		out.println("-- --- --");
		out.println(gameBoard[1][0] + " | " + gameBoard[1][1] + " | " + gameBoard[1][2]);
		out.println("-- --- --");
		out.println(gameBoard[2][0] + " | " + gameBoard[2][1] + " | " + gameBoard[2][2]);
		out.println();
	}
}