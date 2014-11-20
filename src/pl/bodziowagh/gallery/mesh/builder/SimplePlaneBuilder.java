package pl.bodziowagh.gallery.mesh.builder;

import pl.bodziowagh.gallery.mesh.SimplePlane;
import android.graphics.Bitmap;

public class SimplePlaneBuilder {
	SimplePlane simplePlane = null;

	public SimplePlaneBuilder(float width, float height) {
		simplePlane = new SimplePlane(width, height);
	}

	public SimplePlaneBuilder withX(float x) {
		simplePlane.x = x;
		return this;
	}

	public SimplePlaneBuilder withY(float y) {
		simplePlane.y = y;
		return this;
	}

	public SimplePlaneBuilder withZ(float z) {
		simplePlane.z = z;
		return this;
	}

	public SimplePlaneBuilder withInitialRotation(float rx, float ry, float rz) {
		simplePlane.rx = rx;
		simplePlane.ry = ry;
		simplePlane.rz = rz;
		return this;
	}

	public SimplePlaneBuilder withBitmap(Bitmap bitmap) {
		simplePlane.loadBitmap(bitmap);
		return this;
	}

	public SimplePlane build() {
		return simplePlane;
	}
}
