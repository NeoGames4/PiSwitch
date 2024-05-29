package Main;

import java.util.ArrayList;

import API.SwitchDetector;
import API.SwitchEvent;
import API.SwitchListener;

public class Launcher {
	
	public static void main(String[] args) {
		SwitchDetector detector = new SwitchDetector();
		detector.addListener(new SwitchListener() {
			@Override
			public void onSingleClick(SwitchEvent e) {
				// Do something
			}
			
			@Override
			public void onClickSequence(ArrayList<SwitchEvent> e) {
				// Do something
			}
		});
		detector.start();
	}

}
