FROM eclipse-temurin:21-jre-alpine

# label to be assigned along with  Docker build [Mandatory]
ARG SOURCE
ARG COMMIT_HASH
ARG COMMIT_ID
ARG BUILD_TIME
LABEL source=${SOURCE}
LABEL commit_hash=${COMMIT_HASH}
LABEL commit_id=${COMMIT_ID}
LABEL build_time=${BUILD_TIME}

# can be passed during Docker build as build time environment for github branch to pickup configuration from.
ARG spring_config_label

# can be passed during Docker build as build time environment for spring profiles active 
ARG active_profile

# can be passed during Docker build as build time environment for config server URL
ARG spring_config_url

# environment variable to pass github branch to pickup configuration from, at docker runtime
ENV spring_config_label_env=${spring_config_label}
# environment variable to pass active profile such as DEV, QA etc at docker runtime
ENV active_profile_env=${active_profile}
# environment variable to pass spring configuration url, at docker runtime
ENV spring_config_url_env=${spring_config_url}

ARG biosdk_zip_url

ENV biosdk_zip_file_path=${biosdk_zip_url}

ARG biosdk_local_dir=biosdk-client

ENV biosdk_local_dir_name=${biosdk_local_dir}

ARG sdk_impl

ARG service_context=/biosdk-service

ENV service_context_env=${service_context}

#ENV biosdk_zip_url=$biosdk_zip_url
ENV biosdk_bioapi_impl=${sdk_impl}

# can be passed during Docker build as build time environment for github branch to pickup configuration from.
ARG container_user=mosip

# can be passed during Docker build as build time environment for github branch to pickup configuration from.
ARG container_user_group=mosip

# can be passed during Docker build as build time environment for github branch to pickup configuration from.
ARG container_user_uid=1002

# can be passed during Docker build as build time environment for github branch to pickup configuration from.
ARG container_user_gid=1001

# install packages and create user
RUN apk -q update \
&& apk add -q build-base cmake git bash unzip wget libstdc++ libc++-dev libjpeg-turbo-dev libpng-dev libwebp-dev tiff-dev openblas-dev ffmpeg-dev gfortran sudo\
&& addgroup -g ${container_user_gid} ${container_user_group} \
&& adduser -s /bin/sh -u ${container_user_uid} -G ${container_user_group} -h /home/${container_user} --disabled-password ${container_user}
    
# Install glibc
ENV GLIBC_VERSION="2.35-r0"
RUN wget -q -O /etc/apk/keys/sgerrand.rsa.pub https://alpine-pkgs.sgerrand.com/sgerrand.rsa.pub && \
    wget https://github.com/sgerrand/alpine-pkg-glibc/releases/download/${GLIBC_VERSION}/glibc-${GLIBC_VERSION}.apk && \
    wget https://github.com/sgerrand/alpine-pkg-glibc/releases/download/${GLIBC_VERSION}/glibc-bin-${GLIBC_VERSION}.apk && \
    apk add --no-cache --force-overwrite glibc-${GLIBC_VERSION}.apk glibc-bin-${GLIBC_VERSION}.apk && \
    rm -f /etc/apk/keys/sgerrand.rsa.pub && \
    rm glibc-${GLIBC_VERSION}.apk glibc-bin-${GLIBC_VERSION}.apk
    
# Download and build OpenCV
ENV OPENCV_VERSION=4.5.3
RUN mkdir /opencv && \
    cd /opencv && \
    wget -O opencv.zip https://github.com/opencv/opencv/archive/${OPENCV_VERSION}.zip && \
    unzip opencv.zip && \
	wget -O opencv_contrib.zip https://github.com/opencv/opencv_contrib/archive/${OPENCV_VERSION}.zip && \
    unzip opencv_contrib.zip && \
    mkdir -p /opencv/opencv-${OPENCV_VERSION}/build && \
    cd /opencv/opencv-${OPENCV_VERSION}/build && \
    cmake -D CMAKE_BUILD_TYPE=Release \
          -D CMAKE_INSTALL_PREFIX=/usr/local \
		  -D OPENCV_EXTRA_MODULES_PATH=/opencv/opencv_contrib-${OPENCV_VERSION}/modules \          
          -D WITH_OPENCL=OFF \
          -D WITH_IPP=OFF \
          -D WITH_TBB=OFF \
          -D BUILD_EXAMPLES=OFF \
          -D BUILD_DOCS=OFF \
          -D BUILD_TESTS=OFF \
          -D BUILD_PERF_TESTS=OFF \
          -D BUILD_opencv_java=ON \
          -D INSTALL_C_EXAMPLES=OFF \
          -D INSTALL_PYTHON_EXAMPLES=OFF \
          -D BUILD_opencv_python=OFF .. && \
    make -j$(nproc) && \
    make install && \
    ldconfig

# set working directory for the user
WORKDIR /home/${container_user}

ENV work_dir=/home/${container_user}

ARG loader_path=${work_dir}/additional_jars/

RUN mkdir -p ${loader_path}

ENV loader_path_env=${loader_path}

ADD configure_biosdk.sh /home/${container_user}/configure_biosdk.sh

RUN chmod +x /home/${container_user}/configure_biosdk.sh

RUN chmod a-w /home/${container_user}/configure_biosdk.sh

ARG logging_level_root=INFO

ENV logging_level_root_env=${logging_level_root}

ADD ./target/biosdk-services-*.jar biosdk-services.jar

RUN chmod 775 biosdk-services.jar

# change permissions of file inside working dir
RUN chown -R ${container_user}:${container_user} /home/${container_user}

# select container user for all tasks
USER ${container_user_uid}:${container_user_gid}

EXPOSE 9099

# Set the environment variable for OpenCV native library path
ENV LD_LIBRARY_PATH=/usr/local/share/java/opencv4:$LD_LIBRARY_PATH

ENTRYPOINT ["/home/mosip/configure_biosdk.sh"]

CMD echo $biosdk_bioapi_impl ; \
java -Dloader.path="${loader_path_env}" -Djava.library.path=/usr/local/share/java/opencv4 -Dserver.servlet.context-path="${service_context_env}" -Dspring.cloud.config.label="${spring_config_label_env}" -Dspring.profiles.active="${active_profile_env}"  -Dspring.cloud.config.uri="${spring_config_url_env}" --add-modules=ALL-SYSTEM --add-opens java.xml/jdk.xml.internal=ALL-UNNAMED --add-opens java.base/java.lang.reflect=ALL-UNNAMED --add-opens java.base/java.lang.stream=ALL-UNNAMED --add-opens java.base/java.time=ALL-UNNAMED --add-opens java.base/java.time.LocalDate=ALL-UNNAMED --add-opens java.base/java.time.LocalDateTime=ALL-UNNAMED --add-opens java.base/java.time.LocalDateTime.date=ALL-UNNAMED --add-opens java.base/jdk.internal.reflect.DirectMethodHandleAccessor=ALL-UNNAMED -jar biosdk-services.jar
