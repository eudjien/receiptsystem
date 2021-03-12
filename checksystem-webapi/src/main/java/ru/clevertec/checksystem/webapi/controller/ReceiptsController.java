package ru.clevertec.checksystem.webapi.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.checksystem.core.configuration.ApplicationModelMapper;
import ru.clevertec.checksystem.core.dto.receipt.ReceiptDto;
import ru.clevertec.checksystem.core.repository.ReceiptItemRepository;
import ru.clevertec.checksystem.core.repository.ReceiptRepository;
import ru.clevertec.checksystem.core.service.ReceiptService;

import java.util.List;

@RestController
public class ReceiptsController {

    private final ReceiptRepository receiptRepository;
    private final ReceiptItemRepository receiptItemRepository;
    private final ReceiptService receiptService;
    private final ModelMapper modelMapper;

    @Autowired
    public ReceiptsController(
            ReceiptRepository receiptRepository,
            ReceiptItemRepository receiptItemRepository,
            ReceiptService receiptService,
            ApplicationModelMapper modelMapper) {
        this.receiptRepository = receiptRepository;
        this.receiptService = receiptService;
        this.receiptItemRepository = receiptItemRepository;
        this.modelMapper = modelMapper;
    }

    @GetMapping(value = "/receipts/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ReceiptDto> findById(@PathVariable Long id) {
        var receipt = receiptRepository.findById(id);
        return receipt.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(modelMapper.map(receipt.get(), ReceiptDto.class), HttpStatus.OK);
    }

    @GetMapping(value = "/receipts/all", produces = MediaType.APPLICATION_JSON_VALUE)
    ReceiptDto[] all() {
        return modelMapper.map(receiptRepository.findAll(), ReceiptDto[].class);
    }

    @GetMapping(value = "/receipts", produces = MediaType.APPLICATION_JSON_VALUE)
    Page<ReceiptDto> page(Pageable pageable) {
        return receiptRepository.findAll(pageable).map(check -> modelMapper.map(check, ReceiptDto.class));
    }

    @DeleteMapping(value = "/receipts/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> deleteById(@PathVariable Long id) {

        var optionalReceipt = receiptRepository.findById(id);

        if (optionalReceipt.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        receiptRepository.delete(optionalReceipt.get());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/receipts", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> deleteAllById(@RequestParam("id") List<Long> ids) {

        var receipts = receiptRepository.findAllById(ids);

        if (!receipts.iterator().hasNext())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        receiptRepository.deleteAll(receipts);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
