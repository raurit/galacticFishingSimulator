package phishing;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Author: Rayne Aurit 
 * Date: 2025-03-01
 * 
 * The main methods for the game to run, including the settings, 
 * introduction sequence and tutorial, running the game, ending 
 * sequence(s), and credits. Helper methods as needed
 */

public class runGame {
	
	public static int textSpeed = 1;
	public static int textLength = 57;
	
	public static void main(String args[]) {
		// load in data from csv's
		ArrayList<String> firstNames = DataLoader.loadDataFromFile("firstnames.csv");
		ArrayList<String> lastNames = DataLoader.loadDataFromFile("lastnames.csv");
		ArrayList<String> domains = DataLoader.loadDataFromFile("domains.csv");
		ArrayList<String> greetings = DataLoader.loadDataFromFile("greetings.csv");
		ArrayList<String> bodies = DataLoader.loadDataFromFile("safeBodies.csv");
		ArrayList<String> pBodies = DataLoader.loadDataFromFile("unsafeBodies.csv");
		ArrayList<String> closings = DataLoader.loadDataFromFile("closings.csv");
		
		System.out.println("---------------------------------------------------------\n"
				+ "Welcome to Galactic Fishing!\nCornhacks Project by Rayne Aurit\n"
				+ "---------------------------------------------------------");
		
		// initialization
		Player player = new Player();
		Scanner kb = new Scanner(System.in);
		
		// logic calls to functions
		settings(kb, player);
		introduction(player,kb);
		hourOfPlay(player,kb,firstNames,lastNames,domains,greetings,bodies,pBodies,closings); // tutorial hour 0
		while ((player.getHour() < 8) && !player.isGameOver()) {
			settings(kb, player);
			player.nextHour();
			hourOfPlay(player,kb,firstNames,lastNames,domains,greetings,bodies,pBodies,closings);
		}
		if (player.isGameOver()) {
			badEnding(player);
		} else {
			goodEnding(player);
		}
		credits(player);
		
		// close resources
		kb.close();/**/
	}
	
	/**
	 * Method that runs settings, asking the player
	 * if they would like to change the textSpeed,
	 * continue the game, or quit the game
	 * 
	 * @param kb, player
	 */
	
	public static void settings(Scanner kb, Player player) {
		System.out.println("Main Menu\n"
				+ "---------------------------------------------------------");
		int optionSelect = 0;
		while (optionSelect != 2) {
			printLettersOneByOne("1. Change text speed settings\n"
					+ "2. Continue to game\n"
					+ "3. Quit\n\n");
			System.out.print("Enter a number 1-3: ");
			try {
				optionSelect = kb.nextInt();
				if (optionSelect == 1) {
						// updating text speed setting for the player 
						System.out.println("Updating Textspeed. . . ");
						String textSpeedInput = "";
						while(!textSpeedInput.equals("c")) {
							printLettersOneByOne("Type 'f' for faster, 's' for slower, 'c' to continue: ");
							textSpeedInput = kb.next();
							if (textSpeedInput.equals("f")) {
								textSpeed *= 0.6;
							} if (textSpeedInput.equals("s")) {
								textSpeed *= 1.5;
							}
						}
						System.out.println("Textspeed updated\n"
								+ "---------------------------------------------------------\n");
				} else if (optionSelect == 3) {
					credits(player);
					System.out.println("Exiting...\n"
							+ "---------------------------------------------------------");
					System.exit(0);
				} else if (optionSelect != 2) {
					System.out.println("Invalid number. Try again");
				}
			} catch (Exception e) {
				String throwaway = kb.next();
				System.err.println("Invalid input.");
			}
		}
		
		System.out.println("---------------------------------------------------------");
		if (player.getName().equals("")) {
			printLettersOneByOne("And so it begins");
		} else {
			switch (player.getHour()+1) {
				case 1:
					printLettersOneByOne("The only thing between your home and descruction is you. Don't fail");
					break;
				case 2:
					printLettersOneByOne("The warmups are getting harder");
					break;
				case 3:
					printLettersOneByOne("Think you are up for more?");
					break;
				case 4:
					printLettersOneByOne("Keep going");
					break;
				case 5:
					printLettersOneByOne("Kill them");
					break;
				case 6:
					printLettersOneByOne("Only you can be employee of the month");
					break;
				case 7:
					printLettersOneByOne("Only you can stop the madness");
					break;
				case 8:
					printLettersOneByOne("The last hour of the work day");
					break;
			}
		}
		printPause();
		System.out.println("\n---------------------------------------------------------");
	}
	
	/**
	 * Method that runs the introduction and starts the 
	 * tutorial. Asks for player's name via a "contract" 
	 * in game. 
	 * 
	 * @param player, kb
	 */
	
