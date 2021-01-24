package ru.clevertec.checksystem.core.io.writer;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import ru.clevertec.checksystem.core.check.Check;

import java.io.File;
import java.util.Collection;

public class XmlCheckWriter extends CheckWriter {

    private final XmlMapper mapper = new XmlMapper();

    @Override
    public byte[] write(Collection<Check> input) throws Exception {
        return mapper.writeValueAsBytes(input);
    }

    @Override
    public void write(Collection<Check> input, File file) throws Exception {
        mapper.writeValue(file, input);
    }
}
