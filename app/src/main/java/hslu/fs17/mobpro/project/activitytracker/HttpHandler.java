package hslu.fs17.mobpro.project.activitytracker;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.OkHttpClient;


class HttpHandler extends AsyncTask<String, String, String> {

    @Override
    protected String doInBackground(String... params) {
        OkHttpClient client = new OkHttpClient();
        //TODO
        try {
            URL url;
            if(params.length > 1)
                url = new URL(params[0]);
            else {
                url = new URL("http://192.168.0.22:12345");
            }
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setChunkedStreamingMode(0);
            OutputStream out = new BufferedOutputStream(connection.getOutputStream());
            writeStream(out);

            InputStream in = new BufferedInputStream(connection.getInputStream());
            readStream(in);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void readStream(InputStream in) {
    }

    private void writeStream(OutputStream out) {
        //TODO
        try {
            out.write(65);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}