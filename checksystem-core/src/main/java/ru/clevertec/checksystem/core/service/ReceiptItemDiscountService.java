package ru.clevertec.checksystem.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import ru.clevertec.checksystem.core.dto.discount.receiptitem.ReceiptItemDiscountDto;
import ru.clevertec.checksystem.core.entity.discount.receiptitem.ReceiptItemDiscount;
import ru.clevertec.checksystem.core.repository.ReceiptItemDiscountRepository;
import ru.clevertec.checksystem.core.service.common.IReceiptItemDiscountService;
import ru.clevertec.checksystem.core.util.mapper.ApplicationModelMapper;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Component
public class ReceiptItemDiscountService implements IReceiptItemDiscountService {

    private final ReceiptItemDiscountRepository receiptItemDiscountRepository;
    private final ApplicationModelMapper modelMapper;

    @Autowired
    public ReceiptItemDiscountService(ReceiptItemDiscountRepository receiptItemDiscountRepository, ApplicationModelMapper modelMapper) {
        this.receiptItemDiscountRepository = receiptItemDiscountRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ReceiptItemDiscountDto getReceiptItemDiscountById(Long id) {

        var receiptDiscount = receiptItemDiscountRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("ReceiptItemDiscount with id '%s' not found", id)));

        return modelMapper.map(receiptDiscount, ReceiptItemDiscountDto.class);
    }

    @Override
    public List<ReceiptItemDiscountDto> getReceiptItemDiscounts() {
        return modelMapper.mapToList(receiptItemDiscountRepository.findAll());
    }

    @Override
    public List<ReceiptItemDiscountDto> getReceiptItemDiscounts(Sort sort) {
        return modelMapper.mapToList(receiptItemDiscountRepository.findAll(sort));
    }

    @Override
    public Page<ReceiptItemDiscountDto> getReceiptItemDiscountsPage(Pageable pageable) {
        return receiptItemDiscountRepository.findAll(pageable).map(e -> modelMapper.map(e, ReceiptItemDiscountDto.class));
    }

    @Override
    public List<ReceiptItemDiscountDto> getReceiptItemDiscountsById(Collection<Long> ids) {
        return modelMapper.mapToList(receiptItemDiscountRepository.findAllById(ids));
    }

    @Override
    public List<ReceiptItemDiscountDto> getReceiptItemDiscountsById(Sort sort, Collection<Long> ids) {
        return modelMapper.mapToList(receiptItemDiscountRepository.findAllByIdIn(sort, ids));
    }

    @Override
    public Page<ReceiptItemDiscountDto> getReceiptItemDiscountsPageById(Pageable pageable, Collection<Long> ids) {
        return receiptItemDiscountRepository.findAllByIdIn(pageable, ids).map(e -> modelMapper.map(e, ReceiptItemDiscountDto.class));
    }

    @Override
    public void deleteReceiptItemDiscountById(Long id) {

        var discount = receiptItemDiscountRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("ReceiptItemDiscount with id '%s' not found", id)));

        receiptItemDiscountRepository.delete(discount);
    }

    @Override
    public ReceiptItemDiscountDto createReceiptItemDiscount(ReceiptItemDiscountDto dto) {

        if (Objects.isNull(dto))
            throw new NullPointerException("dto");

        return modelMapper.map(receiptItemDiscountRepository.save(modelMapper.map(dto, ReceiptItemDiscount.class)), ReceiptItemDiscountDto.class);
    }

    @Override
    public ReceiptItemDiscountDto updateReceiptItemDiscount(ReceiptItemDiscountDto dto) {

        if (Objects.isNull(dto))
            throw new NullPointerException("dto");

        if (!receiptItemDiscountRepository.existsById(dto.getId()))
            throw new EntityNotFoundException(String.format("ReceiptItemDiscount with id '%s' not found", dto.getId()));

        var discount = receiptItemDiscountRepository.findById(dto.getId()).orElseThrow();

        modelMapper.map(dto, discount);

        return modelMapper.map(receiptItemDiscountRepository.save(discount), ReceiptItemDiscountDto.class);
    }

    @Override
    public Long getReceiptItemDiscountCount() {
        return receiptItemDiscountRepository.count();
    }
}
