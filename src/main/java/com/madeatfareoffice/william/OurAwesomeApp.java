package com.madeatfareoffice.william;

import static spark.Spark.get;
import static spark.Spark.post;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.madeatfareoffice.william.objects.*;
import org.joda.time.YearMonth;
import org.joda.time.LocalDate;
import org.sql2o.Sql2o;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.io.StringWriter;

public class OurAwesomeApp
{

	private static final int HTTP_BAD_REQUEST = 400;
	private static final int HTTP_OK = 200;
	private static final String JSON_TYPE = "application/json";
	private Sql2oDao sql2oDao;

	public interface Validable {
		boolean isValid();
		String getErrorMessage();
	}

	/*
	SAMPLE DATA MODEL
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
	*/

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

	public static <T> T jsonToData(Request request, Class<T> clazz) throws IOException
	{
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(request.body(), clazz);
	}

	public static String response(Response response, int httpCode, Object object) {
		response.status(httpCode);
		response.type(JSON_TYPE);
		return dataToJson(object);
	}

	public static String ok(Response response, Object object) {
		return response(response, HTTP_OK, object);
	}

	public static String id(Response response, String id) {
		return response(response, HTTP_OK, new ResourceIdResponse(id));
	}

	public static String validationError(Response response, Validable object) {
		return response(response, HTTP_BAD_REQUEST, new ErrorResponse(object.getErrorMessage()));
	}

	public static String error(Response response, String message, Exception e) {
		return response(response, HTTP_BAD_REQUEST, new ErrorResponse(message + (e == null ? "" : ": " + e.getMessage() + (e.getCause() == null ? "" : " (" + e.getCause().getMessage() + ")"))));
	}

	public static String parseError(Response response) {
		return error(response, "JSON parse error", null);
	}

	public static void main( String[] args) {
		//port(4567);
		//secure(keystoreFilePath, keystorePassword, truststoreFilePath, truststorePassword);
		//threadPool(maxThreads);
		//threadPool(maxThreads, minThreads, timeOutMillis);

//		Model model = new Model();

		Sql2o sql2o = new Sql2o("jdbc:postgresql://10.0.0.88:5432/william", "william_owner", "postgres");
		Sql2oDao sql2oDao = new Sql2oDao(sql2o);
		sql2oDao.createAction("GPS", "MIAT71", new LocalDate());
		sql2oDao.createAction("GPS", "MIAT71", new LocalDate());

		aboutUs();
		otaApi();
		actionApi();
		recommendApi();

		/*
		SAMPLE CODE
		// insert a post (using HTTP post method)
		post("/posts", (request, response) -> {
			try {
				ObjectMapper mapper = new ObjectMapper();
				NewPostPayload creation = mapper.readValue(request.body(), NewPostPayload.class);
				if (!creation.isValid()) {
					response.status(HTTP_BAD_REQUEST);
					return "";
				}
				int id = model.createPost(creation.getTitle(), creation.getContent(), creation.getCategories());
				response.status(200);
				response.type("application/json");
				return id;
			} catch (JsonParseException jpe) {
				response.status(HTTP_BAD_REQUEST);
				return "";
			}
		});

		// get all post (using HTTP get method)
		get("/posts", (request, response) -> {
			response.status(200);
			response.type("application/json");
			return dataToJson(model.getAllPosts());
		});
		*/
	}

	/*
	 * They asked for it
	 */
	public static void aboutUs() {
		get("/", (request, response) -> ok(response, new AboutUsResponse()));
	}

	/*
	OtaEquipment is additional equipment for rental cars. And something that can be recommended to the end user
	of a car rental website. Populate at least 30 ACRISS standard equipments to your DB. (This message and its
	attributes mentions OTA, even though the codes we use are ACRISS codes).

	ACRISS Code list on Fareoffice Dropbox: https://www.dropbox.com/s/unu02moi1sn1jzy/EquipmentCodes.xlsx?dl=0

	POST: ...
	GET: list all items that have been added to the database

	Request example: {”description”:”GPS”,”ota”:”GPS device”}
	Responses:
		Successfully stored
    		{”id”:”5746f7ea1b674201c0f11368″}
	*/
	public static void otaApi() {
		get("/api/otaequipment", (request, response) -> {
			// TODO implement it
			return ok(response, new OtaEquipmentResponse[] {new OtaEquipmentResponse()});
		});

		post("/api/otaequipment", (request, response) -> {
			try {
				OtaEquipmentRequest reqObj = jsonToData(request, OtaEquipmentRequest.class);
				if (!reqObj.isValid()) {
					return validationError(response, reqObj);
				}
				// TODO implement stuff here
				return id(response, "dummy");
			} catch (JsonParseException jpe) {
				return parseError(response);
			}
		});
	}

	/*
	ACTION API

	In a car rental process; when someone is picking additional equipment, this should be recorded as an ”action”.
	Store a proper OTA code (i.e. CSI), a pickup date and a pickup location.

	POST: save Action to the db
	GET: list all actions that have been added to the database

	Request example

	    {”ota”:”NAV”,”plo”:”MADT71″,”date”:”2016-10-14″}

	Responses
    	Errors
  			{”error”:”OTA Code not in our database”}
    		{”error”:”Value error”}
    Successfully stored
    	{”id”:”5746f39a1b674201ba031ab1″}
	 */
	public static void actionApi() {
		get("/api/action", (request, response) -> {
			// TODO implement
			return ok(response, new ActionResponse[] { new ActionResponse() });
		});

		post("/api/action", (request, response) -> {
			ActionRequest reqObj = jsonToData(request, ActionRequest.class);
			if (!reqObj.isValid()) {
				return validationError(response, reqObj);
			}
			// TODO implement
			return id(response, "dummy");
		});

	}

	/*
	GET: show the  5 MOST popular equipment items  for ${month}. This can be used as a recommendation for
	other visitors to our fictional car rental site.

	Response examples:
		Error:
    		{”error”:”Validation error”}
    		{”error”:”Value error”}
    	Items returned for MAY 2016:
    		{
    			‘date’: datetime.datetime(2016, 5, 7, 0, 0),
    			‘plo’: ‘MIAT71’,
    			‘_id’: ObjectId(‘574625c81b67420191c5066c’),
    			‘ota’: ‘CBB’
    		},
    		{
    			‘date’: datetime.datetime(2016, 5, 6, 0, 0),
    			‘plo’: ‘ORDT71’,
    			‘_id’: ObjectId(‘574626041b67420191c5066d’),
    			‘ota’: ‘CSR’
    		}

	 */
	public static void recommendApi() {
		get("/api/recommend/:year/:month", (request, response) -> {
			String year = request.params(":year");
			String month = request.params(":month");
			YearMonth yearMonth;
			try {
				yearMonth = Validator.parseYearMonth(year, month);
			} catch (IllegalArgumentException e) {
				return error(response, "invalid parameters", e);
			}
			// TODO implement
			return ok(response, new RecommendResponse[] { new RecommendResponse() });
		});
	}
}
