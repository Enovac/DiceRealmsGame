package main.java.game.realms;

import main.java.game.collectibles.ArcaneBoost;
import main.java.game.collectibles.Bonus;
import main.java.game.collectibles.ElementalCrest;
import main.java.game.collectibles.Reward;
import main.java.game.collectibles.TimeWarp;
import main.java.game.creatures.*;
import main.java.game.dice.*;
import main.java.game.engine.Move;

public class YellowRealm extends Realms{
    private Lion lion;

//============================Constructor============================================
    public YellowRealm(){
        super(RealmColor.YELLOW,11,11);
        lion=new Lion();
    }
//============================Methods================================================
    @Override
    public boolean attack(int diceValue, Creature creature) {
        if(lion.checkPossibleAttack(diceValue)){
            incrementTotalNumberOfAttacks();
            int totalNumberOfAttacks=getTotalNumberOfAttacks();
            if(totalNumberOfAttacks==4||totalNumberOfAttacks==7||totalNumberOfAttacks==9)
                diceValue*=2;
            else if(totalNumberOfAttacks==11)
                diceValue*=3;    
            updateTotalRealmScore(diceValue);
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
            
        Move[] moves =new Move[6];
        for(int i=0;i<moves.length;i++)
             moves[i]=getPossibleMovesForADie(i+1,RealmColor.YELLOW)[0];
        return moves;     

    }
    @Override
    public Move[] getPossibleMovesForADie(int diceValue, RealmColor colorOfDice) {
        if(lion.checkPossibleAttack(diceValue)){
            YellowDice tempDice=new YellowDice(diceValue);
            return new Move[]{new Move(tempDice,lion)};
        }
        return new Move[0];
    }
    @Override
    public boolean isRewardAvailable() {
        Reward[] rewards=getRealmRewards();
        return rewards[getTotalNumberOfAttacks()-1]!=null;
    }
    @Override
    public void initializePreviousAttacks(String[] previousAttacks) {
        for(int i=0;i<previousAttacks.length;i++)
         previousAttacks[i]="0    ";
    }

//============================G&S====================================================    
    public Lion getLion(){
        return lion;
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
        Reward[] templateRewards=new Reward[]{null,null,new TimeWarp(),null,new Bonus(RealmColor.RED),
            new ArcaneBoost(),null,new ElementalCrest(),null,new Bonus(RealmColor.MAGENTA),null};

        for(int i=0;i<templateRewards.length;i++)
            realmRewards[i]=templateRewards[i];
    }
    public  Creature getCreatureByRealm(Dice dice){
        return lion;   
    }
//============================toString===============================================   
    @Override
    public String toString() {
            String[] prevAt=getPreviousAttacks();//previousAttacks
            String[] drawRew=new String[]{"TW   ","RB   ","AB   ","EC   ","MB   "};//drawReward
            switch(getTotalNumberOfAttacks()){
            case 11:
            case 10:drawRew[4]="X    ";
            case 9:
            case 8:drawRew[3]="X    ";
            case 7:
            case 6:drawRew[2]="X    ";
            case 5:drawRew[1]="X    ";
            case 4:
            case 3:drawRew[0]="X    ";
            } 

           return "Radiant Savanna: Solar Lion (YELLOW REALM):"+"\n"+
           "+-----------------------------------------------------------------------+"+"\n"+
           "|  #  |1    |2    |3    |4    |5    |6    |7    |8    |9    |10   |11   |"+"\n"+
           "+-----------------------------------------------------------------------+"+"\n"+
           "|  H  |"+prevAt[0]+"|"+prevAt[1]+"|"+prevAt[2]+"|"+prevAt[3]+"|"+prevAt[4]+"|"+
            prevAt[5]+"|"+prevAt[6]+"|"+prevAt[7]+"|"+prevAt[8]+"|"+prevAt[9]+"|"+prevAt[10]+"|"+"\n"+
           "|  M  |     |     |     |x2   |     |     |x2   |     |x2   |     |x3   |"+"\n"+
           "|  R  |     |     |"+drawRew[0]+"|     |"+drawRew[1]+"|"+drawRew[2]+"|     |"+
            drawRew[3]+"|     |"+drawRew[4]+"|     |"+"\n"+
           "+-----------------------------------------------------------------------+"+"\n\n";
    }
    

}
