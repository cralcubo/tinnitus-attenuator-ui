package bo.roman.tinnitus.ui.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ResourcesLocatorUtil {
	
	public static URL findFileUrl(String fxmlPath) throws MalformedURLException {
		Path p = Paths.get(fxmlPath);
		if (p != null) {
			return p.toUri().toURL();
		}

		return null;
	}

}
