package yaprnn.dvv;

import java.util.*;
import yaprnn.mlp.*;

class TestDVV {

	static TangensHyperbolicus tanh = new TangensHyperbolicus();
	
	public static void main(String[] args) throws InvalidFileException {
		TestDVV.test01();
		TestDVV.test02();
		TestDVV.test03();
		//TestDVV.test();
	}

	public static void test01() {
		byte[][] img = new byte[1][1];
		img[0][0] = 100;
		IdxPicture pic = new IdxPicture(img, "0", "file", 0);
		pic.subsample(1, 0.95, tanh);
		if(pic.getData()[0] != tanh.compute(100.0))
			System.out.println("Error in DVV-Test 01");
		pic.subsample(1, 0.0, tanh);
		if(pic.getData()[0] != tanh.compute(100.0))
			System.out.println("Error in DVV-Test 01");
		pic.subsample(0, 0.0, tanh);
		pic.subsample(2, 0.0, tanh);
	}

	public static void test02() {
		byte[][] img = new byte[100][100];
		for(int i=0; i<img.length; i++)
			for(int j=0; j<img.length; j++)
				img[i][j] = 50;
		IdxPicture pic = new IdxPicture(img, "0", "file", 0);
		for(int k=0; k<=95; k++) {
			byte[][] data = pic.previewSubsampledData(23, k/100.0);
			for(int i=0; i<data.length; i++)
				for(int j=0; j<data.length; j++)
					if(data[i][j] != 50)
						System.out.println("Error in DVV-Test 02");
		}
	}

	public static void test03() {
		byte[][] img = new byte[10][10];
		for(int i=0; i<img.length; i++)
			for(int j=0; j<img.length; j++)
				img[i][j] = 0;
		for(int i=0; i<img.length; i++)
			img[i][i] = 10;
		IdxPicture pic = new IdxPicture(img, "0", "file", 0);
		byte[][] data = pic.previewSubsampledData(5, 0.0);
		for(int i=0; i<data.length; i++)
			for(int j=0; j<data.length; j++) {
				if(i==j && data[i][j] != 5)
					System.out.println("Error in DVV-Test 03");
				if(i!=j && data[i][j] != 0)
					System.out.println("Error in DVV-Test 03");
			}
	}

	public static void test() throws InvalidFileException {
		DVV dvv = new DVV("images", "labels");
		Collection<? extends Data> allData = dvv.getDataSet();
		for(Data data : allData) {
			byte[][] image = ((IdxPicture)data).previewSubsampledData(10, 0);
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
