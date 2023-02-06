package io.github.jwdeveloper.spigot.fluent.core.documentation.implementation.renderer;

import io.github.jwdeveloper.spigot.fluent.core.common.TextBuilder;
import io.github.jwdeveloper.spigot.fluent.core.common.java.StringUtils;
import io.github.jwdeveloper.spigot.fluent.core.documentation.api.DocumentationRenderer;
import io.github.jwdeveloper.spigot.fluent.core.documentation.api.models.DocumentationSection;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GithubDocumentationRenderer extends DocumentationRenderer {

    @Override
    public String getName() {
        return "documentation-github.md";
    }


    @Override
    protected void onTitleSection(TextBuilder builder, DocumentationSection section) {
        if (section.getId().equals("yml-title")) {
            return;
        }
        builder.text("##").space().text(section.getContent()).newLine();
    }

    @Override
    protected void onTextSection(TextBuilder builder, DocumentationSection section) {
        if (section.hasAttribute("github-ignore")) {
            return;
        }
        if (section.hasAttribute("bold")) {
            builder.newLine().text("###").space().text(section.getContent()).newLine();
            return;
        }
        super.onTextSection(builder, section);
    }

    @Override
    protected void onLinkSection(TextBuilder builder, DocumentationSection section) {

        builder.newLine().text("[" + section.getId() + "]").text("(").text(section.getContent()).text(")").newLine().newLine();
    }

    @Override
    protected void onCodeSection(TextBuilder builder, DocumentationSection section) {
        builder.newLine()
                .text(" ```java")
                .text(section.getContent())
                .text("```")
                .newLine();
    }

    @Override
    protected void onImageSection(TextBuilder builder, DocumentationSection section) {

        if (section.hasAttribute("link")) {

            builder.text("<a href=\"" + section.getId() + "\">")
                    .text("<img src=\"" + section.getContent() + "\"  />")
                    .text("</a>");
            return;
        }

        builder.newLine().text("![alt text]").text("(").text(section.getContent()).text(")").newLine().newLine();
    }

    @Override
    protected void onListSection(TextBuilder builder, DocumentationSection section) {
        builder.text(" - ").space().text(section.getContent()).newLine();
    }

    @Override
    protected void onYmlSection(TextBuilder builder, DocumentationSection section) {
        builder.text("``` yaml").newLine();
        builder.text(section.getContent()).newLine();
        builder.text("```").newLine();
    }

    @Override
    protected void onVideoSection(TextBuilder builder, DocumentationSection section) {
        var content = section.getContent();
        if (content.contains("youtube")) {
            var id = getYouTubeId(content);
            var imageUrl = "https://img.youtube.com/vi/" + id + "/0.jpg";
            builder.newLine().text("[![IMAGE ALT TEXT HERE]").text("(").text(imageUrl).text(")]")
                    .text("(").text(section.getContent()).text(")").newLine().newLine();
        }
    }

    private String getYouTubeId(String youTubeUrl) {
        String pattern = "(?<=youtu.be/|watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(youTubeUrl);
        if (matcher.find()) {
            return matcher.group();
        } else {
            return StringUtils.EMPTY;
        }
    }
}
