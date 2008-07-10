package yaprnn.mlp;

public class NoEtaAdjustment implements Eta {

	private double eta;
	
	public NoEtaAdjustment(double eta) {
		this.eta = eta;
	}
	
	public double getEta(double lastError) {
		return eta;
	}
	
	public String toString(){
		return Double.toString(eta);
	}
}
