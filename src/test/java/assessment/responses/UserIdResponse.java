package assessment.responses;

import com.sun.tools.xjc.reader.xmlschema.bindinfo.BIConversion;
import io.restassured.path.json.JsonPath;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import assessment.base.BaseAPI;
import net.sf.geographiclib.Geodesic;
import net.sf.geographiclib.GeodesicData;
import assesment.request.User;

import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserIdResponse extends BaseAPI {

    private static Logger logger = LoggerFactory.getLogger(UserIdResponse.class);


    public static void assertSuccesfullListUsers() {
        JsonPath jsonPathEvaluator = responseGet.jsonPath();

        Integer userArraySize = jsonPathEvaluator.getList("$").size();
        int totalUserCount = 0;
        int[] nullArray = new int[userArraySize];
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

        logger.info("\n******TEST FOR API returning the total number of users - " + totalUserCount);
    }

    public static void assertSuccesfullListUsersbyCity(String city) {
        JsonPath jsonPathEvaluator = responseGet.jsonPath();
        if (jsonPathEvaluator.getList("$").size() == 0) {
            logger.info("\n******There is no user data for CITY " + city);
        }
        else logger.info("\n******There are " + jsonPathEvaluator.getList("$").size() + " users in city of " + city);
    }

    public static void parseLondonUsers() throws IOException {

        List<User> london50MilesUsers = new ArrayList<>();

        // 51 deg 30 min 26 sec N
        double londonLat = 51 + (30 / 60.0) + (26 / 60.0 / 60.0);
        // 0 deg 7 min 39 sec W
        double londonLon = 0 - (7 / 60.0) - (39 / 60.0 / 60.0);

       // JSONArray usersLondon = new JSONArray(responseGet.toString());
        String resp = responseGet.getBody().asString();
        JSONArray usersLondon = new JSONArray(resp);

        for (int i = 0; i < usersLondon.length(); i++)
        {
            JSONObject userLondon = usersLondon.getJSONObject(i);
            // ...
            double userLat = userLondon.getDouble("latitude");
            double userLon = userLondon.getDouble("longitude");

            GeodesicData result =
                    Geodesic.WGS84.Inverse(londonLat, londonLon, userLat, userLon);

            double distanceInMeters = result.s12;
            double distanceInMiles = distanceInMeters / 1609.34;

            if (distanceInMiles <= 50)
            {
                int id = userLondon.getInt("id");
                String first_name = userLondon.getString("first_name");
                String last_name = userLondon.getString("last_name");
                String email = userLondon.getString("email");
                String ip_address = userLondon.getString("ip_address");

                User user = new User();
                user.setId(id);
                user.setFirstName(first_name);
                user.setLastName(last_name);
                user.setEmail(email);
                user.setIp_address(ip_address);
                user.setLatitude(userLat);
                user.setLatitude(userLon);
                london50MilesUsers.add(user);
            }
        }

        //Code to print Users within 50 miles of London
        System.out.println("********Users in London within 50 Miles******");
        for (int j=0;j <london50MilesUsers.size();j++) {
            System.out.println("\n" + "******** id: " + london50MilesUsers.get(j).getId()
                    + " " + "first name: " + london50MilesUsers.get(j).getFirstName()
                    + " " + "last name: " + london50MilesUsers.get(j).getLastName()
                    + " " + "email: " + london50MilesUsers.get(j).getEmail()
                    + " " + "IP Address: " + london50MilesUsers.get(j).getIp_address()
                    + " " + "latitude: " + london50MilesUsers.get(j).getLatitude()
                    + " " + "longtitude: " + london50MilesUsers.get(j).getLongitude());

        }
    }

}