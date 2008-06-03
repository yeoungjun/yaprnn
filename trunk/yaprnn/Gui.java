package yaprnn.gui;

public class GUI {
	private static GUI gui = new GUI();
	private View view = new View();
	private Controller controller = new Controller();
	
	private GUI() {
    }
	public static void init(){
	}
	public static GUI getGUI() {
		return gui;
	}
	
    public static void main(String[] args){
    	GUI g = GUI.getGUI();
    }
}