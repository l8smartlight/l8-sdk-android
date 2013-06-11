package com.l8smartlight.sdk.android.bluetooth;

import java.io.IOException;
import java.io.InputStream;

public interface CRC8 {

        /**
         * Calculates a CRC8 from the given data block.
         * @param block the array from which the CRC is calculated
         * @param from the initial index of the range to be copied, inclusive
         * @param to the final index of the range to be copied, exclusive. (This index may lie outside the array.)
         * @return the calculated CRC8 (8-bit)
         */
        public abstract int calc(byte[] block, int from, int to);

        /**
         * Calculates a CRC8 from the given data block.
         * @param block the array from which the CRC is calculated
         * @param from the initial index of the range to be copied, inclusive
         * @param to the final index of the range to be copied, exclusive. (This index may lie outside the array.)
         * @param initial the initial CRC value (normally 0)
         * @return the calculated CRC8 (8-bit)
         */
        public abstract int calc(byte[] block, int from, int to, int initial);

        /**
         * Calculates a CRC8 from the given data block.
         * @param block the array from which the CRC is calculated
         * @param initial the initial CRC value (normally 0)
         * @return the calculated CRC8 (8-bit)
         */
        public abstract int calc(byte[] block, int initial);

        /**
         * Calculates a CRC8 from the given data block.
         * @param block the array from which the CRC is calculated
         * @return the calculated CRC8 (8-bit)
         */
        public abstract int calc(byte[] block);

        /**
         * Calculates a CRC8 from the given InputStream. 
         * @param stream the stream to calculate the CRC8 from
         * @return the calculated CRC8 (8-bit)
         * @throws IOException if an I/O error occurs
         */
        public abstract int calc(InputStream stream) throws IOException;

        /**
         * Calculates a CRC8 from the given InputStream. 
         * @param stream the stream to calculate the CRC8 from
         * @param initial the initial CRC value (normally 0)
         * @return the calculated CRC8 (8-bit)
         * @throws IOException if an I/O error occurs
         */
        public abstract int calc(InputStream stream, int initial) throws IOException;

        /**
         * Calculates a CRC8 from the given InputStream. Note that this function may return before count has been reached if an end of stream occurred. 
         * @param stream the stream to calculate the CRC8 from
         * @param length the number of bytes to read
         * @param initial the initial CRC value (normally 0)
         * @return the calculated CRC8 (8-bit)
         * @throws IOException if an I/O error occurs
         */
        public abstract int calc(InputStream stream, int length, int initial) throws IOException;
}