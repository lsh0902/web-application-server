package session;

import java.util.HashMap;
import java.util.Map;

public class SessionDB {
    private static Map<String, HttpSession> sessions = new HashMap<>();

    public static HttpSession getSession(String id) {
        HttpSession httpSession = sessions.get(id);
        if(httpSession == null) {
            HttpSession newSession = new HttpSession(id);
            sessions.put(id, newSession);
            return newSession;
        }
        return httpSession;
    }

    public static void remove(String id){
        sessions.remove(id);
    }
}
