package main.java.game.collectibles;

public abstract class Reward {
    private final RewardType rewardType;
    public Reward(RewardType rewardType){
        this.rewardType=rewardType;
    }


    public RewardType getRewardType(){
        return rewardType;
    }

}
