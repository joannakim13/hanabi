package com.hanabi.resource;

import java.util.ArrayList;
import java.util.List;

public class Player {

	private List<Tile> tiles = new ArrayList<Tile>();
	private Tiles theboard;
	
	public Player(Tiles theboard, int nTiles) {
		this.theboard = theboard;
		for (int i = 0; i < nTiles; ++i) {
			tiles.add(theboard.removeTile());
		}
	}
	
	private void drawTile() {
		tiles.add(theboard.removeTile());
	}
	
	public void playTile(int position) {
		theboard.playTile(tiles.get(position));
		tiles.remove(position);
		drawTile();
	}
	
	public void swap(int position1, int position2) {
		Tile tmp = tiles.get(position1);
		tiles.set(position1, tiles.get(position2));
		tiles.set(position2, tmp);
	}
	
	public void discardTile(int position) {
		theboard.discardTile(tiles.get(position));
		tiles.remove(position);
		drawTile();
	}
	public void receiveHint(Tiles.Color color) {
		for (Tile tile : tiles) {
				tile.colorHint(color);
			
		}
	}
	
	
	public void receiveHint(int number) {
		for (Tile tile : tiles) {
			if (tile.number == number) {
				tile.numberHint();
			}
			
		}
	}
	
	public List<Tile> getTiles() {
		return tiles;
	}
	
	enum Move {
		HINT, DISCARD, PLAY
	}
	
}
