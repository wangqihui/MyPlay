package face.com.wangqihui.facescanning;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    public  FaceOverlayView mFaceOverlayView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFaceOverlayView =(FaceOverlayView)findViewById(R.id.face_overlay);

        /**读入raw图片流并转为位图**/
        InputStream stream =getResources().openRawResource(R.raw.face);
        Bitmap bitmap= BitmapFactory.decodeStream(stream);
        /**然后输出到faceview**/
        mFaceOverlayView.setBitmap(bitmap);

    }
}
