package game;

import java.util.ArrayList;
import java.util.List;

import server.ClientHandler;

public class Room {
	private String roomnum;
	private boolean gamestarted = false;
	private List<ClientHandler> chl = new ArrayList<ClientHandler>();
	private ClientHandler partyleader;
	private String status = "NotStarted";
	private Game game;
	Player p1, p2, p3, p4;

	public Room(String num) {
		this.roomnum = num;
	}

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
	
	public Player getPlayer(ClientHandler cl) {
		switch(chl.indexOf(cl)+1) {
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

	public Game getGame() {
		return game;
	}

	public void addPlayer(ClientHandler cl) {
		if (chl.isEmpty()) {
			chl.add(cl);
			partyleader = cl;
		} else {
			chl.add(cl);
		}
	}
	
	public boolean isLeader(ClientHandler cl) {
		if(cl == partyleader) {
			return true;
		}
		return false;
	}

	public void addtoRoom(ClientHandler cl) {
		chl.add(cl);
	}

	
	public String getRoomNum() {
		return roomnum;
	}

	public int getNumPlayers() {
		return chl.size();
	}


	public String getStatus() {
		return status;
	}

	public List<ClientHandler> getPlayerList() {
		return chl;
	}
	

}
