	/**
	 * Make the given method accessible, explicitly setting it accessible if
	 * necessary. The {@code setAccessible(true)} method is only called
	 * when actually necessary, to avoid unnecessary conflicts with a JVM
	 * SecurityManager (if active).
	 * @param method the method to make accessible
	 * @see java.lang.reflect.Method#setAccessible
	 */
@SuppressWarnings("deprecation")  // on JDK 9
public static void makeAccessible(Method method) {
	if ((!Modifier.isPublic(method.getModifiers()) ||
			!Modifier.isPublic(method.getDeclaringClass().getModifiers())) && !method.isAccessible()) {
		method.setAccessible(true);
	}
}
子类和父类的反射方法必须是public的么？


InvocableHandlerMethod是每一个类都是必须的么？