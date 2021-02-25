package ru.clevertec.checksystem.core.common.template;

import java.io.IOException;
import java.io.InputStream;

public interface ITemplate {
    InputStream getInputStream() throws IOException;
}
