package face.com.wangqihui.bindertest;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    Messenger mService =null;

    /** Flag indicating whether we have called bind on the service. */
    boolean mBound;

    /**
     * Class for interacting with the main interface of the service.
     */
    private ServiceConnection mConnection = new ServiceConnection(){
        public void onServiceConnected(ComponentName className,IBinder service){
            // This is called when the connection with the service has been
            // established, giving us the object we can use to
            // interact with the service.  We are communicating with the
            // service using a Messenger, so here we get a client-side
            // representation of that from the raw IBinder object.
            mService = new Messenger(service);
            mBound = true;
        }

        public void onServiceDisconnected(ComponentName className){
            // This is called when the connection with the service has been
            // unexpectedly disconnected -- that is, its process crashed.
            mService = null;
            mBound = false;
        }
    };

    public void sayHello(View v){
        if(!mBound) return;
        // Create and send a message to the service, using a supported 'what' value
        Message msg = Message.obtain(null,MessengerService.MSG_SAY_HELLO,0,0);
        try{
            mService.send(msg);
        }catch(RemoteException e){
            e.printStackTrace();
        }
    }


    @Override
    protected void onStart(){
        super.onStart();
        // Bind to the service
        bindService(new Intent(this,MessengerService.class), mConnection,
                Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop(){
        super.onStop();
        // Unbind from the service
        if(mBound){
            unbindService(mConnection);
            mBound =false;
        }
    }
}
