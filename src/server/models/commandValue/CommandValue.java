package server.models.argument;

import java.util.ArrayList;

import org.json.JSONObject;

public abstract class Argument {


	protected String name;
	protected String description;
	
	public Argument(String name,String description) {
		this.name = name;
		this.description = description;
	}
}
