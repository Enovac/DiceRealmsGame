package main.java.game.collectibles;

public abstract class Reward { //TODO IMPLEMENT COMPARABLE
    private final RewardType rewardType;
//=======================================Constructor===================================
    public Reward(RewardType rewardType){
        this.rewardType=rewardType;
    }
//=======================================Get&Set=======================================
    public RewardType getRewardType(){
        return rewardType;
    }
}
