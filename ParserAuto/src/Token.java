/*
 *  Class Token
 *  
 *  Each token is represented by its type and value. 
 */

public class Token {
	
	// list of tokens
	static final int EOF = 0;
	static final int SM = 1;
	static final int PO = 2;
	static final int MO = 3;
	static final int TO = 4;
	static final int DO = 5;
	static final int NM = 6;
	static final int FA	= 7;
	static final int LP	= 8;
	static final int RP	= 9;
	static final int LB	= 10;
	static final int RB	= 11;
	static final int AO	= 12;
	static final int MD	= 13;
	static final int EQ	= 14;
	static final int NE	= 15;
	static final int LO	= 16;
	static final int LA	= 17;
	static final int KW	= 18;
	static final int BL	= 19;
	static final int ST	= 20;
	static final int ID	= 21;
	static final int ERROR	= 22;
	
	private int token; // Type of of token
	private String lexeme; // The lexeme
	
	public int line;
	public int at;

	public Token(int token,String lexeme){
		this.token=token;
		this.lexeme=lexeme;
	}
	public Token(int token, String lexeme,int line,int at) {
		this.token = token;
		this.lexeme = lexeme;
		this.line=line;
		this.at=at;
	}

	// Returns the type of the token
	public int getTokenType() {
		return token;
	}

	// Returns the lexeme of the token
	public String getLexeme() {
		return lexeme;
	}

	public static String getLexemeByType(int type) {
		String lexeme="";
		switch(type){
		case SM:
			lexeme=";";
			break;
		case PO:
			lexeme="+";
			break;
		case MO:
			lexeme="-";
			break;
		case TO:
			lexeme="*";
			break;
		case DO:
			lexeme="/";
			break;
		case FA:
			lexeme=",";
			break;
		case LB:
			lexeme="{";
			break;
		case RB:
			lexeme="}";
			break;
		case LP:
			lexeme="(";
			break;
		case RP:
			lexeme=")";
			break;
		case AO:
			lexeme="=";
			break;
		case MD:
			lexeme="%";
			break;
		case EQ:
			lexeme="==";
			break;
		case NE:
			lexeme="!=";
			break;
		case LO:
			lexeme="||";
			break;
		case LA:
			lexeme="&&";
			break;
		case ID:
			lexeme="ID";
			break;
		case KW:
			lexeme="KW";
			break;
		default:
			System.out.println(type);
			lexeme=null;
			break;
			
		}
		return lexeme;
	}
	
	
	// Returns a string representation of the token
	public String toString() {
		return token + "\t" + lexeme;
	}
	
}