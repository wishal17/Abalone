package features;

import java.util.Scanner;

public class AbaloneTUI {
	public static void main(String[] args) {
		System.out.println("Enter Number of Players");
		Scanner sc = new Scanner(System.in);
		int playernum = sc.nextInt();
		while(playernum>4 || playernum <2) {
			System.out.println("Error! Enter number of players between 2-4");
			playernum = sc.nextInt();
		}
		if(playernum == 2) {
			sc = new Scanner(System.in);
			System.out.println("Enter Player 1 name");
			String p1input = sc.nextLine();
			sc = new Scanner(System.in);
			System.out.println("Enter Player 2 name");
			String p2input = sc.nextLine();
			Player p1 = new HumanPlayer(p1input, Marble.BB);
			Player p2 = new HumanPlayer(p2input, Marble.WW);
			Game game = new Game(p1, p2);
			game.start();
		} else if(playernum == 3) {
			sc = new Scanner(System.in);
			System.out.println("Enter Player 1 name");
			String p1input = sc.nextLine();
			sc = new Scanner(System.in);
			System.out.println("Enter Player 2 name");
			String p2input = sc.nextLine();
			sc = new Scanner(System.in);
			System.out.println("Enter Player 3 name");
			String p3input = sc.nextLine();
			Player p1 = new HumanPlayer(p1input, Marble.BB);
			Player p2 = new HumanPlayer(p2input, Marble.WW);
			Player p3 = new HumanPlayer(p3input, Marble.YY);
			Game game = new Game(p1, p2, p3);
			game.start();
		} else if(playernum == 4) {
			sc = new Scanner(System.in);
			System.out.println("Enter Player 1 name");
			String p1input = sc.nextLine();
			sc = new Scanner(System.in);
			System.out.println("Enter Player 2 name");
			String p2input = sc.nextLine();
			sc = new Scanner(System.in);
			System.out.println("Enter Player 3 name");
			String p3input = sc.nextLine();
			sc = new Scanner(System.in);
			System.out.println("Enter Player 4 name");
			String p4input = sc.nextLine();
			Player p1 = new HumanPlayer(p1input, Marble.BB);
			Player p2 = new HumanPlayer(p2input, Marble.WW);
			Player p3 = new HumanPlayer(p3input, Marble.YY);
			Player p4 = new HumanPlayer(p4input, Marble.RR);
			Game game = new Game(p1, p2, p3, p4);
			game.start();
		}
		
		

		
		
	}
}
