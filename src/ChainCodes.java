import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

public class ChainCodes {
	private int borderPoints,height,width,x0,y0;
	ArrayList<Integer> chainCode = new ArrayList<Integer>();
	private double perimeter;
	private boolean[][] pixelMap;
	private BufferedImage image;
	
	/**
	 * Construtor que inicializa o algoritimo de chain codes.
	 * @param image
	 * @param width
	 * @param height
	 * @param x0
	 * @param y0
	 */
	public ChainCodes(BufferedImage image,int width,int height,int x0,int y0){
		this.borderPoints = 0;
		this.perimeter = 0;
		this.width = width;
		this.height = height;
		this.image = image;
		this.x0 = x0;
		this.y0 = y0;
		this.pixelMap = new boolean[height][width];
		
		/* Inicializando com false o mapa de pixels */
		for(int i = 0 ; i < this.height; i++) Arrays.fill(this.pixelMap[i], false); 
	}
	
	/**
	 * Seta o ponto inicial do objeto.
	 * @param auxPoint
	 */
	public void setInitialPoint(int[] auxPoint){
		auxPoint[0] = this.x0;
		auxPoint[1] = this.y0;
	}
		
	/**
	 * @return the borderPoints
	 */
	public int getBorderPoints() {
		return borderPoints;
	}

	/**
	 * Calcula o número de pontos na borda que é igual ao numero de arestas logo tamanho do vetor [0,tamanho]
	 */
	public void calcBorderPoints() {
		this.borderPoints = chainCode.size();
	}

	/**
	 * @return the perimeter
	 */
	public double getPerimeter() {
		return perimeter;
	}
	
	/**
	 * Checa se é um elemento da borda.
	 * @param x
	 * @param y
	 * @return true se é da borda e false caso contrário
	 */
	public boolean checkIfBorder(int x,int y){
		if(pixelMap[y][x]) return false;
		if(Integer.toHexString(image.getRGB(x,y-1)).compareTo("ffffffff") == 0) return true;
		if(Integer.toHexString(image.getRGB(x+1,y)).compareTo("ffffffff") == 0) return true;
		if(Integer.toHexString(image.getRGB(x,y+1)).compareTo("ffffffff") == 0) return true;
		if(Integer.toHexString(image.getRGB(x-1,y)).compareTo("ffffffff") == 0) return true;
		
		return false;
	}
	
	/**
	 * Acha um vizinho que também é uma borda no sentido horário
	 * @param x
	 * @param y
	 * @param auxPoint
	 */
	public void findBorder(int x,int y,int[] auxPoint){
		/* Percorrer sentindo horario */
		
		/* Se vizinho leste é valido */
		if(Integer.toHexString(image.getRGB(x+1,y)).compareTo("ffffffff") != 0 && checkIfBorder(x+1,y)){
			auxPoint[0] = x+1;
			auxPoint[1] = y;
			chainCode.add(0);
			return;
		}

		/* Se vizinho sudeste é valido */
		if(Integer.toHexString(image.getRGB(x+1,y+1)).compareTo("ffffffff") != 0 && checkIfBorder(x+1,y+1)){
			auxPoint[0] = x+1;
			auxPoint[1] = y+1;
			chainCode.add(7);
			return;
		}

		/* Se vizinho sul é valido */
		if(Integer.toHexString(image.getRGB(x,y+1)).compareTo("ffffffff") != 0 && checkIfBorder(x,y+1)){
			auxPoint[0] = x;
			auxPoint[1] = y+1;
			chainCode.add(6);
			return;
		}

		/* Se vizinho sudoeste é valido */
		if(Integer.toHexString(image.getRGB(x-1,y+1)).compareTo("ffffffff") != 0 && checkIfBorder(x-1,y+1)){
			auxPoint[0] = x-1;
			auxPoint[1] = y+1;
			chainCode.add(5);
			return;
		}

		/* Se vizinho oeste é valido */
		if(Integer.toHexString(image.getRGB(x-1,y)).compareTo("ffffffff") != 0 && checkIfBorder(x-1,y)){
			auxPoint[0] = x-1;
			auxPoint[1] = y;
			chainCode.add(4);
			return;
		}
 
		/* Se vizinho noroeste é valido */
		if(Integer.toHexString(image.getRGB(x-1,y-1)).compareTo("ffffffff") != 0 && checkIfBorder(x-1,y-1)){
			auxPoint[0] = x-1;
			auxPoint[1] = y-1;
			chainCode.add(3);
			return;
		}

		/* Se vizinho norte é valido */
		if(Integer.toHexString(image.getRGB(x,y-1)).compareTo("ffffffff") != 0 && checkIfBorder(x,y-1)){
			auxPoint[0] = x;
			auxPoint[1] = y-1;
			chainCode.add(2);
			return;
		}

		/* Se vizinho nordeste é valido */
		if(Integer.toHexString(image.getRGB(x+1,y-1)).compareTo("ffffffff") != 0 && checkIfBorder(x+1,y-1)){
			auxPoint[0] = x+1;
			auxPoint[1] = y-1;
			chainCode.add(1);
			return;
		}
			
		/* Colocando a ultima aresta sentido horario */
		if(auxPoint[0] == x0 && auxPoint[1] - 1 == y0) chainCode.add(2);
		if(auxPoint[0] + 1 == x0 && auxPoint[1] == y0) chainCode.add(0);
		if(auxPoint[0] + 1 == x0 && auxPoint[1]-1 >=0 && auxPoint[1] - 1 == y0) chainCode.add(1);
		
		return;
	}
	
	/**
	 * Calcula o perimetro com base no vetor chain codes.
	 */
	public void calcPerimeter(){
		this.perimeter = 0;
		
		for(int i = 0; i < chainCode.size(); i++){
			/* Calculo da diagonal */
			if(chainCode.get(i) == 1 || chainCode.get(i) == 3 || chainCode.get(i) == 5 || chainCode.get(i) == 7) this.perimeter += Math.sqrt(2);
			else this.perimeter += 1;
		}
	}
	
	/**
	 * Fazendo o contorno do objeto e cria o vetor chain codes.
	 */
	public void doContour() {
		int[] auxPoint = new int[2];
		setInitialPoint(auxPoint);
		
		do {
			pixelMap[auxPoint[1]][auxPoint[0]] = true;
			findBorder(auxPoint[0],auxPoint[1],auxPoint);			
		} while(!pixelMap[auxPoint[1]][auxPoint[0]]); /* Se ponto auxiliar ja foi visitado sai, chegamos no ponto inicial */
	}
	
	/**
	 * Funcão auxiliar
	 * Printa o mapa de pixel
	 
	public void printPixelMap() {
		System.out.println(borderPoints + "!" + this.height + this.width );
		for(int i = 0; i < this.height; i++) {
			for(int j = 0; j < this.width; j++) {
				if (pixelMap[i][j]) 
					System.out.print("º ");
				else 
					System.out.print("  ");
			}
			System.out.println();
		}
	}
	*/
}
