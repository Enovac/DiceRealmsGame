package main.java.game.collectibles;
import main.java.game.realms.*;
public class Bonus extends Reward{
    private RealmColor bonusColor;
//=======================================Constructor===================================
    public Bonus(RealmColor bonusColor){
        super(RewardType.BONUS);
        this.bonusColor=bonusColor;
    }
//=======================================Get&Set=======================================
    public RealmColor getBonusColor(){
        return bonusColor;
    }
    protected void setEssenceBonusColor(RealmColor color){
        this.bonusColor=color;
    }
}
