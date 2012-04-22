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

		// System.out.println(value + " > Class Decl"); // class{ case!
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
		value&=Block();
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
		
		boolean value = match(Token.LB);
		
		System.out.println("Matching LB in block: " + value + ", " + token.getLexeme());		
		value&=Statements();
		
		 System.out.println("Matching Statements in block: " + value + ", " + token.getLexeme());		
		value&=match(Token.RB);
		
		System.out.println("Matching RB in Block: " + value + ", " + token.getLexeme());		
		return value;
		
	}
	
	/**
	* Checks for the statements grammar
	* @author Hossam_Amer
	**/
	
	public boolean Statements()
	{
		// System.out.println("Hello I am from statemetns");
		//Handle Epsilon case
		if(token.getTokenType()==Token.RB)
			return true;
			
			return StatementsPrime();

	}
	
	public boolean StatementsPrime()
	{
		if(willGoStatement())
		{
			boolean value = Statement();
			System.out.println("StatementsPrime: Matching Statement: " + value + ", " + token.getLexeme());
			return value;
		}
		
		System.out.println("StatementsPrime: Matching Epsilon: " + true + ", " + token.getLexeme());		
		return true;
		// XXXX possible problem: not calling statementsPrime!
		
		
		// return value;
	}
	
	public boolean willGoStatement()
	{
		return token.getLexeme().equals("int") || token.getLexeme().equals("float")
			|| token.getLexeme().equals("boolean") || token.getLexeme().equals("String") ||
			token.getTokenType() == Token.ID || token.getLexeme().equals("if") ||
			token.getLexeme().equals("while") || token.getTokenType() == Token.LB ||
			token.getTokenType() == Token.RB || token.getLexeme().equals("return");
	}
	
	public boolean Statement()
	{
		
		boolean value = true;
	
		while(true)
		{
		
			if(token.getLexeme().equals("int") || token.getLexeme().equals("float")
				|| token.getLexeme().equals("boolean") || token.getLexeme().equals("String"))
					value &= LocalVarDecl();
				else if (token.getTokenType() == Token.ID)		
					value &= AssignStmt();
				else if(token.getLexeme().equals("if"))	
					value &= IfStmt();
				else if(token.getLexeme().equals("while"))
					value &= WhileStmt();
				else if (token.getLexeme().equals("return"))
					value &= ReturnStmt();
				else if(token.getTokenType() == Token.LB)
				 		value &= Block();
					else if (token.getTokenType() == Token.RB)
						break;
					//  else if (token.getLexeme().equals("else"))
					// {	value &= IfStmtHelper();}	
					else
						return false; // To avoid infinite loop when having a statement
	
		}		
				return value;
	}
	
	// TODO
	public boolean IfStmt()
	{
		
		System.out.println("\nHello From IF Statment! :D");
		boolean value = match("if");
		System.out.println("IfStmt: if: " + value + ", " + token.getLexeme());
		value &= match(Token.LP);
		System.out.println("IfStmt: LP: " + value + ", " + token.getLexeme());		
		value &= Expression();
		System.out.println("IfStmt: Expression: " + value + ", " + token.getLexeme());		
		// token = lexer.nextToken();//XXX Expression not implemented
		value &= match(Token.RP);
		System.out.println("IfStmt: RP: " + value + ", " + token.getLexeme());		

		value &= IfStmtInsideHelper();
		System.out.println("IfStmt: Statement: " + value + ", " + token.getLexeme());				
		// value &= IfStmtHelper();
		// System.out.println("IfStmt: IfStmtHelper: " + value + ", " + token.getLexeme());		
				
		return value;
	}
	
	
	public boolean IfStmtInsideHelper()
	{
		boolean value = true;
	
		while(true)
		{
		
			if(token.getLexeme().equals("int") || token.getLexeme().equals("float")
				|| token.getLexeme().equals("boolean") || token.getLexeme().equals("String"))
					value &= LocalVarDecl();
				else if (token.getTokenType() == Token.ID)		
					value &= AssignStmt();
				else if(token.getLexeme().equals("if"))	
					value &= IfStmt();
				else if(token.getLexeme().equals("while"))
					value &= WhileStmt();
				else if (token.getLexeme().equals("return"))
					value &= ReturnStmt();
				else if(token.getTokenType() == Token.LB)
				 		value &= Block();
					else if (token.getTokenType() == Token.RB)
						break;
					 else if (token.getLexeme().equals("else"))
					 {	value &= IfStmtHelper();}	// To help going to the next step if there is an else after an if
					else
						return false; // To avoid infinite loop when having a statement
	
		}		
				return value;	
	}
	
	
	public boolean IfStmtHelper()
	{
		if(token.getLexeme().equals("else"))
		{
			boolean value = false;
			value = match("else");
			System.out.println("IfStmtHelper: else: " + value + ", " + token.getLexeme());
			
			// Did like that to make sure that there if there is an else after an if > Go!
			 value &= Statement();// XXX not sure for dangling else
				
			
			System.out.println("IfStmtHelper: Statement: " + value + ", " + token.getLexeme());
			
			return value;
		}
		
		System.out.println("IfStmtHelper: Matching Epsilon: " + true + ", " + token.getLexeme());
		// For the epsilon case, no else
		return true;

	}
	
	public boolean WhileStmt()
	{
		System.out.println("Hello from While! ");
		boolean value = match("while");
		System.out.println("WhileStmt: I matched the while: " + value + ", " + token.getLexeme());
		value &= match(Token.LP);
		System.out.println("WhileStmt: I matched the LP: " + value+ ", " + token.getLexeme());
		value &= Expression();
		System.out.println("WhileStmt: I matched the Expression: " + value + ", " + token.getLexeme());
		value &= match(Token.RP);
		System.out.println("WhileStmt: I matched the RP: " + value + ", " + token.getLexeme());
		value &= Statement();
		// token = lexer.nextToken();
		System.out.println("WhileStmt: I matched the Statement: " + value + ", " + token.getLexeme());
		
		return value;
	}
	
	public boolean ReturnStmt()
	{
		boolean value = match("return");
		
		System.out.println("ReturnStmt: I matched the return: " + value + ", " + token.getLexeme());
		value &= Expression();
		System.out.println("ReturnStmt: I matched the Expression: " + value + ", " + token.getLexeme());
		value &= match(Token.SM);
		System.out.println("ReturnStmt: I matched the SM: " + value + ", " + token.getLexeme());
		
		return value;
	}
	
	public boolean LocalVarDecl()
	{
		boolean value = Type();
		
		System.out.println("LocalVarDecl(1): Type: " + value+ ", " + token.getLexeme());		
		value &= match(Token.ID);
		System.out.println("LocalVarDecl(2): ID: " + value+ ", " + token.getLexeme());		
		value &= match(Token.SM);
		System.out.println("LocalVarDecl(3): SM: " + value + ", " + token.getLexeme());		
		

		return value;
	}

	public boolean AssignStmt()
	{
		boolean value = match(Token.ID);
		
		System.out.println("AssignStmt: Matching ID: " + value+ ", " + token.getLexeme());		
		value &= match(Token.AO);
		System.out.println("AssignStmt: Matching AO: " + value + ", " + token.getLexeme());		
		value &= Expression();		
		System.out.println("AssignStmt: Matching Expression: " + value + ", " + token.getLexeme());		
		// token = lexer.nextToken(); // XXX Done since Expression is not done
		value &= match(Token.SM);
		System.out.println("AssignStmt: Matching SM: " + value + ", " + token.getLexeme());		
		
		return value;
	}
	
	// TODO
	public boolean Expression()
	{
		
		System.out.println("\n Hello from Expression :D!" + ", " + token.getLexeme());
		boolean value = ConditionalAndExpr();
		System.out.println("Expression: Matching ConditionalAndExpr: " + value+ ", " + token.getLexeme());
		
		value &= ExpressionPrime();
		// token = lexer.nextToken(); // to advance to the next token after finishing the methods
		
		
		
		System.out.println("Expression: Matching ExpressionPrime: " + value+ ", " + token.getLexeme());
		return value;
	}
	
	public boolean ExpressionPrime()
	{
		if(token.getTokenType() == Token.LO)
		{
			boolean value = match(Token.LO);
			System.out.println("ExpressionPrime: Matching LO: " + value+ ", " + token.getLexeme());
			value &= ConditionalAndExpr();
			System.out.println("ExpressionPrime: Matching ConditionalAndExpr: " + value+ ", " + token.getLexeme());
			value &= ExpressionPrime();
			System.out.println("ExpressionPrime: Matching ExpressionPrime: " + value+ ", " + token.getLexeme());
			
			return value;
		
		}
		System.out.println("ExpressionPrime: Matching Epsilon: " + true + ", " + token.getLexeme());						
		return true;
		
	}
	
	
	public boolean ConditionalAndExpr()
	{
		System.out.println("\n Hello from ConditionalAndExpr :D!" + ", " + token.getLexeme());
		
		boolean value = EqualityExpr();
		System.out.println("ConditionalAndExpr: Matching EqualityExpr: " + value+ ", " + token.getLexeme());
		value &= ConditionalAndExprPrime();
		System.out.println("ConditionalAndExpr: Matching ConditionalAndExprPrime: " + value+ ", " + token.getLexeme());
		
		return value;
	
	}
	
	
	public boolean ConditionalAndExprPrime()
	{
		if(token.getTokenType() == Token.LA)
		{
			boolean value = match(Token.LA);
			System.out.println("ConditionalAndExprPrime: Matching LA: " + value+ ", " + token.getLexeme());
			value &= EqualityExpr();
			System.out.println("ConditionalAndExprPrime: Matching EqualityExpr: " + value+ ", " + token.getLexeme());
			value &= ConditionalAndExprPrime();
			System.out.println("ConditionalAndExprPrime: Matching ConditionalAndExprPrime: " + value+ ", " + token.getLexeme());
			
			return value;
		
		}
		
		System.out.println("ConditionalAndExprPrime: Matching Epsilon: " + true + ", " + token.getLexeme());				
		return true;
	}
	
	
	public boolean EqualityExpr()
	{
		
				System.out.println("\n Hello from EqualityExpr :D!" + ", " + token.getLexeme());
		boolean value = AdditiveExpr();
		System.out.println("EqualityExpr: Matching AdditiveExpr: " + value+ ", " + token.getLexeme());
		
		value &= EqualityExprPrime();
		System.out.println("EqualityExpr: Matching EqualityExprPrime: " + value+ ", " + token.getLexeme());

		return value;
	}
	
	public boolean EqualityExprPrime()
	{
		if(token.getTokenType() == Token.EQ || token.getTokenType() == Token.NE) // if == or NE
		{
			boolean value = (token.getTokenType() == Token.EQ)? match(Token.EQ): match(Token.NE);
			System.out.println("EqualityExprPrime: Matching EQ-NE: " + value + ", " + token.getLexeme());
			value &= AdditiveExpr();
			System.out.println("EqualityExprPrime: Matching AdditiveExpr: " + value + ", " + token.getLexeme());
			value &= EqualityExprPrime();
			System.out.println("EqualityExprPrime: Matching EqualityExprPrime: " + value + ", " + token.getLexeme());
			
			return value;

		}
		
			// token = lexer.nextToken(); // to advance to the next token after finishing the Comparison methods
			System.out.println("EqualityExprPrime: Matching Epsilon: " + true + ", " + token.getLexeme());		
		
		return true;
	}
	
	public boolean AdditiveExpr()
	{
		
		System.out.println("\n Hello from AdditiveExpr :D!" + ", " + token.getLexeme());
		boolean value = MultiplicativeExpr();
		System.out.println("AdditiveExpr: Matching MultiplicativeExpr: " + value);
		
		value &= AdditiveExprPrime();
		System.out.println("AdditiveExpr: Matching AdditiveExprPrime: " + value);		

		return value;
		
	}
	
	public boolean AdditiveExprPrime()
	{
		if(token.getTokenType() == Token.PO || token.getTokenType() == Token.MO) // if + or -
		{
			boolean value = (token.getTokenType() == Token.PO)? match(Token.PO): match(Token.MO);
			System.out.println("AdditiveExprPrime: Matching PO-MO: " + value);		
			value &= MultiplicativeExpr();
			System.out.println("AdditiveExprPrime: Matching MultiplicativeExpr: " + value);			
			value &= AdditiveExprPrime();
			System.out.println("AdditiveExprPrime: Matching AdditiveExprPrime: " + value);			
			
			return value;

		}
		
		System.out.println("AdditiveExprPrime: Matching Epsilon: " + true + ", " + token.getLexeme());
		return true;
	}
	
	public boolean MultiplicativeExpr()
	{
		
		System.out.println("\n Hello from MultiplicativeExpr :D!" + ", " + token.getLexeme());
		
		boolean value = PrimaryExpr();
		System.out.println("MultiplicativeExpr: Matching PrimaryExpr: " + value + ", " + token.getLexeme());
		
		value &= MultiplicativeExprPrime();
		System.out.println("MultiplicativeExpr: Matching MultiplicativeExprPrime: " + value + ", " + token.getLexeme());

		return value;
		
	}	
		
	public boolean MultiplicativeExprPrime()
	{
		if(token.getTokenType() == Token.TO || token.getTokenType() == Token.DO || 
		token.getTokenType() == Token.MD) // if * or / or %
		{
			boolean value = (token.getTokenType() == Token.TO)? match(Token.TO):
			 					(token.getTokenType() == Token.DO)? match(Token.DO): match(Token.MD);

			System.out.println("MultiplicativeExprPrime: Matching TO-DO-MD: " + value + ", " + token.getLexeme());
			value &= PrimaryExpr();
			System.out.println("MultiplicativeExprPrime: Matching PrimaryExpr: " + value + ", " + token.getLexeme());
			value &= MultiplicativeExprPrime();
			System.out.println("MultiplicativeExprPrime: Matching MultiplicativeExprPrime: " + value + ", " + token.getLexeme());
			
			return value;

		}
		
		System.out.println("MultiplicativeExprPrime: Matching Epsilon: " + true + ", " + token.getLexeme());
		return true;
		
	}
		
	
	public boolean PrimaryExpr()
	{
		if(token.getTokenType() == Token.NM || token.getTokenType() == Token.BL 
		|| token.getTokenType() == Token.ST)
		{
			
			System.out.println("PrimaryExpr: Matching NM-BL-ST: " + true + ", " + token.getLexeme());
			token = lexer.nextToken(); // to advance to the next token after finishing the Comparison methods
			return true;
		}
		else if(token.getTokenType() == Token.LP)
		{
			System.out.println("\n Matching (Expression) case! >>>>>");
			boolean value = match(Token.LP);
			System.out.println("PrimaryExpr: Matching LP: " + value + ", " + token.getLexeme());
			value &= Expression();
			System.out.println("PrimaryExpr: Matching Expression: " + value + ", " + token.getLexeme());
			value &= match(Token.RP);
			System.out.println("PrimaryExpr: Matching RP: " + value + ", " + token.getLexeme());
			
			System.out.println("\n DONE WITH Matching (Expression) case! >>>>>");
			
			return value;
		}
		else if(token.getTokenType() == Token.ID)
		{
			// look ahead for the id
			boolean value = match(Token.ID);
			System.out.println("PrimaryExpr: Matching ID: " + value + ", " + token.getLexeme());
			
			// then check if there is RP then call ActualParms otherwise, it is true
			// XXXXXXXX
			if(token.getTokenType() == Token.LP)
			{
				value &= match(Token.LP);
				System.out.println("PrimaryExpr: Matching LP: " + value + ", " + token.getLexeme());
				
				// return value;
				// Look ahead to check for epsilon in case of ActualParams
				// if not LP, then it must be ActualParams, afterwards, match the LP either ways
				if(token.getTokenType() != Token.RP)
				{
					value &= ActualParams();
					System.out.println("PrimaryExpr: Matching ActualParams: " + value + ", " + token.getLexeme());
				}
					value &= match(Token.RP);
					System.out.println("PrimaryExpr: Matching RP: " + value + ", " + token.getLexeme());		
				
			}
		/**/
			
			return value;
		}
		
		return false;
	}	
	
	public boolean ActualParams()
	{ 
		return ProperActualParams();
	}
	
	public boolean ProperActualParams()
	{
		boolean value = Expression();
		System.out.println("ProperActualParams: Matching Expression: " + value + ", " + token.getLexeme());
		
		value &= ProperActualParamsPrime();
		System.out.println("ProperActualParams: Matching Expression: " + value + ", " + token.getLexeme());
		return value;
		
	}
	
	public boolean ProperActualParamsPrime()
	{
		if(token.getTokenType() == Token.FA)
		{
			boolean value = match(Token.FA);// ,
			System.out.println("ProperActualParamsPrime: Matching FA: " + value + ", " + token.getLexeme());

			value &= Expression();
			System.out.println("ProperActualParamsPrime: Matching Expression: " + value + ", " + token.getLexeme());
			value &= ProperActualParamsPrime();
			System.out.println("ProperActualParamsPrime: Matching ProperActualParamsPrime: " + value + ", " + token.getLexeme());

			return value;
			
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
