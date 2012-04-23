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
		
		//Check for Empty input file
		if(token.getTokenType()==Token.EOF)
			return false;
		
		while(token.getTokenType() != Token.EOF) {
			value = ClassDecl();
			
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
		value&=Block();
		
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
	
	private boolean Type(){
		boolean value=false;
		
		if(isEqual("int") || isEqual("float") || isEqual("boolean") || isEqual("String"))
		{
			value=match(token.getTokenType());
		}
		return value;
		
	}
	
	private boolean Block(){
		
		boolean value=match(Token.LB);
		value&=Statements();
		value&=match(Token.RB);
		return value;
		
	}
	
	private boolean Statements(){
		//Handle Epsilon case
		if(token.getTokenType()==Token.RB)
			return true;
			
			return StatementsPrime();

	}
	
	private boolean StatementsPrime(){
		
		if(isType() || isEqual(Token.ID) || isEqual("if") || isEqual("while") || isEqual("return")
				|| isEqual(Token.RB) || isEqual(Token.LB))
			return Statement();
		
		return true;
	}
	
	
	private boolean Statement(){
		boolean value = true;
		
		while(true)
		{
		
			if(isType())
					value &= LocalVarDecl();
					
			else if (token.getTokenType() == Token.ID)		
					value &= AssignStmt();
					
			else if(isEqual("if"))	
					value &= IfStmt();
					
			else if(isEqual("while"))
					value &= WhileStmt();
					
			else if (isEqual("return"))
					value &= ReturnStmt();
					
			else if(isEqual(Token.LB))
				 	value &= Block();
					
			else if (isEqual(Token.RB))
					break;	
					
			else
					return false; 
	
		}		
				return value;
	}
	
	
	public boolean WhileStmt(){
		boolean value = match("while");
		value &= match(Token.LP);
		value &= Expression();
		value &= match(Token.RP);
		value &= Statement();

		
		return value;
	}
	
	public boolean IfStmt()
	{
		boolean value = match("if");
		value &= match(Token.LP);	
		value &= Expression();	
		value &= match(Token.RP);
		value &= IfStmtInsideHelper();
			

				
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
					 {	value &= IfStmtHelper();}	
					else
						return false;
	
		}		
				return value;	
	}
	
	
	public boolean IfStmtHelper()
	{
		if(token.getLexeme().equals("else"))
		{
			boolean value = false;
			value = match("else");
			 value &= Statement();
				
			
			return value;
		}

		return true;

	}
	
	public boolean ReturnStmt(){
		boolean value = match("return");
		value &= Expression();
		value &= match(Token.SM);
		
		return value;
	}
	
	private boolean LocalVarDecl(){
		boolean value=true;
		
		value&=Type();
		value&=match(Token.ID);
		value&=match(Token.SM);

		return value;
		
	}
	private boolean AssignStmt(){
		boolean value=true;
		
		value&=match(Token.ID);
		value&=match(Token.AO);
		value&=Expression();		
		value&=match(Token.SM);
		
		return value;
	}

	private boolean Expression(){
		boolean value=ConditionalAndExpr();
		value&=ExpressionPrime();
		
		return value;
	}
	
	private boolean ExpressionPrime(){
		boolean value=true;
		if(isEqual(Token.LO))
		{
			value&=match(Token.LO);
			value&=ConditionalAndExpr();
			value&=ExpressionPrime();
		}
		return value;
	}
	
	private boolean ConditionalAndExpr(){
		boolean value=EqualityExpr();
		value&=ConditionalAndExprPrime();
		
		return value;
		
	}
	
	private boolean ConditionalAndExprPrime(){
		boolean value=true;
		if(isEqual( Token.LA))
		{
			value &= match(Token.LA);
			value &= EqualityExpr();
			value &= ConditionalAndExprPrime();
			return value;
		
		}
					
		return value;
	}
	
	private boolean EqualityExpr(){
		boolean value = AdditiveExpr();
		value &= EqualityExprPrime();

		return value;
	}

	private boolean EqualityExprPrime(){
		boolean value =true;
		if(isEqual(Token.EQ) || isEqual(Token.NE))
		{
			value &= (token.getTokenType() == Token.EQ)? match(Token.EQ): match(Token.NE);
			value &= AdditiveExpr();
			value &= EqualityExprPrime();;
		}
		
		return value;
	}

	private boolean AdditiveExpr(){
		boolean value = MultiplicativeExpr();
		value &= AdditiveExprPrime();	

		return value;
		
	}
	
	private boolean AdditiveExprPrime(){
		boolean value=true;
		if(isEqual(Token.PO) || isEqual(Token.MO))
		{
			value &= (token.getTokenType() == Token.PO)? match(Token.PO): match(Token.MO);	
			value &= MultiplicativeExpr();		
			value &= AdditiveExprPrime();
		}
		
		return value;
	}
	
	private boolean MultiplicativeExpr(){
		boolean value = PrimaryExpr();
		value &= MultiplicativeExprPrime();


		return value;
		
	}
	
	private boolean MultiplicativeExprPrime(){
		boolean value=true;
		if(isEqual(Token.TO) || isEqual(Token.DO) || isEqual(Token.MD))
		{
			value &= (token.getTokenType() == Token.TO)? match(Token.TO):(token.getTokenType() == Token.DO)? match(Token.DO): match(Token.MD);
			value &= PrimaryExpr();
			value &= MultiplicativeExprPrime();
		}
		
		return value;
		
	}
	
	private boolean PrimaryExpr(){
		if(isEqual(Token.NM) || isEqual(Token.BL) || isEqual(Token.ST))
		{
			match(token.getTokenType());
			return true;
		}
		else if(isEqual(Token.ID))
		{
			boolean value=match(Token.ID);
			
			if(isEqual(Token.LP))
			{
				value&=match(Token.LP);
				value&=!isEqual(Token.RP)?ActualParams():true;
				value &= match(Token.RP);
				
			}
			
			return value;
		}
		else if(isEqual(Token.LP))
		{
			boolean value=match(Token.LP);
			value&=Expression();
			value&=match(Token.RP);
			
			return value;
		
		}
		
		return false;

	}
	
	public boolean ActualParams()
	{ 
		return ProperActualParams();
	}
	
	private boolean ProperActualParams()
	{
		boolean value = Expression();	
		value &= ProperActualParamsPrime();

		return value;
		
	}
	
	private boolean ProperActualParamsPrime()
	{
		if(isEqual(Token.FA))
		{
			boolean value = match(Token.FA);
			value &= Expression();
			value &= ProperActualParamsPrime();

			return value;
			
		}
		
		return true;
	}
	
	// checks if current token matches to Type.
	private boolean isType(){
		if(token.getLexeme().equals("int") || token.getLexeme().equals("float")
				|| token.getLexeme().equals("boolean") || token.getLexeme().equals("String"))
			return true;
		else return false;
	}
	// matches token aganist lexeme.
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
