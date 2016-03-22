package core.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

class TestUser{
	private int age;
	private String name;
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}

public class JsonUtil {
	
	public static <T> T JsonToObject(String jsonString, Class<T> tClass) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(jsonString, tClass);
		} catch (Exception e) {
			
		}
		return null;
	}
	
	public static String ObjectToJson(Object javaBean) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(javaBean);
		} catch (JsonProcessingException e) {
			System.out.println(e.toString());
		}
		return null;
	}
	
	public static void main(String[] args) throws Exception {
		TestUser user = new TestUser();
		user.setAge(11);
		user.setName("xww");
		
		JsonUtil a = new JsonUtil();
		
		String userString = a.ObjectToJson(user);
		System.out.println(userString);
		TestUser user2 =  JsonToObject(userString, TestUser.class);
		System.out.println(user2.getAge()+user2.getName());
	}

}
