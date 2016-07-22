package com.fengyulong.android_common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.woke.common.R;

/**
 * 自定义可切换的button
 * 可以表示男、女；是或者否
 *
 * @author Ranfl
 */
public class SwitchButton extends RelativeLayout {

    private RelativeLayout rlBackGround;
    private TextView leftText;
    private ImageView leftImage;
    private TextView rightText;
    private ImageView rightImage;


    /**
     * 背景
     */
    private Drawable openDrawable;
    private Drawable closeDrawable;
    private CharSequence openText;
    private CharSequence closeText;
    private int textSize;

    private boolean isOpen;

    public SwitchButton(Context context) {
        super(context);

        initView(context);
    }

    /**
     * 布局时执行该方法
     *
     * @param context
     * @param attrs
     */
    public SwitchButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SwitchButton);
        openDrawable = ta.getDrawable(R.styleable.SwitchButton_swithOpenBackground);//获取open时的背景
        closeDrawable = ta.getDrawable(R.styleable.SwitchButton_swithCloseBackground);//获取close时的背景
        openText = ta.getText(R.styleable.SwitchButton_swithOpenText);
        closeText = ta.getText(R.styleable.SwitchButton_swithCloseText);
        textSize = ta.getDimensionPixelSize(R.styleable.SwitchButton_swithTextSize, 0);//字体大小
        isOpen = ta.getBoolean(R.styleable.SwitchButton_swithChecked, false);

        setSwitchButtonAppearance();

        ta.recycle();
    }

    public SwitchButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View.inflate(context, R.layout.custom_switch_button, SwitchButton.this);
        rlBackGround = (RelativeLayout) findViewById(R.id.rl_background);
        leftText = (TextView) findViewById(R.id.left_txt);
        leftImage = (ImageView) findViewById(R.id.left_image);
        rightText = (TextView) findViewById(R.id.right_txt);
        rightImage = (ImageView) findViewById(R.id.right_image);
    }

    /**
     * 初始化数据
     */
    private void setSwitchButtonAppearance() {
        if (openDrawable != null) {
            rlBackGround.setBackgroundDrawable(openDrawable);
        }

        if (textSize != 0) {
            leftText.setTextSize(textSize);
            rightText.setTextSize(textSize);
        }

        if (!TextUtils.isEmpty(openText)) {
            leftText.setText(openText);
        }

        if (!TextUtils.isEmpty(closeText)) {
            rightText.setText(closeText);
        }

        setChecked(isOpen);
    }

    /**
     * 选中状态
     *
     * @return
     */
    public boolean getChecked() {
        return isOpen;
    }

    /**
     * 设置选中状态
     *
     * @param checked
     */
    public void setChecked(boolean checked) {
        isOpen = checked;
        if (isOpen) {//开的状态，开的背景，左边文字，右边按钮
            rlBackGround.setBackgroundDrawable(openDrawable);
            leftText.setVisibility(View.VISIBLE);
            leftImage.setVisibility(View.INVISIBLE);
            rightText.setVisibility(View.INVISIBLE);
            rightImage.setVisibility(View.VISIBLE);
        } else {//关的状态，关的背景，左边按钮，右边文字
            rlBackGround.setBackgroundDrawable(closeDrawable);
            leftText.setVisibility(View.INVISIBLE);
            leftImage.setVisibility(View.VISIBLE);
            rightText.setVisibility(View.VISIBLE);
            rightImage.setVisibility(View.INVISIBLE);
        }
    }

}
