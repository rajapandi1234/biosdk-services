package io.mosip.biosdk.services.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Data Transfer Object (DTO) for encapsulating error details in the MOSIP
 * Biometric SDK service. This class holds an error code and an error message.
 * <p>
 * The {@code ErrorDto} class uses Lombok annotations for boilerplate code
 * reduction.
 * </p>
 *
 *
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ErrorDto {
	/**
	 * The error code.
	 */
	private String code;

	/**
	 * The error message.
	 */
	private String message;
}