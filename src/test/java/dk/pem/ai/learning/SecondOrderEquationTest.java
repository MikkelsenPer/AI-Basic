package dk.pem.ai.learning;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import dk.pem.ai.training.GreedyRandom;

public class SecondOrderEquationTest {
	SecondOrderEquation soe = new SecondOrderEquation();
	
	double[] longTermMemory = {10,3,5};
	double lowRange = 1;
	double highRange = 3;
	
	double[] inputData = {1,2,3};
	double[] outputData = {7,16,29};
	
	int numberOfIterations = 100000;

	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void testGreedyRandom() {
		GreedyRandom gr = new GreedyRandom();
		gr.setLearningAlgorithm(soe);
		gr.SetLongTermMemory(longTermMemory);
		gr.setRandomHighLow(lowRange, highRange);
		ArrayList<double[]> inputAndOutput = new ArrayList<double[]>();
		inputAndOutput.add(0, inputData);
		inputAndOutput.add(1, outputData);
		gr.setTrainingData(inputAndOutput);
		
		double score = gr.getBestScore(numberOfIterations);
		System.out.println("Best score " +score);
		
		assertEquals(score, 0, 0);
		
		
	}

}
