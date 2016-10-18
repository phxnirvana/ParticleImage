package com.phxnirvana.pathdemo.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;

import com.phxnirvana.pathdemo.R;
import com.phxnirvana.pathdemo.TranslateValueAnimator;
import com.phxnirvana.pathdemo.model.Particle;

import java.util.ArrayList;

/**
 * @author Chayne_Shen 2016/9/30 0030.
 */

public class PathView extends View {
    Bitmap mBitmap;
    Paint mPaint;
    float[] mFloats = new float[2];
    int mWidth;
    int mHeight;
    int mDrawType;
    TranslateValueAnimator mValueAnimator;

    public PathView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        mPaint = new Paint();
        mPaint.setColor(Color.RED);//颜色
        mPaint.setStrokeWidth(10f);//画笔宽度
        mPaint.setStyle(Paint.Style.STROKE);//类型
        mPaint.setStrokeCap(Paint.Cap.BUTT);//笔触
        test1(mFloats);
    }

    public void setBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
        mPaint.reset();
        mPaint.setStyle(Paint.Style.FILL);
        calculate();

    }

    ArrayList<Particle> mParticles = new ArrayList<>();

    private void calculate() {
        int height = mBitmap.getHeight();
        int width = mBitmap.getWidth();
        int cols = 72;
        int rows = 72;
        int devideWidth = width / cols;
        int devideHeight = height / rows;
        Log.d("PathView", "height:" + height);
        Log.d("PathView", "width:" + width);
//        取每块的像素
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                Particle particle = new Particle();
                particle.x = 984 / 2 - 72 + i * devideWidth + (Math.random() - 0.5) * 20;
                particle.y = j * devideHeight + (Math.random() - 0.5) * 20;
                particle.color = mBitmap.getPixel(i * devideWidth, j * devideHeight);
                particle.radius = Math.random() * 2;
                particle.interval = Math.random();
                particle.duration = (int) (Math.random() * 5000);
                mParticles.add(particle);
//                }
            }
        }
        mValueAnimator = new TranslateValueAnimator(this, mParticles);
        mValueAnimator.setFloatValues(0, 1);
        mValueAnimator.setDuration(5000);
