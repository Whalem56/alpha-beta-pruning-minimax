/***************************************************************************************
  CS540 - Section 2
  Homework Assignment 2: Game Playing

  TakeStones.java
  This is the main class that implements functions for Take Stones playing!
  ---------
 *Free to modify anything in this file, except the class name 
  	You are required:
  		- To keep the class name as TakeStones for testing
  		- Not to import any external libraries
  		- Not to include any packages 
 *Notice: To use this file, you should implement 4 methods below.

	@author: TA 
	@date: Feb 2017
 *****************************************************************************************/

import java.util.ArrayList;


public class TakeStones {

	final int WIN_SCORE = 100;	// score of max winning game
	final int LOSE_SCORE = -100;// score of max losing game
	final int INFINITY = 1000;	// infinity constant

	/** 
	 * Class constructor.
	 */
	public TakeStones () {};


	/**
	 * This method is used to generate a list of successors 
	 * @param state This is the current game state
	 * @return ArrayList<Integer> This is the list of state's successors
	 */
	public ArrayList<Integer> generate_successors(GameState state) {
		int lastMove = state.get_last_move();	// the last move
		int size = state.get_size();			// game size
		ArrayList<Integer> successors = new ArrayList<Integer>(); // list of successors
		// First move
		if (lastMove == -1)
		{
			// Base Case
			if (size == 3)
			{
				size = 1;
			}
			else
			{
				// Original size is even
				if (size % 2 == 0)
				{
					size = (size / 2) - 1;
				}
				else
				{
					size = size / 2;
				}
				// Adjust if still even
				if (size % 2 == 0)
				{
					size--;
				}
			}
			for (int i = 1; i <= size; i = i + 2)
			{
				successors.add(i);
			}
		}
		// Not the first move
		else
		{
			for (int i = 1; i <= size; ++i)
			{
				// Stone is factor or multiple of lastMove
				if (lastMove % i == 0 || i % lastMove == 0)
				{
					// Stone has not been taken and is available
					if (state.get_stone(i))
					{
						successors.add(i);
					}
				}
			}
		}	
		return successors;
	}


	/**
	 * This method is used to evaluate a game state based on 
	 * the given heuristic function 
	 * @param state This is the current game state
	 * @return int This is the static score of given state
	 */
	public int evaluate_state(GameState state, boolean maxPlayer) 
	{
		// if stone 1 is still available, score is 0
		if (state.get_stone(1))
		{
			return 0;
		}
		int lastMove = state.get_last_move();
		int score = 0;
		ArrayList<Integer> successors = generate_successors(state);
		if (1 == lastMove) 
		{
			// Size of successors is even
			if (successors.size() % 2 == 0)
			{
				score = -5;
			}
			else
			{
				score = 5;
			}
		} 
		else if (Helper.is_prime(lastMove))
		{
			for (int i = 0; i < successors.size(); ++i)
			{
				if (successors.get(i) % lastMove == 0)
				{
					score++;
				}
			}
			// There's an even number of multiples of prime last move
			if (score % 2 == 0)
			{
				score = -7;
			}
			else
			{
				score = 7;
			}
		} 
		else 
		{
			int prime = Helper.get_largest_prime_factor(lastMove);
			for (int i = 0; i < successors.size(); ++i)
			{
				if (successors.get(i) % prime == 0)
				{
					score++;
				}
			}
			// There's an even number of multiples of largest prime factor
			// of last move in the successor list
			if (score % 2 == 0)
			{
				score = -6;
			}
			else
			{
				score = 6;
			}
		}
		return score;
	}


	/**
	 * This method is used to get the best next move from the current state
	 * @param state This is the current game state
	 * @param depth Current depth of search
	 * @param maxPlayer True if player is Max Player; Otherwise, false
	 * @return int This is the number indicating chosen stone
	 */
	public int get_next_move(GameState state, int depth, boolean maxPlayer) 
	{
		int move = -1;			// the best next move 
		int alpha = -INFINITY;	// initial value of alpha
		int beta = INFINITY;	// initial value of alpha

		// Getting successors of the given state 
		ArrayList<Integer> successors = generate_successors(state);

		// Check if depth is 0 or it is terminal state 
		if (0 == depth || 0 == successors.size()) {
			state.log();
			Helper.log_alphabeta(alpha, beta);
			return move;
		}
		int tempVal = 0;
		int lastMove;
		int currStone;
		for (int i = 0; i < successors.size(); ++i)
		{
			currStone = successors.get(i);
			lastMove = state.get_last_move();
			state.remove_stone(currStone);
			tempVal = alphabeta(state, depth - 1, alpha, beta, !maxPlayer);
			// Max Player
			if (maxPlayer)
			{
				if (tempVal > alpha)
				{
					alpha = tempVal;
					move = currStone;
				}
			}
			// Min Player
			else
			{
				if (tempVal < beta)
				{
					beta = tempVal;
					move = currStone;
				}
			}
			// Return state back to original self
			state.set_stone(currStone);
			state.set_last_move(lastMove);
		}

		// Print state and alpha, beta before return 
		state.log();
		Helper.log_alphabeta(alpha, beta);
		return move;
	}


