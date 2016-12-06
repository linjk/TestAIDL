package cn.linjk.testaidlserver.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by LinJK on 06/12/2016.
 */

public class ServiceUserManager extends Service{

    private static final String TAG = ServiceUserManager.class.getSimpleName();

    private CopyOnWriteArrayList<User> mUserCopyOnWriteArrayList = new CopyOnWriteArrayList<>();

    private Binder mBinder = new IUserManager.Stub() {
        @Override
        public void addUser(User user) throws RemoteException {
            mUserCopyOnWriteArrayList.add(user);
        }

        @Override
        public List<User> getAllUsers() throws RemoteException {
            return mUserCopyOnWriteArrayList;
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        mUserCopyOnWriteArrayList.add(new User(1, "Jim"));
        mUserCopyOnWriteArrayList.add(new User(2, "Jim_LinJK"));
    }

    @Nullable
    @Override
    public IBinder onBind(Intent pIntent) {
        return mBinder;
    }
}
