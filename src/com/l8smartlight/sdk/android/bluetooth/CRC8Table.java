package com.l8smartlight.sdk.android.bluetooth;
import java.io.IOException;
import java.io.InputStream;

public class CRC8Table implements CRC8 {
        
        private final int[] table;
        
        public CRC8Table(int polynomial) {
                table = generateTable(polynomial);
        }

        public CRC8Table(int[] table) {
                this.table = table;
        }
        
        public static byte[] compress(int[] table) {
                byte[] tmp = new byte[table.length];
                for (int i = 0; i < table.length; ++i) {
                        tmp[i] = (byte)table[i];
                }
                return tmp;
        }

        public static int[] decompress(byte[] table) {
                int[] tmp = new int[table.length];
                for (int i = 0; i < table.length; ++i) {
                        tmp[i] = table[i] & 0xFF;
                }
                return tmp;
        }
        
        /*private static int pushByte(int b, int polynomial) {
                for (int i = 0; i < 8; ++i) {
                        if ((b & 1) != 0) {
                                int step1 = b >> 1;
                                b = step1 ^ polynomial;
                        } else {
                                b = b >> 1;
                        }
                }
                return b;
        }*/
        
        private static int pushByte(int b, int polynomial) {
            for (int i = 0; i < 8; ++i) {
                    if ((b & 0x80) != 0) {
                            int step1 = b << 1;
                            b = step1 ^ polynomial;
                    } else {
                            b = b << 1;
                    }
            }
            return b&0xFF;
    }

        /**
         * Creates a CRC8 table given a polynomial.
         * @param polynomial
         * @return
         */
        public static int[] generateTable(int polynomial) {
                int[] table = new int[256];
                for (int i = 0; i < 256; ++i) {
                        table[i] = pushByte(i, polynomial);
                }
                return table;
        }
        
        private int calcImpl(byte[] block, int from, int to, int initial) {
                int crc = initial;
                for(int i = from; i < to; ++i) {
                        crc = getTable()[crc ^ (block[i] & 0xFF)];
                }
                return crc;
        }

        /* (non-Javadoc)
         * @see CRC8#calc(byte[], int, int)
         */
        @Override
        public int calc(byte[] block, int from, int to) {
                return calcImpl(block, from, to, 0);
        }

        /* (non-Javadoc)
         * @see CRC8#calc(byte[], int, int, int)
         */
        @Override
        public int calc(byte[] block, int from, int to, int initial) {
                return calcImpl(block, from, to, initial);
        }

        /* (non-Javadoc)
         * @see CRC8#calc(byte[], int)
         */
        @Override
        public int calc(byte[] block, int initial) {
                return calcImpl(block, 0, block.length, initial);
        }
        
        /* (non-Javadoc)
         * @see CRC8#calc(byte[])
         */
        @Override
        public int calc(byte[] block) {
                return calc(block, 0, block.length, 0);
        }
        
        /* (non-Javadoc)
         * @see CRC8#calc(java.io.InputStream)
         */
        @Override
        public int calc(InputStream stream) throws IOException {
                return calc(stream, 0);
        }
        
        /* (non-Javadoc)
         * @see CRC8#calc(java.io.InputStream, int)
         */
        @Override
        public int calc(InputStream stream, int initial) throws IOException {
                int b;
                int crc = initial;
                while((b = stream.read()) != -1) {
                        crc = getTable()[crc ^ (b & 0xFF)];                     
                }
                return crc;
        }

        /* (non-Javadoc)
         * @see CRC8#calc(java.io.InputStream, int, int)
         */
        @Override
        public int calc(InputStream stream, int length, int initial) throws IOException {
                int b;
                int crc = initial;
                for (int i = 0; (b = stream.read()) != -1 || i < length; ++i) {
                        crc = getTable()[crc ^ (b & 0xFF)];
                }
                return crc;
        }

        public int[] getTable() {
                return table;
        }
        
}