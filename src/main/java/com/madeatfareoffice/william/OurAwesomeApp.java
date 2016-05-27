package com.madeatfareoffice.william;

import static spark.Spark.get;
import static spark.Spark.post;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.madeatfareoffice.william.objects.AboutUs;
import lombok.Data;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OurAwesomeApp
{

	private static final int HTTP_BAD_REQUEST = 400;

	public interface Validable {
		boolean isValid();
	}

	@Data
	static class NewPostPayload {
		private String title;
		private List categories = new LinkedList<>();
		private String content;

		public boolean isValid() {
			return title != null && !title.isEmpty() && !categories.isEmpty();
		}
	}

	// In a real application you may want to use a DB, for this example we just store the posts in memory
	public static class Model {
		private int nextId = 1;
		private Map posts = new HashMap<>();

		@Data
		class Post {
			private int id;
			private String title;
			private List categories;
			private String content;
		}

		public int createPost(String title, String content, List categories){
			int id = nextId++;
			Post post = new Post();
			post.setId(id);
			post.setTitle(title);
			post.setContent(content);
			post.setCategories(categories);
			posts.put(id, post);
			return id;
		}

		public List getAllPosts(){
			return (List) posts.keySet().stream().sorted().map((id) -> posts.get(id)).collect(Collectors.toList());
		}
	}

	public static String dataToJson(Object data) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(SerializationFeature.INDENT_OUTPUT);
			StringWriter sw = new StringWriter();
			mapper.writeValue(sw, data);
			return sw.toString();
		} catch (IOException e){
			throw new RuntimeException("IOException from a StringWriter?", e);
		}
	}

	public static void main( String[] args) {
		//port(4567);
		//secure(keystoreFilePath, keystorePassword, truststoreFilePath, truststorePassword);
		//threadPool(maxThreads);
		//threadPool(maxThreads, minThreads, timeOutMillis);

		Model model = new Model();

		aboutUs();
		otaGet();
		otaPost();
		actionGet();
		actionPost();
	}

	public static void aboutUs() {
		get("/", (request, response) -> {
			response.status(200);
			response.type("application/json");
			return dataToJson(new AboutUs());
		});
	}

	/**
	 * OtaEquipment is additional equipment for rental cars. And something that can be recommended to the end user
	 * of a car rental website. Populate at least 30 ACRISS standard equipments to your DB. (This message and its
	 * attributes mentions OTA, even though the codes we use are ACRISS codes).
	 *
	 * ACRISS Code list on Fareoffice Dropbox: https://www.dropbox.com/s/unu02moi1sn1jzy/EquipmentCodes.xlsx?dl=0
	 */
	public static void otaGet() {
		get("/api/otaequipment", (request, response) -> {
			response.status(200);
			response.type("application/json");
			return dataToJson(null);
		});

	}

	/**
	 * List all items that have been added to the database
	 *
	 * Request example: {”description”:”GPS”,”ota”:”GPS device”}
	 * Response, successfully stored: {”id”:”5746f7ea1b674201c0f11368″}
	 */
	public static void otaPost() {
		post("/api/otaequipment", (request, response) -> {
			response.status(200);
			response.type("application/json");
			return dataToJson(null);
		});
	}

	/**
	 * Record an action
	 *
	 * Request example: {”ota”:”NAV”,”plo”:”MADT71″,”date”:”2016-10-14″}
	 * Response, successfully stored: {”id”:”5746f39a1b674201ba031ab1″}
	 * Response, error: {”error”:”OTA Code not in our database”}
	 * Response, error: {”error”:”Value error”}
	 */
	public static void actionPost() {
		post("/api/action", (request, response) -> {
			response.status(200);
			response.type("application/json");
			return dataToJson(null);
		});
	}

	/**
	 * List all actions that have been added to the database
	 */
	public static void actionGet() {
		post("/api/action", (request, response) -> {
			response.status(200);
			response.type("application/json");
			return dataToJson(null);
		});
	}
}