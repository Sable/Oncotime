import MySQLdb
from prettytable import PrettyTable
db=MySQLdb.connect(host='127.0.0.1', port=2000, user='520student', passwd='comp520', db='oncodb')

ontology={"consult_referral_received": {"info":["Task.LastUpdated as TimeStamp","Patient.PatientSerNum","Patient.Sex","Patient.DateOfBirth","Patient.PostalCode","PatientDoctor.DoctorSerNum","PatientDoctor.OncologistFlag","Diagnosis.Description","Task.CreationDate","Task.DueDateTime","Priority.PriorityCode","Task.CompletionDate"],"tables":[	"Patient","Priority","Task","Alias","Diagnosis","PatientDoctor"],"constraints": {"Alias.AliasName":"\"CRR with Origin\"","Alias.AliasSerNum":"Task.AliasSerNum","Task.PatientSerNum":"Patient.PatientSerNum","Task.PrioritySerNum":"Priority.PrioritySerNum","Task.Status":"\"Open\"","Diagnosis.PatientSerNum":"Patient.PatientSerNum","PatientDoctor.PatientSerNum":"Patient.PatientSerNum"},"period":"Task"},"initial_consult_booked": {"info":["Task.LastUpdated as TimeStamp","Patient.PatientSerNum","Patient.Sex","Patient.DateOfBirth","Patient.PostalCode","PatientDoctor.DoctorSerNum","PatientDoctor.OncologistFlag","Diagnosis.Description","Task.CreationDate as TimeStamp","Task.DueDateTime","Priority.PriorityCode","Task.CompletionDate"],"tables":["Patient","Priority","Task","Alias","Diagnosis","PatientDoctor"],"constraints": {"Alias.AliasName":"\"CRR with Origin\"","Alias.AliasSerNum":"Task.AliasSerNum","Task.PatientSerNum":"Patient.PatientSerNum","Task.PrioritySerNum":"Priority.PrioritySerNum","Task.Status":"\"In Progress\"","Diagnosis.PatientSerNum":"Patient.PatientSerNum","PatientDoctor.PatientSerNum":"Patient.PatientSerNum"},"period":"Task"},"initial_consult_completed": {"info":["Task.LastUpdated as TimeStamp","Patient.PatientSerNum","Patient.Sex","Patient.DateOfBirth","Patient.PostalCode","PatientDoctor.DoctorSerNum","PatientDoctor.OncologistFlag","Diagnosis.Description","Task.CreationDate as TimeStamp","Task.DueDateTime","Priority.PriorityCode","Task.CompletionDate"],"tables":["Patient","Priority","Task","Alias","Diagnosis","PatientDoctor"],"constraints": {"Alias.AliasName":"\"CRR with Origin\"","Alias.AliasSerNum":"Task.AliasSerNum","Task.PatientSerNum":"Patient.PatientSerNum","Task.PrioritySerNum":"Priority.PrioritySerNum","Task.Status":"\"Completed\"","Diagnosis.PatientSerNum":"Patient.PatientSerNum","PatientDoctor.PatientSerNum":"Patient.PatientSerNum"},"period":"Task"},"ct_sim_booked": {"info":["Patient.PatientSerNum","PatientDoctor.DoctorSerNum","PatientDoctor.OncologistFlag","Patient.Sex","Patient.DateOfBirth","Patient.PostalCode","Diagnosis.Description","Appointment.LastUpdated as TimeStamp"],"tables":["Patient","Appointment","Alias","Diagnosis","PatientDoctor"],"constraints": {"Alias.AliasName":"\"Ct-Sim\"","Alias.AliasSerNum":"Appointment.AliasSerNum","Appointment.PatientSerNum":"Patient.PatientSerNum","Diagnosis.PatientSerNum":"Patient.PatientSerNum","PatientDoctor.PatientSerNum":"Patient.PatientSerNum"},"period":"Appointment"},"ct_sim_completed": {"info":["Patient.PatientSerNum","PatientDoctor.DoctorSerNum","PatientDoctor.OncologistFlag","Patient.Sex","Patient.DateOfBirth","Patient.PostalCode","Diagnosis.Description","Appointment.LastUpdated as TimeStamp","Appointment.ScheduledStartTime","Appointment.ScheduledEndTime"],"tables":["Patient","Appointment","Alias","Diagnosis","PatientDoctor"],"constraints": {"Alias.AliasName":"\"Ct-Sim\"","Alias.AliasSerNum":"Appointment.AliasSerNum","Appointment.PatientSerNum":"Patient.PatientSerNum","Diagnosis.PatientSerNum":"Patient.PatientSerNum","PatientDoctor.PatientSerNum":"Patient.PatientSerNum","Appointment.Status":"\"Manually Completed\""},"period":"Appointment"},"treatment_began": {"info":["Patient.PatientSerNum","PatientDoctor.DoctorSerNum","PatientDoctor.OncologistFlag","Patient.Sex","Patient.DateOfBirth","Patient.PostalCode","Diagnosis.Description","Appointment.LastUpdated as TimeStamp","Appointment.ScheduledStartTime","Appointment.ScheduledEndTime"],"tables":["Patient","Appointment","Alias","Diagnosis","PatientDoctor"],"constraints": {"Alias.AliasName":"\"First Treatment\"","Alias.AliasSerNum":"Appointment.AliasSerNum","Appointment.PatientSerNum":"Patient.PatientSerNum","Diagnosis.PatientSerNum":"Patient.PatientSerNum","PatientDoctor.PatientSerNum":"Patient.PatientSerNum","Appointment.Status":"\"Open\""},"period":"Appointment"},"patient_arrives": {"info":["Patient.PatientSerNum","PatientDoctor.DoctorSerNum","PatientDoctor.OncologistFlag","Patient.Sex","Patient.DateOfBirth","Patient.PostalCode","Diagnosis.Description","PatientLocation.LastUpdated as TimeStamp","PatientLocation.ArrivalDateTime","Appointment.ScheduledStartTime"],"tables":["Patient","Appointment","Diagnosis","PatientDoctor","PatientLocation"],"constraints": {"Appointment.PatientSerNum":"Patient.PatientSerNum","Diagnosis.PatientSerNum":"Patient.PatientSerNum","PatientDoctor.PatientSerNum":"Patient.PatientSerNum","Appointment.AppointmentSerNum":"PatientLocation.AppointmentSerNum"},"period":"PatientLocation"},"patient_scheduled": {"info":["Patient.PatientSerNum","PatientDoctor.DoctorSerNum","PatientDoctor.OncologistFlag","Patient.Sex","Patient.DateOfBirth","Patient.PostalCode","Diagnosis.Description","Appointment.LastUpdated as TimeStamp","Appointment.ScheduledStartTime"],"tables":["Patient","Appointment","Diagnosis","PatientDoctor"],"constraints": {"Appointment.PatientSerNum":"Patient.PatientSerNum","Diagnosis.PatientSerNum":"Patient.PatientSerNum","PatientDoctor.PatientSerNum":"Patient.PatientSerNum",},"period":"Appointment"}}
events = ["*"]
filters = "and (Patient.DateOfBirth = 1960) and (Diagnosis.Description REGEXP \"breast\" or Diagnosis.Description REGEXP \"prostate\" or Diagnosis.Description REGEXP \"liver\") and (Patient.Sex REGEXP \"Male\") "

