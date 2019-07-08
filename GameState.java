/***************************************************************************************
  CS540 - Section 2
  Homework Assignment 2: Game Playing

  GameState.java
  This defines the state of a game.
  ---------
  	* Free to modify anything in this file, except the class name 
  	You are required:
  		- Not import any external libraries
  		- Not include any packages 

	@author: TA 
	@date: Feb 2017
*****************************************************************************************/

public class GameState {
	private int size;			// The number of stones
	private boolean[] stones;	// Game state: true for available stones, false for taken ones
	private int lastMove;		// The last move

	/**
    * Class constructor specifying the number of stones.
    */
	public GameState (int size) {
		
		this.size = size; 

		//  For convenience, we use 1-based index, and set 0 to be unavailable
		this.stones = new boolean[this.size + 1];
		this.stones[0] = false;
		
		// Set default state of stones to available
		for (int i = 1; i <= this.size; ++i) {
			this.stones[i] = true;
		} 

		// Set the last move be -1
		this.lastMove = -1;
	}

	/**
	* This method is used to take a stone out
	* @param idx Index of the taken stone
	* @return Nothing
	*/
	public void remove_stone(int idx) {
		this.stones[idx] = false;
		this.lastMove = idx;
	}

	/**
	* These are get/set methods for a stone
	* @param idx Index of the taken stone
	*/
	public void set_stone(int idx) {
		this.stones[idx] = true;
	}
	public boolean get_stone(int idx) {
		return this.stones[idx];
	}

	/**
	* These are get/set methods for lastMove variable
	* @param idx Index of the taken stone
	*/
	public int get_last_move() {
		return this.lastMove;
	}
	public void set_last_move(int move) {
		this.lastMove = move;
	}

	/**
	* This is get method for game size
	* @return int the number of stones
	*/
	public int get_size() {
		return this.size;
	}

	/**
	* This method is used to print out the state of game
	*/
	public void log() {
		// Print the last move 
		System.out.println(lastMove);

		// Print the available stones in order
		for (int i = 1; i <= size; ++i) 
			if (stones[i]) {
				System.out.printf("%d ", i);
			}
		System.out.println();
	}
}	
