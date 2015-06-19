f = open(__file__[0:__file__.rfind('/')+1] + '/correct1_results.txt', 'w')

filters = 'Diagnosis.Description REGEXP "breast"  and ((Patient.DateOfBirth >= 1960  and Patient.DateOfBirth <= 1965 ) ) and ((PERIOD.LastUpdated >= "2000-01-01 00:00:00" and PERIOD.LastUpdated <= "2014-12-31 23:59:59") ) and '
events = ['*']
ontology={"consult_referral_received": {"info":["Task.LastUpdated as TimeStamp","Patient.PatientSerNum","Patient.Sex","Patient.DateOfBirth","Patient.PostalCode","PatientDoctor.DoctorSerNum","PatientDoctor.OncologistFlag","Diagnosis.Description","Task.CreationDate","Task.DueDateTime","Priority.PriorityCode","Task.CompletionDate"],"tables":["Patient","Priority","Task","Alias","Diagnosis","PatientDoctor"],"constraints": {"Alias.AliasName":"\"CRR with Origin\"","Alias.AliasSerNum":"Task.AliasSerNum","Task.PatientSerNum":"Patient.PatientSerNum","Task.PrioritySerNum":"Priority.PrioritySerNum","Task.Status":"\"Open\"","Diagnosis.PatientSerNum":"Patient.PatientSerNum","PatientDoctor.PatientSerNum":"Patient.PatientSerNum"},"period":"Task"},"initial_consult_booked": {"info":["Task.LastUpdated as TimeStamp","Patient.PatientSerNum","Patient.Sex","Patient.DateOfBirth","Patient.PostalCode","PatientDoctor.DoctorSerNum","PatientDoctor.OncologistFlag","Diagnosis.Description","Task.CreationDate as TimeStamp","Task.DueDateTime","Priority.PriorityCode","Task.CompletionDate"],"tables":["Patient","Priority","Task","Alias","Diagnosis","PatientDoctor"],"constraints": {"Alias.AliasName":"\"CRR with Origin\"","Alias.AliasSerNum":"Task.AliasSerNum","Task.PatientSerNum":"Patient.PatientSerNum","Task.PrioritySerNum":"Priority.PrioritySerNum","Task.Status":"\"In Progress\"","Diagnosis.PatientSerNum":"Patient.PatientSerNum","PatientDoctor.PatientSerNum":"Patient.PatientSerNum"},"period":"Task"},"initial_consult_completed": {"info":["Task.LastUpdated as TimeStamp","Patient.PatientSerNum","Patient.Sex","Patient.DateOfBirth","Patient.PostalCode","PatientDoctor.DoctorSerNum","PatientDoctor.OncologistFlag","Diagnosis.Description","Task.CreationDate as TimeStamp","Task.DueDateTime","Priority.PriorityCode","Task.CompletionDate"],"tables":["Patient","Priority","Task","Alias","Diagnosis","PatientDoctor"],"constraints": {"Alias.AliasName":"\"CRR with Origin\"","Alias.AliasSerNum":"Task.AliasSerNum","Task.PatientSerNum":"Patient.PatientSerNum","Task.PrioritySerNum":"Priority.PrioritySerNum","Task.Status":"\"Completed\"","Diagnosis.PatientSerNum":"Patient.PatientSerNum","PatientDoctor.PatientSerNum":"Patient.PatientSerNum"},"period":"Task"},"ct_sim_booked": {"info":["Patient.PatientSerNum","PatientDoctor.DoctorSerNum","PatientDoctor.OncologistFlag","Patient.Sex","Patient.DateOfBirth","Patient.PostalCode","Diagnosis.Description","Appointment.LastUpdated as TimeStamp"],"tables":["Patient","Appointment","Alias","Diagnosis","PatientDoctor"],"constraints": {"Alias.AliasName":"\"Ct-Sim\"","Alias.AliasSerNum":"Appointment.AliasSerNum","Appointment.PatientSerNum":"Patient.PatientSerNum","Diagnosis.PatientSerNum":"Patient.PatientSerNum","PatientDoctor.PatientSerNum":"Patient.PatientSerNum"},"period":"Appointment"},"ct_sim_completed": {"info":["Patient.PatientSerNum","PatientDoctor.DoctorSerNum","PatientDoctor.OncologistFlag","Patient.Sex","Patient.DateOfBirth","Patient.PostalCode","Diagnosis.Description","Appointment.LastUpdated as TimeStamp","Appointment.ScheduledStartTime","Appointment.ScheduledEndTime"],"tables":["Patient","Appointment","Alias","Diagnosis","PatientDoctor"],"constraints": {"Alias.AliasName":"\"Ct-Sim\"","Alias.AliasSerNum":"Appointment.AliasSerNum","Appointment.PatientSerNum":"Patient.PatientSerNum","Diagnosis.PatientSerNum":"Patient.PatientSerNum","PatientDoctor.PatientSerNum":"Patient.PatientSerNum","Appointment.Status":"\"Manually Completed\""},"period":"Appointment"},"treatment_began": {"info":["Patient.PatientSerNum","PatientDoctor.DoctorSerNum","PatientDoctor.OncologistFlag","Patient.Sex","Patient.DateOfBirth","Patient.PostalCode","Diagnosis.Description","Appointment.LastUpdated as TimeStamp","Appointment.ScheduledStartTime","Appointment.ScheduledEndTime"],"tables":["Patient","Appointment","Alias","Diagnosis","PatientDoctor"],"constraints": {"Alias.AliasName":"\"First Treatment\"","Alias.AliasSerNum":"Appointment.AliasSerNum","Appointment.PatientSerNum":"Patient.PatientSerNum","Diagnosis.PatientSerNum":"Patient.PatientSerNum","PatientDoctor.PatientSerNum":"Patient.PatientSerNum","Appointment.Status":"\"Open\""},"period":"Appointment"},"patient_arrives": {"info":["Patient.PatientSerNum","PatientDoctor.DoctorSerNum","PatientDoctor.OncologistFlag","Patient.Sex","Patient.DateOfBirth","Patient.PostalCode","Diagnosis.Description","PatientLocation.LastUpdated as TimeStamp","PatientLocation.ArrivalDateTime","Appointment.ScheduledStartTime"],"tables":["Patient","Appointment","Diagnosis","PatientDoctor","PatientLocation"],"constraints": {"Appointment.PatientSerNum":"Patient.PatientSerNum","Diagnosis.PatientSerNum":"Patient.PatientSerNum","PatientDoctor.PatientSerNum":"Patient.PatientSerNum","Appointment.AppointmentSerNum":"PatientLocation.AppointmentSerNum"},"period":"PatientLocation"},"patient_scheduled": {"info":["Patient.PatientSerNum","PatientDoctor.DoctorSerNum","PatientDoctor.OncologistFlag","Patient.Sex","Patient.DateOfBirth","Patient.PostalCode","Diagnosis.Description","Appointment.LastUpdated as TimeStamp","Appointment.ScheduledStartTime"],"tables":["Patient","Appointment","Diagnosis","PatientDoctor"],"constraints": {"Appointment.PatientSerNum":"Patient.PatientSerNum","Diagnosis.PatientSerNum":"Patient.PatientSerNum","PatientDoctor.PatientSerNum":"Patient.PatientSerNum",},"period":"Appointment"}}
import datetime, copy
from sets import Set

