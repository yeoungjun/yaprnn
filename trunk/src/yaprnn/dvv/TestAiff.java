package yaprnn.dvv;

import java.util.*;

class TestAiff{

		public static void main(String[] args) throws InvalidFileException {
			TestAiff.test01();
		}

		
		public static void test01(){
			try {
			Collection<String> incomming  = new ArrayList<String>(2);
			incomming.add("/home/fisch/Uni/mpgi3/workspace/yaprnn/yaprnn/dvv/o3-2.aiff");
			incomming.add("/home/fisch/Uni/mpgi3/workspace/yaprnn/yaprnn/dvv/o3-2-hex.aiff");
			DVV dvv = new DVV(incomming);
			Collection<? extends Data> allData = dvv.getDataSet();;
			for(Data data : allData) {
				System.out.println(((AiffSound)data).getFilename());
				System.out.println(((AiffSound)data).getLabel());
				data.subsample(0, 0, new yaprnn.mlp.TangensHyperbolicus());
			}  
			}
			catch (InvalidFileException e) {
				e.printStackTrace();
			}
		}
	 
}

