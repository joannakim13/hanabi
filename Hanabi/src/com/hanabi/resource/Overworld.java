package com.hanabi.resource;

import java.util.ArrayList;
import java.util.List;

public class Overworld {
	
	private static List<Player> players = new ArrayList<Player>();
	
	public static void main(String[] args) {
		int nPlayers = Integer.parseInt(args[1]);
		Tiles tiles = new Tiles(false);
		for (int i = 0; i < nPlayers; ++i) {
			players.add(new Player(tiles));
		}
	}
}
