package project.navigation.components;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by VINCENT on 27/02/2016.
 */
public class ImageViewButton extends ImageView {

    private static final int SHADOW_SIZE = 8;

    public ImageViewButton(Context context) {
        super(context);
    }

    public ImageViewButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageViewButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        Drawable drawable = getDrawable();

        if (drawable == null) {
            return;
        }

        if (getWidth() == 0 || getHeight() == 0) {
            return;
        }

        Bitmap b = ((BitmapDrawable)drawable).getBitmap() ;

        Bitmap shadowedBitmap = this.getShadowedBitmap(b);
        canvas.drawBitmap(shadowedBitmap, 0, 0, null);
    }

    protected Bitmap getShadowedBitmap(final Bitmap bmp) {

        final int imgWidth = this.getWidth();
        final int imgHeight = this.getHeight();

        final int bmpWidth = bmp.getWidth();
        final int bmpHeight = bmp.getHeight();
        final Rect bmpRect = new Rect(0, 0, bmpWidth, bmpHeight);

        final int borderRight = imgWidth - SHADOW_SIZE;
        final int borderBottom = imgHeight - SHADOW_SIZE;
        final Rect borderRect = new Rect(0, 0, borderRight, borderBottom);

        final Paint borderPaint = new Paint();
        borderPaint.setColor(Color.BLACK);

        final Rect dstRect = new Rect(1, 1, borderRight - 1, borderBottom - 1);

        final Rect shadowRect = new Rect(SHADOW_SIZE, SHADOW_SIZE, imgWidth, imgHeight);
        final Paint shadowPaint = new Paint();
        //shadowPaint.setShader(new RadialGradient( imgWidth/2f, imgHeight/2f, (float)imgWidth, Color.GRAY, Color.WHITE, Shader.TileMode.CLAMP));
        shadowPaint.setARGB(50,0,0,0);

        final Bitmap bmpResult = Bitmap.createBitmap(imgWidth, imgHeight, Bitmap.Config.ARGB_8888);
        final Canvas c = new Canvas(bmpResult);

        c.drawRect(shadowRect, shadowPaint);
        c.drawRect(borderRect, borderPaint);
        c.drawBitmap(bmp, bmpRect, dstRect, null);

        return bmpResult;
    }

}
