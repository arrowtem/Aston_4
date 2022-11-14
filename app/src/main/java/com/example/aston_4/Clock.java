package com.example.aston_4;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;

public class Clock extends View {
    private ArrayList<Integer> radiusOfStartHand = new ArrayList<>();
    private int height, width = 0;
    private int padding = 0;
    private int fontSize = 0;
    private int numeralSpacing = 0;
    private int handTruncation, hourHandTruncation = 0;
    private int radius = 0;
    private Paint paint;
    private boolean isInit;
  
    private Rect rect = new Rect();

    public Clock(Context context) {
        super(context);
    }

    public Clock(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Clock(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initClock() {
        height = getHeight();
        width = getWidth();
        padding = numeralSpacing + 50;
        fontSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 13,
                getResources().getDisplayMetrics());
        radiusOfStartHand.add(430) ;
        radiusOfStartHand.add(410) ;
        radiusOfStartHand.add(390) ;
        int min = Math.min(height, width);
        radius = min / 2 - padding;
        paint = new Paint(Paint.ANTI_ALIAS_FLAG );
        isInit = true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!isInit) {
            initClock();
        }
        canvas.drawColor(Color.WHITE);
        drawCircle(canvas);
        drawNumeral(canvas);
        drawHands(canvas);
        postInvalidateDelayed(500);
    }

    private void drawHand(Canvas canvas, double loc, boolean isHour,int color,int widthOfLine,int length,int num) {
        double angle = Math.PI * loc / 30 - Math.PI / 2;
        int handRadius = isHour ? radius - handTruncation - hourHandTruncation : radius - handTruncation;
        paint.setColor(color);
        paint.setStrokeWidth(widthOfLine);
        canvas.drawLine((float) (width / 2 + Math.cos(angle) * (handRadius-radiusOfStartHand.get(num))), (float) (height / 2 + Math.sin(angle) * (handRadius-radiusOfStartHand.get(num))),
                (float) (width / 2+ Math.cos(angle) * (handRadius-length)),
                (float) (height / 2 + Math.sin(angle) * (handRadius-length)),
               paint);
    }

    private void drawHands(Canvas canvas) {
        Calendar c = Calendar.getInstance();
        float hour = c.get(Calendar.HOUR_OF_DAY);
        hour = hour > 12 ? hour - 12 : hour;
        drawHand(canvas, hour+ ( 5.0f * c.get(Calendar.MINUTE)/60  ), true,getResources().getColor(android.R.color.black),25,170,0);
        drawHand(canvas, c.get(Calendar.MINUTE), false,getResources().getColor(R.color.red),20,200,1);
        drawHand(canvas, c.get(Calendar.SECOND), false,getResources().getColor(R.color.blue),15,200,2);
    }

    private void drawNumeral(Canvas canvas) {
        paint.setTextSize(fontSize);
        paint.setStrokeWidth(25);
        for(int i = 0;i<12;i++) {
            double angle = Math.PI / 6 * i ;
            int x_end = (int) (width / 2 + Math.cos(angle) * (radius-10) - rect.width() / 2);
            int y_end = (int) (height / 2 + Math.sin(angle) * (radius-10) + rect.height() / 2);
            int x_start = (int) (width / 2 + Math.cos(angle) * (radius+35) - rect.width() / 2);
            int y_start = (int) (height / 2 + Math.sin(angle) * (radius+35) + rect.height() / 2);
            canvas.drawLine(x_start,y_start, x_end, y_end, paint);
        }
    }



    private void drawCircle(Canvas canvas) {
        paint.reset();
        paint.setColor(getResources().getColor(android.R.color.black));
        paint.setStrokeWidth(20);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(width / 2, height / 2, radius + padding - 10, paint);
    }
}
