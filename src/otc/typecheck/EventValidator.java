package otc.typecheck;

import java.util.HashMap;

/**
 * We want to be able to validate our sequences, and our events. 
 * @author vikramsundaram
 *
 */
public class EventValidator 
{
	// We keep a hashmap to make it quick access to our events. 
	private static HashMap<String, Event> validEvents = new HashMap<String, Event>();
	
	
	
	/*********************************************************************************			
	 * 			 				 VALIDATION METHODS 								 *
	 *********************************************************************************/
	
	// We simply want to know if our event names are valid. 
	public static boolean isValidEventName(String scriptName)
	{
		if(validEvents.get(scriptName) == null) return false; 
		
		return true; 
	}
	
	


	
	/*********************************************************************************			
	 * 			 				 EVENT CREATION 								  	 *
	 *********************************************************************************/
	
	/**
	 * This is where the magic happens, we call the initializing method
	 *  for each event we want to include. 
	 * If we want to add a new event we simply add a new method and call it from init. 
	 */
	public static void init()
	{
		addCTSimBooked();
		addCTSimCompleted();
		addInitialConsultCompleted(); 
		addConsultReferralReceived(); 
		addTreatmentBegan(); 
		addPatientScheduled(); 
		addPatientArrives(); 
	}
	
	private static void addCTSimBooked() 
	{
		Event ctSimBooked = new Event(); 
		
		ctSimBooked.setScriptName("ct_sim_booked");
		ctSimBooked.setDatabaseName("CT SIM Booked");
		
		validEvents.put(ctSimBooked.getScriptName(), ctSimBooked); 
	}
	
	private static void addCTSimCompleted() 
	{
		Event ctSimCompleted = new Event(); 
		
		ctSimCompleted.setScriptName("ct_sim_completed");
		ctSimCompleted.setDatabaseName("CT SIM Completed");
		
		validEvents.put(ctSimCompleted.getScriptName(), ctSimCompleted); 
	}
	
	private static void addInitialConsultCompleted() 
	{
		Event initialConsultCompleted = new Event();
		
		initialConsultCompleted.setScriptName("initial_consult_completed");
		initialConsultCompleted.setDatabaseName("Initial Consult Completed");
		
		validEvents.put(initialConsultCompleted.getScriptName(), initialConsultCompleted); 
		
		
	}

	private static void addConsultReferralReceived() 
	{
		Event consultReferralReceived = new Event();
		
		consultReferralReceived.setScriptName("consult_referral_received");
		consultReferralReceived.setDatabaseName("Consult referral Received");
		
		validEvents.put(consultReferralReceived.getScriptName(), consultReferralReceived); 
	}
	
	private static void addTreatmentBegan()
	{
		Event treatmentBegan = new Event();
		
		treatmentBegan.setScriptName("treatment_began");
		treatmentBegan.setDatabaseName("Treatment Began");
		
		validEvents.put(treatmentBegan.getScriptName(), treatmentBegan); 
	}
	
	private static void addPatientScheduled()
	{
		Event patientScheduled = new Event();
		
		patientScheduled.setScriptName("patient_scheduled");
		patientScheduled.setDatabaseName("Patient Scheduled");
		
		validEvents.put(patientScheduled.getScriptName(), patientScheduled); 
	}
	
	private static void addPatientArrives()
	{
		Event patientArrives = new Event();
		
		patientArrives.setScriptName("patient_arrives");
		patientArrives.setDatabaseName("Patient Arrives");
		
		validEvents.put(patientArrives.getScriptName(), patientArrives); 
	}
	
}
