import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.application.Platform;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.control.ComboBox;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

/**
 * Creates the Frontend for users to interact with
 */
public class Frontend extends Application implements FrontendInterface {

    // Stores element references for later use
    private static BackendInterface back;
    private static Label path;
    private static ComboBox<String> src;
    private static ComboBox<String> dst;
    private static CheckBox showTimesBox;
    public static Pane pane;
    public static Scene scene;

    /**
     * Sets the Backend used by Frontend
     */
    public static void setBackend(BackendInterface back) {
	Frontend.back = back;
    }
    /**
     * Initializes this Frontend
     */
    public void start(Stage stage) {
	Pane root = new Pane();
	this.pane = root;
	// Calls methods to create all elements
	createAllControls(root);
	Scene scene = new Scene(root, 800, 600);
	this.scene = scene;
	// Sets up scene
	stage.setScene(scene);
	stage.setTitle("Path Locator");
	stage.show();
    }
    
    /**
     * Creates all controls in the GUI.
     * @param parent the parent pane that contains all controls
     */
    public void createAllControls(Pane parent) {
	// Calls all other methods to create controls
	createShortestPathControls(parent);
	createPathListDisplay(parent);
	createAdditionalFeatureControls(parent);
	createAboutAndQuitControls(parent);
    }
    /**
     * Creates the controls for the shortest path search.
     * @param parent the parent pane that contains all controls
     */
    public void createShortestPathControls(Pane parent) {
	// Sets up ComboBox for users to see all locations and select start point
	ComboBox<String> src = new ComboBox<String>(FXCollections.observableArrayList(back.getListOfAllLocations()));
	src.setLayoutX(32);
	src.setLayoutY(16);
	parent.getChildren().add(src);
	src.setId("src");
	this.src = src;
	// Sets up ComboBox for users to see all locations and select end point
	ComboBox<String> dst = new ComboBox<String>(FXCollections.observableArrayList(back.getListOfAllLocations()));
	dst.setLayoutX(32);
	dst.setLayoutY(48);
	parent.getChildren().add(dst);
	dst.setId("dst");
	// Sets up Button to submit start and end
	Button find = new Button("Submit/Find");
	find.setId("find");
	find.setLayoutX(32);
	find.setLayoutY(80);
	parent.getChildren().add(find);
	this.dst = dst;

	String srcBox = src.getValue();
	String dstBox = dst.getValue();
	// Shows the results when the button is pressed, also includes time if box is checked
	find.setOnAction(new EventHandler<ActionEvent>() {
		public void handle(ActionEvent event) {
		    try {
		    if(showTimesBox.isSelected()) {
			path.setText(path.getText()+"\n"+back.getTravelTimesOnPath(src.getValue().toString(),dst.getValue().toString()));
		    }
		    else {
			path.setText(back.findShortestPath(srcBox,dstBox).toString());
		    }} catch (Exception e) {
			path.setText("Please select two locations");
		    }
		}
	    });
    }
    /**
     * Creates the controls for displaying the shortest path returned by the search.
     * @param the parent pane that contains all controls
     */
    public void createPathListDisplay(Pane parent) {
	// Sets up the label that will display the path results
	Label path = new Label("");
	path.setId("path");
	this.path = path;
	path.setLayoutX(32);
	path.setLayoutY(112);
	parent.getChildren().add(path);
    }
    /**
     * Creates controls for the two features in addition to the shortest path search.
     * @param parent parent pane that contains all controls
     */
    public void createAdditionalFeatureControls(Pane parent) {
	// Calls additional methods to create controls
	this.createTravelTimesBox(parent);
	this.createFindReachableControls(parent);
    }
    /**
     * Creates the check box to add travel times in the result display.
     * @param parent parent pane that contains all controls
     */
    public void createTravelTimesBox(Pane parent) {
	// Sets CheckBox to select whether or not to show times with path
	CheckBox showTimesBox = new CheckBox("Show Travel Times:");
	showTimesBox.setId("showTimesBox");
	showTimesBox.setLayoutX(200);
	showTimesBox.setLayoutY(80);
	parent.getChildren().add(showTimesBox);
	this.showTimesBox = showTimesBox;
    }
    /**
     * Creates controls to search for all destinations reachable in a given time.
     * @param parent parent pane that contains all controls
     */
    public void createFindReachableControls(Pane parent) {
	// Sets up ComboBox to select the starting location for reachable locations
	ComboBox<String> start = new ComboBox<String>(FXCollections.observableArrayList(back.getListOfAllLocations()));
	start.setLayoutX(32);
	start.setLayoutY(176);
	parent.getChildren().add(start);
	this.src = src;
	// Sets up TextField for users to input a time for reachable locations
        TextField time = new TextField();
	time.setLayoutX(32);
	time.setLayoutY(208);
	parent.getChildren().add(time);
	time.setId("time");
	// Sets up Button to show results for reachable locations
	Button search = new Button("Find Locations");
	search.setLayoutX(32);
	search.setLayoutY(240);
	parent.getChildren().add(search);
	search.setId("search");
	// Sets up label to show results of reachable locations
	Label reachableLabel = new Label("");
	reachableLabel.setLayoutX(32);
	reachableLabel.setLayoutY(272);
	parent.getChildren().add(reachableLabel);
	reachableLabel.setId("reachableLabel");
	// Shows results of reachable controls when button is clicked
	// Shows error if time sent was not valid
	search.setOnAction(new EventHandler<ActionEvent>() {
		public void handle(ActionEvent event) {
		    try {
			Double dTime = Double.parseDouble(time.getText());
			reachableLabel.setText(back.getReachableLocations(start.getValue(),dTime).toString());
		    } catch (Exception e) {
			reachableLabel.setText("Invalid time");
		    }
		}
	    });
	
    }
    /**
     * Creates an about and quit button.
     * @param parent parent pane that contains all controls
     */
    public void createAboutAndQuitControls(Pane parent) {
	// Creates button to display app information
	Button about = new Button("About");
	about.setLayoutX(32);
	about.setLayoutY(560);
	parent.getChildren().add(about);
	about.setId("about");
	// Sets up label to describe app functionality
	Label aboutInfo = new Label("Select a start and end location to view the shortest path to the destination.\nSelect a start location and time in seconds to view reachable locations.");
	aboutInfo.setLayoutX(32);
	aboutInfo.setLayoutY(336);
	aboutInfo.setVisible(false);
	parent.getChildren().add(aboutInfo);
	aboutInfo.setId("aboutInfo");
	// Sets up button for users to quit the app
	Button quit = new Button("Quit");
	quit.setLayoutX(96);
	quit.setLayoutY(560);
	parent.getChildren().add(quit);
	quit.setId("quit");
	// Quits the application when the quit button is pressed
	quit.setOnAction(new EventHandler<ActionEvent>() {
		public void handle(ActionEvent event) {
		    Platform.exit();
		}
	    });
	// Shows information about the app when the about button is pressed
	about.setOnAction(new EventHandler<ActionEvent>() {
		public void handle(ActionEvent event) {
		    aboutInfo.setVisible(true);
		}
	    });
    }
}
