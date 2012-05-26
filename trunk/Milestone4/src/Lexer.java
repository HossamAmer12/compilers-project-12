import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/*
 * Class Lexer
 * 
 * Update the method nextToken() such to the provided
 * specifications of the Decaf Programming Language.
 * 
 * You are not allowed to use any built it in tokenizer
 * in Java. You are only allowed to scan the input file
 * one character at a time.
 */

public class Lexer {

	private BufferedReader reader; // Reader
	private char curr; // The current character being scanned

	private final static int STATE_CONTROLLER=1;
	private final static int STATE_NUMERIC_START=2;
	private final static int STATE_NUMERIC_BODY=3;
	private final static int STATE_WORD_START=4;
	private final static int STATE_WORD_BODY=5;
	private final static int STATE_DECIMAL_HANDLER=6;
	private final static int STATE_ID_START=7;
	private final static int STATE_ID_BODY=8;
	private final static int STATE_EQUALITY=9;
	private final static int STATE_NOT_EQUALITY=10;
	private final static int STATE_LOGICAL_AND=11;
	private final static int STATE_LOGICAL_OR=12;
	private final static int STATE_STRING_START=13;
	private final static int STATE_STRING_BODY=14;
	private final static int STATE_COMMENTS_HANDLER=15;
	private final static int STATE_SINGLE_COMMENT=16;
	private final static int STATE_MULTIPLE_COMMENT_OPEN=17;
	private final static int STATE_MULTIPLE_COMMENT_CLOSE=18;

	
	private static final char EOF = (char) (-1);
	
	private static int line=1;
	private static int at=1;
	

	// End of file character

