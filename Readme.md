# *** MOVED TO GITLAB ***

This is the OncoTime Summer Research Project. 

You can make the compiler by running (in the src folder):
"make jar"

This creates otc.jar in the root folder.

To compile an OncoTime program, run:
"java -jar otc.jar <Filename>.onc > Filename.py"

You will have to install the following Python packages:
prettytable
MySQLdb

To change your database connection configuration, edit the file:
"src/otc/codegeneration/CodeGenerator.java"
This must match the mysql-server configuration on your system.

You can setup the toydb on your machine by running:
"source toydb.sql" on the mysql shell or
"mysql -u [username] - p   [dbname] < toydb.sql" in the command prompt.
You may need to create the oncodb database first ("CREATE DATABASE oncodb;").




