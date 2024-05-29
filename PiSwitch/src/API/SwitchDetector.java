package API;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * A detector that measures changes of the switch position and notifies listeners.<br>
 * {@link #start()} must be executed once to start the measurement process.<br>
 * See {@link #start()} for details on the measurement process and different listeners.<br>
 * Use {@link #addListener(SwitchListener)} to add a {@link API.SwitchListener}.
 * @author Mika Thein
 * @see #addListener(SwitchListener)
 * @see #start()
 * @see API.SwitchListener
 * @see API.SwitchEvent
 */
public class SwitchDetector {
	
	// Listeners
	
	private ArrayList<SwitchListener> listeners = new ArrayList<>();
	
	/**
	 * Adds a SwitchListener to the detector. The switch detector must be started using {@link #start()} to detect any events.
	 * @param listener the listener.
	 * @see #removeListener(SwitchListener)
	 * @see #removeAllListeners()
	 * @see #start()
	 * @see #isRunning()
	 * @see #stop()
	 */
	public void addListener(SwitchListener listener) {
		listeners.add(listener);
	}
	
	/**
	 * Removes the given listener.
	 * @param listener the listener.
	 * @return Whether the removal has been successful. (See {@link java.util.ArrayList#remove(Object)}.)
	 * @see #addListener(SwitchListener)
	 * @see #removeAllListeners()
	 * @see #start()
	 * @see #isRunning()
	 * @see #stop()
	 */
	public boolean removeListener(SwitchListener listener) {
		return listeners.remove(listener);
	}
	
	/**
	 * Removes all listeners.
	 * @param listener the listener.
	 * @see #addListener(SwitchListener)
	 * @see #removeListener(SwitchListener)
	 * @see #start()
	 * @see #isRunning()
	 * @see #stop()
	 */
	public void removeAllListeners() {
		listeners.clear();
	}
	
	// Running process
	
	private ScheduledExecutorService thread = null;
	
	private ArrayList<SwitchEvent> currentEvents = new ArrayList<>();
	
	private SwitchEvent lastEvent = new SwitchEvent(0, 0, SwitchEvent.SWITCH_NAN);
	
	private long waitingTime = 500;
	
	/**
	 * How long the detector should wait (in milliseconds) between each measurement.
	 * @see #getWaitingTime()
	 */
	public static final long PRECISION = 10;
	
	/**
	 * Starts the measurement-process.
	 * The detector waits {@link #PRECISION} = {@value #PRECISION} milliseconds between each measurement.
	 * If any update on the switch position has been detected, all listeners are going to be notified.
	 * Currently there are two different scenarios.
	 * 
	 * <ul>
	 * <li><b>Single click</b> ({@link API.SwitchListener#onSingleClick(SwitchEvent)}) is triggered whenever any update on the switch position has been detected.</li>
	 * <li><b>Click sequence</b> ({@link API.SwitchListener#onClickSequence(ArrayList)}) is triggered whenever {@link #getWaitingTime()} milliseconds passed since the last click.</li>
	 * </ul>
	 * 
	 * This method does nothing if {@link #isRunning()} is true.
	 * @see #stop()
	 * @see #isRunning()
	 */
	public void start() {
		if(!isRunning()) {
			thread = Executors.newScheduledThreadPool(1);
			thread.scheduleAtFixedRate(new Runnable() {
				@Override
				public void run() {
					boolean input = false; // TODO
					
					// If the switch has been pressed
					if(input) {
						// Log the current event
						long time = System.currentTimeMillis();
						lastEvent = new SwitchEvent(time, time-lastEvent.time, SwitchEvent.SWITCH_NAN);
						currentEvents.add(lastEvent);
						// Notify listeners
						for(SwitchListener l : listeners) {
							new Thread() {
								@Override
								public void run() {
									l.onSingleClick(lastEvent);
								}
							};
						}
					}
					
					// Whether currentEvents should be send as input
					else if(currentEvents.size() > 0 && System.currentTimeMillis() - lastEvent.time > waitingTime) {
						// Notify listeners
						for(SwitchListener l : listeners) {
							new Thread() {
								@Override
								public void run() {
									l.onClickSequence(new ArrayList<>(currentEvents));
								}
							};
						}
						// Clear current events
						currentEvents.clear();
					}
				}
			}, 0, PRECISION, TimeUnit.MILLISECONDS);
		}
	}
	
	/**
	 * Stops the current measurement process.<br>
	 * Does nothing if {@link #isRunning()} is already false.
	 * @see #start()
	 * @see #isRunning()
	 */
	public void stop() {
		if(isRunning()) {
			thread.shutdown();
		}
	}
	
	/**
	 * @return whether a current measurement process is running.
	 * @see #start()
	 * @see #stop()
	 */
	public boolean isRunning() {
		return thread == null || thread.isTerminated();
	}
	
	/**
	 * Sets the current waiting time. Needs to be greater than the {@link #PRECISION} = {@value #PRECISION}.
	 * @param waitingTime the new waiting time in milliseconds.
	 * @return whether the new waiting time is greater than {@link #PRECISION} and has been accepted.
	 * @see #getWaitingTime()
	 * @see #PRECISION
	 */
	public boolean setWaitingTime(long waitingTime) {
		if(waitingTime > PRECISION) {
			this.waitingTime = waitingTime;
		} return false;
	}
	
	/**
	 * See {@link #start()} for details on the waiting time.
	 * @return the current waiting time that needs to pass without a switch toggle to recognize an input sequence.
	 * @see #setWaitingTime(long)
	 * @see #PRECISION
	 */
	public long getWaitingTime() {
		return waitingTime;
	}
	
	/**
	 * Returns the latest switch event.
	 * If no switch event has been detected yet, this method will return a SwitchEvent with {@link API.SwitchEvent#time} = 0, {@link API.SwitchEvent#timeDelta} = 0 and {@link API.SwitchEvent#switchPosition} = {@link API.SwitchEvent#SWITCH_NAN}.
	 * @return the latest switch event.
	 */
	public SwitchEvent getLatestSwitchEvent() {
		return lastEvent;
	}

}