class Patient(object):
	def __init__(self, pID, pSex, pBirthyear, pDiagnosis, pPostalcode):
		self.kind = "Patient"
		self.id = pID
		self.sex = pSex
		self.birthyear = pBirthyear
		self.diagnosis = pDiagnosis
		self.postalcode = pPostalcode

class Doctor(object):
	def __init__(self, pID, pOncologist):
		self.kind = "Doctor"
		self.id = pID
		self.oncologist = pOncologist

class Diagnosis(object):
	def __init__(self, pDiagnosis):
		self.kind = "Diagnosis"
		self.name = pDiagnosis

class Entry(object):
	def __init__(self):
		''' This object will be built up dynamically by iterating over each entry returned from the database.
		@param x Each parameter will be the names of the columns returned from the database.
		@param timestamp Every instance will have a timestamp attribute.
		@param event_name Every instance will also have an event name attribute.
		'''
		pass
	''' The following two functions will allow entries to be compared using their timestamp attributes. 
		This will make sequence filtering very easy.
	'''
	def __cmp__(self, entry2):
		y, m, d, hh, mm, ss = self.__format_datetime(self.timestamp)
		d1 = datetime.datetime(y, m, d, hh, mm, ss)
		y, m, d, hh, mm, ss = self.__format_datetime(entry2.timestamp)
		d2 = datetime.datetime(y, m, d, hh, mm, ss)
		if d1 < d2:
			return -1
		elif d1 == d2:
			return 0
		elif d1 > d2:
			return 1
	
	
	def __format_datetime(self, timestamp):
		date = timestamp.split(" ")[0].split("-")
		time = timestamp.split(" ")[1].split(":")
		year, month, day = int(date[0]), int(date[1]), int(date[2])
		hours, minutes, seconds = int(time[0]), int(time[1]), int(time[2])
		return year, month, day, hours, minutes, seconds

