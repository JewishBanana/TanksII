package objects;

public enum TileType {
	
	VOIDTILE(0xFF000000),
	BOXTILE(0xFF7F0000),
	FLOORTILE(0xFF00FF0C),
	MAINPLAYER(0xFF0094FF),
	BASICENEMY(0xFFFF0AD2);
	
	private int hex;
	
	private TileType(int hex) {
		this.hex = hex;
	}
	public int getHex() {
		return hex;
	}
	public void setHex(int hex) {
		this.hex = hex;
	}
	public static TileType getTypeByHex(int hex) {
		for (TileType type : values())
			if (type.getHex() == hex)
				return type;
		return TileType.VOIDTILE;
	}
}
