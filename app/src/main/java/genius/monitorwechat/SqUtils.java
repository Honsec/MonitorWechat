package genius.monitorwechat;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SqUtils {
  private   String TAG=TAG=SqUtils.class.getSimpleName();

	private Context context;
	private SharedPreferences sp = null;
	private SharedPreferences.Editor edit = null;

	public SqUtils(Context context) {
		this(context, PreferenceManager.getDefaultSharedPreferences(context));
	}

	public SqUtils(Context context, String filename) {
		this(context, context.getSharedPreferences(filename, Context.MODE_PRIVATE));
	}

	public SqUtils(Context context, SharedPreferences sp) {
		this.context = context;
		this.sp = sp;
		edit = sp.edit();
	}

	public SharedPreferences getInstance() {
		return sp;
	}


	public synchronized void setValue(String key, boolean value) {
		edit.putBoolean(key, value);
		edit.commit();
	}

	public synchronized void setValue(int resKey, boolean value) {
		setValue(this.context.getString(resKey), value);
	}

	// Float
	public synchronized void setValue(String key, float value) {
		edit.putFloat(key, value);
		edit.commit();
	}

	public synchronized void setValue(int resKey, float value) {
		setValue(this.context.getString(resKey), value);
	}

	// Integer
	public synchronized void setValue(String key, int value) {
		edit.putInt(key, value);
		edit.commit();
	}

	public synchronized void setValue(int resKey, int value) {
		setValue(this.context.getString(resKey), value);
	}

	// Long
	public synchronized void setValue(String key, long value) {
		edit.putLong(key, value);
		edit.commit();
	}

	public synchronized void setValue(int resKey, long value) {
		setValue(this.context.getString(resKey), value);
	}

	// String
	public synchronized void setValue(String key, String value) {
		edit.putString(key, value);
		edit.commit();
	}

	public synchronized void setValue(int resKey, String value) {
		setValue(this.context.getString(resKey), value);
	}

	// Get

	// Boolean
	public boolean getValue(String key, boolean defaultValue) {
		return sp.getBoolean(key, defaultValue);
	}

	public boolean getValue(int resKey, boolean defaultValue) {
		return getValue(this.context.getString(resKey), defaultValue);
	}

	// Float
	public float getValue(String key, float defaultValue) {
		return sp.getFloat(key, defaultValue);
	}

	public float getValue(int resKey, float defaultValue) {
		return getValue(this.context.getString(resKey), defaultValue);
	}

	// Integer
	public int getValue(String key, int defaultValue) {
		return sp.getInt(key, defaultValue);
	}

	public int getValue(int resKey, int defaultValue) {
		return getValue(this.context.getString(resKey), defaultValue);
	}

	// Long
	public long getValue(String key, long defaultValue) {
		return sp.getLong(key, defaultValue);
	}

	public long getValue(int resKey, long defaultValue) {
		return getValue(this.context.getString(resKey), defaultValue);
	}

	// String
	public String getValue(String key, String defaultValue) {
		return sp.getString(key, defaultValue);
	}

	public String getValue(int resKey, String defaultValue) {
		return getValue(this.context.getString(resKey), defaultValue);
	}

	// Delete
	public synchronized void remove(String key) {
		edit.remove(key);
		edit.commit();
	}

	public synchronized void clear() {
		edit.clear();
		edit.commit();
	}


}
