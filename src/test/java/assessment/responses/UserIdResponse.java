package assessment.responses;

import com.sun.tools.xjc.reader.xmlschema.bindinfo.BIConversion;
import io.restassured.path.json.JsonPath;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import assessment.base.BaseAPI;



public class UserIdResponse extends BaseAPI {

    private static Logger logger = LoggerFactory.getLogger(UserIdResponse.class);

    public static void assertSuccesfullUserResponsebyId(int id) {
        JSONObject response = new JSONObject(responseGet.getBody().asString());

        if (response instanceof JSONObject)
          assertJsonIntegerAttributeGetResponse("id",id);
        else {
            logger.info("\n The return object for user id is more than two elements " + id);
            Assert.assertFalse(true);
        }
    }

    public static void assertSuccesfullListUsers() {
        JsonPath jsonPathEvaluator = responseGet.jsonPath();
        Integer userArraySize = jsonPathEvaluator.getList("$").size();
        int totalUserCount = 0;
        int[] nullArray = new int[userArraySize];
        // logger.info("\n The user list Array is of size " + userArraySize);

        Boolean flag = false;

        for(int i= 0;i < userArraySize; i++) {
            if(jsonPathEvaluator.getString("id["+i+"]") == null){
                logger.info("\n The Array element for ID null is "+ i);
                flag = true;
            }
            else {
                totalUserCount = totalUserCount + 1;
            }
        }
        if(flag){
            logger.info("\n The id field for array element is null ");
            Assert.assertFalse(flag);
        }

        logger.info("\n The total number of users is " + totalUserCount);
    }

    public static void assertSuccesfullListUsersbyCity(String city) {
        JsonPath jsonPathEvaluator = responseGet.jsonPath();
        if (jsonPathEvaluator.getList("$").size() == 0) {
            logger.info("\n******There is no user data for CITY " + city);
        }
        else logger.info("\n******There are " + jsonPathEvaluator.getList("$").size() + " users in city of " + city);
    }

//    public static void parseLondonUsers() {
//        // 51 deg 30 min 26 sec N
//        double londonLat = 51 + (30 / 60.0) + (26 / 60.0 / 60.0);
//        // 0 deg 7 min 39 sec W
//        double londonLon = 0 - (7 / 60.0) - (39 / 60.0 / 60.0);
//
//        System.out.println("People who live in London");
//        JSONArray usersLondon = new JSONArray((responseGet));
//
//        for (int i = 0; i < usersLondon.length(); i++)
//        {
//            JSONObject userLondon = usersLondon.getJSONObject(i);
//            // ...
//            double latitude = userLondon.getDouble("latitude");
//            double longitude = userLondon.getDouble("longitude");
//
//            double userLat = convertToDecimalDegrees(latitude);
//            double userLon = convertToDecimalDegrees(longitude);
//
//            GeodesicData result =
//                    Geodesic.WGS84.Inverse(londonLat, londonLon, userLat, userLon);
//
//            double distanceInMeters = result.s12;
//            double distanceInMiles = distanceInMeters / 1609.34;
//
//            if (distanceInMiles <= 50)
//            {
//                int id = userLondon.getInt("id");
//                String first_name = userLondon.getString("first_name");
//                String last_name = userLondon.getString("last_name");
//                String email = userLondon.getString("email");
//                String ip_address = userLondon.getString("ip_address");
//             //   int latitude = userLondon.getInt("latitude");
//             //   int longitude = userLondon.getInt("longitude");
//                System.out.println("id: " + id + " " + "first name: " + first_name + " " + "last name: " + last_name + " " + "email: " + email + " "
//                        + "IP Address: " + ip_address + " " + "latitude: " + latitude + " " + "longtitude: " + longitude);
//            }
//        }
//
//    }
}