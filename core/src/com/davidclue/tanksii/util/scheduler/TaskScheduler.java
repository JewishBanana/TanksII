package com.davidclue.tanksii.util.scheduler;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;

public class TaskScheduler {

	private Queue<ScheduledTask> tasks = new ArrayDeque<>();
	
	public void tick() {
		Iterator<ScheduledTask> it = tasks.iterator();
		while (it.hasNext()) {
			ScheduledTask task = it.next();
			if (task.cancelled) {
				it.remove();
				continue;
			}
			task.tick();
		}
	}
	public void scheduleDelayedTask(int delay, Runnable task) {
		tasks.add(new ScheduledDelayedTask(delay, task));
	}
	public void scheduleRepeatingTask(int delay, int repeat, Runnable task) {
		tasks.add(new ScheduledRepeatingTask(delay, repeat, task));
	}
}
