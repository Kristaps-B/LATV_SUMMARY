import View.SummView;
import Controller.SummController;
import Model.SummModel;

public class SummMain {
	public static void main(String[] args) {
		// GUI gui = new GUI();
		// gui.createGUI();

		new SummController(new SummView(), new SummModel());

	}
}
