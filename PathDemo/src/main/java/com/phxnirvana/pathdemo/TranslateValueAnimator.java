package com.phxnirvana.pathdemo;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import com.phxnirvana.pathdemo.model.Particle;

import java.util.ArrayList;

/**
 * @author Chayne_Shen 2016/10/17 0017.
 */

public class TranslateValueAnimator extends ValueAnimator {
    private View mView;
    private ArrayList<Particle> mParticles;

    public TranslateValueAnimator(View view, ArrayList<Particle> particles) {
        mView = view;
        mParticles = particles;
    }

    public void draw(Canvas canvas, Paint paint) {
        if (mParticles != null && mParticles.size() > 0) {
            for (Particle particle : mParticles) {
                paint.setColor(particle.color);

                float curX = (float) particle.x / particle.duration * getCurrentPlayTime();
                float curY = (float) particle.y / particle.duration * getCurrentPlayTime();
                canvas.drawCircle((float) (curX > particle.x ? particle.x : curX),
                        (float) (curY > particle.y ? particle.y : curY),
                        (float) particle.radius,
                        paint);
            }
        }
    }

    @Override
    public ValueAnimator setDuration(long duration) {
        return super.setDuration(duration);
    }


    @Override
    public void start() {
        super.start();
        mView.invalidate();
    }
}
