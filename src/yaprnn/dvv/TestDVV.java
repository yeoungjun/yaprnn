package yaprnn.dvv;

import java.util.*;

class TestDVV {
	
	public static void main(String[] args) throws InvalidFileException {
		TestDVV.test01();
		TestDVV.test02();
	}

	public static void test01() {
		byte[][] image = { {1, 0, 0, 0}, {0, 1, 0, 0}, {0, 0, 1, 0}, {0, 0, 0, 1} };
		IdxPicture pic = new IdxPicture(image, "1", "a", 0);
		pic.subsample(0, 0, new yaprnn.mlp.TangensHyperbolicus());
		double[] data = pic.getData();
		for(int i=0; i<image.length; i++)
			for(int j=0; j<image[0].length; j++)
				if(data[i*image[0].length + j] != Math.tanh((double)image[i][j]))
					System.out.println("data scaled incorrectly");
		System.out.println("Test 01 OK");
	}

	public static void test02() throws InvalidFileException {
		DVV dvv = new DVV("images", "labels");
		Collection<? extends Data> allData = dvv.getDataSet();
		for(Data data : allData) {
			byte[][] image = ((IdxPicture)data).previewRawData();
			System.out.println(data.getLabel());
			for(int i=0; i<image.length; i++) {
				for(int j=0; j<image[0].length; j++)
					System.out.print(image[i][j] < 0 ? '*' : ' ');
				System.out.println("");
			}
			System.out.println("");
		}
	}

}
