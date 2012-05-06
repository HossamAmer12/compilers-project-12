public class MethodDecl
{
	public Type type;
	public FormalParams formalParams;
	public Block block;
	
	
	public static final int LB = 1;
	public static final int RB = 2;
	public static final int ID = 3; // for id
	public static final int KW = 4; // for static
	
	public MethodDecl()
	{}
	
	public MethodDecl(Type type, FormalParams formalParams, Block block)
	{
		this.type = type;
		this.formalParams = formalParams;
		this.block = block;
	}
	
}