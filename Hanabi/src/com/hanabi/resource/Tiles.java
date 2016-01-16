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
				for (int i = 1; i <= 5; ++i) {
					Map<Integer, Integer> map = new HashMap<Integer, Integer>();
					map.put(i, 0);
					discard.put(color, map);
				}
			}
		}
		players = new Player[nPlayers];
		for (int i = 0; i < nPlayers; ++i) {
			players[i] = new Player(this, nPlayers < 4 ? 5 : 4);
		}
	}
	
	public Tile removeTile() {
		return board.removeRandom();
	}
	
	public void playTile(Tile tile) {
		if (played.get(tile.color) == tile.number - 1) {
			played.put(tile.color, played.get(tile.color)+1);
		} else {
			lives--;
		}
	}
	
	public void discardTile(Tile tile) {
		discard.get(tile.color).put(tile.number, discard.get(tile.color).get(tile.number) + 1);
	}

	
	public void turn(String[] args) {
		String move = args[0];
		if (move.equals(Move.DISCARD.toString())) {
			int position = Integer.parseInt(args[1]);
			players[turn].discardTile(position);
		} else if (move.equals(Move.PLAY.toString())) {
			int position = Integer.parseInt(args[1]);
			players[turn].playTile(position);
		} else if (move.equals(Move.HINT.toString())) {
			if (args[1].equals("COLOR")) {
				Tiles.Color color = Tiles.Color.valueOf(args[2]);
				giveHint(Integer.valueOf(args[3]), color);
			} else if (args[1].equals("NUMBER")) {
				int number = Integer.valueOf(args[2]);
				giveHint(Integer.valueOf(args[3]), number);
			}
		}
	}
	
	public void giveHint(int player, Tiles.Color color) {
		players[player].receiveHint(color);
	}

	public void giveHint(int player, int number) {
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
