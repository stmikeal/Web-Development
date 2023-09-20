package bean;

import org.json.JSONObject;
import tools.PointManagerFactory;

import javax.ejb.Stateful;
import java.util.ArrayList;

@Stateful
public class PointBeanList {
    private ArrayList<JSONObject> collection = new ArrayList();
    private String username = null;

    private void update(){
        ArrayList<pointBean> temp= new ArrayList<>(PointManagerFactory.getManager().getAll(username));
        collection = new ArrayList<>();
        for(pointBean item:temp){
            collection.add(item.toJSON());
        }
    }

    public JSONObject toJSON() {
        update();
        JSONObject result = new JSONObject();
        result.put("list", collection);
        return result;
    }

    public void add(JSONObject point, String username) {
        collection.add(point);
        if (this.username==null){
            this.username = username;
        }
    }

}
