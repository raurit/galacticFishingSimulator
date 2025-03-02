package phishing;

/**
 * Author: Rayne Aurit
 * Date: 2025-03-01
 * 
 * Object to represent an in-game hourOfPlay given the current hourOfPlay
 * the player is on. The goal score to advance is set to 2^(hourOfPlay).
 */

public class HourOfPlay {
	private int hourOfPlay;
	private int score;
	private int goal;
	private boolean isTimeUp;
	
	public HourOfPlay(int hourOfPlay) {
		this.hourOfPlay = hourOfPlay;
		this.score = 0;
		this.goal = hourOfPlay*2;
		this.isTimeUp = false;
	}
	
	public int getHourOfPlay() {
		return this.hourOfPlay;
	}
	
	public int getScore() {
		return this.score;
	}

	public int getGoal() {
		return this.goal;
	}
	
	public boolean isTimeUp() {
		return this.isTimeUp;
	}
	
	public void addScore() {
		this.score += 1;
	}
	
	public void hit() {
		this.score = (int)(this.score * 0.5);
	}
	
	public void timeIsUp() {
		this.isTimeUp = true;
	}
}
