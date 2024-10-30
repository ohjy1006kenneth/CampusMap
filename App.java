import javafx.application.Application;

public class App {
  public static void main(String[] args) {
    Backend back = new Backend(new DijkstraGraph<>());
    try {
      back.loadGraphData("campus.dot");
    } catch (Exception e){
      
    }
    
    Frontend.setBackend(back);
    Application.launch(Frontend.class, args);
  }
}
