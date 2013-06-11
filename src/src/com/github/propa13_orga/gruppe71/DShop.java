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



public class DShop implements ActionListener {

	public DPanel SpielPanel;
	public JFrame frame;
	public JPanel pan;
	public ImageIcon[] pics;
	public ImageIcon pic;
	public String[] bild,eingabe;
	public String dollar;
	public Dimension[] d,b,s,a,p,h;
	public Dimension r,e,c,z;
	public JLabel[] label,hinweise;
	public JTextField[] amount,anzahl,preis;
	public JTextField transaktion,result,pending,inventar;
	public int[] wert,items,money,wert2;
	public JButton einkauf,reset,calcul,play;
	public JButton[] buy,sell;
	public int total,back,gesamt; //Total Transaction and back to DDynamic wert +Items Gesamt
	public String[] namen;
	
	
	/**
	 * Der Konstruktor der dann beim Aufprall mit dem Shop
	 * erscheint
	 */
	public DShop(DPanel panel,int money){
		this.SpielPanel=panel;
		this.frame=new JFrame();
		this.pan=new JPanel();
		pan.setLayout(null);
		this.setTotal(0);
		this.items=new int[SpielPanel.getDynamicObject(0).NumberItems()];
		this.FetchItems(); //Die Items die im Inventar liegen
		this.ItemGesamt();
		this.InitItemStorage();
		this.back=money;//Spielgeld
		
		//Welcome
		
		JTextField info= new JTextField("Willkommen im Shop!");
		Dimension size1=info.getPreferredSize();
		info.setBounds(320,20,size1.width, size1.height);
		info.setEditable(false);
		pan.add(info);
		
		//Das Spielgeld
		result=new JTextField("Verm�gen:  "+this.back+" $$$");
		Dimension size3=result.getPreferredSize();
		result.setBounds(20,40,size3.width,size3.height);
		result.setEditable(false);
		pan.add(result);
		
		//Pending --- Nachricht der Transaktion
		pending=new JTextField("Pending... Warte auf Transaktion");
		Dimension size7=pending.getPreferredSize();
		pending.setBounds(20,400,size7.width,size7.height);
		pending.setEditable(false);
		pan.add(pending);
		
		
		//Info Verkaufen
		JLabel info2=new JLabel("Kaufen und Verkaufen Sie!");
		Dimension size2=info2.getPreferredSize();
		info2.setBounds(310,50,size2.width,size2.height);
		pan.add(info2);
		
		//Menge
		JLabel menge=new JLabel("Menge");
		Dimension size4=menge.getPreferredSize();
		menge.setBounds(70,90,size4.width,size4.height);
		pan.add(menge);
		
		//Kosten Einnahmen
		JLabel price=new JLabel("Kosten / Einnahmen");
		Dimension size5=price.getPreferredSize();
		price.setBounds(420,90,size5.width,size5.height);
		pan.add(price);
		
		//Gesamtwert
		transaktion =new JTextField("TOTAL TRANSACTION VALUE:   "+this.total+"$$$");
		Dimension size6=transaktion.getPreferredSize();
		transaktion.setBounds(20,360,size6.width+50,size6.height);
		transaktion.setEditable(false);
		pan.add(transaktion);
		
		inventar =new JTextField("Items-Gesamt:     "+ this.ItemGesamt()+"Stk.");
		Dimension size8=transaktion.getPreferredSize();
		inventar.setBounds(630,260,150,size8.height);
		inventar.setEditable(false);
		pan.add(inventar);
		
		
		
		/**Methoden der Buttons,Labels,Pics etc.
		 * 
		 * 
		 */
		this.ShopItems(); //6 St�ck gemacht
		
		this.InitPreis();
		
		this.BuySellEinkauf();
		
		this.Amount();
		
		this.Eingabe();
		
		this.Dollar();
		
		this.Bild();
	
		this.InitPic();
		
		this.InitLabel();
		
		this.InitDimension();
		
		this.AddToPan();
		
		this.Wert();
		//
		
		
		//Purchase Au�erhalb der for Shchleife
		this.einkauf.setBounds(300,355,this.e.width,this.e.height);
		this.einkauf.addActionListener(this);
		this.pan.add(einkauf);
		

		//calcul total
		this.calcul.setBounds(580,180,this.c.width,this.c.height);
		this.calcul.addActionListener(this);
		this.pan.add(calcul);
		
		//Reset
		this.reset.setBounds(320,80,this.r.width,this.r.height);
		this.reset.addActionListener(this);
		this.pan.add(reset);
		
		//Continue playing
		this.play.setBounds(580,20,200,this.z.height);
		this.play.addActionListener(this);
		this.pan.add(play);
		
		
		
		System.out.println(this.total);
		
		
		
		//Einstellungen
		pan.setBackground(Color.ORANGE);
		frame.getContentPane().add(pan);
		frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		frame.setTitle("Uncle Buck�s Burger Laden");
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
		this.pic= new ImageIcon(dollar);
		this.pic.setImage(this.pic.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT));
		
	}
	
	/**
	 * Die Dimension wird ben�tigt um die prefekte H�he,Breite des Labels,Buttons
	 * festzulegen
	 */
	public void InitDimension(){
		this.d=new Dimension[SpielPanel.getDynamicObject(0).NumberItems()];
		this.b=new Dimension[SpielPanel.getDynamicObject(0).NumberItems()];
		this.s=new Dimension[SpielPanel.getDynamicObject(0).NumberItems()];
		this.a=new Dimension[SpielPanel.getDynamicObject(0).NumberItems()];
		this.p=new Dimension[SpielPanel.getDynamicObject(0).NumberItems()];
		this.h=new Dimension[SpielPanel.getDynamicObject(0).NumberItems()];
		
		
		for(int i=0;i<SpielPanel.getDynamicObject(0).NumberItems();i++){ //Mit Array
			this.d[i]= this.label[i].getPreferredSize();
			this.b[i]= this.buy[i].getPreferredSize();
			this.s[i]= this.sell[i].getPreferredSize();
			this.a[i]= this.anzahl[i].getPreferredSize();
			this.p[i]= this.preis[i].getPreferredSize();
			this.h[i]= this.preis[i].getPreferredSize();
			
			}
		//Ohne Arrays
		this.r=this.reset.getPreferredSize();
		this.e=this.einkauf.getPreferredSize();
		this.c= this.calcul.getPreferredSize();
		this.z= this.calcul.getPreferredSize();
	}
	
	/**
	 *Initialiesiere die Namen der Items 
	 * und Hinweise zu den Items
	 */
	public void InitLabel(){
		this.label=new JLabel[SpielPanel.getDynamicObject(0).NumberItems()];
		this.hinweise=new JLabel[SpielPanel.getDynamicObject(0).NumberItems()];
		for(int i=0;i<SpielPanel.getDynamicObject(0).NumberItems();i++){
			this.label[i]= new JLabel(this.Name(i),this.pics[i],JLabel.RIGHT);
			this.hinweise[i]= new JLabel(this.Name(i)+"   "+"Preis:  "+ this.money[i]+". Im Inventar:"+this.items[i]+"Stk.!!!",this.pic,JLabel.RIGHT);
			}
	
	}
	
	//Der einsame Dollar String
	public String Dollar(){
		this.dollar=new String();
		this.dollar="src/src/com/github/propa13_orga/gruppe71/bb_money.png";
		return this.dollar;
	}
	/**
	 * Hier werden die Bilder platziert am besten in der Reihenfolge
	 * der Items in DDynamic
	 * @return
	 */
	public String[] Bild(){
		this.bild=new String[SpielPanel.getDynamicObject(0).NumberItems()];
		
		bild[0]="src/src/com/github/propa13_orga/gruppe71/bb_messer.jpg";
		bild[1]="src/src/com/github/propa13_orga/gruppe71/bb_ketchup.png";
		bild[2]="src/src/com/github/propa13_orga/gruppe71/bb_messer.png";
		bild[3]="src/src/com/github/propa13_orga/gruppe71/bb_ketchup.png";
		bild[4]="src/src/com/github/propa13_orga/gruppe71/bb_trank.png";
		bild[5]="src/src/com/github/propa13_orga/gruppe71/bb_messer.jpg";
		
	
		return bild;
	}
	
	/**
	 * Hier werden alle Labels;Buttons etc. im Panel eingefuegt
	 * 
	 */
	public void AddToPan(){
		int y=0;
		int z=0;
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
			
			//Hinweise Pricing etc.
			this.hinweise[i].setBounds(500,250+z,260,120);
			this.pan.add(hinweise[i]);
			
			//Anzahl Gesamt Gegenst�nde
			this.anzahl[i].setBounds(320,120+y,this.a[i].width+25,this.a[i].height+5);// hier justizieren
			anzahl[i].setEditable(false);
			this.pan.add(anzahl[i]);
			
			//Preis Items
			this.preis[i].setBounds(420,120+y,this.p[i].width+50,this.p[i].height+5);//hier auch mit +10,+5
			preis[i].setEditable(false);
			this.pan.add(preis[i]);
			
			y+=30;
			z+=25;
		
			}
	}
	
	/**
	 * Zeigt wie vie Gold der Spieler in diesem Zeitpunkt hat
	 * 
	 */
	
	
	
	/**
	 * Anzahl der Gegenst�nde zum Kauf oder Verkauf werden hier aufgelistet
	 * 
	 */
	public void Amount(){
		
		this.amount=new JTextField[SpielPanel.getDynamicObject(0).NumberItems()];
		this.preis=new JTextField[SpielPanel.getDynamicObject(0).NumberItems()];
		this.anzahl=new JTextField[SpielPanel.getDynamicObject(0).NumberItems()];
		for(int i=0;i<SpielPanel.getDynamicObject(0).NumberItems();i++){
			this.amount[i]=new JTextField("0");
			this.anzahl[i]= new JTextField("Anzahl:     ");
			this.preis[i]=new JTextField("Total:         ");
			
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
			this.wert[i]=0;
			
			
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
		this.einkauf=new JButton("PURCHASE");
		this.reset=new JButton("Reset All");
		this.calcul=new JButton("Calculate Total");
		this.play=new JButton("Zur�ck zum Spiel");
	}
	

	
	
		
		/**
		 * ActionListener h�rt auf Button
		 * 
		 */
		public void actionPerformed(ActionEvent e) {
			
			//Zur�ck zum Spiel
			if(e.getSource()==this.play){
				SpielPanel.getDynamicObject(0).setMoney(this.back);
				for(int i=0;i<SpielPanel.getDynamicObject(0).NumberItems();i++){
				SpielPanel.getDynamicObject(0).setItems(this.wert2[i]);
				}
				frame.dispose();
			}
			
			//Einkauf Purchase Button
			if(e.getSource()==this.einkauf){
				System.out.println(getTotal());
				if(this.back>=(-this.total) && this.total<0){
					this.back+=(this.total);
					result.setText("TOTAL:  "+this.back+"$$$");
					for(int i=0;i<SpielPanel.getDynamicObject(0).NumberItems();i++){
						this.items[i]+=this.wert2[i];
						this.hinweise[i].setText(this.Name(i)+"   "+"Preis:  "+ this.money[i]+". Im Inventar:"+this.items[i]+"Stk.!!!");
						
					}
					inventar.setText("Items-Gesamt:     "+ this.ItemGesamt()+"Stk.");
					pending.setText("Transaktion erfolgreich");
					
				}
				else if(this.total>0){
					this.back+=this.getTotal();
					result.setText("TOTAL:  "+this.back+"$$$");
					for(int i=0;i<SpielPanel.getDynamicObject(0).NumberItems();i++){
						this.items[i]+=this.wert2[i];
						this.hinweise[i].setText(this.Name(i)+"   "+"Preis:  "+ this.money[i]+". Im Inventar:"+this.items[i]+"Stk.!!!");
					}
					inventar.setText("Items-Gesamt:     "+ this.ItemGesamt()+"Stk.");
					pending.setText("Sie h�ufen Geld an!");
				}

				else if(e.getSource()==this.einkauf){
					if(this.back<(-1*this.total) && this.total<0){
						pending.setText("Zu wenig Geld!");
					}
				
				/*else if(e.getSource()==this.einkauf){
					for(int i=0;i<SpielPanel.getDynamicObject(0).NumberItems();i++){
						if(){
							pending.setText("Zu wenig Geld!");
						}
					}*/

				}
		}
				
			
			//Gesamtwert
			if(e.getSource()==this.calcul){
		
				System.out.println(total);
			transaktion.setText("TOTAL TRANSACTION VALUE: "+this.getTotal()+"$$$");
			}
			
			
			//Buy Interaktion
			for(int i=0;i<SpielPanel.getDynamicObject(0).NumberItems();i++){
				
			if(e.getSource()==this.buy[i]){
			
				this.total+=(-1*this.wert[i]);
				
				this.eingabe[i]=this.amount[i].getText();
				try{
				this.wert[i]=Integer.parseInt(eingabe[i]);
				}
				catch(NumberFormatException exc){
					this.total+=(1*this.wert[i]);
					pending.setText("Geben Sie eine Zahl ein");
					break;
				}
				this.wert2[i]=(1*this.wert[i]);
				
				anzahl[i].setText("Anzahl:"   +wert[i]);
				
				this.wert[i]*=-(this.money[i]);
				
				
				
				System.out.println(this.wert[i]);
				
				this.total+=this.wert[i];
				
				this.preis[i].setText("Total: "+this.wert[i]+" $$$");
				
				break;	
			
			}
			
		}	
			
			
			
	
			
	
			//Sell Interaktion
			for(int i=0;i<SpielPanel.getDynamicObject(0).NumberItems();i++){
				if(e.getSource()==this.sell[i])	{
					
					
					this.total+=(-1*this.wert[i]);
					
					this.eingabe[i]=this.amount[i].getText();
					
					try{
						this.wert[i]=Integer.parseInt(eingabe[i]);
						}
						catch(NumberFormatException exc){
							this.total+=(1*this.wert[i]);
							pending.setText("Geben Sie eine Zahl ein");
							break;
						}
					
					if(this.items[i]>=wert[i]){
						
					anzahl[i].setText("Anzahl:"   +wert[i]);
					
					this.wert2[i]=(-1*this.wert[i]);
					
					this.wert[i]*=this.money[i];
				
					System.out.println(this.wert[i]);
					
					this.total+=this.wert[i];
					
					preis[i].setText("Total: "+this.wert[i]+" $$$");
					break;
					}
					else this.wert[i]=0;
						this.wert2[i]=0;
					pending.setText("Zu wenige Items!");
					break;
					
				}
				
				
			}
			
			
			for(int i=0;i<SpielPanel.getDynamicObject(0).NumberItems();i++){
			if(e.getSource()==this.reset){
				
				
				this.wert[i]=0;
				
				this.total=0;
				
				anzahl[i].setText("Anzahl:"   +wert[i]);
				
				preis[i].setText("Total:"     +this.wert[i]+"$$$");
				
				transaktion.setText("TOTAL TRANSACTION VALUE: "+this.total+"$$$");
				
				pending.setText("Currently reseted");
				
				
			}
			
	}
			
}
		
			public int setTotal(int p){
				this.total+=p;
				return this.total;
				
			}
		
			
			

		//Preis�bersicht bei PriceList hier Init
		public void InitPreis(){
			this.money=new int[SpielPanel.getDynamicObject(0).NumberItems()];
			
			
			this.PriceList();
		}
		
		//Preisliste $$$
		public int getTotal(){
			return this.total;
		}
		public void PriceList(){
				this.money[0]=100;
				this.money[1]=20;
				this.money[2]=77;
				this.money[3]=19;
			    this.money[4]=25;
				this.money[5]=50;
		}
		
		public String[] ShopItems(){
			this.namen=new String[SpielPanel.getDynamicObject(0).NumberItems()];
			this.namen[0]="SM";
			this.namen[1]="Heinz";
			this.namen[2]="Dijon";
			this.namen[3]="HP";
			this.namen[4]="ZT A";
			this.namen[5]="ZT B";
			
			return this.namen;
		}
		
	public String Name(int p){
		return this.namen[p];
	}
	public void FetchItems(){
		for(int i=0;i<SpielPanel.getDynamicObject(0).NumberItems();i++){
			this.items[i]=SpielPanel.getDynamicObject(0).AnzahlItems(i);
		}
	}
	//Zwischenspeicherung
	public int[] InitItemStorage(){
		this.wert2=new int[SpielPanel.getDynamicObject(0).NumberItems()];
		for(int i=0;i<SpielPanel.getDynamicObject(0).NumberItems();i++){
			this.wert2[i]=0;
		}
		return this.wert2;
	}
	
	public int ItemGesamt(){
		this.gesamt=0;
		for(int i=0;i<SpielPanel.getDynamicObject(0).NumberItems();i++){
			this.gesamt+=this.items[i];
		}
		return this.gesamt;
	}
	
}
	
	
	

