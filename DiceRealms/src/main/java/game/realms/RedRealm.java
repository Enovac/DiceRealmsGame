package main.java.game.realms;

import main.java.game.collectibles.ArcaneBoost;
import main.java.game.collectibles.Bonus;
import main.java.game.collectibles.ElementalCrest;
import main.java.game.collectibles.Reward;
import main.java.game.creatures.*;
import main.java.game.dice.Dice;
import main.java.game.dice.RedDice;
import main.java.game.engine.Move;
import java.util.*;

public class RedRealm extends Realms{//TODO: CHECK OTHER TODOS IN OTHER REALM
    private Dragon dragon1;
    private Dragon dragon2;
    private Dragon dragon3;
    private Dragon dragon4;
    private final int faceRow;
    private final int wingsRow;
    private final int tailRow;
    private final int heartRow;
    private final int diagonalRow;
//=======================================Constructor===================================
    public RedRealm(){
        super(RealmColor.RED,5,16);
        dragon1=new Dragon(DragonNumber.Dragon1,3,2,1,0);
        dragon2=new Dragon(DragonNumber.Dragon2,6,1,0,3);
        dragon3=new Dragon(DragonNumber.Dragon3,5,0,2,4);
        dragon4=new Dragon(DragonNumber.Dragon4,0,5,4,6);
        faceRow=0;
        wingsRow=1;
        tailRow=2;
        heartRow=3;
        diagonalRow=4;
    }
//=======================================Methods=======================================
    @Override
    public boolean attack(int diceValue, Creature creature) {//TODO: FIX MAKE DEPEND ON THE DICE.GETSELECTDRAG PASS DICE PARAMETER
        Dragon currentDragon=(Dragon)creature;
        if(currentDragon.checkPossibleAttack(diceValue)){
            incrementTotalNumberOfAttacks();
            recordAttack(diceValue,currentDragon);//the string thing
            currentDragon.attackPart(diceValue);
            updateTotalRealmScore(diceValue);
            if(isRealmDefeated())
                closeRealm();
            return true;
        }
        return false;
    }
    @Override
    public void updateTotalRealmScore(int value) {
        int newScore=0;
        if(dragon1.isDeadDragon())
            newScore+=10;
        if(dragon2.isDeadDragon())
            newScore+=14;
        if(dragon3.isDeadDragon())
            newScore+=16;
        if(dragon4.isDeadDragon())
            newScore+=20;
        setTotalRealmScore(newScore);             
    }
     
    @Override
    public Move[] getAllPossibleMoves() {
        if(!isRealmAccessible())
            return new Move[0];
        Move[] moves=new Move[12-getTotalNumberOfAttacks()];
        for(int i=1,j=0;i<=6;i++){
            Move[]movesForDice=getPossibleMovesForADie(i, RealmColor.RED);
            for(Move move:movesForDice)
                moves[j++]=move;
                            
        }      
        return moves;
    }

    @Override
    public Move[] getPossibleMovesForADie(int diceValue, RealmColor colorOfDice) {
        int counter=0;
    
        Move dragon1Move=null;
        Move dragon2Move=null;
        Move dragon3Move=null;
        Move dragon4Move=null;

        if(dragon1.checkPossibleAttack(diceValue)){
            counter++;
            RedDice tempDice=new RedDice(diceValue);
            dragon1Move =new Move(tempDice,dragon1);
        }
        if(dragon2.checkPossibleAttack(diceValue)){
            counter++;
            RedDice tempDice=new RedDice(diceValue);
            dragon2Move =new Move(tempDice,dragon2);
        }
        if(dragon3.checkPossibleAttack(diceValue)){
            counter++;
            RedDice tempDice=new RedDice(diceValue);
            dragon3Move =new Move(tempDice,dragon3);
        }
        if(dragon4.checkPossibleAttack(diceValue)){
            counter++;
            RedDice tempDice=new RedDice(diceValue);
            dragon4Move =new Move(tempDice,dragon4);
        }

        Move[] moves=new Move[counter];

        int index=0;

        if(dragon1Move!=null)
            moves[index++]=dragon1Move;
        if(dragon2Move!=null)
            moves[index++]=dragon2Move;      
        if(dragon3Move!=null)
            moves[index++]=dragon3Move;   
        if(dragon4Move!=null)
            moves[index++]=dragon4Move; 

        return moves;
    }
   
