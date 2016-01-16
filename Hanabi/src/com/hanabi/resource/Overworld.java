package com.hanabi.resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Overworld {

	public static void main(String[] args) {
		int nPlayers = Integer.parseInt(args[0]);
		boolean multi = Boolean.parseBoolean(args[1]);
		Tiles tiles = new Tiles(multi, nPlayers);
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in));
		try {
			String line = "";
			while (line != null) {
				tiles.print();
				line = reader.readLine();
				tiles.turn(line.split("\\s+"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
