package com.zhujunji.common.utils;

import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

@Slf4j
public class ImageUtil {

    static Optional<String> imageToBase64(File image) {
        if (image == null || !image.exists() || !image.isFile()) {
            return Optional.empty();
        }
        String fileName = image.getName();
        Optional<String> formatNameOptional = FileUtil.getSuffix(fileName);
        log.info("image to base64 fileName: {} formatName: {}", fileName, formatNameOptional.orElse(""));
        String imageBase64 = formatNameOptional
                .map(formatName -> {

                    try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                        BufferedImage bufferedImage = ImageIO.read(image);
                        ImageIO.write(bufferedImage, formatName, outputStream);
                        return Base64.getEncoder().encodeToString(outputStream.toByteArray());
                    } catch (IOException e) {
                        log.error("image to base64 failed!", e);
                    }
                    return null;
                })
                .orElse(null);

        return Optional.ofNullable(imageBase64);
    }

    public static void main(String[] args) {
        Optional<String> imageBase64Optional = imageToBase64(new File("C:\\Image_20210806180506.png"));
        imageBase64Optional.ifPresent(System.out::println);
    }

}
