package ru.clevertec.checksystem.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.clevertec.checksystem.core.common.service.ICheckService;
import ru.clevertec.checksystem.core.event.EventEmitter;
import ru.clevertec.checksystem.core.repository.CheckItemRepository;
import ru.clevertec.checksystem.core.repository.CheckRepository;

@Service
public class CheckService extends EventEmitter<Object> implements ICheckService {

    private final CheckRepository checkRepository;
    private final CheckItemRepository checkItemRepository;

    @Autowired
    public CheckService(CheckRepository checkRepository, CheckItemRepository checkItemRepository) {
        this.checkRepository = checkRepository;
        this.checkItemRepository = checkItemRepository;
    }

    public CheckRepository getCheckRepository() {
        return checkRepository;
    }

    public CheckItemRepository getCheckItemRepository() {
        return checkItemRepository;
    }
}