	public static void introduction(Player player, Scanner kb) {
		// sets the scene for the player
		printLettersOneByOne(wrapText("You find yourself in a small room, "
				+ "a singular table in front of you and a bright light "
				+ "hitting your face. You don't remember how you got here,"
				+ " but you know that you aren't in trouble"));
		printPause();
		printLettersOneByOne("yet.\n");
		printLettersOneByOne(wrapText("As your eyes focus, you notice a "
				+ "tall man sitting across from you. He's not quite a man "
				+ "despite his figure. The more you look at him, the more "
				+ "you realize that he doesn't have human features. His "
				+ "skin is discolored, almost metallic in the way it "
				+ "shines, and he smells of rust. He has a nametag "
				+ "fastened to his shirt labeled \"F r3d\" You decide "
				+ "that he is friendly enough."));
		
		System.out.println("\n");
		
		//ask for and sets player name
		printLettersOneByOne("Fr3d: What is your name, recruit?\n\n");
		System.out.print("Enter your name: ");
		player.setName(kb.next());
		
		//continues introduction sequence
		printLettersOneByOne(wrapText("Fr3d: Pleased to meet you, " + player.getName() 
		+ ". Long ago, they used to call this job \"fishing\", but that "
		+ "was back when we lived on a planet with oceans. Now, all we see "
		+ "is rock and debris everywhere"));
		printPause();
		System.out.println();
		printLettersOneByOne(wrapText("I can see the confused look on "
				+ "your face, " + player.getName() + ". This isn't a "
						+ "ghost hire. I still have a job for you. Are "
						+ "you ready?\n\n"));
		continuePrompt(kb);
		System.out.println("\ncontinuing...");
	}
	
	/**
	 * Method that runs the hour of the game, letting the player
	 * gain points for their score if they correctly identify
	 * a randomly generated email as a phishing email
	 * 
	 * @param player, kb, firstNames, lastNames, domains, greetings, bodies, pBodies, closings
	 */
	
	public static void hourOfPlay(Player player, Scanner kb, ArrayList<String> firstNames, ArrayList<String> lastNames, ArrayList<String> domains, ArrayList<String> greetings, ArrayList<String> bodies,ArrayList<String> pBodies,ArrayList<String> closings) {
		HourOfPlay hourOfPlay = new HourOfPlay(player.getHour());
		timer(hourOfPlay);
		while (!hourOfPlay.isTimeUp()) {
			System.out.println("---------------------------------------------------------");
			// creates new email randomly
			boolean isPhishy = Math.random() < 0.5;;
			String username = (lastNames.get((int)(Math.random()*lastNames.size())) + firstNames.get((int)(Math.random()*firstNames.size()))).toLowerCase();
			String domain = domains.get((int)(Math.random()*domains.size()));
			String body = generateBody(greetings,bodies,pBodies,closings,isPhishy);
			Email email = new Email(username,domain,body,isPhishy);
			
			System.out.println(email);
			System.out.println("---------------------------------------------------------");
			
			// player chooses to shoot or not, handling in cases where they are correct or not
			String shoot = "";
			System.out.print("Do you shoot? (y/n): ");
			shoot = kb.next();
			System.out.println("---------------------------------------------------------");
			while (!(shoot.equals("y") || shoot.equals("n"))) {
				System.out.println("please choose 'y' or 'n'");
				System.out.print("Do you shoot? (y/n): ");
				shoot = kb.next();
				System.out.println("---------------------------------------------------------");
			}
			if (shoot.equals("y")) {
				if (email.getIsPhishy()) {
					hourOfPlay.addScore();
					player.isCorrect();
				} else {
					hourOfPlay.hit();
					player.isIncorrect();
				}
			} else {
				if (!email.getIsPhishy()) {
					hourOfPlay.addScore();
					player.isCorrect();
				} else {
					hourOfPlay.hit();
					player.isIncorrect();
				}
			}
		}
		if (hourOfPlay.getScore() < hourOfPlay.getGoal()) {
			player.gameOver();
		}
		player.addScore(hourOfPlay.getScore());
		player.updateHighScore(hourOfPlay.getScore());
		printScore(player);
	}
	
	/**
	 * Prints the info of the player
	 * 
	 * @param player
	 */
	
	public static void printScore(Player player) {
		System.out.println("\n---------------------------------------------------------"
				+ "\nPlayer: " + player.getName()
				+ "\nCurrent Score: " + player.getScore()
				+ "\nHighest Round Score: " + player.getHighestScore()
				+ "\nPercent Correct: " + player.getPercent()
				+ "\nCurrent Hour: " + player.getHour()
				+ "\n---------------------------------------------------------");
	}
	
	/**
	 * Method that randomly generates email body text by selecting from the given
	 * lists of text
	 * 
	 * @param  greetings, bodies, pBodies, closings, isPhishy
	 * @return body
	 */
	
