package ru.clevertec.checksystem.core.io.read;

import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.core.entity.receipt.Receipt;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

@Component
public class XmlReceiptReader implements IReceiptReader {

    private final XmlMapper mapper;
    private final CollectionType collectionType;

    @Autowired
    public XmlReceiptReader(XmlMapper xmlMapper) {
        mapper = xmlMapper;
        collectionType = xmlMapper.getTypeFactory().constructCollectionType(ArrayList.class, Receipt.class);
    }

    @Override
    public Collection<Receipt> read(byte[] bytes) throws IOException {
        return mapper.readValue(bytes, collectionType);
    }

    @Override
    public Collection<Receipt> read(InputStream is) throws IOException {
        return mapper.readValue(is, collectionType);
    }

    @Override
    public Collection<Receipt> read(File file) throws IOException {
        return mapper.readValue(file, collectionType);
    }
}
