package APITesting.com.org.api;

import static io.restassured.RestAssured.*;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class WeatherGetRequest {
	
	//Simple Get request
	@Test
	public void test_01(){
		
	Response response = when().
	get("http://api.openweathermap.org/data/2.5/weather?q=London&appid=b4b3a44b444e6b886f129227d7f7a2bf");
	
	System.out.println("Status Code: " + response.getStatusCode());
	Assert.assertEquals(response.getStatusCode(), 200);
		
	}
	//Negative test
	@Test
	public void test_02(){
		
	Response response = when().
	get("http://api.openweathermap.org/data/2.5/weather?q=London&appid=b4b3a44b444e6b886f129get401now");
	
	System.out.println("Status Code: " + response.getStatusCode());
	Assert.assertEquals(response.getStatusCode(), 401);
		
	}
	
	//Using parameter with rest assred
	@Test
	public void test_03(){
		
	Response response = given().
		                param("q","London").
		                param("appid","b4b3a44b444e6b886f129227d7f7a2bf").
		         //       param("q","New York").
		         //       param("appid","b4b3a44b444e6b886f129227d7f7a2bf").
		                when().
	get("http://api.openweathermap.org/data/2.5/weather");
	
	System.out.println("Status Code: " + response.getStatusCode());
	Assert.assertEquals(response.getStatusCode(), 200);
		
	}
	
	@Test
	public void test_04(){
						given().
		                param("q","London").
		                param("appid","b4b3a44b444e6b886f129227d7f7a2bf").
		                when().
	get("http://api.openweathermap.org/data/2.5/weather").
	then().
	assertThat().statusCode(200);
		
	}
	
	
	@Test
	public void test_05() {
		
	Response response = given().
		                param("q","Catonsville").
		                param("appid","b4b3a44b444e6b886f129227d7f7a2bf").
		                when().
	get("http://api.openweathermap.org/data/2.5/weather");
	
	System.out.println(response.asString());
		
	}
	
	@Test
	public void test_06() {
		
	Response response = given().
		                param("zip","21228,us").
		                param("appid","b4b3a44b444e6b886f129227d7f7a2bf").
		                when().
	get("http://api.openweathermap.org/data/2.5/weather");
	
	Assert.assertEquals(response.getStatusCode(), 200);
	System.out.println(response.asString());
		
	}
	
	
	@Test
	public void test_07() {
		
	String weatherReport = given().
		                param("id","4350635").
		                param("appid","b4b3a44b444e6b886f129227d7f7a2bf").
		                when().
		                get("http://api.openweathermap.org/data/2.5/weather").
		                then().
		                contentType(ContentType.JSON).
		                extract().
		                path("weather[0].description");
	
	System.out.println("WeatherReport: " + weatherReport);
		
	}
	
	
	@Test
	public void test_08() {
		
	Response response = given().
		                param("zip","21228,us").
		                param("appid","b4b3a44b444e6b886f129227d7f7a2bf").
		                when().
		                get("http://api.openweathermap.org/data/2.5/weather");
	
	String actualWeatherReport = response.then().
						contentType(ContentType.JSON).
						extract().
						path("weather[0].description");
	
	String expectedWeatherReport=null;
	
	expectedWeatherReport = "clear sky";
	
	if (actualWeatherReport.equalsIgnoreCase(expectedWeatherReport)){
		System.out.println("Test Passed");
	} else {
		System.out.println("Test Failed");
	}
	
	System.out.println("WeatherReport: " + actualWeatherReport);
		
	}
	
	@Test
	public void test_09() {
		
		Response responseById = given().
                param("id","4350635").
                param("appid","b4b3a44b444e6b886f129227d7f7a2bf").
                when().
                get("http://api.openweathermap.org/data/2.5/weather");
		
		String lon = String.valueOf(responseById.then().
				contentType(ContentType.JSON).
				extract().path("coord.lon"));
		
		System.out.println("Longitude is: " + lon);
		
		String lat = String.valueOf(responseById.then().
				contentType(ContentType.JSON).
				extract().path("coord.lat"));
		
		System.out.println("Latitude is: " + lat);
		
		String reportByCoordinates = given().
									param("lon", lon).
									param("lat",lat).
									param("appid","b4b3a44b444e6b886f129227d7f7a2bf").
									when().
									get("http://api.openweathermap.org/data/2.5/weather").
									then().
									contentType(ContentType.JSON).
									extract().
									path("weather[0].description");
		
		String reportById = responseById.then().
						contentType(ContentType.JSON).
						extract().
						path("weather[0].description");
		
		System.out.println("Report by city ID is: "+reportById+ 
				"\nAnd, report by coordinates is: " +reportByCoordinates);
		
		Assert.assertEquals(reportById, reportByCoordinates);
				
	}

}
