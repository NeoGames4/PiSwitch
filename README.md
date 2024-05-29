# PiSwitch
### The currently available code is not yet ready for use. The Raspberry Pi part is still missing. More details on usage might be coming sooner or later.
A simple Java API (Java 8) to detect switch toggles and toggle sequences.

## Getting started
1. Install and import the latest release.
2. Install [Pi4J v1.3](https://www.pi4j.com/1.3/install.html).
3. Install [WiringPi](https://github.com/WiringPi/WiringPi) on your Raspberry Pi.

## Example
```java
SwitchDetector detector = new SwitchDetector();
detector.addListener(new SwitchListener() {
  @Override
  public void onSingleClick(SwitchEvent e) {
    // Do something whenever the switch has been toggled
    System.out.println("The switch has been pressed!");
  }
  
  @Override
  public void onClickSequence(ArrayList<SwitchEvent> events) {
    // Do something whenever a switch sequence has been detected
    for(SwitchEvent e : events) {
      System.out.println("At time " + e.time + " the switch has been toggled to " + e.switchPosition + ".");
    }
  }
});
detector.start();
```
