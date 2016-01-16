package com.hanabi.resource;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Tiles {

	enum Color {
		RED, YELLOW, GREEN, BLUE, WHITE, MULTI
	}

	private SetRandom<Tile> board = new SetRandom<>();
	private Map<Color, Integer> played = new HashMap<>();
	private Map<Color, Map<Integer, Integer>> discard = new HashMap<>();
	private int lives = 3;

	public Tiles(boolean multi) {
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
}
