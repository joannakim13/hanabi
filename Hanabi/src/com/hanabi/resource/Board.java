package com.hanabi.resource;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.hanabi.type.TileType;

public class Board {

	private static Board board = null;
	private static short[] playedTiles;
	private static TileBag discardedTiles;
	private static TileBag hiddenTiles;
	private static int numPlayers;
	private static Map<Integer, Hand> playerToHand;
	
	private static short NUM_COLORS = 5; //should parameterize
	private static short NUM_NUMBERS = 5; //should parameterize
	
	private Board(int numPlayers) {
		playedTiles = new short[NUM_COLORS];
		discardedTiles = new TileBag(false);
		hiddenTiles = new TileBag(true);
		this.numPlayers = numPlayers;
		playerToHand = new HashMap<Integer, Hand>();
		for (int i = 0; i < numPlayers; i++) {
			playerToHand.put(i, new Hand());
		}
	}
	
	public static Board getInstance(int numPlayers) {
		if (board == null) {
			board = new Board(numPlayers);
		}
		return board;
	}
	
	public void applyHint(int player, TileType.Color color) {
		Tile[] tiles = playerToHand.get(player).getTiles();
		for (Tile tile : tiles) {
			if (tile.color == color) {
				tile.colorHinted = true;
			}
		}
	}
	
	public void applyHint(int player, int number) {
		Tile[] tiles = playerToHand.get(player).getTiles();
		for (Tile tile : tiles) {
			if (tile.number == number) {
				tile.numberHinted = true;
			}
		}
	}
	
	public void playTile(Tile tile) throws PlayException {
		if (playedTiles[BoardConf.colorToInt.get(tile.color)] == tile.number - 1) {
			playedTiles[BoardConf.colorToInt.get(tile.color)]++;
		} else {
			throw new PlayException(tile);
		}
	}
	
	public Tile drawTile() {//maybe throw at tilebag levelthrows NoTilesLeftException
		return hiddenTiles.removeTile();
		//return null; // shoudl draw a random tile frmo hidden tiles, return
		//edge case end of game, no tiles left, then discard is still ok
	}
	
	public void discardTile(Tile discardedTile) {
		discardedTiles.addTile(discardedTile);
	}
	
	public static int getNumPlayers() {
		return numPlayers;
	}
	
	class TileBag {
		
		private Map<Integer, Tile> tiles = new HashMap<Integer, Tile>();
		private Random random = new Random();

		public TileBag(boolean generateTiles) {
			int count = 0;
			if (generateTiles) {
				for (TileType.Color color : TileType.Color.values()) {
					for (int i = 0; i < 3; i++) {
						tiles.put(count++, new Tile(1, color));
					}
					for (int i = 0; i < 2; i++) {
						tiles.put(count++, new Tile(2, color));
						tiles.put(count++, new Tile(3, color));
						tiles.put(count++, new Tile(4, color));
					}
					tiles.put(count++, new Tile(5, color));
				}
			}
		}
		
		public void addTile(Tile tile) {
			tiles.put(tiles.size(), tile);
		}
		
		public Tile removeTile() {//handle empty bag
			int idx = random.nextInt(tiles.size());
			Tile drawnTile = tiles.get(idx);
			tiles.put(idx, tiles.get(tiles.size() - 1));
			tiles.remove(tiles.size() - 1);
			return drawnTile;
		}
	}
	
	class PlayException extends Exception {
		public PlayException(Tile tile) {
			super(tile + " cannot be played on the board");
		}
	}
	
	class NoTilesLeftException extends Exception {
		public NoTilesLeftException() {
			super("No more tiles to draw!");
		}
	}
}
