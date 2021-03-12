package ru.clevertec.checksystem.core.io.read;

import com.fasterxml.jackson.databind.type.CollectionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.core.common.io.read.IReceiptReader;
import ru.clevertec.checksystem.core.configuration.ApplicationXmlMapper;
import ru.clevertec.checksystem.core.entity.receipt.Receipt;
import ru.clevertec.custom.list.SinglyLinkedList;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

@Component
public class XmlReceiptReader implements IReceiptReader {

    private final ApplicationXmlMapper mapper;
    private final CollectionType collectionType;

    @Autowired
    public XmlReceiptReader(ApplicationXmlMapper xmlMapper) {
        mapper = xmlMapper;
        collectionType = xmlMapper.getTypeFactory().constructCollectionType(SinglyLinkedList.class, Receipt.class);
    }

    @Override
    public Collection<Receipt> read(byte[] bytes) throws IOException {
        return mapper.readValue(bytes, collectionType);
    }

    @Override
    public Collection<Receipt> read(InputStream inputStream) throws IOException {
        return mapper.readValue(inputStream, collectionType);
    }

    @Override
    public Collection<Receipt> read(File sourceFile) throws IOException {
        return mapper.readValue(sourceFile, collectionType);
    }
}
