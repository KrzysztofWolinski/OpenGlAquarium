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

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import pl.bodziowagh.gallery.mesh.Group;
import pl.bodziowagh.gallery.mesh.Mesh;
import pl.bodziowagh.gallery.mesh.SimplePlane;
import android.graphics.Bitmap;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;

public class OpenGLRenderer implements Renderer {
	private final Group root;

	private float mAngle = 0;
	private float angleDifference = 0;
	private final float maxAngleDifference = 10;
	private boolean isScreenTouched = false;

	private final Bitmap texture1, texture2;
	int textureCounter = 0;

	public OpenGLRenderer(Bitmap texture1, Bitmap texture2) {
		// Initialize our root.
		Group group = new Group();
		root = group;

		this.texture1 = texture1;
		this.texture2 = texture2;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.opengl.GLSurfaceView.Renderer#onSurfaceCreated(javax.microedition
	 * .khronos.opengles.GL10, javax.microedition.khronos.egl.EGLConfig)
	 */
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// Set the background color to black ( rgba ).
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
		// Enable Smooth Shading, default not really needed.
		gl.glShadeModel(GL10.GL_SMOOTH);
		// Depth buffer setup.
		gl.glClearDepthf(1.0f);
		// Enables depth testing.
		gl.glEnable(GL10.GL_DEPTH_TEST);
		// The type of depth testing to do.
		gl.glDepthFunc(GL10.GL_LEQUAL);
		// Really nice perspective calculations.
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.opengl.GLSurfaceView.Renderer#onDrawFrame(javax.microedition.
	 * khronos.opengles.GL10)
	 */
	public void onDrawFrame(GL10 gl) {
		// Clears the screen and depth buffer.
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		// Replace the current matrix with the identity matrix
		gl.glLoadIdentity();

		// Translates 5 units into the screen, and rotating it a little bit
		gl.glRotatef(20, 1, 0, 0);
		gl.glTranslatef(0, 0, -5);

		// Update angle
		final float slowdownRate = 0.1f;
		if ((isScreenTouched == false) && (Math.abs(mAngle) > 0)) {
			if (angleDifference > 0) {
				angleDifference -= slowdownRate;
			} else {
				angleDifference += slowdownRate;
			}

			if (Math.abs(angleDifference) <= slowdownRate) {
				angleDifference = 0;
			}
		}
		mAngle += angleDifference;

		textureCounter++;

		// Rotating the whole scene (objects)
		gl.glRotatef(mAngle, 0, 1, 0);

		// Rotating every object so they're visible to the spectator
		for (int i = 0; i < root.size(); i++) {
			root.get(i).ry = -mAngle;

			if (((SimplePlane) root.get(i)).isAlive()) {
				if ((textureCounter % 10 == 0) && (textureCounter % 20 == 0)) {
					root.get(i).loadBitmap(this.texture1);
				} else if ((textureCounter % 10 == 0)
						&& (textureCounter % 20 != 0)) {
					root.get(i).loadBitmap(this.texture2);
				}
			}
		}

		gl.glTranslatef(0, -1.5f, 0);

		// Draw our scene.
		root.draw(gl);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.opengl.GLSurfaceView.Renderer#onSurfaceChanged(javax.microedition
	 * .khronos.opengles.GL10, int, int)
	 */
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// Sets the current view port to the new size.
		gl.glViewport(0, 0, width, height);
		// Select the projection matrix
		gl.glMatrixMode(GL10.GL_PROJECTION);
		// Reset the projection matrix
		gl.glLoadIdentity();
		// Calculate the aspect ratio of the window
		GLU.gluPerspective(gl, 45.0f, (float) width / (float) height, 0.1f,
				1000.0f);
		// Select the modelview matrix
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		// Reset the modelview matrix
		gl.glLoadIdentity();
	}

	/**
	 * Adds a mesh to the root.
	 * 
	 * @param mesh
	 *            the mesh to add.
	 */
	public void addMesh(Mesh mesh) {
		root.add(mesh);
	}

	public float getAngle() {
		return mAngle;
	}

	/**
	 * Sets the rotation angle of the triangle shape (mTriangle).
	 */
	public void setAngle(float angle) {
		mAngle = angle;
	}

	public void setAngleDifference(float angleDifference) {
		if (Math.abs(angleDifference) > maxAngleDifference) {
			this.angleDifference = (angleDifference / Math.abs(angleDifference))
					* maxAngleDifference;
		} else {
			this.angleDifference = angleDifference;
		}
	}

	public void setScreenTouched(boolean isScreenTouched) {
		this.isScreenTouched = isScreenTouched;
	}

	public void clickOccured(float x, float y) {
		int closestPlaneIndex = 0;

		float angle = mAngle % 360;
		while (angle < 0) {
			angle += 360;
		}

		float pizzaPiece = 360 / root.size();

		closestPlaneIndex = (root.size() - ((int) (angle / pizzaPiece)))
				% root.size();

		if (closestPlaneIndex >= 0 && closestPlaneIndex < 9) {
			((SimplePlane) root.get(closestPlaneIndex)).killJay();
		}
	}
}
