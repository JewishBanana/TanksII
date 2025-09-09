package com.davidclue.tanksii.util;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

import com.davidclue.tanksii.Level;
import com.davidclue.tanksii.entities.Entity;
import com.davidclue.tanksii.map.Tile;
import com.davidclue.tanksii.map.TileFace;
import com.davidclue.tanksii.map.TileType;

public class AIUtil {
	
	private static Random rand = new Random();
	
	public static Entity getRandomTarget(Level level) {
		return level.getTargets().isEmpty() ? null : (Entity) Arrays.asList(level.getTargets().toArray()).get(rand.nextInt(level.getTargets().size()));
	}
	private static boolean isTileInvalid(Tile tile, Set<Tile> closed) {
		return tile.getType() == TileType.VOIDTILE || tile.isSolid() || closed.contains(tile);
	}
	public static Queue<Tile> pathFind(Tile start, Tile end) {
		Set<Tile> closed = new HashSet<>();
		Queue<Details> open = new PriorityQueue<>((o1, o2) -> (int) Math.round(o1.value - o2.value));
		open.add(new Details(start, 0.0));
		Map<Tile, TileCell> cellMap = new HashMap<>();
		while (!open.isEmpty()) {
			Details details = open.poll();
			closed.add(details.tile);
			for (TileFace face : TileFace.values()) {
				Tile tile = details.tile.getRelative(face);
				if (isTileInvalid(tile, closed))
					continue;
				switch (face) {
				case TOP_LEFT:
					if (isTileInvalid(tile.getRelative(TileFace.UP), closed) && isTileInvalid(tile.getRelative(TileFace.LEFT), closed))
						continue;
					break;
				case TOP_RIGHT:
					if (isTileInvalid(tile.getRelative(TileFace.UP), closed) && isTileInvalid(tile.getRelative(TileFace.RIGHT), closed))
						continue;
					break;
				case BOTTOM_RIGHT:
					if (isTileInvalid(tile.getRelative(TileFace.DOWN), closed) && isTileInvalid(tile.getRelative(TileFace.RIGHT), closed))
						continue;
					break;
				case BOTTOM_LEFT:
					if (isTileInvalid(tile.getRelative(TileFace.DOWN), closed) && isTileInvalid(tile.getRelative(TileFace.LEFT), closed))
						continue;
					break;
				default:
					break;
				}
				TileCell cell = cellMap.get(tile);
				if (cell == null) {
					cell = new TileCell(details.tile, -1, -1);
					cellMap.put(tile, cell);
				}
				if (tile.equals(end)) {
					cell.parent = details.tile;
					Stack<Tile> path = new Stack<>();
					path.push(tile);
					while (true) {
						path.push(cell.parent);
						cell = cellMap.get(cell.parent);
						if (cell == null || cell.parent.equals(start))
							break;
					}
					Queue<Tile> result = new ArrayDeque<>();
					while (!path.isEmpty())
						result.add(path.pop());
					return result;
				}
				double g = cell.g + (TileFace.adjacentFaces.contains(face) ? 1.0 : 1.4);
				double f = cell.g + g + Math.hypot(tile.getTileX() - end.getTileX(), tile.getTileY() - end.getTileY());
				if (cell.f == -1 || cell.f > f) {
					cell.f = f;
					cell.g = g;
					cell.parent = details.tile;
					open.add(new Details(tile, f));
				}
			}
		}
		return null;
	}
	private static class TileCell {
		private Tile parent;
		private double f, g;
		
		private TileCell(Tile tile, double f, double g) {
			this.parent = tile;
			this.f = f;
			this.g = g;
		}
	}
	private static class Details {
		private Tile tile;
		private double value;
		
		private Details(Tile tile, double value) {
			this.tile = tile;
			this.value = value;
		}
	}
}
