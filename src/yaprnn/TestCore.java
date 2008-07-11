package yaprnn;

import java.util.*;

import yaprnn.mlp.*;

class TestCore {

	private static Core core;
	private static ActivationFunction tanh = new TangensHyperbolicus();

	public static void test01() throws Exception {
		core = new Core();
		core.openIdxPicture("/home/fisch/Uni/mpgi3/images/images", "/home/fisch/Uni/mpgi3/images/labels");
		core.preprocess(14, 0.5, tanh);
		core.chooseRandomTrainingData(0.8, 0.2);
		core.newMLP("test", 5, 20, 0, 0);
		Eta eta = new NoEtaAdjustment(0.2);
		core.trainOnline(eta, 1000, 0.1, 0.99);
	}

	public static void test02() throws Exception {
		Collection<String> filenames = new ArrayList<String>(420);
		for (int i = 1; i<8; i++){
			for (int j = 1; j<13; j++){
				filenames.add("/home/fisch/Uni/mpgi3/vokale/data/a" + i + "-" + j + ".aiff");
				filenames.add("/home/fisch/Uni/mpgi3/vokale/data/e" + i + "-" + j + ".aiff");
				filenames.add("/home/fisch/Uni/mpgi3/vokale/data/i" + i + "-" + j + ".aiff");
				filenames.add("/home/fisch/Uni/mpgi3/vokale/data/o" + i + "-" + j + ".aiff");
				filenames.add("/home/fisch/Uni/mpgi3/vokale/data/u" + i + "-" + j + ".aiff");
			}
		}
		core = new Core();
		core.openAiffSound(filenames);
		core.preprocess(250, 0.5, tanh);
		core.chooseRandomTrainingData(0.6, 0.4);
		core.newMLP("test", 5, 20, 0, 0);
		Eta eta = new NoEtaAdjustment(0.2);
		core.trainOnline(eta, 1000, 0.1, 0.99);
	}
	
	public static void main(String[] args) throws Exception {
		/*TestCore.test01();*/
		TestCore.test02();
	}

}
