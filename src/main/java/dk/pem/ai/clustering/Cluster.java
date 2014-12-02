package dk.pem.ai.clustering;


import java.util.ArrayList;

/**
 * A Cluster is simply a holder for observations. 
 * A cluster holds a group of observations that belongs to the same category.
 * The clusters centroid will only be calculated if changes has been made - that is
 * if there has been added or removed observations to the cluster. 
 * @author Per Mikkelsen
 *
 */
public class Cluster {
	private ArrayList<double[]> observations = new ArrayList<double[]>();
	private String id;
	private double[] centroid;
	
	public Cluster(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}

	
	public boolean addObservation(double[] observation) {
		setCentroid(null);
		return observations.add(observation);
	}
	
	public double[] getObservation(int index) {
		return observations.get(index);
	}
	
	public boolean removeObservation(double[] observation) {
		setCentroid(null);
		return observations.remove(observation);
	}
	
	public int getNumberOfObservations() {
		return observations.size();
	}
	
	public boolean containsObservation(double[] observation) {
		return observations.contains(observation);
	}
	
	public double[] getCentroid() {
		if (centroid == null) {
			double[] sum = getCalculatedClusterSum();
			double[] newCentroid = getCalculatedClusterCentroid(sum);
			setCentroid(newCentroid);
		} 
		return centroid;
	}
	
	private double[] getCalculatedClusterSum() {
		int numberOfObservations = getNumberOfObservations();
		double[] sum = getInitializedArray();
		for(int i = 0; i < numberOfObservations; i++) {
			double[] aObservation = observations.get(i);
			sum = getSum(aObservation, sum);
		}
		return sum;
	}
	
	
	private double[] getSum(double[] observationA, double[] observationB) {
		int length = observationA.length;
		double[] merged = new double[length];
		for(int i = 0; i < length; i++) {
			double featureA = observationA[i];
			double featureB = observationB[i];
			double sum = featureA + featureB;
			merged[i] = sum;
		}
		return merged;
	}
	
	private double[] getCalculatedClusterCentroid(double[] observationA) {		
		int numberOfObservations = getNumberOfObservations();
		int arraySize = observationA.length;
		double[] meanArray = getInitializedArray();
		for(int i = 0; i < arraySize; i++) {
			double featureA = observationA[i];
			double featureMean = featureA / numberOfObservations;
			meanArray[i] = featureMean;
		}
		return meanArray;
	}
	
	
	private double[] getInitializedArray() {
		int size = getFeatureSize();
		double[] array = new double[size];
		return array;
	}
	
	private int getFeatureSize() {
		int size = 0;
		size = observations.get(0).length;
		return size;
	}
	
	private void setCentroid(double[] newCentroid) {
		this.centroid = newCentroid;
	}
	
	
	
}
