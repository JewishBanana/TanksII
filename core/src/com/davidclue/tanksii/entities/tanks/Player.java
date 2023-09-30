package com.davidclue.tanksii.entities.tanks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.davidclue.tanksii.Camera;
import com.davidclue.tanksii.Level;
import com.davidclue.tanksii.TanksII;
import com.davidclue.tanksii.entities.ID;
import com.davidclue.tanksii.entities.InputGameObject;
import com.davidclue.tanksii.entities.projectiles.Projectile;
import com.davidclue.tanksii.input.InputHandler;
import com.davidclue.tanksii.rendering.Renderer;
import com.davidclue.tanksii.util.Utils;
import com.davidclue.tanksii.util.Velocity;

import space.earlygrey.shapedrawer.ShapeDrawer;

public class Player extends InputGameObject implements Shooter {
	
	private double gunAngle;
	private int stallTicks,shotCooldown;

	public Player(Level level, double x, double y, double width, double height, ID id) {
		super(level, x, y, width, height, id);
	}

	@Override
	public void tick() {
		super.tick();
		move();
		moveNozzle();
		
		shotCooldown--;
		removeDeadProjectiles();
	}
	@Override
	public void render(ShapeDrawer drawer) {
		Renderer.renderRectangle(x, y, width, height, Color.WHITE);
		drawer.setColor(Color.CORAL);
		drawer.filledRectangle(new Rectangle((float)(Camera.getFixedX(x)+(width/2)+30), (float)(Camera.getFixedY(y)+(height/2)), 60, 20).setCenter((float)(Camera.getFixedX(x)+(width/2)), (float)(Camera.getFixedY(y)+(height/2))), (float) gunAngle, Color.CORAL, Color.CORAL);
//		Renderer.renderRectangle(x+(width/2), y+(height/3), 60, 20, Color.CORAL, (float)gunAngle);
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
		
		velocity.setX(Utils.clamp(velocity.getX(), -5, 5));
		velocity.setY(Utils.clamp(velocity.getY(), -5, 5));
	}
	public void mousePressed(int button, int mouseX, int mouseY) {
		if (button != 0 || shotCooldown > 0 || aliveProjectiles() >= 5 || dead)
			return;
		shotCooldown = 20;
		Projectile tempBullet = new Projectile(level, x+(width/2)-4, y+(height/2)-4, 8, 8, ID.BULLET, this);
		double angle = Math.atan2(mouseY - y-(height/2)+Camera.getY(), mouseX - x-(width/2)+Camera.getX());
		tempBullet.setVelocity(new Velocity(Math.cos(angle), Math.sin(angle)).normalize().multiply(10));
		tempBullet.setBounces(2);
		tempBullet.setDamage(bulletDamage);
		TanksII.handler.addObject(tempBullet);
		projectiles.add(tempBullet);
		stallTicks = 20;
		this.setVelocity(new Velocity());
	}
	private void moveNozzle() {
		if (shotCooldown > 0)
			return;
		gunAngle = Math.atan2(InputHandler.getMouseY() - y-(height/2)+Camera.getY(), InputHandler.getMouseX() - x-(width/2)+Camera.getX());
	}
}
