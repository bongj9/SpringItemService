package hello.itemservice.domain.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {
    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) { //아이템목록조회
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

    /* @PostMapping("/add") //같은 url으로 오더라도 액션이 다르다 즉 http메서드로 액션을 구분한다*/
    public String addItemV1(@RequestParam String itemName,
                            @RequestParam int price,
                            @RequestParam Integer quantity,
                            Model model) {
        Item item = new Item(); //위에 파라미터룰 생성하면 이코드부터 객체를 생성해서 받는 역할을 한다
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item); //객체를 받아주는 역할

        model.addAttribute("item", item);

        return "/basic/item";
    }

    /*@PostMapping("/add") //같은 url으로 오더라도 액션이 다르다 즉 http메서드로 액션을 구분한다*/
    public String addItemV2(@ModelAttribute("item") Item item, Model model
    ) { //("item")을 없애도 기본규칙은 첫 클래스이름을 대문자에서 소문자로 바꿔지기 떄문에 생략가능하다
  /*      Item item = new Item(); //위에 파라미터룰 생성하면 이코드부터 객체를 생성해서 받는 역할을 한다
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);*/
//modelAttribute가 객체생성해주고 거기에 set을 호출해준다
        itemRepository.save(item); //객체를 받아주는 역할
/*          자동생성되기 떄문에 생략가능
        model.addAttribute("item", item);*/

        return "/basic/item";
    }

  //  @PostMapping("/add")
    public String addItemv3(Item item) {//우리가 만든 임의의 객체는 모델어트리뷰트를 생략할수있다 일반 int,string은 파라미터로 생략가능
        itemRepository.save(item);
        return "/basic/item";
    }
    //@PostMapping("/add")
    public String addItemv5(Item item) {
        itemRepository.save(item);
        return "redirect:/basic/items/" + item.getId();
    }
    @PostMapping("/add")
    public String addItemv6(Item item ,RedirectAttributes redirectAttributes) {
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/basic/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) { //어떤상품을 수정할지 확인해야함
        Item item = itemRepository.findById(itemId); //itemId를 찾아오고
        model.addAttribute("item",item);
        return "basic/editForm";
    }
    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) { //어떤상품을 수정할지 확인해야함
        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}";
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
