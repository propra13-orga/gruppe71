package src.com.github.propa13_orga.gruppe71;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class DSkill implements ActionListener {
	
	private DPanel SpielPanel;
	public JFrame frame;
	public JPanel pan;
	public JTextField points;
	public JLabel[] rank;
	public JTextField passive;
	public JLabel hast,hast2,hast3;//bewirkt mehr Exp bei Mobs töten
	public JLabel greed,greed2;//mehr coins
	public JButton wisdom;//wisdom gehört zu hast...
	public JButton greed3;
	
	public String[] rank2;
	
	
	
	
	public DSkill(DPanel panel){
		this.SpielPanel=panel;
		this.frame = new JFrame();
		pan = new JPanel();
		pan.setLayout(null);
		//Init Objekte
		
		this.Rank();
		//Titel JTextField
		JTextField info= new JTextField("Fähigkeiten Upgrade!");
		Dimension size1=info.getPreferredSize();
		info.setBounds(200,20,size1.width, size1.height);
		info.setEditable(false);
		pan.add(info);
		
		//Max Rank
		JTextField info2= new JTextField("Max Rank for all abilities is: 5");
		Dimension size2=info.getPreferredSize();
		info2.setBounds(360,50,size2.width+50, size2.height);
		info2.setEditable(false);
		pan.add(info2);
		
		
		this.points= new JTextField("Total Skillpoints Left: "+SpielPanel.getDynamicObject(0).getSkills());
		Dimension size3=points.getPreferredSize();
		points.setBounds(20,50,size3.width+5, size3.height);
		points.setEditable(false);
		pan.add(points);
		
		this.passive= new JTextField("Passive Fähigkeiten:");
		Dimension size4=points.getPreferredSize();
		passive.setBounds(20,100,size4.width-5, size4.height);
		passive.setEditable(false);
		pan.add(passive);
		
		//Hast ... Mehr EXP Rank 0
		this.hast= new JLabel("1.WISDOM-> MEHR EXP");
		Dimension size5=hast.getPreferredSize();
		hast.setBounds(20,140,size5.width, size5.height);
		pan.add(hast);
		
		
		this.hast2= new JLabel("Current Rank:  ");
		Dimension size7=hast.getPreferredSize();
		hast2.setBounds(20,160,size7.width, size7.height);
		pan.add(hast2);
		
		this.hast3= new JLabel("Bonus:  ");
		Dimension size8=hast.getPreferredSize();
		hast3.setBounds(20,180,size8.width, size8.height);
		pan.add(hast3);
		
		//Button Wisdom
		this.wisdom= new JButton("UPGRADE");
		Dimension size9=hast.getPreferredSize();
		wisdom.setBounds(20,200,size9.width, size9.height);
		this.wisdom.addActionListener(this);
		pan.add(wisdom);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//Greed
		this.greed= new JLabel("2.Greed-> MEHR COINS");
		Dimension size6=greed.getPreferredSize();
		greed.setBounds(20,240,size6.width, size6.height);
		pan.add(greed);
		
		
		

		

		//Eigenschaften Frame
		frame.setTitle("Fähigkeiten");
		frame.getContentPane().add(pan);
		pan.setBackground(Color.ORANGE);
		frame.setLocation(350,150);
		frame.setSize(550,400);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
	}
	
	//Hier wird alles Initialisiert
	public String[] Rank(){
		this.rank2=new String[6];
		
		this.rank2[0]="0";
		this.rank2[1]="1";
		this.rank2[2]="2";
		this.rank2[3]="3";
		this.rank2[4]="4";
		this.rank2[5]="5";
		
		return this.rank2;
		
	}
	
	/**
	 * ActionListener hoert auf Button
	 * 
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.wisdom){
			if(SpielPanel.getDynamicObject(0).getSkills()>=2){//Hier
				SpielPanel.getDynamicObject(0).setSkills(-2);
				this.points.setText("Total Skillpoints Left: "+SpielPanel.getDynamicObject(0).getSkills());
			}
			
			
			
	}
	
	
	}
	
	
	

}