class Results(object):
	def __init__(self, pRawData = None, pOuterScope = None, pDoctor = None, pPatient = None, pDiagnosis = None):
		''' Mainly this initializes the three main dictionaries used by a results object.
			patients, doctors, and diagnosis keep their respective keys are a list of entries that belong to that key.
		'''
		if pRawData != None:
			self.__load_from_data(pRawData)
		elif pOuterScope != None:
			self.doctors, self.diagnoses, self.patients = self.__copy(pOuterScope)
			if pDoctor != None:
				self.__load_from_scope(self.doctors, pDoctor)
			elif pDiagnosis != None:
				self.__load_from_scope(self.diagnoses, pDiagnosis)
			elif pPatient != None:
				self.__load_from_scope(self.patients, pPatient)

	def iterate_by(self, val):
		self.iterate = val

	def __iter__(self):
		if self.iterate == "patient":
			for p in self.patients:
				yield Results(pOuterScope=self, pPatient=p)
		elif self.iterate == "doctor":
			for d in self.doctors:
				yield Results(pOuterScope=self, pDoctor=d)
		elif self.iterate == "diagnosis":
			for d in self.diagnoses:
				yield Results(pOuterScope=self, pDiagnosis=d)

	def __load_from_scope(self, filter_dict, filter_key):
		entries = filter_dict[filter_key]
		entries_ids = Set([id(x) for x in entries])
		for d in [self.patients, self.diagnoses, self.doctors]:
			if filter_dict != d:
				empty = []
				for key in d:
					to_remove = [entry for entry in d[key] if id(entry) not in entries_ids]
					for entry in to_remove:
						d[key].remove(entry)
					if len(d[key]) == 0:
						empty.append(key)
				for key in empty:
					del d[key]
			else:
				safe = d[filter_key]
				d.clear()
				d[filter_key] = safe

	def __copy(self, other):
		doctors, diagnoses, patients = {}, {}, {}
		for key in other.doctors:
			doctors[key] = copy.copy(other.doctors[key])
		for key in other.diagnoses:
			diagnoses[key] = copy.copy(other.diagnoses[key])
		for key in other.patients:
			patients[key] = copy.copy(other.patients[key])
		return doctors, diagnoses, patients 

	def __load_from_data(self, pRawData):
		''' This will be called the first time we create an object from the global scope.
		@param pRawData This is the data returned from the database. It is a list of where each event has the format
						(columns [list], data [list of lists], event_name)
		'''
		def __add_to_dict(d, key, val):
			if key in d:
				d[key].add(val)
			else:
				d[key] = Set([val,])

		self.doctors = {}
		self.patients = {}
		self.diagnoses = {}

		for event_name, (columns, data) in pRawData.iteritems():
			doctor_ix = columns.index('DoctorSerNum')
			diagnosis_ix = columns.index('Description')
			patient_ix =  columns.index('PatientSerNum')
			for row in data:
				entry = Entry()
				entry.event_name = event_name
				for col_name, val in zip(columns, row):
					entry.__setattr__(col_name.lower(), val)
				__add_to_dict(self.doctors, row[doctor_ix], entry)
				__add_to_dict(self.diagnoses, row[diagnosis_ix], entry)
				__add_to_dict(self.patients, row[patient_ix], entry)

