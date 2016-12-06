package cn.linjk.testaidlclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;

import cn.linjk.testaidlserver.aidl.IUserManager;
import cn.linjk.testaidlserver.aidl.ServiceUserManager;
import cn.linjk.testaidlserver.aidl.User;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = MainActivity.class.getSimpleName();

    private Button btnAddUser;

    private IUserManager lvIUserManager;

    private boolean isServiceConnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isServiceConnected = false;

        btnAddUser = (Button)findViewById(R.id.btn_add_user);
        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                try {
                    User lvUser = new User(3, "Apple");
                    lvIUserManager.addUser(lvUser);
                    //
                    List<User> lvUserList = lvIUserManager.getAllUsers();
                    for (User lvUserTmp : lvUserList) {
                        Log.i(TAG, "[User name] : " + lvUserTmp.userName + " and [User id] : " + lvUserTmp.userId);
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        bindService(new Intent(this, ServiceUserManager.class), mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        unbindService(mServiceConnection);
        super.onDestroy();
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName pComponentName, IBinder pIBinder) {
            isServiceConnected = true;

            lvIUserManager = IUserManager.Stub.asInterface(pIBinder);

            try {
                List<User> lvUserList = lvIUserManager.getAllUsers();
                for (User lvUser : lvUserList) {
                    Log.i(TAG, "[User name] : " + lvUser.userName + " and [User id] : " + lvUser.userId);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName pComponentName) {
            isServiceConnected = false;
        }
    };
}
