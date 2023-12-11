package ru.practicum.shareit.item;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/items")
public class ItemController {
    private final String user = "X-Sharer-User-Id";
    private ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }


    @PostMapping //  Добавление новой вещи
    public ItemDto addItems(@RequestBody ItemDto itemDto, @RequestHeader(user) Integer ownerId) {
        log.info("метод ddItems . userId " + ownerId);
        return itemService.addItem(itemDto, ownerId);
    }

    @PatchMapping("/{itemId}")  // Редактирование вещи
    public ItemDto updateItems(@PathVariable Integer itemId, @RequestBody ItemDto itemDto,
                               @RequestHeader(user) Integer ownerId) {
        log.info("метод updateItems . userId " + ownerId + " itemId " + itemId);
        return itemService.updateItem(itemId, itemDto, ownerId);
    }


    @GetMapping("/{itemId}") //  Просмотр информации о конкретной вещи по её идентификатору
    public ItemDto getItemsById(@PathVariable Integer itemId, @RequestHeader(user) Integer ownerId) {
        log.info("метод getItemsById . userId " + ownerId + " itemId " + itemId);
        return itemService.getItemById(itemId, ownerId);
    }

    @GetMapping() //  Просмотр владельцем списка всех его вещей
    public List<ItemDto> getAllItemsOneUser(@RequestHeader(user) Integer ownerId,
                                            @RequestParam(defaultValue = "0", required = false) Integer from,
                                            @RequestParam(defaultValue = "20", required = false) Integer size) {
        log.info("метод getAllItemsOneUser . userId " + ownerId);
        return itemService.getAllItemsOneUser(ownerId, from, size);
    }

    @GetMapping("/search") // Поиск вещи потенциальным арендатором
    public List<ItemDto> searchItemByText(@RequestParam String text,
                                          @RequestParam(defaultValue = "0", required = false) Integer from,
                                          @RequestParam(defaultValue = "20", required = false) Integer size) {
        log.info("метод searchItemByText");
        return itemService.searchItemByText(text, from, size);
    }

    @PostMapping("/{itemId}/comment")
    public CommentDto addComment(@PathVariable Integer itemId, @RequestHeader(user) Integer userId,
                                 @RequestBody CommentDto commentDto) {
        log.info("метод addComment . userId " + userId + " itemId " + itemId);
        return itemService.addComment(itemId, userId, commentDto);
    }
}
