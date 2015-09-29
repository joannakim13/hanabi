package com.hanabi.resource;

import com.hanabi.type.TileType;

public class Tile {

	public int number;
	public TileType.Color color;
	
	public Tile(int n, TileType.Color c) {
		number = n;
		color = c;
	}
	
	@Override
	public String toString() {
		return "(" + number + ", " + color + ")";
	} 
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + number;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tile other = (Tile) obj;
		if (color != other.color)
			return false;
		if (number != other.number)
			return false;
		return true;
	}
} 
