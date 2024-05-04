package main.java.game.collectibles;
import main.java.game.realms.*;
public class Bonus extends Reward{
    private final RealmColor bonusColor;
    public Bonus(RealmColor bonusColor){
        super(RewardType.BONUS);
        this.bonusColor=bonusColor;
    }

    public RealmColor getBonusColor(){
        return bonusColor;
    }

}
