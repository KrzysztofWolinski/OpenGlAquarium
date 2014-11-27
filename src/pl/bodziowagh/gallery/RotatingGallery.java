package pl.bodziowagh.gallery;

import java.util.ArrayList;
import java.util.List;

import pl.bodziowagh.gallery.mesh.SimplePlane;
import pl.bodziowagh.gallery.mesh.builder.SimplePlaneBuilder;
import se.jayway.opengl.tutorial.R;
import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class RotatingGallery extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Remove the title bar from the window.
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		// Make the windows into full screen mode.
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// Create a OpenGL view.
		MyGLSurfaceView view = new MyGLSurfaceView(this);

		// Creating and attaching the renderer.
		OpenGLRenderer renderer = new OpenGLRenderer(
				BitmapFactory.decodeResource(getResources(), R.drawable.jay),
				BitmapFactory.decodeResource(getResources(), R.drawable.jay2));
		view.setRenderer(renderer);
		setContentView(view);

		view.start(renderer);

		List<SimplePlane> planeList = new ArrayList<SimplePlane>();

		int listSize = 9;
		float radius = 2f;

		for (int i = 0; i < listSize; i++) {
			double x = Math.sin((2 * Math.PI) * i / listSize) * radius;
			double z = Math.cos((2 * Math.PI) * i / listSize) * radius;
			double y = 0;

			planeList.add(new SimplePlaneBuilder(1, 1)
					.withX((float) x)
					.withY((float) y)
					.withZ((float) z)
					.withInitialRotation(0, 0, 0)
					.withBitmap(
							BitmapFactory.decodeResource(getResources(),
									R.drawable.jay))
					.withDeathBitmap(
							BitmapFactory.decodeResource(getResources(),
									R.drawable.dead_jay)).build());

			renderer.addMesh(planeList.get(i));
		}

	}
}