package API;

import java.util.ArrayList;

/**
 * A switch listener for events detected by the {@link API.SwitchDetector}-class.
 * @author Mika Thein
 * @see #onSingleClick(SwitchEvent)
 * @see #onClickSequence(ArrayList)
 * @see API.SwitchDetector
 * @see API.SwitchEvent
 */
public interface SwitchListener {
	
	/**
	 * Whenever the switch has been pressed.<br>
	 * <b>See {@link API.SwitchDetector#start()} for details.</b>
	 * @param e the SwitchEvent, storing information on the switch event.
	 * @see #onClickSequence(ArrayList)
	 */
	void onSingleClick(SwitchEvent e);
	
	/**
	 * Whenever an input sequence has been detected. (Multiple toggles in a row.)<br>
	 * <b>See {@link API.SwitchDetector#start()} for details.</b>
	 * @param e an array of SwitchEvents, storing information on each event.
	 * @see #onSingleClick(SwitchEvent)
	 */
	void onClickSequence(ArrayList<SwitchEvent> e);

}
