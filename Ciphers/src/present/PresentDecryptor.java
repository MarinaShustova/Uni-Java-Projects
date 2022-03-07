package present;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

public class PresentDecryptor {
    ArrayList<ByteArray> roundKeys;
    File source;
    public final int[] INVERSE_SBOX = {
            0x05, 0x0E, 0x0F, 0x08, 0x0C, 0x01, 0x02, 0x0D, 0x0B, 0x04, 0x06, 0x03, 0x00, 0x07, 0x09, 0x0A
    };
    public final int[] INVERSE_PERMUTATION = {
            0x00, 0x04, 0x08, 0x0c, 0x10, 0x14, 0x18, 0x1c, 0x20, 0x24, 0x28, 0x2c, 0x30, 0x34, 0x38, 0x3c,
            0x01, 0x05, 0x09, 0x0d, 0x11, 0x15, 0x19, 0x1d, 0x21, 0x25, 0x29, 0x2d, 0x31, 0x35, 0x39, 0x3d,
            0x02, 0x06, 0x0a, 0x0e, 0x12, 0x16, 0x1a, 0x1e, 0x22, 0x26, 0x2a, 0x2e, 0x32, 0x36, 0x3a, 0x3e,
            0x03, 0x07, 0x0b, 0x0f, 0x13, 0x17, 0x1b, 0x1f, 0x23, 0x27, 0x2b, 0x2f, 0x33, 0x37, 0x3b, 0x3f
    };

    PresentDecryptor(String filename) {
        source = new File(filename);
    }

    public void writeDecrypted(){
        ByteArray decrypted = decrypt();
        File file = new File("presentDecrypted.txt");
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            fileOutputStream.write(toByteArray(decrypted));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private byte[] toByteArray(ByteArray source) {
        byte[] res = new byte[source.length()-2];
        for (int i = 0; i < source.length()-2; ++i) {
            res[i] = (byte) source.get(i);
        }
        return res;
    }

    public ByteArray decrypt() {
        ByteArray result = null;
        try {
            byte[] fileContent = Files.readAllBytes(source.toPath());
            result = new ByteArray(fileContent.length);
            ByteArray content = new ByteArray(fileContent);
            int size = fileContent.length;
            int pos = 0;
            while (pos < size) {
                ByteArray current;
                if (size - pos >= 8) {
                    current = new ByteArray(8);
                    current.copyBytes(content, pos, 0, 8);
                    result.copyBytes(decrypt(current), 0, pos, 8);
                } else {
                    current = new ByteArray(8);
                    current.copyBytes(content, pos, 0, size - pos);
                    result.copyBytes(decrypt(current), 0, pos, size - pos);
                }
                pos += 8;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public ByteArray decrypt(ByteArray block) {
        ByteArray decrypted = block.clone();

        for (int i = 0; i < 31; ++i) {
            decrypted = decrypted.xor(roundKeys.get(roundKeys.size()-1-i), 0, 8);
            decrypted = permute(decrypted);
            decrypted = subBytes(decrypted);
        }
        decrypted.xor(roundKeys.get(0), 0, 8);
        return decrypted;
    }

    private ByteArray permute(ByteArray state) {
        ByteArray newState = state.clone();

        for (int i = 0; i < Long.SIZE; i++) {
            newState.setBit(i, state.getBit(INVERSE_PERMUTATION[i]));
        }

        return newState;
    }

    private ByteArray subBytes(ByteArray state) {
        int value, msbNibble, lsbNibble;

        for (int i = 0; i < 8; i++) {
            value = state.get(i);
            lsbNibble = INVERSE_SBOX[value & 0x0F];
            msbNibble = INVERSE_SBOX[(value & 0xF0) >> 4] << 4;
            state.set(i, msbNibble | lsbNibble);
        }

        return state;
    }
}