package com.company.pm.common.utils;

import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Mono;

public class FileUtils {
    
    public static Mono<byte[]> readBytesContent(FilePart filePart) {
        return filePart.content().flatMap(dataBuffer -> {
            byte[] bytes = new byte[dataBuffer.readableByteCount()];
            dataBuffer.read(bytes);
            return Mono.just(bytes);
        }).last();
    }
}