	public static String generateBody(ArrayList<String> greetings,ArrayList<String> bodies,ArrayList<String> pBodies, ArrayList<String>closings, boolean isPhishy) {
		String body = greetings.get((int)(Math.random()*greetings.size())) + "\n";
		String b;
		if (isPhishy) {
			b = pBodies.get((int)(Math.random()*pBodies.size()));
			int random = (int)(Math.random()*4)+1;
			switch (random) {
				case 1:
					break;
				case 2:
					b = "URGENT:" + b;
					break;
				case 3:
					b = "You have 24 hours to respond to this message." + b;
					break;
				case 4:
					b = b + "click this link: _______________.____";
					break;
			}
		} else {
			b = bodies.get((int)(Math.random()*bodies.size()));
		}
		body = body + wrapText(b) + "\n" + closings.get((int)(Math.random()*closings.size())) + "\nBOT\n";
		return body;
	}
	
	/**
	 * Method that wraps text to fit within the visual bounds of the console
	 * 
	 * @param text
	 * @return wrappedText
	 */
	
	public static String wrapText(String text) {
		//wrap text for display
		StringBuilder wrappedText = new StringBuilder();
		String[] words = text.split(" ");
		int currentLineLength = 0;
        for (String word : words) {
            if (currentLineLength + word.length() > textLength) {
                wrappedText.append("\n");
                currentLineLength = 0;
            }
            wrappedText.append(word).append(" ");
            currentLineLength += word.length() + 1;
        }
        return wrappedText.toString().trim();
	}
	
	/**
	 * Method that triggers when the Player's isGameOver 
	 * field is set to true
	 * 
	 * @param player
	 */
	
	public static void badEnding(Player player) {
		printLettersOneByOne(wrapText("As you stand up after valiant fighting, you notice that your blaster"
				+ "is all out of charges and yet there are still monsters wading by. "
				+ "Fr3d walks over and looks down at you"));
		printPause();
		System.out.println("\n");
		printLettersOneByOne(wrapText("Fr3d: You do not have what it takes to serve as an employee of the\n"
				+ "Galactic Fisherman Guild, " + player.getName()));
		System.out.println("\n\nYOU HAVE BEEN FIRED\n");
		printPause();
		System.out.println();
		printPause();
	}
	
	/**
	 * Method that triggers text when the Player's isGameOver 
	 * field is set to false and the player reaches the 
	 * end of the 8th hour
	 * 
	 * @param player
	 */
	
	public static void goodEnding(Player player) {
		printLettersOneByOne("As you are finishing your last report and put down your blaster,"
				+ "you notice Fr3d next to you begin to smile.\n\n");
		printLettersOneByOne("Fr3d: ");
		printPause();
		printLettersOneByOne("You did good, " + player.getName() + ". Come back here at the same time tomorrow. Stay sharp.\n\n");
	}
	
	/**
	 * Method that displays the player's final scores when 
	 * the game is done
	 * 
	 * @param player
	 */
	
	public static void credits(Player player) {
		System.out.println("\n---------------------------------------------------------\n"
				+ "Thank you for playing Galactic Phishing!\n\n"
				+ "\nPlayer: " + player.getName()
				+ "\nFinal Score: " + player.getScore()
				+ "\nHighest Round Score: " + player.getHighestScore()
				+ "\nPercent Correct: " + player.getPercent()
				+ "\nHours lasted: " + player.getHour()
				+ "\n---------------------------------------------------------");
		System.exit(0);
	}
	
	/**
	 * Method that prints letters one by one using a delay.
	 * Delay is set by global variable textSpeed, which can 
	 * be changed changed in settings()
	 * 
	 * @param text
	 */
	
	public static void printLettersOneByOne(String text) {
		for (int i = 0; i < text.length(); i++) {
	        System.out.print(text.charAt(i));
	        try {
	            Thread.sleep(textSpeed);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    }
	}
	
	/**
	 * Prints a pause ". . . " in dialogue using delays
	 */
	
	public static void printPause() {
		String text = ". . . ";
		for (int i = 0; i < text.length(); i++) {
	        System.out.print(text.charAt(i));
	        try {
	            Thread.sleep(textSpeed*4); // time interval in milliseconds
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
		}
	}
	
	/**
	 * Method that stops the game until the player 
	 * replies with "yes"
	 * 
	 * @param kb
	 */
	
	public static void continuePrompt(Scanner kb) {
		System.out.print("\n\nSay 'yes' to continue: ");
		String nextKey = kb.next();
		while (!nextKey.equals("yes")) {
			printLettersOneByOne("\nSorry, I didn't catch that. Are you ready, recruit?\n\n");
			System.out.print("Say 'yes' to continue: ");
			nextKey = kb.next();
		}
	}
	
	/**
	 * Methods that sets a timer and changes HourOfPlay's 
	 * isTimeUp field to true when the timer runs out
	 * 
	 * @param hourOfPlay
	 */
	public static void timer(HourOfPlay hourOfPlay) {
		Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
            	endTimer(hourOfPlay);
            }
        };
        // Schedule the task to run after a delay of half a minute (30 seconds * 1000 milliseconds)
        timer.schedule(task, 30*1000L);
	}

	private static void endTimer(HourOfPlay hourOfPlay) {
        hourOfPlay.timeIsUp();
    }
}
