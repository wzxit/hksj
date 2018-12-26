package com.hksj.limit.common.property;

import com.hksj.limit.common.util.IOUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class LimitContent {
    private String content;

    public LimitContent(String path, String encoding) throws IOException {
        InputStream inputStream = null;
        try {
            inputStream = IOUtil.getInputStream(path);
            this.content = IOUtils.toString(inputStream, encoding);
        } finally {
            if (inputStream != null) {
                IOUtils.closeQuietly(inputStream);
            }
        }
    }

    public LimitContent(File file, String encoding) throws IOException {
        this.content = FileUtils.readFileToString(file, encoding);
    }

    public LimitContent(StringBuilder stringBuilder) throws IOException {
        this.content = stringBuilder.toString();
    }

    public String getContent() {
        return content;
    }
}
