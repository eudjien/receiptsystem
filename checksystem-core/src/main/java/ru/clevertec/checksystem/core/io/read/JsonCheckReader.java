package ru.clevertec.checksystem.core.io.read;

import com.fasterxml.jackson.databind.type.CollectionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.core.common.io.read.ICheckReader;
import ru.clevertec.checksystem.core.configuration.ApplicationJsonMapper;
import ru.clevertec.checksystem.core.entity.check.Check;
import ru.clevertec.custom.list.SinglyLinkedList;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

@Component
public class JsonCheckReader implements ICheckReader {

    private final ApplicationJsonMapper jsonMapper;
    private final CollectionType collectionType;

    @Autowired
    public JsonCheckReader(ApplicationJsonMapper jsonMapper) {
        this.jsonMapper = jsonMapper;
        collectionType = jsonMapper.getTypeFactory().constructCollectionType(SinglyLinkedList.class, Check.class);
    }

    @Override
    public Collection<Check> read(byte[] bytes) throws IOException {
        return jsonMapper.readValue(bytes, collectionType);
    }

    @Override
    public Collection<Check> read(InputStream inputStream) throws IOException {
        return jsonMapper.readValue(inputStream, collectionType);
    }

    @Override
    public Collection<Check> read(File sourceFile) throws IOException {
        return jsonMapper.readValue(sourceFile, collectionType);
    }
}
