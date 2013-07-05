package src.com.github.propa13_orga.gruppe71;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;

import java.util.Arrays;
import java.util.Random;

import javax.swing.JFrame;

/**
* Klasse des Benutzerinterfaces 
*/
public class HUDPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JFrame SpielFenster;
	private DPanel SpielPanel;
	private DDynamic[] DynamicObjects;

	private JLabel[] Spieler_Name;
	private JLabel[] Spieler_Lives;
	private JLabel[] Spieler_Mana;
	private JLabel[] Spieler_Money;
	private JLabel[] Spieler_Weapon;
	private JLabel[] Spieler_Weapon_Icon;
	private JLabel[] Spieler_Weapon_Amount;
	private JLabel[] tasten;
	private String[] keys;
	private Dimension[] h;
	private JLabel[] exp;
	private JLabel[] level;
	private JLabel[] skills;
	
	
	/**
	 * Initialisiert die Klassenattribute
	 */
	public HUDPanel(JFrame pJFrame, DPanel pSpielPanel){
		//Konstruktor
		super();

		//Setze alles auf Start-Wert
		this.SpielFenster = pJFrame;
		this.SpielPanel = pSpielPanel;
		this.DynamicObjects = pSpielPanel.getDynamicObjects();
		this.Spieler_Name = new JLabel[2];	
		this.Spieler_Lives = new JLabel[2];		
		this.Spieler_Mana = new JLabel[2];		
		this.Spieler_Money = new JLabel[2];		
		this.Spieler_Weapon = new JLabel[2];
		this.Spieler_Weapon_Icon = new JLabel[2];
		this.Spieler_Weapon_Amount = new JLabel[2];			
		this.exp=new JLabel[2];
		this.level=new JLabel[2];
		this.skills=new JLabel[2];
		this.InitTasten();
		this.InitKeys();
		this.InitDimension();
		this.AddToPan();
		
		
			
		

		for(int i = 0; i < this.SpielPanel.SpielerModus(); i++){
			// Spieler Name hinzugefuegt
			this.Spieler_Name[i] = new JLabel("Spieler "+(i+1)); // create some stuff
			this.Spieler_Name[i].setFont(new Font("Serif", Font.BOLD, 22));
			this.Spieler_Name[i].setBounds(20, 0+(i*40), 100, 60);
			this.add(this.Spieler_Name[i]);

			
			
			//Level
			this.level[i] = new JLabel("Level: "); // create some stuff
			this.level[i].setFont(new Font("Serif", Font.BOLD, 20));
			this.level[i].setBounds(20, 35+(i*40), 100, 60);
			this.add(this.level[i]);
			
			
			//Skills Unused
			this.skills[i] = new JLabel("Skillpoints Left: "); // create some stuff
			this.skills[i].setFont(new Font("Serif", Font.BOLD, 20));
			this.skills[i].setBounds(170, 35+(i*40), 200, 60);
			this.add(this.skills[i]);
			
			
			//EXP
			this.exp[i] = new JLabel("EXP: "); // create some stuff
			this.exp[i].setFont(new Font("Serif", Font.BOLD, 18));
			this.exp[i].setBounds(420, 35+(i*40), 100, 60);
			this.add(this.exp[i]);
			
			
			//Leben
			Image Image_Lives = Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/bb_live.png");
			Image Image_Lives_Scaled = Image_Lives.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);//Skaliert auf 30x30
			ImageIcon Lives_ImageIcon = new ImageIcon(Image_Lives_Scaled);
			JLabel Lives_Icon = new JLabel(Lives_ImageIcon);
			Lives_Icon.setBounds(120, 15+(i*40), 30, 30);
			this.add(Lives_Icon);//Icon hinzugefuegt
			
			this.Spieler_Lives[i] = new JLabel("3"); // Lebensanzeige
			this.Spieler_Lives[i].setFont(new Font("Arial", Font.BOLD, 18));
			this.Spieler_Lives[i].setBounds(165, 0+(i*40), 100, 60);
			this.add(this.Spieler_Lives[i]);

			//Mana
			Image Image_Mana = Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/bb_ketchup.png");
			Image Image_Mana_Scaled = Image_Mana.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);//Skaliert auf 30x30
			ImageIcon Mana_ImageIcon = new ImageIcon(Image_Mana_Scaled);
			JLabel Mana_Icon = new JLabel(Mana_ImageIcon);
			Mana_Icon.setBounds(220, 15+(i*40), 30, 30);
			this.add(Mana_Icon);//Icon hinzugefuegt

			this.Spieler_Mana[i] = new JLabel("10"); // Manaanzeige
			this.Spieler_Mana[i].setFont(new Font("Arial", Font.BOLD, 18));
			this.Spieler_Mana[i].setBounds(265, 0+(i*40), 100, 60);
			this.add(this.Spieler_Mana[i]);
			

			//Money
			Image Image_Money = Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/bb_money.png");
			Image Image_Money_Scaled = Image_Money.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);//Skaliert auf 30x30
			ImageIcon Money_ImageIcon = new ImageIcon(Image_Money_Scaled);
			JLabel Money_Icon = new JLabel(Money_ImageIcon);
			Money_Icon.setBounds(320, 15+(i*40), 30, 30);
			this.add(Money_Icon);//Icon hinzugefuegt

			this.Spieler_Money[i] = new JLabel("0"); // Geldanzeige
			this.Spieler_Money[i].setFont(new Font("Arial", Font.BOLD, 18));
			this.Spieler_Money[i].setBounds(365, 0+(i*40), 100, 60);
			this.add(this.Spieler_Money[i]);
			
			//Waffen
			this.Spieler_Weapon[i] = new JLabel("Weapon:"); // Waffenanzeige
			this.Spieler_Weapon[i].setFont(new Font("Arial", Font.BOLD, 18));
			this.Spieler_Weapon[i].setBounds(420, 0+(i*40), 100, 60);
			this.add(this.Spieler_Weapon[i]);
			
			//WeaponIcon
			Image Image_Weapon = Toolkit.getDefaultToolkit().getImage("");
			Image Image_Weapon_Scaled = Image_Weapon.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);//Skaliert auf 30x30
			ImageIcon Weapon_ImageIcon = new ImageIcon(Image_Weapon_Scaled);
			this.Spieler_Weapon_Icon[i] = new JLabel(Weapon_ImageIcon);
			this.Spieler_Weapon_Icon[i].setBounds(500, 15+(i*40), 30, 30);
			this.add(this.Spieler_Weapon_Icon[i]);//Icon hinzugefuegt
			

			//Waffen Zaehler
			this.Spieler_Weapon_Amount[i] = new JLabel("0"); // Waffenanzeige
			this.Spieler_Weapon_Amount[i].setFont(new Font("Arial", Font.BOLD, 18));
			this.Spieler_Weapon_Amount[i].setBounds(550, 0+(i*40), 100, 60);
			this.add(this.Spieler_Weapon_Amount[i]);
			
			
			
		}		
		
	}
	
	/**
	 * Zeichnet den gesamten Bildschirm neu und tut das mehrmals in der Sekunde
	 * @param pGraphics Java-Graphicsobjekt
	 */
	public void paintComponent(Graphics pGraphics) {

		super.paintComponent(pGraphics);
		
		for(int i = 0; i < this.SpielPanel.SpielerModus(); i++){
			
			this.Spieler_Lives[i].setText(Integer.toString(this.DynamicObjects[i].getLives())); // Lebensanzeige
			this.Spieler_Mana[i].setText(Integer.toString(this.DynamicObjects[i].getMana())); // Mana
			this.Spieler_Money[i].setText(Integer.toString(this.DynamicObjects[i].getMoney())); // Geld
			this.exp[i].setText("EXP:  "+this.DynamicObjects[i].getExp()); // EXP
			this.level[i].setText("LEVEL:  "+this.DynamicObjects[i].SpielerLevel()); // Level
			this.skills[i].setText("Skillpoints Left:  "+this.DynamicObjects[i].getSkills()); // Level
			
			
			Image Image_Weapon = null;
			
			//WeaponIcon
			if(this.DynamicObjects[i].getActiveItem() == 20){
				Image_Weapon = Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/bb_pKaese.png");
				this.Spieler_Weapon_Amount[i].setText(Integer.toString(this.DynamicObjects[i].AnzahlItems(1))); // Lebensanzeige
			}else if(this.DynamicObjects[i].getActiveItem() == 22){
				Image_Weapon = Toolkit.getDefaultToolkit().getImage("src/src/com/github/propa13_orga/gruppe71/bb_pMesser.png");
				this.Spieler_Weapon_Amount[i].setText(""); // Lebensanzeige
			}else{
				Image_Weapon = Toolkit.getDefaultToolkit().getImage("");
				this.Spieler_Weapon_Amount[i].setText(""); // Lebensanzeige
			}
			
			Image Image_Weapon_Scaled = Image_Weapon.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);//Skaliert auf 30x30
			ImageIcon Weapon_ImageIcon = new ImageIcon(Image_Weapon_Scaled);
			this.Spieler_Weapon_Icon[i].setIcon(Weapon_ImageIcon);
			
		}
		
		//Jetzt kann alles was wir gerade gemalt haben neu gezeichnet werden
		this.repaint();
	}
	/**
	 * Initialisiert die Tasten
	 */
	public String[] InitTasten(){
		this.keys=new String[4];
		
		this.keys[0]="Info:";
		this.keys[1]="Shoot: N";
		this.keys[2]="Change: B";
		this.keys[3]="Magic: M";
		
		return this.keys;
	
	}
	/**
	 * Tastenbelegungen Waffen etc.
	 */
	public void InitKeys(){
		this.tasten=new JLabel[4];
		for(int i=0;i<this.tasten.length;i++){
		//Tasten
		this.tasten[i] = new JLabel(this.keys[i]); // Waffenanzeige
		this.tasten[i].setFont(new Font("Arial", Font.BOLD, 14));
		}
		
	}
	
	/**
	 * Fuegt zum Panel hinzu
	 */
	public void AddToPan(){
		int y=0;
	for(int i=0;i<this.keys.length;i++){
	this.tasten[i].setBounds(y+100,80,h[i].width+40, h[i].height+30);
	this.add(this.tasten[i]);
	y+=100;
	
	}
	}
	
	
	/**
	 * Initialisiert das Seitenverhaeltnis
	 */
	public void InitDimension(){

		this.h=new Dimension[keys.length];
		
		
		for(int i=0;i<this.keys.length;i++){ //Mit Array
		
			this.h[i]= this.tasten[i].getPreferredSize();
			
			}
	
	}
	
}

