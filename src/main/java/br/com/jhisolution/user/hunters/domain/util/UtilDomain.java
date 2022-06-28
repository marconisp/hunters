package br.com.jhisolution.user.hunters.domain.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

public class UtilDomain {

    public static byte[] convertBufferedImageToByte(BufferedImage image, String tipoImage) {
        byte[] imageInByte = null;

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            if ("image/jpg".equalsIgnoreCase(tipoImage)) {
                ImageIO.write(image, "jpg", baos);
            } else if ("image/jpeg".equalsIgnoreCase(tipoImage)) {
                ImageIO.write(image, "jpeg", baos);
            } else if ("image/png".equalsIgnoreCase(tipoImage)) {
                ImageIO.write(image, "png", baos);
            }

            baos.flush();
            imageInByte = baos.toByteArray();
            baos.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return imageInByte;
    }
}
