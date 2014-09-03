/* Kinect Hackathon 2014
 * 
 * Garret Meier, Luke Geiken, Alex Rinehart 
 * 
 */


package com.kinectHackathon.HappyLittleCloud;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;


import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;


public class CloudObject extends ImageIcon{
	protected int x;
	private int width;
	private int height;
	protected double y;
	private InputStream fileName;
	private BufferedImage img;
	
	public CloudObject(int x, int y,InputStream fileName, String url, int width,int height)  {
		super(url);
		
		try {
			this.fileName = fileName;//.toURI()
			img = ImageIO.read(this.fileName);
			this.fileName.close();
		} catch (IOException e) {
			//e.printStackTrace();
		}
		
		this.width = width;
		this.height = height;
		this.setImage(img.getScaledInstance(this.width, this.height, 0));
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
	
	public void setFileName(InputStream fileName) {
		
		this.setX(this.getX());
		try {
			this.fileName = fileName;
			this.img = ImageIO.read(this.fileName);
			this.setImage(img.getScaledInstance(this.width, this.height, 0));
		} catch (IOException e) {
			
		}
	}
	
	public String getFile()  {
		return this.fileName.toString();
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
