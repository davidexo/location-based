package de.davidbielenberg.locationbasedservice;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class MapView extends View {

    Paint paint;
    float x;
    float y;
    float size = 30;

    Bitmap map;

    double xMap;
    double yMap;

    Drawable mapdrawable;


    //Map
    double mapWidth;
    double mapHeight;
    double startLat;
    double startLon;
    double endLat;
    double endLon;
    double mapLon;
    double mapLat;



    public MapView(Context context) {
        super(context);
    }

    public MapView (Context context, AttributeSet attrs){
        super(context, attrs);

        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);

        map =  BitmapFactory.decodeResource(getResources(), R.drawable.fhfl_map);
        mapdrawable = new BitmapDrawable(map);

    }

    public void setPosition(double latitude, double longitude){

        mapWidth = this.getWidth();
        mapHeight = this.getHeight();

        mapLon = startLon - endLon;
        mapLat = endLat - startLat;

        //Links-Oben
        startLon = 54.776627;
        startLat = 9.448113;

        //Rechts-Unten
        endLon = 54.774028;
        endLat = 9.453253;


        xMap = ((mapWidth*(startLon-longitude))/mapLon);
        yMap = ((-mapHeight*(startLat-latitude))/mapLat);

        Log.i("PixelWidth", "" + xMap);
        Log.i("PixelHeight", "" + yMap);



        x = (float)xMap;
        y = (float)yMap;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


            Rect bounds = canvas.getClipBounds();
            mapdrawable.setBounds(bounds);
            mapdrawable.draw(canvas);

       // paint = new Paint();
        //paint.setColor(Color.RED);
       // paint.setStyle(Paint.Style.FILL);

        //Kreis
        canvas.drawCircle(y,x,size,paint);


        //invalidate();

    }
}
