
public class WaterDrop extends CloudObject{
	private int tributary;
	private double speed;
	
	public WaterDrop(int x,String fileName) {
		super(x,0,fileName,40,40);
		this.speed = 1.5;
	}
	
	public WaterDrop(int x,double y,String fileName,int width,int height,double speed) {
		super(x,(int)y,fileName,width,height);
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
