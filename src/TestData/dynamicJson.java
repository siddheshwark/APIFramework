package TestData;

import static io.restassured.RestAssured.given;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import junit.framework.Assert;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class dynamicJson{


	@SuppressWarnings("deprecation")
	@Test (dataProvider="TestData")
	public void addBooks(String isbn,String aisle) {

		RestAssured.baseURI="http://216.10.245.166";

		//Post API

		String response=given().log().all().header("Content-Type","application/json")
				.body(Payload.addBook(isbn,aisle))
				.when().post("/Library/Addbook.php")
				.then().log().all().assertThat().statusCode(200).extract().response().asString();

 
		Assert.assertEquals("Book Already Exists", "Book Already Exists");

		JsonPath j1=ReUsableMethods.rawToJson(response);
		String id=j1.getString("ID");
		System.out.println(id);


	}
	
	//dataprovider is used parameterize test with different test data

	@DataProvider(name="TestData")
	public  static Object[][] getData() {

		return new Object[][] {{"awse","4536"},{"edrf","5478"},{"edcv","5621"}};

	}
}
