package io.github.jwdeveloper.spigot.fluent.tools;/*
 * MIT License
 *
 * Copyright (c)  2023. jwdeveloper
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import java.util.Map;

public class TemplateUtility
{
    public static String generateTemplate(String template, Map<String, Object> values) {
        for(var entry : values.entrySet())
        {
            template = doReplacement(template,entry.getKey(), entry.getValue().toString());
        }
        return template;
    }

    public static String generateTemplate2(String template, Map<String, Object> values) {
        for(var entry : values.entrySet())
        {
            template = doReplacement2(template,entry.getKey(), entry.getValue().toString());
        }
        return template;
    }

    private static String doReplacement(String template, String keyword, String value)
    {
        var key = "\\{"+keyword+"}";
        return template.replaceAll(key, value);
    }

    private static String doReplacement2(String template, String keyword, String value)
    {
        var key = "(\\$)("+keyword+")(\\$)";
        return template.replaceAll(key, value);
    }
}
