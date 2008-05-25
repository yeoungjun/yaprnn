package mlp;

public class TestMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			
			int[] layers = new int[6];
			double[] bias = new double[6];
			ActivationFunction[] f = new ActivationFunction[8];

			ActivationFunction tan = new Linear();
						
			for (int i = 0; i < f.length; i++) {
				if(i < layers.length) 	layers[i] = i+1;
				f[i] = tan;
				if(i < bias.length) bias[i] = 0;
			}

			
			
			MLP testNetwork = new MLP(2,2, layers, 0.2, f, bias, false);
			double[] input = new double[2];
			double[] target =  new double[2];
			for(int i = 0; i < 2; i++) {
				input[i] = i;
				target[i] = 0;
			}
			
			target[0] = 1;
			
			System.out.println(testNetwork.toString());
			for(int i = 0; i < 10000; i++)
				if(testNetwork.runOnline(input, target) < 0.001){
					System.out.println("Nach " + i + " Iterationen Fehlerwert von 0.001 unterschritten.");
					break;
				}
			
			System.out.println(testNetwork.toString());
			System.out.println("Versuche Eingabedaten [0][1] zu erkenen...");
			for(double i : testNetwork.classify(target))
				System.out.println(i);
			

		} catch (BadConfigException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