	/**
	 * This method is used to implement alpha-beta pruning for both 2 players
	 * @param state This is the current game state
	 * @param depth Current depth of search
	 * @param alpha Current Alpha value
	 * @param beta Current Beta value
	 * @param maxPlayer True if player is Max Player; Otherwise, false
	 * @return int This is the number indicating score of the best next move
	 */
	public int alphabeta(GameState state, int depth, int alpha, int beta, boolean maxPlayer) 
	{
		int v = INFINITY; // score of the best next move

		ArrayList<Integer> successors = generate_successors(state);
		int lastMove;
		int currStone;
		if (successors.isEmpty())
		{
			if (!maxPlayer)
			{
				v = WIN_SCORE;
			}
			else
			{
				v = LOSE_SCORE;
			}
		}
		else
		{
			// Max Player
			if (maxPlayer)
			{
				v = -INFINITY;
				for (int i = 0; i < successors.size(); ++i)
				{
					// Base Case
					if (0 == depth)
					{
						v = max(v, evaluate_state(state, maxPlayer));
					}
					else
					{
						currStone = successors.get(i);
						lastMove = state.get_last_move();
						state.remove_stone(currStone);
						v = max(v, alphabeta(state, depth - 1, alpha, beta, !maxPlayer));
						// Return state back to self
						state.set_stone(currStone);
						state.set_last_move(lastMove);
					}
					// Prune if needed
					if (beta < v)
					{
						break;
					}
					// Update alpha if applicable
					alpha = max(v, alpha);
				}
			}
			// Min Player
			else
			{
				for (int i = 0; i < successors.size(); ++i)
				{
					// Base Case
					if (0 == depth)
					{
						v = min(v, evaluate_state(state, maxPlayer));
					}
					else
					{
						currStone = successors.get(i);
						lastMove = state.get_last_move();
						state.remove_stone(currStone);
						v = min(v, alphabeta(state, depth - 1, alpha, beta, !maxPlayer));
						// Return state back to self
						state.set_stone(currStone);
						state.set_last_move(lastMove);
					}
					// Prune if needed
					if (alpha > v)
					{
						break;
					}
					// Update beta if applicable
					beta = min(v, beta);
				}
			}
		}
		// Print state and alpha, beta before return 
		state.log();
		Helper.log_alphabeta(alpha, beta);
		return v;	
	}


	/**
	 * This is the main method which makes use of addNum method.
	 * @param args A sequence of integer numbers, including the number of stones,
	 * the number of taken stones, a list of taken stone and search depth
	 * @return Nothing.
	 * @exception IOException On input error.
	 * @see IOException
	 */
	public static void main (String[] args) 
	{
		try 
		{
			// Read input from command line
			int n = Integer.parseInt(args[0]);		// the number of stones
			int nTaken = Integer.parseInt(args[1]);	// the number of taken stones

			// Initialize the game state
			GameState state = new GameState(n);		// game state
			int stone;
			for (int i = 0; i < nTaken; i++) 
			{
				stone = Integer.parseInt(args[i + 2]);
				state.remove_stone(stone);
			}

			int depth = Integer.parseInt(args[nTaken + 2]);	// search depth
			// Process for depth being 0
			if (0 == depth)
				depth = n + 1;

			TakeStones player = new TakeStones();	// TakeStones Object
			boolean maxPlayer = (0 == (nTaken % 2));// Detect current player

			// Get next move
			int move = player.get_next_move(state, depth, maxPlayer);	
			// Remove the chosen stone out of the board
			state.remove_stone(move); 
			// Print Solution 
			System.out.println("NEXT MOVE");
			state.log();

		} catch (Exception e) {
			System.out.println("Invalid input");
		}
	}
	private int max(int a, int b)
	{
		if (a > b)
		{
			return a;
		}
		else
		{
			return b;
		}
	}
	private int min(int a, int b)
	{
		if (a < b)
		{
			return a;
		}
		else
		{
			return b;
		}
	}
}