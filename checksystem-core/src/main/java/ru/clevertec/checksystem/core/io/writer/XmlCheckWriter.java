package ru.clevertec.checksystem.core.io.writer;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import ru.clevertec.checksystem.core.check.Check;

import java.util.List;

public class XmlCheckWriter extends CheckWriter {

    private final XmlMapper mapper = new XmlMapper();

    @Override
    public byte[] write(Check check) throws Exception {
        return mapper.writeValueAsBytes(check);
    }

    @Override
    public byte[] write(List<Check> input) throws Exception {
        return mapper.writeValueAsBytes(input);
    }
}
