package ru.clevertec.checksystem.core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.clevertec.checksystem.core.dto.ProductDto;
import ru.clevertec.checksystem.core.dto.SummaryDto;
import ru.clevertec.checksystem.core.dto.discount.receipt.ReceiptDiscountDto;
import ru.clevertec.checksystem.core.dto.discount.receiptitem.ReceiptItemDiscountDto;
import ru.clevertec.checksystem.core.dto.receipt.ReceiptDto;
import ru.clevertec.checksystem.core.dto.receipt.ReceiptItemDto;
import ru.clevertec.checksystem.core.entity.discount.AbstractDiscount;
import ru.clevertec.checksystem.core.entity.discount.receipt.ConstantReceiptDiscount;
import ru.clevertec.checksystem.core.entity.discount.receipt.PercentageReceiptDiscount;
import ru.clevertec.checksystem.core.entity.discount.receipt.ReceiptDiscount;
import ru.clevertec.checksystem.core.entity.discount.receiptitem.ConstantReceiptItemDiscount;
import ru.clevertec.checksystem.core.entity.discount.receiptitem.PercentageReceiptItemDiscount;
import ru.clevertec.checksystem.core.entity.discount.receiptitem.ReceiptItemDiscount;
import ru.clevertec.checksystem.core.entity.discount.receiptitem.ThresholdPercentageReceiptItemDiscount;
import ru.clevertec.checksystem.core.entity.receipt.Receipt;
import ru.clevertec.checksystem.core.entity.receipt.ReceiptItem;
import ru.clevertec.checksystem.core.repository.*;
import ru.clevertec.checksystem.core.service.common.IReceiptService;
import ru.clevertec.checksystem.core.util.mapper.ApplicationModelMapper;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

@Service
@RequiredArgsConstructor
public class ReceiptService implements IReceiptService {

    private final ReceiptRepository receiptRepository;
    private final ReceiptItemRepository receiptItemRepository;
    private final ReceiptDiscountRepository receiptDiscountRepository;
    private final ReceiptItemDiscountRepository receiptItemDiscountRepository;
    private final ProductRepository productRepository;
    private final ApplicationModelMapper modelMapper;

    @Override
    public ReceiptDto getReceiptById(Long id) {

        var receipt = receiptRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Receipt with id '%s' not found", id)));

        return modelMapper.map(receipt, ReceiptDto.class);
    }

