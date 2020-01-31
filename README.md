# Semesterarbeit
Semesterarbeit von Stefan Hutter, Philipp Tuor, Rade Ilic

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
Willkomen auf unserem Git Repository für unsere Semesterarbeit.

Wir arbeiten zu dritt an einem "Tank Wars" Spiel (ähnlich wie Schiffe versenken).

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
Installationsanleitung:
1. Clone Git Repository (Master branch)
2. Setup SDK (vielleicht wird ein anderer SDK benutzt)
3. Ab SDK 11 muss ein Pathfix(--module-path ${PATH_TO_FX} --add-modules javafx.controls,javafx.fxml) bei intellij unter run edit      
   eingefügt werden und die Libraries von JavaFX hinzugefügt werden.
4. Im Ordner src sind die Unit Tests und die Java Applikation
5. Für Unit Tests-> Maven -> Rechtsklick auf install ->run
6. Die TankWars_Application.java öffnen und ausführen

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
Spielanleitung:
- Als Spieler wird man mit Instruction Labels durch das Spiel geführt.
- Bei Problemen um Mehrspielermodus, vergleichen Sie die angezeigete IP Adresse im Game mit der Netzwerkkonfiguration ihres Rechners (bei Windows ipconfig im Terminal). Es kann sein, wenn sie mehrere aktive Netzwerkkonfigurationen haben, dass im Game die falsche ip angezeigt wird.

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
Folgende Regeln gelten in unserem Repository:

Wir arbeiten auf einem Developement Branch.
Auf diesem Branch sind immer die neusten Commits.

In den Master wird erst gepusht, wenn die user story den Status DONE hat. 
Der Push findet an Gruppenmeetings statt, wobei wichtig ist, dass die ganze Gruppe mit der Funktion einverstanden ist.

Ansonsten befinden sich die stories im Status: OPEN, IN PROGRESS oder REVIEW.
Sobald eine user story im Status Review ist, ist diese Story im Developement Branch von einem Developer erledigt und bereit zur Überprüfung.
Wenn im meeting entschieden wird, dass diese Story richtig erledigt wurde, kann die Story als DONE definiert werden und die Story kann in den Master gepusht werden.

Wichtig ist, dass niemals direkt in den Master gepusht wird! 
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
