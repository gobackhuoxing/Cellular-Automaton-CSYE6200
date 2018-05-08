/**
 * CSYE 6200
 * @author Wenbo Sun
 * NUID:001994516
 *
 */
package edu.neu.csye6200.ca;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Label;
import java.util.Observable;

//abstract class for APP main class
public abstract class CAApp extends Observable  {
	
	static protected JPanel mainPanel = null;
	
	protected JPanel panel = null;
	protected JFrame frame = null;
	protected JScrollPane scrollPane = null;
	protected JPanel northPanel = null;
	protected JPanel southPanel = null;
	protected JDialog dialog =null;
	protected JButton startBtn = null;
	protected JButton stopBtn = null;
	protected JButton pauseBtn = null;
	protected JButton resumeBtn = null;
	protected Label label1 = null;
	protected Label label2 = null;
	protected Label label3 = null;
	protected Label label4 = null;
	protected static JTextArea textArea =null;
	protected JTextField textField = null;
	@SuppressWarnings("rawtypes")
	protected JComboBox comboBox = null;
	protected ButtonGroup group1 = null;
	protected JRadioButton radio1 = null;
	protected ButtonGroup group2 = null;
	protected JRadioButton radio2 = null;
	protected CACanvas canvas = null;
    
	//method to initial GUI
	public void initGui(){
		frame = new JFrame();	//create a frame
		panel = new JPanel();	//create a panel
		scrollPane = new JScrollPane(panel);	//create a ScrollPane
		textArea = new JTextArea();	//create a textarea
	}
	
	//method to create northPanel
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public  JPanel getNorthPanel(){
    	northPanel = new JPanel();
       	northPanel.setLayout(new FlowLayout());
       	
       	//create buttons, textField and comboBox
       	startBtn = new JButton("START");
       	pauseBtn = new JButton("PAUSE");
       	resumeBtn = new JButton("RESUME");
       	stopBtn = new JButton("STOP");
       	label1 = new Label("How many Generations do you want : ");
   		
   		textField = new JTextField(10);
   		
   		label2 = new Label("Select a rule : ");
   		comboBox =new JComboBox ();
   		comboBox.addItem("Rule1 (default)");
   		comboBox.addItem("Rule2");
   		comboBox.addItem("Rule3");
   		
   		//add everything to northPanel
   		northPanel.add(startBtn);
   		northPanel.add(pauseBtn);
   		northPanel.add(resumeBtn);
   		northPanel.add(stopBtn);
   		northPanel.add(label1);
   		northPanel.add(textField);
   		northPanel.add(label2);
   		northPanel.add(comboBox);
   		
   		northPanel.setBackground(Color.WHITE);
   		
   		return northPanel;
    }
    
    //method to create southPanel
    public  JPanel getSouthPanel(){
    	southPanel = new JPanel();
    	southPanel.setLayout(new FlowLayout());
    	label3 = new Label("Select a style:");
    	label4 = new Label("Select a speed:");
    	
    	//create group for radiobutton
    	group1 =new ButtonGroup();
    	group2 =new ButtonGroup();
    	southPanel.setBackground(Color.WHITE);
		return southPanel;
    };
    
    //method to get mainPanel from CACanvas.class
    public  JPanel getMainPanel(){
    	mainPanel = new CACanvas();
    	return mainPanel;
    }
}
