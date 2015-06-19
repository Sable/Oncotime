package otc.symboltable;

import otc.node.ADoctorActor;
import otc.node.APatientActor;
import otc.node.PActor;

public class ActorSymbol extends Symbol 
{
	
	// All actors are hashed using this key. 
	public static String actorKey = "ActorSymbol"; 
	
	// Each Actor Symbol also has the name of the variable. 
	private String name; 
	public String getName(){return this.name;}
	
	
	/**
	 * Constructor, deconstructs the actor node into the appropriate actor Symbol.
	 * @param actor
	 * @param pName
	 */
	public ActorSymbol(PActor actor, String pName) 
	{
		
		// Get the appropriate actor type. s
		if(actor instanceof ADoctorActor)
			this.objectType = ObjectType.Doctor; 
		else if(actor instanceof APatientActor)
			this.objectType = ObjectType.Patient; 
		else
			this.objectType = ObjectType.Diagnosis; 
		
		// Set the name. 
		this.name = pName; 
		
	}
	

}
