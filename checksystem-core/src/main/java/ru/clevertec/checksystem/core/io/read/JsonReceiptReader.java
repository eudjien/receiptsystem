package ru.clevertec.checksystem.core.io.read;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.core.entity.receipt.Receipt;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

@Component
public class JsonReceiptReader implements IReceiptReader {

    private final ObjectMapper objectMapper;
    private final CollectionType collectionType;

    @Autowired
    public JsonReceiptReader(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        collectionType = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, Receipt.class);
    }

    @Override
    public Collection<Receipt> read(byte[] bytes) throws IOException {
        return objectMapper.readValue(bytes, collectionType);
    }

    @Override
    public Collection<Receipt> read(InputStream is) throws IOException {
        return objectMapper.readValue(is, collectionType);
    }

    @Override
    public Collection<Receipt> read(File file) throws IOException {
        return objectMapper.readValue(file, collectionType);
    }
}
