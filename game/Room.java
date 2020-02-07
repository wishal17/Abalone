package game;

import java.util.ArrayList;
import java.util.List;

import client.ClientTUI;
import protocol.ProtocolMessages;
import server.ClientHandler;

/**
 * Class used to create a room on a server so multiple games can take place at
 * the same time.
 * 
 * @author wisha
 *
 */
public class Room {
	private String roomnum;
	private Board serverboard;
	private List<ClientHandler> chl = new ArrayList<ClientHandler>();
	private ClientHandler partyleader;
	private String status = "NotStarted";
	private int turns;
	/**
	 * Constructor for the room
	 * 
	 * @param num
	 */
	public Room(String num) {
		this.roomnum = num;
	}

	/**
	 * Starts a new game
	 * 
	 * @param numplayers
	 */
	public void startGame(int numplayers) {
		reset();
		switch (numplayers) {
		case 2:
			chl.get(0).assignMarble(Marble.WW);
			chl.get(1).assignMarble(Marble.BB);
			serverboard = new Board(new Layout(2));
			break;
		case 3:
			chl.get(0).assignMarble(Marble.WW);
			chl.get(1).assignMarble(Marble.BB);
			chl.get(2).assignMarble(Marble.YY);
			serverboard = new Board(new Layout(3));
			break;
		case 4:
			chl.get(0).assignMarble(Marble.WW);
			chl.get(1).assignMarble(Marble.YY);
			chl.get(2).assignMarble(Marble.BB);
			chl.get(3).assignMarble(Marble.RR);
			serverboard = new Board(new Layout(4));
			break;
		}
		status = "Started";
	}

	/**
	 * Assigns the party leader's teammate
	 * 
	 * @param cl
	 */
	public void leaderTeammate(ClientHandler cl) {
		if (getPlayerList().size() == 4 && getStatus().equals("NotStarted") && chl.contains(cl) && cl != partyleader) {
			ClientHandler temp = chl.get(2);
			int initpos = chl.indexOf(cl);
			chl.set(2, cl);
			chl.set(initpos, temp);
		}
	}

	public void reset() {
		status = "NotStarted";
		serverboard = new Board(new Layout(2));
		turns = 0;
	}

	// Makes move for the given coordinate

	public String makeMove(ClientHandler cl, String coords, String direction) {
		if (gameOver()) {
			return printResult(null);
		}

		if (ClientTUI.numerical(direction)) {
			if (cl.getClientHandlerName().equals(playerturn())) {
				boolean valid = false;
				String pos1, pos2, pos3;
				String move = coords;
				if (move.length() == 6) {
					pos1 = move.charAt(0) + "" + move.charAt(1);
					pos2 = move.charAt(2) + "" + move.charAt(3);
					pos3 = move.charAt(4) + "" + move.charAt(5);
					valid = serverboard.isValidMove(pos1, pos2, pos3, Integer.parseInt(direction), cl.getMarble());
				} else if (move.length() == 4) {
					pos1 = move.charAt(0) + "" + move.charAt(1);
					pos2 = move.charAt(2) + "" + move.charAt(3);
					valid = serverboard.isValidMove(pos1, pos2, Integer.parseInt(direction), cl.getMarble());
				} else if (move.length() == 2) {
					pos1 = move.charAt(0) + "" + move.charAt(1);
					valid = serverboard.isValidMove(pos1, Integer.parseInt(direction), cl.getMarble());
				}
				if (valid) {
					turns++;
					if (gameOver()) {
						return printResult(null);
					}
					return ProtocolMessages.MOVE + ProtocolMessages.DELIMITER + coords + ProtocolMessages.DELIMITER
							+ direction + ProtocolMessages.DELIMITER + cl.getClientHandlerName() + "\n";
				}
				return ProtocolMessages.ERROR + ProtocolMessages.DELIMITER + "MoveNotAllowed\n";
			}
		}
		return ProtocolMessages.ERROR + ProtocolMessages.DELIMITER + "NotYourTurn\n";
	}

	public boolean gameOver() {
		return (serverboard.hasWinner() || turns == 96);
	}

