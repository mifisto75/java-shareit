package ru.practicum.shareit.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.service.RequestService;

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
    public ItemRequestDto addRequest(@RequestBody ItemRequestDto itemRequestDto,
                                     @RequestHeader(requester) Integer requesterId) { //добавить новый запрос вещи.
        log.info("метод addRequest user = " + requester);
        return requestService.addRequest(itemRequestDto, requesterId);
    }

    @GetMapping
    public List<ItemRequestDto> getAllRequestOneUser(@RequestHeader(requester) Integer requesterId) { // получить список своих запросов
        log.info("метод getAllRequestOneUser user = " + requester);
        return requestService.getAllRequestOneUser(requesterId);
    }

    @GetMapping("/all")
    public List<ItemRequestDto> getRequestsAllUsers(
            @RequestHeader(requester) Integer requesterId,
            @RequestParam(defaultValue = "0", required = false) Integer from,
            @RequestParam(defaultValue = "20", required = false) Integer size) { // получить список запросов, созданных другими пользователями.
        log.info("метод getRequestsAllUsers user = " + requester + " from = " + from + " size = " + size);
        return requestService.getRequestsAllUsers(requesterId, from, size);
    }

    @GetMapping("/{requestId}")
    public ItemRequestDto getRequestById(@PathVariable Integer requestId,
                                         @RequestHeader(requester) Integer requesterId) { // получить данные об одном конкретном запросе
        log.info("метод getRequestById user = " + requester + " Id = " + requesterId);
        return requestService.getRequestById(requestId, requesterId);
    }

}
