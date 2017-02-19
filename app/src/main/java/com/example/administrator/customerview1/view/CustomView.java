package com.example.administrator.customerview1.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.example.administrator.customerview1.util.DIsplayUtil;

public class CustomView extends View {
    private final int dp_50;
    private final int dp_100;
    private Paint yellowPaint;
    private  Paint blackPaint;
    private int radius;
    private int cricleX;
    private int cricleY;

    public CustomView(Context context) {
        this(context, null);
    }

    public CustomView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        blackPaint = new Paint();
        blackPaint.setAntiAlias(true);
        blackPaint.setColor(Color.RED);
        blackPaint.setStyle(Paint.Style.STROKE);


        yellowPaint = new Paint();
        yellowPaint.setAntiAlias(true);
        yellowPaint.setColor(Color.BLACK);
        yellowPaint.setStyle(Paint.Style.FILL);
        yellowPaint.setTextSize(20);

        dp_100 = DIsplayUtil.dp2px(100, context);
        dp_50 = DIsplayUtil.dp2px(50, context);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(dp_100,dp_100);
        }else if (widthMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(dp_100,heightSize);
        }else if (heightMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(widthSize,dp_100);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int width = getWidth() - paddingLeft -paddingRight;
        int height = getHeight() - paddingTop -paddingBottom;
        int min = Math.min(width, height);
        radius = Math.min(width, height)/2;
        RectF rectF = new RectF(0+paddingLeft,0+paddingTop,min+paddingLeft,min+paddingTop);
        cricleX = radius+getPaddingLeft();
        cricleY = radius+getPaddingTop();
        /*for (int i= 0;i < 8;i++){
            if (i%2 == 0){
//                canvas.drawRect(rectF,yellowPaint);
                canvas.drawArc(rectF,0,45,true,blackPaint);
            }else {
                canvas.rotate(45,cricleX,cricleY);
                canvas.drawArc(rectF,0,45,true,yellowPaint);
                canvas.rotate(45,cricleX,cricleY);

            }
        }*/
        canvas.drawArc(rectF,0,45,true,blackPaint);
        Path path = new Path();
        path.addArc(rectF,0,45);
        String s = "东方";
        float textSize = yellowPaint.measureText(s);
        float vOffset = radius / 3.0f;
        float hOffset = (float) (2.0f * Math.PI * radius / 8.0f-textSize)/2.0f;
        //        float hOffset = (float) (2 * Math.PI * (radius) / 8);
        canvas.drawTextOnPath(s,path,hOffset,vOffset,yellowPaint);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), android.R.mipmap.sym_def_app_icon);
        drawIcon(width/2,height/2,radius,-45,bitmap,canvas);
//        canvas.drawBitmap(bitmap,);


    }

    private void drawIcon(int xx,int yy,int mRadius,float startAngle, Bitmap bitmap,Canvas mCanvas)
    {

        int imgWidth = mRadius / 4;

        float angle = (float) Math.toRadians(60 +startAngle);

        float x = (float) (xx + mRadius / 2 * Math.cos(angle));
        float y = (float) (yy + mRadius / 2  * Math.sin(angle));

        // 确定绘制图片的位置
        RectF rect = new RectF(x - imgWidth *3/ 4, y - imgWidth*3 / 4, x + imgWidth
                *3/ 4, y + imgWidth*3/4);


        mCanvas.drawBitmap(bitmap, null, rect, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getAction();

        if (action == MotionEvent.ACTION_UP){
            ObjectAnimator animator = ObjectAnimator.ofFloat(this,"rotation",0,360*8+45);
            animator.setDuration(10000);
            animator.setInterpolator(new AccelerateDecelerateInterpolator());
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {

                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            animator.start();

        }
        return true;
    }
}
