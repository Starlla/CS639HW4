package com.example.chuyutongcs639hw4;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

public class CircleAnimationView extends View {
    Paint mCirclePaint;
    int mCircleColor;
    float mCircleRadius;
    float mCircleSpeed;
    float mCircleCurrentPosition;
    float screenWidth;

    int centerX;
    int centerY;

    private final long COUNT_INTERVAL = 16l;
    int moveDirection;

    Runnable mUpdateCounterRunnable = () -> {
        updateCirclePosition();
    };

    public CircleAnimationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleAnimationView);
        mCircleColor = a.getColor(R.styleable.CircleAnimationView_circleColor, Color.GRAY);
        mCircleRadius = a.getDimension(R.styleable.CircleAnimationView_circleRadius, dpToPx(20));
        mCircleSpeed = a.getDimension(R.styleable.CircleAnimationView_circleSpeed, dpToPx(5));
        a.recycle();
        initializePaints();
        mCircleCurrentPosition = mCircleRadius;
        moveDirection = 1;

    }

    private void initializePaints() {
        mCirclePaint = new Paint();
        mCirclePaint.setColor(mCircleColor);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        centerX = getWidth()/2;
        centerY = getHeight()/2;
        screenWidth = getWidth();
        canvas.drawCircle(mCircleCurrentPosition,centerY, mCircleRadius,mCirclePaint);
        postDelayed(mUpdateCounterRunnable, COUNT_INTERVAL);
    }


    private void updateCirclePosition(){
        if(moveDirection == 1) {
            if (mCircleCurrentPosition < (screenWidth - mCircleRadius - mCircleSpeed))
                mCircleCurrentPosition = mCircleCurrentPosition + mCircleSpeed;
            else{
                mCircleCurrentPosition = screenWidth - mCircleRadius;
                moveDirection *= -1;
            }

        }
        else if(moveDirection == -1){
            if (mCircleCurrentPosition > (0 + mCircleRadius + mCircleSpeed))
                mCircleCurrentPosition = mCircleCurrentPosition - mCircleSpeed;
            else {
                mCircleCurrentPosition = mCircleRadius;
                moveDirection *= -1;
            }
        }
    invalidate();

    }

    public void setCircleColor(int color){
        mCircleColor = color;
        mCirclePaint.setColor(mCircleColor);
        invalidate();
    }

    public void setCircleRadius(int radius){
        mCircleRadius = dpToPx(radius);
        invalidate();
    }


    public void setCircleSpeed(int speed){
        mCircleSpeed = dpToPx(speed);
        invalidate();
    }

    public void stopCircle() {
        removeCallbacks(mUpdateCounterRunnable);
    }
    private int dpToPx(int dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getResources().getDisplayMetrics());
    }


}
