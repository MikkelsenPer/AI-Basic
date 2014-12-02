package dk.pem.ai.training.unsupervised;

import java.util.ArrayList;
import java.util.Iterator;

import dk.pem.ai.clustering.Cluster;
import dk.pem.ai.util.RandomCategory;
import dk.pem.ai.vector.metrics.Distance;

/**
 * Implementation of the K-Mean algorithm.
 * @author Per Mikkelsen
 *
 */
public class KMean {
	ArrayList<Cluster> clusters = new ArrayList<Cluster>();
	int numberOfCategories;
	int countNumberOfAddedObservations;
	boolean reorderingComplete = false;
	
	public KMean(int numberOfCategories) {
		this.numberOfCategories = numberOfCategories;
		createClusters(numberOfCategories);
		
	}
	
	private void createClusters(int numberOfCategories) {
		for(int i = 0; i < numberOfCategories; i++) {
			Cluster aCluster = new Cluster(String.valueOf(i));
			clusters.add(i, aCluster);
		}		
	}

	/**
	 * Adds an observation to a random cluster
	 * @param observation
	 * @return true if the observation was added
	 */
	public boolean addObservation(double[] observation) {
		countNumberOfAddedObservations++;
		int addToCategory = RandomCategory.getRandomZeroBasedIndex(numberOfCategories);
		Cluster cluster = clusters.get(addToCategory); 
		return cluster.addObservation(observation);
	}
	
	public ArrayList<Cluster> getOrdredClusters() throws Exception {
		if(numberOfCategories > countNumberOfAddedObservations) {
			new Exception("Not enough observations added.");
		}
		while(!reorderingComplete) {
			fixClustersForZeroContent();
			reorderObservations();
		}
		return clusters;	
	}
	
	/**
	 * A cluster needs to have at least one observation. This method fixes
	 * all clusters that don't have any observations. The method simply takes 
	 * a random observation from a random cluster and adds it to the cluster
	 * with no observations.
	 */
	private void fixClustersForZeroContent() {
		Iterator<Cluster> ite = clusters.iterator();
		while(ite.hasNext()) {
			Cluster aCluster = ite.next();
			int numberOfObservations = aCluster.getNumberOfObservations();
			if(numberOfObservations == 0) {
				System.out.println("Found an emppty cluster. Will move one observation to this cluster with the ID " +aCluster.getId());
				double[] observation = getObservationFromNonZeroCluster();
				aCluster.addObservation(observation);
			}
		}
		reorderingComplete = true;
	}
	
	/**
	 * Returns a random observation from a random cluster that has more than one observation
	 * The observation is deleted from the cluster 
	 * @return
	 */
	private double[] getObservationFromNonZeroCluster() {
		boolean found = false;
		int numberOfTries = 0;
		int maxNumberOfTries = 10*numberOfCategories;
		double[] randomObservation = null;
		while(!found || numberOfTries > maxNumberOfTries){
			int randomCluster = RandomCategory.getRandomZeroBasedIndex(numberOfCategories);
			Cluster aCluster = clusters.get(randomCluster);
			int numberOfObservations = aCluster.getNumberOfObservations();
			if(numberOfObservations > 0) {
				int randomObservationIndex = RandomCategory.getRandomZeroBasedIndex(numberOfObservations);
				randomObservation = aCluster.getObservation(randomObservationIndex);
				aCluster.removeObservation(randomObservation);
				found = true;
			}
			numberOfTries++;			
		}
		return randomObservation;
	}
	
	private void reorderObservations() {
		for(int i = 0; i < numberOfCategories; i++) {
			Cluster cluster = clusters.get(i);
			int numberOfobservations = cluster.getNumberOfObservations();
			for(int j = 0; j < numberOfobservations; j++) {
				double[] observation = cluster.getObservation(j);
				System.out.println("Cluster " +cluster.getId() +": Reorder the observation " +observation[0]);
				Cluster targetCluster = findNearstCluster(observation);
				boolean moved = moveObservationToCluster(cluster, targetCluster, observation);
				if(moved) {
					numberOfobservations--;
					reorderingComplete = false;
				}
				System.out.println("obs has been moved " +moved);
				
				
			}
		}
	}
	
	
	
	private Cluster findNearstCluster(double[] observation) {
		Cluster targetCluster = null;
		double distance = -1;
		for(int i = 0; i < numberOfCategories; i++) {
			Cluster cluster = clusters.get(i);
			double[] clusterCentroid = cluster.getCentroid();
			double tempDistance = Distance.getEuclideanDistance(clusterCentroid, observation);
			if(distance == -1 || distance > tempDistance ) {
				distance = tempDistance;
				targetCluster = cluster;
			}
		}
		return targetCluster;
		
	}
	
	/**
	 * moves an observation from one cluster to another cluster. Returns false if the clusters are identical.
	 * If the observation is moved the true will be returned.
	 * @param movefromCluster
	 * @param moveToCluster
	 * @param observation
	 * @return
	 */
	private boolean moveObservationToCluster(Cluster movefromCluster, Cluster moveToCluster, double[] observation) {
		if(movefromCluster == moveToCluster) {
			return false;
		}
		movefromCluster.removeObservation(observation);
		moveToCluster.addObservation(observation);
		return true;
		
	}
	
}
