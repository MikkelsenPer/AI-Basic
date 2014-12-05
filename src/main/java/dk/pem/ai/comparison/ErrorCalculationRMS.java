package dk.pem.ai.comparison;

public class ErrorCalculationRMS implements ErrorCalculation {
	ErrorCalculationMSE mse = new ErrorCalculationMSE(); 

	@Override
	public double getErrorCalculationResult(double[] expected, double[] actually) {
		double calculatesMSE = mse.getErrorCalculationResult(expected, actually);
		return Math.sqrt(calculatesMSE);
	}

}
