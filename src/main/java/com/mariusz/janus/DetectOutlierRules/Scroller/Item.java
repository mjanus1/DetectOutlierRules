package com.mariusz.janus.DetectOutlierRules.Scroller;

public class Item<T> {
	private T object;
	private int number;
	private boolean selected;
	
	public Item(T object) {
		super();
		this.object = object;
	}

	public Item(T object, int number) {
		super();
		this.object = object;
		this.number = number;
	}
	
	public Item(T object,boolean selected) {
		super();
		this.object = object;
		this.selected = selected;
	}

	public Item(T object, int number, boolean selected) {
		super();
		this.object = object;
		this.number = number;
		this.selected = selected;
	}
	
	public void setObject(T object) {
		this.object = object;
	}
	public T getObject() {
		return object;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public int getNumber() {
		return number;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public boolean isSelected() {
		return selected;
	}
}
