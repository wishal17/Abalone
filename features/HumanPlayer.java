package features;

import java.util.Scanner;


/**
 * Class for maintaining a human player in Abalone.
 */
public class HumanPlayer extends Player {

	// -- Constructors -----------------------------------------------

	/**
	 * Creates a new human player object.
	 * 
	 * @requires name is not null
	 * @requires Marble is either BB, WW, RR or YY.
	 * @ensures the Name of this player will be name
	 * @ensures the Marble of this player will be Marble
	 */
	public HumanPlayer(String name, Marble Marble) {
		super(name, Marble);
	}

	// -- Commands ---------------------------------------------------

	/**
	 * Asks the user to input the field where to place the next Marble. This is done
	 * using the standard input/output.
	 * 
	 * @requires board is not null
	 * @ensures the returned in is a valid move
	 * @param board the game board
	 * @return the player's chosen field
	 */
	public void determineMove(Board board) {
		String prompt = "> " + getName() + " (" + getMarble().toString() + ")" + ", what is your choice? ";
		String pos1, pos2, pos3;
		System.out.println(prompt);
		Scanner sc = new Scanner(System.in);
		String command = sc.nextLine();
		String split[] = command.split(";");
		String move = split[1];
		int direction = Integer.parseInt(split[2]);
		Marble marble = getMarble();
		boolean valid = true;
		
		if (move.length() == 6) {
			pos1 = move.charAt(0) + "" + move.charAt(1);
			pos2 = move.charAt(2) + "" + move.charAt(3);
			pos3 = move.charAt(4) + "" + move.charAt(5);
			valid = board.isValidMove(pos1, pos2, pos3, direction, marble);
		} else if (move.length() == 4) {
			pos1 = move.charAt(0) + "" + move.charAt(1);
			pos2 = move.charAt(2) + "" + move.charAt(3);
			valid = board.isValidMove(pos1, pos2, direction, marble);
		} else if (move.length() == 2) {
			pos1 = move.charAt(0) + "" + move.charAt(1);
			valid = board.isValidMove(pos1, direction, marble);
		}
		while (!valid) {
            System.out.println("ERROR: This move " + move
                    + " is not a valid choice.");
            System.out.println(prompt);
            sc = new Scanner(System.in);
    		command = sc.nextLine();
            if (move.length() == 6) {
    			pos1 = move.charAt(0) + "" + move.charAt(1);
    			pos2 = move.charAt(2) + "" + move.charAt(3);
    			pos3 = move.charAt(4) + "" + move.charAt(5);
    			valid = board.isValidMove(pos1, pos2, pos3, direction, marble);
    		} else if (move.length() == 4) {
    			pos1 = move.charAt(0) + "" + move.charAt(1);
    			pos2 = move.charAt(2) + "" + move.charAt(3);
    			valid = board.isValidMove(pos1, pos2, direction, marble);
    		} else if (move.length() == 2) {
    			pos1 = move.charAt(0) + "" + move.charAt(1);
    			valid = board.isValidMove(pos1, direction, marble);
    		}
        }
	}

}
