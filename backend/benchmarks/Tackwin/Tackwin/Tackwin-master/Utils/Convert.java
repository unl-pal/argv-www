public class Convert {
	/**
	 * Convert a short(16bit) to two byte(8bit) in an array. Using Little-Endian
	 * 
	 * @param value
	 * @return
	 */
	public static byte[] shortToBytes(short value) {
		return new byte[] { (byte) (value), (byte) (value >> 8) };
	}
	/**
	 * Convert an array of two byte(8bit) to a short(16bit). Using Little-Endian
	 * 
	 * @param value
	 * @return
	 */
	public static short bytesToShort(byte[] value) {
		return (short) ((value[0] & 0xFF) + (short) (value[1] << 8));
	}
	/**
	 * Convert a long(64bit) to height byte(8bit) in an array. Using Little-Endian
	 * 
	 * @param value
	 * @return
	 */
	public static byte[] longToBytes(long value) {
		return new byte[] { (byte) (value), 
							(byte) (value >> 8), 
							(byte) (value >> 16),
						    (byte) (value >> 24), 
						    (byte) (value >> 32), 
						    (byte) (value >> 40),
							(byte) (value >> 48), 
							(byte) (value >> 56) };
	}
	/**
	 * Convert an array of eight bytes(8bit) to a long(64bit). Using Little-Endian
	 * 
	 * @param value
	 * @return
	 */
	public static long bytesToLong(byte[] value) {
		return   ((long) (value[0] & 0xFF)) | 
				(((long) (value[1] & 0xFF)) << 8) | 
				(((long) (value[2] & 0xFF)) << 16) |
				(((long) (value[3] & 0xFF)) << 24) | 
				(((long) (value[4] & 0xFF)) << 32) |
				(((long) (value[5] & 0xFF)) << 40) |
				(((long) (value[6] & 0xFF)) << 48) |
				(((long) (value[7] & 0xFF)) << 56);
	}
}
