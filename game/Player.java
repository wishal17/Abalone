package game;


/**
 * Abstract class for keeping a player in Abalone.
 */
public abstract class Player {

    // -- Instance variables -----------------------------------------

    private String name;
    private Marble Marble;

    // -- Constructors -----------------------------------------------

    /**
     * Creates a new Player object.
     * @requires name is not null
     * @requires Marble is either Black, White, Yellow or Red
     * @ensures the Name of this player will be name
     * @ensures the Marble of this player will be Marble
     */
    public Player(String name, Marble Marble) {
        this.name = name;
        this.Marble = Marble;
    }

    // -- Queries ----------------------------------------------------

    /**
     * Returns the name of the player.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the Marble of the player.
     */
    public Marble getMarble() {
        return Marble;
    }

    /**
     * Determines the field for the next move.
     * @requires board is not null and not full
     * @ensures the returned in is a valid field index and that field is empty
     * @param board the current game board
     * @return the player's choice
     */
    public abstract void determineMove(Board board);

    // -- Commands ---------------------------------------------------

    /**
     * Makes a move on the board. <br>
     * @requires board is not null and not full
     * @param board the current board
     */
    public void makeMove(Board board) {
        determineMove(board);
    }

}
