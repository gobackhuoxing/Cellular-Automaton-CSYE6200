/**
 * CSYE 6200
 * @author Wenbo Sun
 * NUID:001994516
 *
 */
package edu.neu.csye6200.ca;

import java.awt.BorderLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.ScrollPaneConstants;

//initial UI and put everything together
public  class CellularAutomatonAPP extends CAApp {

	private static Logger log = Logger.getLogger(CellularAutomatonAPP.class.getName());
	
	//variable for buttons and comboBox
	static boolean bStart = false;
	static boolean bStop = false;
	static boolean bPause = false;
	static String box = "Rule1 (default)";
	//variable to control style
	static String style = "Black & White (default)" ;
	//variable to control speed
	static String speed = "Medium (default)" ;
	
	//constructor
    public CellularAutomatonAPP(){
		log.info("App started");
		initGUI();
	}
	
    //method to initial GUI
    public void initGUI() {
    	super.initGui();
    	
		frame.setSize(1350, 700);								//specify frame
		frame.setTitle("Cellular Automaton by Wenbo Sun");
		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		frame.add(getNorthPanel(),BorderLayout.NORTH);			//add northPanel to frame
		frame.add(getSouthPanel(),BorderLayout.SOUTH);			//add southPanel to frame
		
    	textArea.setLineWrap(true); 
    	
    	panel.setLayout(new BorderLayout());
    	panel.add(textArea,BorderLayout.WEST);					//add textArea to panel
		panel.add(getMainPanel(),BorderLayout.CENTER);			//add mainPanel to panel
		
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED); //specify scrollPane
    	scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setViewportView(panel);
		frame.add(scrollPane,BorderLayout.CENTER);				//add scrollPane to panel
		
