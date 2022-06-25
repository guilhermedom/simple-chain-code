import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 *
 * @author Guilherme Domingos Faria Silva
 * @author Leonardo Meireles Murtha Oliveira
 *
 */
public class ImageProcessing {
	private File inFile;
	private static BufferedImage image;
	private ChainCode chainCode;
	int[] initial = new int[2];
	
	/**
	 *
	 * Constructor reads file name, opens it and creates buffered image.
	 * @param fileName
	 * @throws IOException
	 */
	public ImageProcessing(String fileName) throws IOException {
		inFile = new File(fileName);
		// System.out.println(inFile.exists() + "  " + inFile.getAbsolutePath());
		image = ImageIO.read(inFile);
	}
	
	/**
	 *
	 * Pass through image until it finds the first point that is not white,
	 * given by the hexadecimal string "ffffffff".
	 * @return coordinates of the first non-white point. null is returned if
	 * the image is entirely white.
	 * 
	 */
	public int[] getInitial() {
		for(int y = 0; y < image.getHeight() ; y++){
			for(int x = 0; x < image.getWidth() ; x++){
				if(Integer.toHexString(image.getRGB(x, y)).compareTo("ffffffff") != 0){
					initial[0] = x;
					initial[1] = y;
					return initial;
				}
			}	
		}	
		return null;
	}
	
	/**
	 * Pass through image and computes the largest height of the object in the image.
	 * This is the largest continuous vertical line of non-white points.
	 * from the object in the image.
	 * @return height of the object.
	 */
	public int getDotHeight() {
		int dotHeight = Integer.MIN_VALUE,auxHeight;
		
		for (int x = 0; x < image.getWidth(); x++) {
			auxHeight = 0;
			for (int y = 0; y < image.getHeight(); y++) {
				if (Integer.toHexString(image.getRGB(x, y)).compareTo("ffffffff") != 0) {
					auxHeight++;
				}
			}
			if (auxHeight > dotHeight) {
				dotHeight = auxHeight;
			}
		}
		return dotHeight;
	}
	
	/**
	 * Pass through image and computes the largest width of the object in the image.
	 * This is the largest continuous horizontal line of non-white points.
	 * @return width of the object.
	 */
	public int getDotWidth() {
		int dotWidth = Integer.MIN_VALUE,auxWidth;

		for (int y = 0; y < image.getHeight(); y++) {
			auxWidth = 0;
			for (int x = 0; x < image.getWidth(); x++) {	
				if (Integer.toHexString(image.getRGB(x, y)).compareTo("ffffffff") != 0) {
					auxWidth++;
				}
			}
			if (auxWidth > dotWidth) {
				dotWidth = auxWidth;
			}
		}
		return dotWidth;
	}
	
	/**
	 * Instantiate chain code for the image and calls function to calculate the object contour.
	 */
	public void initChainCode() {
		getInitial();
		chainCode = new ChainCode(image, image.getWidth(), image.getHeight(), initial[0], initial[1]);
		chainCode.doContour();
	}
	
	/**
	 * Uses chain code to count the number of points in the border of the object. 
	 * @return number of points in the border of the object.
	 */
	public int getBorder() {
		initChainCode();
		chainCode.calcBorderPoints();
		return chainCode.getBorderPoints();
	}
	
	/**
	 * Uses chain code to calculate the size of the border of the object.
	 * @return size of the border of the object.
	 */
	public double getSizeBorder() {
		initChainCode();
		chainCode.calcPerimeter();
		return chainCode.getPerimeter();
	}

}
