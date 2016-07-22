package com.fengyulong.android_common.view.rotatecircle;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.fengyulong.android_common.R;

public class RotateCircle extends View {

    private static final int DEFAULT_CIRCLE_COLOR = Color.BLUE;
    private static final int DEFAULT_RING_COLOR = Color.BLUE;
    private Paint circlePaint;//实心圆画笔
    private Paint ringPaint;//圆环画笔
    private Paint txtPaint;//字体画笔
    private Paint commentPaint;
    private int circleColor;//实心圆颜色
    private int ringColor;//圆环颜色
    private float circleRadius;//实心圆半径
    private float ringRadius;//圆环半径
    private float circleStrokeWidth;//圆宽度
    private float ringStrokeWidth;//圆环宽度
    private float xCenter;
    private float yCenter;//圆心坐标
    private float txtWidth;
    private float txtHeight;//字体宽高
    private int totalProgress;//总进度
    private int currentProgress;//当前进度
    private String content;


    public RotateCircle(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        initAttrs(context, attrs);
        initVariable();

    }


    private void initVariable() {
        // TODO Auto-generated method stub
        circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(circleColor);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(circleStrokeWidth);

        ringPaint = new Paint();
        ringPaint.setAntiAlias(true);
        ringPaint.setColor(ringColor);
        ringPaint.setStyle(Paint.Style.STROKE);
        ringPaint.setStrokeWidth(circleStrokeWidth);

        txtPaint = new Paint();
        txtPaint.setStyle(Paint.Style.FILL);
        txtPaint.setAntiAlias(true);
        txtPaint.setARGB(255, 255, 255, 255);
        txtPaint.setTextSize((float) (circleRadius / 1.5));

        commentPaint = new Paint();
        commentPaint.setStyle(Paint.Style.FILL);
        commentPaint.setAntiAlias(true);
        commentPaint.setARGB(255, 255, 255, 255);
        commentPaint.setTextSize(circleRadius / 5);

        FontMetrics fm = new FontMetrics();
        txtHeight = (float) Math.ceil(fm.descent - fm.ascent);
    }


    public void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typeArray = context.obtainStyledAttributes(attrs, R.styleable.RotateCircleView);
        circleRadius = typeArray.getDimension(R.styleable.RotateCircleView_circle_radius, 180);
        ringRadius = typeArray.getDimension(R.styleable.RotateCircleView_circle_radius, 180);
        circleColor = typeArray.getColor(R.styleable.RotateCircleView_circle_color, DEFAULT_CIRCLE_COLOR);
        ringColor = typeArray.getColor(R.styleable.RotateCircleView_ring_color, DEFAULT_RING_COLOR);
        circleStrokeWidth = typeArray.getDimension(R.styleable.RotateCircleView_circle_stroke_width, 10);
        ringStrokeWidth = typeArray.getDimension(R.styleable.RotateCircleView_circle_stroke_width, 10);
        typeArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        xCenter = getWidth() / 2;
        yCenter = getHeight() / 2;
        canvas.drawCircle(xCenter, yCenter, circleRadius, circlePaint);

        if (currentProgress > 0) {
            RectF oval = new RectF();
            oval.left = xCenter - circleRadius;
            oval.right = xCenter + circleRadius;
            oval.top = yCenter - circleRadius;
            oval.bottom = yCenter + circleRadius;
            canvas.drawArc(oval, -90, ((float) currentProgress / totalProgress) * 360, false, ringPaint);
            String score = currentProgress + "";
            txtWidth = txtPaint.measureText(score, 0, score.length());
            canvas.drawText(score, xCenter - txtWidth / 2, yCenter - txtHeight / 3, txtPaint);
            FontMetricsInt fontMetrics = commentPaint.getFontMetricsInt();
            Rect bounds = new Rect();
            commentPaint.getTextBounds(content, 0, content.length(), bounds);
            double baseline = (getMeasuredHeight() - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top * 2.2;
            canvas.drawText(content, getMeasuredWidth() / 2 - bounds.width() / 2, (float) baseline, commentPaint);

        }
    }


    public int getTotalProgress() {
        return totalProgress;
    }


    public void setTotalProgress(int totalProgress) {
        this.totalProgress = totalProgress;
    }


    public int getCurrentProgress() {
        return currentProgress;
    }


    public void setCurrentProgress(int currentProgress) {
        this.currentProgress = currentProgress;
        postInvalidate();
    }

    public void setContent(String content) {
        this.content = content;
    }

}
