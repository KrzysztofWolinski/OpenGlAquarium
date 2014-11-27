package pl.bodziowagh.gallery;

import pl.bodziowagh.gallery.enums.ScreenState;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

class MyGLSurfaceView extends GLSurfaceView {

	private final float TOUCH_SCALE_FACTOR = 180.0f / 400;
	private float mPreviousX;

	private ScreenState screenState;

	private OpenGLRenderer renderer;

	public MyGLSurfaceView(Context context) {
		super(context);
		this.screenState = ScreenState.IDLE;
	}

	public void start(OpenGLRenderer renderer) {
		this.renderer = renderer;
	}

	@Override
	public boolean onTouchEvent(MotionEvent e) {
		float x = e.getX();
		float y = e.getY();

		switch (e.getAction()) {
		case MotionEvent.ACTION_UP:
			if (this.screenState != ScreenState.SWIPING) {
				if (this.screenState == ScreenState.BLOCKED) {
					this.screenState = ScreenState.IDLE;
				} else {
					this.screenState = ScreenState.BLOCKED;
				}
				this.clickEvent(x, y);
			} else {
				this.screenState = ScreenState.IDLE;
			}
			renderer.setScreenTouched(false);
			break;
		case MotionEvent.ACTION_MOVE:
			if (this.screenState != ScreenState.BLOCKED) {
				renderer.setScreenTouched(true);
				float dx = x - mPreviousX;

				this.screenState = ScreenState.SWIPING;
				this.swipeEvent(dx);
			}
			break;
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
