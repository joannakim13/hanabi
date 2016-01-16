package com.hanabi.resource;

import java.util.ArrayList;
import java.util.List;

public class Player {

	private List<Tile> tiles = new ArrayList<Tile>();
	private Tiles theboard;
	
	public Player(Tiles theboard) {
		this.theboard = theboard;
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
	
	public void giveHint(Player player, Tiles.Color color) {
		player.receiveHint(color);
	}

	public void giveHint(Player player, int number) {
		player.receiveHint(number);
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
	
	enum Move {
		HINT, DISCARD, PLAY
	}
	
	public void turn(String move, String[] args) {
		if (move.equals(Move.DISCARD.toString())) {
			int position = Integer.parseInt(args[0]);
		} else if (move.equals(Move.PLAY.toString())) {
			int position = Integer.parseInt(args[0]);
		} else if (move.equals(Move.HINT.toString())) {
			if (args[1].equals("COLOR")) {
				
			}
		}
	}
	
}
