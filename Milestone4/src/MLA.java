import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/*
 * Class MLA
 * 
 * Reads a specified input file, tokenize it, and writes
 * the output to a specified output file;
 * 
 * Input and output files can be given as command line
 * arguments. If no arguments are given, hard coded file
 * names will be used.
 * 
 * Output file will be automatically overwritten if exists.
 * 
 */
public class MLA {
	public static void main(String[] args) {
		String inFile = "Algebra.decaf";
		String outFile = "Sample.out";

		if (args.length > 1) {
			inFile = args[0];
			outFile = args[1];
		}

		Lexer lexer = new Lexer(inFile);

		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));

			Token t;
			t=lexer.nextToken();
			while (t.getTokenType() != Token.EOF) {
				writer.write(t.toString());
				writer.newLine();
				t=lexer.nextToken();
				
			}
			
			writer.close(); 
			
			System.out.println("Done tokenizing file: " + inFile);
			System.out.println("Output written in file: " + outFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
