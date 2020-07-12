package com.example.opencvtu;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import org.opencv.android.Utils;
import org.opencv.core.CvException;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class MainActivity extends AppCompatActivity {

    static {
        System.loadLibrary("opencv_java3");
    }

    private ImageView card;
    private Button convert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        card = findViewById(R.id.imageView);
        convert = findViewById(R.id.converGray);
        
        convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card.setImageBitmap(processs(BitmapFactory.decodeResource(getApplicationContext().getResources(),
                        R.drawable.adhar)));
                getColorBitmap((BitmapFactory.decodeResource(getApplicationContext().getResources(),
                        R.drawable.adhar)));
            }
        });


    }


    private void getColorBitmap(Bitmap decodeResource) {
        new Abc().execute(decodeResource);
    }


    private Bitmap processs(Bitmap bmpOriginal) {
//        int width, height;
//        height = bmpOriginal.getHeight();
//        width = bmpOriginal.getWidth();
//
//        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
//        Canvas c = new Canvas(bmpGrayscale);
//        Paint paint = new Paint();
//        ColorMatrix cm = new ColorMatrix();
//        cm.setSaturation(0);
//        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
//        paint.setColorFilter(f);
//        c.drawBitmap(bmpOriginal, 0, 0, paint);
        Mat mat = new Mat();
        Bitmap bmp32 = bmpOriginal.copy(Bitmap.Config.ARGB_8888, true);
        Utils.bitmapToMat(bmp32, mat);
        Mat rgb = new Mat();
        Mat thresh = new Mat();

        Imgproc.cvtColor(mat, rgb, Imgproc.COLOR_BGR2GRAY);

        Imgproc.threshold(rgb,thresh,200,225,Imgproc.THRESH_BINARY);
        Bitmap bmp = null;
        try {
            //Imgproc.cvtColor(seedsImage, tmp, Imgproc.COLOR_RGB2BGRA);

            bmp = Bitmap.createBitmap(thresh.cols(), thresh.rows(), Bitmap.Config.ARGB_8888);
            Utils.matToBitmap(thresh, bmp);
        } catch (CvException e) {
            Log.d("Exception", e.getMessage());
        }
        return bmp;
    }


    public class Abc extends AsyncTask<Bitmap, Void, String> {


        @Override
        protected String doInBackground(Bitmap... bitmaps) {
            for (int x = 0; x < bitmaps[0].getWidth(); x++) {
                for (int y = 0; y < bitmaps[0].getHeight(); y++) {
                    bitmaps[0].getPixel(x, y);
                    int a = Color.red(bitmaps[0].getPixel(x, y));
                    int b = Color.blue(bitmaps[0].getPixel(x, y));
                    int c = Color.green(bitmaps[0].getPixel(x, y));
                    if (a == 255 && b == 255 && c == 255)
                        Log.d("cdfdgb", "I am white");
                }
            }
            return "defg";
        }
    }
}