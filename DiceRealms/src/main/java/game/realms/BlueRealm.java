package main.java.game.realms;

import main.java.game.collectibles.*;
import main.java.game.creatures.*;
import main.java.game.dice.BlueDice;
import main.java.game.dice.Dice;
import main.java.game.engine.*;

public class BlueRealm extends Realms{
    private Hydra hydra;
    private int totalDefeatedHeads;
//============================Constructor============================================
    public BlueRealm(){
         super(RealmColor.BLUE,11,11);
         hydra=new Hydra();
         hydra.spawnHydra();
    }
//============================Methods================================================
    @Override
    public boolean attack(int diceValue,Creature creature){
        if(hydra.checkPossibleAttack(diceValue)){
            if(hydra.killHead()&&!hydra.isRespawned())
                hydra.respawnHydra();
            totalDefeatedHeads++;
            incrementTotalNumberOfAttacks();
            recordAttack(diceValue);//the string thing
            updateTotalRealmScore(totalDefeatedHeads);
            if(isRealmDefeated())
                closeRealm();
            //Give user his rewards
            return true;
        }
        return false;

        }
    @Override
    public boolean isRealmDefeated() {
        return getTotalNumberOfAttacks()==11;
    }    
    @Override    
    public void updateTotalRealmScore(int totalDefeatedHeads){
        switch(totalDefeatedHeads){
            case 1:setTotalRealmScore(1);break;
            case 2:setTotalRealmScore(3);break;
            case 3:setTotalRealmScore(6);break;
            case 4:setTotalRealmScore(10);break;
            case 5:setTotalRealmScore(15);break;
            case 6:setTotalRealmScore(21);break;
            case 7:setTotalRealmScore(28);break;
            case 8:setTotalRealmScore(36);break;
            case 9:setTotalRealmScore(45);break;
            case 10:setTotalRealmScore(55);break;
            case 11:setTotalRealmScore(66);break;
        }
    }   
    @Override
    public Move[] getAllPossibleMoves() {
        if(!isRealmAccessible())
            return new Move[0];
        int minimumAttackValue=hydra.getMinimumAttackValue();
        int moveArraySize=6-minimumAttackValue+1;//+1 to include the minimumAttack Value
        Move[] moves=new Move[moveArraySize];
        for(int i=0;i<moves.length;i++,minimumAttackValue++)
            moves[i]=getPossibleMovesForADie(minimumAttackValue,RealmColor.BLUE)[0];
        return  moves;
    }
    @Override
    public Move[] getPossibleMovesForADie(int diceValue,RealmColor colorOfDice) {
        if(hydra.checkPossibleAttack(diceValue)){
            BlueDice tempDice=new BlueDice(diceValue);
            return new Move[]{new Move(tempDice,hydra)};
        }
        return new Move[0];
    }
    @Override
    public boolean isRewardAvailable() {
        Reward[] rewards=getRealmRewards();
        return rewards[getTotalNumberOfAttacks()-1]!=null;
    }
    @Override
    public void initializePreviousAttacks(String[] previousAttacks){
        for(int i=0;i<previousAttacks.length;i++)
            previousAttacks[i]="---  ";
        
    }
   
    
//============================G&S====================================================
    public Hydra getHydra(){
        return hydra;
    }
    @Override
    public Reward getReward() {
        Reward[] rewards=getRealmRewards();
        Reward recievedReward=rewards[getTotalNumberOfAttacks()-1];
        rewardClaimed(getTotalNumberOfAttacks()-1);
        return recievedReward;
    }
    @Override
    public void setRealmRewards(Reward[] realmRewards) {
        Reward[] templateRewards=new Reward[]{null,null,null,new ArcaneBoost(),null,
          new Bonus(RealmColor.GREEN),new ElementalCrest(),null,new Bonus(RealmColor.MAGENTA),new TimeWarp(),null};   
        for(int i=0;i<templateRewards.length;i++)
            realmRewards[i]=templateRewards[i];
    }
    public  Creature getCreatureByRealm(Dice dice){
        return hydra;
    }
//============================toString===============================================   
    @Override
    public String toString() {
       String[] prevAt=getPreviousAttacks();//previousAttacks
       String[] drawRew=new String[]{"AB   ","GB   ","EC   ","MB   ","TW   "};//drawReward
       switch(getTotalNumberOfAttacks()){
        case 11:
        case 10:drawRew[4]="X    ";
        case 9:drawRew[3]="X    ";
        case 8:
        case 7:drawRew[2]="X    ";
        case 6:drawRew[1]="X    ";
        case 5:
        case 4:drawRew[0]="X    ";
       } 
        
        return "Tide Abyss: Hydra Serpents (BLUE REALM):"+"\n"+
        "+-----------------------------------------------------------------------+"+"\n"+
        "|  #  |H11  |H12  |H13  |H14  |H15  |H21  |H22  |H23  |H24  |H25  |H26  |"+"\n"+
        "+-----------------------------------------------------------------------+"+"\n"+
        "|  H  |"+prevAt[0]+"|"+prevAt[1]+"|"+prevAt[2]+"|"+prevAt[3]+"|"+prevAt[4]+"|"
        +prevAt[5]+"|"+prevAt[6]+"|"+prevAt[7]+"|"+prevAt[8]+"|"+prevAt[9]+"|"+prevAt[10]+"|"+"\n"+
        "|  C  |≥1   |≥2   |≥3   |≥4   |≥5   |≥1   |≥2   |≥3   |≥4   |≥5   |≥6   |"+"\n"+
        "|  R  |     |     |     |"+drawRew[0]+"|     |"+drawRew[1]+"|"+drawRew[2]+"|     |"
        +drawRew[3]+"|"+drawRew[4]+"|     |"+"\n"+
        "+-----------------------------------------------------------------------+"+"\n"+
        "|  S  |1    |3    |6    |10   |15   |21   |28   |36   |45   |55   |66   |"+"\n"+
        "+-----------------------------------------------------------------------+"+"\n\n";
    }
    
    

    

}
