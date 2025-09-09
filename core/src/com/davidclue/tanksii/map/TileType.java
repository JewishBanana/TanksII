package com.davidclue.tanksii.map;

import java.lang.reflect.InvocationTargetException;

import com.davidclue.tanksii.Level;
import com.davidclue.tanksii.TanksII;
import com.davidclue.tanksii.entities.Entity;
import com.davidclue.tanksii.entities.EntityType;
import com.davidclue.tanksii.map.tiles.BoxTile;
import com.davidclue.tanksii.map.tiles.FloorTile;
import com.davidclue.tanksii.map.tiles.VoidTile;

public enum TileType {
	
	VOIDTILE(0xFF000000, VoidTile.class),
	BOXTILE(0xFF7F0000, BoxTile.class),
	FLOORTILE(0xFF00FF0C, FloorTile.class),
	
	MAINPLAYER(0xFF0094FF, EntityType.PLAYER),
	BROWNTANK(0xFFFF0AD2, EntityType.BROWNTANK),
	GRAYTANK(0xFF404040, EntityType.GRAYTANK),
	GREENTANK(0xFF003F0A, EntityType.GREENTANK);
	
	private int hex;
	private Class<? extends Tile> tileClass;
	private EntityType entityType;
	
	private TileType(int hex, Class<? extends Tile> tileClass) {
		this.hex = hex;
		this.tileClass = tileClass;
	}
	private TileType(int hex, EntityType entityType) {
		this.hex = hex;
		this.entityType = entityType;
	}
	public Tile createNewTile(Level level, int tileX, int tileY) {
		if (tileClass == null)
			throw new NullPointerException("TileType "+this.toString()+" has no defined tile class!");
		try {
			Tile tile = tileClass.getDeclaredConstructor(Level.class, int.class, int.class).newInstance(level, tileX, tileY);
			tile.type = this;
			return tile;
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}
	public Entity createNewEntity(Level level, int tileX, int tileY) {
		if (entityType == null)
			throw new NullPointerException("TileType "+this.toString()+" has no defined entity class!");
		return entityType.createNewEntity(level, tileX * TanksII.tileSize, tileY * TanksII.tileSize);
	}
	public static TileType getTypeByHex(int hex) {
		for (TileType type : values())
			if (type.getHex() == hex)
				return type;
		return TileType.VOIDTILE;
	}
	public boolean isEntity() {
		return entityType != null;
	}
	public int getHex() {
		return hex;
	}
	public void setHex(int hex) {
		this.hex = hex;
	}
}
