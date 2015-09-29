package com.hanabi.resource;

public class Hand {

	public static final short NUM_TILES = 4;
	public static Board board = Board.getInstance(Board.getNumPlayers());
	
	private Tile[] tiles;
	
	public Hand() {
		tiles = new Tile[NUM_TILES];
		for (int i = 0; i < NUM_TILES; i++) {
			tiles[i] = board.drawTile();
		}
	}
	
	public Tile[] getTiles() {
		return tiles;
	}
	
	public void playTile(int play, int place) {
		board.playTile(tiles[play]);
		replaceTile(play, place);
	}
	
	public void discardTile(int play, int place) {
		board.discardTile(tiles[play]);
		replaceTile(play, place);
	}
	
	private void replaceTile(int play, int place) {
		Tile tile = board.drawTile();
		if (play > place) {
			for (int i = play; i > place; i++) {
				tiles[i] = tiles[i-1];
			}
		} else if (play < place) {
			for (int i = play; i < place; i++) {
				tiles[i] = tiles[i+1];
			}
		}
		tiles[place] = tile;
	}
}
