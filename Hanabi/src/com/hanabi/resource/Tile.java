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
		if (color == colorHint) {
		  colorsHinted.add(colorHint);
		}
	}

	@Override
	public String toString() {
		return "(" + number	 + "," + color + ")";
	}

	public String toString(boolean isYourTurn) {
	  if (isYourTurn) {
	    String colors = "?";
	    if (colorsHinted.size() > 0) {
	      colors = colorsHinted.toString();
	    }
	    String numbers = "?";
	    if (numberHinted) {
	      numbers = String.valueOf(number);
	    }
	    return "(" + colors + "," + numbers + ")";
	  } else {
	    return toString();
	  }
	}
}
