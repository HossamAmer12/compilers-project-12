/*
 * class Parser
 * 
 * Parses the tokens returned from the lexical analyzer.
 * 
 * Update the parser implementation to parse the Decaf
 * language according to the grammar provided. 
 */
public class Parser {

	private Lexer lexer; // lexical analyzer
	private Token token; // look ahead token
	
	public Parser(Lexer lex) {
		lexer = lex;
	}
	
	public boolean parse() {
		
		token = lexer.nextToken();
		
		boolean value;
		
		while(token.getTokenType() != Token.EOF) {
			value = expr();
			value &= match(Token.SM);
			
			if(!value)
				return false;
		}
		
		return true;
	}
	
	private boolean expr(){
		boolean value = term();

		while (true) {
			switch (token.getTokenType()) {
			case Token.MO:
				value &= match(token.getTokenType());
				value &= term();
				break;

			case Token.PO:
				value &= match(token.getTokenType());
				value &= term();
				break;

			default:
				return value;
			}
		}
	}

	private boolean term(){
		boolean value = match(Token.NM);

		while (true) {
			switch (token.getTokenType()) {
			
			case Token.DO:
				value &= match(token.getTokenType());
				value &= match(Token.NM);
				break;

			case Token.TO:
				value &= match(token.getTokenType());
				value &= match(Token.NM);
				break;

			default:
				return value;
			}
		}
	}

	private boolean match(int t) {
		if (token.getTokenType() == t) {
			token = lexer.nextToken();
			return true;
		} else {
			return false;
		}
	}
}
