

import static io.restassured.RestAssured.*;


import org.testng.Assert;
import TestData.Payload;
import TestData.ReUsableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;


public class firstTest {
	public static void main(String[] arg) {

		RestAssured.baseURI="https://rahulshettyacademy.com";

		//Post API

		String response=given().queryParam("key", "qaclick123").header("Content-Type","application/json")
				.body(Payload.addPlace())
				.when().post("maps/api/place/add/json")
				.then().assertThat().statusCode(200).extract().response().asString();

		System.out.println(response);
		JsonPath js=new JsonPath(response);
		String placeId=js.get("place_id");
		System.out.println(placeId);

		//###########################################################################################################

		//update place

		String Newaddress="70 winter walk, USA";	
		given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body("{\r\n"
				+ "\"place_id\":\""+placeId+"\",\r\n"
				+ "\"address\":\""+Newaddress+"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}")
		.when().put("maps/api/place/update/json")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();//body("msg", equalTo("Address successfully updated"));

		//###########################################################################################################

		//get place

		String getResponse=given().log().all().queryParam("key", "qaclick123")
				.queryParam("place_id", placeId)
				.when().get("maps/api/place/get/json")
				.then().log().all().assertThat().statusCode(200).extract().response().asString()	                          
				;
		System.out.println(getResponse);
		JsonPath js1=ReUsableMethods.rawToJson(getResponse);
		String actualAddress=js1.get("address");

		Assert.assertEquals(actualAddress, Newaddress, "Successfully matched");

	}

}
