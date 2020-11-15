import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Pointillism {
	
	JFrame jf;
	DrawPanel dp;
	
	public int w, h;
	
	final static int bound = 10;

	PointillismArt p;

	int lowerBound = 166;
	int pixelSize = 5;
	
	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("INVALID ARGUMENTS\nUSAGE: java -jar <jar_file>.jar <image_to_convert>");
			return;
		}
		Pointillism point = new Pointillism(args[0]);
		point.run();
	}

	public Pointillism(String file) {
		p = new PointillismArt(file, bound);
	}
	
	private void run() {
		
		
		p.imageToPoints(pixelSize, lowerBound);
		
		jf = new JFrame("TEST");
		jf.setDefaultCloseOperation(jf.EXIT_ON_CLOSE);
		
		dp = new DrawPanel();
		jf.getContentPane().add(BorderLayout.CENTER, dp);
		
		jf.setSize(p.w + bound * 2, p.h + 23 + bound * 2);
		jf.setLocation(875, 50);
		jf.setVisible(true);
		jf.setResizable(false);
		jf.addKeyListener(new KeyListen());
		
		w = jf.getWidth();
		h = jf.getHeight() - 20;
		while (true) {
			jf.repaint();
			try {
				Thread.sleep(150);
			} catch (Exception exc) {
				System.out.println(1);
			}
		}
	}
	
	private void draw(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0,  0, w,  h);
		//g.drawImage(p.photo, 0, 0, null);
		g.drawImage(p.art, 0, 0, null);
	}
	
	private void aKeyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
			pixelSize++;
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
			pixelSize--;
		if (e.getKeyCode() == KeyEvent.VK_UP)
			lowerBound++;
		if (e.getKeyCode() == KeyEvent.VK_DOWN)
			lowerBound--;
		
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			p.makePhoto("test.jpg");
			return;
		}
		
		
		pixelSize = Math.max(1, pixelSize);
		lowerBound = Math.max(0, Math.min(180, lowerBound));
		
		p.imageToPoints(pixelSize, lowerBound);
		System.out.println(lowerBound + " : " + pixelSize);
	}
	
	public class DrawPanel extends JPanel {
		public void paintComponent(Graphics g) {
			draw(g);
		}
	}
	
	public class KeyListen extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			aKeyPressed(e);
		}
	}
	
}