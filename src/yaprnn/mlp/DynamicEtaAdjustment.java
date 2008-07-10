package yaprnn.mlp;

public class DynamicEtaAdjustment implements Eta {

	double eta, lastError, signum, roh;
	
	public DynamicEtaAdjustment(double eta, double signum, double roh) {
		this.eta = eta;
		this.signum = signum;
		this.roh = roh;
	}
	
	public double getEta(double lastError) {
		
		if(this.lastError > lastError)
			eta *= signum;
		else if(this.lastError < lastError)
			eta *= roh;
		
		this.lastError = lastError;
		return eta;
	}

	public String toString(){
		return Double.toString(eta);
	}
}
