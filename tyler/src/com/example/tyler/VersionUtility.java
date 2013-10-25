package com.example.tyler;

import java.util.regex.Pattern;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

public class VersionUtility {
    
    public static boolean hasNewVersion(String latest, String current) {
    	if (latest == null) {
    		return false;
    	}
        String s1 = normalisedVersion(latest);
        String s2 = normalisedVersion(current);
        return s1.compareTo(s2) > 0;
    }

	public static String getCurrentVersion(Context ctx) {
		PackageManager packageManager = ctx.getPackageManager();
	    PackageInfo packInfo = null;
		try {
			packInfo = packageManager.getPackageInfo(ctx.getPackageName(),0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return packInfo.versionName;
	}

    private static String normalisedVersion(String version) {
        return normalisedVersion(version, ".", 4);
    }

    private static String normalisedVersion(String version, String sep, int maxWidth) {
        String[] split = Pattern.compile(sep, Pattern.LITERAL).split(version);
        StringBuilder sb = new StringBuilder();
        for (String s : split) {
            sb.append(String.format("%" + maxWidth + 's', s));
        }
        return sb.toString();
    }
}
