package ru.clevertec.checksystem.core.io.read;

import com.fasterxml.jackson.databind.type.CollectionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.core.common.io.read.IReceiptGenerateReader;
import ru.clevertec.checksystem.core.configuration.ApplicationJsonMapper;
import ru.clevertec.checksystem.core.dto.ReceiptGenerate;
import ru.clevertec.custom.list.SinglyLinkedList;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

@Component
public class JsonReceiptGenerateReader implements IReceiptGenerateReader {

    private final ApplicationJsonMapper jsonMapper;
    private final CollectionType collectionType;

    @Autowired
    public JsonReceiptGenerateReader(ApplicationJsonMapper jsonMapper) {
        this.jsonMapper = jsonMapper;
        collectionType = jsonMapper.getTypeFactory().constructCollectionType(SinglyLinkedList.class, ReceiptGenerate.class);
    }

    @Override
    public Collection<ReceiptGenerate> read(byte[] bytes) throws IOException {
        return jsonMapper.readValue(bytes, collectionType);
    }

    @Override
    public Collection<ReceiptGenerate> read(InputStream inputStream) throws IOException {
        return jsonMapper.readValue(inputStream, collectionType);
    }

    @Override
    public Collection<ReceiptGenerate> read(File sourceFile) throws IOException {
        return jsonMapper.readValue(sourceFile, collectionType);
    }
}
