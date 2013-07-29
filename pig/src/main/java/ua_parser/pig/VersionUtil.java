package ua_parser.pig;

import ua_parser.OS;
import ua_parser.UserAgent;

public class VersionUtil {
	
	public static String toFullNameString(String family, String versionString){
		if (versionString != null && !versionString.isEmpty())
			return String.format("%s %s", family, versionString);
		else
			return family;
	}

	public static String toFullNameString(UserAgent ua){
		return toFullNameString(ua.family, toVersionString(ua));
	}
	
	public static String toFullNameString(OS os){
		return toFullNameString(os.family, toVersionString(os));
	}
	
	public static String toVersionString(String major, String minor,
			String patch, String patchMinor) {

		StringBuffer b = new StringBuffer();
		
		if (major != null && !major.isEmpty()) {
			b.append(major);
			if (minor != null && !minor.isEmpty()) {
				if (Character.isDigit(minor.charAt(0))) {
					b.append('.');
				}
				b.append(minor);
				if (patch != null && !patch.isEmpty()) {
					if (Character.isDigit(patch.charAt(0))) {
						b.append('.');
					}
					b.append(patch);
					if (patchMinor != null && !patchMinor.isEmpty()) {
						if (Character.isDigit(patchMinor.charAt(0))) {
							b.append('.');
						}
						b.append(patchMinor);
					}
				}
			}
		}
		return b.toString();
	}
	
	public static String toVersionString(UserAgent ua){
		return toVersionString(ua.major, ua.minor, ua.patch, null);
	}
	
	public static String toVersionString(OS os){
		return toVersionString(os.major, os.minor, os.patch, os.patchMinor);
	}
}
