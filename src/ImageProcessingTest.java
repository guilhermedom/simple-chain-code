import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;

/**
 * 
 * @author Leonardo Meireles Murtha Oliveira 4180285
 * @author Guilherme Domingos Faria Silva	9361094
 *
 */

public class ImageProcessingTest {
	
	@Test
	public void testGetInitial() throws IOException {
		ImageProcessing ip = new ImageProcessing("images/circle.png");
		int[] initial = new int[2];
		initial = ip.getInitial();
	}
	
	@Test
	public void testGetInitialNull() throws IOException {
		ImageProcessing ip = new ImageProcessing("images/blank.png");
		int[] initial = new int[2];
		initial = ip.getInitial();
	}
	
	@Test
	public void testGetDotHeight() throws IOException {
		ImageProcessing ip = new ImageProcessing("images/circle.png");
		int dotHeight = ip.getDotHeight();
		assertEquals(20, dotHeight);
	}
	
	@Test
	public void testGetDotWidth() throws IOException {
		ImageProcessing ip = new ImageProcessing("images/circle.png");
		int dotWidth = ip.getDotWidth();
		assertEquals(20, dotWidth);
	}
	
	@Test
	public void testGetBorder() throws IOException {
		ImageProcessing ip = new ImageProcessing("images/circle.png");
		int border = ip.getBorder();
		assertEquals(52, border);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testGetSizeBorder() throws IOException {
		ImageProcessing ip = new ImageProcessing("images/circle.png");
		double sizeBorder = ip.getSizeBorder();
		int s = (int) sizeBorder;
		assertEquals(61, s);
	}
	
	@Test
	public void testGetSizeBorder2() throws IOException {
		ImageProcessing ip = new ImageProcessing("images/ellipsis.png");
		double sizeBorder = ip.getSizeBorder();
		int border = ip.getBorder();
		int dotWidth = ip.getDotWidth();
		int dotHeight = ip.getDotHeight();
	}
	
	@Test
	public void testFindBorder() throws IOException {
		BufferedImage image;
		File inFile = new File("images/circle.png");
		image = ImageIO.read(inFile);
		ChainCodes chainCode = new ChainCodes(image,20,20,1,1);
		chainCode.doContour();

	}
	
	@Test
	public void testMain() throws IOException {
		Main m = new Main();
		m.main(null);
	}
	
	@Test
	public void testEntradaTeclado() {
		EntradaTeclado t = new EntradaTeclado();
	}
}
