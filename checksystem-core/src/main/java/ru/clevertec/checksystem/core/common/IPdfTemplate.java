package ru.clevertec.checksystem.core.common;

import java.io.IOException;
import java.io.InputStream;

public interface IPdfTemplate {

    int getTopOffset();

    void setTopOffset(int offset);

    InputStream getInputStream() throws IOException;
}
