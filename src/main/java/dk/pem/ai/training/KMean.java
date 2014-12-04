package dk.pem.ai.training;

import java.util.ArrayList;
import java.util.Iterator;

import dk.pem.ai.clustering.Cluster;
import dk.pem.ai.util.RandomGenerator;
import dk.pem.ai.vector.metrics.Distance;

/**
 * Implementation of the K-Mean algorithm.
 * K-Means is an unsupervised learning algorithm that solve the clustering problem. That is
 * grouping n observations into n clusters where n >= m.
 * The grouping will be done by picking the shortest distance to the clusters centroid for each
 * observation. To calculate the distance the Euclidean algorithm is used.
 * 
 * K-Mean is a non deterministic algorithm meaning that running K-Mean on the same set of data
 * will not necessary give the same result. Below you can see 2 results of K-Mean on the same data - 
 * as you can see the result are not the same but both results are actually correct.
 * 
 * First Result
 * Cluster 0 has an observation 1090.0
 * Cluster 1 has an observation 11.0
 * Cluster 1 has an observation 101.0
 * Cluster 1 has an observation 105.0
 * Cluster 1 has an observation 12.0
 * Cluster 1 has an observation 10.0
 * Cluster 1 has an observation 1.0
 * Cluster 1 has an observation 100.0
 * Cluster 2 has an observation 1000.0
 * Cluster 2 has an observation 1010.0
 * 
 * Second result:
 * Cluster 0 has an observation 1000.0
 * Cluster 0 has an observation 1010.0
 * Cluster 0 has an observation 1090.0
 * Cluster 1 has an observation 105.0
 * Cluster 1 has an observation 100.0
 * Cluster 1 has an observation 101.0
 * Cluster 2 has an observation 10.0
 * Cluster 2 has an observation 11.0
 * Cluster 2 has an observation 12.0
 * Cluster 2 has an observation 1.0
 * 
 *  
 * -------------------How to use-------------------------
 * Initiate the K-Mean with the number of categories (cluster). Then use the addObverstion to
 * add the vectors that the algorithm should split into the n clusters that you specified in the 
 * creation of the object. It is required that the vectors has the same length and the numbers of 
 * vectors should at minimum be the same as the number of clusters (otherwise the algorithm cannot 
 * solve it).
 * When all the vectors has been added them simply call the method getOrdredClusters. This will return
 * an ArrayList with n clusters containing the observations grouped in the clusters.
 *   
 * @author Per Mikkelsen
 *
 */
public class KMean {
	ArrayList<Cluster> clusters = new ArrayList<Cluster>();
	int numberOfCategories;
	int countNumberOfAddedObservations;
	boolean reorderingComplete = false;
	
	/**
	 * Creates an instance of the KMean with the specified number of categories given by 
	 * the parameter numberOfCategories. 
	 * @param numberOfCategories
	 */
	public KMean(int numberOfCategories) {
		this.numberOfCategories = numberOfCategories;
		createClusters(numberOfCategories);
		
	}
	
	
	/**
	 * Adds an observation to a random cluster. 
	 * @param observation
	 * @return true if the observation was added
	 */
	public boolean addObservation(double[] observation) {
		countNumberOfAddedObservations++;
		int addToCategory = RandomGenerator.getRandomZeroBasedIndex(numberOfCategories);
		Cluster cluster = clusters.get(addToCategory); 
		return cluster.addObservation(observation);
	}
	
	/**
	 * When all the observations has been added then this method will take care
	 * of sorting all the observations to the clusters (categories) with the use
	 * of the K-Mean algorithm. It is necessary that there are minimum the same amount
	 * of observations as categories - if not this method will throw an exception.
	 * @return
	 * @throws Exception
	 */
	public ArrayList<Cluster> getOrdredClusters() throws Exception {
		if(numberOfCategories > countNumberOfAddedObservations) {
			new Exception("Not enough observations added.");
		}
		while(!reorderingComplete) {
			fixClustersForZeroContent();
			reorderObservationsToClusters();
		}
		return clusters;	
	}
	
	
	private void createClusters(int numberOfCategories) {
		for(int i = 0; i < numberOfCategories; i++) {
			Cluster aCluster = new Cluster(String.valueOf(i));
			clusters.add(i, aCluster);
		}		
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
			int randomCluster = RandomGenerator.getRandomZeroBasedIndex(numberOfCategories);
			Cluster aCluster = clusters.get(randomCluster);
			int numberOfObservations = aCluster.getNumberOfObservations();
			if(numberOfObservations > 0) {
				int randomObservationIndex = RandomGenerator.getRandomZeroBasedIndex(numberOfObservations);
				randomObservation = aCluster.getObservation(randomObservationIndex);
				aCluster.removeObservation(randomObservation);
				found = true;
			}
			numberOfTries++;			
		}
		return randomObservation;
	}
	
	/**
	 * Enumerate each observation and pair (move) them to the cluster that
	 * has the nearest distance to observation. If an observation is being moved
	 * then the global parameter ReorderingComplete is being set to false. This is done
	 * to keep track of when the clusters centroid has to be recalculated. This method only
	 * runs through the clusters once and it will be necessary to run through them until 
	 * reorderingComplete = true. The clusters centroid is being calculated by the Cluster.
	 */
	private void reorderObservationsToClusters() {
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
	
	
	/**
	 * Find the nearest cluster to the observation given as a parameter. The
	 * Euclidean distance is used to calculate the distance
	 * @param observation
	 * @return The Cluster that is has the shortest Euclidean distance to the observation
	 */
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
			return false; //no move if the clusters are the same
		}
		movefromCluster.removeObservation(observation);
		moveToCluster.addObservation(observation);
		return true;
		
	}
	
}
