package com.phxnirvana.pathdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author Chayne_Shen 2016/9/30 0030.
 */

public class ProgressCircleView extends View {
    //    宽
    private int with;
    //    高
    private int hight;
    //    间距
    private int pading;
    //    小圆环半径
    private int radius;
    //    圆环宽度
    private int paintWith;
    //    圆环数
    private int count;
    //    当前选中圆环
    private int currentPosition;
    //    正常颜色
    private int normalColor;
    //    选中颜色
    private int clickColor;
    //    画笔
    private Paint paint;


    public ProgressCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
//        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ProgressCircleOldView);
//        pading = a.getDimensionPixelOffset(R.styleable.ProgressCircleOldView_pading, 0);
//        radius = a.getDimensionPixelOffset(R.styleable.ProgressCircleOldView_radius, 10);
//        paintWith = a.getDimensionPixelOffset(R.styleable.ProgressCircleOldView_paintWith, 4);
//        count = a.getInt(R.styleable.ProgressCircleOldView_pie, 5);
//        currentPosition = a.getInt(R.styleable.ProgressCircleOldView_currentPosition, 0);
//        normalColor = a.getColor(R.styleable.ProgressCircleOldView_normalColor, Color.BLUE);
//        clickColor = a.getColor(R.styleable.ProgressCircleOldView_clickColor, Color.RED);

//        a.recycle();
        pading = 0;
        radius = 10;
        paintWith = 4;
        count = 5;
        currentPosition = 0;
        normalColor = Color.BLUE;
        clickColor = Color.RED;
        initPaint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    private void initPaint() {
        paint = new Paint();
        paint.setStrokeWidth(paintWith);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
    }

    public ProgressCircleView(Context context) {
        super(context);
        initPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float[] position = new float[2];
        float[] tan = new float[2];
        float distance;
        Path pathCircle = new Path();
        RectF rectF = new RectF(
                pading + radius,
                pading + radius + paintWith,
                with - pading - radius,
                hight - pading - radius);
        pathCircle.arcTo(rectF, -90, 359);
        PathMeasure pathMeasure = new PathMeasure(pathCircle, false);
        for (int index = 0; index < count; index++) {
            if (currentPosition == index) {
                paint.setColor(clickColor);
            } else {
                paint.setColor(normalColor);
            }
            float allLength = pathMeasure.getLength();
            distance = (allLength / count) * (index);
            pathMeasure.getPosTan(distance, position, tan);
            canvas.drawCircle(position[0], position[1], radius, paint);
        }
    }

    public int getWith() {
        return with;
    }

    public ProgressCircleView setWith(int with) {
        this.with = with;
        return this;
    }

    public int getHight() {
        return hight;
    }

    public ProgressCircleView setHight(int hight) {
        this.hight = hight;
        return this;
    }

    public int getPading() {
        return pading;
    }

    public ProgressCircleView setPading(int pading) {
        this.pading = pading;
        return this;
    }

    public int getRadius() {
        return radius;
    }

    public ProgressCircleView setRadius(int radius) {
        this.radius = radius;
        return this;
    }

    public int getPaintWith() {
        return paintWith;
    }

    public ProgressCircleView setPaintWith(int paintWith) {
        this.paintWith = paintWith;
        return this;
    }

    public int getCount() {
        return count;
    }

    public ProgressCircleView setCount(int count) {
        this.count = count;
        return this;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public ProgressCircleView setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
        invalidate();
        return this;
    }

    public int getNormalColor() {
        return normalColor;
    }

    public ProgressCircleView setNormalColor(int normalColor) {
        this.normalColor = normalColor;
        return this;
    }

    public int getClickColor() {
        return clickColor;
    }

    public ProgressCircleView setClickColor(int clickColor) {
        this.clickColor = clickColor;
        return this;
    }

    public Paint getPaint() {
        return paint;
    }

    public ProgressCircleView setPaint(Paint paint) {
        this.paint = paint;
        return this;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        with = getWidth();
        hight = getHeight();
    }
}
