package game;

import java.util.ArrayList;
import java.util.List;

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
	private boolean gamestarted = false;
	private List<ClientHandler> chl = new ArrayList<ClientHandler>();
	private ClientHandler partyleader;
	private String status = "NotStarted";
	private Game game;
	Player p1, p2, p3, p4;

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

		switch (numplayers) {
		case 2:
			p1 = new HumanPlayer(chl.get(0).getClientHandlerName(), Marble.WW);
			p2 = new HumanPlayer(chl.get(1).getClientHandlerName(), Marble.BB);
			game = new Game(p1, p2);
			game.start();
			break;
		case 3:
			p1 = new HumanPlayer(chl.get(0).getClientHandlerName(), Marble.WW);
			p2 = new HumanPlayer(chl.get(1).getClientHandlerName(), Marble.BB);
			p3 = new HumanPlayer(chl.get(2).getClientHandlerName(), Marble.YY);
			game = new Game(p1, p2, p3);
			game.start();
		case 4:
			p1 = new HumanPlayer(chl.get(0).getClientHandlerName(), Marble.WW);
			p2 = new HumanPlayer(chl.get(1).getClientHandlerName(), Marble.YY);
			p3 = new HumanPlayer(chl.get(2).getClientHandlerName(), Marble.BB);
			p4 = new HumanPlayer(chl.get(3).getClientHandlerName(), Marble.RR);
			game = new Game(p1, p2, p3, p4);
			game.start();
		}
		status = "Started";
	}

	/**
	 * Assigns the party leader's teammate
	 * 
	 * @param cl
	 */
	public void leaderTeammate(ClientHandler cl) {
		for (ClientHandler c : getPlayerList()) {
			if (c == cl || getPlayerList().get(2) != c) {
				int leaderteampos = getPlayerList().indexOf(c);
				ClientHandler temp = getPlayerList().get(2);
				getPlayerList().set(2, cl);
				getPlayerList().set(leaderteampos, temp);
			}
		}
	}

	/**
	 * Used to get the player each client handler controls.s
	 * 
	 * @param cl
	 * @return
	 */
	public Player getPlayer(ClientHandler cl) {
		switch (chl.indexOf(cl) + 1) {
		case 1:
			return p1;
		case 2:
			return p2;
		case 3:
			return p3;
		case 4:
			return p4;
		}
		return null;
	}

	/**
	 * Returns the game.
	 */
	public Game getGame() {
		return game;
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
			partyleader = cl;
		} else {
			chl.add(cl);
		}
	}

	/**
	 * Removes a player from the room. NOT COMPLETELY IMPLEMENTED NOT COMPLETELY
	 * IMPLEMENTED NOT COMPLETELY IMPLEMENTED NOT COMPLETELY IMPLEMENTED NOT
	 * COMPLETELY IMPLEMENTED
	 * 
	 * NOT COMPLETELY IMPLEMENTEDNOT COMPLETELY IMPLEMENTED NOT COMPLETELY
	 * IMPLEMENTED NOT COMPLETELY IMPLEMENTED NOT COMPLETELY IMPLEMENTED
	 * 
	 * @param cl
	 */
	public void removePlayer(ClientHandler cl) {
		if (chl.contains(cl)) {
			if(chl.size()>1) {
				if (chl.get(0) == cl) {
						partyleader = chl.get(1);
				}
			}
			
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
	 * Gets the clients in the room.
	 * 
	 * @return
	 */
	public List<ClientHandler> getPlayerList() {
		return chl;
	}

	/**
	 * Makes a move
	 * 
	 * @param p
	 */
	public void makeMove(Player p) {
		p.determineMove(game.getBoard());
	}

}
