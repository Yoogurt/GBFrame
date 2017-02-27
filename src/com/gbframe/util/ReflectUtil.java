package com.gbframe.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectUtil {

	public static Class<?> findClass(String className) {

		try {
			return Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Field getInstanceField(Object instance, String name) {

		return getClassField(instance.getClass(), name);
	}

	public static Object getField(Field mField, Object instance) {

		if (mField != null)
			if (mField.isAccessible())
				return mField;
			else
				try {
					mField.setAccessible(true);
					return mField.get(instance);
				} catch (Exception e) {
				} finally {
					mField.setAccessible(false);
				}

		return null;

	}

	public static Field getClassField(Class<?> mClass, String name) {

		try {
			Field mField = mClass.getDeclaredField(name);

			if (mField != null)
				return mField;

		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Method getInstanceMethod(Object instance, String name) {
		return getClassMethod(instance.getClass(), name);
	}

	public static Method getClassMethod(Class<?> mClass, String name, Class<?>... method) {
		try {

			Method mMethod = mClass.getDeclaredMethod(name, method);
			return mMethod;

		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static Object newInstance(Object instance) {
		try {
			Class<?> mClass = instance.getClass();

			return mClass.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static Class<?> getSuperClassByName(Class<?> mClass, String name) {

		while (mClass != null) {

			if (mClass.getSimpleName().equals(name))
				return mClass;

			mClass = mClass.getSuperclass();

		}
		return null;
	}

	/**
	 * Safely cast the primitive variety to correct type , it take time to
	 * transform
	 * 
	 * @param instance
	 * @param method
	 * @param parameter
	 * @return
	 */
	public static Object invokeMethodSafely(Object instance, String method, Object... parameter) {

		if (parameter.length <= 0) {

			Method mMethod = getClassMethod(instance.getClass(), method);

			try {

				if (mMethod != null)
					if (mMethod.isAccessible())
						return mMethod.invoke(instance);
					else {
						try {
							mMethod.setAccessible(true);
							return mMethod.invoke(instance);
						} catch (Exception e) {
						} finally {
							mMethod.setAccessible(false);
						}
					}

			} catch (Exception e) {
				e.printStackTrace();
			}

			return null;
		}

		int length;

		Class<?>[] mClasses = new Class<?>[length = parameter.length];

		for (int i = 0; i < length; i++)
			mClasses[i] = getTypeForPrimitive(parameter[i].getClass());

		Method mMethod = getClassMethod(instance.getClass(), method, mClasses);

		try {
			if (mMethod != null)
				if (mMethod.isAccessible())
					return mMethod.invoke(instance, parameter);
				else {
					try {
						mMethod.setAccessible(true);
						return mMethod.invoke(instance, parameter);
					} catch (Exception e) {
					} finally {
						mMethod.setAccessible(false);
					}
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean setField(Object instance, String name, Object arg) {

		Field mField = getInstanceField(instance, name);

		if (mField != null)
			if (mField.isAccessible())
				try {
					mField.set(instance, arg);
					return true;
				} catch (Exception e) {
					e.printStackTrace();
				}
			else {
				try {
					mField.setAccessible(true);
					mField.set(instance, arg);
					return true;
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					mField.setAccessible(false);
				}
			}
		return false;

	}

	private static Class<?> getTypeForPrimitive(Class<?> mClass) {

		if (mClass == Integer.class)
			return Integer.TYPE;
		else if (mClass == Long.class)
			return Long.TYPE;
		else if (mClass == Character.class)
			return Character.TYPE;
		else if (mClass == Short.class)
			return Short.TYPE;
		else if (mClass == Byte.class)
			return Byte.TYPE;
		else if (mClass == Boolean.class)
			return Boolean.TYPE;
		else if (mClass == Float.class)
			return Float.TYPE;
		else if (mClass == Double.class)
			return Double.TYPE;
		else
			return mClass;

	}

}
