package com.davidclue.tanksii.util;

import java.util.Arrays;
import java.util.Random;

import com.davidclue.tanksii.Level;
import com.davidclue.tanksii.entities.Entity;

public class AIUtil {
	
	private static Random rand = new Random();
	
	public static Entity getRandomTarget(Level level) {
		return level.getTargets().isEmpty() ? null : (Entity) Arrays.asList(level.getTargets().toArray()).get(rand.nextInt(level.getTargets().size()));
	}
}
