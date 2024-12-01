package com.ColPlat.Backend.utils;

import lombok.AllArgsConstructor;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import net.coobird.thumbnailator.Thumbnails;

public class ImageUtils {

    private static volatile ImageUtils instance;

    private ImageUtils() {}

    public static ImageUtils getInstance() {
        if (instance == null) {
            synchronized (ImageUtils.class) {
                if (instance == null) {
                    instance = new ImageUtils();
                }
            }
        }
        return instance;
    }

    public byte[] compressPngImageToThumbnail(byte[] originalImage) throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(originalImage);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        Thumbnails.of(inputStream)
                .size(200, 200)
                .outputFormat("png")
                .toOutputStream(outputStream);

        return outputStream.toByteArray();
    }
}
