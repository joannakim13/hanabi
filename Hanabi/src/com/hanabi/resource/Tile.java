package com.hanabi.resource;

import java.util.HashSet;
import java.util.Set;

public class Tile {

	public int number;
	public Tiles.Color color;
	private boolean numberHinted = false;
	private Set<Tiles.Color> colorsHinted = new HashSet<Tiles.Color>();
	
	public Tile(int n, Tiles.Color c) {
		number = n;
		color = c;
	}
	
	public void numberHint() {
		numberHinted = true;
	}
	
	public void colorHint(Tiles.Color colorHint) {
		colorsHinted.add(colorHint);
	}
	
	@Override
	public String toString() {
		return "(" + number	 + "," + color + ")";
	}
} 
