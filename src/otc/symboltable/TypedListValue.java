package otc.symboltable;

public class TypedListValue extends Symbol 
{
	private Type type;
	private String value;
	
	public String getValue(){return value;}
	public void setValue(String v2){this.value = v2;} 
	
	public TypedListValue(Type t, String v)
	{
		super.setType(t); 
		this.value = v; 
	}
	
	public String toString()
	{
		return this.getType() + " " + this.value; 
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
