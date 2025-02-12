package com.github.cleydyr.dart.net;

import com.github.cleydyr.dart.release.DartSassReleaseParameter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.URL;
import javax.inject.Named;
import javax.inject.Singleton;
import org.apache.maven.settings.MavenSettingsBuilder;
import org.apache.maven.settings.Settings;
import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.component.annotations.Requirement;
import org.codehaus.plexus.logging.Logger;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.kohsuke.github.*;
import org.kohsuke.github.extras.ImpatientHttpConnector;

@Singleton
@Named
@SuppressWarnings("deprecation")
@Component(role = GithubLatestVersionProvider.class)
public class NetworkingGithubLatestVersionProvider implements GithubLatestVersionProvider {
    @Requirement
    private Logger logger;

    @Requirement
    private MavenSettingsBuilder mavenSettingsBuilder;

    private final GithubLatestVersionProvider fallbackVersionProvider = new DummyGithubLatestVersionProvider();

    @Override
    public String get(String os, String arch) {
        try {
            GitHub github = new GitHubBuilder()
                    .withConnector(new ImpatientHttpConnector(this::setupConnection))
                    .build();

            GHRepository repository = github.getRepository("sass/dart-sass");

            PagedIterable<GHRelease> ghReleases = repository.listReleases();

            for (GHRelease ghRelease : ghReleases) {
                logger.debug("Checking release " + ghRelease.getName());

                String version = ghRelease.getTagName();

                DartSassReleaseParameter dartSassReleaseParameter = new DartSassReleaseParameter(os, arch, version);

                for (GHAsset ghAsset : ghRelease.getAssets()) {
                    logger.debug("Checking asset " + ghAsset.getName());

                    if (ghAsset.getName().equals(dartSassReleaseParameter.getArtifactName())) {
                        return version;
                    }
                }

                logger.info("Skipping version " + version + " because it doesn't have a matching asset");
            }
        } catch (IOException e) {
            logger.warn("Error while getting latest version from GitHub API", e);
        }

        logger.warn("Falling back to latest known release (" + fallbackVersionProvider.get(os, arch) + ")");

        return fallbackVersionProvider.get(os, arch);
    }

    public HttpURLConnection setupConnection(URL url) throws IOException {
        if (mavenSettingsBuilder == null) {
            return (HttpURLConnection) url.openConnection();
        }

        return setupWithMavenSettings(url);
    }

    public HttpURLConnection setupWithMavenSettings(URL url) throws IOException {
        try {
            Settings settings = mavenSettingsBuilder.buildSettings();

            org.apache.maven.settings.Proxy activeProxy = settings.getActiveProxy();

            if (activeProxy == null) {
                return (HttpURLConnection) url.openConnection();
            }

            Proxy proxy = getProxy(activeProxy);

            return (HttpURLConnection) url.openConnection(proxy);
        } catch (IOException | XmlPullParserException e) {
            logger.warn("Error while parsing maven settings. Settings like proxy will be ignored.");

            return (HttpURLConnection) url.openConnection();
        }
    }

    private static Proxy getProxy(org.apache.maven.settings.Proxy activeProxy) {
        String hostname = activeProxy.getHost();

        int port = activeProxy.getPort();

        return new Proxy(Type.HTTP, new InetSocketAddress(hostname, port));
    }
}
