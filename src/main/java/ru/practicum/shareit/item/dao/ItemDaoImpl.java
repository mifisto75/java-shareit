package ru.practicum.shareit.item.dao;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.exeptions.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ItemDaoImpl implements ItemDao {

    private int nextItemId = 1;
    public HashMap<Integer, Item> itemStorage = new HashMap<>(); //хранение вещей в памяти

    @Override
    public ItemDto addItems(Item item, int ownerId) {
        item.setId(nextItemId++);
        item.setOwner(ownerId);
        itemStorage.put(item.getId(), item);
        return ItemMapper.toItemDto(item);
    }

    @Override
    public ItemDto updateItems(int itemId, Item item, int ownerId) {
        checkIdItemStorage(itemId);
        if (itemStorage.get(itemId).getOwner() != ownerId) {
            throw new NotFoundException("вы не можете редактировать чужие объявления");
        }
        if (item.getName() != null) {
            itemStorage.get(itemId).setName(item.getName());
        }
        if (item.getDescription() != null) {
            itemStorage.get(itemId).setDescription(item.getDescription());
        }
        if (item.getAvailable() != null) {
            itemStorage.get(itemId).setAvailable(item.getAvailable());
        }
        return ItemMapper.toItemDto(itemStorage.get(itemId));
    }

    @Override
    public ItemDto getItemsById(int itemId) {
        checkIdItemStorage(itemId);
        return ItemMapper.toItemDto(itemStorage.get(itemId));
    }

    @Override
    public List<ItemDto> getAllItemsOneUser(int ownerId) {
        return itemStorage.values().stream().filter(item -> item.getOwner() == ownerId)
                        .map(ItemMapper::toItemDto).collect(Collectors.toList());
    }

    @Override
    public List<ItemDto> searchItemByText(String text) {
        return itemStorage.values().stream().filter(Item::getAvailable)
                        .filter(item -> item.getName().toLowerCase().contains(text.toLowerCase()) ||
                        item.getDescription().toLowerCase().contains(text.toLowerCase()))
                                .map(ItemMapper::toItemDto).collect(Collectors.toList());
    }

    @Override
    public void checkIdItemStorage(int id) {
        if (!itemStorage.containsKey(id)) {
            throw new NotFoundException("по вашему id не была найдена вещь");
        }
    }

}
