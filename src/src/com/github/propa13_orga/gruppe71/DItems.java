package src.com.github.propa13_orga.gruppe71;



import java.awt.ComponentOrientation;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;


public class DItems  {
	
	private static final long serialVersionUID = 1L;
	
	
	private DPanel SpielPanel;
	public ImageIcon[] pics;
	public JLabel[]label;
	public String[] bild;
	public JFrame frame;
	
	
	public DItems(DPanel panel) {
	this.SpielPanel=panel;
	//Init Objekte
	
	this.frame = new JFrame();
	//Prozess...
	
	this.Bild();
	this.InitPic();
	this.InitLabel();
	
	
     
	FlowLayout flowLayout = new FlowLayout();
	frame.getContentPane().setLayout(flowLayout);
	frame.getContentPane().setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

	//Eigenschaften Frame
	frame.setTitle("Inventar");
	frame.setLocation(350,150);
	frame.setSize(550,400);
	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	frame.setResizable(false);
	frame.setVisible(true);
	}
	public void InitLabel(){
		
		this.label=new JLabel[SpielPanel.getDynamicObject(0).NumberItems()];
		for(int i=0;i<SpielPanel.getDynamicObject(0).NumberItems();i++){
	    this.label[i]=new JLabel();
	    this.label[i].setFont(new Font("Serif", Font.PLAIN, 12));
		this.label[i].setText("Slot Nr.:"+(i+1)+"   "+SpielPanel.getDynamicObject(0).getName(i) +"  Anzahl:   "+"  "+SpielPanel.getDynamicObject(0).AnzahlItems(i));	
		this.label[i].setIcon(pics[i]);
		frame.add(label[i]);
		
		}
	}
	
	public void InitPic(){
		this.pics=new ImageIcon[SpielPanel.getDynamicObject(0).NumberItems()];
		for(int i=0;i<SpielPanel.getDynamicObject(0).NumberItems();i++){
		this.pics[i]= new ImageIcon(bild[i]);
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
		
		for(int i=0;i<SpielPanel.getDynamicObject(0).NumberItems();i++){
			if(SpielPanel.getDynamicObject(0).getName(i)=="Leer"){
				bild[i]="src/src/com/github/propa13_orga/gruppe71/Leer.png";	
			}
		
	}
		return bild;
	}
	
	
}
	
	
	
	
	
	

	

