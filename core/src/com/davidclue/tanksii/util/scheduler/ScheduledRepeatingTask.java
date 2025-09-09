package com.davidclue.tanksii.util.scheduler;

public class ScheduledRepeatingTask extends ScheduledTask {
	
	private int delay, repeat, tick;

	protected ScheduledRepeatingTask(int delay, int repeat, Runnable task) {
		super(task);
		this.delay = delay;
		this.repeat = repeat;
		this.tick = repeat;
	}
	@Override
	protected void tick() {
		if (delay > 0) {
			delay--;
			return;
		}
		if (tick >= repeat) {
			task.run();
			tick = 0;
			return;
		}
		tick++;
	}
}
