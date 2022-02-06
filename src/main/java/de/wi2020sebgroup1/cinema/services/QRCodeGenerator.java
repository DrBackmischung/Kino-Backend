package de.wi2020sebgroup1.cinema.services;

import java.io.ByteArrayOutputStream;

import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

@Service
public class QRCodeGenerator {
	
	public static byte[] generateQRCode(String msg) {

		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		try {
			msg.trim();
			BitMatrix bitMatrix = qrCodeWriter.encode(msg, BarcodeFormat.QR_CODE, 800, 800);
			
			ByteArrayOutputStream pngOutPutStream = new ByteArrayOutputStream();
			@SuppressWarnings("unused")
			MatrixToImageConfig conf = new MatrixToImageConfig(0x000000, 0xFFFFFF);
			
			MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutPutStream);
			byte[] pngData = pngOutPutStream.toByteArray();
			return pngData;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

}
