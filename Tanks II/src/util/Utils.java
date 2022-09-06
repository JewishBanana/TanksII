package util;

public class Utils {
	
	public static double clamp(double var, double min, double max) {
		if (var >= max) var = max;
		else if (var <= min) var = min;
		return var;
	}
}
