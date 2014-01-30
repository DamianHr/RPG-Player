package view.tools;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Damian
 */
public class WebRequester {
    public static String sendAuthentificationRequest(String urlToRequest, String username, String password) throws UnsupportedEncodingException {
        String parameters = encodeParameters(new UrlParameter("login", username), new UrlParameter("passwd", password));
        return sendRequest(urlToRequest, parameters);
    }

    public static String sendDataRetrivingRequest(String urlToRequest, int userid, int gameId) throws UnsupportedEncodingException {
        String parameters = encodeParameters(new UrlParameter("user",  String.valueOf(userid)), new UrlParameter("game", String.valueOf(gameId)));
        return sendRequest(urlToRequest, parameters);
    }

    public static String sendRequest(String urlToRequest, String parameters) {
        HttpURLConnection connection;
        try {
            URL url = new URL(urlToRequest);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length", Integer.toString(parameters.getBytes().length));
            connection.setRequestProperty("Content-Language", "en-US");

            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            //Send request
            DataOutputStream outputStream = new DataOutputStream (
                    connection.getOutputStream ());
            outputStream.writeBytes(parameters);
            outputStream.flush();
            outputStream.close();

            //Get Response
            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            StringBuilder response = new StringBuilder();
            while((line = bufferedReader.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            bufferedReader.close();
            return response.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String encodeParameters(UrlParameter... parameters) throws UnsupportedEncodingException {
        StringBuilder encodedParameters= new StringBuilder();
        for(UrlParameter parameter : parameters) {
            if(encodedParameters.length() > 0)
                encodedParameters.append("&");
            encodedParameters.append(parameter.parameterName);
            encodedParameters.append("=");
            encodedParameters.append(URLEncoder.encode(parameter.parameterValue, "UTF-8"));
        }
        return encodedParameters.toString();
    }

    static class UrlParameter {
        public String parameterName, parameterValue;

        public UrlParameter(String paramterName, String parameterValue) {
            this.parameterName = paramterName;
            this.parameterValue = parameterValue;
        }
    }
}