package com.jiahangchun.tool;

import com.google.gson.*;
import com.google.gson.internal.StringMap;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JsonUtil {
	public static Gson gson;

	static {
		GsonBuilder gb = new GsonBuilder();
		gb.disableHtmlEscaping();
//		gb.setFieldNamingPolicy(
//				FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
		gb.setDateFormat("yyyy-MM-dd HH:mm:ss");
		//FIXME 避免反射map时，整数变成double
		gb.registerTypeAdapter(Double.class, new JsonSerializer<Double>() {
			public JsonElement serialize(Double src, Type typeOfSrc, JsonSerializationContext context) {
				if (src == src.longValue()) {
					return new JsonPrimitive(src.longValue());
				}
				return new JsonPrimitive(src);
			}
		});
		gb.registerTypeAdapter(new TypeToken<StringMap<Object>>() {
				}.getType(),
				new JsonDeserializer<StringMap<Object>>() {
					public StringMap<Object> deserialize(
							JsonElement json, Type typeOfT,
							JsonDeserializationContext context) throws JsonParseException {

						StringMap<Object> stringMap = new StringMap<Object>();
						JsonObject jsonObject = json.getAsJsonObject();
						Set<Map.Entry<String, JsonElement>> entrySet = jsonObject.entrySet();
						for (Map.Entry<String, JsonElement> entry : entrySet) {
							JsonElement jsonElement = entry.getValue();
							Object obj = deserializeElement(jsonElement, typeOfT, context);
							stringMap.put(entry.getKey(), obj);
						}
						return stringMap;
					}

					public Object deserializeElement(JsonElement json, Type typeOfT,
                                                     JsonDeserializationContext context) throws JsonParseException {
						if (json.isJsonPrimitive()) {
							JsonPrimitive jsonPrimitive = json.getAsJsonPrimitive();
							if (jsonPrimitive.isString()) {
								return jsonPrimitive.getAsString();
							} else if (jsonPrimitive.isBoolean()) {
								return jsonPrimitive.getAsBoolean();
							} else if (jsonPrimitive.isNumber()) {
								Number number = jsonPrimitive.getAsNumber();
								try {
									if (number.longValue() == number.doubleValue()) {
										return number.longValue();
									} else {
										return number;
									}
								} catch (Exception e) {
									//TODO 这里临时先这么处理，上线前需要重点验证下
									e.printStackTrace();
									return null;
								}
							} else {
								return null;
							}
						} else if (json.isJsonNull()) {
							return null;
						} else if (json.isJsonArray()) {
							JsonArray jsonElements = json.getAsJsonArray();
							List<Object> objList = new ArrayList<Object>();
							for (JsonElement jsonElement : jsonElements) {
								objList.add(this.deserializeElement(jsonElement, typeOfT, context));
							}
							return objList;
						} else if (json.isJsonObject()) {
							StringMap<Object> stringMap = new StringMap<Object>();
							Set<Map.Entry<String, JsonElement>> entrySet = json.getAsJsonObject().entrySet();
							for (Map.Entry<String, JsonElement> entry : entrySet) {
								JsonElement value = entry.getValue();
								Object obj = this.deserializeElement(value, typeOfT, context);
								stringMap.put(entry.getKey(), obj);
							}
							return stringMap;
						} else {
							return null;
						}
					}
				});
		gson = gb.create();
	}

	public static <T> T parseJson(String jsonStr, Class<T> tClass) {
		return gson.fromJson(jsonStr, tClass);
	}

	public static <T> T parseJson(JsonReader jsonReader, Class<T> tClass) {
		return gson.fromJson(jsonReader, tClass);
	}

	public static <T> T parseJson(String jsonStr, Type typeOfT) {
		return gson.fromJson(jsonStr, typeOfT);
	}

	/**
	 * 使用Gson生成json字符串
	 *
	 * @param src
	 * @return
	 */
	public static String toJson(Object src) {
		return gson.toJson(src);
	}
}


