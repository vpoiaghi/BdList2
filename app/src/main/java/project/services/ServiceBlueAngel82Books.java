package project.services;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import project.services.abs.IAsyncServiceListener;

/**
 * Created by VINCENT on 03/02/2019.
 *
 */
public class ServiceBlueAngel82Books extends AsyncTask<String, Void, ServiceBlueAngel82Books.HttpResult> {

    private final IAsyncServiceListener listener;

    public ServiceBlueAngel82Books(IAsyncServiceListener listener) {
        super();
        this.listener = listener;
    }

    public void searchByIsbn(String isbn) {
        String uri = "http://blueangel82.ddns.net/bd/search/isbn/" + isbn;
        super.execute(uri);
    }

    @Override
    protected HttpResult doInBackground(String... bookURLs) {
        //request book info

        StringBuilder bookBuilder = new StringBuilder();

        String bookSearchURL = bookURLs[0];
        int status = 0;

        DefaultHttpClient bookClient = new DefaultHttpClient();

        try {
            //get the data

            bookClient.getCredentialsProvider().setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT), new NTCredentials("bruno", "bruno", "", ""));


            HttpGet bookGet = new HttpGet(bookSearchURL);
            HttpResponse bookResponse = bookClient.execute(bookGet);
            StatusLine bookSearchStatus = bookResponse.getStatusLine();

            status = bookSearchStatus.getStatusCode();

            if (status == 200) {
                //we have a result
                HttpEntity bookEntity = bookResponse.getEntity();

                InputStream bookContent = bookEntity.getContent();
                InputStreamReader bookInput = new InputStreamReader(bookContent);
                BufferedReader bookReader = new BufferedReader(bookInput);

                String lineIn;
                while ((lineIn=bookReader.readLine())!=null) {
                    bookBuilder.append(lineIn);
                }
            }

        }
        catch(Exception e){
            e.printStackTrace();
        }

        return new HttpResult(status, bookBuilder.toString());
    }

    @Override
    protected void onPostExecute(HttpResult result) {

        Gson gson = new Gson();
        ResultEdition object = gson.fromJson(result.getValue(), ResultEdition.class);


        listener.asyncServiceCallBack(result.getStatus(), object);
    }

    public class ResultEdition {
        @SerializedName("serie")
        private String serie;
        @SerializedName("tome")
        private String number;
        @SerializedName("isbn")
        private String isbn;
        @SerializedName("editeur")
        private String editor;
        @SerializedName("titre")
        private String title;

        public String getSerie() {
            return serie;
        }

        public void setSerie(String serie) {
            this.serie = serie;
        }

        public String getEditor() {
            return editor;
        }

        public void setEditor(String editor) {
            this.editor = editor;
        }

        public String getIsbn() {
            return isbn;
        }

        public void setIsbn(String isbn) {
            this.isbn = isbn;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public class HttpResult {
        private int status;
        private String value;

        public HttpResult(int status, String value) {
            this.status = status;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public int getStatus() {
            return status;
        }
    }
}
