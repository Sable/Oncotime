import MySQLdb
db=MySQLdb.connect(host='127.0.0.1', port=2000, user='520student', passwd='comp520', db='oncodb')

ontology={"consult_referral_received": {"info":["Task.LastUpdated as TimeStamp","Patient.PatientSerNum","Patient.Sex","Patient.DateOfBirth","Patient.PostalCode","PatientDoctor.DoctorSerNum","PatientDoctor.OncologistFlag","Diagnosis.Description","Task.CreationDate","Task.DueDateTime","Priority.PriorityCode","Task.CompletionDate"],"tables":[	"Patient","Priority","Task","Alias","Diagnosis","PatientDoctor"],"constraints": {"Alias.AliasName":"\"CRR with Origin\"","Alias.AliasSerNum":"Task.AliasSerNum","Task.PatientSerNum":"Patient.PatientSerNum","Task.PrioritySerNum":"Priority.PrioritySerNum","Task.Status":"\"Open\"","Diagnosis.PatientSerNum":"Patient.PatientSerNum","PatientDoctor.PatientSerNum":"Patient.PatientSerNum"},"period":"Task"},"initial_consult_booked": {"info":["Task.LastUpdated as TimeStamp","Patient.PatientSerNum","Patient.Sex","Patient.DateOfBirth","Patient.PostalCode","PatientDoctor.DoctorSerNum","PatientDoctor.OncologistFlag","Diagnosis.Description","Task.CreationDate as TimeStamp","Task.DueDateTime","Priority.PriorityCode","Task.CompletionDate"],"tables":["Patient","Priority","Task","Alias","Diagnosis","PatientDoctor"],"constraints": {"Alias.AliasName":"\"CRR with Origin\"","Alias.AliasSerNum":"Task.AliasSerNum","Task.PatientSerNum":"Patient.PatientSerNum","Task.PrioritySerNum":"Priority.PrioritySerNum","Task.Status":"\"In Progress\"","Diagnosis.PatientSerNum":"Patient.PatientSerNum","PatientDoctor.PatientSerNum":"Patient.PatientSerNum"},"period":"Task"},"initial_consult_completed": {"info":["Task.LastUpdated as TimeStamp","Patient.PatientSerNum","Patient.Sex","Patient.DateOfBirth","Patient.PostalCode","PatientDoctor.DoctorSerNum","PatientDoctor.OncologistFlag","Diagnosis.Description","Task.CreationDate as TimeStamp","Task.DueDateTime","Priority.PriorityCode","Task.CompletionDate"],"tables":["Patient","Priority","Task","Alias","Diagnosis","PatientDoctor"],"constraints": {"Alias.AliasName":"\"CRR with Origin\"","Alias.AliasSerNum":"Task.AliasSerNum","Task.PatientSerNum":"Patient.PatientSerNum","Task.PrioritySerNum":"Priority.PrioritySerNum","Task.Status":"\"Completed\"","Diagnosis.PatientSerNum":"Patient.PatientSerNum","PatientDoctor.PatientSerNum":"Patient.PatientSerNum"},"period":"Task"},"ct_sim_booked": {"info":["Patient.PatientSerNum","PatientDoctor.DoctorSerNum","PatientDoctor.OncologistFlag","Patient.Sex","Patient.DateOfBirth","Patient.PostalCode","Diagnosis.Description","Appointment.LastUpdated as TimeStamp"],"tables":["Patient","Appointment","Alias","Diagnosis","PatientDoctor"],"constraints": {"Alias.AliasName":"\"Ct-Sim\"","Alias.AliasSerNum":"Appointment.AliasSerNum","Appointment.PatientSerNum":"Patient.PatientSerNum","Diagnosis.PatientSerNum":"Patient.PatientSerNum","PatientDoctor.PatientSerNum":"Patient.PatientSerNum"},"period":"Appointment"},"ct_sim_completed": {"info":["Patient.PatientSerNum","PatientDoctor.DoctorSerNum","PatientDoctor.OncologistFlag","Patient.Sex","Patient.DateOfBirth","Patient.PostalCode","Diagnosis.Description","Appointment.LastUpdated as TimeStamp","Appointment.ScheduledStartTime","Appointment.ScheduledEndTime"],"tables":["Patient","Appointment","Alias","Diagnosis","PatientDoctor"],"constraints": {"Alias.AliasName":"\"Ct-Sim\"","Alias.AliasSerNum":"Appointment.AliasSerNum","Appointment.PatientSerNum":"Patient.PatientSerNum","Diagnosis.PatientSerNum":"Patient.PatientSerNum","PatientDoctor.PatientSerNum":"Patient.PatientSerNum","Appointment.Status":"\"Manually Completed\""},"period":"Appointment"},"treatment_began": {"info":["Patient.PatientSerNum","PatientDoctor.DoctorSerNum","PatientDoctor.OncologistFlag","Patient.Sex","Patient.DateOfBirth","Patient.PostalCode","Diagnosis.Description","Appointment.LastUpdated as TimeStamp","Appointment.ScheduledStartTime","Appointment.ScheduledEndTime"],"tables":["Patient","Appointment","Alias","Diagnosis","PatientDoctor"],"constraints": {"Alias.AliasName":"\"First Treatment\"","Alias.AliasSerNum":"Appointment.AliasSerNum","Appointment.PatientSerNum":"Patient.PatientSerNum","Diagnosis.PatientSerNum":"Patient.PatientSerNum","PatientDoctor.PatientSerNum":"Patient.PatientSerNum","Appointment.Status":"\"Open\""},"period":"Appointment"},"patient_arrives": {"info":["Patient.PatientSerNum","PatientDoctor.DoctorSerNum","PatientDoctor.OncologistFlag","Patient.Sex","Patient.DateOfBirth","Patient.PostalCode","Diagnosis.Description","PatientLocation.LastUpdated as TimeStamp","PatientLocation.ArrivalDateTime","Appointment.ScheduledStartTime"],"tables":["Patient","Appointment","Diagnosis","PatientDoctor","PatientLocation"],"constraints": {"Appointment.PatientSerNum":"Patient.PatientSerNum","Diagnosis.PatientSerNum":"Patient.PatientSerNum","PatientDoctor.PatientSerNum":"Patient.PatientSerNum","Appointment.AppointmentSerNum":"PatientLocation.AppointmentSerNum"},"period":"PatientLocation"},"patient_scheduled": {"info":["Patient.PatientSerNum","PatientDoctor.DoctorSerNum","PatientDoctor.OncologistFlag","Patient.Sex","Patient.DateOfBirth","Patient.PostalCode","Diagnosis.Description","Appointment.LastUpdated as TimeStamp","Appointment.ScheduledStartTime"],"tables":["Patient","Appointment","Diagnosis","PatientDoctor"],"constraints": {"Appointment.PatientSerNum":"Patient.PatientSerNum","Diagnosis.PatientSerNum":"Patient.PatientSerNum","PatientDoctor.PatientSerNum":"Patient.PatientSerNum",},"period":"Appointment"}}
events = ["*"]
filters = "and (Patient.DateOfBirth = 1960) and (Patient.Sex REGEXP \"Male\") "
class Patient(object):
	def __init__(self, pID, pSex, pBirthyear, pDiagnosis, pPostalcode):
		self.id = pID
		self.sex = pSex
		self.birthyear = pBirthyear
		self.diagnosis = pDiagnosis
		self.postalcode = pPostalcode

