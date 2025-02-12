package com.github.cleydyr.dart.system.io;

import com.github.cleydyr.dart.release.DartSassReleaseParameter;
import java.io.File;

public interface ReleaseDownloader {
    void download(DartSassReleaseParameter release, File destination) throws ReleaseDownloadException;
}
