package TME.State;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

public class SaveSelector {
	private int aos;
	private int aobc = 0, aobr = 0;
	private int x = 10, y = 50, w = 175, h = 50;
	private int bselected = 0;
	private int lbr = 0, lbc = 0;
	private boolean editor = false, back = false;
	private int ls;
	
	private Rectangle r, cr;
	
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
	
	public void mPressed(MouseEvent e){
		int mbp = e.getButton();
		for(int a = 0; a < aos + 1; a++){
			if(mbp == 1 && bselected == a && cr.contains(e.getPoint())){
				setEditor(true);
				if(bselected != aos){
					setLS(a + 1);
					a = aos + 2;
				} else if(bselected == aos){
					setBack(true);
					a = aos + 2;
				}
				
			}
		}
	}
	public void mMoved(MouseEvent e){
		checkCurrentBox(e);
	}
	public void checkCurrentBox(MouseEvent e){
		for(int a = 0, cr = 0, cc = 0, ny = h + (h/2); a < aos; a++, cr++){
			if(cr >= 5){
				cr = 0;
				cc++;
			}
			r = new Rectangle(x + (w * cc), y + (ny * cr), w, h);
			if(r.contains(e.getPoint()) == true){
				bselected = a;
				lbr = cr;
				lbc = cc;
				this.cr = (Rectangle) r.clone();
			}
		}
		r = new Rectangle((1280/2) - (w/2), 720 - 100, w, h);
		if(r.contains(e.getPoint()) == true){
			this.cr = (Rectangle) r.clone();
			bselected = aos;
		}
	}
	
	public void draw(Graphics g){
		for(int a = 0, cr = 0, cc = 0, nx = w + (w/4), ny = h + (h/2); a < aos; a++, cr++){
			if(cr >= 5){
				cr = 0;
				cc++;
			}
			dBox(g, Color.WHITE, x + (nx * cc), y + (ny * cr), w, h);
			FMA(g, Color.BLACK, fm, Text, "Map " + Integer.toString(a + 1), 2, 4, (x + (w/2)) + (nx * cc), (y + (h/2) + (ny * cr)));
		}
		dBox(g, Color.WHITE, (1280/2) - (w/2), 720 - 100, w, h);
		FMA(g, Color.BLACK, fm, Text, "Back", 2, 4, (1280/2), (720 - 100) + (h/2));
		if(bselected == aos){lBox(g, Color.RED, (1280/2) - (w/2), 720 - 100, w, h);}
		if(bselected != aos){lBox(g, Color.RED, x + ((w + (w/4)) * lbc), y + ((h + (h/2)) * lbr), w, h);}
	}
	
	//Getters and Setters
	public void setAOS(int i){
		aos = i;
	}
	public boolean getEditor(){
		return editor;
	}
	public void setEditor(boolean b){
		editor = b;
	}
	public Integer getLS(){
		return ls;
	}
	public void setLS(int ls){
		this.ls = ls;
	}
	public boolean getBack(){
		return back;
	}
	public void setBack(boolean b){
		back = b;
	}
	
}
