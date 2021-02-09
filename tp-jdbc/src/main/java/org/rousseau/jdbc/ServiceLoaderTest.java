package org.rousseau.jdbc;

import java.sql.Driver;
import java.util.ServiceLoader;

public class ServiceLoaderTest {

	public static void main(String[] args) {
		ServiceLoader<Driver> driverServiceLoader = ServiceLoader.load(Driver.class);
		driverServiceLoader.forEach(System.out::println);

	}

}
