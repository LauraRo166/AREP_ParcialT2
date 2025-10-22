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
    int newValue;
    private static final String USER_AGENT = "Mozilla/5.0";
    @GetMapping("/lucasSequence")
    public String lucasSequence(@RequestParam(value = "value") Integer value) throws IOException {
        newValue = value;
        return selectMachine();
    }

    private String selectMachine() throws IOException {
        String URL1 = "http://ec2-52-91-250-75.compute-1.amazonaws.com:8080/lucasSequence?value=" + newValue;
        String URL2 = "http://ec2-54-227-50-236.compute-1.amazonaws.com:8080/lucasSequence?value=" + newValue;
        if (machine==1){
            System.out.println("Machine: " + machine + " with URL: " + URL1);
            return httpConnection(URL1);
        }else{
            System.out.println("Machine: " + machine + " with URL: " + URL2);
            machine = 1;
            return httpConnection(URL2);
        }

    }
    private String httpConnection(String GET_URL) throws IOException {

        URL obj = new URL(GET_URL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        try{
            int responseCode = con.getResponseCode();
        }catch (Exception e){
            System.out.println("GET request not worked in machine: " + GET_URL );
            if (machine==1){
                machine = 2;
            }else{
                machine = 1;
            }
            System.out.println("Machine: " + machine);
            return selectMachine();
        }

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
            System.out.println("GET request not worked in machine: " + GET_URL );
            if (machine==1){
                machine = 2;
            }else{
                machine = 1;
            }
            System.out.println("Change Machine to: " + machine);
            return selectMachine();
        }
    }

}
