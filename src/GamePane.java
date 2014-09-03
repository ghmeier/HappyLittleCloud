import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;



public class GamePane extends JPanel implements KeyListener {

		public static final int WIDTH = 800;
		public static final int HEIGHT = 600;
        private BufferedImage img;
      	public static Player co = new Player(-100,330,"src/img/spence.png",70,70,4);
      	public static CloudObject backGround = new CloudObject(0,0,"src/img/cloudBack.png",WIDTH,HEIGHT);
      	public static CloudObject burnedVillage = new CloudObject(-200,470,"src/img/burned_village.png",200,100);
      	public static ArrayList<WaterDrop> drops = new ArrayList<WaterDrop>();
      	public static GamePane pane = new GamePane();
      	public static int timeLength=300000;
      	public static long curTime;
      	public static long timeOut;
      	public static long oldTime;
      	public boolean hasWon = false;
      	public boolean cinematic = false;
        
        public static void main(String[] args) {
        	GamePane game = new GamePane();
        	Random r= new Random();
            JFrame frame = new JFrame("Happy Little Cloud");           
            frame.addKeyListener(game);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(pane);
            frame.setSize(WIDTH,HEIGHT);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            
            oldTime = System.currentTimeMillis();
            curTime = System.currentTimeMillis();
            timeOut = System.currentTimeMillis();
            pane.cinematic = true;
            pane.openScene();
            pane.cinematic = false;
            timeOut = System.currentTimeMillis() + timeLength;//in seconds
            co.setY(330);
            co.setX(375);
            while (timeOut>curTime && !pane.hasWon) {
            	curTime = System.currentTimeMillis();
            	if (curTime>oldTime+700 && r.nextInt(10)>8) {
            		
            		oldTime = curTime;
            		if (co.getCharacterType()>=2) {
            			drops.add(new WaterDrop(r.nextInt(750),"src/img/basiccloud.png"));
            		}else {
            			drops.add(new WaterDrop(r.nextInt(750),"src/img/raindrop.png"));
            		}
            	}
            	
            	if (co.getCharacterType()>=Player.MAX_LEVEL-1) {
            		pane.hasWon = true;
            	}
            	pane.repaint();
           }
           pane.gameEnd();
           if (pane.hasWon) {
        	   System.out.println("You won :)");
        	   while(co.getY()>-co.getHeight() || drops.size()>1) {
        		   curTime = System.currentTimeMillis();
        		   if (curTime>oldTime+10) {
        			   oldTime=curTime;
	        		   co.endMove();
	        		   if (r.nextInt()>8 && co.getY()>-co.getHeight()){
	        			   drops.add(new WaterDrop(co.getX()+r.nextInt(co.getWidth()),co.getY()+co.getHeight(),"src/img/raindrop.png",10,10,1.7));
	        		   }
	        		   pane.repaint();
        		   }
        	   }
        	   timeOut = System.currentTimeMillis();
        	   pane.endScene();
           }else {
        	   System.out.println("You lost");
        	   CloudObject youWon = new CloudObject(0,0,"src/img/losing_game.png",800,600);	   
        	   youWon.paintIcon(pane, pane.getGraphics(), youWon.getX(), (int)youWon.getY());
           }
           
        }
        
        public GamePane() {
           
        }
        
        public void rain() {
        	Random r = new Random();
 		   if (curTime>oldTime+10) {
			   oldTime=curTime;
    		   
    		   if (r.nextInt()>8 && co.getY()>-co.getHeight()){
    			   drops.add(new WaterDrop(co.getX()+r.nextInt(co.getWidth()),co.getY()+co.getHeight(),"src/img/raindrop.png",10,10,1.7));
    		   }
    		   //pane.repaint();
		   }
        }

