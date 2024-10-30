// === CS400 Spring 2024 File Header Information ===
// Name: Kenneth Oh
// Email: oh87@wisc.edu
// Lecturer: Gary
// Notes to Grader: <optional extra notes>

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.testfx.framework.junit5.ApplicationTest;
import org.junit.jupiter.api.Assertions;

import javafx.application.Application;
import javafx.stage.Window;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;



/**
* Class to hold JUnit tests for the BackendImplementation class
*/
public class BackendDeveloperTests extends ApplicationTest {

    /**
     * Test functionality of loadGraphData()
     */
    @Test
    void testLoadGraphData() {
        GraphADT<String, Double> graph = new DijkstraGraph<>();
        Backend backend = new Backend(graph);

        Assertions.assertThrows(IOException.class, () -> backend.loadGraphData("cam.dot"));

        try {
            backend.loadGraphData("campus.dot");
        } catch (IOException e) {
            Assertions.fail();
        }
    }

    /**
     * Test functionality of getListOfAllLocations()
     */
    @Test
    void testGetListOfAllLocations() {
        GraphADT<String, Double> graph = new GraphPlaceholder();
        Backend backend = new Backend(graph);
        
        try {
            backend.loadGraphData("campus.dot");
        } catch (IOException e) {
        }

        List<String> allLocations = backend.getListOfAllLocations();
        Assertions.assertTrue(Arrays.toString(allLocations.toArray()).contains("Union South"));
        Assertions.assertTrue(Arrays.toString(allLocations.toArray()).contains("Computer Sciences and Statistics"));
        Assertions.assertTrue(Arrays.toString(allLocations.toArray()).contains("Atmospheric, Oceanic and Space Sciences"));
    }

    /**
     * Test functionality of findShortestPath()
     */
    @Test
    void testFindShortestPath() {
        GraphADT<String, Double> graph = new GraphPlaceholder();
        Backend backend = new Backend(graph);

        List<String> shortestPaths = backend.findShortestPath("start", "end");

        Assertions.assertTrue(Arrays.toString(shortestPaths.toArray()).contains("Union South"));
        Assertions.assertTrue(Arrays.toString(shortestPaths.toArray()).contains("Computer Sciences and Statistics"));
        Assertions.assertTrue(Arrays.toString(shortestPaths.toArray()).contains("Atmospheric, Oceanic and Space Sciences"));
    }

    /**
     * Test functionality of getTravelTimesOnPath()
     */
    @Test
    void testGetTravelTimesOnPath() {
        GraphADT<String, Double> graph = new GraphPlaceholder();
        Backend backend = new Backend(graph);

        List<Double> cost = backend.getTravelTimesOnPath("start", "end");

        Assertions.assertEquals(cost.get(0), 303.2);
        Assertions.assertEquals(cost.get(1), 303.2);
    }

    /**
     * Test functionality of getReachableLocations()
     */
    @Test
    void testGetReachableLocations() {
        GraphADT<String, Double> graph = new GraphPlaceholder();
        Backend backend = new Backend(graph);

        try {
            backend.loadGraphData("campus.dot");
        } catch (IOException e) {
        }

        List<String> reachableLocations = backend.getReachableLocations("Memorial Union", 303.3);
        Assertions.assertTrue(reachableLocations.isEmpty());
    }

    /**
     * Test functionality of when viewing shortest path to the destination
     */
    @Test
    void IntegrationTest1() {
        Backend back = new Backend(new DijkstraGraph<>());
        try {
            back.loadGraphData("campus.dot");
        } catch (Exception e) {
        }

        Frontend.setBackend(back);

        try {
            ApplicationTest.launch(Frontend.class);
        } catch (Exception e) {
        }
        

        Label path =  lookup("#path").query();
        ComboBox src = lookup("#src").query();
        ComboBox dst = lookup("#dst").query();
        CheckBox showTimesBox = lookup("#showTimesBox").query();
        Button find = lookup("#find").query();
    
        src.getSelectionModel().select(0);
        dst.getSelectionModel().select(1);
        clickOn("#showTimesBox");
        clickOn("#find");


        //105.8
        Assertions.assertEquals("Please select two locations", path.getText());
    }

    /**
     * Test functionality of getReachableLocations
     */
    @Test
    void IntegrationTest2() {
        Backend back = new Backend(new DijkstraGraph<>());
        try {
            back.loadGraphData("campus.dot");
        } catch (Exception e) {
        }

        Frontend front = new Frontend();
        front.setBackend(back);

        ComboBox start = (ComboBox)(front.pane.lookup("#src"));
        TextField time = (TextField)(front.scene.lookup("#time"));
        Button search = (Button)(front.scene.lookup("#search"));
        Label reachableLabel = (Label)(front.scene.lookup("#reachableLabel"));

        start.getSelectionModel().select(0);
        clickOn("#time");
        write("200");
        
        Assertions.assertEquals("Locations", reachableLabel.getText());
    }

    /**
     * Test if it outpus invalid time when provide wrong time input
     */
    @Test
    void PartnerTest1() {
        Frontend.setBackend(new BackendPlaceholder(new GraphPlaceholder()));

        Button find = lookup("#find").query();
	    Assertions.assertEquals("Submit/Find",find.getText());
    }

    /**
     * Test functionality of getReachableLocations
     */
    @Test
    void PartnerTest2() {
        GraphADT<String, Double> graph = new GraphPlaceholder();
        BackendInterface back = new BackendPlaceholder(graph);
        Frontend front = new Frontend();
        front.setBackend(back);

        ComboBox start = (ComboBox)(front.pane.lookup("#src"));
        TextField time = (TextField)(front.scene.lookup("#time"));
        Button search = (Button)(front.scene.lookup("#search"));
        Label reachableLabel = (Label)(front.scene.lookup("#reachableLabel"));

        start.getSelectionModel().select(0);
        clickOn("#time");
        write("");
        
        Assertions.assertEquals("Locations", reachableLabel.getText());

    }
}