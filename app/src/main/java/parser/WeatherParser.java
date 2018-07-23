package parser;

import com.rhyscoronado.weatherapp.model.City;
import com.rhyscoronado.weatherapp.model.Clouds;
import com.rhyscoronado.weatherapp.model.List;
import com.rhyscoronado.weatherapp.model.Main;
import com.rhyscoronado.weatherapp.model.Weather;
import com.rhyscoronado.weatherapp.model.WeatherMain;
import com.rhyscoronado.weatherapp.model.Wind;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WeatherParser {

    public static WeatherMain parseWeatherForecast(JSONObject object) throws JSONException {

        WeatherMain forecast = new WeatherMain();

        // We get weather info (This is an array)
        JSONArray jArr = object.getJSONArray("list");

        for(int i = 0; i<jArr.length(); i++) {

            List list = new List();

            JSONObject jsonObject = jArr.getJSONObject(i);
            JSONObject mainObj = getObject("main", jsonObject);

            //Main
            Main main = new Main();
            main.setHumidity(getInt("humidity", mainObj));
            main.setPressure((double) getInt("pressure", mainObj));
            main.setTempMax((double) getFloat("temp_max", mainObj));
            main.setTempMin((double) getFloat("temp_min", mainObj));
            main.setTemp((double) getFloat("temp", mainObj));
            list.setMain(main);

            //Weather
            JSONObject weatherObject = jsonObject.getJSONArray("weather").getJSONObject(0);
            Weather weather = new Weather();
            weather.setId(getInt("id", weatherObject));
            weather.setDescription(getString("description", weatherObject));
            weather.setMain(getString("main", weatherObject));
            weather.setIcon(getString("icon", weatherObject));
            list.getWeather().add(weather);

            // Wind
            Wind wind = new Wind();
            JSONObject wObj = getObject("wind", jsonObject);
            wind.setSpeed((double) getFloat("speed", wObj));
            wind.setDeg((double) getFloat("deg", wObj));
            list.setWind(wind);

            // Clouds
            Clouds clouds = new Clouds();
            JSONObject cObj = getObject("clouds", jsonObject);
            clouds.setAll(getInt("all", cObj));
            list.setClouds(clouds);

            list.setDtTxt(getString("dt_txt", jsonObject));

            forecast.getList().add(list);

        }

        City city = new City();
        JSONObject cityObject  = object.getJSONObject("city");
        city.setCountry(getString("country", cityObject));
        city.setName(getString("name", cityObject));

        forecast.setCity(city);
        return forecast;
    }


    private static JSONObject getObject(String tagName, JSONObject jObj) throws JSONException {
        JSONObject subObj = jObj.getJSONObject(tagName);
        return subObj;
    }

    private static String getString(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getString(tagName);
    }

    private static float getFloat(String tagName, JSONObject jObj) throws JSONException {
        return (float) jObj.getDouble(tagName);
    }

    private static int getInt(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getInt(tagName);
    }

}
