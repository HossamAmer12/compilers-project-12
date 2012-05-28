import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;



public class Parser {

	private Lexer lexer; // lexical analyzer
	private Token token; // look ahead token

	public ArrayList<String> lines;

	ClassDecl classRoot;

	public Parser(Lexer lex) {
		lexer = lex;
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

	public ClassDecl parse() throws SyntaxException {

		token = lexer.nextToken();

		// Check for Empty input file
		if (token.getTokenType() == Token.EOF) {
			return new ClassDecl();
		}

		classRoot = new ClassDecl();

		//while (token.getTokenType() != Token.EOF) {
			classDecl();
		//}

		return classRoot;
	}

	private void classDecl() throws SyntaxException {

		match("class");
		match(Token.ID);
		if (isEqual(Token.LB))
			match(Token.LB);
		else
			Report.syntaxErrorToken(token.line, token.at-token.getLexeme().length(), "{",
					token.getLexeme(), lines.get(token.line - 1));

		classRoot = new ClassDecl(methodDecls());

		if (isEqual(Token.RB)){
			match(Token.RB);
			}
		else
			Report.syntaxErrorGrammer(token.line, token.at-token.getLexeme().length(),"Expected } but found end of file.",lines.get(lines.size()-1));

		if(token.getTokenType()!=Token.EOF ){
			Report.syntaxErrorGrammer(token.line, token.at, "Expected end of file in:", lines.get(token.line-1));
		}
	}

	private MethodDecls methodDecls() throws SyntaxException {
		// Handle Epsilon case
		if (token.getTokenType() == Token.RB)
			return null;

		MethodDecls value = new MethodDecls(token.line,
				lines.get(token.line - 1));
		while (true) {
			if (token.getLexeme().equals("static")) {

				value.add(methodDecl());
			} else
				break;
		}

		return value;

	}

	private MethodDecl methodDecl() throws SyntaxException {

		match("static");

		Type type = Type();

		String methodID = token.getLexeme();
		Token t = token;
		match(Token.ID);
		match(Token.LP);

		FormalParams formalParams = formalParams();

		match(Token.RP);

		Block block = block();

		return new MethodDecl(type, formalParams, block, methodID, t.line,
				t.at, lines.get(t.line - 1));
	}

	private Type Type() throws SyntaxException {

		if (isEqual("int") || isEqual("float") || isEqual("boolean")
				|| isEqual("String")) {
			String type = token.getLexeme();
			match(token.getTokenType());
			return new Type(type);
		} else {
			Report.syntaxErrorGrammer(token.line, token.at-token.getLexeme().length(),
					"Cannot match token '" + token.getLexeme()
							+ "' to a type in:", lines.get(token.line - 1));
			return null;
		}
	}

	private FormalParams formalParams() throws SyntaxException {

		FormalParams formalParams = new FormalParams();

		// Handle Epsilon case
		while (token.getTokenType() != Token.RP && (isType() || isEqual(",") || token.getTokenType() == Token.ID)) {
			formalParams.add(formalParam());
			if (token.getLexeme().equals(","))
				match(",");
			else if(isType())
				Report.syntaxErrorGrammer(token.line, token.at-token.getLexeme().length(), "A comma is expected in:", lines.get(token.line-1));
		}

		return formalParams;

	}

	private FormalParam formalParam() throws SyntaxException {

		Type type = Type();
		String idLexeme = token.getLexeme();
		match(Token.ID);
		return new FormalParam(idLexeme, type);
	}

	private Block block() throws SyntaxException {

		match(Token.LB);

		Statements stats = statements();
		match(Token.RB);

		return new Block(stats);

	}

	private Statements statements() throws SyntaxException {
		Statements stats = new Statements();

		while (token.getTokenType() != Token.RB && isStatement()) {

			Statement g = statement();
			stats.add(g);
		}

		return stats;
	}

	private boolean isStatement() {
		if (isType()) {
			return true;
		} else if (token.getTokenType() == Token.ID) {
			return true;
		} else if (isEqual("if")) {
			return true;
		} else if (isEqual("while")) {
			return true;
		} else if (isEqual("return")) {
			return true;
		} else if (isEqual(Token.LB)) {
			return true;
		}
		return false;

	}

	private Statement statement() throws SyntaxException {

		Statement value = new Statement();

		while (true) {
			if (isType()) {

				value = new Statement(localVarDecl());
				break;
			} else if (token.getTokenType() == Token.ID) {
				value = new Statement(assignStmt());
				break;
			} else if (isEqual("if")) {
				value = new Statement(ifStmt());
				break;
			} else if (isEqual("while")) {
				value = new Statement(whileStmt());
				break;
			} else if (isEqual("return")) {
				value = new Statement(returnStmt());
				break;
			} else if (isEqual(Token.LB)) {
				value = new Statement(block());
				break;
			} else if (isEqual(Token.ERROR)) {

				Report.displayWarning(token.line, token.at-token.getLexeme().length(), "Invalid Input",
						lines.get(token.line - 1));
				token = lexer.nextToken();
				break;
			} else if (isEqual(Token.RB)) {
				Report.syntaxErrorGrammer(token.line, token.at-token.getLexeme().length(),
						"A statement or a block is expected.",
						lines.get(token.line - 1));
				break;
			} else
				return value;

		}
		return value;
	}

	private LocalVarDecl localVarDecl() throws SyntaxException {
	
		Type type = Type();
		String idLexeme = token.getLexeme();
		Token t = token;
		

		while (token.getTokenType() != Token.SM) {
		
			if( token.getTokenType()==Token.ID){
				match(Token.ID);
				break;
				}
				else
				{
					Report.syntaxErrorGrammer(token.line, token.at-token.getLexeme().length(), "An identifier was expected  in:",lines.get(token.line-1));
					break;
				}

		}
	
		match(Token.SM);

		return new LocalVarDecl(idLexeme, type, t.line, t.at,
				lines.get(t.line - 1));
	}

	private AssignStmt assignStmt() throws SyntaxException {

		String idLexeme = token.getLexeme();
		Token t;
		t = token;
		
		match(Token.ID);
		match(Token.AO);

		Expression expr = expression();
		match(Token.SM);

		return new AssignStmt(idLexeme, expr, t, lines.get(t.line - 1));
	}

	private WhileStmt whileStmt() throws SyntaxException {

		match("while");
		match(Token.LP);
		Token t = token;
		Expression expr = expression();
		match(Token.RP);
		Statement stmt = statement();

		return new WhileStmt(expr, stmt, t.line, t.at, lines.get(t.line - 1));
	}

	private ReturnStmt returnStmt() throws SyntaxException {

		match("return");
		Token t = token;
		Expression expr = expression();
		
		match(Token.SM);

		return new ReturnStmt(expr, t.line, t.at, lines.get(t.line - 1));
	}

	private IfStmt ifStmt() throws SyntaxException {
		match("if");
		match(Token.LP);
		Expression expr = expression();
		Token t = token;
		
		match(Token.RP);
		Statement stmt = statement();

		IfStmt value = new IfStmt(expr, stmt, t.line, t.at,
				lines.get(t.line - 1));

		if (token.getLexeme().equals("else")) {
			match("else");
			Statement elseStmt = statement();

			value = new IfStmt(expr, stmt, elseStmt, t.line, t.at,
					lines.get(t.line - 1));

		}

		return value;
	}

	private Expression expression() throws SyntaxException {

		Expression expr = conditionalAndExpr();

		while (true) {
			if (isEqual(Token.LO)) {
				match(Token.LO);
				expr = new Expression(conditionalAndExpr(), expr, token.line,
						token.at-token.getLexeme().length(), lines.get(token.line - 1));
				break;

			}

			else if (isEqual(Token.ERROR)) {
				Report.displayWarning(token.line, token.at-token.getLexeme().length(), "String message",
						"String in");
				token = lexer.nextToken();
				expr = new Expression(conditionalAndExpr(), expr, token.line,
						token.at-token.getLexeme().length(), lines.get(token.line - 1));

				return expr;
			} else
				return expr;

		}

		return expr;
	}

	private ConditionalAndExpr conditionalAndExpr() throws SyntaxException {

		ConditionalAndExpr expr = equalityExpr();

		while (true) {
			if (isEqual(Token.LA)) {
				match(Token.LA);
				expr = new ConditionalAndExpr(equalityExpr(), expr);
			}

			else if (isEqual(Token.ERROR)) {

				Report.displayWarning(token.line, token.at-token.getLexeme().length(),
						"& converted to &&", lines.get(token.line - 1));
				// Report.displayWarning(token.line, token.at-token.getLexeme().length(),
				// "& converted to &&",token.getLexeme(),
				// lines.get(token.line-1));
				token = lexer.nextToken();
				expr = new ConditionalAndExpr(equalityExpr(), expr);

				return expr;
			}

			else
				return expr;

		}

	}

	private EqualityExpr equalityExpr() throws SyntaxException {

		EqualityExpr expr = additiveExpr();

		while (true) {
			if (isEqual(Token.EQ) || isEqual(Token.NE)) {

				int op = token.getTokenType();
				match(op);
				expr = (op == Token.EQ) ? new EqualityExpr(additiveExpr(),
						EqualityExpr.EQ, expr, token.line, token.at-token.getLexeme().length(),
						lines.get(token.line - 1)) : new EqualityExpr(
						additiveExpr(), EqualityExpr.NE, expr, token.line,
						token.at-token.getLexeme().length(), lines.get(token.line - 1));
			} else
				return expr;
		}

	}

	private AdditiveExpr additiveExpr() throws SyntaxException {

		AdditiveExpr expr = multiplicativeExpr();

		while (true) {
			if (isEqual(Token.PO) || isEqual(Token.MO)) {

				int op = token.getTokenType();
				match(op);
				expr = (op == Token.PO) ? new AdditiveExpr(
						multiplicativeExpr(), AdditiveExpr.PO, expr,
						token.line, token.at-token.getLexeme().length(), lines.get(token.line - 1))
						: new AdditiveExpr(multiplicativeExpr(),
								AdditiveExpr.MO, expr, token.line, token.at-token.getLexeme().length(),
								lines.get(token.line - 1));
			} else
				return expr;

		}

	}

	private MultiplicativeExpr multiplicativeExpr() throws SyntaxException {

		MultiplicativeExpr expr = primaryExpr();

		while (true) {
			if (isEqual(Token.TO) || isEqual(Token.DO) || isEqual(Token.MD)) {

				int op = token.getTokenType();
				match(op);
				expr = (op == Token.TO) ? new MultiplicativeExpr(primaryExpr(),
						MultiplicativeExpr.TO, expr, token.line, token.at-token.getLexeme().length(),
						lines.get(token.line - 1))
						: (op == Token.DO) ? new MultiplicativeExpr(
								primaryExpr(), MultiplicativeExpr.DO, expr,
								token.line, token.at-token.getLexeme().length(), lines.get(token.line - 1))
								: new MultiplicativeExpr(primaryExpr(),
										MultiplicativeExpr.MD, expr,
										token.line, token.at-token.getLexeme().length(),
										lines.get(token.line - 1));
			}

			else
				return expr;

		}

	}

	private PrimaryExpr primaryExpr() throws SyntaxException {
		
		PrimaryExpr value = new PrimaryExpr();
		
		if (isEqual(Token.NM)) {
			value = new PrimaryExpr(token.getLexeme());
			match(Token.NM);
		}

		else if (isEqual(Token.BL)) {

			value = new PrimaryExpr(token.getLexeme(), null, null);
			match(Token.BL);

		} else if (isEqual(Token.ST)) {
			value = new PrimaryExpr(null, token.getLexeme(), null);
			match(Token.ST);
			
		} else if (isEqual(Token.ERROR)) {
			Report.displayWarning(token.line, token.at-token.getLexeme().length(),
					"A string should be ended \" literal",
					lines.get(token.line - 1));
			token = lexer.nextToken();

			value = new PrimaryExpr(null, token.getLexeme(), null);
			
		} else if (isEqual(Token.LP)) {
			match(Token.LP);
			value = new PrimaryExpr(expression());
			match(Token.RP);
		}

		else if (isEqual(Token.ID)) {
			String idLexeme = token.getLexeme();

			match(Token.ID);

			if (isEqual(Token.LP)) {

				match(Token.LP);

				if (!isEqual(Token.RP)) {
					value = new PrimaryExpr(actualParams(), idLexeme);
					match(token.getTokenType());

				} else {
					
					value = new PrimaryExpr(null, idLexeme);
					match(Token.RP);

				}

			} else {
			
				value = new PrimaryExpr(null, null, idLexeme);

			}

		}	else{
			Report.syntaxErrorGrammer(token.line, token.at-token.getLexeme().length(), "A Primay Expression was expected in:", lines.get(token.line-1));
			
		}
		
		value.token = token;
		value.line = lines.get(token.line - 1);

		return value;

	}

	private ActualParams actualParams() throws SyntaxException {
		return new ActualParams(properActualParams());
	}

	private ProperActualParams properActualParams() throws SyntaxException {

		ProperActualParams params = new ProperActualParams();

		params.add(expression());

		while (token.getTokenType() != Token.RP) {
			match(Token.FA);
			params.add(expression());
		}

		return params;
	}

	private boolean isType() {
		if (token.getLexeme().equals("int")
				|| token.getLexeme().equals("float")
				|| token.getLexeme().equals("boolean")
				|| token.getLexeme().equals("String"))
			return true;
		else
			return false;
	}

	private boolean isEqual(String lexeme) {
		if (token.getLexeme().equals(lexeme)) {
			return true;
		} else
			return false;
	}

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
		} else if (token.getTokenType() == Token.ERROR) {
			Report.displayWarning(token.line, token.at-token.getLexeme().length(), "Invalid Input "
					+ token.getLexeme(), lines.get(token.line - 1));
			token = lexer.nextToken();

		} else {
			if (Token.getLexemeByType(t) == "ID") {
				Report.syntaxErrorGrammer(token.line, token.at-token.getLexeme().length(),
						"An identifier was expected in:", lines.get(token.line - 1));

			} else if (Token.getLexemeByType(t) == "KW") {
				Report.syntaxErrorGrammer(token.line, token.at-token.getLexeme().length(),
						"A keyword was expected in:", lines.get(token.line - 1));
			} else
				Report.syntaxErrorToken(token.line, token.at-token.getLexeme().length(),
						Token.getLexemeByType(t), token.getLexeme(),
						lines.get(token.line-1));

			token = lexer.nextToken();
		}
	}

	private String getPreviousLine(int lineNo){
	
		String line="";
		for(int i=lineNo-1;i>=0;i--){
			
			if(!lines.get(i).trim().isEmpty()){
				line=lines.get(i);
				break;
			}
		}
		return line;
	}
	
	private void match(String lexeme) throws SyntaxException {
		if (token.getLexeme().equals(lexeme)) {
			token = lexer.nextToken();
		} else
			Report.syntaxErrorToken(token.line, token.at-token.getLexeme().length(), lexeme,
					token.getLexeme(), lines.get(token.line - 1));
	}
}