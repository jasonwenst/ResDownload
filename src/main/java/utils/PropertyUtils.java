package utils;

import java.util.ResourceBundle;
import org.apache.commons.lang3.StringUtils;

public class PropertyUtils {
	
	private static final String PROPERTY = "properties";
	private static ResourceBundle rb = null;

	static {
		loadProperty();
	}
	
	
	private static void loadProperty() {
		rb = ResourceBundle.getBundle(PROPERTY.trim());
	}
	
	public static String getProperty(String key) {
		if(rb == null) {
			return "";
		}
		if(!StringUtils.isNoneEmpty(key)) {
			return "";
		}
		if(!rb.containsKey(key)) {
			return "";
		}
		return rb.getString(key);
	}
	
}