	public Lexer(String file) {
		try {
			reader = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// Read the first character
	
	}

	private char read() {
		try {
			return (char) (reader.read());
		} catch (IOException e) {
			e.printStackTrace();
			return EOF;
		}
	}

	// Checks if a character is a digit
	private boolean isNumeric(char c) {

		if (c >= '0' && c <= '9')
			return true;

		return false;
	}
	
	//Removes all trailing zeros
	private String removeTrailingZeors(String floatBuffer)
	{

		String result="";
		if(!floatBuffer.contains("."))
			return floatBuffer;
		boolean stop=false;
		for(int i=floatBuffer.length()-1;i>=0;i--)
		{
	
			if(!stop)
			{
			if(floatBuffer.charAt(i)=='0')
			{
				if(i>0)
				{
					if(floatBuffer.charAt(i-1)=='.')
					{
						stop=true;
						result=floatBuffer.charAt(i)+result;
					}
						
				}
				continue;
			}
			else
			{
				stop=true;
				result=floatBuffer.charAt(i)+result;
			}
			}
			else
				result=floatBuffer.charAt(i)+result;


				
		}
			
		
		
		return result;
	}
	
	private Token getErrorToken(char curr)
	{
		return new Token(Token.ERROR,"Invalid input: "+curr,line,at);
	}
	
	// Checks if a character is a letter
	private boolean isLetter(char curr)
	{
		boolean letter=false;
		
		if(curr=='a' || curr=='A')
			letter=true;
		if(curr=='b' || curr=='B')
			letter=true;
		if(curr=='c' || curr=='c')
			letter=true;
		if(curr=='d' || curr=='D')
			letter=true;
		if(curr=='e' || curr=='E')
			letter=true;
		if(curr=='f' || curr=='F')
			letter=true;
		if(curr=='g' || curr=='G')
			letter=true;
		if(curr=='h' || curr=='H')
			letter=true;
		if(curr=='i' || curr=='I')
			letter=true;
		if(curr=='j' || curr=='J')
			letter=true;
		if(curr=='k' || curr=='K')
			letter=true;
		if(curr=='l' || curr=='L')
			letter=true;
		if(curr=='m' || curr=='M')
			letter=true;
		if(curr=='n' || curr=='N')
			letter=true;
		if(curr=='o' || curr=='O')
			letter=true;
		if(curr=='p' || curr=='P')
			letter=true;
		if(curr=='q' || curr=='Q')
			letter=true;
		if(curr=='r' || curr=='R')
			letter=true;
		if(curr=='s' || curr=='S')
			letter=true;
		if(curr=='t' || curr=='T')
			letter=true;
		if(curr=='u' || curr=='U')
			letter=true;
		if(curr=='v' || curr=='V')
			letter=true;
		if(curr=='w' || curr=='W')
			letter=true;
		if(curr=='x' || curr=='X')
			letter=true;
		if(curr=='y' || curr=='Y')
			letter=true;
		if(curr=='z' || curr=='Z')
			letter=true;
		
			
		return letter;
		
		
	}


	// Checks if a character is a quote
	private boolean isQuote(char c)
	{
		return c == '"';
	}
	
	
	// Checks if a string is a reserved keyword
	private boolean isKeyword(String candidate)
	{
		if(candidate.equals("class") || candidate.equals("else") || candidate.equals("if") ||
			candidate.equals("int")  || candidate.equals("float") || candidate.equals("boolean") ||
			candidate.equals("String") || candidate.equals("return") || candidate.equals("static") ||
			candidate.equals("while"))
			return true;
		else
			return false;
		
	}
	
	//Returns a token corresponding 
	private Token getTokenByOperator(char curr)
	{
		Token token=null;

		switch (curr) 
		{


		case ';':
			token= new Token(Token.SM,""+curr,line,at);
			break;
		case '=':
			token= new Token(Token.AO,""+curr,line,at);
			break;
		case '{':
			token= new Token(Token.LB, ""+curr,line,at);
			break;
		case '}':
			token= new Token(Token.RB, ""+curr,line,at);
			break;
		case ',':
			token= new Token(Token.FA, ""+curr,line,at);
			break;
		case '+':
		
			token= new Token(Token.PO, ""+curr,line,at);
			break;
		case '-':
		
			token= new Token(Token.MO, ""+curr,line,at);
			break;
		case '*':
		
			token= new Token(Token.TO, ""+curr,line,at);
			break;
		case '/':
		
			token= new Token(Token.DO, ""+curr,line,at);
			break;
		case '(':
			
			token= new Token(Token.LP, ""+curr,line,at);
			break;
		case ')':
			
			token= new Token(Token.RP, ""+curr,line,at);
			break;
		case '%':
			
			token= new Token(Token.MD, ""+curr,line,at);
			break;

		}
		return token;
	}
	
	// Checks if a char is operator
	private boolean isOperator(char curr)
	{
		if(curr=='{' || curr== '}' || curr==';' || curr==',' || curr=='('
		|| curr=='%' || curr=='&' || curr== '|' || curr=='!' || curr=='/'
		|| curr==')' || curr=='=' || curr=='+' || curr=='-' || curr=='*')
			return true;
		else 
			return false;
	}
	
	private Token handleEndOfFile(String wordBuffer,String floatBuffer)
	{
		
		//WordBuffer not empty
		if(!wordBuffer.isEmpty())
		{
			if(isKeyword(wordBuffer))
				return new Token(Token.KW,wordBuffer,line,at);
			else if(isBoolean(wordBuffer))
				return new Token (Token.BL, wordBuffer,line,at);
			//Case String
			else if(wordBuffer.charAt(0)=='\"')
			{
				if(wordBuffer.charAt(wordBuffer.length()-1)=='\"')
					return new Token(Token.ST,wordBuffer,line,at);
				else
					return new Token(Token.ERROR,wordBuffer,line,at);
			}
			else
				return new Token(Token.ID,wordBuffer,line,at);
			
		}
		else if(!floatBuffer.isEmpty())
			return new Token(Token.NM,removeTrailingZeors(floatBuffer),line,at);
		

			
		return new Token(Token.EOF,"EOF",line,at);
		
	}
	
	// Checks if a string is a reserved keyword
	private boolean isBoolean(String candidate)
	{
		if(candidate.equals("true") || candidate.equals("false"))
			return true;
		else
			return false;
		
	}
	
	private void readCharacter(){
		curr=read();
		at++;
	}
	public Token nextToken() {

		int state = 1; // Initial state
		int numBuffer = 0; // A buffer for number literals
		String floatBuffer="";
		String wordBuffer="";
		
		while (true) {
	
			
			if (curr == EOF) {
				line++;
				
	
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				//return null;
				//TODO
				 return handleEndOfFile(wordBuffer, floatBuffer);
			}

			switch (state) 
			{
			
			//////////////////////////////////
			case STATE_CONTROLLER:
			
					if(curr=='\n'){
						at=1;
						line++;
						readCharacter();
						continue;
					}
					//Case: WHITESPACES
					if(curr==0 || curr==' ' || curr=='\b' ||
						curr=='\f'|| curr=='\t' || curr=='\r')
					{
						readCharacter();
						continue;
					}
					
					//Case: KEYWORD
					if(isLetter(curr))
					{
						state=STATE_WORD_START;
						continue;					
					}
					//Case: OPERATOR
					else if(isOperator(curr))
					{
		
						//Handle EQUALITY
						if(curr=='=')
						{
							readCharacter();
							state=STATE_EQUALITY;
							continue;
						}
						//Handle NOT EQUALITY
						else if(curr=='!')
						{
							readCharacter();
							state=STATE_NOT_EQUALITY;
							continue;
						}
						//Handle LOGICAL AND
						else if(curr=='&')
						{
							readCharacter();
							state=STATE_LOGICAL_AND;
							continue;
						}
						//Handle LOGICAL OR
						else if(curr=='|')
						{
							readCharacter();
							state=STATE_LOGICAL_OR;
							continue;
						}
						//Handle COMMENTS
						else if(curr=='/')
						{
							readCharacter();
							state=STATE_COMMENTS_HANDLER;
							continue;
						}
						else
						{
							Token token=getTokenByOperator(curr);
							readCharacter();
							return token;
						}

					}
					//Case: NUMBER
					else if(isNumeric(curr))
					{
						state=STATE_NUMERIC_START;
						continue;
					}
					//Case: ID starting with underscore
					else if(curr=='_')
					{
						state=STATE_ID_START;
						continue;
					}
					//Case: STRING Literals
					else if(isQuote(curr))
					{
						state=STATE_STRING_START;
						continue;
					}
					else
					{
						Token token=getErrorToken(curr);
						readCharacter();
						return  token;
					}
			
					
			///////////////////////////////////
			case STATE_WORD_START:
				
				if(isLetter(curr))
				{
					wordBuffer="";
					wordBuffer+=curr;
					
					state=STATE_WORD_BODY;
					readCharacter();
				}else return null;
				
				continue;
				
		    ////////////////////////////////////
		    case STATE_WORD_BODY:
		    	
		    	if(isLetter(curr) || isNumeric(curr))
		    	{
		    		wordBuffer+=curr;
		    		readCharacter();
		    	}
		    	else
		    	{
		    		//Case: KEYWORD
		    		if(isKeyword(wordBuffer))
		    		{
		    			return new Token(Token.KW,wordBuffer,line,at);
		    		}
		    		//Case : BOOLEAN
		    		else if(isBoolean(wordBuffer))
		    		{
		    			return new Token(Token.BL,wordBuffer,line,at);
		    		}
		    		else if(curr=='_')
		    		{
		    			state=STATE_ID_BODY;
		    		}
		    		//Case: ID not starting with letter
		    		else
		    		{
		    			return new Token(Token.ID,wordBuffer,line,at);
		    		}
		    		
		    	}
		    	continue;
		    ////////////////////////////////////////
		    case STATE_NUMERIC_START:
		    	
		    	if (isNumeric(curr)) 
		    	{
					numBuffer = 0; 
					numBuffer += (curr - '0');
					
					floatBuffer = "" + numBuffer;


					state = STATE_NUMERIC_BODY;
					curr = read();
				} else {
					return null;
				}
				continue;
			///////////////////////////////////////////
		    case STATE_NUMERIC_BODY:
		    	
		    	if (isNumeric(curr)) 
		    	{
					numBuffer *= 10;
					numBuffer += (curr - '0');

					floatBuffer = "" + numBuffer;
					curr = read();
					

				} 
				else if (curr == '.')
				{
					state =  STATE_DECIMAL_HANDLER;
					
					floatBuffer += ".";
					curr = read();
					
				
					
					// Go back to handle "3."
					if (!isNumeric(curr)) {
						
						// if (curr == '.')
						// {
						// 	readCharacter();
						// }
						
			
						state = STATE_CONTROLLER;
					}
				}
				else 
				{
				
					return new Token(Token.NM, "" + floatBuffer,line,at);
				}
				continue;
			/////////////////////////////////////////////////
			case STATE_DECIMAL_HANDLER:
				
				if (isNumeric(curr)) 
				{
					
					floatBuffer += curr;
					readCharacter();
			
				}
				else {
					
					return new Token(Token.NM, "" + removeTrailingZeors(floatBuffer),line,at);
				}
				
				continue;
				/////////////////////////////////////////////////////
			case STATE_ID_START:
				
				if(curr=='_')
				{
					wordBuffer="";
					wordBuffer+=curr;
					readCharacter();
					state=STATE_ID_BODY;
				}else return null;
				
				continue;
				//////////////////////////////////////////////////////////
				case STATE_ID_BODY:
					
					if(isLetter(curr) || isNumeric(curr) || curr=='_')
					{
						wordBuffer+=curr;
						readCharacter();
					}
					else return new Token(Token.ID,wordBuffer,line,at);
				continue;
				/////////////////////////////////////////////////////////////
				case STATE_EQUALITY:
					if(curr!='=')
					{
						//readCharacter();
						return getTokenByOperator('=');
					}else {readCharacter();return new Token(Token.EQ,"==",line,at);}
				//////////////////////////////////////////////////////////////
				case STATE_NOT_EQUALITY:
					if(curr=='=')
					{
						readCharacter();
						return new Token(Token.NE,"!=",line,at);
					}else{readCharacter(); return getErrorToken('!');}
				///////////////////////////////////////////////////////////////
				case STATE_LOGICAL_AND:
					if(curr=='&')
					{
						readCharacter();
						return new Token(Token.LA,"&&",line,at);
						
					}else return getErrorToken('&');
				///////////////////////////////////////////////////////////////
				case STATE_LOGICAL_OR:
					if(curr=='|')
					{
						readCharacter();
						return new Token(Token.LO,"||",line,at);
					}
					else return getErrorToken('|');
				/////////////////////////////////////////////////////////
				case STATE_STRING_START:
					
					if(isQuote(curr))
					{
						wordBuffer="";
						wordBuffer+=curr;
						readCharacter();
						state=STATE_STRING_BODY;
					}else return null;
				continue;
				//////////////////////////////////////////////////////////
				case STATE_STRING_BODY:
					
					if(isQuote(curr))
					{
						wordBuffer+=curr;
						readCharacter();
						return new Token(Token.ST,wordBuffer,line,at);
					}
					else if(curr=='\n')
					{
						//TODO case one qoute at end of file no breaks
						state=STATE_CONTROLLER;
						
						// System.out.println("WordBuffer:  " + wordBuffer);
						readCharacter();
						return new Token(Token.ERROR,wordBuffer,line,at);
					}
					else
					{
						// System.out.println("WordBuffer22222:  " + wordBuffer);
						wordBuffer+=curr;
						readCharacter();
						
					}
					continue;
				///////////////////////////////////////////////////////////////
				case STATE_COMMENTS_HANDLER:
					//Handle Single Comments
					if(curr=='/')
					{
						readCharacter();
						state=STATE_SINGLE_COMMENT;
						continue;
					}
					//Handle Multiple comments open
					else if(curr=='*')
					{	line++;
						at=1;
						readCharacter();
						state=STATE_MULTIPLE_COMMENT_OPEN;
						continue;
					}
					else return new Token(Token.DO,"/",line,at);

				//////////////////////////////////////////////////////////////////
				case STATE_SINGLE_COMMENT:
					if(curr!='\n')
					{
						readCharacter();
						continue;
					}
					else 
					{
						at=1;
						line++;
						readCharacter();
						state=STATE_CONTROLLER;
					}
					continue;
				///////////////////////////////////////////////////////////////////
				case STATE_MULTIPLE_COMMENT_OPEN:
					if(curr!='*')
					{
						readCharacter();
						continue;
					}
					else 
					{
						readCharacter();
						state=STATE_MULTIPLE_COMMENT_CLOSE;
					}
					continue;
				/////////////////////////////////////////////////////////////////////
				case STATE_MULTIPLE_COMMENT_CLOSE:
					if(curr=='/')
					{
						readCharacter();
						state=STATE_CONTROLLER;
						continue;
					}
					//TODO Comment multiple not closed
					else{readCharacter();continue;}
					
				
					
					


						
		    
		     }

				
				
			
				
				
			}
		}
	}

