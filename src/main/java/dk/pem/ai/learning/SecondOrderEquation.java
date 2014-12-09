package dk.pem.ai.learning;

public class SecondOrderEquation implements LearningAlgorithm {

	@Override
	public double getCalculatedResult(double inputdata, double[] longtermMemory) {
		double a = longtermMemory[0] * inputdata * inputdata;
		double b = longtermMemory[1] * inputdata;
		double c = longtermMemory[2];
		
		return a + b + c;
	}

}
