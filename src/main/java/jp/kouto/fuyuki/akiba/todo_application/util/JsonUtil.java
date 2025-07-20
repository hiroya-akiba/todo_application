package jp.kouto.fuyuki.akiba.todo_application.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JsonUtil {

	private static final Gson gson = new Gson();

	/**
	 * Jsonレスポンス
	 * @param res
	 * @param obj
	 * @throws IOException
	 */
	public static void writeJson(HttpServletResponse res, Object obj) throws IOException {
	    res.setContentType("application/json; charset=UTF-8");
	    res.setCharacterEncoding("UTF-8");
	    PrintWriter out = res.getWriter();
	    String json = "";
	    try {
	    	json = new Gson().toJson(obj);
		    out.print(json);
		    out.flush();
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	}

	/**
	 * リクエストのJsonをDTOに変換する
	 * @param <T>
	 * @param req
	 * @param clazz
	 * @return
	 * @throws IOException
	 */
    public static <T> T readJson(HttpServletRequest req, Class<T> clazz) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = req.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }
        return gson.fromJson(sb.toString(), clazz);
    }

    /**
     * リクエストのJsonをList\<DTO\>で読み取る 
     * @param <T>
     * @param req
     * @param clazz
     * @return
     * @throws IOException
     */
    public static <T> List<T> readJsonList(HttpServletRequest req, Class<T> clazz) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = req.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }
        Type listType = TypeToken.getParameterized(List.class, clazz).getType();
        return gson.fromJson(sb.toString(), listType);
    }
}
