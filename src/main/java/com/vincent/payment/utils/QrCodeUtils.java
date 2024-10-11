package com.vincent.payment.utils;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 二维码数据转换为base64
 *
 */
public class QrCodeUtils {

    public static String crateQRCode(String content) {
        if (StrUtil.isNotEmpty(content)) {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8"); //指定字符编码
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H); // 指定纠错等级
            hints.put(EncodeHintType.MARGIN, 1); //设置图片的边距
            try {
                QRCodeWriter writer = new QRCodeWriter();
                BitMatrix bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, 256, 256, hints);
                BufferedImage imageBytes = MatrixToImageWriter.toBufferedImage(bitMatrix);
                ImageIO.write(imageBytes,"png",os);
                return "data:image/jpeg;base64," + Base64.encode(os.toByteArray());
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
