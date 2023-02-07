#!/bin/bash

function startPoll
{
URL=$(curl -X POST http://127.0.0.1:8361/polls    -H "Content-Type: application/json" -d "{ \"topic\": \"$1\", \"options\": [ \"$2\", \"$3\", \"$4\"]}")
open http://127.0.0.1:8361/polls/$URL
}

function startDualPoll
{
URL=$(curl -X POST http://127.0.0.1:8361/polls    -H "Content-Type: application/json" -d "{ \"topic\": \"$1\", \"options\": [ \"$2\", \"$3\"]}")
open http://127.0.0.1:8361/polls/$URL
}

# kill whatever is blocking the port
## Kills the process running on port 8361, if there is one.
function killApp8361 {

  # Get ID of process running on 8361, if there is one
  PID=$(lsof -ti:8361)

  # If there is a service running, kill it
  if [[ -n "$PID" ]]; then
    kill "$PID"
  fi
}

killApp8361
sleep 1
java -jar ~/Code/LivePoll/target/LivePoll.jar &


# Library and Bank exercise recap
read -p "Press enter to open next poll"
startPoll "How did the Library and Bank exercise go?"  "Gave it an honest try" "Just read the solution" "There was homework?"

# Library and Bank exercise recap
read -p "Press enter to open next poll"
startPoll "How did the Maven exercise go?"  "Finished in class" "Finished at home" "Did not finish"




# Library and Bank exercise recap
#read -p "Press enter to open next poll"
#startPoll "How did the Library and Bank exercise go?"  "Gave it an honest try" "Just read the solution" "There was homework?"

#read -p "Press enter to open next poll"
#startDualPoll "For solving the Bank and Library example, the regular lecture time was" "Sufficient" "Insufficient"

#read -p "Press enter to open next poll"
#startDualPoll "We've seen two kinds of behavioural models, sequence diagrams and ... Diagrams" "Communication" "Collaboration"

#read -p "Press enter to open next poll"
#startPoll "In a SD, a box with the statement:  boolean foo := false  indicates... " "Comparison"  "Condition" "Assignment"

#read -p "Press enter to open next poll"
#startPoll "Which Spring Annotation comes closest to the CD <<System-Wide>> stereotype?" "@Singleton" "@Controller" "@Component"

#read -p "Press enter to open next poll"
#startPoll "Which diagrams are not directly relevant for the creation of SDs?" "Operation Models" "Use Case Models" "Class Diagrams"

#read -p "Press enter to open next poll"
#startPoll "Which diagrams are not directly relevant for the creation of CDs?" "Operation Models" "Envrionment Models" "Domain Models"


## SD follow up and CD recap
#read -p "Press enter to open next poll"
#startDualPoll "Sequence and Communication diagrams are semantically" "Different" "Equivalent"

#read -p "Press enter to open next poll"
#startDualPoll "The arrow direction in IMs is" "irrelevant" "relevant"

#read -p "Press enter to open next poll"
#startDualPoll "Steps in communication diagrams are" "enumerated" "ordered by lifeline"

#read -p "Press enter to open next poll"
#startPoll "Spring @Component is considered Singlton-like, because…" "only ever instance created" "internal implementation of singleton pattern" "neither of"

#read -p "Press enter to open next poll"
#startDualPoll "Time is captured in Sequence Diagrams by means of" "Lifeline Y axis" "Diagram X axis"

#read -p "Press enter to open next poll"
#startDualPoll "Why is it not necessary to implement a destroy even in Java" "Objects are never destroyed" "Destruction is taken care of by the GC"

#read -p "Press enter to open next poll"
#startDualPoll "Which type of interaction diagrams is requested in M6" "Sequence" "Communication"

#read -p "Press enter to open next poll (CD)"
#startPoll "Which java keyword best describes UML frozen refs" "final" "static" "nonvolatile"

exit 255

## New Year



#read -p "Press enter to open next poll"
#startPoll "I prefer lectures on..." "Tuesday" "Does not matter" "Thursday"

#read -p "Press enter to open next poll"
#startPoll "Maven dependencies are the same as plugins" "Always Yes" "Yes, if code is signed" "No"

#read -p "Press enter to open next poll"
#startPoll "Methods without public/private violate checkstyle" "Yes" "Yes, unless JDK 19" "No"

#read -p "Press enter to open next poll"
#startPoll "Which is true about autowiring" "Annot. field should be an IF" "Constructor injection is evil" "is same as @Component"

#read -p "Press enter to open next poll"
#startPoll "Which of the following is true" "LS is the same as BGP" "BGP is part of LS" "LS is part of BGP"

#read -p "Press enter to open next poll"
#startPoll "Printing an object to JSON also prints the object name" "Yes" "Random" "No"

#read -p "Press enter to open next poll"
#startPoll "Which is true about autowiring" "Annot. field should be an IF" "Constructor injection is evil" "is same as @Component"



## Spring / REST

#read -p "Press enter to open next poll"
#startPoll "Spring's @RestController annotation subclasses..." "@Autowired" "@Component" "@Value"

#read -p "Press enter to open next poll"
#startPoll "The ID of an idempotent resource is..." "Known before creation" "Not known before creation" "Incremental"

#read -p "Press enter to open next poll"
#startPoll "Non-Idempotent resources should be added with..." "POST on parent list" "PUT on child" "POST on child"

#read -p "Press enter to open next poll"
#startPoll "ResTL models, as REST API definitions are" "semantically incomplete" "semantically complete" "idempotent"

#read -p "Press enter to open next poll"
#startPoll "The ID of an idempotent resource is..." "Known before creation" "Not known before creation" "Incremental"

#read -p "Press enter to open next poll"
#startPoll "Spring is reflective that means it can" "Imitate REST Resources" "Change code at runtime" "Build to WAR files"



## Operation Models (recap II)

#read -p "Press enter to open next poll"
#startPoll "Operation Models have consistency constraints to all other models seen in class ?" "Yes" "All except Domain Models" "All except Use Cases"

#read -p "Press enter to open next poll"
#startPoll "The SCOPE entry of an operation model lists all" "Concepts read" "Concepts read or written" "Concepts written"

#read -p "Press enter to open next poll"
#startPoll "Operation models describe..." "the effects of an operation" "the algorithm to perform an operation" "algorithm and effects of an operation"

#read -p "Press enter to open next poll"
#startPoll "Service deployment commonly refers to..." "the associated github repos" "the associated containers" "the host machine location"

## Operation Models

# read -p "Press enter to open next poll"
# startPoll "Concept Models capture state of the system needed to..." "provide UC functionality" "capture implementation details" "compile"

# read -p "Press enter to open next poll"
# startPoll "Environment Models are in the same phase as..." "UC Models" "Domain Models" "Concept Models"

# read -p "Press enter to open next poll"
# startPoll "Concept Models and Domain Models" "Show operations" "Show multiplicities" "Show the system boundary"

# read -p "Press enter to open next poll"
# startPoll "UML DataType is used to..." "force compare by value" "force compare by reference" "implement String .equals"


## Env models

#read -p "Press enter to open next poll"
#startPoll "Which is NOT part of Environment Models" "Inputs" "Timed Events" "System Operations"

#read -p "Press enter to open next poll"
#startPoll "In Env. Models, all messages are..." "asynchronous" "synchronous" "single-threaded"

#read -p "Press enter to open next poll"
#startPoll "In Env. Models, modeling of facilitator actors is ___ recommended" "always" "if non-standard HID" "never"

#read -p "Press enter to open next poll"
#startPoll "In Env. Models, actor multiplicity indicates how many actor instances..." "exist" "exist simultaneously" "can communicate simultaneously"

#read -p "Press enter to open next poll"
#startPoll "The M in MVC translates to which Fondue requirement specification model" "Domain Models" "Concept Models" "REST Models"

#read -p "Press enter to open next poll"
#startPoll "In domain models the system boundaries are subject to..." "Nothing" "Scope" "Facilitator Actor Existence"


## Concept models

#read -p "Press enter to open next poll"
#startPoll "In UML, both black and white diamond imply..." "Transitivity" "Aggregation" "Composition"

#read -p "Press enter to open next poll"
#startPoll "UML Datatypes are used to..." "replace .equals()" "Group without objects" "Enable string comparison"

#read -p "Press enter to open next poll"
#startPoll "Which of the following is true" "Class diagrams subset concept models" "Concept diagrams represent domain models" "Concept fields can be objects"

#read -p "Press enter to open next poll"
#startPoll "Which of the following is wrong" "Associations can be reflective" "Multiplicities are mandatory" "Enumerations require an association"

#read -p "Press enter to open next poll"
#startPoll "I have installed the Lobby Service on my machine." "Yes" "Have unresolved issues" "No"

#read -p "Press enter to open next poll"
#startPoll "I finished the RESTify exercises" "Yes" "Have unresolved issues" "No"


## Object Orientation

#read -p "Press enter to open next poll"
#startPoll "Which OO concept is missing in Python" "Information Hiding" "Inheritance" "Encapsulation"

#read -p "Press enter to open next poll"
#startPoll "An objects behaviour depends on its state" "Yes" "Only if static" "No"

#read -p "Press enter to open next poll"
#startPoll "Which concept is part of UML interfaces but not of Java interfaces" "Getters" "Constructors" "Annotations"



## HTTP / REST

#read -p "Press enter to open next poll"
#startPoll "I have installed and tested the LS/BGP" "Yes" "Tried but failed" "Not yet"

#read -p "Press enter to open next poll"
#startPoll "I did the RESTify exercise homework" "Yes" "Not yet" "No, and I do not think I will"

#read -p "Press enter to open next poll"
#startPoll "The R in CRUD translates to which HTTP method" "Put" "Post" "Get"

#read -p "Press enter to open next poll"
#startPoll "The LS provided web-ui should be used as is in my splendor solution" "Yes" "No, but we can copy code" "No"

#read -p "Press enter to open next poll"
#startPoll "In REST, API endpoints are a direct representation of the underlying service implementation" "Yes" "Unsure" "No"

#read -p "Press enter to open next poll"
#startPoll "In HTTP, any side can initiate a connection any time" "Yes" "Unsure" "No"


