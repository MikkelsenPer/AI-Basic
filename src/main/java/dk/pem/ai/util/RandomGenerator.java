package dk.pem.ai.util;

import java.util.Random;

/**
 * This class is uses the java.util.Random and adds no new functionality. 
 * The class only creates one instance of the java.util.Random and not one instance for each random number. 
 * @author Per Mikkelsen
 *
 */
public class RandomGenerator {
	private static Random randomGenerator = new Random();
	
	/**
	 * Returns a random, uniformly distributed, integer between 0 and the numberOfCategory -1. 
	 * @param numberOfCategory the number of categories.
	 * @return random number
	 */
	public static int getRandomZeroBasedIndex(int numberOfCategory) {
		int category = randomGenerator.nextInt(numberOfCategory);
		return category;
	}
	
	public static double getRandomZeroBasedIndex(double lowRange, double highRange) {
		double number = randomGenerator.nextDouble();
		number = (number * highRange) + lowRange;
		if (number > highRange) {
			return number - lowRange;
		}
		return number;
	}

}
