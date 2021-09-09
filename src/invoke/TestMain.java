package invoke;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class TestMain {

	public static void main(String[] args) throws Exception {
		TestBean2 bean = new TestBean2();

		Class<?> clazz = bean.getClass();
		System.out.println(clazz);

		HashMap map = new HashMap();
		Annotation a = TestBean2.class.getDeclaredAnnotation(TestAnn.class);

		Method[] methods = TestBean2.class.getDeclaredMethods();
		for (Method me : methods) {

//			System.out.println(me);
//			me.isAnnotationPresent(TestAnn.class);
//			System.out.println(me.isAnnotationPresent(TestAnn.class));
			if (me.isAnnotationPresent(TestAnn.class)) {

//				System.out.println(me.getAnnotatedReceiverType());
//				
//				System.out.println(me.getAnnotatedReturnType());
				System.out.println("此方法名稱:" + me.getName());
				String prgid = me.getDeclaredAnnotation(TestAnn.class).prgid();
				System.out.println("prgid = " + prgid);
				System.out.println("此方法接收參數:" + me.getParameterTypes());
				System.out.println("有幾個參數:" + me.getParameterCount());
				System.out.println("此方法回傳類型" + me.getReturnType());
				if (Arrays.asList(me.getParameterTypes()).contains(HashMap.class)) {
					System.out.println("參數(Parameter)為HashMap的method");
					System.out.println(me.invoke(clazz.newInstance(), map));
				} else {
					if (me.getReturnType().equals(String.class)) {
						System.out.println("回傳值為String的method");
						System.out.println(me.invoke(clazz.newInstance()));
//					} 
//					else if(me.getReturnType()!=String.class){
//						System.out.println();

					} else {
						System.out.println("其他參數(Parameter)的method");
						if (me.getReturnType() != null) {
							System.out.println("有return 值");
							try {

								if (me.getParameterCount() > 0) {

									Class<?> types[] = me.getParameterTypes();
									List<Object> objects = new ArrayList<>();

									for (Class<?> c : types) {

										System.out.println("types=" + c.getTypeName());

										try {
											
											objects.add(c.newInstance());
//										objects.add(c.getClass());
//										System.out.println();

										} catch (Exception e) {
											e.printStackTrace();
											objects.add(null);
											// TODO: handle exception
										}

									}
									for (Object o : objects) {
										System.out.println("o=" + o);
									}

//									System.out.println(me.invoke(clazz.newInstance(), 1, "123", true));
//									System.out.println(me.invoke(clazz.newInstance(), 0, "", false));
									System.out.println(me.invoke(clazz.newInstance(),objects.toArray(new Object[objects.size()])));// objects

								}

								else {
									System.out.println("不需要參數");
								}

							} catch (Exception e) {
								e.printStackTrace();
							}
						} else {

							System.out.println("沒有return 值");
						}

					}

				}

			}

		}

//		bean.getClass().getDeclaredAnnotations();
//		Annotation annotation = bean.getClass().getDeclaredAnnotation(TestAnn.class);
//		System.out.println(annotation);
//		

	}
}
