package com.davidclue.tanksii.util.scheduler;

public class ScheduledDelayedTask extends ScheduledTask {
	
	private int delay;

	protected ScheduledDelayedTask(int delay, Runnable task) {
		super(task);
		this.delay = delay;
	}
	@Override
	protected void tick() {
		if (delay-- <= 0) {
			this.task.run();
			this.cancelled = true;
		}
	}
}
