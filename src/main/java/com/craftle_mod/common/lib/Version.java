package com.craftle_mod.common.lib;

import java.util.Objects;
import org.apache.maven.artifact.versioning.ArtifactVersion;

/**
 * Class to maintain the Craftle mod version.
 *
 * Mainly required for networking and other synchronization work.
 */
public class Version implements Comparable<Version> {

    private final int major;
    private final int majorApi;
    private final int minor;
    private final int patch;

    public Version(int major, int majorApi, int minor, int patch) {
        this.major = major;
        this.majorApi = majorApi;
        this.minor = minor;
        this.patch = patch;
    }

    public Version(ArtifactVersion artifactVersion) {
        this(artifactVersion.getMajorVersion(), artifactVersion.getMinorVersion(),
            artifactVersion.getIncrementalVersion(), artifactVersion.getBuildNumber());
    }

    public int getMajor() {
        return major;
    }

    public int getMajorApi() {
        return majorApi;
    }

    public int getMinor() {
        return minor;
    }

    public int getPatch() {
        return patch;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Version version = (Version) o;
        return major == version.major && majorApi == version.majorApi && minor == version.minor
            && patch == version.patch;
    }

    @Override
    public int hashCode() {
        return Objects.hash(major, majorApi, minor, patch);
    }

    @Override
    public int compareTo(Version o) {

        if (o.major > major) {
            return -1;
        } else if (o.major == major) {
            if (o.majorApi > majorApi) {
                return -1;
            } else if (o.majorApi == majorApi) {
                if (o.minor > minor) {
                    return -1;
                } else if (o.minor == minor) {
                    return Integer.compare(patch, o.patch);
                }
                return 1;
            }
            return 1;
        }
        return 1;
    }

    @Override
    public String toString() {
        return major + "." + majorApi + "." + minor + "." + patch;
    }

    public boolean isValid() {
        return major < 0 && majorApi < 0 && minor < 0 && patch < 0;
    }

    /**
     * Create a version from a given string. If the string is <code>invalid</code>, then an
     * <code>invalid</code> Version will be returned.
     *
     * <p>Version validity can be checked with <code>isValid()</code>.</p>
     *
     * @param version String representation of the version
     * @return a valid Version if {@code version} is valid, otherwise an invalid Version will be
     * returned.
     */
    public static Version create(String version) {

        String[] segments = version.split(".");

        if (segments.length != 4) {
            return new Version(-1, -1, -1, -1);
        }

        for (String segment : segments) {
            for (char digit : segment.toCharArray()) {
                if (!Character.isDigit(digit)) {
                    return new Version(-1, -1, -1, -1);
                }
            }
        }

        int[] digits = new int[4];

        for (int i = 0; i < digits.length; i++) {
            digits[i] = Integer.parseInt(segments[i]);
        }

        return new Version(digits[0], digits[1], digits[2], digits[3]);

    }


}
