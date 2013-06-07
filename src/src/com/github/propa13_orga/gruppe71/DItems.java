package src.com.github.propa13_orga.gruppe71;

import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DItems extends JFrame {
	
	private static final long serialVersionUID = 1L;
	JLabel p;
	Container s;
	
	
	public DItems() {
		JPanel panel3=new JPanel();
		s=getContentPane();
		s.setLayout(new FlowLayout());
		Icon bug = new ImageIcon( "src/src/com/github/propa13_orga/gruppe71/messer.jpg" );
		p=new JLabel();
		p.setText("Anzahl");
		p.setIcon(bug);
		s.add(p);
		setSize(400, 400);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocation(800, 200) ;
		setResizable(false);
		add(panel3);
		setVisible(true);
		
	}


}
