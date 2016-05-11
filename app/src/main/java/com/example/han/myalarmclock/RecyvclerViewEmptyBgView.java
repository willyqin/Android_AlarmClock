package com.example.han.myalarmclock;

import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by Han on 2016/5/10.
 */
public class RecyvclerViewEmptyBgView extends View {
    public RecyvclerViewEmptyBgView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(getResources().getColor(R.color.colorGray));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4);
        paint.setTextSize(48);
        paint.setShader(null);
        canvas.drawText("Hello", 1.0f, 1.0f, paint);
    }


}
