import java.util.ArrayList;

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
	
	ClassDecl classRoot;
	
	public Parser(Lexer lex) {
		lexer = lex;
	}
	
	public ClassDecl parse() throws SyntaxException {
		
		
		token = lexer.nextToken();

		// System.out.println("Token: " + token.getLexeme());

		//Check for Empty input file
		if(token.getTokenType()==Token.EOF)
		{
					return new ClassDecl();
		}

			classRoot = new ClassDecl();
					
			while(token.getTokenType() != Token.EOF) 
			{
				 classDecl();
			}
			
			return classRoot;

		
	}
	
	private void classDecl() throws SyntaxException
	{
		match("class");
		match(Token.ID);
		match(Token.LB);
		
		classRoot.mDecls = methodDecls();

		match(Token.RB);

		// return new ClassDecl();
	}
	
	private ArrayList<MethodDecl> methodDecls() throws SyntaxException
	{
	
		//Handle Epsilon case
		if(token.getTokenType()==Token.RB)
			return new ArrayList<MethodDecl>();
		
			ArrayList <MethodDecl> value = new ArrayList<MethodDecl>();

		while(true)
		{
			if(token.getLexeme().equals("static"))
			{
				value.add(methodDecl());
				// value&=MethodDecl();
			}else break;
		}
		return value;
	}
	
	private MethodDecl methodDecl() throws SyntaxException 
	{
		
		
		match("static");
		Type();
		
		match(Token.ID);
		match(Token.LP);
		
		
		// FormalParams formalParams = formalParams();
		match(Token.RP);

		// XXXXXXX
		match(Token.LB);
		match(Token.RB);
	
		// value&=Block();
		
		return new MethodDecl();
	}
	
	private void Type() throws SyntaxException {

		if(isEqual("int") || isEqual("float") || isEqual("boolean") || isEqual("String"))
			{
				match(token.getTokenType());
			}	
		else 
				throw new SyntaxException("Cannot Match Tokens");		
	}
	
	// the structure of the formal parameters??S
	private FormalParams formalParams() throws SyntaxException {
	
		//Handle Epsilon case
		if(token.getTokenType()==Token.RP)
			return new FormalParams();
		
		return new FormalParams(properFormalParams());
		// return true;
	
	}
	
	private ProperFormalParams properFormalParams() throws SyntaxException{
		
		formalParam();
		// boolean value &= ProperFormalParamsPRIME();
		// return value;
		
		return new ProperFormalParams();
	}
	
	private boolean ProperFormalParamsPRIME() throws SyntaxException{

			//Handle Epsilon case
			if(token.getTokenType()==Token.RP)
				return true;

			boolean value=true;

			while(true)
			{
				if(token.getLexeme().equals(","))
				{
					// value&=match(",");
					match(",");
					// value&=formalParam();
					formalParam();					
				}else break;

			}
			return value;
			}
	
	
	// should we store the id in the formal parameter?
	private void formalParam() throws SyntaxException{

			Type();
			match(Token.ID);

		}
	
	
	
	
	// matches token against lexeme.
	private boolean isEqual(String lexeme){
		if(token.getLexeme().equals(lexeme)){
			return true;
		}else 
			return false;
	}
	// matches token against type.
	private boolean isEqual(int t) {
		if (token.getTokenType() == t) {
			return true;
		} else {
			return false;
		}
	}
	
	private void match(int t) throws SyntaxException {
		if (token.getTokenType() == t) {
			token = lexer.nextToken();
		} else { 
			throw new SyntaxException("Cannot Match Tokens");
		}
	}
	
	
	// matches token against lexeme and advances to next token.
	private void match(String lexeme) throws SyntaxException {
		if(token.getLexeme().equals(lexeme)){
			token = lexer.nextToken();
			// return true;
		}else 
			throw new SyntaxException("Cannot Match Tokens");
	}
}
