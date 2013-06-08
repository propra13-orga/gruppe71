package src.com.github.propa13_orga.gruppe71;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DShop {

	public DPanel SpielPanel;
	public JFrame frame;
	public JPanel pan;
	public ImageIcon[] pics;
	public String[] bild;
	public Dimension[] d;
	public JLabel[] label;
	
	public DShop(DPanel panel){
		this.SpielPanel=panel;
		this.frame=new JFrame();
		this.pan=new JPanel();
		pan.setLayout(null);
		
		
		//Welcome
		
		JTextField info= new JTextField("Willkommen im Shop!");
		Dimension size1=info.getPreferredSize();
		info.setBounds(200,20,size1.width, size1.height);
		info.setEditable(false);
		pan.add(info);
		
		//Info Verkaufen
		JLabel info2=new JLabel("Kaufen und Verkaufen Sie!");
		Dimension size2=info2.getPreferredSize();
		info2.setBounds(190,40,size2.width,size2.height);
		pan.add(info2);
		//Pics mit Labels
		this.Bild();
		this.InitPic();
		this.InitLabel();
		this.getDimension();
		this.AddToPan();
		
		
		//
		
		
		
		
		
		//Einstellungen
		pan.setBackground(Color.ORANGE);
		frame.getContentPane().add(pan);
		frame.setTitle("Der Shop");
		frame.setLocation(350,150);
		frame.setSize(550,400);
		frame.setVisible(true);
	}
	
	public void InitPic(){
		this.pics=new ImageIcon[SpielPanel.getDynamicObject(0).NumberItems()];
		for(int i=0;i<SpielPanel.getDynamicObject(0).NumberItems();i++){
		this.pics[i]= new ImageIcon(bild[i]);
		this.pics[i].setImage(this.pics[i].getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT));
		}
	}
	public void getDimension(){
		this.d=new Dimension[SpielPanel.getDynamicObject(0).NumberItems()];
			for(int i=0;i<SpielPanel.getDynamicObject(0).NumberItems();i++){
			this.d[i]= this.label[i].getPreferredSize();
			}
		
	}
	public void InitLabel(){
		this.label=new JLabel[SpielPanel.getDynamicObject(0).NumberItems()];
		for(int i=0;i<SpielPanel.getDynamicObject(0).NumberItems();i++){
			this.label[i]= new JLabel(SpielPanel.getDynamicObject(0).getName(i),this.pics[i],JLabel.RIGHT);
			}
	}
	
	public String[] Bild(){
		this.bild=new String[SpielPanel.getDynamicObject(0).NumberItems()];
		
		bild[0]="src/src/com/github/propa13_orga/gruppe71/messer.jpg";
		bild[1]="src/src/com/github/propa13_orga/gruppe71/bb_ketchup01.png";
		bild[2]="src/src/com/github/propa13_orga/gruppe71/messer.jpg";
		bild[3]="src/src/com/github/propa13_orga/gruppe71/bb_ketchup01.png";
		bild[4]="src/src/com/github/propa13_orga/gruppe71/bb_ketchup01.png";
		bild[5]="src/src/com/github/propa13_orga/gruppe71/messer.jpg";
		
	
		return bild;
	}
	public void AddToPan(){
		int y=0;
		for(int i=0;i<SpielPanel.getDynamicObject(0).NumberItems();i++){
			this.label[i].setBounds(20,120+y,this.d[i].width,this.d[i].height);
			this.pan.add(label[i]);
			y+=30;
			}
	}
}
