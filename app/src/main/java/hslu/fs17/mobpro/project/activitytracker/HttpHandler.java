package hslu.fs17.mobpro.project.activitytracker;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;


class HttpHandler extends AsyncTask<String, String, String> {

    private AtomicBoolean stillRunning;

    public HttpHandler() {
        super();
        this.stillRunning = new AtomicBoolean(true);
    }

    @Override
    protected String doInBackground(String... params) {
        while (this.stillRunning.get()) {
            try {
                synchronized (this) {
                    OkHttpClient client = new OkHttpClient();
                    //TODO
                    try {
                        String url = "http://192.168.1.150:80";
                        if (params.length > 1) {
                            url = params[0];
                        }
                        OkHttpClient client2 = new OkHttpClient();
                        RequestBody requestBody = new MultipartBody.Builder()
                                .setType(MultipartBody.FORM)
                                .addFormDataPart("procentage", "procentage")
                                .build();

                        Request request = new Request.Builder()
                                .url(url)
                                .method("POST", RequestBody.create(null, new byte[0]))
                                .post(requestBody)
                                .build();
                        client2.newCall(request).execute();
                    } catch (IOException e) {
                        System.out.println("Something went Wrong");
                        e.printStackTrace();
                    }
                }
                Thread.sleep(100000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void stopTask() {
        this.stillRunning.set(false);
    }
}