
import acm.io.*;
import acm.program.*;
import acm.util.*;

public class Yahtzee extends GraphicsProgram implements YahtzeeConstants {
	
	public static void main(String[] args) {
		new Yahtzee().start(args);
	}
	
	public void run() {
		IODialog dialog = getDialog();
		
		nPlayers = dialog.readInt("Enter number of players"); /*Check so its no more or less than max/min players and adding player to game */
		 while(true) {
	            if (nPlayers > 0 && nPlayers <= MAX_PLAYERS) {
	                break;
	            } else if (nPlayers > MAX_PLAYERS) {
	                nPlayers = dialog.readInt(MAX_PLAYERS + " is the maximum.\nEnter number of players ");
	            } else {
	                nPlayers = dialog.readInt("There must be at least one player.\nEnter number of players ");
	            }
	        }
		playerNames = new String[nPlayers];
		for (int i = 1; i <= nPlayers; i++) {
			playerNames[i - 1] = dialog.readLine("Enter name for player " + i);
		}
		
		playerBonusCounted = new boolean[nPlayers]; 	/*Initziating values to check if the bonus for a player is calculated  */
		display = new YahtzeeDisplay(getGCanvas(), playerNames);
		fillArrayScoreCard();		
		playGame();
	}
	
	/* filling the array that will be the scoreboard with empotyScore (that is -2) to use for check if its empty */
	private void fillArrayScoreCard() {	
		scorecard = new int[nPlayers][N_CATEGORIES];
		for (int row = 0 ; row < nPlayers ; row++) {
			for (int column = 0 ; column < N_CATEGORIES ; column++ ) {
				scorecard[row][column] = emptyScore;
			}
		}
		
	}

	private void playGame() { /*starting the loop that check what player it is then starting the game */
		
		for (int r = 0; r < N_SCORING_CATEGORIES ; r++) {
			for (int p = 1 ; p <= nPlayers ; p++) {
				display.printMessage("Its player nr " + p + "'s turn. Please roll the dice!");
				throwDiceOne(p);
				throwNext(p);
				updateCategory(dice, p);		
			}
		}
	}
		
	public void throwDiceOne(int p) { /*initiating the first roll*/
		display.waitForPlayerToClickRoll(p);
		dice = rollDice();
		display.displayDice(dice);
	}
		
	public int[] rollDice() { /*Generates the array for each number on the first 5 dices */
		for (int i = 0; i < 5 ; i++) {
			dice[i] = (rgen.nextInt(3, 6));
		}
		return dice;
	}
		
	public void rollDice(int i) { /*Overloaded method for the reroll dices*/
		dice[i] = (rgen.nextInt(1, 6));
	}
	
	public void throwNext(int p) {
		for (int t = 0 ; t < 2 ; t++) {
		display.printMessage("Choose dices to reroll and press Roll Again. If u want to save all dices, just press Roll Again.");
		display.waitForPlayerToSelectDice();
		for (int i = 0 ; i < N_DICE ; i++) {
			if (display.isDieSelected(i)) {
			rollDice(i);	
			}
		}	
		display.displayDice(dice);
		}
	}
	
	/* The main method for updating the category. From here we send in the dice and player to different methods
	 * to check if the dice roll is valid. If not, the point will be zero on the pressed category.
	 */
	private void updateCategory(int[] dice, int player){
		
		display.printMessage("Please choose a category");
		int score;
		int chosen = display.waitForPlayerToSelectCategory();
		
		while (true){
			if ((chosen >= ONES && chosen <= SIXES) && scorecard[player-1][chosen-1] == emptyScore) {
				score = OneToSix(chosen, dice);
				display.updateScorecard(chosen, player, score);
				break;
			}
			if (chosen >= THREE_OF_A_KIND && chosen <= LARGE_STRAIGHT && scorecard[player-1][chosen-1] == emptyScore) {
				score = checkCat(chosen, dice);
				if(score > -1) {
					display.updateScorecard(chosen, player, score);
					break;
				}
			}
			if (chosen == YAHTZEE && scorecard[player-1][chosen-1] == emptyScore) {
				score = checkCat(YAHTZEE, dice);
				if(score > -1) {
					display.updateScorecard(YAHTZEE, player, score);
					break;
				}
			}
			if (chosen == CHANCE && scorecard[player-1][chosen-1] == emptyScore) {
				score = checkChance(dice);
				display.updateScorecard(CHANCE, player, score);
				break;
			}
			else {
				display.printMessage("You cannot choose this category. Select another one.");
				chosen = display.waitForPlayerToSelectCategory();
			}
		}
		
		/*Put the round point in the 2d array scoreboard */
		scorecard[player-1][chosen-1] = score;
		
		/*Checking if the player allready counted bonus or not, than checking if its time to sum up score of game */
		if (playerBonusCounted[player-1] == false) {
		checkBonus(player);
		}
		checkSumScore(player);
		score = 0; /* reseting the score to 0 for the next player */
		
	}
	
	
	private void checkBonus(int player) { /* Checking if the upper score on scorecard has score or not */
		int sum = 0;
		boolean checked = (scorecard[player-1][ONES-1] > emptyScore && scorecard[player-1][TWOS-1] > emptyScore && scorecard[player-1][THREES-1] > emptyScore 
				&& scorecard[player-1][FOURS-1] > emptyScore && scorecard[player-1][FIVES-1] > emptyScore && scorecard[player-1][SIXES-1] > emptyScore);
		
		if (checked) {  /* summing up the upper score and calculating bonus */
				for (int i = 0 ; i < 6 ; i ++) {
					sum += scorecard[player-1][i];
				}
				
				if (sum >= BONUSCAP && checked) {
					display.updateScorecard(UPPER_SCORE, player, sum);
					display.updateScorecard(UPPER_BONUS, player, BONUS_SCORE);
					scorecard[player-1][UPPER_SCORE-1] = sum;
					scorecard[player-1][UPPER_BONUS-1] = BONUS_SCORE;
					playerBonusCounted[player-1] = true;
				}
				else {
					display.updateScorecard(UPPER_SCORE, player, sum);
					display.updateScorecard(UPPER_BONUS, player, 0);
					scorecard[player-1][UPPER_SCORE-1] = sum;
					scorecard[player-1][UPPER_BONUS-1] = 0;
					playerBonusCounted[player-1] = true;
				}
				
		}
	}
		
