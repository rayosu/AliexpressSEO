package cc.surui.model;import java.lang.reflect.Field;import java.lang.reflect.InvocationTargetException;import java.lang.reflect.Method;import java.util.ArrayList;import java.util.HashMap;import java.util.List;import java.util.Map;import java.util.regex.Matcher;import java.util.regex.Pattern;import org.jsoup.helper.StringUtil;import cc.surui.db.SQLiteJDBC;public abstract class Persistent<T> {	public abstract Object getId();		public T get() {		String tableName = this.getClass().getSimpleName().toUpperCase();		StringBuilder sql = new StringBuilder("SELECT * FROM ").append(tableName)		.append(" WHERE ID = ?");		List<Map<String, String>> list = SQLiteJDBC.instance().query(sql.toString(), this.getId());		if(!list.isEmpty()){			return toBean(list.get(0));		}		return null;	}    public void delete() {        String tableName = this.getClass().getSimpleName().toUpperCase();        StringBuilder sql = new StringBuilder("DELETE FROM ").append(tableName)                .append(" WHERE ID = ?");        SQLiteJDBC.instance().update(sql.toString(), this.getId());    }	public List<T> list() {		List<T> beans = new ArrayList<>();				String tableName = this.getClass().getSimpleName().toUpperCase();		Map<String, Object> map = toMap();		List<Object> valueList = new ArrayList<>();		StringBuilder sql = new StringBuilder("SELECT * FROM ").append(tableName).append(" WHERE 1=1");		for (String field : map.keySet()) {			Object object = map.get(field);			if(object != null && !StringUtil.isBlank(object.toString()) && !object.toString().equals("0")){				sql.append(" AND ").append(field.toUpperCase()).append(" = ?");				valueList.add(object);			}		}		Object[] values = valueList.toArray();		List<Map<String, String>> list = SQLiteJDBC.instance().query(sql.toString(), values);		for (Map<String, String> map2 : list) {			T bean = toBean(map2);			beans.add(bean);		}		return beans;	}	public boolean save() {		String tableName = this.getClass().getSimpleName().toUpperCase();		Map<String, Object> map = toMap();		List<String> paramsList = new ArrayList<String>();		for (String field : map.keySet()) {			paramsList.add("?");		}		// 所需动态参数		String params = StringUtil.join(paramsList, ",");		String fields = StringUtil.join(map.keySet(), ",").toUpperCase();		Object[] values = map.values().toArray();		// 生成sql语句		StringBuilder sql = new StringBuilder("INSERT INTO ").append(tableName).append(" (" + fields + ")").append(" VALUES (" + params + ")");		int count = SQLiteJDBC.instance().update(sql.toString(), values);		if (count != 0) {			return true;		}		return false;	}	public boolean update() {		String tableName = this.getClass().getSimpleName().toUpperCase();		Map<String, Object> map = toMap();		List<Object> valueList = new ArrayList<>();		StringBuilder sql = new StringBuilder("UPDATE "+tableName+" SET ");		List<String> sets = new ArrayList<>();		for (String field : map.keySet()) {			if(field.equalsIgnoreCase("id")){				continue;			}			Object object = map.get(field);			if(object != null && !StringUtil.isBlank(object.toString()) && !object.toString().equals("0")){				sets.add(field.toUpperCase()+" = ?");				valueList.add(object);			}		}		Object[] values = valueList.toArray();		sql.append(StringUtil.join(sets, ",")).append(" WHERE ID = "). append(this.getId());		System.out.println(sql.toString());		System.out.println(values);		int count = SQLiteJDBC.instance().update(sql.toString(), values);		if(count != 0){			return true;		}		return false;	}	public boolean saveOrUpdate() {		if (this.get() != null) {			return this.update();		} else {			return this.save();		}	}	public T toBean(Map<String, String> map) {		T bean = null;		try {			bean = (T) this.getClass().newInstance();			for (String key : map.keySet()) {				String fieldName = underline4camel(key);				String value = map.get(key);				String methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);				Method[] methods = this.getClass().getDeclaredMethods();				for (Method method : methods) {					if(method.getName().equals(methodName)){						Class clazz = method.getParameterTypes()[0];						method.invoke(bean, caseTo(clazz, value));					}				}			}		} catch (InstantiationException | IllegalAccessException | SecurityException | IllegalArgumentException | InvocationTargetException e) {			e.printStackTrace();		}		return bean;	}		public Object caseTo(Class clazz, Object object){		if(clazz == Long.class){			return Long.parseLong(object.toString());		} else if (clazz == Integer.class) {			return Integer.parseInt(object.toString());		} else {			return clazz.cast(object);		}	}	public Map<String, Object> toMap() {		Map<String, Object> map = new HashMap<String, Object>();		Field[] fields = this.getClass().getDeclaredFields();		for (Field field : fields) {			try {				String fieldName = field.getName();				String methodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);				Method[] methods = this.getClass().getDeclaredMethods();				for (Method method : methods) {					if(method.getName().equals(methodName)){						Object value = method.invoke(this);						map.put(camel4underline(fieldName), value);					}				}//				field.setAccessible(true);//				Object value = field.get(this);//				if (value instanceof Property) {//					value = ((Property) value).getValue();//				}//				map.put(camel4underline(field.getName()), value);//				field.setAccessible(false);			} catch (IllegalArgumentException |IllegalAccessException |InvocationTargetException e) {				e.printStackTrace();			}		}		return map;	}	public String underline4camel(String param) {		Pattern p = Pattern.compile("_");		if (param == null || param.equals("")) {			return "";		}		StringBuilder builder = new StringBuilder(param.toLowerCase());		Matcher mc = p.matcher(param);		int i = 0;		while (mc.find()) {			String upper = builder.substring(mc.start() + i + 1, mc.end() + i + 1).toUpperCase();			builder.replace(mc.start() + i, mc.end() + i + 1, upper);			i--;		}		return builder.toString();	}	public String camel4underline(String param) {		Pattern p = Pattern.compile("[A-Z]");		if (param == null || param.equals("")) {			return "";		}		StringBuilder builder = new StringBuilder(param);		Matcher mc = p.matcher(param);		int i = 0;		while (mc.find()) {			builder.replace(mc.start() + i, mc.end() + i, "_" + mc.group().toLowerCase());			i++;		}		if ('_' == builder.charAt(0)) {			builder.deleteCharAt(0);		}		return builder.toString().toUpperCase();	}}