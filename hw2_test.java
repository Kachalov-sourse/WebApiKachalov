package fordropbox;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Order;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.config.EncoderConfig.encoderConfig;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class Test {
	private static String file_path = "src/hw2/resources/test.txt";
	//dropbox authentication token
	private static String auth_token = "_CPPB8UPk0osAAAAAAAAYL3jj0is7XQDTDIpvzO7jl0J0BYqr3bPlemDhBBl1WbkH";
	
	@Order(1)
    @Test
    public void upload_test() {
        RestAssured.config = RestAssured.config().encoderConfig(encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false));
        File file = new File(file_path);
        String apiArgs = "{\"mode\":\"add\",\"autorename\":true,\"mute\":false,\"path\":\"/test.txt\"}";

        given().headers("Authorization","Bearer " + auth_token, "Content-Type", "application/octet-stream", "Dropbox-API-Arg", apiArgs).body(file)
        .when().post("https://content.dropboxapi.com/2/files/upload")
        .then().statusCode(200);
    }
	
	@Order(2)
    @Test
    public void get_metadata_test() {
        Map<String,String> path = new HashMap<String,String>();
        path.put("path", "/test.txt");

        given().headers("Authorization","Bearer " + auth_token, "Content-Type", "application/json").body(path)
        .when().post("https://api.dropboxapi.com/2/files/get_metadata")
        .then().statusCode(200);
    }
	
	@Order(3)
    @Test
    public void delete_test() {
        Map<String,String> path = new HashMap<String,String>();
        path.put("path", "/test.txt");

        given().headers("Authorization","Bearer " + auth_token, "Content-Type", "application/json").body(path))
        .when().post("https://api.dropboxapi.com/2/files/delete_v2")
        .then().statusCode(200);
    }
}