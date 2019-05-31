
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JSlider;
import java.awt.image.*;
import java.awt.event.*;

/**
 * @author MKirkby
 * DO NOT UNTIL I SAY SO.
 */
@SuppressWarnings("serial")
public class ProjectSandStorm extends JFrame {
	Dirk dirk;
	public static void main(String[] args) {
		ProjectSandStorm pss = new ProjectSandStorm();
		pss.setVisible(true);
		pss.setSize(800,600);
	}JSlider slider;
	public ProjectSandStorm() {
		super("Project Sand Storm");
		
		dirk = new Dirk();
		
		slider = new JSlider();
		slider.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent arg0) {
				repaint();
			}
		});
		slider.setMaximum(5);
		slider.setValue(0);
		getContentPane().add(slider, BorderLayout.SOUTH);
		repaint();
	}
	public void paint(Graphics g) {
		dirk.draw(slider.getValue());
		g.drawImage(dirk.buffer,350,250,null);
		super.paint(g);
	}
}
class Dirk {
	public BufferedImage buffer;
	public Dirk() {
		
	}
	public void draw(int level) {
		buffer = new BufferedImage(100,100,BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D)buffer.getGraphics();
		switch(level) {
		case 0:
			g.setColor(Color.black);
			g.drawLine(49,35,52,35);
			break;
		}
	}
}
