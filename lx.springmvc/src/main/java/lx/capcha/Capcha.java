package lx.capcha;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import jj.play.ns.nl.captcha.Captcha;
import jj.play.ns.nl.captcha.backgrounds.GradiatedBackgroundProducer;
import jj.play.ns.nl.captcha.gimpy.FishEyeGimpyRenderer;
import jj.play.ns.nl.captcha.noise.StraightLineNoiseProducer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Capcha {

    static Logger LOGGER = LoggerFactory.getLogger(Capcha.class);

    public static byte[] getCapcha() {
        Captcha capcha = new Captcha.Builder(200, 50).addText().addBackground(new GradiatedBackgroundProducer())
                .addNoise(new StraightLineNoiseProducer()).gimp(new FishEyeGimpyRenderer()).addBorder().build();
        BufferedImage image = capcha.getImage();
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(image, "png", out);
            return out.toByteArray();
        } catch (IOException e) {
            LOGGER.error("some wrong! ", e);
        }
        return null;
    }

    public static void main(String[] args) throws IOException {
        Captcha capcha = new Captcha.Builder(200, 50).addText().addBackground(new GradiatedBackgroundProducer())
                .addNoise(new StraightLineNoiseProducer()).gimp(new FishEyeGimpyRenderer()).addBorder().build();
        BufferedImage image = capcha.getImage();
        for (int i = 1; i < 3; i++) {
            ImageIO.write(image, "png", new ByteArrayOutputStream());
        }
    }

}
