package yaprnn.mlp;

public class StaticEtaAdjustment implements Eta {

	private double eta, iterarorBorder, multiplier;
	private int iterations = 0;
	
	public StaticEtaAdjustment(double eta, double multiplier, int iterations) {
		this.eta = eta;
		this.iterarorBorder = iterations;
		this.multiplier = multiplier;
	}
	
	public double getEta(double lastError) {
		if(iterations % iterarorBorder == 0 && iterations != 0)
			eta *= multiplier;
		
		iterations++;
		return eta;
	}

	public String toString(){
		return Double.toString(eta);
	}
}
