/***************************************************************************************
  CS540 - Section 2
  Homework Assignment 2: Game Playing

  Helper.java
  This is a singleton that creates some assistant functions.
  ---------
 * Free to modify anything in this file, except the class name 
  	You are require:
  		- Not to import any external libraries
  		- Not to include any packages 
 *Notice: To use this file, you should implement 2 methods below.

	@author: TA 
	@date: Feb 2017
 *****************************************************************************************/

public class Helper {

	/** 
	 * Class constructor.
	 */
	private Helper () {}

	/**
	 * This method is used to check if a number is prime or not
	 * @param x A positive integer number
	 * @return boolean True if x is prime; Otherwise, false
	 */
	public static boolean is_prime(int x) {

		for (int i = 2; i < x; ++i)
		{
			if (x % i == 0)
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * This method is used to get the largest prime factor 
	 * @param x A positive integer number
	 * @return int The largest prime factor of x
	 */
	public static int get_largest_prime_factor(int x) {

		int factor = 1;

		int count = 1;

		while (count < x)
		{
			if (is_prime(count) && x % count == 0)
			{
				factor = count;
			}
			count++;
		}
		return factor;
	}

	/**
	 * This method is used to print out the values of alpha and beta
	 * @param alpha Current alpha value
	 * @param beta Current beta value
	 * @return Nothing.
	 */
	public static void log_alphabeta(int alpha, int beta) {
		System.out.printf("alpha: %d\tbeta: %d\n", alpha, beta);
	}
}