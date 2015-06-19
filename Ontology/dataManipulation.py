

class Patient(object):
	def __init__(self, pID, pSex, pBirthyear, pDiagnosis, pPostalcode):
		self.id = pID
		self.sex = pSex
		self.birthyear = pBirthyear
		self.diagnosis = pDiagnosis
		self.postalcode = pPostalcode
		self.events = []

	def __getitem__(self, field):
		if field == "ID":
			return self.id 
		if field == "SEX":
			return self.sex 
		if field == "BIRTHYEAR":
			return self.birthyear
		if field == "DIAGNOSIS":
			return str(self.diagnosis)
		if field == "POSTALCODE":
			return self.postalcode
		if field == "EVENTS":
			return str(self.events)

	def addPatientEvent(self, name, doc, time):
		for event in self.events:
			if event.name == name:
				event.doctor += [doc]
				event.doctor = list(set(event.doctor))
				return
		self.events.append(self.PatientEvent(name, doc, time))
		self.events.sort(key=lambda x: x.time, reverse=False)

	class PatientEvent(object):
		def __init__(self, pName, pDoc, pTime):
			self.name = pName 
			self.doctor = [pDoc]
			self.time = pTime

		def __repr__(self):
			return "%s with %s on %s" % (self.name, self.doctor, self.time)

	@staticmethod
	def getfields():
		return ['ID', 'SEX', 'BIRTHYEAR', 'POSTALCODE', 'DIAGNOSIS', "EVENTS"]

class Doctor(object):
	def __init__(self, pID, pOncologist):
		self.id = pID
		self.oncologist = pOncologist

	def __getitem__(self, field):
		if field == "ID":
			return self.id
		if field == "ONCOLOGIST":
			return self.oncologist

	@staticmethod
	def getfields():
		return ['ID', 'ONCOLOGIST']

class Diagnosis(object):
	def __init__(self, pDiagnosis):
		self.diagnosis = pDiagnosis

	def __getitem__(self, field):
		if field == "DIAGNOSIS":
			return self.diagnosis

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

	def __init__(self, raw_data):
		
		self.patients = {}
		self.doctors = {}
		self.diagnosis = {}
		self.events = {}

		def add_patient(self, item, event):
			
			# Get all the required information
			id = item['PatientSerNum']
			sex = item['Sex']
			birthyear = item['DateOfBirth']
			diagnosis = [item['Description']]
			postalcode = item['PostalCode']

			# Get all the required information for the patient event. 
			name = event 
			docId = item['DoctorSerNum']
			time = item['TimeStamp']
			
			# Construct the patient object. 
			patient = Patient(id, sex, birthyear, diagnosis, postalcode)
			patient.addPatientEvent(name, docId, time)

			# If It already exists, we augment it's information. 
			old_patient = self.patients.get(id, None)

			if old_patient == None:
				self.patients[patient.id] = patient 
			else:
				old_patient.diagnosis += diagnosis
				old_patient.diagnosis = list(set(old_patient.diagnosis))
				old_patient.addPatientEvent(name, docId, time)

		def add_doctor(self, item):
			
			# Get all the required information
			oncologist = item['OncologistFlag']
			id = item['DoctorSerNum']

			# Construct the doctor object. 
			doctor = Doctor(id, oncologist)

			# If it already exists, we augment it's information. 
			old_doctor = self.doctors.get(id, None)

			if(old_doctor == None):
				self.doctors[doctor.id] = doctor 

		def add_diagnosis(self, item):

			# Add all the required information. 
			diagnosis_name = item['Description']

			# Construct the diagnosis object. 
			diagnosisObj = Diagnosis(diagnosis_name)

			self.diagnosis[diagnosisObj.diagnosis] = diagnosisObj

		for event in raw_data:
			for item in raw_data[event]: 
				add_patient(self, item, event)
				add_doctor(self, item)
				add_diagnosis(self, item)
				# add_event(self, item)

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

def printActor(results, name, actor_type, attr_list = None):
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

