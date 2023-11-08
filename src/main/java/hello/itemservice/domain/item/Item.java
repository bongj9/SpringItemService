package hello.itemservice.domain.item;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter //@Data를 ㅇDTO일때는 써도되지만 게터세터를 써보
public class Item {

    private Long id;
    private String itemName;
    private Integer price;
    private Integer quantity; //Integer를 int 대신 쓴 이유는 null값도 쓸수있어

    public Item() {
    }//기본 생성자 생성

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    } //Id를 제외한 생성자를 생성
}
