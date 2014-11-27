package pl.bodziowagh.gallery.enums;

public enum ScreenState {

	IDLE("idle"), SWIPING("swiping");

	private String value;

	private ScreenState(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
