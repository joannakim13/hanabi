package com.hanabi.resource;

import java.util.HashMap;
import java.util.Map;

import com.hanabi.type.TileType.Color;

public class BoardConf {

	private static BoardConf boardConfInstance = null;
	public static Map<Color, Integer> colorToInt = new HashMap<Color, Integer>();
	
	private BoardConf() {
		int count = 0;
		for (Color color : Color.values()) {
			colorToInt.put(color, count++);
		}
	}
	
	public BoardConf getInstance() {
		if (boardConfInstance == null) {
			boardConfInstance = new BoardConf();
		}
		return boardConfInstance;
	}
}