//        mValueAnimator.setInterpolator();
        mValueAnimator.addUpdateListener(
                (ValueAnimator valueAnimator) -> invalidate());
        mValueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mValueAnimator.setCurrentPlayTime(mValueAnimator.getDuration());
                invalidate();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    public void start() {
        mValueAnimator.start();

    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        setMeasuredDimension(200, 200);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
    }

    public int getDrawType() {
        return mDrawType;
    }

    public PathView setDrawType(int drawType) {
        mDrawType = drawType;
        return this;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        pathCircle(canvas);
//        mWidth = getWidth();
//        mHeight = getHeight();
        Log.d("PathView", "mDrawType:" + mDrawType);
        mPaint.setColor(Color.RED);//颜色
        mPaint.setStrokeWidth(10f);//画笔宽度
        mPaint.setStyle(Paint.Style.STROKE);//类型
        mPaint.setStrokeCap(Paint.Cap.BUTT);//笔触
        switch (mDrawType) {
            case R.id.btn1:
                pathLine(canvas);
                break;
            case R.id.btn2:
                pathQuad(canvas);
                break;
            case R.id.btn3:
                pathTriangle(canvas);
                break;
            case R.id.btn4:
                pathRect(canvas);
                break;
            case R.id.btn5:
                pathArc(canvas);
                break;
            case R.id.btn6:
                pathCircle(canvas);
                break;
            case R.id.btn7:
                pathFakeProgressBar(canvas);
                break;
            case R.id.btn8:
                break;
            case R.id.btn9:
                break;
            case R.id.btn10:
                mPaint.reset();
                mValueAnimator.draw(canvas, mPaint);

                break;
        }
    }

    private void pathLine(Canvas canvas) {
        Log.d("PathView", "line");
        Path path = new Path();
        path.lineTo(100, 100);
//        path.moveTo(200, 100);
//        path.close();
        canvas.drawPath(path, mPaint);
    }

    private void pathQuad(Canvas canvas) {
        Path path = new Path();
        path.moveTo(0, 100);
        path.quadTo(100, 0, 200, 100);
        canvas.drawPath(path, mPaint);
    }

    private void pathTriangle(Canvas canvas) {
        Path path = new Path();
        path.moveTo(100, 100);
        path.lineTo(100, 200);
        path.lineTo(200, 200);
        path.close();
        canvas.drawPath(path, mPaint);
    }

    private void pathRect(Canvas canvas) {
        RectF rectF = new RectF(200, 200, 400, 400);
        Path path = new Path();
//        path.addRect(rectF, Path.Direction.CCW);
        path.addRoundRect(rectF, 20, 20, Path.Direction.CW);
        path.setLastPoint(500, 500);                // <-- 重置最后一个点的位置
        canvas.drawPath(path, mPaint);
    }

    private void pathArc(Canvas canvas) {
        Path path = new Path();
        path.addArc(200, 100, 400, 300, -180, 90);
        canvas.drawPath(path, mPaint);
    }

    private void pathCircle(Canvas canvas) {
        Path path = new Path();
        path.addCircle(100, 100, 50, Path.Direction.CW);
        canvas.drawPath(path, mPaint);
        path.isConvex();
    }

    @Override
    public void startAnimation(Animation animation) {
        super.startAnimation(animation);
    }

    double progress;
    float[] position = new float[2];
    float[] tan = new float[2];

    private void paintRectangle(Canvas canvas) {
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.STROKE);
        Path path = new Path();
        RectF rectFarc = new RectF(200, 300, 400, 700);
        path.addRect(rectFarc, Path.Direction.CCW);
        PathMeasure measure = new PathMeasure(path, false);
        progress = progress < 1 ? progress + 0.005d : 0;
        mPaint.setColor(Color.YELLOW);
        measure.getPosTan((int) (measure.getLength() * progress), position, tan);
        canvas.drawPath(path, mPaint);

        mPaint.setColor(Color.RED);
        Path path1 = new Path();
        Path path2 = new Path();
        path1.moveTo(position[0] - 20, position[1] + 20);
        path1.lineTo(position[0], position[1]);
        path1.lineTo(position[0] + 20, position[1] + 20);
        path1.close();

        float degrees = (float) (Math.atan2(tan[1], tan[0]) * 180.0 / Math.PI);
        Matrix matrix = new Matrix();
        matrix.setRotate(degrees + 90, position[0], position[1]);
        path2.addPath(path1, matrix);
        measure.getSegment(-1000, (int) (measure.getLength() * progress), path2, true);
        mPaint.setColor(Color.BLUE);
        canvas.drawPath(path2, mPaint);
        invalidate();
    }

    private void drawBoom(Canvas canvas) {
        int height = mBitmap.getHeight();
        int width = mBitmap.getWidth();
        mPaint.reset();
        mPaint.setStyle(Paint.Style.FILL);

    }

    private void pathFakeProgressBar(Canvas canvas) {
        mPaint.setStrokeWidth(20);
        mPaint.setStyle(Paint.Style.STROKE);
//        圆
        Path path = new Path();
        path.addCircle(600, 400, 100, Path.Direction.CCW);
        PathMeasure measure = new PathMeasure(path, false);
        progress = progress < 1 ? progress + 0.005 : 0;
        Matrix matrix = new Matrix();
        mPaint.setColor(Color.YELLOW);
        measure.getPosTan((int) (measure.getLength() * progress), position, tan);
        canvas.drawPath(path, mPaint);

        mPaint.setColor(Color.RED);
//        箭头
        Path path1 = new Path();
        path1.moveTo(position[0] - 20, position[1] + 20);
        path1.lineTo(position[0], position[1]);
        path1.lineTo(position[0] + 20, position[1] + 20);
//        path1.close();

        Path path2 = new Path();
        float degrees = (float) (Math.atan2(tan[1], tan[0]) * 180.0 / Math.PI);
        matrix.setRotate(degrees + 90, position[0], position[1]);
        path2.addPath(path1, matrix);
//        进度线
        measure.getSegment(0, (int) (measure.getLength() * progress), path2, true);
        mPaint.setColor(Color.BLUE);
        canvas.drawPath(path2, mPaint);
        invalidate();
    }

    private void test1(float[] floats) {
        floats[0] = 123;
    }

    /**
     * 绘制简单的pathline
     */
    private void paintSimplePath(Canvas canvas) {
        Path path = new Path();
        path.lineTo(200, 0);
//        移动位置
        path.moveTo(200, 300);
//        修改最后一个点位置
//        path.setLastPoint(200,300);
        path.lineTo(200, 200);
        path.close();
        canvas.drawPath(path, mPaint);
    }

    /**
     * 绘制基本图形+长方形*****
     *
     * @param canvas
     */
    private void paintMorePath(Canvas canvas) {
        Path rect = new Path();
        Path circle = new Path();
        RectF rectF = new RectF(200, 200, 400, 400);
        RectF rectFarc = new RectF(200, 100, 400, 300);
        circle.addCircle(300, 200, 50, Path.Direction.CCW);
//        rect.addRect(rectF, Path.Direction.CCW);
        rect.addPath(circle);
        rect.moveTo(300, 200);
        rect.arcTo(rectFarc, -180, 90);
        canvas.drawPath(rect, mPaint);
    }


    /**
     * 画出xy坐标系
     */
    private void paintXy(Canvas canvas) {
        Path path = new Path();
        path.moveTo(0, 50);
        path.lineTo(0, 50);
        path.lineTo(50, 0);
        path.lineTo(100, 50);
        path.moveTo(50, 0);
        path.lineTo(50, 300);
        path.lineTo(300, 300);
        path.lineTo(250, 250);
        path.moveTo(300, 300);
        path.lineTo(250, 350);
        canvas.drawPath(path, mPaint);
    }

    public void recycle() {
        if (mBitmap != null) {
            mBitmap.recycle();
        }
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }
}
