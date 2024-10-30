import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
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
import org.junit.jupiter.api.BeforeEach;
import org.testfx.framework.junit5.ApplicationTest;

/**
 * Tester class for methods implemented through FrontendInterface
 *
 * Does not test createAllControls() or createAdditionalFeatureControls() as 
 * they only call methods tested already in this class
 */
public class FrontendDeveloperTests extends ApplicationTest {
    /**
     * This method launches the JavaFX application that you would like to test
     * BeforeEach of your @Test methods are run
     */
    @BeforeEach
    public void setup() throws Exception {
	// Sets up the backend for the frontend tests and launches test application
	Frontend.setBackend(new BackendPlaceholder(new GraphPlaceholder()));
	ApplicationTest.launch(Frontend.class);
    }

    @Test
    /**
     * Tests createPathListDisplay and createShortestPathControls button/label
     * functionality/contents
     */
    public void testCreateShortestPath() {
	// Sets up frontend and retrieves buttons and labels
	Frontend front = new Frontend();
	Button find = (Button)(front.scene.lookup("#find"));
        Label path =  (Label)(front.scene.lookup("#path"));
	// Confirms labels and button are properly set
	Assertions.assertEquals("",path.getText());
	Assertions.assertEquals("Submit/Find",find.getText());
    }


    @Test
    /**
     * Tests createTravelTimesBox and checkbox content/functionality
     */
    public void testCreateTravelTimesBox() {
	// Sets up frontend and retrieves checkbox
	Frontend front = new Frontend();
	CheckBox showTimesBox = (CheckBox)(front.scene.lookup("#showTimesBox"));
	// Confirms checkbox content and state
	Assertions.assertTrue(!showTimesBox.isSelected());
	Assertions.assertEquals("Show Travel Times:",showTimesBox.getText());
    }

    @Test
    /**
     * Tests createFindReachableControls and button/label functionality/content
     */
    public void testCreateFindReachableControls() {
	// Sets up backend and retrieves elements
	Frontend front = new Frontend();
	TextField time = (TextField)(front.scene.lookup("#time"));
	Label reachableLabel = (Label)(front.scene.lookup("#reachableLabel"));
	Button search = (Button)(front.scene.lookup("#search"));
	// Confirms content of scene elements from frontend
	Assertions.assertEquals("",time.getText());
	Assertions.assertEquals("Find Locations",search.getText());
	Assertions.assertEquals("",reachableLabel.getText());
    }

    @Test
    /**
     * Tests createAboutAndQuitControls and button functionality/content
     */
    public void testCreatAboutAndQuitControls() {
	// Sets up frontend and retrieves buttons and labels
	Frontend front = new Frontend();
	Button about = (Button)(front.scene.lookup("#about"));
	Button quit = (Button)(front.scene.lookup("#quit"));
	Label aboutInfo = (Label)(front.scene.lookup("#aboutInfo"));
	// Confirms content of scene elements
	Assertions.assertEquals("Select a start and end location to view the shortest path to the destination.\nSelect a start location and time in seconds to view reachable locations.",aboutInfo.getText());
	Assertions.assertEquals("About",about.getText());
	Assertions.assertEquals("Quit",quit.getText());
    }
}
