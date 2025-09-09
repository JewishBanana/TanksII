package com.davidclue.tanksii.util;

import java.util.Collection;
import java.util.Random;
import java.util.function.Predicate;

public class Utils {
	
	private static Random rand;
	static {
		rand = new Random();
	}
	
	public static double clamp(double var, double min, double max) {
		return var < min ? min : var > max ? max : var;
	}
	@SuppressWarnings("unchecked")
	public static <T> T getRandomElement(Collection<T> collection) {
		if (collection.isEmpty())
			return null;
		return (T) collection.toArray()[rand.nextInt(collection.size())];
	}
	@SuppressWarnings("unchecked")
	public static <T> T getRandomElement(Collection<T> collection, Predicate<T> predicate) {
		if (collection.isEmpty())
			return null;
		T[] array = (T[]) collection.stream().filter(predicate).toArray();
		return array[rand.nextInt(array.length)];
	}
}
