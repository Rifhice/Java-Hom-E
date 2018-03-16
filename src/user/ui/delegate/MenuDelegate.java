package user.ui.delegate;

public interface MenuDelegate {
	
	public static enum CONTENT {
			HOME,
			CONTROLS,
			SENSORS,
			ACTUATORS,
			BEHAVIOURS_COMMANDS,
			AMBIENCES,
			CATEGORIES,
			ACCOUNTS;
		
		  @Override
		  public String toString() {
		    switch(this) {
		      case HOME: return "Home";
		      case CONTROLS: return "Controls";
		      case SENSORS: return "Sensors";
		      case ACTUATORS: return "Actuators";
		      case BEHAVIOURS_COMMANDS: return "Behaviours/Commands";
		      case AMBIENCES: return "Ambiences";
		      case CATEGORIES: return "Categories";
		      case ACCOUNTS: return "Accounts";
		      default: return this.name();
		    }
		  }
	}

	public void chanteCurrentContent(CONTENT content);
}
