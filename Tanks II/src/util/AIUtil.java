package util;

import java.util.Arrays;
import java.util.Random;

import entities.GameObject;
import general.Level;

public class AIUtil {
	
	private static Random rand = new Random();
	
	public static GameObject getRandomTarget(Level level) {
		return level.getTargets().isEmpty() ? null : (GameObject) Arrays.asList(level.getTargets().toArray()).get(rand.nextInt(level.getTargets().size()));
	}
}
