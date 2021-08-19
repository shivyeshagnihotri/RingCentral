package group.artifact;

import java.io.FileInputStream;
import java.util.Properties;

import org.json.JSONObject;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ApiTestcases {

	Properties prop;
	SoftAssert asserts;

	//Initiating config file
	@BeforeTest
	public void config() throws Exception{
		prop = new Properties();
		FileInputStream fis = new FileInputStream("config.properties");
		prop.load(fis); 
	}

	//GET request with invalid id
	@Test(priority=1)
	public void getInvalidId() throws Exception
	{
		asserts = new SoftAssert();
		RestAssured.baseURI = "http://localhost:8080";
		RequestSpecification getIdRequest = RestAssured.given();
		Response getById = getIdRequest.request(Method.GET,"/api/users/"+prop.getProperty("invalidId"));
		//System.out.println(getById.asString());
		//System.out.println(getById.statusCode());
		asserts.assertEquals(getById.statusCode(),404);
		asserts.assertFalse(getById.getHeaders().toString().isEmpty());
		asserts.assertAll();
	}

	//GET request where id is not of type int
	@Test(priority=2)
	public void getStringid() throws Exception
	{
		asserts = new SoftAssert();
		RestAssured.baseURI = "http://localhost:8080";
		RequestSpecification getIdRequest = RestAssured.given();
		Response getById = getIdRequest.request(Method.GET,"/api/users/hello");
		//System.out.println(getById.asString());
		//System.out.println(getById.statusCode());
		asserts.assertEquals(getById.statusCode(),400);
		asserts.assertFalse(getById.getHeaders().toString().isEmpty());
		asserts.assertAll();
	}

	//Get request without any param should work as getting all users
	@Test(priority=3)
	public void getWithoutid() throws Exception
	{
		asserts = new SoftAssert();
		RestAssured.baseURI = "http://localhost:8080";
		RequestSpecification getIdRequest = RestAssured.given();
		Response getById = getIdRequest.request(Method.GET,"/api/users/");
		//System.out.println(getById.asString());
		JSONObject byIdPath = new JSONObject(getById.asString());
		asserts.assertFalse(byIdPath.isEmpty());
		asserts.assertEquals(getById.statusCode(),200);
		asserts.assertFalse(getById.getHeaders().toString().isEmpty());
		asserts.assertAll();
	}

	//GET request for valid id
	@Test(priority=4)
	public void getValidId() throws Exception
	{
		asserts = new SoftAssert();
		RestAssured.baseURI = "http://localhost:8080";
		RequestSpecification getIdRequest = RestAssured.given();
		Response getById = getIdRequest.request(Method.GET,"/api/users/"+prop.getProperty("validId"));
		//System.out.println(getById.asString());
		JSONObject byIdPath = new JSONObject(getById.asString());
		asserts.assertEquals(byIdPath.get("id").toString(),prop.getProperty("validId"));
		asserts.assertEquals(getById.statusCode(),200);
		asserts.assertFalse(getById.getHeaders().toString().isEmpty());
		asserts.assertAll();
	}

	//PUT request without passing headers
	@Test(priority=5)
	public void putValidIdWithoutHeader() throws Exception
	{
		asserts = new SoftAssert();
		RestAssured.baseURI = "http://localhost:8080";
		RequestSpecification putIdRequest = RestAssured.given();
		String payload = "{\"dayOfBirth\":\""+prop.getProperty("dayOfBirth")+"\",\"email\":\""+prop.getProperty("email")+"\",\"firstName\":\""+prop.getProperty("firstName")+"\",\"lastName\":\""+prop.getProperty("lastName")+"\"}";
		putIdRequest.body(payload);
		Response putById = putIdRequest.request(Method.PUT,"/api/users/"+prop.getProperty("validId"));
		//System.out.println(putById.asString());
		//System.out.println(putById.getStatusCode());
		asserts.assertEquals(putById.statusCode(),400);
		asserts.assertFalse(putById.getHeaders().toString().isEmpty());
		asserts.assertAll();
	}

	//PUT request for invalid id
	@Test(priority=6)
	public void putInvalidId() throws Exception
	{
		asserts = new SoftAssert();
		RestAssured.baseURI = "http://localhost:8080";
		RequestSpecification putIdRequest = RestAssured.given().header("Content-Type", "application/json");
		String payload = "{\"dayOfBirth\":\""+prop.getProperty("dayOfBirth")+"\",\"email\":\""+prop.getProperty("email")+"\",\"firstName\":\""+prop.getProperty("firstName")+"\",\"lastName\":\""+prop.getProperty("lastName")+"\"}";
		putIdRequest.body(payload);
		Response putById = putIdRequest.request(Method.PUT,"/api/users/"+prop.getProperty("invalidId"));
		//System.out.println(putById.asString());
		//System.out.println(putById.getStatusCode());
		asserts.assertEquals(putById.statusCode(),409);
		asserts.assertFalse(putById.getHeaders().toString().isEmpty());
		asserts.assertAll();
	}

	//PUT request for invalid body data
	@Test(priority=7)
	public void putValidIdWithInvalidData() throws Exception
	{
		asserts = new SoftAssert();
		RestAssured.baseURI = "http://localhost:8080";
		RequestSpecification putIdRequest = RestAssured.given().header("Content-Type", "application/json");
		String payload = "{\"dayOfBirth\": 10}";
		putIdRequest.body(payload);
		Response putById = putIdRequest.request(Method.PUT,"/api/users/"+prop.getProperty("invalidId"));
		//System.out.println(putById.asString());
		//System.out.println(putById.getStatusCode());
		asserts.assertEquals(putById.statusCode(),400);
		asserts.assertFalse(putById.getHeaders().toString().isEmpty());
		asserts.assertAll();
	}

	//PUT request for valid id
	@Test(priority=8)
	public void putValidId() throws Exception
	{
		asserts = new SoftAssert();
		RestAssured.baseURI = "http://localhost:8080";
		RequestSpecification putIdRequest = RestAssured.given().header("Content-Type", "application/json");
		String payload = "{\"dayOfBirth\":\""+prop.getProperty("dayOfBirth")+"\",\"email\":\""+prop.getProperty("email")+"\",\"firstName\":\""+prop.getProperty("firstName")+"\",\"lastName\":\""+prop.getProperty("lastName")+"\"}";
		putIdRequest.body(payload);
		Response putById = putIdRequest.request(Method.PUT,"/api/users/"+prop.getProperty("validId"));
		//System.out.println(putById.asString());
		JSONObject putIdPath = new JSONObject(putById.asString());

		//Validate data as per params
		asserts.assertEquals(putById.statusCode(),200);
		asserts.assertFalse(putById.getHeaders().toString().isEmpty());
		asserts.assertEquals(putIdPath.get("dayOfBirth"),prop.getProperty("dayOfBirth"));
		asserts.assertEquals(putIdPath.get("email"),prop.getProperty("email"));
		asserts.assertEquals(putIdPath.get("firstName"),prop.getProperty("firstName"));
		asserts.assertEquals(putIdPath.get("lastName"),prop.getProperty("lastName"));
		asserts.assertAll();
	}

	//DELETE request for invalid id
	@Test(priority=9)
	public void deleteInvalidId() throws Exception
	{
		asserts = new SoftAssert();
		RestAssured.baseURI = "http://localhost:8080";
		RequestSpecification getIdRequest = RestAssured.given();
		Response getById = getIdRequest.request(Method.DELETE,"/api/users/"+prop.getProperty("invalidId"));
		//System.out.println(getById.asString());
		//System.out.println(getById.statusCode());
		asserts.assertEquals(getById.statusCode(),404);
		asserts.assertFalse(getById.getHeaders().toString().isEmpty());
		asserts.assertAll();
	}

	//DELETE request where id is not of type int
	@Test(priority=10)
	public void deleteStringId() throws Exception
	{
		asserts = new SoftAssert();
		RestAssured.baseURI = "http://localhost:8080";
		RequestSpecification getIdRequest = RestAssured.given();
		Response getById = getIdRequest.request(Method.DELETE,"/api/users/hello");
		//System.out.println(getById.asString());
		//System.out.println(getById.statusCode());
		asserts.assertEquals(getById.statusCode(),400);
		asserts.assertFalse(getById.getHeaders().toString().isEmpty());
		asserts.assertAll();
	}

	//DELETE request without id
	@Test(priority=11)
	public void deleteWithoutId() throws Exception
	{
		asserts = new SoftAssert();
		RestAssured.baseURI = "http://localhost:8080";
		RequestSpecification getIdRequest = RestAssured.given();
		Response getById = getIdRequest.request(Method.DELETE,"/api/users/");
		//System.out.println(getById.asString());
		asserts.assertEquals(getById.statusCode(),404);
		asserts.assertFalse(getById.getHeaders().toString().isEmpty());
		asserts.assertAll();
	}

	//DELETE request with valid id
	@Test(priority=12) 
	public void deleteValidId() throws Exception 
	{
		asserts =new SoftAssert();
		RestAssured.baseURI = "http://localhost:8080";
		RequestSpecification getIdRequest = RestAssured.given(); 
		Response getById = getIdRequest.request(Method.DELETE,"/api/users/"+prop.getProperty("validId"));
		//System.out.println(getById.asString());
		//System.out.println(getById.statusCode());
		asserts.assertEquals(getById.statusCode(),204);
		asserts.assertFalse(getById.getHeaders().toString().isEmpty());
		if(getById.statusCode()==204)
		{
			RequestSpecification getRequest = RestAssured.given();
			Response getAll = getRequest.request(Method.GET,"/api/users/"+prop.getProperty("validId"));
			//System.out.println(getAll.asString());
			//System.out.println(getAll.statusCode());
			asserts.assertEquals(getAll.statusCode(),404);
			asserts.assertFalse(getAll.getHeaders().toString().isEmpty());
		}
		asserts.assertAll();
		//Verify with GET request that id has been deleted
	}	

}
