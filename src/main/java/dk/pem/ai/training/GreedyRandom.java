package dk.pem.ai.training;

import java.util.ArrayList;


import dk.pem.ai.comparison.ErrorCalculationMSE;
import dk.pem.ai.learning.LearningAlgorithm;
import dk.pem.ai.util.RandomGenerator;

public class GreedyRandom {
	private double[] longTermMemory;	
	double lowRange;
	double highRange;
	double[] inputData;
	double[] expextedOutputData;
	LearningAlgorithm learningAlgorithm;
	double bestScore = -1;

	public double getBestScore(int numberOfIterations) {
		bestScore = getCalculateScore(inputData, expextedOutputData);
		for (int i = 0; i < numberOfIterations; i++) {
			iterate();			
		}
		System.out.println("One param " +longTermMemory[0]);
		System.out.println("One param " +longTermMemory[1]);
		System.out.println("One param " +longTermMemory[2]);
		
		return bestScore;
	}
	
	public void SetLongTermMemory(double[] longTermMemory) {
		this.longTermMemory = longTermMemory;
	}
	
	public void setRandomHighLow(double lowRange, double highRange) {
		this.lowRange = lowRange;
		this.highRange = highRange;
	}
	
	public void setLearningAlgorithm(LearningAlgorithm learningAlgorithm) {
		this.learningAlgorithm = learningAlgorithm;
	}
	
	public void setTrainingData(ArrayList<double[]> inputAndOutput){
		inputData = inputAndOutput.get(0);
		expextedOutputData = inputAndOutput.get(1);
	}
	
	private int getTrainingDataSize() {
		return inputData.length;
	}
	
	private void iterate() {
		int size = getTrainingDataSize();
		double[] newLongTermMemory = getNewLongtermMemory();
		double[] calculatedData = new double[size];
		for(int i = 0; i < size; i++) {
			double input = inputData[i];
			calculatedData[i] = getCalculateResult(input, newLongTermMemory);		
		}
		double score = getCalculateScore(calculatedData, expextedOutputData);
		if (isBestScore(score)) {
			setNewBestGuess(score, newLongTermMemory);			
		}
	}
	
	
	
	private void setNewBestGuess(double score, double[] longtermMemory) {
		bestScore = score;
		this.longTermMemory = longtermMemory;
	}
	
	private boolean isBestScore(double score) {
		if (bestScore == -1) {
			bestScore = getCalculateScore(inputData, expextedOutputData);			
		}
		
		return score < bestScore;
	}
	
	private double[] getNewLongtermMemory() {
		int length = longTermMemory.length;
		double[] newLongTermMemory = new double[length];
		for (int i = 0; i < length; i++) {
			double aNewRandom = RandomGenerator.getRandomZeroBasedIndex(lowRange, highRange);
			newLongTermMemory[i] = aNewRandom;
		}
		return newLongTermMemory;
		
	}
	
	private double getCalculateResult(double input, double[] longTermMemory) {
		LearningAlgorithm la = getLearningAlgoritm();
		return la.getCalculatedResult(input, longTermMemory);
	}
	
	private LearningAlgorithm getLearningAlgoritm() {
		return learningAlgorithm;
	}

	public double[] getLongtermMemory() {
		return longTermMemory;
	}
	
	
	private double getCalculateScore(double[] input, double[] expected) {
		ErrorCalculationMSE mse = new ErrorCalculationMSE();
		return mse.getErrorCalculationResult(expected, input);
		
	}
}
