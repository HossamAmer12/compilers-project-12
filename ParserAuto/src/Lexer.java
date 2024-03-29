import java.io.*;
import java_cup.runtime.Symbol;
import java.util.ArrayList;


class Lexer implements java_cup.runtime.Scanner {
	private final int YY_BUFFER_SIZE = 512;
	private final int YY_F = -1;
	private final int YY_NO_STATE = -1;
	private final int YY_NOT_ACCEPT = 0;
	private final int YY_START = 1;
	private final int YY_END = 2;
	private final int YY_NO_ANCHOR = 4;
	private final int YY_BOL = 128;
	private final int YY_EOF = 129;

  private int comment_count = 0;
	String fileName;
	public ArrayList<String> lines;
  	private int state = 0; // 0 normal , 1 CommentM, 3 string
	private String sb = "";
	private String currentLine="";
	public Lexer(java.io.InputStream s , String fileName){
		this();
		yy_reader = new java.io.BufferedReader(new java.io.InputStreamReader(s));
		this.fileName = fileName;
		lines = readLines();
	}
	public ArrayList<String> readLines() {
			ArrayList<String> lines = new ArrayList<String>();
			try {
				FileInputStream fstream = new FileInputStream("Algebra.decaf");
				DataInputStream in = new DataInputStream(fstream);
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				String strLine;
				while ((strLine = br.readLine()) != null) {
					lines.add(strLine);
				}
				in.close();
			} catch (Exception e) {
				System.err.println("Error: " + e.getMessage());
			}
			return lines;
		}
	private java.io.BufferedReader yy_reader;
	private int yy_buffer_index;
	private int yy_buffer_read;
	private int yy_buffer_start;
	private int yy_buffer_end;
	private char yy_buffer[];
	private int yychar;
	private int yyline;
	private boolean yy_at_bol;
	private int yy_lexical_state;

	Lexer (java.io.Reader reader) {
		this ();
		if (null == reader) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(reader);
	}

	Lexer (java.io.InputStream instream) {
		this ();
		if (null == instream) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(new java.io.InputStreamReader(instream));
	}

	private Lexer () {
		yy_buffer = new char[YY_BUFFER_SIZE];
		yy_buffer_read = 0;
		yy_buffer_index = 0;
		yy_buffer_start = 0;
		yy_buffer_end = 0;
		yychar = 0;
		yyline = 0;
		yy_at_bol = true;
		yy_lexical_state = YYINITIAL;
 
	// initialize the warning list will be printed each new line and cleared.
	// warnings= new ArrayList<Warning>();
	}

