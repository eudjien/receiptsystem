package ru.clevertec.checksystem.core.io.read;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.core.entity.receipt.Receipt;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

@Component
@RequiredArgsConstructor
public class JsonReceiptReader implements IReceiptReader {

    private final ObjectMapper objectMapper;

    @Override
    public Collection<Receipt> read(byte[] bytes) throws IOException {
        return objectMapper.readValue(bytes, getCollectionType());
    }

    @Override
    public Collection<Receipt> read(InputStream is) throws IOException {
        return objectMapper.readValue(is, getCollectionType());
    }

    @Override
    public Collection<Receipt> read(File file) throws IOException {
        return objectMapper.readValue(file, getCollectionType());
    }

    private CollectionType getCollectionType() {
        return objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, Receipt.class);
    }
}
