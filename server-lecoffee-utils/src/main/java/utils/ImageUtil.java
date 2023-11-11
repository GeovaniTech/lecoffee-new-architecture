package utils;

import java.util.Base64;

public class ImageUtil {
	public static String geRenderedImage(byte[] imageBytes) {
		return Base64.getEncoder().encodeToString(imageBytes);
	}
}
