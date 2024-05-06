package main.java.game.realms;
import main.java.game.collectibles.ArcaneBoost;
import main.java.game.collectibles.Bonus;
import main.java.game.collectibles.ElementalCrest;
import main.java.game.collectibles.Reward;
import main.java.game.collectibles.TimeWarp;
import main.java.game.creatures.*;
import main.java.game.dice.*;
import main.java.game.engine.Move;


public class GreenRealm extends Realms{
    Gaia gaiaGurdian;
    private final int rewardColumn1;
    private final int rewardColumn2;
    private final int rewardColumn3;
    private final int rewardColumn4;
    private final int rewardRow1;
    private final int rewardRow2;
    private final int rewardRow3;
//============================Constructor============================================
    public GreenRealm(){
        super(RealmColor.GREEN,7,12);
        gaiaGurdian=new Gaia();
        rewardColumn1=0;
        rewardColumn2=1;
        rewardColumn3=2;
        rewardColumn4=3;
        rewardRow1=4;
        rewardRow2=5;
        rewardRow3=6;
    }
//============================Methods================================================
    @Override
    public boolean attack(int diceValue, Creature creature) {//diceValue is green+white
        if(gaiaGurdian.checkPossibleAttack(diceValue)){
            gaiaGurdian.killGaiaGurdian(diceValue);
            incrementTotalNumberOfAttacks();
            updateTotalRealmScore(getTotalNumberOfAttacks());
            recordAttack(diceValue);//the string thing
            if(isRealmDefeated())
                    closeRealm();
            //get rewards
            return true;
        }
        return false;
    }
    @Override
    public void updateTotalRealmScore(int totalNumberOfAttacks) {
        switch(totalNumberOfAttacks){
            case 1:setTotalRealmScore(1);break;
            case 2:setTotalRealmScore(2);break;
            case 3:setTotalRealmScore(4);break;
            case 4:setTotalRealmScore(7);break;
            case 5:setTotalRealmScore(11);break;
            case 6:setTotalRealmScore(16);break;
            case 7:setTotalRealmScore(22);break;
            case 8:setTotalRealmScore(29);break;
            case 9:setTotalRealmScore(37);break;
            case 10:setTotalRealmScore(46);break;
            case 11:setTotalRealmScore(56);break;
        }
        
    }
    @Override
    public boolean isRealmDefeated() {
        return getTotalNumberOfAttacks()==11;
    }
    @Override
    public Move[] getAllPossibleMoves() {
        if(!isRealmAccessible())
            return new Move[0];
        boolean[] gurdiansHealth=gaiaGurdian.getGurdiansHealth();
        int moveArraySize=0;
        for(boolean gaiaAlive:gurdiansHealth)
            if(gaiaAlive)moveArraySize++;
        Move[] moves=new Move[moveArraySize];
        
        for(int i=2,arr=0;i<gurdiansHealth.length;i++)
            if(gurdiansHealth[i])
                moves[arr++]=getPossibleMovesForADie(i,RealmColor.GREEN)[0];
        return moves;
    }
    @Override
    public Move[] getPossibleMovesForADie(int diceValue,RealmColor colorOfDice) {
        if(gaiaGurdian.checkPossibleAttack(diceValue)){
                 Dice tempDice=new GreenDice(diceValue);
            return new Move[]{new Move(tempDice,gaiaGurdian)};
        }
        return new Move[0];
    }
    @Override
    public boolean isRewardAvailable() {
        boolean[] gurdiansHealth=gaiaGurdian.getGurdiansHealth();
        Reward[] rewards=getRealmRewards();

        if(!(gurdiansHealth[5]||gurdiansHealth[9])&&rewards[rewardColumn1]!=null)
            return true;
        else if(!(gurdiansHealth[2]||gurdiansHealth[6]||gurdiansHealth[10])&&rewards[rewardColumn2]!=null)   
            return true;
        else if(!(gurdiansHealth[3]||gurdiansHealth[7]||gurdiansHealth[11])&&rewards[rewardColumn3]!=null)
            return true;
        else if(!(gurdiansHealth[4]||gurdiansHealth[8]||gurdiansHealth[12])&&rewards[rewardColumn4]!=null)      
            return true;
        else if(!(gurdiansHealth[2]||gurdiansHealth[3]||gurdiansHealth[4])&&rewards[rewardRow1]!=null)
            return true;
        else if(!(gurdiansHealth[5]||gurdiansHealth[6]||gurdiansHealth[7]||gurdiansHealth[8])&&rewards[rewardRow2]!=null)
            return true;
        else if(!(gurdiansHealth[9]||gurdiansHealth[10]||gurdiansHealth[11]||gurdiansHealth[12])&&rewards[rewardRow3]!=null)
            return true; 
       return false;                     
    }
    @Override
    public void initializePreviousAttacks(String[] previousAttacks) {
        previousAttacks[0]="X    ";//Everything shifted Down one
        for(int i=1;i<9;i++)
            previousAttacks[i]=(i+1)+"    ";
        previousAttacks[9]=10+"   ";
        previousAttacks[10]=11+"   ";
        previousAttacks[11]=12+"   ";      
    }
    @Override
    public void recordAttack(int diceValue){
        String[]previousAttacks=getPreviousAttacks();
        previousAttacks[diceValue-1]="X    ";

    }
//============================G&S====================================================   
    public Gaia getGaia(){
        return gaiaGurdian;
    }
    @Override
    public Reward getReward() {
        boolean[] gurdiansHealth=gaiaGurdian.getGurdiansHealth();
        Reward[] rewards=getRealmRewards();

        if(!(gurdiansHealth[5]||gurdiansHealth[9])&&rewards[rewardColumn1]!=null){
            Reward recievedReward=rewards[rewardColumn1];
            rewardClaimed(rewardColumn1);
            return recievedReward;
        }
        else if(!(gurdiansHealth[2]||gurdiansHealth[6]||gurdiansHealth[10])&&rewards[rewardColumn2]!=null){
            Reward recievedReward=rewards[rewardColumn2];
            rewardClaimed(rewardColumn2);
            return recievedReward;
        } 
        else if(!(gurdiansHealth[3]||gurdiansHealth[7]||gurdiansHealth[11])&&rewards[rewardColumn3]!=null){
            Reward recievedReward=rewards[rewardColumn3];
            rewardClaimed(rewardColumn3);
            return recievedReward;
        }
            
        else if(!(gurdiansHealth[4]||gurdiansHealth[8]||gurdiansHealth[12])&&rewards[rewardColumn4]!=null){
            Reward recievedReward=rewards[rewardColumn4];
            rewardClaimed(rewardColumn4);
            return recievedReward;
        }       
        else if(!(gurdiansHealth[2]||gurdiansHealth[3]||gurdiansHealth[4])&&rewards[rewardRow1]!=null){
            Reward recievedReward=rewards[rewardRow1];
            rewardClaimed(rewardRow1);
            return recievedReward;
        }    
        else if(!(gurdiansHealth[5]||gurdiansHealth[6]||gurdiansHealth[7]||gurdiansHealth[8])&&rewards[rewardRow2]!=null){
            Reward recievedReward=rewards[rewardRow2];
            rewardClaimed(rewardRow2);
            return recievedReward;

        }  
        else if(!(gurdiansHealth[9]||gurdiansHealth[10]||gurdiansHealth[11]||gurdiansHealth[12])&&rewards[rewardRow3]!=null){
            Reward recievedReward=rewards[rewardRow3];
            rewardClaimed(rewardRow3);
            return recievedReward;
        }
        return null;
    }
    @Override
    public void setRealmRewards(Reward[] realmRewards) {
       Reward[] templateRewards=new Reward[]{new TimeWarp(),new Bonus(RealmColor.BLUE),new Bonus(RealmColor.MAGENTA),
        new ArcaneBoost(),new Bonus(RealmColor.YELLOW),new Bonus(RealmColor.RED),new ElementalCrest()};  

        for(int i=0;i<templateRewards.length;i++)
            realmRewards[i]=templateRewards[i];
    }
    public  Creature getCreatureByRealm(Dice dice){
        return gaiaGurdian;
    }
//============================toString===============================================   
    @Override
    public String toString() {
        String[] prevAt=getPreviousAttacks();//previousAttacks
        String[] drawRew=new String[]{"TW   ","BB   ","MB   ","AB   ","YB   ","RB   ","EC   "};//drawReward
        Reward[] rewards=getRealmRewards();
        if(rewards[rewardColumn1]==null)
            drawRew[0]="X    ";
        if(rewards[rewardColumn2]==null)
            drawRew[1]="X    ";
        if(rewards[rewardColumn3]==null)
            drawRew[2]="X    ";     
        if(rewards[rewardColumn4]==null)
            drawRew[3]="X    ";
        if(rewards[rewardRow1]==null)
            drawRew[4]="X    ";
        if(rewards[rewardRow2]==null)
            drawRew[5]="X    ";
        if(rewards[rewardRow3]==null)
            drawRew[6]="X    ";
         


        return "Terra's Heartland: Gaia Guardians (GREEN REALM):"+"\n"+ 
        "+-----------------------------------+"+"\n"+
        "|  #  |1    |2    |3    |4    |R    |"+"\n"+
        "+-----------------------------------+"+"\n"+
        "|  1  |"+prevAt[0]+"|"+prevAt[1]+"|"+prevAt[2]+"|"+prevAt[3]+"|"+drawRew[4]+"|"+"\n"+
        "|  2  |"+prevAt[4]+"|"+prevAt[5]+"|"+prevAt[6]+"|"+prevAt[7]+"|"+drawRew[5]+"|"+"\n"+
        "|  3  |"+prevAt[8]+"|"+prevAt[9]+"|"+prevAt[10]+"|"+prevAt[11]+"|"+drawRew[6]+"|"+"\n"+
        "+-----------------------------------+"+"\n"+
        "|  R  |"+drawRew[0]+"|"+drawRew[1]+"|"+drawRew[2]+"|"+drawRew[3]+"|     |"+"\n"+
        "+-----------------------------------------------------------------------+"+"\n"+
        "|  S  |1    |2    |4    |7    |11   |16   |22   |29   |37   |46   |56   |"+"\n"+
        "+-----------------------------------------------------------------------+"+"\n\n";

    }
   
    

}
