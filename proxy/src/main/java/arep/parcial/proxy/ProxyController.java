package arep.parcial.proxy;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@RestController
public class ProxyController {

    int machine = 1;
    private static final String USER_AGENT = "Mozilla/5.0";

    @GetMapping("/lucasSequence")
    public String lucasSequence(@RequestParam(value = "value") Integer value) throws IOException {
        if (machine==1){
            machine = 2;
            return httpConnection("http://localhost:8080/lucasSequence?value=" + value);
        }else{
            machine = 1;
            return httpConnection("http://localhost:8080/lucasSequence?value=" + value);
        }
    }

    private String httpConnection(String GET_URL) throws IOException {

        URL obj = new URL(GET_URL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);

        //The following invocation perform the connection implicitly before getting the code
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            System.out.println(response.toString());
            return response.toString();
        } else {
            System.out.println("GET request not worked");
            return "";
        }
    }

}
