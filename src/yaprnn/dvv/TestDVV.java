package yaprnn.dvv;

import java.util.*;

import yaprnn.mlp.*;

class TestDVV {

	static TangensHyperbolicus tanh = new TangensHyperbolicus();
	
	public static void main(String[] args) throws InvalidFileException {
		TestDVV.test01();
		TestDVV.test02();
		TestDVV.test03();
		TestDVV.test04();
		TestDVV.test05();
		TestDVV.testSound01();
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

	public static void test04() {
		byte[][] img = { {1, -1, 1, -1}, {1, -1, 1, -1}, {1, -1, 1, -1}, {1, -1, 1, -1} };
		IdxPicture pic = new IdxPicture(img, "0", "file", 0);
		byte[][] data = pic.previewSubsampledData(2, 0.0);
		int r = 128;
		for(int i=0; i<data.length; i++)
			for(int j=0; j<data.length; j++)
				if(data[i][j] != (byte)r)
					System.out.println("Error in DVV-Test 04");
	}

	public static void test05() {
		byte[][] img = { {-128, 127}, {127, -128} };
		IdxPicture pic = new IdxPicture(img, "0", "file", 0);
		byte[][] data = pic.previewSubsampledData(1, 0.95);
		int r = 510/4;
		for(int i=0; i<data.length; i++)
			for(int j=0; j<data.length; j++)
				if(data[i][j] != (byte)r)
					System.out.println("Error in DVV-Test 05");
	}

	public static void test() throws InvalidFileException {
		DVV dvv = null;
		try {
			dvv = new DVV("images", "labels");
		} catch(Exception e) {
			e.printStackTrace();
		}
		Collection<? extends Data> allData = dvv.getDataSet();
		for(Data data : allData) {
			byte[][] image = ((IdxPicture)data).previewSubsampledData(10, 0);
			System.out.println(data.getLabel());
			for(int i=0; i<image.length; i++) {
				for(int j=0; j<image[0].length; j++)
					System.out.print(image[i][j] != 0 ? '*' : ' ');
				System.out.println("");
			}
			System.out.println("");
		}
	}
	
	public static void testSound01(){
		try {
		Collection<String> incomming  = new ArrayList<String>(2);
		incomming.add("/home/fisch/Uni/mpgi3/vokale/data/a1-1.aiff");
		incomming.add("/home/fisch/Uni/mpgi3/vokale/data/dummy.aiff");
		incomming.add("/home/fisch/Uni/mpgi3/vokale/data/a9-30.aiff");
		DVV dvv = new DVV(incomming);
		Collection<? extends Data> allData = dvv.getDataSet();;
		for(Data data : allData) {
			System.out.println(((AiffSound)data).getFilename());
			System.out.println(((AiffSound)data).getLabel());
			data.subsample(0, 0, new yaprnn.mlp.TangensHyperbolicus());
		}  
		}
		catch (InvalidFileException e) {
			System.out.println(e.getFilename() + " hat kein gültiges Format.");
		}
		catch (NoSuchFileException e) {
			System.out.println(e.getFilename() + " ist nicht vorhanden.");
		}
	}

}
