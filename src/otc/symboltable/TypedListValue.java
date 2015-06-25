package otc.symboltable;

public class TypedListValue extends Symbol 
{
	private Type type;
	private String value;
	private String value2; 
	
	public String getValue(){return value;}
	public void setValue(String v1){this.value = v1;}
	
	public String getValue2(){return value2;}
	public void setValue2(String v2){this.value2 = v2;}
	
	public TypedListValue(Type t, String v)
	{
		super.setType(t); 
		this.value = v; 
	}
	

	public TypedListValue(Type t, String v, String v2)
	{
		super.setType(t); 
		this.value = v;
		this.value2 = v2; 	
	}
	
	
	public String toString()
	{
		String r = this.getType() + " " + this.value;
		if (this.value2 != null) r += " to " + this.value2;
		return r; 
	}
	
	@Override
	public boolean equals(Object other)
	{
		if(other == null) return false; 
		if(other == this) return true; 
		if(!(other instanceof TypedListValue)) return false;
		
		TypedListValue otherTypedValue = (TypedListValue) other; 
		
		return this.getValue() == otherTypedValue.getValue();
	}

}
