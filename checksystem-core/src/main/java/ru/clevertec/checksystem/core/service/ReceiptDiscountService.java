package ru.clevertec.checksystem.core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.clevertec.checksystem.core.dto.discount.receipt.ReceiptDiscountDto;
import ru.clevertec.checksystem.core.entity.discount.receipt.ReceiptDiscount;
import ru.clevertec.checksystem.core.repository.ReceiptDiscountRepository;
import ru.clevertec.checksystem.core.service.common.IReceiptDiscountService;
import ru.clevertec.checksystem.core.util.mapper.ApplicationModelMapper;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ReceiptDiscountService implements IReceiptDiscountService {

    private final ReceiptDiscountRepository receiptDiscountRepository;
    private final ApplicationModelMapper modelMapper;

    @Override
    public ReceiptDiscountDto getReceiptDiscountById(Long id) {

        var receiptDiscount = receiptDiscountRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("ReceiptDiscount with id '%s' not found", id)));

        return modelMapper.map(receiptDiscount, ReceiptDiscountDto.class);
    }

    @Override
    public List<ReceiptDiscountDto> getReceiptDiscounts() {
        return modelMapper.mapToList(receiptDiscountRepository.findAll());
    }

    @Override
    public List<ReceiptDiscountDto> getReceiptDiscounts(Sort sort) {
        return modelMapper.mapToList(receiptDiscountRepository.findAll(sort));
    }

    @Override
    public Page<ReceiptDiscountDto> getReceiptDiscountsPage(Pageable pageable) {
        return receiptDiscountRepository.findAll(pageable).map(e -> modelMapper.map(e, ReceiptDiscountDto.class));
    }

    @Override
    public List<ReceiptDiscountDto> getReceiptDiscountsById(Collection<Long> ids) {
        return modelMapper.mapToList(receiptDiscountRepository.findAllById(ids));
    }

    @Override
    public List<ReceiptDiscountDto> getReceiptDiscountsById(Sort sort, Collection<Long> ids) {
        return modelMapper.mapToList(receiptDiscountRepository.findAllByIdIn(sort, ids));
    }

    @Override
    public Page<ReceiptDiscountDto> getReceiptDiscountsPageById(Pageable pageable, Collection<Long> ids) {
        return receiptDiscountRepository.findAllByIdIn(pageable, ids).map(e -> modelMapper.map(e, ReceiptDiscountDto.class));
    }

    @Override
    public void deleteReceiptDiscountById(Long id) {

        var discount = receiptDiscountRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("ReceiptDiscount with id '%s' not found", id)));

        receiptDiscountRepository.delete(discount);
    }

    public ReceiptDiscountDto createReceiptDiscount(ReceiptDiscountDto receiptDiscountDto) {

        if (Objects.isNull(receiptDiscountDto))
            throw new NullPointerException("receiptDiscountDto");

        return modelMapper.map(receiptDiscountRepository.save(modelMapper.map(receiptDiscountDto, ReceiptDiscount.class)), ReceiptDiscountDto.class);
    }

    public ReceiptDiscountDto updateReceiptDiscount(ReceiptDiscountDto receiptDiscountDto) {

        if (Objects.isNull(receiptDiscountDto))
            throw new NullPointerException("receiptDiscountDto");

        if (!receiptDiscountRepository.existsById(receiptDiscountDto.getId()))
            throw new EntityNotFoundException(String.format("ReceiptDiscount with id '%s' not found", receiptDiscountDto.getId()));

        var discount = receiptDiscountRepository.findById(receiptDiscountDto.getId()).orElseThrow();

        modelMapper.map(receiptDiscountDto, discount);

        return modelMapper.map(receiptDiscountRepository.save(discount), ReceiptDiscountDto.class);
    }

    @Override
    public Long getReceiptDiscountCount() {
        return receiptDiscountRepository.count();
    }
}
