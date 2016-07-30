package com.sohu.mpV2.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifIFD0Directory;

import net.coobird.thumbnailator.Thumbnails;

public class ImageUtil {

    public static void test(byte[] b) throws IOException {
        InputStream in = new ByteArrayInputStream(b);
        BufferedImage image = Thumbnails.of(in).scale(1).asBufferedImage();
        File outputfile = new File("D:\\image.jpg");
        ImageIO.write(image, "jpg", outputfile);
    }
    
    public static void test1(byte[] bytes) throws IOException, ImageProcessingException, MetadataException {
        Metadata r = ImageMetadataReader.readMetadata(new ByteArrayInputStream(bytes), bytes.length);
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(bytes));
        ExifIFD0Directory exif = r.getFirstDirectoryOfType(ExifIFD0Directory.class);
        int orientation = exif.getInt(ExifIFD0Directory.TAG_ORIENTATION);
        int width = image.getWidth();
        int height = image.getHeight();
        AffineTransform t = new AffineTransform();

        switch (orientation) {
        case 1:
            break;
        case 2: // Flip X
            t.scale(-1.0, 1.0);
            t.translate(width, 0);
            break;
        case 3: // PI rotation 
            t.translate(width, height);
            t.rotate(Math.PI);
            break;
        case 4: // Flip Y
            t.scale(1.0, -1.0);
            t.translate(0, -height);
            break;
        case 5: // - PI/2 and Flip X
            t.rotate(-Math.PI / 2);
            t.scale(-1.0, 1.0);
            break;
        case 6: // -PI/2 and -width
            t.translate(height, 0);
            t.rotate(Math.PI / 2);
            break;
        case 7: // PI/2 and Flip
            t.scale(-1.0, 1.0);
            t.translate(-height, 0);
            t.translate(0, width);
            t.rotate(  3 * Math.PI / 2);
            break;
        case 8: // PI / 2
            t.translate(0, width);
            t.rotate(  3 * Math.PI / 2);
            break;
        }
        AffineTransformOp op = new AffineTransformOp(t, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        BufferedImage destinationImage = op.createCompatibleDestImage(image, (image.getType() == BufferedImage.TYPE_BYTE_GRAY) ? image.getColorModel() : null );
        Graphics2D g = destinationImage.createGraphics();
        g.setBackground(Color.WHITE);
        g.clearRect(0, 0, destinationImage.getWidth(), destinationImage.getHeight());
        File outputfile = new File("D:\\imagea.jpg");
        destinationImage = op.filter(image, destinationImage);
        ImageIO.write(destinationImage, "jpg", outputfile);
        FileUtils.writeByteArrayToFile(new File("D:\\aaa.jpg"), bytes);
    }
    
}
