package hello.itemservice.domain.item;


import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository //@componEnt의 대상이 된다
public class ItemRepository {

    private static final Map<Long, Item> store = new HashMap<>(); //static //여러개가 동시에 접근하게 된다면 해시맵을 쓰면안되고 concurrnetHashMap, atomicmLong으로 써야한다
    private static Long sequence = 0L; //static

    public Item save(Item item) {
        item.setId(++sequence); //시퀀스값 증가시켜서 아이템값에 넣고
        store.put(item.getId(), item); //스토어에 아이템 반
        return item;
    }

    public Item findById(Long id) {
        return store.get(id);
    }

    public List<Item> findAll() { //전체조회
        return new ArrayList<>(store.values()); //Collection
    }

    public void update(Long itemId, Item updateParam) {
        Item findItem = findById(itemId);
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    public void clearStore() {
        store.clear();
    }
}
