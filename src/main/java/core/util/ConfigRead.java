package core.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigRead {	
	public static String getConfigProperties(String key){
		Properties prop = new Properties();
		try {			
			InputStream in = new ConfigRead().getClass().getResourceAsStream("config.properties" ); 
			prop.load(in);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return prop.getProperty(key).trim(); 
				
	}

}
