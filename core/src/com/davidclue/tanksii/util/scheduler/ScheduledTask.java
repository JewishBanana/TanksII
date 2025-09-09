package com.davidclue.tanksii.util.scheduler;

public abstract class ScheduledTask {

	protected Runnable task;
	protected boolean cancelled;
	
	public ScheduledTask(Runnable task) {
		this.task = task;
	}
	protected abstract void tick();
}
