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
		} else {
			System.out.println("You lost a life");
			lives--;
			discardTile(tile);
		}
	}
	
	public void discardTile(Tile tile) {
		discard.get(tile.color).put(tile.number, discard.get(tile.color).get(tile.number) + 1);
	}

	
	public void turn(String[] args) {
		String move = args[0];
		if (move.equalsIgnoreCase(Move.DISCARD.toString())) {
			int position = Integer.parseInt(args[1]);
			if (position <= 0 || position > nTiles) {
				System.out.println("Tile position should be between 1 and " + nTiles);
				return;
			}
			players[turn++].discardTile(position-1);
		} else if (move.equalsIgnoreCase(Move.PLAY.toString())) {
			int position = Integer.parseInt(args[1]);
			if (position <= 0 || position > nTiles) {
				System.out.println("Tile position should be between 1 and " + nTiles);
				return;
			}
			players[turn++].playTile(position-1);
		} else if (move.equalsIgnoreCase(Move.HINT.toString())) {
			if (args[1].equalsIgnoreCase("COLOR")) {
				try {
					Tiles.Color color = Tiles.Color.valueOf(args[2]);
					giveHint(Integer.valueOf(args[3]), color);
					turn++;
				} catch (IllegalArgumentException e) {
					System.out.println("Color " + args[2] + " is not valid color.");
					return;
				}
			} else if (args[1].equalsIgnoreCase("NUMBER")) {
				int number = Integer.valueOf(args[2]);
				giveHint(Integer.valueOf(args[3]), number);
				turn++;
			} else {
				System.out.println("usage: hint color <color> <playerNumber>\nhint number <number> <playerNumber>\n");
			}
		} else {
			System.out.println("usage: discard <position>\nplay <position>\nhint color <color> <playerNumber>\nhint number <number> <playerNumber>\n");
		}
	}
	
	public void giveHint(int player, Tiles.Color color) {
		if (player <= 0 || player > players.length) {
			System.out.println("Player " + player + " not a valid player.");
			return;
		}
		players[player].receiveHint(color);
	}

	public void giveHint(int player, int number) {
		if (player <= 0 || player > players.length) {
			System.out.println("Player " + player + " not a valid player.");
			return;
		}
		if (number < 1 || number > 5) {
			System.out.println("Number " + number + " not a valid number.");
			return;
		}
		players[player].receiveHint(number);
	}
		
	public void print() {
		System.out.println("HINTS: " + hints + ", LIVES: " + lives);
		for (int i = 0; i < players.length; ++i ) {
			System.out.println("PLAYER " + i);
			for (Tile tile : players[i].getTiles()) {
				System.out.print(tile + ", ");
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
	} 
}
