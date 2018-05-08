/**
 * CSYE 6200
 * @author Wenbo Sun
 * NUID:001994516
 *
 */
package edu.neu.csye6200.ca;

import java.util.ArrayList;

//class to contain a single generation
public class CAGeneration {
	
	ArrayList<CACell> cList = new ArrayList<CACell>();  //create a ArrayList to save the new generation
	
	//Constructor
	public CAGeneration(){
		
	}
	
	public ArrayList<CACell> getGen(){
		return cList;
	}
}
