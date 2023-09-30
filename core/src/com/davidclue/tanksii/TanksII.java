package com.davidclue.tanksii;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;
import com.davidclue.tanksii.input.InputHandler;
import com.davidclue.tanksii.rendering.Renderer;

import space.earlygrey.shapedrawer.ShapeDrawer;

public class TanksII extends ApplicationAdapter {
	
	long lastTime = System.nanoTime();
	double amountOfTicks = 60.0;
	double ns = 1000000000 / amountOfTicks;
	double delta = 0;
	long timer = System.currentTimeMillis();
	int updates = 0;
	int frames;
	
	private PolygonSpriteBatch batch;
	private ShapeDrawer drawer;
	private Texture texture;
	private OrthographicCamera orthoCam;
	
	public static int WIDTH = 1600, HEIGHT = 900;
	public int fullWidth, fullHeight;
	public static int tileSize = 64;
	public String title = "Tanks II";
	
	public static Handler handler;
	private Level level;
	
	@Override
	public void create() {
		batch = new PolygonSpriteBatch();
		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
	    pixmap.setColor(Color.WHITE);
	    pixmap.fill();
	    texture = new Texture(pixmap);
	    pixmap.dispose();
	    TextureRegion region = new TextureRegion(texture);
		drawer = new ShapeDrawer(batch, region);
		Renderer.drawer = this.drawer;
		
		orthoCam = new OrthographicCamera();
		orthoCam.setToOrtho(true);
		batch.setProjectionMatrix(orthoCam.combined);
		
		handler = new Handler();
		new Camera(0, 0);
		new InputHandler(this);
		level = new Level(this, handler);
		handler.setLevel(level);
		
		level.load("levels/default/level1.png");
	}
	private void tick() {
		handler.tick();
		Camera.tick();
	}
	@Override
	public void render() {
		long now = System.nanoTime();
		delta += (now - lastTime) / ns;
		lastTime = now;
		while (delta >= 1) {
			tick();
			updates++;
			delta--;
		}
		if (System.currentTimeMillis() - timer > 1000) {
			timer += 1000;
			System.out.println("FPS: " + frames + " TICKS: " + updates);
			frames = 0;
			updates = 0;
		}
		
		// render
		
		ScreenUtils.clear(0, 0, 0, 1);
		batch.begin();
		
		level.render(drawer);
		handler.render(drawer);
		
		batch.end();
		frames++;
	}
	@Override
	public void dispose() {
		batch.dispose();
		texture.dispose();
	}
}