class Doctor(object):
	def __init__(self, pID, pOncologist):
		self.id = pID
		self.oncologist = pOncologist

class Diagnosis(object):
	def __init__(self, pDiagnosis):
		self.diagnosis = pDiagnosis

	def __str__(self):
		return "Foo"

class Results(object):
	"""
		This global results object contains mutliple types of information. 
		It holds a list of Patient objects, and their associated data. 
		It holds a list of Doctor objects and their associated data. 
		It holds a list of Diagnosis objects and their associated data. 
		It holds a list of Event objects and their associated data. 
	"""
	def __init__(self, raw_data):
		
		self.patients = {}
		self.doctors = {}
		self.diagnosis = {}
		self.events = {}

		def add_patient(self, item):
			
			# Get all the required information
			id = item['PatientSerNum']
			sex = item['Sex']
			birthyear = item['DateOfBirth']
			diagnosis = [item['Description']]
			postalcode = item['PostalCode']
			
			# Construct the patient object. 
			patient = Patient(id, sex, birthyear, diagnosis, postalcode)

			# If It already exists, we augment it's information. 
			old_patient = self.patients.get(id, None)

			if old_patient == None:
				self.patients[patient.id] = patient 
			else:
				old_patient.diagnosis += diagnosis
				old_patient.diagnosis = list(set(old_patient.diagnosis))

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
				add_patient(self, item)
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
		
# We want to return a Hashmap of all the information we want.
results = Results(get_results(events))