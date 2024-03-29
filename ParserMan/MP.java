/*
 * Class MP
 * 
 * Parses a specified input file and prints whether the
 * file was successfully printed or not.
 * 
 * Input file can be given as command line argument.
 * If no arguments are given, a hard coded file name
 * will be used.
 * 
 */
public class MP {
	public static void main(String[] args) {
		String inFile = "Sample.in";

		if (args.length > 1) {
			inFile = args[0];
		}

		Lexer lexer = new Lexer(inFile);

		Parser parser = new Parser(lexer);
		
		boolean value = parser.parse();
		
		if(value)
			System.out.println("File: " + inFile + " parsed successfully.");
		else
			System.out.println("Error in parsing file: " + inFile);
	}
}
