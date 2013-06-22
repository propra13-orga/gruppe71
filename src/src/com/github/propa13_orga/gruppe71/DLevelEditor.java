package src.com.github.propa13_orga.gruppe71;

import java.awt.FileDialog;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


import java.awt.event.KeyAdapter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class DLevelEditor extends JFrame{
	

    private javax.swing.Timer timer;
    private int xPos, yPos;
	
	/**
	 * Initialisiert das LevelFenster Fenster
	 */
	public DLevelEditor(){
		
		//Fenster-Eigenschaften werden gesetzt
		this.setSize(610, 540);
		this.setLocation(350, 150) ;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("DungeonCrawler");
		this.setResizable(false);
		this.setLayout(null);

		
		final JFrame TmpFenster = this;

		//Panel zum Fenster hinzugefuegt, hier wird das LevelFenster gemalt
		final DLevelPanel LevelPanel = new DLevelPanel(this);
		LevelPanel.setBounds(0, 70, 610, 360);
		LevelPanel.setLayout(null);
		
		JPanel UIPanel = new JPanel();
		UIPanel.setBounds(0, 360, 610, 180);
		UIPanel.setLayout(null);
		

		final JLabel LabelCurrentLevel = new JLabel("Level:    "+LevelPanel.CurrentLevel); // create some stuff
		LabelCurrentLevel.setFont(new Font("Serif", Font.BOLD, 16));
		LabelCurrentLevel.setBounds(467, 5, 80, 60);
		UIPanel.add(LabelCurrentLevel);
		
		setTitle("Level "+LevelPanel.CurrentLevel);

		final JLabel LabelCurrentSection = new JLabel("Abschn: "+LevelPanel.CurrentLevelSection); // create some stuff
		LabelCurrentSection.setFont(new Font("Serif", Font.BOLD, 16));
		LabelCurrentSection.setBounds(467, 40, 80, 60);
		UIPanel.add(LabelCurrentSection);
		

		final JLabel LabelLevelorganize = new JLabel("Level ordnen"); // create some stuff
		LabelLevelorganize.setFont(new Font("Serif", Font.BOLD, 16));
		LabelLevelorganize.setBounds(460, 75, 90, 60);
		UIPanel.add(LabelLevelorganize);

		final JLabel LabelAnzahlLevel = new JLabel("Levelanzahl: "+LevelPanel.anzahlLevel()); // create some stuff
		LabelAnzahlLevel.setFont(new Font("Serif", Font.BOLD, 16));
		LabelAnzahlLevel.setBounds(300, 5, 150, 60);
		UIPanel.add(LabelAnzahlLevel);
		

		JButton bNew = new JButton("Neu") ;
		bNew.setBounds(20,20, 60, 30) ;
		bNew.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				LevelPanel.createNewLevel();
				LevelPanel.loadLevel((LevelPanel.anzahlLevel()-1));
				LabelCurrentLevel.setText("Level:    "+LevelPanel.getCurrentLevel());
				LabelCurrentSection.setText("Abschn: "+LevelPanel.getCurrentLevelSection());
				LabelAnzahlLevel.setText("Levelanzahl: "+LevelPanel.anzahlLevel());

				setTitle("Level "+LevelPanel.CurrentLevel);
			}
		});
		UIPanel.add(bNew);
		

		JButton bSave = new JButton("Speichern") ;
		bSave.setBounds(90,20, 130, 30) ;
		bSave.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				LevelPanel.saveLevel(LevelPanel.CurrentLevel);
			}
		});
		UIPanel.add(bSave);
		
		JButton bImport = new JButton("Import") ;
		bImport.setBounds(20,55, 85, 30) ;
		bImport.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {

		        FileDialog OeffnenDialog = new FileDialog(TmpFenster, "Oeffnen", FileDialog.LOAD);
		        OeffnenDialog.setVisible(true);
		        String FilePath = OeffnenDialog.getDirectory() + OeffnenDialog.getFile();
		        
		        File LevelFile = new File("src/src/com/github/propa13_orga/gruppe71/level"+ Integer.toString(LevelPanel.CurrentLevel)+".txt"); //Dateiname der LevelDatei
				File LevelNewFile = new File(FilePath); //Zu oeffnende Datei
				FileChannel Level = null;
				FileChannel LevelNew = null;
				
				try {

				
				    	Level = new FileInputStream(LevelNewFile).getChannel();
				    	LevelNew = new FileOutputStream(LevelFile).getChannel();
				    	LevelNew.transferFrom(Level, 0, Level.size());

				    	Level.close();
				    	LevelNew.close();
				    	
			    } catch (IOException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
			    
			    LevelPanel.loadLevel(LevelPanel.CurrentLevel);

			}
		});
		UIPanel.add(bImport);
		

		JButton bExport = new JButton("Export") ;
		bExport.setBounds(115,55, 85, 30) ;
		bExport.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {

		        FileDialog SpeicherDialog = new FileDialog(TmpFenster, "Speichern", FileDialog.SAVE);
		        SpeicherDialog.setVisible(true);
		        String FilePath = SpeicherDialog.getDirectory() + SpeicherDialog.getFile();
		        
		        File LevelFile = new File("src/src/com/github/propa13_orga/gruppe71/level"+ Integer.toString(LevelPanel.CurrentLevel)+".txt"); //Dateiname der LevelDatei
				File LevelNewFile = new File(FilePath); //Zu speichernde Datei
				FileChannel Level = null;
				FileChannel LevelNew = null;
				
				try {

				
				    	Level = new FileInputStream(LevelFile).getChannel();
				    	LevelNew = new FileOutputStream(LevelNewFile).getChannel();
				    	LevelNew.transferFrom(Level, 0, Level.size());

				    	Level.close();
				    	LevelNew.close();
				    	
			    } catch (IOException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}

			}
		});
		UIPanel.add(bExport);
		
		JButton bCurrentLevelLeft = new JButton("<") ;
		bCurrentLevelLeft.setBounds(410,20, 41, 30) ;
		bCurrentLevelLeft.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				LevelPanel.saveLevel(LevelPanel.CurrentLevel);
				LevelPanel.loadPreviousLevel();
				LabelCurrentLevel.setText("Level:    "+LevelPanel.getCurrentLevel());

				setTitle("Level "+LevelPanel.CurrentLevel);
			}
		});
		UIPanel.add(bCurrentLevelLeft);
		
		JButton bCurrentLevelRight = new JButton(">") ;
		bCurrentLevelRight.setBounds(555,20, 41, 30) ;
		bCurrentLevelRight.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				LevelPanel.saveLevel(LevelPanel.CurrentLevel);
				LevelPanel.loadNextLevel();
				LabelCurrentLevel.setText("Level:    "+LevelPanel.getCurrentLevel());

				setTitle("Level "+LevelPanel.CurrentLevel);
			}
		});
		UIPanel.add(bCurrentLevelRight);
		
		

		JButton bCurrentSectionLeft = new JButton("<") ;
		bCurrentSectionLeft.setBounds(410,55, 41, 30) ;
		bCurrentSectionLeft.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				LevelPanel.saveLevel(LevelPanel.CurrentLevel);
				LevelPanel.loadPreviousLevelSection();
				LabelCurrentSection.setText("Abschn: "+LevelPanel.getCurrentLevelSection());
			}
		});
		UIPanel.add(bCurrentSectionLeft);

		JButton bCurrentSectionRight = new JButton(">") ;
		bCurrentSectionRight.setBounds(555,55, 41, 30) ;
		bCurrentSectionRight.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				LevelPanel.saveLevel(LevelPanel.CurrentLevel);
				LevelPanel.loadNextLevelSection();
				LabelCurrentSection.setText("Abschn: "+LevelPanel.getCurrentLevelSection());
			}
		});
		UIPanel.add(bCurrentSectionRight);

		JButton bLevelOrganizeLeft = new JButton("<") ;
		bLevelOrganizeLeft.setBounds(410,90, 41, 30) ;
		bLevelOrganizeLeft.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				LevelPanel.saveLevel(LevelPanel.CurrentLevel);
				LevelPanel.moveLevelLeft();
				LabelCurrentLevel.setText("Level:    "+LevelPanel.getCurrentLevel());
				LabelCurrentSection.setText("Abschn: "+LevelPanel.getCurrentLevelSection());
				setTitle("Level "+LevelPanel.CurrentLevel);
			}
		});
		UIPanel.add(bLevelOrganizeLeft);
		
		JButton bLevelOrganizeRight = new JButton(">") ;
		bLevelOrganizeRight.setBounds(555,90, 41, 30) ;
		bLevelOrganizeRight.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				LevelPanel.saveLevel(LevelPanel.CurrentLevel);
				LevelPanel.moveLevelRight();
				LabelCurrentLevel.setText("Level:    "+LevelPanel.getCurrentLevel());
				LabelCurrentSection.setText("Abschn: "+LevelPanel.getCurrentLevelSection());
				setTitle("Level "+LevelPanel.CurrentLevel);
			}
		});
		UIPanel.add(bLevelOrganizeRight);


		
		final JLabel LabelTypName = new JLabel("Typ: "); // create some stuff
		LabelTypName.setFont(new Font("Serif", Font.BOLD, 16));
		LabelTypName.setBounds(20, 80, 60, 60);
		UIPanel.add(LabelTypName);
		

		final JLabel LabelTyp = new JLabel("Nichts"); // create some stuff
		LabelTyp.setFont(new Font("Arial", Font.ITALIC, 15));
		LabelTyp.setBounds(60, 80, 180, 60);
		UIPanel.add(LabelTyp);
		
		LevelPanel.addMouseListener(new MouseListener() {
	            @Override
	            public void mouseReleased(MouseEvent e) {
	                System.out.println(":MOUSE_RELEASED_EVENT:");
	            }
	            @Override
	            public void mousePressed(MouseEvent e) {
	            	xPos = e.getX();
	                yPos = e.getY();
	                
	                xPos = ((xPos - (xPos % 30))/30);
	                yPos = ((yPos - (yPos % 30))/30);
	                

	                if(xPos < 20 && yPos < 12){
	                
	                if(e.isMetaDown() == true) //Rechtsklick?
		                LevelPanel.getStaticObject(xPos, yPos).setType(LevelPanel.previousType((LevelPanel.getStaticObject(xPos, yPos).getType())));
	                else //Linksklick
	                LevelPanel.getStaticObject(xPos, yPos).setType(LevelPanel.nextType((LevelPanel.getStaticObject(xPos, yPos).getType())));
	                
	                String TmpName = LevelPanel.DigittoName(LevelPanel.getStaticObject(xPos, yPos).getType());
	                
	                LabelTyp.setText(TmpName);
	                }
	               
	            }
	            @Override
	            public void mouseExited(MouseEvent e) {
	                System.out.println(":MOUSE_EXITED_EVENT:");
	            }
	            @Override
	            public void mouseEntered(MouseEvent e) {
	                System.out.println(":MOUSE_ENTER_EVENT:"); 
	            }
	            @Override
	            public void mouseClicked(MouseEvent e) {
	                System.out.println(":MOUSE_CLICK_EVENT:");
	            }
	        });
		LevelPanel.addMouseMotionListener(new MouseMotionListener() {
		      public void mouseDragged(MouseEvent e) {
		    	    xPos = e.getX();
	                yPos = e.getY();
	                
	                xPos = ((xPos - (xPos % 30))/30);
	                yPos = ((yPos - (yPos % 30))/30);
	                
	                String TmpName;
	                if(xPos < 20 && yPos < 12)
	                	TmpName = LevelPanel.DigittoName(LevelPanel.getStaticObject(xPos, yPos).getType());
	                else
	                	TmpName = "Nichts";
	                
	                LabelTyp.setText(TmpName);
	               
			      }

			      public void mouseMoved(MouseEvent e) {
			    	  	xPos = e.getX();
		                yPos = e.getY();
		                
		                xPos = ((xPos - (xPos % 30))/30);
		                yPos = ((yPos - (yPos % 30))/30);
		                
		                String TmpName;
		                if(xPos < 20 && yPos < 12)
		                	TmpName = LevelPanel.DigittoName(LevelPanel.getStaticObject(xPos, yPos).getType());
		                else
		                	TmpName = "Nichts";
		                
		                LabelTyp.setText(TmpName);
		               
			      }
			    }
				
		
		
		);
		
		this.setContentPane(LevelPanel);
		
		this.add(UIPanel);
		
		//Gebe dem Panel Focus, so dass es Tasteneingaben erkennt
		LevelPanel.setFocusable( true );
	   
		// Alles fertig also kann das Fenster gemalt werden
		this.setVisible(true);
	}

}
