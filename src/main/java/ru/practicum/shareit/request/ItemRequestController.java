package ru.practicum.shareit.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.service.RequestService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/requests")
public class ItemRequestController {
    private final String requester = "X-Sharer-User-Id";
    private RequestService requestService;

    @Autowired
    public ItemRequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @PostMapping
    public ItemRequestDto addRequest(@Valid @RequestBody ItemRequestDto itemRequestDto,
                                     @RequestHeader(requester) Integer requesterId) { //добавить новый запрос вещи.
        return requestService.addRequest(itemRequestDto, requesterId);
    }

    @GetMapping
    public List<ItemRequestDto> getAllRequestOneUser(@RequestHeader(requester) Integer requesterId) { // получить список своих запросов
        return requestService.getAllRequestOneUser(requesterId);
    }

    @GetMapping("/all")
    public List<ItemRequestDto> getRequestsAllUsers(
            @RequestHeader(requester) Integer requesterId,
            @PositiveOrZero @RequestParam(defaultValue = "0" ,required = false) Integer from,
            @Positive @RequestParam(defaultValue = "20" ,required = false) Integer size) { // получить список запросов, созданных другими пользователями.
        return requestService.getRequestsAllUsers(requesterId, from, size);
    }

    @GetMapping("/{requestId}")
    public ItemRequestDto getRequestById(@PathVariable Integer requestId,
                                         @RequestHeader(requester) Integer requesterId) { // получить данные об одном конкретном запросе
        return requestService.getRequestById(requestId, requesterId);
    }

}
