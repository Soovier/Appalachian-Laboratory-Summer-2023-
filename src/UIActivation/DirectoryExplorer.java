package UIActivation;

import java.io.File;

public class DirectoryExplorer {
    public static void main(String[] args) {
//		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
//		String[] fontFamilies = ge.getAvailableFontFamilyNames();
//
//		for (String fontFamily : fontFamilies) {
//			System.out.println(fontFamily);
//		}

		File v1 = new File("UI-Items/nedsdasdwbackgdround.jpg");
		System.out.println(v1.getAbsoluteFile());

    }
}