package com.johannesqvarford.setup.env;

import com.johannesqvarford.setup.opt.Options;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Locale;

public interface Environment {
    void execute(Options options) throws IOException, InterruptedException;
    Path vscodeSettingsDirectory();
    void installPackages(String... packageNames) throws IOException, InterruptedException;

    static Environment current() throws IOException {
        switch(getOperatingSystemType())
        {
            case MacOS:
                return new OSXEnvironment();
            case Windows:
            case Linux:
            case Other:
            default:
                throw new IOException("not supported");
        }
    }

    public static OSType getOperatingSystemType() {
        String OS = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
        if ((OS.indexOf("mac") >= 0) || (OS.indexOf("darwin") >= 0)) {
            return OSType.MacOS;
        } else if (OS.indexOf("win") >= 0) {
            return OSType.Windows;
        } else if (OS.indexOf("nux") >= 0) {
            return OSType.Linux;
        } else {
            return OSType.Other;
        }
    }

    public enum OSType {
        Windows, MacOS, Linux, Other
    };
}