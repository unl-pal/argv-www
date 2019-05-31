import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class EigenPanel extends JPanel {

	
	
	private Image imageHintergrund;
	
	
	public String pfad = System.getProperty("user.dir") + "//images//";
	
	public EigenPanel(){
		super();
	}
		
	public EigenPanel(int bild){
		super();
		
		if(bild == 1){
			imageHintergrund = new ImageIcon(pfad +"Hintergrund1.jpg").getImage();
		}
		if(bild == 2){
			imageHintergrund = new ImageIcon(pfad +"Hintergrund2.png").getImage();
		}
		if(bild == 3){
			imageHintergrund = new ImageIcon(pfad +"Hintergrund3.jpg").getImage();
		}
		if(bild == 4){
			imageHintergrund = new ImageIcon(pfad +"Hintergrund4.jpg").getImage();
		}		
		
	}

	
	@Override
	public void paintComponent (Graphics g){
		if(this.imageHintergrund != null){
			g.drawImage(imageHintergrund, 0, 0, this.getWidth(), this.getHeight(), null);
		}
	}
	
	
	
}