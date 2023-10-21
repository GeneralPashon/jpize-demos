package lang;

import jpize.files.MapConfig;
import jpize.files.Resource;

import java.util.HashMap;

public class ClientLanguage{

    private HashMap<String, String> storage;

    public static ClientLanguage loadFrom(Resource res){
        ClientLanguage language = new ClientLanguage();
        MapConfig map = new MapConfig(res);
        map.load();
        language.storage = map.getMap();

        return language;
    }

    public String getOrDefault(String key){
        return storage.getOrDefault(key, key);
    }

}
