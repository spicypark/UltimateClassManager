package backend;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

public class Database {
	private static final String DATA_DIR = "data";
	private static final String PROFILES_FILE = DATA_DIR + File.separator + "profiles.json";
	private static final String CLASSES_FILE = DATA_DIR + File.separator + "classes.json";

	private static final Gson GSON;

	static {
		GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
		JsonSerializer<LocalDateTime> ser = (src, typeOfSrc, context) -> new JsonPrimitive(src.toString());
		JsonDeserializer<LocalDateTime> deser = (json, typeOfT, context) -> LocalDateTime.parse(json.getAsString());
		builder.registerTypeAdapter(LocalDateTime.class, ser);
		builder.registerTypeAdapter(LocalDateTime.class, deser);
		GSON = builder.create();
	}

	private static void ensureDataDir() {
		File dir = new File(DATA_DIR);
		if (!dir.exists()) dir.mkdirs();
	}

	public static <T> boolean saveList(List<T> list, String filename) {
		ensureDataDir();
		try (FileWriter writer = new FileWriter(filename)) {
			GSON.toJson(list, writer);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static <T> ArrayList<T> loadList(String filename, Class<T> clazz) {
		File f = new File(filename);
		if (!f.exists()) return null;
		try (FileReader reader = new FileReader(f)) {
			Type listType = TypeToken.getParameterized(ArrayList.class, clazz).getType();
			ArrayList<T> list = GSON.fromJson(reader, listType);
			return list;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static boolean saveStudentProfiles(ArrayList<StudentProfile> profiles) {
		return saveList(profiles, PROFILES_FILE);
	}

	public static ArrayList<StudentProfile> loadStudentProfiles() {
		ArrayList<StudentProfile> profiles = loadList(PROFILES_FILE, StudentProfile.class);
		if (profiles == null) return null;
		// rebuild GUI elements that were marked transient
		for (StudentProfile sp : profiles) {
			sp.rebuildButton();
		}
		return profiles;
	}

	public static boolean saveTeacherClasses(ArrayList<ArtClass> classes) {
		return saveList(classes, CLASSES_FILE);
	}

	public static ArrayList<ArtClass> loadTeacherClasses() {
		ArrayList<ArtClass> classes = loadList(CLASSES_FILE, ArtClass.class);
		if (classes == null) return null;
		for (ArtClass ac : classes) {
			ac.rebuildButton();
		}
		return classes;
	}
}
