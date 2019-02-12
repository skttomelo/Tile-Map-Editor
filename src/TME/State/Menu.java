package TME.State;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Menu {
	private int w = 175, h = 50, x = (1280/2) - (w/2), y = (720/2) + (h/2);
	private int tw, th;
	private int bselected = 0;
	private boolean exit = false, edit = false, load = false;
	private Font Title = new Font("Calibri", Font.BOLD, 64), Text = new Font("Calibri", Font.BOLD, 16);
	private FontMetrics fm;
	
	
	public void setLoad(boolean a){
		load = a;
	}
	public boolean getLoad(){
		return load;
	}
	public boolean getEdit(){
		return edit;
	}
	public void setEdit(boolean b){
		edit = b;
	}
	
	public void update(){
		if(exit == true){
			System.exit(0);
		}
	}
	
	public void kPressed(KeyEvent e){
		int key = e.getKeyCode();
		if(key == e.VK_ESCAPE){
			exit = true;
		}
		if(key == e.VK_SPACE){
			if(bselected == 1){
				exit = true;
			}
			if(bselected == 0){
				edit = true;
			}
		}
		if(key == e.VK_UP){
			bselected--;
			if(bselected < 0){
				bselected = 1;
			}
		}
		if(key == e.VK_DOWN){
			bselected++;
			if(bselected > 1){
				bselected = 0;
			}
		}
	}
	
	public void mPressed(MouseEvent e){
		int mbp = e.getButton();
		if(mbp == 1 && bselected == 0 && r1().contains(e.getPoint()) == true){
			edit = true;
		}
		if(mbp == 1 && bselected == 1 && r2().contains(e.getPoint()) == true){
			load = true;
		}
		if(mbp == 1 && bselected == 2 && r3().contains(e.getPoint()) == true){
			exit = true;
		}
		
	}
	public void mMoved(MouseEvent e){
		if(r1().contains(e.getPoint()) == true){
			bselected = 0;
		}
		if(r2().contains(e.getPoint()) == true){
			bselected = 1;
		}
		if(r3().contains(e.getPoint()) == true){
			bselected = 2;
		}
	}
	
	private Rectangle r1(){return new Rectangle(x, y, w, h);}
	private Rectangle r2(){return new Rectangle(x, (y + h) + (h/2), w, h);}
	private Rectangle r3(){return new Rectangle(x, y + ((h + (h/2)) * 2), w, h);}
	public void draw(Graphics g){
		g.setColor(Color.white);
		g.setFont(Title);
		fm = g.getFontMetrics(Title);
		th = fm.getHeight();
		tw = fm.stringWidth("Tile Map Editor");
		g.drawString("Tile Map Editor", x - (tw/2) + (w/2), y - th);
		g.fillRect(x, y, w, h);
		g.fillRect(x, (y + h) + (h/2), w, h);
		g.fillRect(x, y + ((h + (h/2)) * 2), w, h);
		g.setColor(Color.RED);
		if(bselected == 0){g.drawRect(x, y, w, h);}
		if(bselected == 1){g.drawRect(x, (y + h) + (h/2), w, h);}
		if(bselected == 2){g.drawRect(x, y + ((h + (h/2)) * 2), w, h);}
		g.setColor(Color.BLACK);
		g.setFont(Text);
		fm = g.getFontMetrics(Text);
		th = fm.getHeight();
		tw = fm.stringWidth("Edit");
		g.drawString("Edit", (x + (w/2)) - (tw/2), (y + (h/2)) + (th/4));
		tw = fm.stringWidth("Load File");
		g.drawString("Load File", (x + (w/2)) - (tw/2), y + (h * 2) + (th/4));
		tw = fm.stringWidth("Exit");
		g.drawString("Exit", (x + (w/2)) - (tw/2), y + ((h * 4) - (h/2)) + (th/4));
	}
}
