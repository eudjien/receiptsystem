package ru.clevertec.checksystem.core.template;

import java.io.IOException;
import java.io.InputStream;

public interface ITemplate {
    InputStream getInputStream() throws IOException;
}