        @Override
        public Dimension getPreferredSize() {
            return img == null ? new Dimension(32, 32) : new Dimension(img.getWidth(), img.getHeight());
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g); 
            Image off = createImage(WIDTH,HEIGHT);           
            Graphics second = off.getGraphics();
            second.setFont(new Font("Volter (Goldfish)",Font.PLAIN,30));
            backGround.paintIcon(pane,second, backGround.getX(), (int)backGround.getY());
            burnedVillage.paintIcon(pane,second,burnedVillage.getX(),(int)burnedVillage.getY());
            co.paintIcon(pane, second, co.getX(), (int)co.getY());
            if (!this.cinematic){
	           
	            
	            co.move();
	            for (int i=0;i<drops.size();i++) {
	            	WaterDrop e = drops.get(i);
	        		if (e.getY()>HEIGHT) {
	        			drops.remove(e);
	        			break;
	        		} else if ((e.getX()>=co.getX() && e.getX()<=co.getX()+co.getWidth()) && (e.getY()>=co.getY() && e.getY()<=co.getY()+co.getHeight())&& !this.hasWon){
	        			drops.remove(e);
	        			co.hitTributary();
	        			break;
	        		}else {
	        			e.setSpeed(.5*(co.getCharacterType()+1));
	        			e.move();       			
	                    e.paintIcon(pane,second, e.getX(), (int)e.getY());
	        		}           		
	            }
            
	            second.drawString(Integer.toString(co.getTotalTributaries()), 20, 40);
            	second.drawString(Long.toString(((timeOut - curTime)/1000)/60)+":"+((timeOut - curTime)/1000)%60, 680, 40);
            }
            g.drawImage(off, 0,0,this);
        }

		@Override
		public void keyPressed(KeyEvent arg0) {
			if (!this.hasWon && arg0.getKeyChar()=='a') {
				co.setX(co.getX()-5);
				//pane.repaint();
			} else if (!this.hasWon && arg0.getKeyChar()=='d') {
				co.setX(co.getX()+5);
				//pane.repaint();

			}
			
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub
			if (arg0.getKeyChar()=='a') {
				co.setX(co.getX()-5);
				//pane.repaint();
			} else if (arg0.getKeyChar()=='d') {
				co.setX(co.getX()+5);
				//pane.repaint();
			}
		}
		
		public void endScene() {
     	   co.setX(350);
     	   co.setY(10);
     	   burnedVillage.setX(350);
     	   boolean newVillage = false; 
     	   Image off = createImage(WIDTH,HEIGHT);    
           Graphics second = off.getGraphics();
           CloudObject text1 = new CloudObject(100,10,"src/img/spence_end.png",280,171);
           CloudObject text2 = new CloudObject(100,310,"src/img/village_end.png",280,171);
     	   while(curTime<timeOut+13000) {
     		   curTime = System.currentTimeMillis();
				backGround.paintIcon(pane, second, backGround.getX(), (int)backGround.getY());
				co.paintIcon(this, second, co.getX(),(int)co.getY());
				burnedVillage.paintIcon(this, second, burnedVillage.getX(),(int)burnedVillage.getY());
				for (int i=0;i<drops.size();i++) {
					drops.get(i).paintIcon(pane,second, drops.get(i).getX(), (int)drops.get(i).getY());
	        		if (drops.get(i).getY()>HEIGHT) {
	        			drops.remove(drops.get(i));
	        			break;
	        		} else {
	        			drops.get(i).move();
	        		}
				}
     		   if ( newVillage == false && curTime>timeOut+ 3000 ) {
     			   burnedVillage.setFileName("src/img/saved_village.png");
     			   newVillage = true;
     		   }
     		   if (curTime>timeOut+3000 && curTime<timeOut+8000) {
     			   text2.paintIcon(pane, second, text2.getX(),(int) text2.getY());
     		   }
     		   if (curTime>timeOut+7900 && curTime<timeOut+13000) {
     			   text1.paintIcon(pane, second, text1.getX(),(int) text1.getY());
     		   }
     		   pane.rain();
     		  pane.getGraphics().drawImage(off, 0,0,pane);
     	   }
     	   CloudObject youWon = new CloudObject(0,0,"src/img/you_won.png",800,600);	   
     	   youWon.paintIcon(pane, pane.getGraphics(), youWon.getX(), (int)youWon.getY());
			
		}
		
		public void openScene() {
            Image off = createImage(WIDTH,HEIGHT);           
            Graphics second = off.getGraphics();
			while (curTime<timeOut+10000) {
				backGround.setFileName("src/img/splashpage.png");
				backGround.paintIcon(pane, second, backGround.getX(), (int)backGround.getY());
				curTime = System.currentTimeMillis();
				this.getGraphics().drawImage(off, 0,0,this);
			}
			backGround.setFileName("src/img/cloudBack.png");
			burnedVillage.setX(200);
			co.setX(370);
			co.setY(470);
			CloudObject text1 = new CloudObject(10,200,"src/img/beginning_text.png",280,171);
			CloudObject text2 = new CloudObject(270,300,"src/img/spence_text.png",280,171);
			CloudObject text3 = new CloudObject(270,300,"src/img/spence_text2.png",280,171);
			CloudObject text4 = new CloudObject(300,200,"src/img/spence_text3.png",280,171);
			off = createImage(WIDTH,HEIGHT);
			second = off.getGraphics();
			timeOut = curTime;
			while (curTime<timeOut+17000) {
				curTime = System.currentTimeMillis();
				backGround.paintIcon(pane, second, backGround.getX(), (int)backGround.getY());
				co.paintIcon(this, second, co.getX(),(int)co.getY());
				burnedVillage.paintIcon(this, second, burnedVillage.getX(),(int)burnedVillage.getY());
				if (curTime>timeOut+200 && curTime<timeOut + 5500) {
					text1.paintIcon(this, second, text1.getX(), (int)text1.getY());
				}
				if (curTime>timeOut+3000 && curTime<timeOut + 8500) {
					text2.paintIcon(this, second, text2.getX(),(int)text2.getY());
				}
				if (curTime>timeOut+8400 && curTime<timeOut + 13400) {
					text3.paintIcon(this, second, text3.getX(),(int)text3.getY());
				}
				if (curTime>timeOut+13400 && curTime<timeOut + 17000) {
					text4.paintIcon(this, second, text4.getX(),(int)text4.getY());
				}
				this.getGraphics().drawImage(off, 0,0,this);
				//this.repaint();
			}
			off = createImage(WIDTH,HEIGHT);
			second = off.getGraphics();
			while (co.getX()<WIDTH && burnedVillage.getX()>-burnedVillage.getWidth()) {
				co.setX(co.getX()+1);
				burnedVillage.setX(burnedVillage.getX()-1);
				backGround.paintIcon(pane, second, backGround.getX(), (int)backGround.getY());
				co.paintIcon(this, second, co.getX(),(int)co.getY());
				burnedVillage.paintIcon(this,second, burnedVillage.getX(),(int)burnedVillage.getY());
				
				this.getGraphics().drawImage(off, 0,0,this);
			}
		}
		
		public void gameEnd() {
			for (int i=drops.size()-1;i>=0;i--) {
				drops.remove(i);
			}
			
			pane.repaint();
		}
}

