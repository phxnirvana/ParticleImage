package com.phxnirvana.pathdemo.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.phxnirvana.pathdemo.TranslateValueAnimator;
import com.phxnirvana.pathdemo.model.Particle;

import java.util.ArrayList;

/**
 * @author Chayne_Shen 2016/10/18 0018.
 */

public class BoomView extends View {
    public static final int DEFAULT_DURATION = 2000;
    private int mWidth;
    private int mHeight;
    private Paint mPaint;
    private Bitmap mBitmap;
    private int[] mDividers;
    private TranslateValueAnimator mValueAnimator;
    private ArrayList<Particle> mParticles = new ArrayList<>();
    private int mDuration = DEFAULT_DURATION;

    public BoomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
        init();
    }

    public BoomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr, 0);
        init();
    }

    public BoomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr,
                     int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mValueAnimator = new TranslateValueAnimator(this, mParticles);
        mValueAnimator.setIntValues(0, mDuration);
        mValueAnimator.setDuration(mDuration);
        mValueAnimator.addUpdateListener(
                (ValueAnimator valueAnimator) -> invalidate());
        mValueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mValueAnimator.setCurrentPlayTime(mDuration);
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

    private void calculate() {
        int height = mBitmap.getHeight();
        int width = mBitmap.getWidth();
        int cols;
        int rows;
        if (mDividers != null) {
            cols = mDividers[0];
            rows = mDividers[1];
        } else {
            cols = height / 2;
            rows = width / 2;
        }
        int divideWidth = width / cols;
        int divideHeight = height / rows;
//        取每块的像素
        // TODO: 2016/10/18 0018 这里可以进一步优化，如根据透明度取舍等 
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                Particle particle = new Particle();
                particle.x = mWidth / 3 + i * divideWidth + (Math.random() - 0.5) * 20;
                particle.y = j * divideHeight + (Math.random() - 0.5) * 20;
                particle.color = mBitmap.getPixel(i * divideWidth, j * divideHeight);
                particle.radius = Math.random() * 2;
//                particle.interval = Math.random();
                particle.duration = (int) (Math.random() * mDuration);
                mParticles.add(particle);
            }
        }
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        setMeasuredDimension(200, 200);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
    }

    /**
     * 执行动画
     */
    public void startBoom() {
        calculate();
        if (mValueAnimator != null) {
            mValueAnimator.start();
        } else {
            throw new IllegalStateException("ValueAnimator can't be null!");
        }
    }

    public BoomView setRepeatCount(int repeatCount) {
        mValueAnimator.setRepeatCount(repeatCount);
        return this;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        pathCircle(canvas);
        mValueAnimator.draw(canvas, mPaint);
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public BoomView setBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
        return this;
    }

    public int getDuration() {
        return mDuration;
    }

    /**
     * 持续时间
     * @param duration 单位ms
     * @return this
     */
    public BoomView setDuration(int duration) {
        mDuration = duration;
        mValueAnimator.setDuration(mDuration);
        return this;
    }

    public int[] getDividers() {
        return mDividers;
    }

    public BoomView setDividers(int[] dividers) {
        mDividers = dividers;
        return this;
    }
}
