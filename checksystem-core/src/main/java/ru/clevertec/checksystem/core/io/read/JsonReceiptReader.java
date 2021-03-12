package ru.clevertec.checksystem.core.io.read;

import com.fasterxml.jackson.databind.type.CollectionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.core.common.io.read.IReceiptReader;
import ru.clevertec.checksystem.core.configuration.ApplicationJsonMapper;
import ru.clevertec.checksystem.core.entity.receipt.Receipt;
import ru.clevertec.custom.list.SinglyLinkedList;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

@Component
public class JsonReceiptReader implements IReceiptReader {

    private final ApplicationJsonMapper jsonMapper;
    private final CollectionType collectionType;

    @Autowired
    public JsonReceiptReader(ApplicationJsonMapper jsonMapper) {
        this.jsonMapper = jsonMapper;
        collectionType = jsonMapper.getTypeFactory().constructCollectionType(SinglyLinkedList.class, Receipt.class);
    }

    @Override
    public Collection<Receipt> read(byte[] bytes) throws IOException {
        return jsonMapper.readValue(bytes, collectionType);
    }

    @Override
    public Collection<Receipt> read(InputStream inputStream) throws IOException {
        return jsonMapper.readValue(inputStream, collectionType);
    }

    @Override
    public Collection<Receipt> read(File sourceFile) throws IOException {
        return jsonMapper.readValue(sourceFile, collectionType);
    }
}
