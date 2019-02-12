package TME.Display;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;
import javax.swing.event.MouseInputAdapter;

import TME.State.State;

@SuppressWarnings("serial")
public class Main extends JFrame{
	
	int x = 1280, y = 720;
	int frames, ticks;
	
	State s;
	
	private void init(){
		//jframe
		setTitle("Tile Map Editor BY: Trevor Crow");
		setSize(x, y);
		setVisible(true);
		setFocusable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		addKeyListener(new kl());
	    addMouseMotionListener(new mousemovement());
	    addMouseListener(new mouseinput());
	    
		//state initialization
		s = new State();
	}
	public Main(){
		init();
		gameloop();
	}
	
	//main game loop
	private void gameloop(){
		while(true){
			long lastTime = System.nanoTime();
			double nsPerTick = 1000000000D / 60D;
			
			frames = 0;
			ticks = 0;
			
			long lastTimer = System.currentTimeMillis();
			double delta = 0;
			while(true){
				long now = System.nanoTime();
				delta += (now - lastTime)/ nsPerTick;
				lastTime = now;
				
				while(delta >= 1){
					ticks++;
					update();
					delta -= 1;
				}
				
				frames++;
				render();
				
				if(System.currentTimeMillis() - lastTimer >= 1000){
					lastTimer += 1000;
					frames = 0;
					ticks = 0;
				}
			}
		}
	}
	
	//logic updater
	private void update(){
		s.update();
	}
	
	//graphics renderer
	private void render(){
		BufferStrategy bs = getBufferStrategy();
		if(bs == null){
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.black);
		g.fillRect(0, 0, x, y);
		s.draw(g);
		
		g.dispose();
		bs.show();
	}
	
	//key-listener
	private class kl extends KeyAdapter{
		public void keyPressed(KeyEvent e){
			s.pressed(e);
		}
		public void keyReleased(KeyEvent e){
		}
	}
	
	//mouse input and mouse movement
	private class mouseinput implements MouseListener{

		public void mouseClicked(MouseEvent e) {
			
		}

		public void mouseEntered(MouseEvent e) {
			
		}
		public void mouseExited(MouseEvent e) {
			
		}
		public void mousePressed(MouseEvent e) {
			s.mPressed(e);
		}
		public void mouseReleased(MouseEvent e) {
			s.mReleased(e);
		}
		
	}
	private class mousemovement extends MouseInputAdapter{
	    public void mouseMoved(MouseEvent e){
	    	s.mMoved(e);
	    }
	    
	    public void mouseDragged(MouseEvent e){
	    	s.mDragged(e);
	    }
	}
	
	public static void main(String[] args){
		new Main();
	}
}
