package src.com.github.propa13_orga.gruppe71;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;



public class DShop implements ActionListener {

	public DPanel SpielPanel;
	public JFrame frame;
	public JPanel pan;
	public ImageIcon[] pics;
	public String[] bild,eingabe;
	public Dimension[] d,b,s,a;
	public JLabel[] label;
	public JTextField[] amount,anzahl;
	public int[] wert;
	public JButton einkauf;
	public JButton[] buy,sell;
	
	
	public DShop(DPanel panel){
		this.SpielPanel=panel;
		this.frame=new JFrame();
		this.pan=new JPanel();
		pan.setLayout(null);
		
		
		//Welcome
		
		JTextField info= new JTextField("Willkommen im Shop!");
		Dimension size1=info.getPreferredSize();
		info.setBounds(320,20,size1.width, size1.height);
		info.setEditable(false);
		pan.add(info);
		
		//Info Verkaufen
		JLabel info2=new JLabel("Kaufen und Verkaufen Sie!");
		Dimension size2=info2.getPreferredSize();
		info2.setBounds(310,50,size2.width,size2.height);
		pan.add(info2);
		//Menge
		JLabel menge=new JLabel("Menge");
		Dimension size3=menge.getPreferredSize();
		menge.setBounds(70,90,size3.width,size3.height);
		pan.add(menge);
		//Pics mit Labels
		this.BuySellEinkauf();
		this.Amount();
		this.Eingabe();
		this.Bild();
		this.InitPic();
		this.InitLabel();
		this.InitDimension();
		this.AddToPan();
		this.Gold();
		this.Wert();
		//
		
		
		
		
		
		//Einstellungen
		pan.setBackground(Color.ORANGE);
		frame.getContentPane().add(pan);
		frame.setTitle("Der Shop");
		frame.setLocation(300,100);
		frame.setSize(800,500);
		frame.setResizable(false);
		frame.setVisible(true);
	}
	
	public void InitPic(){
		this.pics=new ImageIcon[SpielPanel.getDynamicObject(0).NumberItems()];
		for(int i=0;i<SpielPanel.getDynamicObject(0).NumberItems();i++){
		this.pics[i]= new ImageIcon(bild[i]);
		this.pics[i].setImage(this.pics[i].getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT));
		}
	}
	public void InitDimension(){
		this.d=new Dimension[SpielPanel.getDynamicObject(0).NumberItems()];
		this.b=new Dimension[SpielPanel.getDynamicObject(0).NumberItems()];
		this.s=new Dimension[SpielPanel.getDynamicObject(0).NumberItems()];
		this.a=new Dimension[SpielPanel.getDynamicObject(0).NumberItems()];
		for(int i=0;i<SpielPanel.getDynamicObject(0).NumberItems();i++){
			this.d[i]= this.label[i].getPreferredSize();
			this.b[i]= this.buy[i].getPreferredSize();
			this.s[i]= this.sell[i].getPreferredSize();
			this.a[i]= this.sell[i].getPreferredSize();
			
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
			//Label mit Bild
			this.label[i].setBounds(20,120+y,this.d[i].width,this.d[i].height);
			this.pan.add(label[i]);
			//Anzahl
			this.anzahl[i].setBounds(300,120+y,this.a[i].width,this.a[i].height);
			anzahl[i].setEditable(false);
			this.pan.add(anzahl[i]);
			//Textfield mit Wert
			this.amount[i].setBounds(80,120+y,20,20);
			amount[i].setEditable(true);
			this.pan.add(amount[i]);
			//buy und sell buttons
			this.buy[i].setBounds(120,120+y,this.b[i].width,this.d[i].height);
			this.buy[i].addActionListener(this);
			this.pan.add(buy[i]);
			
			this.sell[i].setBounds(200,120+y,this.s[i].width,this.s[i].height);
			this.sell[i].addActionListener(this);
			this.pan.add(sell[i]);
			y+=30;
			}
	}
	public void Gold(){
		JTextField money =new JTextField("Vermögen:  "+SpielPanel.getDynamicObject(0).getMoney()+" $$$");
		Dimension size3=money.getPreferredSize();
		money.setBounds(20,40,size3.width,size3.height);
		money.setEditable(false);
		pan.add(money);
	}
	
	public void Amount(){
		
		this.amount=new JTextField[SpielPanel.getDynamicObject(0).NumberItems()];
		
		this.anzahl=new JTextField[SpielPanel.getDynamicObject(0).NumberItems()];
		for(int i=0;i<SpielPanel.getDynamicObject(0).NumberItems();i++){
			this.amount[i]=new JTextField("0");
			this.anzahl[i]= new JTextField("Anzahl:  ");
			
		}
	}
	
	public void Eingabe(){
		this.eingabe=new String[SpielPanel.getDynamicObject(0).NumberItems()];
		for(int i=0;i<SpielPanel.getDynamicObject(0).NumberItems();i++){
			
			this.eingabe[i]=this.amount[i].getText();	
		}
	}
	public void Wert(){
		this.wert=new int[SpielPanel.getDynamicObject(0).NumberItems()];
		for(int i=0;i<SpielPanel.getDynamicObject(0).NumberItems();i++){
			this.wert[i]=Integer.parseInt(eingabe[i]);	
		}
	}
	public void BuySellEinkauf(){
		this.buy=new JButton[SpielPanel.getDynamicObject(0).NumberItems()];
		this.sell=new JButton[SpielPanel.getDynamicObject(0).NumberItems()];
		
		for(int i=0;i<SpielPanel.getDynamicObject(0).NumberItems();i++){
			this.buy[i]=new JButton("Kaufe!") ;
			
			
			
			this.sell[i]=new JButton("Verkaufe!") ;
				
		}
		this.einkauf=new JButton("LOS!");
		
	}
	

	
	
		

		public void actionPerformed(ActionEvent e) {
			for(int i=0;i<SpielPanel.getDynamicObject(0).NumberItems();i++){
			if(e.getSource()==this.buy[i])
			if(this.wert[i]<=99 || this.wert[i]>=0){
				this.eingabe[i]=this.amount[i].getText();
				this.wert[i]=Integer.parseInt(eingabe[i]);
				anzahl[i].setText("Anzahl:"+wert[i]);
			}
			else this.wert[i]=0;
		}
		
	}
		  
}

	
	
	

