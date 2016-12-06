// IUserManager.aidl
package cn.linjk.testaidlserver.aidl;

// Declare any non-default types here with import statements
import cn.linjk.testaidlserver.aidl.User;

interface IUserManager {
    void addUser(in User user);
    List<User> getAllUsers();
}
