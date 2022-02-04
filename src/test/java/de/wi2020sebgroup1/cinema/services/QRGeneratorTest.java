package de.wi2020sebgroup1.cinema.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations="classpath:test.properties")
public class QRGeneratorTest {
	
	@Autowired
	QRCodeGenerator qrGenerator;
	
	@Test
	void testGenerate() throws Exception {
		assertDoesNotThrow(new Executable() {
            @SuppressWarnings("static-access")
			@Override
            public void execute() {
            	qrGenerator.generateQRCode("Hallo");  
            }
        });
	}
	
	@Test
	void testGenerateException() throws Exception {
		assertDoesNotThrow(new Executable() {
            @SuppressWarnings("static-access")
			@Override
            public void execute() {
            	qrGenerator.generateQRCode(null);  
            }
        });
	}
	
//	@Test
//	void testGenerateException() throws Exception {
//		assertThrows(Exception.class, new Executable() {
//            @SuppressWarnings("static-access")
//			@Override
//            public void execute() {
//            	qrGenerator.generateQRCode(null);        
//            }
//        });
//	}

}