from matplotlib.backends.backend_pdf import PdfPages
class TimelineHelper(object):
	pdf_name = ""
	new_pdf = True
	pdf_obj = None

	@staticmethod 
	def reset_timeline(name):
		TimelineHelper.new_pdf = True
		TimelineHelper.pdf_name = name

	@staticmethod
	def display_timeline(results, patient_id):
		def squash_events(events):
			ret_events = [events[0]]
			for e in events[1:]:
				if e.event_name == ret_events[-1].event_name and e.timestamp == ret_events[-1].timestamp:
					continue
				ret_events.append(e)
			return ret_events

		def __format_datetime(timestamp):
			date = timestamp.split(" ")[0].split("-")
			time = timestamp.split(" ")[1].split(":")
			year, month, day = int(date[0]), int(date[1]), int(date[2])
			hours, minutes, seconds = int(time[0]), int(time[1]), int(time[2])
			return year, month, day, hours, minutes, seconds
		import matplotlib.pyplot as plt
		import matplotlib
		events = squash_events(sorted(results.patients[patient_id]))
		formatting = [__format_datetime(event.timestamp) for event in events]
		dates = [datetime.datetime(f[0], f[1], f[2], f[3], f[4], f[5]) for f in formatting]
		xs = [matplotlib.dates.date2num(d) for d in dates]
		ys = [1] * len(xs)
		fig = plt.figure()
		fig.suptitle('Timeline for Patient ' + list(events)[0].patientsernum, fontsize=14, fontweight='bold')

		ax = fig.add_subplot(111)
		fig.subplots_adjust(top=0.85)

		ax.set_xlabel('Date')

		ax.plot_date(dates, ys, 'o')
		x1,x2,y1,y2 = plt.axis()
		plt.axis((x1-100,x2+100,0,2))
		for ix, e in enumerate(events):
			ax.annotate(e.event_name, xy=(xs[ix], ys[ix]), xytext=(xs[ix], ys[ix]+0.1 * ix))
		fig.autofmt_xdate()
		if TimelineHelper.new_pdf == True:
			if TimelineHelper.pdf_obj != None:
				TimelineHelper.pdf_obj.close()
			TimelineHelper.new_pdf = False
			TimelineHelper.pdf_obj = PdfPages(TimelineHelper.pdf_name + '.pdf')
		TimelineHelper.pdf_obj.savefig(fig)
		plt.close()

def cleanup():
	if TimelineHelper.pdf_obj != None:
		TimelineHelper.pdf_obj.close()

def match_sequence(sub_results, template):
	''' @param sub_results is expected to be a Results type we are iterating over by patients.
		@param template is expected to be a list of event names
	'''
	patient_id = sub_results.patients.keys()[0]
	events = sorted(sub_results.patients[patient_id])
	template_ix = 0
	events_to_remove = []
	for patient_ix in xrange(0, len(events)):
		if events[patient_ix].event_name.strip().lower() == template[template_ix].strip().lower():
			template_ix += 1
		else:
			events_to_remove.append(events[patient_ix])
		if template_ix == len(template): 
			for e in events_to_remove:
				sub_results.patients[patient_id].remove(e)
			return True
	return False

def test():
	from template import get_results
	events = ["ct_sim_booked", "consult_referral_received"]
	results = get_results(events, "Patient.Sex=\"Male\"",test= True)
	res = Results(results)

	res.iterate_by("patient")
	for sub in res:
		print match_sequence(sub, events)
	
	'''
	res.iterate_by("diagnosis")
	for res_d in res:
		res_d.iterate_by("doctor")
		for res_d2 in res_d:
			print res_d2.diagnoses.keys(), res_d2.doctors.keys()
			print "\t", len(res_d2.patients)
	'''
	res.iterate_by("patient")
	for res2 in res:
		events = res2.patients[res2.patients.keys()[0]]
		for event in sorted(events):
			print event.event_name, event.timestamp, event.doctorsernum, event.description
		print
		print

