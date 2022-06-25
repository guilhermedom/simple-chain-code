import java.io.IOException;

public class Main {
	public static void main(String[] args) throws IOException {
		ImageProcessing test;
		String s = new String();
		
		System.out.println("Insert file name including file format (e.g. .png)");
		s = KeyboardInput.readString();
		
		test = new ImageProcessing(s);
		
		System.out.println(test.getDotWidth());
		System.out.println(test.getDotHeight());
		
		System.out.println(test.getBorder());
		
		System.out.println(test.getSizeBorder());
	}
}
