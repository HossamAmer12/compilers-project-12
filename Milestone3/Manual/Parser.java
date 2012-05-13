

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
		
		match(Token.RB);

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
			
				value.add(methodDecl());
			}else break;
		}
		return value;
		
	}

	private MethodDecl methodDecl() throws SyntaxException 
	{
	
		match("static");
		
		Type type = Type();			
		
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
		return new MethodDecl(type, formalParams, block, methodID);
	}
	
	private Type Type() throws SyntaxException {

		if(isEqual("int") || isEqual("float") || isEqual("boolean") || isEqual("String"))
		{
				String type = token.getLexeme();
				match(token.getTokenType());
				return new Type(type);
		}	
		else 
				throw new SyntaxException("Cannot Match Tokens");		
	}
	
	// the structure of the formal parameters
	private FormalParams formalParams() throws SyntaxException {
	
		FormalParams formalParams = new FormalParams();

		//Handle Epsilon case
		while(token.getTokenType()!=Token.RP)
			{
				formalParams.add(formalParam());
				if(token.getLexeme().equals(","))
					match(",");
			}
		
		return formalParams;
		// return true;
	
	}

	
	// should we store the id in the formal parameter?
	private FormalParam formalParam() throws SyntaxException{

			Type type = Type();
			String idLexeme = token.getLexeme();
			match(Token.ID);
			return new FormalParam(idLexeme, type);//XXXXXPossible bug
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
				Statement g=statement();
				stats.add(g);
			}
					
			return stats;	
		}
		
		private Statement statement() throws SyntaxException{
            // boolean value = true;
            Statement value = new Statement(1);

            while(true)
            {
                    if(isType())
                    {
                            value = new Statement(localVarDecl());
                            break;
                    }
                    else if (token.getTokenType() == Token.ID)
                    {
                                    value = new Statement(assignStmt());
                                    break;
                    }
                     else if(isEqual("if"))
                            {
                                    value = new Statement(ifStmt());
                                    break;
                            }
                     else if(isEqual("while"))
                            {
                                    value = new Statement(whileStmt());
                                    break;
                            }
                     else if (isEqual("return"))
                            {
                                            value = new Statement(returnStmt());
                                            break;
                            }
                     else if(isEqual(Token.LB))
                            {
                                    value = new Statement(block());
                                    break;
                            }
                     else if (isEqual(Token.RB))
                            {
                                    break;
                            }
                    else
                            return value;


            }
                            return value;
    }
		
		private LocalVarDecl localVarDecl() throws SyntaxException{
			Type type = Type();
			String idLexeme = token.getLexeme();
			
			match(Token.ID);
			match(Token.SM);

			return new LocalVarDecl(idLexeme, type);
		}
		
		private AssignStmt assignStmt() throws SyntaxException{

			String idLexeme = token.getLexeme();
			match(Token.ID);
			match(Token.AO);
			
			 Expression expr = expression();			
			match(Token.SM);

			return new AssignStmt(idLexeme, expr);
		}
		
		
		public WhileStmt whileStmt() throws SyntaxException{
			
			match("while");
			match(Token.LP);
			Expression expr = expression();
			match(Token.RP);
			Statement stmt = statement();

			return new WhileStmt(expr, stmt);
		}
		
		public ReturnStmt returnStmt() throws SyntaxException{
			
				match("return");
		
				Expression expr = expression();
				match(Token.SM);

				return new ReturnStmt(expr);
		}
		
		public IfStmt ifStmt() throws SyntaxException
		{
			match("if");
			match(Token.LP);	
			Expression expr = expression();	
			match(Token.RP);
			Statement stmt = statement();
			
			IfStmt value = new IfStmt(expr, stmt);
			
			if(token.getLexeme().equals("else"))
			{
				 match("else");
				 Statement elseStmt = statement();
				
				value = new IfStmt(expr, stmt, elseStmt);
				
			}

			return value;
		}
		
		
		
		private Expression expression() throws SyntaxException{
			
			Expression expr = conditionalAndExpr();
			
			while (true)
			{
				if(isEqual(Token.LO))
				{
					match(Token.LO);
					expr = new Expression(conditionalAndExpr(), expr);
					break;

				}
				else
					return expr;
				
			}
			
			return expr;
		}
		
		private ConditionalAndExpr conditionalAndExpr() throws SyntaxException{
			
			ConditionalAndExpr expr = equalityExpr();
			
			while(true)
			{
				if(isEqual(Token.LA))
				{
					match(Token.LA);
					expr = new ConditionalAndExpr(equalityExpr(), expr);
				}
				else
				 	return expr;
				
			}
			
		}
		
		
		private EqualityExpr equalityExpr() throws SyntaxException{
			
			EqualityExpr expr = additiveExpr();
			
			while(true)
			{
				if(isEqual(Token.EQ) || isEqual(Token.NE))
				{
					
					int op = token.getTokenType();
				 	match(op);
					expr = (op == Token.EQ)? 
								new EqualityExpr(additiveExpr(), EqualityExpr.EQ, expr): 
								new EqualityExpr(additiveExpr(), EqualityExpr.NE, expr);
				}
				else
					return expr;
			}
			

		}
		
		private AdditiveExpr additiveExpr() throws SyntaxException{
			
			AdditiveExpr expr = multiplicativeExpr();
			
			while(true)
			{
				if(isEqual(Token.PO) || isEqual(Token.MO))
				{
					
					int op = token.getTokenType();
				 	match(op);
					expr = (op == Token.PO)? 
								new AdditiveExpr(multiplicativeExpr(), AdditiveExpr.PO, expr): 
								new AdditiveExpr(multiplicativeExpr(), AdditiveExpr.MO, expr);
				}
				else
				 	return expr;
				
			}
			
			// return expr;

		}
		
		private MultiplicativeExpr multiplicativeExpr() throws SyntaxException{
			
			MultiplicativeExpr expr = primaryExpr();
			
			while(true)
			{
				if(isEqual(Token.TO) || isEqual(Token.DO) || isEqual(Token.MD))
				{
					
					int op = token.getTokenType();
					match(op);				
					expr =	(op == Token.TO)? new MultiplicativeExpr(primaryExpr(), MultiplicativeExpr.TO, expr): 
							(op == Token.DO)? new MultiplicativeExpr(primaryExpr(), MultiplicativeExpr.DO, expr): 
											  new MultiplicativeExpr(primaryExpr(), MultiplicativeExpr.MD, expr);
				}
				
				else
				 	return expr;
				
			}
			
			// return expr;
		}
		
		public PrimaryExpr primaryExpr() throws SyntaxException
		{
			PrimaryExpr value = new PrimaryExpr();
			if(isEqual(Token.NM))
			{
			   value = new PrimaryExpr(token.getLexeme());
			   match(Token.NM);
			}
			
			else if(isEqual(Token.BL))
			{
				// public PrimaryExpr(String bool, String string, String idLexeme)
				// One constructor but place the right variable in its order
				value = new PrimaryExpr(token.getLexeme(), null, null);
				match(Token.BL);
				
			}
			else if(isEqual(Token.ST))
			{
				value = new PrimaryExpr(null, token.getLexeme(), null);
				match(Token.ST);
			}

			else if(isEqual(Token.LP))
			{
				match(Token.LP);
				value = new PrimaryExpr(expression());
				match(Token.RP);
			}
			
			else if(isEqual(Token.ID))
			{
				String idLexeme = token.getLexeme();
				
				match(Token.ID);

				if(isEqual(Token.LP))
				{
					
					match(Token.LP);
					
					if(!isEqual(Token.RP)){
						value=new PrimaryExpr(actualParams(), idLexeme);
						match(token.getTokenType());

					}else{
						//CALL EXPR
						value=new PrimaryExpr(null, idLexeme);
						match(Token.RP);
					
					}
		

				}else {	
					value=new PrimaryExpr(null, null, idLexeme);
				
				}

			}
				
			return value;	
			
		}
		
		public ActualParams actualParams() throws SyntaxException
		{ 
			return new ActualParams(properActualParams());
		}
		
		private ProperActualParams properActualParams() throws SyntaxException
		{
			
			ProperActualParams params = new ProperActualParams();	
			
			// Should be at least one expression
			params.add(expression());
				
			
			while(token.getTokenType() != Token.RP)
			{
			 	match(Token.FA);
				params.add(expression());
			}
			
			return params;
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
			throw new SyntaxException("Syntax error: Cannot match tokens.");
		}
	}
	
	
	// matches token against lexeme and advances to next token.
	private void match(String lexeme) throws SyntaxException {
		if(token.getLexeme().equals(lexeme)){
			token = lexer.nextToken();
			// return true;
		}else 
			throw new SyntaxException("Syntax error: Cannot match tokens.");
	}
}
