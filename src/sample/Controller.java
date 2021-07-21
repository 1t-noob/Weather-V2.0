package sample;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javazoom.jl.decoder.JavaLayerException;

import org.json.JSONArray;
import org.json.JSONObject;


import javax.sound.sampled.*;
import java.io.*;


import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ResourceBundle;

public class Controller  {

    @FXML
    public ResourceBundle resources;

    @FXML
    public URL location;

    @FXML
    public Text weather;

    @FXML
    public Text temp;

    @FXML
    public Text mintemp;

    @FXML
    public Text maxtemp;

    @FXML
    public Text feeltemp;

    @FXML
    public Text humidity;

    @FXML
    public Text press;

    @FXML
    public Text speed;

    @FXML
    public Text deg;

    @FXML
    public Text cloud;

    @FXML
    public Text condition;

    @FXML
    public TextField city_field;
    @FXML
    public ImageView flag;

    @FXML
    void initialize() throws  UnsupportedAudioFileException, IOException, LineUnavailableException {

        Tooltip t = new Tooltip("Data at the moment\n" +
                                     "in this district.");
        Tooltip.install(feeltemp,t);
        Tooltip.install(maxtemp,t);
        Tooltip.install(mintemp,t);
        Tooltip.install(temp,t);
        city_field.setOnAction(event -> {
            flag.setVisible(true);
          //  String city = city_field.getText().trim().toLowerCase();
            String city = null;
            try {
                city = URLEncoder.encode(city_field.getText().trim().toLowerCase(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }


            String url = null;
            try {
                url = getUrlContent("http://api.openweathermap.org/data/2.5/weather?q="+city+
                        "&units=metric&appid=1c6b9f7d26667720ba79ba64f29ede10");

            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            } catch (JavaLayerException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            getContent(url);

            JSONObject object = new JSONObject(url);
            String nameCountry =  object.getJSONObject("sys").getString("country").toLowerCase();
            try {
                new EnableSound().goodLoad();
            } catch (JavaLayerException e) {
                e.printStackTrace();
            }
            flag.setImage(new Image(this.getClass().getResource("img/png/"+nameCountry+".png").toExternalForm()));
        } );
    }

    private  void  getContent (String url) {

        JSONObject obj = new JSONObject(url);
        temp.setText("TEMPERATURE       " +  obj.getJSONObject("main").getDouble("temp")+ "°");
        mintemp.setText("MIN_TEMP      " +  obj.getJSONObject("main").getDouble("temp_min"));
        maxtemp.setText("MAX_TEMP     " +  obj.getJSONObject("main").getDouble("temp_max"));
        feeltemp.setText("FEELS_LIKE    " +  obj.getJSONObject("main").getDouble("feels_like"));
        humidity.setText("HUMIDITY         " +  obj.getJSONObject("main").getDouble("humidity")+ "%");
        press.setText("PRESSURE          " +  obj.getJSONObject("main").getDouble("pressure"));
        speed.setText("WIND SPEED         " +  obj.getJSONObject("wind").getDouble("speed")+ " m/s");
        deg.setText("deg    " +  obj.getJSONObject("wind").getDouble("deg")+"°");
        cloud.setText("CLOUDS    " +  obj.getJSONObject("clouds").getDouble("all")+"%");
        JSONArray jsonArray = obj.getJSONArray("weather");

        String w_condition = jsonArray.getJSONObject(0).getString("description").toUpperCase();
        String[] wc = w_condition.split(" ");
        String t = "";
        if (wc.length>2){
            for (int i = 0; i < wc.length ; i++) {
                t = t +  wc[i] + " ";
                if (i==1) t = t+ "\n"+"\t"+"\t"+"\t";
            }
        }
        else t = w_condition;
      //  condition.setText("WEATHER          "  + jsonArray.getJSONObject(0).getString("description").toUpperCase());
        condition.setText("WEATHER          "  + t);

        Tooltip codeCountry = new Tooltip(obj.getJSONObject("sys").getString("country"));
        Tooltip.install(flag, codeCountry);

    }


    public String getUrlContent(String urlAdress) throws UnsupportedAudioFileException, IOException, LineUnavailableException, JavaLayerException, InterruptedException {
        StringBuffer content = new StringBuffer();
        try {
            URL url = new URL(urlAdress);
            String line;
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();

        }
        catch (Exception e) {
            Animation eror = new Animation(city_field);
            flag.setVisible(false);

            eror.playAnim();
            new EnableSound().errorLoad();
            temp.setText("TEMPERATURE       " );
            mintemp.setText("MIN_TEMP      " );
            maxtemp.setText("MAX_TEMP     " );
            feeltemp.setText("FEELS_LIKE    " );
            humidity.setText("HUMIDITY         " );
            press.setText("PRESSURE          " );
            speed.setText("WIND SPEED         ");
            deg.setText("deg    " );
            cloud.setText("CLOUDS    ");
            condition.setText("WEATHER          "  );
        }

        return content.toString();
    }


}
