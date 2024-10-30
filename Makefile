runBDTest: BackendDeveloperTests.class
	java --module-path ../javafx/lib --add-modules javafx.controls --add-opens javafx.graphics/com.sun.javafx.application=ALL-UNNAMED -jar ../junit5fx.jar -cp . -c BackendDeveloperTests

runFDTest: FrontendDeveloperTests.class
	java --module-path ../javafx/lib --add-modules javafx.controls --add-opens javafx.graphics/com.sun.javafx.application=ALL-UNNAMED -jar ../junit5fx.jar -cp . -c FrontendDeveloperTests

BackendDeveloperTests.class: BackendDeveloperTests.java	Backend.class Frontend.class DijkstraGraph.class
	javac --module-path ../javafx/lib --add-modules javafx.controls -cp .:../junit5fx.jar BackendDeveloperTests.java

FrontendDeveloperTests.class: FrontendDeveloperTests.java
	javac --module-path ../javafx/lib --add-modules javafx.controls -cp .:../junit5fx.jar FrontendDeveloperTests.java

Frontend.class: Frontend.java
	javac --module-path ../javafx/lib --add-modules javafx.controls Frontend.java

GraphPlaceholder.class: GraphPlaceholder.java
	javac GraphPlaceholder.java

Backend.class: Backend.java Frontend.class DijkstraGraph.class
	javac Backend.java

DijkstraGraph.class: DijkstraGraph.java BaseGraph.java MapADT.java PlaceholderMap.java
	javac PlaceholderMap.java
	javac MapADT.java
	javac BaseGraph.java
	javac DijkstraGraph.java

App.class: App.java Frontend.class Backend.class DijkstraGraph.class
	javac --module-path ../javafx/lib --add-modules javafx.controls App.java

runApp: App.class
	java --module-path ../javafx/lib --add-modules javafx.controls App

clean: 
	rm *.class