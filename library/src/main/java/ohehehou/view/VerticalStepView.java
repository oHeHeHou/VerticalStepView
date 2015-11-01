package ohehehou.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by caoyuhong
 * Created Date 15/8/26
 */
public class VerticalStepView extends View {

    private final int DEFAULT_LABEL_COLOR = Color.BLACK;
    private final int DEFAULT_STEP_COLOR = Color.GRAY;
    private final int DEFAULT_PROGRESS_COLOR = Color.BLACK;
    private final int DEFAULT_LABEL_MARGIN = 8;
    private final int DEFAULT_LABEL_SPACE = 5;
    private final int DEFAULT_LINE_LENGTH = 40;
    private final int DEFAULT_LABEL_SIZE = 14;
    private final int DEFAULT_SUB_LABEL_SIZE = 10;
    private final int DEFAULT_CIRCLE_RADIUS = 8;
    private final int DEFAULT_LINE_THICKNESS = 5;

    private List<Item> itemList = new ArrayList<Item>();
    private Paint mGraphPaint;
    private Paint mProgressGraphPaint;
    private Paint mLabelPaint;
    private Paint mSubLabelPaint;
    private int mDefaultColor;
    private int mCircleRadius;
    private String longestLabel = "";
    private String longestSubLabel = "";
    private int mLabelTextSize;
    private int mSubLabelTextSize;
    private int mLabelTextColor;
    private int mSubLabelTextColor;
    private int mProgressColor;
    private int progress;
    private float mLabelHeight;
    private float mSubLabelHeight;
    private int mLineThickness;
    private DisplayMetrics mDisplayMetrics;


