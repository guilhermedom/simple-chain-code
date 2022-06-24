import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 
 * @author Leonardo Meireles Murtha Oliveira 4180285
 * @author Guilherme Domingos Faria Silva	9361094
 *
 */
public class ImageProcessing {
	private File inFile;
	private static BufferedImage image;
	private ChainCodes chainCode;
	int[] initial = new int[2];
	
	/**
	 * Construtor que recebe apenas o nome do arquivo, ele abre o arquivo(imagem) e depois cria
	 * uma bufferedimage.
	 * @param fileName
	 * @throws IOException
	 */
	public ImageProcessing(String fileName) throws IOException {
		inFile = new File(fileName);
		// System.out.println(inFile.exists() + "  " + inFile.getAbsolutePath());
		image = ImageIO.read(inFile);
	}
	
	/**
	 * Varre a imagem como se fosse uma matriz até
	 * localizar o primeiro ponto que não é branco dado pelo string hexadecimal(ffffffff),
	 * se string hexadecimal do getRGB naquela coordenada for diferente da do branco então retornamos
	 * um vetor com as coordenadas x e y.
	 * @return retorna um par de coordenadas do primeiro ponto diferente de branco,
	 * caso a matriz seja inteira branca sera retornado null.
	 * 
	 */
	public int[] getInitial(){
		
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
	 * Varre a imagem como se fosse uma matriz e calcula a maior altura que é a maior linha ininterrupta
	 * do objeto de valores diferentes de branco na vertical.
	 * @return altura do objeto.
	 */
	public int getDotHeight() {
		int dotHeight = Integer.MIN_VALUE,auxHeight;
		
		for(int x = 0; x < image.getWidth() ; x++){
			auxHeight = 0;
			for(int y = 0; y < image.getHeight() ; y++){
				if(Integer.toHexString(image.getRGB(x, y)).compareTo("ffffffff") != 0) auxHeight++;	
			}
			if(auxHeight > dotHeight) dotHeight = auxHeight; 
		}
		
		return dotHeight;
	}
	
	/**
	 * Varre a imagem como se fosse uma matriz e calcula a maior largura que é a maior linha ininterrupta
	 * do objeto de valores diferentes de branco na horizontal.
	 * @return largura do objeto.
	 */
	public int getDotWidth() {
		int dotWidth = Integer.MIN_VALUE,auxWidth;

		for (int y = 0; y < image.getHeight(); y++) {
			auxWidth = 0;
			for (int x = 0; x < image.getWidth() ; x++) {	
				if (Integer.toHexString(image.getRGB(x, y)).compareTo("ffffffff") != 0) auxWidth++;	
			}
			if (auxWidth > dotWidth) dotWidth = auxWidth; 
		}
		return dotWidth;
	}
	
	/**
	 * Inicializa e faz o contorno da imagem usando chain codes.
	 */
	public void initChainCodes() {
		getInitial();
		chainCode = new ChainCodes(image, image.getWidth(), image.getHeight(), initial[0], initial[1]);
		chainCode.doContour();
	}
	
	/**
	 * Usa algoritimo de chain Codes para calcular o numero de pontos na borda usando o vetor de chain codes 
	 * @return o número de pontos da borda
	 */
	public int getBorder() {
		initChainCodes();
		chainCode.calcBorderPoints();
		return chainCode.getBorderPoints();
	}
	
	/**
	 * Usa algoritimo de chain Codes para calcular o numero de pontos na borda usando o vetor de chain codes 
	 * @return o tamanho da borda
	 */
	public double getSizeBorder() {
		initChainCodes();
		chainCode.calcPerimeter();
		return chainCode.getPerimeter();
	}

}
