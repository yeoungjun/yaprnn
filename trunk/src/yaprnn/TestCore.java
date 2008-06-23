package yaprnn;

import yaprnn.mlp.*;

class TestCore {

	private static Core core;
	private static ActivationFunction tanh = new TangensHyperbolicus();

	public static void test01() throws Exception {
		core = new Core();
		core.openIdxPicture("images", "labels");
		core.preprocess(14, 0.5, tanh);
		core.chooseRandomTrainingData(0.8, 0.2);
		int[] layers = { 20, 20, 20 };
		int[] func = { 0, 0, 0, 0, 0 };
		double[] bias = { 0, 0, 0 };
		core.newMLP(layers, func, bias, false);
		core.trainOnline(0.2, 1000, 0.1);
	}

	public static void main(String[] args) throws Exception {
		TestCore.test01();
	}

}
