package ru.clevertec.checksystem.core.common.service;

import ru.clevertec.checksystem.core.repository.CheckItemRepository;
import ru.clevertec.checksystem.core.repository.CheckRepository;

public interface ICheckService extends IService {
    CheckRepository getCheckRepository();

    CheckItemRepository getCheckItemRepository();
}
