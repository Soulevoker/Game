package com.reapersrage.things;

import com.reapersrage.things.IndexNode;
import com.reapersrage.things.Thing;

@SuppressWarnings("unchecked")	//stop conversion warnings
public class ThingBlock<T extends Thing> {
	//default max values and set max values
	public static final int DEFAULT_MAX_THINGS=200;
	private int MAX_THINGS;	//modifiable
	
	//Arrays of all things, the first of each thing, and the counter
	private IndexNode<T>[] things;	//array of all things
	private int first;		//index of first Thing (first node with data)
	private int nextAvail;		//index of next available Thing (first node with null data)
	private int lastAvail;		//index of last available Thing (for deletion purposes)
	private int numThings;	//count of number of things
	
	//Constructor
	//calls param instructor with default max
	public ThingBlock() {
		this(DEFAULT_MAX_THINGS);
	}
	
	//Constructor (maxThings)
	//initializes block and variables with max value
	public ThingBlock(int maxThings) {
		int i;	//counter
		//initialize max things
		MAX_THINGS=maxThings;
		//initialize counter
		numThings=0;
		//initialize array and allocate space
		things=new IndexNode[MAX_THINGS];	//initialize array
		for(i=0; i<MAX_THINGS-1; i++)	//initialize each node
			things[i]=new IndexNode<T>(null,i+1);	//create new node with pointers
		things[i]=new IndexNode<T>(null,-1);	//set last node to point to first=-1
		//initialize first Thing and player
		// 0 is location 0, -1 represents an
		// empty list (no first location)
		first=-1;
		//initialize next available Thing and player
		// 0 is location 0 (first available location), -1 is no location (full list)
		// this should not occur because the list is resizeable (the ensureCapacity()
		// method within add method creates a new, bigger array)
		nextAvail=0;
		//initialize last available
		lastAvail=MAX_THINGS-1;
	}
	
	//Update
	//calls update method for each thing
	public void update() {
		int i,curr;
		for(i=0,curr=first; i<numThings&&curr!=nextAvail; i++,curr=things[curr].getNext())
			things[curr].getData().update();
	}
	
	public int add(T obj) {
		//counter index, previous index, current index
		// curr is used for the returned index where obj was inserted
		int i=0,prev=-1,curr=-1;
		
		ensureAddable();	//ensures the array has enough space and variables
							// have acceptable params (doubles size if necessary)
		//check size
		if(numThings==0||first==-1) {	//if empty list
			//add to front
			assert(nextAvail>=0);	//make sure next Thing is valid index
									// ensureAddable() should make this
									//always evaluate to true
			things[nextAvail].setData(obj);	//set data of next available node
			numThings=1;	//set numThings (if list was empty, it is now 1)
			first=nextAvail;	//set firstThing (this Thing is only Thing in list)
			nextAvail=things[nextAvail].getNext();	//set next available Thing
			curr=first;		//set return index
		}
		else {
			prev=lastAvail;		//start with no prev (node before first is last available)
			curr=first;		//start at first value (curr=first) and walk curr and prev
			
			//the for loop walks the nodes of things with data by setting the
			// prev equal to the curr and the curr equal to its next; the counter
			// i does not increment either value and does not represent an index
			// in this case; i simply counts the number of things walked to
			// ensure it has not exceeded the number of things; curr passes the
			// nodes with non-deleted data when the counter exceeds the number of
			// things OR when current points to the next available index, which
			// cannot be used (deletion does NOT set the data to null, so
			// restoration is possible); "i<numThings" and "curr!=nextAvail"
			// should both return false at the same time; it is simply a precaution
			// to add both.
			for(i=0; i<numThings&&curr!=nextAvail; i++,curr=things[curr].getNext()) {
				//if current Thing is bigger than adding Thing, break out
				// else, set prev to current, update current, and keep walking
				if(things[curr].getData().compareX(obj)>=0)
					break;	//prev will be the node before where the object belongs
				else
					prev=curr;
			}
			
			//if current equals next available index (i==numThings), then
			// obj is largest Thing and is inserted at the back of the list
			if(i==numThings||curr==nextAvail) {
				things[nextAvail].setData(obj);	//set data of next available node
				nextAvail=things[curr].getNext();		//set nextAvail
				numThings++;	//increment numThings
			}
			else {
				curr=nextAvail;	//set curr to next available location (return index)
				nextAvail=things[curr].getNext();	//set next available location
				things[curr].setData(obj);		//set data of next available location (now curr)
				things[prev].setNext(curr);	//make prev point to next available
				numThings++;	//increment numThings
			}
			
			//check if nextAvail pushed to first
			if(nextAvail==first) {
				nextAvail=-1;
				lastAvail=-1;
			}
		}
		
		return curr;
	}
	
