package de.mslab.ciphers;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class LED112Test extends AbstractCipherTest {
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		cipher = new LED112();
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		cipher = null;
	}

	@Test
	public void testDecrypt() {
		int[] key = { 0x01, 0x23, 0x45, 0x67, 0x89, 0xab, 0xcd, 0xef, 0x01, 0x23, 0x45, 0x67, 0x89, 0xab };
		int[] plaintext = { 0x01, 0x23, 0x45, 0x67, 0x89, 0xab, 0xcd, 0xef };
		int[] ciphertext = { 0xb9, 0x69, 0xb2, 0x32, 0xbf, 0x31, 0xf2, 0x18 };
		testDecryption(key, plaintext, ciphertext);
	}
	
	@Test
	public void testDecryptEmptyValues() {
		int[] key = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
		int[] plaintext = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
		int[] ciphertext = { 0x16, 0x50, 0xb2, 0x30, 0xe8, 0xf9, 0x28, 0xbb };
		testDecryption(key, plaintext, ciphertext);
	}
	
	@Test
	public void testEncrypt() {
		int[] key = { 0x01, 0x23, 0x45, 0x67, 0x89, 0xab, 0xcd, 0xef, 0x01, 0x23, 0x45, 0x67, 0x89, 0xab };
		int[] plaintext = { 0x01, 0x23, 0x45, 0x67, 0x89, 0xab, 0xcd, 0xef };
		int[] ciphertext = { 0xb9, 0x69, 0xb2, 0x32, 0xbf, 0x31, 0xf2, 0x18 };
		testEncryption(key, plaintext, ciphertext);
	}
	
	@Test
	public void testEncryptEmptyValues() {
		int[] key = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
		int[] plaintext = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
		int[] ciphertext = { 0x16, 0x50, 0xb2, 0x30, 0xe8, 0xf9, 0x28, 0xbb };
		testEncryption(key, plaintext, ciphertext);
	}
	
}
