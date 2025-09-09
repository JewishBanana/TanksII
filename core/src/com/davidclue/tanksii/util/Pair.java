package com.davidclue.tanksii.util;

public class Pair<T, E> {
	
	private T var1;
	private E var2;
	
	private Pair(T var1, E var2) {
		this.var1 = var1;
		this.var2 = var2;
	}
	public T getFirst() {
		return var1;
	}
	public E getSecond() {
		return var2;
	}
	public void setFirst(T var1) {
		this.var1 = var1;
	}
	public void setSecond(E var1) {
		this.var2 = var1;
	}
	public static <T, E> Pair<T, E> of(T var1, E var2) {
		return new Pair<T, E>(var1, var2);
	}
}
