package TME.State;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class LevelSizeSelector {
	
	private int w = 175, h = 50,x = (1280/2) - (w/2), y = (720/2) + (h/2);
	private int ms;
	private int bselected = 0;
	private boolean back = false, bclicked = false;
	
	public boolean getBack(){
		return back;
	}
	public void setBack(boolean b){
		back = b;
	}
	public boolean getClicked(){
		return bclicked;
	}
	public void setClicked(boolean b){
		bclicked = b;
	}
	
	public Integer getMapSize(){
		return ms;
	}
	
	FontMetrics fm;
	private Font Title = new Font("Calibri", Font.BOLD, 64), Text = new Font("Calibri", Font.BOLD, 16);
	
	//Font Metrics Algorithm
	private int tw, th;
	private void FMA(Graphics g, Color c, FontMetrics fm, Font f, String s, int factorX, int factorY, int x, int y){
		g.setColor(c);
		g.setFont(f);
		fm = g.getFontMetrics(f);
		tw = fm.stringWidth(s);
		th = fm.getHeight();
		g.drawString(s, x - (tw/factorX), y + (th/factorY));
	}
	//Box creator
	private void dBox(Graphics g, Color c, int x, int y, int w, int h){
		g.setColor(c);
		g.fillRect(x, y, w, h);
	}
	//selected Box creator
	private void lBox(Graphics g, Color c, int x, int y, int w, int h){
		g.setColor(c);
		g.drawRect(x, y, w, h);
	}
	
	public void kPressed(KeyEvent e){
		int key = e.getKeyCode();
		if(key == e.VK_ESCAPE){
			back = true;
		}
		if(key == e.VK_SPACE){
			if(bselected == 3){
				back = true;
			}
			if(bselected == 2){
				ms = 3;
			}
			if(bselected == 1){
				ms = 2;
			}
			if(bselected == 0){
				ms = 1;
			}
		}
		if(key == e.VK_UP){
			bselected--;
			if(bselected < 0){
				bselected = 3;
			}
		}
		if(key == e.VK_DOWN){
			bselected++;
			if(bselected > 3){
				bselected = 0;
			}
		}
	}
	public void mPressed(MouseEvent e){
		int mbp = e.getButton();
		if(mbp == 1 && bselected == 0 && r1().contains(e.getPoint()) == true){
			ms = 1;
			bclicked = true;
		}
		if(mbp == 1 && bselected == 1 && r2().contains(e.getPoint()) == true){
			ms = 2;
			bclicked = true;
		}
		if(mbp == 1 && bselected == 2 && r3().contains(e.getPoint()) == true){
			ms = 3;
			bclicked = true;
		}
		if(mbp == 1 && bselected == 3 && r4().contains(e.getPoint()) == true){
			back = true;
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
		if(r4().contains(e.getPoint()) == true){
			bselected = 3;
		}
	}
	
	public Rectangle r1(){return new Rectangle(x, y, w, h);}
	public Rectangle r2(){return new Rectangle(x, y + (h + (h/2)), w, h);}	
	public Rectangle r3(){return new Rectangle(x, y + ((h * 2) + (h)), w, h);}	
	public Rectangle r4(){return new Rectangle(x, y + ((h * 2) + (h * 2) + (h/2)), w, h);}	
	public void draw(Graphics g){
		//title
		FMA(g, Color.WHITE, fm, Title, "Pick A Map Size", 2, 1, x + (w/2), (y - (h/2)) - (720/4));
		//10x10
		dBox(g, Color.WHITE, x, y, w, h);
		FMA(g, Color.black, fm, Text, "10 x 10", 2, 4, x + (w/2), y + (h/2));
		//20x20
		dBox(g, Color.WHITE, x, y + (h + (h/2)), w, h);
		FMA(g, Color.black, fm, Text, "20 x 20", 2, 4, x + (w/2), y + (h/2) + (h + (h/2)));
		//30x30
		dBox(g, Color.WHITE, x, y + ((h * 2) + (h)), w, h);
		FMA(g, Color.black, fm, Text, "30 x 30", 2, 4, x + (w/2), y + (h/2) + ((h * 2) + (h)));
		//back to main menu
		dBox(g, Color.WHITE, x, y + ((h * 2) + (h * 2) + (h/2)), w, h);
		FMA(g, Color.black, fm, Text, "Back", 2, 4, x + (w/2), y + (h/2) + ((h * 2) + (h * 2) + (h/2)));
		if(bselected == 0){
			lBox(g, Color.RED, x, y, w, h);
		}
		if(bselected == 1){
			lBox(g, Color.RED, x, y + (h + (h/2)), w, h);
		}
		if(bselected == 2){
			lBox(g, Color.RED, x, y + ((h * 2) + (h)), w, h);
		}
		if(bselected == 3){
			lBox(g, Color.RED, x, y + ((h * 2) + (h * 2) + (h/2)), w, h);
		}
	}
	
}