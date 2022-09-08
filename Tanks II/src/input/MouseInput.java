package input;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import entities.GameObject;
import entities.ID;
import entities.projectiles.Projectile;
import general.Camera;
import general.Handler;
import util.Velocity;

public class MouseInput extends MouseAdapter {
	
	private Handler handler;
	private Camera cam;
	private GameObject tempPlayer = null;
	
	public MouseInput(Handler handler, Camera cam) {
		this.handler = handler;
		this.cam = cam;
	}
	public void findPlayer(GameObject player) {
		this.tempPlayer = player;
	}
	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		
		if (tempPlayer != null) {
			GameObject tempBullet = new Projectile(handler.getLevel(), tempPlayer.getX()+32, tempPlayer.getY()+32, 8, 8, ID.BULLET, tempPlayer);
			handler.addObject(tempBullet);
			
			float angle = (float) Math.atan2(my - tempPlayer.getY()-16+cam.getY(), mx - tempPlayer.getX()-16+cam.getX());
			int bulletVel = 10;
			
			tempBullet.setVelocity(new Velocity((float) ((bulletVel) * Math.cos(angle)), (float) ((bulletVel) * Math.sin(angle))));
		}
	}
}