def countTypeMappingHelper(type):
	typemap={}
	if type == "patient":
		typemap["id"] = "patientsernum"
		typemap["sex"] = "sex"
		typemap["birthyear"] = "dateofbirth"
		typemap["postalcode"] = "postalcode"
		return typemap
	elif type == "doctor":
		typemap["id"] = "doctorsernum"
		typemap["oncologist"] = "oncologistflag"
		return typemap
	else:
		typemap["name"] = "description"
		return typemap


def count (type, field, results):
	typemapping = countTypeMappingHelper(type)
	res = results
	count={}
	res.iterate_by(type)
	for res_d in res:
		events = res_d.patients[res_d.patients.keys()[0]]
		event = list(events)[0]
		if event.__getattribute__(typemapping[field]) in count:
			count[event.__getattribute__(typemapping[field])] += 1
		else:
			count[event.__getattribute__(typemapping[field])] = 1
	return count
	
def plotBar(tableDic):
	import matplotlib.pyplot as plt
	import matplotlib

	plt.bar(range(len(tableDic)), tableDic.values(), align='center')
	plt.xticks(range(len(tableDic)), tableDic.keys())
	plt.show()



def get_results(events, filters, ontology = None, test=False):
	if test:
		import json
		with open('ontology.json') as handle:
			ontology = json.load(handle)

	if events[0] == '*':
		events = ontology.keys()

	import re
	filters = re.sub('and[ ]*$', '', filters)
	results = {}
	for event in events:

		#print event
		#print filters 

		# Assign each table a unique identifier.
		event = event.strip("\"")
		if "Doctor" in filters:
			ontology[event]['tables'].append('Doctor')
			ontology[event]['constraints']['Doctor.DoctorSerNum'] = 'PatientDoctor.DoctorSerNum'
		tables = ontology[event]['tables']
		table_idents = {}
		for ix in xrange(0, len(tables)):
			table_idents[tables[ix]] = 't%d' % ix

		filters_n = filters


		if "PERIOD" in filters_n:
			filters_n = filters_n.replace("PERIOD", ontology[event]['period'])
		for t in table_idents:
			filters_n = filters_n.replace(t + ".", table_idents[t] + ".")
		# Build the query.
		query = "SELECT "
		for ix in xrange(0, len(ontology[event]['info'])):
			info = ontology[event]['info'][ix]
			table = info.split(".")[0]
			info_n = info.replace(table + ".", table_idents[table] + ".")

			query += info_n
			if ix != len(ontology[event]['info'])-1:
				query += ","


			query += " "

		query += "FROM "
		for t, ident in table_idents.iteritems():
			query += t + " " + ident + ", "
		query = query[:-2]

		print filters 

		query += " WHERE "
		for key,val in ontology[event]['constraints'].iteritems():
			key_n, val_n = key, val
			if "." in key:
				table = key.split(".")[0]
				key_n = key.replace(table + ".", table_idents[table] + ".")
			if "." in val:
				table = val.split(".")[0]
				val_n = val.replace(table + ".", table_idents[table] + ".")
			query += key_n + " = " + val_n + " and "
		if filters.strip() != "":
			query += filters_n
		else:
			query = query[:-4]

		print query + " \n\n"

		import paramiko
		client = paramiko.client.SSHClient()
		client.set_missing_host_key_policy(paramiko.AutoAddPolicy())
		client.connect('hig.cs.mcgill.ca',username='cs520')
		cmd = 'mysql -u 520student --password="comp520" -B -e ' + '\'use oncodb;' + query + '\';'
		_, stdout, stderr = client.exec_command(cmd)

		import copy
		columns = stdout.readline().strip().split('\t')
		if len(columns) <= 1:
			print "Warning: No events found for " + event
			continue
		data = []
		for line in stdout.readlines():
			data.append(line.strip().split('\t'))

		results[event] = (copy.deepcopy(columns), copy.deepcopy(data))

		# print results 

	return results

results = get_results(events, filters, ontology)

import sys
exit_prog = True
for _, (_, data) in results.iteritems():
	if len(data) > 0:
		exit_prog = False
if exit_prog:
	sys.exit(0)
res_global = Results(results)

x=count("diagnosis", "name", res_global)
plotBar(x)

for i in x:
	f.write(str(i) + ',' + str(x[i])+'\n')
f.write(str(len(x))+'\n')
cleanup()

f.close()