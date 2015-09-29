package com.hanabi.resource;

import java.util.Collection;

public class Board {

	private static Board board = null;
	private static short[] playedTiles;
	private static Collection<Tile> discardedTiles;
	private static Collection<Tile> hiddenTiles;
	
	private static short NUM_COLORS = 5; //should parameterize
	private static short NUM_NUMBERS = 5; //should parameterize
	
	private Board() {
		playedTiles = new short[NUM_COLORS];
	}
	
	public Board getInstance() {
		if (board == null) {
			board = new Board();
		}
		return board;
	}
	
	public void playTile(Tile tile) throws PlayException {
		if (playedTiles[BoardConf.colorToInt.get(tile.color)] == tile.number - 1) {
			playedTiles[BoardConf.colorToInt.get(tile.color)]++;
		} else {
			throw new PlayException(tile);
		}
	}
	
	public Tile drawTile() throws NoTilesLeftException {
		//if no tiles left, throw exception
		return null; // shoudl draw a random tile frmo hidden tiles, return
	}
	
	public Tile discardTile(Tile discardedTile) {
		//add discardedtile to discardedtiles
		return drawTile();//edge case end of game, no tiles left, then discard is still ok
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
