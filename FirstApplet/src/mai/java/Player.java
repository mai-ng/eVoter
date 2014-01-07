package mai.java;

import java.awt.Color;
import java.awt.Graphics;

public class Player {

	private int y = 200;
	private int radius = 20;
	private int x = 200;

	public void update(HelloApplet a){
		x+=5;
//		y+=5;
	}
	
	public void paint( Graphics g){
		g.setColor(Color.BLUE);
		g.fillRect(x , y, radius*2, radius*2);
		
	}
}
