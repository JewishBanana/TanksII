package com.davidclue.tanksii.scenes;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.davidclue.tanksii.Level;
import com.davidclue.tanksii.Scene;
import com.davidclue.tanksii.TanksII;
import com.davidclue.tanksii.input.InputHandler;
import com.davidclue.tanksii.rendering.Renderer;
import com.davidclue.tanksii.util.scheduler.TaskScheduler;

import space.earlygrey.shapedrawer.ShapeDrawer;

public class LevelScene extends Scene {
	
	public List<Level> levelList = new ArrayList<>();
	public Level level;
	public int currentLevel = 1;
	private boolean frozen, blackScreen;
	
	private TaskScheduler scheduler;
	
	public LevelScene(String levelPath) {
		this.scheduler = new TaskScheduler();
		int index = 1;
		while (true) {
			FileHandle handle = Gdx.files.classpath(levelPath+"/level"+index+".png");
			if (!handle.exists())
				break;
			InputStream stream = handle.read();
			Level lvl = new Level();
			try {
				lvl.load(stream);
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println("Error trying to load level "+index+'!');
				index++;
				continue;
			}
			levelList.add(lvl);
			index++;
		}
		if (levelList.isEmpty())
			throw new NullPointerException("There is no starting level!");
		level = levelList.get(currentLevel-1);
		Renderer.setCamera(level.camera);
		InputHandler.mouseListener.add(level.client);
	}
	@Override
	public void tick() {
		scheduler.tick();
		if (level.hasFailed) {
			if (!frozen) {
				frozen = true;
				scheduler.scheduleDelayedTask(40, () -> {
					blackScreen = true;
					level.client.removeMouseListener();
					level.reset();
					InputHandler.mouseListener.add(level.client);
					level.camera.tick();
					scheduler.scheduleDelayedTask(60, () -> {
						blackScreen = false;
						scheduler.scheduleDelayedTask(40, () -> {
							frozen = false;
							level.hasFailed = false;
						});
					});
				});
			}
			return;
		}
		if (level.isCompleted) {
			if (!frozen) {
				frozen = true;
				scheduler.scheduleDelayedTask(40, () -> {
					blackScreen = true;
					if (levelList.size() > currentLevel) {
						level.client.removeMouseListener();
						level = levelList.get(currentLevel);
						currentLevel++;
						level.camera.tick();
						Renderer.setCamera(level.camera);
						InputHandler.mouseListener.add(level.client);
					}
					scheduler.scheduleDelayedTask(60, () -> {
						blackScreen = false;
						scheduler.scheduleDelayedTask(40, () -> {
							frozen = false;
						});
					});
				});
			}
			return;
		}
		level.tick();
	}
	@Override
	public void render(ShapeDrawer drawer) {
		if (blackScreen) {
			drawer.filledRectangle(0, 0, TanksII.WIDTH, TanksII.HEIGHT, Color.BLACK);
			return;
		}
		level.render(drawer);
	}
}
