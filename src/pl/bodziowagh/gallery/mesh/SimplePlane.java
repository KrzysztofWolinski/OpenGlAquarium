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
package pl.bodziowagh.gallery.mesh;

import android.graphics.Bitmap;

/**
 * SimplePlane is a setup class for Mesh that creates a plane mesh.
 * 
 * @author Per-Erik Bergman (per-erik.bergman@jayway.com)
 * 
 */
public class SimplePlane extends Mesh {

	private boolean isAlive = true;
	private Bitmap deathBitmap;

	private Bitmap texture1, texture2;
	private boolean isTexture1 = true;

	private boolean isResized = false;
	private float size = 0.5f;

	private boolean isShrinking = false;
	private boolean isEnlarging = false;

	/**
	 * Create a plane with a default with and height of 1 unit.
	 */
	public SimplePlane() {
		this(1, 1);
	}

	/**
	 * Create a plane.
	 * 
	 * @param width
	 *            the width of the plane.
	 * @param height
	 *            the height of the plane.
	 */
	public SimplePlane(float width, float height) {
		// Mapping coordinates for the vertices
		float textureCoordinates[] = { 0.0f, 1.0f, //
				1.0f, 1.0f, //
				0.0f, 0.0f, //
				1.0f, 0.0f, //
		};

		short[] indices = new short[] { 0, 1, 2, 1, 3, 2 };

		float[] vertices = new float[] { -0.5f, -0.5f, 0.0f, 0.5f, -0.5f, 0.0f,
				-0.5f, 0.5f, 0.0f, 0.5f, 0.5f, 0.0f };

		setIndices(indices);
		setVertices(vertices);
		setTextureCoordinates(textureCoordinates);
	}

	public void setDeathBitmap(Bitmap bitmap) {
		this.deathBitmap = bitmap;
	}

	public void killJay() {
		this.isAlive = false;
		this.loadBitmap(this.deathBitmap);
	}

	public boolean isAlive() {
		return this.isAlive;
	}

	public void resize() {
		if (this.isResized) {
			// this.resizeShrink();
			this.isShrinking = true;
		} else {
			// this.resizeEnlarge();
			this.isEnlarging = true;
		}
	}

	@Override
	public void doStuff() {

		if (this.isShrinking == true) {
			if (this.size > 0.5f) {
				this.size -= 0.01;
			} else {
				this.isShrinking = false;
				this.isResized = false;
			}
		} else if (this.isEnlarging == true) {
			if (this.size < 0.9f) {
				this.size += 0.01;
			} else {
				this.isEnlarging = false;
				this.isResized = true;
			}
		}

		float[] vertices = new float[] { -size, -size, 0.0f, size, -size, 0.0f,
				-size, size, 0.0f, size, size, 0.0f };

		setVertices(vertices);
	}

	@Deprecated
	private void resizeEnlarge() {
		float[] vertices = new float[] { -0.8f, -0.8f, 0.0f, 0.8f, -0.8f, 0.0f,
				-0.8f, 0.8f, 0.0f, 0.8f, 0.8f, 0.0f };

		setVertices(vertices);
	}

	@Deprecated
	private void resizeShrink() {
		float[] vertices = new float[] { -0.5f, -0.5f, 0.0f, 0.5f, -0.5f, 0.0f,
				-0.5f, 0.5f, 0.0f, 0.5f, 0.5f, 0.0f };

		setVertices(vertices);
	}

	public void setTextures(Bitmap texture1, Bitmap texture2) {
		this.texture1 = texture1;
		this.texture2 = texture2;
	}

	public void swapTextures() {
		if (this.isTexture1 == true) {
			this.loadBitmap(this.texture2);
			this.isTexture1 = false;
		} else {
			this.loadBitmap(this.texture1);
			this.isTexture1 = true;
		}
	}
}