	private boolean yy_eof_done = false;
	private final int SINGLE_LINE_COMMENTS = 2;
	private final int YYINITIAL = 0;
	private final int COMMENTS = 1;
	private final int yy_state_dtrans[] = {
		0,
		41,
		52
	};
	private void yybegin (int state) {
		yy_lexical_state = state;
	}
	private int yy_advance ()
		throws java.io.IOException {
		int next_read;
		int i;
		int j;

		if (yy_buffer_index < yy_buffer_read) {
			return yy_buffer[yy_buffer_index++];
		}

		if (0 != yy_buffer_start) {
			i = yy_buffer_start;
			j = 0;
			while (i < yy_buffer_read) {
				yy_buffer[j] = yy_buffer[i];
				++i;
				++j;
			}
			yy_buffer_end = yy_buffer_end - yy_buffer_start;
			yy_buffer_start = 0;
			yy_buffer_read = j;
			yy_buffer_index = j;
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}

		while (yy_buffer_index >= yy_buffer_read) {
			if (yy_buffer_index >= yy_buffer.length) {
				yy_buffer = yy_double(yy_buffer);
			}
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}
		return yy_buffer[yy_buffer_index++];
	}
	private void yy_move_end () {
		if (yy_buffer_end > yy_buffer_start &&
		    '\n' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
		if (yy_buffer_end > yy_buffer_start &&
		    '\r' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
	}
	private boolean yy_last_was_cr=false;
	private void yy_mark_start () {
		int i;
		for (i = yy_buffer_start; i < yy_buffer_index; ++i) {
			if ('\n' == yy_buffer[i] && !yy_last_was_cr) {
				++yyline;
			}
			if ('\r' == yy_buffer[i]) {
				++yyline;
				yy_last_was_cr=true;
			} else yy_last_was_cr=false;
		}
		yychar = yychar
			+ yy_buffer_index - yy_buffer_start;
		yy_buffer_start = yy_buffer_index;
	}
	private void yy_mark_end () {
		yy_buffer_end = yy_buffer_index;
	}
	private void yy_to_mark () {
		yy_buffer_index = yy_buffer_end;
		yy_at_bol = (yy_buffer_end > yy_buffer_start) &&
		            ('\r' == yy_buffer[yy_buffer_end-1] ||
		             '\n' == yy_buffer[yy_buffer_end-1] ||
		             2028/*LS*/ == yy_buffer[yy_buffer_end-1] ||
		             2029/*PS*/ == yy_buffer[yy_buffer_end-1]);
	}
	private java.lang.String yytext () {
		return (new java.lang.String(yy_buffer,
			yy_buffer_start,
			yy_buffer_end - yy_buffer_start));
	}
	private int yylength () {
		return yy_buffer_end - yy_buffer_start;
	}
	private char[] yy_double (char buf[]) {
		int i;
		char newbuf[];
		newbuf = new char[2*buf.length];
		for (i = 0; i < buf.length; ++i) {
			newbuf[i] = buf[i];
		}
		return newbuf;
	}
	private final int YY_E_INTERNAL = 0;
	private final int YY_E_MATCH = 1;
	private java.lang.String yy_error_string[] = {
		"Error: Internal error.\n",
		"Error: Unmatched input.\n"
	};
	private void yy_error (int code,boolean fatal) {
		java.lang.System.out.print(yy_error_string[code]);
		java.lang.System.out.flush();
		if (fatal) {
			throw new Error("Fatal Error.\n");
		}
	}
	private int[][] unpackFromString(int size1, int size2, String st) {
		int colonIndex = -1;
		String lengthString;
		int sequenceLength = 0;
		int sequenceInteger = 0;

		int commaIndex;
		String workString;

		int res[][] = new int[size1][size2];
		for (int i= 0; i < size1; i++) {
			for (int j= 0; j < size2; j++) {
				if (sequenceLength != 0) {
					res[i][j] = sequenceInteger;
					sequenceLength--;
					continue;
				}
				commaIndex = st.indexOf(',');
				workString = (commaIndex==-1) ? st :
					st.substring(0, commaIndex);
				st = st.substring(commaIndex+1);
				colonIndex = workString.indexOf(':');
				if (colonIndex == -1) {
					res[i][j]=Integer.parseInt(workString);
					continue;
				}
				lengthString =
					workString.substring(colonIndex+1);
				sequenceLength=Integer.parseInt(lengthString);
				workString=workString.substring(0,colonIndex);
				sequenceInteger=Integer.parseInt(workString);
				res[i][j] = sequenceInteger;
				sequenceLength--;
			}
		}
		return res;
	}
	private int yy_acpt[] = {
		/* 0 */ YY_NOT_ACCEPT,
		/* 1 */ YY_NO_ANCHOR,
		/* 2 */ YY_NO_ANCHOR,
		/* 3 */ YY_NO_ANCHOR,
		/* 4 */ YY_NO_ANCHOR,
		/* 5 */ YY_NO_ANCHOR,
		/* 6 */ YY_NO_ANCHOR,
		/* 7 */ YY_NO_ANCHOR,
		/* 8 */ YY_NO_ANCHOR,
		/* 9 */ YY_NO_ANCHOR,
		/* 10 */ YY_NO_ANCHOR,
		/* 11 */ YY_NO_ANCHOR,
		/* 12 */ YY_NO_ANCHOR,
		/* 13 */ YY_NO_ANCHOR,
		/* 14 */ YY_NO_ANCHOR,
		/* 15 */ YY_NO_ANCHOR,
		/* 16 */ YY_NO_ANCHOR,
		/* 17 */ YY_NO_ANCHOR,
		/* 18 */ YY_NO_ANCHOR,
		/* 19 */ YY_NO_ANCHOR,
		/* 20 */ YY_NO_ANCHOR,
		/* 21 */ YY_NO_ANCHOR,
		/* 22 */ YY_NO_ANCHOR,
		/* 23 */ YY_NO_ANCHOR,
		/* 24 */ YY_NO_ANCHOR,
		/* 25 */ YY_NO_ANCHOR,
		/* 26 */ YY_NO_ANCHOR,
		/* 27 */ YY_NO_ANCHOR,
		/* 28 */ YY_NO_ANCHOR,
		/* 29 */ YY_NO_ANCHOR,
		/* 30 */ YY_NO_ANCHOR,
		/* 31 */ YY_NO_ANCHOR,
		/* 32 */ YY_NO_ANCHOR,
		/* 33 */ YY_NO_ANCHOR,
		/* 34 */ YY_NO_ANCHOR,
		/* 35 */ YY_NO_ANCHOR,
		/* 36 */ YY_NO_ANCHOR,
		/* 37 */ YY_NO_ANCHOR,
		/* 38 */ YY_NO_ANCHOR,
		/* 39 */ YY_NO_ANCHOR,
		/* 40 */ YY_NO_ANCHOR,
		/* 41 */ YY_NO_ANCHOR,
		/* 42 */ YY_NO_ANCHOR,
		/* 43 */ YY_NO_ANCHOR,
		/* 44 */ YY_NO_ANCHOR,
		/* 45 */ YY_NO_ANCHOR,
		/* 46 */ YY_NOT_ACCEPT,
		/* 47 */ YY_NO_ANCHOR,
		/* 48 */ YY_NO_ANCHOR,
		/* 49 */ YY_NO_ANCHOR,
		/* 50 */ YY_NO_ANCHOR,
		/* 51 */ YY_NO_ANCHOR,
		/* 52 */ YY_NOT_ACCEPT,
		/* 53 */ YY_NO_ANCHOR,
		/* 54 */ YY_NO_ANCHOR,
		/* 55 */ YY_NO_ANCHOR,
		/* 56 */ YY_NO_ANCHOR,
		/* 57 */ YY_NO_ANCHOR,
		/* 58 */ YY_NO_ANCHOR,
		/* 59 */ YY_NO_ANCHOR,
		/* 60 */ YY_NO_ANCHOR,
		/* 61 */ YY_NO_ANCHOR,
		/* 62 */ YY_NO_ANCHOR,
		/* 63 */ YY_NO_ANCHOR,
		/* 64 */ YY_NO_ANCHOR,
		/* 65 */ YY_NO_ANCHOR,
		/* 66 */ YY_NO_ANCHOR,
		/* 67 */ YY_NO_ANCHOR,
		/* 68 */ YY_NO_ANCHOR,
		/* 69 */ YY_NO_ANCHOR,
		/* 70 */ YY_NO_ANCHOR,
		/* 71 */ YY_NO_ANCHOR,
		/* 72 */ YY_NO_ANCHOR,
		/* 73 */ YY_NO_ANCHOR,
		/* 74 */ YY_NO_ANCHOR,
		/* 75 */ YY_NO_ANCHOR,
		/* 76 */ YY_NO_ANCHOR,
		/* 77 */ YY_NO_ANCHOR,
		/* 78 */ YY_NO_ANCHOR,
		/* 79 */ YY_NO_ANCHOR,
		/* 80 */ YY_NO_ANCHOR,
		/* 81 */ YY_NO_ANCHOR,
		/* 82 */ YY_NO_ANCHOR,
		/* 83 */ YY_NO_ANCHOR,
		/* 84 */ YY_NO_ANCHOR,
		/* 85 */ YY_NO_ANCHOR,
		/* 86 */ YY_NO_ANCHOR,
		/* 87 */ YY_NO_ANCHOR,
		/* 88 */ YY_NO_ANCHOR,
		/* 89 */ YY_NO_ANCHOR,
		/* 90 */ YY_NO_ANCHOR,
		/* 91 */ YY_NO_ANCHOR,
		/* 92 */ YY_NO_ANCHOR,
		/* 93 */ YY_NO_ANCHOR,
		/* 94 */ YY_NO_ANCHOR,
		/* 95 */ YY_NO_ANCHOR,
		/* 96 */ YY_NO_ANCHOR,
		/* 97 */ YY_NO_ANCHOR
	};
	private int yy_cmap[] = unpackFromString(1,130,
"22:8,27:2,1,22,27,2,22:18,27,35,26,22:2,40,25,22,36,38,4,39,33,31,28,3,24:1" +
"0,22,29,22,32,22:3,23:18,16,23:7,22:4,23,22,7,14,5,23,11,12,18,21,10,23:2,6" +
",23,15,13,23:2,17,8,9,19,23,20,23:3,30,37,34,22:2,0:2")[0];

	private int yy_rmap[] = unpackFromString(1,98,
"0,1:2,2,3,1,4,1,5,6,7,1:4,8,1:3,9,1:5,10,1:5,10:10,11,1:4,12,13,14,12,15,16" +
",17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41" +
",42,10,43,44,45,46,47,48,49,50,51,52,53,54,10,55,56,57,58,59,60")[0];

	private int yy_nxt[][] = unpackFromString(61,41,
"1,2,3,4,5,6,91:2,92,75,47,76,93,91,94,91,95,96,91:2,97,91,7,91,8,9,10,3,11," +
"12,13,14,15,16,17,48,18,19,20,21,22,-1:43,3,-1:24,3,-1:16,23,24,-1:41,91,77" +
",91:15,-1,91,78,-1:40,8,-1:3,46,-1:37,26,-1:16,50:2,10,-1,10:17,-1,10:3,27," +
"10:4,-1,10:9,-1:32,28,-1:45,30,-1:8,91:17,-1,91,78,-1:16,1,51:2,55,54,51:17" +
",7,51:3,7,51:4,7,51:9,-1:24,49,-1:21,91:7,25,91:2,53,91:6,-1,91,78,-1:48,29" +
",-1:9,50:2,-1:39,51:3,-1,51:17,-1,51:3,-1,51:4,-1,51:9,1,44,45:39,-1:5,91:4" +
",31,91:12,-1,91,78,-1:19,43,-1:38,51:3,42,51:17,-1,51:3,-1,51:4,-1,51:9,-1:" +
"5,91:6,32,91:10,-1,91,78,-1:21,91:6,33,91:10,-1,91,78,-1:21,91:3,34,91:13,-" +
"1,91,78,-1:21,91:4,35,91:12,-1,91,78,-1:21,91:6,36,91:10,-1,91,78,-1:21,37," +
"91:16,-1,91,78,-1:21,91:13,38,91:3,-1,91,78,-1:21,91:10,39,91:6,-1,91,78,-1" +
":21,91:10,40,91:6,-1,91,78,-1:21,91:14,56,91:2,-1,91,78,-1:21,91:3,57,91:13" +
",-1,91,78,-1:21,91:3,58,91:13,-1,91,78,-1:21,91:2,59,91:14,-1,91,78,-1:21,9" +
"1:3,56,91:13,-1,91,78,-1:21,91,60,91:15,-1,91,78,-1:21,91:5,61,91:11,-1,91," +
"78,-1:21,91:10,62,91:6,-1,91,78,-1:21,91:12,63,91:4,-1,91,78,-1:21,91:2,64," +
"91:14,-1,91,78,-1:21,91:12,65,91:4,-1,91,78,-1:21,91,66,91:15,-1,91,78,-1:2" +
"1,91:2,67,91:14,-1,91,78,-1:21,91:2,86,91:14,-1,91,78,-1:21,91:8,68,91:8,-1" +
",91,78,-1:21,91,69,91:15,-1,91,78,-1:21,91:8,87,91:8,-1,91,78,-1:21,91:12,8" +
"8,91:4,-1,91,78,-1:21,91:4,89,91:12,-1,91,78,-1:21,91:5,70,91:11,-1,91,78,-" +
"1:21,91:4,71,91:12,-1,91,78,-1:21,91,90,91:15,-1,91,78,-1:21,91:5,72,91:11," +
"-1,91,78,-1:21,91:14,73,91:2,-1,91,78,-1:21,91:6,74,91:10,-1,91,78,-1:21,91" +
":4,79,91:12,-1,91,78,-1:21,91,80,81,91:14,-1,91,78,-1:21,91:8,82,91:8,-1,91" +
",78,-1:21,91:4,83,91:12,-1,91,78,-1:21,91:6,84,91:10,-1,91,78,-1:21,91:16,8" +
"5,-1,91,78,-1:16");

	public java_cup.runtime.Symbol next_token ()
		throws java.io.IOException {
		int yy_lookahead;
		int yy_anchor = YY_NO_ANCHOR;
		int yy_state = yy_state_dtrans[yy_lexical_state];
		int yy_next_state = YY_NO_STATE;
		int yy_last_accept_state = YY_NO_STATE;
		boolean yy_initial = true;
		int yy_this_accept;

		yy_mark_start();
		yy_this_accept = yy_acpt[yy_state];
		if (YY_NOT_ACCEPT != yy_this_accept) {
			yy_last_accept_state = yy_state;
			yy_mark_end();
		}
		while (true) {
			if (yy_initial && yy_at_bol) yy_lookahead = YY_BOL;
			else yy_lookahead = yy_advance();
			yy_next_state = YY_F;
			yy_next_state = yy_nxt[yy_rmap[yy_state]][yy_cmap[yy_lookahead]];
			if (YY_EOF == yy_lookahead && true == yy_initial) {

	if (state== 2)
	{
		Report.displayWarning(yyline + 1, yychar, "Missing \" at the end of the string", lines.get(yyline));
		// warnings.add(new Warning("Missing \" at the end of the string", yyline + 1, yychar));
		currentLine = "";
		return new Symbol(sym.ST, new Token(sym.ST, sb, yyline + 1, yychar));
	}
	   if(state == 1){
		state = 0;
		System.out.println("Hello from state1");
		// this.showWarnings();
		System.out.println();
		return null;
	}
//	System.out.println("Hello from state2 warnings");
//		this.showWarnings();
		System.out.println();
		return null;
			}
			if (YY_F != yy_next_state) {
				yy_state = yy_next_state;
				yy_initial = false;
				yy_this_accept = yy_acpt[yy_state];
				if (YY_NOT_ACCEPT != yy_this_accept) {
					yy_last_accept_state = yy_state;
					yy_mark_end();
				}
			}
			else {
				if (YY_NO_STATE == yy_last_accept_state) {
					throw (new Error("Lexical Error: Unmatched Input."));
				}
				else {
					yy_anchor = yy_acpt[yy_last_accept_state];
					if (0 != (YY_END & yy_anchor)) {
						yy_move_end();
					}
					yy_to_mark();
					switch (yy_last_accept_state) {
					case 1:
						
					case -2:
						break;
					case 2:
						{ 
// System.out.println("Clear Lines");
	currentLine = "";
	yychar = 0;
}
					case -3:
						break;
					case 3:
						{ 
	currentLine+=yytext();
}
					case -4:
						break;
					case 4:
						{ 
currentLine+=yytext();
return new Symbol(sym.DO, new Token(sym.DO,yytext(), yyline + 1, yychar));
//  return new Symbol(sym.DO, yytext());	
}
					case -5:
						break;
					case 5:
						{ 
currentLine+=yytext();
return new Symbol(sym.TO, new Token(sym.TO,yytext(), yyline + 1, yychar));
//  return new Symbol(sym.TO, yytext());	
}
					case -6:
						break;
					case 6:
						{ 
	currentLine+=yytext();
	return new Symbol(sym.ID, new Token(sym.ID,yytext(), yyline + 1, yychar));
	// return new Symbol(sym.ID, yytext());
}
					case -7:
						break;
					case 7:
						{
currentLine += yytext();
Report.displayWarning(yyline + 1, yychar, "Invalid character " + yychar, lines.get(yyline));
// warnings.add(new Warning("Invalid character "+yytext(),yyline + 1,yychar));
//        return new Symbol(sym.ERROR, "Invalid Input: " + yytext(	));
}
					case -8:
						break;
					case 8:
						{ 
	String num = yytext();
	String part1 = "";
	String part2 = "";
	int dotIndex = -1;
	boolean nonZeroFound = false;
	for (int i = 0; i<num.length(); i++)
	{
		if(num.charAt(i) == '.')
		{
			dotIndex = i;
			break;
		}
		if(nonZeroFound)
		{
				part1 += num.charAt(i);
		}
		if(num.charAt(i) != '0' && !nonZeroFound)
		{
			part1 += num.charAt(i);
			nonZeroFound = true;
		}
	}
	if(!nonZeroFound)
		part1 = "0";
	boolean stopRemovingZeros = false;
	int zerosCount = 0;
	String numRev = "";
	if(dotIndex != -1)
	{
		for(int i = num.length() - 1; i>dotIndex; i--)
		{		
			if(num.charAt(i) != '0')	stopRemovingZeros = true;
			if(stopRemovingZeros)	numRev += num.charAt(i);
			if(num.charAt(i) == '0' && !stopRemovingZeros) zerosCount++;
		}
		for(int i = numRev.length() - 1; i >= 0; i--)
		{
			part2 += numRev.charAt(i);
		}
		part2 = (zerosCount==1)? ".0": "." + part2;
	}
	currentLine+=yytext();
	return new Symbol(sym.NM, new Token(sym.KW_class,"" + part1 + part2, yyline + 1, yychar));
}
					case -9:
						break;
					case 9:
						{ 
  	currentLine+=yytext();
  	//Single &: to be converted to && and &&.
	Report.displayWarning(yyline + 1, yychar, "Single & to be converted into &&", lines.get(yyline));
  	return new Symbol(sym.LA, new Token(sym.LA,yytext()+"&", yyline + 1, yychar));
}
					case -10:
						break;
					case 10:
						{ 
	currentLine += yytext();
	Report.displayWarning(yyline + 1, yychar, "Missing \" at the end of the string at char no " + yychar, lines.get(yyline));
	return new Symbol(sym.ST, new Token(sym.ST, yytext() + "\"", yyline + 1, yychar));
//	Report.displayWarning(yyline + 1, yychar, "Missing \" at the end of the string at char no " + yychar, lines.get(lines.size()-1));
//	Report.displayWarning(yyline + 1, yychar, "Invalid character \" " + yytext(), yyline + 1);	
	// return new Symbol(sym.ERROR, "Invalid String Literal");
}
					case -11:
						break;
					case 11:
						{ 
currentLine+=yytext();
 Report.displayWarning(yyline + 1, yychar, "Wrong number " + yychar, lines.get(yyline));
}
					case -12:
						break;
					case 12:
						{ 
	currentLine+=yytext();
	return new Symbol(sym.SM, new Token(sym.SM,yytext(), yyline + 1, yychar));
//   return new Symbol(sym.SM, yytext());	
}
					case -13:
						break;
					case 13:
						{ 
currentLine+=yytext();
return new Symbol(sym.LB, new Token(sym.LB,yytext(), yyline + 1, yychar));
//  return new Symbol(sym.LB, yytext());	
}
					case -14:
						break;
					case 14:
						{ 
currentLine+=yytext();
return new Symbol(sym.MO, new Token(sym.MO,yytext(), yyline + 1, yychar));
//  return new Symbol(sym.MO, yytext());	
}
					case -15:
						break;
					case 15:
						{ 
currentLine+=yytext();
return new Symbol(sym.AO, new Token(sym.AO,yytext(), yyline + 1, yychar));
//  return new Symbol(sym.AO, yytext());	
}
					case -16:
						break;
					case 16:
						{ 
currentLine+=yytext();
return new Symbol(sym.FA, new Token(sym.FA,yytext(), yyline + 1, yychar));
 // return new Symbol(sym.FA, yytext());	
}
					case -17:
						break;
					case 17:
						{ 
currentLine+=yytext();
return new Symbol(sym.RB, new Token(sym.RB,yytext(), yyline + 1, yychar));
//  return new Symbol(sym.RB, yytext());	
}
					case -18:
						break;
					case 18:
						{ 
currentLine+=yytext();
return new Symbol(sym.LP, new Token(sym.LP,yytext(), yyline + 1, yychar));
//  return new Symbol(sym.LP, yytext());	
}
					case -19:
						break;
					case 19:
						{ 
  	currentLine += yytext();
  	// Single | and ||: to be converted to && and ||.
	Report.displayWarning(yyline + 1, yychar, "Single | to be converted into ||", lines.get(yyline));
//	warnings.add(new Warning("| converted to ||",yyline + 1,yychar));
  	return new Symbol(sym.LO, new Token(sym.LO,yytext()+"|", yyline + 1, yychar));
}
					case -20:
						break;
					case 20:
						{ 
currentLine+=yytext();
return new Symbol(sym.RP, new Token(sym.RP,yytext(), yyline + 1, yychar));
//  return new Symbol(sym.RP, yytext());	
}
					case -21:
						break;
					case 21:
						{ 
currentLine+=yytext();
return new Symbol(sym.PO, new Token(sym.PO,yytext(), yyline + 1, yychar));
//  return new Symbol(sym.PO, yytext());
}
					case -22:
						break;
					case 22:
						{ 
currentLine+=yytext();
return new Symbol(sym.MD, new Token(sym.MD,yytext(), yyline + 1, yychar));
//  return new Symbol(sym.MD, yytext());	
}
					case -23:
						break;
					case 23:
						{ yybegin(SINGLE_LINE_COMMENTS);}
					case -24:
						break;
					case 24:
						{ yybegin(COMMENTS); comment_count = comment_count + 1; }
					case -25:
						break;
					case 25:
						{ 
currentLine+=yytext();
return new Symbol(sym.KW_if, new Token(sym.KW_if,yytext(), yyline + 1, yychar));
// return new Symbol(sym.KW_if, yytext()); 
}
					case -26:
						break;
					case 26:
						{ 
currentLine+=yytext();
return new Symbol(sym.LA, new Token(sym.LA,yytext(), yyline + 1, yychar));
//  return new Symbol(sym.LA, yytext());
}
					case -27:
						break;
					case 27:
						{ 
currentLine+=yytext();
return new Symbol(sym.ST, new Token(sym.ST,yytext(), yyline + 1, yychar));
//	return new Symbol(sym.ST, yytext());
}
					case -28:
						break;
					case 28:
						{ 
currentLine+=yytext();
return new Symbol(sym.EQ, new Token(sym.EQ,yytext(), yyline + 1, yychar));
//  return new Symbol(sym.EQ, yytext());	
}
					case -29:
						break;
					case 29:
						{ 
currentLine+=yytext();
return new Symbol(sym.NE, new Token(sym.NE,yytext(), yyline + 1, yychar));
//  return new Symbol(sym.NE, yytext());	
}
					case -30:
						break;
					case 30:
						{ 
currentLine+=yytext();
return new Symbol(sym.LO, new Token(sym.LO,yytext(), yyline + 1, yychar));
//  return new Symbol(sym.LO, yytext());	
}
					case -31:
						break;
					case 31:
						{ 
currentLine+=yytext();
return new Symbol(sym.KW_int, new Token(sym.KW_int,yytext(), yyline + 1, yychar));
//return new Symbol(sym.KW_int, yytext()); 
}
					case -32:
						break;
					case 32:
						{ 
//	System.out.println("BL:  "+  (yyline+1) + ", yytext: " + yytext());
	currentLine+=yytext();
	return new Symbol(sym.BL, new Token(sym.BL,yytext(), yyline + 1, yychar));
//	return new Symbol(sym.BL, yytext());
}
					case -33:
						break;
					case 33:
						{ 
currentLine+=yytext();
return new Symbol(sym.KW_else, new Token(sym.KW_else,yytext(), yyline + 1 , yychar));
// return new Symbol(sym.KW_else, yytext()); 
}
					case -34:
						break;
					case 34:
						{ 
currentLine+=yytext();
return new Symbol(sym.KW_class, new Token(sym.KW_class,yytext(), yyline + 1, yychar));
// return new Symbol(sym.KW_class, yytext(), yyline)); 
}
					case -35:
						break;
					case 35:
						{ 
currentLine+=yytext();
return new Symbol(sym.KW_float, new Token(sym.KW_float,yytext(), yyline + 1, yychar));
// return new Symbol(sym.KW_float, yytext()); 
}
					case -36:
						break;
					case 36:
						{ 
currentLine+=yytext();
return new Symbol(sym.KW_while, new Token(sym.KW_while,yytext(), yyline + 1, yychar));
// return new Symbol(sym.KW_while, yytext()); 
}
					case -37:
						break;
					case 37:
						{ 
currentLine+=yytext();
return new Symbol(sym.KW_static, new Token(sym.KW_static,yytext(), yyline + 1 , yychar));
// return new Symbol(sym.KW_static, yytext());
}
					case -38:
						break;
					case 38:
						{
currentLine+=yytext();
return new Symbol(sym.KW_String, new Token(sym.KW_String,yytext(), yyline + 1, yychar));
// return new Symbol(sym.KW_String, yytext()); 
}
					case -39:
						break;
					case 39:
						{ 
currentLine+=yytext();
return new Symbol(sym.KW_return, new Token(sym.KW_return,yytext(), yyline + 1, yychar));
// return new Symbol(sym.KW_return, yytext()); 
}
					case -40:
						break;
					case 40:
						{ 
currentLine+=yytext();
return new Symbol(sym.KW_boolean, new Token(sym.KW_boolean,yytext(), yyline + 1, yychar));
// return new Symbol(sym.KW_boolean, yytext()); 
}
					case -41:
						break;
					case 41:
						{}
					case -42:
						break;
					case 42:
						{ 
// state = 1;
comment_count = comment_count + 1; 
}
					case -43:
						break;
					case 43:
						{ 
	state = 0;
	comment_count = comment_count - 1; 
	if (comment_count == 0) {
    		yybegin(YYINITIAL);
	}
}
					case -44:
						break;
					case 44:
						{ 
yychar=0;
currentLine="";
yybegin(YYINITIAL); 
}
					case -45:
						break;
					case 45:
						{}
					case -46:
						break;
					case 47:
						{ 
	currentLine+=yytext();
	return new Symbol(sym.ID, new Token(sym.ID,yytext(), yyline + 1, yychar));
	// return new Symbol(sym.ID, yytext());
}
					case -47:
						break;
					case 48:
						{
currentLine += yytext();
Report.displayWarning(yyline + 1, yychar, "Invalid character " + yychar, lines.get(yyline));
// warnings.add(new Warning("Invalid character "+yytext(),yyline + 1,yychar));
//        return new Symbol(sym.ERROR, "Invalid Input: " + yytext(	));
}
					case -48:
						break;
					case 49:
						{ 
	String num = yytext();
	String part1 = "";
	String part2 = "";
	int dotIndex = -1;
	boolean nonZeroFound = false;
	for (int i = 0; i<num.length(); i++)
	{
		if(num.charAt(i) == '.')
		{
			dotIndex = i;
			break;
		}
		if(nonZeroFound)
		{
				part1 += num.charAt(i);
		}
		if(num.charAt(i) != '0' && !nonZeroFound)
		{
			part1 += num.charAt(i);
			nonZeroFound = true;
		}
	}
	if(!nonZeroFound)
		part1 = "0";
	boolean stopRemovingZeros = false;
	int zerosCount = 0;
	String numRev = "";
	if(dotIndex != -1)
	{
		for(int i = num.length() - 1; i>dotIndex; i--)
		{		
			if(num.charAt(i) != '0')	stopRemovingZeros = true;
			if(stopRemovingZeros)	numRev += num.charAt(i);
			if(num.charAt(i) == '0' && !stopRemovingZeros) zerosCount++;
		}
		for(int i = numRev.length() - 1; i >= 0; i--)
		{
			part2 += numRev.charAt(i);
		}
		part2 = (zerosCount==1)? ".0": "." + part2;
	}
	currentLine+=yytext();
	return new Symbol(sym.NM, new Token(sym.KW_class,"" + part1 + part2, yyline + 1, yychar));
}
					case -49:
						break;
					case 50:
						{ 
	currentLine += yytext();
	Report.displayWarning(yyline + 1, yychar, "Missing \" at the end of the string at char no " + yychar, lines.get(yyline));
	return new Symbol(sym.ST, new Token(sym.ST, yytext() + "\"", yyline + 1, yychar));
//	Report.displayWarning(yyline + 1, yychar, "Missing \" at the end of the string at char no " + yychar, lines.get(lines.size()-1));
//	Report.displayWarning(yyline + 1, yychar, "Invalid character \" " + yytext(), yyline + 1);	
	// return new Symbol(sym.ERROR, "Invalid String Literal");
}
					case -50:
						break;
					case 51:
						{}
					case -51:
						break;
					case 53:
						{ 
	currentLine+=yytext();
	return new Symbol(sym.ID, new Token(sym.ID,yytext(), yyline + 1, yychar));
	// return new Symbol(sym.ID, yytext());
}
					case -52:
						break;
					case 54:
						{
currentLine += yytext();
Report.displayWarning(yyline + 1, yychar, "Invalid character " + yychar, lines.get(yyline));
// warnings.add(new Warning("Invalid character "+yytext(),yyline + 1,yychar));
//        return new Symbol(sym.ERROR, "Invalid Input: " + yytext(	));
}
					case -53:
						break;
					case 55:
						{}
					case -54:
						break;
					case 56:
						{ 
	currentLine+=yytext();
	return new Symbol(sym.ID, new Token(sym.ID,yytext(), yyline + 1, yychar));
	// return new Symbol(sym.ID, yytext());
}
					case -55:
						break;
					case 57:
						{ 
	currentLine+=yytext();
	return new Symbol(sym.ID, new Token(sym.ID,yytext(), yyline + 1, yychar));
	// return new Symbol(sym.ID, yytext());
}
					case -56:
						break;
					case 58:
						{ 
	currentLine+=yytext();
	return new Symbol(sym.ID, new Token(sym.ID,yytext(), yyline + 1, yychar));
	// return new Symbol(sym.ID, yytext());
}
					case -57:
						break;
					case 59:
						{ 
	currentLine+=yytext();
	return new Symbol(sym.ID, new Token(sym.ID,yytext(), yyline + 1, yychar));
	// return new Symbol(sym.ID, yytext());
}
					case -58:
						break;
					case 60:
						{ 
	currentLine+=yytext();
	return new Symbol(sym.ID, new Token(sym.ID,yytext(), yyline + 1, yychar));
	// return new Symbol(sym.ID, yytext());
}
					case -59:
						break;
					case 61:
						{ 
	currentLine+=yytext();
	return new Symbol(sym.ID, new Token(sym.ID,yytext(), yyline + 1, yychar));
	// return new Symbol(sym.ID, yytext());
}
					case -60:
						break;
					case 62:
						{ 
	currentLine+=yytext();
	return new Symbol(sym.ID, new Token(sym.ID,yytext(), yyline + 1, yychar));
	// return new Symbol(sym.ID, yytext());
}
					case -61:
						break;
					case 63:
						{ 
	currentLine+=yytext();
	return new Symbol(sym.ID, new Token(sym.ID,yytext(), yyline + 1, yychar));
	// return new Symbol(sym.ID, yytext());
}
					case -62:
						break;
					case 64:
						{ 
	currentLine+=yytext();
	return new Symbol(sym.ID, new Token(sym.ID,yytext(), yyline + 1, yychar));
	// return new Symbol(sym.ID, yytext());
}
					case -63:
						break;
					case 65:
						{ 
	currentLine+=yytext();
	return new Symbol(sym.ID, new Token(sym.ID,yytext(), yyline + 1, yychar));
	// return new Symbol(sym.ID, yytext());
}
					case -64:
						break;
					case 66:
						{ 
	currentLine+=yytext();
	return new Symbol(sym.ID, new Token(sym.ID,yytext(), yyline + 1, yychar));
	// return new Symbol(sym.ID, yytext());
}
					case -65:
						break;
					case 67:
						{ 
	currentLine+=yytext();
	return new Symbol(sym.ID, new Token(sym.ID,yytext(), yyline + 1, yychar));
	// return new Symbol(sym.ID, yytext());
}
					case -66:
						break;
					case 68:
						{ 
	currentLine+=yytext();
	return new Symbol(sym.ID, new Token(sym.ID,yytext(), yyline + 1, yychar));
	// return new Symbol(sym.ID, yytext());
}
					case -67:
						break;
					case 69:
						{ 
	currentLine+=yytext();
	return new Symbol(sym.ID, new Token(sym.ID,yytext(), yyline + 1, yychar));
	// return new Symbol(sym.ID, yytext());
}
					case -68:
						break;
					case 70:
						{ 
	currentLine+=yytext();
	return new Symbol(sym.ID, new Token(sym.ID,yytext(), yyline + 1, yychar));
	// return new Symbol(sym.ID, yytext());
}
					case -69:
						break;
					case 71:
						{ 
	currentLine+=yytext();
	return new Symbol(sym.ID, new Token(sym.ID,yytext(), yyline + 1, yychar));
	// return new Symbol(sym.ID, yytext());
}
					case -70:
						break;
					case 72:
						{ 
	currentLine+=yytext();
	return new Symbol(sym.ID, new Token(sym.ID,yytext(), yyline + 1, yychar));
	// return new Symbol(sym.ID, yytext());
}
					case -71:
						break;
					case 73:
						{ 
	currentLine+=yytext();
	return new Symbol(sym.ID, new Token(sym.ID,yytext(), yyline + 1, yychar));
	// return new Symbol(sym.ID, yytext());
}
					case -72:
						break;
					case 74:
						{ 
	currentLine+=yytext();
	return new Symbol(sym.ID, new Token(sym.ID,yytext(), yyline + 1, yychar));
	// return new Symbol(sym.ID, yytext());
}
					case -73:
						break;
					case 75:
						{ 
	currentLine+=yytext();
	return new Symbol(sym.ID, new Token(sym.ID,yytext(), yyline + 1, yychar));
	// return new Symbol(sym.ID, yytext());
}
					case -74:
						break;
					case 76:
						{ 
	currentLine+=yytext();
	return new Symbol(sym.ID, new Token(sym.ID,yytext(), yyline + 1, yychar));
	// return new Symbol(sym.ID, yytext());
}
					case -75:
						break;
					case 77:
						{ 
	currentLine+=yytext();
	return new Symbol(sym.ID, new Token(sym.ID,yytext(), yyline + 1, yychar));
	// return new Symbol(sym.ID, yytext());
}
					case -76:
						break;
					case 78:
						{ 
	currentLine+=yytext();
	return new Symbol(sym.ID, new Token(sym.ID,yytext(), yyline + 1, yychar));
	// return new Symbol(sym.ID, yytext());
}
					case -77:
						break;
					case 79:
						{ 
	currentLine+=yytext();
	return new Symbol(sym.ID, new Token(sym.ID,yytext(), yyline + 1, yychar));
	// return new Symbol(sym.ID, yytext());
}
					case -78:
						break;
					case 80:
						{ 
	currentLine+=yytext();
	return new Symbol(sym.ID, new Token(sym.ID,yytext(), yyline + 1, yychar));
	// return new Symbol(sym.ID, yytext());
}
					case -79:
						break;
					case 81:
						{ 
	currentLine+=yytext();
	return new Symbol(sym.ID, new Token(sym.ID,yytext(), yyline + 1, yychar));
	// return new Symbol(sym.ID, yytext());
}
					case -80:
						break;
					case 82:
						{ 
	currentLine+=yytext();
	return new Symbol(sym.ID, new Token(sym.ID,yytext(), yyline + 1, yychar));
	// return new Symbol(sym.ID, yytext());
}
					case -81:
						break;
					case 83:
						{ 
	currentLine+=yytext();
	return new Symbol(sym.ID, new Token(sym.ID,yytext(), yyline + 1, yychar));
	// return new Symbol(sym.ID, yytext());
}
					case -82:
						break;
					case 84:
						{ 
	currentLine+=yytext();
	return new Symbol(sym.ID, new Token(sym.ID,yytext(), yyline + 1, yychar));
	// return new Symbol(sym.ID, yytext());
}
					case -83:
						break;
					case 85:
						{ 
	currentLine+=yytext();
	return new Symbol(sym.ID, new Token(sym.ID,yytext(), yyline + 1, yychar));
	// return new Symbol(sym.ID, yytext());
}
					case -84:
						break;
					case 86:
						{ 
	currentLine+=yytext();
	return new Symbol(sym.ID, new Token(sym.ID,yytext(), yyline + 1, yychar));
	// return new Symbol(sym.ID, yytext());
}
					case -85:
						break;
					case 87:
						{ 
	currentLine+=yytext();
	return new Symbol(sym.ID, new Token(sym.ID,yytext(), yyline + 1, yychar));
	// return new Symbol(sym.ID, yytext());
}
					case -86:
						break;
					case 88:
						{ 
	currentLine+=yytext();
	return new Symbol(sym.ID, new Token(sym.ID,yytext(), yyline + 1, yychar));
	// return new Symbol(sym.ID, yytext());
}
					case -87:
						break;
					case 89:
						{ 
	currentLine+=yytext();
	return new Symbol(sym.ID, new Token(sym.ID,yytext(), yyline + 1, yychar));
	// return new Symbol(sym.ID, yytext());
}
					case -88:
						break;
					case 90:
						{ 
	currentLine+=yytext();
	return new Symbol(sym.ID, new Token(sym.ID,yytext(), yyline + 1, yychar));
	// return new Symbol(sym.ID, yytext());
}
					case -89:
						break;
					case 91:
						{ 
	currentLine+=yytext();
	return new Symbol(sym.ID, new Token(sym.ID,yytext(), yyline + 1, yychar));
	// return new Symbol(sym.ID, yytext());
}
					case -90:
						break;
					case 92:
						{ 
	currentLine+=yytext();
	return new Symbol(sym.ID, new Token(sym.ID,yytext(), yyline + 1, yychar));
	// return new Symbol(sym.ID, yytext());
}
					case -91:
						break;
					case 93:
						{ 
	currentLine+=yytext();
	return new Symbol(sym.ID, new Token(sym.ID,yytext(), yyline + 1, yychar));
	// return new Symbol(sym.ID, yytext());
}
					case -92:
						break;
					case 94:
						{ 
	currentLine+=yytext();
	return new Symbol(sym.ID, new Token(sym.ID,yytext(), yyline + 1, yychar));
	// return new Symbol(sym.ID, yytext());
}
					case -93:
						break;
					case 95:
						{ 
	currentLine+=yytext();
	return new Symbol(sym.ID, new Token(sym.ID,yytext(), yyline + 1, yychar));
	// return new Symbol(sym.ID, yytext());
}
					case -94:
						break;
					case 96:
						{ 
	currentLine+=yytext();
	return new Symbol(sym.ID, new Token(sym.ID,yytext(), yyline + 1, yychar));
	// return new Symbol(sym.ID, yytext());
}
					case -95:
						break;
					case 97:
						{ 
	currentLine+=yytext();
	return new Symbol(sym.ID, new Token(sym.ID,yytext(), yyline + 1, yychar));
	// return new Symbol(sym.ID, yytext());
}
					case -96:
						break;
					default:
						yy_error(YY_E_INTERNAL,false);
					case -1:
					}
					yy_initial = true;
					yy_state = yy_state_dtrans[yy_lexical_state];
					yy_next_state = YY_NO_STATE;
					yy_last_accept_state = YY_NO_STATE;
					yy_mark_start();
					yy_this_accept = yy_acpt[yy_state];
					if (YY_NOT_ACCEPT != yy_this_accept) {
						yy_last_accept_state = yy_state;
						yy_mark_end();
					}
				}
			}
		}
	}
}
