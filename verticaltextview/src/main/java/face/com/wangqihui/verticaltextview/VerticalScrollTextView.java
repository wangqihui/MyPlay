package face.com.wangqihui.verticaltextview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import android.os.Handler;


/**
 * Created by wangqihui on 2016/2/25.
 * 跑马灯式的 textview 自动滚动效果实现
 */
public class VerticalScrollTextView extends TextView {
    private Paint mPaint;  //普通的画笔
    private Paint mPaint_focuse;//选中的画笔
    private float mX;
    private float mY;
    public int index=0; //记录选中的列表位置
    public List<Sentence> sentencesList;  //列表数据
    private float middleY;// y轴中间
    private static final int DY = 40; // 每一行的间隔
    public float mTouchHistoryY;

    public VerticalScrollTextView(Context context) {
        super(context);
        init();
    }

    public VerticalScrollTextView(Context context, AttributeSet attr) {
        super(context, attr);
        init();
    }
    public VerticalScrollTextView(Context context, AttributeSet attr, int i) {
        super(context, attr, i);
        init();
    }


     /*
      * 初始化 画笔和数据
     * */
    private void init(){
        setFocusable(true);
        if(null==sentencesList){
            sentencesList =new ArrayList<Sentence>() ;
            Sentence sentence =new Sentence(0,"暂无功告");
            sentencesList.add(sentence);

        }
        // 非高亮部分
        mPaint =new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(16);
        mPaint.setTypeface(Typeface.SERIF);
        // 高亮部分
        mPaint_focuse =new Paint();
        mPaint_focuse.setAntiAlias(true);
        mPaint_focuse.setColor(Color.RED);
        mPaint_focuse.setTextSize(18);
        mPaint_focuse.setTypeface(Typeface.SANS_SERIF);
    }

    protected  void onDraw(Canvas canvas){
        super.onDraw(canvas);
        canvas.drawColor(0xEFeffff);
        mPaint_focuse.setTextAlign(Paint.Align.CENTER);
        mPaint.setTextAlign(Paint.Align.CENTER);
        if(index==-1){
           return;
        }

        /*
        * 画跑马灯的步骤 1 先画出中间显示的部分 2 画出中间部分前面的部分 3画出中间后面的显示部分
        * */
        canvas.drawText(sentencesList.get(index).getName(), mX, middleY, mPaint_focuse);
        //前面那部分
        float tempY=middleY;
        for(int i=index-1;i>=0;i--){
            tempY =tempY-DY;
            if(tempY<0){
                break;
            }
            canvas.drawText(sentencesList.get(i).getName(),mX,tempY,mPaint);
        }
        //后面那部分
         tempY=middleY;
        for(int i=index+1;i<sentencesList.size();i++){
            tempY =tempY+DY;
            if(tempY>mY){
                break;
            }
            canvas.drawText(sentencesList.get(i).getName(),mX,tempY,mPaint);
        }
    }

    public List<Sentence> getSentencesList() {
        return sentencesList;
    }

    public void setSentencesList(List<Sentence> sentencesList) {
        this.sentencesList = sentencesList;
    }

    protected  void onSizeChanged(int w, int h, int ow, int oh){
        super.onSizeChanged(w,h,ow,oh);
        mX = w * 0.5f;
        mY = h;
        middleY = h * 0.5f;
    }

    public int updateIndex(int index){
        if(index==-1){
            return -1;
        }
        this.index=index;
        return index;
    }


    public void updateUI(){
         new Thread(new UpdateThread()).start();
    }


    class UpdateThread implements Runnable {
        long time = 1000; // 开始 的时间，不能为零，否则前面几句没有显示出来
        int i=0;
        @Override
        public void run() {
            while (true){
                long sleeptime = updateIndex(i);
                time += sleeptime;
                mHandler.post(mUpdateResults);
                if (sleeptime == -1)
                    return;
                try {
                    Thread.sleep(time);
                    i++;
                    if(i==getSentencesList().size())
                        i=0;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    Handler mHandler = new Handler();
    Runnable mUpdateResults = new Runnable() {
        public void run() {
            invalidate(); // 更新视图
        }
    };


}
