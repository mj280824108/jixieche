package com.weiwei.jixieche.rabbit;

import java.io.*;

/**
 * @author Gavin
 * @date 2019-06-21 15:07
 */
public class ObjectUtils {

	/**
	 * 将对象转换为字节数组 通过流的方式
	 */
	public static byte[] ObjectFormByte(Object object) throws IOException {
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		ObjectOutputStream oo = new ObjectOutputStream(bo);
		oo.writeObject(object);
		byte[] bytes = bo.toByteArray();
		return bytes;
	}


	/**
	 * 将字节数组转换为对象
	 */
	public static <T> T ByteArrayToObjecct(byte[] bytes, Class<T> clazz) throws IOException ,ClassNotFoundException {
		ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
		ObjectInputStream oi = new ObjectInputStream(bi);
		T t = (T) oi.readObject();
		return t;

	}






}
