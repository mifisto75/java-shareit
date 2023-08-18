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
    private static final String OWNER_ID = "X-Sharer-User-Id";
    private ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }


    @PostMapping // addItems Добавление новой вещи
    public ItemDto addItems(@RequestBody ItemDto itemDto, @RequestHeader(OWNER_ID) int ownerId) {
        log.info("метод ddItems . userId " + ownerId);
        return itemService.addItems(itemDto, ownerId);
    }

    @PatchMapping("/{itemId}")  //updateItems Редактирование вещи
    public ItemDto updateItems(@PathVariable Integer itemId, @RequestBody ItemDto itemDto, @RequestHeader(OWNER_ID) int ownerId) {
        log.info("метод updateItems . userId " + ownerId + " itemId " + itemId);
        return itemService.updateItems(itemId, itemDto, ownerId);
    }


    @GetMapping("/{itemId}") // getItemsById Просмотр информации о конкретной вещи по её идентификатору
    public ItemDto getItemsById(@PathVariable Integer itemId, @RequestHeader(OWNER_ID) Integer ownerId) {
        log.info("метод getItemsById . userId " + ownerId + " itemId " + itemId);
        return itemService.getItemsById(itemId, ownerId);
    }

    @GetMapping() // getAllItemsOneUser Просмотр владельцем списка всех его вещей
    public List<ItemDto> getAllItemsOneUser(@RequestHeader(OWNER_ID) int ownerId) {
        log.info("метод getAllItemsOneUser . userId " + ownerId);
        return itemService.getAllItemsOneUser(ownerId);
    }

    @GetMapping("/search") // Поиск вещи потенциальным арендатором
    public List<ItemDto> searchItemByText(@RequestParam String text) {
        log.info("метод searchItemByText");
        return itemService.searchItemByText(text);
    }

    @PostMapping("/{itemId}/comment")
    public CommentDto addComment(@PathVariable Integer itemId, @RequestHeader(OWNER_ID) Integer userId,
                                 @RequestBody CommentDto commentDto) {
        log.info("метод addComment . userId " + userId + " itemId " + itemId);
        return itemService.addComment(itemId, userId, commentDto);
    }
}
