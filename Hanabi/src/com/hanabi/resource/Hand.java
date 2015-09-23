package com.hanabi.resource;

public class Hand {

	public static final short NUM_TILES = 4;
	
	private Tile[] tiles;
	
	public Hand(Tile[] tiles) {
		System.arraycopy(tiles, 0, this.tiles, 0, NUM_TILES);
	}
}
