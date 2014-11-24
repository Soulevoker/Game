package com.reapersrage.things;

import com.reapersrage.things.Thing;

public class IndexNode<T extends Thing> {
	
	private T object;	//pointer to the data (Thing, Player, etc.)
	private int next;	//index of next Thing in order (used by ThingBlock)
	
	//constructor
	//calls constructor (object, next)
	public IndexNode() {
		this(null,-1);
	}
	
	//constructor (object)
	//calls constructor (object, next)
	public IndexNode(T object) {
		this(object,-1);
	}
	
	//constructor (next)
	//calls constructor (object, next)
	public IndexNode(int next) {
		this(null, next);
	}
	
	//constructor
	//initializes object and next variable
	public IndexNode(T object, int next) {
		this.object=(object==null?null:object);	//attempt to avoid null pointer issues
		this.next=next;
	}
	
	//Set Data (T)
	//sets the data of the object (doesn't affect next)
	public void setData(T data) {
		object=data;
	}
	
	//Set Next (int)
	//sets the next index
	public void setNext(int next) {
		this.next=next;
	}
	
	//Get Data
	//returns object
	public T getData() {
		return object;
	}
	
	//Get Next
	//returns next index
	public int getNext() {
		return next;
	}
}
