package com.ColPlat.Backend.utils;

import com.sun.tools.javac.Main;
import lombok.AllArgsConstructor;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

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

    public byte[] getDefaultThumbnailImage() throws IOException {
        byte[] defaultProfilePic = null;
        try (InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("images/default_profile_picture.png")) {
            if (inputStream != null) {
                defaultProfilePic = inputStream.readAllBytes();
            } else {
                throw new RuntimeException("Slika nije pronađena u resources folderu!");
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Greška prilikom učitavanja slike!");
        } finally {
            return defaultProfilePic;
        }
    }
}
