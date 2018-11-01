package runnables;

import levels.Level;

public class resume implements Runnable {

	private Level level;

	public resume(Level level) {
		this.level = level;
	}

	@Override
	public void run() {
		level.resume();
	}

}
