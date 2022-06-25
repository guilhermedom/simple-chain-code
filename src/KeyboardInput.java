import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * This class helps to deal with keyboard input. It has functions to
 * read strings, ints and doubles.
 * The class does not check for valid inputs only.
 * @author Delamaro
 * @author Guilherme Domingos Faria Silva
 *
 */
public class KeyboardInput {
	static InputStreamReader isr = new InputStreamReader(System.in);
	static BufferedReader br = new BufferedReader(isr);
	
	/**
	 * Reads string from keyboard input until "Enter" key is pressed.
	 * @return string typed by the user.
	 * @throws IOException
	 */
	public static String readString() throws IOException {
		String x;
		x = br.readLine();
		return x;
	}

	/**
	 * Reads string from keyboard input and tries to convert it into an int.
	 * No checks for valid inputs are performed.
	 * @return int value typed by the user. 
	 * @throws IOException
	 */
	public static int readInt() throws IOException {
		String x = readString();
		return Integer.parseInt(x);
	}

	/**
	 * Reads string from keyboard input and tries to convert it into a double.
	 * No checks for valid inputs are performed.
	 * @return double value typed by the user. 
	 * @throws IOException
	 */
	public static double readDouble() throws IOException {
		String x = readString();
		return Double.parseDouble(x);
	}
}
