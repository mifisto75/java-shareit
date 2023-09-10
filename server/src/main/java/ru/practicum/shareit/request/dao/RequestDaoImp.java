package ru.practicum.shareit.request.dao;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exeptions.NotFoundException;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.repository.RequestRepository;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RequestDaoImp implements RequestDao {
    private final RequestRepository requestRepository;

    public RequestDaoImp(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    @Transactional
    @Override
    public ItemRequest addRequest(ItemRequest request) {
        request.setCreated(LocalDateTime.now());
        return requestRepository.save(request);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ItemRequest> getAllRequestOneUser(int requesterId) {
        return requestRepository.findAllByRequesterIdOrderByCreatedAsc(requesterId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ItemRequest> getRequestsAllUsers(User requester, int from, int size) {
        return requestRepository
                .findAllByRequesterNotOrderByCreatedAsc(requester, PageRequest.of(from, size))
                .stream()
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public ItemRequest getRequestById(int id) {
        return requestRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("по вашему id не был найден запрос"));
    }

}