    @Override
    public void initializePreviousAttacks(String[] previousAttacks) {
        String[] templateValue=new String[]{"3","6","5","X","2","1","X","5","1","X","2","4","X","3","4","6"};
        for(int i=0;i<templateValue.length;i++)
            previousAttacks[i]=templateValue[i]+"    ";
    }
    public void recordAttack(int diceValue,Dragon drag){
        int offsetValue=4;
        if(diceValue==drag.getFace())
            offsetValue*=0;
        else if(diceValue==drag.getWings())
            offsetValue*=1;
        else if(diceValue==drag.getTail())
            offsetValue*=2;
        else
            offsetValue*=3;
        switch (drag.getDragonNumber()) {
            case Dragon4:offsetValue++;
            case Dragon3:offsetValue++;
            case Dragon2:offsetValue++;
            case Dragon1:break;//did to remove the warning
        }    
        recordAttack(offsetValue);    
    }
    @Override
    public void recordAttack(int indexValue){
        String[]previousAttacks=getPreviousAttacks();
        previousAttacks[indexValue]="X    ";

    }
//=======================================Get&Set=======================================
    @Override
    public Reward[] getReward() {
        Reward[] rewards=getRealmRewards();
        ArrayList<Reward> outputRewards=new ArrayList<Reward>();
        if(isAllFacesKilled()&&rewards[faceRow]!=null){
            Reward recievedReward=rewards[faceRow];
            rewardClaimed(faceRow);
              outputRewards.add(recievedReward);
        }  
        if(isAllWingsKilled()&&rewards[wingsRow]!=null){
            Reward recievedReward=rewards[wingsRow];
            rewardClaimed(wingsRow);
            outputRewards.add(recievedReward);
        }  
        if(isAllTailsKilled()&&rewards[tailRow]!=null){
            Reward recievedReward=rewards[tailRow];
            rewardClaimed(tailRow);
            outputRewards.add(recievedReward);;
        }  
        if(isAllHeartsKilled()&&rewards[heartRow]!=null){
            Reward recievedReward=rewards[heartRow];
            rewardClaimed(heartRow);
            outputRewards.add(recievedReward);
        }  
        if(dragon1.isFaceKilled()&&dragon2.isWingsKilled()&&dragon3.isTailKilled()&&dragon4.isHeartKilled()){
            Reward recievedReward=rewards[diagonalRow];
            rewardClaimed(diagonalRow);
            outputRewards.add(recievedReward);
        }
        Reward[] rewardsOutput=new Reward[outputRewards.size()];
        for(int i=0;i<rewardsOutput.length;i++)
            rewardsOutput[i]=outputRewards.get(i);
            
        Arrays.sort(rewardsOutput);
        return rewardsOutput;  
    }
    
     @Override
    public boolean isRewardAvailable() {//TODO: Fix implementation so its diagonal not all dead
        Reward[] rewards=getRealmRewards();
        if(isAllFacesKilled()&&rewards[faceRow]!=null)  
            return true;
        if(isAllWingsKilled()&&rewards[wingsRow]!=null)  
            return true;    
        if(isAllTailsKilled()&&rewards[tailRow]!=null)  
            return true;     
        if(isAllHeartsKilled()&&rewards[heartRow]!=null)  
            return true; 
        if(dragon1.isFaceKilled()&&dragon2.isWingsKilled()&&dragon3.isTailKilled()&&dragon4.isHeartKilled()&&rewards[diagonalRow]!=null)
            return true;    
        return false;    
    }
    public Dragon[] getAliveDragons(){
        int count=0;
        if(!dragon1.isDeadDragon())
            count++;
        if(!dragon2.isDeadDragon())
            count++;
        if(!dragon3.isDeadDragon())
            count++;
        if(!dragon4.isDeadDragon())
            count++;
        if(count==0)
            return null;
        Dragon[] aliveDragons=new Dragon[count];
        count=0;
        if(!dragon1.isDeadDragon())
            aliveDragons[count++]=dragon1;
        if(!dragon2.isDeadDragon())
            aliveDragons[count++]=dragon2;
        if(!dragon3.isDeadDragon())
            aliveDragons[count++]=dragon3;
        if(!dragon4.isDeadDragon())
            aliveDragons[count++]=dragon4;
        return aliveDragons;
    }
    public Dragon getDragon1(){
        return dragon1;
    }
    public Dragon getDragon2(){
        return dragon2;
    }
    public Dragon getDragon3(){
        return dragon3;
    }
    public Dragon getDragon4(){
        return dragon4;
    }
    