############################################################
# UTILITY METHODS	 		 							   #
############################################################
def clean(items):
	items = list(set(items))

############################################################
# OBJECT CLASSES	 		 							   #
############################################################

class Patient(object):
	def __init__(self, pID, pSex, pBirthyear, pPostalcode):
		self.id = pID
		self.sex = pSex
		self.birthyear = pBirthyear
		self.postalcode = pPostalcode
		self.events = []
		self.diagnosis = []
		self.doctors = []

	def __hash__(self):
		return hash(self.id)

	def __eq__(self, other):
		if other == None: return False
		else: return (self.id) == (other.id)

	def __getitem__(self, field):
		if field == "ID":
			return self.id 
		if field == "SEX":
			return self.sex 
		if field == "BIRTHYEAR":
			return self.birthyear
		if field == "DIAGNOSIS":
			return self.diagnosis
		if field == "POSTALCODE":
			return self.postalcode
		if field == "EVENTS":
			return self.events
		if field == "DOCTORS":
			return self.doctors

	def addDoctor(self, doctor):
		self.doctors += doctor
		clean(self.doctors)

	def addDiagnosis(self, diagnosis):
		self.diagnosis += diagnosis
		clean(self.diagnosis)

	@staticmethod
	def getfields():
		return ['ID', 'SEX', 'BIRTHYEAR', 'POSTALCODE', 'DIAGNOSIS', "EVENTS", "DOCTORS"]

