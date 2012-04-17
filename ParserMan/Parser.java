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
			value = ClassDecl();
			//value &= match(Token.SM);
			
			if(!value)
				return false;
		}
		
		return true;
	}
	private boolean ClassDecl()
	{
		boolean value=match("class");
		value&=match(Token.ID);
		value&=match(Token.LB);
		value&=MethodDecls();
		value&=match(Token.RB);

		return value;
	}
	
	private boolean MethodDecls()
	{
		//Handle Epsilon case
		if(token.getTokenType()==Token.RB)
			return true;
		
		boolean value=true;
		while(true)
		{
			if(token.getLexeme().equals("static"))
			{
				value&=MethodDecl();
			}else break;
		}
		return value;
	}
	private boolean MethodDecl()
	{
		boolean value=match("static");
		value&=Type();
		value&=match(Token.ID);
		value&=match(Token.LP);
		value&=FormalParams();
		value&=match(Token.RP);
		//TODO match block value&=Block();
		return value;
	}
	private boolean FormalParams(){
	
		//Handle Epsilon case
		if(token.getTokenType()==Token.RP)
			return true;
		
		return ProperFormalParams();
	
	}
	private boolean ProperFormalParams(){
		
		boolean value=FormalParam();
		value&=ProperFormalParamsPRIME();
		return value;
	}
	private boolean ProperFormalParamsPRIME(){
		
		//Handle Epsilon case
		if(token.getTokenType()==Token.RP)
			return true;
		
		boolean value=true;
		
		while(true)
		{
			if(token.getLexeme().equals(","))
			{
				value&=match(",");
				value&=FormalParam();
			}else break;
				
		}
		return value;
		}
		
	
	private boolean FormalParam(){
		
		boolean value=Type();
		value&=match(Token.ID);

		return value;
	}
	private boolean Type()
	{
		boolean value=false;
		
		if(token.getLexeme().equals("int") || token.getLexeme().equals("float")
			|| token.getLexeme().equals("boolean") || token.getLexeme().equals("String"))
		{
			value=match(token.getTokenType());
		}
		return value;
		
	}
	private boolean Block(){
		
		boolean value=match(Token.LB);
		//TODO value&=Statements();
		value&=match(Token.RB);
		return value;
		
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

	// matches token against lexeme and advances to next token.
	private boolean match(String lexeme){
		if(token.getLexeme().equals(lexeme)){
			token=lexer.nextToken();
			return true;
		}else 
			return false;
	}
	// matches token against type and advances to next token.
	private boolean match(int t) {
		if (token.getTokenType() == t) {
			token = lexer.nextToken();
			return true;
		} else {
			return false;
		}
	}
}
