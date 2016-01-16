package com.hanabi.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.hanabi.resource.Player.Move;

public class Tiles {

	enum Color {
		RED, YELLOW, GREEN, BLUE, WHITE, MULTI
	}

	private SetRandom<Tile> board = new SetRandom<>();
	private Map<Color, Integer> played = new HashMap<>();
	private Map<Color, Map<Integer, Integer>> discard = new HashMap<>();
	private int lives = 3;
	private int hints = 8;
	private Player[] players;
	private int turn = 0;

	private final int totalPlayableTiles;

	private int nTiles;

	public Tiles(boolean multi, int nPlayers) {
		for (Color color : Color.values()) {
			if (color != Color.MULTI || multi) {
				board.add(new Tile(1, color));
				board.add(new Tile(1, color));
				board.add(new Tile(1, color));
				board.add(new Tile(2, color));
				board.add(new Tile(2, color));
				board.add(new Tile(3, color));
				board.add(new Tile(3, color));
				board.add(new Tile(4, color));
				board.add(new Tile(4, color));
				board.add(new Tile(5, color));
				played.put(color, 0);
				Map<Integer, Integer> map = new HashMap<Integer, Integer>();
				for (int i = 1; i <= 5; ++i) {
					map.put(i, 0);
				}
				discard.put(color, map);
			}
		}
		totalPlayableTiles = (Color.values().length + (multi ? 0 : -1)) * 5;
		players = new Player[nPlayers];
		nTiles = nPlayers < 4 ? 5 : 4;
		for (int i = 0; i < nPlayers; ++i) {
			players[i] = new Player(this, nTiles);
		}
	}

	public Tile removeTile() {
		return board.removeRandom();
	}

	public void playTile(Tile tile) {
		if (played.get(tile.color) == tile.number - 1) {
			played.put(tile.color, played.get(tile.color)+1);
			if (tile.number == 5) {
				hints++;
				int total = 0;
				for (Color allColors : played.keySet()) {
				  total += played.get(allColors);
				}
				if (totalPlayableTiles == total) {
				  System.out.println("Good job you won.");
				  System.exit(0);
				}
			}
		} else {
			System.out.println("You lost a life");
			lives--;
			if (lives == 0) {
				lose();
			}
			discardTile(tile, false);
		}
	}

	public void lose() {
		System.out.println("You lost");
		System.exit(0);
	}

	public void discardTile(Tile tile, boolean giveHint) {
		discard.get(tile.color).put(tile.number, discard.get(tile.color).get(tile.number) + 1);
		if (played.get(tile.color) < tile.number) {
			int number = discard.get(tile.color).get(tile.number);
			if ((number == 3 && tile.number == 1) || (number == 2 && (tile.number >= 2 && tile.number <= 4) || (number == 1 && tile.number == 5))) {
				lose();
			}
		}
		if (giveHint) {
		if (hints < 8) {
		hints++;
		}
		}
	}


	public void turn(String[] args) {
	  if (args == null) {
	    System.out.println("try again");
	    return;
	  }
		String move = args[0];
		if (move.equalsIgnoreCase(Move.DISCARD.toString())) {
		  if (args.length <= 1 ) {
	      System.out.println("try again");
		    return;
		  }
			int position = Integer.parseInt(args[1]);
			if (position <= 0 || position > nTiles) {
				System.out.println("Tile position should be between 1 and " + nTiles);
				return;
			}
			players[turn].discardTile(position-1);
incTurn();
		} else if (move.equalsIgnoreCase(Move.PLAY.toString())) {
		  if (args.length <= 1 ) {
	      System.out.println("try again");
      return;
    }
			int position = Integer.parseInt(args[1]);
			if (position <= 0 || position > nTiles) {
				System.out.println("Tile position should be between 1 and " + nTiles);
				return;
			}
			players[turn].playTile(position-1);
			incTurn();
		} else if (move.equalsIgnoreCase(Move.HINT.toString())) {
		  if (args.length < 4 ) {
	      System.out.println("try again");
        return;
      }
			if (hints == 0) {
				System.out.println("Out of hints");
				return;
			}
			if (args[1].equalsIgnoreCase("COLOR")) {
				try {
					Tiles.Color color = Tiles.Color.valueOf(args[2].toUpperCase());
					giveHint(Integer.valueOf(args[3])-1, color);
		      incTurn();
					hints--;
				} catch (IllegalArgumentException e) {
					System.out.println("Color " + args[2] + " is not valid color.");
					return;
				}
			} else if (args[1].equalsIgnoreCase("NUMBER")) {
				int number = Integer.valueOf(args[2]);
				giveHint(Integer.valueOf(args[3])-1, number);
				incTurn();
				hints--;
			} else {
				System.out.println("usage: hint color <color> <playerNumber>\nhint number <number> <playerNumber>\n");
			}
		} else {
			System.out.println("usage: discard <position>\nplay <position>\nhint color <color> <playerNumber>\nhint number <number> <playerNumber>\n");
		}
	}

	public void giveHint(int player, Tiles.Color color) {
checkPlayer(player);
		players[player].receiveHint(color);
	}

	public void giveHint(int player, int number) {
		checkPlayer(player);
		if (number < 1 || number > 5) {
			System.out.println("Number " + number + " not a valid number.");
			return;
		}
		players[player].receiveHint(number);
	}

	private void checkPlayer(int player) {
	   if (player < 0 || player >= players.length) {
	      System.out.println("Player " + player + " not a valid player.");
	      return;
	    } else if (player == turn) {
	      System.out.println("You can't hint to yourself dummy");
	      return;
	    }
	}
	public void print() {
	  System.out.println("------------");
		System.out.println("HINTS: " + hints + ", LIVES: " + lives + ", NUMBER OF TILES: " + board.size());
		for (int i = 0; i < players.length; ++i ) {
			System.out.println("PLAYER " + (i+1));
			for (Tile tile : players[i].getTiles()) {
				System.out.print(tile.toString(i == turn) + ", ");
			}
			System.out.println();
		}
		System.out.println("PLAYED TILES");
		for (Color color : played.keySet()) {
			System.out.print(color);
			for (int i = 1; i <= played.get(color); ++i) {
				System.out.print(" " + i) ;
			}
			System.out.println();
		}
		System.out.println("DISCARDED TILES");
		for (Color color : discard.keySet()) {
			System.out.print(color.toString());
			for (int i = 1; i <= 5; ++i) {
				if (discard.get(color).get(i) != 0) {
					System.out.print("(" + color.toString() + "," + i + "):"+discard.get(color).get(i) + " ");
				}
			}
			System.out.println();
		}
	}

	private void incTurn() {
    turn = (turn + 1) % players.length;
	}
}
