package mobappdev.demo.finalexam.problem4;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

import mobappdev.demo.finalexam.R;

public class ImageDownloaderActivity extends AppCompatActivity {
    private static final String TAG = "ImgDler";

    private ImageView mDisplay;
    private DownloadImage mDownloadImageTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate called");
        setContentView(R.layout.activity_image_downloader);

        mDisplay = (ImageView)findViewById(R.id.iv_image);

        mDownloadImageTask = new DownloadImage();
    }

    public void downloadPressed(View view) {
        String url = "https://camo.derpicdn.net/409146886402e82218b6763062d5c3ddbd250db0?url=http%3A%2F%2Fimg04.deviantart.net%2F360e%2Fi%2F2015%2F300%2F9%2Fd%2Ftemmie_by_ilovegir64-d9elpal.png";
        Log.d(TAG, "download initiated");
        mDownloadImageTask.execute(url);
        mDownloadImageTask = new DownloadImage();
    }


    // use this method to scale the image
    private Bitmap getScaledBitmap(String path) {
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);

        return getScaledBitmap(path, size.x, size.y);
    }

    private static Bitmap getScaledBitmap(String path, int width, int height) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        float srcWidth = options.outWidth;
        float srcHeight = options.outHeight;

        Log.d(TAG, "requested width=" + width + "," + "requested height=" + height);
        Log.d(TAG, "srcWidth=" + srcWidth + "," + "srcHeight=" + srcHeight);

        int sampleSize = 1;
        if(srcHeight > height || srcWidth > width ) {
            if(srcWidth > srcHeight) {
                sampleSize = Math.round(srcHeight / height);
            }
            else {
                sampleSize = Math.round(srcWidth / width);
            }
        }

        Log.d(TAG, "sampleSize=" + sampleSize);

        BitmapFactory.Options scaledOptions = new BitmapFactory.Options();
        scaledOptions.inSampleSize = sampleSize;

        //return BitmapFactory.decodeFile(path, scaledOptions);
        return BitmapFactory.decodeFile(path);
    }

    private class DownloadImage extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String filename = null;
            Bitmap image = null;
            try{
                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.connect();
                image = BitmapFactory.decodeStream(connection.getInputStream());
            }
            catch(IOException e){
                Log.e(TAG, "IOException");
            }
            //Save to file system
            filename = "IMG_" + UUID.randomUUID().toString() + ".jpg";
            File file = new File(getApplicationContext().getFilesDir(), filename);

            FileOutputStream stream = null;
            try{
                stream = new FileOutputStream(file);
                image.compress(Bitmap.CompressFormat.PNG, 100, stream);
            }
            catch(IOException e){
                Log.d(TAG, "IOException");
            }
            finally{
                try{
                    stream.close();
                }
                catch(IOException e){
                    Log.d(TAG, "IOException");
                }
            }

            return file.getAbsolutePath();
        }

        @Override
        protected void onPostExecute(String result){
            //update imageview
            if(result != null) {
                Bitmap image = getScaledBitmap(result);
                mDisplay.setImageBitmap(image);
            }
            else{
                Log.d(TAG, "image download failed");
            }
        }
    }
}
