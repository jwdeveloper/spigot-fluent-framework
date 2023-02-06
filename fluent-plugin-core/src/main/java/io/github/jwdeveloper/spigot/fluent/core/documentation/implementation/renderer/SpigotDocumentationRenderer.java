package io.github.jwdeveloper.spigot.fluent.core.documentation.implementation.renderer;

import io.github.jwdeveloper.spigot.fluent.core.common.TextBuilder;
import io.github.jwdeveloper.spigot.fluent.core.common.java.StringUtils;
import io.github.jwdeveloper.spigot.fluent.core.documentation.api.DocumentationRenderer;
import io.github.jwdeveloper.spigot.fluent.core.documentation.api.models.DocumentationSection;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpigotDocumentationRenderer extends DocumentationRenderer {


    @Override
    public String getName() {
        return "documentation-spigot.txt";
    }


    @Override
    protected void onLinkSection(TextBuilder builder, DocumentationSection section) {
        builder.newLine().text("[URL='").text(section.getContent()).text("']").text(section.getId()).text("[/URL]").newLine();
    }

    @Override
    protected void onTextSection(TextBuilder builder, DocumentationSection section) {
        if(section.hasAttribute("spigot-ignore"))
        {
            return;
        }
        if(section.hasAttribute("bold"))
        {
            builder.text("[B]").space().text(section.getContent()).text("[/B]").newLine();
            return;
        }
        super.onTextSection(builder, section);
    }

    @Override
    protected void onTitleSection(TextBuilder builder, DocumentationSection section) {
        if(section.getId().equals("yml-title"))
        {
            return;
        }

        builder.text("[B]").space().text(section.getContent()).text("[/B]").newLine();
    }

    @Override
    protected void onListSection(TextBuilder builder, DocumentationSection section) {
        builder.text("[LIST]").newLine();
        builder.text("[*]").text(section.getContent()).newLine();
        builder.text("[/LIST]").newLine();
    }

    @Override
    protected void onCodeSection(TextBuilder builder, DocumentationSection section) {
        builder.text("[code=Java]").space()
                .text(section.getContent())
                .space()
                .text("[/code]");
    }

    @Override
    protected void onImageSection(TextBuilder builder, DocumentationSection section) {
        if(section.hasAttribute("link"))
        {
            builder.text(" [URL='" + section.getId() + "']");
            builder.text("[IMG]").text(section.getContent()).text("[/IMG]");
            builder.text("[/URL]");
            return;
        }
        builder.text("[CENTER]").text("[IMG]").space().text(section.getContent()).text("[/IMG]").text("[/CENTER]").newLine();
    }

    @Override
    protected void onYmlSection(TextBuilder builder, DocumentationSection section) {
        builder.text("[code=YAML]").space().text(section.getContent()).text("[/code]").newLine();
    }


    @Override
    protected void onVideoSection(TextBuilder builder, DocumentationSection section) {
        var content = section.getContent();
        if(content.contains("youtube"))
        {
            var id = getYouTubeId(content);
            builder.text("[CENTER]").text("[MEDIA=youtube]").space().text(id).text("[/MEDIA]").text("[/CENTER]").newLine();
        }
    }

    private String getYouTubeId (String youTubeUrl) {
        String pattern = "(?<=youtu.be/|watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(youTubeUrl);
        if(matcher.find()){
            return matcher.group();
        } else {
            return StringUtils.EMPTY;
        }
    }
}
