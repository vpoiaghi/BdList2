package project.services;

import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by VINCENT on 03/02/2019.
 *
 */
public class ServiceGoogleBooks extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... bookURLs) {
        //request book info

        StringBuilder bookBuilder = new StringBuilder();

        for (String bookSearchURL : bookURLs) {
            //search urls
            HttpClient bookClient = new DefaultHttpClient();

            try {
                //get the data
                HttpGet bookGet = new HttpGet(bookSearchURL);
                HttpResponse bookResponse = bookClient.execute(bookGet);

                StatusLine bookSearchStatus = bookResponse.getStatusLine();
                if (bookSearchStatus.getStatusCode()==200) {
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
        }

        return bookBuilder.toString();
    }

}