    public VerticalStepView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.VerticalStepView,
                0, 0
        );

        mDisplayMetrics = getResources().getDisplayMetrics();

        try {
            mCircleRadius = a.getDimensionPixelSize(R.styleable.VerticalStepView_circleRadius,
                    (int) (DEFAULT_CIRCLE_RADIUS * mDisplayMetrics.density));
            mLabelTextSize = a.getDimensionPixelSize(R.styleable.VerticalStepView_labelTextSize,
                    (int) (DEFAULT_LABEL_SIZE * mDisplayMetrics.density));
            mSubLabelTextSize = a.getDimensionPixelSize(R.styleable.VerticalStepView_subLabelTextSize,
                    (int) (DEFAULT_SUB_LABEL_SIZE * mDisplayMetrics.density));
            mDefaultColor = a.getColor(R.styleable.VerticalStepView_normalColor, DEFAULT_STEP_COLOR);
            mLineThickness = a.getDimensionPixelSize(R.styleable.VerticalStepView_lineThickness,
                    (int) (DEFAULT_LINE_THICKNESS * mDisplayMetrics.density));
            mLabelTextColor = a.getColor(R.styleable.VerticalStepView_labelTextColor, DEFAULT_LABEL_COLOR);
            mSubLabelTextColor = a.getColor(R.styleable.VerticalStepView_subLabelTextColor, DEFAULT_LABEL_COLOR);
            mProgressColor = a.getColor(R.styleable.VerticalStepView_progressColor, DEFAULT_PROGRESS_COLOR);
        } finally {
            a.recycle();
        }

        init();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
        invalidate();
    }

    public void addStepItem(String label, String subLabel) {
        getMaxLabelText(label);
        getMaxSubLabelText(subLabel);
        Item item = new Item();
        item.label = label;
        item.subLabel = subLabel;
        itemList.add(item);
        invalidate();
    }

    public void addStepItem(String label) {
        getMaxLabelText(label);
        Item item = new Item();
        item.label = label;
        itemList.add(item);
        invalidate();
    }

    private void getMaxLabelText(String str) {
        if (str.length() > longestLabel.length()) {
            longestLabel = str;
        }
    }

    private void getMaxSubLabelText(String str) {
        if (str.length() > longestSubLabel.length()) {
            longestSubLabel = str;
        }
    }

    private void init() {
        mLabelPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLabelPaint.setTextSize(mLabelTextSize);
        mLabelPaint.setColor(mLabelTextColor);
        mSubLabelPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSubLabelPaint.setTextSize(mSubLabelTextSize);
        mSubLabelPaint.setColor(mSubLabelTextColor);
        mGraphPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mGraphPaint.setStyle(Paint.Style.FILL);
        mGraphPaint.setColor(mDefaultColor);
        mGraphPaint.setStrokeWidth(mLineThickness);
        mProgressGraphPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mProgressGraphPaint.setStyle(Paint.Style.FILL);
        mProgressGraphPaint.setColor(mProgressColor);
        mProgressGraphPaint.setStrokeWidth(mLineThickness);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec),
                measureHeight(heightMeasureSpec));
    }

    private int measureHeight(int measureSpec) {
        int result = 0;
        int specSize = MeasureSpec.getSize(measureSpec);
        int specMode = MeasureSpec.getMode(measureSpec);
        mLabelHeight = -mLabelPaint.ascent() + mLabelPaint.descent();
        mSubLabelHeight = -mSubLabelPaint.ascent() + mSubLabelPaint.descent();

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            if (itemList.size() > 1) {

                float topDiff = calcTopDiff();
                float bottomDiff = calcBottomDiff();
                int minHeight = (int) (getPaddingTop() + getPaddingBottom()
                        + DEFAULT_LINE_LENGTH * mDisplayMetrics.density * (itemList.size() - 1)
                        + mCircleRadius * 2 * itemList.size()
                        + topDiff + bottomDiff);
                result = Math.min(specSize, minHeight);
            }
        }

        return result;
    }

    private int measureWidth(int measureSpec) {
        int result;
        int specSize = MeasureSpec.getSize(measureSpec);
        int specMode = MeasureSpec.getMode(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = getPaddingLeft() + getPaddingRight() + mCircleRadius * 2
                    + (int) (DEFAULT_LABEL_MARGIN * mDisplayMetrics.density)
                    + Math.max((int) mLabelPaint.measureText(longestLabel),
                    (int) mSubLabelPaint.measureText(longestSubLabel));
            //wrap_content
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(specSize, result);
            }
        }
        return result;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

        if (itemList.size() > 1) {
            //根据第一个节点和最后一个节点上，字体高度和节点圆圈的高度差，计算非文字部分的高度
            float topDiff = calcTopDiff();
            float bottomDiff = calcBottomDiff();
            float heigtWithoutLabel = getHeight() - topDiff - bottomDiff;
            //圆心之间的间距
            float circleCentreDist = (heigtWithoutLabel - mCircleRadius * 2) / (itemList.size() - 1);
            for (int i = 0; i < itemList.size(); i++) {
                Item item = itemList.get(i);
                item.circleCx = getPaddingLeft() + mCircleRadius;
                item.circleCy = getPaddingTop() + mCircleRadius + topDiff + circleCentreDist * i;
                item.labelCx = item.circleCx + mCircleRadius
                        + DEFAULT_LABEL_MARGIN * mDisplayMetrics.density;
                //包含主标题和副标题
                if (item.subLabel != null && item.subLabel.length() != 0) {
                    item.subLabelCx = item.labelCx;
                    item.subLabelCy = item.circleCy + (mLabelHeight + mSubLabelHeight
                            + mDisplayMetrics.density * DEFAULT_LABEL_SPACE) / 2
                            - mSubLabelPaint.descent();
                    item.labelCy = item.subLabelCy + mSubLabelPaint.ascent()
                            - mDisplayMetrics.density * DEFAULT_LABEL_SPACE
                            - mLabelPaint.descent();
                } else {
                    //只包含主标题
                    item.labelCy = item.circleCy + mLabelHeight / 2
                            - mLabelPaint.descent();
                }
            }
        }
    }

    /**
     * 纵向绘制时，根据第一个节点的圆圈和它
     * 右侧字体的高度差，计算这个圆圈和顶部的距离
     *
     * @return
     */
    private float calcTopDiff() {
        float firstLabelHeight;
        Item first = itemList.get(0);
        firstLabelHeight = calcLabelHeight(first);
        return mCircleRadius - firstLabelHeight / 2 > 0 ? 0 :
                firstLabelHeight / 2 - mCircleRadius;
    }

    /**
     * 纵向绘制时，根据最后一个节点的圆圈和它
     * 右侧字体的高度差，计算这个圆圈和底部的距离
     *
     * @return
     */
    private float calcBottomDiff() {
        float lastLabelHeight;
        Item last = itemList.get(itemList.size() - 1);
        lastLabelHeight = calcLabelHeight(last);
        return mCircleRadius - lastLabelHeight / 2 > 0 ? 0 :
                lastLabelHeight / 2 - mCircleRadius;
    }

    private float calcLabelHeight(Item item) {
        float height;
        float labelSpace =
                DEFAULT_LABEL_SPACE * mDisplayMetrics.density;
        if (item.subLabel != null) {
            height = labelSpace + mLabelHeight + mSubLabelHeight;
        } else {
            height = mLabelHeight;
        }

        return height;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < itemList.size() - 1; i++) {
            Item item = itemList.get(i);
            Item itemNext = itemList.get(i + 1);
            if (progress > 1 && i < progress - 1) {
                canvas.drawLine(item.circleCx, item.circleCy, itemNext.circleCx,
                        itemNext.circleCy, mProgressGraphPaint);
            } else {
                canvas.drawLine(item.circleCx, item.circleCy, itemNext.circleCx,
                        itemNext.circleCy, mGraphPaint);
            }
        }

        for (int i = 0; i < itemList.size(); i++) {
            Item item = itemList.get(i);
            if (progress > 0 && i <= progress - 1) {
                canvas.drawCircle(item.circleCx, item.circleCy, mCircleRadius, mProgressGraphPaint);
            } else {
                canvas.drawCircle(item.circleCx, item.circleCy, mCircleRadius, mGraphPaint);
            }
            canvas.drawText(item.label, item.labelCx, item.labelCy, mLabelPaint);
            if (item.subLabel != null && item.subLabel.length() != 0) {
                canvas.drawText(item.subLabel, item.subLabelCx, item.subLabelCy, mSubLabelPaint);
            }
        }
    }

    private class Item {
        public String label;
        public String subLabel;
        public float circleCx;
        public float circleCy;
        public float labelCx;
        public float labelCy;
        public float subLabelCx;
        public float subLabelCy;
    }
}