package com.davidclue.tanksii;

import java.io.File;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import com.davidclue.tanksii.scenes.LevelScene;

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
	
	public static int WIDTH = 1600, HEIGHT = 800;
	public static int tileSize = 64;
	
	private Scene scene;
	
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
		
		orthoCam = new OrthographicCamera();
		orthoCam.setToOrtho(true);
		batch.setProjectionMatrix(orthoCam.combined);
		
		new InputHandler(this);
		scene = new LevelScene("levels/default");
		
		Renderer.init(drawer);
	}
	private void tick() {
		if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
			if (Gdx.graphics.isFullscreen())
				Gdx.graphics.setWindowedMode(1600, 800);
			else
				Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
//			WIDTH = Gdx.graphics.getWidth();
//			HEIGHT = Gdx.graphics.getHeight();
		}
		scene.tick();
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
		
		scene.render(drawer);
		
		batch.end();
		frames++;
	}
	@Override
	public void dispose() {
		batch.dispose();
		texture.dispose();
	}
	public static void getAllFiles(File curDir) {
        File[] filesList = curDir.listFiles();
        for(File f : filesList){
            if(f.isDirectory())
                getAllFiles(f);
            if(f.isFile()){
                System.err.println(f.getName());
            }
        }
    }
}
