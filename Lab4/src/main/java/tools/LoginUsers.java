package tools;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Set;

public class LoginUsers {
    private static HashMap<String, LocalDateTime> list;
    private static HashMap<String, String> listToken;
    private static HashMap<String, String> listUsername;

    public static void addSession(String session){
        list.put(session, LocalDateTime.now());
    }
    public static void addToken(String session, String token){
        listToken.put(session, token);
    }
    public static void addUsername(String session, String username){
        listUsername.put(session, username);
    }

    public static boolean checkSession(String session, String token, String username){
        boolean result = false;
        Set<String> setOfList = list.keySet();
        if (setOfList.contains(session)) {
            LocalDateTime authTime = list.get(session);
            LocalDateTime thisTime = LocalDateTime.now();
            if (authTime.getYear()==thisTime.getYear()&&authTime.getMonth()==thisTime.getMonth()&&
                authTime.getDayOfMonth()==thisTime.getDayOfMonth()&&authTime.getHour()==thisTime.getHour()&&
                thisTime.getMinute()-authTime.getMinute()<30&&
                listUsername.get(session).equals(username)&& listToken.get(session).equals(token)){

                result = true;
            } else {
                list.remove(session);
            }
        }
        return result;
    }

    public boolean checkToken(String username, String token){
        if (!listToken.get(username).equals(token)) return false;
        return true;
    }
}
