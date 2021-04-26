package ru.clevertec.checksystem.core.io.read;

import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
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
public class XmlReceiptReader implements IReceiptReader {

    private final XmlMapper xmlMapper;

    @Override
    public Collection<Receipt> read(byte[] bytes) throws IOException {
        return xmlMapper.readValue(bytes, getCollectionType());
    }

    @Override
    public Collection<Receipt> read(InputStream is) throws IOException {
        return xmlMapper.readValue(is, getCollectionType());
    }

    @Override
    public Collection<Receipt> read(File file) throws IOException {
        return xmlMapper.readValue(file, getCollectionType());
    }

    private CollectionType getCollectionType() {
        return xmlMapper.getTypeFactory().constructCollectionType(ArrayList.class, Receipt.class);
    }
}
