package com.davidclue.tanksii.entities.tanks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.davidclue.tanksii.Level;
import com.davidclue.tanksii.entities.LivingEntity;
import com.davidclue.tanksii.entities.projectiles.Projectile;
import com.davidclue.tanksii.input.InputAdapter;
import com.davidclue.tanksii.input.InputHandler;
import com.davidclue.tanksii.rendering.Renderer;
import com.davidclue.tanksii.util.Utils;
import com.davidclue.tanksii.util.Velocity;

import space.earlygrey.shapedrawer.ShapeDrawer;

public class Player extends LivingEntity implements Shooter, InputAdapter {
	
	private double gunAngle;
	private int stallTicks,shotCooldown;
	private boolean colors;

	public Player(Level level, double x, double y) {
		super(level, x, y);
		this.movementSpeed = 5.0;
		this.lighting = 0xF000;
	}
	@Override
	public void tick() {
		move();
		moveNozzle();
		
		//cam changing
		if (Gdx.input.isKeyJustPressed(Input.Keys.T))
			this.level.camera.setTrackingObject(Utils.getRandomElement(this.level.getEntities(), e -> e instanceof LivingEntity && !e.isDead()));
		if (Gdx.input.isKeyJustPressed(Input.Keys.Y))
			this.level.camera.setTrackingObject(this);
		if (Gdx.input.isKeyJustPressed(Input.Keys.U))
			this.setImmune(!this.isImmune());
		if (Gdx.input.isKeyJustPressed(Input.Keys.I))
			this.colors = !colors;
		if (colors) {
			if (this.lighting >= 0xFFFF)
				changeLighting(0xF000);
			if (Gdx.input.isKeyJustPressed(Input.Keys.O)) {
				changeLighting(this.lighting + 1);
				updateLight(true);
			}
		}
		if (Gdx.input.isKeyPressed(Input.Keys.MINUS))
			this.level.camera.setZoom(this.level.camera.getZoom() - 0.005);
		if (Gdx.input.isKeyPressed(Input.Keys.EQUALS))
			this.level.camera.setZoom(this.level.camera.getZoom() + 0.005);
		if (Gdx.input.isKeyJustPressed(Input.Keys.P))
			Renderer.renderLighting = !Renderer.renderLighting;
		
		shotCooldown--;
		removeDeadProjectiles();
		
		super.tick();
	}
	@Override
	public void render(ShapeDrawer drawer) {
		if (this.isImmune())
			Renderer.renderRectangle(x, y, width, height, Color.NAVY);
		else
			Renderer.renderRectangle(x, y, width, height, Color.WHITE);
		Renderer.renderRectangle(x+(width/2)-30, y+(height/2)-10, 60, 20, Color.CORAL, gunAngle);
	}
//	@Override
//	public void render(aef) {
//		
//		g.setColor(Color.white);
//		g.fillRect((int)x, (int)y, (int)width, (int)height);
//		
//		Graphics2D g2d = (Graphics2D) g.create();
//		Rectangle rect2 = new Rectangle((int) (x+(width/2)), (int) (y+(height/3)), 60, 20);
//	    g2d.rotate(gunAngle, (int) (x+(width/2)), (int) (y+(height/2)));
//		g2d.setColor(Color.magenta);
//		g2d.draw(rect2);
//		g2d.fill(rect2);
//		g2d.dispose();
//	}
	private void move() {
		if (stallTicks-- > 0)
			return;
		if (Gdx.input.isKeyPressed(Input.Keys.D)) velocity.setX(velocity.getX()+acceleration);
		else if (Gdx.input.isKeyPressed(Input.Keys.A)) velocity.setX(velocity.getX()-acceleration);
		else if (!(Gdx.input.isKeyPressed(Input.Keys.D)) && !Gdx.input.isKeyPressed(Input.Keys.A)) {
			if (velocity.getX() > 0) velocity.setX(velocity.getX()-decceleration);
			else if (velocity.getX() < 0) velocity.setX(velocity.getX()+decceleration);
		}
		
		if (Gdx.input.isKeyPressed(Input.Keys.S)) velocity.setY(velocity.getY()+acceleration);
		else if (Gdx.input.isKeyPressed(Input.Keys.W)) velocity.setY(velocity.getY()-acceleration);
		else if (!(Gdx.input.isKeyPressed(Input.Keys.W)) && !Gdx.input.isKeyPressed(Input.Keys.S)) {
			if (velocity.getY() > 0) velocity.setY(velocity.getY()-decceleration);
			else if (velocity.getY() < 0) velocity.setY(velocity.getY()+decceleration);
		}
		
		velocity.setX(Utils.clamp(velocity.getX(), -movementSpeed, movementSpeed));
		velocity.setY(Utils.clamp(velocity.getY(), -movementSpeed, movementSpeed));
	}
	public void mousePressed(int button, int mouseX, int mouseY) {
//		if (button == 1) {
//			Tile tile = level.getTile(level.camera.getLevelX(mouseX), level.camera.getLevelY(mouseY));
//			level.handler.addParticle(new Particle(level, tile.getX()+32, tile.getY()+32, 15, 15, 120));
//			tile.setType(TileType.BOXTILE);
//		} else {
//			Tile tile = level.getTile(level.camera.getLevelX(mouseX), level.camera.getLevelY(mouseY));
//			level.handler.addParticle(new Particle(level, tile.getX()+32, tile.getY()+32, 15, 15, 120));
//			tile.setType(TileType.FLOORTILE);
//		}
		if (button != 0 || shotCooldown > 0 || aliveProjectiles() >= 5 || isDead())
			return;
		System.out.println(aliveProjectiles());
		shotCooldown = 20;
		Projectile tempBullet = new Projectile(level, x+(width/2)-4, y+(height/2)-4, 8, 8, this);
		double angle = Math.atan2(mouseY - level.camera.getScreenY(y+height/2), mouseX - level.camera.getScreenX(x+width/2));
		tempBullet.setVelocity(new Velocity(Math.cos(angle), Math.sin(angle)).normalize().multiply(10));
		tempBullet.setBounces(2);
		tempBullet.setDamage(bulletDamage);
		level.handler.addObject(tempBullet);
		projectiles.add(tempBullet);
		stallTicks = 20;
		this.setVelocity(new Velocity());
	}
	private void moveNozzle() {
		if (shotCooldown > 0)
			return;
		gunAngle = Math.atan2(InputHandler.getMouseY() - level.camera.getScreenY(y+height/2), InputHandler.getMouseX() - level.camera.getScreenX(x+width/2));
	}
	@Override
	public void mouseMoved() {
	}
	@Override
	public void removeMouseListener() {
		InputHandler.mouseListener.remove(this);
	}
}
