package io.mosip.biosdk.services.config;

import io.mosip.kernel.core.logger.spi.Logger;
import io.mosip.kernel.logger.logback.factory.Logfactory;

/**
 * Configuration class for setting up the logger for the MOSIP Biometric SDK
 * service. This class provides a method to configure and retrieve a logger
 * instance for a given class.
 * <p>
 * The {@code LoggerConfig} class is final and contains a private constructor to
 * prevent instantiation. The logging configuration is done using the MOSIP
 * kernel logger's {@link Logfactory}.
 * </p>
 *
 * <pre>
 * {@code
 * public final class LoggerConfig {
 * 	// Logger configuration details
 * }
 * }
 * </pre>
 *
 * @since 1.0.0
 */
public final class LoggerConfig {
	/**
	 * Private constructor to prevent instantiation. This ensures that the class
	 * serves only as a utility for configuring loggers.
	 */
	private LoggerConfig() {
	}

	/**
	 * Configures and returns a logger instance for the specified class.
	 * <p>
	 * This method uses the {@link Logfactory#getSlf4jLogger(Class)} method to
	 * create a logger that adheres to the SLF4J logging facade.
	 * </p>
	 *
	 * @param clazz the class for which the logger is to be configured.
	 * @return a {@link Logger} instance for the specified class.
	 */
	public static Logger logConfig(Class<?> clazz) {
		return Logfactory.getSlf4jLogger(clazz);
	}
}