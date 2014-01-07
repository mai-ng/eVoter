package mai.java;


import java.applet.*;
import java.awt.*;

public class HelloApplet extends Applet implements Runnable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private boolean running = true;
	
	Thread thread = new Thread(this);
	Player p;
	
	public void init(){
		setSize(400, 400);
		
		p = new Player();
	}
	
	public void destroy(){ running = false;}
	
	public void start(){ thread.start();}
	
	public void stop(){ running = false;}
	
	public void run(){
		while( running ){
			
			repaint();
			p.update(this);
			
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
			}
		}
	}
	
	public void paint( Graphics g){
		p.paint(g);
	}

}
