package phishing;

/**
 * Author: Rayne Aurit
 * Date: 2025-03-01
 * 
 * Object to represent a player. A player is the current user of the program,
 * created when the program is run. 
 */

public class Player {
	private String playerName;
	private int score;
	private int highestScore;
	private int hour;
	private int correct;
	private int incorrect;
	private boolean gameOver;
	
	public Player() {
		this.playerName = "";
		this.score = 0;
		this.highestScore = this.score;
		this.hour = 0;
		this.correct = 0;
		this.incorrect = 0;
		gameOver = false;
	}
	
	public String getName() {
		return this.playerName;
	}
	
	public int getScore() {
		return this.score;
	}
	
	public int getHighestScore() {
		return this.highestScore;
	}
	
	public int getHour() {
		return hour;
	}
	
	public double getPercent() {
		return ((double)(this.correct)) / (this.correct + this.incorrect);
	}
	
	public void isCorrect() {
		this.correct++;
	}
	
	public void isIncorrect() {
		this.incorrect++;
	}
	
	public boolean isGameOver() {
		return gameOver;
	}
	
	public void gameOver() {
		this.gameOver = true;
	}
	
	public void setName(String name) {
		this.playerName = name;
	}
	
	public void addScore(int points) {
		this.score += points;
	}
	
	public void updateHighScore(int points) {
		if (points > this.highestScore) {
			this.highestScore = points;
		}
	}
	
	public void nextHour() {
		this.hour += 1;
	}
}
