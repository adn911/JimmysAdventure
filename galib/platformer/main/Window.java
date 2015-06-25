package galib.platformer.main;

import javax.swing.JFrame;

public class Window {

	public static void main(String args[]) {

		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setTitle("Jimmys Adventure");

		window.setUndecorated(true);
		window.add(new Canvas());
		window.pack();

		window.setResizable(false);
		window.setLocationRelativeTo(null);

		window.setVisible(true);
		
	}
	
	
}
