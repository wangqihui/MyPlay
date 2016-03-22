package face.com.wangqihui.verticaltextview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public VerticalScrollTextView scrollview;
    /*
    * 跑马灯文字显示的方式实现
    *
    * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scrollview =(VerticalScrollTextView)findViewById(R.id.sampleView1);
        List lst=new ArrayList<Sentence>();
        for(int i=0;i<30;i++){
            if(i%2==0){
                Sentence sen=new Sentence(i,i+"、金球奖三甲揭晓 C罗梅西哈维入围 ");
                lst.add(i, sen);
            }else{
                Sentence sen=new Sentence(i,i+"、公牛欲用三大主力换魔兽？？？？");
                lst.add(i, sen);
            }
        }
        scrollview.setSentencesList(lst);
        scrollview.updateUI();
    }
}
