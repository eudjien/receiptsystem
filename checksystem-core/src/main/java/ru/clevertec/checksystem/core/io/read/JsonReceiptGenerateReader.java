package ru.clevertec.checksystem.core.io.read;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.core.data.generate.ReceiptGenerate;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

@Component
@RequiredArgsConstructor
public class JsonReceiptGenerateReader implements IReceiptGenerateReader {

    private final ObjectMapper objectMapper;

    @Override
    public Collection<ReceiptGenerate> read(byte[] bytes) throws IOException {
        return objectMapper.readValue(bytes, getCollectionType());
    }

    @Override
    public Collection<ReceiptGenerate> read(InputStream is) throws IOException {
        return objectMapper.readValue(is, getCollectionType());
    }

    @Override
    public Collection<ReceiptGenerate> read(File file) throws IOException {
        return objectMapper.readValue(file, getCollectionType());
    }

    private CollectionType getCollectionType() {
        return objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, ReceiptGenerate.class);
    }
}