    @Override
    public ReceiptItemDto getReceiptItemById(Long id) {

        var receiptItem = receiptItemRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("ReceiptItem with id '%s' not found", id)));

        return modelMapper.map(receiptItem, ReceiptItemDto.class);
    }

    @Override
    public ReceiptItemDto getReceiptItemByIdAndReceiptId(Long id, Long receiptId) {

        var receiptItem = receiptItemRepository.findByIdAndReceiptId(id, receiptId).orElseThrow(() ->
                new EntityNotFoundException(String.format("ReceiptItem with id '%s' and receiptId '%s not found", id, receiptId)));

        return modelMapper.map(receiptItem, ReceiptItemDto.class);
    }

    @Override
    public ProductDto getProductByReceiptIdAndItemId(Long receiptId, Long receiptItemId) {

        var product = productRepository.findByReceiptIdAndReceiptItemId(receiptId, receiptItemId).orElseThrow(() ->
                new EntityNotFoundException(String.format("Product with receiptId '%s' and receiptItemId '%s' not found", receiptId, receiptItemId)));

        return modelMapper.map(product, ProductDto.class);
    }

    @Override
    public ProductDto getProductByReceiptItemId(Long receiptItemId) {

        var product = productRepository.findByReceiptItemId(receiptItemId).orElseThrow(() ->
                new EntityNotFoundException(String.format("Product with receiptItemId '%s' not found", receiptItemId)));

        return modelMapper.map(product, ProductDto.class);
    }

    @Override
    public ReceiptDiscountDto getReceiptDiscountByIdAndReceiptId(Long discountId, Long receiptDiscountId) {

        var receiptDiscount = receiptDiscountRepository.findByIdAndReceiptId(discountId, receiptDiscountId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("ReceiptDiscount with receiptId '%s' and discountId '%s' not found", receiptDiscountId, discountId)));

        return modelMapper.map(receiptDiscount, ReceiptDiscountDto.class);
    }

    @Override
    public ReceiptItemDiscountDto getReceiptItemDiscountByIdAndReceiptItemId(Long receiptItemId, Long receiptItemDiscountId) {
        var receiptDiscount = receiptItemDiscountRepository.findByIdAndReceiptItemId(receiptItemDiscountId, receiptItemId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("ReceiptItemDiscount with receiptId '%s' and receiptItemDiscountId '%s' not found", receiptItemDiscountId, receiptItemId)));

        return modelMapper.map(receiptDiscount, ReceiptItemDiscountDto.class);
    }

    @Override
    public List<ReceiptDto> findReceiptsById(Sort sort, Collection<Long> ids) {
        return modelMapper.mapToList(receiptRepository.findAllByIdIn(sort, ids));
    }

    @Override
    public List<ReceiptDto> getReceipts() {
        return modelMapper.mapToList(receiptRepository.findAll());
    }

    @Override
    public List<ReceiptDto> getReceipts(Sort sort) {
        return modelMapper.mapToList(receiptRepository.findAll(sort));
    }

    @Override
    public List<ReceiptDto> getReceiptsById(Collection<Long> ids) {
        return modelMapper.mapToList(receiptRepository.findAllById(ids));
    }

    @Override
    public Page<ReceiptDto> getReceiptsPage(Pageable pageable) {
        return receiptRepository.findAll(pageable).map(e -> modelMapper.map(e, ReceiptDto.class));
    }

    @Override
    public Page<ReceiptDto> getReceiptsPageById(Pageable pageable, Collection<Long> ids) {
        return receiptRepository.findAllByIdIn(pageable, ids).map(e -> modelMapper.map(e, ReceiptDto.class));
    }

    @Override
    public List<ReceiptItemDto> getReceiptItems(Sort sort, Long receiptId) {
        return modelMapper.mapToList(receiptItemRepository.findAllByReceiptId(sort, receiptId));
    }

    @Override
    public List<ReceiptItemDto> getReceiptItems(Long receiptId) {
        return modelMapper.mapToList(receiptItemRepository.findAllByReceiptId(receiptId));
    }

    @Override
    public Page<ReceiptItemDto> getReceiptItemsPage(Pageable pageable, Long receiptId) {
        return receiptItemRepository.findAllByReceiptId(pageable, receiptId).map(e -> modelMapper.map(e, ReceiptItemDto.class));
    }

    @Override
    public List<ReceiptDiscountDto> getDiscounts(Sort sort, Long receiptId) {
        return modelMapper.mapToList(receiptDiscountRepository.findAllByReceiptId(receiptId, sort));
    }

    @Override
    public List<ReceiptDiscountDto> getDiscounts(Long receiptId) {
        return modelMapper.mapToList(receiptDiscountRepository.findAllByReceiptId(receiptId));
    }

    @Override
    public Page<ReceiptDiscountDto> getReceiptDiscountsPage(Pageable pageable, Long receiptId) {
        return receiptDiscountRepository.findAllByReceiptId(receiptId, pageable).map(e -> modelMapper.map(e, ReceiptDiscountDto.class));
    }

    @Override
    public Page<ReceiptItemDiscountDto> getReceiptItemDiscountsPage(Pageable pageable, Long receiptItemId) {
        return receiptItemDiscountRepository.findAllByReceiptItemId(receiptItemId, pageable).map(e -> modelMapper.map(e, ReceiptItemDiscountDto.class));
    }

    @Override
    public List<ReceiptItemDto> getReceiptItems() {
        return modelMapper.mapToList(receiptItemRepository.findAll());
    }

    @Override
    public List<ReceiptItemDto> getReceiptItems(Sort sort) {
        return modelMapper.mapToList(receiptItemRepository.findAll(sort));
    }

    @Override
    public Page<ReceiptItemDto> getReceiptItemsPage(Pageable pageable) {
        return receiptItemRepository.findAll(pageable).map(e -> modelMapper.map(e, ReceiptItemDto.class));
    }

    @Override
    public List<ReceiptItemDto> getReceiptItemsById(Collection<Long> ids) {
        return modelMapper.mapToList(receiptItemRepository.findAllById(ids));
    }

    @Override
    public List<ReceiptItemDto> getReceiptItemsById(Sort sort, Collection<Long> ids) {
        return modelMapper.mapToList(receiptItemRepository.findAllByIdIn(sort, ids));
    }

    @Override
    public Page<ReceiptItemDto> getReceiptItemsPageById(Pageable pageable, Collection<Long> ids) {
        return receiptItemRepository.findAllByIdIn(pageable, ids).map(e -> modelMapper.map(e, ReceiptItemDto.class));
    }

    @Override
    public List<ReceiptItemDto> getReceiptItemsByReceiptId(Long receiptId) {
        return modelMapper.mapToList(receiptItemRepository.findAllByReceiptId(receiptId));
    }

    @Override
    public List<ReceiptItemDto> getReceiptItemsByReceiptId(Sort sort, Long receiptId) {
        return modelMapper.mapToList(receiptItemRepository.findAllByReceiptId(sort, receiptId));
    }

    @Override
    public Page<ReceiptItemDto> getReceiptItemsPageByReceiptId(Pageable pageable, Long receiptId) {
        return receiptItemRepository.findAllByReceiptId(pageable, receiptId).map(e -> modelMapper.map(e, ReceiptItemDto.class));
    }

    @Override
    public ReceiptItemDto removeItemFromReceipt(Long receiptItemId, Long receiptId) {

        var receipt = receiptRepository.findById(receiptId).orElseThrow(() ->
                new EntityNotFoundException(String.format("Receipt with id '%s' not found", receiptId)));

        var receiptItem = receiptItemRepository.findByIdAndReceiptId(receiptItemId, receiptId).orElseThrow(() ->
                new EntityNotFoundException(String.format("ReceiptItem with id '%s' in Receipt with id '%s' not found", receiptItemId, receiptId)));

        receipt.removeReceiptItem(receiptItem);

        return modelMapper.map(receiptRepository.save(receipt), ReceiptItemDto.class);
    }

    @Override
    public ReceiptItemDto addItemFromReceipt(ReceiptItemDto receiptItemDto) {

        var receipt = receiptRepository.findById(receiptItemDto.getReceiptId()).orElseThrow(() ->
                new EntityNotFoundException(String.format("Receipt with id '%s' not found", receiptItemDto.getReceiptId())));

        if (!productRepository.existsById(receiptItemDto.getProductId()))
            throw new EntityNotFoundException(String.format("Product with id '%s' not found", receiptItemDto.getProductId()));

        var receiptItem = modelMapper.map(receiptItemDto, ReceiptItem.class);

        receipt.addReceiptItem(receiptItem);

        return modelMapper.map(receiptRepository.save(receipt), ReceiptItemDto.class);
    }

    @Override
    public Long getReceiptCount() {
        return receiptRepository.count();
    }

    @Override
    public Long getReceiptItemCount() {
        return receiptItemRepository.count();
    }

    @Override
    public void deleteReceiptById(Long id) {

        var receipt = receiptRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Receipt with id '%s' not found", id)));

        receiptRepository.delete(receipt);
    }

    @Override
    public void deleteReceiptItemById(Long id) {

        var receiptItem = receiptItemRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("ReceiptItem with id '%s' not found", id)));

        receiptItem.removeReceipt();

        receiptItemRepository.delete(receiptItem);
    }

    @Override
    public ReceiptDto createReceipt(ReceiptDto receiptDto) {

        if (Objects.isNull(receiptDto))
            throw new NullPointerException("receiptDto");

        var receipt = new Receipt();

        mapReceiptFromDto(receiptDto, receipt);

        return modelMapper.map(receiptRepository.save(receipt), ReceiptDto.class);
    }

    @Override
    public ReceiptDto updateReceipt(ReceiptDto receiptDto) {

        if (Objects.isNull(receiptDto))
            throw new NullPointerException("receiptDto");

        var receipt = receiptRepository.findById(receiptDto.getId()).orElseThrow(() ->
                new EntityNotFoundException(String.format("Receipt with id '%s' not found", receiptDto.getId())));

        mapReceiptFromDto(receiptDto, receipt);

        return modelMapper.map(receiptRepository.save(receipt), ReceiptDto.class);
    }

    @Override
    public ReceiptItemDto createReceiptItem(ReceiptItemDto receiptItemDto) {

        if (Objects.isNull(receiptItemDto))
            throw new NullPointerException("receiptItemDto");

        var receiptItem = new ReceiptItem();
        mapReceiptItemFromDto(receiptItemDto, receiptItem, true);

        return modelMapper.map(receiptItemRepository.save(receiptItem), ReceiptItemDto.class);
    }

    @Override
    public ReceiptItemDto updateReceiptItem(ReceiptItemDto receiptItemDto) {

        if (Objects.isNull(receiptItemDto))
            throw new NullPointerException("receiptItemDto");

        if (!receiptRepository.existsById(receiptItemDto.getReceiptId()))
            throw new EntityNotFoundException(String.format("Receipt with id '%s' not found", receiptItemDto.getReceiptId()));

        var receiptItem = receiptItemRepository.findById(receiptItemDto.getId()).orElseThrow(() ->
                new EntityNotFoundException(String.format("ReceiptItem with id '%s' not found", receiptItemDto.getId())));

        mapReceiptItemFromDto(receiptItemDto, receiptItem, true);

        return modelMapper.map(receiptItemRepository.save(receiptItem), ReceiptItemDto.class);
    }

    @Override
    public void addDiscountToReceipt(Long receiptId, Long receiptDiscountId) {

        var receipt = receiptRepository.findById(receiptId).orElseThrow(() ->
                new EntityNotFoundException(String.format("Receipt with id '%s' not found", receiptDiscountId)));

        var receiptDiscount = receiptDiscountRepository.findById(receiptDiscountId).orElseThrow(() ->
                new EntityNotFoundException(String.format("ReceiptDiscount with id '%s' not found", receiptId)));

        if (receiptDiscountRepository.existsByIdAndReceiptId(receiptDiscountId, receiptId))
            throw new EntityExistsException(
                    String.format("ReceiptDiscount with id '%s' is already assigned to Receipt with id '%s'", receiptDiscountId, receiptId));

        receipt.addDiscount(receiptDiscount);

        receiptRepository.save(receipt);
    }

    @Override
    public void removeDiscountFromReceipt(Long receiptId, Long receiptDiscountId) {

        var receipt = receiptRepository.findById(receiptId).orElseThrow(() ->
                new EntityNotFoundException(String.format("Receipt with id '%s' not found", receiptDiscountId)));

        var receiptDiscount = receiptDiscountRepository.findById(receiptDiscountId).orElseThrow(() ->
                new EntityNotFoundException(String.format("ReceiptDiscount with id '%s' not found", receiptId)));

        if (!receiptDiscountRepository.existsByIdAndReceiptId(receiptDiscountId, receiptId))
            throw new EntityNotFoundException(
                    String.format("No ReceiptDiscount with id '%s' assigned to Receipt with id '%s'", receiptDiscountId, receiptId));

        receipt.removeDiscount(receiptDiscount);

        receiptRepository.save(receipt);
    }

    @Override
    public SummaryDto getReceiptSummaryById(Long receiptId) {

        var receipt = receiptRepository.findById(receiptId).orElseThrow(() ->
                new EntityNotFoundException(String.format("Receipt with id '%s' not found", receiptId)));

        return getReceiptSummary(receipt);
    }

    @Override
    public SummaryDto getReceiptItemSummaryById(Long receiptItemId) {

        var receiptItem = receiptItemRepository.findById(receiptItemId).orElseThrow(() ->
                new EntityNotFoundException(String.format("ReceiptItem with id '%s' not found", receiptItemId)));

        return getReceiptItemSummary(receiptItem);
    }

    @Override
    public SummaryDto getReceiptSummary(Receipt receipt) {

        var subTotalAmount = subTotalAmount(receipt);
        var discountAmount = calcAllReceiptDiscounts(receipt);
        var totalAmount = subTotalAmount.subtract(discountAmount);

        return new SummaryDto(subTotalAmount, discountAmount, totalAmount);
    }

    @Override
    public SummaryDto getReceiptItemSummary(ReceiptItem receiptItem) {

        var subTotalAmount = receiptItemSubTotalAmount(receiptItem);
        var discountAmount = calcAllReceiptItemDiscounts(receiptItem);
        var totalAmount = subTotalAmount.subtract(discountAmount);

        return new SummaryDto(subTotalAmount, discountAmount, totalAmount);
    }

    private void mapReceiptFromDto(ReceiptDto receiptDto, Receipt receipt) {

        receipt.setId(receiptDto.getId());
        receipt.setName(receiptDto.getName());
        receipt.setDescription(receiptDto.getDescription());
        receipt.setPhoneNumber(receiptDto.getPhoneNumber());
        receipt.setAddress(receiptDto.getAddress());
        receipt.setCashier(receiptDto.getCashier());
        receipt.setDate(receiptDto.getDate());

        receipt.getReceiptItems().clear();
        receipt.clearDiscounts();

        if (receiptDto.getReceiptItems() != null) {
            for (var receiptItemDto : receiptDto.getReceiptItems()) {
                var receiptItem = new ReceiptItem();
                mapReceiptItemFromDto(receiptItemDto, receiptItem, false);
                receipt.addReceiptItem(receiptItem);
            }
        }

        if (receiptDto.getDiscounts() != null) {
            for (var receiptDiscountDto : receiptDto.getDiscounts()) {
                receipt.addDiscount(receiptDiscountRepository.findById(receiptDiscountDto.getId()).orElseThrow(() ->
                        new EntityNotFoundException(
                                String.format("ReceiptDiscount with id '%s' not found", receiptDiscountDto.getId()))));
            }
        }
    }

    private void mapReceiptItemFromDto(ReceiptItemDto receiptItemDto, ReceiptItem receiptItem, boolean findReceipt) {

        receiptItem.setId(receiptItemDto.getId());
        receiptItem.setQuantity(receiptItemDto.getQuantity());
        receiptItem.setProductId(receiptItemDto.getProductId());
        receiptItem.setReceiptId(receiptItemDto.getReceiptId());

        receiptItem.clearDiscounts();

        var product = productRepository.findById(receiptItemDto.getProductId()).orElseThrow(() ->
                new EntityNotFoundException(String.format("Product with id '%s' not found", receiptItemDto.getProductId())));
        receiptItem.setProduct(product);

        if (findReceipt) {
            var receipt = receiptRepository.findById(receiptItemDto.getReceiptId()).orElseThrow(() ->
                    new EntityNotFoundException(String.format("Receipt with id '%s' not found", receiptItemDto.getReceiptId())));
            receiptItem.setReceipt(receipt);
        }

        for (var receiptItemDiscountDto : receiptItemDto.getDiscounts()) {
            receiptItem.addDiscount(receiptItemDiscountRepository.findById(receiptItemDiscountDto.getId()).orElseThrow(() ->
                    new EntityNotFoundException(String.format("ReceiptItemDiscount with id '%s' not found", receiptItemDiscountDto.getId()))));
        }
    }

    private BigDecimal receiptItemSubTotalAmount(ReceiptItem receiptItem) {
        return receiptItem.getProduct().getPrice().multiply(BigDecimal.valueOf(receiptItem.getQuantity()));
    }

    private BigDecimal calcAllReceiptDiscounts(Receipt receipt) {
        return receipt.getDiscounts().stream()
                .map(receiptDiscount -> calcReceiptDiscount(receipt, receiptDiscount))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    private BigDecimal calcAllReceiptItemsDiscounts(Receipt receipt) {
        return receipt.getReceiptItems().stream()
                .map(this::calcAllReceiptItemDiscounts)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    private BigDecimal calcAllReceiptItemDiscounts(ReceiptItem receiptItem) {
        return receiptItem.getDiscounts().stream()
                .map(receiptItemDiscount -> calcReceiptItemDiscount(receiptItem, receiptItemDiscount))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    private BigDecimal subTotalAmount(Receipt receipt) {
        return receipt.getReceiptItems().stream()
                .map(this::receiptItemSubTotalAmount)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    private BigDecimal calcReceiptDiscount(Receipt receipt, ReceiptDiscount receiptDiscount) {

        var subTotalAmount = subTotalAmount(receipt);
        var discountAmount = calcAllReceiptItemsDiscounts(receipt);
        var totalAmount = subTotalAmount.subtract(discountAmount);

        var unwrappedDiscounts = unwrapDiscount(receiptDiscount);

        while (!unwrappedDiscounts.empty()) {
            var currentDiscountAmount = BigDecimal.ZERO;
            var poppedDiscount = unwrappedDiscounts.pop();
            if (poppedDiscount instanceof PercentageReceiptDiscount) {
                currentDiscountAmount = totalAmount.multiply(BigDecimal.valueOf(((PercentageReceiptDiscount) poppedDiscount).getPercent()))
                        .divide(BigDecimal.valueOf(100), RoundingMode.HALF_EVEN);
            } else if (poppedDiscount instanceof ConstantReceiptDiscount) {
                currentDiscountAmount = ((ConstantReceiptDiscount) poppedDiscount).getConstant();
            } else {
                throw new IllegalArgumentException("Unsupported type of discount");
            }
            totalAmount = totalAmount.subtract(currentDiscountAmount);
            discountAmount = discountAmount.add(currentDiscountAmount);
        }

        return discountAmount;
    }

    private BigDecimal calcReceiptItemDiscount(ReceiptItem receiptItem, ReceiptItemDiscount receiptItemDiscount) {

        var unwrappedDiscounts = unwrapDiscount(receiptItemDiscount);

        var totalAmount = receiptItemSubTotalAmount(receiptItem);
        var discountAmount = BigDecimal.ZERO;

        while (!unwrappedDiscounts.empty()) {
            var currentDiscountAmount = BigDecimal.ZERO;
            var poppedDiscount = unwrappedDiscounts.pop();
            if (poppedDiscount instanceof PercentageReceiptItemDiscount) {
                currentDiscountAmount = totalAmount.multiply(BigDecimal.valueOf(((PercentageReceiptItemDiscount) poppedDiscount).getPercent()))
                        .divide(BigDecimal.valueOf(100), RoundingMode.HALF_EVEN);
            } else if (poppedDiscount instanceof ConstantReceiptItemDiscount) {
                currentDiscountAmount = ((ConstantReceiptItemDiscount) poppedDiscount).getConstant();
            } else if (poppedDiscount instanceof ThresholdPercentageReceiptItemDiscount) {
                var d = (ThresholdPercentageReceiptItemDiscount) poppedDiscount;
                if (receiptItem.getQuantity() > d.getThreshold())
                    currentDiscountAmount = totalAmount.multiply(BigDecimal.valueOf(d.getPercent())).divide(BigDecimal.valueOf(100), RoundingMode.HALF_EVEN);
            } else {
                throw new IllegalArgumentException("Unsupported type of discount");
            }
            totalAmount = totalAmount.subtract(currentDiscountAmount);
            discountAmount = discountAmount.add(currentDiscountAmount);
        }

        return discountAmount;
    }

    private static <T extends AbstractDiscount<T>> Stack<T> unwrapDiscount(T discount) {

        var discountStack = new Stack<T>();

        var currentDiscount = discount;

        while (currentDiscount != null) {
            discountStack.add(currentDiscount);
            currentDiscount = currentDiscount.getDependentDiscount();
        }

        return discountStack;
    }
}
