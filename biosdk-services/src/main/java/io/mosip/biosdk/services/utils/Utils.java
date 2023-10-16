package io.mosip.biosdk.services.utils;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiConsumer;

import org.apache.commons.codec.digest.DigestUtils;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import io.mosip.biosdk.services.dto.RequestDto;
import io.mosip.biosdk.services.impl.spec_1_0.dto.request.CheckQualityRequestDto;
import io.mosip.biosdk.services.impl.spec_1_0.dto.request.ConvertFormatRequestDto;
import io.mosip.biosdk.services.impl.spec_1_0.dto.request.ExtractTemplateRequestDto;
import io.mosip.biosdk.services.impl.spec_1_0.dto.request.InitRequestDto;
import io.mosip.biosdk.services.impl.spec_1_0.dto.request.MatchRequestDto;
import io.mosip.biosdk.services.impl.spec_1_0.dto.request.SegmentRequestDto;
import io.mosip.kernel.biometrics.entities.BIR;
import io.mosip.kernel.biometrics.entities.BiometricRecord;
import io.mosip.kernel.core.util.DateUtils;

@Component
public class Utils {
    @Autowired
    private Gson gson;

    private String utcDateTimePattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    public String getCurrentResponseTime() {
        return DateUtils.formatDate(new Date(System.currentTimeMillis()), utcDateTimePattern);
    }

    public RequestDto getRequestInfo(String request) throws ParseException {
        return gson.fromJson(request, RequestDto.class);
    }

	public static String base64Decode(String data){
        return new String(Base64.getDecoder().decode(data), StandardCharsets.UTF_8);
    }
	
	public static String toString(BiometricRecord biometricRecord) {
		if(biometricRecord == null) {
			return "null";
		}
		
		StringBuilder stringBuilder = new StringBuilder();
		appendString(biometricRecord, stringBuilder);
		return stringBuilder.toString();
	}

    private static void appendString(BiometricRecord biometricRecord, StringBuilder stringBuilder) {
    	if(biometricRecord == null) {
    		stringBuilder.append("null");
		} else {
			stringBuilder.append("{\"BiometricRecord\": { ");
			stringBuilder.append("\"birInfo\": ");
			stringBuilder.append(biometricRecord.getBirInfo());
			stringBuilder.append(", \"cbeffversion\":");
			stringBuilder.append(biometricRecord.getCbeffversion());
			stringBuilder.append(", \"version\":");
			stringBuilder.append(biometricRecord.getVersion());
			stringBuilder.append(", \"segments\":");
			List<BIR> segments = biometricRecord.getSegments();
			if(segments == null) {
	    		stringBuilder.append("null");
			} else {
				appendString(segments.stream().iterator(), stringBuilder, Utils::appendString);
			}
			stringBuilder.append(" }}");
		}
	}

	private static <T> void appendString(Iterator<T> iterator, StringBuilder stringBuilder, BiConsumer<T, StringBuilder> appendBiConsumer) {
		stringBuilder.append("[ ");
		while (iterator.hasNext()) {
			T element = iterator.next();
			appendBiConsumer.accept(element, stringBuilder);
			if(iterator.hasNext()) {
				stringBuilder.append(", ");
			}
		}
		stringBuilder.append(" ]");
	}

	private static void appendString(BIR bir, StringBuilder stringBuilder) {
		if(bir == null) {
    		stringBuilder.append("null");
		} else {
			stringBuilder.append("{ \"BIR\": {");
			stringBuilder.append("\"bdbInfo\": ");
			stringBuilder.append(bir.getBdbInfo());
			stringBuilder.append(", \"birInfo\": ");
			stringBuilder.append(bir.getBirInfo());
			stringBuilder.append(", \"cbeffversion\": ");
			stringBuilder.append(bir.getCbeffversion());
			stringBuilder.append(", \"others\": ");
			stringBuilder.append(bir.getOthers());
			stringBuilder.append(", \"sb\": ");
			stringBuilder.append(bir.getSb());
			stringBuilder.append(", \"sbInfo\": ");
			stringBuilder.append(bir.getSbInfo());
			stringBuilder.append(", \"version\": ");
			stringBuilder.append(bir.getVersion());
			stringBuilder.append(", \"bdbHash\": ");
			stringBuilder.append(bir.getBdb() == null ? "null" : DigestUtils.sha256Hex(bir.getBdb()));
			stringBuilder.append(" }}");
		}
	}
	
