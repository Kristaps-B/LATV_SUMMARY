import model.SentenceComparison;
import model.SummModel;
import model.WordComparison;
import View.SummView;
import Controller.SummController;

public class SummMain {
	public static void main(String[] args) {
		// GUI gui = new GUI();
		// gui.createGUI();
		
	
		new SummController(new SummView(), new SummModel());

	}
}
