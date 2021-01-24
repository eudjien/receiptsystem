package ru.clevertec.checksystem.core.io.reader;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import ru.clevertec.checksystem.core.check.Check;
import ru.clevertec.normalino.list.NormalinoList;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;

public class XmlCheckReader extends CheckReader {

    private final XmlMapper mapper = new XmlMapper();

    @Override
    public Collection<Check> read(byte[] bytes) throws Exception {
        return new NormalinoList<>(Arrays.asList(mapper.readValue(bytes, Check[].class)));
    }

    @Override
    public Collection<Check> read(File file) throws Exception {
        return new NormalinoList<>(Arrays.asList(mapper.readValue(file, Check[].class)));
    }
}
