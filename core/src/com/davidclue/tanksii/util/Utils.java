package com.davidclue.tanksii.util;

public class Utils {
	
	public static double clamp(double var, double min, double max) {
		return var < min ? min : var > max ? max : var;
	}
}
