import org.json.simple.*;
import org.json.simple.parser.*;
public class ID_Generator {

    private static ID_Generator instance = new ID_Generator();

    private ID_Generator() {
    }

    public static ID_Generator getInstance() {
        if (instance == null) {
            instance = new ID_Generator();
        }
        return instance;
    }

    public int generateNewId() {
        JSONHelper JSONFile = new JSONHelper();

        int lastId = getLastUsedId();
        int newId = lastId + 1;
        //System.out.println("newId: " + newId + " lastId: " + lastId);
        JSONFile.updateKeyValue("src/JSONFiles/DataList.json", "lastUsedId", newId);
        return newId;
    }

    private int getLastUsedId() {
        JSONHelper JSONFile = new JSONHelper();
        JSONObject dataList = JSONFile.getJSONFile("src/JSONFiles/DataList.json");
        int lastUsedId;

        try {
            long value = (long) dataList.get("lastUsedId");
            lastUsedId = (int) value;
            //System.out.println("LastUsedId:  " + lastUsedId);
        
        } catch (Exception e) {
            e.printStackTrace();
            //System.out.println("Faaaaaaaaaaaaaaaaaaail");
            lastUsedId = 0;
            dataList.put("lastUsedId", 0);
            JSONFile.updateDataList(dataList);
        }
        return lastUsedId;
    }

    

}
