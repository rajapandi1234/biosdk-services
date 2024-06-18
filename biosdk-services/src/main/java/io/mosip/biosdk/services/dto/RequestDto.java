package io.mosip.biosdk.services.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Data Transfer Object (DTO) for encapsulating requests in the MOSIP Biometric
 * SDK service. This class holds version information and the request data.
 * <p>
 * The {@code RequestDto} class uses Lombok annotations for boilerplate code
 * reduction.
 * </p>
 *
 *
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@ToString
public class RequestDto {
	/**
	 * The version of the request.
	 */
	private String version;

	/**
	 * The request data.
	 */
	private String request;
}