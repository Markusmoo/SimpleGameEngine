package ca.tonsaker.SimpleGameEngine.main;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class GABEN extends JFrame {

	private JPanel contentPane;
	private Image img;

	/**
	 * Create the frame.
	 */
	public GABEN() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		this.setResizable(true);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		File image2 = new File("gaben.gif");
        try {
			img = ImageIO.read(image2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        this.repaint();
		this.setVisible(true);
	}

	
	public void paint(Graphics g){
		super.paint(g);
		
		g.drawImage(img.getScaledInstance(this.getWidth(), this.getHeight(), 0), 0, 0, null);
	}
}
