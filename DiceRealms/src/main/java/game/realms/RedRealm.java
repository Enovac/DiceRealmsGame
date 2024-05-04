package main.java.game.realms;

import main.java.game.creatures.*;
import main.java.game.dice.RedDice;
import main.java.game.engine.Move;


public class RedRealm extends Realms{
    private Dragon dragon1;
    private Dragon dragon2;
    private Dragon dragon3;
    private Dragon dragon4;
//============================Constructor============================================
    public RedRealm(){
        super(RealmColor.RED);
        dragon1=new Dragon(DragonNumber.Dragon1,3,2,1,0);
        dragon2=new Dragon(DragonNumber.Dragon2,6,1,0,3);
        dragon3=new Dragon(DragonNumber.Dragon3,5,0,2,4);
        dragon4=new Dragon(DragonNumber.Dragon4,0,5,4,6);
    }
//============================Methods================================================
  
    @Override
    public boolean attack(int diceValue, Creature creature) {
        Dragon currentDragon=(Dragon)creature;
        if(currentDragon.checkPossibleAttack(diceValue)){
            currentDragon.attackPart(diceValue);
            incrementTotalNumberOfAttacks();
            updateTotalRealmScore(diceValue);//change
            if(isRealmDefeated())
                closeRealm();
            //getRewards
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
    public boolean isRealmDefeated() {
        return dragon1.isDeadDragon()&&dragon2.isDeadDragon()&&dragon3.isDeadDragon()&&dragon4.isDeadDragon();
    }  
    @Override
    public Move[] getAllPossibleMoves() {
        if(!isRealmAccessible())
            return null;

        Move[] movesForDice1=getPossibleMovesForADie(1,RealmColor.RED); 
        Move[] movesForDice2=getPossibleMovesForADie(2,RealmColor.RED); 
        Move[] movesForDice3=getPossibleMovesForADie(3,RealmColor.RED); 
        Move[] movesForDice4=getPossibleMovesForADie(4,RealmColor.RED);  
        Move[] movesForDice5=getPossibleMovesForADie(5,RealmColor.RED); 
        Move[] movesForDice6=getPossibleMovesForADie(6,RealmColor.RED); 
        
        int moveArraySize=0;
        if(movesForDice1!=null)
            moveArraySize+=movesForDice1.length;
        if(movesForDice2!=null)
            moveArraySize+=movesForDice2.length;  
        if(movesForDice3!=null)
            moveArraySize+=movesForDice3.length;  
        if(movesForDice4!=null)
            moveArraySize+=movesForDice4.length;
        if(movesForDice5!=null)
            moveArraySize+=movesForDice5.length;
        if(movesForDice6!=null)
            moveArraySize+=movesForDice6.length;

        Move[] moves=new Move[moveArraySize];
        int index=0;
        
        if(movesForDice1!=null)
            for(Move x:movesForDice1)
                moves[index++]=x;
        if(movesForDice2!=null)
            for(Move x:movesForDice2)
                moves[index++]=x; 
        if(movesForDice3!=null)
            for(Move x:movesForDice3)
                moves[index++]=x;  
        if(movesForDice4!=null)
            for(Move x:movesForDice4)
                moves[index++]=x; 
        if(movesForDice5!=null)
            for(Move x:movesForDice5)
                moves[index++]=x;   
        if(movesForDice6!=null)
            for(Move x:movesForDice6)
                moves[index++]=x;     
        
        return moves;
    }

    @Override
    public Move[] getPossibleMovesForADie(int diceValue, RealmColor colorOfDice) {
        int counter=0;
        Move[] moves=null;
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

        if(counter!=0)
            moves=new Move[counter];

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

//============================G&S====================================================
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

    
}
