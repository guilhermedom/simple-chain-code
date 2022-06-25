import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Guilherme Domingos Faria Silva
 *
 */
public class ChainCode {
	private int borderPoints, height, width, x0, y0;
	ArrayList<Integer> chainCode = new ArrayList<Integer>();
	private double perimeter;
	private boolean[][] pixelMap;
	private BufferedImage image;
	
	/**
	 * Initialize chain code algorithm.
	 * @param image
	 * @param width
	 * @param height
	 * @param x0
	 * @param y0
	 */
	public ChainCode(BufferedImage image, int width, int height, int x0, int y0) {
		this.borderPoints = 0;
		this.perimeter = 0;
		this.width = width;
		this.height = height;
		this.image = image;
		this.x0 = x0;
		this.y0 = y0;
		this.pixelMap = new boolean[height][width];
		
		// Initialize all values in pixel map to false
		for(int i = 0; i < this.height; i++) {
			Arrays.fill(this.pixelMap[i], false);
		}
	}
	
	/**
	 * Sets starting point of the object.
	 * @param auxPoint
	 */
	public void setInitialPoint(int[] auxPoint) {
		auxPoint[0] = this.x0;
		auxPoint[1] = this.y0;
	}
		
	/**
	 * Gets border points of the object.
	 * @return points in the border of the object.
	 */
	public int getBorderPoints() {
		return borderPoints;
	}

	/**
	 * Computes number of points in the border of the object. This is equal to the number
     * of edges or the size of vector chainCode.
	 */
	public void calcBorderPoints() {
		this.borderPoints = chainCode.size();
	}

	/**
	 * Gets the perimeter of the object. That is, the size of the border of the object.
	 * @return size of the border of the object.
	 */
	public double getPerimeter() {
		return perimeter;
	}
	
	/**
	 * Checks if point is part of the border.
	 * @param x
	 * @param y
	 * @return true if point is in the border, false otherwise.
	 */
	public boolean checkIfBorder (int x, int y) {
		if (pixelMap[y][x]) {
			return false;
		}
		if (Integer.toHexString(image.getRGB(x, y - 1)).compareTo("ffffffff") == 0) {
			return true;
		}
		if (Integer.toHexString(image.getRGB(x + 1, y)).compareTo("ffffffff") == 0) {
			return true;
		}
		if (Integer.toHexString(image.getRGB(x, y + 1)).compareTo("ffffffff") == 0) {
			return true;
		}
		if (Integer.toHexString(image.getRGB(x - 1, y)).compareTo("ffffffff") == 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * Finds next neighbor point in the border, clockwise manner.
	 * @param x
	 * @param y
	 * @param auxPoint
	 */
	public void findBorder (int x, int y, int[] auxPoint) {		
		// Eastern neighbor is a border point.
		if (Integer.toHexString(image.getRGB(x + 1, y)).compareTo("ffffffff") != 0 && checkIfBorder(x + 1, y)) {
			auxPoint[0] = x + 1;
			auxPoint[1] = y;
			chainCode.add(0);
			return;
		}

		// South-eastern neighbor is a border point.
		if (Integer.toHexString(image.getRGB(x + 1, y + 1)).compareTo("ffffffff") != 0 && checkIfBorder(x + 1, y + 1)) {
			auxPoint[0] = x + 1;
			auxPoint[1] = y + 1;
			chainCode.add(7);
			return;
		}

		// Southern neighbor is a border point.
		if (Integer.toHexString(image.getRGB(x, y + 1)).compareTo("ffffffff") != 0 && checkIfBorder(x, y + 1)) {
			auxPoint[0] = x;
			auxPoint[1] = y + 1;
			chainCode.add(6);
			return;
		}

		// South-western neighbor is a border point.
		if (Integer.toHexString(image.getRGB(x - 1, y + 1)).compareTo("ffffffff") != 0 && checkIfBorder(x - 1, y + 1)) {
			auxPoint[0] = x - 1;
			auxPoint[1] = y + 1;
			chainCode.add(5);
			return;
		}

		// Western neighbor is a border point.
		if (Integer.toHexString(image.getRGB(x - 1, y)).compareTo("ffffffff") != 0 && checkIfBorder(x - 1, y)) {
			auxPoint[0] = x - 1;
			auxPoint[1] = y;
			chainCode.add(4);
			return;
		}
 
		// North-western neighbor is a border point.
		if (Integer.toHexString(image.getRGB(x - 1, y - 1)).compareTo("ffffffff") != 0 && checkIfBorder(x - 1, y - 1)) {
			auxPoint[0] = x - 1;
			auxPoint[1] = y - 1;
			chainCode.add(3);
			return;
		}

		// Northern neighbor is a border point.
		if (Integer.toHexString(image.getRGB(x, y - 1)).compareTo("ffffffff") != 0 && checkIfBorder(x, y - 1)) {
			auxPoint[0] = x;
			auxPoint[1] = y - 1;
			chainCode.add(2);
			return;
		}

		// North-eastern neighbor is a border point.
		if (Integer.toHexString(image.getRGB(x + 1, y - 1)).compareTo("ffffffff") != 0 && checkIfBorder(x + 1, y - 1)) {
			auxPoint[0] = x + 1;
			auxPoint[1] = y - 1;
			chainCode.add(1);
			return;
		}
			
		// Inserting last edge in clockwise manner.
		if (auxPoint[0] == x0 && auxPoint[1] - 1 == y0) {
			chainCode.add(2);
		}
		if (auxPoint[0] + 1 == x0 && auxPoint[1] == y0) {
			chainCode.add(0);
		}
		if (auxPoint[0] + 1 == x0 && auxPoint[1]-1 >= 0 && auxPoint[1] - 1 == y0) {
			chainCode.add(1);
		}
		
		return;
	}
	
	/**
	 * Calculate perimeter using chain code array of points.
	 */
	public void calcPerimeter() {
		this.perimeter = 0;
		
		for (int i = 0; i < chainCode.size(); i++) {
			// Calculating diagonal.
			if (chainCode.get(i) == 1 || chainCode.get(i) == 3 || chainCode.get(i) == 5 || chainCode.get(i) == 7) {
				this.perimeter += Math.sqrt(2);
			}
			else {
				this.perimeter += 1;
			}
		}
	}
	
	/**
	 * Contour object and create chainCode array.
	 */
	public void doContour() {
		int[] auxPoint = new int[2];
		setInitialPoint(auxPoint);
		
		do {
			pixelMap[auxPoint[1]][auxPoint[0]] = true;
			findBorder(auxPoint[0], auxPoint[1], auxPoint);			
		} while (!pixelMap[auxPoint[1]][auxPoint[0]]); // Breaks if auxiliary point was already visited, we are at the initial point.
	}
	
	/**
	 * Auxiliary function. Prints pixel map.
	 */
	public void printPixelMap() {
		System.out.println(borderPoints + "!" + this.height + this.width);
		for (int i = 0; i < this.height; i++) {
			for (int j = 0; j < this.width; j++) {
				if (pixelMap[i][j]) { 
					System.out.print("º ");
				}
				else {
					System.out.print("  ");
				}
			}
			System.out.println();
		}
	}
}
