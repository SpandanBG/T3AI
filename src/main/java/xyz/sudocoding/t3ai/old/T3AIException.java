package xyz.sudocoding.t3ai.old;

/**
 * This is an Exception class to handle special Exceptions of the T3AI
 * */
public class T3AIException extends Exception{
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
    public int getErrorTag(){
		return errTag;
	}
}
