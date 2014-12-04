package dk.pem.ai.training;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import dk.pem.ai.clustering.Cluster;
import dk.pem.ai.training.KMean;

public class KMeanTest {
	KMean kMean;
	double[] observationA = {10,12};
	double[] observationB = {11,12};
	double[] observationC = {100,322};
	double[] observationD = {101,222};
	double[] observationE = {105,111};
	double[] observationF = {12,12};
	double[] observationG = {1000,1111};
	double[] observationH = {1010,1212};
	double[] observationJ = {1,1};
	double[] observationK = {1090,2121};
	

	@Before
	public void setUp() throws Exception {
		kMean = new KMean(3);
		kMean.addObservation(observationA);
		kMean.addObservation(observationB);
		kMean.addObservation(observationC);
		kMean.addObservation(observationD);
		kMean.addObservation(observationE);
		kMean.addObservation(observationF);
		kMean.addObservation(observationG);
		kMean.addObservation(observationH);
		kMean.addObservation(observationJ);
		kMean.addObservation(observationK);
	}

	@Test
	public void testGetOrdredClusters() {
		ArrayList<Cluster> clusters = null;
		try {
			clusters = kMean.getOrdredClusters();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Cluster clusterA = clusters.get(0);
		double sumA = getClusterSum(clusterA);
		
		Cluster clusterB = clusters.get(1);
		double sumB = getClusterSum(clusterB);
		
		Cluster clusterC = clusters.get(2);
		double sumC = getClusterSum(clusterC);
		
		double totalSum = sumA + sumB + sumC;
		assertEquals(totalSum, 3440, 0);
		
		printCluster(clusterA);
		printCluster(clusterB);
		printCluster(clusterC);
		
		
	}
	
	private void printCluster(Cluster cluster) {
		int numberOfObservations = cluster.getNumberOfObservations();
		for(int i = 0; i < numberOfObservations; i++) {
			double[] observation = cluster.getObservation(i);
			System.out.println("Cluster " +cluster.getId() +" has an observation " +observation[0]);
			
		}
	}
	
	
	private double getClusterSum(Cluster cluster) {
		int numberOfObservations = cluster.getNumberOfObservations();
		double sum = 0;
		for(int i = 0; i < numberOfObservations; i++) { 
			double[] obs = cluster.getObservation(i);
			double subSum = obs[0];
			sum = sum + subSum;
		}
		return sum;
	}
	

}
