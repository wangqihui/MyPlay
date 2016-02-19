package face.com.wangqihui.facescanning;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;

import java.util.HashMap;


/**
 * Created by wangqihui on 2016/1/13.
 */
public class FaceOverlayView extends View {

    private Bitmap mBitmap;
    private SparseArray<Face> mFaces ;

    public FaceOverlayView(Context context) {
       this(context,null);
    }

    public FaceOverlayView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public FaceOverlayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setBitmap(Bitmap bitmap) {
        mBitmap = bitmap;

        /*开启人脸检测*/
        FaceDetector detector = null;
        try {
            detector = new FaceDetector.Builder(getContext())
                    .setTrackingEnabled(false)
                    .setLandmarkType(FaceDetector.ALL_LANDMARKS)
                    .setMode(FaceDetector.ACCURATE_MODE)
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
        }

        /**
         * 你需要检查FaceDetector是否是可操作的。每当用户第一次在设备上使用人脸检测，
         * Play Services服务需要加载一组小型本地库去处理应用程序的请求。
         * 虽然这些工作一般在应用程序启动之前就完成了，但是做好失败处理同样是必要的。
         如果FaceDetector是可操作的，那么你需要将位图数据转化成Frame对象，
         并通过detect函数传入用来做人脸数据分析。当完成数据分析后，你需要释放探测器，
         防止内存泄露。最后调用invalidate()函数来触发视图刷新。
         * **/

        //检测人脸检测是否可用，如果可用
        if (!detector.isOperational()) {
             //不可以用，重新加载
            Log.i("wangqihui","not initial");
        } else {
            Frame frame =new Frame.Builder().setBitmap(bitmap).build();
            mFaces=detector.detect(frame);
            detector.release();
        }
  /*      Frame frame =new Frame.Builder().setBitmap(bitmap).build();
        mFaces=detector.detect(frame);
        detector.release();*/
        invalidate();
    }

    /*覆写ondraw函数，来刷新图片，即分析后的操作在此自动调用*/
    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        if(null!=mBitmap && null!=mFaces){
            double scale = drawBitmap(canvas);
           drawFaceBox(canvas, scale);
        }
    }

    /**
    * drawBitmap(Canvas canvas)方法会将图像自适应大小的画在画布上，同时返回一个正确的缩放值供你使用。
    * */
    private double drawBitmap(Canvas canvas) {
        double viewWidth =canvas.getWidth();
        double viewHight =canvas.getHeight();
        double imageWidth=mBitmap.getWidth();
        double imageHight=mBitmap.getHeight();

        double scale =Math.min(viewWidth / imageWidth, viewHight / imageHight);
        Rect destBounds = new Rect(0, 0, (int)(imageWidth * scale), (int)(imageHight * scale));
        canvas.drawBitmap(mBitmap, null, destBounds, null);
        return scale;
    }

    /**
     * drawFaceBox(Canvas canvas, double scale)方法会更有趣，被检测到人脸数据以位置信息的方式存储到mFaces中，
     * 这个方法将基于这些位置数据中的宽、高在检测到的人脸位置画一个绿色的矩形框。
     你需要定义自己的绘画对象，然后从你的SparseArray数组中循环的找出位置、高度和宽度信息，再利用这些信息在画布上画出矩形。
     */

    private void drawFaceBox(Canvas canvas, double scale){
          //新建画笔
        Paint paint =new Paint();
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);

        float left =0;
        float top=0;
        float right=0;
        float bottom=0;

        for(int i=0;i<mFaces.size();i++){
            Face face =mFaces.get(i);
            left = (float)scale*face.getPosition().x;
            top  =(float)scale*face.getPosition().y;
            right = (float) scale * ( face.getPosition().x + face.getWidth() );
            bottom = (float) scale * ( face.getPosition().y + face.getHeight() );

            canvas.drawRect( left, top, right, bottom, paint );
        }
    }

}
