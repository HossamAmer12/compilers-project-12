public class SemanticException extends Exception {
	public static final int DUPLICATE_VAR=1;
	public static final int DUPLICATE_METHOD=2;
	public static final int VAR_DECLARATION=3;
	public static final int TYPE_MISMATCH=4;
	public static final int CONDITION_MISMATCH=5;
	public static final int METHOD_UNDEFINED=6;
	public static final int METHOD_MISSING_ARGUMENTS=7;
	public static final int VAR_INITIALIZATION=8;
	
	
	public SemanticException(String s) {
		super(s);
	}
	
	public SemanticException(String s,int type) throws SemanticException{
		String exception="";
		
		switch(type){
		
		case DUPLICATE_VAR:
			exception=String.format("Duplicate local variable %s.", s);
			break;
		case DUPLICATE_METHOD:
			exception=String.format("Duplicate method declaration %s.",s);
			break;
		case VAR_DECLARATION:
			exception=String.format("%s cannot be resolved to a variable.",s);
			break;
		case TYPE_MISMATCH:
			exception=String.format("Type mismatch for variable %s.",s);
			break;
		case CONDITION_MISMATCH:
			exception=String.format("Condition type mismatch for %s: Must be boolean.",s);
			break;
		case METHOD_UNDEFINED:
			exception=String.format("The method %s is undefined",s);
			break;
		case METHOD_MISSING_ARGUMENTS:
			exception=String.format("The method call for %s has missing or invalid arguments",s);
			break;
		case VAR_INITIALIZATION:
			exception=String.format("%s must be initialized first.", s);
			break;
		}
		
		throw new SemanticException(exception);
	}
	
}
