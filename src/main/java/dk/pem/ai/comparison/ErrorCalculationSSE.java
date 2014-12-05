package dk.pem.ai.comparison;


public class ErrorCalculationSSE implements ErrorCalculation {
	double[] expected;
	double[] actually;


	@Override
	public double getErrorCalculationResult(double[] expected, double[] actually) {
		this.actually = actually;
		this.expected = expected;
		return getEES();
	}
	
	
	private double getEES() {
		double ees = 0;
		int length = actually.length;
		for(int i = 0; i < length; i++) {
			double a = actually[i];
			double e = expected[i];
			double diff = a-e;
			ees = ees + diff*diff;
		}
		return ees;
	}


}
