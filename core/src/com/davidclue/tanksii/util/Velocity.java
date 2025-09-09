package com.davidclue.tanksii.util;

public class Velocity {
	
	private double x,y;
	
	public Velocity() {
		this.x = 0.0D;
		this.y = 0.0D;
	}
	public Velocity(double x, double y) {
		this.x = x;
		this.y = y;
	}
	public Velocity normalize() {
		double div = Math.sqrt(x*x) + Math.sqrt(y*y);
		return new Velocity(x/div, y/div);
	}
	public Velocity multiply(double value) {
		this.x *= value;
		this.y *= value;
		return this;
	}
	public Velocity clone() {
		return new Velocity(x, y);
	}
	public void reverseXVel() {
		this.x *= -1;
	}
	public void reverseYVel() {
		this.y *= -1;
	}
	public void reverse() {
		this.x *= -1;
		this.y *= -1;
	}
	public Velocity add(double x, double y) {
		this.x += x;
		this.y += y;
		return this;
	}
	public Velocity subtract(double x, double y) {
		this.x -= x;
		this.y -= y;
		return this;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public void set(double x, double y) {
		this.x = x;
		this.y = y;
	}
}
