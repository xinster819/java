package utils;

import static org.bytedeco.javacpp.lept.pixDestroy;
import static org.bytedeco.javacpp.lept.pixRead;

import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.lept.PIX;
import org.bytedeco.javacpp.tesseract.TessBaseAPI;

/**
 * 图片识别，用google的tesseract图片识别库，效果还不错
 * 
 * @author alchemist
 */
public class OCRUtil {

    final static String DATA_PATH = "others";
    final static String LANGUAGE = "chi_sim";

    public static String imageRead(String des) {
        return imageRead(des, LANGUAGE);
    }

    public static String imageRead(String des, String language) {

        TessBaseAPI TBA = new TessBaseAPI();
        if (TBA.Init(DATA_PATH, language) != 0) {
            System.err.println("Could not initialize tesseract.");
            System.exit(1);
        }
        BytePointer outText;
        // Open input image with leptonica library
        PIX image = pixRead(des);
        TBA.SetImage(image);
        // Get OCR result
        outText = TBA.GetUTF8Text();
        String result = outText.getString();
        System.out.println(result);
        // Destroy used object and release memory
        TBA.End();
        outText.deallocate();
        pixDestroy(image);
        return RegexUtil.specharsReplace(result);
    }

    public static void main(String[] args) {
        System.out.println(imageRead("others/testImage/jgz.jpeg"));
        System.out.println(imageRead("others/testImage/test.png"));
    }
}