    	frame.setVisible(true);
        }
	
    //method to create northPanel
	@Override
   	public JPanel getNorthPanel() {
       	
   		super.getNorthPanel();
   		
   		//specify the "START" button
   		startBtn.addActionListener(new ActionListener(){	//add ActionListener for START
   			public void actionPerformed(ActionEvent e){
   				log.info("START button was pressed");
   				
   				//if thread is running and START was already pressed create a warning dialog
   				if( Thread.currentThread().isAlive() && bStart ){
   					JOptionPane.showMessageDialog(panel, "please STOP before RESTART", "please STOP FIRST",JOptionPane.WARNING_MESSAGE);
   				}
   				//if not get the input in textField and cast it to int and sent it to Observer
   				else{
   					try{
	   					int num = Integer.valueOf(textField.getText()).intValue();	
	   					setChanged();
	   					notifyObservers(num);
	   				//if the input is not a int create a warning dialog	
	   				}catch(NumberFormatException ex){
	   					bStop =true;
	   					JOptionPane.showMessageDialog(panel, "Please input a vaild number", "INVALID INPUT",JOptionPane.WARNING_MESSAGE);
	   				}
   					
   					bStart = true;
   					bStop = false;
	   				// start the thread
   					new Thread( (Runnable) mainPanel).start();	
   				}
   			}
   		});
   		 
   		//specify the "PAUSE" button
   		pauseBtn.addActionListener(new ActionListener(){	//add ActionListener for PAUSE
  			 public void actionPerformed(ActionEvent e){
  				 log.info("PAUSE button was pressed");
  				 //if STOP was not pressed let the thread wait
  				 if(!bStop) {
  					 bPause = true; 
  				 //if not create a warning dialog
  				 }else{
  					JOptionPane.showMessageDialog(panel, "Please RESTART ", "PROGRESS IS STOP",JOptionPane.WARNING_MESSAGE);
  				 }
  			 }
  		 });

   		//specify the "RESUME" button
   		resumeBtn.addActionListener(new ActionListener(){	//add ActionListener for RESUME
  			 public void actionPerformed(ActionEvent e){
  				 log.info("RESUME button was pressed"); 
  				 
  				 //if STOP was not pressed
  				 if(!bStop){
  					 //if PAUSE was not pressed create a warning dialog
	  				 if(!bPause ){
	  					 JOptionPane.showMessageDialog(panel, "Please PAUSE before you RESUME ", "PROGRESS IS RUNNING",JOptionPane.WARNING_MESSAGE);
	  				 }
	  				 //if PAUSE was pressed wake up the thread
	  				 if( bPause ){
		  				 synchronized(mainPanel){
		  					 bPause = false;
		  					 mainPanel.notify();
		  				 }
	  				 } 
  				 }
  				 //if STOP was pressed create a warning dialog
  				 else{
  					JOptionPane.showMessageDialog(panel, "Please RESTART ", "PROGRESS IS STOP",JOptionPane.WARNING_MESSAGE);
  				 }
  			 }
   		});
   		    	
   		//specify the "STOP" button
   		stopBtn.addActionListener(new ActionListener(){		//add ActionListener for STOP
			public void actionPerformed(ActionEvent e){
   				 log.info("STOP button was pressed");
   				 //stop the thread
   				 bStop= true;  
   				 bStart =false;
   				 bPause = false; 
   				 //wake up and stop the thread
   				 synchronized(mainPanel){
  					 bPause = false;
  					 mainPanel.notify();
  				 }
   				 //clear the gList
   				 CAGenerationSet.gList.clear();
   				 textArea.setText(" ");
   			 }
   		 });
   		
   		//specify the comboBox
   		comboBox.addActionListener(new ActionListener(){	//add ActionListener for comboBox
   			public void actionPerformed(ActionEvent e){
   				//get the input
   				box =(String) comboBox.getSelectedItem(); 				 
   			}
   		});
   		 
   		return northPanel;
   	}
    
	//method to create southPanel
    @Override
	public JPanel getSouthPanel() {
    	super.getSouthPanel();
    	southPanel.add(label3);	
    	
    	//add radioButton for style select
    	addStyleSelect("Black & White (default)",true);
    	addStyleSelect("Reversed Black & White",false);
    	addStyleSelect("Colour",false);
    	
    	//add radioButton for speed select
    	southPanel.add(label4);	
    	addSpeedSelect("Fast",false);
    	addSpeedSelect("Medium (default)",true);
    	addSpeedSelect("Slow",false);
    	
		return southPanel;
	} 
    
    //method to get mainPanel from CACanvas.class
    @Override
	public JPanel getMainPanel(){

    	super.getMainPanel();
		
		return mainPanel;
	}
	
    //method to create radioButton for style select
    public void addStyleSelect(final String n,boolean s){
    	radio1=new JRadioButton(n,s);
    	group1.add(radio1);
    	southPanel.add(radio1);
    	
    	//ActionListener to get the input from style select
    	radio1.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
   				if("Black & White (default)".equals(n)){
   					style = "Black & White (default)" ;
   				}
   				if("Reversed Black & White".equals(n)){
   					style = "Reversed Black & White" ;
   				}
   				if("Colour".equals(n)){
   					style = "Colour" ;
   				}
   			}
   		});
    }
    
    //method to create radioButton for speed select
    public void addSpeedSelect(final String n,boolean s){
    	radio2=new JRadioButton(n,s);
    	group2.add(radio2);
    	southPanel.add(radio2);
    	
    	//ActionListener to get the input from speed select
    	radio2.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
   				if("Fast".equals(n)){
   					speed = "Fast" ;
   				}
   				if("Medium (default)".equals(n)){
   					speed = "Medium (default)" ;
   				}
   				if("Slow".equals(n)){
   					speed = "Slow" ;
   				}
   			}
   		});
    }

	public static void main (String[] args){
		CellularAutomatonAPP caa = new CellularAutomatonAPP();	
		CACanvas cac = new CACanvas();
		caa.addObserver(cac);
	}	
}