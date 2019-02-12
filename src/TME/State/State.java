package TME.State;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class State {
	
	private int state = 1; //0 = close program, 1 = go to screen that ask what size do you want to draw on, and 2 takes you to the editor state
	
	private Menu m;
	private LevelSizeSelector lss;
	private SaveSelector ss;
	private Editor editor;
	private BufferedReader br;
	private File sf;
	private int aos; //amount of saves
	private String aoss;
	BufferedWriter wf;
	private void SaveAOS(){
		try {
			sf = new File("aos.sav");
			wf = new BufferedWriter(new FileWriter(sf));
			aoss = Integer.toString(aos);
			ss.setAOS(aos);
			wf.write(aoss);
			wf.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void Load(){
		try {
			sf = new File("aos.sav");
			br = new BufferedReader(new FileReader(sf));
			aoss = br.readLine();
			aos = Integer.parseInt(aoss);
			br.close();
			editor.setAOS(aos);
			ss.setAOS(aos);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void init(){
		m = new Menu();
		lss = new LevelSizeSelector();
		editor = new Editor();
		ss = new SaveSelector();
		Load();
	}
	public State(){
		init();
	}
	
	public void update(){
		if(state == 0){
			System.exit(0);
		}
		if(state == 1){
			m.update();
			if(m.getEdit() == true){
				state = 2;
				m.setEdit(false);
			}
			if(m.getLoad() == true){
				state = 4;
				m.setLoad(false);;
			}
		}
		if(state == 2){
			if(lss.getClicked() == true){
				lss.setClicked(false);
				state = 3;
				editor.setMapSize(lss.getMapSize());
			}
			if(lss.getBack() == true){
				state = 1;
				lss.setBack(false);
			}
		}
		if(state == 3){
			editor.update();
			if(editor.getBack() == true){
				editor.setBack(false);
				state = 1;
			} else if(editor.getAOSaving() == true){
				editor.setAOSaving(false);
				aos = editor.getAOS();
				SaveAOS();
			} else if(editor.getLoad() == true){
				editor.setLoad(false);
				state = 4;
			}
		}
		if(state == 4){
			if(ss.getEditor() == true){
				state = 3;
				ss.setEditor(false);
				editor.Load(ss.getLS());
			}
			if(ss.getBack() == true){
				state = 1;
				ss.setBack(false);
			}
		}
	}
	
	//key listener
	public void pressed(KeyEvent e){
		if(state == 1){
			//m.kPressed(e);
		}
		if(state == 2){
			//lss.kPressed(e);
		}
		if(state == 3){
			editor.kPressed(e);
		}
		if(state == 4){
			
		}
	}
	
	//mouse listeners
	public void mPressed(MouseEvent e){
		if(state == 1){
			m.mPressed(e);
		}
		if(state == 2){
			lss.mPressed(e);
		}
		if(state == 3){
			editor.mPressed(e);
		}
		if(state == 4){
			ss.mPressed(e);
		}
	}
	public void mReleased(MouseEvent e){
		if(state == 3){
			editor.mReleased(e);
		}
	}
	public void mMoved(MouseEvent e){
		if(state == 1){
			m.mMoved(e);
		}
		if(state == 2){
			lss.mMoved(e);
		}
		if(state == 3){
			editor.mMoved(e);
		}
		if(state == 4){
			ss.mMoved(e);
		}
	}
	public void mDragged(MouseEvent e){
		if(state == 1){
			
		}
		if(state == 2){
			
		}
		if(state == 3){
			editor.mDragged(e);
		}
		if(state == 4){
			
		}
	}
	
	//draw method
	public void draw(Graphics g){
		if(state == 1){
			m.draw(g);
		}
		if(state == 2){
			lss.draw(g);
		}
		if(state == 3){
			editor.draw(g);
		}
		if(state == 4){
			ss.draw(g);
		}
	}
	
}
