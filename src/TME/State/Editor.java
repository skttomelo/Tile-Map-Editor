package TME.State;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Editor {
	private int aos, cs, ms, asmax, sw, sh;
	private int cps = 0;
	private int air = 0, sand = 1,door = 2, wall = 3, entrance = 4, exit = 5, teleportpad = 6, nothing = 7;
	private Color ca = new Color(78, 163, 255), cd = new Color(132, 66, 0), cg = new Color(0, 116, 0), csand = new Color(229, 191, 0), cstone = new Color(145, 145, 145), cw = new Color(0, 85, 255), cc = new Color(229, 229, 229);
	private int sblock;
	private int[][] ablock;
	private boolean back = false, pause = false, tp = false, tpo = true;
	private boolean mClicked = false;
	private int mx = 0, my = 0, bx = 0, by = 0;
	private int x = 3, y = 26;
	private int[] rectcoordsx, rectcoordsy;
	private int gridx, gridy;
	private boolean save = false, aosaving = false, cssaving = false;
	private String mw, mh;
	private File sf;
	private String delimiter = " ";
	private String lss;
	private boolean mloaded = false;
	private boolean showgrid = true, momap = false, obox = false;
	private Rectangle map, optionbox = new Rectangle((1280/2), 26, (1280/2) - 4, 720 - 30), gbox = new Rectangle((1280/2) + 5, 26 + 5, 175, 50), nsavebox = new Rectangle((1280/2) + 185, 26 +  5, 175, 50), csavebox = new Rectangle((1280/2) + 15 + (175 * 2), 26 +  5, 175, 50), loadbox = new Rectangle((1280/2) + 5, 26  + 60, 175, 50);
	private boolean nsave = false, savecurrentfile = false , loadfile = false;
	private int oboxselected = -1;
	private String sg = "On";
	private int sgof = 0;
	
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
	
	public void Load(int ls){
		lss = Integer.toString(ls);
		mloaded = true;
		try{
			sf = new File("map" + lss + ".sav");
			BufferedReader rf = new BufferedReader(new FileReader(sf));
			ms = Integer.parseInt(rf.readLine());
			ms = Integer.parseInt(rf.readLine());
			ablock = new int[this.ms][this.ms];
			asmax = this.ms + 1;
			mw = mh = Integer.toString(this.ms);
			sw = 60 / (ms / 10);
			sh = 60 / (ms / 10);
			map = new Rectangle(3, 26, sw * ms, sh * ms);
			rectcoordsx = new int[asmax];
			rectcoordsy = new int[asmax];
			asmax = this.ms;
			gridx = this.ms;
			gridy = this. ms;
			for(int m = 3, l = 0; l < rectcoordsx.length; m += sw, l++){
				rectcoordsx[l] = m;
			}
			for(int m = 26, l = 0; l < rectcoordsy.length; m += sh, l++){
				rectcoordsy[l] = m;
			}
			for(int row = 0; row < ms; row++){
				String line = rf.readLine();
				String[] b = line.split(delimiter);
				for(int col = 0; col < ms; col++){
					ablock[row][col] = Integer.parseInt(b[col]);
				}
			}
			rf.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void Save(){
		if(nsave == true){
			nsave = false;
			mloaded = false;
			aos++;
			sf = new File("map" + aos + ".sav");
			lss = Integer.toString(aos);
			try {
				BufferedWriter wf = new BufferedWriter(new FileWriter(sf));
				//current save file
				//width
				wf.write(mw);
				wf.newLine();
				//height
				wf.write(mh);
				wf.newLine();
				for(int row = 0; row < ms; row++){
					for(int col = 0; col < ms; col++){
						wf.write(ablock[row][col] + " ");
					}
					wf.newLine();
				}
				wf.close();
			} catch (Exception e){
				e.printStackTrace();
			}
		} else if(savecurrentfile == true){
			savecurrentfile = false;
			sf = new File("map" + lss + ".sav");
			try {
				BufferedWriter wf = new BufferedWriter(new FileWriter(sf));
				//current save file
				//width
				wf.write(mw);
				wf.newLine();
				//height
				wf.write(mh);
				wf.newLine();
				for(int row = 0; row < ms; row++){
					for(int col = 0; col < ms; col++){
						wf.write(ablock[row][col] + " ");
					}
					wf.newLine();
				}
				wf.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void setMapSize(int ms){
		mloaded = false;
		this.ms = ms * 10;
		asmax = this.ms + 1;
		mw = mh = Integer.toString(this.ms);
		sw = 60 / ms;
		sh = 60 / ms;
		map = new Rectangle(3, 26, sw * this.ms, sh * this.ms);
		ablock = new int[this.ms][this.ms];
		rectcoordsx = new int[asmax];
		rectcoordsy = new int[asmax];
		asmax = this.ms;
		gridx = this.ms;
		gridy = this. ms;
		for(int m = 3, l = 0; l < rectcoordsx.length; m += sw, l++){
			rectcoordsx[l] = m;
		}
		for(int m = 26, l = 0; l < rectcoordsy.length; m += sh, l++){
			rectcoordsy[l] = m;
		}
		for(int r = 0; r < this.ms; r++){
			for(int c = 0; c < this.ms; c++){
				ablock[r][c] = sand;

			}
		}
	}
	
	public void update(){
		if(momap == true){
			for(int i = asmax; i > 0; i--){
				if(mx <= rectcoordsx[i]){
					bx = rectcoordsx[i] - sw;
					gridx = i - 1;
				}
			}
			for(int i = asmax; i > 0; i--){
				if(my <= rectcoordsy[i]){
					by = rectcoordsy[i] - sh;
					gridy = i - 1;
				}
			}
		}
		if(mClicked == true){
			for(int r = 0; r < rectcoordsy.length; r++){
				for(int c = 0; c < rectcoordsx.length; c++){
					if(r == gridy && c == gridx){
						if(sblock == air){
							ablock[r][c] = sblock;
						} else	if(sblock == sand){
							ablock[r][c] = sblock;
						} else	if(sblock == door){
							ablock[r][c] = sblock;
						} else if(sblock == wall){
							ablock[r][c] = sblock;
						} else if(sblock == entrance){
							ablock[r][c] = sblock;
						} else if(sblock == exit){
							ablock[r][c] = sblock;
						} else if(sblock == teleportpad){
							ablock[r][c] = sblock;
						} else if(sblock == nothing){
							ablock[r][c] = sblock;
						}
					}
				}
			}
		}
		if(save == true){
			save = false;
			setAOSaving(true);
			Save();
		}
	}
	
	public void mMoved(MouseEvent e){
		mx = e.getX();
		my = e.getY();
		if(map.contains(e.getPoint()) == true){
			momap = true;
		} else if(map.contains(e.getPoint()) == false){
			momap = false;
			if(optionbox.contains(e.getPoint()) == true){
				obox = true;
			} else if(optionbox.contains(e.getPoint()) == false){
				obox = false;
			}
			oBoxSelection(e);
		}
	}
	public void mPressed(MouseEvent e){
		int mbp = e.getButton();
		if(mbp == 1 && momap == true){
			mClicked = true;
		}
		showGrid(e, mbp);
		NCSaveButtonClickCheck(e, mbp);
	}
	public void mReleased(MouseEvent e){
		mClicked = false;
	}
	public void mDragged(MouseEvent e){
		int mbp = e.getButton();
		if(mbp == 1 && map.contains(e.getPoint()) == true){
			mClicked = true;
		}
		mx = e.getX();
		my = e.getY();
	}
	public void kPressed(KeyEvent e){
		int key = e.getKeyCode();
		if(key == e.VK_1){
			sblock = air;
		}else if(key == e.VK_2){
			sblock = sand;
		}else if(key == e.VK_3){
			sblock = door;
		}else if(key == e.VK_4){
			sblock = wall;
		}else if(key == e.VK_5){
			sblock = entrance;
		}else if(key == e.VK_6){
			sblock = exit;
		}else if(key == e.VK_7){
			sblock = teleportpad;
		}else if(key == e.VK_8){
			sblock = nothing;
		}else if(key == e.VK_ESCAPE){
			back = true;
		}else if(key == e.VK_S){
			save = true;
		}
		
	}
	
	public void draw(Graphics g){
		for(int d = 0; d < ms; d++){
			for(int m = 0; m < ms; m++){
				int rc = ablock[d][m];
				if(rc == air){
					dBox(g, ca, x + (m * sw), y + (d * sh), sw, sh);
				} else	if(rc == sand){
					dBox(g, cd, x + (m * sw), y + (d * sh), sw, sh);
				} else	if(rc == door){
					g.setColor(cc);
					dBox(g, cc, x + (m * sw), y + (d * sh), sw, sh);
				} else if(rc == wall){
					dBox(g, cw, x + (m * sw), y + (d * sh), sw, sh);
				} else if(rc == entrance){
					dBox(g, cg, x + (m * sw), y + (d * sh), sw, sh);
				} else if(rc == exit){
					dBox(g, cstone, x + (m * sw), y + (d * sh), sw, sh);
				} else if(rc == teleportpad){
					dBox(g, csand, x + (m * sw), y + (d * sh), sw, sh);
				}
			}
		}
		if(momap == true){
			if(sblock == air){
				g.setColor(ca);
			} else	if(sblock == sand){
				g.setColor(cd);
			} else	if(sblock == door){
				g.setColor(cc);
			} else if(sblock == wall){
				g.setColor(cw);
			} else if(sblock == entrance){
				g.setColor(cg);
			} else if(sblock == exit){
				g.setColor(cstone);
			} else if(sblock == teleportpad){
				g.setColor(csand);
			}
			g.fillRect(bx, by, sw, sh);
		}
		lBox(g, Color.WHITE, (1280/2), 26, (1280/2) - 4, 720 - 30);
		dBox(g, Color.WHITE, (1280/2) + 5, 26 + 5, 175, 50);
		FMA(g, Color.BLACK, fm, Text, "Show Grid: " + sg, 2, 4, ((1280/2) + 5) + (175/2), (26 + 5) + 25);
		dBox(g, Color.WHITE, (1280/2) + 185, 26 +  5, 175, 50);
		FMA(g, Color.BLACK, fm, Text, "New Save", 2, 4, (1280/2) + 185 + (175/2), 26 + 30);
		dBox(g, Color.WHITE, (1280/2) + 15 + (175 * 2), 26 +  5, 175, 50);
		FMA(g, Color.BLACK, fm, Text, "Save", 2, 4, (1280/2) + 15 + (175*2) + (175/2), 26 + 30);
		dBox(g, Color.WHITE, (1280/2) + 5, 26  + 60, 175, 50);
		FMA(g, Color.BLACK, fm, Text, "Load File", 2, 4, ((1280/2) + 5) + (175/2), 26 + 85);
		if(oboxselected == 0){lBox(g, Color.red, (1280/2) + 5, 26 + 5, 175, 50);} else
		if(oboxselected == 1){lBox(g, Color.red, (1280/2) + 185, 26 +  5, 175, 50);} else
		if(oboxselected == 2){lBox(g, Color.red, (1280/2) + 15 + (175 * 2), 26 +  5, 175, 50);} else
		if(oboxselected == 3){lBox(g, Color.red, (1280/2) + 5, 26  + 60, 175, 50);}
		if(showgrid == true){
			for(int r = 0; r < ms; r++){
				for(int c = 0; c < ms; c++){
					lBox(g, Color.WHITE, x + (c * sw), y + (r * sh), sw, sh);
				}
			}
		}
	}
	
	public boolean getAOSaving(){
		return aosaving;
	}
	public void setAOSaving(boolean b){
		aosaving = b;
	}
	public boolean getCSSaving(){
		return cssaving;
	}
	public void setCSSaving(boolean b){
		cssaving = b;
	}
	public void setAOS(int i){
		aos = i;
	}
	public Integer getAOS(){
		return aos;
	}
	public void setCS(int i){
		cs = i;
	}
	public Integer getCS(){
		return cs;
	}
	public boolean getBack(){
		return back;
	}
	public void setBack(boolean b){
		back = b;
	}
	public boolean getLoad(){
		return loadfile;
	}
	public void setLoad(boolean v){
		loadfile = v;
	}
	private void showGrid(MouseEvent e, int mbp){
		if(mbp == 1 && gbox.contains(e.getPoint()) == true){
			sgof++;
			if(sgof > 1){
				sgof = 0;
			}
			if(sgof == 0){
				showgrid = true;
				sg = "On";
			} else if(sgof == 1){
				showgrid = false;
				sg = "Off";
			}
		}
	}
	private void oBoxSelection(MouseEvent e){
		if(gbox.contains(e.getPoint()) == true){
			oboxselected = 0;
		} else if(nsavebox.contains(e.getPoint()) == true){
			oboxselected = 1;
		} else if(csavebox.contains(e.getPoint()) == true){
			oboxselected = 2;
		} else if(loadbox.contains(e.getPoint()) == true){
			oboxselected = 3;
		} else if(gbox.contains(e.getPoint()) == false && nsavebox.contains(e.getPoint()) == false && csavebox.contains(e.getPoint()) == false && loadbox.contains(e.getPoint()) == false){ //this if statement tells the program that if none of the boxes are selected then don't highlight any of them
			oboxselected = -1;
		}
	}
	private void NCSaveButtonClickCheck(MouseEvent e, int mbp){
		if(mbp == 1){
			if(nsavebox.contains(e.getPoint()) == true){
				save = true;
				nsave = true;
				savecurrentfile = false;
			} else if(csavebox.contains(e.getPoint()) == true){
				save = true;
				savecurrentfile = true;
				nsave = false;
			} else if(loadbox.contains(e.getPoint()) == true){
				loadfile = true;
				savecurrentfile = false;
				nsave = false;
			}
		}
	}
}
