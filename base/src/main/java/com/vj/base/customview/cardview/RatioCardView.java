package com.vj.base.customview.cardview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.cardview.widget.CardView;

import com.vj.base.R;

public class RatioCardView extends CardView {

    private float mRatio = 1f;

    public RatioCardView(Context context) {
        super(context);
    }

    public RatioCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray styled = getContext().obtainStyledAttributes(attrs, R.styleable.RatioCardView);
            mRatio = styled.getFloat(R.styleable.RatioCardView_card_ratio, 1f);
            styled.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = (int) (width * mRatio);
        setMeasuredDimension(width, height);
        measureChildren(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
        super.onMeasure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
    }

}