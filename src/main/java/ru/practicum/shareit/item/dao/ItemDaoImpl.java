package ru.practicum.shareit.item.dao;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.exeptions.NotFoundException;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.ItemRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ItemDaoImpl implements ItemDao {

    private final ItemRepository itemRepository;
    private final CommentRepository commentRepository;

    public ItemDaoImpl(ItemRepository itemRepository, CommentRepository commentRepository) {
        this.itemRepository = itemRepository;
        this.commentRepository = commentRepository;
    }

  //  @Transactional
    @Override
    public Item addItems(Item item) {
        return itemRepository.save(item);
    }

  //  @Transactional
    @Override
    public Item updateItems(int itemId, Item item) {
        Item originalItem = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("по вашему id не была найдена вещь"));

        if (originalItem.getOwner().getId() != item.getOwner().getId()) {
            throw new NotFoundException("вы не можете редактировать чужие объявления");
        }
        Optional.ofNullable(item.getName()).ifPresent(originalItem::setName);
        Optional.ofNullable(item.getDescription()).ifPresent(originalItem::setDescription);
        Optional.ofNullable(item.getAvailable()).ifPresent(originalItem::setAvailable);

        return itemRepository.save(originalItem);
    }

  //  @Transactional(readOnly = true)
    @Override
    public Item getItemsById(int itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("по вашему id не была найдена вещь"));
    }

  //  @Transactional(readOnly = true)
    @Override
    public List<Item> getAllItemsOneUser(int ownerId) {
        return itemRepository.findAll()
                .stream()
                .filter(item -> item.getOwner().getId() == ownerId)
                .collect(Collectors.toList());

    }

  //  @Transactional(readOnly = true)
    @Override
    public List<Item> searchItemByText(String text) {
        return itemRepository.findAll()
                .stream()
                .filter(Item::getAvailable)
                .filter(item -> containsText(item, text))
                .collect(Collectors.toList());
    }

   // @Transactional
    @Override
    public Comment addComment(Comment comment) {
        comment.setCreated(LocalDateTime.now()); //максимально мвежая дата перед добовлением в бд
        return commentRepository.save(comment);
    }

  //  @Transactional(readOnly = true)
    @Override
    public List<Comment> getAllCommentOneItem(int id) {
        return commentRepository.findByItemId(id);
    }

    private boolean containsText(Item item, String text) {
        return item.getName().toLowerCase().contains(text.toLowerCase()) ||
                item.getDescription().toLowerCase().contains(text.toLowerCase());
    }

}
