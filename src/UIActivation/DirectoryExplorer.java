package UIActivation;

import java.awt.GraphicsEnvironment;

public class DirectoryExplorer {
    public static void main(String[] args) {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		String[] fontFamilies = ge.getAvailableFontFamilyNames();

		for (String fontFamily : fontFamilies) {
			System.out.println(fontFamily);
		}
    }
}