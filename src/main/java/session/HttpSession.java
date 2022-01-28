package session;

import java.util.HashMap;
import java.util.Map;

public class HttpSession {
    private Map<String, Object> values = new HashMap<>();

    private String id;

    public HttpSession(String id) {
        this.id = id;
    }

    public Object getValues(String id) {
        return values.get(id);
    }

    public void addValues(String key, Object values) {
        this.values.put(key, values);
    }

    public void removeValue(String id) {
        this.values.remove(id);
    }

    public void invalidate() {
        SessionDB.remove(id);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
