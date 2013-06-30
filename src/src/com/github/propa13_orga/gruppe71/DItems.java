package src.com.github.propa13_orga.gruppe71;



import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Image;

import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class DItems  {
	
	private static final long serialVersionUID = 1L;
	
	
	private DPanel SpielPanel;
	public ImageIcon[] pics;
	public JLabel[]label;
	public String[] bild;
	public JFrame frame;
	public Dimension[] l;
	public JPanel pan;
	public JTextField money;
	
	public DItems(DPanel panel) {
	this.SpielPanel=panel;
	this.frame = new JFrame();
	pan = new JPanel();
	pan.setLayout(null);
	//Init Objekte
	
	
	//Prozess...
	
	JTextField info= new JTextField("Deine Items sind hier aufgelistet!");
	Dimension size1=info.getPreferredSize();
	info.setBounds(180,20,size1.width, size1.height);
	info.setEditable(false);
	pan.add(info);
	

	
	
	
	this.Bild();
	
	this.InitPic();
	
	this.InitLabel();
	
	this.InitDimension();
	
	this.AddToPan();

	

	//Eigenschaften Frame
	frame.setTitle("Inventar");
	frame.getContentPane().add(pan);
	pan.setBackground(Color.ORANGE);
	frame.setLocation(350,150);
	frame.setSize(550,400);
	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	frame.setResizable(false);
	frame.setVisible(true);
	}
	
	/**
	 * Label Initialisierung
	 * 
	 */
	public void InitLabel(){
		
		this.label=new JLabel[SpielPanel.getDynamicObject(0).NumberItems()];
		for(int i=0;i<SpielPanel.getDynamicObject(0).NumberItems();i++){
	    this.label[i]=new JLabel("Slot Nr.:"+(i+1)+"   "+SpielPanel.getDynamicObject(0).getName(i) +"  Anzahl:   "+"  "+SpielPanel.getDynamicObject(0).AnzahlItems(i),pics[i],JLabel.RIGHT);
	    this.label[i].setFont(new Font("Serif", Font.PLAIN, 12));
		
		
		
		}
	}
	
	/**
	 * Dimension der Labels etc. zur Ordnung auf dem Panel
	 * 
	 */
	public void InitDimension(){
		this.l=new Dimension[SpielPanel.getDynamicObject(0).NumberItems()];
		for(int i=0;i<SpielPanel.getDynamicObject(0).NumberItems();i++){
			this.l[i]=label[i].getPreferredSize();
		}
	}
	
	/**
	 * Alles wird nun dem Panel zugefuegt , Dimension etc.(Shop)
	 *
	 */
	public void AddToPan(){
		int y=0;
		int z=0;
		for(int i=0;i<=(SpielPanel.getDynamicObject(0).NumberItems()/3);i++){
		   this.label[i].setBounds(40,80+y,l[i].width,l[i].height);
		   pan.add(label[i]);
		   y+=70;
		}
		for(int i=(SpielPanel.getDynamicObject(0).NumberItems()/3)+1;i<SpielPanel.getDynamicObject(0).NumberItems();i++){
			   this.label[i].setBounds(260,80+z,l[i].width,l[i].height);
			   pan.add(label[i]);
			   z+=70;
			}
		
		this.money= new JTextField("Dein Vermoegen: "+this.SpielPanel.getDynamicObject(0).getMoney()+" $$$");
		Dimension size2=money.getPreferredSize();
		money.setBounds(20,340,size2.width, size2.height);
		money.setEditable(false);
		pan.add(money);
		
	}
	
	/**
	 * Die Bilder fuer die Items 6 Stk. kommen hier
	 * 
	 */
	public void InitPic(){
		this.pics=new ImageIcon[SpielPanel.getDynamicObject(0).NumberItems()];
		for(int i=0;i<SpielPanel.getDynamicObject(0).NumberItems();i++){
		this.pics[i]= new ImageIcon(bild[i]);
		this.pics[i].setImage(this.pics[i].getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
		}
	}
	
	/**
	 * String der Bilder
	 *
	 */
	public String[] Bild(){
		this.bild=new String[SpielPanel.getDynamicObject(0).NumberItems()];
		
		bild[0]="src/src/com/github/propa13_orga/gruppe71/bb_messer.png";
		bild[1]="src/src/com/github/propa13_orga/gruppe71/bb_pKaese.png";
		bild[2]="src/src/com/github/propa13_orga/gruppe71/messer.png";
		bild[3]="src/src/com/github/propa13_orga/gruppe71/bb_ketchup01.png";
		bild[4]="src/src/com/github/propa13_orga/gruppe71/bb_ketchup01.png";
		bild[5]="src/src/com/github/propa13_orga/gruppe71/messer.png";
		
		for(int i=0;i<SpielPanel.getDynamicObject(0).NumberItems();i++){
			if(SpielPanel.getDynamicObject(0).getName(i)=="Leer"){
				bild[i]="src/src/com/github/propa13_orga/gruppe71/bb_leer.png";	
			}
		
	}
		return bild;
	}
	
	
}
	
	
	
	
	
	

	

