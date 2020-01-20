package tin.thurein.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.commons.lang3.reflect.TypeUtils;
import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JSONUtils {

    private static Gson gson;

    static {
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
    }

    /**
     * Convert Json Array to List
     *
     * @param aClass
     * @param jsonArray
     * @return
     */
    public static <T> List<T> convertJsonArrayToList(Class<T> aClass, String jsonArray) {
        Type type = TypeUtils.parameterize(ArrayList.class, aClass);
        return gson.fromJson(jsonArray, type);
    }

    /**
     * Convert List to json array
     *
     * @param tempList
     * @return
     */
    public static <T> JSONArray convertListToJsonArray(List<T> tempList) {
        try {
            return new JSONArray(gson.toJson(tempList));
        } catch (JSONException e) {
            System.err.println("JSONException : " + e.getMessage());
            return null;
        }
    }

    /**
     * convert json string to object
     *
     * @param aClass
     * @param jsonString
     * @param <T>
     * @return
     */
    public static <T> T convertJsonToObject(Class<T> aClass, String jsonString) {
        return gson.fromJson(jsonString, aClass);
    }

    /**
     * convert object to json string
     *
     * @param object
     * @param <T>
     * @return
     */
    public static <T> String convertObjectToJsonString(T object) {
        return gson.toJson(object);
    }
}