class Doctor(object):
	def __init__(self, pID, pOncologist):
		self.id = pID
		self.oncologist = pOncologist
		self.patients = []
		self.diagnosis = []

	def __getitem__(self, field):
		if field == "ID":
			return self.id
		if field == "ONCOLOGIST":
			return self.oncologist

	def __hash__(self):
		return hash(self.id)

	def __eq__(self, other):
		if other == None: return False
		return (self.id) == (other.id)

	def addPatient(self, patient):
		self.patients += patient
		clean(self.patients)

	def addDiagnosis(self, diagnosis):
		self.diagnosis += diagnosis
		clean(self.diagnosis)

	@staticmethod
	def getfields():
		return ['ID', 'ONCOLOGIST']

class Diagnosis(object):
	def __init__(self, pDiagnosis):
		self.diagnosis = pDiagnosis

	def __getitem__(self, field):
		if field == "DIAGNOSIS":
			return self.diagnosis

	def __hash__(self):
		return hash(self.diagnosis)

	def __eq__(self, other):
		if other == None: return False
		return (self.diagnosis == other.diagnosis)

	@staticmethod
	def getfields():
		return ['DIAGNOSIS']

class Results(object):
	"""
		This global results object contains mutliple types of information. 
		It holds a list of Patient objects, and their associated data. 
		It holds a list of Doctor objects and their associated data. 
		It holds a list of Diagnosis objects and their associated data. 
		It holds a list of Event objects and their associated data. 
	"""
	def get_type(self, type):
		if type == "PATIENTS" or type == "PATIENT":
			return self.patients
		if type == "DOCTOR":
			return self.doctors 
		if type == "DIAGNOSIS":
			return self.diagnosis

	def add_actor(self, actor, type):
		actors = self.get_type(type)

		print actor

		if actors.get(actor, None) == None:
			actors[actor] = actor
		else:
			if type == "PATIENTS" or type == "PATIENT":
				actors[actor].addDoctor(actor.doctors)
				actors[actor].addDiagnosis(actor.diagnosis)
			if type == "DOCTOR":
				actors[actor].addPatient(actor.patients)
				actors[actor].addDiagnosis(actor.diagnosis)


	def __init__(self, raw_data):
		
		self.patients = {}
		self.doctors = {}
		self.diagnosis = {}
		self.events = {}
		
		def incorporateData(self, item, event):

			dataNotFound =  "Unknown"

			eventName = 	event 
			patId 	= 		item.get('PatientSerNum', dataNotFound)
			sex = 			item.get('Sex', dataNotFound)
			birthyear = 	item.get('DateOfBirth', dataNotFound)
			description = 	item.get('Description', dataNotFound)
			postalcode = 	item.get('PostalCode', dataNotFound)
			docId = 		item.get('DoctorSerNum', dataNotFound)
			oncologist = 	item.get('OncologistFlag')
			time = 			item.get('TimeStamp', dataNotFound)
			diagnosis = 	item.get('Description', dataNotFound)

			print "Event: %s for Patient: %s", (event, str(patId))  

			# Construct the Patient Object.
			patient = Patient(patId, sex, birthyear, postalcode) 
			
			# Construct the Doctor Object. 
			doctor = Doctor(docId, oncologist)
			
			# Construct the Diagnosis Object.
			diagnosis = Diagnosis(description)
			
			# Incorporate the data between objects.
			patient.addDoctor(doctor)
			patient.addDiagnosis(diagnosis)
			doctor.addPatient(patient)
			doctor.addDiagnosis(diagnosis)

			# Add the items to our Hash Tables.
			self.add_actor(patient, "PATIENT")
			self.add_actor(doctor, "DOCTOR")
			self.add_actor(diagnosis, "DIAGNOSIS")

		# Add the data to our hashes. 
		for event in raw_data:
			for item in raw_data[event]: 
				print event
				incorporateData(self, item, event)


