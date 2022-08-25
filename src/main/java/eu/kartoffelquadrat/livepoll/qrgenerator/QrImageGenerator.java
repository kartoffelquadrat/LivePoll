package eu.kartoffelquadrat.livepoll.qrgenerator;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Helper class for all functionality related to QR code generating.
 */
@Component
public class QrImageGenerator {

    @Value("${qrcode.pixel.dimensions}")
    private int DIMENSIONS = 128;

    /**
     * Turns a provided String into a QR code, represented as BitMatrix (not yet persisted to file system).
     *
     * @param content as the string to encode.
     * @return BitMatrix as the actual QR code, represented as java object (not yet a file on disk).
     * @throws WriterException in case the encoding failed.
     */
    public BitMatrix encodeQr(String content) throws WriterException {

        // Ensure String is in UTF-8 encoding by converting to byte stream first
//        byte[] contentBytes = content.getBytes(StandardCharsets.UTF_8);
//        String UTF8ContentString = contentBytes.toString();

        // Generate the matrix (non file-system representation of qr code)
        BitMatrix matrix = new MultiFormatWriter().encode(content.toString(), BarcodeFormat.QR_CODE, DIMENSIONS, DIMENSIONS);
        return matrix;
    }

    /**
     * Exports a provided QR code as PNG to disk (uses system temp directory)
     *
     * @param filename as the name of the file (without path information), inluding file extension.
     * @param qr       as the BitMatrix encoding the qrcode as object.
     * @return the location on disk where the qr encoded file is stored.
     * @throws IOException in case the persistence to disk fails.
     */
    public String exportQrToDisk(String filename, BitMatrix qr) throws IOException {

        File qrFile = new File(System.getProperty("java.io.tmpdir"), filename);
        MatrixToImageWriter.writeToPath(qr, "PNG", qrFile.toPath());
        return qrFile.toString();
    }
}
