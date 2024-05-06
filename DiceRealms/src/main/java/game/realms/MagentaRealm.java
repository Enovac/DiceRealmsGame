package main.java.game.realms;

import main.java.game.collectibles.*;
import main.java.game.creatures.*;
import main.java.game.dice.*;
import main.java.game.engine.Move;

public class MagentaRealm extends Realms{
    private Phoenix phoenix;

//============================Constructor============================================
    public MagentaRealm(){
        super(RealmColor.MAGENTA,11,11);
        phoenix=new Phoenix();
    }
//============================Methods================================================
    @Override
    public  boolean attack(int diceValue,Creature creature){
        if(phoenix.checkPossibleAttack(diceValue)){
            phoenix.setHealth(diceValue);
            updateTotalRealmScore(diceValue);
            incrementTotalNumberOfAttacks();
            recordAttack(diceValue);//the string thing
            if(isRealmDefeated())
                closeRealm();
            //Give Rewards 
            return true;
        }
        return false;
    }
    @Override
    public void updateTotalRealmScore(int value) {
        int oldScore=getTotalRealmScore();
        setTotalRealmScore(value+oldScore);
    }
    @Override
    public boolean isRealmDefeated() {
        return getTotalNumberOfAttacks()==11;
    }
    @Override
    public Move[] getAllPossibleMoves() {
        if(!isRealmAccessible())
            return new Move[0];
        int minimumAttackValue=phoenix.getMinimumAttackValue();
        int moveArraySize=6-minimumAttackValue+1;//+1 to include the minimumAttack Value
        Move[] moves=new Move[moveArraySize];
        for(int i=0;i<moves.length;i++,minimumAttackValue++)
            moves[i]=getPossibleMovesForADie(minimumAttackValue,RealmColor.MAGENTA)[0];
        return  moves;
    }
    @Override
    public Move[] getPossibleMovesForADie(int diceValue, RealmColor colorOfDice) {
        if(phoenix.checkPossibleAttack(diceValue)){
            MagentaDice tempDice=new MagentaDice(diceValue);
            return new Move[]{new Move(tempDice,phoenix)};
        }
        return new Move[0];
    }
    @Override
    public boolean isRewardAvailable() {
        Reward[] rewards=getRealmRewards();
        return rewards[getTotalNumberOfAttacks()]!=null;
    }
    @Override
    public void initializePreviousAttacks(String[] previousAttacks) {
        for(int i=0;i<previousAttacks.length;i++)
         previousAttacks[i]="0    ";
    }
//============================G&S====================================================   
    public Phoenix getPhoenix(){
        return phoenix;
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
        Reward[] templateRewards=new Reward[]{null,null,new TimeWarp(),new Bonus(RealmColor.GREEN),new ArcaneBoost(),
        new Bonus(RealmColor.RED),new ElementalCrest(),new TimeWarp(),new Bonus(RealmColor.BLUE),new Bonus(RealmColor.YELLOW),
        new ArcaneBoost()};

        for(int i=0;i<templateRewards.length;i++)
            realmRewards[i]=templateRewards[i];
    }
    public  Creature getCreatureByRealm(Dice dice){
        return phoenix;   
    }
//============================toString===============================================   
    @Override
    public String toString() {
    String[] prevAt=getPreviousAttacks();//previousAttacks
       String[] drawRew=new String[]{"TW   ","GB   ","AB   ","RB   ","EC   ","TW   ","BB   ","YB   ","AB   "};//drawReward
       switch(getTotalNumberOfAttacks()){
        case 11:drawRew[8]="X    ";
        case 10:drawRew[7]="X    ";
        case 9:drawRew[6]="X    ";
        case 8:drawRew[5]="X    ";
        case 7:drawRew[4]="X    ";
        case 6:drawRew[3]="X    ";
        case 5:drawRew[2]="X    ";
        case 4:drawRew[1]="X    ";
        case 3:drawRew[0]="X    ";
       } 
        

      return "Mystical Sky: Majestic Phoenix (MAGENTA REALM):"+"\n"+
       "+-----------------------------------------------------------------------+"+"\n"+
       "|  #  |1    |2    |3    |4    |5    |6    |7    |8    |9    |10   |11   |"+"\n"+
       "+-----------------------------------------------------------------------+"+"\n"+
       "|  H  |"+prevAt[0]+"|"+prevAt[1]+"|"+prevAt[2]+"|"+prevAt[3]+"|"+prevAt[4]+"|"+
       prevAt[5]+"|"+prevAt[6]+"|"+prevAt[7]+"|"+prevAt[8]+"|"+prevAt[9]+"|"+prevAt[10]+"|"+"\n"+
       "|  C  |<    |<    |<    |<    |<    |<    |<    |<    |<    |<    |<    |"+"\n"+
       "|  R  |     |     |"+drawRew[0]+"|"+drawRew[1]+"|"+drawRew[2]+"|"+drawRew[3]+"|"+
       drawRew[4]+"|"+drawRew[5]+"|"+drawRew[6]+"|"+drawRew[7]+"|"+drawRew[8]+"|"+"\n"+
       "+-----------------------------------------------------------------------+"+"\n\n";
    }
   

}
 