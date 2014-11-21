/**
 * Copyright 2010 Per-Erik Bergman (per-erik.bergman@jayway.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

/**
 * This class is the setup for the Tutorial part VI located at:
 * http://blog.jayway.com/
 * 
 * @author Per-Erik Bergman (per-erik.bergman@jayway.com)
 * 
 */
public class TutorialPartVI extends Activity {

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
									R.drawable.jay)).build());

			renderer.addMesh(planeList.get(i));
		}

	}
}