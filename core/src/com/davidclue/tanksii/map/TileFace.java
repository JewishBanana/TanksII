package com.davidclue.tanksii.map;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public enum TileFace {
	UP,
	DOWN,
	LEFT,
	RIGHT,
	TOP_RIGHT,
	BOTTOM_RIGHT,
	BOTTOM_LEFT,
	TOP_LEFT;
	
	public static Set<TileFace> adjacentFaces = new HashSet<>(Arrays.asList(UP, DOWN, LEFT, RIGHT));
}
