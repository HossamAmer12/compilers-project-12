
// XX unused
public class CallExpr
{
	String methodName;
	public ActualParams actualParams;
	
	
	public CallExpr()
	{}
	
	public CallExpr(String methodName, ActualParams actualParams)
	{
		this.methodName = methodName;
		this.actualParams = actualParams;
	}
	
	public String toString()
	{
		return "";
	}
}