	public static String toString(ExtractTemplateRequestDto extractTemplateRequestDto) {
		if(extractTemplateRequestDto == null) {
			return "null";
		}
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("{\"ExtractTemplateRequestDto\": { ");
		stringBuilder.append("\"flags:\"");
		stringBuilder.append(extractTemplateRequestDto.getFlags());
		stringBuilder.append(", \"modalitiesToExtract\": ");
		stringBuilder.append(extractTemplateRequestDto.getModalitiesToExtract());
		stringBuilder.append(", \"sample\": ");
		appendString(extractTemplateRequestDto.getSample(), stringBuilder);
		stringBuilder.append(" }}");
		return stringBuilder.toString();
	}

	public static String toString(MatchRequestDto matchRequestDto) {
		if(matchRequestDto == null) {
			return "null";
		}
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("{\"MatchRequestDto\": { ");
		stringBuilder.append("\"flags\":");
		stringBuilder.append(matchRequestDto.getFlags());
		stringBuilder.append(", \"modalitiesToMatch\": ");
		stringBuilder.append(matchRequestDto.getModalitiesToMatch());
		stringBuilder.append(", \"sample\": ");
		appendString(matchRequestDto.getSample(), stringBuilder);
		stringBuilder.append(", \"gallery\": ");
		appendString(Arrays.stream(matchRequestDto.getGallery()).iterator(), stringBuilder, Utils::appendString);
		stringBuilder.append(" }}");
		return stringBuilder.toString();
	}

	public static String toString(InitRequestDto initRequestDto) {
		if(initRequestDto == null) {
			return "null";
		}
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("{\"InitRequestDto\": { ");
		stringBuilder.append("\"initParams\":");
		stringBuilder.append(" }}");
		return stringBuilder.toString();
	}

	public static String toString(CheckQualityRequestDto checkQualityRequestDto) {
		if(checkQualityRequestDto == null) {
			return "null";
		}
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("{\"CheckQualityRequestDto\": { ");
		stringBuilder.append("\"flags\":");
		stringBuilder.append(checkQualityRequestDto.getFlags());
		stringBuilder.append(", \"modalitiesToCheck\": ");
		stringBuilder.append(checkQualityRequestDto.getModalitiesToCheck());
		stringBuilder.append(", \"sample\": ");
		appendString(checkQualityRequestDto.getSample(), stringBuilder);
		stringBuilder.append(" }}");
		return stringBuilder.toString();
	}

	public static String toString(SegmentRequestDto segmentRequestDto) {
		if(segmentRequestDto == null) {
			return "null";
		}
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("{\"SegmentRequestDto\": { ");
		stringBuilder.append("\"flags\":");
		stringBuilder.append(segmentRequestDto.getFlags());
		stringBuilder.append(", \"modalitiesToSegment\": ");
		stringBuilder.append(segmentRequestDto.getModalitiesToSegment());
		stringBuilder.append(", \"sample\": ");
		appendString(segmentRequestDto.getSample(), stringBuilder);
		stringBuilder.append(" }}");
		return stringBuilder.toString();
	}

	public static String toString(ConvertFormatRequestDto convertFormatRequestDto) {
		if(convertFormatRequestDto == null) {
			return "null";
		}
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("{\"ConvertFormatRequestDto\": { ");
		stringBuilder.append("\"sourceFormat\":");
		stringBuilder.append(convertFormatRequestDto.getSourceFormat());
		stringBuilder.append(", \"targetFormat\": ");
		stringBuilder.append(convertFormatRequestDto.getTargetFormat());
		stringBuilder.append(", \"modalitiesToConvert\": ");
		stringBuilder.append(convertFormatRequestDto.getModalitiesToConvert());
		stringBuilder.append(", \"sample\": ");
		stringBuilder.append(", \"sourceParams\":");
		stringBuilder.append(convertFormatRequestDto.getSourceParams());
		stringBuilder.append(", \"targetParams\": ");
		stringBuilder.append(convertFormatRequestDto.getTargetParams());
		appendString(convertFormatRequestDto.getSample(), stringBuilder);
		stringBuilder.append(" }}");
		return stringBuilder.toString();
	}
}
