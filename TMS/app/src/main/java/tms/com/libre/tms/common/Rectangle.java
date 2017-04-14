package tms.com.libre.tms.common;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by GL62 on 4/14/2017.
 */

public class Rectangle extends android.support.v7.widget.AppCompatImageView {

    public Rectangle(Context context) {
        super(context);
    }

    public Rectangle(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Rectangle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (getMeasuredWidth() > getMeasuredHeight()) {
            setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
        } else {
            setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight());
        }

    }
}
