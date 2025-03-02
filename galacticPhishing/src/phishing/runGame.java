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
	
	public static int textSpeed = 50;
	public static int textLength = 50;
	public static String lineBreak = "--------------------------------------------------";
	
	public static void main(String args[]) {
		// load in data from csv's
		ArrayList<String> firstNames = DataLoader.loadDataFromFile("firstnames.csv");
		ArrayList<String> lastNames = DataLoader.loadDataFromFile("lastnames.csv");
		ArrayList<String> domains = DataLoader.loadDataFromFile("domains.csv");
		ArrayList<String> greetings = DataLoader.loadDataFromFile("greetings.csv");
		ArrayList<String> bodies = DataLoader.loadDataFromFile("safeBodies.csv");
		ArrayList<String> pBodies = DataLoader.loadDataFromFile("unsafeBodies.csv");
		ArrayList<String> closings = DataLoader.loadDataFromFile("closings.csv");
		
		System.out.println(lineBreak + "\n"
				+ "Welcome to Galactic Fishing!\nCornhacks Project by Rayne Aurit\n"
				+ lineBreak);
		
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
				+ lineBreak);
		int optionSelect = 0;
		while (optionSelect != 3) {
			printLettersOneByOne("1. Change narration speed settings\n"
					+ "2. Change text length settings\n"
					+ "3. Continue to game\n"
					+ "4. Quit\n\n");
			System.out.print("Enter a number 1-4: ");
			try {
				optionSelect = kb.nextInt();
				if (optionSelect == 1) {
						// updating text speed setting for the player 
						System.out.println("Updating narration speed. . . ");
						String textSpeedInput = "";
						while(!textSpeedInput.equals("c")) {
							printLettersOneByOne(wrapText("Type 'f' for faster, 's' for slower, 'c' to continue: "));
							textSpeedInput = kb.next();
							if (textSpeedInput.equals("f")) {
								textSpeed *= 0.6;
							} if (textSpeedInput.equals("s")) {
								textSpeed *= 1.5;
							}
						}
						System.out.println("Narration speed updated\n"
								+ lineBreak + "\n");
				} else if (optionSelect == 2) {
					// updating how long the application can be for the best viewing experience
					System.out.println("Updating text length. . . ");
					String textSpeedInput = "";
					while(!textSpeedInput.equals("c")) {
						printLettersOneByOne(wrapText("here is some sample text here is some sample text here is some sample text here is some sample text here is some sample text "));
						System.out.println();					
						printLettersOneByOne(wrapText("Type 'l' for longer, 's' for shorter, 'c' to continue: "));
						textSpeedInput = kb.next();
						if (textSpeedInput.equals("l")) {
							textLength += 5;
						} if (textSpeedInput.equals("s")) {
							textLength -= 5;
						}
						lineBreak = "";
						for (int i = 0; i<textLength; i++) {
							lineBreak = lineBreak + "-";
						}
					}
					System.out.println("Text length updated\n"
							+ "Text length = " + textLength + " characters\n"
							+ lineBreak + "\n");
				} else if (optionSelect == 4) {
					credits(player);
					System.out.println("Exiting...\n"
							+ lineBreak);
					System.exit(0);
				} else if (optionSelect != 3) {
					System.out.println("Invalid number. Try again");
				}
			} catch (Exception e) {
				String throwaway = kb.next();
				System.err.println("Invalid input.");
			}
		}
		
		System.out.println(lineBreak);
		if (player.getName().equals("")) {
			printLettersOneByOne("And so it begins");
		} else {
			switch (player.getHour()+1) {
				case 1:
					printLettersOneByOne("Fr3d: Welcome to the team");
					break;
				case 2:
					printLettersOneByOne("The warmups are getting harder");
					break;
				case 3:
					printLettersOneByOne("Less and less time with bigger goals to hit");
					break;
				case 4:
					printLettersOneByOne("Keep going");
					break;
				case 5:
					printLettersOneByOne("Kill them");
					break;
				case 6:
					printLettersOneByOne("You still have yet to prove yourself");
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
		System.out.println(lineBreak);
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
		printLettersOneByOne(wrapText("yet. As your eyes focus, you notice a "
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
		System.out.println();
		
		//continues introduction sequence and how-to
		printLettersOneByOne(wrapText("Fr3d: Pleased to meet you, " + player.getName() 
		+ ". Long ago, they used to call this job \"fishing\", but that "
		+ "was back when we lived on a planet with oceans. Now, all we see "
		+ "is rock and debris everywhere"));
		printPause();
		printLettersOneByOne(wrapText("I can see the confused look on "
				+ "your face, " + player.getName() + ". This isn't a "
						+ "ghost hire. I still have a job for you. Are "
						+ "you ready?\n\n"));
		continuePrompt(kb);
		System.out.println();
		printLettersOneByOne(wrapText("Fr3d looks you up and down before nodding."));
		System.out.println("\n");
		printLettersOneByOne(wrapText("Fr3d: There are a few things that you must know before we "
				+ "start you off officially on your first day. "
				+ "I won't let you learn just by doing. There are some specific things that you need to know. "
				+ "Fishing is about catching things, yes, but specifically catching ph1$h. "
				+ "They are rogue ai that present themselves as normal mail, but they aren't. "
				+ "They are insidious bots trying to infiltrate with malicious code"));
		printPause();
		printLettersOneByOne(wrapText("Are you following, " + player.getName() + "?"));
		continuePrompt(kb);
		System.out.println();
		printLettersOneByOne(wrapText("Fr3d: Good. In this world, any malicious code can be the "
				+ "difference between having a rogue ai revolution or keeping "
				+ "the peace between you humans and us robots. I know you don't "
				+ "want us to be rampaging around like that. It is why the "
				+ "general sent you. Let's see if you are a quick learner like he says"));
		printPause();
		printLettersOneByOne(wrapText("There are three easy things that tip off if mail has "
				+ "malicious code that could infect the system"));
		printPause();
		System.out.println(lineBreak);
		printLettersOneByOne(wrapText("1. The email is misspelled or has poor grammar"));
		printPause();
		printLettersOneByOne(wrapText("Humans are bad at typing, especially those who don't know "
				+ "what language you speak well"));
		printPause();
		printLettersOneByOne(wrapText("2. The email has a [SUSPICIOUS LINK]"));
		printPause();
		printLettersOneByOne(wrapText("You'll know it when you see it"));
		printPause();
		printLettersOneByOne(wrapText("3. The email is URGENT or demands your attention"));
		printPause();
		printLettersOneByOne(wrapText("They want you to panic so you make easier mistakes"));
		printPause();
		printLettersOneByOne(wrapText("4. The have generic greetings like User or Customer"));
		printPause();
		printLettersOneByOne(wrapText("They don't actually know who you are. It's a mass mail sent out"));
		printPause();
		printLettersOneByOne(wrapText("5. The sender is unfamiliar or suspicious"));
		printPause();
		printLettersOneByOne(wrapText("They will have special characters instead of normal alphabet letters. "
				+ "One could be using 0lss0n instead of olsson, hdr.1nc instead of hdr.inc"));
		printPause();
		System.out.println(lineBreak);
		printLettersOneByOne(wrapText("Did you get all that, " + player.getName() + "?"));
		continuePrompt(kb);
		System.out.println();
		printLettersOneByOne(wrapText("This next hour, I'll send you some practice emails. They look just "
				+ "like the real deal, but don't sweat it. If you see something malicious, shoot it "
				+ "and it will be disintegrated. If you shoot anything that's not a ph1$h or don't shooot "
				+ "when you should have"));
		printPause();
		printLettersOneByOne(wrapText("Just know that it is better that you stay on your feet. I won't take failures."));
		System.out.print("\n\nAre you ready to start your training hour?");
		continuePrompt(kb);
		System.out.println();
		printLettersOneByOne(wrapText("Fr3d takes one more look at you to make sure you are listening and then hands you "
				+ "the contract. You sign it and officially become a member of PHISHERMANSGUILD.INC. Before you know it, "
				+ "a piece of mail pops up on your screen"));
		printPause();
		System.out.println("TRAINING START!");
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
		
		//beginning hour text sequence that plays only during non-training hours
		if (player.getHour() > 0) {
			printLettersOneByOne(wrapText(""));
			printLettersOneByOne(wrapText("Hour " + hourOfPlay.getHourOfPlay()));
			printPause();
			printLettersOneByOne(wrapText("Goal score " + hourOfPlay.getGoal()));
			printPause();
			printLettersOneByOne(wrapText("You have 30 seconds to hit the goal"));
			printPause();
			System.out.println();
			printLettersOneByOne(wrapText("Are you ready, " + player.getName() + "?"));
			printPause();
			continuePrompt(kb);
			System.out.println();
			printLettersOneByOne(wrapText("Starting in"));
			printPause();
			printLettersOneByOne(wrapText("3"));
			printPause();
			printLettersOneByOne(wrapText("2"));
			printPause();
			printLettersOneByOne(wrapText("1"));
			printPause();
			System.out.println("Hour starts now!");
		}
		
		//game runs
		timer(hourOfPlay);
		while (!hourOfPlay.isTimeUp()) {
			System.out.println(lineBreak);
			// creates new email randomly
			boolean isPhishy = Math.random() < 0.5;;
			String username = (lastNames.get((int)(Math.random()*lastNames.size())) + firstNames.get((int)(Math.random()*firstNames.size()))).toLowerCase();
			String domain = domains.get((int)(Math.random()*domains.size()));
			String body = generateBody(greetings,bodies,pBodies,closings,isPhishy);
			Email email = new Email(username,domain,body,isPhishy);
			
			System.out.println(email);
			System.out.println(lineBreak);
			
			// player chooses to shoot or not, handling in cases where they are correct or not
			String shoot = "";
			System.out.print("Do you shoot? (y/n): ");
			shoot = kb.next();
			System.out.println(lineBreak);
			while (!(shoot.equals("y") || shoot.equals("n"))) {
				System.out.println("please choose 'y' or 'n'");
				System.out.print("Do you shoot? (y/n): ");
				shoot = kb.next();
				System.out.println(lineBreak);
			}
			if (shoot.equals("y")) {
				if (email.getIsPhishy()) {
					hourOfPlay.addScore();
					player.isCorrect();
				} else {
					hourOfPlay.hit();
					player.isIncorrect();
					printPause();
				}
			} else {
				if (!email.getIsPhishy()) {
					hourOfPlay.addScore();
					player.isCorrect();
				} else {
					hourOfPlay.hit();
					player.isIncorrect();
					printPause();
				}
			}
		}
		
		// end hour game handling and text sequence
		if (hourOfPlay.getScore() < hourOfPlay.getGoal()) {
			player.gameOver();
		}
		player.addScore(hourOfPlay.getScore());
		player.updateHighScore(hourOfPlay.getScore());
		printLettersOneByOne(wrapText("The hour is over! You fix up your desk before the next one starts... hopefully "
				+ "you did well enough to impress Fr3d"));
		printPause();
		printScore(player);
	}
	
	/**
	 * Prints the info of the player
	 * 
	 * @param player
	 */
	
	public static void printScore(Player player) {
		System.out.println("\n" + lineBreak
				+ "\nPlayer: " + player.getName()
				+ "\nCurrent Score: " + player.getScore()
				+ "\nHighest Round Score: " + player.getHighestScore()
				+ "\nPercent Correct: " + player.getPercent()
				+ "\nCurrent Hour: " + player.getHour()
				+ "\n" + lineBreak);
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
					b = b + "click this link: [SUSPICIOUS LINK]";
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
		System.out.println("\n"+lineBreak+"\n"
				+ "Thank you for playing Galactic Phishing!\n\n"
				+ "\nPlayer: " + player.getName()
				+ "\nFinal Score: " + player.getScore()
				+ "\nHighest Round Score: " + player.getHighestScore()
				+ "\nPercent Correct: " + player.getPercent()
				+ "\nHours lasted: " + player.getHour()
				+ "\n"+lineBreak);
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
		System.out.println();
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
