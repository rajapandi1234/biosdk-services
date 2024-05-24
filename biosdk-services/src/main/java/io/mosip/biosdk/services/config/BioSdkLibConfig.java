package io.mosip.biosdk.services.config;

import static io.mosip.biosdk.services.constants.AppConstants.LOGGER_IDTYPE;
import static io.mosip.biosdk.services.constants.AppConstants.LOGGER_SESSIONID;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;

import io.mosip.biosdk.services.constants.ErrorMessages;
import io.mosip.biosdk.services.exceptions.BioSDKException;
import io.mosip.kernel.biometrics.spi.IBioApiV2;
import jakarta.annotation.PostConstruct;

@Configuration
public class BioSdkLibConfig {
    private static final Logger logger = LoggerFactory.getLogger(BioSdkLibConfig.class);

    private Environment env;

    @Autowired
    public BioSdkLibConfig(Environment env) {
    	this.env = env;
    }
    
    @PostConstruct
    public void validateBioSdkLib() throws ClassNotFoundException {
    	String sdkClass = this.env.getProperty("biosdk_bioapi_impl");
		logger.info(LOGGER_SESSIONID, LOGGER_IDTYPE, "validateBioSdkLib::Biosdk class: ", sdkClass);
        if (StringUtils.isNotBlank(sdkClass)) {
            logger.debug(LOGGER_SESSIONID, LOGGER_IDTYPE, "validating Bio SDK Class is present or not");
            Class.forName(sdkClass);
        }

        logger.debug(LOGGER_SESSIONID, LOGGER_IDTYPE, "validateBioSdkLib: Bio SDK Class is not provided");
    }

	@Bean
    @Lazy
    public IBioApiV2 iBioApi() throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
    	String sdkClass = this.env.getProperty("biosdk_bioapi_impl");
		logger.info(LOGGER_SESSIONID, LOGGER_IDTYPE, "iBioApi::Biosdk class:", sdkClass);
    	if (StringUtils.isNotBlank(sdkClass)) {
            logger.debug(LOGGER_SESSIONID, LOGGER_IDTYPE, "instance of Bio SDK is created");
            Constructor<?> constructor = Class.forName(sdkClass).getDeclaredConstructor();            
            return (IBioApiV2) constructor.newInstance();
        } else {
            logger.debug(LOGGER_SESSIONID, LOGGER_IDTYPE, "no Bio SDK is provided");
            throw new BioSDKException(ErrorMessages.NO_BIOSDK_PROVIDER_FOUND.toString(),
					ErrorMessages.NO_BIOSDK_PROVIDER_FOUND.getMessage());
        }
    }
}
