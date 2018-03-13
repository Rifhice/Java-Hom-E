package ui.delegate;

public interface MenuDelegate {
	
	public static enum CONTENT {
			HOME,
			BEHAVIOURS
	}

	public void chanteCurrentContent(CONTENT content);
}
