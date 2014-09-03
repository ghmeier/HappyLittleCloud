/* Kinect Hackathon 2014
 * 
 * Garret Meier, Luke Geiken, Alex Rinehart 
 * 
 */
package com.kinectHackathon.HappyLittleCloud;

import java.io.InputStream;
import java.net.URL;

public class WaterDrop extends CloudObject{
	private int tributary;
	private double speed;
	
	public WaterDrop(int x, InputStream fileName,String url) {
		super(x,0,fileName,url,40,40);
		this.speed = 1.5;
	}
	
	public WaterDrop(int x,double y,InputStream fileName, String url, int width,int height,double speed) {
		super(x,(int)y,fileName,url,width,height);
		this.speed = speed;
				
	}
	
	public double getSpeed(){
		return this.speed;
	}
	
	public void setSpeed(double y) {
		this.speed = y;
	}
	
	public int getTributary() {
		return this.tributary;
	}
	
	public void move() {
		this.setY(this.getY()+this.speed);
	}
}
