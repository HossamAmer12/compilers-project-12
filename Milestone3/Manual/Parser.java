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
		
		classRoot = new ClassDecl(methodDecls());

		// System.out.println("Class Root: "+ classRoot.methodDelcs.size());
		// System.out.println("====================================");
		// System.out.println("Class Root: "+ classRoot.methodDelcs);
				// System.out.println("====================================");
		
		match(Token.RB);

		// return new ClassDecl();
	}
	
	private MethodDecls methodDecls() throws SyntaxException
	{
		//Handle Epsilon case
		if(token.getTokenType()==Token.RB)
			return null;
		
		MethodDecls value = new MethodDecls();
		while(true)
		{
			if(token.getLexeme().equals("static"))
			{
				// value &=MethodDecl();
				value.add(methodDecl());
			}else break;
		}
		return value;
		
	}

	private MethodDecl methodDecl() throws SyntaxException 
	{
	
		match("static");
		
		Type();			
		
		String methodID = token.getLexeme();
		match(Token.ID);
		match(Token.LP);
		
		FormalParams formalParams = formalParams();
		// value.formalParams = formalParams();
		
		// FormalParams formalParams = formalParams();
		match(Token.RP);

		// XXXXXXX
		// match(Token.LB);
		// match(Token.RB);
	
		 // value&=Block();
		Block block = block();
		
		
		// return new MethodDecl();
		return new MethodDecl(new Type(), formalParams, block, methodID);
	}
	
	private void Type() throws SyntaxException {

		if(isEqual("int") || isEqual("float") || isEqual("boolean") || isEqual("String"))
			{
				match(token.getTokenType());
			}	
		else 
				throw new SyntaxException("Cannot Match Tokens");		
	}
	
	// the structure of the formal parameters
	private FormalParams formalParams() throws SyntaxException {
	
		FormalParams formalParams = new FormalParams();

		//Handle Epsilon case
		while(token.getTokenType()!=Token.RP)
			formalParams.add(formalParam());
		
		return formalParams;
		// return true;
	
	}

	
	// should we store the id in the formal parameter?
	private FormalParam formalParam() throws SyntaxException{

			Type();
			String idLexeme = token.getLexeme();
			match(Token.ID);
			return new FormalParam(idLexeme);//XXXXXPossible bug
	}
	
	private Block block() throws SyntaxException{


			match(Token.LB);
			Statements stats = statements();
			match(Token.RB);
			
			return new Block(stats);

		}
		
		private Statements statements() throws SyntaxException{
			Statements stats = new Statements();	
			
			while(token.getTokenType() != Token.RB)
			{
				stats.add(statement());
			}
					
			return stats;	
		}
		
		private Statement statement() throws SyntaxException{
			// boolean value = true;
			Statement value = new Statement();
			
			while(true)
			{
				if(isType())
					value = new Statement(localVarDecl());
				else if (token.getTokenType() == Token.ID)
					value = new Statement(assignStmt());
				// else if(isEqual("if"))
				// else if(isEqual("while"))
				// else if (isEqual("return"))	
				// else if(isEqual(Token.LB))
				 else if (isEqual(Token.RB))
					break;
				else
					return value;
					

			}		
					return new Statement();
		}
		
		private LocalVarDecl localVarDecl() throws SyntaxException{

			Type();
			String idLexeme = token.getLexeme();
			match(Token.ID);
			match(Token.SM);

			return new LocalVarDecl(idLexeme);
		}
		
		private AssignStmt assignStmt() throws SyntaxException{

			String idLexeme = token.getLexeme();
			match(Token.ID);
			match(Token.AO);
			// Expression expr = Expression();		
			match(Token.SM);

			return new AssignStmt(idLexeme, new Expression());
		}
		
	
	
		// checks if current token matches to Type.
		private boolean isType(){
			if(token.getLexeme().equals("int") || token.getLexeme().equals("float")
					|| token.getLexeme().equals("boolean") || token.getLexeme().equals("String"))
				return true;
			else return false;
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