    public boolean isAllFacesKilled(){
        return dragon1.isFaceKilled()&&dragon2.isFaceKilled()&&
               dragon3.isFaceKilled()&&dragon4.isFaceKilled();
    }
    public boolean isAllWingsKilled(){
        return dragon1.isWingsKilled()&&dragon2.isWingsKilled()&&
               dragon3.isWingsKilled()&&dragon4.isWingsKilled();
    }
    public boolean isAllTailsKilled(){
        return dragon1.isTailKilled()&&dragon2.isTailKilled()&&
        dragon3.isTailKilled()&&dragon4.isTailKilled();
    }
    public boolean isAllHeartsKilled(){
        return dragon1.isHeartKilled()&&dragon2.isHeartKilled()&&
               dragon3.isHeartKilled()&&dragon4.isHeartKilled();
    }
    @Override
    public void setRealmRewards(Reward[] realmRewards) {
        Reward[] templateRewards=new Reward[]{new Bonus(RealmColor.GREEN),new Bonus(RealmColor.YELLOW),new Bonus(RealmColor.BLUE),
            new ElementalCrest(),new ArcaneBoost()};
        for(int i=0;i<templateRewards.length;i++)
            realmRewards[i]=templateRewards[i];
    }
     public  Creature getCreatureByRealm(Dice dice){
        int dv=dice.getValue();
        Dragon[] temp=new Dragon[]{dragon1,dragon2,dragon3,dragon4};
        for(Dragon x:temp)
            if(x.getFace()==dv||x.getWings()==dv||x.getTail()==dv||x.getHeart()==dv)
                return x;
        return null;        
    }
    @Override
    public boolean isRealmDefeated() {
        return dragon1.isDeadDragon()&&dragon2.isDeadDragon()&&dragon3.isDeadDragon()&&dragon4.isDeadDragon();
    } 
//=======================================Display=======================================
    @Override
    public String toString() {
        String[] prevAt=getPreviousAttacks();//previousAttacks
        String[] drawRew=new String[]{"GB   ","YB   ","BB   ","EC   ","AB   "};//drawReward
        Reward[] rewards=getRealmRewards();
        if(rewards[faceRow]==null)
            drawRew[0]="X    ";
        if(rewards[wingsRow]==null)
            drawRew[1]="X    "; 
        if(rewards[tailRow]==null)
            drawRew[2]="X    ";   
        if(rewards[heartRow]==null)
            drawRew[3]="X    ";
        if(rewards[diagonalRow]==null)
            drawRew[4]="X    ";


        return "Emberfall Dominion: Pyroclast Dragon (RED REALM):"+"\n"+
        "+-----------------------------------+"+"\n"+
        "|  #  |D1   |D2   |D3   |D4   |R    |"+"\n"+
        "+-----------------------------------+"+"\n"+
        "|  F  |"+prevAt[0]+"|"+prevAt[1]+"|"+prevAt[2]+"|"+prevAt[3]+"|"+drawRew[0]+"|"+"\n"+
        "|  W  |"+prevAt[4]+"|"+prevAt[5]+"|"+prevAt[6]+"|"+prevAt[7]+"|"+drawRew[1]+"|"+"\n"+
        "|  T  |"+prevAt[8]+"|"+prevAt[9]+"|"+prevAt[10]+"|"+prevAt[11]+"|"+drawRew[2]+"|"+"\n"+
        "|  H  |"+prevAt[12]+"|"+prevAt[13]+"|"+prevAt[14]+"|"+prevAt[15]+"|"+drawRew[3]+"|"+"\n"+
        "+-----------------------------------+"+"\n"+
        "|  S  |10   |14   |16   |20   |"+drawRew[4]+"|"+"\n"+
        "+-----------------------------------+"+"\n\n";
    }

    
}
