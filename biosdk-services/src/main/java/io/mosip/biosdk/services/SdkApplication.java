package io.mosip.biosdk.services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * The {@code SdkApplication} class serves as the entry point for the MOSIP
 * Biometric SDK service application. This class is responsible for
 * bootstrapping the Spring Boot application.
 * <p>
 * The application is configured to exclude the automatic configuration of the
 * data source by using {@link EnableAutoConfiguration} with
 * {@link DataSourceAutoConfiguration} excluded. This is useful in scenarios
 * where the application does not interact with a database or when a custom data
 * source configuration is required.
 * <p>
 * To run the application, the {@code main} method invokes
 * {@link SpringApplication#run(Class, String[])} with
 * {@code SdkApplication.class} and the command-line arguments passed to it.
 * </p>
 *
 * <pre>
 * {@code
 * public static void main(String[] args) {
 * 	SpringApplication.run(SdkApplication.class, args);
 * }
 * }
 * </pre>
 * 
 * @since 1.0
 */
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class })
@SpringBootApplication
public class SdkApplication {

	/**
	 * The main method serves as the entry point for the Spring Boot application. It
	 * delegates to {@link SpringApplication#run(Class, String[])} to launch the
	 * application.
	 *
	 * @param args Command-line arguments passed to the application.
	 */
	public static void main(String[] args) {
		SpringApplication.run(SdkApplication.class, args);
	}
}