	//Remove (T)
	//searches list for object that equals search parameter as defined
	// in Thing and returns the index of the deleted value (or -1)
	// *** REMOVE DOES NOT ACTUALLY DELETE DATA *** The list is
	// seprated between things (nodes with accessable data) and
	// available things (nodes whose data cannot be used:  either
	// the data was never initialized, or it has been "removed").
	// The remove function simply adds the found node to the available
	// section by linking around it, and it adds it at the end so
	// "removed" data can still be recovered. There is even a possibility
	// for a "back" or "undo" button to a point (more variables would
	// have to be added and the code modified, of course).
	public int remove(T obj) {
		//counter index, previous index, current index
		// curr is used for the returned index where obj was deleted from;
		// curr should be equal to lastAvail (as lastAvail points to the
		// last object removed) in all cases except for unsuccessful
		// removal (object not found), when curr=-1
		int i=0,prev=lastAvail,curr=first;
		
		//cannot find/search if list is empty
		if(isEmpty())
			return -1;
		
		//walk list searching for obj in things
		for(i=0; i<numThings&&curr!=nextAvail; i++,curr=things[curr].getNext()) {
			//if things[curr]==obj, break
			if(things[curr].equals(obj))
				break;	//save prev and curr
			else
				prev=curr;
		}
		
		//if curr==nextAvail, return -1
		if(removeAt(curr,prev)==null)
			curr=-1;
		//else, return index
		return curr;
	}
	
	//Remove At (int)
	//removes an object at a location and returns the removed object
	// or null if not found
	// *** DOES NOT DELETE DATA *** (see "remove(T)" for details)
	public T removeAt(int loc) {
		//counter index, previous index, current index
		int i,prev=lastAvail,curr=first;
		T obj=null;	//object to be returned
		
		//cannot search if list is empty
		if(isEmpty())
			return obj;
		
		//walk list searching for who points to obj
		for(i=0; i<numThings&&curr!=nextAvail; i++) {
			if(curr==i)
				break;
			else {
				prev=curr;
				curr=things[curr].getNext();
			}
		}
		
		//call general remove function
		obj=removeAt(curr,prev);
		
		return obj;	//removeAt(curr,prev) returns null if not found
	}
	
	//removeAt (curr, prev) - private
	//once curr and prev are found in the other remove
	// functions, the same algorythm is done to check
	// if the value is found and to update all the
	// data members properly; since the index of the
	// one to be deleted is passed into the function
	// (as curr), only the data is returned (the Thing)
	private T removeAt(int curr, int prev) {
		//return object (Thing)
		T obj=null;
		//if past end of list, obj not found
		if(curr==nextAvail)
			obj=null;	//return null
		//if curr==first, remove first
		else if(curr==first) {
			//save object data for return
			obj=things[first].getData();
			//set new first (to second)
			first=things[first].getNext();
			//check if no second
			if(first==nextAvail)
				first=-1;
			//set lastAvail to current (old first)
			lastAvail=curr;
		}
		//else, remove obj at curr
		else {
			//save object data for return
			obj=things[curr].getData();
			//set [prev].next to [curr].next
			things[prev].setNext(things[curr].getNext());
			//set [lastAvail].next to curr
			things[lastAvail].setNext(curr);
			//set [curr].next to first (lastAvail node points to first)
			things[curr].setNext(first);
			//set [curr] to lastAvail
			lastAvail=curr;
		}
		
		return obj;
	}
	
	//Get Thing At (index)
	//returns the thing at the given index
	public T getThingAt(int index) {
		return things[index].getData();
	}
	
	//Is Empty
	//returns if the list has no usable things
	public boolean isEmpty() {
		return (numThings==0||first==-1);
	}
	
	//Ensure Addable
	//if capacity has not been reached, it does nothing; but
	// if it has, then it doubles the maximum size of the array
	// and sets all involved variables to ensure proper
	// additions can be made
	private void ensureAddable() {
		if(numThings==MAX_THINGS || nextAvail==-1)	//if list is full
		{
			int i, max=MAX_THINGS;	//counter and old max
			MAX_THINGS*=2;	//double capacity
			IndexNode<T>[] tempArray = new IndexNode[MAX_THINGS];
			//copy current to temp
			for(i=0; i<max; i++)
				tempArray[i]=things[i];
			//set next avail
			nextAvail=i;
			//finish temp with new nodes
			for(max=MAX_THINGS-1;i<max;i++)
				tempArray[i]=new IndexNode<T>(null,i+1);
			//set last avail
			lastAvail=i;
			//make lastAvail node point to first
			tempArray[i]=new IndexNode<T>(null,first);
			//set current to temp
			things=tempArray;
		}
	}
}
