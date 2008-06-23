package yaprnn;

import yaprnn.mlp.*;

class TestCore {

	private static Core core;
	private static ActivationFunction tanh = new TangensHyperbolicus();

	public static void test01() {
		core = new Core();
		core.openIdxPicture("images", "labels");
		core.preprocess(14, 0.5, tanh);
		core.chooseRandomTrainingData(0.6, 0.2);
		int[] layers = { 10, 10 ,10 };
		int[] func = { 0, 0, 0, 0, 0 };
		double[] bias = { 0, 0, 0 };
		core.newMLP(layers, func, bias, false);
		core.trainOnline(1.0, 10000, 0.1);
	}

	public static void main(String[] args) {
		TestCore.test01();
	}

}
