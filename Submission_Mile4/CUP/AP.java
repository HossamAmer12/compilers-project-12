import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.ArrayList;

import java_cup.runtime.Symbol;

public class AP {

	public static void main(String[] args) {
		
		String inFile = "Algebra.decaf";
		String outFile = "Sample.out";

		if (args.length > 1) {
			inFile = args[0];
		}
		
		try {
			FileInputStream fis = new FileInputStream(inFile);
			BufferedInputStream bis = new BufferedInputStream(fis);
			DataInputStream dis = new DataInputStream(bis);
			
			////////////////////////////////////////////////////////
//			Lexer lexer = new Lexer(dis, inFile);
			
		
			////////////////////////////////////////////////////////
			
			parser parser = new parser(new Lexer(dis, inFile),inFile);
			Symbol res = parser.parse();
			ClassDecl cd = (ClassDecl)res.value;

			System.out.println(cd);
			
			cd.check();

			System.out.println("Semantic Analysis Completed with No Errors.");
	
			fis.close();
			bis.close();
			dis.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
