package xingyu.lu.review.tools.qrcodez;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QRCodeTool {

    private static final String QRCODE_URL = "http://url/";
    private static final String QRCODE_LOGO_IMAGE_PATH = "/Users/lxy/IdeaProjects/introspection/qrcode/logo.jpeg";
    private static final String QRCODE_IMAGE_SAVE_PATH = "/Users/lxy/IdeaProjects/introspection/qrcode/";

    public static void main(String[] args) throws WriterException {
        try {
            for (int i = 1; i <= 3000; i++) {
                generateQRCode(1200, 1200,
                        QRCODE_URL + i,
                        QRCODE_IMAGE_SAVE_PATH + i + ".jpeg",
                        QRCODE_LOGO_IMAGE_PATH);
                Thread.sleep(1000);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成二维码
     *
     * @param qrW       二维码宽度
     * @param qrH       二维码高度
     * @param qrContext 二维码内容
     * @param qrPath    保存二维码的文件路径
     * @param logoPath  logo文件路径，不需要logo可以传null
     */
    public static boolean generateQRCode(int qrW, int qrH, String qrContext, String qrPath, String logoPath) {
        try {
            QRCodeWriter writer = new QRCodeWriter();

            Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);// 选择H档容错度，即使30%的图案被遮挡，也可以被正确扫描，这是保证之后添加LOGO图标的关键

            BitMatrix matrix = writer.encode(qrContext, BarcodeFormat.QR_CODE, qrW, qrH, hints);

            BufferedImage qrImg = MatrixToImageWriter.toBufferedImage(matrix);

            if (logoPath != null) {
                BufferedImage logoImg = ImageIO.read(new File(logoPath));

                int logoW = Math.min(logoImg.getWidth(null), qrImg.getWidth() * 2 / 10);
                int logoH = logoImg.getHeight(null) > qrImg.getHeight() * 2 / 10 ?
                        (qrImg.getHeight() * 2 / 10) : logoImg.getWidth(null);
                int logoX = (qrImg.getWidth() - logoW) / 2;
                int logoY = (qrImg.getHeight() - logoH) / 2;

                Graphics2D g = qrImg.createGraphics();
                ;

                g.drawImage(logoImg.getScaledInstance(logoX, logoY, Image.SCALE_SMOOTH),
                        logoX, logoY, logoW, logoH, null);

                g.dispose();
                qrImg.flush();
            }

            ImageIO.write(qrImg, qrPath.substring(qrPath.lastIndexOf(".") + 1), new File(qrPath));
            System.out.println("生成成功");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}