	/**
	 * Prints out the result of the game after the game is over. If there is a
	 * winner, the winner(s) will be printed (depending on the number of players).
	 */
	public String printResult(ClientHandler client) {
		if (serverboard.getLayout().returnPlayers().equals("four")) {
			if (serverboard.hasWinner()) {
				ClientHandler winner1 = null;
				ClientHandler winner2 = null;
				if (serverboard.isWinner(chl.get(0).getMarble())) {
					winner1 = chl.get(0);
					winner2 = chl.get(2);
				} else if (serverboard.isWinner(chl.get(1).getMarble())) {
					winner1 = chl.get(1);
					winner2 = chl.get(3);
				} else if (serverboard.isWinner(chl.get(2).getMarble())) {
					winner1 = chl.get(2);
					winner2 = chl.get(0);
				} else if (serverboard.isWinner(chl.get(3).getMarble())) {
					winner1 = chl.get(3);
					winner2 = chl.get(1);
				}
				return ProtocolMessages.FINISH + ProtocolMessages.DELIMITER + winner1.getClientHandlerName()
						+ ProtocolMessages.DELIMITER + winner2.getClientHandlerName() + "\n";
			} else {
				return ProtocolMessages.FINISH + ProtocolMessages.DELIMITER + "\n";
			}
		} else if (serverboard.getLayout().returnPlayers().equals("two")
				|| serverboard.getLayout().returnPlayers().equals("three")) {
			if (client != null) {
				return ProtocolMessages.FINISH + ProtocolMessages.DELIMITER + chl.get(0).getClientHandlerName() + "\n";
			}
			if (serverboard.hasWinner()) {
				ClientHandler winner = null;
				if (serverboard.isWinner(chl.get(0).getMarble())) {
					winner = chl.get(0);
				} else if (serverboard.isWinner(chl.get(1).getMarble())) {
					winner = chl.get(1);
				} else if (serverboard.isWinner(chl.get(2).getMarble())) {
					winner = chl.get(2);
				} else if (serverboard.isWinner(chl.get(3).getMarble())) {
					winner = chl.get(3);
				}
				return ProtocolMessages.FINISH + ProtocolMessages.DELIMITER + winner.getClientHandlerName() + "\n";
			} else {
				return ProtocolMessages.FINISH + ProtocolMessages.DELIMITER + "\n";
			}
		}
		return null;
	}

	/**
	 * Used to add a new client into a room. If the room is empty and a client is
	 * added, the client is the party leader.
	 * 
	 * @param cl
	 */
	public void addPlayer(ClientHandler cl) {
		if (chl.isEmpty()) {
			chl.add(cl);
			cl.assignRoom(this);
			partyleader = cl;
		} else {
			chl.add(cl);
			cl.assignRoom(this);
		}
	}

	/**
	 * Removes a player from the room.
	 * 
	 * @param cl
	 */
	public void removePlayer(ClientHandler cl) {
		if (chl.contains(cl)) {
			if (chl.size() > 1) {
				if (chl.get(0) == cl) {
					partyleader = chl.get(1);
				}
			}
			if (getStatus().equals("Started")) {
				serverboard.removePlayersMarbles(cl.getMarble());
			}
			cl.assignRoom(null);
			chl.remove(cl);
		}
	}

	/**
	 * Determines if a player is the room's party leader.
	 * 
	 * @param cl
	 * @return
	 */
	public boolean isLeader(ClientHandler cl) {
		if (cl == partyleader) {
			return true;
		}
		return false;
	}

	/**
	 * Gets the number of the room
	 * 
	 * @return
	 */
	public String getRoomNum() {
		return roomnum;
	}

	/**
	 * Gets the number of players in the room
	 * 
	 * @return
	 */
	public int getNumPlayers() {
		return chl.size();
	}

	/**
	 * Returns a status of the game
	 * 
	 * @return "NotStarted" || "Started"
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Returns the player's turn
	 * 
	 * @return clienthandler's name
	 */
	public String playerturn() {
		return chl.get(turns % chl.size()).getClientHandlerName();
	}

	public Board getBoard() {
		return serverboard;
	}

	/**
	 * Gets the clients in the room.
	 * 
	 * @return
	 */
	public List<ClientHandler> getPlayerList() {
		return chl;
	}

	public void setStatus(String string) {
		status = string;
	}

}
