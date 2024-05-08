package main.java.game.collectibles;
public class ArcaneBoost extends Power{
    @Override
    public int compareTo(Reward o) {
        RewardType reward1=getRewardType();
        RewardType reward2=getRewardType();
        if(reward1==RewardType.BONUS)
            return-1;
        if(reward2==RewardType.BONUS)
            return 1;
        if(reward1==RewardType.POWER)
            return-1;
        if(reward2==RewardType.POWER)
            return 1; 
        if(reward1==RewardType.CREST)
            return-1;
        return 1;//Reward2 is crest           
    }
}