	private void checkSumScore(int player) {  /* First checking if the scorecard is filled */
		int categoryChecked = 0;
		for (int s = 0 ; s < 15 ; s++)
			if (scorecard[player-1][s] > -2) {
				categoryChecked++;
			}
		if (categoryChecked == 15) {
			int sum = 0;
			int lowerSum = 0;
			
			for (int i = 6 ; i < 15 ; i ++) {  /*Counting from upper score to chance*/
				sum += scorecard[player-1][i];
				display.updateScorecard(TOTAL, player, sum);
			}
			for (int i = 8 ; i < 15 ; i ++) {
				lowerSum += scorecard[player-1][i];
				display.updateScorecard(LOWER_SCORE, player, lowerSum);
			}
		}
	}
	
	private int OneToSix(int cat, int[] dice) {	/*Calculating the sum of dice 1-6 */
		int score = 0;
		
		if (cat >= ONES && cat <= SIXES) {
			for (int i = 0 ; i < N_DICE ; i++) {
				if(dice[i] == cat) {
					score += cat;
				}
			}
		}
		return score;
	}
	
	private int checkCat(int cat, int dice[]) {
		int score = 0;
		int[] samedice = new int[7];
		
			/* Counting through all dices and putting the different numbers into the samedice[] array.
			 * The samedice array is 7 slots big so i can use the dicenumber variable for both checking the dice's number and filling the array */
		
		for (int dicenumber = 1 ; dicenumber <= 6 ; dicenumber++) {
			for (int i = 0 ; i < dice.length ; i++) {
				if (dice[i] == dicenumber) {
				samedice[dicenumber] += 1;
				}
			}
		}
		
		for (int j = 1 ; j < samedice.length ; j++) {  /*Using the samedice array to check for different categories*/
			if (cat == THREE_OF_A_KIND && samedice[j] >= 3 && samedice[j] <= 5) {
				score = j * 3;
				return score;
			}
			else if (cat == FOUR_OF_A_KIND && samedice[j] >= 4 && samedice[j] <= 5) {
				score = j * 4;
				return score;
			}
			else if (cat == YAHTZEE && samedice[j] == 5) {
				score = 50;
				return score;
			}
			else if (cat == FULL_HOUSE && samedice[j] == 3) {
				int checkDouble = samedice [j];
				for (int k = 1 ; k < samedice.length ; k++) {
					if (samedice[k] == 2 && samedice[k] != checkDouble) {
						score = 25;
						return score;
					}
				}
			}
			else if (cat == SMALL_STRAIGHT && samedice[1] == 1 && samedice [2] == 1 && samedice [3] == 1 && samedice[4] == 1 && samedice[5] == 1 && samedice[6] == 0) {
				score = 30;
				return score;
			}
			else if (cat == LARGE_STRAIGHT && samedice[1] == 0 && samedice [2] == 1 && samedice [3] == 1 && samedice[4] == 1 && samedice[5] == 1 && samedice[6] == 1) {
				score = 40;
				return score;
			}
			
		}
		return score; /*return 0 if the category don't match*/
	}
	
	private int checkChance(int dice[]) { 
		int score = 0;
		for (int i = 0 ; i < dice.length ; i++) {
			score += dice[i];
		}
		return score;
	}

	/* Private instance variables */
	private int[] dice = new int[5];
	private int[][] scorecard;
	private String[] playerNames;
	private boolean playerBonusCounted[];
	private int emptyScore = -2;
	
	private int nPlayers;
	private YahtzeeDisplay display;
	private RandomGenerator rgen = new RandomGenerator();
}
