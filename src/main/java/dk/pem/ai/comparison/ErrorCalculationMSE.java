package dk.pem.ai.comparison;


public class ErrorCalculationMSE implements ErrorCalculation {
	ErrorCalculationSSE sse = new ErrorCalculationSSE();

	

	@Override
	public double getErrorCalculationResult(double[] expected, double[] actually) {
		int numberOfObservations = getNumberOfObservation(expected);
		double calculatedSSE = getSSE(expected, actually);		
		return calculatedSSE/numberOfObservations;
	}
	
	private int getNumberOfObservation(double[] expected) {
		return expected.length;
	}
	
	private double getSSE(double[] expected, double[] actually) {
		return sse.getErrorCalculationResult(expected, actually);
		
	}
	
	
	

}
