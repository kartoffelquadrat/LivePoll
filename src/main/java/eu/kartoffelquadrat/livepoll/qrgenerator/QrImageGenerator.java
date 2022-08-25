package eu.kartoffelquadrat.livepoll.qrgenerator;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Helper class for all functionality related to QR code generating.
 */
public class QrImageGenerator {

    private static final int DIMENSIONS = 32;

    /**
     * Turns a provided String into a QR code, represented as BitMatrix (not yet persisted to file system).
     *
     * @param content as the stirng to encode.
     * @return BitMatrix as the actual QR code, represented as java object (not yet a file on disk).
     * @throws WriterException in case the encodign failed.
     */
    public static BitMatrix encodeQr(String content) throws WriterException {

        // Ensure String is in UTF-8 encoding by converting to byte stream first
        byte[] contentBytes = content.getBytes(StandardCharsets.UTF_8);
        String UTF8ContentString = contentBytes.toString();

        // Generate the matrix (non file-system representation of qr code)
        BitMatrix matrix = new MultiFormatWriter().encode(UTF8ContentString, BarcodeFormat.QR_CODE, DIMENSIONS, DIMENSIONS);
        return matrix;
    }

    public static void exportQrToDisk(String filename, BitMatrix qr) throws IOException {

        File qrFile = new File(System.getProperty("java.io.tmpdir"), filename);
        MatrixToImageWriter.writeToPath(qr, "svg", qrFile.toPath());

    }
}
