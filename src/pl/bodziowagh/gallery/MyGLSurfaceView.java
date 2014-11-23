package pl.bodziowagh.gallery;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

class MyGLSurfaceView extends GLSurfaceView {

	private final float TOUCH_SCALE_FACTOR = 180.0f / 400;
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
			renderer.setScreenTouched(true);
			float dx = x - mPreviousX;

			if (Math.abs(dx) < 1) {
				this.clickEvent(x, y);
			} else {
				this.swipeEvent(dx);
			}

		case MotionEvent.ACTION_CANCEL:
			renderer.setScreenTouched(false);
		}

		mPreviousX = x;
		return true;
	}

	private void swipeEvent(float dx) {
		renderer.setAngleDifference((dx * TOUCH_SCALE_FACTOR));
	}

	private void clickEvent(float x, float y) {
		this.renderer.clickOccured(x, y);
	}

	protected OpenGLRenderer getRenderer() {
		return this.renderer;
	}
}
