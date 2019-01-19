package project.navigation.components;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by VINCENT on 27/02/2016.
 */
public class ImageViewEventButton extends ImageViewButton {

    final private AttributeSet attrs;
    final private int defStyle;

    private String text = null;

    public ImageViewEventButton(Context context) {
        super(context);
        attrs = null;
        defStyle = 0;
    }

    public ImageViewEventButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.attrs = attrs;
        this.defStyle = 0;
    }

    public ImageViewEventButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.attrs = attrs;
        this.defStyle = defStyle;
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
        this.refreshDrawableState();
    }


    @Override
    protected Bitmap getShadowedBitmap(final Bitmap bmp) {

        Bitmap bmpResult = null;

        if (bmp != null) {

            if ((text != null) && (text.length() > 0)) {

                final int imgWidth = bmp.getWidth();
                final int imgHeight = bmp.getHeight();

                bmpResult = Bitmap.createBitmap(imgWidth, imgHeight, Bitmap.Config.ARGB_8888);
                final Canvas c = new Canvas(bmpResult);

                try {
                    final AssetManager assets = this.getContext().getAssets();

                    final Typeface fontFace = Typeface.createFromAsset(assets, "fonts/utsaahb.ttf");
                    final Paint textPaint = new Paint();
                    textPaint.setTypeface(fontFace);

                    textPaint.setColor(Color.WHITE);
                    textPaint.setTextSize(40);

                    c.drawBitmap(bmp, 0, 0, null);
                    c.drawText(text, 30, 66, textPaint);

                } catch (Exception e) {
                }

            } else {
                bmpResult = bmp;
            }

            bmpResult = super.getShadowedBitmap(bmpResult);
        }

        return bmpResult;
    }

}
