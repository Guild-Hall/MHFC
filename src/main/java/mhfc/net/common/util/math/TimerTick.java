package mhfc.net.common.util.math;

/**
 * This is a timer that can be used to easily animate models between poses. You have to set the number of ticks between
 * poses, increase or decrease the timer, and get the percentage using a specific function.
 *
 * @author RafaMv
 */
public class TimerTick {

	private int timer;

	private int duration;

	private int timerChange;

	public TimerTick(int dur) {
		timer = 0;
		duration = dur;
	}
	public void setTickDuration(int dur) {
		timer = 0;
		duration = dur;
	}

	public int getTimer() {
		return timer;
	}

	public void setTimer(int time) {
		timer = time;

		if (timer > duration) {
			timer = duration;
		} else if (timer < 0) {
			timer = 0;
		}
	}

	public void resetTickTimer() {
		timer = 0;
	}
	public void increaseTickTimer() {
		if (timer < duration) {
			timer++;
			setTimerChange(1);
		}
	}

	public boolean ifCanIncreaseTickTimer() {
		return timer < duration;
	}

	public void increaseTickTimer(int time) {
		int newTime = timer + time;
		if (newTime <= duration && newTime >= 0) {
			timer = newTime;
		} else {
			timer = newTime < 0 ? 0 : duration;
		}
	}

	public void decreaseTimer() {
		if (timer > 0.0D) {
			timer--;
			setTimerChange(-1);
		}
	}

	public boolean canDecreaseTimer() {
		return timer > 0.0D;
	}

	public void decreaseTimer(int tim) {
		if (timer - tim > 0.0D) {
			timer -= tim;
		} else {
			timer = 0;
		}
	}
	public int getTimerChange() {
		return timerChange;
	}
	public void setTimerChange(int timerChange) {
		this.timerChange = timerChange;
	}

}
