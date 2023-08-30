package ru.practicum.shareit.request.service;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.dao.ItemDao;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.request.dao.RequestDao;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.mapper.RequestMapper;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.dao.UserDao;
import ru.practicum.shareit.user.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RequestService {
    private final UserDao userDao;
    private final ItemDao itemDao;
    private final RequestDao requestDao;

    public RequestService(UserDao userDao, ItemDao itemDao, RequestDao requestDao) {
        this.userDao = userDao;
        this.itemDao = itemDao;
        this.requestDao = requestDao;
    }

    public ItemRequestDto addRequest(ItemRequestDto dto, int requesterId) {
        User user = userDao.getUserById(requesterId);
        ItemRequest request = RequestMapper.toRequest(dto, user);
        return RequestMapper.toRequestDto(requestDao.addRequest(request));
    }

    public List<ItemRequestDto> getAllRequestOneUser(int requesterId) {
        userDao.getUserById(requesterId);
        List<ItemRequestDto> listDto = requestDao.getAllRequestOneUser(requesterId)
                .stream()
                .map(RequestMapper::toRequestDto)
                .collect(Collectors.toList());
        listDto.forEach(this::setRequestItems);
        return listDto;
    }

    public List<ItemRequestDto> getRequestsAllUsers(int requesterId, int from, int size) {
        User user = userDao.getUserById(requesterId);
        List<ItemRequestDto> listDto = requestDao.getRequestsAllUsers(user, from, size)
                .stream()
                .map(RequestMapper::toRequestDto)
                .collect(Collectors.toList());
        listDto.forEach(this::setRequestItems);
        return listDto;
    }

    public ItemRequestDto getRequestById(int requestId, int requesterId) {
        userDao.getUserById(requesterId);
        ItemRequestDto dto = RequestMapper.toRequestDto(requestDao.getRequestById(requestId));
        setRequestItems(dto);
        return dto;
    }

    private void setRequestItems(ItemRequestDto itemRequestDto) {
        itemRequestDto.setItems(itemDao.getAllItemsByOneRequest(itemRequestDto.getId())
                .stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList()));
    }
}
