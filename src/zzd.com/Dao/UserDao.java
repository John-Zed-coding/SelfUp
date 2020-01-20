package zzd.com.Dao;

import zzd.com.domain.User;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 定义用户信息
 */
public class UserDao {
    private static User userMsg;
    private static ConcurrentHashMap<String, User> userMap = new ConcurrentHashMap();
    public static ConcurrentHashMap<String, User> getUserMap() {
        return userMap;
    }

    public static void setUserMap(ConcurrentHashMap<String, User> userMap) {
        UserDao.userMap = userMap;
    }

    public static User getUserMsg() {
        return userMsg;
    }

    public static void setUserMsg(User userMsg) {
        UserDao.userMsg = userMsg;
    }

    public UserDao() {}

    public boolean searchUserByUsername(String username) {
        if(userMap.containsKey(username)){
            return false;
        }else{
            return true;
        }
    }
    public void addUser(User user) {
        userMap.put(user.getUsername(),user);
    }
}
