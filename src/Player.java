import java.util.ArrayList;

public class Player extends CloudObject {
	/**
	 * The current score of the player
	 */
	public static final int MAX_LEVEL = 5;
	private int score;
	
	/**
	 * the Arraylist of the .png character files
	 */
	private ArrayList<String> characterModel;
	
	/**
	 * The int to identify which character the player is current on
	 */
	private int characterType;
	
	/**
	 * THe player's current multiplier
	 */
	private int multiplier;
	
	/**
	 * The number need to "level up", which means to
	 * move on to the next character type
	 */
	private int levelUpInt;
	
	/**
	 * The current number of tributaries hit
	 */
	private int numTributaries;
	private int totalTributaries;
	private int floatToggle  = -1;
	
	/**
	 * The Player constructor 
	 * @param levelUpInt the int needed to "level up"
	 */
	public Player(int x, int y, String fileName, int width,int height, int levelUpInt) {
		super(x,y,fileName,width,height);
		score = 0;
		numTributaries = 0;
		multiplier = 1;		
		characterType = 0;
		this.levelUpInt = levelUpInt;
		characterModel = new ArrayList<String>(5);
		characterModel.add("src/img/spermy.png");
		characterModel.add("src/img/waterblob.png");
		characterModel.add("src/img/misty.png");
		characterModel.add("src/img/cloud.png");
		characterModel.add("src/img/raincloud.png");
	}
	
	/**
	 * Gets the current score
	 * @return the current score
	 */
	public int getScore() {
		return score;
		
	}
	
	/**
	 * Gets the character model (.png file) as a string
	 * @return String of the .png file
	 */
	public String getCharacter() {
		return characterModel.get(characterType);
	}
	
	public void move() {
		if (this.getY()<320||this.getY()>340) {
			this.floatToggle = -this.floatToggle;
		}
			this.setY(this.getY()+this.floatToggle*.05);
	}
	
	public int getTotalTributaries() {
		return this.totalTributaries;
		
	}
	
	public int getCharacterType() {
		return this.characterType;
	}
	/**
	 * Call when a tributary is hit
	 */
	public void hitTributary() {
		
		score += 1 * multiplier;
		numTributaries++;
		totalTributaries++;
		if(numTributaries >= levelUpInt*(characterType+1)*2 && characterType<this.characterModel.size()) {
			numTributaries = 0;
			characterType++;
			this.setWidth(this.getWidth()+20);
			this.setHeight(this.getHeight()+20);
			this.setFileName(getCharacter());

		}
	}
	
	public boolean endMove(){
		if (this.getX()<350-this.getWidth()) {
			this.setX(this.getX()+5);
		} else if (this.getX()>350){
			this.setX(this.getX()-5);
		}
		if (this.getY()>-this.getHeight()-this.getWidth()) {
			this.setY(this.getY()-3);
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public void setX(int x) {
		if (x>=GamePane.WIDTH-this.getWidth()) {
			x= GamePane.WIDTH-this.getWidth();
		}else if (x<=-10) {
			x=0;
		} else {
			this.x = x;
		}
	}
}
