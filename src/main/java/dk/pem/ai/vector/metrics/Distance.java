package dk.pem.ai.vector.metrics;

/**
 * Class for calculating distances between vectors.
 * @author Per Mikkelsen
 *
 */
public class Distance {

	/**
	 * Returns the Euclidean distance between to vectors.
	 */
	public double getEuclideanDistance(double[] vectorA, double[] vectorB) {
		double sum = 0;
		for (int i = 0; i < vectorA.length; i++) {
			double vA = vectorA[i];
			double vB = vectorB[i];
			double dif = vA - vB;
			sum = sum + dif*dif;
		}
		return Math.sqrt(sum);
	}
	
}