############################################################
# FUNCTIONAL METHODS 		 							   #
############################################################

def get_results(events):
	"""
		This method gets the information from the database.
		It takes into account information from ontology.json, as well as filters. 
	"""

	# If our events set is empty, we assume all of them. 
	if events[0] == '*':
		events = ontology.keys()

	# Our Hashmap of results. 
	results = {}

	for event in events:
		
		# The information we want.  
		query = "SELECT "
		for ix in xrange(0, len(ontology[event]['info'])):
			query += ontology[event]['info'][ix]

			if(ix != len(ontology[event]['info']) - 1):
				query += ", " 

		# The tables where that information is located. 
		query += " FROM " 
		for ix in xrange(0, len(ontology[event]['tables'])):
			query += ontology[event]['tables'][ix]

			if(ix != len(ontology[event]['tables']) - 1):
				query += ", "


		# The constraints we have inherently. 
		query += " WHERE "
		for key,val in ontology[event]['constraints'].iteritems():
			query += key + "=" + val + " and "
		query = query[:-4]


		# The constraints from our filters. 
		periodTable = ontology[event]['period']
		query += filters.replace("PERIOD", periodTable)

		# Terminate the query.
		query += ";" 

		# Get the appropriate data. 
		a = db.cursor(MySQLdb.cursors.DictCursor)
		a.execute(query) 

		results[event] = a.fetchall() 

	return results 

############################################################
# COMPUTATION METHODS 		 							   #
############################################################

def tableDecleration(actorType, field, results):
	"""
		Returns a dictionary that counts the actors by their fields.  
	"""
	table = {}
	table["Actor"] = actorType
	table["Field"] = field 
	actor_table = results.get_type(actorType)	

	for key in actor_table:
		value = actor_table[key][field]

		if table.get(value, None) == None:
			table[value] = 1
		else:
			table[value] += 1

	return table

def printHeader(name):
	print "-" * len(name)
	print name
	print "-" * len(name)

def printSpace():
	print "\n" *1

def printTable(table):
	"""
		Pretty prints tables. 
	"""
	pretty_table = PrettyTable([table["Field"], "Count"])

	for key in table:
		if key != "Actor" and key != "Field":
			pretty_table.add_row([key, table[key]])

	print pretty_table

def printActor(results, name, actor_type, attr_list = None, tabCount = 0):
	"""
		Function handles printing actors, their attributes, and nested actors. 
	"""

	def getResult(actor_type, name, att):
		return str(results.get_type(actor_type)[name][att])

	# If we are printing a Patient
	if actor_type == "PATIENT":
		if attr_list == None:
			attr_list = Patient.getfields()		

	# If we are printing a Diagnosis
	if actor_type == "DIAGNOSIS":
		if attr_list == None:
			attr_list = Diagnosis.getfields()
	
	# If we are printing a Doctor.
	if actor_type == "DOCTOR":
		if attr_list == None:
			attr_list = Doctor.getfields()

	# Print the content
	printHeader(actor_type)	
			
	for att in attr_list:
		print "{0:<12}: {1}".format(att, getResult(actor_type, name, att))

	printSpace()

def printPatientTimeline(results, name):
	printHeader("Patient Timeline for: " + str(name))

	patient = results.get_type("PATIENT")[name]

	for event in patient.events:
		print "[{0:<19}]: {1:<25} with doctor(s) {2:<10}".format(str(event.time), str(event.name), str(event.doctor))

	printSpace()

def printLength(table):
	print "Table of {0} counted by {1} has length {2}".format(table["Actor"], table["Field"], len(table) - 2)


# We want to return a Hashmap of all the information we want.
results = Results(get_results(events))


############################################################
# GENERATED COMPUTATIONS 		 						   #
############################################################

# Normal Print
# Timeline Print
# Values Print
# Nested Print
# Table Item Print
# Sequences(!!)
# Tables




for p, pval in results.get_type("PATIENT").iteritems():
	for d in pval["DIAGNOSIS"]: 
		printActor(results,  p, "PATIENT")