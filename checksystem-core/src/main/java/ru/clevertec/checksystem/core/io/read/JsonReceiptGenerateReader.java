package ru.clevertec.checksystem.core.io.read;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.core.data.generate.ReceiptGenerate;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

@Component
public class JsonReceiptGenerateReader implements IReceiptGenerateReader {

    private final ObjectMapper jsonMapper;
    private final CollectionType collectionType;

    @Autowired
    public JsonReceiptGenerateReader(ObjectMapper objectMapper) {
        this.jsonMapper = objectMapper;
        collectionType = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, ReceiptGenerate.class);
    }

    @Override
    public Collection<ReceiptGenerate> read(byte[] bytes) throws IOException {
        return jsonMapper.readValue(bytes, collectionType);
    }

    @Override
    public Collection<ReceiptGenerate> read(InputStream is) throws IOException {
        return jsonMapper.readValue(is, collectionType);
    }

    @Override
    public Collection<ReceiptGenerate> read(File file) throws IOException {
        return jsonMapper.readValue(file, collectionType);
    }
}
