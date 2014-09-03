import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;


public class CloudObject extends ImageIcon{
	protected int x;
	private int width;
	private int height;
	protected double y;
	private String fileName;
	private BufferedImage img;
	
	public CloudObject(int x, int y, String fileName,int width,int height)  {
		super(fileName);
		this.fileName = fileName;
		try {
			img = ImageIO.read(new File(this.fileName));
		} catch (IOException e) {
			//e.printStackTrace();
		}
		
		this.width = width;
		this.height = height;
		this.setImage(this.getImage().getScaledInstance(this.width, this.height, 0));
		this.x = x;
		this.y = y;
		
	}
	
	public int getX(){
		return this.x;
	}
	
	public double getY() {
		return this.y;
	}
	
	public BufferedImage getBufferedImage() {
		return this.img;
	}
	
	public void setX(int x) {

			this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
		this.setX(this.getX());
		try {
			this.setImage(ImageIO.read(new File(this.fileName)).getScaledInstance(this.width, this.height, 0));
		} catch (IOException e) {
			
		}
	}
	
	public String getFile()  {
		return this.fileName;
	}
	
	public int getWidth() {		
		return this.width;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public int getHeight(){
		return this.height;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
}
