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
	public Dimension[] d,b,s,a,p;
	public Dimension r,e;
	public JLabel[] label;
	public JTextField[] amount,anzahl,preis,transaktion;
	public int[] wert,wert2,money;
	public JButton einkauf,reset;
	public JButton[] buy,sell;
	
	
	/**
	 * Der Konstruktor der dann beim Aufprall mit dem Shop
	 * erscheint
	 */
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
		
		JLabel price=new JLabel("Kosten / Einnahmen");
		Dimension size4=price.getPreferredSize();
		price.setBounds(420,90,size4.width,size4.height);
		pan.add(price);
		/**Methoden der Buttons,Labels,Pics etc.
		 * 
		 * 
		 */
		this.Transaktion();
		this.InitPreis();
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
		frame.setTitle("Uncle Buck´s Burger Laden");
		frame.setLocation(300,100);
		frame.setSize(800,500);
		frame.setResizable(false);
		frame.setVisible(true);
	}
	
	/**
	 * Skaliere die Bilder hier
	 * 
	 */
	public void InitPic(){
		this.pics=new ImageIcon[SpielPanel.getDynamicObject(0).NumberItems()];
		for(int i=0;i<SpielPanel.getDynamicObject(0).NumberItems();i++){
		this.pics[i]= new ImageIcon(bild[i]);
		this.pics[i].setImage(this.pics[i].getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT));
		}
	}
	
	/**
	 * Die Dimension wird benötigt um die prefekte Höhe,Breite des Labels,Buttons
	 * festzulegen
	 */
	public void InitDimension(){
		this.d=new Dimension[SpielPanel.getDynamicObject(0).NumberItems()];
		this.b=new Dimension[SpielPanel.getDynamicObject(0).NumberItems()];
		this.s=new Dimension[SpielPanel.getDynamicObject(0).NumberItems()];
		this.a=new Dimension[SpielPanel.getDynamicObject(0).NumberItems()];
		this.p=new Dimension[SpielPanel.getDynamicObject(0).NumberItems()];
		
		for(int i=0;i<SpielPanel.getDynamicObject(0).NumberItems();i++){ //Mit Array
			this.d[i]= this.label[i].getPreferredSize();
			this.b[i]= this.buy[i].getPreferredSize();
			this.s[i]= this.sell[i].getPreferredSize();
			this.a[i]= this.anzahl[i].getPreferredSize();
			this.p[i]= this.preis[i].getPreferredSize();
			
			}
		//Ohne Arrays
		this.r=this.reset.getPreferredSize();
		this.e=this.reset.getPreferredSize();
	}
	
	/**
	 *Initialiesiere die Namen der Items 
	 * 
	 */
	public void InitLabel(){
		this.label=new JLabel[SpielPanel.getDynamicObject(0).NumberItems()];
		
		for(int i=0;i<SpielPanel.getDynamicObject(0).NumberItems();i++){
			this.label[i]= new JLabel(SpielPanel.getDynamicObject(0).getName(i),this.pics[i],JLabel.RIGHT);
			}
	
	}
	/**
	 * Hier werden die Bilder platziert am besten in der Reihenfolge
	 * der Items in DDynamic
	 * @return
	 */
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
	
	/**
	 * Hier werden alle Labels;Buttons etc. im Panel eingefuegt
	 * 
	 */
	public void AddToPan(){
		int y=0;
		for(int i=0;i<SpielPanel.getDynamicObject(0).NumberItems();i++){
			
			//Label mit Bild
			this.label[i].setBounds(20,120+y,this.d[i].width,this.d[i].height);
			this.pan.add(label[i]);
			
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
			
			//Reset
			this.reset.setBounds(320,80,this.r.width,this.r.height);
			this.reset.addActionListener(this);
			this.pan.add(reset);
			
			
			//Anzahl Gesamt Gegenstönde
			this.anzahl[i].setBounds(320,120+y,this.a[i].width+10,this.a[i].height+5);// hier justizieren
			anzahl[i].setEditable(false);
			this.pan.add(anzahl[i]);
			
			//Preis Items
			this.preis[i].setBounds(420,120+y,this.p[i].width+10,this.p[i].height+5);//hier auch mit +10,+5
			preis[i].setEditable(false);
			this.pan.add(preis[i]);
			
			y+=30;
			
		
			}
	}
	
	/**
	 * Zeigt wie vie Gold der Spieler in diesem Zeitpunkt hat
	 * 
	 */
	public void Gold(){
		JTextField money =new JTextField("Vermögen:  "+SpielPanel.getDynamicObject(0).getMoney()+" $$$");
		Dimension size3=money.getPreferredSize();
		money.setBounds(20,40,size3.width,size3.height);
		money.setEditable(false);
		pan.add(money);
	}
	
	/**
	 * Anzahl der Gegenstände zum Kauf oder Verkauf werden hier aufgelistet
	 * 
	 */
	public void Amount(){
		
		this.amount=new JTextField[SpielPanel.getDynamicObject(0).NumberItems()];
		this.preis=new JTextField[SpielPanel.getDynamicObject(0).NumberItems()];
		this.anzahl=new JTextField[SpielPanel.getDynamicObject(0).NumberItems()];
		for(int i=0;i<SpielPanel.getDynamicObject(0).NumberItems();i++){
			this.amount[i]=new JTextField("0");
			this.anzahl[i]= new JTextField("Anzahl:     ");
			this.preis[i]=new JTextField("Total:           ");
			
		}
	}
	
	/**
	 * Hier Bekomme Text
	 * 
	 */
	public void Eingabe(){
		this.eingabe=new String[SpielPanel.getDynamicObject(0).NumberItems()];
		for(int i=0;i<SpielPanel.getDynamicObject(0).NumberItems();i++){
			
			this.eingabe[i]=this.amount[i].getText();	
		}
	}
	
	/**
	 *Bekomme Wert des Strings in Textfield
	 * wandle in int um.
	 */
	public void Wert(){
		this.wert=new int[SpielPanel.getDynamicObject(0).NumberItems()];
		for(int i=0;i<SpielPanel.getDynamicObject(0).NumberItems();i++){
			this.wert[i]=Integer.parseInt(eingabe[i]);	
		}
	}
	
	/**
	 * Button Verkauf Sell und Einkauf
	 * 
	 */
	public void BuySellEinkauf(){
		this.buy=new JButton[SpielPanel.getDynamicObject(0).NumberItems()];
		this.sell=new JButton[SpielPanel.getDynamicObject(0).NumberItems()];
		
		for(int i=0;i<SpielPanel.getDynamicObject(0).NumberItems();i++){
			this.buy[i]=new JButton("Kaufe!") ;
			this.sell[i]=new JButton("Verkaufe!");
			
			
		
				
		}
		this.einkauf=new JButton("LOS!");
		this.reset=new JButton("Reset All");
	}
	

	
	
		
		/**
		 * ActionListener hört auf Button
		 * 
		 */
		public void actionPerformed(ActionEvent e) {
			
			//Buy Interaktion
			for(int i=0;i<SpielPanel.getDynamicObject(0).NumberItems();i++){
				
			if(e.getSource()==this.buy[i])
			if(this.wert[i]<=99 || this.wert[i]>=0){
				this.wert[i]=0;
				this.wert2[i]=0;
				this.eingabe[i]=this.amount[i].getText();
				this.wert[i]=Integer.parseInt(eingabe[i]);
				this.wert2[i]=this.wert[i];
				anzahl[i].setText("Anzahl:"   +wert2[i]);
				this.wert[i]*=this.money[i];
				this.preis[i].setText("Total:   "+this.wert[i]+"$$$");
				
				
			}
			else this.wert[i]=0;
		}
			
			//Sell Interaktion
			for(int i=0;i<SpielPanel.getDynamicObject(0).NumberItems();i++){
				if(e.getSource()==this.sell[i])	
				if(this.wert[i]<=99 || this.wert[i]>=0){
					this.wert[i]=0;
					this.wert2[i]=0;
					this.eingabe[i]=this.amount[i].getText();
					this.wert[i]=Integer.parseInt(eingabe[i]);
					this.wert[i]*=-1;
					this.wert2[i]=this.wert[i];
					this.wert[i]*=this.money[i];
					anzahl[i].setText("Anzahl:"   +wert2[i]);
					preis[i].setText("Total:"     +this.wert[i]+"$$$");
					
					
				}
				else this.wert[i]=0;
			}
			
			for(int i=0;i<SpielPanel.getDynamicObject(0).NumberItems();i++){
			if(e.getSource()==this.reset){
				this.wert[i]=Integer.parseInt(eingabe[i]);
				this.wert[i]=0;
				anzahl[i].setText("Anzahl:"   +wert[i]);
				
				
			}
			
		}	
	}
		
		//Preisübersicht bei PriceList hier Init
		public void InitPreis(){
			this.money=new int[SpielPanel.getDynamicObject(0).NumberItems()];
			this.wert2=new int[SpielPanel.getDynamicObject(0).NumberItems()];
			for(int i=0;i<SpielPanel.getDynamicObject(0).NumberItems();i++){
				this.wert2[i]=0;
			}
			
			this.PriceList();
		}
		public void PriceList(){
				this.money[0]=5;
				this.money[1]=6;
				this.money[2]=7;
				this.money[3]=8;
			    this.money[4]=9;
				this.money[5]=10;
		}
		
		// Hier der Gesamtwert
		public void Transaktion(){
			JTextField transaktion =new JTextField("TOTAL TRANSACTION VALUE:   ");
			Dimension size5=transaktion.getPreferredSize();
			transaktion.setBounds(20,360,size5.width,size5.height);
			transaktion.setEditable(false);
			pan.add(transaktion);
		}
		  
}

	
	
	

