package src.com.github.propa13_orga.gruppe71;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JFrame;



public class DRules extends JFrame{
	
	
	private static final long serialVersionUID = 1L;
	
	
	public DRules() {
		String content="Das ist ein Test\n asdasd \n \n \n sadasd asdasd\n";
		JFrame frame=new JFrame();
		JPanel panel2=new JPanel();
		frame.setSize(300, 300);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setTitle("Dungeon Crawler Rules");
		frame.setLocation(1000, 200) ;
		frame.setResizable(false);
		JTextArea area=new JTextArea(content,5,20);
		frame.add(panel2);
		frame.add(area);
		frame.setVisible(true);
	}

}
