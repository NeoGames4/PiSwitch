package API;

/**
 * A class to store information on the switch after it has been pressed.
 * @author Mika Thein
 * @see #time
 * @see #timeDelta
 * @see #switchPosition
 * @see #SwitchEvent(long, long, int)
 * @see API.SwitchDetector
 * @see API.SwitchListener
 */
public class SwitchEvent {
	
	/**
	 * If the switch position is unknown or if there is no fitting category.
	 * @see #SWITCH_DOWN
	 * @see #SWITCH_UP
	 * @see #switchPosition
	 */
	public static final int SWITCH_NAN = -1;
	
	/**
	 * If the lower switch half is pressed down.
	 * @see #SWITCH_UP
	 * @see #SWITCH_NAN
	 * @see #switchPosition
	 */
	public static final int SWITCH_DOWN = 1;
	
	/**
	 * If the upper switch half is pressed down.
	 * @see #SWITCH_DOWN
	 * @see #SWITCH_NAN
	 * @see #switchPosition
	 */
	public static final int SWITCH_UP = 0;
	
	/**
	 * The time in epoch milliseconds when the switch has been pressed.
	 * @see #timeDelta
	 * @see #switchPosition
	 */
	public final long time;
	
	/**
	 * The time in milliseconds since the switch has been previously pressed.
	 * @see #time
	 * @see #switchPosition
	 */
	public final long timeDelta;
	
	/**
	 * The position of the switch after it has been pressed.
	 * @see #SWITCH_DOWN
	 * @see #SWITCH_UP
	 * @see #SWITCH_NAN
	 * @see #time
	 * @see #timeDelta
	 */
	public final int switchPosition;
	
	/**
	 * Initiates a new SwitchEvent.
	 * @param time see {@link #time}.
	 * @param timeDelta see {@link #timeDelta}.
	 * @param switchPosition see {@link #switchPosition}.
	 */
	public SwitchEvent(long time, long timeDelta, int switchPosition) {
		this.time = time;
		this.timeDelta = timeDelta;
		this.switchPosition = switchPosition;
	}

}
