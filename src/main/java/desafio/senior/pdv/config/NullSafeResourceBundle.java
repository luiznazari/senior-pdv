package desafio.senior.pdv.config;

import java.util.Enumeration;
import java.util.ResourceBundle;

public class NullSafeResourceBundle extends ResourceBundle {
	
	private ResourceBundle delegate;
	
	NullSafeResourceBundle() {
		this.delegate = ResourceBundle.getBundle("messages");
	}
	
	public static NullSafeResourceBundle getInstance() {
		return new NullSafeResourceBundle();
	}
	
	@Override
	protected Object handleGetObject(String key) {
		if (this.delegate.containsKey(key)) {
			return this.delegate.getString(key);
		} else {
			return "[" + key + "]";
		}
	}
	
	@Override
	public Enumeration<String> getKeys() {
		return this.delegate.getKeys();
	}
	
	@Override
	public boolean containsKey(String key) {
		return true;
	}
	
}
