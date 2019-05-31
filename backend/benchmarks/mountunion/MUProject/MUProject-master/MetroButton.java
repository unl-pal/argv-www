// Metro Buttons - to make JButtons look pretty
// Hacked together with love by Kyle Dreger
// Version: 1.0.0
// URL: github.com/mu-socs/mu-project

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;

public class MetroButton extends JButton implements MouseListener{
    int defaultFontSize = 15;     
    Font defaultFont = new Font("Gill Sans MT", Font.BOLD, defaultFontSize);
    Color textColor = Color.decode("#ffffff");
    Color hoverColor;

    public MetroButton(String s, Color bg) {
        s = s.toUpperCase();
        this.setFocusPainted(false);
        this.setText(s);
        this.setBorder(null);
        this.setBackground(bg);
        this.setHoverColor(bg.darker());
        this.setForeground(textColor);
        this.hoverColor = bg.darker();
        this.setFont(defaultFont);
        this.setOpaque(true);
        addMouseListener(this);
    }

    public void setHoverColor(Color color) {
        hoverColor = color; 
    }

    public void setFontSize(int size) {
        Font newDefaultFont = new Font("Gill Sans MT", Font.BOLD, size);
        this.setFont(newDefaultFont);
    }

    @Override public void mouseClicked(MouseEvent me) {}
    @Override public void mouseReleased(MouseEvent me) {}
    @Override public void mousePressed(MouseEvent me) {}
    
    // Add a nice hover effect
    @Override
    public void mouseEntered(MouseEvent e) { this.setBackground(this.hoverColor); }
    @Override
    public void mouseExited(MouseEvent e) { 
        Color normalState = hoverColor.brighter();
        this.setBackground(normalState); 
    }
}
    