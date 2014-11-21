package pl.bodziowagh.gallery;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

class MyGLSurfaceView extends GLSurfaceView {

	private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
	private float mPreviousX;
	private float mPreviousY;

	private OpenGLRenderer renderer;

	public MyGLSurfaceView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public void start(OpenGLRenderer renderer) {
		this.renderer = renderer;
	}

	@Override
	public boolean onTouchEvent(MotionEvent e) {
		float x = e.getX();
		float y = e.getY();

		switch (e.getAction()) {
		case MotionEvent.ACTION_MOVE:

			float dx = x - mPreviousX;
			// float dy = y - mPreviousY;

			// reverse direction of rotation above the mid-line
			/*
			 * if (y > getHeight() / 2) { dx = dx * -1; }
			 * 
			 * // reverse direction of rotation to left of the mid-line if (x <
			 * getWidth() / 2) { dy = dy * -1; }
			 */

			renderer.setAngle(renderer.getAngle()
					+ ((dx /* + dy */) * TOUCH_SCALE_FACTOR));

		}

		mPreviousX = x;
		// mPreviousY = y;
		return true;

	}

	protected OpenGLRenderer getRenderer() {
		return this.renderer;
	}
}
