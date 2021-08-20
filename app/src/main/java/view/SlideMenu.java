package view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Scroller;

public class SlideMenu extends FrameLayout {
    private View menuView,mainView;
    private int menuWidth;
    private Scroller scroller;

    public SlideMenu(Context context) {
        super(context);
        init();
    }
    public SlideMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    private void init(){
        scroller = new Scroller(getContext());
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        menuView = getChildAt(0);
        mainView = getChildAt(1);
        menuWidth = menuView.getLayoutParams().width;
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        menuView.layout(-menuWidth, 0, 0, b);
        mainView.layout(0, 0, r, b);
    }

    private int downX;
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) event.getX();
                int deltaX = moveX - downX;
                int newScrollX = getScrollX() - deltaX;
                if (newScrollX < -menuWidth) newScrollX = -menuWidth;
                if (newScrollX > 0) newScrollX = 0;
                scrollTo(newScrollX, 0);
                downX = moveX;
                break;
            case MotionEvent.ACTION_UP:
                //当滑动距离小于Menu宽度的一半时，平滑滑动到主页面
                if(getScrollX()>-menuWidth/2){
                    closeMenu();
                }else {
                    //当滑动距离大于Menu宽度的一半时，平滑滑动到Menu页面
                    openMenu();
                }
                break;
        }
        return true;
    }
    //关闭menu
    private void closeMenu(){
        scroller.startScroll(getScrollX(),0,0-getScrollX(),0,400);
        invalidate();
    }

    private void openMenu(){
        scroller.startScroll(getScrollX(),0,-menuWidth-getScrollX(),0,400);
        invalidate();
    }

    public void computeScroll(){
        super.computeScroll();
        if(scroller.computeScrollOffset()){
            scrollTo(scroller.getCurrX(),0);
            invalidate();
        }
    }

    public void switchMenu(){
        if(getScrollX()==0){
            openMenu();
        }else {
            closeMenu();
        }
    }



}

