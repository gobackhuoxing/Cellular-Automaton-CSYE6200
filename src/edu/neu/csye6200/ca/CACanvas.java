/**
 * CSYE 6200
 * @author Wenbo Sun
 * NUID:001994516
 *
 */
package edu.neu.csye6200.ca;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;

import javax.swing.JPanel;

//class to create a thread to draw the picture
public class CACanvas extends JPanel implements Runnable,Observer {

	private static final long serialVersionUID = 1L;
	
	private static Logger log = Logger.getLogger(CACanvas.class.getName());
	
	//variable to control how many generations to display
	static int gen = 0;
	
	/*//constructor
	public CACanvas(){
		repaint();
	}*/
	
	//method to draw the picture
	public void paint(Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		
		Dimension size = getSize();
		g2d.setColor(Color.white);
		g2d.fillRect(0, 0, size.width, size.height);
	
		int cellSize = 5;
		//get every elements in gList and draw them by the selected rule and style
		for(int j = 0; j<CAGenerationSet.gList.size(); j++){	
			for(int i = 0; i<CAGenerationSet.gList.get(j).cList.size(); i++){
				int color = (int) Math.floor( CAGenerationSet.gList.get(j).cList.get(i).getCell()*255);
				Color col= null;
				//style 1
				if(CellularAutomatonAPP.style == "Black & White (default)"){
					col = new Color(255-color,255-color,255-color);
				}
				//style 2
				if(CellularAutomatonAPP.style == "Reversed Black & White"){
					col = new Color(color,color,color);
				}
				//style 3
				if(CellularAutomatonAPP.style == "Colour"){
					col = new Color(254-(color*2)%255,254-(color*3)%255,254-(color*4)%255);
				}
				paintCell(g2d,i*6,j*6, cellSize, col);
			}	
		}
	}	
	
	//method to draw a single cell
	private void paintCell(Graphics2D g2d,int x, int y, int size1, Color color) {
		g2d.setColor(color);
		g2d.fillRect(x, y, size1, size1);
	}

	//Thread
	@Override
	public void run() {
		synchronized (CellularAutomatonAPP.mainPanel){
			//variable to control speed
			int sp = 100;
			
			CARule car = new CARule();
			
			//paint the first generation if the user input != 0
			if(gen!=0){
			car.getFirstGen();
			repaint();
			}
			
			//paint the following generations
			for(int k = 0; k<gen-1; k++)	{
				
				//Pause if user click PAUSE button
				if(CellularAutomatonAPP.bPause){
					try {
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				//change speed by user's select
				if(CellularAutomatonAPP.speed== "Fast") sp=30;
				if(CellularAutomatonAPP.speed== "Medium (default)") sp=100;
				if(CellularAutomatonAPP.speed== "Slow") sp=300;
				
				//stop the thread if user click STOP button
				if(CellularAutomatonAPP.bStop) break;
				car.getNextGeneration();
				repaint();
				
				//control the scrollPanel
				if (k%3 ==0){
					CellularAutomatonAPP.textArea.append("\n");
					CellularAutomatonAPP.textArea.setCaretPosition(CellularAutomatonAPP.textArea.getText().length());
					}
				
				//sleep between two generations
				try {
					Thread.sleep(sp);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	//method to receive data from CellularAutomatonAPP.class
	@Override
	public void update(Observable o, Object num) {
		try{
		gen=(int) num;
		}catch(NumberFormatException ex){
			log.info("Passed a rule");
		}		
	}
}
