/**
 * CSYE 6200
 * @author Wenbo Sun
 * NUID:001994516
 *
 */
package edu.neu.csye6200.ca;

//class to create generation
public class CARule{
	
	//the length of every generation
	static int gLength = 201;
	
	CAGeneration cag = new CAGeneration();
	
	//rule to calculate the value of a cell by it's neighbor
	public double rule (double left, double mid, double right ){
		double val=0.0;
		if(CellularAutomatonAPP.box=="Rule1 (default)"){
			val =(left+mid+right)/2 - Math.floor( (left+mid+right)/2); // rule 1
		}
		if(CellularAutomatonAPP.box=="Rule2"){
			val =(left+mid+right)*1.5 - Math.floor( (left+mid+right)*1.5); // rule 2
		}
		if(CellularAutomatonAPP.box=="Rule3"){
			val =(left+mid+right)*2.5 - Math.floor( (left+mid+right)*2.5); // rule 3
		}
		return val;                                                               
	}
	
	//method to create the first generation and save it in cList
	public  void getFirstGen() {
		for(int i = 0; i < (gLength+1)/2-1; i++ ){
			CACell firstcell = new CACell(0);
			cag.cList.add(firstcell);
		}
		
		       
		CACell midcell = new CACell(1.0);    				 //the value of middle cell is 1
		cag.cList.add(midcell);
		
		
		for(int i = (gLength+1)/2 ; i < gLength; i++ ){
			CACell lastcell = new CACell(0);
			cag.cList.add(lastcell);
		}
		CAGenerationSet.gList.add(cag);
	}
	
	//method to get next generation by the previous one
	public void getNextGeneration(){
		
		CAGeneration newgen = new CAGeneration();
		
		CACell firstcell = new CACell (rule(0, cag.cList.get(0).getCell(), cag.cList.get(1).getCell()));  //set 0 as the value of edge
		newgen.cList.add(firstcell);
		
		for (int j =1 ; j< gLength-1 ; j++){
			CACell midcell = new CACell(rule(cag.cList.get(j-1).getCell(), cag.cList.get(j).getCell(), cag.cList.get(j+1).getCell())); // calculate the value of other cell
			newgen.cList.add(midcell);
		}
			
		CACell lastcell = new CACell (rule(0, cag.cList.get(gLength-2).getCell(), cag.cList.get(gLength-1).getCell())); //set 0 as the value of edge
		newgen.cList.add(lastcell);
			
		CAGenerationSet.gList.add(newgen);			//save the new generation in gList
		cag = newgen;
	}
	
	/*
	//method to create all generation and save them into gList
	public void getGeneration(){
		getFirstGen();                        //save the first generation to gList
		CAGeneration previousgen = cag;
		CAGenerationSet.gList.add(cag);
			
		int count = 100;
		for(int i = 0; i < count; i++){		 //create the following generation
			CAGeneration newgen = new CAGeneration();
			
			CACell firstcell = new CACell (rule(0, previousgen.cList.get(0).getCell(), previousgen.cList.get(1).getCell()));  //set 0 as the value of edge
			newgen.cList.add(firstcell);
			
			for (int j =1 ; j< gLength-1 ; j++){
				CACell midcell = new CACell(rule(previousgen.cList.get(j-1).getCell(), previousgen.cList.get(j).getCell(), previousgen.cList.get(j+1).getCell())); // calculate the value of other cell
				newgen.cList.add(midcell);
			}
				
			CACell lastcell = new CACell (rule(0, previousgen.cList.get(gLength-2).getCell(), previousgen.cList.get(gLength-1).getCell())); //set 0 as the value of edge
			newgen.cList.add(lastcell);
				
			CAGenerationSet.gList.add(newgen);			//save the new generation in gList
			previousgen = newgen;
			
		}	
	}*/
	
	/*	
	//method to test getGeneration() and getNextGeneration()
	public  void displayGen(){
	
		System.out.println(" ");
		for(CAGeneration ca : CAGenerationSet.gList ){	
			for(CACell cac : ca.cList){
				System.out.print(cac.getCell());
				System.out.print(" ");
			}
			System.out.println(" ");
		}					
	}*/
}

