
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

	/**
	* Klasse fuer die Faehigkeiten 
	*/
	public class NSkill implements ActionListener {
		
		private NPanel SpielPanel;
		public JFrame frame;
		public JPanel pan;
		public JTextField points;
		public JLabel[] rank;
		public JTextField passive;
		public JLabel hast,hast2,hast3;//bewirkt mehr Exp bei Mobs toeten
		public JLabel greed,greed2,greed3;//mehr coins
		public JLabel crit,crit2,crit3;
		public JButton wisdom,greed4,crit4;//wisdom gehoert zu hast...
		public JLabel lifesteal,lifesteal2,lifesteal3;
		public JButton lifesteal4;
		public JTextField ultiminfo;
		
		
		public String[] rank2;
		
		
		
		
		public NSkill(NPanel panel){
			this.SpielPanel=panel;
			this.frame = new JFrame();
			pan = new JPanel();
			pan.setLayout(null);
			//Init Objekte
			
			
			//Titel JTextField
			JTextField info= new JTextField("Faehigkeiten Upgrade!");
			Dimension size1=info.getPreferredSize();
			info.setBounds(200,20,size1.width, size1.height);
			info.setEditable(false);
			pan.add(info);
			
			//Max Rank
			JTextField info2= new JTextField("Max Rank for all abilities is: 5");
			Dimension size2=info.getPreferredSize();
			info2.setBounds(340,50,size2.width+50, size2.height);
			info2.setEditable(false);
			pan.add(info2);
			
			//Ultima Info
			JTextField ultimainfo= new JTextField("Ultima needs Rank 5 of WS/GR/HE");
			Dimension size40=info.getPreferredSize();
			ultimainfo.setBounds(340,100,size40.width+150, size40.height);
			ultimainfo.setEditable(false);
			pan.add(ultimainfo);
			
			
			this.points= new JTextField("Total Skillpoints Left: "+SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).getSkills());
			Dimension size3=points.getPreferredSize();
			points.setBounds(20,50,size3.width+5, size3.height);
			points.setEditable(false);
			pan.add(points);
			
			this.passive= new JTextField("Passive Faehigkeiten:");
			Dimension size4=points.getPreferredSize();
			passive.setBounds(20,100,size4.width-5, size4.height);
			passive.setEditable(false);
			pan.add(passive);
			
			//Hast ... Mehr EXP Rank 0
			this.hast= new JLabel("1.WISDOM-> MEHR EXP");
			Dimension size5=hast.getPreferredSize();
			hast.setBounds(20,140,size5.width, size5.height);
			pan.add(hast);
			
			
			this.hast2= new JLabel("Current Rank:  "+SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).getCurrentRank(0));
			Dimension size7=hast.getPreferredSize();
			hast2.setBounds(20,160,size7.width, size7.height);
			pan.add(hast2);
			
			this.hast3= new JLabel("Bonus:+  "+SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).getCurrentWisdom(SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).getCurrentRank(0))+"  EXP");
			Dimension size8=hast.getPreferredSize();
			hast3.setBounds(20,180,size8.width, size8.height);
			pan.add(hast3);
			
			//Button Wisdom
			this.wisdom= new JButton("UPGRADE");
			Dimension size9=hast.getPreferredSize();
			wisdom.setBounds(20,200,size9.width, size9.height);
			this.wisdom.addActionListener(this);
			pan.add(wisdom);
			
			
			//Greed; Coins Rank 1
			this.greed= new JLabel("2.Greed-> MEHR COINS");
			Dimension size6=greed.getPreferredSize();
			greed.setBounds(20,240,size6.width, size6.height);
			pan.add(greed);
			
			this.greed2= new JLabel("Current Rank:  "+SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).getCurrentRank(1));
			Dimension size10=greed2.getPreferredSize();
			greed2.setBounds(20,260,size10.width, size10.height);
			pan.add(greed2);
			
			this.greed3= new JLabel("Bonus:+ "+SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).getCurrentWisdom(SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).getCurrentRank(1))+" Coins");
			Dimension size11=greed3.getPreferredSize();
			greed3.setBounds(20,280,size11.width+20, size11.height);
			pan.add(greed3);
			
			//Button Wisdom
			this.greed4= new JButton("UPGRADE");
			Dimension size12=greed4.getPreferredSize();
			greed4.setBounds(20,300,size12.width, size12.height);
			this.greed4.addActionListener(this);
			pan.add(greed4);
			
		   // Crit Rank 2
			
			this.crit= new JLabel("3.HAWK EYE-> CRIT");
			Dimension size13=crit.getPreferredSize();
			crit.setBounds(340,140,size13.width, size13.height);
			pan.add(crit);
					
			this.crit2= new JLabel("Current Rank:  "+SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).getCurrentRank(2));			Dimension size14=crit2.getPreferredSize();
			crit2.setBounds(340,160,size14.width, size14.height);
			pan.add(crit2);
					
			this.crit3= new JLabel("Bonus:+ "+SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).getCurrentCrit(SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).getCurrentRank(2))+"% Crit ");
			Dimension size15=crit3.getPreferredSize();
			crit3.setBounds(340,180,size15.width+20, size15.height);
			pan.add(crit3);
					
			//Button Crit
			this.crit4= new JButton("UPGRADE");
			Dimension size16=crit4.getPreferredSize();
			crit4.setBounds(340,200,size16.width, size16.height);
			this.crit4.addActionListener(this);
			pan.add(crit4);
			
			// Ultima Lifesteal Rank 3 Max 1.
			
			this.lifesteal= new JLabel("4.BLOOD LUST(ULTIMA)");
			Dimension size17=lifesteal.getPreferredSize();
			lifesteal.setBounds(340,240,size17.width, size17.height);
			pan.add(lifesteal);
					
			this.lifesteal2= new JLabel("Current Rank:  "+SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).getCurrentRank(3));
			Dimension size18=lifesteal2.getPreferredSize();
			lifesteal2.setBounds(340,260,size18.width, size18.height);
			pan.add(lifesteal2);
					
			this.lifesteal3= new JLabel("Bonus: 50% Health Restore per kill");
			Dimension size19=lifesteal3.getPreferredSize();
			lifesteal3.setBounds(340,280,size19.width+70, size19.height);
			pan.add(lifesteal3);
					
			//Button lifesteal
			this.lifesteal4= new JButton("GET ULTIMA!");
			Dimension size20=lifesteal4.getPreferredSize();
			lifesteal4.setBounds(340,300,size20.width, size20.height);
			this.lifesteal4.addActionListener(this);
			pan.add(lifesteal4);
			
			
			
			
			
			
			
			
			
			
			
			

			//Eigenschaften Frame
			frame.setTitle("Faehigkeiten");
			frame.getContentPane().add(pan);
			pan.setBackground(Color.ORANGE);
			frame.setLocation(350,150);
			frame.setSize(550,400);
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setResizable(false);
			frame.setVisible(true);
		}
		
		/**
		 * ActionListener hoert auf Button
		 * 
		 */
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==this.wisdom){// rank 0 Array WISDOM
				try{
				if(SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).getSkills()>=1 && SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).getCurrentRank(0)<=5){//Hier
					SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).setSkills(-1);
					SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).setCurrentRank(0,1);
					SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).setCurrentWisdom(0,SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).getCurrentRank(0));
					//Changes
					this.points.setText("Total Skillpoints Left: "+SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).getSkills());
					this.hast2.setText("Current Rank:  "+SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).getCurrentRank(0));
					this.hast3.setText("Bonus:+  "+SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).getCurrentWisdom(SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).getCurrentRank(0))+"EXP");
				}
				}
				catch(ArrayIndexOutOfBoundsException ex){
					SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).setCurrentRank(0,-1);
					SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).setSkills(1);//+1 wieder
				}
			}
				
				if(e.getSource()==this.greed4){// rank 1 Array GREED
					try{
					if(SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).getSkills()>=1 && SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).getCurrentRank(1)<=5){//Hier
						SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).setSkills(-1);
						SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).setCurrentRank(1,1);
						SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).setCurrentGreed(0,SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).getCurrentRank(1));
						//Changes
						this.points.setText("Total Skillpoints Left: "+SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).getSkills());
						this.greed2.setText("Current Rank:  "+SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).getCurrentRank(1));
						this.greed3.setText("Bonus:+  "+SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).getCurrentGreed(SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).getCurrentRank(1))+" Coins");
						
						
					}
					}
					catch(ArrayIndexOutOfBoundsException ex){
						SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).setCurrentRank(1,-1);
						SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).setSkills(1);//+1 wieder
						System.out.println(SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).getCurrentRank(1));
					}
				
				
		}
				
				if(e.getSource()==this.crit4){// rank 2 Array crit
					try{
					if(SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).getSkills()>=2 && SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).getCurrentRank(2)<=5){//Hier
						SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).setSkills(-2);
						SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).setCurrentRank(2,1);
						SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).setCurrentCrit(0,SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).getCurrentRank(2));
						//Changes
						this.points.setText("Total Skillpoints Left: "+SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).getSkills());
						this.crit2.setText("Current Rank:  "+SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).getCurrentRank(2));
						this.crit3.setText("Bonus:+  "+SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).getCurrentCrit(SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).getCurrentRank(2))+" % Crit");
						
						
					}
					}
					catch(ArrayIndexOutOfBoundsException ex){
						SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).setCurrentRank(2,-1);
						SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).setSkills(2);//+2 wieder
						System.out.println(SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).getCurrentRank(2));
					}
				
					
				
		}
		
		

		
		if(e.getSource()==this.lifesteal4){// rank 2 Array crit
			try{
			if(SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).getCurrentRank(1)==5 && SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).getCurrentRank(2)==5 && SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).getCurrentRank(0)==5 && SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).getCurrentRank(3)<1){
				
				SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).setCurrentRank(3,1);
				SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).setUltima(true);
				//Changes
				
				this.lifesteal2.setText("Current Rank:  "+SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).getCurrentRank(3));
				
				
				
			}
			}
			catch(ArrayIndexOutOfBoundsException ex){
				SpielPanel.getDynamicObject(SpielPanel.getPlayerIndex()).setCurrentRank(3,-1);
				
			}
		
			
		
	}
		}
		
		
}
