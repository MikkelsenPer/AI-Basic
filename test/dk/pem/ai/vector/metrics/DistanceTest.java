package dk.pem.ai.vector.metrics;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import dk.pem.ai.vector.metrics.Distance;

public class DistanceTest {
	double[] vectorA = {12,12,1,123,32,45,5,67,7,6};
	double[] vectorB = {12,12,1,123,32,45,5,67,7,6};
	double[] vectorC = {12,12,1,123,32,45,5,67,6,6};
	Distance distance = new Distance();

	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void testGetEuclideanDistance() {
		double euclideanDistance = distance.getEuclideanDistance(vectorA, vectorB);
		assertEquals(euclideanDistance, 0, 0);
		double euclideanDistanceNotEqual = distance.getEuclideanDistance(vectorA, vectorC);
		assertNotEquals(euclideanDistanceNotEqual, 0);
	}

}
