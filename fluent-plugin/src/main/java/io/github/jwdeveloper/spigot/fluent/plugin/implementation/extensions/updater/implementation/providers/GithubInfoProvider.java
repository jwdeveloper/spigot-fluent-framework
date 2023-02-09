package io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.updater.implementation.providers;

import com.google.gson.JsonParser;
import io.github.jwdeveloper.spigot.fluent.core.common.java.StringUtils;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.updater.api.info.UpdateInfo;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.updater.api.UpdateInfoProvider;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.updater.api.options.GithubUpdaterOptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GithubInfoProvider implements UpdateInfoProvider {
    private final GithubUpdaterOptions options;
    private final String releaseURL;

    public GithubInfoProvider(GithubUpdaterOptions options) {
        this.options = options;
        releaseURL = String.format("https://api.github.com/repos/%s/%s/releases/latest", this.options.getGithubUserName(), this.options.getRepositoryName());
    }


    @Override
    public UpdateInfo getUpdateInfo() throws IOException {
        var response = doRequest();
        return mapToInfoResponse(response);
    }

    private String doRequest() throws IOException {
        var builder = new StringBuilder();
        var url = new URL(releaseURL);
        var conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        try (var reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            var line = StringUtils.EMPTY;
            while (true) {
                line = reader.readLine();
                if (line == null)
                    break;
                builder.append(line);
            }
        } catch (Exception e) {
            throw new RuntimeException("Unable to get content from update request", e);
        }

        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("update request invalid response code");
        }


        return builder.toString();
    }

    private UpdateInfo mapToInfoResponse(String content) {
        var json = new JsonParser().parse(content).getAsJsonObject();
        var result = new UpdateInfo();

        var assetsElement = json.getAsJsonArray("assets");
        for (var element : assetsElement) {
            var elementJson = element.getAsJsonObject();
            var name = elementJson.get("name").getAsString();
            if (!name.contains(".jar")) {
                continue;
            }

            var downloadUrl = elementJson.get("browser_download_url").getAsString();
            result.setFileName(name);
            result.setDownloadUrl(downloadUrl);
        }


        var description = json.get("body").getAsString();
        var version = json.get("tag_name").getAsString();

        result.setVersion(version);
        result.setDescription(description);
        return result;
    }

}
