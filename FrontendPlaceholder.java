import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class FrontendPlaceholder extends Application implements FrontendInterface {

  private static BackendPlaceholder back;

  public static void setBackend(BackendPlaceholder back) {
    FrontendPlaceholder.back = back;
  }

  public void start(Stage stage) {
    Pane root = new Pane();

    createAllControls(root);

    Scene scene = new Scene(root, 800, 600);
    stage.setScene(scene);
    stage.setTitle("P2: Prototype");
    stage.show();
  }

  public void createAllControls(Pane parent) {
    createShortestPathControls(parent);
    createPathListDisplay(parent);
    createAdditionalFeatureControls(parent);
    createAboutAndQuitControls(parent);
  }

  public void createShortestPathControls(Pane parent) {
    Label src = new Label("Path Start Selector:  Memorial Union");
    src.setLayoutX(32);
    src.setLayoutY(16);
    parent.getChildren().add(src);

    Label dst = new Label("Path End Selector: Computer Science");
    dst.setLayoutX(32);
    dst.setLayoutY(48);
    parent.getChildren().add(dst);

    Button find = new Button("Submit/Find Button");
    find.setLayoutX(32);
    find.setLayoutY(80);
    parent.getChildren().add(find);
  }

  public void createPathListDisplay(Pane parent) {
    Label path =
        new Label(
            "Results List: \n\tMemorial Union\n\tSciene Hall\n\tPyschology\n\tComputer Science" +
            "\n\n" +
            "Results List (with travel times):\n\tMemorial Union\n\t-(30sec)->Science Hall\n\t-(170sec)->Psychology\n\t-(45sec)->Computer Science\n\tTotal time: 4.08min"
            );
    path.setLayoutX(32);
    path.setLayoutY(112);
    parent.getChildren().add(path);
  }

  public void createAdditionalFeatureControls(Pane parent) {
    this.createTravelTimesBox(parent);
    this.createFindReachableControls(parent);
  }

  public void createTravelTimesBox(Pane parent) {
    CheckBox showTimesBox = new CheckBox("Show Walking Times");
    showTimesBox.setLayoutX(200);
    showTimesBox.setLayoutY(80);
    parent.getChildren().add(showTimesBox);
  }

  public void createFindReachableControls(Pane parent) {
    Label locationSelector = new Label("Location Selector:  Memorial Union");
    locationSelector.setLayoutX(500);
    locationSelector.setLayoutY(16);
    parent.getChildren().add(locationSelector);
    Label timeSelector = new Label("Time Selector:  200sec");
    timeSelector.setLayoutX(500);
    timeSelector.setLayoutY(48);
    parent.getChildren().add(timeSelector);
    Button findLocations = new Button("Find Locations");
    findLocations.setLayoutX(500);
    findLocations.setLayoutY(80);
    parent.getChildren().add(findLocations);
    Label reachableLabel = new Label("Locations in 200sec walking distance:\n\tUnion South\n\tComputer Science");
    reachableLabel.setLayoutX(500);
    reachableLabel.setLayoutY(112);
    parent.getChildren().add(reachableLabel);
  }

  public void createAboutAndQuitControls(Pane parent) {
    Button about = new Button("About");
    about.setLayoutX(32);
    about.setLayoutY(560);
    parent.getChildren().add(about);

    Button quit = new Button("Quit");
    quit.setLayoutX(96);
    quit.setLayoutY(560);
    parent.getChildren().add(quit);
  }
}
