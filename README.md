# NodeRedDoSDetection

## Modules
### JavaScriptToNeo4J
Module to save JavaScript files to a Neo4J Database. Most important is the compiler class, it offers to convience methods
taking some inputfiles and a path to a database to write the AST and the CFG to the database. It instance a Google Closure Compiler and operates it. 

The database will contain a AST and CFG within the AST for every JavaScript input file. Labels, Relationshiptypes and Properties
are defined in JavaScriptToNeo4J/db.

To run my current run configiguration execute `mvn clean install exec:java`. [Maven](https://maven.apache.org/) needs to be installed and on your path. This configurations are used by me to validate my work. They will change quickly and will be not documented. Example usage programs might be added later.
If you use Intelij the run configuration I use are shared as well.

### NodeRedDoS
Contains all the JSON-Representation of Node-Red DoS Attacks used. Will contain also programs to measure the impact of this 
attacks on a [Wamp Server](http://www.wampserver.com/en/) (Version 2.5, default configuration).
To run this attacks install [Node-RED](http://nodered.org/) on your machine and import the JSON-File from clipboard. They will normaly attack an apache running
on localhost:80.

### Archive
Old modules not used or supported anymore.

  
