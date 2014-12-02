package dk.pem.ai.clustering;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ClusterTest {
	Cluster cluster;

	@Before
	public void setUp() throws Exception {
		cluster = new Cluster("id");
	}
	
	@Test
	public void testAddAndRemove() {
		double[] obsA = {2,2,2};
		boolean added = cluster.addObservation(obsA);
		assertTrue(added);
		boolean removed = cluster.removeObservation(obsA);
		assertTrue(removed);
		//This must return false - it has already been removed
		boolean removedFalse = cluster.removeObservation(obsA);
		assertFalse(removedFalse);
	}
	
	@Test
	public void testGetNumberOfObservations() {
		int a = cluster.getNumberOfObservations();
		assertEquals(a, 0);
		double[] obsA = {2,2,2};
		cluster.addObservation(obsA);
		a = cluster.getNumberOfObservations();
		assertEquals(a, 1);
		//Cleanup
		cluster.removeObservation(obsA);
	}

	@Test
	public void testGetCentroid() {
		double[] obsA = {2,2,2};
		double[] obsB = {8,2,4};
		double[] expected = {5,2,3};
		cluster.addObservation(obsA);
		cluster.addObservation(obsB);
		double[] calculatedMean = cluster.getCentroid();
		assertArrayEquals(expected, calculatedMean, 0);
		double[] obsC = {11,2,0};
		double[] expectedC = {7,2,2};
		cluster.addObservation(obsC);
		double[] calculatedMeanC = cluster.getCentroid();
		assertArrayEquals(expectedC, calculatedMeanC, 0);
		//Calculate it again with no changes to see if we get the same back
		double[] calculatedMeanD = cluster.getCentroid();
		assertArrayEquals(expectedC, calculatedMeanD, 0);
		
		//Cleanup
		cluster.removeObservation(obsA);
		cluster.removeObservation(obsB);
		cluster.removeObservation(obsC);
	}

}
