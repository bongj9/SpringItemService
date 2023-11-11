package hello.itemservice.domain.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {
    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model){ //아이템목록조회
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "/basic/items"; //여기위치에 view를 만들어 넣는다
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item); //모델에 넣는것
        return "/basic/item";
    }

    @GetMapping("/add")
    public String addForm() {
        return "/basic/addForm";
    }
    @PostConstruct //테스트용 데이터 추가
    public void init() {
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));

    }
}
 /*   @Autowired
    public BasicItemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository; //@AutoWired를 통해 컴포턴트에 등록된다
    }//생성자가 한개만있다면 Autowired를 생략할수있다
}하지만 이걸 RequiredArgsConstructor(lombok)으로 final이 붙은 인스턴스를 생성자를 만들어준다 */
