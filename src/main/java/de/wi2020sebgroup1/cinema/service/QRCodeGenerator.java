package de.wi2020sebgroup1.cinema.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

@Service
public class QRCodeGenerator {
	
	public static byte[] generateQRCode(UUID bookingID) {
		
		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		try {
			BitMatrix bitMatrix = qrCodeWriter.encode(bookingID.toString(), null, 800, 800);
			
			ByteArrayOutputStream pngOutPutStream = new ByteArrayOutputStream();
			MatrixToImageConfig conf = new MatrixToImageConfig(0x000000, 0xFFFFFF);
			
			MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutPutStream);
			byte[] pngData = pngOutPutStream.toByteArray();
			return pngData;
		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return null;
		
	}

}
